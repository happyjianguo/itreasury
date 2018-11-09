/*
 * Created on 2004-11-9
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.settlement.transinvestment.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;

import com.iss.itreasury.util.DataFormat;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


import javax.servlet.ServletRequest;

import com.iss.itreasury.util.ITreasuryException;

/**
 * @author ygzhao
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class QueryTransInvestmentInfo implements Serializable 
{
    private long Id = -1; //唯一标识
    private String sSerialNo = ""; //序号（流水）（由）
    private String sSerialNo2 = "";//序号（流水）（至）
    private String sTransNo = ""; //交易号
    private String sTransNo2 = ""; 
    private long nTransactoinTypeID = -1; //交易类型
    private long nTransInvestmentTypeID = -1; //台账类型
    private String sContractNo = ""; //转贴现合同编号
    private String sContractNo2 = "";
    private String sCounterPartNo = ""; //交易对手
    private String sDiscountBillNo = ""; //汇票号码
    private String sDiscountBillNo2 = "";
    private String sBillAcceptanceUser = ""; //汇票承兑人
    private String sAcceptanceUserBank = ""; //承兑人开户银行
    private String sAcceptanceUserAccount = ""; //承兑人账号
    private long nDiscountBillTypeID = -1; //汇票类型（银票/商票）
    private long nDiscountType1 = -1; //买入/卖出
    private long nDiscountType2 = -1; //买断/回购
    private  Timestamp dtDiscountDate = null; //转贴现日期
    private  Timestamp dtDiscountDate2 = null;
    private double nMonthInterest = 0.0; //转贴现利率（月）
    private double nMonthInterest2 = 0.0;
    private  Timestamp dtBuyEndDate = null; //回购到期日
    private  Timestamp dtBuyEndDate2 = null;
    private  Timestamp dtBillEndDate  = null; //票据到期日
    private  Timestamp dtBillEndDate2  = null;
    private long nDiscountDays = -1; //转贴天数
    private long nDiscountDays2 = -1; 
    private double mBillAmount = 0.0; //票面金额
    private double mBillAmount2 = 0.0;
    private double mDiscountPayAmount =0.0; //转贴实际收取/支付金额
    private double mDiscountPayAmount2 =0.0;
    private String sAccountBankNo = ""; //开户银行
    private String sAccountNo = ""; //账号
    private long nIsRepurchase = -1; //是否已经回购
    private long nIsRepayment  = -1; //是否已经到期收回
    private String sRemark = ""; //备注
    private long nborrowTypeID = -1; //拆借类别
    private  Timestamp dtBorrowDate = null; //拆入/拆出时间
    private  Timestamp dtBorrowDate2 = null; 
    private double mBorrowAmount = 0.0; //拆入/拆出金额
    private double mBorrowAmount2 = 0.0;
    private  Timestamp dtPayDate  = null; //返款时间
    private  Timestamp dtPayDate2  = null;
    private long nInterestDays = -1; //天数
    private long nInterestDays2 = -1;
    private double mInterestRate = 0.0; //利率 ----------
    private double mInterestRate2 = 0.0;
    private double mInterestAmount = 0.0; //利息额
    private double mInterestAmount2 = 0.0;
    private double mPayAmountCJ = 0.0; //返款金额
    private double mPayAmountCJ2 = 0.0;
    private long nIsPaid = -1; //是否已经返款
    private String sDealNo = ""; //成交单编号
    private String sDealNo2 = "";
    private String sProductType = ""; //产品品种
    private String sProductName = ""; //产品名称
    private  Timestamp dtDealDate = null; //成交日期
    private  Timestamp dtDealDate2 = null;
    private double mPayAmountQ = 0.0; //实际收付金额
    private double mPayAmountQ2 = 0.0;
    private long nBondValue = -1; //债券现值
    private long nBondValue2 = -1;
    private long nBondQuantity = -1; //债券数量
    private long nBondQuantity2 = -1;
    private  Timestamp dtInterestStart = null; //回购起息日
    private  Timestamp dtInterestStart2 = null;
    private  Timestamp dtInterestEnd = null; //回购到期日
    private  Timestamp dtInterestEnd2 = null;
    private long nBuyDaysJQ = -1; //回购天数
    private long nBuyDaysJQ2 = -1;
    private double nBuyAmount = 0.0; //回购金额
    private double nBuyAmount2 = 0.0;
    private String sOperator = ""; //负责人
    private long bBought = -1; //是否已经购回
    private long nProductValue = -1; //产品现值
    private long nProductValue2 = -1;
    private long nProductQuantity = -1; //产品数量
    private long nProductQuantity2 = -1;
    private long nShareHolderAccountID = -1; //股东账户编码
    private long nFundAccountID = -1; //资金账户编码
    private String sBuyContract = ""; //回购合同编号
    private String sBuyContract2 = "";
    private String sLoanPart = ""; //原合同贷款单位
    private String sLoanPart2 = "";
    private  Timestamp dtLoanStartDate = null; //原合同借款日期
    private  Timestamp dtLoanStartDate2 = null;
    private  Timestamp dtLoanEndDate = null; //原合同借款到期日
    private  Timestamp dtLoanEndDate2 = null;
    private double mLoanAmount = 0.0; //原合同借款金额
    private double mLoanAmount2 = 0.0;
    private double mLoanBlance = 0.0; //原合同贷款余额
    private double mLoanBlance2 = 0.0;
    private  Timestamp  dtLoanBlanceEndDate = null; //原合同贷款余额到期日
    private  Timestamp  dtLoanBlanceEndDate2 = null;
    private  Timestamp dtRepurchaseDate = null; //回购日
    private  Timestamp dtRepurchaseDate2 = null;
    private  Timestamp dtBuyDate = null; //购回日
    private  Timestamp dtBuyDate2 = null;
    private long nBuyDaysD = -1; //回购天数
    private long nBuyDaysD2 = -1;
    private double mBuyAmount = 0.0; //购回金额
    private double mBuyAmount2 = 0.0;
    private Timestamp dtInputDate = null; //录入日期
    private Timestamp dtInputDate2 = null;
    private long StatusID = -1; //	Number	状态
    private long officeID=-1;//办事处ID
    private long currencyID=-1;//币种ID

    public long getOfficeID() {
		return officeID;
	}
	public void setOfficeID(long officeID) {
		this.officeID = officeID;
	}
	public long getCurrencyID() {
		return currencyID;
	}
	public void setCurrencyID(long currencyID) {
		this.currencyID = currencyID;
	}
	/**
     * @return Returns the bBought.
     */
    public long getBBought()
    {
        return bBought;
    }
    /**
     * @param bought The bBought to set.
     */
    public void setBBought(long bought)
    {
        bBought = bought;
    }
    /**
     * @return Returns the dtBillEndDate.
     */
    public Timestamp getDtBillEndDate()
    {
        return dtBillEndDate;
    }
    /**
     * @param dtBillEndDate The dtBillEndDate to set.
     */
    public void setDtBillEndDate(Timestamp dtBillEndDate)
    {
        this.dtBillEndDate = dtBillEndDate;
    }
    /**
     * @return Returns the dtBillEndDate2.
     */
    public Timestamp getDtBillEndDate2()
    {
        return dtBillEndDate2;
    }
    /**
     * @param dtBillEndDate2 The dtBillEndDate2 to set.
     */
    public void setDtBillEndDate2(Timestamp dtBillEndDate2)
    {
        this.dtBillEndDate2 = dtBillEndDate2;
    }
    /**
     * @return Returns the dtBorrowDate.
     */
    public Timestamp getDtBorrowDate()
    {
        return dtBorrowDate;
    }
    /**
     * @param dtBorrowDate The dtBorrowDate to set.
     */
    public void setDtBorrowDate(Timestamp dtBorrowDate)
    {
        this.dtBorrowDate = dtBorrowDate;
    }
    /**
     * @return Returns the dtBorrowDate2.
     */
    public Timestamp getDtBorrowDate2()
    {
        return dtBorrowDate2;
    }
    /**
     * @param dtBorrowDate2 The dtBorrowDate2 to set.
     */
    public void setDtBorrowDate2(Timestamp dtBorrowDate2)
    {
        this.dtBorrowDate2 = dtBorrowDate2;
    }
    /**
     * @return Returns the dtBuyDate.
     */
    public Timestamp getDtBuyDate()
    {
        return dtBuyDate;
    }
    /**
     * @param dtBuyDate The dtBuyDate to set.
     */
    public void setDtBuyDate(Timestamp dtBuyDate)
    {
        this.dtBuyDate = dtBuyDate;
    }
    /**
     * @return Returns the dtBuyDate2.
     */
    public Timestamp getDtBuyDate2()
    {
        return dtBuyDate2;
    }
    /**
     * @param dtBuyDate2 The dtBuyDate2 to set.
     */
    public void setDtBuyDate2(Timestamp dtBuyDate2)
    {
        this.dtBuyDate2 = dtBuyDate2;
    }
    /**
     * @return Returns the dtBuyEndDate.
     */
    public Timestamp getDtBuyEndDate()
    {
        return dtBuyEndDate;
    }
    /**
     * @param dtBuyEndDate The dtBuyEndDate to set.
     */
    public void setDtBuyEndDate(Timestamp dtBuyEndDate)
    {
        this.dtBuyEndDate = dtBuyEndDate;
    }
    /**
     * @return Returns the dtBuyEndDate2.
     */
    public Timestamp getDtBuyEndDate2()
    {
        return dtBuyEndDate2;
    }
    /**
     * @param dtBuyEndDate2 The dtBuyEndDate2 to set.
     */
    public void setDtBuyEndDate2(Timestamp dtBuyEndDate2)
    {
        this.dtBuyEndDate2 = dtBuyEndDate2;
    }
    /**
     * @return Returns the dtDealDate.
     */
    public Timestamp getDtDealDate()
    {
        return dtDealDate;
    }
    /**
     * @param dtDealDate The dtDealDate to set.
     */
    public void setDtDealDate(Timestamp dtDealDate)
    {
        this.dtDealDate = dtDealDate;
    }
    /**
     * @return Returns the dtDealDate2.
     */
    public Timestamp getDtDealDate2()
    {
        return dtDealDate2;
    }
    /**
     * @param dtDealDate2 The dtDealDate2 to set.
     */
    public void setDtDealDate2(Timestamp dtDealDate2)
    {
        this.dtDealDate2 = dtDealDate2;
    }
    /**
     * @return Returns the dtDiscountDate.
     */
    public Timestamp getDtDiscountDate()
    {
        return dtDiscountDate;
    }
    /**
     * @param dtDiscountDate The dtDiscountDate to set.
     */
    public void setDtDiscountDate(Timestamp dtDiscountDate)
    {
        this.dtDiscountDate = dtDiscountDate;
    }
    /**
     * @return Returns the dtDiscountDate2.
     */
    public Timestamp getDtDiscountDate2()
    {
        return dtDiscountDate2;
    }
    /**
     * @param dtDiscountDate2 The dtDiscountDate2 to set.
     */
    public void setDtDiscountDate2(Timestamp dtDiscountDate2)
    {
        this.dtDiscountDate2 = dtDiscountDate2;
    }
    /**
     * @return Returns the dtInputDate.
     */
    public Timestamp getDtInputDate()
    {
        return dtInputDate;
    }
    /**
     * @param dtInputDate The dtInputDate to set.
     */
    public void setDtInputDate(Timestamp dtInputDate)
    {
        this.dtInputDate = dtInputDate;
    }
    /**
     * @return Returns the dtInputDate2.
     */
    public Timestamp getDtInputDate2()
    {
        return dtInputDate2;
    }
    /**
     * @param dtInputDate2 The dtInputDate2 to set.
     */
    public void setDtInputDate2(Timestamp dtInputDate2)
    {
        this.dtInputDate2 = dtInputDate2;
    }
    /**
     * @return Returns the dtInterestEnd.
     */
    public Timestamp getDtInterestEnd()
    {
        return dtInterestEnd;
    }
    /**
     * @param dtInterestEnd The dtInterestEnd to set.
     */
    public void setDtInterestEnd(Timestamp dtInterestEnd)
    {
        this.dtInterestEnd = dtInterestEnd;
    }
    /**
     * @return Returns the dtInterestEnd2.
     */
    public Timestamp getDtInterestEnd2()
    {
        return dtInterestEnd2;
    }
    /**
     * @param dtInterestEnd2 The dtInterestEnd2 to set.
     */
    public void setDtInterestEnd2(Timestamp dtInterestEnd2)
    {
        this.dtInterestEnd2 = dtInterestEnd2;
    }
    /**
     * @return Returns the dtInterestStart.
     */
    public Timestamp getDtInterestStart()
    {
        return dtInterestStart;
    }
    /**
     * @param dtInterestStart The dtInterestStart to set.
     */
    public void setDtInterestStart(Timestamp dtInterestStart)
    {
        this.dtInterestStart = dtInterestStart;
    }
    /**
     * @return Returns the dtInterestStart2.
     */
    public Timestamp getDtInterestStart2()
    {
        return dtInterestStart2;
    }
    /**
     * @param dtInterestStart2 The dtInterestStart2 to set.
     */
    public void setDtInterestStart2(Timestamp dtInterestStart2)
    {
        this.dtInterestStart2 = dtInterestStart2;
    }
    /**
     * @return Returns the dtLoanBlanceEndDate.
     */
    public Timestamp getDtLoanBlanceEndDate()
    {
        return dtLoanBlanceEndDate;
    }
    /**
     * @param dtLoanBlanceEndDate The dtLoanBlanceEndDate to set.
     */
    public void setDtLoanBlanceEndDate(Timestamp dtLoanBlanceEndDate)
    {
        this.dtLoanBlanceEndDate = dtLoanBlanceEndDate;
    }
    /**
     * @return Returns the dtLoanBlanceEndDate2.
     */
    public Timestamp getDtLoanBlanceEndDate2()
    {
        return dtLoanBlanceEndDate2;
    }
    /**
     * @param dtLoanBlanceEndDate2 The dtLoanBlanceEndDate2 to set.
     */
    public void setDtLoanBlanceEndDate2(Timestamp dtLoanBlanceEndDate2)
    {
        this.dtLoanBlanceEndDate2 = dtLoanBlanceEndDate2;
    }
    /**
     * @return Returns the dtLoanEndDate.
     */
    public Timestamp getDtLoanEndDate()
    {
        return dtLoanEndDate;
    }
    /**
     * @param dtLoanEndDate The dtLoanEndDate to set.
     */
    public void setDtLoanEndDate(Timestamp dtLoanEndDate)
    {
        this.dtLoanEndDate = dtLoanEndDate;
    }
    /**
     * @return Returns the dtLoanEndDate2.
     */
    public Timestamp getDtLoanEndDate2()
    {
        return dtLoanEndDate2;
    }
    /**
     * @param dtLoanEndDate2 The dtLoanEndDate2 to set.
     */
    public void setDtLoanEndDate2(Timestamp dtLoanEndDate2)
    {
        this.dtLoanEndDate2 = dtLoanEndDate2;
    }
    /**
     * @return Returns the dtLoanStartDate.
     */
    public Timestamp getDtLoanStartDate()
    {
        return dtLoanStartDate;
    }
    /**
     * @param dtLoanStartDate The dtLoanStartDate to set.
     */
    public void setDtLoanStartDate(Timestamp dtLoanStartDate)
    {
        this.dtLoanStartDate = dtLoanStartDate;
    }
    /**
     * @return Returns the dtLoanStartDate2.
     */
    public Timestamp getDtLoanStartDate2()
    {
        return dtLoanStartDate2;
    }
    /**
     * @param dtLoanStartDate2 The dtLoanStartDate2 to set.
     */
    public void setDtLoanStartDate2(Timestamp dtLoanStartDate2)
    {
        this.dtLoanStartDate2 = dtLoanStartDate2;
    }
    /**
     * @return Returns the dtPayDate.
     */
    public Timestamp getDtPayDate()
    {
        return dtPayDate;
    }
    /**
     * @param dtPayDate The dtPayDate to set.
     */
    public void setDtPayDate(Timestamp dtPayDate)
    {
        this.dtPayDate = dtPayDate;
    }
    /**
     * @return Returns the dtPayDate2.
     */
    public Timestamp getDtPayDate2()
    {
        return dtPayDate2;
    }
    /**
     * @param dtPayDate2 The dtPayDate2 to set.
     */
    public void setDtPayDate2(Timestamp dtPayDate2)
    {
        this.dtPayDate2 = dtPayDate2;
    }
    /**
     * @return Returns the dtRepurchaseDate.
     */
    public Timestamp getDtRepurchaseDate()
    {
        return dtRepurchaseDate;
    }
    /**
     * @param dtRepurchaseDate The dtRepurchaseDate to set.
     */
    public void setDtRepurchaseDate(Timestamp dtRepurchaseDate)
    {
        this.dtRepurchaseDate = dtRepurchaseDate;
    }
    /**
     * @return Returns the dtRepurchaseDate2.
     */
    public Timestamp getDtRepurchaseDate2()
    {
        return dtRepurchaseDate2;
    }
    /**
     * @param dtRepurchaseDate2 The dtRepurchaseDate2 to set.
     */
    public void setDtRepurchaseDate2(Timestamp dtRepurchaseDate2)
    {
        this.dtRepurchaseDate2 = dtRepurchaseDate2;
    }
    /**
     * @return Returns the id.
     */
    public long getId()
    {
        return Id;
    }
    /**
     * @param id The id to set.
     */
    public void setId(long id)
    {
        Id = id;
    }
    /**
     * @return Returns the mBillAmount.
     */
    public double getMBillAmount()
    {
        return mBillAmount;
    }
    /**
     * @param billAmount The mBillAmount to set.
     */
    public void setMBillAmount(double billAmount)
    {
        mBillAmount = billAmount;
    }
    /**
     * @return Returns the mBillAmount2.
     */
    public double getMBillAmount2()
    {
        return mBillAmount2;
    }
    /**
     * @param billAmount2 The mBillAmount2 to set.
     */
    public void setMBillAmount2(double billAmount2)
    {
        mBillAmount2 = billAmount2;
    }
    /**
     * @return Returns the mBorrowAmount.
     */
    public double getMBorrowAmount()
    {
        return mBorrowAmount;
    }
    /**
     * @param borrowAmount The mBorrowAmount to set.
     */
    public void setMBorrowAmount(double borrowAmount)
    {
        mBorrowAmount = borrowAmount;
    }
    /**
     * @return Returns the mBorrowAmount2.
     */
    public double getMBorrowAmount2()
    {
        return mBorrowAmount2;
    }
    /**
     * @param borrowAmount2 The mBorrowAmount2 to set.
     */
    public void setMBorrowAmount2(double borrowAmount2)
    {
        mBorrowAmount2 = borrowAmount2;
    }
    /**
     * @return Returns the mBuyAmount.
     */
    public double getMBuyAmount()
    {
        return mBuyAmount;
    }
    /**
     * @param buyAmount The mBuyAmount to set.
     */
    public void setMBuyAmount(double buyAmount)
    {
        mBuyAmount = buyAmount;
    }
    /**
     * @return Returns the mBuyAmount2.
     */
    public double getMBuyAmount2()
    {
        return mBuyAmount2;
    }
    /**
     * @param buyAmount2 The mBuyAmount2 to set.
     */
    public void setMBuyAmount2(double buyAmount2)
    {
        mBuyAmount2 = buyAmount2;
    }
    /**
     * @return Returns the mDiscountPayAmount.
     */
    public double getMDiscountPayAmount()
    {
        return mDiscountPayAmount;
    }
    /**
     * @param discountPayAmount The mDiscountPayAmount to set.
     */
    public void setMDiscountPayAmount(double discountPayAmount)
    {
        mDiscountPayAmount = discountPayAmount;
    }
    /**
     * @return Returns the mDiscountPayAmount2.
     */
    public double getMDiscountPayAmount2()
    {
        return mDiscountPayAmount2;
    }
    /**
     * @param discountPayAmount2 The mDiscountPayAmount2 to set.
     */
    public void setMDiscountPayAmount2(double discountPayAmount2)
    {
        mDiscountPayAmount2 = discountPayAmount2;
    }
    /**
     * @return Returns the mInterestAmount.
     */
    public double getMInterestAmount()
    {
        return mInterestAmount;
    }
    /**
     * @param interestAmount The mInterestAmount to set.
     */
    public void setMInterestAmount(double interestAmount)
    {
        mInterestAmount = interestAmount;
    }
    /**
     * @return Returns the mInterestAmount2.
     */
    public double getMInterestAmount2()
    {
        return mInterestAmount2;
    }
    /**
     * @param interestAmount2 The mInterestAmount2 to set.
     */
    public void setMInterestAmount2(double interestAmount2)
    {
        mInterestAmount2 = interestAmount2;
    }
    /**
     * @return Returns the mInterestRate.
     */
    public double getMInterestRate()
    {
        return mInterestRate;
    }
    /**
     * @param interestRate The mInterestRate to set.
     */
    public void setMInterestRate(double interestRate)
    {
        mInterestRate = interestRate;
    }
    /**
     * @return Returns the mInterestRate2.
     */
    public double getMInterestRate2()
    {
        return mInterestRate2;
    }
    /**
     * @param interestRate2 The mInterestRate2 to set.
     */
    public void setMInterestRate2(double interestRate2)
    {
        mInterestRate2 = interestRate2;
    }
    /**
     * @return Returns the mLoanAmount.
     */
    public double getMLoanAmount()
    {
        return mLoanAmount;
    }
    /**
     * @param loanAmount The mLoanAmount to set.
     */
    public void setMLoanAmount(double loanAmount)
    {
        mLoanAmount = loanAmount;
    }
    /**
     * @return Returns the mLoanAmount2.
     */
    public double getMLoanAmount2()
    {
        return mLoanAmount2;
    }
    /**
     * @param loanAmount2 The mLoanAmount2 to set.
     */
    public void setMLoanAmount2(double loanAmount2)
    {
        mLoanAmount2 = loanAmount2;
    }
    /**
     * @return Returns the mLoanBlance.
     */
    public double getMLoanBlance()
    {
        return mLoanBlance;
    }
    /**
     * @param loanBlance The mLoanBlance to set.
     */
    public void setMLoanBlance(double loanBlance)
    {
        mLoanBlance = loanBlance;
    }
    /**
     * @return Returns the mLoanBlance2.
     */
    public double getMLoanBlance2()
    {
        return mLoanBlance2;
    }
    /**
     * @param loanBlance2 The mLoanBlance2 to set.
     */
    public void setMLoanBlance2(double loanBlance2)
    {
        mLoanBlance2 = loanBlance2;
    }
    /**
     * @return Returns the mPayAmountCJ.
     */
    public double getMPayAmountCJ()
    {
        return mPayAmountCJ;
    }
    /**
     * @param payAmountCJ The mPayAmountCJ to set.
     */
    public void setMPayAmountCJ(double payAmountCJ)
    {
        mPayAmountCJ = payAmountCJ;
    }
    /**
     * @return Returns the mPayAmountCJ2.
     */
    public double getMPayAmountCJ2()
    {
        return mPayAmountCJ2;
    }
    /**
     * @param payAmountCJ2 The mPayAmountCJ2 to set.
     */
    public void setMPayAmountCJ2(double payAmountCJ2)
    {
        mPayAmountCJ2 = payAmountCJ2;
    }
    /**
     * @return Returns the mPayAmountQ.
     */
    public double getMPayAmountQ()
    {
        return mPayAmountQ;
    }
    /**
     * @param payAmountQ The mPayAmountQ to set.
     */
    public void setMPayAmountQ(double payAmountQ)
    {
        mPayAmountQ = payAmountQ;
    }
    /**
     * @return Returns the mPayAmountQ2.
     */
    public double getMPayAmountQ2()
    {
        return mPayAmountQ2;
    }
    /**
     * @param payAmountQ2 The mPayAmountQ2 to set.
     */
    public void setMPayAmountQ2(double payAmountQ2)
    {
        mPayAmountQ2 = payAmountQ2;
    }
    /**
     * @return Returns the nBondQuantity.
     */
    public long getNBondQuantity()
    {
        return nBondQuantity;
    }
    /**
     * @param bondQuantity The nBondQuantity to set.
     */
    public void setNBondQuantity(long bondQuantity)
    {
        nBondQuantity = bondQuantity;
    }
    /**
     * @return Returns the nBondQuantity2.
     */
    public long getNBondQuantity2()
    {
        return nBondQuantity2;
    }
    /**
     * @param bondQuantity2 The nBondQuantity2 to set.
     */
    public void setNBondQuantity2(long bondQuantity2)
    {
        nBondQuantity2 = bondQuantity2;
    }
    /**
     * @return Returns the nBondValue.
     */
    public long getNBondValue()
    {
        return nBondValue;
    }
    /**
     * @param bondValue The nBondValue to set.
     */
    public void setNBondValue(long bondValue)
    {
        nBondValue = bondValue;
    }
    /**
     * @return Returns the nBondValue2.
     */
    public long getNBondValue2()
    {
        return nBondValue2;
    }
    /**
     * @param bondValue2 The nBondValue2 to set.
     */
    public void setNBondValue2(long bondValue2)
    {
        nBondValue2 = bondValue2;
    }
    /**
     * @return Returns the nborrowTypeID.
     */
    public long getNborrowTypeID()
    {
        return nborrowTypeID;
    }
    /**
     * @param nborrowTypeID The nborrowTypeID to set.
     */
    public void setNborrowTypeID(long nborrowTypeID)
    {
        this.nborrowTypeID = nborrowTypeID;
    }
    /**
     * @return Returns the nBuyAmount.
     */
    public double getNBuyAmount()
    {
        return nBuyAmount;
    }
    /**
     * @param buyAmount The nBuyAmount to set.
     */
    public void setNBuyAmount(double buyAmount)
    {
        nBuyAmount = buyAmount;
    }
    /**
     * @return Returns the nBuyAmount2.
     */
    public double getNBuyAmount2()
    {
        return nBuyAmount2;
    }
    /**
     * @param buyAmount2 The nBuyAmount2 to set.
     */
    public void setNBuyAmount2(double buyAmount2)
    {
        nBuyAmount2 = buyAmount2;
    }
    /**
     * @return Returns the nBuyDaysD.
     */
    public long getNBuyDaysD()
    {
        return nBuyDaysD;
    }
    /**
     * @param buyDaysD The nBuyDaysD to set.
     */
    public void setNBuyDaysD(long buyDaysD)
    {
        nBuyDaysD = buyDaysD;
    }
    /**
     * @return Returns the nBuyDaysD2.
     */
    public long getNBuyDaysD2()
    {
        return nBuyDaysD2;
    }
    /**
     * @param buyDaysD2 The nBuyDaysD2 to set.
     */
    public void setNBuyDaysD2(long buyDaysD2)
    {
        nBuyDaysD2 = buyDaysD2;
    }
    /**
     * @return Returns the nBuyDaysJQ.
     */
    public long getNBuyDaysJQ()
    {
        return nBuyDaysJQ;
    }
    /**
     * @param buyDaysJQ The nBuyDaysJQ to set.
     */
    public void setNBuyDaysJQ(long buyDaysJQ)
    {
        nBuyDaysJQ = buyDaysJQ;
    }
    /**
     * @return Returns the nBuyDaysJQ2.
     */
    public long getNBuyDaysJQ2()
    {
        return nBuyDaysJQ2;
    }
    /**
     * @param buyDaysJQ2 The nBuyDaysJQ2 to set.
     */
    public void setNBuyDaysJQ2(long buyDaysJQ2)
    {
        nBuyDaysJQ2 = buyDaysJQ2;
    }
    /**
     * @return Returns the nDiscountBillTypeID.
     */
    public long getNDiscountBillTypeID()
    {
        return nDiscountBillTypeID;
    }
    /**
     * @param discountBillTypeID The nDiscountBillTypeID to set.
     */
    public void setNDiscountBillTypeID(long discountBillTypeID)
    {
        nDiscountBillTypeID = discountBillTypeID;
    }
    /**
     * @return Returns the nDiscountDays.
     */
    public long getNDiscountDays()
    {
        return nDiscountDays;
    }
    /**
     * @param discountDays The nDiscountDays to set.
     */
    public void setNDiscountDays(long discountDays)
    {
        nDiscountDays = discountDays;
    }
    /**
     * @return Returns the nDiscountDays2.
     */
    public long getNDiscountDays2()
    {
        return nDiscountDays2;
    }
    /**
     * @param discountDays2 The nDiscountDays2 to set.
     */
    public void setNDiscountDays2(long discountDays2)
    {
        nDiscountDays2 = discountDays2;
    }
    /**
     * @return Returns the nDiscountType1.
     */
    public long getNDiscountType1()
    {
        return nDiscountType1;
    }
    /**
     * @param discountType1 The nDiscountType1 to set.
     */
    public void setNDiscountType1(long discountType1)
    {
        nDiscountType1 = discountType1;
    }
    /**
     * @return Returns the nDiscountType2.
     */
    public long getNDiscountType2()
    {
        return nDiscountType2;
    }
    /**
     * @param discountType2 The nDiscountType2 to set.
     */
    public void setNDiscountType2(long discountType2)
    {
        nDiscountType2 = discountType2;
    }
    /**
     * @return Returns the nFundAccountID.
     */
    public long getNFundAccountID()
    {
        return nFundAccountID;
    }
    /**
     * @param fundAccountID The nFundAccountID to set.
     */
    public void setNFundAccountID(long fundAccountID)
    {
        nFundAccountID = fundAccountID;
    }
    /**
     * @return Returns the nInterestDays.
     */
    public long getNInterestDays()
    {
        return nInterestDays;
    }
    /**
     * @param interestDays The nInterestDays to set.
     */
    public void setNInterestDays(long interestDays)
    {
        nInterestDays = interestDays;
    }
    /**
     * @return Returns the nInterestDays2.
     */
    public long getNInterestDays2()
    {
        return nInterestDays2;
    }
    /**
     * @param interestDays2 The nInterestDays2 to set.
     */
    public void setNInterestDays2(long interestDays2)
    {
        nInterestDays2 = interestDays2;
    }
    /**
     * @return Returns the nIsPaid.
     */
    public long getNIsPaid()
    {
        return nIsPaid;
    }
    /**
     * @param isPaid The nIsPaid to set.
     */
    public void setNIsPaid(long isPaid)
    {
        nIsPaid = isPaid;
    }
    /**
     * @return Returns the nIsRepayment.
     */
    public long getNIsRepayment()
    {
        return nIsRepayment;
    }
    /**
     * @param isRepayment The nIsRepayment to set.
     */
    public void setNIsRepayment(long isRepayment)
    {
        nIsRepayment = isRepayment;
    }
    /**
     * @return Returns the nIsRepurchase.
     */
    public long getNIsRepurchase()
    {
        return nIsRepurchase;
    }
    /**
     * @param isRepurchase The nIsRepurchase to set.
     */
    public void setNIsRepurchase(long isRepurchase)
    {
        nIsRepurchase = isRepurchase;
    }
    /**
     * @return Returns the nMonthInterest.
     */
    public double getNMonthInterest()
    {
        return nMonthInterest;
    }
    /**
     * @param monthInterest The nMonthInterest to set.
     */
    public void setNMonthInterest(double monthInterest)
    {
        nMonthInterest = monthInterest;
    }
    /**
     * @return Returns the nMonthInterest2.
     */
    public double getNMonthInterest2()
    {
        return nMonthInterest2;
    }
    /**
     * @param monthInterest2 The nMonthInterest2 to set.
     */
    public void setNMonthInterest2(double monthInterest2)
    {
        nMonthInterest2 = monthInterest2;
    }
    /**
     * @return Returns the nProductQuantity.
     */
    public long getNProductQuantity()
    {
        return nProductQuantity;
    }
    /**
     * @param productQuantity The nProductQuantity to set.
     */
    public void setNProductQuantity(long productQuantity)
    {
        nProductQuantity = productQuantity;
    }
    /**
     * @return Returns the nProductQuantity2.
     */
    public long getNProductQuantity2()
    {
        return nProductQuantity2;
    }
    /**
     * @param productQuantity2 The nProductQuantity2 to set.
     */
    public void setNProductQuantity2(long productQuantity2)
    {
        nProductQuantity2 = productQuantity2;
    }
    /**
     * @return Returns the nProductValue.
     */
    public long getNProductValue()
    {
        return nProductValue;
    }
    /**
     * @param productValue The nProductValue to set.
     */
    public void setNProductValue(long productValue)
    {
        nProductValue = productValue;
    }
    /**
     * @return Returns the nProductValue2.
     */
    public long getNProductValue2()
    {
        return nProductValue2;
    }
    /**
     * @param productValue2 The nProductValue2 to set.
     */
    public void setNProductValue2(long productValue2)
    {
        nProductValue2 = productValue2;
    }
    /**
     * @return Returns the nSerialNo.
     */
    public String getSSerialNo()
    {
        return sSerialNo;
    }
    /**
     * @param serialNo The nSerialNo to set.
     */
    public void setSSerialNo(String serialNo)
    {
        sSerialNo = serialNo;
    }
    /**
     * @return Returns the nSerialNo2.
     */
    public String getSSerialNo2()
    {
        return sSerialNo2;
    }
    /**
     * @param serialNo2 The nSerialNo2 to set.
     */
    public void setSSerialNo2(String serialNo2)
    {
        sSerialNo2 = serialNo2;
    }
    /**
     * @return Returns the nShareHolderAccountID.
     */
    public long getNShareHolderAccountID()
    {
        return nShareHolderAccountID;
    }
    /**
     * @param shareHolderAccountID The nShareHolderAccountID to set.
     */
    public void setNShareHolderAccountID(long shareHolderAccountID)
    {
        nShareHolderAccountID = shareHolderAccountID;
    }
    /**
     * @return Returns the nTransactoinTypeID.
     */
    public long getNTransactoinTypeID()
    {
        return nTransactoinTypeID;
    }
    /**
     * @param transactoinTypeID The nTransactoinTypeID to set.
     */
    public void setNTransactoinTypeID(long transactoinTypeID)
    {
        nTransactoinTypeID = transactoinTypeID;
    }
    /**
     * @return Returns the nTransInvestmentTypeID.
     */
    public long getNTransInvestmentTypeID()
    {
        return nTransInvestmentTypeID;
    }
    /**
     * @param transInvestmentTypeID The nTransInvestmentTypeID to set.
     */
    public void setNTransInvestmentTypeID(long transInvestmentTypeID)
    {
        nTransInvestmentTypeID = transInvestmentTypeID;
    }
    /**
     * @return Returns the sAcceptanceUserAccount.
     */
    public String getSAcceptanceUserAccount()
    {
        return sAcceptanceUserAccount;
    }
    /**
     * @param acceptanceUserAccount The sAcceptanceUserAccount to set.
     */
    public void setSAcceptanceUserAccount(String acceptanceUserAccount)
    {
        sAcceptanceUserAccount = acceptanceUserAccount;
    }
    /**
     * @return Returns the sAcceptanceUserBank.
     */
    public String getSAcceptanceUserBank()
    {
        return sAcceptanceUserBank;
    }
    /**
     * @param acceptanceUserBank The sAcceptanceUserBank to set.
     */
    public void setSAcceptanceUserBank(String acceptanceUserBank)
    {
        sAcceptanceUserBank = acceptanceUserBank;
    }
    /**
     * @return Returns the sAccountBankNo.
     */
    public String getSAccountBankNo()
    {
        return sAccountBankNo;
    }
    /**
     * @param accountBankNo The sAccountBankNo to set.
     */
    public void setSAccountBankNo(String accountBankNo)
    {
        sAccountBankNo = accountBankNo;
    }
    /**
     * @return Returns the sAccountNo.
     */
    public String getSAccountNo()
    {
        return sAccountNo;
    }
    /**
     * @param accountNo The sAccountNo to set.
     */
    public void setSAccountNo(String accountNo)
    {
        sAccountNo = accountNo;
    }
    /**
     * @return Returns the sBillAcceptanceUser.
     */
    public String getSBillAcceptanceUser()
    {
        return sBillAcceptanceUser;
    }
    /**
     * @param billAcceptanceUser The sBillAcceptanceUser to set.
     */
    public void setSBillAcceptanceUser(String billAcceptanceUser)
    {
        sBillAcceptanceUser = billAcceptanceUser;
    }
    /**
     * @return Returns the sBuyContract.
     */
    public String getSBuyContract()
    {
        return sBuyContract;
    }
    /**
     * @param buyContract The sBuyContract to set.
     */
    public void setSBuyContract(String buyContract)
    {
        sBuyContract = buyContract;
    }
    /**
     * @return Returns the sBuyContract2.
     */
    public String getSBuyContract2()
    {
        return sBuyContract2;
    }
    /**
     * @param buyContract2 The sBuyContract2 to set.
     */
    public void setSBuyContract2(String buyContract2)
    {
        sBuyContract2 = buyContract2;
    }
    /**
     * @return Returns the sContractNo.
     */
    public String getSContractNo()
    {
        return sContractNo;
    }
    /**
     * @param contractNo The sContractNo to set.
     */
    public void setSContractNo(String contractNo)
    {
        sContractNo = contractNo;
    }
    /**
     * @return Returns the sContractNo2.
     */
    public String getSContractNo2()
    {
        return sContractNo2;
    }
    /**
     * @param contractNo2 The sContractNo2 to set.
     */
    public void setSContractNo2(String contractNo2)
    {
        sContractNo2 = contractNo2;
    }
    /**
     * @return Returns the sCounterPartNo.
     */
    public String getSCounterPartNo()
    {
        return sCounterPartNo;
    }
    /**
     * @param counterPartNo The sCounterPartNo to set.
     */
    public void setSCounterPartNo(String counterPartNo)
    {
        sCounterPartNo = counterPartNo;
    }
    /**
     * @return Returns the sDealNo.
     */
    public String getSDealNo()
    {
        return sDealNo;
    }
    /**
     * @param dealNo The sDealNo to set.
     */
    public void setSDealNo(String dealNo)
    {
        sDealNo = dealNo;
    }
    /**
     * @return Returns the sDealNo2.
     */
    public String getSDealNo2()
    {
        return sDealNo2;
    }
    /**
     * @param dealNo2 The sDealNo2 to set.
     */
    public void setSDealNo2(String dealNo2)
    {
        sDealNo2 = dealNo2;
    }
    /**
     * @return Returns the sDiscountBillNo.
     */
    public String getSDiscountBillNo()
    {
        return sDiscountBillNo;
    }
    /**
     * @param discountBillNo The sDiscountBillNo to set.
     */
    public void setSDiscountBillNo(String discountBillNo)
    {
        sDiscountBillNo = discountBillNo;
    }
    /**
     * @return Returns the sDiscountBillNo2.
     */
    public String getSDiscountBillNo2()
    {
        return sDiscountBillNo2;
    }
    /**
     * @param discountBillNo2 The sDiscountBillNo2 to set.
     */
    public void setSDiscountBillNo2(String discountBillNo2)
    {
        sDiscountBillNo2 = discountBillNo2;
    }
    /**
     * @return Returns the sLoanPart.
     */
    public String getSLoanPart()
    {
        return sLoanPart;
    }
    /**
     * @param loanPart The sLoanPart to set.
     */
    public void setSLoanPart(String loanPart)
    {
        sLoanPart = loanPart;
    }
    /**
     * @return Returns the sLoanPart2.
     */
    public String getSLoanPart2()
    {
        return sLoanPart2;
    }
    /**
     * @param loanPart2 The sLoanPart2 to set.
     */
    public void setSLoanPart2(String loanPart2)
    {
        sLoanPart2 = loanPart2;
    }
    /**
     * @return Returns the sOperator.
     */
    public String getSOperator()
    {
        return sOperator;
    }
    /**
     * @param operator The sOperator to set.
     */
    public void setSOperator(String operator)
    {
        sOperator = operator;
    }
    /**
     * @return Returns the sProductName.
     */
    public String getSProductName()
    {
        return sProductName;
    }
    /**
     * @param productName The sProductName to set.
     */
    public void setSProductName(String productName)
    {
        sProductName = productName;
    }
    /**
     * @return Returns the sProductType.
     */
    public String getSProductType()
    {
        return sProductType;
    }
    /**
     * @param productType The sProductType to set.
     */
    public void setSProductType(String productType)
    {
        sProductType = productType;
    }
    /**
     * @return Returns the sRemark.
     */
    public String getSRemark()
    {
        return sRemark;
    }
    /**
     * @param remark The sRemark to set.
     */
    public void setSRemark(String remark)
    {
        sRemark = remark;
    }
    /**
     * @return Returns the statusID.
     */
    public long getStatusID()
    {
        return StatusID;
    }
    /**
     * @param statusID The statusID to set.
     */
    public void setStatusID(long statusID)
    {
        StatusID = statusID;
    }
    /**
     * @return Returns the sTransNo.
     */
    public String getSTransNo()
    {
        return sTransNo;
    }
    /**
     * @param transNo The sTransNo to set.
     */
    public void setSTransNo(String transNo)
    {
        sTransNo = transNo;
    }
    /**
     * @return Returns the sTransNo2.
     */
    public String getSTransNo2()
    {
        return sTransNo2;
    }
    /**
     * @param transNo2 The sTransNo2 to set.
     */
    public void setSTransNo2(String transNo2)
    {
        sTransNo2 = transNo2;
    }
    
    /**@author Barry
	 * 2004-1-9
	 * 从request中得到完整的dataentity
	 * 修改人：jsxie
	 * 主要是因为通过反射机制获取类的属性名时，大小写变了，而且没有规律，
	 * 这里在getAttribute时都统一变成小写：request.getAttribute(p[n].getName().toLowerCase())
	 * 这样要求页面的参数名也要统一写成小写
	 */
	public void convertRequestToDataEntity(ServletRequest request) throws ITreasuryException
	{
		/**
		 * 设定初始值
		 */
		Object[] objIniString 	= {""};
		Object[] objIniLong 	= {new Long(-1)};
		Object[] objIniDouble 	= {new Double(0.0)};
		Object[] objIniTimestamp= {null};
		
		String[] dataTypes = {"double","long","java.lang.String","java.sql.Timestamp"};			//支持的数据类型
		
		BeanInfo info = null;
		try{
			info = Introspector.getBeanInfo(this.getClass());
		}catch (IntrospectionException e) {
			e.printStackTrace();throw new ITreasuryException("Java Bean.内省异常发生",e);			
		}
		//Log.print("----------从request转化到dataentity----------");
		PropertyDescriptor[] p = info.getPropertyDescriptors();
		for (int n=0;n<p.length;n++){
			if (p[n].getName().compareToIgnoreCase("class")==0) continue;
			
			String strValue = (String) request.getAttribute(p[n].getName().toLowerCase());
			
			//Log.print("key:" + p[n].getName() + "// Value:"+strValue);
	
			if (strValue != null){
				Object[] oParam = new Object[]{};
				Method m = p[n].getWriteMethod(); 
				
				if (m!=null){
					if(m.getParameterTypes()[0].getName().equals(dataTypes[0])){			//parameter type is double
						if (strValue.trim().equals("")){
							strValue = "0.0";
						}
						oParam = new Double[]{new Double(DataFormat.parseNumber(strValue.trim()))};
					}
					else if(m.getParameterTypes()[0].getName().equals(dataTypes[1])){	//parameter type is long
						/**
						 * 针对ID做的特殊处理,如果对象参数的长度为0,说明更改了,赋予参数初始值
						 */
						if (strValue.trim().equals("")) strValue = "-1";
						oParam = new Long[]{new Long(strValue.trim())};
					}
					else if(m.getParameterTypes()[0].getName().equals(dataTypes[2])){	//parameter type is String
						oParam = new String[]{strValue.trim()};
					}
					else if(m.getParameterTypes()[0].getName().equals(dataTypes[3])){	//parameter type is Timestamp
						oParam = new Timestamp[]{DataFormat.getDateTime(strValue.trim())};
					}
					try
					{
						m.invoke(this,oParam);				//set parameters to dataentity
					}
					catch (IllegalArgumentException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();throw new ITreasuryException("从request中获得dataentity错误",e);
					}
					catch (IllegalAccessException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();throw new ITreasuryException("从request中获得dataentity错误",e);
					}
					catch(InvocationTargetException e){
						e.printStackTrace();throw new ITreasuryException("从request中获得dataentity错误",e);
					}
				}
			}
		}
	}
}