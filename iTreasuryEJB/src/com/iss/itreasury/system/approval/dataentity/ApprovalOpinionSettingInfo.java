package com.iss.itreasury.system.approval.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;

import com.iss.sysframe.base.dataentity.BaseDataEntity;

public class ApprovalOpinionSettingInfo extends BaseDataEntity implements Serializable {
	
	private long id = -1;          			//����
	private long officeID = -1;				//���´�ID
    private long currencyID = -1;			//����ID	
    private long moduleID = -1;				//ģ���ʾ 
    private String code = "";				//���
	private String description = "";		//������� 
	private long statusID = -1;				//״̬	
	private long inputUserID = -1;          //¼����
	private Timestamp inputDate = null;		//¼��ʱ��
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getOfficeID() {
		return officeID;
	}
	public void setOfficeID(long officeID) {
		this.officeID = officeID;
	}
	public long getCurrencyID() {
		return currencyID;
	}
	public void setCurrencyID(long currencyID) {
		this.currencyID = currencyID;
	}
	public long getModuleID() {
		return moduleID;
	}
	public void setModuleID(long moduleID) {
		this.moduleID = moduleID;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public long getStatusID() {
		return statusID;
	}
	public void setStatusID(long statusID) {
		this.statusID = statusID;
	}
	public long getInputUserID() {
		return inputUserID;
	}
	public void setInputUserID(long inputUserID) {
		this.inputUserID = inputUserID;
	}
	public Timestamp getInputDate() {
		return inputDate;
	}
	public void setInputDate(Timestamp inputDate) {
		this.inputDate = inputDate;
	}

}
