package com.iss.itreasury.loan.aftercredit.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;

import com.iss.itreasury.util.ITreasuryBaseDataEntity;


public class AfterCreditFileInfo extends ITreasuryBaseDataEntity{
	

	/**
	 * 
	 */
	
	private long reportID;//贷后调查报告ID
	private String fileName;//上传、下载文件的名称
	private String oldfileName;//原上传文件名
	private long upUserID;//上传人的用户ID
	private String upUserName;//上传人的姓名
	private String checkReportCode;//贷后调查报告编号
	private long id =-1;
	private Timestamp upFileDate;//上传时间
	private String ids = "";
	public long getReportID() {
		return reportID;
	}
	public void setReportID(long reportID) {
		this.reportID = reportID;
		putUsedField("reportID", reportID);
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
		putUsedField("fileName", fileName);
	}
	public long getUpUserID() {
		return upUserID;
	}
	public void setUpUserID(long upUserID) {
		this.upUserID = upUserID;
		putUsedField("upUserID", upUserID);
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
		putUsedField("id", id);
	}
	public String getOldfileName() {
		return oldfileName;
	}
	public void setOldfileName(String oldfileName) {
		this.oldfileName = oldfileName;
		putUsedField("oldfileName", oldfileName);
	}
	public Timestamp getUpFileDate() {
		return upFileDate;
	}
	public void setUpFileDate(Timestamp upFileDate) {
		this.upFileDate = upFileDate;
		putUsedField("upFileDate", upFileDate);
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	public String getUpUserName() {
		return upUserName;
	}
	public void setUpUserName(String upUserName) {
		this.upUserName = upUserName;
	}
	public String getCheckReportCode() {
		return checkReportCode;
	}
	public void setCheckReportCode(String checkReportCode) {
		this.checkReportCode = checkReportCode;
	}
	
	
	

}
