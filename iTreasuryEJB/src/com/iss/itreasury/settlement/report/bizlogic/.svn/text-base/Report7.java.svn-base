/*
 * Created on 2005-10-17
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.settlement.report.bizlogic;

import java.sql.Timestamp;
import java.util.Vector;
import com.iss.itreasury.settlement.query.paraminfo.QueryFixedDepositInfo;
import com.iss.itreasury.settlement.report.dao.Report7Dao;
import com.iss.itreasury.settlement.report.dataentity.ReportResultInfo;

/**
 * @author yinghu
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class Report7 {
    public Vector queryPlatformFundFluxtDetail(Timestamp firstDay,
            Timestamp lastDay, QueryFixedDepositInfo qInfo) throws Exception {
        ReportResultInfo[] infosFrom = (new Report7Dao())
                .queryPlatformFundFluxtDetail(firstDay, lastDay, qInfo);
        Vector result = new Vector();
        for (int i = 0; i < infosFrom.length; i++) {
            result.addElement(transferInfo(infosFrom[i]));
        }
        ReportResultInfo infoAddAll=CommonBiz.calcTotal(infosFrom);
        infoAddAll.setStringColumn1("Лузм");
        result.addElement(infoAddAll);
        return result;
    }

    private ReportResultInfo transferInfo(ReportResultInfo infoFrom) {
        ReportResultInfo result = new ReportResultInfo();
        result.setStringColumn1(infoFrom.getStringColumn1());
        result.setDoubleColumn1(infoFrom.getDoubleColumn1());
        result.setDoubleColumn2(infoFrom.getDoubleColumn2());
        result.setDoubleColumn3(infoFrom.getDoubleColumn3());
        result.setDoubleColumn4(infoFrom.getDoubleColumn4());
        result.setDoubleColumn5(infoFrom.getDoubleColumn5());
        result.setDoubleColumn6(infoFrom.getDoubleColumn6());
        result.setDoubleColumn7(infoFrom.getDoubleColumn7());
        result.setDoubleColumn8(infoFrom.getDoubleColumn8());
        result.setDoubleColumn9(infoFrom.getDoubleColumn9());
        result.setDoubleColumn10(infoFrom.getDoubleColumn10());
        result.setDoubleColumn11(infoFrom.getDoubleColumn11());
        result.setDoubleColumn12(infoFrom.getDoubleColumn12());
        result.setDoubleColumn13(infoFrom.getDoubleColumn13());
        result.setDoubleColumn14(infoFrom.getDoubleColumn14());
        result.setDoubleColumn15(infoFrom.getDoubleColumn15());
        result.setDoubleColumn16(infoFrom.getDoubleColumn16());
        result.setDoubleColumn17(infoFrom.getDoubleColumn17());
        result.setDoubleColumn18(infoFrom.getDoubleColumn18());
        result.setDoubleColumn19(infoFrom.getDoubleColumn1()
                + infoFrom.getDoubleColumn3() + infoFrom.getDoubleColumn5()
                + infoFrom.getDoubleColumn11() + infoFrom.getDoubleColumn13());
        result.setDoubleColumn20(infoFrom.getDoubleColumn2()
                + infoFrom.getDoubleColumn4() + infoFrom.getDoubleColumn6()
                - infoFrom.getDoubleColumn12() - infoFrom.getDoubleColumn14());
        return result;
    }

    public static void main(String[] args) {
    }
}