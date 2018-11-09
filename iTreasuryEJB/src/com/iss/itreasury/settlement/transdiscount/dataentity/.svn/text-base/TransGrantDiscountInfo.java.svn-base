/*
 * Created on 2003-10-14
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.settlement.transdiscount.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;

import com.iss.itreasury.system.approval.dataentity.InutParameterInfo;

/**
 * Title:        		iTreasury
 * Description:         refer to the table "sett_TransGrantDiscount"
 * Copyright:           Copyright (c) 2003
 * Company:             iSoftStone
 * @author              yfsu 
 * @version
 *  Date of Creation    2003-10-14
 */
public class TransGrantDiscountInfo implements Serializable
{
	private boolean isPayForm = false;//true表示放款通知单方式

	private long ID = -1; // 贴现发放业务ID
	private long OfficeID = -1; //	办事处		
	private long CurrencyID = -1; // 币种			
	private String TransNo = ""; //交易号			
	private long TransactionTypeID = -1; //交易类型

	private long DiscountAccountID = -1; //	贴现账户		
	private long DiscountContractID = -1; //	贴现合同号		
	private long DiscountNoteID = -1; //	贴现凭证		
	private double DiscountBillAmount = 0.0; //	汇票金额		
	private double DiscountAmount = 0.0; //	贴现金额		
	private long DepositAccountID = -1; //	贴现转至活期账户ID		
	private long PayTypeID = -1; //	贴现付款方式
	private long BankID = -1; //	汇出行ID		
	private String ExtAcctNo = ""; //	外部账户号				
	private String ExtAcctName = ""; //	外部客户名称			
	private String BankName = ""; //	外部汇入行名称			
	private String Province = ""; //汇入行省			
	private String City = ""; //	汇入行市			
	private long CashFlowID = -1; //	现金流向		
	private double Interest = 0.0; //	利息		
	private Timestamp InterestStartDate = null; //	起息日			
	private Timestamp ExecuteDate = null; //	执行日			
	private Timestamp ModifyDate = null; //	修改时间：年月日时分秒				
	private Timestamp InputDate = null; //	录入日期			
	private long InputUserID = -1; //	录入人		
	private long CheckUserID = -1; //	复核人		
	private String Abstract = ""; // 摘要			
	private String CheckAbstract = ""; //	取消复核摘要			
	private long StatusID = -1; //	交易状态
	private double mBankAcceptanceAmount = 0.0;
	private double mTradeAcceptanceAmount = 0.0;
	private long nAbstract=-1; //贴现ID
	
	//买方付息
	private long SignBillClientID = -1;//出票客户ID
	
	private String SignBillClientName = "";//出票客户名称
	private String SignBillAccountName = "";//贴现单位账号
	private String SignBillBankName = "";//贴现单位开户银行
	
	private long SignBillAccountID = -1;//出票客户活期账户
	private double InterestOfSign = 0.0;//出票人承担利息
	private double InterestOfDiscount = 0.0;//贴现人承担利息 
	private double PurchaseInterestRate = 0.0;//买方付息比例
	
	//Added by zwsun, 2007-06-20, 审批流
	private InutParameterInfo inutParameterInfo=null;

	public String getSignBillAccountName() {
		return SignBillAccountName;
	}

	public void setSignBillAccountName(String signBillAccountName) {
		SignBillAccountName = signBillAccountName;
	}

	public String getSignBillBankName() {
		return SignBillBankName;
	}

	public void setSignBillBankName(String signBillBankName) {
		SignBillBankName = signBillBankName;
	}

	public InutParameterInfo getInutParameterInfo() {
		return inutParameterInfo;
	}

	public void setInutParameterInfo(InutParameterInfo inutParameterInfo) {
		this.inutParameterInfo = inutParameterInfo;
	}

	/**
	 * @return Abstract
	 */
	public String getAbstract()
	{
		return Abstract;
	}

	/** 
	 * @return BankID
	 */
	public long getBankID()
	{
		return BankID;
	}

	/**
	 * @return BankName
	 */
	public String getBankName()
	{
		return BankName;
	}

	/**
	 * @return CashFlowID
	 */
	public long getCashFlowID()
	{
		return CashFlowID;
	}

