/*
 * Created on 2007-2-6
 *
 */
package com.iss.itreasury.fcinterface.bankportal.accesslog.dataentity;


import java.util.Date;

import com.iss.itreasury.fcinterface.bankportal.sysframe.dataentity.BaseDataEntity;

/**
 * @author xintan
 * 
 */
public class LoggerInfo extends BaseDataEntity {

	private long id = -1;

	private long moduleID = -1;//模块号，银企接口为12，资金监控为17

	private long userID = -1; //用户ID

	private String userName = ""; //用户名称

	private Date accessTime = null; //访问时间

	private long currencyID = -1;//币种

	private String functionPointCode = "";

	private String functionPointDescription = ""; // Function Point Description

	private String remoteIP = "";

	private String remoteHost = "";

	public long getId() {
		return id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.iss.itreasury.fcinterface.bankportal.sysframe.dataentity.BaseDataEntity#setId(long)
	 */
	public void setId(long id) {
		this.id = id;
		putUsedField("id", id);
	}

	/**
	 * @return Returns the accessTime.
	 */
	public Date getAccessTime ( )
	{
		return accessTime ;
	}
	/**
	 * @param accessTime The accessTime to set.
	 */
	public void setAccessTime ( Date accessTime )
	{
		this.accessTime = accessTime ;
		putUsedField ( "accessTime" , accessTime ) ;
	}
	/**
	 * @return Returns the currencyID.
	 */
	public long getCurrencyID ( )
	{
		return currencyID ;
	}
	/**
	 * @param currencyID The currencyID to set.
	 */
	public void setCurrencyID ( long currencyID )
	{
		this.currencyID = currencyID ;
		putUsedField ( "currencyID" , currencyID ) ;
	}
	/**
	 * @return Returns the functionPointCode.
	 */
	public String getFunctionPointCode ( )
	{
		return functionPointCode ;
	}
	/**
	 * @param functionPointCode The functionPointCode to set.
	 */
	public void setFunctionPointCode ( String functionPointCode )
	{
		this.functionPointCode = functionPointCode ;
		putUsedField ( "functionPointCode" , functionPointCode ) ;
	}
	/**
	 * @return Returns the functionPointDescription.
	 */
	public String getFunctionPointDescription ( )
	{
		return functionPointDescription ;
	}
	/**
	 * @param functionPointDescription The functionPointDescription to set.
	 */
	public void setFunctionPointDescription ( String functionPointDescription )
	{
		this.functionPointDescription = functionPointDescription ;
		putUsedField ( "functionPointDescription" , functionPointDescription ) ;
	}
	/**
	 * @return Returns the moduleID.
	 */
	public long getModuleID ( )
	{
		return moduleID ;
	}
	/**
	 * @param moduleID The moduleID to set.
	 */
	public void setModuleID ( long moduleID )
	{
		this.moduleID = moduleID ;
		putUsedField ( "moduleID" , moduleID ) ;
	}
	/**
	 * @return Returns the userID.
	 */
	public long getUserID ( )
	{
		return userID ;
	}
	/**
	 * @param userID The userID to set.
	 */
	public void setUserID ( long userID )
	{
		this.userID = userID ;
		putUsedField ( "userID" , userID ) ;
	}
	/**
	 * @return Returns the userName.
	 */
	public String getUserName ( )
	{
		return userName ;
	}
	/**
	 * @param userName The userName to set.
	 */
	public void setUserName ( String userName )
	{
		this.userName = userName ;
		putUsedField ( "userName" , userName ) ;
	}
	/**
	 * @return Returns the remoteHost.
	 */
	public String getRemoteHost() {
		return remoteHost;
	}
	/**
	 * @param remoteHost The remoteHost to set.
	 */
	public void setRemoteHost(String remoteHost) {
		this.remoteHost = remoteHost;
		putUsedField ( "remoteHost" , remoteHost ) ;
	}
	
	/**
	 * @return Returns the remoteIP.
	 */
	public String getRemoteIP() {
		return remoteIP;
	}
	/**
	 * @param remoteIP The remoteIP to set.
	 */
	public void setRemoteIP(String remoteIP) {
		this.remoteIP = remoteIP;
		putUsedField ( "remoteIP" , remoteIP ) ;
	}
}