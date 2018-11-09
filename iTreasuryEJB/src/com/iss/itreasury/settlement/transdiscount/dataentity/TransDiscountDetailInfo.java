/*
 * Created on 2008-1-10
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
 * Copyright:           Copyright (c) 2003mDiscountBillAmount
 * Company:             iSoftStone
 * @author              yfsu 
 * @version
 *  Date of Creation    2003-10-14
 */
public class TransDiscountDetailInfo implements Serializable
{
	private long ID = -1; // 转贴现发放业务ID
	private long nOfficeID = -1; //	办事处		
	private long nCurrencyID = -1; // 币种			
	private String sTransNo = ""; //交易号			
	private long nTransactionTypeID = -1; //交易类型
	private long nDiscountCounterPartid =-1;//交易对手
	private long nInOrOut = -1;//买入还是卖出
	private long nDiscountTypeId = -1;//买断还是回购
	private long DiscountAccountID = -1; //	贴现账户	
	private long nDiscountContractID = -1; //	转贴现合同号		
	private long nDiscountCredence = -1; //	转贴现凭证		
	private double mDiscountBillAmount = 0.0; //	汇票金额		
	private double DiscountCredenceAmount = 0.0; //	转贴现凭证金额		
	private long nDepositAccountID = -1; //	贴现转至活期账户ID		
	private long PayTypeID = -1; //	贴现付款方式
	private long nReceiveClientID = -1;//收款方客户ID
	private long nReceiveAccountID = -1;//收款放帐户ID
	private long nPayClientID = -1;//付款方ID
	private long nPayAccountID = -1 ;//付款方帐户ID
	
	private long nBankID = -1; //	汇出行ID		
	private String sExtAccountNo = ""; //	外部账户号				
	private String sExtClientName = ""; //	外部客户名称			
	private String sRemitinBank = ""; //	外部汇入行名称			
	private String sRemitinProvince = ""; //汇入行省			
	private String sRemitinCity = ""; //	汇入行市			
	private long nCashFlowID = -1; //	现金流向		
	private double mInterest = 0.0; //	利息		
	private Timestamp dtInterestStart = null; //	起息日			
	private Timestamp dtExecute = null; //	执行日			
	private Timestamp dtModify = null; //	修改时间：年月日时分秒				
	private Timestamp dtInput = null; //	录入日期			
	private long nInputUserID = -1; //	录入人		
	private long nCheckUserID = -1; //	复核人
	private long nAbstractID = -1;//摘要ID
	private String sAbstract = ""; // 摘要			
	private String sConfirmAbstract = ""; //	取消复核摘要
	private String sCancleAbstract = ""; //	取消复核摘要		
	private long nStatusID = -1; //	交易状态
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
	 * @return DiscountAccountID
	 */
	public long getDiscountAccountID()
	{
		return DiscountAccountID;
	}

	

	

	

	
	/**
	 * @return ID
	 */
	public long getID()
	{
		return ID;
	}

	

	

	

	/**
	 * @return PayTypeID
	 */
	public long getPayTypeID()
	{
		return PayTypeID;
	}

	/**
	 * @param l
	 */
	public void setDiscountAccountID(long l)
	{
		DiscountAccountID = l;
	}

	

	

	

	
	/**
	 * @param l
	 */
	public void setID(long l)
	{
		ID = l;
	}

	
	

	