	/**
	 * @return CheckAbstract
	 */
	public String getCheckAbstract()
	{
		return CheckAbstract;
	}

	/**
	 * @return CheckUserID
	 */
	public long getCheckUserID()
	{
		return CheckUserID;
	}

	/**
	 * @return City
	 */
	public String getCity()
	{
		return City;
	}

	/**
	 * @return CurrencyID
	 */
	public long getCurrencyID()
	{
		return CurrencyID;
	}

	/**
	 * @return DepositAccountID
	 */
	public long getDepositAccountID()
	{
		return DepositAccountID;
	}

	/**
	 * @return DiscountAccountID
	 */
	public long getDiscountAccountID()
	{
		return DiscountAccountID;
	}

	/**
	 * @return DiscountAmount
	 */
	public double getDiscountAmount()
	{
		return DiscountAmount;
	}

	/**
	 * @return DiscountBillAmount
	 */
	public double getDiscountBillAmount()
	{
		return DiscountBillAmount;
	}

	/**
	 * @return DiscountContractID
	 */
	public long getDiscountContractID()
	{
		return DiscountContractID;
	}

	/**
	 * @return DiscountNoteID
	 */
	public long getDiscountNoteID()
	{
		return DiscountNoteID;
	}

	/**
	 * @return ExecuteDate
	 */
	public Timestamp getExecuteDate()
	{
		return ExecuteDate;
	}

	/**
	 * @return ExtAcctName
	 */
	public String getExtAcctName()
	{
		return ExtAcctName;
	}

	/**
	 * @return ExtAcctNo
	 */
	public String getExtAcctNo()
	{
		return ExtAcctNo;
	}

	/**
	 * @return ID
	 */
	public long getID()
	{
		return ID;
	}

	/**
	 * @return InputDate
	 */
	public Timestamp getInputDate()
	{
		return InputDate;
	}

	/**
	 * @return InputUserID
	 */
	public long getInputUserID()
	{
		return InputUserID;
	}

	/**
	 * @return Interest
	 */
	public double getInterest()
	{
		return Interest;
	}

	/**
	 * @return InterestStartDate
	 */
	public Timestamp getInterestStartDate()
	{
		return InterestStartDate;
	}

	/**
	 * @return ModifyDate
	 */
	public Timestamp getModifyDate()
	{
		return ModifyDate;
	}

	/**
	 * @return OfficeID
	 */
	public long getOfficeID()
	{
		return OfficeID;
	}

	/**
	 * @return PayTypeID
	 */
	public long getPayTypeID()
	{
		return PayTypeID;
	}

	/**
	 * @return Province
	 */
	public String getProvince()
	{
		return Province;
	}

	/**
	 * @return StatusID
	 */
	public long getStatusID()
	{
		return StatusID;
	}

	/**
	 * @return TransactionTypeID
	 */
	public long getTransactionTypeID()
	{
		return TransactionTypeID;
	}

	/**
	 * @return TransNo
	 */
	public String getTransNo()
	{
		return TransNo;
	}

	/**
	 * @param string
	 */
	public void setAbstract(String string)
	{
		Abstract = string;
	}

	/**
	 * @param l
	 */
	public void setBankID(long l)
	{
		BankID = l;
	}

	/**
	 * @param string
	 */
	public void setBankName(String string)
	{
		BankName = string;
	}

	/**
	 * @param l
	 */
	public void setCashFlowID(long l)
	{
		CashFlowID = l;
	}

	/**
	 * @param string
	 */
	public void setCheckAbstract(String string)
	{
		CheckAbstract = string;
	}

	/**
	 * @param l
	 */
	public void setCheckUserID(long l)
	{
		CheckUserID = l;
	}

	/**
	 * @param string
	 */
	public void setCity(String string)
	{
		City = string;
	}

	/**
	 * @param l
	 */
	public void setCurrencyID(long l)
	{
		CurrencyID = l;
	}

	/**
	 * @param l
	 */
	public void setDepositAccountID(long l)
	{
		DepositAccountID = l;
	}

	/**
	 * @param l
	 */
	public void setDiscountAccountID(long l)
	{
		DiscountAccountID = l;
	}

	/**
	 * @param d
	 */
	public void setDiscountAmount(double d)
	{
		DiscountAmount = d;
	}

