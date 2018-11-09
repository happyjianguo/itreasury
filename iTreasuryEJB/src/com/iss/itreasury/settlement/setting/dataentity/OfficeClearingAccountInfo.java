package com.iss.itreasury.settlement.setting.dataentity;


import com.iss.itreasury.util.ITreasuryBaseDataEntity;

public class OfficeClearingAccountInfo extends ITreasuryBaseDataEntity{

	private long id = -1;
	private long officeid=-1;          //����ID
	private long currencyid=-1;        //����ID
	private String subjectcode="";     //�������㱸�����Ŀ
	private long status =-1;           //״̬
	private String officename="";      //��������
	private String officecode="";      //�������
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		putUsedField("id", id);
		this.id = id;
	}
	public long getOfficeid() {
		return officeid;
	}
	public void setOfficeid(long officeid) {
		putUsedField("officeid", officeid);
		this.officeid = officeid;
	}
	public long getCurrencyid() {
		return currencyid;
	}
	public void setCurrencyid(long currencyid) {
		putUsedField("currencyid", currencyid);
		this.currencyid = currencyid;
	}
	public String getSubjectcode() {
		return subjectcode;
	}
	public void setSubjectcode(String subjectcode) {
		putUsedField("subjectcode", subjectcode);
		this.subjectcode = subjectcode;
	}
	public long getStatus() {
		return status;
	}
	public void setStatus(long status) {
		putUsedField("statusid", status);
		this.status = status;
	}
	public String getOfficename() {
		return officename;
	}
	public void setOfficename(String officename) {
		putUsedField("officename", officename);
		this.officename = officename;
	}
	public String getOfficecode() {
		return officecode;
	}
	public void setOfficecode(String officecode) {
		putUsedField("officecode",officecode);
		this.officecode = officecode;
	}
}
