package com.iss.itreasury.ebank.pwconfig.dataentity;

import java.sql.Timestamp;

import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.ITreasuryBaseDataEntity;

public class PasswordInfo extends ITreasuryBaseDataEntity{
	private long id;					//序号
	private long minPassword;			//密码最小长度
	private long maxPassword;			//密码最大长度
	private long compriseNumber;		//是否包含数字
	private long termDate;				//密码过期日期
	private long remindDate;			//密码提醒日期
	private long forcePerfect;			//是否强制修改
	private long currencyId;			//币种
	private long officeId;				//办事处
	private long clientId;				//客户
	private long inputuserId;			//录入人
	private Timestamp modifyDate;		//修改日期
	private Timestamp inputDate;		//录入日期
	private long status;				//状态
	private long compriseTerm;			//是否校验日期
	private long compriseCapital;		//是否包含大写字母
	private long compriseLowercase;	//是否包含小写字母
	private long compriseSpecial;		//是否包含特殊字母
	private long type;					//类型，是网银还是财务



	public void firstInit(){			//初始化密码设置信息，用于无密码设置时
		this.minPassword = Constant.PASSWORD_MIN_LENGTH;
		this.maxPassword = Constant.PASSWORD_MAX_LENGTH;
		this.compriseNumber = 0;
		this.compriseLowercase = 0;
		this.compriseCapital = 0;
		this.compriseTerm = 0;
		this.compriseSpecial = 0;
		this.termDate = 0;
		this.remindDate = 0;
		this.forcePerfect = 0;
		this.currencyId = -1;
		this.officeId = -1;
		this.clientId = -1;
		this.id = -1;
		this.type = 1;
	}
	

	public long getId() {
		// TODO 自动生成方法存根
		return this.id;
	}


	public void setId(long ID) {
		// TODO 自动生成方法存根
		this.id = ID;
		putUsedField("id", ID);
	}	

	public long getMinPassword() {
		return minPassword;
	}

	public void setMinPassword(long minPassword) {
		this.minPassword = minPassword;
		putUsedField("minPassword", minPassword);
	}

	public long getMaxPassword() {
		return maxPassword;
	}

	public void setMaxPassword(long maxPassword) {
		this.maxPassword = maxPassword;
		putUsedField("maxPassword", maxPassword);
	}

	public long getCompriseNumber() {
		return compriseNumber;
	}

	public void setCompriseNumber(long compriseNumber) {
		this.compriseNumber = compriseNumber;
		putUsedField("compriseNumber", compriseNumber);
	}

	public long getTermDate() {
		return termDate;
	}

	public void setTermDate(long termDate) {
		this.termDate = termDate;
		putUsedField("termDate", termDate);
	}

	public long getRemindDate() {
		return remindDate;
	}

	public void setRemindDate(long remindDate) {
		this.remindDate = remindDate;
		putUsedField("remindDate", remindDate);
	}

	public long getForcePerfect() {
		return forcePerfect;
	}

	public void setForcePerfect(long forcePerfect) {
		this.forcePerfect = forcePerfect;
		putUsedField("forcePerfect", forcePerfect);
	}

	public long getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(long currencyId) {
		this.currencyId = currencyId;
		putUsedField("currencyId", currencyId);
	}

	public long getOfficeId() {
		return officeId;
	}

	public void setOfficeId(long officeId) {
		this.officeId = officeId;
		putUsedField("officeId", officeId);
	}

	public long getClientId() {
		return clientId;
	}

	public void setClientId(long clientId) {
		this.clientId = clientId;
		putUsedField("clientId", clientId);
	}

	public long getInputuserId() {
		return inputuserId;
	}

	public void setInputuserId(long inputuserId) {
		this.inputuserId = inputuserId;
		putUsedField("inputuserId", inputuserId);
	}

	public Timestamp getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Timestamp modifyDate) {
		this.modifyDate = modifyDate;
		putUsedField("modifyDate", modifyDate);
	}

	public Timestamp getInputDate() {
		return inputDate;
	}

	public void setInputDate(Timestamp inputDate) {
		this.inputDate = inputDate;
		putUsedField("inputDate", inputDate);
	}

	public long getStatus() {
		return status;
	}

	public void setStatus(long status) {
		this.status = status;
		putUsedField("status", status);
	}
	
	public long getCompriseTerm() {
		return compriseTerm;
	}

	public void setCompriseTerm(long compriseTerm) {
		this.compriseTerm = compriseTerm;
		putUsedField("compriseTerm", compriseTerm);
	}

	public long getCompriseCapital() {
		return compriseCapital;
	}

	public void setCompriseCapital(long compriseCapital) {
		this.compriseCapital = compriseCapital;
		putUsedField("compriseCapital", compriseCapital);
	}

	public long getCompriseLowercase() {
		return compriseLowercase;
	}

	public void setCompriseLowercase(long compriseLowercase) {
		this.compriseLowercase = compriseLowercase;
		putUsedField("compriseLowercase", compriseLowercase);
	}
	
	public long getCompriseSpecial() {
		return compriseSpecial;
	}

	public void setCompriseSpecial(long compriseSpecial) {
		this.compriseSpecial = compriseSpecial;
		putUsedField("compriseSpecial", compriseSpecial);
	}
	
	public long getType() {
		return type;
	}


	public void setType(long type) {
		this.type = type;
		putUsedField("type", type);
	}
}
