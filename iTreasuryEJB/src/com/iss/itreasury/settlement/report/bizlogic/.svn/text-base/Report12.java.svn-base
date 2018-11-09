/*
 * Created on 2005-11-16
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.settlement.report.bizlogic;

import java.sql.Timestamp;
import com.iss.itreasury.settlement.query.paraminfo.QueryFixedDepositInfo;
import com.iss.itreasury.settlement.report.dao.Report12Dao;
import com.iss.itreasury.settlement.report.dataentity.Report12ResultInfo;
import com.iss.itreasury.util.DataFormat;

/**
 * @author yinghu
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class Report12 {
    /**
     * 查询生成航天科工财务公司计算平台资金总表所需要的数据集
     * 对从数据库里取得的数据进行加工处理
     * @param date
     * @param qInfo
     * @return
     * @throws Exception
     */
    public Report12ResultInfo queryDataForFundTotalFormOfTreasuryCompany(Timestamp date,
            QueryFixedDepositInfo qInfo) throws Exception {
        Report12ResultInfo result = fetchData(date, qInfo);
        result.setTotalAsset(calcTotalAsset(result));
        result.setCashOfBank(calcCashOfBank(result));
        result.setCashOfBankInBeiJing(calcCashOfBankInBeiJing(result));
        result.setCashOfBankNotInBeiJing(result.getDepositOfGuiZhouIndustrialBank());
        result.setCashOfCapitalConstructionBank(calcCashOfCapitalConstructionBank(result));
        result.setCashOughtToBeReceived(calcCashOughtToBeReceived(result));
        result.setTotalDebt(calcTotalDebt(result));
        result.setShortTermDeposit(calcShortTermDeposit(result));
        result.setCashOughtToBePayed(calcCashOughtToBePayed(result));
        result.setTotalIncome(calcTotalIncome(result));
        result.setTotalInterestIncome(calcTotalInterestImcome(result));
        result.setTotalInterestPayOut(calcTotalInterestPayout(result));
        return result;
    }

    /**
     * 使用dao方法从数据库读取数据
     * @param date
     * @param qInfo
     * @return
     * @throws Exception
     */
    private Report12ResultInfo fetchData(Timestamp date,
            QueryFixedDepositInfo qInfo) throws Exception {
        Report12Dao dao = new Report12Dao();
        Report12ResultInfo result = new Report12ResultInfo();
        result.setDepositOfIndustrialBank(dao
                .queryBalanceBySubjectCodeAccurately(date, qInfo,
                        "10202010101,10202010102"));
        result
                .setDepositOfConstructBank(dao
                        .queryBalanceBySubjectCodeAccurately(date, qInfo,
                                "10202010201"));
        result
                .setDepositOfPeopleLiveBank(dao
                        .queryBalanceBySubjectCodeAccurately(date, qInfo,
                                "10202010401"));
        result
                .setDepositOfGuangDaXuanWuBank(dao
                        .queryBalanceBySubjectCodeAccurately(date, qInfo,
                                "10202010301"));
        result
                .setDepositOfGuangDaZhongGuancunBank(dao
                        .queryBalanceBySubjectCodeAccurately(date, qInfo,
                                "10202010302"));
        result
                .setDepositOfBeiJingZhiZhuBank(dao
                        .queryBalanceBySubjectCodeAccurately(date, qInfo,
                                "10202010502"));
        result
                .setDepositOfBeiJingYongDinluBank(dao
                        .queryBalanceBySubjectCodeAccurately(date, qInfo,
                                "10202010501"));
        result
                .setDepositOfAgricultureBank(dao
                        .queryBalanceBySubjectCodeAccurately(date, qInfo,
                                "10202010701"));
        result
                .setDepositOfGuiZhouIndustrialBank(dao
                        .queryBalanceBySubjectCodeAccurately(date, qInfo,
                                "10202010103"));
        result
                .setDepositOfConstructionCapitalConstructionBank(dao
                        .queryBalanceBySubjectCodeAccurately(date, qInfo,
                                "10202020101"));
        result
                .setDepositOfZhaoShangCapitalConstructionBank(dao
                        .queryBalanceBySubjectCodeAccurately(date, qInfo,
                                "10202020201"));
        result
                .setDepositOfGuiZhouCapitalConstructionBank(dao
                        .queryBalanceBySubjectCodeAccurately(date, qInfo,
                                "10202020801"));
        result.setPreparedFundOfPeopleBank(dao
                .queryBalanceBySubjectCodeAccurately(date, qInfo, "132"));
        result.setInnerLoan(dao.queryBalanceBySubjectCodeMistily(date, qInfo,
                "11202"));
        result.setCashOfTreasuryCompanyOughtToBeReceived(dao
                .queryBalanceBySubjectCodeAccurately(date, qInfo, "1050201"));
        result.setOtherCashOughtToBeReceived(dao
                .queryBalanceBySubjectCodeAccurately(date, qInfo, "1050202"));
        result.setConsignedLoan(dao.queryBalanceBySubjectCodeMistily(date,
                qInfo, "11602"));
        result.setInnerDeposit(queryBalanceBySubjectCodeArrayMistily(date,
                qInfo, new String[] { "21201", "21202", "21209", "21210" }));
        result.setInnerDepositOfBeiJingGradeTwoAccount(dao
                .queryBalanceBySubjectCodeMistily(date, qInfo, "21203"));
        result.setInnerDepositOfGuizhouGradeTwoAccount(dao
                .queryBalanceBySubjectCodeMistily(date, qInfo, "21204"));
        result.setNotifyDeposit(dao.queryBalanceBySubjectCodeMistily(date,
                qInfo, "21205"));
        result.setFixedDeposit(dao.queryFixedDeposit(date, qInfo, false));
        result.setLongTermDeposit(dao.queryFixedDeposit(date, qInfo, true));
        result.setCashOfTreasuryCompanyOughtToBePayed(dao
                .queryBalanceBySubjectCodeAccurately(date, qInfo, "2020201"));
        result.setOtherCashOughtToBePayed(dao
                .queryBalanceBySubjectCodeAccurately(date, qInfo, "2020202"));
        result.setConsignedDeposit(dao.queryBalanceBySubjectCodeMistily(date,
                qInfo, "21302"));
        result.setInnerLoanInterestIncome(dao
                .queryBalanceBySubjectCodeAccurately(date, qInfo, "501020101"));
        result.setBankDepositInterestIncome(dao
                .queryBalanceBySubjectCodeAccurately(date, qInfo, "501020201"));
        result.setGuiZhouBankDepositInterestIncome(dao
                .queryBalanceBySubjectCodeAccurately(date, qInfo, "501020202"));
        result.setInnerDepositInterestPayOut(dao
                .queryBalanceBySubjectCodeAccurately(date, qInfo, "521020101"));
        result.setGuiZhouInnerDepositInterestPayOut(dao
                .queryBalanceBySubjectCodeAccurately(date, qInfo, "521020102"));
        return result;
    }

    private double queryBalanceBySubjectCodeArrayMistily(Timestamp date,
            QueryFixedDepositInfo qInfo, String[] subjectArray)
            throws Exception {
        double result = 0.0;
        Report12Dao dao = new Report12Dao();
        for (int i = 0; i < subjectArray.length; i++) {
            result += dao.queryBalanceBySubjectCodeMistily(date, qInfo,
                    subjectArray[i]);
        }
        return result;
    }

    private double calcCashOfBankInBeiJing(
            Report12ResultInfo infoFrom) {
        return infoFrom.getDepositOfIndustrialBank()
                + infoFrom.getDepositOfConstructBank()
                + infoFrom.getDepositOfPeopleLiveBank()
                + infoFrom.getDepositOfGuangDaXuanWuBank()
                + infoFrom.getDepositOfGuangDaZhongGuancunBank()
                + infoFrom.getDepositOfBeiJingZhiZhuBank()
                + infoFrom.getDepositOfBeiJingYongDinluBank()
                + infoFrom.getDepositOfAgricultureBank();
    }

    private double calcCashOfCapitalConstructionBank(
            Report12ResultInfo infoFrom) {
        return infoFrom.getDepositOfConstructionCapitalConstructionBank()
                + infoFrom.getDepositOfZhaoShangCapitalConstructionBank()
                + infoFrom.getDepositOfGuiZhouCapitalConstructionBank();
    }

    private double calcCashOfBank(Report12ResultInfo infoFrom) {
        return calcCashOfBankInBeiJing(infoFrom)
                + infoFrom.getPreparedFundOfPeopleBank()
                + calcCashOfCapitalConstructionBank(infoFrom);
    }

    private double calcCashOughtToBeReceived(
            Report12ResultInfo infoFrom) {
        return infoFrom.getCashOfTreasuryCompanyOughtToBeReceived()
                + infoFrom.getOtherCashOughtToBeReceived();
    }

    private double calcTotalAsset(Report12ResultInfo infoFrom) {
        return calcCashOfBank(infoFrom)
                + infoFrom.getPreparedFundOfPeopleBank()
                + infoFrom.getInnerLoan() + calcCashOughtToBeReceived(infoFrom)
                + infoFrom.getConsignedLoan();
    }

    private double calcShortTermDeposit(Report12ResultInfo infoFrom) {
        return infoFrom.getInnerDeposit()
                + infoFrom.getInnerDepositOfBeiJingGradeTwoAccount()
                + infoFrom.getInnerDepositOfGuizhouGradeTwoAccount()
                + infoFrom.getNotifyDeposit() + infoFrom.getFixedDeposit();
    }

    private double calcCashOughtToBePayed(Report12ResultInfo infoFrom) {
        return infoFrom.getCashOfTreasuryCompanyOughtToBePayed()
                + infoFrom.getOtherCashOughtToBePayed();
    }

    private double calcTotalDebt(Report12ResultInfo infoFrom) {
        return calcShortTermDeposit(infoFrom) + infoFrom.getLongTermDeposit()
                + calcCashOughtToBePayed(infoFrom) + infoFrom.getConsignedDeposit();
    }

    private double calcTotalInterestImcome(
            Report12ResultInfo infoFrom) {
        return infoFrom.getInnerLoanInterestIncome()
                + infoFrom.getBankDepositInterestIncome()
                + infoFrom.getGuiZhouBankDepositInterestIncome()
                + infoFrom.getDismantleInterestIncome();
    }

    private double calcTotalInterestPayout(
            Report12ResultInfo infoFrom) {
        return infoFrom.getInnerDepositInterestPayOut()
                + infoFrom.getGuiZhouInnerDepositInterestPayOut();
    }

    private double calcTotalIncome(Report12ResultInfo infoFrom) {
        return calcTotalInterestImcome(infoFrom)
                - calcTotalInterestPayout(infoFrom);
    }

    public static void main(String[] args) {
        Report12 r12 = new Report12();
        QueryFixedDepositInfo qInfo = new QueryFixedDepositInfo();
        qInfo.setOfficeID(1);
        qInfo.setCurrencyID(1);
        Timestamp ts1 = DataFormat.getDateTime("2005-10-11");
        try {
            r12.queryDataForFundTotalFormOfTreasuryCompany(ts1,qInfo);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}