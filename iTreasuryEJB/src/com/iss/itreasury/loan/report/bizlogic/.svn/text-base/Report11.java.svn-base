/*
 * Created on 2005-12-08
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.loan.report.bizlogic;

import java.util.Collection;
import java.util.LinkedList;
import com.iss.itreasury.loan.contract.bizlogic.ContractOperation;
import com.iss.itreasury.loan.report.dao.Report11Dao;
import com.iss.itreasury.loan.report.dataentity.ReportResultInfo;
import com.iss.itreasury.loan.report.dataentity.ReportWhereInfo;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.util.DataFormat;

/**
 * @author yinghu
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class Report11 {

    /**
     * 查询财务公司高风险贷款明细表
     * 
     * @param wInfo
     * @return
     * @throws Exception
     */
    public Collection queryTreasuryCompanyHignRiskLoanDetail(
            ReportWhereInfo wInfo) throws Exception {
        //wInfo.setTypeId3(LOANConstant.IndustryType2.PLATFORM);
    	wInfo.setTypeId3(1);
        LinkedList listOfPlatform = handleDataForOneLoanType(wInfo,
                new String[] { "一", "平台贷款" });
//        wInfo.setTypeId3(LOANConstant.IndustryType2.LOCAL);
        wInfo.setTypeId3(2);
        LinkedList listOfLocal = handleDataForOneLoanType(wInfo, new String[] {
                "二", "本部贷款" });
        LinkedList result = new LinkedList();
        result.addAll(listOfPlatform);
        result.addAll(listOfLocal);
        if (result.size() > 0) {
            result.add(buildGatherInfoForAll(listOfPlatform, listOfLocal));
        }
        return result;
    }

    private LinkedList handleDataForOneLoanType(ReportWhereInfo wInfo,
            String[] messages) throws Exception {
        LinkedList result = new LinkedList();
        ReportResultInfo[] infos = (new Report11Dao())
                .queryTreasuryCompanyHignRiskLoanDetailOfAssignedTypeId3(wInfo);
        if (infos != null && infos.length > 0) {
            for (int i = 0; i < infos.length; i++) {
                result.add(buildCommonInfo(infos[i], wInfo));
            }
            result.addFirst(buildGatherInfoForOneType(result, messages));
        }
        return result;
    }

    private ReportResultInfo buildCommonInfo(ReportResultInfo info,
            ReportWhereInfo wInfo) throws Exception {
        ContractOperation cOp = new ContractOperation();
        ReportResultInfo result = new ReportResultInfo();
        result.setStringColumn1(info.getStringColumn1());
        result.setStringColumn2(info.getStringColumn2());
        result.setDoubleColumn1(cOp.getLateAmount(info.getLongColumn1())
                .getBalanceAmount());
        result.setLongColumn1(info.getLongColumn2());
        result.setTsColumn1(info.getTsColumn1());
        result.setTsColumn2(info.getTsColumn2());
        result.setTsColumn3(info.getTsColumn3());
        //展期次数不能为负数
        if (info.getLongColumn3() > 0) {
            result.setLongColumn2(info.getLongColumn3());
        } else {
            result.setLongColumn2(0);
        }
        result.setDoubleColumn2(cOp.getLateAmount(info.getLongColumn1())
                .getInterestAmount());
        //如果展期到期日为空，就取查询日期和贷款到期日的差
        //否则取查询日期和展期日期的差
        if (info.getTsColumn3() == null) {
            result.setLongColumn3(DataFormat.getTime(info.getTsColumn2(), wInfo
                    .getDate()));
        } else {
            result.setLongColumn3(DataFormat.getTime(info.getTsColumn3(), wInfo
                    .getDate()));
        }
        //逾期天数不能为负数
        if (result.getLongColumn3() < 0) {
            result.setLongColumn3(0);
        }
        result.setStringColumn3(LOANConstant.IndustryType1.getName(info
                .getLongColumn4()));
        result.setStringColumn4(LOANConstant.AreaType.getName(info
                .getLongColumn5()));
        result.setStringColumn5(makeUpAssureStyleString(info));
        CommonBiz.devideByTenThousandsForAllDoubleColumn(result);
        return result;
    }

    private String makeUpAssureStyleString(ReportResultInfo info) {
        StringBuffer result = new StringBuffer();
        if (info.getLongColumn6() == 1) {
            result.append(LOANConstant.AssureType
                    .getName(LOANConstant.AssureType.CREDIT)
                    + " ");
        } else {
            result.append(" ");
        }
        if (info.getLongColumn7() == 1) {
            result.append(LOANConstant.AssureType
                    .getName(LOANConstant.AssureType.ASSURE)
                    + " ");
        } else {
            result.append(" ");
        }
        if (info.getLongColumn8() == 1) {
            result.append(LOANConstant.AssureType
                    .getName(LOANConstant.AssureType.PLEDGE)
                    + " ");
        } else {
            result.append(" ");
        }
        if (info.getLongColumn9() == 1) {
            result.append(LOANConstant.AssureType
                    .getName(LOANConstant.AssureType.IMPAWN)
                    + " ");
        } else {
            result.append(" ");
        }
        if (info.getLongColumn10() == 1) {
            result.append(LOANConstant.AssureType
                    .getName(LOANConstant.AssureType.RECOGNIZANCE)
                    + " ");
        } else {
            result.append(" ");
        }
        return result.toString().trim();
    }

    private ReportResultInfo buildGatherInfoForOneType(LinkedList listOfInfos,
            String[] messages) throws Exception {
        ReportResultInfo result = CommonBiz.calcTotal(listOfInfos.toArray());
        result.setStringColumn1(messages[0]);
        result.setStringColumn2(messages[1]);
        //设置汇总标志
        result.setLongColumn5(1);
        return result;
    }

    private ReportResultInfo buildGatherInfoForAll(LinkedList listOfPlatform,
            LinkedList listOfLocal) {
        ReportResultInfo result = new ReportResultInfo();
        ReportResultInfo gatherInfoOfPlatForm = new ReportResultInfo();
        if (listOfPlatform.size() > 0) {
            gatherInfoOfPlatForm = (ReportResultInfo) listOfPlatform.get(0);
        }
        ReportResultInfo gatherInfoOfLocal = new ReportResultInfo();
        if (listOfLocal.size() > 0) {
            gatherInfoOfLocal = (ReportResultInfo) listOfLocal.get(0);
        }
        result.setDoubleColumn1(gatherInfoOfPlatForm.getDoubleColumn1()
                + gatherInfoOfLocal.getDoubleColumn1());
        result.setDoubleColumn2(gatherInfoOfPlatForm.getDoubleColumn2()
                + gatherInfoOfLocal.getDoubleColumn2());
        result.setStringColumn1("总计");
        //设置汇总标志
        result.setLongColumn5(1);
        return result;
    }
}