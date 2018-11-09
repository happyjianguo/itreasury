package com.iss.itreasury.settlement.account.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Title:        		iTreasury
 * Description:         
 * Copyright:           Copyright (c) 2003
 * Company:             iSoftStone
 * @author             yehuang 
 * @version
 *  Date of Creation    2003-9-22
 */
public class TransAccountDetailInfo implements Serializable
{
	//PK
	private long id = -1;
	//	办事处编号ID
	private long officeID = -1;
	//币种
	private long currencyID = -1;
	//交易类型
	private long transactionTypeID = -1;
	//执行日
	private Timestamp dtExecute = null;
	//交易编号
	private String transNo = "";
	//交易账户ID
	private long transAccountID = -1;
	//交易子账户ID
	private long transSubAccountID = -1;	
	
	//交易发生额
	private double amount = 0.0;
	//交易方向
	private long transDirection = -1;
	//对方账户ID
	private long oppAccountID = -1;
	//对方子账户ID
	private long oppSubAccountID = -1;
	//起息日
	private Timestamp dtInterestStart = null;
	//摘要
	private String abstractStr = "";
	//交易状态	
	private long statusID = -1;
	//票据类型
	private long billTypeID = -1;
	//票据号
	private String billNo = "";
	//银行支票号
	private String bankChequeNo = "";
	//批组号
	private long group = -1;
	//利息倒填标志
	private long interestBackFlag = -1; 
	//摘要ID
	private long abstractID = -1;
	//序列号（一付多收和多笔贷款收回使用，Add by Forest）
	private long SerialNo = -1;	
	//预算项目ID（add by yliu）
	private long budgetItemID=-1;
	//预算状态ID（add by yliu）
	private long budgetStatusID=-1;
	
	
	
		
	//定期存单号(只作为账簿与账户参数传递使用，在数据库中没有对应的字段)
	private String fixedDepositNo = "";
	//放款通知单ID(只作为账簿与账户参数传递使用，在数据库中没有对应的字段)
	private long loanNoteID = -1;
	//一般交易都有保存　删除　复核　取消复核操作，因此在支取操作时要首先
	//减掉未复核金额，但是利息操作没有保存（即累计未复核金额操作），因此支取时不进行
	//扣除累计未复核金额操作(只作为账簿与账户参数传递使用，在数据库中没有对应的字段)
	private boolean isCommonOperation = true;
	
	//贴现票据id,为转贴现卖出到期收回冲销业务增加(2004年9月2日,gqzhang)
	private long discountBillID = -1;
	//对方账户号
	private String oppAccountNo = "";
	//对方账户名称信息
	private String oppAccountName = "";
	//金额类型
	private long amountType = -1;
	
	//利率
	private double dRate = 0.0;
	
	public double getRate() {
		return dRate;
	}

	public void setRate(double rate) {
		dRate = rate;
	}

	/**
	 * Returns the abstractStr.
	 * @return String
	 */
	public String getAbstractStr()
	{
		return abstractStr;
	}

	/**
	 * Returns the amount.
	 * @return long
	 */
	public double getAmount()
	{
		return amount;
	}

	/**
	 * Returns the bankChequeNo.
	 * @return long
	 */
	public String getBankChequeNo()
	{
		return bankChequeNo;
	}

	/**
	 * Returns the billNo.
	 * @return long
	 */
	public String getBillNo()
	{
		return billNo;
	}

	/**
	 * Returns the billTypeID.
	 * @return long
	 */
	public long getBillTypeID()
	{
		return billTypeID;
	}

	/**
	 * Returns the currencyID.
	 * @return long
	 */
	public long getCurrencyID()
	{
		return currencyID;
	}

	/**
	 * Returns the dtExecute.
	 * @return Timestamp
	 */
	public Timestamp getDtExecute()
	{
		return dtExecute;
	}

	/**
	 * Returns the dtInterestStart.
	 * @return Timestamp
	 */
	public Timestamp getDtInterestStart()
	{
		return dtInterestStart;
	}

	/**
	 * Returns the group.
	 * @return long
	 */
	public long getGroup()
	{
		return group;
	}

