/*
 * Created on 2005-9-23
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.settlement.report.bizlogic;

import java.sql.Timestamp;
import java.util.Vector;
import com.iss.itreasury.settlement.query.paraminfo.QueryFixedDepositInfo;
import com.iss.itreasury.settlement.report.dao.Report4Dao;
import com.iss.itreasury.settlement.report.dataentity.ReportResultInfo;
import com.iss.itreasury.settlement.util.NameRef;
import com.iss.itreasury.util.DataFormat;

/**
 * @author yinghu
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class Report4 {
    /**
     * 按查询条件取得客户存款余额结构
     * 
     * @param date
     * @param qInfo
     * @return
     * @throws Exception
     */
    public Vector queryClientDepositStructure(Timestamp firstDay,
            Timestamp lastDay, QueryFixedDepositInfo qInfo) throws Exception {
        Vector result = new Vector();
        Report4Dao dao = new Report4Dao();
        ReportResultInfo[] infos = dao.queryClientDepositStructure(firstDay,
                lastDay, qInfo);
        //将数据集合的数值除以10000实现单位由元变成万元
        for(int i=0;i<infos.length;i++){
            CommonBiz.devideByTenThousandsForAllDoubleColumn(infos[i]);
        }
        long currentClientTypeId = infos[0].getLongColumn1();//初始化当前客户种类
        //对数据集合进行转换计算变成页面需要的形式
        for (int i = 0; i < infos.length; i++) {
            if (CommonBiz.isZeroInfo(infos[i])) {
                continue;
            }
            if (currentClientTypeId != infos[i].getLongColumn1())//当前客户种类发生变化就插入汇总
            {
                ReportResultInfo gatherInfoOfOneClientType = buildGatherInfoOfOneClientType(
                        infos, currentClientTypeId);
                result.addElement(gatherInfoOfOneClientType);
                currentClientTypeId = infos[i].getLongColumn1();
            }
            ReportResultInfo commonInfo = buildCommonInfo(infos[i], infos);
            result.addElement(commonInfo);
            //最后一次汇总无法通过客户种类变化来取得，需要追加
            if(i==(infos.length-1)){
                ReportResultInfo gatherInfoOfOneClientType = buildGatherInfoOfOneClientType(
                        infos, currentClientTypeId);
                result.addElement(gatherInfoOfOneClientType);
            }
        }
        return result;
    }

    private ReportResultInfo buildCommonInfo(ReportResultInfo info,
            ReportResultInfo[] infos) throws Exception {
        ReportResultInfo result = new ReportResultInfo();
        result.setStringColumn1(info.getStringColumn1());
        result.setLongColumn1(info.getLongColumn1());
        result.setDoubleColumn1(addAllFinanceCompanyDeposit(info)
                + addAllLocalBankDeposit(info));
        result.setDoubleColumn2(addAllFinanceCompanyDeposit(info));
        result.setDoubleColumn3(info.getDoubleColumn1());
        result.setDoubleColumn4(info.getDoubleColumn2());
        result.setDoubleColumn5(info.getDoubleColumn3());
        result.setDoubleColumn6(info.getDoubleColumn4());
        result.setDoubleColumn7(info.getDoubleColumn5());
        result.setDoubleColumn8(info.getDoubleColumn6());
        result.setDoubleColumn9(addAllLocalBankDeposit(info));
        result.setDoubleColumn10(info.getDoubleColumn7());
        result.setDoubleColumn11(info.getDoubleColumn8());
        result.setDoubleColumn12(info.getDoubleColumn9());
        result.setDoubleColumn13(info.getDoubleColumn10());
        result.setDoubleColumn14(info.getDoubleColumn11());
        result.setDoubleColumn15(calcFocusDegree(info));
        result.setDoubleColumn16(calcInnerDepositProportion(info, infos));
        result.setDoubleColumn17(calcCurrentProportion(info));
        return result;
    }

    private int querySumOfClientsOfOneClientType(
            ReportResultInfo[] reportResultInfos, long clientTypeId) {
        int result = 0;
        for (int i = 0; i < reportResultInfos.length; i++) {
            if (clientTypeId == reportResultInfos[i].getLongColumn1()) {
                result++;
            }
        }
        return result;
    }

    private ReportResultInfo buildGatherInfoOfOneClientType(
            ReportResultInfo[] infos, long clientTypeId) throws Exception {
        int sumOfClientsOfOneClientType = querySumOfClientsOfOneClientType(
                infos, clientTypeId);
        ReportResultInfo[] infosOfOneClientType = new ReportResultInfo[sumOfClientsOfOneClientType];
        //把一种客户类型的info放到一个新数组里
        for (int i = 0, pos = 0; i < infos.length; i++) {
            if (infos[i].getLongColumn1() == clientTypeId) {
                infosOfOneClientType[pos] = infos[i];
                pos++;
            }
        }
        ReportResultInfo result = buildCommonInfo(CommonBiz
                .calcTotal(infosOfOneClientType), infos);
        result.setStringColumn1(NameRef.getClientTypeNameByID(clientTypeId)
                + "汇总");
        result.setLongColumn1(clientTypeId);
        return result;
    }

    private double calcFocusDegree(ReportResultInfo reportResultInfo) {
        double result = 0;
        result = addAllFinanceCompanyDeposit(reportResultInfo);
        if ((addAllFinanceCompanyDeposit(reportResultInfo)
                + addAllLocalBankDeposit(reportResultInfo) - reportResultInfo
                .getDoubleColumn12()) == 0) {
            return 0;
        }
        result = result
                / (addAllFinanceCompanyDeposit(reportResultInfo)
                        + addAllLocalBankDeposit(reportResultInfo) - reportResultInfo
                        .getDoubleColumn12());
        result = result * 100;
        if (result > 100) {
            result = 100;
        }
        return result;
    }

    private double calcInnerDepositProportion(
            ReportResultInfo reportResultInfo,
            ReportResultInfo[] reportResultInfos) throws Exception {

        double result = 0;
        ReportResultInfo infoOfPlatformDeposit = CommonBiz
                .calcTotal(reportResultInfos);
        if (addAllFinanceCompanyDeposit(infoOfPlatformDeposit) == 0) {
            return 0;
        }
        result = addAllFinanceCompanyDeposit(reportResultInfo)
                / addAllFinanceCompanyDeposit(infoOfPlatformDeposit);
        result = result * 100;
        return result;
    }

    private double calcCurrentProportion(ReportResultInfo reportResultInfo) {
        double result = 0;
        if (addAllFinanceCompanyDeposit(reportResultInfo) == 0) {
            return 0;
        }
        result = reportResultInfo.getDoubleColumn1()
                / addAllFinanceCompanyDeposit(reportResultInfo);
        result = result * 100;
        return result;
    }

    private double addAllFinanceCompanyDeposit(ReportResultInfo reportResultInfo) {
        double result = 0;
        result = reportResultInfo.getDoubleColumn1()
                + reportResultInfo.getDoubleColumn2()
                + reportResultInfo.getDoubleColumn3()
                + reportResultInfo.getDoubleColumn4()
                + reportResultInfo.getDoubleColumn5()
                + reportResultInfo.getDoubleColumn6();
        return result;
    }

    private double addAllLocalBankDeposit(ReportResultInfo reportResultInfo) {
        double result = 0;
        result = reportResultInfo.getDoubleColumn7()
                + reportResultInfo.getDoubleColumn8()
                + reportResultInfo.getDoubleColumn9()
                + reportResultInfo.getDoubleColumn10()
                + reportResultInfo.getDoubleColumn11();
        return result;
    }

    public static void main(String[] args) {
        Report4 r4 = new Report4();
        QueryFixedDepositInfo qInfo = new QueryFixedDepositInfo();
        qInfo.setOfficeID(1);
        qInfo.setCurrencyID(1);
        Timestamp ts1 = DataFormat.getDateTime("2005-10-11");
        Timestamp ts2 = DataFormat.getDateTime("2005-10-11");
        ReportResultInfo[] infos=null;
        try {
            
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        

    }
}