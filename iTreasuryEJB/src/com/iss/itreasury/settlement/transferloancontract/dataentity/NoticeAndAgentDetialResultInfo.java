package com.iss.itreasury.settlement.transferloancontract.dataentity;

import java.sql.Timestamp;

public class NoticeAndAgentDetialResultInfo implements java.io.Serializable{
	private long officeID = -1; 
	private long currencyID = -1;
	private long transferLoanAmountID = -1; 	//�տ�����ID
	private long noticeFormId = -1;				//ת���տ�֪ͨ����ID
	private long noticeDetailID = -1;			//�տ�֪ͨ����ϸID
	private long loanContractID = -1;			//�����ͬID
	private String loancontractCode = "";		//�����ͬ���
	private long nborrowclientid = -1;			//�����ID
	private long loanPayNoticeID = -1;			//����ſ�֪ͨ��ID
	private String loanPayNoticeCode = "";		//����ſ�֪ͨ�����
	private Timestamp lastClearInterestDate = null;	//�ϴν�Ϣ��
	private double balance = 0.0;					//ת�����
	private double rate = 0.000000;					//�ſ�֪ͨ������
	private double interest = 0.00;					//ת����Ϣ
	private long payAccountID = -1;					//����˻�����ID
	private long transTypeID = -1;                  //��������
	private Timestamp interestStart = null ;    
	private long craContractDetailID = -1;          //�����Ӻ�ͬID
	public long getCraContractDetailID() {
		return craContractDetailID;
	}
	public void setCraContractDetailID(long craContractDetailID) {
		this.craContractDetailID = craContractDetailID;
	}
	public Timestamp getInterestStart() {
		return interestStart;
	}
	public void setInterestStart(Timestamp interestStart) {
		this.interestStart = interestStart;
	}
	public double getBalance() {
		return balance;
	}
	public long getTransTypeID() {
		return transTypeID;
	}
	public void setTransTypeID(long transTypeID) {
		this.transTypeID = transTypeID;
	}
	public void setBalance(double balance) {
		this.balance = balance;
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
	public long getNoticeDetailID() {
		return noticeDetailID;
	}
	public void setNoticeDetailID(long noticeDetailID) {
		this.noticeDetailID = noticeDetailID;
	}
	public long getPayAccountID() {
		return payAccountID;
	}
	public void setPayAccountID(long payAccountID) {
		this.payAccountID = payAccountID;
	}
	public long getNoticeFormId() {
		return noticeFormId;
	}
	public void setNoticeFormId(long noticeFormId) {
		this.noticeFormId = noticeFormId;
	}
	public long getOfficeID() {
		return officeID;
	}
	public void setOfficeID(long officeID) {
		this.officeID = officeID;
	}
	public double getRate() {
		return rate;
	}
	public void setRate(double rate) {
		this.rate = rate;
	}
	public long getTransferLoanAmountID() {
		return transferLoanAmountID;
	}
	public void setTransferLoanAmountID(long transferLoanAmountID) {
		this.transferLoanAmountID = transferLoanAmountID;
	}
}
