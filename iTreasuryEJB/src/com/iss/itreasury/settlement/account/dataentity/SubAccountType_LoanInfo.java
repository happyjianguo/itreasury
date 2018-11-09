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
public class SubAccountType_LoanInfo implements Serializable {

	private long id = -1;
	private long officeID = -1;
	private long currencyID = -1;
	private long serialNo = -1;
	private long accountTypeID = -1;
	private long loanTypeID = -1;
	private long loanMonthID = -1;
	private long loanYearID = -1;
	private long consignerClientID = -1;
	private long draftTypeID = -1;
	private String subjectCode = "";
	private long loanMonthStart = -1;
	private long loanMonthEnd = -1;	
	private String interestSubject = "";
	private String bookedInterestSubject = "";
	//Add By Huang Ye for HN new requirement 02/20/2004
	private String interestTaxSubject = ""; //利息税费科目
	
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
	 * Returns the consignerClientID.
	 * @return long
	 */
	public long getConsignerClientID() {
		return consignerClientID;
	}

	/**
	 * Returns the currencyID.
	 * @return long
	 */
	public long getCurrencyID() {
		return currencyID;
	}

	/**
	 * Returns the draftTypeID.
	 * @return long
	 */
	public long getDraftTypeID() {
		return draftTypeID;
	}

	/**
	 * Returns the id.
	 * @return long
	 */
	public long getId() {
		return id;
	}

	/**
	 * Returns the interestSubject.
	 * @return String
	 */
	public String getInterestSubject() {
		return interestSubject;
	}

	/**
	 * Returns the loanMonthEnd.
	 * @return long
	 */
	public long getLoanMonthEnd() {
		return loanMonthEnd;
	}

	/**
	 * Returns the loanMonthID.
	 * @return long
	 */
	public long getLoanMonthID() {
		return loanMonthID;
	}

	/**
	 * Returns the loanMonthStart.
	 * @return long
	 */
	public long getLoanMonthStart() {
		return loanMonthStart;
	}

	/**
	 * Returns the loanTypeID.
	 * @return long
	 */
	public long getLoanTypeID() {
		return loanTypeID;
	}

	/**
	 * Returns the loanYearID.
	 * @return long
	 */
	public long getLoanYearID() {
		return loanYearID;
	}

	/**
	 * Returns the officeID.
	 * @return long
	 */
	public long getOfficeID() {
		return officeID;
	}

	/**
	 * Returns the serialNo.
	 * @return long
	 */
	public long getSerialNo() {
		return serialNo;
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
	 * Sets the consignerClientID.
	 * @param consignerClientID The consignerClientID to set
	 */
	public void setConsignerClientID(long consignerClientID) {
		this.consignerClientID = consignerClientID;
	}

	/**
	 * Sets the currencyID.
	 * @param currencyID The currencyID to set
	 */
	public void setCurrencyID(long currencyID) {
		this.currencyID = currencyID;
	}

	/**
	 * Sets the draftTypeID.
	 * @param draftTypeID The draftTypeID to set
	 */
	public void setDraftTypeID(long draftTypeID) {
		this.draftTypeID = draftTypeID;
	}

	/**
	 * Sets the id.
	 * @param id The id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Sets the interestSubject.
	 * @param interestSubject The interestSubject to set
	 */
	public void setInterestSubject(String interestSubject) {
		this.interestSubject = interestSubject;
	}

	/**
	 * Sets the loanMonthEnd.
	 * @param loanMonthEnd The loanMonthEnd to set
	 */
	public void setLoanMonthEnd(long loanMonthEnd) {
		this.loanMonthEnd = loanMonthEnd;
	}

	/**
	 * Sets the loanMonthID.
	 * @param loanMonthID The loanMonthID to set
	 */
	public void setLoanMonthID(long loanMonthID) {
		this.loanMonthID = loanMonthID;
	}

	/**
	 * Sets the loanMonthStart.
	 * @param loanMonthStart The loanMonthStart to set
	 */
	public void setLoanMonthStart(long loanMonthStart) {
		this.loanMonthStart = loanMonthStart;
	}

	/**
	 * Sets the loanTypeID.
	 * @param loanTypeID The loanTypeID to set
	 */
	public void setLoanTypeID(long loanTypeID) {
		this.loanTypeID = loanTypeID;
	}

	/**
	 * Sets the loanYearID.
	 * @param loanYearID The loanYearID to set
	 */
	public void setLoanYearID(long loanYearID) {
		this.loanYearID = loanYearID;
	}

	/**
	 * Sets the officeID.
	 * @param officeID The officeID to set
	 */
	public void setOfficeID(long officeID) {
		this.officeID = officeID;
	}

	/**
	 * Sets the serialNo.
	 * @param serialNo The serialNo to set
	 */
	public void setSerialNo(long serialNo) {
		this.serialNo = serialNo;
	}

	/**
	 * Sets the subjectCode.
	 * @param subjectCode The subjectCode to set
	 */
	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}

	/**
	 * Returns the interestTaxSubject.
	 * @return String
	 */
	public String getInterestTaxSubject() {
		return interestTaxSubject;
	}

	/**
	 * Sets the interestTaxSubject.
	 * @param interestTaxSubject The interestTaxSubject to set
	 */
	public void setInterestTaxSubject(String interestTaxSubject) {
		this.interestTaxSubject = interestTaxSubject;
	}

}
