package com.iss.itreasury.treasuryplan.setting.dataentity;
import java.io.Serializable;
import java.sql.Timestamp;


import com.iss.itreasury.treasuryplan.util.TreasuryPlanBaseDataEntity;

/**
 * Title:        		iTreasury
 * Description:         
 * Copyright:           Copyright (c) 2003
 * Company:             iSoftStone
 * @author               xrli 
 * @version
 *  Date of Creation    2004-2-27
 */
public class SystemParameterInfo extends TreasuryPlanBaseDataEntity implements Serializable
{
	private long		id 								= -1;	//主键
	private String		parameterName 					= "";	//参数名称	
	private Timestamp	effectiveDate		 			= null;//生效日期
	private double		parameterValue 					= 0.0;	//参数	
	private long		statusID 						= -1;	//状态Id
	private long		inputUserID 					= -1;	//录入人Id
	private Timestamp	inputDate 						= null;//录入时间
	private long		updateUserID 					= -1;	//修改人Id
	private Timestamp	updateDate 						= null;//修改时间
    private long      officeID                        = -1;   //办事处ID 
	
	public long getOfficeID() {
		return officeID;
	}
	public void setOfficeID(long officeID) {
		putUsedField("officeID", officeID);
		this.officeID = officeID;
	}
	/**
	 * @return Returns the effectiveDate.
	 */
	public Timestamp getEffectiveDate() {
		return effectiveDate;
	}
	/**
	 * @param effectiveDate The effectiveDate to set.
	 */
	public void setEffectiveDate(Timestamp effectiveDate) {
		putUsedField("effectiveDate", effectiveDate);
		this.effectiveDate = effectiveDate;
	}
	/**
	 * @return Returns the id.
	 */
	public long getId() {
		return id;
	}
	/**
	 * @param id The id to set.
	 */
	public void setId(long id) {
		putUsedField("id", id);
		this.id = id;
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
	 * @return Returns the parameterName.
	 */
	public String getParameterName() {
		return parameterName;
	}
	/**
	 * @param parameterName The parameterName to set.
	 */
	public void setParameterName(String parameterName) {
		putUsedField("parameterName", parameterName);
		this.parameterName = parameterName;
	}
	/**
	 * @return Returns the parameterValue.
	 */
	public double getParameterValue() {
		return parameterValue;
	}
	/**
	 * @param parameterValue The parameterValue to set.
	 */
	public void setParameterValue(double parameterValue) {
		putUsedField("parameterValue", parameterValue);
		this.parameterValue = parameterValue;
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
