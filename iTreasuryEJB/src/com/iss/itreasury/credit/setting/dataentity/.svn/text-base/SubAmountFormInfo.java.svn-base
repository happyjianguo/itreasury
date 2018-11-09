package com.iss.itreasury.credit.setting.dataentity;

import java.sql.Timestamp;

import com.iss.itreasury.util.ITreasuryBaseDataEntity;

public class SubAmountFormInfo extends ITreasuryBaseDataEntity {

	private long id = -1;
	private long amountFormId = -1;
	private long creditType = -1;
	private long creditShare = -1; //是否对品种授信
	private double creditAmount = 0.0;
	private double excessPercent = 0.0;
	private long officeId = -1;
	private long currencyId = -1;
	private long inputUserId = -1;
	private Timestamp inputDate = null;
	private long state = -1;
	
	private double usedAmount = 0.0;//已用金额

	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
		putUsedField("id", id);
	}
	public long getAmountFormId() {
		return amountFormId;
	}
	public void setAmountFormId(long amountFormId) {
		this.amountFormId = amountFormId;
		putUsedField("amountFormId", amountFormId);
	}
	public long getCreditType() {
		return creditType;
	}
	public void setCreditType(long creditType) {
		this.creditType = creditType;
		putUsedField("creditType", creditType);
	}
	public long getCreditShare() {
		return creditShare;
	}
	public void setCreditShare(long creditShare) {
		this.creditShare = creditShare;
		putUsedField("creditShare", creditShare);
	}
	public double getCreditAmount() {
		return creditAmount;
	}
	public void setCreditAmount(double creditAmount) {
		this.creditAmount = creditAmount;
		putUsedField("creditAmount", creditAmount);
	}
	public double getExcessPercent() {
		return excessPercent;
	}
	public void setExcessPercent(double excessPercent) {
		this.excessPercent = excessPercent;
		putUsedField("excessPercent", excessPercent);
	}
	public long getOfficeId() {
		return officeId;
	}
	public void setOfficeId(long officeId) {
		this.officeId = officeId;
		putUsedField("officeId", officeId);
	}
	public long getCurrencyId() {
		return currencyId;
	}
	public void setCurrencyId(long currencyId) {
		this.currencyId = currencyId;
		putUsedField("currencyId", currencyId);
	}
	public long getInputUserId() {
		return inputUserId;
	}
	public void setInputUserId(long inputUserId) {
		this.inputUserId = inputUserId;
		putUsedField("inputUserId", inputUserId);
	}
	public Timestamp getInputDate() {
		return inputDate;
	}
	public void setInputDate(Timestamp inputDate) {
		this.inputDate = inputDate;
		putUsedField("inputDate", inputDate);
	}
	public long getState() {
		return state;
	}
	public void setState(long state) {
		this.state = state;
		putUsedField("state", state);
	}
	public double getUsedAmount() {
		return usedAmount;
	}
	public void setUsedAmount(double usedAmount) {
		this.usedAmount = usedAmount;
	}
}
