package com.iss.itreasury.custommenu.dataentity;

import java.util.Collection;

public class User_customprivilegeInfo {
	private long id;
	private long userId;
	private String dModuleName;
	private long statusId;
	private Collection detail;

	public String getDModuleName() {
		return dModuleName;
	}
	public void setDModuleName(String moduleName) {
		dModuleName = moduleName;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getStatusId() {
		return statusId;
	}
	public void setStatusId(long statusId) {
		this.statusId = statusId;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public Collection getDetail() {
		return detail;
	}
	public void setDetail(Collection detail) {
		this.detail = detail;
	}
}
