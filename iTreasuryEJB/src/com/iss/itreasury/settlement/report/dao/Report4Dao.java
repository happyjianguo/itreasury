/*
 * Created on 2005-9-28
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.settlement.report.dao;

import java.sql.Timestamp;
import com.iss.itreasury.settlement.query.paraminfo.QueryFixedDepositInfo;
import com.iss.itreasury.settlement.query.queryobj.BaseQueryObject;
import com.iss.itreasury.settlement.report.dataentity.ReportResultInfo;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Log4j;
import com.iss.system.dao.*;

/**
 * @author yinghu
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class Report4Dao extends BaseQueryObject {

    private Log4j logger = new Log4j(Constant.ModuleType.SETTLEMENT, this);

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

    private static String createSqlToVerifyBankAccountOffilialeStatus(
            QueryFixedDepositInfo qInfo) {
        StringBuffer result = new StringBuffer();
        result.append("and SETT_BANKACCOUNTOFFILIALE.nCurrencyId="
                + qInfo.getCurrencyID() + " \n");
        result.append("and SETT_BANKACCOUNTOFFILIALE.nOfficeId="
                + qInfo.getOfficeID() + " \n");
        return result.toString();
    }

    private static String createSqlToVerifyAccountDailyAccountBalanceStatus(
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

    /**
     * 生成当地银行存款-客户列
     * 
     * @param date
     * @param qInfo
     * @param bankAccountCodes
     * @return
     */
    private String createSqlForAvgBranchClientColumn(Timestamp firstDay,
            Timestamp lastDay, QueryFixedDepositInfo qInfo, String bankTypes) {
        StringBuffer result = new StringBuffer();
        result.append("(select client.id as clientId,\n");
        result
                .append("sum(nvl(sett_BalanceOfBankAccount.mBalance,0))/(select to_date('"
                        + DataFormat.formatDate(lastDay)
                        + "','yyyy-mm-dd')-to_date('"
                        + DataFormat.formatDate(firstDay)
                        + "','yyyy-mm-dd')+1 from dual) as balance \n");
        result
                .append("from sett_BalanceOfBankAccount,SETT_BANKACCOUNTOFFILIALE,\n");
        result.append("client \n");
        result
                .append("where SETT_BANKACCOUNTOFFILIALE.sBankAccountNo=sett_BalanceOfBankAccount.sBankAccountNo \n");
        result.append("and SETT_BANKACCOUNTOFFILIALE.nClientId=client.id \n");
        if (bankTypes.equals("")) {
        }//如果是""就是所有银行
        else {
            result.append("and SETT_BANKACCOUNTOFFILIALE.nBnkType in ("
                    + bankTypes + ") \n");
        }
        result.append(createSqlToVerifyDate(
                "sett_BalanceOfBankAccount.dtCurrent", firstDay, lastDay));
        result.append(createSqlToVerifyBankAccountOffilialeStatus(qInfo));
        result.append("group by client.id) \n");
        return result.toString();
    }

    /**
     * 生成财务公司存款（内部存款）-客户列
     * 
     * @param firstDay
     * @param lastDay
     * @param qInfo
     * @param accountTypeIds
     * @return
     */
    private String createSqlForAvgAccountBalanceClientColumn(Timestamp firstDay,
            Timestamp lastDay, QueryFixedDepositInfo qInfo,
            String accountTypeIds) {
        StringBuffer result = new StringBuffer();

        result.append("(select client.id as clientId,\n");
        result
                .append("sum(nvl(sett_DailyAccountBalance.mBalance,0))/(select to_date('"
                        + DataFormat.formatDate(lastDay)
                        + "','yyyy-mm-dd')-to_date('"
                        + DataFormat.formatDate(firstDay)
                        + "','yyyy-mm-dd')+1 from dual) as balance \n");
        result.append("from sett_DailyAccountBalance,sett_Account,\n");
        result.append("client \n");
        result
                .append("where sett_DailyAccountBalance.nAccountId=sett_Account.id \n");
        result.append("and sett_Account.nClientId=client.Id \n");
        if (accountTypeIds.equals("")) {
        }//如果是""就是求所有账户类型的和
        else {
            result.append("and sett_Account.nAccountTypeId in ("
                    + accountTypeIds + ") \n");
        }
        result.append(createSqlToVerifyAccountDailyAccountBalanceStatus(qInfo));
        result.append(createSqlToVerifyDate("sett_DailyAccountBalance.dtDate",
                firstDay, lastDay));
        result.append("group by client.id) \n");
        return result.toString();
    }

    /**
     * 查询客户余额表结构
     * 
     * @param firstDay
     * @param lastDay
     * @param qInfo
     * @return
     * @throws Exception
     */
    public ReportResultInfo[] queryClientDepositStructure(Timestamp firstDay,
            Timestamp lastDay, QueryFixedDepositInfo qInfo) throws Exception {
        StringBuffer sqlStr = new StringBuffer();
        sqlStr.append("select aa.clientName as StringColumn1\n");//StringColumn1--客户名称
        sqlStr.append(",aa.clientTypeId as LongColumn1\n");//LongColumn1--客户类型id
        for (int i = 1; i <= 12; i++) {
            sqlStr.append(",bb" + i + ".balance as DoubleColumn" + i + " \n");//StringColumni--第i种金额
        }
        sqlStr.append("from \n");
        sqlStr.append(createSqlForClientTableColumn());
        sqlStr.append("aa, \n");
        StringBuffer accountTypeIds = new StringBuffer();
        accountTypeIds.append("1, \n");
        accountTypeIds.append("7, \n");
        accountTypeIds.append("8 \n");
        sqlStr.append(createSqlForAvgAccountBalanceClientColumn(firstDay,
                lastDay, qInfo, accountTypeIds.toString()));//1,7,8--活期
        sqlStr.append("bb1, \n");
        accountTypeIds.setLength(0);
        accountTypeIds.append("5 \n");
        sqlStr.append(createSqlForAvgAccountBalanceClientColumn(firstDay,
                lastDay, qInfo, accountTypeIds.toString()));//5--定期存款
        sqlStr.append("bb2, \n");
        accountTypeIds.setLength(0);
        accountTypeIds.append("6 \n");
        sqlStr.append(createSqlForAvgAccountBalanceClientColumn(firstDay,
                lastDay, qInfo, accountTypeIds.toString()));//6--通知存款
        sqlStr.append("bb3, \n");
        accountTypeIds.setLength(0);
        accountTypeIds.append("2 \n");
        sqlStr.append(createSqlForAvgAccountBalanceClientColumn(firstDay,
                lastDay, qInfo, accountTypeIds.toString()));//2--基建活期账户
        sqlStr.append("bb4, \n");
        accountTypeIds.setLength(0);
        accountTypeIds.append("11 \n");
        sqlStr.append(createSqlForAvgAccountBalanceClientColumn(firstDay,
                lastDay, qInfo, accountTypeIds.toString()));//11--专项存款账户
        sqlStr.append("bb5, \n");
        accountTypeIds.setLength(0);
        accountTypeIds.append("3 \n");
        sqlStr.append(createSqlForAvgAccountBalanceClientColumn(firstDay,
                lastDay, qInfo, accountTypeIds.toString()));//3--封闭活期账户
        sqlStr.append("bb6, \n");
        StringBuffer bankTypes = new StringBuffer();
        bankTypes.append("2 \n");
        sqlStr.append(createSqlForAvgBranchClientColumn(firstDay, lastDay,
                qInfo, bankTypes.toString()));//工行
        sqlStr.append("bb7, \n");
        bankTypes.setLength(0);
        bankTypes.append("3 \n");
        sqlStr.append(createSqlForAvgBranchClientColumn(firstDay, lastDay,
                qInfo, bankTypes.toString()));//建行
        sqlStr.append("bb8, \n");
        bankTypes.setLength(0);
        bankTypes.append("10 \n");
        sqlStr.append(createSqlForAvgBranchClientColumn(firstDay, lastDay,
                qInfo, bankTypes.toString()));//光大
        sqlStr.append("bb9, \n");
        bankTypes.setLength(0);
        bankTypes.append("5 \n");
        sqlStr.append(createSqlForAvgBranchClientColumn(firstDay, lastDay,
                qInfo, bankTypes.toString()));//民生
        sqlStr.append("bb10, \n");
        bankTypes.setLength(0);
        bankTypes.append("-1 \n");
        sqlStr.append(createSqlForAvgBranchClientColumn(firstDay, lastDay,
                qInfo, bankTypes.toString()));//它行
        sqlStr.append("bb11, \n");
        accountTypeIds.setLength(0);
        accountTypeIds.append("7, \n");
        accountTypeIds.append("8 \n");
        sqlStr.append(createSqlForAvgAccountBalanceClientColumn(firstDay,
                lastDay, qInfo, accountTypeIds.toString()));//7,8--工行二级户
        sqlStr.append("bb12 \n");
        sqlStr.append("where 1=1 \n");
        for (int i = 1; i <= 12; i++) {
            sqlStr.append("and aa.clientId=bb" + i + ".clientId(+) \n");
        }
        sqlStr.append("\n order by aa.clientTypeId,aa.clientId \n");
        System.out.println(sqlStr.toString());
        ReportResultInfo[] results = null;
        try {
            initDAO();
            prepareStatement(sqlStr.toString());
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
        Report4Dao dao = new Report4Dao();
        QueryFixedDepositInfo qInfo = new QueryFixedDepositInfo();
        qInfo.setOfficeID(1);
        qInfo.setCurrencyID(1);
        Timestamp ts1 = DataFormat.getDateTime("2005-09-03");
        Timestamp ts2 = DataFormat.getDateTime("2005-11-03");
        ReportResultInfo[] infos=null;
        try {
            infos=dao.queryClientDepositStructure(ts1, ts2, qInfo);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        for(int i=0;i<infos.length;i++){
            System.out.println(infos[i].getDoubleColumn12());
        }
    }
}