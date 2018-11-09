package com.iss.itreasury.project.gzbfcl.settlement.dataentity;

import java.sql.Timestamp;
import java.util.Collection;

import com.iss.itreasury.util.ITreasuryBaseDataEntity;

public class FD_OrganizationInfo extends ITreasuryBaseDataEntity{
	
	private long Id=-1;//��λ ID
	private String OrganizationName="";//��λ����
	private long inputUserID=-1;//¼����ID
	private Timestamp inputTime=null;//¼��ʱ��
	private long officeID=-1;//���´�ID
	private long currentID=-1;//����ID
	private long modifyUserID=-1;//�޸���ID
	private Timestamp modifyTime=null;//�޸�ʱ��
	private long statusID=-1;//״̬
	/**
	 * @return the id
	 */
	public long getId() {
		return Id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		Id = id;
		putUsedField("id", id);
	}
	/**
	 * @return the organizationName
	 */
	public String getOrganizationName() {
		return OrganizationName;
	}
	/**
	 * @param organizationName the organizationName to set
	 */
	public void setOrganizationName(String organizationName) {
		OrganizationName = organizationName;
		putUsedField("organizationName", organizationName);
	}
	/**
	 * @return the inputUserID
	 */
	public long getInputUserID() {
		return inputUserID;
	}
	/**
	 * @param inputUserID the inputUserID to set
	 */
	public void setInputUserID(long inputUserID) {
		this.inputUserID = inputUserID;
		putUsedField("inputUserID", inputUserID);
	}
	/**
	 * @return the inputTime
	 */
	public Timestamp getInputTime() {
		return inputTime;
	}
	/**
	 * @param inputTime the inputTime to set
	 */
	public void setInputTime(Timestamp inputTime) {
		this.inputTime = inputTime;
		putUsedField("inputTime", inputTime);
	}
	/**
	 * @return the officeID
	 */
	public long getOfficeID() {
		return officeID;
	}
	/**
	 * @param officeID the officeID to set
	 */
	public void setOfficeID(long officeID) {
		this.officeID = officeID;
		putUsedField("officeID", officeID);
	}
	/**
	 * @return the currentID
	 */
	public long getCurrentID() {
		return currentID;
	}
	/**
	 * @param currentID the currentID to set
	 */
	public void setCurrentID(long currentID) {
		this.currentID = currentID;
		putUsedField("currentID", currentID);
	}
	/**
	 * @return the modifyUserID
	 */
	public long getModifyUserID() {
		return modifyUserID;
	}
	/**
	 * @param modifyUserID the modifyUserID to set
	 */
	public void setModifyUserID(long modifyUserID) {
		this.modifyUserID = modifyUserID;
		putUsedField("modifyUserID", modifyUserID);
	}
	/**
	 * @return the modifyTime
	 */
	public Timestamp getModifyTime() {
		return modifyTime;
	}
	/**
	 * @param modifyTime the modifyTime to set
	 */
	public void setModifyTime(Timestamp modifyTime) {
		this.modifyTime = modifyTime;
		putUsedField("modifyTime", modifyTime);
	}
	/**
	 * @return the statusID
	 */
	public long getStatusID() {
		return statusID;
	}
	/**
	 * @param statusID the statusID to set
	 */
	public void setStatusID(long statusID) {
		this.statusID = statusID;
		putUsedField("statusID", statusID);
	}
	
	
}