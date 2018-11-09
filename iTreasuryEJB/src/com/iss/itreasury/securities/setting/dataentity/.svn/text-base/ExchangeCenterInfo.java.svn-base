/**
 * Title:        		iTreasury
 * Description:         
 * Copyright:           Copyright (c) 2003
 * Company:             iSoftStone
 * @author              kewen hu 
 * @version
 * Date of Creation     2004-03-17
 */
package com.iss.itreasury.securities.setting.dataentity;

import java.sql.Timestamp;

import com.iss.itreasury.securities.util.SECBaseDataEntity;

public class ExchangeCenterInfo extends SECBaseDataEntity {
	//ID	ID	ID	Number	唯一不空	自增
	private long id = -1;
	//证交所代码	sCode	Code	Varchar2(100)	　	证交所代码
	private String Code = "";
	//证交所名称	sName	Name	Varchar2(100)	　	证交所名称
	private String Name = "";
	//状态	nStatusID	StatusID	Number	　	状态：1，有效；-1，已删除
	private long StatusID = -1;
	//录入人	nWriter	InputUserID	Number	　	录入人
	private long InputUserID = -1;
	//录入时间	dtWrite	InputDate	DATE	　	录入时间
	private Timestamp InputDate = null;
	//修改人	nUpdatePerson	UpdateUserID	Number	　	修改人
	private long UpdateUserID = -1;
	//修改时间	dtUpdate	UpdateDate	DATE	　	修改时间
	private Timestamp UpdateDate = null;
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
	 * @return Returns the name.
	 */
	public String getName() {
		return Name;
	}
	/**
	 * @param name The name to set.
	 */
	public void setName(String name) {
		putUsedField("name", name);
		Name = name;
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
}