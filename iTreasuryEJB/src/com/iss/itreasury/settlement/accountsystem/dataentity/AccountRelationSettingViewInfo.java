package com.iss.itreasury.settlement.accountsystem.dataentity;

/**
 * 
 * @author leiyang3
 *
 */
public class AccountRelationSettingViewInfo {
	
	//AccountRelationSetting
	private long id = -1;
  	private long nAccountSystemId = -1;
  	private long nAccountId = -1;
  	private long nStatusId = -1;
  	private long nOfficeId = -1;
  	private long nCurrencyId = -1;
  	
  	//AccountSystemSettingInfo部分信息
  	private String systemCode = null;
  	private String systemName = null;
  	private String upAccountCode = null;
  	private String upAccountName = null;
  	
  	//AccountInfo
  	private String accountCode = null;
  	private String accountName = null;
  	
  	//ClientInfo
  	private long nClientId = -1;
  	private String clientCode = null;
  	private String clientName = null;
  	
  	//TransCurrentDeposit
  	private long nTransId = -1;
  	private String transNo = null;
  	private long transactionTypeID = -1;
  	
  	//balance
  	private double mbalance = 0.0;
  	
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getClientCode() {
		return clientCode;
	}
	public void setClientCode(String clientCode) {
		this.clientCode = clientCode;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public double getMbalance() {
		return mbalance;
	}
	public void setMbalance(double mbalance) {
		this.mbalance = mbalance;
	}
	public long getNAccountId() {
		return nAccountId;
	}
	public void setNAccountId(long accountId) {
		nAccountId = accountId;
	}
	public long getNAccountSystemId() {
		return nAccountSystemId;
	}
	public void setNAccountSystemId(long accountSystemId) {
		nAccountSystemId = accountSystemId;
	}
	public long getNClientId() {
		return nClientId;
	}
	public void setNClientId(long clientId) {
		nClientId = clientId;
	}
	public long getNCurrencyId() {
		return nCurrencyId;
	}
	public void setNCurrencyId(long currencyId) {
		nCurrencyId = currencyId;
	}
	public long getNOfficeId() {
		return nOfficeId;
	}
	public void setNOfficeId(long officeId) {
		nOfficeId = officeId;
	}
	public long getNStatusId() {
		return nStatusId;
	}
	public void setNStatusId(long statusId) {
		nStatusId = statusId;
	}
	public long getNTransId() {
		return nTransId;
	}
	public void setNTransId(long transId) {
		nTransId = transId;
	}
	public String getTransNo() {
		return transNo;
	}
	public void setTransNo(String transNo) {
		this.transNo = transNo;
	}
	public String getAccountCode() {
		return accountCode;
	}
	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}
	public String getSystemCode() {
		return systemCode;
	}
	public void setSystemCode(String systemCode) {
		this.systemCode = systemCode;
	}
	public String getSystemName() {
		return systemName;
	}
	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}
	public String getUpAccountCode() {
		return upAccountCode;
	}
	public void setUpAccountCode(String upAccountCode) {
		this.upAccountCode = upAccountCode;
	}
	public String getUpAccountName() {
		return upAccountName;
	}
	public void setUpAccountName(String upAccountName) {
		this.upAccountName = upAccountName;
	}
	public long getTransactionTypeID() {
		return transactionTypeID;
	}
	public void setTransactionTypeID(long transactionTypeID) {
		this.transactionTypeID = transactionTypeID;
	}

}
