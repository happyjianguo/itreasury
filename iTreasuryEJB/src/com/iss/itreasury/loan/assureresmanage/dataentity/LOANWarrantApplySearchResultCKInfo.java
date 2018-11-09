package com.iss.itreasury.loan.assureresmanage.dataentity;

import java.io.Serializable;

public class LOANWarrantApplySearchResultCKInfo implements Serializable {

	private long   gID = -1;
	private String gageCode = "";
	private String gageName = "";
	private long   gageType = -1;
	private long   freeUnitsID = -1;
	private long   feePersonType = -1;
	private long   officeID = -1;
	private long   currencyID = -1;
	private long   warrantID = -1;
	private String warrantName = "";
	private String warrantCode = "";
	private double warrantValue = 0;
	private String csName = "";
	private String eCode = "";
	private long   status = -1;
	private long   inputUserID = -1;
	private String userName = "";
	private java.sql.Timestamp inputDate = null;
	
	private long applyID = -1;
	private long applyType = -1;
	private String applyCode = "";
	private long nextCheckUserID = -1;
	private long loanType = -1;
	private long subLoanType = -1;
	private long nextCheckLevel = -1;
	private long  isLowLevel = -1;
	public long getNextCheckUserID() {
		return nextCheckUserID;
	}
	public void setNextCheckUserID(long nextCheckUserID) {
		this.nextCheckUserID = nextCheckUserID;
	}
	public String getApplyCode() {
		return applyCode;
	}
	public void setApplyCode(String applyCode) {
		this.applyCode = applyCode;
	}
	public long getApplyID() {
		return applyID;
	}
	public void setApplyID(long applyID) {
		this.applyID = applyID;
	}
	public long getApplyType() {
		return applyType;
	}
	public void setApplyType(long applyType) {
		this.applyType = applyType;
	}
	public String getCsName() {
		return csName;
	}
	public void setCsName(String csName) {
		this.csName = csName;
	}
	public long getCurrencyID() {
		return currencyID;
	}
	public void setCurrencyID(long currencyID) {
		this.currencyID = currencyID;
	}
	public String getECode() {
		return eCode;
	}
	public void setECode(String code) {
		eCode = code;
	}
	public long getFeePersonType() {
		return feePersonType;
	}
	public void setFeePersonType(long feePersonType) {
		this.feePersonType = feePersonType;
	}
	public long getFreeUnitsID() {
		return freeUnitsID;
	}
	public void setFreeUnitsID(long freeUnitsID) {
		this.freeUnitsID = freeUnitsID;
	}
	public String getGageCode() {
		return gageCode;
	}
	public void setGageCode(String gageCode) {
		this.gageCode = gageCode;
	}
	public String getGageName() {
		return gageName;
	}
	public void setGageName(String gageName) {
		this.gageName = gageName;
	}
	public long getGageType() {
		return gageType;
	}
	public void setGageType(long gageType) {
		this.gageType = gageType;
	}
	public long getGID() {
		return gID;
	}
	public void setGID(long gid) {
		gID = gid;
	}
	public java.sql.Timestamp getInputDate() {
		return inputDate;
	}
	public void setInputDate(java.sql.Timestamp inputDate) {
		this.inputDate = inputDate;
	}
	public long getInputUserID() {
		return inputUserID;
	}
	public void setInputUserID(long inputUserID) {
		this.inputUserID = inputUserID;
	}
	public long getOfficeID() {
		return officeID;
	}
	public void setOfficeID(long officeID) {
		this.officeID = officeID;
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
	public String getWarrantCode() {
		return warrantCode;
	}
	public void setWarrantCode(String warrantCode) {
		this.warrantCode = warrantCode;
	}
	public long getWarrantID() {
		return warrantID;
	}
	public void setWarrantID(long warrantID) {
		this.warrantID = warrantID;
	}
	public String getWarrantName() {
		return warrantName;
	}
	public void setWarrantName(String warrantName) {
		this.warrantName = warrantName;
	}
	public double getWarrantValue() {
		return warrantValue;
	}
	public void setWarrantValue(double warrantValue) {
		this.warrantValue = warrantValue;
	}
	public long getLoanType() {
		return loanType;
	}
	public void setLoanType(long loanType) {
		this.loanType = loanType;
	}
	public long getNextCheckLevel() {
		return nextCheckLevel;
	}
	public void setNextCheckLevel(long nextCheckLevel) {
		this.nextCheckLevel = nextCheckLevel;
	}
	public long getSubLoanType() {
		return subLoanType;
	}
	public void setSubLoanType(long subLoanType) {
		this.subLoanType = subLoanType;
	}
	public long getIsLowLevel() {
		return isLowLevel;
	}
	public void setIsLowLevel(long isLowLevel) {
		this.isLowLevel = isLowLevel;
	}
	
	
}
