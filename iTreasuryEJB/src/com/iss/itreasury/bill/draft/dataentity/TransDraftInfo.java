package com.iss.itreasury.bill.draft.dataentity;

import java.sql.Timestamp;

import com.iss.itreasury.bill.util.BillDataEntity;

public class TransDraftInfo extends BillDataEntity {
	private long id;
	private long transTypeID;
	private String transTypeName;
	private String billCode;
	private long ndrafttypeid;
	private double mAmount;
	private Timestamp dtCreate;
	private Timestamp dtEnd;
	private String transCode;
	private long statusID;
	private long inputUserID;
	private Timestamp inputDate;
	private long officeId;
	private long currencyId;
	private String sAbstract;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		id = id;
	}

	public long getTransTypeID() {
		return transTypeID;
	}

	public void setTransTypeID(long transTypeID) {
		this.transTypeID = transTypeID;
	}

	public String getTransTypeName() {
		return transTypeName;
	}

	public void setTransTypeName(String transTypeName) {
		this.transTypeName = transTypeName;
	}

	public String getBillCode() {
		return billCode;
	}

	public void setBillCode(String billCode) {
		this.billCode = billCode;
	}

	public long getNdrafttypeid() {
		return ndrafttypeid;
	}

	public void setNdrafttypeid(long ndrafttypeid) {
		this.ndrafttypeid = ndrafttypeid;
	}

	public double getMAmount() {
		return mAmount;
	}

	public void setMAmount(double amount) {
		mAmount = amount;
	}

	public Timestamp getDtCreate() {
		return dtCreate;
	}

	public void setDtCreate(Timestamp dtCreate) {
		this.dtCreate = dtCreate;
	}

	public Timestamp getDtEnd() {
		return dtEnd;
	}

	public void setDtEnd(Timestamp dtEnd) {
		this.dtEnd = dtEnd;
	}

	public String getTransCode() {
		return transCode;
	}

	public void setTransCode(String transCode) {
		this.transCode = transCode;
	}

	public long getStatusID() {
		return statusID;
	}

	public void setStatusID(long statusID) {
		this.statusID = statusID;
	}

	public long getInputUserID() {
		return inputUserID;
	}

	public void setInputUserID(long inputUserID) {
		this.inputUserID = inputUserID;
	}

	public Timestamp getInputDate() {
		return inputDate;
	}

	public void setInputDate(Timestamp inputDate) {
		this.inputDate = inputDate;
	}

	public long getOfficeId() {
		return officeId;
	}

	public void setOfficeId(long officeId) {
		this.officeId = officeId;
	}

	public long getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(long currencyId) {
		this.currencyId = currencyId;
	}

	public String getSAbstract() {
		return sAbstract;
	}

	public void setSAbstract(String abstract1) {
		sAbstract = abstract1;
	}


}
