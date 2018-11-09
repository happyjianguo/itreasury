package com.iss.itreasury.clientmanage.client.dataentity;

import com.iss.itreasury.util.ITreasuryBaseDataEntity;

public class DepositAndLoanMessageInfo extends ITreasuryBaseDataEntity{

	private long id = -1;
	private long clientId = -1;
	private String departmentCode = "";
	private String economicComponentCode = "";
	private String areaCode = "";
	private String industryCode = "";
	private String sizeCode = "";
	private long nStatusId = -1;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
		putUsedField("id", id);
	}
	public long getClientId() {
		return clientId;
	}
	public void setClientId(long clientId) {
		this.clientId = clientId;
		putUsedField("clientId", clientId);
	}
	public String getDepartmentCode() {
		return departmentCode;
	}
	public void setDepartmentCode(String departmentCode) {
		this.departmentCode = departmentCode;
		putUsedField("departmentCode", departmentCode);
	}
	public String getEconomicComponentCode() {
		return economicComponentCode;
	}
	public void setEconomicComponentCode(String economicComponentCode) {
		this.economicComponentCode = economicComponentCode;
		putUsedField("economicComponentCode", economicComponentCode);
	}
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
		putUsedField("areaCode", areaCode);
	}
	public String getIndustryCode() {
		return industryCode;
	}
	public void setIndustryCode(String industryCode) {
		this.industryCode = industryCode;
		putUsedField("industryCode", industryCode);
	}
	public String getSizeCode() {
		return sizeCode;
	}
	public void setSizeCode(String sizeCode) {
		this.sizeCode = sizeCode;
		putUsedField("sizeCode", sizeCode);
	}
	public long getNStatusId() {
		return nStatusId;
	}
	public void setNStatusId(long statusId) {
		nStatusId = statusId;
		putUsedField("nStatusId", statusId);
	}
	
}
