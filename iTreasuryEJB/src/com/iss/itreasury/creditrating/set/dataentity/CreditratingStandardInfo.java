package com.iss.itreasury.creditrating.set.dataentity;

import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.ITreasuryBaseDataEntity;

public class CreditratingStandardInfo extends ITreasuryBaseDataEntity
{
	
	private long id=-1;//标准等级主表id
	private String name="";//标准等级名称
	private String description="";//标准等级描述
	private String remark="";//标准等级备注

	private long officeid=-1;
	private long currencyid=-1;
	private long inputuserid=-1;
	java.sql.Timestamp inputdate=null;
	private long state=-1;
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
		putUsedField("description",description);
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
		putUsedField("id",id);
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
		putUsedField("name",name);
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
		putUsedField("remark",remark);
	}
	public long getCurrencyid() {
		return currencyid;
	}
	public void setCurrencyid(long currencyid) {
		this.currencyid = currencyid;
		putUsedField("currencyid",currencyid);
	}
	public java.sql.Timestamp getInputdate() {
		return inputdate;
	}
	public void setInputdate(java.sql.Timestamp inputdate) {
		this.inputdate = inputdate;
		putUsedField("inputdate",inputdate);
	}
	public long getInputuserid() {
		return inputuserid;
	}
	public void setInputuserid(long inputuserid) {
		this.inputuserid = inputuserid;
		putUsedField("inputuserid",inputuserid);
	}
	public long getOfficeid() {
		return officeid;
	}
	public void setOfficeid(long officeid) {
		this.officeid = officeid;
		putUsedField("officeid",officeid);
	}
	public long getState() {
		return state;
	}
	public void setState(long state) {
		this.state = state;
		putUsedField("state",state);
	}
	
	
	
	

}
