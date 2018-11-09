package com.iss.itreasury.evoucher.setting.dataentity;

import java.util.Date;

import com.iss.itreasury.util.ITreasuryBaseDataEntity;

public class SignatureEntity extends ITreasuryBaseDataEntity{

	private long id = -1;

	private long nofficeid = -1;

	private long nclientid = -1;

	private long nissignature = 1;

	private Date dtinputdate;

	private String inputuserid = "";



	public Date getDtinputdate() {
		return dtinputdate;
	}

	public void setDtinputdate(Date dtinputdate) {
		this.dtinputdate = dtinputdate;
		putUsedField("dtinputdate",dtinputdate);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
		putUsedField("id",id);
	}

	public String getInputuserid() {
		return inputuserid;
	}

	public void setInputuserid(String inputuserid) {
		this.inputuserid = inputuserid;
		putUsedField("inputuserid",inputuserid);
	}

	public long getNclientid() {
		return nclientid;
	}

	public void setNclientid(long nclientid) {
		this.nclientid = nclientid;
		putUsedField("nclientid",nclientid);
	}

	public long getNissignature() {
		return nissignature;
	}

	public void setNissignature(long nissignature) {
		this.nissignature = nissignature;
		putUsedField("nissignature",nissignature);
	}

	public long getNofficeid() {
		return nofficeid;
	}

	public void setNofficeid(long nofficeid) {
		this.nofficeid = nofficeid;
		putUsedField("nofficeid",nofficeid);
	}

}
