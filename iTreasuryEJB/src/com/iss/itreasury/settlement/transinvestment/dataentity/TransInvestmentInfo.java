/*
 * Created on 2004-11-5
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.settlement.transinvestment.dataentity;
import java.io.Serializable;
import java.sql.Timestamp;

import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.ITreasuryBaseDataEntity;
//import com.iss.itreasury.util.Log;

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
public class TransInvestmentInfo extends ITreasuryBaseDataEntity implements Serializable
{
    private long Id = -1; //唯一标识
    private String sSerialNo = ""; //序号（流水）
    private String sTransNo = ""; //交易号
    private long nTransactoinTypeID = -1; //交易类型
    private long nTransInvestmentTypeID = -1; //台账类型 
    private String sContractNo = ""; //转贴现合同编号
    private String sCounterPartNo = ""; //交易对手
    private String sDiscountBillNo = ""; //汇票号码
    private String sBillAcceptanceUser = ""; //汇票承兑人
    private String sAcceptanceUserBank = ""; //承兑人开户银行
    private String sAcceptanceUserAccount = ""; //承兑人账号
    private long nDiscountBillTypeID = -1; //汇票类型（银票/商票）
    private long nDiscountType1 = -1; //买入/卖出
    private long nDiscountType2 = -1; //买断/回购
    private  Timestamp dtDiscountDate = null; //转贴现日期
    private double nMonthInterest = 0.0; //转贴现利率（月）
    private  Timestamp dtBuyEndDate = null; //回购到期日
    private  Timestamp dtBillEndDate  = null; //票据到期日
    private long nDiscountDays = -1; //转贴天数
    private double mBillAmount = 0.0; //票面金额
    private double mDiscountPayAmount =0.0; //转贴实际收取/支付金额
    private String sAccountBankNo = ""; //开户银行
    private String sAccountNo = ""; //账号
    private long nIsRepurchase = -1; //是否已经回购
    private long nIsRepayment  = -1; //是否已经到期收回
    private String sRemark = ""; //备注
    private long nborrowTypeID = -1; //拆借类别
    private  Timestamp dtBorrowDate = null; //拆入/拆出时间
    private double mBorrowAmount = 0.0; //拆入/拆出金额
    private  Timestamp dtPayDate  = null; //返款时间
    private long nInterestDays = -1; //天数
    private double mInterestRate = 0.0; //利率 ----------
    private double mInterestAmount = 0.0; //利息额
    private double mPayAmountCJ = 0.0; //返款金额
    private long nIsPaid = -1; //是否已经返款
    private String sDealNo = ""; //成交单编号
    private String sProductType = ""; //产品品种
    private String sProductName = ""; //产品名称
    private  Timestamp dtDealDate = null; //成交日期
    private double mPayAmountQ = 0.0; //实际收付金额
    private long nBondValue = -1; //债券现值
    private long nBondQuantity = -1; //债券数量
    private  Timestamp dtInterestStart = null; //回购起息日
    private  Timestamp dtInterestEnd = null; //回购到期日
    private long nBuyDaysJQ = -1; //回购天数JQ_nBuyDays
    private double nBuyAmount = 0.0; //回购金额
    private String sOperator = ""; //负责人
    private long bBought = -1; //是否已经购回
    private long nProductValue = -1; //产品现值
    private long nProductQuantity = -1; //产品数量
    private long nShareHolderAccountID = -1; //股东账户编码
    private long nFundAccountID = -1; //资金账户编码
    private String sBuyContract = ""; //回购合同编号
    private String sLoanPart = ""; //原合同贷款单位
    private  Timestamp dtLoanStartDate = null; //原合同借款日期
    private  Timestamp dtLoanEndDate = null; //原合同借款到期日
    private double mLoanAmount = 0.0; //原合同借款金额
    private double mLoanBlance = 0.0; //原合同贷款余额
    private  Timestamp  dtLoanBlanceEndDate = null; //原合同贷款余额到期日
    private  Timestamp dtRepurchaseDate = null; //回购日
    private  Timestamp dtBuyDate = null; //购回日
    private long nBuyDaysD = -1; //回购天数D_nBuyDays
    private double mBuyAmount = 0.0; //购回金额
    private Timestamp dtInputDate = null; //录入日期
    private long nStatusID = -1; //	Number	状态   
   
  
    private long nOfficeId = -1;	
    private long nCurrencyId = -1;
    private long nSubinvestMentTypeId = -1;
    
    
    
    
    /**
     * @return Returns the nOfficeId.
     */
    public long getNOfficeId()
    {
        return nOfficeId;
    }
    /**
     * @param bought The nOfficeId to set.
     */
    public void setNOfficeId(long OfficeID)
    {
    
        putUsedField("nOfficeId", OfficeID);
        nOfficeId = OfficeID;
    }
    
    /**
     * @return Returns the nCurrencyId.
     */
	 public long getNCurrencyId()
	    {    
	        return nCurrencyId;
	    }
	    /**
	     * @param bought The CurrencyId to set.
	     */
	  public void setNCurrencyId(long CurrencyId)
	   {
	       putUsedField("nCurrencyId", CurrencyId);
	       nCurrencyId = CurrencyId;
	    }  
	  /**
	     * @return Returns the nSubinvestMentTypeId.
	     */
      public long getNSubinvestMentTypeId()
      {
         return nSubinvestMentTypeId;
       }
    /**
     * @param bought The SubinvestMentTypeId to set.
     */
       public void setNSubinvestMentTypeId(long SubinvestMentTypeId)
       {
        putUsedField("nSubinvestMentTypeId", SubinvestMentTypeId);
        nSubinvestMentTypeId = SubinvestMentTypeId;
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
        putUsedField("JQ_bBought", bought);
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
        putUsedField("Z_dtBillEndDate", dtBillEndDate);
        this.dtBillEndDate = dtBillEndDate;
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
        putUsedField("C_dtBorrowDate", dtBorrowDate);
        this.dtBorrowDate = dtBorrowDate;
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
        putUsedField("D_dtBuyDate", dtBuyDate);
        this.dtBuyDate = dtBuyDate;
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
        putUsedField("Z_dtBuyEndDate", dtBuyEndDate);
        this.dtBuyEndDate = dtBuyEndDate;
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
        putUsedField("Q_dtDealDate", dtDealDate);
        this.dtDealDate = dtDealDate;
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
        putUsedField("Z_dtDiscountDate", dtDiscountDate);
        this.dtDiscountDate = dtDiscountDate;
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
        putUsedField("dtInputDate", dtInputDate);
        this.dtInputDate = dtInputDate;
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
        putUsedField("JQ_dtInterestEnd", dtInterestEnd);
        this.dtInterestEnd = dtInterestEnd;
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
        putUsedField("JQ_dtInterestStart", dtInterestStart);
       this.dtInterestStart = dtInterestStart;
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
        putUsedField("D_dtLoanBlanceEndDate", dtLoanBlanceEndDate);
        this.dtLoanBlanceEndDate = dtLoanBlanceEndDate;
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
        putUsedField("D_dtLoanEndDate", dtLoanEndDate);
        this.dtLoanEndDate = dtLoanEndDate;
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
        putUsedField("D_dtLoanStartDate", dtLoanStartDate);
        this.dtLoanStartDate = dtLoanStartDate;
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
        putUsedField("C_dtPayDate", dtPayDate);
        this.dtPayDate = dtPayDate;
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
        putUsedField("D_dtRepurchaseDate", dtRepurchaseDate);
        this.dtRepurchaseDate = dtRepurchaseDate;
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
        putUsedField("id", id);
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
        putUsedField("Z_mBillAmount", billAmount);
        mBillAmount = billAmount;
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
        putUsedField("C_mBorrowAmount", borrowAmount);
        mBorrowAmount = borrowAmount;
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
        putUsedField("D_mBuyAmount", buyAmount);
        mBuyAmount = buyAmount;
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
        putUsedField("Z_mDiscountPayAmount", discountPayAmount);
        mDiscountPayAmount = discountPayAmount;
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
        putUsedField("mInterestAmount", interestAmount);
        mInterestAmount = interestAmount;
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
        putUsedField("mInterestRate", interestRate);
        mInterestRate = interestRate;
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
        putUsedField("D_mLoanAmount", loanAmount);
        mLoanAmount = loanAmount;
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
        putUsedField("D_mLoanBlance", loanBlance);
        mLoanBlance = loanBlance;
    }
    /**
     * @return Returns the mPayAmount.
     */
    public double getMPayAmountQ()
    {
        return mPayAmountQ;
    }
    /**
     * @param payAmount The mPayAmount to set.
     */
    public void setMPayAmountQ(double payAmountQ)
    {
        putUsedField("Q_mPayAmount", payAmountQ);
        mPayAmountQ = payAmountQ;
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
        putUsedField("C_mPayAmount", payAmountCJ);
        mPayAmountCJ = payAmountCJ;
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
        putUsedField("JQ_nBondQuantity", bondQuantity);
        nBondQuantity = bondQuantity;
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
        putUsedField("JQ_nBondValue", bondValue);
        nBondValue = bondValue;
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
        putUsedField("C_nborrowTypeID", nborrowTypeID);
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
        putUsedField("JQ_nBuyAmount", buyAmount);
        nBuyAmount = buyAmount;
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
        putUsedField("D_nBuyDays", buyDaysD);
        nBuyDaysD = buyDaysD;
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
        putUsedField("JQ_nBuyDays", buyDaysJQ);
        nBuyDaysJQ = buyDaysJQ;
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
        putUsedField("Z_nDiscountBillTypeID", discountBillTypeID);
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
        putUsedField("Z_nDiscountDays", discountDays);
        nDiscountDays = discountDays;
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
        putUsedField("Z_nDiscountType1", discountType1);
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
        putUsedField("Z_nDiscountType2", discountType2);
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
        putUsedField("JXQ_nFundAccountID", fundAccountID);
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
        putUsedField("nInterestDays", interestDays);
        nInterestDays = interestDays;
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
        putUsedField("C_nIsPaid", isPaid);
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
        putUsedField("Z_nIsRepayment", isRepayment);
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
        putUsedField("Z_nIsRepurchase", isRepurchase);
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
        putUsedField("Z_nMonthInterest", monthInterest);
        nMonthInterest = monthInterest;
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
        putUsedField("XQ_nProductQuantity", productQuantity);
        nProductQuantity = productQuantity;
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
        putUsedField("XQ_nProductValue", productValue);
        nProductValue = productValue;
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
        putUsedField("JXQ_nShareHolderAccountID", shareHolderAccountID);
        nShareHolderAccountID = shareHolderAccountID;
    }
    /**
     * @return Returns the nStatusID.
     */
    public long getNStatusID()
    {
        return nStatusID;
    }
    /**
     * @param statusID The nStatusID to set.
     */
    public void setNStatusID(long statusID)
    {
        putUsedField("nStatusID", statusID);
        nStatusID = statusID;
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
        putUsedField("nTransactoinTypeID", transactoinTypeID);
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
        putUsedField("nTransInvestmentTypeID", transInvestmentTypeID);
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
        putUsedField("Z_sAcceptanceUserAccount", acceptanceUserAccount);
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
        putUsedField("Z_sAcceptanceUserBank", acceptanceUserBank);
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
        putUsedField("Z_sAccountBankNo", accountBankNo);
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
        putUsedField("Z_sAccountNo", accountNo);
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
        putUsedField("Z_sBillAcceptanceUser", billAcceptanceUser);
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
        putUsedField("D_sBuyContract", buyContract);
        sBuyContract = buyContract;
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
        putUsedField("Z_sContractNo", contractNo);
        sContractNo = contractNo;
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
        putUsedField("sCounterPartNo", counterPartNo);
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
        putUsedField("Q_sDealNo", dealNo);
        sDealNo = dealNo;
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
        putUsedField("Z_sDiscountBillNo", discountBillNo);
        sDiscountBillNo = discountBillNo;
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
        putUsedField("D_sLoanPart", loanPart);
        sLoanPart = loanPart;
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
        putUsedField("sOperator", operator);
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
        putUsedField("Q_sProductName", productName);
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
        putUsedField("Q_sProductType", productType);
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
        putUsedField("sRemark", remark);
        sRemark = remark;
    }
    /**
     * @return Returns the sSerialNo.
     */
    public String getSSerialNo()
    {
        return sSerialNo;
    }
    /**
     * @param serialNo The sSerialNo to set.
     */
    public void setSSerialNo(String serialNo)
    {
        putUsedField("sSerialNo", serialNo);
        sSerialNo = serialNo;
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
        putUsedField("C_sTransNo", transNo);
        sTransNo = transNo;
    }
    
    /**@author Barry
	 * 2004-1-9
	 * 从request中得到完整的dataentity
	 * 修改人：jsxie
	 * 覆盖父类的方法，主要是因为通过反射机制获取类的属性名时，大小写变了，而且没有规律，
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
			throw new ITreasuryException("Java Bean.内省异常发生",e);			
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
						throw new ITreasuryException("从request中获得dataentity错误",e);
					}
					catch (IllegalAccessException e)
					{
						// TODO Auto-generated catch block
						throw new ITreasuryException("从request中获得dataentity错误",e);
					}
					catch(InvocationTargetException e){
						throw new ITreasuryException("从request中获得dataentity错误",e);
					}
				}
			}
		}
	}
    
}
