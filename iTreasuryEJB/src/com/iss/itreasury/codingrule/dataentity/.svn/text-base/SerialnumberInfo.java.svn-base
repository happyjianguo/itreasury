package com.iss.itreasury.codingrule.dataentity;

import java.sql.Timestamp;

import com.iss.itreasury.util.ITreasuryBaseDataEntity;

public class SerialnumberInfo extends ITreasuryBaseDataEntity{
	private long id= -1;  //ID
	private String name = ""; //流水号名称
	//private long rulerelationid = -1; // 关联id
	private long officeid = -1; // 办事处ID
	private long iscurrency = -1; //流水号是否区分币种
	private long iscontract = -1; //流水号是否区分合同(放款通知单专用)
	private long  statusid = -1; //状态
	private long periodtype = -1; //流水号循环周期（1：年；2：月；3：日）
	
	//辅助信息(非表字段),如果该流水规则根据币种和合同区分,则从前台将以下两属性初始化
	private long currencyID = -1;
	private long contractID = -1;

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

	public long getIscontract() {
		return iscontract;
	}

	public void setIscontract(long iscontract) {
		this.iscontract = iscontract;
		putUsedField("iscontract", iscontract);
	}

	public long getIscurrency() {
		return iscurrency;
	}

	public void setIscurrency(long iscurrency) {
		this.iscurrency = iscurrency;
		putUsedField("iscurrency", iscurrency);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		putUsedField("name", name);
	}

	public long getPeriodtype() {
		return periodtype;
	}

	public void setPeriodtype(long periodtype) {
		this.periodtype = periodtype;
		putUsedField("periodtype", periodtype);
	}

	public long getStatusid() {
		return statusid;
	}

	public void setStatusid(long statusid) {
		this.statusid = statusid;
		putUsedField("statusid", statusid);
	}
	
	//辅助信息的set get方法
	public long getContractID() {
		return contractID;
	}

	public void setContractID(long contractID) {
		this.contractID = contractID;
	}

	public long getCurrencyID() {
		return currencyID;
	}

	public void setCurrencyID(long currencyID) {
		this.currencyID = currencyID;
	}

	
}
