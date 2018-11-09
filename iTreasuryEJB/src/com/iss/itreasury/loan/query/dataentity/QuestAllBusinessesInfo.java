package com.iss.itreasury.loan.query.dataentity;

import java.io.Serializable;

public class QuestAllBusinessesInfo extends Object implements Serializable {
	
	
	private String contractCodeFrom;//贴现编号
	private String contractCodeTo;
	
	private long lClientIDFrom;//借款单位
	private long lClientIDTo;
	
	private double LoanAmountFrom; //
	private double LoanAmountTo;
	
	private String tsStartLoanDateFrom;//贷款起始日期
	private String tsStartLoanDateTo;
	
	private String tsEndLoanDateFrom;//贷款结束日期
	private String tsEndLoanDateTo;
	
	private String tsLeftMoneyDate;//余额日期
	
	
	private String loanTypes;
	
	private String loanStatuses;
	
	private long officeID;
	private long currencyID;

	
	
	public long getOfficeID() {
		return officeID;
	}

	public void setOfficeID(long officeID) {
		this.officeID = officeID;
	}

	public long getCurrencyID() {
		return currencyID;
	}

	public void setCurrencyID(long currencyID) {
		this.currencyID = currencyID;
	}

	public String getContractCodeFrom() {
		return contractCodeFrom;
	}

	public void setContractCodeFrom(String contractCodeFrom) {
		this.contractCodeFrom = contractCodeFrom;
	}

	public String getContractCodeTo() {
		return contractCodeTo;
	}

	public void setContractCodeTo(String contractCodeTo) {
		this.contractCodeTo = contractCodeTo;
	}

	public long getLClientIDFrom() {
		return lClientIDFrom;
	}

	public void setLClientIDFrom(long clientIDFrom) {
		lClientIDFrom = clientIDFrom;
	}

	public long getLClientIDTo() {
		return lClientIDTo;
	}

	public void setLClientIDTo(long clientIDTo) {
		lClientIDTo = clientIDTo;
	}



	public double getLoanAmountFrom() {
		return LoanAmountFrom;
	}

	public void setLoanAmountFrom(double loanAmountFrom) {
		LoanAmountFrom = loanAmountFrom;
	}

	public double getLoanAmountTo() {
		return LoanAmountTo;
	}

	public void setLoanAmountTo(double loanAmountTo) {
		LoanAmountTo = loanAmountTo;
	}

	public String getTsStartLoanDateFrom() {
		return tsStartLoanDateFrom;
	}

	public void setTsStartLoanDateFrom(String tsStartLoanDateFrom) {
		this.tsStartLoanDateFrom = tsStartLoanDateFrom;
	}

	public String getTsStartLoanDateTo() {
		return tsStartLoanDateTo;
	}

	public void setTsStartLoanDateTo(String tsStartLoanDateTo) {
		this.tsStartLoanDateTo = tsStartLoanDateTo;
	}

	public String getTsEndLoanDateFrom() {
		return tsEndLoanDateFrom;
	}

	public void setTsEndLoanDateFrom(String tsEndLoanDateFrom) {
		this.tsEndLoanDateFrom = tsEndLoanDateFrom;
	}

	public String getTsEndLoanDateTo() {
		return tsEndLoanDateTo;
	}

	public void setTsEndLoanDateTo(String tsEndLoanDateTo) {
		this.tsEndLoanDateTo = tsEndLoanDateTo;
	}

	public String getTsLeftMoneyDate() {
		return tsLeftMoneyDate;
	}

	public void setTsLeftMoneyDate(String tsLeftMoneyDate) {
		this.tsLeftMoneyDate = tsLeftMoneyDate;
	}

	public String getLoanTypes() {
		return loanTypes;
	}

	public void setLoanTypes(String loanTypes) {
		this.loanTypes = loanTypes;
	}

	public String getLoanStatuses() {
		return loanStatuses;
	}

	public void setLoanStatuses(String loanStatuses) {
		this.loanStatuses = loanStatuses;
	}
	
	
	
	

}
