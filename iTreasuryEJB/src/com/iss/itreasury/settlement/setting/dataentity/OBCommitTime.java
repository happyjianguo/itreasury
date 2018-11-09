/*
 * Created on 2007-07-23
 */
package com.iss.itreasury.settlement.setting.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;

public class OBCommitTime implements Serializable {
	private long id = -1;  //ID
	private long officeId = -1;  //���´�ID
	private long currencyId = -1;  //����ID
	private String commitTime = "";  //����ʱ��
	private long isControl = -1;  //�������
	private long inputUserId = -1;  //�����û�ID
	private Timestamp inputDate;  //�����û�ʱ��
	private long status = -1;  //״̬
	
	public String getCommitTime() {
		return commitTime;
	}
	public void setCommitTime(String commitTime) {
		this.commitTime = commitTime;
	}
	public long getCurrencyId() {
		return currencyId;
	}
	public void setCurrencyId(long currencyId) {
		this.currencyId = currencyId;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Timestamp getInputDate() {
		return inputDate;
	}
	public void setInputDate(Timestamp inputDate) {
		this.inputDate = inputDate;
	}
	public long getInputUserId() {
		return inputUserId;
	}
	public void setInputUserId(long inputUserId) {
		this.inputUserId = inputUserId;
	}
	public long getIsControl() {
		return isControl;
	}
	public void setIsControl(long isControl) {
		this.isControl = isControl;
	}
	public long getOfficeId() {
		return officeId;
	}
	public void setOfficeId(long officeId) {
		this.officeId = officeId;
	}
	public long getStatus() {
		return status;
	}
	public void setStatus(long status) {
		this.status = status;
	}
}
