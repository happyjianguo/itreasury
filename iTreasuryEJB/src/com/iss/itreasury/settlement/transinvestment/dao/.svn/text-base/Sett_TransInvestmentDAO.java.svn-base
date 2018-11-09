/*
 * Created on 2004-11-4
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.settlement.transinvestment.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;

import com.iss.itreasury.dao.SettlementDAO;
import com.iss.itreasury.settlement.transinvestment.dataentity.TransInvestmentInfo;
import com.iss.itreasury.settlement.transinvestment.dataentity.QueryTransInvestmentInfo;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Log;
import com.iss.itreasury.util.Log4j;
import com.iss.itreasury.util.DataFormat;

/**
 * @author ygzhao
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class Sett_TransInvestmentDAO extends SettlementDAO
{
    protected Log4j log4j = new Log4j(Constant.ModuleType.SETTLEMENT, this);

    public Sett_TransInvestmentDAO()
    {
        super("sett_TransInvestment");
        setUseMaxID();
    }

    /**
     * 根据标识查询投融资业务的方法：
     * 
     * @param lID
     *            long , 投融资业务的ID
     * @return TransInvestmentInfo, 投融资业务实体类
     * @throws Exception
     */
    public TransInvestmentInfo findByID(long lID) throws Exception
    {
        TransInvestmentInfo info = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rset = null;

        StringBuffer buffer = new StringBuffer();
        buffer.append("SELECT * FROM sett_TransInvestment \n");
        buffer.append("\n WHERE id = " + lID);

        try
        {
            conn = this.getConnection();
            log.info(buffer.toString());
            pstmt = conn.prepareStatement(buffer.toString());
            rset = pstmt.executeQuery();
            if (rset.next())
            {
                info = getInfoFromRS(rset);
            }
        } finally
        {
            this.cleanup(rset);
            this.cleanup(pstmt);
            this.cleanup(conn);
        }       
        return info;
    }

    public Collection findByCondition(QueryTransInvestmentInfo condition)
            throws Exception
    {
        long lReturn = -1;

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rset = null;
        ArrayList list = new ArrayList();

        //        
        String sSerialNo = condition.getSSerialNo(); //序号（流水）（由）
        String sSerialNo2 = condition.getSSerialNo2();//序号（流水）（至）
        String C_sTransNo = condition.getSTransNo(); //交易号
        String C_sTransNo2 = condition.getSTransNo2();
        long nTransactoinTypeID = condition.getNTransactoinTypeID();//交易类型
        long nTransInvestmentTypeID = condition.getNTransInvestmentTypeID(); //台账类型
        String Z_sContractNo = condition.getSContractNo(); //转贴现合同编号
        String Z_sContractNo2 = condition.getSContractNo2();
        String sCounterPartNo = condition.getSCounterPartNo(); //交易对手
        String Z_sDiscountBillNo = condition.getSDiscountBillNo(); //汇票号码
        String Z_sDiscountBillNo2 = condition.getSDiscountBillNo2();
        String Z_sBillAcceptanceUser = condition.getSBillAcceptanceUser(); //汇票承兑人
        String Z_sAcceptanceUserBank = condition.getSAcceptanceUserBank(); //承兑人开户银行
        String Z_sAcceptanceUserAccount = condition.getSAcceptanceUserAccount(); //承兑人账号
        long Z_nDiscountBillTypeID = condition.getNDiscountBillTypeID(); //汇票类型（银票/商票）
        long Z_nDiscountType1 = condition.getNDiscountType1(); //买入/卖出
        long Z_nDiscountType2 = condition.getNDiscountType2(); //买断/回购
        Timestamp Z_dtDiscountDate = condition.getDtDiscountDate(); //转贴现日期
        Timestamp Z_dtDiscountDate2 = condition.getDtDiscountDate2();
        double Z_nMonthInterest = condition.getNMonthInterest(); //转贴现利率（月）
        double Z_nMonthInterest2 = condition.getNMonthInterest2();
        Timestamp Z_dtBuyEndDate = condition.getDtBuyEndDate(); //回购到期日
        Timestamp Z_dtBuyEndDate2 = condition.getDtBuyEndDate2();
        Timestamp Z_dtBillEndDate = condition.getDtBillEndDate(); //票据到期日
        Timestamp Z_dtBillEndDate2 = condition.getDtBillEndDate2();
        long Z_nDiscountDays = condition.getNDiscountDays(); //转贴天数
        long Z_nDiscountDays2 = condition.getNDiscountDays2();
        double Z_mBillAmount = condition.getMBillAmount(); //票面金额
        double Z_mBillAmount2 = condition.getMBillAmount2();
        double Z_mDiscountPayAmount = condition.getMDiscountPayAmount(); //转贴实际收取/支付金额
        double Z_mDiscountPayAmount2 = condition.getMDiscountPayAmount2();
        String Z_sAccountBankNo = condition.getSAccountBankNo(); //开户银行
        String Z_sAccountNo = condition.getSAccountNo(); //账号
        long Z_nIsRepurchase = condition.getNIsRepurchase(); //是否已经回购
        long Z_nIsRepayment = condition.getNIsRepayment(); //是否已经到期收回
        long C_nborrowTypeID = condition.getNborrowTypeID(); //拆借类别
        Timestamp C_dtBorrowDate = condition.getDtBorrowDate(); //拆入/拆出时间
        Timestamp C_dtBorrowDate2 = condition.getDtBorrowDate2();
        double C_mBorrowAmount = condition.getMBorrowAmount(); //拆入/拆出金额
        double C_mBorrowAmount2 = condition.getMBorrowAmount2();
        Timestamp C_dtPayDate = condition.getDtPayDate(); //返款时间
        Timestamp C_dtPayDate2 = condition.getDtPayDate2();
        long nInterestDays = condition.getNInterestDays(); //天数
        long nInterestDays2 = condition.getNInterestDays2();
        double mInterestRate = condition.getMInterestRate(); //利率 ----------
        double mInterestRate2 = condition.getMInterestRate2();
        double mInterestAmount = condition.getMInterestAmount(); //利息额
        double mInterestAmount2 = condition.getMInterestAmount2();
        double C_mPayAmount = condition.getMPayAmountCJ(); //返款金额
        double C_mPayAmount2 = condition.getMPayAmountCJ2();
        long C_nIsPaid = condition.getNIsPaid(); //是否已经返款
        String Q_sDealNo = condition.getSDealNo(); //成交单编号
        String Q_sDealNo2 = condition.getSDealNo2();
        String Q_sProductType = condition.getSProductType(); //产品品种
        String Q_sProductName = condition.getSProductName(); //产品名称
        Timestamp Q_dtDealDate = condition.getDtDealDate(); //成交日期
        Timestamp Q_dtDealDate2 = condition.getDtDealDate2();
        double Q_mPayAmount = condition.getMPayAmountQ(); //实际收付金额
        double Q_mPayAmount2 = condition.getMPayAmountQ2();
        long JQ_nBondValue = condition.getNBondValue(); //债券现值
        long JQ_nBondValue2 = condition.getNBondValue2();
        long JQ_nBondQuantity = condition.getNBondQuantity(); //债券数量
        long JQ_nBondQuantity2 = condition.getNBondQuantity2();
        Timestamp JQ_dtInterestStart = condition.getDtInterestStart(); //回购起息日
        Timestamp JQ_dtInterestStart2 = condition.getDtInterestStart2();
        Timestamp JQ_dtInterestEnd = condition.getDtInterestEnd(); //回购到期日
        Timestamp JQ_dtInterestEnd2 = condition.getDtInterestEnd2();
        long JQ_nBuyDays = condition.getNBuyDaysJQ(); //回购天数
        long JQ_nBuyDays2 = condition.getNBuyDaysJQ2();
        double JQ_nBuyAmount = condition.getNBuyAmount(); //回购金额
        double JQ_nBuyAmount2 = condition.getNBuyAmount2();
        String sOperator = condition.getSOperator(); //负责人
        long JQ_bBought = condition.getBBought(); //是否已经购回
        long XQ_nProductValue = condition.getNProductValue(); //产品现值
        long XQ_nProductValue2 = condition.getNProductValue2();
        long XQ_nProductQuantity = condition.getNProductQuantity(); //产品数量
        long XQ_nProductQuantity2 = condition.getNProductQuantity2();
        long JXQ_nShareHolderAccountID = condition.getNShareHolderAccountID(); //股东账户编码
        long JXQ_nFundAccountID = condition.getNFundAccountID(); //资金账户编码
        String D_sBuyContract = condition.getSBuyContract(); //回购合同编号
        String D_sBuyContract2 = condition.getSBuyContract2();
        String D_sLoanPart = condition.getSLoanPart(); //原合同贷款单位
        String D_sLoanPart2 = condition.getSLoanPart2();
        Timestamp D_dtLoanStartDate = condition.getDtLoanStartDate(); //原合同借款日期
        Timestamp D_dtLoanStartDate2 = condition.getDtLoanStartDate2();
        Timestamp D_dtLoanEndDate = condition.getDtLoanEndDate(); //原合同借款到期日
        Timestamp D_dtLoanEndDate2 = condition.getDtLoanEndDate2();        
        double D_mLoanAmount = condition.getMLoanAmount(); //原合同借款金额
        double D_mLoanAmount2 = condition.getMLoanAmount2();
        double D_mLoanBlance = condition.getMLoanBlance(); //原合同贷款余额
        double D_mLoanBlance2 = condition.getMLoanBlance();
        Timestamp D_dtLoanBlanceEndDate = condition.getDtLoanBlanceEndDate(); //原合同贷款余额到期日
        Timestamp D_dtLoanBlanceEndDate2 = condition.getDtLoanBlanceEndDate2();
        Timestamp D_dtRepurchaseDate = condition.getDtRepurchaseDate(); //回购日
        Timestamp D_dtRepurchaseDate2 = condition.getDtRepurchaseDate2();
        Timestamp D_dtBuyDate = condition.getDtBuyDate(); //购回日
        Timestamp D_dtBuyDate2 = condition.getDtBuyDate2();        
        long D_nBuyDays = condition.getNBuyDaysD(); //回购天数
        long D_nBuyDays2 = condition.getNBuyDaysD2();
        double D_mBuyAmount = condition.getMBuyAmount(); //购回金额
        double D_mBuyAmount2 = condition.getMBuyAmount2();
        Timestamp dtInputDate = condition.getDtInputDate(); //录入日期
        Timestamp dtInputDate2 = condition.getDtInputDate2();

        long nStatusID = -1; //	Number 状态

        StringBuffer buffer = new StringBuffer();
        buffer.append("SELECT * FROM sett_TransInvestment \n");
        buffer.append(" WHERE 1=1 ");

        if (!"".equals(sSerialNo))
        {
            buffer.append("\n AND sSerialNo >= '" + sSerialNo + "' ");
        }
        if (!"".equals(sSerialNo2))
        {
            buffer.append("\n AND sSerialNo <= '" + sSerialNo2 + "' ");
        }
        if (!"".equals(C_sTransNo))
        {
            buffer.append("\n AND C_sTransNo >= '" + C_sTransNo + "' ");
        }
        if (!"".equals(C_sTransNo2))
        {
            buffer.append("\n AND C_sTransNo <= '" + C_sTransNo2 + "' ");
        }
        if (nTransactoinTypeID > 0)
        {
            buffer.append("\n AND nTransactoinTypeID = " + nTransactoinTypeID);
        }
        if (nTransInvestmentTypeID > 0)
        {
            buffer.append("\n AND nTransInvestmentTypeID = "
                    + nTransInvestmentTypeID);
        }
        if (!"".equals(Z_sContractNo))
        {
            buffer.append("\n AND Z_sContractNo >= '" + Z_sContractNo + "' ");
        }
        if (!"".equals(Z_sContractNo2))
        {
            buffer.append("\n AND Z_sContractNo <= '" + Z_sContractNo2 + "' ");
        }
        if (!"".equals(sCounterPartNo))
        {
            buffer.append("\n AND sCounterPartNo like '" + sCounterPartNo
                    + "' ");
        }
        if (!"".equals(Z_sDiscountBillNo))
        {
            buffer.append("\n AND Z_sDiscountBillNo >= '" + Z_sDiscountBillNo
                    + "' ");
        }
        if (!"".equals(Z_sDiscountBillNo2))
        {
            buffer.append("\n AND Z_sDiscountBillNo <= '" + Z_sDiscountBillNo2
                    + "' ");
        }
        if (!"".equals(Z_sBillAcceptanceUser))
        {
            buffer.append("\n AND Z_sBillAcceptanceUser like '"
                    + Z_sBillAcceptanceUser + "' ");
        }
        if (!"".equals(Z_sAcceptanceUserBank))
        {
            buffer.append("\n AND Z_sAcceptanceUserBank like '"
                    + Z_sAcceptanceUserBank + "' ");
        }
        if (!"".equals(Z_sAcceptanceUserAccount))
        {
            buffer.append("\n AND Z_sAcceptanceUserAccount like '"
                    + Z_sAcceptanceUserAccount + "' ");
        }
        if (Z_nDiscountBillTypeID > 0)
        {
            buffer.append("\n AND Z_nDiscountBillTypeID = "
                    + Z_nDiscountBillTypeID);
        }
        if (Z_nDiscountType1 > 0)
        {
            buffer.append("\n AND Z_nDiscountType1 = " + Z_nDiscountType1);
        }
        if (Z_nDiscountType2 > 0)
        {
            buffer.append("\n AND Z_nDiscountType2 = " + Z_nDiscountType2);
        }
        if (Z_dtDiscountDate != null)
        {
            buffer.append("\n AND Z_dtDiscountDate >= to_date('"
                    + DataFormat.getDateString(Z_dtDiscountDate)
                    + "','yyyy-mm-dd')");
        }
        if (Z_dtDiscountDate2 != null)
        {
            buffer.append("\n AND Z_dtDiscountDate <= to_date('"
                    + DataFormat.getDateString(Z_dtDiscountDate2)
                    + "','yyyy-mm-dd')");
        }
        if (Z_nMonthInterest != 0.0)
        {
            buffer.append("\n AND Z_nMonthInterest >= " + Z_nMonthInterest);
        }
        if (Z_nMonthInterest2 != 0.0)
        {
            buffer.append("\n AND Z_nMonthInterest <= " + Z_nMonthInterest2);
        }
        if (Z_dtBuyEndDate != null)
        {
            buffer.append("\n AND Z_dtBuyEndDate >= to_date('"
                    + DataFormat.getDateString(Z_dtBuyEndDate)
                    + "','yyyy-mm-dd')");
        }
        if (Z_dtBuyEndDate2 != null)
        {
            buffer.append("\n AND Z_dtBuyEndDate <= to_date('"
                    + DataFormat.getDateString(Z_dtBuyEndDate2)
                    + "','yyyy-mm-dd')");
        }
        if (Z_dtBillEndDate != null)
        {
            buffer.append("\n AND Z_dtBillEndDate >= to_date('"
                    + DataFormat.getDateString(Z_dtBillEndDate)
                    + "','yyyy-mm-dd')");
        }
        if (Z_dtBillEndDate2 != null)
        {
            buffer.append("\n AND Z_dtBillEndDate <= to_date('"
                    + DataFormat.getDateString(Z_dtBillEndDate2)
                    + "','yyyy-mm-dd')");
        }
        if (Z_nDiscountDays > 0)
        {
            buffer.append("\n AND Z_nDiscountDays >= " + Z_nDiscountDays);
        }
        if (Z_nDiscountDays2 > 0)
        {
            buffer.append("\n AND Z_nDiscountDays <= " + Z_nDiscountDays2);
        }
        if (Z_mBillAmount != 0.0)
        {
            buffer.append("\n AND Z_mBillAmount >= " + Z_mBillAmount);
        }
        if (Z_mBillAmount2 != 0.0)
        {
            buffer.append("\n AND Z_mBillAmount <= " + Z_mBillAmount2);
        }
        if (Z_mDiscountPayAmount != 0.0)
        {
            buffer.append("\n AND Z_mDiscountPayAmount >= "
                    + Z_mDiscountPayAmount);
        }
        if (Z_mDiscountPayAmount2 != 0.0)
        {
            buffer.append("\n AND Z_mDiscountPayAmount <= "
                    + Z_mDiscountPayAmount2);
        }
        if (!"".equals(Z_sAccountBankNo))
        {
            buffer.append("\n AND Z_sAccountBankNo like '" + Z_sAccountBankNo
                    + "' ");
        }
        if (!"".equals(Z_sAccountNo))
        {
            buffer.append("\n AND Z_sAccountNo like '" + Z_sAccountNo + "' ");
        }
        if (Z_nIsRepurchase > 0)
        {
            buffer.append("\n AND Z_nIsRepurchase = " + Z_nIsRepurchase);
        }
        if (Z_nIsRepayment > 0)
        {
            buffer.append("\n AND Z_nIsRepayment = " + Z_nIsRepayment);
        }
        if (C_nborrowTypeID > 0)
        {
            buffer.append("\n AND C_nborrowTypeID = " + C_nborrowTypeID);
        }
        if (C_dtBorrowDate != null)
        {
            buffer.append("\n AND C_dtBorrowDate >= to_date('"
                    + DataFormat.getDateString(C_dtBorrowDate)
                    + "','yyyy-mm-dd')");
        }
        if (C_dtBorrowDate2 != null)
        {
            buffer.append("\n AND C_dtBorrowDate <= to_date('"
                    + DataFormat.getDateString(C_dtBorrowDate2)
                    + "','yyyy-mm-dd')");
        }
        if (C_mBorrowAmount != 0.0)
        {
            buffer.append("\n AND C_mBorrowAmount >= " + C_mBorrowAmount);
        }
        if (C_mBorrowAmount2 != 0.0)
        {
            buffer.append("\n AND C_mBorrowAmount <= " + C_mBorrowAmount2);
        }
        if (C_dtPayDate != null)
        {
            buffer
                    .append("\n AND C_dtPayDate >= to_date('"
                            + DataFormat.getDateString(C_dtPayDate)
                            + "','yyyy-mm-dd')");
        }
        if (C_dtPayDate2 != null)
        {
            buffer.append("\n AND C_dtPayDate <= to_date('"
                    + DataFormat.getDateString(C_dtPayDate2)
                    + "','yyyy-mm-dd')");
        }
        if (nInterestDays > 0)
        {
            buffer.append("\n AND nInterestDays >= " + nInterestDays);
        }
        if (nInterestDays2 > 0)
        {
            buffer.append("\n AND nInterestDays <= " + nInterestDays2);
        }
        if (mInterestRate != 0.0)
        {
            buffer.append("\n AND mInterestRate >= " + mInterestRate);
        }
        if (mInterestRate2 != 0.0)
        {
            buffer.append("\n AND mInterestRate <= " + mInterestRate2);
        }
        if (mInterestAmount != 0.0)
        {
            buffer.append("\n AND mInterestAmount >= " + mInterestAmount);
        }
        if (mInterestAmount2 != 0.0)
        {
            buffer.append("\n AND mInterestAmount <= " + mInterestAmount2);
        }
        if (C_mPayAmount != 0.0)
        {
            buffer.append("\n AND C_mPayAmount >= " + C_mPayAmount);
        }
        if (C_mPayAmount2 != 0.0)
        {
            buffer.append("\n AND C_mPayAmount <= " + C_mPayAmount2);
        }
        if (C_nIsPaid > 0)
        {
            buffer.append("\n AND C_nIsPaid = " + C_nIsPaid);
        }
        if (!"".equals(Q_sDealNo))
        {
            buffer.append("\n AND Q_sDealNo >= '" + Q_sDealNo + "' ");
        }
        if (!"".equals(Q_sDealNo2))
        {
            buffer.append("\n AND Q_sDealNo <= '" + Q_sDealNo2 + "' ");
        }
        if (!"".equals(Q_sProductType))
        {
            buffer.append("\n AND Q_sProductType like '" + Q_sProductType
                    + "' ");
        }
        if (!"".equals(Q_sProductName))
        {
            buffer.append("\n AND Q_sProductName like '" + Q_sProductName
                    + "' ");
        }
        if (Q_dtDealDate != null)
        {
            buffer.append("\n AND Q_dtDealDate >= to_date('"
                    + DataFormat.getDateString(Q_dtDealDate)
                    + "','yyyy-mm-dd')");
        }
        if (Q_dtDealDate2 != null)
        {
            buffer.append("\n AND Q_dtDealDate <= to_date('"
                    + DataFormat.getDateString(Q_dtDealDate2)
                    + "','yyyy-mm-dd')");
        }
        if (Q_mPayAmount != 0.0)
        {
            buffer.append("\n AND Q_mPayAmount >= " + Q_mPayAmount);
        }
        if (Q_mPayAmount2 != 0.0)
        {
            buffer.append("\n AND Q_mPayAmount <= " + Q_mPayAmount2);
        }
        if (JQ_nBondValue > 0)
        {
            buffer.append("\n AND JQ_nBondValue >= " + JQ_nBondValue);
        }
        if (JQ_nBondValue2 > 0)
        {
            buffer.append("\n AND JQ_nBondValue <= " + JQ_nBondValue2);
        }
        if (JQ_nBondQuantity > 0)
        {
            buffer.append("\n AND JQ_nBondQuantity >= " + JQ_nBondQuantity);
        }
        if (JQ_nBondQuantity2 > 0)
        {
            buffer.append("\n AND JQ_nBondQuantity <= " + JQ_nBondQuantity2);
        }
        if (JQ_dtInterestStart != null)
        {
            buffer.append("\n AND JQ_dtInterestStart >= to_date('"
                    + DataFormat.getDateString(JQ_dtInterestStart)
                    + "','yyyy-mm-dd')");
        }
        if (JQ_dtInterestStart2 != null)
        {
            buffer.append("\n AND JQ_dtInterestStart <= to_date('"
                    + DataFormat.getDateString(JQ_dtInterestStart2)
                    + "','yyyy-mm-dd')");
        }
        if (JQ_dtInterestEnd != null)
        {
            buffer.append("\n AND JQ_dtInterestEnd >= to_date('"
                    + DataFormat.getDateString(JQ_dtInterestEnd)
                    + "','yyyy-mm-dd')");
        }
        if (JQ_dtInterestEnd2 != null)
        {
            buffer.append("\n AND JQ_dtInterestEnd <= to_date('"
                    + DataFormat.getDateString(JQ_dtInterestEnd2)
                    + "','yyyy-mm-dd')");
        }
        if (JQ_nBuyDays > 0)
        {
            buffer.append("\n AND JQ_nBuyDays >= " + JQ_nBuyDays);
        }
        if (JQ_nBuyDays2 > 0)
        {
            buffer.append("\n AND JQ_nBuyDays <= " + JQ_nBuyDays2);
        }
        if (JQ_nBuyAmount != 0.0)
        {
            buffer.append("\n AND JQ_nBuyAmount >= " + JQ_nBuyAmount);
        }
        if (JQ_nBuyAmount2 != 0.0)
        {
            buffer.append("\n AND JQ_nBuyAmount <= " + JQ_nBuyAmount2);
        }
        if (!"".equals(sOperator))
        {
            buffer.append("\n AND sOperator like '" + sOperator + "' ");
        }
        if (JQ_bBought > 0)
        {
            buffer.append("\n AND JQ_bBought = " + JQ_bBought);
        }
        if (XQ_nProductValue > 0)
        {
            buffer.append("\n AND XQ_nProductValue >= " + XQ_nProductValue);
        }
        if (XQ_nProductValue2 > 0)
        {
            buffer.append("\n AND XQ_nProductValue <= " + XQ_nProductValue2);
        }
        if (XQ_nProductQuantity > 0)
        {
            buffer.append("\n AND XQ_nProductQuantity >= "
                    + XQ_nProductQuantity);
        }
        if (XQ_nProductQuantity2 > 0)
        {
            buffer.append("\n AND XQ_nProductQuantity <= "
                    + XQ_nProductQuantity2);
        }
        if (JXQ_nShareHolderAccountID > 0)
        {
            buffer.append("\n AND JXQ_nShareHolderAccountID = "
                    + JXQ_nShareHolderAccountID);
        }
        if (JXQ_nFundAccountID > 0)
        {
            buffer.append("\n AND JXQ_nFundAccountID = " + JXQ_nFundAccountID);
        }
        if (!"".equals(D_sBuyContract))
        {
            buffer.append("\n AND D_sBuyContract >= '" + D_sBuyContract + "' ");
        }
        if (!"".equals(D_sBuyContract2))
        {
            buffer.append("\n AND D_sBuyContract <= '" + D_sBuyContract2 + "' ");
        }
        if (!"".equals(D_sLoanPart))
        {
            buffer.append("\n AND D_sLoanPart >= '" + D_sLoanPart + "' ");
        }
        if (!"".equals(D_sLoanPart2))
        {
            buffer.append("\n AND D_sLoanPart <= '" + D_sLoanPart2 + "' ");
        }
        if (D_dtLoanStartDate != null)
        {
            buffer.append("\n AND D_dtLoanStartDate >= to_date('"
                    + DataFormat.getDateString(D_dtLoanStartDate)
                    + "','yyyy-mm-dd')");
        }
        if (D_dtLoanStartDate2 != null)
        {
            buffer.append("\n AND D_dtLoanStartDate <= to_date('"
                    + DataFormat.getDateString(D_dtLoanStartDate2)
                    + "','yyyy-mm-dd')");
        }
        if (D_dtLoanEndDate != null)
        {
            buffer.append("\n AND D_dtLoanEndDate >= to_date('"
                    + DataFormat.getDateString(D_dtLoanEndDate)
                    + "','yyyy-mm-dd')");
        }
        if (D_dtLoanEndDate2 != null)
        {
            buffer.append("\n AND D_dtLoanEndDate <= to_date('"
                    + DataFormat.getDateString(D_dtLoanEndDate2)
                    + "','yyyy-mm-dd')");
        }
        if (D_mLoanAmount != 0.0)
        {
            buffer.append("\n AND D_mLoanAmount >= " + D_mLoanAmount);
        }
        if (D_mLoanAmount2 != 0.0)
        {
            buffer.append("\n AND D_mLoanAmount <= " + D_mLoanAmount2);
        }
        if (D_mLoanBlance != 0.0)
        {
            buffer.append("\n AND D_mLoanBlance >= " + D_mLoanBlance);
        }
        if (D_mLoanBlance2 != 0.0)
        {
            buffer.append("\n AND D_mLoanBlance <= " + D_mLoanBlance2);
        }
        if (D_dtLoanBlanceEndDate != null)
        {
            buffer.append("\n AND D_dtLoanBlanceEndDate >= to_date('"
                    + DataFormat.getDateString(D_dtLoanBlanceEndDate)
                    + "','yyyy-mm-dd')");
        }
        if (D_dtLoanBlanceEndDate2 != null)
        {
            buffer.append("\n AND D_dtLoanBlanceEndDate <= to_date('"
                    + DataFormat.getDateString(D_dtLoanBlanceEndDate2)
                    + "','yyyy-mm-dd')");
        }
        if (D_dtRepurchaseDate != null)
        {
            buffer.append("\n AND D_dtRepurchaseDate >= to_date('"
                    + DataFormat.getDateString(D_dtRepurchaseDate)
                    + "','yyyy-mm-dd')");
        }
        if (D_dtRepurchaseDate2 != null)
        {
            buffer.append("\n AND D_dtRepurchaseDate <= to_date('"
                    + DataFormat.getDateString(D_dtRepurchaseDate2)
                    + "','yyyy-mm-dd')");
        }
        if (D_dtBuyDate != null)
        {
            buffer
                    .append("\n AND D_dtBuyDate >= to_date('"
                            + DataFormat.getDateString(D_dtBuyDate)
                            + "','yyyy-mm-dd')");
        }
        if (D_dtBuyDate2 != null)
        {
            buffer.append("\n AND D_dtBuyDate <= to_date('"
                    + DataFormat.getDateString(D_dtBuyDate2)
                    + "','yyyy-mm-dd')");
        }
        if (D_nBuyDays > 0)
        {
            buffer.append("\n AND D_nBuyDays >= " + D_nBuyDays);
        }
        if (D_nBuyDays2 > 0)
        {
            buffer.append("\n AND D_nBuyDays <= " + D_nBuyDays2);
        }
        if (D_mBuyAmount != 0.0)
        {
            buffer.append("\n AND D_mBuyAmount >= " + D_mBuyAmount);
        }
        if (D_mBuyAmount2 != 0.0)
        {
            buffer.append("\n AND D_mBuyAmount <= " + D_mBuyAmount2);
        }
        if (dtInputDate != null)
        {
            buffer
                    .append("\n AND dtInputDate >= to_date('"
                            + DataFormat.getDateString(dtInputDate)
                            + "','yyyy-mm-dd')");
        }
        if (dtInputDate2 != null)
        {
            buffer.append("\n AND dtInputDate <= to_date('"
                    + DataFormat.getDateString(dtInputDate2)
                    + "','yyyy-mm-dd')");
        }

        buffer.append("\n AND nStatusID != " + Constant.RecordStatus.INVALID);
        buffer.append("\n AND NOFFICEID = " + condition.getOfficeID());
        buffer.append("\n AND NCURRENCYID = " + condition.getCurrencyID());

        try
        {
            conn = this.getConnection();
            log.info("findByCondition = \n " + buffer.toString());
            pstmt = conn.prepareStatement(buffer.toString());

            rset = pstmt.executeQuery();

            while (rset.next())
            {
                TransInvestmentInfo tmp = getInfoFromRS(rset);
                list.add(tmp);
            }
        } finally
        {
            this.cleanup(rset);
            this.cleanup(pstmt);
            this.cleanup(conn);

        }
        return list;
    }

    /**
     * 方法说明：台账记录
     * 
     * @param id
     * @return : long - 返回被删除台账记录ID
     * @throws IException
     */
    public long delete(long id) throws Exception
    {
        //logic delete, so just update status as deleted
        long lReturn = -1;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try
        {
            con = getConnection();
            StringBuffer strSQLBuffer = new StringBuffer();
            strSQLBuffer.append("UPDATE sett_TransInvestment\n");
            strSQLBuffer.append(" SET \n");
            strSQLBuffer.append("nStatusID = ? \n");
            strSQLBuffer.append(" WHERE ID = ? \n");
            String strSQL = strSQLBuffer.toString();
            ps = con.prepareStatement(strSQL);
            log.info(strSQL);
            ps.setLong(1, Constant.RecordStatus.INVALID);
            ps.setLong(2, id);
            ps.executeUpdate();
        } finally
        {
            cleanup(ps);
            cleanup(con);
        }
        return id;
    }

    /**
     * @param
     * @return
     * @throws
     */
    private TransInvestmentInfo getInfoFromRS(ResultSet rset) throws Exception
    {
        TransInvestmentInfo temp = new TransInvestmentInfo();

        temp.setId(rset.getLong("ID"));
        temp.setSSerialNo(rset.getString("sSerialNo"));
        temp.setSTransNo(rset.getString("C_sTransNo"));
        temp.setNTransactoinTypeID(rset.getLong("nTransactoinTypeID"));
        temp.setNTransInvestmentTypeID(rset.getLong("nTransInvestmentTypeID"));
        temp.setSContractNo(rset.getString("Z_sContractNo"));
        temp.setSCounterPartNo(rset.getString("sCounterPartNo"));
        temp.setSDiscountBillNo(rset.getString("Z_sDiscountBillNo"));
        temp.setSBillAcceptanceUser(rset.getString("Z_sBillAcceptanceUser"));
        temp.setSAcceptanceUserBank(rset.getString("Z_sAcceptanceUserBank"));
        temp.setSAcceptanceUserAccount(rset.getString("Z_sAcceptanceUserAccount"));
        temp.setNDiscountBillTypeID(rset.getLong("Z_nDiscountBillTypeID"));
        temp.setNDiscountType1(rset.getLong("Z_nDiscountType1"));
        temp.setNDiscountType2(rset.getLong("Z_nDiscountType2"));
        temp.setDtDiscountDate(rset.getTimestamp("Z_dtDiscountDate"));
        temp.setNMonthInterest(rset.getDouble("Z_nMonthInterest"));
        temp.setDtBuyEndDate(rset.getTimestamp("Z_dtBuyEndDate"));
        temp.setDtBillEndDate(rset.getTimestamp("Z_dtBillEndDate"));
        temp.setNDiscountDays(rset.getLong("Z_nDiscountDays"));
        temp.setMBillAmount(rset.getDouble("Z_mBillAmount"));
        temp.setMDiscountPayAmount(rset.getDouble("Z_mDiscountPayAmount"));
        temp.setSAccountBankNo(rset.getString("Z_sAccountBankNo"));
        temp.setSAccountNo(rset.getString("Z_sAccountNo"));
        temp.setNIsRepurchase(rset.getLong("Z_nIsRepurchase"));
        temp.setNIsRepayment(rset.getLong("Z_nIsRepayment"));
        temp.setSRemark(rset.getString("sRemark"));
        temp.setNborrowTypeID(rset.getLong("C_nborrowTypeID"));
        temp.setDtBorrowDate(rset.getTimestamp("C_dtBorrowDate"));
        temp.setMBorrowAmount(rset.getDouble("C_mBorrowAmount"));
        temp.setDtPayDate(rset.getTimestamp("C_dtPayDate"));
        temp.setNInterestDays(rset.getLong("nInterestDays"));
        temp.setMInterestRate(rset.getDouble("mInterestRate"));
        temp.setMInterestAmount(rset.getDouble("mInterestAmount"));
        temp.setMPayAmountCJ(rset.getDouble("C_mPayAmount"));
        temp.setNIsPaid(rset.getLong("C_nIsPaid"));
        temp.setSDealNo(rset.getString("Q_sDealNo"));
        temp.setSProductType(rset.getString("Q_sProductType"));
        temp.setSProductName(rset.getString("Q_sProductName"));
        temp.setDtDealDate(rset.getTimestamp("Q_dtDealDate"));
        temp.setMPayAmountQ(rset.getDouble("Q_mPayAmount"));
        temp.setNBondValue(rset.getLong("JQ_nBondValue"));
        temp.setNBondQuantity(rset.getLong("JQ_nBondQuantity"));
        temp.setDtInterestStart(rset.getTimestamp("JQ_dtInterestStart"));
        temp.setDtInterestEnd(rset.getTimestamp("JQ_dtInterestEnd"));
        temp.setNBuyDaysJQ(rset.getLong("JQ_nBuyDays"));
        temp.setNBuyAmount(rset.getDouble("JQ_nBuyAmount"));
        temp.setSOperator(rset.getString("sOperator"));
        temp.setBBought(rset.getLong("JQ_bBought"));
        temp.setNProductValue(rset.getLong("XQ_nProductValue"));
        temp.setNProductQuantity(rset.getLong("XQ_nProductQuantity"));
        temp.setNShareHolderAccountID(rset.getLong("JXQ_nShareHolderAccountID"));
        temp.setNFundAccountID(rset.getLong("JXQ_nFundAccountID"));
        temp.setSBuyContract(rset.getString("D_sBuyContract"));
        temp.setSLoanPart(rset.getString("D_sLoanPart"));
        temp.setDtLoanStartDate(rset.getTimestamp("D_dtLoanStartDate"));
        temp.setDtLoanEndDate(rset.getTimestamp("D_dtLoanEndDate"));
        temp.setMLoanAmount(rset.getDouble("D_mLoanAmount"));
        temp.setMLoanBlance(rset.getDouble("D_mLoanBlance"));
        temp.setDtLoanBlanceEndDate(rset.getTimestamp("D_dtLoanBlanceEndDate"));
        temp.setDtRepurchaseDate(rset.getTimestamp("D_dtRepurchaseDate"));
        temp.setDtBuyDate(rset.getTimestamp("D_dtBuyDate"));
        temp.setNBuyDaysD(rset.getLong("D_nBuyDays"));
        temp.setMBuyAmount(rset.getDouble("D_mBuyAmount"));
        temp.setDtInputDate(rset.getTimestamp("dtInputDate"));
        temp.setNStatusID(rset.getLong("nStatusID"));

        return temp;
    }
}