	/**
	 * @param d
	 */
	public void setDiscountBillAmount(double d)
	{
		DiscountBillAmount = d;
	}

	/**
	 * @param l
	 */
	public void setDiscountContractID(long l)
	{
		DiscountContractID = l;
	}

	/**
	 * @param l
	 */
	public void setDiscountNoteID(long l)
	{
		DiscountNoteID = l;
	}

	/**
	 * @param timestamp
	 */
	public void setExecuteDate(Timestamp timestamp)
	{
		ExecuteDate = timestamp;
	}

	/**
	 * @param string
	 */
	public void setExtAcctName(String string)
	{
		ExtAcctName = string;
	}

	/**
	 * @param string
	 */
	public void setExtAcctNo(String string)
	{
		ExtAcctNo = string;
	}

	/**
	 * @param l
	 */
	public void setID(long l)
	{
		ID = l;
	}

	/**
	 * @param timestamp
	 */
	public void setInputDate(Timestamp timestamp)
	{
		InputDate = timestamp;
	}

	/**
	 * @param l
	 */
	public void setInputUserID(long l)
	{
		InputUserID = l;
	}

	/**
	 * @param d
	 */
	public void setInterest(double d)
	{
		Interest = d;
	}

	/**
	 * @param timestamp
	 */
	public void setInterestStartDate(Timestamp timestamp)
	{
		InterestStartDate = timestamp;
	}

	/**
	 * @param timestamp
	 */
	public void setModifyDate(Timestamp timestamp)
	{
		ModifyDate = timestamp;
	}

	/**
	 * @param l
	 */
	public void setOfficeID(long l)
	{
		OfficeID = l;
	}

	/**
	 * @param l
	 */
	public void setPayTypeID(long l)
	{
		PayTypeID = l;
	}

	/**
	 * @param string
	 */
	public void setProvince(String string)
	{
		Province = string;
	}

	/**
	 * @param l
	 */
	public void setStatusID(long l)
	{
		StatusID = l;
	}

	/**
	 * @param l
	 */
	public void setTransactionTypeID(long l)
	{
		TransactionTypeID = l;
	}

	/**
	 * @param string
	 */
	public void setTransNo(String string)
	{
		TransNo = string;
	}

	/**
	 * @return
	 */
	public double getMBankAcceptanceAmount()
	{
		return mBankAcceptanceAmount;
	}

	/**
	 * @return
	 */
	public double getMTradeAcceptanceAmount()
	{
		return mTradeAcceptanceAmount;
	}

	/**
	 * @param d
	 */
	public void setMBankAcceptanceAmount(double d)
	{
		mBankAcceptanceAmount = d;
	}

