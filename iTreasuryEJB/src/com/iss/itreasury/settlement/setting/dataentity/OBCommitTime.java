/*
 * Created on 2007-07-23
 */
package com.iss.itreasury.settlement.setting.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;

public class OBCommitTime implements Serializable {
	private long id = -1;  //ID
	private long officeId = -1;  //办事处ID
	private long currencyId = -1;  //币种ID
	private String commitTime = "";  //设置时间
	private long isControl = -1;  //刚柔控制
	private long inputUserId = -1;  //操作用户ID
	private Timestamp inputDate;  //操作用户时间
	private long status = -1;  //状态
	
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
