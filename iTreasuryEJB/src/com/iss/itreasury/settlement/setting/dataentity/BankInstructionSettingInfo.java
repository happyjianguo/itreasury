package com.iss.itreasury.settlement.setting.dataentity;

import java.sql.Timestamp;

import com.iss.itreasury.util.ITreasuryBaseDataEntity;

public class BankInstructionSettingInfo extends ITreasuryBaseDataEntity {
	private long id = -1;// 主键id

	private long nCurrencyId = -1;// 币种

	private long nOfficeId = -1;// 办事处

	private long transType = -1;// 业务类型

	private long accountModule = -1;// 账户模式

	private long isSend = -1;// 是否发送指令

	private long inputUserID = -1;// 录入人ID

	private Timestamp inputDate = null;// 录入时间

	private long statusId = -1;// 状态
	
	private String inputuserName = "";
	
	private long transModule = -1;// 转账方式（内部转账、银行汇款）    2007-7-20
	
	/** 附加字段（查询用）begin */
	
	private long payModule = -1;// 付款方式    2007-7-20
	
	private long accountId = -1;//账户ID
	
	private long bankId = -1;//银行ID
	
	/** 附加字段（查询用）end */

	public long getAccountModule() {
		return accountModule;
	}

	public void setAccountModule(long accountModule) {
		this.accountModule = accountModule;
		putUsedField("accountModule", accountModule);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
		putUsedField("ID", id);
	}

	public Timestamp getInputDate() {
		return inputDate;
	}

	public void setInputDate(Timestamp inputDate) {
		this.inputDate = inputDate;
		putUsedField("inputDate", inputDate);
	}

	public long getInputUserID() {
		return inputUserID;
	}

	public void setInputUserID(long inputUserID) {
		this.inputUserID = inputUserID;
		putUsedField("inputUserID", inputUserID);
	}

	public long getIsSend() {
		return isSend;
	}

	public void setIsSend(long isSend) {
		this.isSend = isSend;
		putUsedField("isSend", isSend);
	}

	public long getNCurrencyId() {
		return nCurrencyId;
	}

	public void setNCurrencyId(long currencyId) {
		nCurrencyId = currencyId;
		putUsedField("nCurrencyId", currencyId);
	}

	public long getNOfficeId() {
		return nOfficeId;
	}

	public void setNOfficeId(long officeId) {
		nOfficeId = officeId;
		putUsedField("nOfficeId", officeId);
	}

	public long getStatusId() {
		return statusId;
	}

	public void setStatusId(long statusId) {
		this.statusId = statusId;
		putUsedField("statusId", statusId);
	}

	public long getTransType() {
		return transType;
	}

	public void setTransType(long transType) {
		this.transType = transType;
		putUsedField("transType", transType);
	}

	public String getInputuserName() {
		return inputuserName;
	}

	public void setInputuserName(String inputuserName) {
		this.inputuserName = inputuserName;
	}

	
	//增加 付款方式  和  转帐模式   2007-7-19
	public long getPayModule() {
		return payModule;
	}

	public void setPayModule(long payModule) {
		this.payModule = payModule;
		putUsedField("payModule", payModule);
	}

	public long getTransModule() {
		return transModule;
	}

	public void setTransModule(long transModule) {
		this.transModule = transModule;
		putUsedField("transModule", transModule);
	}

	public long getAccountId() {
		return accountId;
	}

	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}

	public long getBankId() {
		return bankId;
	}

	public void setBankId(long bankId) {
		this.bankId = bankId;
	}

}   
