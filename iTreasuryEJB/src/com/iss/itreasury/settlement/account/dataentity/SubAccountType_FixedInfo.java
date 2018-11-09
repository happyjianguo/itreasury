package com.iss.itreasury.settlement.account.dataentity;

import java.io.Serializable;

/**
 * Title:        		iTreasury
 * Description:         
 * Copyright:           Copyright (c) 2003
 * Company:             iSoftStone
 * @author             yehuang 
 * @version
 *  Date of Creation    2003-10-16
 */
public class SubAccountType_FixedInfo implements Serializable {
	
	private long id = -1;
	private long accountTypeID = -1;
	private long fixDepositMonthID = -1;
	private String subjectCode = "";
	private long statusID = -1;
	private String payInterestSubject = "";
	private String bookedInterestSubject = "";
	private long officeID = -1;
	private long currencyID = -1;
	

	/**
	 * Returns the accountTypeID.
	 * @return long
	 */
	public long getAccountTypeID() {
		return accountTypeID;
	}

	/**
	 * Returns the bookedInterestSubject.
	 * @return String
	 */
	public String getBookedInterestSubject() {
		return bookedInterestSubject;
	}

	/**
	 * Returns the currencyID.
	 * @return long
	 */
	public long getCurrencyID() {
		return currencyID;
	}

	/**
	 * Returns the fixDepositMonthID.
	 * @return long
	 */
	public long getFixDepositMonthID() {
		return fixDepositMonthID;
	}

	/**
	 * Returns the id.
	 * @return long
	 */
	public long getId() {
		return id;
	}

	/**
	 * Returns the officeID.
	 * @return long
	 */
	public long getOfficeID() {
		return officeID;
	}

	/**
	 * Returns the payInterestSubject.
	 * @return String
	 */
	public String getPayInterestSubject() {
		return payInterestSubject;
	}

	/**
	 * Returns the statusID.
	 * @return long
	 */
	public long getStatusID() {
		return statusID;
	}

	/**
	 * Returns the subjectCode.
	 * @return String
	 */
	public String getSubjectCode() {
		return subjectCode;
	}

	/**
	 * Sets the accountTypeID.
	 * @param accountTypeID The accountTypeID to set
	 */
	public void setAccountTypeID(long accountTypeID) {
		this.accountTypeID = accountTypeID;
	}

	/**
	 * Sets the bookedInterestSubject.
	 * @param bookedInterestSubject The bookedInterestSubject to set
	 */
	public void setBookedInterestSubject(String bookedInterestSubject) {
		this.bookedInterestSubject = bookedInterestSubject;
	}

	/**
	 * Sets the currencyID.
	 * @param currencyID The currencyID to set
	 */
	public void setCurrencyID(long currencyID) {
		this.currencyID = currencyID;
	}

	/**
	 * Sets the fixDepositMonthID.
	 * @param fixDepositMonthID The fixDepositMonthID to set
	 */
	public void setFixDepositMonthID(long fixDepositMonthID) {
		this.fixDepositMonthID = fixDepositMonthID;
	}

	/**
	 * Sets the id.
	 * @param id The id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Sets the officeID.
	 * @param officeID The officeID to set
	 */
	public void setOfficeID(long officeID) {
		this.officeID = officeID;
	}

	/**
	 * Sets the payInterestSubject.
	 * @param payInterestSubject The payInterestSubject to set
	 */
	public void setPayInterestSubject(String payInterestSubject) {
		this.payInterestSubject = payInterestSubject;
	}

	/**
	 * Sets the statusID.
	 * @param statusID The statusID to set
	 */
	public void setStatusID(long statusID) {
		this.statusID = statusID;
	}

	/**
	 * Sets the subjectCode.
	 * @param subjectCode The subjectCode to set
	 */
	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}

}
