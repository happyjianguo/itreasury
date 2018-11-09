package com.iss.itreasury.settlement.remind.resultinfo;

import java.io.Serializable;

public class BankBalanceInfo implements Serializable 
{
	private String BankAccountNo = "";
	private String BankAccountName = "";
	private long BankTypeID = -1;
	private double BankBalance = 0.0;
	
	public String getBankAccountName() 
	{
		return BankAccountName;
	}
	public void setBankAccountName(String bankAccountName) 
	{
		BankAccountName = bankAccountName;
	}
	public String getBankAccountNo() 
	{
		return BankAccountNo;
	}
	public void setBankAccountNo(String bankAccountNo)
{
		BankAccountNo = bankAccountNo;
	}
	public double getBankBalance() 
	{
		return BankBalance;
	}
	public void setBankBalance(double bankBalance) 
	{
		BankBalance = bankBalance;
	}
	public long getBankTypeID() 
	{
		return BankTypeID;
	}
	public void setBankTypeID(long bankTypeID) 
	{
		BankTypeID = bankTypeID;
	}
	
	
}
