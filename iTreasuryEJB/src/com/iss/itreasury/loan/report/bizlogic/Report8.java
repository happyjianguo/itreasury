/*
 * Created on 2005-11-14
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.loan.report.bizlogic;

import java.util.Collection;
import java.util.LinkedList;
import com.iss.itreasury.loan.report.dao.Report8Dao;
import com.iss.itreasury.loan.report.dataentity.ReportResultInfo;
import com.iss.itreasury.loan.report.dataentity.ReportWhereInfo;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.util.DataFormat;

/**
 * @author yinghu
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class Report8 {
    
    /**
     * 查询财务公司客户评级情况
     * @param wInfo
     * @return
     * @throws Exception
     */
    public Collection queryTreasuryCompanyClientRiskLevelCondition(ReportWhereInfo wInfo) throws Exception{
        ReportResultInfo[] infos=(new Report8Dao().queryTreasuryCompanyClientRiskLevelCondition(wInfo));
        if(infos==null||infos.length==0){
            return new LinkedList();
        }
        LinkedList result=new LinkedList();
        for(int i=0;i<infos.length;i++){
            result.add(trasferInfo(infos[i]));
        }
        return result;
    }
    
    private ReportResultInfo trasferInfo(ReportResultInfo info){
        ReportResultInfo result=new ReportResultInfo();
        result.setStringColumn1(info.getStringColumn1());
        result.setStringColumn2(info.getStringColumn2());
        result.setStringColumn3(LOANConstant.CreditLevel.getName(info.getLongColumn1()));
        result.setTsColumn1(info.getTsColumn1());
        result.setTsColumn2(info.getTsColumn2());
        result.setTsColumn3(DataFormat.getNextMonth(info.getTsColumn1(),6));
        result.setDoubleColumn1(info.getDoubleColumn1()/10000);
        result.setStringColumn4(info.getStringColumn3());
        return result;
    }
}