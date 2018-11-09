package com.iss.itreasury.creditrating.query.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;

public class CreditRatingRecordQueryResultInfo implements Serializable{
	  private long ID = -1;
	  private String ratingCode = "";
	  private long clientID = -1;
	  private String clientNo = "";
	  private String clientName = "";
	  private long  crertType= -1;
	  private long ratingType = -1;
	  private long ratingprojectID = -1;
	  private String ratingprojectName = "";
	  private Timestamp stateDate = null;
	  private Timestamp endDate   = null;
	  private double ratingnumeric = 0.0;
	  private String ratingResult = "";
	  private String remark = "";
	  private Timestamp ratingDate = null;
	  private long officeID  = -1;
	  private long currencyID = -1;
	  private long inputuserID = -1;
	  private Timestamp inputDate   = null;
	  private  long state    = -1;
	public long getClientID() {
		return clientID;
	}
	public void setClientID(long clientID) {
		this.clientID = clientID;
	}
	public long getCurrencyID() {
		return currencyID;
	}
	public void setCurrencyID(long currencyID) {
		this.currencyID = currencyID;
	}
	public Timestamp getEndDate() {
		return endDate;
	}
	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}
	public long getID() {
		return ID;
	}
	public void setID(long id) {
		ID = id;
	}
	public Timestamp getInputDate() {
		return inputDate;
	}
	public void setInputDate(Timestamp inputDate) {
		this.inputDate = inputDate;
	}
	public long getInputuserID() {
		return inputuserID;
	}
	public void setInputuserID(long inputuserID) {
		this.inputuserID = inputuserID;
	}
	public long getOfficeID() {
		return officeID;
	}
	public void setOfficeID(long officeID) {
		this.officeID = officeID;
	}
	public String getRatingCode() {
		return ratingCode;
	}
	public void setRatingCode(String ratingCode) {
		this.ratingCode = ratingCode;
	}
	public Timestamp getRatingDate() {
		return ratingDate;
	}
	public void setRatingDate(Timestamp ratingDate) {
		this.ratingDate = ratingDate;
	}
	public double getRatingnumeric() {
		return ratingnumeric;
	}
	public void setRatingnumeric(double ratingnumeric) {
		this.ratingnumeric = ratingnumeric;
	}
	public long getRatingprojectID() {
		return ratingprojectID;
	}
	public void setRatingprojectID(long ratingprojectID) {
		this.ratingprojectID = ratingprojectID;
	}
	public String getRatingprojectName() {
		return ratingprojectName;
	}
	public void setRatingprojectName(String ratingprojectName) {
		this.ratingprojectName = ratingprojectName;
	}
	public String getRatingResult() {
		return ratingResult;
	}
	public void setRatingResult(String ratingResult) {
		this.ratingResult = ratingResult;
	}
	public long getRatingType() {
		return ratingType;
	}
	public void setRatingType(long ratingType) {
		this.ratingType = ratingType;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public long getState() {
		return state;
	}
	public void setState(long state) {
		this.state = state;
	}
	public Timestamp getStateDate() {
		return stateDate;
	}
	public void setStateDate(Timestamp stateDate) {
		this.stateDate = stateDate;
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
