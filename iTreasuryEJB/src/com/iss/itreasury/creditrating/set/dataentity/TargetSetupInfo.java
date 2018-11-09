package com.iss.itreasury.creditrating.set.dataentity;

import java.sql.Timestamp;
import com.iss.itreasury.util.ITreasuryBaseDataEntity;

/**
 * 
 * @author leiyang3
 * 创建于2009-03-03，信用评级指标体系实体类
 *
 */
public class TargetSetupInfo extends ITreasuryBaseDataEntity {
	
	private long id = -1;
	private String name = "";
	private String description = "";
	private long paterId = -1;
	private String levelCode = "";
	private long childNum = -1;
	private long officeId = -1;
	private long currencyId = -1;
	private long inputUserId = -1;
	private Timestamp inputDate = null;
	private long state = -1;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		putUsedField("name", name);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
		putUsedField("description", description);
	}

	public long getPaterId() {
		return paterId;
	}

	public void setPaterId(long paterId) {
		this.paterId = paterId;
		putUsedField("paterId", paterId);
	}

	public String getLevelCode() {
		return levelCode;
	}

	public void setLevelCode(String levelCode) {
		this.levelCode = levelCode;
		putUsedField("levelCode", levelCode);
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

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
		putUsedField("id", id);
	}

	public long getChildNum() {
		return childNum;
	}

	public void setChildNum(long childNum) {
		this.childNum = childNum;
		putUsedField("childNum", childNum);
	}

}