	/**
	 * Returns the id.
	 * @return long
	 */
	public long getId()
	{
		return id;
	}

	/**
	 * Returns the officeID.
	 * @return long
	 */
	public long getOfficeID()
	{
		return officeID;
	}

	/**
	 * Returns the oppAccountID.
	 * @return long
	 */
	public long getOppAccountID()
	{
		return oppAccountID;
	}

	/**
	 * Returns the statusID.
	 * @return long
	 */
	public long getStatusID()
	{
		return statusID;
	}

	/**
	 * Returns the transAccountID.
	 * @return long
	 */
	public long getTransAccountID()
	{
		return transAccountID;
	}

	/**
	 * Returns the transactionTypeID.
	 * @return long
	 */
	public long getTransactionTypeID()
	{
		return transactionTypeID;
	}

	/**
	 * Returns the transDirection.
	 * @return long
	 */
	public long getTransDirection()
	{
		return transDirection;
	}

	/**
	 * Returns the transNo.
	 * @return String
	 */
	public String getTransNo()
	{
		return transNo;
	}

	/**
	 * Sets the abstractStr.
	 * @param abstractStr The abstractStr to set
	 */
	public void setAbstractStr(String abstractStr)
	{
		this.abstractStr = abstractStr;
	}

	/**
	 * Sets the amount.
	 * @param amount The amount to set
	 */
	public void setAmount(double amount)
	{
		this.amount = amount;
	}

	/**
	 * Sets the bankChequeNo.
	 * @param bankChequeNo The bankChequeNo to set
	 */
	public void setBankChequeNo(String bankChequeNo)
	{
		this.bankChequeNo = bankChequeNo;
	}

	/**
	 * Sets the billNo.
	 * @param billNo The billNo to set
	 */
	public void setBillNo(String billNo)
	{
		this.billNo = billNo;
	}

	/**
	 * Sets the billTypeID.
	 * @param billTypeID The billTypeID to set
	 */
	public void setBillTypeID(long billTypeID)
	{
		this.billTypeID = billTypeID;
	}

	/**
	 * Sets the currencyID.
	 * @param currencyID The currencyID to set
	 */
	public void setCurrencyID(long currencyID)
	{
		this.currencyID = currencyID;
	}

	/**
	 * Sets the dtExecute.
	 * @param dtExecute The dtExecute to set
	 */
	public void setDtExecute(Timestamp dtExecute)
	{
		this.dtExecute = dtExecute;
	}

	/**
	 * Sets the dtInterestStart.
	 * @param dtInterestStart The dtInterestStart to set
	 */
	public void setDtInterestStart(Timestamp dtInterestStart)
	{
		this.dtInterestStart = dtInterestStart;
	}

	/**
	 * Sets the group.
	 * @param group The group to set
	 */
	public void setGroup(long group)
	{
		this.group = group;
	}

	/**
	 * Sets the id.
	 * @param id The id to set
	 */
	public void setId(long id)
	{
		this.id = id;
	}

	/**
	 * Sets the officeID.
	 * @param officeID The officeID to set
	 */
	public void setOfficeID(long officeID)
	{
		this.officeID = officeID;
	}

	/**
	 * Sets the oppAccountID.
	 * @param oppAccountID The oppAccountID to set
	 */
	public void setOppAccountID(long oppAccountID)
	{
		this.oppAccountID = oppAccountID;
	}

	/**
	 * Sets the statusID.
	 * @param statusID The statusID to set
	 */
	public void setStatusID(long statusID)
	{
		this.statusID = statusID;
	}

	/**
	 * Sets the transAccountID.
	 * @param transAccountID The transAccountID to set
	 */
	public void setTransAccountID(long transAccountID)
	{
		this.transAccountID = transAccountID;
	}

	/**
	 * Sets the transactionTypeID.
	 * @param transactionTypeID The transactionTypeID to set
	 */
	public void setTransactionTypeID(long transactionTypeID)
	{
		this.transactionTypeID = transactionTypeID;
	}

