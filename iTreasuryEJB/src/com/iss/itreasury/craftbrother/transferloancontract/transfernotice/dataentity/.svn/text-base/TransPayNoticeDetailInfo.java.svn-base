package com.iss.itreasury.craftbrother.transferloancontract.transfernotice.dataentity;

import java.sql.Timestamp;

import com.iss.itreasury.util.ITreasuryBaseDataEntity;
/**
 * @author jywang
 * @version 1.1
 */
public class TransPayNoticeDetailInfo extends ITreasuryBaseDataEntity{
	
	private long id = -1;    //ID
	private long officeID = -1;      //办事处
	private long currencyID = -1;    //币种
	private long transferPayNoticeID = -1;   //信贷资产转让付款通知单ID
	private long transferRepayNoticeID = -1;  //信贷资产转让收款通知单ID
	private long transferContractID = -1;     //信贷资产转让合同ID
	private long counterPartID = -1;      //交易对手ID
	private long countPartBankID = -1;    //交易对手开户行ID
	private Timestamp lastClearInterest = null;   //上次结息日
	private double amount = 0.00;    //金额
	private double interest = 0.00;  //利息
	private double rate = 0.0000;    //利率
	private long statusID = -1;     //状态
	private Timestamp inputDate = null;  //录入日期
	private long inputUserID = -1;    //录入人
	
	private double payAmountRest = 0.00; //付款余额
	private double payCommsion=0.00;    //卖出买断合同本次扣收的手续费
	
	public double getPayAmountRest() {
		return payAmountRest;
	}
	public void setPayAmountRest(double payAmountRest) {
		this.payAmountRest = payAmountRest;
		putUsedField("payAmountRest" , payAmountRest);
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
		putUsedField("amount" , amount);
	}
	public long getCounterPartID() {
		return counterPartID;
	}
	public void setCounterPartID(long counterPartID) {
		this.counterPartID = counterPartID;
		putUsedField("counterPartID" , counterPartID);
	}
	public long getCountPartBankID() {
		return countPartBankID;
	}
	public void setCountPartBankID(long countPartBankID) {
		this.countPartBankID = countPartBankID;
		putUsedField("countPartBankID" , countPartBankID);
	}
	public long getCurrencyID() {
		return currencyID;
	}
	public void setCurrencyID(long currencyID) {
		this.currencyID = currencyID;
		putUsedField("currencyID" , currencyID);
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
		putUsedField("id" , id);
	}
	public Timestamp getInputDate() {
		return inputDate;
	}
	public void setInputDate(Timestamp inputDate) {
		this.inputDate = inputDate;
		putUsedField("inputDate" , inputDate);
	}
	public long getInputUserID() {
		return inputUserID;
	}
	public void setInputUserID(long inputUserID) {
		this.inputUserID = inputUserID;
		putUsedField("inputUserID" , inputUserID);
	}
	public double getInterest() {
		return interest;
	}
	public void setInterest(double interest) {
		this.interest = interest;
		putUsedField("interest" , interest);
	}
	public Timestamp getLastClearInterest() {
		return lastClearInterest;
	}
	public void setLastClearInterest(Timestamp lastClearInterest) {
		this.lastClearInterest = lastClearInterest;
		putUsedField("lastClearInterest" , lastClearInterest);
	}
	public long getOfficeID() {
		return officeID;
	}
	public void setOfficeID(long officeID) {
		this.officeID = officeID;
		putUsedField("officeID" , officeID);
	}
	
	public long getStatusID() {
		return statusID;
	}
	public void setStatusID(long statusID) {
		this.statusID = statusID;
		putUsedField("statusID" , statusID);
	}
	public long getTransferContractID() {
		return transferContractID;
	}
	public void setTransferContractID(long transferContractID) {
		this.transferContractID = transferContractID;
		putUsedField("transferContractID" , transferContractID);
	}
	public long getTransferPayNoticeID() {
		return transferPayNoticeID;
	}
	public void setTransferPayNoticeID(long transferPayNoticeID) {
		this.transferPayNoticeID = transferPayNoticeID;
		putUsedField("transferPayNoticeID" , transferPayNoticeID);
	}
	public long getTransferRepayNoticeID() {
		return transferRepayNoticeID;
	}
	public void setTransferRepayNoticeID(long transferRepayNoticeID) {
		this.transferRepayNoticeID = transferRepayNoticeID;
		putUsedField("transferRepayNoticeID" , transferRepayNoticeID);
	}
	public double getRate() {
		return rate;
	}
	public void setRate(double rate) {
		this.rate = rate;
		putUsedField("rate" , rate);
	}
	public double getPayCommsion() {
		return payCommsion;
	}
	public void setPayCommsion(double payCommsion) {
		this.payCommsion = payCommsion;
	}
	

}
