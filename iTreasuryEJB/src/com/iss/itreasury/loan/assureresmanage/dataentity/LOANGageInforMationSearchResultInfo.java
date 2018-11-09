package com.iss.itreasury.loan.assureresmanage.dataentity;

import java.io.Serializable;

public class LOANGageInforMationSearchResultInfo implements Serializable {

	private long   gageID = -1;
	private String gageCode = "";
	private String gageName = "";
	private long   gageProperty = -1;
	private long   gageType = -1;
	private long   freeUnitsID = -1;
	private double pledgeAmount = 0;
	private double surPlusAmount = 0;
	private double appraisValue = 0;
	private String freeUnitsName = "";
	private String slegaLpersonCodeCert= "";
	private String userName = "";
	private java.sql.Timestamp inputDate = null;
	private long  status = -1;
	private long  creditTypeID = -1;
	
	public long getCreditTypeID() {
		return creditTypeID;
	}
	public void setCreditTypeID(long creditTypeID) {
		this.creditTypeID = creditTypeID;
	}
	public long getFreeUnitsID() {
		return freeUnitsID;
	}
	public void setFreeUnitsID(long freeUnitsID) {
		this.freeUnitsID = freeUnitsID;
	}
	public String getFreeUnitsName() {
		return freeUnitsName;
	}
	public void setFreeUnitsName(String freeUnitsName) {
		this.freeUnitsName = freeUnitsName;
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
	public java.sql.Timestamp getInputDate() {
		return inputDate;
	}
	public void setInputDate(java.sql.Timestamp inputDate) {
		this.inputDate = inputDate;
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
	public long getStatus() {
		return status;
	}
	public void setStatus(long status) {
		this.status = status;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getGageCode() {
		return gageCode;
	}
	public void setGageCode(String gageCode) {
		this.gageCode = gageCode;
	}
	public double getSurPlusAmount() {
		return surPlusAmount;
	}
	public void setSurPlusAmount(double surPlusAmount) {
		this.surPlusAmount = surPlusAmount;
	}
	public double getAppraisValue() {
		return appraisValue;
	}
	public void setAppraisValue(double appraisValue) {
		this.appraisValue = appraisValue;
	}
	
}
