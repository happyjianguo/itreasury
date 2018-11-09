/*
 * Created on 2004-09-07
 *
 * To change the template for this generated type comment go to 
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.securities.print.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;

import com.iss.itreasury.securities.util.SECBaseDataEntity;

/**
 * Title:        		iTreasury
 * Description:         
 * Copyright:           Copyright (c) 2004
 * Company:             iSoftStone
 * @author              kewen hu
 * @version
 *  Date of Creation    2004-09-07
 */
public class PrintSecuritiesInvestParam extends SECBaseDataEntity implements Serializable {
	//查找日期
	private Timestamp inputDate				= null;
	//业务单位名称
	private long clientId					= -1;
	//证券类别
	private long transactionTypeId			= -1;
	//资金帐号
	private String accountId				= "";
	//证券名称
	private String securitiesId				= "";
	//交易对手名称ID
	private String bankCounterPartId		= "";
	//开户营业部名称ID
	private String bourseCounterPartId		= "";
	//基金管理公司名称ID
	private String fundCounterPartId		= "";
	//办事处
	private long officeId					= -1;
	//币种
	private long currencyId					= -1;
	//排序方式
	private long ascOrDesc					= -1;
	//排序字段
	private long orderField					= -1;
	//业务状态ID
	private long statusId					= -1;

	/**
	 * @return Returns the accountId.
	 */
	public String getAccountId() {
		return accountId;
	}
	/**
	 * @param accountId The accountId to set.
	 */
	public void setAccountId(String accountId) {
		putUsedField("accountId", accountId);
		this.accountId = accountId;
	}
	/**
	 * @return Returns the ascOrDesc.
	 */
	public long getAscOrDesc() {
		return ascOrDesc;
	}
	/**
	 * @param ascOrDesc The ascOrDesc to set.
	 */
	public void setAscOrDesc(long ascOrDesc) {
		putUsedField("ascOrDesc", ascOrDesc);
		this.ascOrDesc = ascOrDesc;
	}
	/**
	 * @return Returns the bankCounterPartId.
	 */
	public String getBankCounterPartId() {
		return bankCounterPartId;
	}
	/**
	 * @param bankCounterPartId The bankCounterPartId to set.
	 */
	public void setBankCounterPartId(String bankCounterPartId) {
		putUsedField("bankCounterPartId", bankCounterPartId);
		this.bankCounterPartId = bankCounterPartId;
	}
	/**
	 * @return Returns the bourseCounterPartId.
	 */
	public String getBourseCounterPartId() {
		return bourseCounterPartId;
	}
	/**
	 * @param bourseCounterPartId The bourseCounterPartId to set.
	 */
	public void setBourseCounterPartId(String bourseCounterPartId) {
		putUsedField("bourseCounterPartId", bourseCounterPartId);
		this.bourseCounterPartId = bourseCounterPartId;
	}
	/**
	 * @return Returns the currencyId.
	 */
	public long getCurrencyId() {
		return currencyId;
	}
	/**
	 * @param currencyId The currencyId to set.
	 */
	public void setCurrencyId(long currencyId) {
		putUsedField("currencyId", currencyId);
		this.currencyId = currencyId;
	}
	/**
	 * @return Returns the fundCounterPartId.
	 */
	public String getFundCounterPartId() {
		return fundCounterPartId;
	}
	/**
	 * @param fundCounterPartId The fundCounterPartId to set.
	 */
	public void setFundCounterPartId(String fundCounterPartId) {
		putUsedField("fundCounterPartId", fundCounterPartId);
		this.fundCounterPartId = fundCounterPartId;
	}
	/**
	 * @return Returns the inputDate.
	 */
	public Timestamp getInputDate() {
		return inputDate;
	}
	/**
	 * @param inputDate The inputDate to set.
	 */
	public void setInputDate(Timestamp inputDate) {
		putUsedField("inputDate", inputDate);
		this.inputDate = inputDate;
	}
	/**
	 * @return Returns the officeId.
	 */
	public long getOfficeId() {
		return officeId;
	}
	/**
	 * @param officeId The officeId to set.
	 */
	public void setOfficeId(long officeId) {
		putUsedField("officeId", officeId);
		this.officeId = officeId;
	}
	/**
	 * @return Returns the orderField.
	 */
	public long getOrderField() {
		return orderField;
	}
	/**
	 * @param orderField The orderField to set.
	 */
	public void setOrderField(long orderField) {
		putUsedField("orderField", orderField);
		this.orderField = orderField;
	}
	/**
	 * @return Returns the securitiesId.
	 */
	public String getSecuritiesId() {
		return securitiesId;
	}
	/**
	 * @param securitiesId The securitiesId to set.
	 */
	public void setSecuritiesId(String securitiesId) {
		putUsedField("securitiesId", securitiesId);
		this.securitiesId = securitiesId;
	}
	/**
	 * @return Returns the statusId.
	 */
	public long getStatusId() {
		return statusId;
	}
	/**
	 * @param statusId The statusId to set.
	 */
	public void setStatusId(long statusId) {
		putUsedField("statusId", statusId);
		this.statusId = statusId;
	}
	/**
	 * (non-Javadoc)
	 * @see com.iss.itreasury.util.ITreasuryBaseDataEntity#getId()
	 */
	public long getId() {
		return 0;
	}
	/**
	 * (non-Javadoc)
	 * @see com.iss.itreasury.util.ITreasuryBaseDataEntity#setId(long)
	 */
	public void setId(long id) {
	}
	/**
	 * @return Returns the clientId.
	 */
	public long getClientId() {
		return clientId;
	}
	/**
	 * @param clientId The clientId to set.
	 */
	public void setClientId(long clientId) {
		putUsedField("clientId", clientId);
		this.clientId = clientId;
	}
	/**
	 * @return Returns the transactionTypeId.
	 */
	public long getTransactionTypeId() {
		return transactionTypeId;
	}
	/**
	 * @param transactionTypeId The transactionTypeId to set.
	 */
	public void setTransactionTypeId(long transactionTypeId) {
		putUsedField("transactionTypeId", transactionTypeId);
		this.transactionTypeId = transactionTypeId;
	}
}