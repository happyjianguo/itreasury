/*
 * Created on 2005-10-17
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.settlement.report.dao;

/**
 * @author yinghu
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */

import java.sql.Timestamp;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.settlement.query.paraminfo.QueryFixedDepositInfo;
import com.iss.itreasury.settlement.query.queryobj.BaseQueryObject;
import com.iss.itreasury.settlement.report.dataentity.ReportResultInfo;
import com.iss.system.dao.*;

public class Report9Dao extends BaseQueryObject {

    private String createSql(Timestamp date, QueryFixedDepositInfo qInfo) {
        StringBuffer result = new StringBuffer();
        result.append("select client.sname as StringColumn1, \n");//单位名称
        result.append("subAcct.dtOpen as TsColumn1,  \n");//存入日期
        result.append("dailyAcct.mBalance/10000 as DoubleColumn1, \n");//金额
        result.append("nvl(subAcct.AF_mRate,0) as DoubleColumn2  \n");//利率
        // from
        result
                .append("from sett_account acct, sett_subAccount subAcct, client, \n");
        result.append("SETT_DAILYACCOUNTBALANCE dailyAcct \n");
        // where
        result
                .append("where subAcct.nAccountID=acct.id and acct.nclientid=client.id  \n");
        result.append("and acct.nAccountTypeID in ("
                + getNotifyAccountType(qInfo.getCurrencyID(),qInfo.getOfficeID()) + ")  \n");
        result.append("and to_char(dailyAcct.dtDate,'yyyy-mm-dd')='"
                + DataFormat.formatDate(date) + "' \n");
        result.append("and acct.nofficeid=" + qInfo.getOfficeID() + " \n");
        result.append("and acct.nCurrencyID=" + qInfo.getCurrencyID() + " \n");
        result.append("and dailyAcct.mBalance<>0 \n");
        result.append("and dailyAcct.NSUBACCOUNTID=subAcct.Id \n");
        result.append("order by client.id \n");
        return result.toString();
    }

    public ReportResultInfo[] queryNotifyDepositBusinessDetail(Timestamp date,
            QueryFixedDepositInfo qInfo) throws Exception {
        StringBuffer strSql = new StringBuffer();
        strSql.append(createSql(date,qInfo));
        System.out.println(strSql.toString());
        ReportResultInfo[] result = null;
        try {
            this.initDAO();
            this.prepareStatement(strSql.toString());
            this.executeQuery();
            result = (ReportResultInfo[]) SqlUtil
                    .parseDataEntityBeans(this.transRS, "",
                            "com.iss.itreasury.settlement.report.dataentity.ReportResultInfo");
        } catch (Exception e) {
            throw e;
        } finally {
            this.finalizeDAO();
        }
        return result;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        Report9Dao dao = new Report9Dao();
        QueryFixedDepositInfo qInfo = new QueryFixedDepositInfo();
        qInfo.setOfficeID(1);
        qInfo.setCurrencyID(1);
        Timestamp ts = DataFormat.getDateTime("2005-10-11");
        try {
            dao.queryNotifyDepositBusinessDetail(ts, qInfo);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}