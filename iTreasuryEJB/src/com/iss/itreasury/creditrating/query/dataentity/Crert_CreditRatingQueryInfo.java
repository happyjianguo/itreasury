package com.iss.itreasury.creditrating.query.dataentity;

import java.io.Serializable;

public class Crert_CreditRatingQueryInfo implements Serializable {

	
	private long clientID = -1;
	
	private String queryDate = "";
	
	private long desc = -1;

	private long orderParam = -1;
	
	private long officeID = 1;
	
	private long currencyID = -1;
	
	public long getCurrencyID() {
		return currencyID;
	}

	public void setCurrencyID(long currencyID) {
		this.currencyID = currencyID;
	}

	public long getOfficeID() {
		return officeID;
	}

	public void setOfficeID(long officeID) {
		this.officeID = officeID;
	}

	public long getClientID() {
		return clientID;
	}

	public long getDesc() {
		return desc;
	}

	public void setDesc(long desc) {
		this.desc = desc;
	}


	public long getOrderParam() {
		return orderParam;
	}

	public void setOrderParam(long orderParam) {
		this.orderParam = orderParam;
	}

	public void setClientID(long clientID) {
		this.clientID = clientID;
	}

	public String getQueryDate() {
		return queryDate;
	}

	public void setQueryDate(String queryDate) {
		this.queryDate = queryDate;
	}
}
