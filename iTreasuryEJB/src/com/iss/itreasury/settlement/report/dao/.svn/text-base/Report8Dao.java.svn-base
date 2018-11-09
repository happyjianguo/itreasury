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

public class Report8Dao extends BaseQueryObject {

    private String createSqlForQuery(Timestamp date, QueryFixedDepositInfo qInfo) {
        StringBuffer result = new StringBuffer();
        result.append("select client.sname as StringColumn1, \n");//单位名称
        result.append("subAcct.AF_sDepositNo as StringColumn2, \n");//存单号
        result.append("nvl(subAcct.mOpenAmount/10000,0) as DoubleColumn1, \n");//金额
        result.append("subAcct.af_ndepositterm as LongColumn1, \n");//期限
        result.append("subAcct.AF_dtStart as TsColumn1,  \n");//启始日
        result.append("subAcct.AF_dtEnd as TsColumn2,  \n");//到期日
        result.append("nvl(subAcct.AF_mRate,0) as DoubleColumn2,  \n");//利率
        result.append("nvl(dailyAcct.mInterest/10000,0) as DoubleColumn3, \n");//合同利息
        result.append("dailyAcct.NSUBACCOUNTSTATUSID as LongColumn2,  \n");//存款状态
        result
                .append("nvl((subAcct.mOpenAmount-dailyAcct.mBalance)/10000,0) as DoubleColumn4, \n");//已提本金
        result.append("nvl(dailyAcct.mBalance/10000,0) as DoubleColumn5, \n");//本金余额
        result
                .append("nvl(subAcct.AF_mPreDrawInterest/10000,0) as DoubleColumn6 \n");//已记提利息
        // from
        result
                .append("from sett_account acct, sett_subAccount subAcct, client, \n");
        result.append("SETT_DAILYACCOUNTBALANCE dailyAcct \n");
        // where
        result
                .append("where subAcct.nAccountID=acct.id and acct.nclientid=client.id  \n");
        result.append("and dailyAcct.NSUBACCOUNTID=subAcct.Id \n");
        result.append("and to_char(dailyAcct.dtDate,'yyyy-mm-dd')='"
                + DataFormat.formatDate(date) + "' \n");
        result.append("and acct.nAccountTypeID in ("
                + getFixAccountType(qInfo.getCurrencyID(),qInfo.getOfficeID()) + ")  \n");
        result.append("and subAcct.mOpenAmount<>0 \n");
        result.append("and acct.nofficeid=" + qInfo.getOfficeID() + " \n");
        result.append("and acct.nCurrencyID=" + qInfo.getCurrencyID() + " \n");
        result.append("order by client.id \n");
        return result.toString();
    }

    public ReportResultInfo[] queryFixedDepositBusinessDetail(Timestamp date,
            QueryFixedDepositInfo qInfo) throws Exception {
        ReportResultInfo[] result = null;
        try {
            initDAO();
            prepareStatement(createSqlForQuery(date, qInfo));
            executeQuery();
            result = (ReportResultInfo[]) SqlUtil
                    .parseDataEntityBeans(transRS, "",
                            "com.iss.itreasury.settlement.report.dataentity.ReportResultInfo");
        } catch (Exception e) {
            throw e;
        } finally {
            finalizeDAO();
        }
        return result;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        Report8Dao dao = new Report8Dao();
        QueryFixedDepositInfo qInfo = new QueryFixedDepositInfo();
        qInfo.setOfficeID(1);
        qInfo.setCurrencyID(1);
        Timestamp ts = DataFormat.getDateTime("2005-10-15");
        try {
            dao.queryFixedDepositBusinessDetail(ts, qInfo);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}