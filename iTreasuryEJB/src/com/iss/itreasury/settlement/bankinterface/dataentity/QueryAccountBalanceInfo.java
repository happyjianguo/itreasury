/*
 * Created on 2004-11-29
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.settlement.bankinterface.dataentity;
import java.io.Serializable;
/**
 * 余额对比清单数据对象
 * 
 * @author ytcui
 * 
 * To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Generation - Code and Comments
 */
public class QueryAccountBalanceInfo implements Serializable {

	private String bankAccountNo = null; //银行账户编号
	private String clientName = null; //客户名称
	private String accountNo = null; //财务公司账户编号
	private double accountBalance = 0.0; //财务公司账户余额
	private double debitMoney = 0.0; //未入账借方发生额
	private double creditMoney = 0.0 ;//未入账贷方发生额
	private double bankAccountBalance = 0.0;//银行账户余额
	
	
	
	/**
	 * 跟银行同步操作是否失败，true 失败
	 */
	private boolean isOperateFailed = false;
	/**
	 * 跟银行同步操作失败原因
	 */
	private String m_strErrorMessage = null;
	/**
	 * @return Returns the bankAccountBalance.
	 */
	public double getBankAccountBalance()
	{
		return bankAccountBalance;
	}
	/**
	 * @param bankAccountBalance The bankAccountBalance to set.
	 */
	public void setBankAccountBalance(double bankAccountBalance)
	{
		this.bankAccountBalance = bankAccountBalance;
	}
	private double balance =0.0;//差额
	 
	
	/**
	 * @return Returns the accountBalance.
	 */
	public double getAccountBalance() {
		return accountBalance;
	}
	/**
	 * @param accountBalance The accountBalance to set.
	 */
	public void setAccountBalance(double accountBalance) {
		this.accountBalance = accountBalance;
	}
	/**
	 * @return Returns the accountNo.
	 */
	public String getAccountNo() {
		return accountNo;
	}
	/**
	 * @param accountNo The accountNo to set.
	 */
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
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
	 * @return Returns the bankAccountNo.
	 */
	public String getBankAccountNo() {
		return bankAccountNo;
	}
	/**
	 * @param bankAccountNo The bankAccountNo to set.
	 */
	public void setBankAccountNo(String bankAccountNo) {
		this.bankAccountNo = bankAccountNo;
	}
	/**
	 * @return Returns the clientName.
	 */
	public String getClientName() {
		return clientName;
	}
	/**
	 * @param clientName The clientName to set.
	 */
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	/**
	 * @return Returns the creditMoney.
	 */
	public double getCreditMoney() {
		return creditMoney;
	}
	/**
	 * @param creditMoney The creditMoney to set.
	 */
	public void setCreditMoney(double creditMoney) {
		this.creditMoney = creditMoney;
	}
	/**
	 * @return Returns the debitMoney.
	 */
	public double getDebitMoney() {
		return debitMoney;
	}
	/**
	 * @param debitMoney The debitMoney to set.
	 */
	public void setDebitMoney(double debitMoney) {
		this.debitMoney = debitMoney;
	}
	/**
	 * @return Returns the isOperateFailed.
	 */
	public boolean isOperateFailed()
	{
		return isOperateFailed;
	}
	/**
	 * @param isOperateFailed The isOperateFailed to set.
	 */
	public void setOperateFailed(boolean isOperateFailed)
	{
		this.isOperateFailed = isOperateFailed;
	}
	/**
	 * @return Returns the m_strErrorMessage.
	 */
	public String getM_strErrorMessage()
	{
		return m_strErrorMessage;
	}
	/**
	 * @param errorMessage The m_strErrorMessage to set.
	 */
	public void setM_strErrorMessage(String errorMessage)
	{
		m_strErrorMessage = errorMessage;
	}
}
