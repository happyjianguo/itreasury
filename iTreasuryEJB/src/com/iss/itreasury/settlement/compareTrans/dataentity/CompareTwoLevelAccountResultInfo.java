package com.iss.itreasury.settlement.compareTrans.dataentity;

public class CompareTwoLevelAccountResultInfo implements java.io.Serializable{
  private long accountID = -1;
  private String accountNo = "";
  private String accountName = "";
  private double balance = 0.0;
  private double interestbalance = 0.0;
  private String settdtdate = "";
  private long bankAccountID = -1;
  private String bankAccountNo = "";
  private String bankaccountName = "";
  private double bankBalance = 0.0;
  private String bankdtdate = "";
public String getBankdtdate() {
	return bankdtdate;
}
public void setBankdtdate(String bankdtdate) {
	this.bankdtdate = bankdtdate;
}
public double getInterestbalance() {
	return interestbalance;
}
public void setInterestbalance(double interestbalance) {
	this.interestbalance = interestbalance;
}
public String getSettdtdate() {
	return settdtdate;
}
public void setSettdtdate(String settdtdate) {
	this.settdtdate = settdtdate;
}
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
public double getBalance() {
	return balance;
}
public void setBalance(double balance) {
	this.balance = balance;
}
public long getBankAccountID() {
	return bankAccountID;
}
public void setBankAccountID(long bankAccountID) {
	this.bankAccountID = bankAccountID;
}
public String getBankaccountName() {
	return bankaccountName;
}
public void setBankaccountName(String bankaccountName) {
	this.bankaccountName = bankaccountName;
}
public String getBankAccountNo() {
	return bankAccountNo;
}
public void setBankAccountNo(String bankAccountNo) {
	this.bankAccountNo = bankAccountNo;
}
public double getBankBalance() {
	return bankBalance;
}
public void setBankBalance(double bankBalance) {
	this.bankBalance = bankBalance;
}
	  
}
