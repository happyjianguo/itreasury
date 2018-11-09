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
import com.iss.itreasury.settlement.report.bizlogic.CommonBiz;
import com.iss.itreasury.settlement.report.dataentity.ReportResultInfo;
import com.iss.itreasury.util.DataFormat;
import com.iss.system.dao.SqlUtil;

/**
 * @author yinghu
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class Report6Dao extends BaseQueryObject {

    private String createSqlForClientTableColumn() {
        StringBuffer result = new StringBuffer();
        result.append("(select client.sName as clientName, \n");
        result.append("client.nSettClientTypeId as clientTypeId, \n");
        result.append("client.id as clientId \n");
        result.append("from client) \n");
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

    private String createSqlToVerifyBudgetStatus(QueryFixedDepositInfo qInfo) {
        StringBuffer result = new StringBuffer();
        result.append("and Budget_System.officeID=" + qInfo.getOfficeID()
                + " \n");
        result.append("and Budget_System.currencyId=" + qInfo.getCurrencyID()
                + " \n");
        result.append("and Budget_PlanDetail.statusId=1 \n");
        return result.toString();
    }

    private String createSqlToVerifyTransActionAccountStatus(
            QueryFixedDepositInfo qInfo) {
        StringBuffer result = new StringBuffer();
        result.append("and sett_Account.nofficeid=" + qInfo.getOfficeID()
                + " \n");
        result.append("and sett_Account.nCurrencyID=" + qInfo.getCurrencyID()
                + " \n");
        result.append("and sett_TransAccountDetail.nStatusId=3 \n");
        return result.toString();
    }

    private String createSqlToVerifyBankAccountOffiliale(
            QueryFixedDepositInfo qInfo) {
        StringBuffer result = new StringBuffer();
        result.append("and SETT_BANKACCOUNTOFFILIALE.nCurrencyId="
                + qInfo.getCurrencyID() + " \n");
        result.append("and SETT_BANKACCOUNTOFFILIALE.nOfficeId="
                + qInfo.getOfficeID() + " \n");
        return result.toString();
    }

    private String createSqlForCommonBudgetColumn(Timestamp firstMonth,
            Timestamp lastMonth, QueryFixedDepositInfo qInfo,
            String accountTypeIds) {
        StringBuffer result = new StringBuffer();
        result.append("(select client.id as clientId, \n");
        result
                .append("sum(nvl(Budget_ItemDetail.budgetAmount,0)) as amount \n");
        result.append("from Budget_ItemDetail,Budget_Plan, \n");
        result.append("client,Budget_System,Budget_PlanDetail \n");
        result.append("where Budget_ItemDetail.accountTypeId in ("
                + accountTypeIds + ") \n");
        result.append(createSqlToVerifyDate("Budget_ItemDetail.executeDate",
                DataFormat.getFirstDateOfMonth(firstMonth), DataFormat
                        .getLastDateOfMonth(lastMonth)));
        result.append(createSqlToVerifyBudgetStatus(qInfo));
        result.append("and Budget_Plan.BudgetFlag=0 \n");//正常预算
        result.append("and Budget_Plan.id=Budget_PlanDetail.planId \n");
        result
                .append("and Budget_PlanDetail.itemID=Budget_ItemDetail.itemId \n");
        result.append("and Budget_ItemDetail.clientId=client.id \n");
        result.append("and Budget_Plan.budgetSystemID=Budget_System.id \n");
        result.append("group by client.id) \n");
        return result.toString();
    }

    private String createSqlForAddBudgetColumn(Timestamp firstMonth,
            Timestamp lastMonth, QueryFixedDepositInfo qInfo,
            String accountTypeIds) {
        StringBuffer result = new StringBuffer();
        result.append("(select client.id as clientId, \n");
        result
                .append("sum(nvl(Budget_ItemDetail.budgetAmount,0)) as amount \n");
        result.append("from Budget_ItemDetail,Budget_Plan, \n");
        result.append("client,Budget_System,Budget_PlanDetail \n");
        result.append("where Budget_ItemDetail.accountTypeId in ("
                + accountTypeIds + ") \n");
        result.append(createSqlToVerifyDate("Budget_ItemDetail.executeDate",
                DataFormat.getFirstDateOfMonth(firstMonth), DataFormat
                        .getLastDateOfMonth(lastMonth)));
        result.append(createSqlToVerifyBudgetStatus(qInfo));
        result.append("and Budget_Plan.BudgetFlag=1 \n");//追加预算
        result.append("and Budget_Plan.id=Budget_PlanDetail.planId \n");
        result
                .append("and Budget_PlanDetail.itemID=Budget_ItemDetail.itemId \n");
        result.append("and Budget_ItemDetail.clientId=client.id \n");
        result.append("and Budget_Plan.budgetSystemID=Budget_System.id \n");
        result.append("group by client.id) \n");
        return result.toString();
    }

    private String createSqlForFetchFundAmountColumn(Timestamp firstMonth,
            Timestamp lastMonth, QueryFixedDepositInfo qInfo,
            String accountTypeIds) {
        StringBuffer result = new StringBuffer();
        result.append("(select client.id as clientId, \n");
        result
                .append("sum(nvl(sett_TransAccountDetail.mAmount,0)) as amount \n");
        result.append("from sett_TransAccountDetail, \n");
        result.append("client,sett_Account \n");
        result.append("where sett_Account.nAccountTypeId in (" + accountTypeIds
                + ") \n");
        result.append(createSqlToVerifyDate(
                "sett_TransAccountDetail.dtExecute", DataFormat
                        .getFirstDateOfMonth(firstMonth), DataFormat
                        .getLastDateOfMonth(lastMonth)));
        result.append(createSqlToVerifyTransActionAccountStatus(qInfo));
        result.append("and sett_Account.nClientId=Client.id \n");
        result
                .append("and sett_TransAccountDetail.nTransAccountId=sett_Account.id \n");
        result.append("and sett_TransAccountDetail.nTransDirection=1 \n");
        result
                .append("and sett_TransAccountDetail.nTransActionTypeId in (2,6) \n");
        result.append("group by client.id) \n");
        return result.toString();
    }

    private String createSqlForFetchFundTimesColumn(Timestamp firstMonth,
            Timestamp lastMonth, QueryFixedDepositInfo qInfo,
            String accountTypeIds) {
        StringBuffer result = new StringBuffer();
        result.append("(select client.id as clientId, \n");
        result.append("count(sett_TransAccountDetail.id) as amount \n");
        result.append("from sett_TransAccountDetail, \n");
        result.append("client,sett_Account \n");
        result.append("where sett_Account.nAccountTypeId in (" + accountTypeIds
                + ") \n");
        result.append(createSqlToVerifyDate(
                "sett_TransAccountDetail.dtExecute", DataFormat
                        .getFirstDateOfMonth(firstMonth), DataFormat
                        .getLastDateOfMonth(lastMonth)));
        result.append(createSqlToVerifyTransActionAccountStatus(qInfo));
        result.append("and sett_Account.nClientId=Client.id \n");
        result
                .append("and sett_TransAccountDetail.nTransAccountId=sett_Account.id \n");
        result.append("and sett_TransAccountDetail.nTransDirection=1 \n");
        result
                .append("and sett_TransAccountDetail.nTransActionTypeId in (2,6) \n");
        result.append("group by client.id) \n");
        return result.toString();
    }

    private String createSqlForBankDepositAvgColumn(Timestamp firstMonth,
            Timestamp lastMonth, QueryFixedDepositInfo qInfo) {
        StringBuffer result = new StringBuffer();
        result.append("(select client.id as clientId,\n");
        result.append("sum(nvl(sett_BalanceOfBankAccount.mBalance,0))/"
                + CommonBiz.numberOfMonthes(firstMonth, lastMonth)
                + " as amount \n");
        result
                .append("from sett_BalanceOfBankAccount,SETT_BANKACCOUNTOFFILIALE,\n");
        result.append("client \n");
        result.append("where 1=1 \n");
        result.append(createSqlToVerifyDate(
                "sett_BalanceOfBankAccount.dtCurrent", firstMonth, lastMonth));
        result
                .append("and SETT_BANKACCOUNTOFFILIALE.sBankAccountNo=sett_BalanceOfBankAccount.sBankAccountNo \n");
        result.append("and SETT_BANKACCOUNTOFFILIALE.nClientId=client.id \n");
        result.append(createSqlToVerifyBankAccountOffiliale(qInfo));
        result.append("group by client.id) \n");
        return result.toString();
    }

    public ReportResultInfo[] queryClientCurrentBudgetDetail(
            Timestamp firstMonth, Timestamp lastMonth,
            QueryFixedDepositInfo qInfo) throws Exception {
        StringBuffer strSql = new StringBuffer();
        strSql.append("select aa.clientName as StringColumn1 \n");
        for (int i = 1; i <= 9; i++) {
            strSql.append(",bb" + i + ".amount as DoubleColumn" + i + " \n");
        }
        strSql.append("from \n");
        strSql.append(createSqlForClientTableColumn());
        strSql.append("aa, \n");
        strSql.append(createSqlForCommonBudgetColumn(firstMonth, lastMonth,
                qInfo, "1"));//活期
        strSql.append("bb1, \n");
        strSql.append(createSqlForCommonBudgetColumn(firstMonth, lastMonth,
                qInfo, "2"));//基建
        strSql.append("bb2, \n");
        strSql.append(createSqlForAddBudgetColumn(firstMonth, lastMonth, qInfo,
                "1"));
        strSql.append("bb3, \n");
        strSql.append(createSqlForAddBudgetColumn(firstMonth, lastMonth, qInfo,
                "2"));
        strSql.append("bb4, \n");
        strSql.append(createSqlForFetchFundAmountColumn(firstMonth, lastMonth,
                qInfo, "1"));
        strSql.append("bb5, \n");
        strSql.append(createSqlForFetchFundTimesColumn(firstMonth, lastMonth,
                qInfo, "1"));
        strSql.append("bb6, \n");
        strSql.append(createSqlForFetchFundAmountColumn(firstMonth, lastMonth,
                qInfo, "2"));
        strSql.append("bb7, \n");
        strSql.append(createSqlForFetchFundTimesColumn(firstMonth, lastMonth,
                qInfo, "2"));
        strSql.append("bb8, \n");
        strSql.append(createSqlForBankDepositAvgColumn(firstMonth, lastMonth,
                qInfo));
        strSql.append("bb9 \n");
        strSql.append("where 1=1 \n");
        for (int i = 1; i <= 9; i++) {
            strSql.append("and aa.clientId=bb" + i + ".clientId(+) \n");
        }
        strSql.append("order by aa.clientId \n");
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
        Report6Dao dao = new Report6Dao();
        QueryFixedDepositInfo qInfo = new QueryFixedDepositInfo();
        qInfo.setOfficeID(1);
        qInfo.setCurrencyID(1);
        Timestamp firstMonth = DataFormat.getDateTime("2005-07-15");
        Timestamp lastMonth = DataFormat.getDateTime("2005-10-15");
        try {
            dao.queryClientCurrentBudgetDetail(firstMonth, lastMonth, qInfo);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}