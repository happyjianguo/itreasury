/**
 * Title:        		iTreasury
 * Description:         
 * Copyright:           Copyright (c) 2003
 * Company:             iSoftStone
 * @author              kewen hu 
 * @version
 * Date of Creation     2004-03-15
 */
package com.iss.itreasury.securities.setting.dataentity;

import java.sql.Timestamp;

import com.iss.itreasury.securities.util.SECBaseDataEntity;

public class RemarkInfo extends SECBaseDataEntity {
	//ID	NOT NULL	NUMBER
	private long id = -1;					//自增,唯一不空
	//CODE		VARCHAR2(5)
	private String Code = "";				//五位数字, 每次新增时自动提示最大的五位数，用户可以修改。
	//DESCRIPTION		VARCHAR2(250)
	private String Description = "";		//备注描述
	//BUSINESSTYPE		NUMBER
	private long BusinessType = -1;			//业务类型
	//STATUSID		NUMBER
	private long StatusID = -1;				//状态：1，有效；-1，已删除
	//INPUTUSERID		NUMBER
	private long InputUserID = -1;			//录入人
	//INPUTDATE		DATE
	private Timestamp InputDate = null;		//录入时间
	//UPDATEUSERID		NUMBER
	private long UpdateUserID = -1;			//修改人
	//UPDATEDATE		DATE
	private Timestamp UpdateDate = null;	//修改时间
	/**
	 * @return Returns the businessType.
	 */
	public long getBusinessType() {
		return BusinessType;
	}
	/**
	 * @param businessType The businessType to set.
	 */
	public void setBusinessType(long businessType) {
		putUsedField("businessType", businessType);
		BusinessType = businessType;
	}
	/**
	 * @return Returns the code.
	 */
	public String getCode() {
		return Code;
	}
	/**
	 * @param code The code to set.
	 */
	public void setCode(String code) {
		putUsedField("code", code);
		Code = code;
	}
	/**
	 * @return Returns the description.
	 */
	public String getDescription() {
		return Description;
	}
	/**
	 * @param description The description to set.
	 */
	public void setDescription(String description) {
		putUsedField("description", description);
		Description = description;
	}
	/**
	 * @return Returns the inputDate.
	 */
	public Timestamp getInputDate() {
		return InputDate;
	}
	/**
	 * @param inputDate The inputDate to set.
	 */
	public void setInputDate(Timestamp inputDate) {
		putUsedField("inputDate", inputDate);
		InputDate = inputDate;
	}
	/**
	 * @return Returns the inputUserID.
	 */
	public long getInputUserID() {
		return InputUserID;
	}
	/**
	 * @param inputUserID The inputUserID to set.
	 */
	public void setInputUserID(long inputUserID) {
		putUsedField("inputUserID", inputUserID);
		InputUserID = inputUserID;
	}
	/**
	 * @return Returns the statusID.
	 */
	public long getStatusID() {
		return StatusID;
	}
	/**
	 * @param statusID The statusID to set.
	 */
	public void setStatusID(long statusID) {
		putUsedField("statusID", statusID);
		StatusID = statusID;
	}
	/**
	 * @return Returns the updateDate.
	 */
	public Timestamp getUpdateDate() {
		return UpdateDate;
	}
	/**
	 * @param updateDate The updateDate to set.
	 */
	public void setUpdateDate(Timestamp updateDate) {
		putUsedField("updateDate", updateDate);
		UpdateDate = updateDate;
	}
	/**
	 * @return Returns the updateUserID.
	 */
	public long getUpdateUserID() {
		return UpdateUserID;
	}
	/**
	 * @param updateUserID The updateUserID to set.
	 */
	public void setUpdateUserID(long updateUserID) {
		putUsedField("updateUserID", updateUserID);
		UpdateUserID = updateUserID;
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
}