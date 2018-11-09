package com.iss.itreasury.settlement.compareTrans.dataentity;

public class CompareTwoLevelAccountDetailCondtion implements java.io.Serializable{
	private long officeID = -1;
	private long currencyID  = -1;
	private long accountID = -1;
	private long bankAccountID = -1;
	private long compareType = -1;
	private String startDate = "";
	private String endDate = "";
	public long getAccountID() {
		return accountID;
	}
	public void setAccountID(long accountID) {
		this.accountID = accountID;
	}
	public long getBankAccountID() {
		return bankAccountID;
	}
	public void setBankAccountID(long bankAccountID) {
		this.bankAccountID = bankAccountID;
	}
	public long getCurrencyID() {
		return currencyID;
	}
	public void setCurrencyID(long currencyID) {
		this.currencyID = currencyID;
	}

	public long getOfficeID() {
		return officeID;
	}
	public void setOfficeID(long officeID) {
		this.officeID = officeID;
	}

	public long getCompareType() {
		return compareType;
	}
	public void setCompareType(long compareType) {
		this.compareType = compareType;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	
}
