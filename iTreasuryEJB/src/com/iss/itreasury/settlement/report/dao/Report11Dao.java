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
import com.iss.itreasury.settlement.report.bizlogic.CommonBiz;
import com.iss.itreasury.settlement.report.dataentity.ReportResultInfo;
import com.iss.system.dao.*;
import com.iss.itreasury.util.DataFormat;

/**
 * @author yinghu
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */ 
public class Report11Dao extends BaseQueryObject {

    private String createSqlForClientSegment() {
        StringBuffer result = new StringBuffer();
        result.append("(select sName,id from client) \n");
        return result.toString();
    }

    /**
     * 生成sett_account和sett_DailyAccountBalance校验的sql
     * 
     * @param qInfo
     * @return
     */
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

    /**
     * 生成sett_account和sett_subaccount校验的sql
     * 
     * @param qInfo
     * @return
     */
    private String createSqlToVerifyAccountSubAccount(
            QueryFixedDepositInfo qInfo) {
        StringBuffer result = new StringBuffer();
        result.append("and sett_Account.nofficeid=" + qInfo.getOfficeID()
                + " \n");
        result.append("and sett_Account.nCurrencyID=" + qInfo.getCurrencyID()
                + " \n");
        result.append("and sett_SubAccount.nStatusID <> 0 \n");
        result.append("and sett_SubAccount.nStatusID <> 2 \n");
        return result.toString();
    }

    private String createSqlForNortifyDepositSegment(Timestamp date,
            QueryFixedDepositInfo qInfo) throws Exception {
        StringBuffer result = new StringBuffer();
        if (CommonBiz.isTodayOrAfterToday(date, qInfo)) {
            result
                    .append("(select client.id as id,sum(nvl(sett_SubAccount.mBalance,0))/10000 as mBalance \n");
            result.append("from sett_account,client,sett_subaccount \n");
            result
                    .append("where sett_subaccount.nAccountId=sett_account.id \n");
            result.append("and sett_account.nAccountTypeId in ("
                    + getNotifyAccountType(qInfo.getCurrencyID(),qInfo.getOfficeID()) + ")  \n");
            createSqlToVerifyAccountSubAccount(qInfo);
            result.append("and sett_account.nClientId=client.id \n");
            result.append("group by client.id) \n");

        } else {
            result
                    .append("(select client.id as id,sum(nvl(sett_DailyAccountBalance.mBalance,0))/10000 as mBalance \n");
            result.append("from sett_account,client,sett_subaccount, \n");
            result.append("sett_DailyAccountBalance \n");
            result
                    .append("where sett_subaccount.nAccountId=sett_account.id \n");
            result
                    .append("and sett_DailyAccountBalance.NSUBACCOUNTID=sett_SubAccount.Id \n");
            result.append("and sett_account.nAccountTypeId in ("
                    + getNotifyAccountType(qInfo.getCurrencyID(),qInfo.getOfficeID()) + ")  \n");
            result
                    .append("and to_char(sett_DailyAccountBalance.dtDate,'yyyy-mm-dd')='"
                            + DataFormat.getDateString(date) + "' \n");
            createSqlToVerifyAccountDailyAccountBalance(qInfo);
            result.append("and sett_account.nClientId=client.id \n");
            result.append("group by client.id) \n");
        }
        return result.toString();
    }

