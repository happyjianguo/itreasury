package com.iss.itreasury.settlement.query.resultinfo;

import java.io.Serializable;
/**
 * Title:        		iTreasury
 * Description:         
 * Copyright:           Copyright (c) 2003
 * Company:             iSoftStone
 * @author              xgzhang
 * @version
 *  Date of Creation    2005-9-13
 */
public class QUpGatherAccountDetailInfo implements Serializable{
	/**
	 * 账户ID
	 */
	protected long accountID = -1;
	/**
	 * 账户
	 */
	protected String accountNo = "";
	/**
	 * 客户ID
	 */
	protected long clientID = -1;
	/**
	 * 客户名称
	 */
	protected String clientName = "";
	/**
	 * 账户合计
	 */
	protected double accountAmountSum = 0.0;
	/**
	 * Get the accountAmountSum
	 * @return double
	 */
	public double getAccountAmountSum() {
		return accountAmountSum;
	}
	/**
	 * Set the accountAmountSum
	 * @param accountAmountSum
	 */
	public void setAccountAmountSum(double accountAmountSum) {
		this.accountAmountSum = accountAmountSum;
	}
	/**
	 * Get the accountID
	 * @return long
	 */
	public long getAccountID() {
		return accountID;
	}
	/**
	 * Set the accountID
	 * @param accountID
	 */
	public void setAccountID(long accountID) {
		this.accountID = accountID;
	}
	 
	public long getClientID() {
		return clientID;
	}
	public void setClientID(long clientID) {
		this.clientID = clientID;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	/**
	 * Get the AccountNo
	 * @return String
	 */
	public String getAccountNo() {
		return accountNo;
	}
	/**
	 * Set the AccountNo
	 * @param accountNo
	 */
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
}
