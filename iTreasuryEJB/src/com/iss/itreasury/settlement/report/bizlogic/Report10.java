package com.iss.itreasury.settlement.report.bizlogic;

import java.sql.Timestamp;
import java.util.Vector;

import com.iss.itreasury.settlement.query.paraminfo.QueryFixedDepositInfo;
import com.iss.itreasury.settlement.query.queryobj.BaseQueryObject;
import com.iss.itreasury.settlement.report.dao.Report10Dao;
import com.iss.itreasury.settlement.report.dataentity.ReportResultInfo;

public class Report10 extends BaseQueryObject {

    /**
     * 取得定期存款结构
     * 
     * @param date
     * @param qInfo
     * @return
     * @throws Exception
     */
    public Vector queryFixedDepositStructure(Timestamp date,
            QueryFixedDepositInfo qInfo) throws Exception {
        ReportResultInfo[] infosFrom = (new Report10Dao())
                .queryFixedDepositStructure(date, qInfo);
        Vector result = new Vector();
        for (int i = 0; i < infosFrom.length; i++) {
            ReportResultInfo infoFrom = infosFrom[i];
            ReportResultInfo infoTo = new ReportResultInfo();
            infoTo.setStringColumn1(infoFrom.getStringColumn1());
            infoTo.setDoubleColumn1(infoFrom.getDoubleColumn1());
            infoTo.setDoubleColumn2(infoFrom.getDoubleColumn2());
            infoTo.setDoubleColumn3(infoFrom.getDoubleColumn3());
            infoTo.setDoubleColumn4(calcClientDepositStructureGather(infoFrom));
            infoTo.setDoubleColumn5(infoFrom.getDoubleColumn4());
            infoTo.setDoubleColumn6(infoFrom.getDoubleColumn5());
            infoTo.setDoubleColumn7(infoFrom.getDoubleColumn6());
            infoTo.setDoubleColumn8(infoFrom.getDoubleColumn7());
            infoTo.setDoubleColumn9(infoFrom.getDoubleColumn8());
            infoTo.setDoubleColumn10(infoFrom.getDoubleColumn9());
            infoTo.setDoubleColumn11(infoFrom.getDoubleColumn10());
            infoTo.setDoubleColumn12(infoFrom.getDoubleColumn11());
            infoTo.setDoubleColumn13(infoFrom.getDoubleColumn12());
            infoTo.setDoubleColumn14(infoFrom.getDoubleColumn13());
            infoTo.setDoubleColumn15(infoFrom.getDoubleColumn14());
            infoTo.setDoubleColumn16(infoFrom.getDoubleColumn15());
            result.addElement(infoTo);
        }
        result = zeroFilter(result);
        return result;
    }

    private Vector zeroFilter(Vector vInfos) throws Exception {
        Vector result = new Vector();
        for (int i = 0, n = vInfos.size(); i < n; i++) {
            if (!CommonBiz.isZeroInfo((ReportResultInfo) vInfos.elementAt(i))) {
                result.addElement((ReportResultInfo) vInfos.elementAt(i));
            }
        }
        return result;
    }

    private double calcClientDepositStructureGather(
            ReportResultInfo reportResultInfo) {
        return reportResultInfo.getDoubleColumn1()
                + reportResultInfo.getDoubleColumn2()
                + reportResultInfo.getDoubleColumn3();
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

}