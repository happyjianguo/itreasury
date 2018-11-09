package com.iss.itreasury.craftbrother.transferloancontract.transfercontract.dataentity;

import java.sql.Timestamp;

import com.iss.itreasury.loan.contract.dataentity.ContractInfo;
import com.iss.itreasury.util.ITreasuryBaseDataEntity;

/**
 * @author minzhao
 * 
 * 信贷资产转让--转让合同明细表
 * 
 */
public class ContractdetailInfo extends ITreasuryBaseDataEntity {
	private long id = -1;

	private long officeid = -1;

	private long currencyid = -1;

	private long sapplyid = -1;

	private long loancontractid = -1; // 自营贷款合同ID

	private long transfercontractformid = -1; // 转让合同ID

	private long loannoteid = -1; // 自营贷款通知单ID

	private double transferamount = 0.0; // 转让金额

	private Timestamp startdate = null;

	private Timestamp enddate = null;

	private long statusid = -1;

	private long inputuserid = -1;

	private Timestamp inputdate = null;

	// --------------------------------------
	// 以下是非转让合同明细表中的字段，为查询所增加
	private ContractInfo contractinfo = null;

	private long borrowClientId = -1; // 自营贷款借款单位

	private String borrowClientName = null;
	
	private String loanContractCode = null;

	private double balance = 0.0; // 转让合同余额

	private double sellamount = 0.0; //已转让金额
	
	private String loanNoticeCode = null; //放款单号
	
	private Timestamp lastClearInterestDate = null; //上次结息日

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
		putUsedField("id", id);
	}

	public long getOfficeid() {
		return officeid;
	}

	public void setOfficeid(long officeid) {
		this.officeid = officeid;
		putUsedField("officeid", officeid);
	}

	public long getCurrencyid() {
		return currencyid;
	}

	public void setCurrencyid(long currencyid) {
		this.currencyid = currencyid;
		putUsedField("currencyid", currencyid);
	}

	public long getSapplyid() {
		return sapplyid;
	}

	public void setSapplyid(long sapplyid) {
		this.sapplyid = sapplyid;
		putUsedField("sapplyid", sapplyid);
	}

	public long getLoancontractid() {
		return loancontractid;
	}

	public void setLoancontractid(long loancontractid) {
		this.loancontractid = loancontractid;
		putUsedField("loancontractid", loancontractid);
	}

	public long getLoannoteid() {
		return loannoteid;
	}

	public void setLoannoteid(long loannoteid) {
		this.loannoteid = loannoteid;
		putUsedField("loannoteid", loannoteid);
	}

	public double getTransferamount() {
		return transferamount;
	}

	public void setTransferamount(double transferamount) {
		this.transferamount = transferamount;
		putUsedField("transferamount", transferamount);
	}

	public Timestamp getStartdate() {
		return startdate;
	}

	public void setStartdate(Timestamp startdate) {
		this.startdate = startdate;
		putUsedField("startdate", startdate);
	}

	public Timestamp getEnddate() {
		return enddate;
	}

	public void setEnddate(Timestamp enddate) {
		this.enddate = enddate;
		putUsedField("enddate", enddate);
	}

	public long getStatusid() {
		return statusid;
	}

	public void setStatusid(long statusid) {
		this.statusid = statusid;
		putUsedField("statusid", statusid);
	}

	public long getInputuserid() {
		return inputuserid;
	}

	public void setInputuserid(long inputuserid) {
		this.inputuserid = inputuserid;
		putUsedField("inputuserid", inputuserid);
	}

	public Timestamp getInputdate() {
		return inputdate;
	}

	public void setInputdate(Timestamp inputdate) {
		this.inputdate = inputdate;
		putUsedField("inputdate", inputdate);
	}

	public long getTransfercontractformid() {
		return transfercontractformid;
	}

	public void setTransfercontractformid(long transfercontractformid) {
		this.transfercontractformid = transfercontractformid;
		putUsedField("transfercontractformid", transfercontractformid);
	}

	public ContractInfo getContractinfo() {
		return contractinfo;
	}

	public void setContractinfo(ContractInfo contractinfo) {
		this.contractinfo = contractinfo;
	}

	public double getSellamount() {
		return sellamount;
	}

	public void setSellamount(double sellamount) {
		this.sellamount = sellamount;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public long getBorrowClientId() {
		return borrowClientId;
	}

	public void setBorrowClientId(long borrowClientId) {
		this.borrowClientId = borrowClientId;
	}

	public String getBorrowClientName() {
		return borrowClientName;
	}

	public void setBorrowClientName(String borrowClientName) {
		this.borrowClientName = borrowClientName;
	}

	public String getLoanContractCode() {
		return loanContractCode;
	}

	public void setLoanContractCode(String loanContractCode) {
		this.loanContractCode = loanContractCode;
	}

	public String getLoanNoticeCode() {
		return loanNoticeCode;
	}

	public void setLoanNoticeCode(String loanNoticeCode) {
		this.loanNoticeCode = loanNoticeCode;
	}

	public Timestamp getLastClearInterestDate() {
		return lastClearInterestDate;
	}

	public void setLastClearInterestDate(Timestamp lastClearInterestDate) {
		this.lastClearInterestDate = lastClearInterestDate;
	}
}
