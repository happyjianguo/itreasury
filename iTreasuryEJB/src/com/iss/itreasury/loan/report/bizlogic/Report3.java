/*
 * Created on 2005-11-14
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.loan.report.bizlogic;

import java.util.Collection;
import java.util.LinkedList;
import com.iss.itreasury.loan.report.dao.Report3Dao;
import com.iss.itreasury.loan.report.dataentity.ReportResultInfo;
import com.iss.itreasury.loan.report.dataentity.ReportWhereInfo;
import com.iss.itreasury.loan.util.LOANConstant;

/**
 * @author yinghu
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class Report3 {
    /**
     * 查询月贷款到期明细，返回页面需要的数据集
     * @param wInfo
     * @return Collection
     * @throws Exception
     */
    public Collection queryLoanAtTermDetailInOneMonth(ReportWhereInfo wInfo)
            throws Exception {
        ReportResultInfo[] infosFrom = (new Report3Dao())
                .queryLoanAtTermDetailInOneMonth(wInfo);
        if (infosFrom==null||infosFrom.length == 0) {
            return new LinkedList();
        }
        ReportResultInfo gatherInfo = CommonBiz.calcTotal(infosFrom);
        LinkedList result = new LinkedList();
        boolean isTypeChanged = false;
        for (int i = 0; i < infosFrom.length; i++) {
            if (isAbleToBuildGatherNotAtTail(infosFrom,i,isTypeChanged)) {
                result.add(buildGatherInfo(infosFrom,-1));
                isTypeChanged=true;
                }
            result.add(infosFrom[i]);
            //末尾处的汇总
            if(i==(infosFrom.length-1)){
                result.add(buildGatherInfo(infosFrom,infosFrom[i].getLongColumn1()));
            }
        }
        return result;
    }
    
    private ReportResultInfo buildGatherInfo(ReportResultInfo[] sourceInfos,long loanTypeId) throws Exception{
        LinkedList list=new LinkedList();
        if(loanTypeId==LOANConstant.LoanType.DB){
            for(int i=0;i<sourceInfos.length;i++){
                if(sourceInfos[i].getLongColumn1()==LOANConstant.LoanType.DB){
                    list.add(sourceInfos[i]);
                }
            }
        }
        else{
            for(int i=0;i<sourceInfos.length;i++){
                if(sourceInfos[i].getLongColumn1()!=LOANConstant.LoanType.DB){
                    list.add(sourceInfos[i]);
                }
            } 
        }
        Object[] infos=list.toArray();
        ReportResultInfo result=new ReportResultInfo();
        result.setDoubleColumn1(CommonBiz.calcTotal(infos,"DoubleColumn1"));
        result.setLongColumn1(-1);
        return result;
    }

    private boolean isAbleToBuildGatherNotAtTail(ReportResultInfo[] infos, int pos,
            boolean isTypeChanged) {
        return infos[0].getLongColumn1() != LOANConstant.LoanType.DB
                && infos[pos].getLongColumn1() == LOANConstant.LoanType.DB
                && isTypeChanged == false;

    }
}