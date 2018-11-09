package com.iss.itreasury.loan.assureresmanage.dataentity;

import java.io.Serializable;
import  java.sql.Timestamp;

public class LOANWarrantApplySearchResultInfo implements Serializable {

	
	private long      applyID              = -1;
	private String    applyCode            = "";
	private long      applyType            = -1;
	private long      gageID               = -1;
	private String    gageCode             = "";
	private String    gageName             = "";
	private long      gageProperty         = -1;
	private long      gageType             = -1;
	private String    feePersonName        = "";
	private String    slegaLpersonCodeCert = "";
	private double    pledgeAmount         = 0;
	private String    applyUserName        = "";
	private Timestamp applyDate            = null;
	private long      applyStatus          = -1;
	
	
	
	public String getApplyCode() {
		return applyCode;
	}
	public void setApplyCode(String applyCode) {
		this.applyCode = applyCode;
	}
	public Timestamp getApplyDate() {
		return applyDate;
	}
	public void setApplyDate(Timestamp applyDate) {
		this.applyDate = applyDate;
	}
	public long getApplyID() {
		return applyID;
	}
	public void setApplyID(long applyID) {
		this.applyID = applyID;
	}
	public long getApplyStatus() {
		return applyStatus;
	}
	public void setApplyStatus(long applyStatus) {
		this.applyStatus = applyStatus;
	}
	public long getApplyType() {
		return applyType;
	}
	public void setApplyType(long applyType) {
		this.applyType = applyType;
	}
	public String getApplyUserName() {
		return applyUserName;
	}
	public void setApplyUserName(String applyUserName) {
		this.applyUserName = applyUserName;
	}
	public String getFeePersonName() {
		return feePersonName;
	}
	public void setFeePersonName(String feePersonName) {
		this.feePersonName = feePersonName;
	}
	public String getGageCode() {
		return gageCode;
	}
	public void setGageCode(String gageCode) {
		this.gageCode = gageCode;
	}
	public long getGageID() {
		return gageID;
	}
	public void setGageID(long gageID) {
		this.gageID = gageID;
	}
	public String getGageName() {
		return gageName;
	}
	public void setGageName(String gageName) {
		this.gageName = gageName;
	}
	public long getGageProperty() {
		return gageProperty;
	}
	public void setGageProperty(long gageProperty) {
		this.gageProperty = gageProperty;
	}
	public long getGageType() {
		return gageType;
	}
	public void setGageType(long gageType) {
		this.gageType = gageType;
	}
	public double getPledgeAmount() {
		return pledgeAmount;
	}
	public void setPledgeAmount(double pledgeAmount) {
		this.pledgeAmount = pledgeAmount;
	}
	public String getSlegaLpersonCodeCert() {
		return slegaLpersonCodeCert;
	}
	public void setSlegaLpersonCodeCert(String slegaLpersonCodeCert) {
		this.slegaLpersonCodeCert = slegaLpersonCodeCert;
	}
}
