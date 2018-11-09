package com.iss.itreasury.system.loginlog.dataentity;

import java.sql.Timestamp;

import com.iss.itreasury.util.ITreasuryBaseDataEntity;

public class LoginLogInfo extends ITreasuryBaseDataEntity {
	private long id;						//序号
	private long userId;					//用户ID
	private String userName;				//用户名
	private long userType;					//用户类型，网银还是财务
	private Timestamp accessTime;			//登录时间
	private long clientId;					//客户
	private long officeId;					//办事处
	private long currencyId;				//币种
	private String clientIp;				//客户端IP
	private String hostIp;					//主机IP
	private String startDate;				//开始日期，用于查询
	private String endDate;				//结束日期，用于查询
	
	private long status=-1;
	
	private String reason="";
	
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
		putUsedField("reason", reason);
	}
	public long getStatus() {
		return status;
	}
	public void setStatus(long status) {
		this.status = status;
		putUsedField("status", status);
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
		putUsedField("id", id);
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
		putUsedField("userid", userId);
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
		putUsedField("username", userName);
	}
	public long getUserType() {
		return userType;
	}
	public void setUserType(long userType) {
		this.userType = userType;
		putUsedField("usertype", userType);
	}
	public Timestamp getAccessTime() {
		return accessTime;
	}
	public void setAccessTime(Timestamp accessTime) {
		this.accessTime = accessTime;
		putUsedField("accesstime", accessTime);
	}
	public long getClientId() {
		return clientId;
	}
	public void setClientId(long clientId) {
		this.clientId = clientId;
		putUsedField("clientid", clientId);
	}
	
	public long getOfficeId() {
		return officeId;
	}
	public void setOfficeId(long officeId) {
		this.officeId = officeId;
		putUsedField("officeid", officeId);
	}
	public long getCurrencyId() {
		return currencyId;
	}
	public void setCurrencyId(long currencyId) {
		this.currencyId = currencyId;
		putUsedField("currencyid", currencyId);
	}
	public String getClientIp() {
		return clientIp;
	}
	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
		putUsedField("clientip", clientIp);
	}
	public String getHostIp() {
		return hostIp;
	}
	public void setHostIp(String hostIp) {
		this.hostIp = hostIp;
		putUsedField("hostip", hostIp);
	}
	
	
	
	
	

}
