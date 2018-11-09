package com.iss.itreasury.system.logger.dataentity;

import java.sql.Timestamp;

import com.iss.itreasury.util.ITreasuryBaseDataEntity;

/**
 * 业务操作日志控制到按钮级
 * 
 * @author JBPan May 23, 2012 CopyRight by Isoftstone
 */
public class LoggerBtnLevelInfo extends ITreasuryBaseDataEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private String sBusinessType = ""; // 业务类型
	private String sTransCode = ""; // 单据编号（有则记录）
	private long lActionTypeID = -1; // 操作类型id 没有在数据库中，不需要putUsedField
	private String sActionType = ""; // 操作类型（保存，删除，暂存...）
	private String sResult = ""; // 操作结果（成功，失败）
	private String sFailReason = ""; // 失败原因 有时存放的是错误代码，在查询显示时需要转换一下
	private String departmentName = "";

	private long Id = -1;
	private long moduleID = -1;
	private long userID = -1;
	private String userName = "";
	private Timestamp accessTime = null;
	private long officeID = -1;
	private long currencyID = -1;
	private String functionPointCode = "";
	private String functionPointDescription = ""; // Function Point
													// Description
	private String remoteIP = "";
	private String remoteHost = "";

	/**
	 * @return Returns the id.
	 */
	public long getId() {
		return Id;
	}

	/**
	 * @param id
	 *            The id to set.
	 */
	public void setId(long id) {
		Id = id;
		putUsedField("id", id);
	}

	/**
	 * @return Returns the accessTime.
	 */
	public Timestamp getAccessTime() {
		return accessTime;
	}

	/**
	 * @param accessTime
	 *            The accessTime to set.
	 */
	public void setAccessTime(Timestamp accessTime) {
		this.accessTime = accessTime;
		putUsedField("accessTime", accessTime);
	}

	/**
	 * @return Returns the currencyID.
	 */
	public long getCurrencyID() {
		return currencyID;
	}

	/**
	 * @param currencyID
	 *            The currencyID to set.
	 */
	public void setCurrencyID(long currencyID) {
		this.currencyID = currencyID;
		putUsedField("currencyID", currencyID);
	}

	/**
	 * @return Returns the functionPointCode.
	 */
	public String getFunctionPointCode() {
		return functionPointCode;
	}

	/**
	 * @param functionPointCode
	 *            The functionPointCode to set.
	 */
	public void setFunctionPointCode(String functionPointCode) {
		this.functionPointCode = functionPointCode;
		putUsedField("functionPointCode", functionPointCode);
	}

	/**
	 * @return Returns the functionPointDescription.
	 */
	public String getFunctionPointDescription() {
		return functionPointDescription;
	}

	/**
	 * @param functionPointDescription
	 *            The functionPointDescription to set.
	 */
	public void setFunctionPointDescription(String functionPointDescription) {
		this.functionPointDescription = functionPointDescription;
		putUsedField("functionPointDescription", functionPointDescription);
	}

	/**
	 * @return Returns the moduleID.
	 */
	public long getModuleID() {
		return moduleID;
	}

	/**
	 * @param moduleID
	 *            The moduleID to set.
	 */
	public void setModuleID(long moduleID) {
		this.moduleID = moduleID;
		putUsedField("moduleID", moduleID);
	}

	/**
	 * @return Returns the officeID.
	 */
	public long getOfficeID() {
		return officeID;
	}

	/**
	 * @param officeID
	 *            The officeID to set.
	 */
	public void setOfficeID(long officeID) {
		this.officeID = officeID;
		putUsedField("officeID", officeID);
	}

	/**
	 * @return Returns the userID.
	 */
	public long getUserID() {
		return userID;
	}

	/**
	 * @param userID
	 *            The userID to set.
	 */
	public void setUserID(long userID) {
		this.userID = userID;
		putUsedField("userID", userID);
	}

	/**
	 * @return Returns the userName.
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName
	 *            The userName to set.
	 */
	public void setUserName(String userName) {
		this.userName = userName;
		putUsedField("userName", userName);
	}

	/**
	 * @return Returns the remoteHost.
	 */
	public String getRemoteHost() {
		return remoteHost;
	}

	/**
	 * @param remoteHost
	 *            The remoteHost to set.
	 */
	public void setRemoteHost(String remoteHost) {
		this.remoteHost = remoteHost;
		putUsedField("remoteHost", remoteHost);
	}

	/**
	 * @return Returns the remoteIP.
	 */
	public String getRemoteIP() {
		return remoteIP;
	}

	/**
	 * @param remoteIP
	 *            The remoteIP to set.
	 */
	public void setRemoteIP(String remoteIP) {
		this.remoteIP = remoteIP;
		putUsedField("remoteIP", remoteIP);
	}

	public String getBusinessType() {
		return sBusinessType;
	}

	public void setBusinessType(String businessType) {
		sBusinessType = businessType;
		putUsedField("sbusinesstype", businessType);
	}

	public String getTransCode() {
		return sTransCode;
	}

	public void setTransCode(String transCode) {
		sTransCode = transCode;
		putUsedField("stranscode", transCode);
	}

	public String getActionType() {
		return sActionType;
	}

	public void setActionType(String actionType) {
		sActionType = actionType;
		putUsedField("sactiontype", actionType);
	}

	public String getResult() {
		return sResult;
	}

	public void setResult(String result) {
		sResult = result;
		putUsedField("sresult", result);
	}

	public String getFailReason() {
		return sFailReason;
	}

	public void setFailReason(String failReason) {
		sFailReason = failReason;
		putUsedField("sfailreason", failReason);
	}

	public long getActionTypeID() {
		return lActionTypeID;
	}

	public void setActionTypeID(long actionTypeID) {
		lActionTypeID = actionTypeID;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

}
