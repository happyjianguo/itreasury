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
import com.iss.itreasury.settlement.report.dao.Report3Dao;
import com.iss.itreasury.settlement.report.dataentity.ReportResultInfo;
import com.iss.itreasury.util.DataFormat;

/**
 * @author yinghu
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class Report3 {
    public Vector queryBalanceOfGradeTwoAccountOfIndustrialBank(Timestamp date,
            QueryFixedDepositInfo qInfo) throws Exception {
        ReportResultInfo[] infosFrom = (new Report3Dao())
                .queryBalanceOfGradeTwoAccountOfIndustrialBank(date, qInfo);
        Vector result = new Vector();
        for (int i = 0; i < infosFrom.length; i++) {
            if (i < CommonBiz.getDaysOfMonth(date)) {
                ReportResultInfo infoFrom = infosFrom[i];
                ReportResultInfo infoTo = new ReportResultInfo();
                infoTo.setStringColumn1(infoFrom.getStringColumn1());
                infoTo.setDoubleColumn1(addAllGradeTwoAccount(infoFrom));
                infoTo.setDoubleColumn2(infoFrom.getDoubleColumn1());
                infoTo.setDoubleColumn3(infoFrom.getDoubleColumn2());
                infoTo.setDoubleColumn4(infoFrom.getDoubleColumn3());
                infoTo.setDoubleColumn5(infoFrom.getDoubleColumn4());
                infoTo.setDoubleColumn6(addAllIndustrialBank(infoFrom));
                infoTo.setDoubleColumn7(infoFrom.getDoubleColumn5());
                infoTo.setDoubleColumn8(infoFrom.getDoubleColumn6());
                infoTo.setDoubleColumn9(infoFrom.getDoubleColumn7());
                infoTo.setDoubleColumn10(infoFrom.getDoubleColumn8());
                infoTo.setDoubleColumn11(calcTotalPreparePayRatio(infoFrom));
                infoTo.setDoubleColumn12(calcAvgTotalPreparePayRatio(infosFrom,
                        i + 1));
                infoTo.setDoubleColumn13(calcXuanWuPreparePayRatio(infoFrom));
                infoTo.setDoubleColumn14(calcAvgPreparePayRatioOfOneColumn(
                        infosFrom, i + 1, "DoubleColumn5", "DoubleColumn1"));
                infoTo
                        .setDoubleColumn15(calcYongDingluPreparePayRatio(infoFrom));
                infoTo.setDoubleColumn16(calcAvgPreparePayRatioOfOneColumn(
                        infosFrom, i + 1, "DoubleColumn6", "DoubleColumn2"));
                infoTo.setDoubleColumn17(calcZhunYiPreparePayRatio(infoFrom));
                infoTo.setDoubleColumn18(calcAvgPreparePayRatioOfOneColumn(
                        infosFrom, i + 1, "DoubleColumn7", "DoubleColumn3"));
                infoTo.setDoubleColumn19(calcOtherPreparePayRatio(infoFrom));
                infoTo.setDoubleColumn20(calcAvgPreparePayRatioOfOneColumn(
                        infosFrom, i + 1, "DoubleColumn8", "DoubleColumn4"));
                result.addElement(infoTo);
            } else {//月份实际日期外放置空info
                result.addElement(new ReportResultInfo());
            }
        }
        return result;
    }

    private double addAllGradeTwoAccount(ReportResultInfo info) {
        return info.getDoubleColumn1() + info.getDoubleColumn2()
                + info.getDoubleColumn3() + info.getDoubleColumn4();
    }

    private double addAllIndustrialBank(ReportResultInfo info) {
        return info.getDoubleColumn5() + info.getDoubleColumn6()
                + info.getDoubleColumn7() + info.getDoubleColumn8();
    }

    private double calcTotalPreparePayRatio(ReportResultInfo info) {
        if (addAllGradeTwoAccount(info) == 0) {
            return 0;
        }
        return (addAllIndustrialBank(info) / addAllGradeTwoAccount(info)) * 100;
    }

    private double calcXuanWuPreparePayRatio(ReportResultInfo info) {
        if (info.getDoubleColumn1() == 0) {
            return 0;
        }
        return (info.getDoubleColumn5() / info.getDoubleColumn1()) * 100;
    }

    private double calcYongDingluPreparePayRatio(ReportResultInfo info) {
        if (info.getDoubleColumn2() == 0) {
            return 0;
        }
        return (info.getDoubleColumn6() / info.getDoubleColumn2()) * 100;
    }

    private double calcZhunYiPreparePayRatio(ReportResultInfo info) {
        if (info.getDoubleColumn3() == 0) {
            return 0;
        }
        return (info.getDoubleColumn7() / info.getDoubleColumn3()) * 100;
    }

    private double calcOtherPreparePayRatio(ReportResultInfo info) {
        if (info.getDoubleColumn4() == 0) {
            return 0;
        }
        return (info.getDoubleColumn8() / info.getDoubleColumn4()) * 100;
    }

    private double calcAvgTotalPreparePayRatio(ReportResultInfo[] infos, int day)
            throws Exception {
        double numerator = CommonBiz.calcTotal(CommonBiz
                .subArray(infos, 0, day), "DoubleColumn5")
                + CommonBiz.calcTotal(CommonBiz.subArray(infos, 0, day),
                        "DoubleColumn6")
                + CommonBiz.calcTotal(CommonBiz.subArray(infos, 0, day),
                        "DoubleColumn7")
                + CommonBiz.calcTotal(CommonBiz.subArray(infos, 0, day),
                        "DoubleColumn8");
        double denominator = CommonBiz.calcTotal(CommonBiz.subArray(infos, 0,
                day), "DoubleColumn1")
                + CommonBiz.calcTotal(CommonBiz.subArray(infos, 0, day),
                        "DoubleColumn2")
                + CommonBiz.calcTotal(CommonBiz.subArray(infos, 0, day),
                        "DoubleColumn3")
                + CommonBiz.calcTotal(CommonBiz.subArray(infos, 0, day),
                        "DoubleColumn4");
        if (denominator == 0) {
            return 0;
        }
        return (numerator / denominator) * 100;
    }

    private double calcAvgPreparePayRatioOfOneColumn(ReportResultInfo[] infos,
            int day, String columnName1, String columnName2) throws Exception {
        double numerator = CommonBiz.calcTotal(CommonBiz
                .subArray(infos, 0, day), columnName1);
        double denominator = CommonBiz.calcTotal(CommonBiz.subArray(infos, 0,
                day), columnName2);
        if (denominator == 0) {
            return 0;
        }
        return (numerator / denominator) * 100;
    }

    public static void main(String[] args) {
        Vector v = new Vector();
        Report3 r3 = new Report3();
        QueryFixedDepositInfo qInfo = new QueryFixedDepositInfo();
        qInfo.setOfficeID(1);
        qInfo.setCurrencyID(1);
        Timestamp ts = DataFormat.getDateTime("2005-09-01");
        try {
            v = r3.queryBalanceOfGradeTwoAccountOfIndustrialBank(ts, qInfo);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        for (int i = 0, n = v.size(); i < n; i++) {
            ReportResultInfo info = (ReportResultInfo) v.elementAt(i);
            System.out.println(info.getDoubleColumn2());
            System.out.println(info.getDoubleColumn7());
            System.out.println(info.getDoubleColumn13());
        }
    }
}