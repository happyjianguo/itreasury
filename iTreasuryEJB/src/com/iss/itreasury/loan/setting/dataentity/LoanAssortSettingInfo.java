package com.iss.itreasury.loan.setting.dataentity;

import java.sql.Timestamp;

import com.iss.itreasury.util.ITreasuryBaseDataEntity;

public class LoanAssortSettingInfo extends ITreasuryBaseDataEntity {
	private long id = -1;// 主键id

	private long currencyId = -1;// 币种

	private long officeId = -1;// 办事处

	private long assortTypeId = -1;// 业务类型

	private String name = "";// 分类名称

	private long inputUserID = -1;// 录入人ID

	private Timestamp inputDate = null;// 录入时间

	private long statusId = -1;// 状态
	
	private String inputuserName = "";
	

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
		putUsedField("ID", id);
	}

	public Timestamp getInputDate() {
		return inputDate;
	}

	public void setInputDate(Timestamp inputDate) {
		this.inputDate = inputDate;
		putUsedField("inputDate", inputDate);
	}

	public long getInputUserID() {
		return inputUserID;
	}

	public void setInputUserID(long inputUserID) {
		this.inputUserID = inputUserID;
		putUsedField("inputUserID", inputUserID);
	}

	public long getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(long currencyId) {
		this.currencyId = currencyId;
		putUsedField("currencyId", currencyId);
	}

	public long getOfficeId() {
		return officeId;
	}

	public void setOfficeId(long officeId) {
		this.officeId = officeId;
		putUsedField("officeId", officeId);
	}

	public long getStatusId() {
		return statusId;
	}

	public void setStatusId(long statusId) {
		this.statusId = statusId;
		putUsedField("statusId", statusId);
	}

	public long getAssortTypeId() {
		return assortTypeId;
	}

	public String getName(){
		return name;
	}
	
	public void setAssortTypeId(long assortTypeId) {
		this.assortTypeId = assortTypeId;
		putUsedField("assortTypeId", assortTypeId);
	}
	
	public void setName(String name) {
		this.name = name;
		putUsedField("NAME", name);
	}

	public String getInputuserName() {
		return inputuserName;
	}

	public void setInputuserName(String inputuserName) {
		this.inputuserName = inputuserName;
	}
}   
