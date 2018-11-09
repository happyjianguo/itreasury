/*
 * Created on 2005-11-16
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.settlement.report.dao;

import java.sql.Timestamp;
import com.iss.itreasury.settlement.query.paraminfo.QueryFixedDepositInfo;
import com.iss.itreasury.settlement.query.queryobj.BaseQueryObject;
import com.iss.itreasury.util.DataFormat;

/**
 * @author yinghu
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class Report12Dao extends BaseQueryObject {

    public double queryBalanceBySubjectCodeAccurately(Timestamp date,
            QueryFixedDepositInfo qInfo, String subjectCodes) throws Exception {
        StringBuffer strSql = new StringBuffer();
        strSql
                .append("select sum(decode(nBalanceDirection,1,mDebitBalance,2,mCreditBalance)) as balance \n");
        strSql.append("from SETT_GLBALANCE \n");
        strSql.append("where to_char(dtGlDate,'yyyy-MM-dd')='"
                + DataFormat.getDateString(date) + "' \n");
        strSql.append("and sGlSubjectCode in (" + subjectCodes + ") \n");
        strSql.append("and nOfficeId=" + qInfo.getOfficeID() + " \n");
        strSql.append("and nCurrencyId=" + qInfo.getCurrencyID() + " \n");
        return executeQueryAndGetDoubleValue(strSql.toString());
    }

    public double queryBalanceBySubjectCodeMistily(Timestamp date,
            QueryFixedDepositInfo qInfo, String subjectCode) throws Exception {
        StringBuffer strSql = new StringBuffer();
        strSql
                .append("select sum(decode(nBalanceDirection,1,mDebitBalance,2,mCreditBalance)) as balance \n");
        strSql.append("from SETT_GLBALANCE \n");
        strSql.append("where to_char(dtGlDate,'yyyy-MM-dd')='"
                + DataFormat.getDateString(date) + "' \n");
        strSql.append("and sGlSubjectCode like '" + subjectCode + "%' \n");
        strSql.append("and nOfficeId=" + qInfo.getOfficeID() + " \n");
        strSql.append("and nCurrencyId=" + qInfo.getCurrencyID() + " \n");
        return executeQueryAndGetDoubleValue(strSql.toString());
    }

    private String createSqlToVerifyAccountDailyAccountBalance(
            QueryFixedDepositInfo qInfo) {
        StringBuffer result = new StringBuffer();
        result.append("and sett_Account.nofficeid=" + qInfo.getOfficeID()
                + " \n");
        result.append("and sett_Account.nCurrencyID=" + qInfo.getCurrencyID()
                + " \n");
        result
                .append("and sett_DailyAccountBalance.nSubAccountStatusId <> 0 \n");
        result
                .append("and sett_DailyAccountBalance.nSubAccountStatusId <> 2 \n");
        return result.toString();
    }

    public double queryFixedDeposit(Timestamp date,
            QueryFixedDepositInfo qInfo, boolean isLongTerm) throws Exception {
        StringBuffer strSql = new StringBuffer();
        strSql.append("select sum(nvl(sett_DailyAccountBalance.mBalance,0)) as balance \n");
        strSql.append("from sett_account, sett_subAccount, \n");
        strSql.append("SETT_DAILYACCOUNTBALANCE \n");
        strSql.append("where sett_Subaccount.nAccountID=sett_Account.id \n");
        strSql
                .append("and sett_DailyAccountBalance.NSUBACCOUNTID=sett_Subaccount.Id \n");
        if (isLongTerm) {
            strSql.append("and sett_Subaccount.af_ndepositterm>12");
        } else {
            strSql.append("and sett_Subaccount.af_NDepositterm<=12");
        }
        strSql
                .append("and to_char(sett_DailyAccountBalance.dtDate,'yyyy-mm-dd')='"
                        + DataFormat.getDateString(date) + "' \n");
        strSql.append("and sett_Account.nAccountTypeID in ("
                + getFixAccountType(qInfo.getCurrencyID(),qInfo.getOfficeID()) + ")  \n");
        strSql.append(createSqlToVerifyAccountDailyAccountBalance(qInfo));
        return executeQueryAndGetDoubleValue(strSql.toString());
    }

    private double executeQueryAndGetDoubleValue(String strSql)
            throws Exception {
        System.out.println(strSql);
        double result = 0.0;
        try {
            this.initDAO();
            this.prepareStatement(strSql.toString());
            this.executeQuery();
            this.transRS.next();
            result = this.transRS.getDouble("balance");
        } catch (Exception e) {
            throw e;
        } finally {
            this.finalizeDAO();
        }
        return result;
    }

    public static void main(String[] args) {
    }
}