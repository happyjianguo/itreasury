/*
 * Created on 2004-6-7
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.system.logger.dataentity;

import java.io.Serializable;

import com.iss.sysframe.base.dataentity.BaseDataEntity;

/**
 * @author yiwang
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class QueryLoggerInfo extends BaseDataEntity implements Serializable{
	private String StartDate = "";
	private String EndDate = "";
	private long OfficeID = -1;
	private long CurrencyID = -1;
	private long ModuleID = -1;
	private long UserID = -1;
	private String UserName = "";
	private String Result = "";
	private long departmentid = -1;
	private long UserType = -1;
	private String endTime = "";
	/**
	 * 
	 */
	public QueryLoggerInfo ( )
	{
		super ( ) ;
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public long getId() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void setId(long id) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * @return Returns the currencyID.
	 */
	public long getCurrencyID ( )
	{
		return CurrencyID ;
	}
	/**
	 * @param currencyID The currencyID to set.
	 */
	public void setCurrencyID ( long currencyID )
	{
		CurrencyID = currencyID ;
	}
	/**
	 * @return Returns the endDate.
	 */
	public String getEndDate ( )
	{
		return EndDate ;
	}
	/**
	 * @param endDate The endDate to set.
	 */
	public void setEndDate ( String endDate )
	{
		EndDate = endDate ;
	}
	/**
	 * @return Returns the moduleID.
	 */
	public long getModuleID ( )
	{
		return ModuleID ;
	}
	/**
	 * @param moduleID The moduleID to set.
	 */
	public void setModuleID ( long moduleID )
	{
		ModuleID = moduleID ;
	}
	/**
	 * @return Returns the officeID.
	 */
	public long getOfficeID ( )
	{
		return OfficeID ;
	}
	/**
	 * @param officeID The officeID to set.
	 */
	public void setOfficeID ( long officeID )
	{
		OfficeID = officeID ;
	}
	/**
	 * @return Returns the startDate.
	 */
	public String getStartDate ( )
	{
		return StartDate ;
	}
	/**
	 * @param startDate The startDate to set.
	 */
	public void setStartDate ( String startDate )
	{
		StartDate = startDate ;
	}
	/**
	 * @return Returns the userID.
	 */
	public long getUserID ( )
	{
		return UserID ;
	}
	/**
	 * @param userID The userID to set.
	 */
	public void setUserID ( long userID )
	{
		UserID = userID ;
	}
	/**
	 * @return Returns the userName.
	 */
	public String getUserName ( )
	{
		return UserName ;
	}
	/**
	 * @param userName The userName to set.
	 */
	public void setUserName ( String userName )
	{
		UserName = userName ;
	}
	public String getResult() 
	{
		return Result;
	}
	public void setResult(String result) 
	{
		if (result.trim().equals("2")) {
			result = "";
		} else if (result.trim().equals("1")) {
			result = "成功";
		} else if (result.trim().equals("0")) {
			result = "失败";
		} else {
			throw new RuntimeException("操作结果输入参数有误!");
		}
		
		Result = result;
	}
	public long getDepartmentid() {
		return departmentid;
	}
	public void setDepartmentid(long departmentid) {
		this.departmentid = departmentid;
	}
	public long getUserType() {
		return UserType;
	}
	public void setUserType(long userType) {
		UserType = userType;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
}
