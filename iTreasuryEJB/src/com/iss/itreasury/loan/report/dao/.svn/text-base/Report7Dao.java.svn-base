/*
 * Created on 2005-11-28
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.loan.report.dao;

import com.iss.itreasury.dao.LoanDAO;
import com.iss.itreasury.loan.report.dataentity.ReportResultInfo;
import com.iss.itreasury.loan.report.dataentity.ReportWhereInfo;
import com.iss.system.dao.SqlUtil;

/**
 * @author yinghu
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class Report7Dao extends LoanDAO {

    public Report7Dao() {
        super("");
        // TODO Auto-generated constructor stub
    }
    
    /**
     * 查询财务公司综合授信情况
     * @param wInfo
     * @return
     * @throws Exception
     */
    public ReportResultInfo[] queryTreasuryCompanyIntegrateGrantCreditCondition(ReportWhereInfo wInfo) throws Exception{
        StringBuffer strSql=new StringBuffer();
        strSql.append("select client.sCode as StringColumn1, \n");
        strSql.append("client.sName as StringColumn2, \n");
        strSql.append("loan_CreditLimit.startDate as TsColumn1, \n");
        strSql.append("loan_CreditLimit.endDate as TsColumn2, \n");
        strSql.append("loan_CreditLimit.creditAmount as DoubleColumn1, \n");
        strSql.append("loan_CreditLimit.assureAmount as DoubleColumn2, \n");
        strSql.append("loan_CreditLimit.remark as StringColumn3, \n");
        strSql.append("client.id as LongColumn1 \n");
        strSql.append("from client,loan_CreditLimit \n");
        strSql.append("where loan_CreditLimit.clientId=client.id \n");
        strSql.append("and loan_CreditLimit.statusId=3 \n");
        strSql.append("and client.nOfficeId="+wInfo.getOfficeId()+" \n");
        strSql.append("order by client.sCode \n");
        System.out.println(strSql.toString());
        ReportResultInfo[] result=null;
        try {
            this.initDAO();
            this.prepareStatement(strSql.toString());
            this.executeQuery();
            result = (ReportResultInfo[]) SqlUtil
                    .parseDataEntityBeans(transRS, "",
                            "com.iss.itreasury.loan.report.dataentity.ReportResultInfo");
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            this.finalizeDAO();
        }
        return result;
    }
}