    private String createSqlForFixedDepositSegment(Timestamp date, int day,
            QueryFixedDepositInfo qInfo) throws Exception {
        StringBuffer result = new StringBuffer();
        if (CommonBiz.isTodayOrAfterToday(DataFormat.getNextDate(date, day),
                qInfo)) {
            result
                    .append("(select client.id as id,sum(nvl(sett_SubAccount.mBalance,0))/10000 as mBalance \n");
            result.append("from sett_account,client,sett_subaccount \n");
            result
                    .append("where to_char(sett_subaccount.af_dtEnd,'yyyy-mm-dd')='"
                            + DataFormat.getDateString(DataFormat.getNextDate(
                                    date, day)) + "' \n");
            result.append("and sett_subaccount.nAccountId=sett_account.id \n");
            result.append("and sett_account.nAccountTypeId in ("
                    + getFixAccountType(qInfo.getCurrencyID(),qInfo.getOfficeID()) + ")  \n");
            result.append(createSqlToVerifyAccountSubAccount(qInfo));
            result.append("and sett_account.nClientId=client.id \n");
            result.append("group by client.id) \n");
        } else {
            result
                    .append("(select client.id as id,sum(nvl(sett_DailyAccountBalance.mBalance,0))/10000 as mBalance \n");
            result.append("from sett_account,client,sett_subaccount, \n");
            result.append("sett_DailyAccountBalance \n");
            result
                    .append("where to_char(sett_subaccount.af_dtEnd,'yyyy-mm-dd')='"
                            + DataFormat.getDateString(DataFormat.getNextDate(
                                    date, day)) + "' \n");
            result
                    .append("and to_char(sett_DailyAccountBalance.dtDate,'yyyy-mm-dd')='"
                            + DataFormat.getDateString(date) + "' \n");
            result.append("and sett_subaccount.nAccountId=sett_account.id \n");
            result
                    .append("and sett_DailyAccountBalance.NSUBACCOUNTID=sett_SubAccount.id \n");
            result.append("and sett_account.nAccountTypeId in ("
                    + getFixAccountType(qInfo.getCurrencyID(),qInfo.getOfficeID()) + ")  \n");
            result.append(createSqlToVerifyAccountDailyAccountBalance(qInfo));
            result.append("and sett_account.nClientId=client.id \n");
            result.append("group by client.id) \n");
        }
        return result.toString();
    }

    public ReportResultInfo[] queryNextMonthFetchFundToPayDetail(
            Timestamp date, QueryFixedDepositInfo qInfo) throws Exception {
        StringBuffer strSQL = new StringBuffer();
        strSQL.append("select aa.sName as StringColumn1 \n");
        strSQL.append(",bb.mBalance as DoubleColumn1 \n");
        for (int i = 1; i <= 30; i++) {
            strSQL.append(",cc" + Integer.toString(i) + ".mBalance \n");
            strSQL.append("as DoubleColumn" + Integer.toString(i + 1) + " \n");
        }
        strSQL.append("from ");
        strSQL.append(createSqlForClientSegment());
        strSQL.append("aa \n");
        strSQL.append(",");
        strSQL.append(createSqlForNortifyDepositSegment(date, qInfo));
        strSQL.append("bb \n");
        for (int i = 1; i <= 30; i++) {
            strSQL.append(",");
            strSQL.append(createSqlForFixedDepositSegment(date, i - 1, qInfo));
            strSQL.append("cc" + Integer.toString(i) + " \n");
        }
        strSQL.append("where aa.id=bb.id(+) \n");
        for (int i = 1; i <= 30; i++) {
            strSQL.append("and aa.id=cc" + Integer.toString(i) + ".id(+) \n");
        }
        strSQL.append("order by aa.id \n");
        System.out.println(strSQL.toString());
        ReportResultInfo[] result = null;
        try {
            initDAO();
            prepareStatement(strSQL.toString());
            executeQuery();
            result = (ReportResultInfo[]) SqlUtil
                    .parseDataEntityBeans(transRS, "",
                            "com.iss.itreasury.settlement.report.dataentity.ReportResultInfo");
        } catch (Exception e) {
            throw e;
        } finally {
            this.finalizeDAO();
        }
        return result;
    }

    public static void main(String[] args) {
        Report11Dao dao = new Report11Dao();
        QueryFixedDepositInfo qInfo = new QueryFixedDepositInfo();
        qInfo.setOfficeID(1);
        qInfo.setCurrencyID(1);
        Timestamp ts = DataFormat.getDateTime("2005-10-11");
        try {
            dao.queryNextMonthFetchFundToPayDetail(ts, qInfo);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}