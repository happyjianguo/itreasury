package com.iss.itreasury.creditrating.set.dataentity;

import java.sql.Timestamp;

import com.iss.itreasury.util.ITreasuryBaseDataEntity;

/**
 * 
 * @author leiyang3
 * 创建于2009-03-05，评级方案视图实体类
 *
 */
public class RatingProjectViewInfo extends ITreasuryBaseDataEntity {

	private long id = -1;
	private String name = "";
	private double summation = 0.0;
	private long standardRatingId = -1;
	private String standardRatingName = "";
	private long targetSetupId = -1;
	private String targetSetupName = "";
	private String targetSetupLevelCode = "";
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
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getSummation() {
		return summation;
	}
	public void setSummation(double summation) {
		this.summation = summation;
	}
	public long getStandardRatingId() {
		return standardRatingId;
	}
	public void setStandardRatingId(long standardRatingId) {
		this.standardRatingId = standardRatingId;
	}
	public String getStandardRatingName() {
		return standardRatingName;
	}
	public void setStandardRatingName(String standardRatingName) {
		this.standardRatingName = standardRatingName;
	}
	public long getTargetSetupId() {
		return targetSetupId;
	}
	public void setTargetSetupId(long targetSetupId) {
		this.targetSetupId = targetSetupId;
	}
	public String getTargetSetupName() {
		return targetSetupName;
	}
	public void setTargetSetupName(String targetSetupName) {
		this.targetSetupName = targetSetupName;
	}
	public long getDecimals() {
		return decimals;
	}
	public void setDecimals(long decimals) {
		this.decimals = decimals;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public long getOfficeId() {
		return officeId;
	}
	public void setOfficeId(long officeId) {
		this.officeId = officeId;
	}
	public long getCurrencyId() {
		return currencyId;
	}
	public void setCurrencyId(long currencyId) {
		this.currencyId = currencyId;
	}
	public long getInputUserId() {
		return inputUserId;
	}
	public void setInputUserId(long inputUserId) {
		this.inputUserId = inputUserId;
	}
	public Timestamp getInputDate() {
		return inputDate;
	}
	public void setInputDate(Timestamp inputDate) {
		this.inputDate = inputDate;
	}
	public long getState() {
		return state;
	}
	public void setState(long state) {
		this.state = state;
	}
	public String getTargetSetupLevelCode() {
		return targetSetupLevelCode;
	}
	public void setTargetSetupLevelCode(String targetSetupLevelCode) {
		this.targetSetupLevelCode = targetSetupLevelCode;
	}
	
}
