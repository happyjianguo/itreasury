package com.iss.itreasury.settlement.bankinterface.dataentity;

import java.sql.Date;

/**
 * @author rongyang
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class BankAccountBalanceInfo
{
	private String BankAccountNo = null;
	private double Balance = 0.0;
	private Date CurrentDate = null;
	private long BankType = -1;
	
	/**
	 * Constructor for BankAccontBalanceInfo.
	 */
	public BankAccountBalanceInfo()
	{
		super();
	}

	/**
	 * @return Returns the balance.
	 */
	public double getBalance()
	{
		return Balance;
	}
	/**
	 * @param balance The balance to set.
	 */
	public void setBalance(double balance)
	{
		Balance = balance;
	}
	/**
	 * @return Returns the bankAccountNo.
	 */
	public String getBankAccountNo()
	{
		return BankAccountNo;
	}
	/**
	 * @param bankAccountNo The bankAccountNo to set.
	 */
	public void setBankAccountNo(String bankAccountNo)
	{
		BankAccountNo = bankAccountNo;
	}
	/**
	 * @return Returns the bankType.
	 */
	public long getBankType()
	{
		return BankType;
	}
	/**
	 * @param bankType The bankType to set.
	 */
	public void setBankType(long bankType)
	{
		BankType = bankType;
	}
	/**
	 * @return Returns the currentDate.
	 */
	public Date getCurrentDate()
	{
		return CurrentDate;
	}
	/**
	 * @param currentDate The currentDate to set.
	 */
	public void setCurrentDate(Date currentDate)
	{
		CurrentDate = currentDate;
	}
}
