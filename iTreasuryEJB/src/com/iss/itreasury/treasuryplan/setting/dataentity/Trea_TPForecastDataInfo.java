/*
 * Created on 2004-07-19
 *
 * To change the template for this generated type comment go to 
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.treasuryplan.setting.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;

import com.iss.itreasury.treasuryplan.util.TreasuryPlanBaseDataEntity;

/**
 * Title:        		iTreasury
 * Description:         
 * Copyright:           Copyright (c) 2003
 * Company:             iSoftStone
 * @author              hewen hu
 * @version
 *  Date of Creation    2004-07-19
 */
public class Trea_TPForecastDataInfo extends TreasuryPlanBaseDataEntity implements Serializable {
	//ID		NUMBER
	private long Id = -1;
	//OFFICEID		NUMBER
	private long officeID = -1;
	//CURRENCYID		NUMBER
	private long currencyID = -1;
	//EXECUTEDATE		DATE
	private Timestamp executeDate = null;
	//TRANSACTIONDATE		DATE
	private Timestamp transactionDate = null;
	//LINEID		NUMBER
	private long lineID = -1;
	//LINENO		VARCHAR2(100)
	private String lineNo = "";
	//LINENAME		VARCHAR2(250)
	private String lineName = "";
	//LINELEVEL		NUMBER
	private long lineLevel = -1;
	//PARENTLINEID		NUMBER
	private long parentLineID = -1;
	//ISLEAF		NUMBER
	private long isLeaf = -1;
	//AUTHORIZEDDEPARTMENTID		NUMBER
	private String authorizedDepartment = "";
	//AUTHORIZEDUSERID		NUMBER
	private String authorizedUser = "";
	//FORECASTAMOUNT		NUMBER(20,2)
	private double forecastAmount = 0.0;
	//PLANAMOUNT		NUMBER(20,2)
	private double planAmount = 0.0;
	//INPUTTIME		DATE
	private Timestamp inputTime = null;
	//ISREADONLY		NUMBER
	private long isReadOnly = -1;

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
	 * @return Returns the executeDate.
	 */
	public Timestamp getExecuteDate() {
		return executeDate;
	}
	/**
	 * @param executeDate The executeDate to set.
	 */
	public void setExecuteDate(Timestamp executeDate) {
		putUsedField("executeDate", executeDate);
		this.executeDate = executeDate;
	}
	/**
	 * @return Returns the forecastAmount.
	 */
	public double getForecastAmount() {
		return forecastAmount;
	}
	/**
	 * @param forecastAmount The forecastAmount to set.
	 */
	public void setForecastAmount(double forecastAmount) {
		putUsedField("forecastAmount", forecastAmount);
		this.forecastAmount = forecastAmount;
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
	 * @return Returns the inputTime.
	 */
	public Timestamp getInputTime() {
		return inputTime;
	}
	/**
	 * @param inputTime The inputTime to set.
	 */
	public void setInputTime(Timestamp inputTime) {
		putUsedField("inputTime", inputTime);
		this.inputTime = inputTime;
	}
	/**
	 * @return Returns the isLeaf.
	 */
	public long getIsLeaf() {
		return isLeaf;
	}
	/**
	 * @param isLeaf The isLeaf to set.
	 */
	public void setIsLeaf(long isLeaf) {
		putUsedField("isLeaf", isLeaf);
		this.isLeaf = isLeaf;
	}
	/**
	 * @return Returns the isReadOnly.
	 */
	public long getIsReadOnly() {
		return isReadOnly;
	}
	/**
	 * @param isReadOnly The isReadOnly to set.
	 */
	public void setIsReadOnly(long isReadOnly) {
		putUsedField("isReadOnly", isReadOnly);
		this.isReadOnly = isReadOnly;
	}
	/**
	 * @return Returns the lineID.
	 */
	public long getLineID() {
		return lineID;
	}
	/**
	 * @param lineID The lineID to set.
	 */
	public void setLineID(long lineID) {
		putUsedField("lineID", lineID);
		this.lineID = lineID;
	}
	/**
	 * @return Returns the lineLevel.
	 */
	public long getLineLevel() {
		return lineLevel;
	}
	/**
	 * @param lineLevel The lineLevel to set.
	 */
	public void setLineLevel(long lineLevel) {
		putUsedField("lineLevel", lineLevel);
		this.lineLevel = lineLevel;
	}
	/**
	 * @return Returns the lineName.
	 */
	public String getLineName() {
		return lineName;
	}
	/**
	 * @param lineName The lineName to set.
	 */
	public void setLineName(String lineName) {
		putUsedField("lineName", lineName);
		this.lineName = lineName;
	}
	/**
	 * @return Returns the lineNo.
	 */
	public String getLineNo() {
		return lineNo;
	}
	/**
	 * @param lineNo The lineNo to set.
	 */
	public void setLineNo(String lineNo) {
		putUsedField("lineNo", lineNo);
		this.lineNo = lineNo;
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
	 * @return Returns the parentLineID.
	 */
	public long getParentLineID() {
		return parentLineID;
	}
	/**
	 * @param parentLineID The parentLineID to set.
	 */
	public void setParentLineID(long parentLineID) {
		putUsedField("parentLineID", parentLineID);
		this.parentLineID = parentLineID;
	}
	/**
	 * @return Returns the planAmount.
	 */
	public double getPlanAmount() {
		return planAmount;
	}
	/**
	 * @param planAmount The planAmount to set.
	 */
	public void setPlanAmount(double planAmount) {
		putUsedField("planAmount", planAmount);
		this.planAmount = planAmount;
	}
	/**
	 * @return Returns the transactionDate.
	 */
	public Timestamp getTransactionDate() {
		return transactionDate;
	}
	/**
	 * @param transactionDate The transactionDate to set.
	 */
	public void setTransactionDate(Timestamp transactionDate) {
		putUsedField("transactionDate", transactionDate);
		this.transactionDate = transactionDate;
	}
	/**
	 * @return Returns the authorizedDepartment.
	 */
	public String getAuthorizedDepartment() {
		return authorizedDepartment;
	}
	/**
	 * @param authorizedDepartment The authorizedDepartment to set.
	 */
	public void setAuthorizedDepartment(String authorizedDepartment) {
		putUsedField("authorizedDepartment", authorizedDepartment);
		this.authorizedDepartment = authorizedDepartment;
	}
	/**
	 * @return Returns the authorizedUser.
	 */
	public String getAuthorizedUser() {
		return authorizedUser;
	}
	/**
	 * @param authorizedUser The authorizedUser to set.
	 */
	public void setAuthorizedUser(String authorizedUser) {
		putUsedField("authorizedUser", authorizedUser);
		this.authorizedUser = authorizedUser;
	}
}
