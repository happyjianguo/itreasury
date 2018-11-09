package com.iss.itreasury.craftbrother.query.dataentity;

import java.sql.Timestamp;

import com.iss.itreasury.securities.util.SECBaseDataEntity;

public class QueryAttornContractInfo  extends SECBaseDataEntity{
	private long id = -1;
	private Timestamp startInputDate = null;//查询开始日期
	private Timestamp endInputDate = null;	//查询结束日期
	private long contractTypeId = -1 ;		//合同类型ID
	private long officeId = -1;				//办事处ID
	private long currencyId = -1;			//币种ID
	private long typeId = -1;				//标识位
	
	public Timestamp getEndInputDate() {
		return endInputDate;
	}
	public void setEndInputDate(Timestamp endInputDate) {
		this.endInputDate = endInputDate;
	}
	public Timestamp getStartInputDate() {
		return startInputDate;
	}
	public void setStartInputDate(Timestamp startInputDate) {
		this.startInputDate = startInputDate;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getCurrencyId() {
		return currencyId;
	}
	public void setCurrencyId(long currencyId) {
		this.currencyId = currencyId;
	}
	public long getOfficeId() {
		return officeId;
	}
	public void setOfficeId(long officeId) {
		this.officeId = officeId;
	}
	public long getContractTypeId() {
		return contractTypeId;
	}
	public void setContractTypeId(long contractTypeId) {
		this.contractTypeId = contractTypeId;
	}
	public long getTypeId() {
		return typeId;
	}
	public void setTypeId(long typeId) {
		this.typeId = typeId;
	}
	
}
