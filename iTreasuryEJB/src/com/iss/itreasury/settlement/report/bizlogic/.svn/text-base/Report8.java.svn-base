package com.iss.itreasury.settlement.report.bizlogic;

import java.sql.Timestamp;
import com.iss.itreasury.settlement.query.paraminfo.QueryFixedDepositInfo;
import com.iss.itreasury.settlement.query.queryobj.BaseQueryObject;
import com.iss.itreasury.settlement.report.dao.Report8Dao;
import com.iss.itreasury.settlement.report.dataentity.ReportResultInfo;

public class Report8 extends BaseQueryObject {

    public ReportResultInfo[] queryFixedDepositBusinessDetail(Timestamp date,
            QueryFixedDepositInfo qInfo) throws Exception {
        return (new Report8Dao()).queryFixedDepositBusinessDetail(date, qInfo);
    }
    
    public double calcGather(ReportResultInfo[] infos) throws Exception{
        return CommonBiz.calcTotal(infos,"DoubleColumn5");
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

}