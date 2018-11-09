package com.iss.itreasury.project.wisgfc.settlement.set.dataentity;

import java.sql.Timestamp;

import com.iss.itreasury.util.ITreasuryBaseDataEntity;

public class SetAcountRebateInfo extends ITreasuryBaseDataEntity{
	private long id;            //主键
	private long acountID;     //账户ID;
	private long clientId;     //客户ID;
	private long accountTypeId;     //账户类型
	private String accountNO;     //账户账号
	private double rebate;       //折扣率
	private long    setUserID;    //设置人ID
	private Timestamp    setDate;         //设置日期
	private long statusID;             //
	public long getStatusID() {
		return statusID;
	}
	public void setStatusID(long statusID) {
		this.statusID = statusID;
		putUsedField("STATESID", statusID);
	}
	public long getAcountID() {
		return acountID;
	}
	public void setAcountID(long acountID) {
		this.acountID = acountID;
		putUsedField("ACCOUNTID", acountID);
	}
	public double getRebate() {
		return rebate;
	}
	public void setRebate(double rebate) {
		this.rebate = rebate;
		putUsedField("REBETE", rebate);
	}
	public long getSetUserID() {
		return setUserID;
	}
	public void setSetUserID(long setUserID) {
		this.setUserID = setUserID;
		putUsedField("INPUTUSERID", setUserID);
	}
	public Timestamp getSetDate() {
		return setDate;
	}
	public void setSetDate(Timestamp setDate) {
		this.setDate = setDate;
		putUsedField("INPUTDATE", setDate);
	}
	public String getAccountNO() {
		return accountNO;
	}
	public void setAccountNO(String accountNO) {
		this.accountNO = accountNO;
	}
	public long getClientId() {
		return clientId;
	}
	public void setClientId(long clientId) {
		this.clientId = clientId;
	}
	public long getAccountTypeId() {
		return accountTypeId;
	}
	public void setAccountTypeId(long accountTypeId) {
		this.accountTypeId = accountTypeId;
	}
	public long getId() {
		return this.id;
	}
	public void setId(long id) {
		this.id = id;
		putUsedField("ID", id);
	}
}
