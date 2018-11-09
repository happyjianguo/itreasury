/*
 * Created on 2004-07-9
 *
 * To change the template for this generated type comment go to 
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.treasuryplan.mywork.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;

import com.iss.itreasury.treasuryplan.util.TreasuryPlanBaseDataEntity;

/**
 * Title:        		iTreasury
 * Description:         
 * Copyright:           Copyright (c) 2003
 * Company:             iSoftStone
 * @author              kewen hu
 * @version
 *  Date of Creation    2004-07-29
 */
public class MyWorkInfo extends TreasuryPlanBaseDataEntity implements Serializable {
	//ID	NOT NULL	NUMBER
	private long Id = -1;
	//OFFICEID		NUMBER
	private long officeID = -1;
	//CURRENCYID		NUMBER
	private long currencyID = -1;
	//DEPARTMENTID		NUMBER
	private long departmentID = -1;
	//PLANDATE		DATE
	private Timestamp planDate = null;
	//STARTDATE		DATE
	private Timestamp startDate = null;
	//ENDDATE		DATE
	private Timestamp endDate = null;
	//AMOUNTUNITID		NUMBER
	private long amountUnitID = -1;
	//INPUTUSERID		NUMBER
	private long inputUserID = -1;
	//INPUTDATE		DATE
	private Timestamp inputDate = null;
	//UPDATEUSERID		NUMBER
	private long updateUserID = -1;
	//UPDATEDATE		DATE
	private Timestamp updateDate = null;
	//NEXTCHECKUSERID		NUMBER
	private long nextCheckUserID = -1;
	//TSTAMP		DATE
	private Timestamp tstamp = null;
	//STATUSID		NUMBER
	private long statusID = -1;
	//NEXTCHECKLEVEL		NUMBER
	private long nextCheckLevel = -1;
	//CODE		VARCHAR2(250)
	private String code = "";

	/**
	 * @return Returns the amountUnitID.
	 */
	public long getAmountUnitID() {
		return amountUnitID;
	}
	/**
	 * @param amountUnitID The amountUnitID to set.
	 */
	public void setAmountUnitID(long amountUnitID) {
		putUsedField("amountUnitID", amountUnitID);
		this.amountUnitID = amountUnitID;
	}
	/**
	 * @return Returns the code.
	 */
	public String getCode() {
		return code;
	}
	/**
	 * @param code The code to set.
	 */
	public void setCode(String code) {
		putUsedField("code", code);
		this.code = code;
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
		putUsedField("currencyID", currencyID);
		this.currencyID = currencyID;
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
		putUsedField("departmentID", departmentID);
		this.departmentID = departmentID;
	}
	/**
	 * @return Returns the endDate.
	 */
	public Timestamp getEndDate() {
		return endDate;
	}
	/**
	 * @param endDate The endDate to set.
	 */
	public void setEndDate(Timestamp endDate) {
		putUsedField("endDate", endDate);
		this.endDate = endDate;
	}
	/**
	 * @return Returns the id.
	 */
	public long getId() {
		return Id;
	}
	/**
	 * @param id The id to set.
	 */
	public void setId(long id) {
		putUsedField("id", id);
		Id = id;
	}
	/**
	 * @return Returns the inputDate.
	 */
	public Timestamp getInputDate() {
		return inputDate;
	}
	/**
	 * @param inputDate The inputDate to set.
	 */
	public void setInputDate(Timestamp inputDate) {
		putUsedField("inputDate", inputDate);
		this.inputDate = inputDate;
	}
	/**
	 * @return Returns the inputUserID.
	 */
	public long getInputUserID() {
		return inputUserID;
	}
	/**
	 * @param inputUserID The inputUserID to set.
	 */
	public void setInputUserID(long inputUserID) {
		putUsedField("inputUserID", inputUserID);
		this.inputUserID = inputUserID;
	}
	/**
	 * @return Returns the nextCheckLevel.
	 */
	public long getNextCheckLevel() {
		return nextCheckLevel;
	}
	/**
	 * @param nextCheckLevel The nextCheckLevel to set.
	 */
	public void setNextCheckLevel(long nextCheckLevel) {
		putUsedField("nextCheckLevel", nextCheckLevel);
		this.nextCheckLevel = nextCheckLevel;
	}
	/**
	 * @return Returns the nextCheckUserID.
	 */
	public long getNextCheckUserID() {
		return nextCheckUserID;
	}
	/**
	 * @param nextCheckUserID The nextCheckUserID to set.
	 */
	public void setNextCheckUserID(long nextCheckUserID) {
		putUsedField("nextCheckUserID", nextCheckUserID);
		this.nextCheckUserID = nextCheckUserID;
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
		putUsedField("officeID", officeID);
		this.officeID = officeID;
	}
	/**
	 * @return Returns the planDate.
	 */
	public Timestamp getPlanDate() {
		return planDate;
	}
	/**
	 * @param planDate The planDate to set.
	 */
	public void setPlanDate(Timestamp planDate) {
		putUsedField("planDate", planDate);
		this.planDate = planDate;
	}
	/**
	 * @return Returns the startDate.
	 */
	public Timestamp getStartDate() {
		return startDate;
	}
	/**
	 * @param startDate The startDate to set.
	 */
	public void setStartDate(Timestamp startDate) {
		putUsedField("startDate", startDate);
		this.startDate = startDate;
	}
	/**
	 * @return Returns the statusID.
	 */
	public long getStatusID() {
		return statusID;
	}
	/**
	 * @param statusID The statusID to set.
	 */
	public void setStatusID(long statusID) {
		putUsedField("statusID", statusID);
		this.statusID = statusID;
	}
	/**
	 * @return Returns the tstamp.
	 */
	public Timestamp getTstamp() {
		return tstamp;
	}
	/**
	 * @param tstamp The tstamp to set.
	 */
	public void setTstamp(Timestamp tstamp) {
		putUsedField("tstamp", tstamp);
		this.tstamp = tstamp;
	}
	/**
	 * @return Returns the updateDate.
	 */
	public Timestamp getUpdateDate() {
		return updateDate;
	}
	/**
	 * @param updateDate The updateDate to set.
	 */
	public void setUpdateDate(Timestamp updateDate) {
		putUsedField("updateDate", updateDate);
		this.updateDate = updateDate;
	}
	/**
	 * @return Returns the updateUserID.
	 */
	public long getUpdateUserID() {
		return updateUserID;
	}
	/**
	 * @param updateUserID The updateUserID to set.
	 */
	public void setUpdateUserID(long updateUserID) {
		putUsedField("updateUserID", updateUserID);
		this.updateUserID = updateUserID;
	}
}
