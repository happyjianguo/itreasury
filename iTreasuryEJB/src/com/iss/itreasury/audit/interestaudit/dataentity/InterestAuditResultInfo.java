package com.iss.itreasury.audit.interestaudit.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;

public class InterestAuditResultInfo implements Serializable
{
	private long  clientID = -1;
    private String clientName = "";
    private String depositNo = "";//新增加存单号 xfma
    private long isNegotiate = -1;//新增加存单号 xfma
    private long accountID = -1;
    private long subAccountID = -1;
    private String accountNo = "";
    private long contractID = -1;
    private String contractCode = "";
    private long contractTypeID = -1;
    private long payID = -1;
    private String payCode = "";
    private long accountTypeID = -1;
    private String accountType = "";
    private Timestamp startDate = null;
    private Timestamp endDate = null;
    private double interestRate =0.0;
    private double balance = 0.0;
    private double sumBalance = 0.0;
    private double sumNegotiateBalance = 0.0;
    private double negotiateRate =0.0;
    private double interestBalance = 0.0;
    private double negotiateBalance = 0.0;
    private long  tempcount = 0;
    private double rowSum = 0.0;
	public long getAccountID() {
		return accountID;
	}
	public void setAccountID(long accountID) {
		this.accountID = accountID;
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
	public long getClientID() {
		return clientID;
	}
	public void setClientID(long clientID) {
		this.clientID = clientID;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public String getDepositNo() {
		return depositNo;
	}
	public void setDepositNo(String depositNo) {
		this.depositNo = depositNo;
	}
	public long getIsNegotiate() {
		return isNegotiate;
	}
	public void setIsNegotiate(long isNegotiate) {
		this.isNegotiate = isNegotiate;
	}
	public double getInterestBalance() {
		return interestBalance;
	}
	public void setInterestBalance(double interestBalance) {
		this.interestBalance = interestBalance;
	}
	public double getInterestRate() {
		return interestRate;
	}
	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}
	public double getNegotiateBalance() {
		return negotiateBalance;
	}
	public void setNegotiateBalance(double negotiateBalance) {
		this.negotiateBalance = negotiateBalance;
	}
	public double getNegotiateRate() {
		return negotiateRate;
	}
	public void setNegotiateRate(double negotiateRate) {
		this.negotiateRate = negotiateRate;
	}
	public Timestamp getStartDate() {
		return startDate;
	}
	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}
	public double getSumBalance() {
		return sumBalance;
	}
	public void setSumBalance(double sumBalance) {
		this.sumBalance = sumBalance;
	}
	public long getTempcount() {
		return tempcount;
	}
	public void setTempcount(long tempcount) {
		this.tempcount = tempcount;
	}
	public double getRowSum() {
		return rowSum;
	}
	public void setRowSum(double rowSum) {
		this.rowSum = rowSum;
	}
	public Timestamp getEndDate() {
		return endDate;
	}
	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}
	public double getSumNegotiateBalance() {
		return sumNegotiateBalance;
	}
	public void setSumNegotiateBalance(double sumNegotiateBalance) {
		this.sumNegotiateBalance = sumNegotiateBalance;
	}
	public String getContractCode() {
		return contractCode;
	}
	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}
	public long getContractID() {
		return contractID;
	}
	public void setContractID(long contractID) {
		this.contractID = contractID;
	}
	public long getContractTypeID() {
		return contractTypeID;
	}
	public void setContractTypeID(long contractTypeID) {
		this.contractTypeID = contractTypeID;
	}
	public String getPayCode() {
		return payCode;
	}
	public void setPayCode(String payCode) {
		this.payCode = payCode;
	}
	public long getPayID() {
		return payID;
	}
	public void setPayID(long payID) {
		this.payID = payID;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public long getAccountTypeID() {
		return accountTypeID;
	}
	public void setAccountTypeID(long accountTypeID) {
		this.accountTypeID = accountTypeID;
	}
	public long getSubAccountID() {
		return subAccountID;
	}
	public void setSubAccountID(long subAccountID) {
		this.subAccountID = subAccountID;
	}
}
