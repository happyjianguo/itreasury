package com.iss.itreasury.loan.assureresmanage.dataentity;

import java.io.Serializable;

public class LOANWarrantApplySearchCKInfo implements Serializable {

	
	private long   warrantName       = -1;
	private String warrantCode       = "";
	private long   feePersonType     = -1;
	private long   feePersonIDFrom   = -1;
    private long   feePersonIDTo     = -1; 
    private double warrantAmountFrom = 0;
    private double warrantAmountTo   = 0;
    private long   inputUserID       = -1;
    private String inputDateFrom     = "";
    private String inputDateTo       = "";
    private long   status            = -1;
	private long   orderParam        = -1;
	private long   desc              = -1;
	private long   officeID = -1;
	private long   currencyID = -1;
	
	private String applyCodeFrom = "";
	private String applyCodeTo = "";
	private long   applyType = -1;
	private long nextCheckUserID = -1;
	
	
	
	public long getNextCheckUserID() {
		return nextCheckUserID;
	}
	public void setNextCheckUserID(long nextCheckUserID) {
		this.nextCheckUserID = nextCheckUserID;
	}
	public String getApplyCodeFrom() {
		return applyCodeFrom;
	}
	public void setApplyCodeFrom(String applyCodeFrom) {
		this.applyCodeFrom = applyCodeFrom;
	}
	public String getApplyCodeTo() {
		return applyCodeTo;
	}
	public void setApplyCodeTo(String applyCodeTo) {
		this.applyCodeTo = applyCodeTo;
	}
	public long getCurrencyID() {
		return currencyID;
	}
	public void setCurrencyID(long currencyID) {
		this.currencyID = currencyID;
	}
	public long getOfficeID() {
		return officeID;
	}
	public void setOfficeID(long officeID) {
		this.officeID = officeID;
	}
	public long getFeePersonIDFrom() {
		return feePersonIDFrom;
	}
	public void setFeePersonIDFrom(long feePersonIDFrom) {
		this.feePersonIDFrom = feePersonIDFrom;
	}
	public long getFeePersonIDTo() {
		return feePersonIDTo;
	}
	public void setFeePersonIDTo(long feePersonIDTo) {
		this.feePersonIDTo = feePersonIDTo;
	}
	public long getFeePersonType() {
		return feePersonType;
	}
	public void setFeePersonType(long feePersonType) {
		this.feePersonType = feePersonType;
	}
	public String getInputDateFrom() {
		return inputDateFrom;
	}
	public void setInputDateFrom(String inputDateFrom) {
		this.inputDateFrom = inputDateFrom;
	}
	public String getInputDateTo() {
		return inputDateTo;
	}
	public void setInputDateTo(String inputDateTo) {
		this.inputDateTo = inputDateTo;
	}
	public long getInputUserID() {
		return inputUserID;
	}
	public void setInputUserID(long inputUserID) {
		this.inputUserID = inputUserID;
	}
	public long getStatus() {
		return status;
	}
	public void setStatus(long status) {
		this.status = status;
	}
	public double getWarrantAmountFrom() {
		return warrantAmountFrom;
	}
	public void setWarrantAmountFrom(double warrantAmountFrom) {
		this.warrantAmountFrom = warrantAmountFrom;
	}
	public double getWarrantAmountTo() {
		return warrantAmountTo;
	}
	public void setWarrantAmountTo(double warrantAmountTo) {
		this.warrantAmountTo = warrantAmountTo;
	}
	public String getWarrantCode() {
		return warrantCode;
	}
	public void setWarrantCode(String warrantCode) {
		this.warrantCode = warrantCode;
	}
	public long getWarrantName() {
		return warrantName;
	}
	public void setWarrantName(long warrantName) {
		this.warrantName = warrantName;
	}
	public long getDesc() {
		return desc;
	}
	public void setDesc(long desc) {
		this.desc = desc;
	}
	public long getOrderParam() {
		return orderParam;
	}
	public void setOrderParam(long orderParam) {
		this.orderParam = orderParam;
	}
	public long getApplyType() {
		return applyType;
	}
	public void setApplyType(long applyType) {
		this.applyType = applyType;
	}
}
