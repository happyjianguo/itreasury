/*
 * Created on 2005-11-14
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.loan.report.bizlogic;

import java.util.LinkedList;
import com.iss.itreasury.loan.contract.bizlogic.ContractOperation;
import com.iss.itreasury.loan.report.dao.Report6Dao;
import com.iss.itreasury.loan.report.dataentity.ReportResultInfo;
import com.iss.itreasury.loan.report.dataentity.ReportWhereInfo;
import com.iss.itreasury.loan.util.LOANConstant;

/**
 * @author yinghu
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class Report6 {
    /**
     * 查询财务公司贷款行业分类情况表2
     * 
     * @param wInfo
     * @return
     * @throws Exception
     */
    public ReportResultInfo queryLoanTradeClassifyConditionWay2(
            ReportWhereInfo wInfo) throws Exception {
        ReportResultInfo[] infosFrom = (new Report6Dao())
                .queryLoanTradeClassifyConditionWay2(wInfo);
        if (infosFrom.length == 0) {
            return new ReportResultInfo();
        }
        LinkedList listFrom = calcDepositFromIdForInfos(infosFrom);
        //单位转换为万元
        for (int i = 0, n = listFrom.size(); i < n; i++) {
            CommonBiz
                    .devideByTenThousandsForAllDoubleColumn((ReportResultInfo) listFrom
                            .get(i));
        }
        ReportResultInfo infoToAddOn = new ReportResultInfo();
        for (int i = 0, n = listFrom.size(); i < n; i++) {
            ReportResultInfo tempInfo = (ReportResultInfo) listFrom.get(i);
            addDepositToReportResultInfo(tempInfo, infoToAddOn);
        }
         return transferInfo(infoToAddOn);
    }

    //返回的余额放在DoubleColumn1里面
    private LinkedList calcDepositFromIdForInfos(ReportResultInfo[] infosFrom)
            throws Exception {
        LinkedList result = new LinkedList();
        for (int i = 0; i < infosFrom.length; i++) {
            infosFrom[i].setDoubleColumn1(new ContractOperation()
                    .getLateAmount(infosFrom[i].getLongColumn1())
                    .getBalanceAmount());
            result.add(infosFrom[i]);
        }
        return result;
    }

    private ReportResultInfo transferInfo(ReportResultInfo info) {
        ReportResultInfo result = new ReportResultInfo();
        result.setDoubleColumn1(info.getDoubleColumn1());
        result.setDoubleColumn2(info.getDoubleColumn2());
        result.setDoubleColumn3(info.getDoubleColumn3());
        result.setDoubleColumn4(info.getDoubleColumn4());
        result.setDoubleColumn5(calcTotalForIndustry(info));
        result.setDoubleColumn6(info.getDoubleColumn5());
        result.setDoubleColumn7(info.getDoubleColumn6());
        result.setDoubleColumn8(info.getDoubleColumn7());
        result.setDoubleColumn9(info.getDoubleColumn8());
        result.setDoubleColumn10(calcTotalForNotIndustry(info));
        //计算10个比值，放到info的DoubleColumn11--DoubleColumn20
        if ((info.getDoubleColumn1() + info.getDoubleColumn5()) == 0) {
            result.setDoubleColumn11(0);
            result.setDoubleColumn16(0);
        } else {
            result.setDoubleColumn11(100 * info.getDoubleColumn1()
                    / (info.getDoubleColumn1() + info.getDoubleColumn5()));
            result.setDoubleColumn16(100 * info.getDoubleColumn5()
                    / (info.getDoubleColumn1() + info.getDoubleColumn5()));
        }
        if ((info.getDoubleColumn2() + info.getDoubleColumn6()) == 0) {
            result.setDoubleColumn12(0);
            result.setDoubleColumn17(0);
        } else {
            result.setDoubleColumn12(100 * info.getDoubleColumn2()
                    / (info.getDoubleColumn2() + info.getDoubleColumn6()));
            result.setDoubleColumn17(100 * info.getDoubleColumn6()
                    / (info.getDoubleColumn2() + info.getDoubleColumn6()));
        }
        if ((info.getDoubleColumn3() + info.getDoubleColumn7()) == 0) {
            result.setDoubleColumn13(0);
            result.setDoubleColumn18(0);
        } else {
            result.setDoubleColumn13(100 * info.getDoubleColumn3()
                    / (info.getDoubleColumn3() + info.getDoubleColumn7()));
            result.setDoubleColumn18(100 * info.getDoubleColumn7()
                    / (info.getDoubleColumn3() + info.getDoubleColumn7()));
        }
        if ((info.getDoubleColumn4() + info.getDoubleColumn8()) == 0) {
            result.setDoubleColumn14(0);
            result.setDoubleColumn19(0);
        } else {
            result.setDoubleColumn14(100 * info.getDoubleColumn4()
                    / (info.getDoubleColumn4() + info.getDoubleColumn8()));
            result.setDoubleColumn19(100 * info.getDoubleColumn8()
                    / (info.getDoubleColumn4() + info.getDoubleColumn8()));
        }
        if ((calcTotalForIndustry(info) + calcTotalForNotIndustry(info)) == 0) {
            result.setDoubleColumn15(0);
            result.setDoubleColumn20(0);
        } else {
            result.setDoubleColumn15(100 * calcTotalForIndustry(info)
                    / (calcTotalForIndustry(info) + calcTotalForNotIndustry(info)));
            result.setDoubleColumn20(100 * calcTotalForNotIndustry(info)
                    / (calcTotalForIndustry(info) + calcTotalForNotIndustry(info)));
        }
        result.setDoubleColumn21(info.getDoubleColumn1()+info.getDoubleColumn5()); 
        result.setDoubleColumn22(info.getDoubleColumn2()+info.getDoubleColumn6());
        result.setDoubleColumn23(info.getDoubleColumn3()+info.getDoubleColumn7());
        result.setDoubleColumn24(info.getDoubleColumn4()+info.getDoubleColumn8());
        result.setDoubleColumn25(calcTotalForIndustry(info)+calcTotalForNotIndustry(info));
        return result;
    }

    //根据两种类型将余额加到传入数据info不同的属性上
    private void addDepositToReportResultInfo(ReportResultInfo infoFrom,
            ReportResultInfo infoTo) {
//        if (infoFrom.getLongColumn3() == LOANConstant.IndustryType1.MILITARY) {
    	if (infoFrom.getLongColumn3() == 1) {
//            if (infoFrom.getLongColumn2() == LOANConstant.AreaType.GENERAL) {
            if (infoFrom.getLongColumn4() == 4) {
                infoTo.setDoubleColumn1(infoFrom.getDoubleColumn1()
                        + infoTo.getDoubleColumn1());
            }
//            if (infoFrom.getLongColumn2() == LOANConstant.AreaType.SPECIAL) {
            if (infoFrom.getLongColumn2() == 3) {
                infoTo.setDoubleColumn2(infoFrom.getDoubleColumn1()
                        + infoTo.getDoubleColumn2());
            }
//            if (infoFrom.getLongColumn2() == LOANConstant.AreaType.POLICY) {
            if (infoFrom.getLongColumn2() == 2) {
                infoTo.setDoubleColumn3(infoFrom.getDoubleColumn1()
                        + infoTo.getDoubleColumn3());
            }
//            if (infoFrom.getLongColumn2() == LOANConstant.AreaType.CLOSE) {
            if (infoFrom.getLongColumn2() == 1) {
                infoTo.setDoubleColumn4(infoFrom.getDoubleColumn1()
                        + infoTo.getDoubleColumn4());
            }
        } else {
//            if (infoFrom.getLongColumn2() == LOANConstant.AreaType.GENERAL) {
        	if (infoFrom.getLongColumn2() == 4) {
                infoTo.setDoubleColumn5(infoFrom.getDoubleColumn1()
                        + infoTo.getDoubleColumn5());
            }
//            if (infoFrom.getLongColumn2() == LOANConstant.AreaType.SPECIAL) {
        	if (infoFrom.getLongColumn2() == 3) {
                infoTo.setDoubleColumn6(infoFrom.getDoubleColumn1()
                        + infoTo.getDoubleColumn6());
            }
//            if (infoFrom.getLongColumn2() == LOANConstant.AreaType.POLICY) {
        	if (infoFrom.getLongColumn2() == 2) {
                infoTo.setDoubleColumn7(infoFrom.getDoubleColumn1()
                        + infoTo.getDoubleColumn7());
            }
//            if (infoFrom.getLongColumn2() == LOANConstant.AreaType.CLOSE) {
        	if (infoFrom.getLongColumn2() == 1) {
                infoTo.setDoubleColumn8(infoFrom.getDoubleColumn1()
                        + infoTo.getDoubleColumn8());
            }
        }
    }

    private double calcTotalForIndustry(ReportResultInfo info) {
        return info.getDoubleColumn1() + info.getDoubleColumn2()
                + info.getDoubleColumn3() + info.getDoubleColumn4();
    }

    private double calcTotalForNotIndustry(ReportResultInfo info) {
        return info.getDoubleColumn5() + info.getDoubleColumn6()
                + info.getDoubleColumn7() + info.getDoubleColumn8();
    }

}