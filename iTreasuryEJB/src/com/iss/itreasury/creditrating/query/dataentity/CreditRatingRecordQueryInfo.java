package com.iss.itreasury.creditrating.query.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;


public class CreditRatingRecordQueryInfo implements Serializable{

	private long officeID = -1;
	private long currencyID = -1;
	private long clientID = -1;
	private String clientNo = "";
	private String clientName = "";	
	private long ratingProjectID = -1;
	private String ratingprojectName ="";
	private long state = -1;
	private long crertType = -1;
	private long ratingType = -1;
	private String queryDate = "";
	public long getClientID() {
		return clientID;
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
	public String getRatingprojectName() {
		return ratingprojectName;
	}
	public void setRatingprojectName(String ratingprojectName) {
		this.ratingprojectName = ratingprojectName;
	}
	public long getRatingType() {
		return ratingType;
	}
	public void setRatingType(long ratingType) {
		this.ratingType = ratingType;
	}
	public long getState() {
		return state;
	}
	public void setState(long state) {
		this.state = state;
	}
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
	public long getRatingProjectID() {
		return ratingProjectID;
	}
	public void setRatingProjectID(long ratingProjectID) {
		this.ratingProjectID = ratingProjectID;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public String getClientNo() {
		return clientNo;
	}
	public void setClientNo(String clientNo) {
		this.clientNo = clientNo;
	}
	public long getCrertType() {
		return crertType;
	}
	public void setCrertType(long crertType) {
		this.crertType = crertType;
	}

}
