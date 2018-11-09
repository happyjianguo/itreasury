package com.iss.itreasury.settlement.report.bizlogic;

import java.sql.Timestamp;
import java.util.Vector;
import com.iss.itreasury.settlement.query.paraminfo.QueryFixedDepositInfo;
import com.iss.itreasury.settlement.report.dao.Report5Dao;
import com.iss.itreasury.settlement.report.dataentity.ReportResultInfo;

public class Report5 {

    public Vector queryClientDepositOrder(Timestamp firstDay,
            Timestamp lastDay, QueryFixedDepositInfo qInfo) throws Exception {
        ReportResultInfo[] infos = (new Report5Dao().queryClientDepositOrder(
                firstDay, lastDay, qInfo));
        Vector result = new Vector();
        for (int i = 0; i < infos.length; i++) {//µ¥Î»×ª»»
            CommonBiz.devideByTenThousandsForAllDoubleColumn(infos[i]);
        }
        for (int i = 0; i < infos.length; i++) {
            if (CommonBiz.isZeroInfo(infos[i])) {
                continue;
            }
            infos[i].setDoubleColumn8(calcInnerDepositProportion(infos[i],
                    infos));
            result.addElement(infos[i]);
        }
        return result;
    }

    private double calcInnerDepositProportion(
            ReportResultInfo reportResultInfo,
            ReportResultInfo[] reportResultInfos) throws Exception {
        double result = 0;
        ReportResultInfo infoOfPlatformDeposit = CommonBiz
                .calcTotal(reportResultInfos);
        if (infoOfPlatformDeposit.getDoubleColumn1() == 0) {
            return 0;
        }
        result = reportResultInfo.getDoubleColumn1()
                / infoOfPlatformDeposit.getDoubleColumn1();
        result = result * 100;
        return result;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

}