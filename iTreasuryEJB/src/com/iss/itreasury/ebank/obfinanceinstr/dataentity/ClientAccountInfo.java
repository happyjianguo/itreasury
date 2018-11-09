/*
 * Created on 2004-10-26
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.ebank.obfinanceinstr.dataentity;

/**
 * @author gdzhao
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ClientAccountInfo
{
	public long payAccountID=-1;
	public String payAccountNo="";
	public String receiveAccountName="";
	public String receiveAccountNo="";
	public double balance=0;
	public double canUseBalance=0;
	public double limitAmount=0;
	public double applyAmount=0;
	/**
	 * @return Returns the applyAmount.
	 */
	public double getApplyAmount() {
		return applyAmount;
	}
	/**
	 * @param applyAmount The applyAmount to set.
	 */
	public void setApplyAmount(double applyAmount) {
		this.applyAmount = applyAmount;
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
		this.balance = balance;
	}
	/**
	 * @return Returns the canUseBalance.
	 */
	public double getCanUseBalance() {
		return canUseBalance;
	}
	/**
	 * @param canUseBalance The canUseBalance to set.
	 */
	public void setCanUseBalance(double canUseBalance) {
		this.canUseBalance = canUseBalance;
	}
	/**
	 * @return Returns the limitAmount.
	 */
	public double getLimitAmount() {
		return limitAmount;
	}
	/**
	 * @param limitAmount The limitAmount to set.
	 */
	public void setLimitAmount(double limitAmount) {
		this.limitAmount = limitAmount;
	}

	
	
	/**
	 * @return Returns the payAccountID.
	 */
	public long getPayAccountID() {
		return payAccountID;
	}
	/**
	 * @param payAccountID The payAccountID to set.
	 */
	public void setPayAccountID(long payAccountID) {
		this.payAccountID = payAccountID;
	}
	/**
	 * @return Returns the payAccountNo.
	 */
	public String getPayAccountNo() {
		return payAccountNo;
	}
	/**
	 * @param payAccountNo The payAccountNo to set.
	 */
	public void setPayAccountNo(String payAccountNo) {
		this.payAccountNo = payAccountNo;
	}
	/**
	 * @return Returns the receiveAccountName.
	 */
	public String getReceiveAccountName() {
		return receiveAccountName;
	}
	/**
	 * @param receiveAccountName The receiveAccountName to set.
	 */
	public void setReceiveAccountName(String receiveAccountName) {
		this.receiveAccountName = receiveAccountName;
	}
	/**
	 * @return Returns the receiveAccountNo.
	 */
	public String getReceiveAccountNo() {
		return receiveAccountNo;
	}
	/**
	 * @param receiveAccountNo The receiveAccountNo to set.
	 */
	public void setReceiveAccountNo(String receiveAccountNo) {
		this.receiveAccountNo = receiveAccountNo;
	}

	
}
