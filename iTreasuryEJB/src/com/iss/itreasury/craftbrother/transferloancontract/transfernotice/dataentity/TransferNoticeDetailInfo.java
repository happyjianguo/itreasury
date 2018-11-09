package com.iss.itreasury.craftbrother.transferloancontract.transfernotice.dataentity;

import java.sql.Timestamp;

import com.iss.itreasury.util.ITreasuryBaseDataEntity;

/**
 * 收款通知单明细(包括代收和非代收)
 * 
 * @author zcwang
 * @version 1.1
 */
public class TransferNoticeDetailInfo extends ITreasuryBaseDataEntity {
	
	private long id = -1; //ID

	private long officeID = -1; //办事处

	private long currencyID = -1; //币种  
		
	private long contractDetailID = -1;//转让合同明细ID
	
	private long noticeFormID = -1; //转让通知单ID	

	private long contractID = -1; //自营贷款合同ID  

	private long payFormID = -1; //自营贷款放款通知单ID 	
	
	private double amount = 0.0; //转让本金  

	private double interest = 0.0; //转让利息   

	private double rate = 0.000000; //自营贷款利率        

	private long payAccountID = -1; //付款方账户ID 

	private long statusID = -1; //状态

	private long inputuserID = -1; //录入人

	private Timestamp inputDate = null; //录入时间
	
	//以下字段为查询所添加--------------------------------------
	
	private long borrowClientID = -1; //借款人ID
	
	private String borrowClientName = null; //借款人名称
	
	private String loanContractCode = null; //贷款合同编号	
	
	private String loanPayNoticeCode = ""; //贷款放款通知单编号

	private long craContractID = -1; //转让合同ID
	
	private long counterPartID = -1; //交易对手ID
	
	private Timestamp lastClearInterest = null; //上次结息日

	private double balance = 0.0; //转让贷款子合同余额

	private Timestamp clearInterestDate = null; //结息日期

	private boolean isChecked = false; //是否勾选		

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
		putUsedField("id", id);

	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
		putUsedField("AMOUNT", amount);
	}

	public long getContractDetailID() {
		return contractDetailID;
	}

	public void setContractDetailID(long contractDetailID) {
		this.contractDetailID = contractDetailID;
		putUsedField("CONTRACTDETAILID", contractDetailID);
	}

	public long getContractID() {
		return contractID;
	}

	public void setContractID(long contractID) {
		this.contractID = contractID;
		putUsedField("CONTRACTID", contractID);
	}

	public long getCurrencyID() {
		return currencyID;
	}

	public void setCurrencyID(long currencyID) {
		this.currencyID = currencyID;
		putUsedField("CURRENCYID", currencyID);
	}

	public Timestamp getInputDate() {
		return inputDate;
	}

	public void setInputDate(Timestamp inputDate) {
		this.inputDate = inputDate;
		putUsedField("INPUTDATE", inputDate);
	}

	public long getInputuserID() {
		return inputuserID;
	}

	public void setInputuserID(long inputuserID) {
		this.inputuserID = inputuserID;
		putUsedField("INPUTUSERID", inputuserID);
	}

	public double getInterest() {
		return interest;
	}

	public void setInterest(double interest) {
		this.interest = interest;
		putUsedField("INTEREST", interest);
	}

	public long getNoticeFormID() {
		return noticeFormID;
	}

	public void setNoticeFormID(long noticeFormID) {
		this.noticeFormID = noticeFormID;
		putUsedField("NOTICEFORMID", noticeFormID);
	}

	public long getOfficeID() {
		return officeID;
	}

	public void setOfficeID(long officeID) {
		this.officeID = officeID;
		putUsedField("OFFICEID", officeID);
	}

	public long getPayAccountID() {
		return payAccountID;
	}

	public void setPayAccountID(long payAccountID) {
		this.payAccountID = payAccountID;
		putUsedField("PAYACCOUNTID", payAccountID);
	}

	public long getPayFormID() {
		return payFormID;
	}

	public void setPayFormID(long payFormID) {
		this.payFormID = payFormID;
		putUsedField("PAYFORMID", payFormID);
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
		putUsedField("RATE", rate);
	}

	public long getStatusID() {
		return statusID;
	}

	public void setStatusID(long statusID) {
		this.statusID = statusID;
		putUsedField("STATUSID", statusID);
	}

	public long getCraContractID() {
		return craContractID;
	}

	public void setCraContractID(long craContractID) {
		this.craContractID = craContractID;
	}

	public Timestamp getLastClearInterest() {
		return lastClearInterest;
	}

	public void setLastClearInterest(Timestamp lastClearInterest) {
		this.lastClearInterest = lastClearInterest;
	}

	public long getCounterPartID() {
		return counterPartID;
	}

	public void setCounterPartID(long counterPartID) {
		this.counterPartID = counterPartID;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public long getBorrowClientID() {
		return borrowClientID;
	}

	public void setBorrowClientID(long borrowClientID) {
		this.borrowClientID = borrowClientID;
	}

	public String getBorrowClientName() {
		return borrowClientName;
	}

	public void setBorrowClientName(String borrowClientName) {
		this.borrowClientName = borrowClientName;
	}

	public Timestamp getClearInterestDate() {
		return clearInterestDate;
	}

	public void setClearInterestDate(Timestamp clearInterestDate) {
		this.clearInterestDate = clearInterestDate;
	}

	public boolean isChecked() {
		return isChecked;
	}

	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}

	public String getLoanContractCode() {
		return loanContractCode;
	}

	public void setLoanContractCode(String loanContractCode) {
		this.loanContractCode = loanContractCode;
	}

	public String getLoanPayNoticeCode() {
		return loanPayNoticeCode;
	}

	public void setLoanPayNoticeCode(String loanPayNoticeCode) {
		this.loanPayNoticeCode = loanPayNoticeCode;
	}

	
}
