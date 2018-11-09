package com.iss.itreasury.settlement.query.resultinfo;

import java.sql.Timestamp;

import com.iss.itreasury.settlement.base.SettlementBaseDataEntity;

public class BankAccountCheckInfo extends SettlementBaseDataEntity 
{
	private long settAccountID = -1;
	private String settAccountNo = "";
	private double settAccountBalance = 0.0;
	private double settDebitAmount = 0.0;
	private long settDebitCount = -1;
	private double settCreditAmount = 0.0;
	private long settCreditCount = -1;
	private String settClientName = "";
	
	private long bankAccountID = -1;
	private String bankAccountNo = "";
	private double bankAccountBalance = 0.0;
	private double bankDebitAmount = 0.0;
	private long bankDebitCount = -1;
	private double bankCreditAmount = 0.0;
	private long bankCreditCount = -1;
	private boolean bankIsOperateFailed = true;
	private String bankErrorMessage = "";
	
	private Timestamp queryDate = null;
	
	public Timestamp getQueryDate() 
	{
		return queryDate;
	}
	public void setQueryDate(Timestamp queryDate) 
	{
		this.queryDate = queryDate;
	}
	public double getBankAccountBalance() 
	{
		return bankAccountBalance;
	}
	public void setBankAccountBalance(double bankAccountBalance) 
	{
		this.bankAccountBalance = bankAccountBalance;
	}
	public long getBankAccountID() 
	{
		return bankAccountID;
	}
	public void setBankAccountID(long bankAccountID) 
	{
		this.bankAccountID = bankAccountID;
	}
	public String getBankAccountNo() 
	{
		return bankAccountNo;
	}
	public void setBankAccountNo(String bankAccountNo) 
	{
		this.bankAccountNo = bankAccountNo;
	}
	public double getBankCreditAmount() 
	{
		return bankCreditAmount;
	}
	public void setBankCreditAmount(double bankCreditAmount) 
	{
		this.bankCreditAmount = bankCreditAmount;
	}
	public double getBankDebitAmount() 
	{
		return bankDebitAmount;
	}
	public void setBankDebitAmount(double bankDebitAmount) 
	{
		this.bankDebitAmount = bankDebitAmount;
	}
	public double getSettAccountBalance() 
	{
		return settAccountBalance;
	}
	public void setSettAccountBalance(double settAccountBalance) 
	{
		this.settAccountBalance = settAccountBalance;
	}
	public long getSettAccountID() 
	{
		return settAccountID;
	}
	public void setSettAccountID(long settAccountID) 
	{
		this.settAccountID = settAccountID;
	}
	public String getSettAccountNo() 
	{
		return settAccountNo;
	}
	public void setSettAccountNo(String settAccountNo) 
	{
		this.settAccountNo = settAccountNo;
	}
	public double getSettCreditAmount() 
	{
		return settCreditAmount;
	}
	public void setSettCreditAmount(double settCreditAmount) 
	{
		this.settCreditAmount = settCreditAmount;
	}
	public double getSettDebitAmount() 
	{
		return settDebitAmount;
	}
	public void setSettDebitAmount(double settDebitAmount) 
	{
		this.settDebitAmount = settDebitAmount;
	}
	public String getSettClientName() 
	{
		return settClientName;
	}
	public void setSettClientName(String settClientName) 
	{
		this.settClientName = settClientName;
	}
	public boolean isBankIsOperateFailed() 
	{
		return bankIsOperateFailed;
	}
	public void setBankIsOperateFailed(boolean bankIsOperateFailed) 
	{
		this.bankIsOperateFailed = bankIsOperateFailed;
	}
	public String getBankErrorMessage() 
	{
		return bankErrorMessage;
	}
	public void setBankErrorMessage(String bankErrorMessage) 
	{
		this.bankErrorMessage = bankErrorMessage;
	}
	public long getBankCreditCount() 
	{
		return bankCreditCount;
	}
	public void setBankCreditCount(long bankCreditCount) 
	{
		this.bankCreditCount = bankCreditCount;
	}
	public long getBankDebitCount() 
	{
		return bankDebitCount;
	}
	public void setBankDebitCount(long bankDebitCount) 
	{
		this.bankDebitCount = bankDebitCount;
	}
	public long getSettCreditCount() 
	{
		return settCreditCount;
	}
	public void setSettCreditCount(long settCreditCount) 
	{
		this.settCreditCount = settCreditCount;
	}
	public long getSettDebitCount() 
	{
		return settDebitCount;
	}
	public void setSettDebitCount(long settDebitCount) 
	{
		this.settDebitCount = settDebitCount;
	}
	
}
