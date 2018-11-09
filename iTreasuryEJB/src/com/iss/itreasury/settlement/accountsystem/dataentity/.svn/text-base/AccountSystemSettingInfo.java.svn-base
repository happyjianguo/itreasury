package com.iss.itreasury.settlement.accountsystem.dataentity;

import java.sql.Timestamp;
import com.iss.itreasury.util.ITreasuryBaseDataEntity;

public class AccountSystemSettingInfo extends ITreasuryBaseDataEntity {

	private long id = -1;
  	private String systemCode = null;
  	private String systemName = null;
  	private long nStatusId = -1;
  	private long nUpAccountId = -1;
  	private long nOfficeId = -1;
  	private long nCurrencyId = -1;
  	private long nClientId = -1;
  	private Timestamp dtInputTime = null;
  	private long nInputUserId = -1;
  	private Timestamp dtModifyTime = null; 
  	private long nModifyUserId = -1;
  	
  	//显示使用的字段
  	private String accountCode = null;
  	private String accountName = null;
  	
	public String getAccountCode() {
		return accountCode;
	}
	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public Timestamp getDtInputTime() {
		return dtInputTime;
	}
	public void setDtInputTime(Timestamp dtInputTime) {
		this.dtInputTime = dtInputTime;
		putUsedField("dtInputTime", dtInputTime);
	}
	public Timestamp getDtModifyTime() {
		return dtModifyTime;
	}
	public void setDtModifyTime(Timestamp dtModifyTime) {
		this.dtModifyTime = dtModifyTime;
		putUsedField("dtModifyTime", dtModifyTime);
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
		putUsedField("id", id);
	}
	public long getNCurrencyId() {
		return nCurrencyId;
	}
	public void setNCurrencyId(long currencyId) {
		nCurrencyId = currencyId;
		putUsedField("nCurrencyId", nCurrencyId);
	}
	public long getNInputUserId() {
		return nInputUserId;
	}
	public void setNInputUserId(long inputUserId) {
		nInputUserId = inputUserId;
		putUsedField("nInputUserId", nInputUserId);
	}
	public long getNModifyUserId() {
		return nModifyUserId;
	}
	public void setNModifyUserId(long modifyUserId) {
		nModifyUserId = modifyUserId;
		putUsedField("nModifyUserId", nModifyUserId);
	}
	public long getNOfficeId() {
		return nOfficeId;
	}
	public void setNOfficeId(long officeId) {
		nOfficeId = officeId;
		putUsedField("nOfficeId", nOfficeId);
	}
	public long getNStatusId() {
		return nStatusId;
	}
	public void setNStatusId(long statusId) {
		nStatusId = statusId;
		putUsedField("nStatusId", nStatusId);
	}
	public String getSystemCode() {
		return systemCode;
	}
	public void setSystemCode(String systemCode) {
		this.systemCode = systemCode;
		putUsedField("systemCode", systemCode);
	}
	public String getSystemName() {
		return systemName;
	}
	public void setSystemName(String systemName) {
		this.systemName = systemName;
		putUsedField("systemName", systemName);
	}
	public long getNUpAccountId() {
		return nUpAccountId;
	}
	public void setNUpAccountId(long upAccountId) {
		nUpAccountId = upAccountId;
		putUsedField("nUpAccountId", nUpAccountId);
	}
	public long getNClientId() {
		return nClientId;
	}
	public void setNClientId(long clientId) {
		nClientId = clientId;
		putUsedField("nClientId", nClientId);
	}
}
