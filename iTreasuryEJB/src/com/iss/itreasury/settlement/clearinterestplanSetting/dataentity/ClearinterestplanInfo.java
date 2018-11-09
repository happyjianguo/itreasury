package com.iss.itreasury.settlement.clearinterestplanSetting.dataentity;

import java.sql.Timestamp;
import java.util.Collection;

import com.iss.itreasury.util.ITreasuryBaseDataEntity;

public class ClearinterestplanInfo extends ITreasuryBaseDataEntity{
	
	
	private long Id=-1;//�ʽ������ ID
	
	private long loanType=-1;	//��������
	private long subLoanType=-1;	//����������
	private String subLoanTypeName="";	//��������������
	private long loanTermTypeID=-1;//����
	private String loanTermTypeName = "";
	private long overDueType=-1;//�ſ����״̬
	private String overDueTypeName = "";
	private long clearType=-1;//��Ϣ��ʽ
	private String clearTypeName="";//��Ϣ��ʽ����
	private long clearTime=-1;//��Ϣʱ��
	
	private long inputUserID=-1;//¼����ID
	private String inputUserName="";//¼��������
	private Timestamp inputTime=null;//¼��ʱ��
	private long officeID=-1;//���´�ID
	private String officeName="";//���´�����
	private long currentID=-1;//����ID
	private long modifyUserID=-1;//�޸���ID
	private String modifyUserName="";//�޸�������
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
	 * @return the loanType
	 */
	public long getLoanType() {
		return loanType;
	}
	/**
	 * @param loanType the loanType to set
	 */
	public void setLoanType(long loanType) {
		this.loanType = loanType;
		putUsedField("loanType", loanType);
	}
	/**
	 * @return the subLoanType
	 */
	public long getSubLoanType() {
		return subLoanType;
	}
	/**
	 * @param subLoanType the subLoanType to set
	 */
	public void setSubLoanType(long subLoanType) {
		this.subLoanType = subLoanType;
		putUsedField("subLoanType", subLoanType);
	}
	/**
	 * @return the clearTypeID
	 */
	public long getClearType() {
		return clearType;
	}
	/**
	 * @param clearTypeID the clearTypeID to set
	 */
	public void setClearType(long clearType) {
		this.clearType = clearType;
		putUsedField("clearType", clearType);
	}

	public long getClearTime() {
		return clearTime;
	}
	public void setClearTime(long clearTime) {
		this.clearTime = clearTime;
		putUsedField("clearTime", clearTime);
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
	/**
	 * @return the subLoanTypeName
	 */
	public String getSubLoanTypeName() {
		return subLoanTypeName;
	}
	/**
	 * @param subLoanTypeName the subLoanTypeName to set
	 */
	public void setSubLoanTypeName(String subLoanTypeName) {
		this.subLoanTypeName = subLoanTypeName;
		
	}
	/**
	 * @return the clearTypeName
	 */
	public String getClearTypeName() {
		return clearTypeName;
	}
	/**
	 * @param clearTypeName the clearTypeName to set
	 */
	public void setClearTypeName(String clearTypeName) {
		this.clearTypeName = clearTypeName;
		
	}
	/**
	 * @return the inputUserName
	 */
	public String getInputUserName() {
		return inputUserName;
	}
	/**
	 * @param inputUserName the inputUserName to set
	 */
	public void setInputUserName(String inputUserName) {
		this.inputUserName = inputUserName;
		
	}
	/**
	 * @return the officeName
	 */
	public String getOfficeName() {
		return officeName;
	}
	/**
	 * @param officeName the officeName to set
	 */
	public void setOfficeName(String officeName) {
		this.officeName = officeName;
		
	}
	/**
	 * @return the modifyUserName
	 */
	public String getModifyUserName() {
		return modifyUserName;
	}
	/**
	 * @param modifyUserName the modifyUserName to set
	 */
	public void setModifyUserName(String modifyUserName) {
		this.modifyUserName = modifyUserName;
		
	}
	public long getLoanTermTypeID() {
		return loanTermTypeID;
	}
	public void setLoanTermTypeID(long loanTermTypeID) {
		this.loanTermTypeID = loanTermTypeID;
		putUsedField("loanTermTypeID", loanTermTypeID);
	}
	public long getOverDueType() {
		return overDueType;
	}
	public void setOverDueType(long overDueType) {
		this.overDueType = overDueType;
		putUsedField("overDueType", overDueType);
	}
	public String getLoanTermTypeName() {
		return loanTermTypeName;
	}
	public void setLoanTermTypeName(String loanTermTypeName) {
		this.loanTermTypeName = loanTermTypeName;
	}
	public String getOverDueTypeName() {
		return overDueTypeName;
	}
	public void setOverDueTypeName(String overDueTypeName) {
		this.overDueTypeName = overDueTypeName;
	}

	
	
	

}
