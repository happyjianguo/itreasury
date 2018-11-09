/*
 * Created on 2005-12-28
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.loan.query.dataentity;

import java.sql.Timestamp;


/**
 * @author yinghu
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class RepurchaseFormInfo {

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
    private long officeId=-1;
    private long currencyId=-1;
    private Timestamp repurchaseDate=null;
    
    
    public Timestamp getRepurchaseDate() {
        return repurchaseDate;
    }
    public void setRepurchaseDate(Timestamp repurchaseDate) {
        this.repurchaseDate = repurchaseDate;
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
}
