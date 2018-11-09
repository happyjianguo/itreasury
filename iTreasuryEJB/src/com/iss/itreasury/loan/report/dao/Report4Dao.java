/*
 * Created on 2005-11-28
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.loan.report.dao;

import java.sql.Timestamp;
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
public class Report4Dao extends LoanDAO {

    public Report4Dao() {
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

    private String createSqlToVerifyDate(String dayColumnName,
            Timestamp firstDay, Timestamp lastDay) {
        StringBuffer result = new StringBuffer();
        result.append("and to_char(" + dayColumnName + ",'yyyy-mm-dd')>=\n");
        result.append("'" + DataFormat.getDateString(firstDay) + "' \n");
        result.append("and to_char(" + dayColumnName + ",'yyyy-mm-dd')<=\n");
        result.append("'" + DataFormat.getDateString(lastDay) + "' \n");
        return result.toString();
    }

    //生成普通列，包括id,合同号,客户名称
    private String createSqlForIdContractCodeClientColumns(ReportWhereInfo wInfo) {
        StringBuffer result = new StringBuffer();
        result.append("(select loan_ContractForm.id as id, \n");
        result.append("sContractCode as contractCode, \n");
        result.append("client.sName as clientName \n");
        result.append("from loan_ContractForm,client \n");
        result.append("where to_char(dtEndDate,'yyyy-mm-dd')>=\n");
        result.append("'"
                + DataFormat.getDateString(DataFormat.getNextMonth(DataFormat
                        .getFirstDateOfMonth(wInfo.getDate()), 1)) + "' \n");
        result.append(CreateSqlToVerifyContractformStatus(wInfo));
        result.append("and loan_ContractForm.nBorrowClientId=client.id) \n");
        return result.toString();
    }

    //月份放在wInfo里的date内,isInMonth为true查询一个月份之内，isInMonth为false
    //查询一年以上
    private String createSqlForAmountColumnInTheMonth(ReportWhereInfo wInfo,
            boolean isInMonth) {
        StringBuffer result = new StringBuffer();
        result.append("(select id,nvl(mExamineAmount,0) as amount \n");
        result.append("from loan_ContractForm \n");
        result.append("where 1=1 \n");
        if (isInMonth) {
            result.append(createSqlToVerifyDate("dtEndDate", DataFormat
                    .getFirstDateOfMonth(wInfo.getDate()), DataFormat
                    .getLastDateOfMonth(wInfo.getDate())));
        } else {
            //进行判断，使结束日期在查询月份的一年以后
            result.append("and to_char(dtEndDate,'yyyy-mm-dd')>=\n");
            result.append("'"
                    + DataFormat.getDateString(DataFormat
                            .getNextMonth(DataFormat.getFirstDateOfMonth(wInfo
                                    .getDate()), 13)) + "' \n");
        }
        result.append(CreateSqlToVerifyContractformStatus(wInfo));
        result.append(") \n");
        return result.toString();
    }

    /**
     * 查询贷款每月到期明细
     * 
     * @param wInfo
     * @return ReportResultInfo[]
     * @throws Exception
     */
    public ReportResultInfo[] queryMensalLoanAtTermDetail(ReportWhereInfo wInfo)
            throws Exception {
        //保存查询条件中的日期
        Timestamp date=wInfo.getDate();
        StringBuffer strSql = new StringBuffer();
        strSql.append("select aa.contractCode as StringColumn1 \n");
        strSql.append(",aa.clientName as StringColumn2 \n");
        for (int i = 1; i <= 13; i++) {
            strSql.append(",bb" + i + ".amount as DoubleColumn" + i + " \n");
        }
        strSql.append("from \n");
        strSql.append(createSqlForIdContractCodeClientColumns(wInfo));
        strSql.append("aa, \n");
        for (int i = 1; i <= 12; i++) {
            wInfo.setDate(DataFormat.getNextMonth(wInfo.getDate(), i));
            strSql.append(createSqlForAmountColumnInTheMonth(wInfo, true));
            strSql.append("bb" + i + ", \n");
            //恢复查询条件中的日期
            wInfo.setDate(date);
        }
        strSql.append(createSqlForAmountColumnInTheMonth(wInfo, false));
        strSql.append("bb13 \n");
        strSql.append("where 1=1 \n");
        for (int i = 1; i <= 13; i++) {
            strSql.append("and aa.id=bb" + i + ".id(+) \n");
        }
        strSql.append("order by aa.contractCode \n");
        System.out.println(strSql.toString());
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