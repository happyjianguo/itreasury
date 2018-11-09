/*
 * Created on 2004-08-20
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
 *  Date of Creation    2004-08-20
 */
public class PrintSecuritiesBalanceParam extends SECBaseDataEntity implements Serializable {
	//查找日期开始日
	private Timestamp noticeInputDateStart	= null;
	//查找日期结束日
	private Timestamp noticeInputDateEnd	= null;
	//证券菜单id
	private long securitiesMenuId			= -1;
	//证券名称
	private String securitiesId				= "";
	//交易对手id
	private String counterPartId			= "";
	//开发营业部ID
	private String bourseCounterPartId		= "";
	//办事处
	private long officeId 					= -1;
	//币种
	private long currencyId 				= -1;
	//排序方式
	private long ascOrDesc					= -1;
	//排序字段
	private long orderField					= -1;
	//业务状态ID
	private long statusId					= -1;
	//一级科目
	private long firSubject               = -1;
    //交易类型(二级科目)
    private String[] transactionTypeIDs     = null;

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
	 * @return Returns the noticeInputDateEnd.
	 */
	public Timestamp getNoticeInputDateEnd() {
		return noticeInputDateEnd;
	}
	/**
	 * @param noticeInputDateEnd The noticeInputDateEnd to set.
	 */
	public void setNoticeInputDateEnd(Timestamp noticeInputDateEnd) {
		putUsedField("noticeInputDateEnd", noticeInputDateEnd);
		this.noticeInputDateEnd = noticeInputDateEnd;
	}
	/**
	 * @return Returns the noticeInputDateStart.
	 */
	public Timestamp getNoticeInputDateStart() {
		return noticeInputDateStart;
	}
	/**
	 * @param noticeInputDateStart The noticeInputDateStart to set.
	 */
	public void setNoticeInputDateStart(Timestamp noticeInputDateStart) {
		putUsedField("noticeInputDateStart", noticeInputDateStart);
		this.noticeInputDateStart = noticeInputDateStart;
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
	 * @return Returns the securitiesMenuId.
	 */
	public long getSecuritiesMenuId() {
		return securitiesMenuId;
	}
	/**
	 * @param securitiesMenuId The securitiesMenuId to set.
	 */
	public void setSecuritiesMenuId(long securitiesMenuId) {
		putUsedField("securitiesMenuId", securitiesMenuId);
		this.securitiesMenuId = securitiesMenuId;
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
	 * @return Returns the counterPartId.
	 */
	public String getCounterPartId() {
		return counterPartId;
	}
	/**
	 * @param counterPartId The counterPartId to set.
	 */
	public void setCounterPartId(String counterPartId) {
		putUsedField("counterPartId", counterPartId);
		this.counterPartId = counterPartId;
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
	 * @return Returns the transactionTypeIDs.
	 */
	public String[] getTransactionTypeIDs() {
		return transactionTypeIDs;
	}
	/**
	 * @param transactionTypeIDs The transactionTypeIDs to set.
	 */
	public void setTransactionTypeIDs(String[] transactionTypeIDs) {
		putUsedField("transactionTypeIDs", transactionTypeIDs);
		this.transactionTypeIDs = transactionTypeIDs;
	}
	
	
	/**
	 * @return Returns the firSubject.
	 */
	public long getFirSubject() {
		return firSubject;
	}
	/**
	 * @param firSubject The firSubject to set.
	 */
	public void setFirSubject(long firSubject) {
		putUsedField("firSubject",firSubject);
		this.firSubject = firSubject;
	}
}