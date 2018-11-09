/*
 * Created on 2005-9-28
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.settlement.report.bizlogic;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.Vector;
import com.iss.itreasury.settlement.query.paraminfo.QueryFixedDepositInfo;
import com.iss.itreasury.settlement.report.dao.Report11Dao;
import com.iss.itreasury.settlement.report.dataentity.ReportResultInfo;

/**
 * @author yinghu
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class Report11 {
    public ReportResultInfo[] queryNextMonthFetchFundToPayDetail(
            Timestamp date, QueryFixedDepositInfo qInfo) throws Exception {

        return zeroFilter((new Report11Dao())
                .queryNextMonthFetchFundToPayDetail(date, qInfo));
    }

    public double calcNextMonthFetchFundToPayGather(
            ReportResultInfo[] reportResultInfos) throws Exception {
        double result = 0;
        Field[] fields = ReportResultInfo.class.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            String fieldName = fields[i].getName();
            if (fieldName.indexOf("Double") >= 0) {
                result += CommonBiz.calcTotal(reportResultInfos, fieldName);
            }
        }
        return result;
    }

    private ReportResultInfo[] zeroFilter(ReportResultInfo[] resourceInfos)
            throws Exception {
        int resultSize = 0;
        for (int i = 0; i < resourceInfos.length; i++) {
            if (!CommonBiz.isZeroInfo(resourceInfos[i])) {
                resultSize++;
            }
        }
        ReportResultInfo[] result = new ReportResultInfo[resultSize];
        for (int i = 0, pos = 0; i < resourceInfos.length; i++) {
            if (!CommonBiz.isZeroInfo(resourceInfos[i])) {
                result[pos] = resourceInfos[i];
                pos++;
            }
        }
        return result;
    }

    public static void main(String[] args) {

        Vector v1 = null;
        try {

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        System.out.println("ok!");
    }
}