package com.iss.itreasury.creditrating.set.dataentity;

import java.sql.Timestamp;

import com.iss.itreasury.util.ITreasuryBaseDataEntity;

/**
 * 
 * @author leiyang3
 * 创建于2009-03-05，评级方案明细实体类
 *
 */
public class RatingProjectSubInfo extends ITreasuryBaseDataEntity {

	private long id = -1;
	private long ratingProjectId = -1;
	private long targetSetupId = -1;
	private double fullMark = 0.0;
	private long ratingType = -1;
	private String ratingValue = "";
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

	public long getRatingProjectId() {
		return ratingProjectId;
	}

	public void setRatingProjectId(long ratingProjectId) {
		this.ratingProjectId = ratingProjectId;
		putUsedField("ratingProjectId", ratingProjectId);
	}

	public long getTargetSetupId() {
		return targetSetupId;
	}

	public void setTargetSetupId(long targetSetupId) {
		this.targetSetupId = targetSetupId;
		putUsedField("targetSetupId", targetSetupId);
	}

	public double getFullMark() {
		return fullMark;
	}

	public void setFullMark(double fullMark) {
		this.fullMark = fullMark;
		putUsedField("fullMark", fullMark);
	}

	public long getRatingType() {
		return ratingType;
	}

	public void setRatingType(long ratingType) {
		this.ratingType = ratingType;
		putUsedField("ratingType", ratingType);
	}

	public String getRatingValue() {
		return ratingValue;
	}

	public void setRatingValue(String ratingValue) {
		this.ratingValue = ratingValue;
		putUsedField("ratingValue", ratingValue);
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
