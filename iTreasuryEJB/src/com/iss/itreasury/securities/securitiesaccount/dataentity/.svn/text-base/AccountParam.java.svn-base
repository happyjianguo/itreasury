package com.iss.itreasury.securities.securitiesaccount.dataentity;

import java.sql.Timestamp;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.securities.deliveryorder.dataentity.DeliveryOrderInfo;
import com.iss.itreasury.securities.exception.SecuritiesDAOException;
import com.iss.itreasury.securities.exception.SecuritiesException;
import com.iss.itreasury.securities.setting.dataentity.TransactionTypeInfo;
import com.iss.itreasury.securities.util.NameRef;
import com.iss.itreasury.securities.util.SECBaseDataEntity;
import com.iss.itreasury.securities.util.SECConstant;

/**
 * Title:        		iTreasury
 * Description:         
 * Copyright:           Copyright (c) 2003
 * Company:             iSoftStone
 * @author             yehuang 
 * @version
 *  Date of Creation    2004-3-02
 */


public class AccountParam extends SECBaseDataEntity {

	private long id = -1; //资金账户ID
	private double amount = 0.0;//交易金额
	private String accountNo = null; //资金账户号
	private Timestamp transactionDate = null; //交易日期/成交日期
	private Timestamp executeDate = null; //执行日
	private Timestamp deliveryDate = null; //交割日期/结算日期
	private long officeID = -1;
	private long currencyID = -1;
	private long transactionTypeID = -1;
	private long isNeedCheckOverDraft = -1; //是否检查透支
	
	

	public AccountParam(DeliveryOrderInfo doInfo) throws SecuritiesException{
		TransactionTypeInfo transTypeInfo = null;
		id = doInfo.getAccountId();
		
		amount = doInfo.getNetIncome();
		//accountNo =
		transactionDate = doInfo.getTransactionDate();
		
		deliveryDate = doInfo.getDeliveryDate();
		isNeedCheckOverDraft = doInfo.getIsCheckOverDraft();
		
	}
	/**
	 * Returns the amount.
	 * @return double
	 */
	public double getAmount() {
		return amount;
	}

	/**
	 * Returns the deliveryDate.
	 * @return Timestamp
	 */
	public Timestamp getDeliveryDate() {
		return deliveryDate;
	}

	/**
	 * Returns the id.
	 * @return long
	 */
	public long getId() {
		return id;
	}

	/**
	 * Returns the transactionDate.
	 * @return Timestamp
	 */
	public Timestamp getTransactionDate() {
		return transactionDate;
	}

	/**
	 * Sets the amount.
	 * @param amount The amount to set
	 */
	public void setAmount(double amount) {
		this.amount = amount;
	}

	/**
	 * Sets the deliveryDate.
	 * @param deliveryDate The deliveryDate to set
	 */
	public void setDeliveryDate(Timestamp deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	/**
	 * Sets the id.
	 * @param id The id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Sets the transactionDate.
	 * @param transactionDate The transactionDate to set
	 */
	public void setTransactionDate(Timestamp transactionDate) {
		this.transactionDate = transactionDate;
	}

	/**
	 * Returns the accountNo.
	 * @return String
	 */
	public String getAccountNo() {
		return accountNo;
	}

	/**
	 * Sets the accountNo.
	 * @param accountNo The accountNo to set
	 */
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	/**
	 * @return Returns the currencyID.
	 */
	public long getCurrencyID() {
		return currencyID;
	}
	/**
	 * @param currencyID The currencyID to set.
	 */
	public void setCurrencyID(long currencyID) {
		putUsedField("currencyID", currencyID);
		this.currencyID = currencyID;
	}
	/**
	 * @return Returns the executeDate.
	 */
	public Timestamp getExecuteDate() {
		return executeDate;
	}
	/**
	 * @param executeDate The executeDate to set.
	 */
	public void setExecuteDate(Timestamp executeDate) {
		putUsedField("executeDate", executeDate);
		this.executeDate = executeDate;
	}
	/**
	 * @return Returns the officeID.
	 */
	public long getOfficeID() {
		return officeID;
	}
	/**
	 * @param officeID The officeID to set.
	 */
	public void setOfficeID(long officeID) {
		putUsedField("officeID", officeID);
		this.officeID = officeID;
	}
	/**
	 * @return Returns the transactionTypeID.
	 */
	public long getTransactionTypeID() {
		return transactionTypeID;
	}
	/**
	 * @param transactionTypeID The transactionTypeID to set.
	 */
	public void setTransactionTypeID(long transactionTypeID) {
		putUsedField("transactionTypeID", transactionTypeID);
		this.transactionTypeID = transactionTypeID;
	}
	/**
	 * @return Returns the isNeedCheckOverDraft.
	 */
	public long getIsNeedCheckOverDraft() {
		return isNeedCheckOverDraft;
	}
	/**
	 * @param isNeedCheckOverDraft The isNeedCheckOverDraft to set.
	 */
	public void setIsNeedCheckOverDraft(long isNeedCheckOverDraft) {
		putUsedField("isNeedCheckOverDraft", isNeedCheckOverDraft);
		this.isNeedCheckOverDraft = isNeedCheckOverDraft;
	}
}
