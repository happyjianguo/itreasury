/*
 * Created on 2003-11-11
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.treasuryplan.query.paraminfo;

import java.io.Serializable;
import java.sql.Timestamp;

import com.iss.itreasury.treasuryplan.util.TreasuryPlanBaseDataEntity;

/**
 * @author xrli
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class QueryDataInfo extends TreasuryPlanBaseDataEntity implements Serializable
{

	/**
	 * 
	 */
	public QueryDataInfo()
	{
		super();
		// TODO Auto-generated constructor stub
	}
	private long officeID = -1;                      //办事处  
	private long currencyID = -1;                    //币种
	private long userID = -1;                    //用户
	private long departmentID = -1;              //部门
	private Timestamp StartDate = null;       // 开始日期
	private Timestamp EndDate = null;         //  结束日期
	private long StatType   =-1;              //统计方式 (按日，周，月，年，按月汇总，按年汇总)
	private long versionID   =-1;           //资金计划版本号
	
	/**
	 * @return Returns the endDate.
	 */
	public Timestamp getEndDate() {
		return EndDate;
	}
	/**
	 * @param endDate The endDate to set.
	 */
	public void setEndDate(Timestamp endDate) {
		EndDate = endDate;
	}
	/**
	 * @return Returns the startDate.
	 */
	public Timestamp getStartDate() {
		return StartDate;
	}
	/**
	 * @param startDate The startDate to set.
	 */
	public void setStartDate(Timestamp startDate) {
		StartDate = startDate;
	}
	/**
	 * @return Returns the statType.
	 */
	public long getStatType() {
		return StatType;
	}
	/**
	 * @param statType The statType to set.
	 */
	public void setStatType(long statType) {
		StatType = statType;
	}
	
	/**
	 * @return Returns the currencyID.
	 */
	public long getCurrencyID() {
		return currencyID;
	}
	/**
	 * @param currencyID The currencyID to set.
	 */
	public void setCurrencyID(long currencyID) {
		this.currencyID = currencyID;
	}
	/**
	 * @return Returns the officeID.
	 */
	public long getOfficeID() {
		return officeID;
	}
	/**
	 * @param officeID The officeID to set.
	 */
	public void setOfficeID(long officeID) {
		this.officeID = officeID;
	}
	/* (non-Javadoc)
	 * @see com.iss.itreasury.util.ITreasuryBaseDataEntity#getId()
	 */
	public long getId() {
		// TODO Auto-generated method stub
		return 0;
	}
	/* (non-Javadoc)
	 * @see com.iss.itreasury.util.ITreasuryBaseDataEntity#setId(long)
	 */
	public void setId(long id) {
		// TODO Auto-generated method stub
		
	}
	/**
	 * @return Returns the versionID.
	 */
	public long getVersionID() {
		return versionID;
	}
	/**
	 * @param versionID The versionID to set.
	 */
	public void setVersionID(long versionID) {
		this.versionID = versionID;
	}
	/**
	 * @return Returns the departmentID.
	 */
	public long getDepartmentID() {
		return departmentID;
	}
	/**
	 * @param departmentID The departmentID to set.
	 */
	public void setDepartmentID(long departmentID) {
		this.departmentID = departmentID;
	}
	/**
	 * @return Returns the userID.
	 */
	public long getUserID() {
		return userID;
	}
	/**
	 * @param userID The userID to set.
	 */
	public void setUserID(long userID) {
		this.userID = userID;
	}
}
