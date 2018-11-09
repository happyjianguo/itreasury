/*
 * Created on 2005-10-10
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.loan.report.bizlogic;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import com.iss.itreasury.loan.report.dataentity.ReportResultInfo;

/**
 * @author yinghu
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class CommonBiz {

    /**
     * 取得ReportResultInfo指定参数名的get方法
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
     * 取得ReportResultInfo指定参数名的set方法
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
     * 计算一个reportResultInfo数组内部reportResultInfo指定属性的平均值
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
     * 计算一个reportResultInfo数组内部reportResultInfo指定属性的和
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
     * 按字段计算一个reportResultInfo数组内部所有的double类型的字段之和
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
     * 判断一个info是否为空 判断依据是info的所有double字段是0
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
     * 所有double列除10000
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
}