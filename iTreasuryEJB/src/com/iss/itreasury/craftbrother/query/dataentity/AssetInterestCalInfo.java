package com.iss.itreasury.craftbrother.query.dataentity;

import java.sql.Timestamp;

import com.iss.itreasury.securities.util.SECBaseDataEntity;

public class AssetInterestCalInfo  extends SECBaseDataEntity{
	private long id = -1;
	private Timestamp startInputDate = null;//查询开始日期
	private Timestamp endInputDate = null;	//查询结束日期
	private long transactionTypeId = -1;	//交易类型
	private long contractIDFrom = -1;		//贷款合同ID开始
	private String contractCodeFrom = "";  //贷款合同编号开始
	private long contractIDTo = -1;			//贷款合同ID结束
	private String contractCodeTo = "";  	//贷款合同编号结束
	private long zContractIDFrom = -1;      //贷款合同ID开始
	private String zContractCodeFrom = ""; 	//转让合同编号开始
	private long zContractIDTo = -1; 		//转让合同ID结束
	private String zContractCodeTo = ""; 	//转让合同编号结束
	private long clientID = -1;				//借款单位ID
	private String clientIDCtrl = ""; 		//借款单位
	private long srBankID = -1;				//受让银行ID
	private String srBankIDCtrl = ""; 		//受让银行
	private long officeId = -1;				//办事处ID
	private long currencyId = -1;			//币种ID

	public long getClientID() {
		return clientID;
	}
	public void setClientID(long clientID) {
		this.clientID = clientID;
		putUsedField("clientID", clientID);
	}
	public String getClientIDCtrl() {
		return clientIDCtrl;
	}
	public void setClientIDCtrl(String clientIDCtrl) {
		this.clientIDCtrl = clientIDCtrl;
		putUsedField("clientIDCtrl", clientIDCtrl);
	}
	public String getContractCodeFrom() {
		return contractCodeFrom;
	}
	public void setContractCodeFrom(String contractCodeFrom) {
		this.contractCodeFrom = contractCodeFrom;
		putUsedField("contractCodeFrom", contractCodeFrom);
	}
	public String getContractCodeTo() {
		return contractCodeTo;
	}
	public void setContractCodeTo(String contractCodeTo) {
		this.contractCodeTo = contractCodeTo;
		putUsedField("contractCodeTo", contractCodeTo);
	}
	public long getContractIDFrom() {
		return contractIDFrom;
	}
	public void setContractIDFrom(long contractIDFrom) {
		this.contractIDFrom = contractIDFrom;
		putUsedField("contractIDFrom", contractIDFrom);
	}
	public long getContractIDTo() {
		return contractIDTo;
	}
	public void setContractIDTo(long contractIDTo) {
		this.contractIDTo = contractIDTo;
		putUsedField("contractIDTo", contractIDTo);
	}
	public Timestamp getEndInputDate() {
		return endInputDate;
	}
	public void setEndInputDate(Timestamp endInputDate) {
		this.endInputDate = endInputDate;
		putUsedField("endInputDate", endInputDate);
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
		putUsedField("id", id);
	}
	public long getSrBankID() {
		return srBankID;
	}
	public void setSrBankID(long srBankID) {
		this.srBankID = srBankID;
		putUsedField("srBankID", srBankID);
	}
	public String getSrBankIDCtrl() {
		return srBankIDCtrl;
	}
	public void setSrBankIDCtrl(String srBankIDCtrl) {
		this.srBankIDCtrl = srBankIDCtrl;
		putUsedField("srBankIDCtrl", srBankIDCtrl);
	}
	public Timestamp getStartInputDate() {
		return startInputDate;
	}
	public void setStartInputDate(Timestamp startInputDate) {
		this.startInputDate = startInputDate;
		putUsedField("startInputDate", startInputDate);
	}
	public long getTransactionTypeId() {
		return transactionTypeId;
	}
	public void setTransactionTypeId(long transactionTypeId) {
		this.transactionTypeId = transactionTypeId;
		putUsedField("transactionTypeId", transactionTypeId);
	}
	public String getZContractCodeFrom() {
		return zContractCodeFrom;
	}
	public void setZContractCodeFrom(String contractCodeFrom) {
		zContractCodeFrom = contractCodeFrom;
		putUsedField("contractCodeFrom", contractCodeFrom);
	}
	public String getZContractCodeTo() {
		return zContractCodeTo;
	}
	public void setZContractCodeTo(String contractCodeTo) {
		zContractCodeTo = contractCodeTo;
		putUsedField("contractCodeTo", contractCodeTo);
	}
	public long getZContractIDFrom() {
		return zContractIDFrom;
	}
	public void setZContractIDFrom(long contractIDFrom) {
		zContractIDFrom = contractIDFrom;
		putUsedField("contractIDFrom", contractIDFrom);
	}
	public long getZContractIDTo() {
		return zContractIDTo;
	}
	public void setZContractIDTo(long contractIDTo) {
		zContractIDTo = contractIDTo;
		putUsedField("contractIDTo", contractIDTo);
	}
	public long getCurrencyId() {
		return currencyId;
	}
	public void setCurrencyId(long currencyId) {
		this.currencyId = currencyId;
	}
	public long getOfficeId() {
		return officeId;
	}
	public void setOfficeId(long officeId) {
		this.officeId = officeId;
	}

}
