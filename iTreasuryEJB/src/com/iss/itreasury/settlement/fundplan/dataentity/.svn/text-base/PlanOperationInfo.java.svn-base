package com.iss.itreasury.settlement.fundplan.dataentity;

import java.sql.Timestamp;

import com.iss.itreasury.util.ITreasuryBaseDataEntity;

public class PlanOperationInfo extends ITreasuryBaseDataEntity{
	private long id;							//ID
	private Timestamp relationDate = null;		//日期
	private String gaveDept = "";				//主送部门
	private String doDept = "";					//主办部门
	private String opinion = "";				//主送部门意见
	private String handleUser = "";				//经办人
	private String principal = "";				//负责人
	private String accountNo = "";				//止付账户
	private long officeid = -1;					//办事处
	private long currencyid = -1;				//币种
	private double inAmount = 0.0;
	private double outAmount = 0.0;
	
	public double getInAmount() {
		return inAmount;
	}
	public void setInAmount(double inAmount) {
		this.inAmount = inAmount;
	}
	public double getOutAmount() {
		return outAmount;
	}
	public void setOutAmount(double outAmount) {
		this.outAmount = outAmount;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
		putUsedField("id", id);
	}
	public Timestamp getRelationDate() {
		return relationDate;
	}
	public void setRelationDate(Timestamp relationDate) {
		this.relationDate = relationDate;
		putUsedField("relationDate", relationDate);
	}
	public String getGaveDept() {
		return gaveDept;
	}
	public void setGaveDept(String gaveDept) {
		this.gaveDept = gaveDept;
		putUsedField("gaveDept", gaveDept);
	}
	public String getDoDept() {
		return doDept;
	}
	public void setDoDept(String doDept) {
		this.doDept = doDept;
		putUsedField("doDept", doDept);
	}
	public String getOpinion() {
		return opinion;
	}
	public void setOpinion(String opinion) {
		this.opinion = opinion;
		putUsedField("opinion", opinion);
	}
	public String getHandleUser() {
		return handleUser;
	}
	public void setHandleUser(String handleUser) {
		this.handleUser = handleUser;
		putUsedField("handleUser", handleUser);
	}
	public String getPrincipal() {
		return principal;
	}
	public void setPrincipal(String principal) {
		this.principal = principal;
		putUsedField("principal", principal);
	}
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
		putUsedField("accountNo", accountNo);
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
}