	/**
	 * @param l
	 */
	public void setPayTypeID(long l)
	{
		PayTypeID = l;
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

	public long getNDiscountTypeId() {
		return nDiscountTypeId;
	}

	public void setNDiscountTypeId(long discountTypeId) {
		nDiscountTypeId = discountTypeId;
	}

	public long getNInOrOut() {
		return nInOrOut;
	}

	public void setNInOrOut(long inOrOut) {
		nInOrOut = inOrOut;
	}

	public double getDiscountCredenceAmount() {
		return DiscountCredenceAmount;
	}

	public void setDiscountCredenceAmount(double discountCredenceAmount) {
		DiscountCredenceAmount = discountCredenceAmount;
	}

	public Timestamp getDtExecute() {
		return dtExecute;
	}

	public void setDtExecute(Timestamp dtExecute) {
		this.dtExecute = dtExecute;
	}

	public Timestamp getDtInput() {
		return dtInput;
	}

	public void setDtInput(Timestamp dtInput) {
		this.dtInput = dtInput;
	}

	public Timestamp getDtInterestStart() {
		return dtInterestStart;
	}

	public void setDtInterestStart(Timestamp dtInterestStart) {
		this.dtInterestStart = dtInterestStart;
	}

	public Timestamp getDtModify() {
		return dtModify;
	}

	public void setDtModify(Timestamp dtModify) {
		this.dtModify = dtModify;
	}

	public double getMInterest() {
		return mInterest;
	}

	public void setMInterest(double interest) {
		mInterest = interest;
	}

	public long getNAbstractID() {
		return nAbstractID;
	}

	public void setNAbstractID(long abstractID) {
		nAbstractID = abstractID;
	}

	public long getNBankID() {
		return nBankID;
	}

	public void setNBankID(long bankID) {
		nBankID = bankID;
	}

	public long getNCashFlowID() {
		return nCashFlowID;
	}

	public void setNCashFlowID(long cashFlowID) {
		nCashFlowID = cashFlowID;
	}

	public long getNCheckUserID() {
		return nCheckUserID;
	}

	public void setNCheckUserID(long checkUserID) {
		nCheckUserID = checkUserID;
	}

	public long getNCurrencyID() {
		return nCurrencyID;
	}

	public void setNCurrencyID(long currencyID) {
		nCurrencyID = currencyID;
	}

	public long getNDepositAccountID() {
		return nDepositAccountID;
	}

	public void setNDepositAccountID(long depositAccountID) {
		nDepositAccountID = depositAccountID;
	}

	public double getMDiscountBillAmount() {
		return mDiscountBillAmount;
	}

	public void setMDiscountBillAmount(double discountBillAmount) {
		mDiscountBillAmount = discountBillAmount;
	}

	public long getNDiscountContractID() {
		return nDiscountContractID;
	}

	public void setNDiscountContractID(long discountContractID) {
		nDiscountContractID = discountContractID;
	}

	public long getNDiscountCounterPartid() {
		return nDiscountCounterPartid;
	}

	public void setNDiscountCounterPartid(long discountCounterPartid) {
		nDiscountCounterPartid = discountCounterPartid;
	}

	public long getNDiscountCredence() {
		return nDiscountCredence;
	}

	public void setNDiscountCredence(long discountCredence) {
		nDiscountCredence = discountCredence;
	}

	public long getNInputUserID() {
		return nInputUserID;
	}

	public void setNInputUserID(long inputUserID) {
		nInputUserID = inputUserID;
	}

	public long getNOfficeID() {
		return nOfficeID;
	}

	public void setNOfficeID(long officeID) {
		nOfficeID = officeID;
	}

	public long getNPayAccountID() {
		return nPayAccountID;
	}

	public void setNPayAccountID(long payAccountID) {
		nPayAccountID = payAccountID;
	}

	public long getNPayClientID() {
		return nPayClientID;
	}

	public void setNPayClientID(long payClient) {
		nPayClientID = payClient;
	}

	public long getNReceiveAccountID() {
		return nReceiveAccountID;
	}

	public void setNReceiveAccountID(long receiveAccountID) {
		nReceiveAccountID = receiveAccountID;
	}

	public long getNReceiveClientID() {
		return nReceiveClientID;
	}

	public void setNReceiveClientID(long receiveClientID) {
		nReceiveClientID = receiveClientID;
	}

	public long getNStatusID() {
		return nStatusID;
	}

	public void setNStatusID(long statusID) {
		nStatusID = statusID;
	}

	public long getNTransactionTypeID() {
		return nTransactionTypeID;
	}

	public void setNTransactionTypeID(long transactionTypeID) {
		nTransactionTypeID = transactionTypeID;
	}

	public String getSAbstract() {
		return sAbstract;
	}

	public void setSAbstract(String abstract1) {
		sAbstract = abstract1;
	}

	public String getSCancleAbstract() {
		return sCancleAbstract;
	}

	public void setSCancleAbstract(String cancleAbstract) {
		sCancleAbstract = cancleAbstract;
	}

	public String getSExtAccountNo() {
		return sExtAccountNo;
	}

	public void setSExtAccountNo(String extAccountNo) {
		sExtAccountNo = extAccountNo;
	}

	public String getSExtClientName() {
		return sExtClientName;
	}

	public void setSExtClientName(String extClientName) {
		sExtClientName = extClientName;
	}

	public String getSRemitinBank() {
		return sRemitinBank;
	}

	public void setSRemitinBank(String remitinBank) {
		sRemitinBank = remitinBank;
	}

	public String getSRemitinCity() {
		return sRemitinCity;
	}

	public void setSRemitinCity(String remitinCity) {
		sRemitinCity = remitinCity;
	}

	public String getSRemitinProvince() {
		return sRemitinProvince;
	}

	public void setSRemitinProvince(String remitinProvince) {
		sRemitinProvince = remitinProvince;
	}

	public String getSTransNo() {
		return sTransNo;
	}

	public void setSTransNo(String transNo) {
		sTransNo = transNo;
	}
	public String getSConfirmAbstract() {
		return sConfirmAbstract;
	}

	public void setSConfirmAbstract(String ConfirmAbstract) {
		sConfirmAbstract = ConfirmAbstract;
	}
}
