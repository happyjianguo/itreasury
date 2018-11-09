package com.iss.itreasury.creditrating.set.dataentity;

import java.sql.Timestamp;

import com.iss.itreasury.util.ITreasuryBaseDataEntity;

/**
 * 
 * @author leiyang3
 * 创建于2009-03-05，评级方案实体类
 *
 */
public class RatingProjectInfo extends ITreasuryBaseDataEntity {

	private long id = -1;
	private String name = "";
	private double summation = 0.0;
	private long standardRatingId = -1;
	private long targetSetupId = -1;
	private long decimals = -1;
	private String remark = "";
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		putUsedField("name", name);
	}

	public double getSummation() {
		return summation;
	}

	public void setSummation(double summation) {
		this.summation = summation;
		putUsedField("summation", summation);
	}

	public long getStandardRatingId() {
		return standardRatingId;
	}

	public void setStandardRatingId(long standardRatingId) {
		this.standardRatingId = standardRatingId;
		putUsedField("standardRatingId", standardRatingId);
	}

	public long getTargetSetupId() {
		return targetSetupId;
	}

	public void setTargetSetupId(long targetSetupId) {
		this.targetSetupId = targetSetupId;
		putUsedField("targetSetupId", targetSetupId);
	}

	public long getDecimals() {
		return decimals;
	}

	public void setDecimals(long decimals) {
		this.decimals = decimals;
		putUsedField("decimals", decimals);
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
		putUsedField("remark", remark);
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
