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
public class QueryVersionInfo extends TreasuryPlanBaseDataEntity implements Serializable
{

	/**
	 * 
	 */
	public QueryVersionInfo()
	{
		super();
		// TODO Auto-generated constructor stub
	}
	private long officeID = -1;                      //���´�  
	private long currencyID = -1;                    //����
	private long userID = -1;                    //�û�
	private long departmentID = -1;              //����
	private Timestamp PlanDateFrom = null;       // ��������
	private Timestamp PlanDateTo = null;         //  ��������
	private Timestamp StartDate = null;       // ��ʼ����
	private Timestamp EndDate = null;         //  ��������	
	private long versionID   =-1;           //�ʽ�ƻ��汾��
	private long PlanStatusID   =-1;           //�ʽ�ƻ��汾״̬
	
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
	/**
	 * @return Returns the planDateFrom.
	 */
	public Timestamp getPlanDateFrom() {
		return PlanDateFrom;
	}
	/**
	 * @param planDateFrom The planDateFrom to set.
	 */
	public void setPlanDateFrom(Timestamp planDateFrom) {
		PlanDateFrom = planDateFrom;
	}
	/**
	 * @return Returns the planDateTo.
	 */
	public Timestamp getPlanDateTo() {
		return PlanDateTo;
	}
	/**
	 * @param planDateTo The planDateTo to set.
	 */
	public void setPlanDateTo(Timestamp planDateTo) {
		PlanDateTo = planDateTo;
	}
	/**
	 * @return Returns the planStatusID.
	 */
	public long getPlanStatusID() {
		return PlanStatusID;
	}
	/**
	 * @param planStatusID The planStatusID to set.
	 */
	public void setPlanStatusID(long planStatusID) {
		PlanStatusID = planStatusID;
	}
}