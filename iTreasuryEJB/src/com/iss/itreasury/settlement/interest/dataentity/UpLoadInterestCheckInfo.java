package com.iss.itreasury.settlement.interest.dataentity;

import java.sql.Timestamp;

import com.iss.itreasury.settlement.base.SettlementBaseDataEntity;

public class UpLoadInterestCheckInfo extends SettlementBaseDataEntity
{
	private long TransInterestID = -1;
	private String AccountNo = "";
	private Timestamp InterestStartDate = null;
	private Timestamp InterestClearDate = null;
	private double InterestAmount = 0.0;
	private String Abstract = "";
	private long StatusID = -1;
	private String AccountName = "";
	
	private long SettAccountID = -1;
	private Timestamp SettInterestStartDate = null;
	private Timestamp SettInterestClearDate = null;
	private double SettInterestAmount = 0.0;
	private String SettBankAccountNo = null;

	
	public String getSettBankAccountNo() {
		return SettBankAccountNo;
	}
	public void setSettBankAccountNo(String settBankAccountNo) {
		SettBankAccountNo = settBankAccountNo;
	}
	public String getAbstract() 
	{
		return Abstract;
	}
	public void setAbstract(String abstract1) 
	{
		Abstract = abstract1;
	}
	public String getAccountNo() 
	{
		return AccountNo;
	}
	public void setAccountNo(String accountNo) 
	{
		AccountNo = accountNo;
	}
	public double getInterestAmount() 
	{
		return InterestAmount;
	}
	public void setInterestAmount(double interestAmount) 
	{
		InterestAmount = interestAmount;
	}
	public Timestamp getInterestClearDate() 
	{
		return InterestClearDate;
	}
	public void setInterestClearDate(Timestamp interestClearDate) 
	{
		InterestClearDate = interestClearDate;
	}
	public Timestamp getInterestStartDate() 
	{
		return InterestStartDate;
	}
	public void setInterestStartDate(Timestamp interestStartDate) 
	{
		InterestStartDate = interestStartDate;
	}
	public long getStatusID() 
	{
		return StatusID;
	}
	public void setStatusID(long statusID) 
	{
		StatusID = statusID;
	}
	public long getSettAccountID() 
	{
		return SettAccountID;
	}
	public void setSettAccountID(long settAccountID) 
	{
		SettAccountID = settAccountID;
	}
	public double getSettInterestAmount() 
	{
		return SettInterestAmount;
	}
	public void setSettInterestAmount(double settInterestAmount) 
	{
		SettInterestAmount = settInterestAmount;
	}
	public Timestamp getSettInterestClearDate() 
	{
		return SettInterestClearDate;
	}
	public void setSettInterestClearDate(Timestamp settInterestClearDate) 
	{
		SettInterestClearDate = settInterestClearDate;
	}
	public Timestamp getSettInterestStartDate() 
	{
		return SettInterestStartDate;
	}
	public void setSettInterestStartDate(Timestamp settInterestStartDate) 
	{
		SettInterestStartDate = settInterestStartDate;
	}
	public String getAccountName() {
		return AccountName;
	}
	public void setAccountName(String accountName) {
		AccountName = accountName;
	}
	public long getTransInterestID() {
		return TransInterestID;
	}
	public void setTransInterestID(long transInterestID) {
		TransInterestID = transInterestID;
	}
	
	
}
