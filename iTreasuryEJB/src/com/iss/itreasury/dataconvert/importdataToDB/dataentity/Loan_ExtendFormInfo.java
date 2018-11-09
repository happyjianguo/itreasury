/*
 * Created on 2006-4-18
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
public class Loan_ExtendFormInfo {
    
    private long id=-1;
    private long nContractId=-1;
    private long nPlanVersionId=-1;
    private long nLastPlanVersionId=-1;
    private long nSerialNo=-1;
    private String sExtendReason="";
    private String sReturnPostpend="";
    private String sOtherContent="";
    private long nStatusId=-1;
    private long nInputUserId=-1;
    private long nInterestTypeId=-1;
    private double mInterestAdjust=0.0;
    private long nNextCheckUserId=-1;
    private Timestamp dtInputDate=null;
    private long nextEndTypeId=-1;
    private long nBankInterestId=-1;
    private double mInterestRate=0.0;
    private long nNextCheckLevel=-1;
    private double mAdjustRate=0.0;
    private double mStaidAdjustRate=0.0;
    private long isLowLevel=-1;
    private long nOfficeId=-1;
    private long nCurrencyId=-1;


    public Timestamp getDtInputDate() {
        return dtInputDate;
    }
    public void setDtInputDate(Timestamp dtInputDate) {
        this.dtInputDate = dtInputDate;
    }
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public long getIsLowLevel() {
        return isLowLevel;
    }
    public void setIsLowLevel(long isLowLevel) {
        this.isLowLevel = isLowLevel;
    }
    public double getMAdjustRate() {
        return mAdjustRate;
    }
    public void setMAdjustRate(double adjustRate) {
        mAdjustRate = adjustRate;
    }
    public double getMInterestAdjust() {
        return mInterestAdjust;
    }
    public void setMInterestAdjust(double interestAdjust) {
        mInterestAdjust = interestAdjust;
    }
    public double getMInterestRate() {
        return mInterestRate;
    }
    public void setMInterestRate(double interestRate) {
        mInterestRate = interestRate;
    }
    public long getNBankInterestId() {
        return nBankInterestId;
    }
    public void setNBankInterestId(long bankInterestId) {
        nBankInterestId = bankInterestId;
    }
    public long getNContractId() {
        return nContractId;
    }
    public void setNContractId(long contractId) {
        nContractId = contractId;
    }
    public long getNCurrencyId() {
        return nCurrencyId;
    }
    public void setNCurrencyId(long currencyId) {
        nCurrencyId = currencyId;
    }
    public long getNextEndTypeId() {
        return nextEndTypeId;
    }
    public void setNextEndTypeId(long nextEndTypeId) {
        this.nextEndTypeId = nextEndTypeId;
    }
    public long getNInputUserId() {
        return nInputUserId;
    }
    public void setNInputUserId(long inputUserId) {
        nInputUserId = inputUserId;
    }
    public long getNInterestTypeId() {
        return nInterestTypeId;
    }
    public void setNInterestTypeId(long interestTypeId) {
        nInterestTypeId = interestTypeId;
    }
    public long getNLastPlanVersionId() {
        return nLastPlanVersionId;
    }
    public void setNLastPlanVersionId(long lastPlanVersionId) {
        nLastPlanVersionId = lastPlanVersionId;
    }
    public long getNNextCheckLevel() {
        return nNextCheckLevel;
    }
    public void setNNextCheckLevel(long nextCheckLevel) {
        nNextCheckLevel = nextCheckLevel;
    }
    public long getNNextCheckUserId() {
        return nNextCheckUserId;
    }
    public void setNNextCheckUserId(long nextCheckUserId) {
        nNextCheckUserId = nextCheckUserId;
    }
    public long getNOfficeId() {
        return nOfficeId;
    }
    public void setNOfficeId(long officeId) {
        nOfficeId = officeId;
    }
    public long getNPlanVersionId() {
        return nPlanVersionId;
    }
    public void setNPlanVersionId(long planVersionId) {
        nPlanVersionId = planVersionId;
    }
    public long getNSerialNo() {
        return nSerialNo;
    }
    public void setNSerialNo(long serialNo) {
        nSerialNo = serialNo;
    }
    public long getNStatusId() {
        return nStatusId;
    }
    public void setNStatusId(long statusId) {
        nStatusId = statusId;
    }
    public String getSExtendReason() {
        return sExtendReason;
    }
    public void setSExtendReason(String extendReason) {
        sExtendReason = extendReason;
    }
    public String getSOtherContent() {
        return sOtherContent;
    }
    public void setSOtherContent(String otherContent) {
        sOtherContent = otherContent;
    }
    public String getSReturnPostpend() {
        return sReturnPostpend;
    }
    public void setSReturnPostpend(String returnPostpend) {
        sReturnPostpend = returnPostpend;
    }
    public double getMStaidAdjustRate() {
        return mStaidAdjustRate;
    }
    public void setMStaidAdjustRate(double staidAdjustRate) {
        mStaidAdjustRate = staidAdjustRate;
    }
}
