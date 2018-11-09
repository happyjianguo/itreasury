/*
 * Created on 2005-11-14
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.loan.report.bizlogic;

import java.util.Collection;
import java.util.LinkedList;
import com.iss.itreasury.loan.contract.bizlogic.ContractOperation;
import com.iss.itreasury.loan.report.dao.Report5Dao;
import com.iss.itreasury.loan.report.dataentity.ReportResultInfo;
import com.iss.itreasury.loan.report.dataentity.ReportWhereInfo;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.loan.util.LOANNameRef;

/**
 * @author yinghu
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class Report5 {
    /**
     * 查询财务公司贷款行业分类情况表1
     * 
     * @param wInfo
     * @return
     * @throws Exception
     */
    public Collection queryLoanTradeClassifyConditionWay1(ReportWhereInfo wInfo)
            throws Exception {
        ReportResultInfo[] infosFrom = (new Report5Dao())
                .queryLoanTradeClassifyConditionWay1(wInfo);
        if (infosFrom.length == 0) {
            return new LinkedList();
        }
        LinkedList infosAddedByClientId = addDepositToInfosGroupByClientId(calcDepositFromIdForInfos(infosFrom));
        //单位转换为万元
        for (int i = 0, n = infosAddedByClientId.size(); i < n; i++) {
            CommonBiz
                    .devideByTenThousandsForAllDoubleColumn((ReportResultInfo) infosAddedByClientId
                            .get(i));
        }
        ReportResultInfo firstInfo = (ReportResultInfo) infosAddedByClientId
                .get(0);
        long currclientTypeId = firstInfo.getLongColumn2();
        LinkedList result = new LinkedList();
        for (int i = 0, n = infosAddedByClientId.size(); i < n; i++) {
            ReportResultInfo tempInfo = (ReportResultInfo) infosAddedByClientId
                    .get(i);
            //遍历开始处先加第一种clientTypeId的汇总
            if (i == 0) {
                result.add(buildGatherInfoOfOneclientTypeId(
                        infosAddedByClientId, currclientTypeId));
            }
            //如果当前clientTypeId发生变化，就插入一个汇总
            if (tempInfo.getLongColumn2() != currclientTypeId) {
                currclientTypeId = tempInfo.getLongColumn2();
                result.add(buildGatherInfoOfOneclientTypeId(
                        infosAddedByClientId, currclientTypeId));
            }
            result.add(buildCommonInfo(tempInfo));
            //遍历结束的地方加上全体的汇总
            if (i == (n - 1)) {
                result.add(buildGatherInfoForAll(infosAddedByClientId));
            }
        }
        return result;
    }

    //返回的余额放在DoubleColumn1里面
    public LinkedList calcDepositFromIdForInfos(ReportResultInfo[] infosFrom)
            throws Exception {
        LinkedList result = new LinkedList();
        for (int i = 0; i < infosFrom.length; i++) {
            infosFrom[i].setDoubleColumn1(new ContractOperation()
                    .getLateAmount(infosFrom[i].getLongColumn5())
                    .getBalanceAmount());
            result.add(infosFrom[i]);
        }
        return result;
    }

    //遍历结果集按客户id分类累加余额
    public LinkedList addDepositToInfosGroupByClientId(LinkedList list) {
        LinkedList result = new LinkedList();
        ReportResultInfo firstInfo = (ReportResultInfo) list.get(0);
        long currClientId = firstInfo.getLongColumn1();
        ReportResultInfo infoToSaveDeposit = new ReportResultInfo();
        //遍历一个list,当clientId发生变化的时候把累加的值加入返回结果
        //list的末尾还需要增加一次把累加值放如返回结果list的操作
        for (int i = 0, n = list.size(); i < n; i++) {
            ReportResultInfo tempInfo = (ReportResultInfo) list.get(i);
            if (currClientId != tempInfo.getLongColumn1()) {
                result.add(infoToSaveDeposit);
                currClientId = tempInfo.getLongColumn1();
                infoToSaveDeposit = new ReportResultInfo();
                addDepositToReportResultInfo(tempInfo, infoToSaveDeposit);
            } else {
                addDepositToReportResultInfo(tempInfo, infoToSaveDeposit);
            }
            if (i == (n - 1)) {
                result.add(infoToSaveDeposit);
            }
        }
        return result;
    }

    //根据两种类型将余额加到传入数据info不同的属性上
    private void addDepositToReportResultInfo(ReportResultInfo infoFrom,
            ReportResultInfo infoTo) {
        infoTo.setLongColumn1(infoFrom.getLongColumn1());
        infoTo.setLongColumn2(infoFrom.getLongColumn2());
        infoTo.setStringColumn1(infoFrom.getStringColumn1());
//        if (infoFrom.getLongColumn4() == LOANConstant.IndustryType1.MILITARY) {
        if (infoFrom.getLongColumn4() == 1) {
//            if (infoFrom.getLongColumn3() == LOANConstant.AreaType.GENERAL) {
        	if (infoFrom.getLongColumn3() == 4) {
                infoTo.setDoubleColumn1(infoFrom.getDoubleColumn1()
                        + infoTo.getDoubleColumn1());
            }
//            if (infoFrom.getLongColumn3() == LOANConstant.AreaType.SPECIAL) {
        	if (infoFrom.getLongColumn3() == 3) {
                infoTo.setDoubleColumn2(infoFrom.getDoubleColumn1()
                        + infoTo.getDoubleColumn2());
            }
//            if (infoFrom.getLongColumn3() == LOANConstant.AreaType.POLICY) {
        	if (infoFrom.getLongColumn3() == 2) {
                infoTo.setDoubleColumn3(infoFrom.getDoubleColumn1()
                        + infoTo.getDoubleColumn3());
            }
//            if (infoFrom.getLongColumn3() == LOANConstant.AreaType.CLOSE) {
        	if (infoFrom.getLongColumn3() == 1) {
                infoTo.setDoubleColumn4(infoFrom.getDoubleColumn1()
                        + infoTo.getDoubleColumn4());
            }
        } else {
//            if (infoFrom.getLongColumn3() == LOANConstant.AreaType.GENERAL) {
        	if (infoFrom.getLongColumn3() == 4) {
                infoTo.setDoubleColumn5(infoFrom.getDoubleColumn1()
                        + infoTo.getDoubleColumn5());
            }
//            if (infoFrom.getLongColumn3() == LOANConstant.AreaType.SPECIAL) {
        	if (infoFrom.getLongColumn3() == 3) {
                infoTo.setDoubleColumn6(infoFrom.getDoubleColumn1()
                        + infoTo.getDoubleColumn6());
            }
//            if (infoFrom.getLongColumn3() == LOANConstant.AreaType.POLICY) {
        	if (infoFrom.getLongColumn3() == 2) {
                infoTo.setDoubleColumn7(infoFrom.getDoubleColumn1()
                        + infoTo.getDoubleColumn7());
            }
//            if (infoFrom.getLongColumn3() == LOANConstant.AreaType.CLOSE) {
        	if (infoFrom.getLongColumn3() == 1) {
                infoTo.setDoubleColumn8(infoFrom.getDoubleColumn1()
                        + infoTo.getDoubleColumn8());
            }
        }
    }

    private ReportResultInfo buildCommonInfo(ReportResultInfo infoFrom) {
        ReportResultInfo result = new ReportResultInfo();
        result.setStringColumn1(infoFrom.getStringColumn1());
        result.setDoubleColumn1(calcTotalForAll(infoFrom));
        result.setDoubleColumn2(infoFrom.getDoubleColumn1());
        result.setDoubleColumn3(infoFrom.getDoubleColumn2());
        result.setDoubleColumn4(infoFrom.getDoubleColumn3());
        result.setDoubleColumn5(infoFrom.getDoubleColumn4());
        result.setDoubleColumn6(calcTotalForIndustry(infoFrom));
        result.setDoubleColumn7(infoFrom.getDoubleColumn5());
        result.setDoubleColumn8(infoFrom.getDoubleColumn6());
        result.setDoubleColumn9(infoFrom.getDoubleColumn7());
        result.setDoubleColumn10(infoFrom.getDoubleColumn8());
        result.setDoubleColumn11(calcTotalForNotIndustry(infoFrom));
        return result;
    }

    private ReportResultInfo buildGatherInfoOfOneclientTypeId(LinkedList list,
            long clientTypeId) throws Exception {
        ReportResultInfo[] infosOfOneclientTypeId = new ReportResultInfo[querySumOfInfosOfOneclientTypeId(
                list, clientTypeId)];
        //将所需拥有特定clientTypeId的info筛选出来转储到一个数组中
        for (int i = 0, j = 0, n = list.size(); i < n; i++) {
            ReportResultInfo tempInfo = (ReportResultInfo) list.get(i);
            if (clientTypeId == tempInfo.getLongColumn2()) {
                infosOfOneclientTypeId[j] = tempInfo;
                j++;
            }
        }
        ReportResultInfo result = CommonBiz.calcTotal(infosOfOneclientTypeId);
        result = buildCommonInfo(result);
        result.setStringColumn1(LOANNameRef.getClientTypeNameByID(clientTypeId)
                + " 汇总");
        return result;
    }

    private ReportResultInfo buildGatherInfoForAll(LinkedList list)
            throws Exception {
        Object[] allInfos = list.toArray();
        ReportResultInfo result = CommonBiz.calcTotal(allInfos);
        result = buildCommonInfo(result);
        result.setStringColumn1("合计");
        return result;
    }

    private int querySumOfInfosOfOneclientTypeId(LinkedList list,
            long clientTypeId) {
        int result = 0;
        for (int i = 0, n = list.size(); i < n; i++) {
            ReportResultInfo tempInfo = (ReportResultInfo) list.get(i);
            if (clientTypeId == tempInfo.getLongColumn2()) {
                result++;
            }
        }
        return result;
    }

    private double calcTotalForIndustry(ReportResultInfo info) {
        return info.getDoubleColumn1() + info.getDoubleColumn2()
                + info.getDoubleColumn3() + info.getDoubleColumn4();
    }

    private double calcTotalForNotIndustry(ReportResultInfo info) {
        return info.getDoubleColumn5() + info.getDoubleColumn6()
                + info.getDoubleColumn7() + info.getDoubleColumn8();
    }

    private double calcTotalForAll(ReportResultInfo info) {
        return calcTotalForIndustry(info) + calcTotalForNotIndustry(info);
    }

}