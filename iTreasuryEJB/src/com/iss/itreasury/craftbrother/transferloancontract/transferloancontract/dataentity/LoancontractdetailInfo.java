package com.iss.itreasury.craftbrother.transferloancontract.transferloancontract.dataentity;

import java.sql.Timestamp;

import com.iss.itreasury.loan.contract.dataentity.ContractInfo;
import com.iss.itreasury.util.ITreasuryBaseDataEntity;

public class LoancontractdetailInfo extends ITreasuryBaseDataEntity
{
	private long id=-1;
	private long officeid=-1;
	private long currencyid=-1;
	private long sapplyid=-1;
	private long loancontractid=-1;
	private String loancontractcode="";
	private long loannoteid=-1;
	private String loannotecode="";
	private double transferamount=0.0;
	private double leftoversattornmentamount=0.0;
	private double loancontractamount=0.0;
	private Timestamp startdate=null;
	
	private Timestamp enddate=null;
	private long statusid=-1;
	private long inputuserid=-1;
	private Timestamp inputdate=null;
	
	
	private ContractInfo contractinfo=null;
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
	public long getSapplyid() {
		return sapplyid;
	}
	public void setSapplyid(long sapplyid) {
		this.sapplyid = sapplyid;
		putUsedField("sapplyid",sapplyid);
	}
	public long getLoancontractid() {
		return loancontractid;
	}
	public void setLoancontractid(long loancontractid) {
		this.loancontractid = loancontractid;
		putUsedField("loancontractid",loancontractid);
	}
	public String getLoancontractcode() {
		return loancontractcode;
	}
	public void setLoancontractcode(String loancontractcode) {
		this.loancontractcode = loancontractcode;
		putUsedField("loancontractcode",loancontractcode);
	}
	public long getLoannoteid() {
		return loannoteid;
	}
	public void setLoannoteid(long loannoteid) {
		this.loannoteid = loannoteid;
		putUsedField("loannoteid",loannoteid);
	}
	public String getLoannotecode() {
		return loannotecode;
	}
	public void setLoannotecode(String loannotecode) {
		this.loannotecode = loannotecode;
		putUsedField("loannotecode",loannotecode);
	}
	public double getTransferamount() {
		return transferamount;
	}
	public void setTransferamount(double transferamount) {
		this.transferamount = transferamount;
		putUsedField("transferamount",transferamount);
	}
	public double getLeftoversattornmentamount() {
		return leftoversattornmentamount;
	}
	public void setLeftoversattornmentamount(double leftoversattornmentamount) {
		this.leftoversattornmentamount = leftoversattornmentamount;
		putUsedField("leftoversattornmentamount",leftoversattornmentamount);
	}
	public double getLoancontractamount() {
		return loancontractamount;
	}
	public void setLoancontractamount(double loancontractamount) {
		this.loancontractamount = loancontractamount;
		putUsedField("loancontractamount",loancontractamount);
	}
	public Timestamp getStartdate() {
		return startdate;
	}
	public void setStartdate(Timestamp startdate) {
		this.startdate = startdate;
		putUsedField("startdate",startdate);
	}
	public Timestamp getEnddate() {
		return enddate;
	}
	public void setEnddate(Timestamp enddate) {
		this.enddate = enddate;
		putUsedField("enddate",enddate);
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
	public ContractInfo getContractinfo() {
		return contractinfo;
	}
	public void setContractinfo(ContractInfo contractinfo) {
		this.contractinfo = contractinfo;
	}
	
	

	

}