	/**
	 * Sets the transDirection.
	 * @param transDirection The transDirection to set
	 */
	public void setTransDirection(long transDirection)
	{
		this.transDirection = transDirection;
	}

	/**
	 * Sets the transNo.
	 * @param transNo The transNo to set
	 */
	public void setTransNo(String transNo)
	{
		this.transNo = transNo;
	}

	/**
	 * @return
	 */
	public long getOppSubAccountID()
	{
		return oppSubAccountID;
	}

	/**
	 * @return
	 */
	public long getTransSubAccountID()
	{
		return transSubAccountID;
	}

	/**
	 * @param l
	 */
	public void setOppSubAccountID(long l)
	{
		oppSubAccountID = l;
	}

	/**
	 * @param l
	 */
	public void setTransSubAccountID(long l)
	{
		transSubAccountID = l;
	}

	/**
	 * Returns the fixedDepositNo.
	 * @return String
	 */
	public String getFixedDepositNo() {
		return fixedDepositNo;
	}

	/**
	 * Returns the loanNoteID.
	 * @return long
	 */
	public long getLoanNoteID() {
		return loanNoteID;
	}

	/**
	 * Sets the fixedDepositNo.
	 * @param fixedDepositNo The fixedDepositNo to set
	 */
	public void setFixedDepositNo(String fixedDepositNo) {
		this.fixedDepositNo = fixedDepositNo;
	}

	/**
	 * Sets the loanNoteID.
	 * @param loanNoteID The loanNoteID to set
	 */
	public void setLoanNoteID(long loanNoteID) {
		this.loanNoteID = loanNoteID;
	}

	/**
	 * Returns the interestBackFlag.
	 * @return long
	 */
	public long getInterestBackFlag() {
		return interestBackFlag;
	}

	/**
	 * Sets the interestBackFlag.
	 * @param interestBackFlag The interestBackFlag to set
	 */
	public void setInterestBackFlag(long interestBackFlag) {
		this.interestBackFlag = interestBackFlag;
	}

	/**
	 * Returns the isCommonOperation.
	 * @return boolean
	 */
	public boolean isCommonOperation() {
		return isCommonOperation;
	}

	/**
	 * Sets the isCommonOperation.
	 * @param isCommonOperation The isCommonOperation to set
	 */
	public void setCommonOperation(boolean isCommonOperation) {
		this.isCommonOperation = isCommonOperation;
	}

	/**
	 * Returns the abstractID.
	 * @return long
	 */
	public long getAbstractID() {
		return abstractID;
	}

	/**
	 * Sets the abstractID.
	 * @param abstractID The abstractID to set
	 */
	public void setAbstractID(long abstractID) {
		this.abstractID = abstractID;
	}

	/**
	 * @return
	 */
	public long getSerialNo()
	{
		return SerialNo;
	}

	/**
	 * @param l
	 */
	public void setSerialNo(long l)
	{
		SerialNo = l;
	}

	/**
	 * Returns the discountBillID.
	 * @return long
	 */
	public long getDiscountBillID()
	{
		return discountBillID;
	}

	/**
	 * Sets the discountBillID.
	 * @param discountBillID The discountBillID to set
	 */
	public void setDiscountBillID(long discountBillID)
	{
		this.discountBillID = discountBillID;
	}

	public long getBudgetItemID() {
		return budgetItemID;
	}

	public void setBudgetItemID(long budgetItemID) {
		this.budgetItemID = budgetItemID;
	}

	public long getBudgetStatusID() {
		return budgetStatusID;
	}

	public void setBudgetStatusID(long budgetStatusID) {
		this.budgetStatusID = budgetStatusID;
	}

	public String getOppAccountName() {
		return oppAccountName;
	}

	public void setOppAccountName(String oppAccountName) {
		this.oppAccountName = oppAccountName;
	}

	public String getOppAccountNo() {
		return oppAccountNo;
	}

	public void setOppAccountNo(String oppAccountNo) {
		this.oppAccountNo = oppAccountNo;
	}

	public long getAmountType() {
		return amountType;
	}

	public void setAmountType(long amountType) {
		this.amountType = amountType;
	}

}
