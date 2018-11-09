/*
 * Created on 2007-6-27
 */
package com.iss.itreasury.settlement.logger.dataentity;

import java.io.Serializable;

/**
 * @author leiyang
 *
 */
public class OpenCloseLogDetailInfo implements Serializable {

	private long id = -1; //ID
	private long openCloseLogId = -1; //��־ID
	private long transTypeId = -1; //��������ID
	private long transId = -1; //����ID
	private String transNo = null; //���ױ��
	private long logType = -1;  //LOG����ID
	private long batchNo = -1;  //��������
	
	public long getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(long batchNo) {
		this.batchNo = batchNo;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getLogType() {
		return logType;
	}
	public void setLogType(long logType) {
		this.logType = logType;
	}
	public String getTransNo() {
		return transNo;
	}
	public void setTransNo(String transNo) {
		this.transNo = transNo;
	}
	public long getTransTypeId() {
		return transTypeId;
	}
	public void setTransTypeId(long transTypeId) {
		this.transTypeId = transTypeId;
	}
	public long getOpenCloseLogId() {
		return openCloseLogId;
	}
	public void setOpenCloseLogId(long openCloseLogId) {
		this.openCloseLogId = openCloseLogId;
	}
	public long getTransId() {
		return transId;
	}
	public void setTransId(long transId) {
		this.transId = transId;
	}
}
