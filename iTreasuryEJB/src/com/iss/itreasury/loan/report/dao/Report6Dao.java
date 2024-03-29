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
import com.iss.itreasury.util.DataFormat;
import com.iss.system.dao.SqlUtil;

/**
 * @author yinghu
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class Report6Dao extends LoanDAO {

    public Report6Dao() {
        super("");
        // TODO Auto-generated constructor stub
    }

    private String CreateSqlToVerifyContractformStatus(ReportWhereInfo wInfo) {
        StringBuffer result = new StringBuffer();
        result.append("and loan_ContractForm.nCurrencyId="
                + wInfo.getCurrencyId() + " \n");
        result.append("and loan_ContractForm.nOfficeId=" + wInfo.getOfficeId()
                + " \n");
        result.append("and loan_ContractForm.nStatusId in (5,6,7,8,9) \n");
        return result.toString();
    }

    private String createSqlForContractFormColumn(ReportWhereInfo wInfo) {
        StringBuffer result = new StringBuffer();
        result.append("select loan_ContractForm.id as LongColumn1, \n");
        result.append("loan_ContractForm.nSubTypeId as LongColumn2, \n");
        result.append("loan_ContractForm.nTypeId2 as LongColumn3 \n");
        result.append("from loan_ContractForm \n");
        result
                .append("where to_char(loan_ContractForm.dtStartDate,'yyyy-mm-dd')<='"
                        + DataFormat.getDateString(wInfo.getDate()) + "' \n");
        result
                .append("and to_char(loan_ContractForm.dtEndDate,'yyyy-mm-dd')>='"
                        + DataFormat.getDateString(wInfo.getDate()) + "' \n");
        result.append(CreateSqlToVerifyContractformStatus(wInfo));
        result.append("order by loan_ContractForm.id \n");
        return result.toString();
    }

    /**
     * 查询财务公司贷款行业分类情况表2
     * 
     * @param wInfo
     * @return
     * @throws Exception
     */
    public ReportResultInfo[] queryLoanTradeClassifyConditionWay2(
            ReportWhereInfo wInfo) throws Exception {
        StringBuffer strSql = new StringBuffer();
        strSql.append(createSqlForContractFormColumn(wInfo));
        //System.out.println(strSql.toString());
        ReportResultInfo[] result = null;
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