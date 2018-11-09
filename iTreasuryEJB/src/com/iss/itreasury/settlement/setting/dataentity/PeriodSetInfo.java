package com.iss.itreasury.settlement.setting.dataentity;


/**
 * <p>Title: iTreasury </p> 
 * <p>Description: 资金周期设置</p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: iSoftstone</p>
 * @Author: 余辉
 * @version 1.0
 * @Date: 2006-04-06
 */

import java.sql.Timestamp;

import com.iss.itreasury.util.ITreasuryBaseDataEntity;

public class PeriodSetInfo extends ITreasuryBaseDataEntity
{
	private long id=-1; //主键id
	private long period=-1;//计划周期
	private Timestamp startDate=null;//开始日期
	private long oldPeriod=-1;	//原周期
	private String periodName = "";//周期名
	private Timestamp oldStartDate = null;//原开始日期
	private long currencyId = -1;//币种
	private long officeId = -1;//办事处
	private long inputUserId=-1;//录入人
	private Timestamp inputDate=null;//录入时间
	private long modifyUserId=-1;//修改人
	private Timestamp modifyDate=null;//修改时间
	private long statusId=-1;//状态
	
	
	

	public String getPeriodName() {
		return periodName;
	}
	
	public void setPeriodName(String periodName) {
		this.periodName = periodName;
		putUsedField("periodName", periodName);
	}
	
	public long getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(long currencyId) {
		this.currencyId = currencyId;
		putUsedField("currencyId", currencyId);
	}

	public Timestamp getInputDate() {
		return inputDate;
	}

	public void setInputDate(Timestamp inputDate) {
		this.inputDate = inputDate;
		putUsedField("inputDate", inputDate);
	}

	public long getOfficeId() {
		return officeId;
	}
	
	public void setOfficeId(long officeId) {
		this.officeId = officeId;
		putUsedField("officeId", officeId);
	}

	public Timestamp getOldStartDate() {
		return oldStartDate;
	}
	
	public void setOldStartDate(Timestamp oldStartDate) {
		this.oldStartDate = oldStartDate;
		putUsedField("oldStartDate", oldStartDate);
	}

	public Timestamp getStartDate() {
		return startDate;
	}
	
	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
		putUsedField("startDate", startDate);
	}
	public Timestamp getModifyDate() {
		return modifyDate;
	}
	public void setModifyDate(Timestamp modifyDate) {
		this.modifyDate = modifyDate;
		putUsedField("modifyDate", modifyDate);
	}
	public long getModifyUserId() {
		return modifyUserId;
	}
	public void setModifyUserId(long modifyUserId) {
		this.modifyUserId = modifyUserId;
		putUsedField("modifyUserId", modifyUserId);
	}
	public long getOldPeriod() {
		return oldPeriod;
	}
	public void setOldPeriod(long oldPeriod) {
		this.oldPeriod = oldPeriod;
		putUsedField("oldPeriod", oldPeriod);
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
		putUsedField("id", id);
	}
	public long getInputUserId() {
		return inputUserId;
	}
	public void setInputUserId(long inputUserId) {
		this.inputUserId = inputUserId;
		putUsedField("inputUserId", inputUserId);
	}
	public long getPeriod() {
		return period;
	}
	public void setPeriod(long period) {
		this.period = period;
		putUsedField("period", period);
	}
	public long getStatusId() {
		return statusId;
	}
	public void setStatusId(long statusId) {
		this.statusId = statusId;
		putUsedField("statusId", statusId);
	}
	

}
    