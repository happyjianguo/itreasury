/*
 * Created on 2005-11-14
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.loan.report.bizlogic;

import java.util.Collection;
import java.util.LinkedList;
import com.iss.itreasury.loan.report.dao.Report4Dao;
import com.iss.itreasury.loan.report.dataentity.ReportResultInfo;
import com.iss.itreasury.loan.report.dataentity.ReportWhereInfo;

/**
 * @author yinghu
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class Report4 {
    public Collection queryMensalLoanAtTermDetail(ReportWhereInfo wInfo) throws Exception{
        ReportResultInfo[] infosFrom=(new Report4Dao()).queryMensalLoanAtTermDetail(wInfo);
        if(infosFrom==null||infosFrom.length==0){
            return new LinkedList();
        } 
        ReportResultInfo gatherInfo=CommonBiz.calcTotal(infosFrom);
        gatherInfo.setStringColumn1("ºÏ¼Æ");
        LinkedList result=new LinkedList();
        for(int i=0;i<infosFrom.length;i++){
            CommonBiz.devideByTenThousandsForAllDoubleColumn(infosFrom[i]);
            result.add(infosFrom[i]);
        }
        CommonBiz.devideByTenThousandsForAllDoubleColumn(gatherInfo);
        result.add(gatherInfo);
        return result;
    }
}