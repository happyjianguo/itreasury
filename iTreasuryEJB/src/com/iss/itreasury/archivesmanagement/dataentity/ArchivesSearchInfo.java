package com.iss.itreasury.archivesmanagement.dataentity;

public class ArchivesSearchInfo {
	long lClientIDStart=-1; //�ͻ���
	long larchivesType=-1; //��������
	long strStartContractCode=-1; //��ͬ��ʼ
	long strEndContractCode=-1;//��ͬ����
	long strStartLoanPayCode=-1;//�ſ�֪ͨ����ʼ
	long strEndLoanPayCode=-1; //�ſ�֪ͨ������
	long inputUserID=-1; //�ϴ���
	java.sql.Timestamp inputTime=null;  //�ϴ�ʱ��
	String remark="";  //����
	
	long officeID=-1;
	public long getOfficeID() {
		return officeID;
	}
	public void setOfficeID(long officeID) {
		this.officeID = officeID;
	}
	public java.sql.Timestamp getInputTime() {
		return inputTime;
	}
	public void setInputTime(java.sql.Timestamp inputTime) {
		this.inputTime = inputTime;
	}
	public long getLarchivesType() {
		return larchivesType;
	}
	public void setLarchivesType(long larchivesType) {
		this.larchivesType = larchivesType;
	}
	public long getLClientIDStart() {
		return lClientIDStart;
	}
	public void setLClientIDStart(long clientIDStart) {
		lClientIDStart = clientIDStart;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public long getStrEndContractCode() {
		return strEndContractCode;
	}
	public void setStrEndContractCode(long strEndContractCode) {
		this.strEndContractCode = strEndContractCode;
	}
	public long getStrEndLoanPayCode() {
		return strEndLoanPayCode;
	}
	public void setStrEndLoanPayCode(long strEndLoanPayCode) {
		this.strEndLoanPayCode = strEndLoanPayCode;
	}
	public long getStrStartContractCode() {
		return strStartContractCode;
	}
	public void setStrStartContractCode(long strStartContractCode) {
		this.strStartContractCode = strStartContractCode;
	}
	public long getStrStartLoanPayCode() {
		return strStartLoanPayCode;
	}
	public void setStrStartLoanPayCode(long strStartLoanPayCode) {
		this.strStartLoanPayCode = strStartLoanPayCode;
	}
	public long getInputUserID() {
		return inputUserID;
	}
	public void setInputUserID(long inputUserID) {
		this.inputUserID = inputUserID;
	}
}
