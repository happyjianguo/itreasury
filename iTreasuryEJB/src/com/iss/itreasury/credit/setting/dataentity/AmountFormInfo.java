package com.iss.itreasury.credit.setting.dataentity;

import java.sql.Timestamp;
import com.iss.itreasury.util.ITreasuryBaseDataEntity;

public class AmountFormInfo extends ITreasuryBaseDataEntity {

	private long id = -1;
	private String creditCode = "";
	private long clientId = -1;
	private long creditModel = -1;
	private long groupCreditId = -1;
	private double creditAmount = 0.0;
	private long controlType = -1;
	private Timestamp startDate = null;
	private Timestamp endDate = null;
	private long officeId = -1;
	private long currencyId = -1;
	private long inputUserId = -1;
	private Timestamp inputDate = null;
	private long state = -1;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
		putUsedField("id", id);
	}
	public String getCreditCode() {
		return creditCode;
	}
	public void setCreditCode(String creditCode) {
		this.creditCode = creditCode;
		putUsedField("creditCode", creditCode);
	}
	public long getClientId() {
		return clientId;
	}
	public void setClientId(long clientId) {
		this.clientId = clientId;
		putUsedField("clientId", clientId);
	}
	public long getCreditModel() {
		return creditModel;
	}
	public void setCreditModel(long creditModel) {
		this.creditModel = creditModel;
		putUsedField("creditModel", creditModel);
	}
	public long getGroupCreditId() {
		return groupCreditId;
	}
	public void setGroupCreditId(long groupCreditId) {
		this.groupCreditId = groupCreditId;
		putUsedField("groupCreditId", groupCreditId);
	}
	public double getCreditAmount() {
		return creditAmount;
	}
	public void setCreditAmount(double creditAmount) {
		this.creditAmount = creditAmount;
		putUsedField("creditAmount", creditAmount);
	}
	public long getControlType() {
		return controlType;
	}
	public void setControlType(long controlType) {
		this.controlType = controlType;
		putUsedField("controlType", controlType);
	}
	public Timestamp getStartDate() {
		return startDate;
	}
	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
		putUsedField("startDate", startDate);
	}
	public Timestamp getEndDate() {
		return endDate;
	}
	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
		putUsedField("endDate", endDate);
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
}
