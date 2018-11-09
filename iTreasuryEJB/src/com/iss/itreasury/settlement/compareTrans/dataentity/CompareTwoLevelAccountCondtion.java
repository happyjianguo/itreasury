package com.iss.itreasury.settlement.compareTrans.dataentity;

public class CompareTwoLevelAccountCondtion implements java.io.Serializable{
	private long officeID = -1;
	private long currencyID  = -1;
	private long clientIDStart = -1;
	private long clientIDEnd = -1;
	private long startAccountID = -1;
	private long endAccountID = -1;
	private String clientIDStartCtrl = "";
	private String clientIDEndCtrl = "";
	private String startAccountIDCtrl = "";
	private String endAccountIDCtrl = "";
	private long compareType = -1;
	private String startDate = "";
	private String endDate = "";
	public String getClientIDEndCtrl() {
		return clientIDEndCtrl;
	}
	public void setClientIDEndCtrl(String clientIDEndCtrl) {
		this.clientIDEndCtrl = clientIDEndCtrl;
	}
	public String getClientIDStartCtrl() {
		return clientIDStartCtrl;
	}
	public void setClientIDStartCtrl(String clientIDStartCtrl) {
		this.clientIDStartCtrl = clientIDStartCtrl;
	}
	public long getCompareType() {
		return compareType;
	}
	public void setCompareType(long compareType) {
		this.compareType = compareType;
	}
	public String getEndAccountIDCtrl() {
		return endAccountIDCtrl;
	}
	public void setEndAccountIDCtrl(String endAccountIDCtrl) {
		this.endAccountIDCtrl = endAccountIDCtrl;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getStartAccountIDCtrl() {
		return startAccountIDCtrl;
	}
	public void setStartAccountIDCtrl(String startAccountIDCtrl) {
		this.startAccountIDCtrl = startAccountIDCtrl;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public long getClientIDEnd() {
		return clientIDEnd;
	}
	public void setClientIDEnd(long clientIDEnd) {
		this.clientIDEnd = clientIDEnd;
	}
	public long getClientIDStart() {
		return clientIDStart;
	}
	public void setClientIDStart(long clientIDStart) {
		this.clientIDStart = clientIDStart;
	}
	public long getEndAccountID() {
		return endAccountID;
	}
	public void setEndAccountID(long endAccountID) {
		this.endAccountID = endAccountID;
	}
	public long getStartAccountID() {
		return startAccountID;
	}
	public void setStartAccountID(long startAccountID) {
		this.startAccountID = startAccountID;
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
}
