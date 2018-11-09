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
import com.iss.itreasury.settlement.report.dao.Report6Dao;
import com.iss.itreasury.settlement.report.dataentity.ReportResultInfo;

/**
 * @author yinghu
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class Report6 {
    public Vector queryClientCurrentBudgetDetail(Timestamp firstMonth,
            Timestamp lastMonth, QueryFixedDepositInfo qInfo) throws Exception {
        ReportResultInfo[] infosFrom = (new Report6Dao())
                .queryClientCurrentBudgetDetail(firstMonth, lastMonth, qInfo);
        Vector result = new Vector();
        for (int i = 0; i < infosFrom.length; i++) {
            if (CommonBiz.isZeroInfo(infosFrom[i])) {
                continue;
            }
            ReportResultInfo infoFrom = infosFrom[i];
            ReportResultInfo infoTo = new ReportResultInfo();
            infoTo.setStringColumn1(infoFrom.getStringColumn1());
            infoTo.setDoubleColumn1(calcTotalBudget(infoFrom));
            infoTo.setDoubleColumn2(infoFrom.getDoubleColumn1());
            infoTo.setDoubleColumn3(infoFrom.getDoubleColumn2());
            infoTo.setDoubleColumn4(infoFrom.getDoubleColumn3());
            infoTo.setDoubleColumn5(infoFrom.getDoubleColumn4());
            infoTo.setDoubleColumn6(infoFrom.getDoubleColumn5());
            infoTo.setDoubleColumn7(infoFrom.getDoubleColumn6());
            infoTo.setDoubleColumn8(infoFrom.getDoubleColumn7());
            infoTo.setDoubleColumn9(infoFrom.getDoubleColumn8());
            infoTo.setDoubleColumn10(infoFrom.getDoubleColumn9());
            infoTo.setDoubleColumn11(calcBankResortRatio(infoFrom));
            result.addElement(infoTo);
        }
        return result;
    }

    private double calcTotalBudget(ReportResultInfo info) {
        return info.getDoubleColumn1() + info.getDoubleColumn2()
                + info.getDoubleColumn3() + info.getDoubleColumn4();
    }

    private double calcBankResortRatio(ReportResultInfo info) {
        if(calcTotalBudget(info)==0){
            return 0;
        }
        return (info.getDoubleColumn1() / calcTotalBudget(info)) * 100;
    }

    public static void main(String[] args) {
    }
}