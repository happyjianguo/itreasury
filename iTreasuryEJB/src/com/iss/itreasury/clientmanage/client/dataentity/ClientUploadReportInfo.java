


/* Generated by Together */
package com.iss.itreasury.clientmanage.client.dataentity;


import java.sql.Timestamp;

import com.iss.itreasury.clientmanage.dataentity.CimsBaseDataEntity;
import com.iss.itreasury.util.ITreasuryBaseDataEntity;

/**
*财务报表实体类
* */
public class ClientUploadReportInfo extends ITreasuryBaseDataEntity {

	private long id = -1;  //报表id
	
	private String reportName = ""; //报表名称
	private long reportType = -1; //报表类型id
	private Timestamp reportDate = null;//报表日期
	private long clientID = -1; //客户id
	private String sDocPath = ""; //文件路径
	private long nstatusID = -1; //状态
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
		putUsedField("id", id);
	}
	public String getReportName() {
		return reportName;
	}
	public void setReportName(String reportName) {
		this.reportName = reportName;
		putUsedField("reportName", reportName);
	}
	public long getReportType() {
		return reportType;
	}
	public void setReportType(long reportType) {
		this.reportType = reportType;
		putUsedField("reportType", reportType);
	}
	public Timestamp getReportDate() {
		return reportDate;
	}
	public void setReportDate(Timestamp reportDate) {
		this.reportDate = reportDate;
		putUsedField("reportDate", reportDate);
	}
	public long getClientID() {
		return clientID;
	}
	public void setClientID(long clientID) {
		this.clientID = clientID;
		putUsedField("clientID", clientID);
	}
	public String getSDocPath() {
		return sDocPath;
	}
	public void setSDocPath(String sDocPath) {
		this.sDocPath = sDocPath;
		putUsedField("sDocPath", sDocPath);
		
	}
	public long getNstatusID() {
		return nstatusID;
	}
	public void setNstatusID(long nstatusID) {
		this.nstatusID = nstatusID;
		putUsedField("nstatusID", nstatusID);
	}
	
	
	
	
	
	
	
	
}
	
