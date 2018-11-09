/*
 * Created on 2005-10-10
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.settlement.report.bizlogic;

import java.sql.Timestamp;
import java.util.Vector;
import com.iss.itreasury.settlement.report.dao.Report1Dao;
import com.iss.itreasury.settlement.query.paraminfo.QueryFixedDepositInfo;
import com.iss.itreasury.settlement.report.dataentity.ReportResultInfo;
import com.iss.itreasury.util.DataFormat;

/**
 * @author yinghu
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class Report1 {

    /**
     * 返回查询结果集
     * 
     * @param year
     * @param month
     * @param qInfo
     * @return
     * @throws Exception
     */
    public Vector queryBankDepositBalanceDetail(Timestamp date,
            QueryFixedDepositInfo qInfo) throws Exception {
        ReportResultInfo[] infosFrom = (new Report1Dao()
                .queryBankDepositBalanceDetail(date, qInfo));
        Vector result = new Vector();
        for (int i = 0; i < infosFrom.length; i++) {
            if (i < CommonBiz.getDaysOfMonth(date)) {
                ReportResultInfo infoTo = transferReportResultInfo(
                        infosFrom[i], CommonBiz.subArray(infosFrom, 0, i + 1));
                result.addElement(infoTo);
            } else {//月份实际日期外放置空info
                result.addElement(new ReportResultInfo());
            }
        }
        return result;
    }

    private ReportResultInfo transferReportResultInfo(
            ReportResultInfo reportResultInfo, ReportResultInfo[] dataGather)
            throws Exception {
        ReportResultInfo result = new ReportResultInfo();
        result.setStringColumn1(reportResultInfo.getStringColumn1());
        result.setDoubleColumn1(addAllForCurrentDay(reportResultInfo));
        result.setDoubleColumn2(addAllForAverage(dataGather));
        result.setDoubleColumn3(reportResultInfo.getDoubleColumn1());
        result.setDoubleColumn4(CommonBiz.calcAvarage(dataGather,
                "DoubleColumn1"));
        result.setDoubleColumn5(reportResultInfo.getDoubleColumn2());
        result.setDoubleColumn6(CommonBiz.calcAvarage(dataGather,
                "DoubleColumn2"));
        result.setDoubleColumn7(reportResultInfo.getDoubleColumn3());
        result.setDoubleColumn8(CommonBiz.calcAvarage(dataGather,
                "DoubleColumn3"));
        result.setDoubleColumn9(reportResultInfo.getDoubleColumn4());
        result.setDoubleColumn10(CommonBiz.calcAvarage(dataGather,
                "DoubleColumn4"));
        result.setDoubleColumn11(reportResultInfo.getDoubleColumn5());
        result.setDoubleColumn12(CommonBiz.calcAvarage(dataGather,
                "DoubleColumn5"));
        result.setDoubleColumn13(reportResultInfo.getDoubleColumn6());
        result.setDoubleColumn14(CommonBiz.calcAvarage(dataGather,
                "DoubleColumn6"));
        result.setDoubleColumn15(reportResultInfo.getDoubleColumn7());
        result.setDoubleColumn16(CommonBiz.calcAvarage(dataGather,
                "DoubleColumn7"));
        result.setDoubleColumn17(reportResultInfo.getDoubleColumn8());
        result.setDoubleColumn18(CommonBiz.calcAvarage(dataGather,
                "DoubleColumn8"));
        result.setDoubleColumn19(reportResultInfo.getDoubleColumn9());
        result.setDoubleColumn20(CommonBiz.calcAvarage(dataGather,
                "DoubleColumn9"));
        return result;
    }

    private double addAllForCurrentDay(ReportResultInfo reportResultInfo) {
        double result = 0;
        result = reportResultInfo.getDoubleColumn1()
                + reportResultInfo.getDoubleColumn2()
                + reportResultInfo.getDoubleColumn3()
                + reportResultInfo.getDoubleColumn4()
                + reportResultInfo.getDoubleColumn5()
                + reportResultInfo.getDoubleColumn6()
                + reportResultInfo.getDoubleColumn7()
                + reportResultInfo.getDoubleColumn8()
                + reportResultInfo.getDoubleColumn9();
        return result;
    }

    private double addAllForAverage(ReportResultInfo[] reportResultInfos)
            throws Exception {
        double result = 0;
        result = CommonBiz.calcAvarage(reportResultInfos, "DoubleColumn1")
                + CommonBiz.calcAvarage(reportResultInfos, "DoubleColumn2")
                + CommonBiz.calcAvarage(reportResultInfos, "DoubleColumn3")
                + CommonBiz.calcAvarage(reportResultInfos, "DoubleColumn4")
                + CommonBiz.calcAvarage(reportResultInfos, "DoubleColumn5")
                + CommonBiz.calcAvarage(reportResultInfos, "DoubleColumn6")
                + CommonBiz.calcAvarage(reportResultInfos, "DoubleColumn7")
                + CommonBiz.calcAvarage(reportResultInfos, "DoubleColumn8")
                + CommonBiz.calcAvarage(reportResultInfos, "DoubleColumn9");
        return result;
    }

    public static void main(String[] args) {
        Report1 report = new Report1();
        Vector v = null;
        Timestamp date=DataFormat.getDateTime("2005-08-09");
        QueryFixedDepositInfo qInfo = new QueryFixedDepositInfo();
        qInfo.setCurrencyID(1);
        qInfo.setOfficeID(1);
        try {
            v=report.queryBankDepositBalanceDetail(date,qInfo);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        for(int i=0;i<v.size();i++){
            ReportResultInfo info=(ReportResultInfo)v.elementAt(i);
            System.out.println(info.getDoubleColumn1()+"==="+info.getDoubleColumn2());
        }

    }
}