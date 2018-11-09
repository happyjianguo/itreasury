package com.iss.itreasury.settlement.transferloancontract.dataentity;

import java.sql.Timestamp;

import com.iss.itreasury.util.ITreasuryBaseDataEntity;

public class TransferLoanAmountDetailinfo extends ITreasuryBaseDataEntity
{
	
	private long id=-1;
	private long officeid=-1;
	private long currencyid=-1;
	private long noticeformid=-1;
	private long contractid=-1;
	private double amount=-1;
	private long transferamountid=-1;
	private long loanaccountid=-1;
	private long statusid=-1;
	private long inputuserid=-1;
	private Timestamp inputdate=null;
	private double transferamount=0.0;
	
	
	
	private String contractcode="";
	private String paycode="";
	private String loanaccountdode="";
	
	private Timestamp intereststart = null;//起息日
	private Timestamp interestsettlement = null;//结息日
	private long cracontractid = -1;//转让合同ID
	private long transtypeid = -1;//交易类型ID
	private Timestamp perinterestsettlement = null;//上次结息日
	private double sellamount=0.0;
	private long borrowClientID = -1;             //借款人ID
	private long contractDetailID = -1;           //贷款子合同明细ID
	
	
	public long getContractDetailID() {
		return contractDetailID;
	}
	public void setContractDetailID(long contractDetailID) {
		this.contractDetailID = contractDetailID;
		putUsedField("contractDetailID",contractDetailID);
	}
	public Timestamp getPerinterestsettlement() {
		return perinterestsettlement;
	}
	public void setPerinterestsettlement(Timestamp perinterestsettlement) {
		this.perinterestsettlement = perinterestsettlement;
	}
	public long getTranstypeid() {
		return transtypeid;
	}
	public void setTranstypeid(long transtypeid) {
		this.transtypeid = transtypeid;
	}
	public long getCracontractid() {
		return cracontractid;
	}
	public void setCracontractid(long cracontractid) {
		this.cracontractid = cracontractid;
	}
	public Timestamp getInterestsettlement() {
		return interestsettlement;
	}
	public void setInterestsettlement(Timestamp interestsettlement) {
		this.interestsettlement = interestsettlement;
	}
	public Timestamp getIntereststart() {
		return intereststart;
	}
	public void setIntereststart(Timestamp intereststart) {
		this.intereststart = intereststart;
		putUsedField("intereststart",intereststart);
	}
	public double getTransferamount() {
		return transferamount;
	}
	public void setTransferamount(double transferamount) {
		this.transferamount = transferamount;
		putUsedField("transferamount",transferamount);
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
		putUsedField("id",id);
	}
	public long getOfficeid() {
		return officeid;
	}
	public void setOfficeid(long officeid) {
		this.officeid = officeid;
		putUsedField("officeid",officeid);
	}
	public long getCurrencyid() {
		return currencyid;
	}
	public void setCurrencyid(long currencyid) {
		this.currencyid = currencyid;
		putUsedField("currencyid",currencyid);
	}
	public long getNoticeformid() {
		return noticeformid;
	}
	public void setNoticeformid(long noticeformid) {
		this.noticeformid = noticeformid;
		putUsedField("noticeformid",noticeformid);
	}
	public long getContractid() {
		return contractid;
	}
	public void setContractid(long contractid) {
		this.contractid = contractid;
		putUsedField("contractid",contractid);
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
		putUsedField("amount",amount);
	}
	public long getTransferamountid() {
		return transferamountid;
	}
	public void setTransferamountid(long transferamountid) {
		this.transferamountid = transferamountid;
		putUsedField("transferamountid",transferamountid);
	}
	public long getLoanaccountid() {
		return loanaccountid;
	}
	public void setLoanaccountid(long loanaccountid) {
		this.loanaccountid = loanaccountid;
		putUsedField("loanaccountid",loanaccountid);
	}
	public long getStatusid() {
		return statusid;
	}
	public void setStatusid(long statusid) {
		this.statusid = statusid;
		putUsedField("statusid",statusid);
	}
	public long getInputuserid() {
		return inputuserid;
	}
	public void setInputuserid(long inputuserid) {
		this.inputuserid = inputuserid;
		putUsedField("inputuserid",inputuserid);
	}
	public Timestamp getInputdate() {
		return inputdate;
	}
	public void setInputdate(Timestamp inputdate) {
		this.inputdate = inputdate;
		putUsedField("inputdate",inputdate);
	}
	public String getContractcode() {
		return contractcode;
	}
	public void setContractcode(String contractcode) {
		this.contractcode = contractcode;
	}
	public String getPaycode() {
		return paycode;
	}
	public void setPaycode(String paycode) {
		this.paycode = paycode;
	}
	public String getLoanaccountdode() {
		return loanaccountdode;
	}
	public void setLoanaccountdode(String loanaccountdode) {
		this.loanaccountdode = loanaccountdode;
	}
	public double getSellamount() {
		return sellamount;
	}
	public void setSellamount(double sellamount) {
		this.sellamount = sellamount;
	}
	public long getBorrowClientID() {
		return borrowClientID;
	}
	public void setBorrowClientID(long borrowClientID) {
		this.borrowClientID = borrowClientID;
	}
	

	
}
