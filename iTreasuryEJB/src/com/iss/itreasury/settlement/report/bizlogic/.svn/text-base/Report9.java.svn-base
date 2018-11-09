package com.iss.itreasury.settlement.report.bizlogic;

import java.sql.Timestamp;
import com.iss.itreasury.settlement.query.paraminfo.QueryFixedDepositInfo;
import com.iss.itreasury.settlement.query.queryobj.BaseQueryObject;
import com.iss.itreasury.settlement.report.dao.Report9Dao;
import com.iss.itreasury.settlement.report.dataentity.ReportResultInfo;

public class Report9 extends BaseQueryObject {

    public ReportResultInfo[] queryNotifyDepositBusinessDetail(Timestamp date,
            QueryFixedDepositInfo qInfo) throws Exception {
        return (new Report9Dao()).queryNotifyDepositBusinessDetail(date, qInfo);
    }
    
    public double calcGather(ReportResultInfo[] infos) throws Exception{
        return CommonBiz.calcTotal(infos,"DoubleColumn1");
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

}
