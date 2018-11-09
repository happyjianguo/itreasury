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
import com.iss.itreasury.settlement.report.dao.Report2Dao;
import com.iss.itreasury.settlement.report.dataentity.ReportResultInfo;
import com.iss.itreasury.util.DataFormat;
 
/**
 * @author yinghu
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class Report2 {

    public Vector querySettlementPlatform(Timestamp date,
            QueryFixedDepositInfo qInfo) throws Exception {
        ReportResultInfo[] infosFrom = (new Report2Dao())
                .querySettlementPlatform(date, qInfo);
        Vector result = new Vector();
        for (int i = 0; i < infosFrom.length; i++) {
            if (i < CommonBiz.getDaysOfMonth(date)) {
                ReportResultInfo infoFrom = infosFrom[i];
                ReportResultInfo infoTo = new ReportResultInfo();
                infoTo.setStringColumn1(infoFrom.getStringColumn1());
                infoTo.setDoubleColumn1(infoFrom.getDoubleColumn1());
                infoTo.setDoubleColumn2(infoFrom.getDoubleColumn2());
                infoTo.setDoubleColumn3(infoFrom.getDoubleColumn3());
                infoTo.setDoubleColumn4(infoFrom.getDoubleColumn4());
                infoTo.setDoubleColumn5(infoFrom.getDoubleColumn5());
                infoTo.setDoubleColumn6(infoFrom.getDoubleColumn6());
                infoTo.setDoubleColumn7(calcBankDepositAddUp(infoFrom));
                infoTo.setDoubleColumn8(calcTotalPreparedToPayRatio(infoFrom));
                infoTo.setDoubleColumn9(calcSingleBankPreparedToPayRatio(
                        infoFrom, infoFrom.getDoubleColumn7()
                                - infoFrom.getDoubleColumn12()));
                infoTo.setDoubleColumn10(calcSingleBankPreparedToPayRatio(
                        infoFrom, infoFrom.getDoubleColumn8()));
                infoTo.setDoubleColumn11(calcSingleBankPreparedToPayRatio(
                        infoFrom, infoFrom.getDoubleColumn9()));
                infoTo.setDoubleColumn12(calcSingleBankPreparedToPayRatio(
                        infoFrom, infoFrom.getDoubleColumn10()));
                infoTo.setDoubleColumn13(calcSingleBankPreparedToPayRatio(
                        infoFrom, infoFrom.getDoubleColumn11()));
                result.addElement(infoTo);
            } else {//月份实际日期外放置空info
                result.addElement(new ReportResultInfo());
            }
        }
        return result;
    }

    private double calcAdjustedInnerDeposit(ReportResultInfo info) {
        return info.getDoubleColumn1()
                - (info.getDoubleColumn2() + info.getDoubleColumn3()
                        + info.getDoubleColumn4() + info.getDoubleColumn5() + info
                        .getDoubleColumn6());
    }

    private double calcBankDepositAddUp(ReportResultInfo info) {
        return (info.getDoubleColumn7() + info.getDoubleColumn8()
                + info.getDoubleColumn9() + info.getDoubleColumn10() + info
                .getDoubleColumn11());
    }

    private double calcTotalPreparedToPayRatio(ReportResultInfo info) {
        if (calcAdjustedInnerDeposit(info) == 0) {
            return 0;
        }
        return calcBankDepositAddUp(info) * 100
                / calcAdjustedInnerDeposit(info);
    }

    private double calcSingleBankPreparedToPayRatio(ReportResultInfo info,
            double balanceOfSingleBank) {
        if (calcBankDepositAddUp(info) == 0) {
            return 0;
        }
        return balanceOfSingleBank * 100 / calcBankDepositAddUp(info);
    }

    public static void main(String[] args) {
        QueryFixedDepositInfo qInfo = new QueryFixedDepositInfo();
        qInfo.setOfficeID(1);
        qInfo.setCurrencyID(1);
        Timestamp ts = DataFormat.getDateTime("2005-10-15");
        Vector v1 = null;
        try {
            v1 = (new Report2()).querySettlementPlatform(ts, qInfo);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        for (int i = 0, n = v1.size(); i < n; i++) {
            ReportResultInfo info = (ReportResultInfo) v1.elementAt(i);
            System.out.println(info.getDoubleColumn7() + "--"
                    + info.getDoubleColumn1() + "--" + info.getDoubleColumn8());
        }

    }
}