/*
 * Created on 2005-10-10
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.settlement.report.bizlogic;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import com.iss.itreasury.settlement.query.paraminfo.QueryFixedDepositInfo;
import com.iss.itreasury.settlement.report.dataentity.ReportResultInfo;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Env;

/**
 * @author yinghu
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class CommonBiz {
    public static int getDaysOfMonth(Timestamp date) {
        String strDate = DataFormat.getDateString(DataFormat
                .getLastDateOfMonth(date));
        return Integer.parseInt(strDate.substring(8));
    }

    public static boolean isTodayOrAfterToday(Timestamp date,
            QueryFixedDepositInfo qInfo) throws Exception {
        Timestamp today = Env.getSystemDate(qInfo.getOfficeID(), qInfo
                .getCurrencyID());
        if (DataFormat.getDateString(date).compareTo(
                DataFormat.getDateString(today)) >= 0) {
            return true;
        }
        return false;
    }

    public static int numberOfMonthes(Timestamp firstMonth, Timestamp lastMonth) {
        return Integer.parseInt(DataFormat.getMonthString(lastMonth))
                - Integer.parseInt(DataFormat.getMonthString(firstMonth)) + 1;
    }

    /**
     * ȡ��ReportResultInfoָ����������get����
     * 
     * @param paraName
     * @return
     * @throws Exception
     */
    public static Method getReadMethodByParaName(String paraName)
            throws Exception {
        Method result = null;
        String methodName = null;
        methodName = "get" + paraName.substring(0, 1).toUpperCase()
                + paraName.substring(1);
        result = ReportResultInfo.class.getMethod(methodName, new Class[] {});
        return result;
    }

    /**
     * ȡ��ReportResultInfoָ����������set����
     * 
     * @param paraName
     * @return
     * @throws Exception
     */
    public static Method getWriteMethodByParaName(String paraName,
            Class paraClass) throws Exception {
        Method result = null;
        String methodName = null;
        methodName = "set" + paraName.substring(0, 1).toUpperCase()
                + paraName.substring(1);
        result = ReportResultInfo.class.getMethod(methodName,
                new Class[] { paraClass });
        return result;
    }

    public static ReportResultInfo[] subArray(ReportResultInfo[] resourceArray,
            int pos1, int pos2) {
        if (pos1 < 0 || pos1 >= pos2 || pos2 > resourceArray.length) {
            return null;
        }
        ReportResultInfo[] result = new ReportResultInfo[pos2 - pos1];
        for (int i = pos1; i < pos2; i++) {
            result[i - pos1] = resourceArray[i];
        }
        return result;
    }

    /**
     * ����һ��reportResultInfo�����ڲ�reportResultInfoָ�����Ե�ƽ��ֵ
     * 
     * @param pos
     * @param vDataSource
     * @param paraName
     * @return
     * @throws Exception
     */
    public static double calcAvarage(Object[] dataGather, String paraName)
            throws Exception {

        return calcTotal(dataGather, paraName) / dataGather.length;
    };

    /**
     * ����һ��reportResultInfo�����ڲ�reportResultInfoָ�����Եĺ�
     * 
     * @param pos
     * @param vDataSource
     * @param paraName
     * @return
     * @throws Exception
     */
    public static double calcTotal(Object[] dataGather, String paraName)
            throws Exception {
        double result = 0;
        for (int i = 0; i < dataGather.length; i++) {
            ReportResultInfo tempInfo = (ReportResultInfo) dataGather[i];
            result += Double.parseDouble(getReadMethodByParaName(paraName)
                    .invoke(tempInfo, new Object[]{}).toString());
        }
        return result;
    }

    /**
     * ���ֶμ���һ��reportResultInfo�����ڲ����е�double���͵��ֶ�֮��
     * 
     * @param args
     * @throws Exception
     */
    public static ReportResultInfo calcTotal(Object[] dataGather)
            throws Exception {
        ReportResultInfo result = new ReportResultInfo();
        Field[] fields = ReportResultInfo.class.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            String fieldName = fields[i].getName();
            if (fields[i].getType().getName().indexOf("double")>=0) {
                double tempDouble = calcTotal(dataGather, fieldName);
                getWriteMethodByParaName(fieldName, double.class).invoke(
                        result, new Object[] { new Double(tempDouble) });
            }
        }
        return result;
    }

    /**
     * �ж�һ��info�Ƿ�Ϊ�� �ж�������info������double�ֶ���0
     * 
     * @param reportResultInfo
     * @return
     * @throws Exception
     */
    public static boolean isZeroInfo(ReportResultInfo reportResultInfo)
            throws Exception {
        Field[] fields = ReportResultInfo.class.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            String fieldName = fields[i].getName();
            if (fields[i].getType().getName().indexOf("double")>=0) {
                Double tempDouble = (Double) getReadMethodByParaName(fieldName)
                        .invoke(reportResultInfo, new Object[] {});
                if (tempDouble.doubleValue() > 0.00001) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * ����double�г�10000
     * 
     * @return
     * @throws Exception
     */
    public static void devideByTenThousandsForAllDoubleColumn(
            ReportResultInfo reportResultInfo) throws Exception {
        Field[] fields = ReportResultInfo.class.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            String fieldName = fields[i].getName();
            if (fields[i].getType().getName().indexOf("double")>=0) {
                Double tempDouble = (Double) getReadMethodByParaName(fieldName)
                        .invoke(reportResultInfo, new Object[] {});
                getWriteMethodByParaName(fieldName, double.class).invoke(
                        reportResultInfo,
                        new Object[] { new Double(
                                tempDouble.doubleValue() / 10000) });
            }
        }
    }

    public static void main(String[] args) {
        ReportResultInfo info=new ReportResultInfo();
        info.setDoubleColumn1(30000);
        try {
            devideByTenThousandsForAllDoubleColumn(info);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println(info.getDoubleColumn1());
    }
}