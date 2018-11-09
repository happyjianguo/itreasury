package com.iss.itreasury.craftbrother.transferloancontract.transferloancontract.dataentity;

import java.sql.Timestamp;

import com.iss.itreasury.craftbrother.transferloancontract.transferapply.dataentity.TransferApplyInfo;
import com.iss.itreasury.craftbrother.transferloancontract.transfercontract.dataentity.TransferContractInfo;
import com.iss.itreasury.system.approval.dataentity.InutParameterInfo;
import com.iss.itreasury.util.ITreasuryBaseDataEntity;

public class LoanapplyformInfo extends ITreasuryBaseDataEntity 
{
	private long id=-1;
	private long officeid=-1;
	private long currencyid=-1;
	private String sloanapplycode="";
	private long sapplyformid=-1;
	private long transtypeid=-1;
	private long statusid=-1;
	private long inputuserid=-1;
	private Timestamp inputdate=null;
	
	
	private String inputusername="";
	private double transferamount=0.0;
	private Timestamp applystartdate=null;
	private Timestamp applyenddate=null;
	
	public String applyid="";
	public String applycode="";
	public String amount="";
	public String startdate="";
	public String enddate="";
	
	public long queryloancontracttype=-1;
	public long queryloancontractstatus=-1;
	public double querycontractamountstart=0.0;
	public double querycontractamountend=0.0;
	public String queryloancontractcodestart="";
	public String queryloancontractcodeend="";
	
	public long querycounterpartid=-1;
	public String counterpartname="";
	
	
	public String clientname="";
	public long clientid=-1;
	public long loancontractpayformid=-1;
	public InutParameterInfo  inutparameterinfo=null;
	public TransferApplyInfo applyinfo=null;
	public TransferContractInfo transfercontractinfo=null;
	
	
	public long getLoancontractpayformid() {
		return loancontractpayformid;
	}
	public void setLoancontractpayformid(long loancontractpayformid) {
		this.loancontractpayformid = loancontractpayformid;
	}
	public TransferApplyInfo getApplyinfo() {
		return applyinfo;
	}
	public void setApplyinfo(TransferApplyInfo applyinfo) {
		this.applyinfo = applyinfo;
	}
	public InutParameterInfo getInutparameterinfo() {
		return inutparameterinfo;
	}
	public void setInutparameterinfo(InutParameterInfo inutparameterinfo) {
		this.inutparameterinfo = inutparameterinfo;
	}
	public String getApplyid() {
		return applyid;
	}
	public void setApplyid(String applyid) {
		this.applyid = applyid;
	}
	public String getApplycode() {
		return applycode;
	}
	public void setApplycode(String applycode) {
		this.applycode = applycode;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getStartdate() {
		return startdate;
	}
	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}
	public String getEnddate() {
		return enddate;
	}
	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}
	public long getQueryloancontracttype() {
		return queryloancontracttype;
	}
	public void setQueryloancontracttype(long queryloancontracttype) {
		this.queryloancontracttype = queryloancontracttype;
	}
	public long getQueryloancontractstatus() {
		return queryloancontractstatus;
	}
	public void setQueryloancontractstatus(long queryloancontractstatus) {
		this.queryloancontractstatus = queryloancontractstatus;
	}
	public double getQuerycontractamountstart() {
		return querycontractamountstart;
	}
	public void setQuerycontractamountstart(double querycontractamountstart) {
		this.querycontractamountstart = querycontractamountstart;
	}
	public double getQuerycontractamountend() {
		return querycontractamountend;
	}
	public void setQuerycontractamountend(double querycontractamountend) {
		this.querycontractamountend = querycontractamountend;
	}
	public String getQueryloancontractcodestart() {
		return queryloancontractcodestart;
	}
	public void setQueryloancontractcodestart(String queryloancontractcodestart) {
		this.queryloancontractcodestart = queryloancontractcodestart;
	}
	public String getQueryloancontractcodeend() {
		return queryloancontractcodeend;
	}
	public void setQueryloancontractcodeend(String queryloancontractcodeend) {
		this.queryloancontractcodeend = queryloancontractcodeend;
	}
	
	public String getClientname() {
		return clientname;
	}

	public void setClientname(String clientname) {
		this.clientname = clientname;
	}

	public long getClientid() {
		return clientid;
	}

	public void setClientid(long clientid) {
		this.clientid = clientid;
	}
	private LoancontractdetailInfo[] info=null;
	
	public LoancontractdetailInfo[] getInfo() {
		return info;
	}
	public void setInfo(LoancontractdetailInfo[] info) {
		this.info = info;
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
	public String getSloanapplycode() {
		return sloanapplycode;
	}
	public void setSloanapplycode(String sloanapplycode) {
		this.sloanapplycode = sloanapplycode;
		putUsedField("sloanapplycode",sloanapplycode);
	}
	public long getSapplyformid() {
		return sapplyformid;
	}
	public void setSapplyformid(long sapplyformid) {
		this.sapplyformid = sapplyformid;
		putUsedField("sapplyformid",sapplyformid);
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
	public long getTranstypeid() {
		return transtypeid;
	}
	public void setTranstypeid(long transtypeid) {
		this.transtypeid = transtypeid;
		putUsedField("transtypeid",transtypeid);
	}
	public TransferContractInfo getTransfercontractinfo() {
		return transfercontractinfo;
	}
	public void setTransfercontractinfo(TransferContractInfo transfercontractinfo) {
		this.transfercontractinfo = transfercontractinfo;
	}
	public long getQuerycounterpartid() {
		return querycounterpartid;
	}
	public void setQuerycounterpartid(long querycounterpartid) {
		this.querycounterpartid = querycounterpartid;
	}
	public String getCounterpartname() {
		return counterpartname;
	}
	public void setCounterpartname(String counterpartname) {
		this.counterpartname = counterpartname;
	}
	public String getInputusername() {
		return inputusername;
	}
	public void setInputusername(String inputusername) {
		this.inputusername = inputusername;
	}
	public double getTransferamount() {
		return transferamount;
	}
	public void setTransferamount(double transferamount) {
		this.transferamount = transferamount;
	}
	public Timestamp getApplystartdate() {
		return applystartdate;
	}
	public void setApplystartdate(Timestamp applystartdate) {
		this.applystartdate = applystartdate;
	}
	public Timestamp getApplyenddate() {
		return applyenddate;
	}
	public void setApplyenddate(Timestamp applyenddate) {
		this.applyenddate = applyenddate;
	}
}
