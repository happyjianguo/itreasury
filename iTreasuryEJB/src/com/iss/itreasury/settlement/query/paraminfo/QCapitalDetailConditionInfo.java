/*
 * Created on 2003-12-18
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.iss.itreasury.settlement.query.paraminfo;
import java.io.Serializable;
import java.sql.Timestamp;
/**
 * @author jinchen
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class QCapitalDetailConditionInfo implements Serializable
{
	private long id                = -1;
	private long officeId          = -1;   //办事处Id
	private long currencyId        = -1;   //币种
	private long clientType        = -1;   //是内部客户还是外部交易对手
	private long clientId          = -1;   //客户Id
	private String counterpartNo   = "";   //外部交易对手
	private Timestamp dateFrom     = null; //查询日期起
	private Timestamp dateTo       = null; //查询日期止 
	
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
		this.clientId = clientId;
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
	/**
	 * @return Returns the clientType.
	 */
	public long getClientType() {
		return clientType;
	}
	/**
	 * @param clientType The clientType to set.
	 */
	public void setClientType(long clientType) {
		this.clientType = clientType;
	}
	/**
	 * @return Returns the counterpartNo.
	 */
	public String getCounterpartNo() {
		return counterpartNo;
	}
	/**
	 * @param counterpartNo The counterpartNo to set.
	 */
	public void setCounterpartNo(String counterpartNo) {
		this.counterpartNo = counterpartNo;
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
}