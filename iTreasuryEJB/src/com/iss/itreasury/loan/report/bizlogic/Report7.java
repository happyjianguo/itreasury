/*
 * Created on 2005-11-14
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.loan.report.bizlogic;

import com.iss.itreasury.loan.credit.bizlogic.CreditBiz;
import com.iss.itreasury.loan.report.dao.Report7Dao;
import com.iss.itreasury.loan.report.dataentity.ReportResultInfo;
import com.iss.itreasury.loan.report.dataentity.ReportWhereInfo;

/**
 * @author yinghu
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class Report7 {
    
    /**
     * 查询财务公司综合授信情况
     * @param wInfo
     * @return
     * @throws Exception
     */
    public ReportResultInfo[] queryTreasuryCompanyIntegrateGrantCreditCondition(ReportWhereInfo wInfo) throws Exception{
        ReportResultInfo[] infos=(new Report7Dao()).queryTreasuryCompanyIntegrateGrantCreditCondition(wInfo);
        if(infos==null||infos.length==0){
            return new ReportResultInfo[]{};
        }
        CreditBiz creditBiz=new CreditBiz();
        for(int i=0;i<infos.length;i++){
       //     infos[i].setDoubleColumn3(creditBiz.getuseCreditAmount(infos[i].getLongColumn1()));
       //     infos[i].setDoubleColumn4(creditBiz.getuseAssureAmount(infos[i].getLongColumn1()));
            CommonBiz.devideByTenThousandsForAllDoubleColumn(infos[i]);
        }
        return infos;
    }
}