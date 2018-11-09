package com.iss.itreasury.audit.settlementaudit.dataentity;

import java.io.Serializable;

public class SettlementAuditResultInfo implements Serializable
{
	private long accountTypeID = -1;
    private String accountType = "";
    private long amountTypeID = -1;
    private long accountID = -1;
    private String accountNo = "";
    private long subaccountID = -1;
    private String accountName = "";
    private String depositNo = "";
    private double beginBalance = 0.0;
    private double payAmount = 0.0;
    private double loanAmount = 0.0;
    private double endBalance = 0.0;
    private String subjectCode = "";
    private String subjectName = "";
    private double subjectBeginBalance= 0.0;
    private double subjectpayAmount = 0.0;
    private double subjectloanAmount = 0.0;
    private double subjectEndBalance = 0.0;
    
    private double subjectbeginBalanceDebit = 0.0;
    private double subjectbeginBalanceCredit = 0.0;
    private double subjectendBalanceDebit = 0.0;
    private double subjectendBalanceCredit = 0.0;
	public long getAccountID() {
		return accountID;
	}
	public void setAccountID(long accountID) {
		this.accountID = accountID;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public long getAccountTypeID() {
		return accountTypeID;
	}
	public void setAccountTypeID(long accountTypeID) {
		this.accountTypeID = accountTypeID;
	}
	public long getAmountTypeID() {
		return amountTypeID;
	}
	public void setAmountTypeID(long amountTypeID) {
		this.amountTypeID = amountTypeID;
	}
	public double getBeginBalance() {
		return beginBalance;
	}
	public void setBeginBalance(double beginBalance) {
		this.beginBalance = beginBalance;
	}
	public double getEndBalance() {
		return endBalance;
	}
	public void setEndBalance(double endBalance) {
		this.endBalance = endBalance;
	}
	public double getLoanAmount() {
		return loanAmount;
	}
	public void setLoanAmount(double loanAmount) {
		this.loanAmount = loanAmount;
	}
	public double getPayAmount() {
		return payAmount;
	}
	public void setPayAmount(double payAmount) {
		this.payAmount = payAmount;
	}
	public double getSubjectBeginBalance() {
		return subjectBeginBalance;
	}
	public void setSubjectBeginBalance(double subjectBeginBalance) {
		this.subjectBeginBalance = subjectBeginBalance;
	}
	public String getSubjectCode() {
		return subjectCode;
	}
	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}
	public double getSubjectEndBalance() {
		return subjectEndBalance;
	}
	public void setSubjectEndBalance(double subjectEndBalance) {
		this.subjectEndBalance = subjectEndBalance;
	}
	public double getSubjectloanAmount() {
		return subjectloanAmount;
	}
	public void setSubjectloanAmount(double subjectloanAmount) {
		this.subjectloanAmount = subjectloanAmount;
	}
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	public double getSubjectpayAmount() {
		return subjectpayAmount;
	}
	public void setSubjectpayAmount(double subjectpayAmount) {
		this.subjectpayAmount = subjectpayAmount;
	}
	public String getDepositNo() {
		return depositNo;
	}
	public void setDepositNo(String depositNo) {
		this.depositNo = depositNo;
	}
	public long getSubaccountID() {
		return subaccountID;
	}
	public void setSubaccountID(long subaccountID) {
		this.subaccountID = subaccountID;
	}
	public double getSubjectbeginBalanceCredit() {
		return subjectbeginBalanceCredit;
	}
	public void setSubjectbeginBalanceCredit(double subjectbeginBalanceCredit) {
		this.subjectbeginBalanceCredit = subjectbeginBalanceCredit;
	}
	public double getSubjectbeginBalanceDebit() {
		return subjectbeginBalanceDebit;
	}
	public void setSubjectbeginBalanceDebit(double subjectbeginBalanceDebit) {
		this.subjectbeginBalanceDebit = subjectbeginBalanceDebit;
	}
	public double getSubjectendBalanceCredit() {
		return subjectendBalanceCredit;
	}
	public void setSubjectendBalanceCredit(double subjectendBalanceCredit) {
		this.subjectendBalanceCredit = subjectendBalanceCredit;
	}
	public double getSubjectendBalanceDebit() {
		return subjectendBalanceDebit;
	}
	public void setSubjectendBalanceDebit(double subjectendBalanceDebit) {
		this.subjectendBalanceDebit = subjectendBalanceDebit;
	}
    
}
