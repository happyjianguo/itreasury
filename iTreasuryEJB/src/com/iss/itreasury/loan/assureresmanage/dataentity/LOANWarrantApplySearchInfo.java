package com.iss.itreasury.loan.assureresmanage.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;
public class LOANWarrantApplySearchInfo implements Serializable {
	    
	   private long   gageProperty      = -1;
	   private long   gageType          = -1;
	   private String gageName          = "";
	   private String gageCode          = "";
	   private long   feePersonType     = -1;
	   private long   feePersonIDFrom   = -1;
	   private long   feePersonIDTo     = -1;
	   private double pledgeAmountFrom  = 0;
	   private double pledgeAmountTo    = 0;
	   private String applyCodeFrom     = "";
	   private String applyCodeTo       = "";
	   private long   applyUserID       = -1;
	   private String applyDateFrom     = "";
	   private String applyDateTo       = "";
	   private long   applyStatus       = -1;
	   private long   applyType         = -1;
	   private long   officeID          = -1;
	   private long   currencyID        = -1;
	   private long   nextCheckUserID   = -1;
	   private long   orderParam        = -1;
	   private long   desc              = -1;
	   
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
	public String getApplyDateFrom() {
		return applyDateFrom;
	}
	public void setApplyDateFrom(String applyDateFrom) {
		this.applyDateFrom = applyDateFrom;
	}
	public String getApplyDateTo() {
		return applyDateTo;
	}
	public void setApplyDateTo(String applyDateTo) {
		this.applyDateTo = applyDateTo;
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
	public long getApplyUserID() {
		return applyUserID;
	}
	public void setApplyUserID(long applyUserID) {
		this.applyUserID = applyUserID;
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
	public long getNextCheckUserID() {
		return nextCheckUserID;
	}
	public void setNextCheckUserID(long nextCheckUserID) {
		this.nextCheckUserID = nextCheckUserID;
	}
}
