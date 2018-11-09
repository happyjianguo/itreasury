package com.iss.itreasury.creditrating.set.dataentity;

import java.sql.Timestamp;

import com.iss.itreasury.util.ITreasuryBaseDataEntity;

/**
 * 
 * @author leiyang3
 * 创建于2009-03-05，评级方案明细视图
 *
 */
public class RatingProjectSubViewInfo extends ITreasuryBaseDataEntity {

	private long id = -1;
	private long ratingProjectId = -1;
	private long targetSetupId = -1;
	private String targetSetupName = "";
	private String targetSetupDescription = "";
	private long targetSetupPaterId = -1;
	private String targetSetupLevelCode = "";
	private long targetSetupChildNum = -1;
	private long targetSetupRow = -1;
	private long targetSetupColnum = -1;
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
	}

	public long getRatingProjectId() {
		return ratingProjectId;
	}

	public void setRatingProjectId(long ratingProjectId) {
		this.ratingProjectId = ratingProjectId;
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

	public String getTargetSetupDescription() {
		return targetSetupDescription;
	}

	public void setTargetSetupDescription(String targetSetupDescription) {
		this.targetSetupDescription = targetSetupDescription;
	}

	public long getTargetSetupPaterId() {
		return targetSetupPaterId;
	}

	public void setTargetSetupPaterId(long targetSetupPaterId) {
		this.targetSetupPaterId = targetSetupPaterId;
	}

	public String getTargetSetupLevelCode() {
		return targetSetupLevelCode;
	}

	public void setTargetSetupLevelCode(String targetSetupLevelCode) {
		this.targetSetupLevelCode = targetSetupLevelCode;
	}

	public long getTargetSetupColnum() {
		return targetSetupColnum;
	}

	public void setTargetSetupColnum(long targetSetupColnum) {
		this.targetSetupColnum = targetSetupColnum;
	}

	public double getFullMark() {
		return fullMark;
	}

	public void setFullMark(double fullMark) {
		this.fullMark = fullMark;
	}

	public long getRatingType() {
		return ratingType;
	}

	public void setRatingType(long ratingType) {
		this.ratingType = ratingType;
	}

	public String getRatingValue() {
		return ratingValue;
	}

	public void setRatingValue(String ratingValue) {
		this.ratingValue = ratingValue;
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

	public long getTargetSetupRow() {
		return targetSetupRow;
	}

	public void setTargetSetupRow(long targetSetupRow) {
		this.targetSetupRow = targetSetupRow;
	}

	public long getTargetSetupChildNum() {
		return targetSetupChildNum;
	}

	public void setTargetSetupChildNum(long targetSetupChildNum) {
		this.targetSetupChildNum = targetSetupChildNum;
	}

}
