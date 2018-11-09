/***********************************************************************
 * Module:  HolidayBookInfo.java
 * Author:  qhzhou
 * Purpose: Defines the Class HolidayBookInfo
 ***********************************************************************/
package com.iss.itreasury.system.setting.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 假日表信息类
 * 
 * @author qhzhou
 * @version v1.0
 */
public class SysCalendarInfo implements Serializable {
	/** PK 使用固定字符格式【例如：2010-01-01】 */
	private String dayId;
	/** 节假日转换类型【1:工作日 转 节假日2：节假日 转 工作日】 */
	private long bhTransType;
	/** 转换说明【保留：暂不使用】 */
	private String bhTransDesc;
	/** 记录状态【1：有效 0：无效】 */
	private long statusId;
	/** 录入人 */
	private long inputUserId;
	/** 录入日期 */
	private Timestamp inputDate;


	public String getDayId() {
		return dayId;
	}

	public void setDayId(String dayId) {
		this.dayId = dayId;
	}

	public long getBhTransType() {
		return bhTransType;
	}

	public void setBhTransType(long bhTransType) {
		this.bhTransType = bhTransType;
	}

	public String getBhTransDesc() {
		return bhTransDesc;
	}

	public void setBhTransDesc(String bhTransDesc) {
		this.bhTransDesc = bhTransDesc;
	}

	public long getStatusId() {
		return statusId;
	}

	public void setStatusId(long statusId) {
		this.statusId = statusId;
	}

	public long getInputUserId() {
		return inputUserId;
	}

	public void setInputUserId(long inputUserId) {
		this.inputUserId = inputUserId;
	}

	public Timestamp getInputDate() {
		return inputDate;
	}

	public void setInputDate(Timestamp inputDate) {
		this.inputDate = inputDate;
	}

}
