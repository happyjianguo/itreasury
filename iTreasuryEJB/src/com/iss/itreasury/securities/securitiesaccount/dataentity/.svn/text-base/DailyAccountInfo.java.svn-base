/**
 * Title:        		iTreasury
 * Description:         
 * Copyright:           Copyright (c) 2003
 * Company:             iSoftStone
 * @author             yehuang 
 * @version
 *  Date of Creation    2004-3-30
 */
package com.iss.itreasury.securities.securitiesaccount.dataentity;

import java.sql.Timestamp;

import com.iss.itreasury.securities.util.SECBaseDataEntity;

/**
 * @author yehuang
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class DailyAccountInfo extends SECBaseDataEntity {
	
	private long id = -1;
	private Timestamp accountDate = null;
	private long accountID = -1;
	private double balance = 0.0;
	private long payNumber = -1;
	private double payAmount = 0.0;
	private long receiveNumber = -1;
	private double receiveAmount = 0.0;
	
	public void setValuesToDefault(){
		this.setBalance(0.0);
		this.setPayAmount(0.0);
		this.setPayNumber(0);
		this.setReceiveAmount(0.0);
		this.setReceiveNumber(0);
	}
	
	/* (non-Javadoc)
	 * @see com.iss.itreasury.util.ITreasuryBaseDataEntity#getId()
	 */
	public long getId() {
		// TODO Auto-generated method stub
		return id;
	}
	/* (non-Javadoc)
	 * @see com.iss.itreasury.util.ITreasuryBaseDataEntity#setId(long)
	 */
	public void setId(long id) {
		this.id = id;
	}
	/**
	 * @return Returns the accountID.
	 */
	public long getAccountID() {
		return accountID;
	}
	/**
	 * @param accountID The accountID to set.
	 */
	public void setAccountID(long accountID) {
		putUsedField("accountID", accountID);
		this.accountID = accountID;
	}
	/**
	 * @return Returns the balance.
	 */
	public double getBalance() {
		return balance;
	}
	/**
	 * @param balance The balance to set.
	 */
	public void setBalance(double balance) {
		putUsedField("balance", balance);
		this.balance = balance;
	}
	/**
	 * @return Returns the date.
	 */
	public Timestamp getAccountDate() {
		return accountDate;
	}
	/**
	 * @param date The date to set.
	 */
	public void setAccountDate(Timestamp accountDate) {
		putUsedField("accountdate", accountDate);
		this.accountDate = accountDate;
	}
	/**
	 * @return Returns the payAmount.
	 */
	public double getPayAmount() {
		return payAmount;
	}
	/**
	 * @param payAmount The payAmount to set.
	 */
	public void setPayAmount(double payAmount) {
		putUsedField("payAmount", payAmount);
		this.payAmount = payAmount;
	}
	/**
	 * @return Returns the payNumber.
	 */
	public long getPayNumber() {
		return payNumber;
	}
	/**
	 * @param payNumber The payNumber to set.
	 */
	public void setPayNumber(long payNumber) {
		putUsedField("payNumber", payNumber);
		this.payNumber = payNumber;
	}
	/**
	 * @return Returns the receiveAmount.
	 */
	public double getReceiveAmount() {
		return receiveAmount;
	}
	/**
	 * @param receiveAmount The receiveAmount to set.
	 */
	public void setReceiveAmount(double receiveAmount) {
		putUsedField("receiveAmount", receiveAmount);
		this.receiveAmount = receiveAmount;
	}
	/**
	 * @return Returns the receiveNumber.
	 */
	public long getReceiveNumber() {
		return receiveNumber;
	}
	/**
	 * @param receiveNumber The receiveNumber to set.
	 */
	public void setReceiveNumber(long receiveNumber) {
		putUsedField("receiveNumber", receiveNumber);
		this.receiveNumber = receiveNumber;
	}
}
