package com.iss.itreasury.craftbrother.setting.dataentity;

import com.iss.itreasury.util.ITreasuryBaseDataEntity;

public class CraSubSubjectSettingInfo extends ITreasuryBaseDataEntity{

	private long id = -1;//主键ID
	
	private long subjectSettID = -1;//交易对手科目设置ID
	
	private long counterPartID = -1;//交易对手ID
	
	private long subTypeID = -1;//交易子类型ID
	
	private String subjectCode = "";//对应科目号
	
	private String payInterestSubjectCode = "";//利息支出科目
	
	private String repayInterestSubjectCode = "";//利息收入科目
	
	private String predrawInterestSubjectCode = "";//计提利息科目
	
	private long statusID = -1;//记录状态1：有效 0：无效
	
	public long getId() {
		// TODO Auto-generated method stub
		return id;
	}

	public void setId(long id) {
		// TODO Auto-generated method stub
		this.id = id;
		putUsedField("id", id);
	}

	public long getSubjectSettID() {
		return subjectSettID;
	}

	public void setSubjectSettID(long subjectSettID) {
		this.subjectSettID = subjectSettID;
		putUsedField("subjectSettID", subjectSettID);
	}

	public long getCounterPartID() {
		return counterPartID;
	}

	public void setCounterPartID(long counterPartID) {
		this.counterPartID = counterPartID;
		putUsedField("counterPartID", counterPartID);
	}

	public long getSubTypeID() {
		return subTypeID;
	}

	public void setSubTypeID(long subTypeID) {
		this.subTypeID = subTypeID;
		putUsedField("subTypeID", subTypeID);
	}

	public String getSubjectCode() {
		return subjectCode;
	}

	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
		putUsedField("subjectCode", subjectCode);
	}

	public String getPayInterestSubjectCode() {
		return payInterestSubjectCode;
	}

	public void setPayInterestSubjectCode(String payInterestSubjectCode) {
		this.payInterestSubjectCode = payInterestSubjectCode;
		putUsedField("payInterestSubjectCode", payInterestSubjectCode);
	}

	public String getRepayInterestSubjectCode() {
		return repayInterestSubjectCode;
	}

	public void setRepayInterestSubjectCode(String repayInterestSubjectCode) {
		this.repayInterestSubjectCode = repayInterestSubjectCode;
		putUsedField("repayInterestSubjectCode", repayInterestSubjectCode);
	}

	public String getPredrawInterestSubjectCode() {
		return predrawInterestSubjectCode;
	}

	public void setPredrawInterestSubjectCode(String predrawInterestSubjectCode) {
		this.predrawInterestSubjectCode = predrawInterestSubjectCode;
		putUsedField("predrawInterestSubjectCode", predrawInterestSubjectCode);
	}

	public long getStatusID() {
		return statusID;
	}

	public void setStatusID(long statusID) {
		this.statusID = statusID;
		putUsedField("statusID", statusID);
	}

	
}
