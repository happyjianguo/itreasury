/*
 * Created on 2005-10-17
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.settlement.report.dao;

import java.sql.Timestamp;
import com.iss.itreasury.settlement.query.paraminfo.QueryFixedDepositInfo;
import com.iss.itreasury.settlement.query.queryobj.BaseQueryObject;
import com.iss.itreasury.settlement.report.dataentity.ReportResultInfo;
import com.iss.itreasury.util.DataFormat;
import com.iss.system.dao.SqlUtil;

/**
 * @author yinghu
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class Report3Dao extends BaseQueryObject {

    private String createSqlToCreateVirtualDateColumn() {
        StringBuffer result = new StringBuffer();
        result.append("(");
        result.append("select '01' as day from dual \n");
        for (int i = 2; i <= 9; i++) {
            result.append("union select '" + "0" + i + "' from dual \n");
        }
        for (int i = 10; i <= 31; i++) {
            result.append("union select '" + i + "' from dual \n");
        }
        result.append(") \n");
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

    private String createSqlToVerifyBranchStatus(QueryFixedDepositInfo qInfo) {
        StringBuffer result = new StringBuffer();
        result.append("and sett_Branch.nCurrencyId=" + qInfo.getCurrencyID()
                + " \n");
        result.append("and sett_Branch.nOfficeId=" + qInfo.getOfficeID()
                + " \n");
        result.append("and sett_Branch.nStatusId=1 \n");
        return result.toString();
    }

    private static String createSqlToVerifyBankAccountOffilialeStatus(
            QueryFixedDepositInfo qInfo) {
        StringBuffer result = new StringBuffer();
        result.append("and SETT_BANKACCOUNTOFFILIALE.nCurrencyId="
                + qInfo.getCurrencyID() + " \n");
        result.append("and SETT_BANKACCOUNTOFFILIALE.nOfficeId="
                + qInfo.getOfficeID() + " \n");
        return result.toString();
    }

    /**
     * 生成当地银行存款-日期列
     * 
     * @param date
     * @param qInfo
     * @param bankAccountCodes
     * @return
     */
    private String createSqlForBranchDateColumn(Timestamp date,
            QueryFixedDepositInfo qInfo, String bankAccountCodes) {
        StringBuffer result = new StringBuffer();
        result
                .append("(select substr(to_char(sett_BalanceOfBankAccount.dtCurrent,'yyyy-mm-dd'),9,2) as day,\n");
        result
                .append("sum(nvl(sett_BalanceOfBankAccount.mBalance,0)) as balance \n");
        result.append("from sett_BalanceOfBankAccount,sett_Branch \n");
        result
                .append("where sett_Branch.sBankAccountCode=sett_BalanceOfBankAccount.sBankAccountNo \n");
        result.append("and sett_Branch.sBankAccountCode in ("
                + bankAccountCodes + ") \n");
        result.append(createSqlToVerifyBranchStatus(qInfo));
        result.append(createSqlToVerifyDate(
                "sett_BalanceOfBankAccount.dtCurrent", DataFormat
                        .getFirstDateOfMonth(date), DataFormat
                        .getLastDateOfMonth(date)));
        result
                .append("group by substr(to_char(sett_BalanceOfBankAccount.dtCurrent,'yyyy-mm-dd'),9,2)) \n");
        return result.toString();
    }

    /**
     * 生成工行二级户的日期-金额列
     * 
     * @param date
     * @param qInfo
     * @return
     */
    private String createSqlForGradeTwoAccountDateColumn(Timestamp date,
            QueryFixedDepositInfo qInfo, String bankAccountCodes) {
        StringBuffer result = new StringBuffer();
        result
                .append("(select substr(to_char(sett_BalanceOfBankAccount.dtCurrent,'yyyy-MM-dd'),9,2) as day,\n");
        result
                .append("sum(nvl(sett_BalanceOfBankAccount.mBalance,0)) as balance \n");
        result
                .append("from sett_BalanceOfBankAccount,SETT_BANKACCOUNTOFFILIALE, \n");
        result.append("sett_Branch \n");
        result
                .append("where SETT_BANKACCOUNTOFFILIALE.sBankAccountNo=sett_BalanceOfBankAccount.sBankAccountNo \n");
        result
                .append("and SETT_BANKACCOUNTOFFILIALE.nUpBankAccountId=sett_Branch.id \n");
        result.append("and sett_Branch.sBankAccountCode in ("
                + bankAccountCodes + ") \n");
        result.append(createSqlToVerifyBankAccountOffilialeStatus(qInfo));
        result.append(createSqlToVerifyDate(
                "sett_BalanceOfBankAccount.dtCurrent", DataFormat
                        .getFirstDateOfMonth(date), DataFormat
                        .getLastDateOfMonth(date)));
        result
                .append("group by substr(to_char(sett_BalanceOfBankAccount.dtCurrent,'yyyy-MM-dd'),9,2)) \n");
        return result.toString();
    }

    public ReportResultInfo[] queryBalanceOfGradeTwoAccountOfIndustrialBank(
            Timestamp date, QueryFixedDepositInfo qInfo) throws Exception {
        StringBuffer strSql = new StringBuffer();
        strSql.append("select aa.day as StringColumn1 \n");
        for (int i = 1; i <= 8; i++) {
            strSql.append(",bb" + i + ".balance as DoubleColumn" + i + " \n");
        }
        strSql.append("from \n");
        strSql.append(createSqlToCreateVirtualDateColumn()); //建立日期虚拟表
        strSql.append("aa, \n");
        StringBuffer bankAccountCodes = new StringBuffer();
        bankAccountCodes.append("'0200001919223001243' \n");
        strSql.append(createSqlForGradeTwoAccountDateColumn(date, qInfo,
                bankAccountCodes.toString()));//宣武二级户
        strSql.append("bb1, \n");
        bankAccountCodes.setLength(0);
        bankAccountCodes.append("'0200004919027310217' \n");
        strSql.append(createSqlForGradeTwoAccountDateColumn(date, qInfo,
                bankAccountCodes.toString()));//永定路二级户
        strSql.append("bb2, \n");
        bankAccountCodes.setLength(0);
        bankAccountCodes.append("'2403020619999999811' \n");
        strSql.append(createSqlForGradeTwoAccountDateColumn(date, qInfo,
                bankAccountCodes.toString()));//遵义二级户
        strSql.append("bb3, \n");
        bankAccountCodes.setLength(0);
        bankAccountCodes.append("'-1' \n");
        strSql.append(createSqlForGradeTwoAccountDateColumn(date, qInfo,
                bankAccountCodes.toString()));//其它
        strSql.append("bb4, \n");
        bankAccountCodes.setLength(0);
        bankAccountCodes.append("'0200001919223001243' \n");
        strSql.append(createSqlForBranchDateColumn(date, qInfo,
                bankAccountCodes.toString()));//宣武开户行
        strSql.append("bb5, \n");
        bankAccountCodes.setLength(0);
        bankAccountCodes.append("'0200004919027310217' \n");
        strSql.append(createSqlForBranchDateColumn(date, qInfo,
                bankAccountCodes.toString()));//永定路开户行
        strSql.append("bb6, \n");
        bankAccountCodes.setLength(0);
        bankAccountCodes.append("'2403020619999999811' \n");
        strSql.append(createSqlForBranchDateColumn(date, qInfo,
                bankAccountCodes.toString()));//遵义开户行
        strSql.append("bb7, \n");
        bankAccountCodes.setLength(0);
        bankAccountCodes.append("'-1' \n");
        strSql.append(createSqlForBranchDateColumn(date, qInfo,
                bankAccountCodes.toString()));//其它
        strSql.append("bb8 \n");
        strSql.append("where 1=1 \n");
        for (int i = 1; i <= 8; i++) {
            strSql.append("and aa.day=bb" + i + ".day(+) \n");
        }
        strSql.append("\n order by to_number(aa.day) \n");
        System.out.println(strSql.toString());
        ReportResultInfo[] results = null;
        try {
            initDAO();
            prepareStatement(strSql.toString());
            executeQuery();
            results = (ReportResultInfo[]) SqlUtil
                    .parseDataEntityBeans(transRS, "",
                            "com.iss.itreasury.settlement.report.dataentity.ReportResultInfo");
        } catch (Exception e) {
            throw e;
        } finally {
            finalizeDAO();
        }
        return results;
    }

    public static void main(String[] args) {
        Report3Dao dao = new Report3Dao();
        QueryFixedDepositInfo qInfo = new QueryFixedDepositInfo();
        qInfo.setOfficeID(1);
        qInfo.setCurrencyID(1);
        Timestamp ts = DataFormat.getDateTime("2005-10-11");
        try {
            dao.queryBalanceOfGradeTwoAccountOfIndustrialBank(ts, qInfo);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}