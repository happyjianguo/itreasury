package com.iss.itreasury.settlement.logger.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;

public class QueryOpenCloseLog implements Serializable {
	
	private long officeId = -1;  //办事处ID
	private long currencyId = -1;  //币种ID
	private long openCloseType = -1;  //开关机类型
	private String openCloseStartDate = null;  //操作日期开始
	private String openCloseEndDate = null;  //操作日期结束
	private long openCloseUserId = -1; //操作用户ID
	private String sName = null;  //操作用户名称
	private String ocDate = null;
	private long batchNo = 1; //操作次数
	private String code = null; //日志流水号

	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getOcDate() {
		return ocDate;
	}
	public void setOcDate(String ocDate) {
		this.ocDate = ocDate;
	}
	public String getOpenCloseEndDate() {
		return openCloseEndDate;
	}
	public void setOpenCloseEndDate(String openCloseEndDate) {
		this.openCloseEndDate = openCloseEndDate;
	}
	public String getOpenCloseStartDate() {
		return openCloseStartDate;
	}
	public void setOpenCloseStartDate(String openCloseStartDate) {
		this.openCloseStartDate = openCloseStartDate;
	}
	public long getOpenCloseType() {
		return openCloseType;
	}
	public void setOpenCloseType(long openCloseType) {
		this.openCloseType = openCloseType;
	}
	public long getCurrencyId() {
		return currencyId;
	}
	public void setCurrencyId(long currencyId) {
		this.currencyId = currencyId;
	}
	public long getOfficeId() {
		return officeId;
	}
	public void setOfficeId(long officeId) {
		this.officeId = officeId;
	}
	public long getOpenCloseUserId() {
		return openCloseUserId;
	}
	public void setOpenCloseUserId(long openCloseUserId) {
		this.openCloseUserId = openCloseUserId;
	}
	public String getSName() {
		return sName;
	}
	public void setSName(String name) {
		sName = name;
	}
	public long getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(long batchNo) {
		this.batchNo = batchNo;
	}
}
