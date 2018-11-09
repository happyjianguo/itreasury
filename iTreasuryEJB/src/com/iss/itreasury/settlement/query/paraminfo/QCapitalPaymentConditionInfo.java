package com.iss.itreasury.settlement.query.paraminfo;

import java.io.Serializable;
import java.sql.Timestamp;

import com.iss.itreasury.util.ITreasuryBaseDataEntity;


public class QCapitalPaymentConditionInfo extends ITreasuryBaseDataEntity implements Serializable
{
	long id              = -1;
	long clientIdFrom    = -1;       //由客户ID
	long clientIdTo      = -1;       //至客户ID
	Timestamp dateFrom   = null;     //由日期
	Timestamp dateTo     = null;     //至日期
	long officeId        = -1;       //办事处Id
	long currencyId      = -1;       //币种Id
	
	public long getId()
	{
		// TODO Auto-generated method stub
		return 0;
	}
	/* (non-Javadoc)
	 * @see com.iss.itreasury.util.ITreasuryBaseDataEntity#setId(long)
	 */
	public void setId(long id)
	{
		// TODO Auto-generated method stub
		
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