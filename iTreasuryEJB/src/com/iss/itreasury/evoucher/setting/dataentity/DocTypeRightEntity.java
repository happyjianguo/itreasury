package com.iss.itreasury.evoucher.setting.dataentity;

import java.util.Date;

import com.iss.itreasury.util.ITreasuryBaseDataEntity;

public class DocTypeRightEntity extends ITreasuryBaseDataEntity {

	private static final long serialVersionUID = 1L;

	private long id = -1;

	private long nofficeid = -1;// 办事处ID

	private long ncurrencyid = -1;// 币种ID

	private long nbilltypeid = -1;// 网银单据类型编号

	private long nissignature = 1;// 是否给单据类型签章授权,0为未授权，1为授权

	private Date dtinputdate;// 单据的录入时间

	private String inputuserid = "";// 单据录入人ID



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
		putUsedField("id", id);
	}

	public String getInputuserid() {
		return inputuserid;
	}

	public void setInputuserid(String inputuserid) {
		this.inputuserid = inputuserid;
		putUsedField("inputuserid", inputuserid);
	}

	public long getNbilltypeid() {
		return nbilltypeid;
	}

	public void setNbilltypeid(long nbilltypeid) {
		this.nbilltypeid = nbilltypeid;
		putUsedField("nbilltypeid", nbilltypeid);
	}

	public long getNcurrencyid() {
		return ncurrencyid;
	}

	public void setNcurrencyid(long ncurrencyid) {
		this.ncurrencyid = ncurrencyid;
		putUsedField("ncurrencyid", ncurrencyid);
	}

	public long getNissignature() {
		return nissignature;
	}

	public void setNissignature(long nissignature) {
		this.nissignature = nissignature;
		putUsedField("nissignature", nissignature);
	}

	public long getNofficeid() {
		return nofficeid;
	}

	public void setNofficeid(long nofficeid) {
		this.nofficeid = nofficeid;
		putUsedField("nofficeid", nofficeid);
	}

}
