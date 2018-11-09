/*
 * Created on 2004-11-17
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.settlement.query.paraminfo;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author sfxiao
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class QCapitalReceiveConditionInfo  implements Serializable
{
	long id              = -1;
	long clientIdFrom    = -1;       //由客户ID
	long clientIdTo      = -1;       //至客户ID
	Timestamp dateFrom   = null;     //由日期
	Timestamp dateTo     = null;     //至日期
	long officeId        = -1;       //办事处Id
	long currencyId      = -1;       //币种Id
	
	public QCapitalReceiveConditionInfo()
	{
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * @return Returns the clientIdFrom.
	 */
	public long getClientIdFrom() {
		return clientIdFrom;
	}
	/**
	 * @param clientIdFrom The clientIdFrom to set.
	 */
	public void setClientIdFrom(long clientIdFrom) {
		this.clientIdFrom = clientIdFrom;
	}
	/**
	 * @return Returns the clientIdTo.
	 */
	public long getClientIdTo() {
		return clientIdTo;
	}
	/**
	 * @param clientIdTo The clientIdTo to set.
	 */
	public void setClientIdTo(long clientIdTo) {
		this.clientIdTo = clientIdTo;
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
		this.currencyId = currencyId;
	}
	/**
	 * @return Returns the dateFrom.
	 */
	public Timestamp getDateFrom() {
		return dateFrom;
	}
	/**
	 * @param dateFrom The dateFrom to set.
	 */
	public void setDateFrom(Timestamp dateFrom) {
		this.dateFrom = dateFrom;
	}
	/**
	 * @return Returns the dateTo.
	 */
	public Timestamp getDateTo() {
		return dateTo;
	}
	/**
	 * @param dateTo The dateTo to set.
	 */
	public void setDateTo(Timestamp dateTo) {
		this.dateTo = dateTo;
	}
	/**
	 * @return Returns the id.
	 */
	public long getId() {
		return id;
	}
	/**
	 * @param id The id to set.
	 */
	public void setId(long id) {
		this.id = id;
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
		this.officeId = officeId;
	}
}
