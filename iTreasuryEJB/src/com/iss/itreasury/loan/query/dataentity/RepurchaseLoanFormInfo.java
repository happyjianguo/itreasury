/*
 * Created on 2005-12-28
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.loan.query.dataentity;

import java.sql.Timestamp;


/**
 * @author liwang
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

public class RepurchaseLoanFormInfo {
	

	private long repurchaseId=-1;
	private long nBorrowClientId=-1;
	private long loanId=-1;
    private long id=-1;
    private String code="";
    private double amount=0.0;
    private double rate=0.0;
    private Timestamp startDate=null;
    private Timestamp endDate=null;
    private long bankId=-1;
    private String remark="";
    private long inputUserId=-1;
    private Timestamp inputDate=null;
    private long statusId=-1;
    private long nstatusId=-1;
    private long officeId=-1;
    private long currencyId=-1;
    private String sContractCode="";
    private String sName="";
    private double mLoanAmount=0.0;
    private Timestamp dtStartDate=null;
    private Timestamp dtEndDate=null;
    
    /**
     * @return Returns the nBorrowClientId.
     */
    public long getNBorrowClientId()
    {
    	return nBorrowClientId;
    }
    /**
     * @param nBorrowClientId The nBorrowClientId to set.
     */
    public void setNBorrowClientId(long nBorrowClientId)
    {
    	this.nBorrowClientId=nBorrowClientId;
    }
    /**
     * @return Returns the nstatusId.
     */
    public long getNstatusId()
    {
    return 	nstatusId;
    }
    
    /**
     * @param nstatusId The nstatusId to set.
     */
    public void setNstatusId(long nstatusId)
    {
    this.nstatusId=nstatusId;	
    }
    /**
     * @return Returns the amount.
     */
    public double getAmount() {
        return amount;
    }
    /**
     * @param amount The amount to set.
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }
    /**
     * @return Returns the bankId.
     */
    public long getBankId() {
        return bankId;
    }
    /**
     * @param bankId The bankId to set.
     */
    public void setBankId(long bankId) {
        this.bankId = bankId;
    }
    /**
     * @return Returns the code.
     */
    public String getCode() {
        return code;
    }
    /**
     * @param code The code to set.
     */
    public void setCode(String code) {
        this.code = code;
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
     * @return Returns the endDate.
     */
    public Timestamp getEndDate() {
        return endDate;
    }
    /**
     * @param endDate The endDate to set.
     */
    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
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
     * @return Returns the RepurchaseId.
     */
    public long getRepurchaseId()
    {
    	return repurchaseId;
    }
    /**
     * @param RepurchaseId The RepurchaseId to set.
     */
    public void setRepurchaseId(long repurchaseId)
    {
     this.repurchaseId=repurchaseId;
    }
    /**
     * @return Returns the LoanId.
     */
    public long getLoanId()
    {
    	return loanId;
    }
    /**
     * @param LoanId The LoanId to set.
     */
    public void setLoanId( long loanId)
    {this.loanId=loanId;}
    /**
     * @return Returns the inputDate.
     */
    public Timestamp getInputDate() {
        return inputDate;
    }
    /**
     * @param inputDate The inputDate to set.
     */
    public void setInputDate(Timestamp inputDate) {
        this.inputDate = inputDate;
    }
    /**
     * @return Returns the inputUserId.
     */
    public long getInputUserId() {
        return inputUserId;
    }
    /**
     * @param inputUserId The inputUserId to set.
     */
    public void setInputUserId(long inputUserId) {
        this.inputUserId = inputUserId;
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
     * @return Returns the rate.
     */
    public double getRate() {
        return rate;
    }
    /**
     * @param rate The rate to set.
     */
    public void setRate(double rate) {
        this.rate = rate;
    }
    /**
     * @return Returns the remark.
     */
    public String getRemark() {
        return remark;
    }
    /**
     * @param remark The remark to set.
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }
    /**
     * @return Returns the startDate.
     */
    public Timestamp getStartDate() {
        return startDate;
    }
    /**
     * @param startDate The startDate to set.
     */
    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }
    /**
     * @return Returns the statusId.
     */
    public long getStatusId() {
        return statusId;
    }
    /**
     * @param statusId The statusId to set.
     */
    public void setStatusId(long statusId) {
        this.statusId = statusId;
    }
    /**
     * @return Returns the sContractCode.
     */
    public String getSContractCode()
    {
    	return sContractCode;
    }
    /**
     * @param sContractCode The sContractCode to set.
     */
    public void setSContractCode(String sContractCode)
    {
    	this.sContractCode=sContractCode;
    }
    /**
     * @return Returns the sName.
     */
    public String getSName()
    {
    	return sName;
    }
    /**
     * @param sName The sName to set.
     */
    public void setSName(String sName)
    {
    	this.sName=sName;
    }
    /**
     * @return Returns the mLoanAmount.
     */
    public double getMLoanAmount()
    {
    	return mLoanAmount;
    }
    /**
     * @param mLoanAmount The mLoanAmount to set.
     */
    public void setMLoanAmount(double mLoanAmount)
    {
    	this.mLoanAmount=mLoanAmount;
    }
    /**
     * @return Returns the dtStartDate.
     */
    public Timestamp getDtStartDate()
    {
    	return dtStartDate;
    }
    /**
     * @param dtStartDate The dtStartDate to set.
     */
    public void setDtStartDate(Timestamp dtStartDate)
    {
    	this.dtStartDate=dtStartDate;
    }
    /**
     * @return Returns the dtStartDate.
     */
    public Timestamp getDtEndDate()
    {
    return dtEndDate;	
    }
    /**
     * @param dtEndDate The dtEndDate to set.
     */
    public void setDtEndDate(Timestamp dtEndDate)
    {
    	this.dtEndDate=dtEndDate;
    }
}
