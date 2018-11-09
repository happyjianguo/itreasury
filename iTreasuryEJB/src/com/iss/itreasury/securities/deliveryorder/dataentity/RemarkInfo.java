package com.iss.itreasury.securities.deliveryorder.dataentity;

import java.sql.Timestamp;

import com.iss.itreasury.securities.util.SECBaseDataEntity;

/**
 * Title:        		iTreasury
 * Description:         
 * Copyright:           Copyright (c) 2003
 * Company:             iSoftStone
 * @author             yehuang 
 * @version
 *  Date of Creation    2004-3-1
 */
public class RemarkInfo extends SECBaseDataEntity {


	private long id = -1;
	private String code = null;
	private String description = null;
	private long businessType = -1;
	private long statusID = -1;
	private long inputUserID = -1;
	private Timestamp inputDate = null;
	private long updateUserID = -1;
	private Timestamp updateDate = null;    


	/**
	 * Returns the businessType.
	 * @return long
	 */
	public long getBusinessType() {
		return businessType;
	}

	/**
	 * Returns the code.
	 * @return String
	 */
	public String getCode() {
		return code;
	}

	/**
	 * Returns the description.
	 * @return String
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Returns the id.
	 * @return long
	 */
	public long getId() {
		return id;
	}

	/**
	 * Returns the inputDate.
	 * @return Timestamp
	 */
	public Timestamp getInputDate() {
		return inputDate;
	}

	/**
	 * Returns the inputUserID.
	 * @return long
	 */
	public long getInputUserID() {
		return inputUserID;
	}

	/**
	 * Returns the statusID.
	 * @return long
	 */
	public long getStatusID() {
		return statusID;
	}

	/**
	 * Returns the updateDate.
	 * @return Timestamp
	 */
	public Timestamp getUpdateDate() {
		return updateDate;
	}

	/**
	 * Returns the updateUserID.
	 * @return long
	 */
	public long getUpdateUserID() {
		return updateUserID;
	}

	/**
	 * Sets the businessType.
	 * @param businessType The businessType to set
	 */
	public void setBusinessType(long businessType) {
		putUsedField("businessType", businessType);		
		this.businessType = businessType;
	}

	/**
	 * Sets the code.
	 * @param code The code to set
	 */
	public void setCode(String code) {
		putUsedField("code", code);		
		this.code = code;
	}

	/**
	 * Sets the description.
	 * @param description The description to set
	 */
	public void setDescription(String description) {
		putUsedField("description", description);
		this.description = description;
	}

	/**
	 * Sets the id.
	 * @param id The id to set
	 */
	public void setId(long id) {
		putUsedField("id", id);			
		this.id = id;
	}

	/**
	 * Sets the inputDate.
	 * @param inputDate The inputDate to set
	 */
	public void setInputDate(Timestamp inputDate) {
		putUsedField("inputDate", inputDate);		
		this.inputDate = inputDate;
	}

	/**
	 * Sets the inputUserID.
	 * @param inputUserID The inputUserID to set
	 */
	public void setInputUserID(long inputUserID) {
		putUsedField("inputUserID", inputUserID);		
		this.inputUserID = inputUserID;
	}

	/**
	 * Sets the statusID.
	 * @param statusID The statusID to set
	 */
	public void setStatusID(long statusID) {
		putUsedField("statusID", statusID);		
		this.statusID = statusID;
	}

	/**
	 * Sets the updateDate.
	 * @param updateDate The updateDate to set
	 */
	public void setUpdateDate(Timestamp updateDate) {
		putUsedField("updateDate", updateDate);		
		this.updateDate = updateDate;
	}

	/**
	 * Sets the updateUserID.
	 * @param updateUserID The updateUserID to set
	 */
	public void setUpdateUserID(long updateUserID) {
		putUsedField("updateUserID", updateUserID);		
		this.updateUserID = updateUserID;
	}

}
