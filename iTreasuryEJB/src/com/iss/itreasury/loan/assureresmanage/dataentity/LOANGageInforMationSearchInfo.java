package com.iss.itreasury.loan.assureresmanage.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;
public class LOANGageInforMationSearchInfo implements Serializable {

	private long gagueID = -1;
	
	private long gagueProperty = -1;
	
	private long gagueType = -1;
	
	private String gagueCode = "";
	
	private String gagueName = "";
	
	private long feePersonType = -1;
	
	private long feePersonFrom = -1;
	
	private long feePersonTo = -1;
	
	private double pledgeAmountFrom = 0;
	
	private double pledgeAmountTo   = 0;
	
	private long inputUserID = -1;
	
	private String inputDateFrom = null;
	
	private String inputDateTo = null;
	
	private double appraisValueFrom = 0;

	private double appraisValueTo = 0;
	
	private long gagueStatus = -1;
	
	private long officeID = -1;
	
	private long currencyID = -1;
	
	private  long orderParam = -1;
	
	private  long desc = -1;
	
	private  long creditType = -1;

	public long getCreditType() {
		return creditType;
	}

	public void setCreditType(long creditType) {
		this.creditType = creditType;
	}

	public long getCurrencyID() {
		return currencyID;
	}

	public void setCurrencyID(long currencyID) {
		this.currencyID = currencyID;
	}

	public long getDesc() {
		return desc;
	}

	public void setDesc(long desc) {
		this.desc = desc;
	}

	public long getFeePersonFrom() {
		return feePersonFrom;
	}

	public void setFeePersonFrom(long feePersonFrom) {
		this.feePersonFrom = feePersonFrom;
	}

	public long getFeePersonTo() {
		return feePersonTo;
	}

	public void setFeePersonTo(long feePersonTo) {
		this.feePersonTo = feePersonTo;
	}

	public long getFeePersonType() {
		return feePersonType;
	}

	public void setFeePersonType(long feePersonType) {
		this.feePersonType = feePersonType;
	}

	public String getGagueCode() {
		return gagueCode;
	}

	public void setGagueCode(String gagueCode) {
		this.gagueCode = gagueCode;
	}

	public long getGagueID() {
		return gagueID;
	}

	public void setGagueID(long gagueID) {
		this.gagueID = gagueID;
	}

	public String getGagueName() {
		return gagueName;
	}

	public void setGagueName(String gagueName) {
		this.gagueName = gagueName;
	}

	public long getGagueProperty() {
		return gagueProperty;
	}

	public void setGagueProperty(long gagueProperty) {
		this.gagueProperty = gagueProperty;
	}

	public long getGagueStatus() {
		return gagueStatus;
	}

	public void setGagueStatus(long gagueStatus) {
		this.gagueStatus = gagueStatus;
	}

	public long getGagueType() {
		return gagueType;
	}

	public void setGagueType(long gagueType) {
		this.gagueType = gagueType;
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

	public long getOfficeID() {
		return officeID;
	}

	public void setOfficeID(long officeID) {
		this.officeID = officeID;
	}

	public long getOrderParam() {
		return orderParam;
	}

	public void setOrderParam(long orderParam) {
		this.orderParam = orderParam;
	}

	public double getPledgeAmountFrom() {
		return pledgeAmountFrom;
	}

	public void setPledgeAmountFrom(double pledgeAmountFrom) {
		this.pledgeAmountFrom = pledgeAmountFrom;
	}

	public double getPledgeAmountTo() {
		return pledgeAmountTo;
	}

	public void setPledgeAmountTo(double pledgeAmountTo) {
		this.pledgeAmountTo = pledgeAmountTo;
	}

	public double getAppraisValueFrom() {
		return appraisValueFrom;
	}

	public void setAppraisValueFrom(double appraisValueFrom) {
		this.appraisValueFrom = appraisValueFrom;
	}

	public double getAppraisValueTo() {
		return appraisValueTo;
	}

	public void setAppraisValueTo(double appraisValueTo) {
		this.appraisValueTo = appraisValueTo;
	}
	
	
	
	
	
	
}
