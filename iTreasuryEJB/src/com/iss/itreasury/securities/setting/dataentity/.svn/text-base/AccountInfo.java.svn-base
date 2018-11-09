package com.iss.itreasury.securities.setting.dataentity;

import com.iss.itreasury.securities.util.SECBaseDataEntity;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author yuxu
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class AccountInfo extends SECBaseDataEntity implements Serializable {
	long 		id 						= -1;	//交割单编号Id
	long 		officeId				= -1;	//机构Id
	long 		currencyId 				= -1;	//币种
	long 		type					= -1;	//资金帐户类型
	String 		code					= "";	//帐户编码
	long 		clientId				= -1;	//业务单位Id
	long 		counterpartId			= -1;	//开户银行/营业部
	String		accountCode				= "";	//资金帐号
	String		accountName				= "";	//资金帐户名称
	Timestamp	openDate				= null;//开户日期
	long 		stockHolderAccountId1	= -1;	//对应股东帐户Id1
	long 		stockHolderAccountId2	= -1;	//对应股东帐户Id1
	String		accountSubject			= "";	//对应科目号
	String		remark					= "";	//摘要
	double		initBalance				= 0.0;	//初始化资金余额
	double		initNetBankroll			= 0.0;	//初始化投入资金净额
	double		balance					= 0.0;	//资金余额
	double		suspenseReceive			= 0.0;	//待交割收款挂帐金额
	double		suspensePay				= 0.0;	//待交割付款挂帐金额
	long 		accountStatusId			= -1;	//帐户状态
	long 		statusId				= -1;	//状态
	long 		inputUserId				= -1;	//录入人
	Timestamp	inputDate				= null;//录入时间
	long 		updateUserId			= -1;	//修改人
	Timestamp	updateDate			    = null;//修改时间
	long 		checkUserId				= -1;	//复核人
	Timestamp	checkDate				= null;//复核时间

	
	/**
	 * @see com.iss.itreasury.util.ITreasuryBaseDataEntity#getId()
	 */
	public long getId() {
		return this.id;
	}
	/**
	 * @see com.iss.itreasury.util.ITreasuryBaseDataEntity#setId(long)
	 */
	public void setId(long id) {
		this.id = id;
		putUsedField("id", id);		
	}
	/**
	 * Returns the accountCode.
	 * @return String
	 */
	public String getAccountCode() {
		return accountCode;
	}

	/**
	 * Returns the accountName.
	 * @return String
	 */
	public String getAccountName() {
		return accountName;
	}

	/**
	 * Returns the accountStatusId.
	 * @return long
	 */
	public long getAccountStatusId() {
		return accountStatusId;
	}

	/**
	 * Returns the accountSubject.
	 * @return String
	 */
	public String getAccountSubject() {
		return accountSubject;
	}

	/**
	 * Returns the balance.
	 * @return double
	 */
	public double getBalance() {
		return balance;
	}

	/**
	 * Returns the checkDate.
	 * @return Timestamp
	 */
	public Timestamp getCheckDate() {
		return checkDate;
	}

	/**
	 * Returns the checkUserId.
	 * @return long
	 */
	public long getCheckUserId() {
		return checkUserId;
	}

	/**
	 * Returns the clientId.
	 * @return long
	 */
	public long getClientId() {
		return clientId;
	}

	/**
	 * Returns the code.
	 * @return String
	 */
	public String getCode() {
		return code;
	}

	/**
	 * Returns the counterpartId.
	 * @return long
	 */
	public long getCounterpartId() {
		return counterpartId;
	}

	/**
	 * Returns the currencyId.
	 * @return long
	 */
	public long getCurrencyId() {
		return currencyId;
	}

	/**
	 * Returns the initBalance.
	 * @return double
	 */
	public double getInitBalance() {
		return initBalance;
	}

	/**
	 * Returns the initNetBankroll.
	 * @return double
	 */
	public double getInitNetBankroll() {
		return initNetBankroll;
	}

	/**
	 * Returns the inputDate.
	 * @return Timestamp
	 */
	public Timestamp getInputDate() {
		return inputDate;
	}

	/**
	 * Returns the inputUserId.
	 * @return long
	 */
	public long getInputUserId() {
		return inputUserId;
	}

	/**
	 * Returns the officeId.
	 * @return long
	 */
	public long getOfficeId() {
		return officeId;
	}

	/**
	 * Returns the openDate.
	 * @return Timestamp
	 */
	public Timestamp getOpenDate() {
		return openDate;
	}

	/**
	 * Returns the remark.
	 * @return String
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * Returns the statusId.
	 * @return long
	 */
	public long getStatusId() {
		return statusId;
	}

	/**
	 * Returns the suspensePay.
	 * @return double
	 */
	public double getSuspensePay() {
		return suspensePay;
	}

	/**
	 * Returns the suspenseReceive.
	 * @return double
	 */
	public double getSuspenseReceive() {
		return suspenseReceive;
	}

	/**
	 * Returns the type.
	 * @return long
	 */
	public long getType() {
		return type;
	}

	/**
	 * Sets the accountCode.
	 * @param accountCode The accountCode to set
	 */
	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
		putUsedField("accountCode", accountCode);
	}

	/**
	 * Sets the accountName.
	 * @param accountName The accountName to set
	 */
	public void setAccountName(String accountName) {
		this.accountName = accountName;
		putUsedField("accountName", accountName);
	}

	/**
	 * Sets the accountStatusId.
	 * @param accountStatusId The accountStatusId to set
	 */
	public void setAccountStatusId(long accountStatusId) {
		this.accountStatusId = accountStatusId;
		putUsedField("accountStatusId", accountStatusId);
	}

	/**
	 * Sets the accountSubject.
	 * @param accountSubject The accountSubject to set
	 */
	public void setAccountSubject(String accountSubject) {
		this.accountSubject = accountSubject;
		putUsedField("accountSubject", accountSubject);
	}

	/**
	 * Sets the balance.
	 * @param balance The balance to set
	 */
	public void setBalance(double balance) {
		this.balance = balance;
		putUsedField("balance", balance);
	}

	/**
	 * Sets the checkDate.
	 * @param checkDate The checkDate to set
	 */
	public void setCheckDate(Timestamp checkDate) {
		this.checkDate = checkDate;
		putUsedField("checkDate", checkDate);
	}

	/**
	 * Sets the checkUserId.
	 * @param checkUserId The checkUserId to set
	 */
	public void setCheckUserId(long checkUserId) {
		this.checkUserId = checkUserId;
		putUsedField("checkUserId", checkUserId);
	}

	/**
	 * Sets the clientId.
	 * @param clientId The clientId to set
	 */
	public void setClientId(long clientId) {
		this.clientId = clientId;
		putUsedField("clientId", clientId);
	}

	/**
	 * Sets the code.
	 * @param code The code to set
	 */
	public void setCode(String code) {
		this.code = code;
		putUsedField("code", code);
	}

	/**
	 * Sets the counterpartId.
	 * @param counterpartId The counterpartId to set
	 */
	public void setCounterpartId(long counterpartId) {
		this.counterpartId = counterpartId;
		putUsedField("counterpartId", counterpartId);
	}

	/**
	 * Sets the currencyId.
	 * @param currencyId The currencyId to set
	 */
	public void setCurrencyId(long currencyId) {
		this.currencyId = currencyId;
		putUsedField("currencyId", currencyId);
	}

	/**
	 * Sets the initBalance.
	 * @param initBalance The initBalance to set
	 */
	public void setInitBalance(double initBalance) {
		this.initBalance = initBalance;
		putUsedField("initBalance", initBalance);
	}

	/**
	 * Sets the initNetBankroll.
	 * @param initNetBankroll The initNetBankroll to set
	 */
	public void setInitNetBankroll(double initNetBankroll) {
		this.initNetBankroll = initNetBankroll;
		putUsedField("initNetBankroll", initNetBankroll);
	}

	/**
	 * Sets the inputDate.
	 * @param inputDate The inputDate to set
	 */
	public void setInputDate(Timestamp inputDate) {
		this.inputDate = inputDate;
		putUsedField("inputDate", inputDate);
	}

	/**
	 * Sets the inputUserId.
	 * @param inputUserId The inputUserId to set
	 */
	public void setInputUserId(long inputUserId) {
		this.inputUserId = inputUserId;
		putUsedField("inputUserId", inputUserId);
	}

	/**
	 * Sets the officeId.
	 * @param officeId The officeId to set
	 */
	public void setOfficeId(long officeId) {
		this.officeId = officeId;
		putUsedField("officeId",  officeId);
	}

	/**
	 * Sets the openDate.
	 * @param openDate The openDate to set
	 */
	public void setOpenDate(Timestamp openDate) {
		this.openDate = openDate;
		putUsedField("openDate", openDate);
	}

	/**
	 * Sets the remark.
	 * @param remark The remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
		putUsedField("remark", remark);
	}

	/**
	 * Sets the statusId.
	 * @param statusId The statusId to set
	 */
	public void setStatusId(long statusId) {
		this.statusId = statusId;
		putUsedField("statusId", statusId);
	}


	/**
	 * Sets the suspensePay.
	 * @param suspensePay The suspensePay to set
	 */
	public void setSuspensePay(double suspensePay) {
		this.suspensePay = suspensePay;
		putUsedField("suspensePay", suspensePay);
	}

	/**
	 * Sets the suspenseReceive.
	 * @param suspenseReceive The suspenseReceive to set
	 */
	public void setSuspenseReceive(double suspenseReceive) {
		this.suspenseReceive = suspenseReceive;
		putUsedField("suspenseReceive", suspenseReceive);
	}

	/**
	 * Sets the type.
	 * @param type The type to set
	 */
	public void setType(long type) {
		this.type = type;
		putUsedField("type", type);
	}

	/**
	 * Returns the updateDate.
	 * @return Timestamp
	 */
	public Timestamp getUpdateDate() {
		return updateDate;
	}

	/**
	 * Returns the updateUserId.
	 * @return long
	 */
	public long getUpdateUserId() {
		return updateUserId;
	}

	/**
	 * Sets the updateDate.
	 * @param updateDate The updateDate to set
	 */
	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
		putUsedField("updateDate", updateDate);
	}

	/**
	 * Sets the updateUserId.
	 * @param updateUserId The updateUserId to set
	 */
	public void setUpdateUserId(long updateUserId) {
		this.updateUserId = updateUserId;
		putUsedField("updateUserId", updateUserId);
	}

	/**
	 * Returns the stockHolderAccountId1.
	 * @return long
	 */
	public long getStockHolderAccountId1() {
		return stockHolderAccountId1;
	}

	/**
	 * Returns the stockHolderAccountId2.
	 * @return long
	 */
	public long getStockHolderAccountId2() {
		return stockHolderAccountId2;
	}

	/**
	 * Sets the stockHolderAccountId1.
	 * @param stockHolderAccountId1 The stockHolderAccountId1 to set
	 */
	public void setStockHolderAccountId1(long stockHolderAccountId1) {
		this.stockHolderAccountId1 = stockHolderAccountId1;
		putUsedField("stockHolderAccountId1", stockHolderAccountId1);
	}

	/**
	 * Sets the stockHolderAccountId2.
	 * @param stockHolderAccountId2 The stockHolderAccountId2 to set
	 */
	public void setStockHolderAccountId2(long stockHolderAccountId2) {
		this.stockHolderAccountId2 = stockHolderAccountId2;
		putUsedField("stockHolderAccountId2", stockHolderAccountId2);
	}

}
