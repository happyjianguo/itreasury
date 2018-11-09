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
 * @author              kewen hu
 * @version
 *  Date of Creation    2004-07-19
 */
public class Trea_OtherIndustryInfo extends TreasuryPlanBaseDataEntity implements Serializable {
	private	long id	= -1;              //	ID
	private	long officeID = -1;         //	����ID
	private	long currencyID = -1;    	//	����ID
	private	String lineNo = null;	//	�д�
	private	String lineName = null;	//	����Ŀ����
	private	long lineLevel = -1;//	����Ŀ����
	private	long parentLineID = -1;	//	�ϼ�����ĿID
	private	long isLeaf	= -1;//	�Ƿ�Ҷ��
	private	String authorizedDepartment	= "";//	��������
	private	String authorizedUser = "";	//	�����û�
	private	long maintenanceFlag = -1;	//	MaintenanceFlag
	private	long statusID = -1;	//	״̬
	private	long inputUserID = -1;	//	¼����
	private	Timestamp inputDate	= null;//	¼��ʱ��
	private	long updateUserID = -1;//	�޸���
	private	Timestamp updateDate = null;//	�޸�ʱ��
	private long IsReadOnly = -1;
    private long IsNeedSum=-1;
	public long getIsNeedSum() {
		return IsNeedSum;
	}
	public void setIsNeedSum(long isNeedSum) {
		putUsedField("IsNeedSum",isNeedSum);
		IsNeedSum = isNeedSum;
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
	 * @return Returns the maintenanceFlag.
	 */
	public long getMaintenanceFlag() {
		return maintenanceFlag;
	}
	/**
	 * @param maintenanceFlag The maintenanceFlag to set.
	 */
	public void setMaintenanceFlag(long maintenanceFlag) {
		putUsedField("maintenanceFlag", maintenanceFlag);
		this.maintenanceFlag = maintenanceFlag;
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
	/**
	 * @return Returns the isReadOnly.
	 */
	public long getIsReadOnly() {
		return IsReadOnly;
	}
	/**
	 * @param isReadOnly The isReadOnly to set.
	 */
	public void setIsReadOnly(long isReadOnly) {
		putUsedField("isReadOnly", isReadOnly);
		IsReadOnly = isReadOnly;
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