	/**
	 * @param d
	 */
	public void setMTradeAcceptanceAmount(double d)
	{
		mTradeAcceptanceAmount = d;
	}

/*
	//test
	private void test()
	{
		ID = -1; // 贴现发放业务ID
		OfficeID = -1; //	办事处		
		CurrencyID = -1; // 币种			
		TransNo = ""; //交易号			
		TransactionTypeID = -1; //交易类型
		DiscountAccountID = -1; //	贴现账户		
		DiscountContractID = -1; //	贴现合同号		
		DiscountNoteID = -1; //	贴现凭证		
		DiscountBillAmount = 0.0; //	汇票金额		
		DiscountAmount = 0.0; //	贴现金额		
		DepositAccountID = -1; //	贴现转至活期账户ID		
		PayTypeID = -1; //	贴现付款方式
		BankID = -1; //	汇出行ID		
		ExtAcctNo = ""; //	外部账户号				
		ExtAcctName = ""; //	外部客户名称			
		BankName = ""; //	外部汇入行名称			
		Province = ""; //汇入行省			
		City = ""; //	汇入行市			
		CashFlowID = -1; //	现金流向		
		Interest = 0.0; //	利息		
		InterestStartDate = null; //	起息日			
		ExecuteDate = null; //	执行日			
		ModifyDate = null; //	修改时间：年月日时分秒			
		InputDate = null; //	录入日期			
		InputUserID = -1; //	录入人		
		CheckUserID = -1; //	复核人		
		Abstract = ""; // 摘要			
		CheckAbstract = ""; //	取消复核摘要			
		StatusID = -1; //	交易状态
		mBankAcceptanceAmount = 0.0;
		mTradeAcceptanceAmount = 0.0;
		
		

		ID = this.getID();
		OfficeID = this.getOfficeID();
		CurrencyID = this.getCurrencyID();
		TransNo = this.getTransNo();		
		TransactionTypeID = this.getTransactionTypeID();
		DiscountAccountID = this.getDiscountAccountID();		
		DiscountContractID = this.getDiscountContractID();
		DiscountNoteID = this.getDiscountNoteID();		
		DiscountBillAmount = this.getDiscountBillAmount();		
		DiscountAmount = this.getDiscountAmount();		
		DepositAccountID = this.getDepositAccountID();	
		PayTypeID = this.getPayTypeID();
		BankID = this.getBankID();		
		ExtAcctNo = this.getExtAcctNo();				
		ExtAcctName = this.getExtAcctName();			
		BankName = this.getBankName();
		Province = this.getProvince();			
		City = this.getCity();		
		CashFlowID = this.getCashFlowID();		
		Interest = this.getInterest();
		InterestStartDate = this.getInterestStartDate();	
		ExecuteDate = this.getExecuteDate();			
		ModifyDate = this.getModifyDate();			
		InputDate = this.getInputDate();			
		InputUserID = this.getInputUserID();	
		CheckUserID = this.getCheckUserID();	
		Abstract = this.getAbstract();	
		CheckAbstract = this.getCheckAbstract();			
		StatusID = this.getStatusID();
		mBankAcceptanceAmount = this.getMBankAcceptanceAmount();
		mTradeAcceptanceAmount = this.getMTradeAcceptanceAmount();

	}
	*/

	/**
	 * @return
	 */
	public long getNAbstract()
	{
		return nAbstract;
	}

	/**
	 * @param l
	 */
	public void setNAbstract(long l)
	{
		nAbstract = l;
	}

	/**
	 * @return Returns the interestOfDiscount.
	 */
	public double getInterestOfDiscount()
	{
		return InterestOfDiscount;
	}
	/**
	 * @param interestOfDiscount The interestOfDiscount to set.
	 */
	public void setInterestOfDiscount(double interestOfDiscount)
	{
		InterestOfDiscount = interestOfDiscount;
	}
	/**
	 * @return Returns the interestOfSign.
	 */
	public double getInterestOfSign()
	{
		return InterestOfSign;
	}
	/**
	 * @param interestOfSign The interestOfSign to set.
	 */
	public void setInterestOfSign(double interestOfSign)
	{
		InterestOfSign = interestOfSign;
	}
	/**
	 * @return Returns the signBillAccountID.
	 */
	public long getSignBillAccountID()
	{
		return SignBillAccountID;
	}
	/**
	 * @param signBillAccountID The signBillAccountID to set.
	 */
	public void setSignBillAccountID(long signBillAccountID)
	{
		SignBillAccountID = signBillAccountID;
	}
	/**
	 * @return Returns the signBillClientID.
	 */
	public long getSignBillClientID()
	{
		return SignBillClientID;
	}
	/**
	 * @param signBillClientID The signBillClientID to set.
	 */
	public void setSignBillClientID(long signBillClientID)
	{
		SignBillClientID = signBillClientID;
	}
	/**
	 * @return Returns the signBillClientName.
	 */
	public String getSignBillClientName()
	{
		return SignBillClientName;
	}
	/**
	 * @param signBillClientName The signBillClientName to set.
	 */
	public void setSignBillClientName(String signBillClientName)
	{
		SignBillClientName = signBillClientName;
	}
	/**
	 * @return Returns the purchaseInterestRate.
	 */
	public double getPurchaseInterestRate()
	{
		return PurchaseInterestRate;
	}
	/**
	 * @param purchaseInterestRate The purchaseInterestRate to set.
	 */
	public void setPurchaseInterestRate(double purchaseInterestRate)
	{
		PurchaseInterestRate = purchaseInterestRate;
	}

	public boolean isPayForm() {
		return isPayForm;
	}

	public void setPayForm(boolean isPayForm) {
		this.isPayForm = isPayForm;
	}
}
