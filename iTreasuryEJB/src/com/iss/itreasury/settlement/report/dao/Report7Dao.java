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

public class Report7Dao extends BaseQueryObject {

    private String createSqlForClientTableColumn() {
        StringBuffer result = new StringBuffer();
        result.append("(select client.sName as clientName, \n");
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

    /**
     * 生成一段日期内部活期交易数量的列
     * 
     * @param firstDay
     * @param lastDay
     * @param qInfo
     * @return
     */
    private String createSqlForNumberOfTransactionColumn(Timestamp firstDay,
            Timestamp lastDay, QueryFixedDepositInfo qInfo) {
        StringBuffer result = new StringBuffer();
        result.append("(select client.id as clientId, \n");
        result.append("count(sett_TransAccountDetail.id) as counts \n");
        result.append("from sett_TransAccountDetail, \n");
        result.append("client,sett_Account \n");
        result.append("where sett_Account.nAccountTypeId in ("
                + getCurrentAccountType(qInfo.getCurrencyID(),qInfo.getOfficeID()) + ") \n");
        result.append(createSqlToVerifyDate(
                "sett_TransAccountDetail.dtInterestStart", firstDay, lastDay));
        result.append(createSqlToVerifyTransActionAccountStatus(qInfo));
        result.append("and sett_Account.nClientId=Client.id \n");
        result
                .append("and sett_TransAccountDetail.nTransAccountId=sett_Account.id \n");
        result.append("group by client.id) \n");
        return result.toString();
    }

    /**
     * 生成财务公司活期存款(内部存款）-金额-客户列
     * 
     * @param firstDay
     * @param lastDay
     * @param qInfo
     * @param accountTypeIds
     * @return
     */
    private String createSqlForAccountClientColumn(Timestamp date,
            QueryFixedDepositInfo qInfo) {
        StringBuffer result = new StringBuffer();
        result.append("(select client.id as clientId,\n");
        result
                .append("sum(nvl(sett_DailyAccountBalance.mBalance,0))/10000 as balance \n");
        result.append("from sett_DailyAccountBalance,sett_Account,\n");
        result.append("client \n");
        result
                .append("where sett_DailyAccountBalance.nAccountId=sett_Account.id \n");
        result.append("and sett_Account.nClientId=client.Id \n");
        result.append("and sett_Account.nAccountTypeId in ("
                + getCurrentAccountType(qInfo.getCurrencyID(),qInfo.getOfficeID()) + ") \n");
        result.append(createSqlToVerifyAccountDailyAccountBalanceStatus(qInfo));
        result.append(createSqlToVerifyDate("sett_DailyAccountBalance.dtDate",
                date, date));
        result.append("group by client.id) \n");
        return result.toString();
    }

    /**
     * 生成造成余额增加的交易统计列
     * 
     * @param firstDay
     * @param lastDay
     * @param qInfo
     * @param transActionTypeIds
     * @return
     */
    private String createSqlForPeriodIncreaseColumn(Timestamp firstDay,
            Timestamp lastDay, QueryFixedDepositInfo qInfo, boolean isInnerTrans) {
        StringBuffer result = new StringBuffer();
        result.append("(select client.id as clientId, \n");
        result.append("count(sett_TransAccountDetail.id) as counts,\n");
        result
                .append("sum(nvl(sett_TransAccountDetail.mAmount,0))/10000 as amount \n");
        result.append("from sett_TransAccountDetail, \n");
        result.append("client,sett_Account \n");
        result.append("where sett_Account.nAccountTypeId in ("
                + getCurrentAccountType(qInfo.getCurrencyID(),qInfo.getOfficeID()) + ") \n");
        result.append(createSqlToVerifyDate(
                "sett_TransAccountDetail.dtInterestStart", firstDay, lastDay));
        result.append(createSqlToVerifyTransActionAccountStatus(qInfo));
        result.append("and sett_Account.nClientId=Client.id \n");
        result
                .append("and sett_TransAccountDetail.nTransAccountId=sett_Account.id \n");
        result.append("and sett_TransAccountDetail.nTransDirection=2 \n");
        if (isInnerTrans) {
            result
                    .append("and sett_TransAccountDetail.nTransActionTypeId in (6) \n");
        } else {
            result
                    .append("and sett_TransAccountDetail.nTransActionTypeId not in (6) \n");
        }
        result.append("group by client.id) \n");
        return result.toString();
    }

    /**
     * 生成造成余额减少的交易统计列
     * 
     * @param firstDay
     * @param lastDay
     * @param qInfo
     * @param transActionTypeIds
     * @return
     */
    private String createSqlForPeriodDecreaseColumn(Timestamp firstDay,
            Timestamp lastDay, QueryFixedDepositInfo qInfo, boolean isInnerTrans) {
        StringBuffer result = new StringBuffer();
        result.append("(select client.id as clientId, \n");
        result.append("count(sett_TransAccountDetail.id) as counts,\n");
        result
                .append("sum(nvl(sett_TransAccountDetail.mAmount,0))/10000 as amount \n");
        result.append("from sett_TransAccountDetail, \n");
        result.append("client,sett_Account \n");
        result.append("where sett_Account.nAccountTypeId in ("
                + getCurrentAccountType(qInfo.getCurrencyID(),qInfo.getOfficeID()) + ") \n");
        result.append(createSqlToVerifyDate(
                "sett_TransAccountDetail.dtInterestStart", firstDay, lastDay));
        result.append(createSqlToVerifyTransActionAccountStatus(qInfo));
        result.append("and sett_Account.nClientId=Client.id \n");
        result
                .append("and sett_TransAccountDetail.nTransAccountId=sett_Account.id \n");
        result.append("and sett_TransAccountDetail.nTransDirection=1 \n");
        if (isInnerTrans) {
            result
                    .append("and sett_TransAccountDetail.nTransActionTypeId in (6) \n");
        } else {
            result
                    .append("and sett_TransAccountDetail.nTransActionTypeId not in (6) \n");
        }
        result.append("group by client.id) \n");
        return result.toString();
    }

    public ReportResultInfo[] queryPlatformFundFluxtDetail(Timestamp firstDay,
            Timestamp lastDay, QueryFixedDepositInfo qInfo) throws Exception {
        StringBuffer strSql = new StringBuffer();
        strSql.append("select aa.clientName as StringColumn1 \n");
        strSql.append(",bb1.counts as DoubleColumn1 \n");
        strSql.append(",bb2.balance as DoubleColumn2 \n");
        for (int i = 1; i <= 8; i++) {
            strSql.append(",cc" + i + ".counts as DoubleColumn" + (2 * i + 1)
                    + " \n");
            strSql.append(",cc" + i + ".amount as DoubleColumn" + (2 * i + 2)
                    + " \n");
        }
        strSql.append("from \n");
        strSql.append(createSqlForClientTableColumn());
        strSql.append("aa, \n");
        strSql.append(createSqlForNumberOfTransactionColumn(DataFormat
                .getDateTime("1980-01-01"), DataFormat
                .getPreviousDate(firstDay), qInfo));
        strSql.append("bb1, \n");
        strSql.append(createSqlForAccountClientColumn(DataFormat
                .getPreviousDate(firstDay), qInfo));//期初
        strSql.append("bb2, \n");
        strSql.append(createSqlForPeriodIncreaseColumn(firstDay, lastDay,
                qInfo, true));//本期增加-本期-内转
        strSql.append("cc1, \n");
        strSql.append(createSqlForPeriodIncreaseColumn(firstDay, lastDay,
                qInfo, false));//本期增加-本期-转入
        strSql.append("cc2, \n");
        strSql.append(createSqlForPeriodIncreaseColumn(DataFormat
                .getDateTime("1980-01-01"), lastDay, qInfo, true));//本期增加-累计-内转
        strSql.append("cc3, \n");
        strSql.append(createSqlForPeriodIncreaseColumn(DataFormat
                .getDateTime("1980-01-01"), lastDay, qInfo, false));//本期增加-累计-转入
        strSql.append("cc4, \n");
        strSql.append(createSqlForPeriodDecreaseColumn(firstDay, lastDay,
                qInfo, true));//本期减少-本期-内转
        strSql.append("cc5, \n");
        strSql.append(createSqlForPeriodDecreaseColumn(firstDay, lastDay,
                qInfo, false));//本期减少-本期-转入
        strSql.append("cc6, \n");
        strSql.append(createSqlForPeriodDecreaseColumn(DataFormat
                .getDateTime("1980-01-01"), lastDay, qInfo, true));//本期减少-累计-内转
        strSql.append("cc7, \n");
        strSql.append(createSqlForPeriodDecreaseColumn(DataFormat
                .getDateTime("1980-01-01"), lastDay, qInfo, false));//本期减少-累计-转入
        strSql.append("cc8 \n");
        strSql.append("where 1=1 \n");
        strSql.append("and aa.clientId=bb1.clientId(+) \n");
        strSql.append("and aa.clientId=bb2.clientId(+) \n");
        for (int i = 1; i <= 8; i++) {
            strSql.append("and aa.clientId=cc" + i + ".clientId(+) \n");
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
        Report7Dao dao = new Report7Dao();
        QueryFixedDepositInfo qInfo = new QueryFixedDepositInfo();
        qInfo.setOfficeID(1);
        qInfo.setCurrencyID(1);
        Timestamp firstDay = DataFormat.getDateTime("2005-09-01");
        Timestamp lastDay = DataFormat.getDateTime("2005-09-30");
        try {
            dao.queryPlatformFundFluxtDetail(firstDay, lastDay, qInfo);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}