package com.iss.itreasury.craftbrother.transferloancontract.transfernotice.dataentity;

import java.sql.Timestamp;

/**
 * @author zcwang
 * @version 1.1
 */
public class ContractAndNoticeDetialResultInfo implements java.io.Serializable{

	private long officeID = -1; 
	private long currencyID = -1;
	private long transferContractFormId  = -1;  //转让合同主ID
	private String transferContractCode  ="";   //转让合同编号
	private long noticeFormId = -1;				//转让收款通知单主ID
	private long contractDetailID = -1;         //转让合同明细ID
	private long noticeDetailID = -1;			//收款通知单明细ID
	private long loanContractID = -1;			//贷款合同ID
	private String loancontractCode = "";		//贷款合同编号
	private long nborrowclientid = -1;			//借款人ID
	private long loanPayNoticeID = -1;			//贷款放款通知单ID
	private String loanPayNoticeCode = "";		//贷款放款通知单编号
	private Timestamp lastClearInterestDate = null;	//上次结息日
	private Timestamp clearInterestDate = null;		//结息日期
	private double transferAmount = 0.0 ;			//转让金额
	private double balance = 0.0;					//转让余额
	private double rate = 0.000000;					//放款通知单利率
	private double interest = 0.00;					//转让利息
	private long payAccountID = -1;					//付款方账户活期ID
	private boolean isChecked = false;				//是否勾选
	private double sellamount=0.0;                  //转让余额2，由于在修改通知单时原转让余额
	                                                //balance被当前收款金额占用，所以要增加此变量
	public boolean isChecked() {
		return isChecked;
	}
	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public long getContractDetailID() {
		return contractDetailID;
	}
	public void setContractDetailID(long contractDetailID) {
		this.contractDetailID = contractDetailID;
	}
	public long getCurrencyID() {
		return currencyID;
	}
	public void setCurrencyID(long currencyID) {
		this.currencyID = currencyID;
	}
	public double getInterest() {
		return interest;
	}
	public void setInterest(double interest) {
		this.interest = interest;
	}
	public Timestamp getLastClearInterestDate() {
		return lastClearInterestDate;
	}
	public void setLastClearInterestDate(Timestamp lastClearInterestDate) {
		this.lastClearInterestDate = lastClearInterestDate;
	}
	public String getLoancontractCode() {
		return loancontractCode;
	}
	public void setLoancontractCode(String loancontractCode) {
		this.loancontractCode = loancontractCode;
	}
	public long getLoanContractID() {
		return loanContractID;
	}
	public void setLoanContractID(long loanContractID) {
		this.loanContractID = loanContractID;
	}
	public String getLoanPayNoticeCode() {
		return loanPayNoticeCode;
	}
	public void setLoanPayNoticeCode(String loanPayNoticeCode) {
		this.loanPayNoticeCode = loanPayNoticeCode;
	}
	public long getLoanPayNoticeID() {
		return loanPayNoticeID;
	}
	public void setLoanPayNoticeID(long loanPayNoticeID) {
		this.loanPayNoticeID = loanPayNoticeID;
	}
	public long getNborrowclientid() {
		return nborrowclientid;
	}
	public void setNborrowclientid(long nborrowclientid) {
		this.nborrowclientid = nborrowclientid;
	}
	public long getOfficeID() {
		return officeID;
	}
	public void setOfficeID(long officeID) {
		this.officeID = officeID;
	}
	public long getPayAccountID() {
		return payAccountID;
	}
	public void setPayAccountID(long payAccountID) {
		this.payAccountID = payAccountID;
	}
	public double getRate() {
		return rate;
	}
	public void setRate(double rate) {
		this.rate = rate;
	}
	public long getNoticeDetailID() {
		return noticeDetailID;
	}
	public void setNoticeDetailID(long noticeDetailID) {
		this.noticeDetailID = noticeDetailID;
	}
	public Timestamp getClearInterestDate() {
		return clearInterestDate;
	}
	public void setClearInterestDate(Timestamp clearInterestDate) {
		this.clearInterestDate = clearInterestDate;
	}
	public double getTransferAmount() {
		return transferAmount;
	}
	public void setTransferAmount(double transferAmount) {
		this.transferAmount = transferAmount;
	}
	public long getTransferContractFormId() {
		return transferContractFormId;
	}
	public void setTransferContractFormId(long transferContractFormId) {
		this.transferContractFormId = transferContractFormId;
	}
	public long getNoticeFormId() {
		return noticeFormId;
	}
	public void setNoticeFormId(long noticeFormId) {
		this.noticeFormId = noticeFormId;
	}
	public String getTransferContractCode() {
		return transferContractCode;
	}
	public void setTransferContractCode(String transferContractCode) {
		this.transferContractCode = transferContractCode;
	}
	public double getSellamount() {
		return sellamount;
	}
	public void setSellamount(double sellamount) {
		this.sellamount = sellamount;
	}
}
