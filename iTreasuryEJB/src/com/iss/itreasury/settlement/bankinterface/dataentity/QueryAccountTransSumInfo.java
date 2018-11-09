/*
 * Created on 2003-10-30
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.settlement.bankinterface.dataentity;

import java.io.Serializable;

/**
 * @author yiwang
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class QueryAccountTransSumInfo implements Serializable
{
	private double DebitAmountSum = 0.0;   //借方金额 
	private double CreditAmountSum = 0.0;   //贷方金额
	private long DebitCount=0;             //借方笔数  
	private long CreditCount=0;            //贷方笔数
	
	//上海电气新增，用于两个对账清单
	private long bankAccountID = -1; //对应分公司账户设置或开户行设置记录id
	private String bankAccountNo = null; //银行账户的账户号，页面显示用
	private String bankAccountName = null; //银行账户的账户名称
	private long clientID = -1; //账户对应客户的记录id，对应分公司账户设置中的nclientid
	private String clientName = null; //客户名称
	private long settAccountID = -1; //分公司账户设置中对应的结算活期账户的id
	private String settAccountNo = null; //结算账户的账户号
	
	/**
	 * 
	 */
	public QueryAccountTransSumInfo()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return
	 */
	public double getCreditAmountSum() {
		return CreditAmountSum;
	}

	/**
	 * @return
	 */
	public long getCreditCount() {
		return CreditCount;
	}

	/**
	 * @return
	 */
	public double getDebitAmountSum() {
		return DebitAmountSum;
	}

	/**
	 * @return
	 */
	public long getDebitCount() {
		return DebitCount;
	}

	/**
	 * @param d
	 */
	public void setCreditAmountSum(double d) {
		CreditAmountSum = d;
	}

	/**
	 * @param l
	 */
	public void setCreditCount(long l) {
		CreditCount = l;
	}

	/**
	 * @param d
	 */
	public void setDebitAmountSum(double d) {
		DebitAmountSum = d;
	}

	/**
	 * @param l
	 */
	public void setDebitCount(long l) {
		DebitCount = l;
	}

	/**
	 * Returns the bankAccountID.
	 * @return long
	 */
	public long getBankAccountID()
	{
		return bankAccountID;
	}

	/**
	 * Returns the bankAccountNo.
	 * @return String
	 */
	public String getBankAccountNo()
	{
		return bankAccountNo;
	}

	/**
	 * Returns the clientID.
	 * @return long
	 */
	public long getClientID()
	{
		return clientID;
	}

	/**
	 * Returns the clientName.
	 * @return String
	 */
	public String getClientName()
	{
		return clientName;
	}

	/**
	 * Returns the settAccountID.
	 * @return long
	 */
	public long getSettAccountID()
	{
		return settAccountID;
	}

	/**
	 * Returns the settAccountNo.
	 * @return String
	 */
	public String getSettAccountNo()
	{
		return settAccountNo;
	}

	/**
	 * Sets the bankAccountID.
	 * @param bankAccountID The bankAccountID to set
	 */
	public void setBankAccountID(long bankAccountID)
	{
		this.bankAccountID = bankAccountID;
	}

	/**
	 * Sets the bankAccountNo.
	 * @param bankAccountNo The bankAccountNo to set
	 */
	public void setBankAccountNo(String bankAccountNo)
	{
		this.bankAccountNo = bankAccountNo;
	}

	/**
	 * Sets the clientID.
	 * @param clientID The clientID to set
	 */
	public void setClientID(long clientID)
	{
		this.clientID = clientID;
	}

	/**
	 * Sets the clientName.
	 * @param clientName The clientName to set
	 */
	public void setClientName(String clientName)
	{
		this.clientName = clientName;
	}

	/**
	 * Sets the settAccountID.
	 * @param settAccountID The settAccountID to set
	 */
	public void setSettAccountID(long settAccountID)
	{
		this.settAccountID = settAccountID;
	}

	/**
	 * Sets the settAccountNo.
	 * @param settAccountNo The settAccountNo to set
	 */
	public void setSettAccountNo(String settAccountNo)
	{
		this.settAccountNo = settAccountNo;
	}

	/**
	 * Returns the bankAccountName.
	 * @return String
	 */
	public String getBankAccountName()
	{
		return bankAccountName;
	}

	/**
	 * Sets the bankAccountName.
	 * @param bankAccountName The bankAccountName to set
	 */
	public void setBankAccountName(String bankAccountName)
	{
		this.bankAccountName = bankAccountName;
	}

}
