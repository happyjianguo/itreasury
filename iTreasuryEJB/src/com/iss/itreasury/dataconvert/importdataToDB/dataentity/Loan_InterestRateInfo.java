/*
 * Created on 2006-4-14
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.dataconvert.importdataToDB.dataentity;

import java.sql.Timestamp;

/**
 * @author yinghu
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Loan_InterestRateInfo {
    
    private long id=-1;
    private long nBankInterestTypeId=-1;
    private double mRate=0.0;
    private Timestamp dtValiDate=null;
    private long nInputUserId=-1;
    private Timestamp dtInput=null;
    private long nModifyUserId=-1;
    private Timestamp dtModify=null;
    private long nOfficeId=-1;
    private long nCurrencyId=-1;

    public Timestamp getDtInput() {
        return dtInput;
    }
    public void setDtInput(Timestamp dtInput) {
        this.dtInput = dtInput;
    }
    public Timestamp getDtModify() {
        return dtModify;
    }
    public void setDtModify(Timestamp dtModify) {
        this.dtModify = dtModify;
    }
    public Timestamp getDtValiDate() {
        return dtValiDate;
    }
    public void setDtValiDate(Timestamp dtValiDate) {
        this.dtValiDate = dtValiDate;
    }
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public double getMRate() {
        return mRate;
    }
    public void setMRate(double rate) {
        mRate = rate;
    }
    public long getNBankInterestTypeId() {
        return nBankInterestTypeId;
    }
    public void setNBankInterestTypeId(long bankInterestTypeId) {
        nBankInterestTypeId = bankInterestTypeId;
    }
    public long getNCurrencyId() {
        return nCurrencyId;
    }
    public void setNCurrencyId(long currencyId) {
        nCurrencyId = currencyId;
    }
    public long getNInputUserId() {
        return nInputUserId;
    }
    public void setNInputUserId(long inputUserId) {
        nInputUserId = inputUserId;
    }
    public long getNModifyUserId() {
        return nModifyUserId;
    }
    public void setNModifyUserId(long modifyUserId) {
        nModifyUserId = modifyUserId;
    }
    public long getNOfficeId() {
        return nOfficeId;
    }
    public void setNOfficeId(long officeId) {
        nOfficeId = officeId;
    }
}
