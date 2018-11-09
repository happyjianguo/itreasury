/*
 * Created on 2006-4-16
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
public class Loan_RateAdjustContractDetailInfo {
    
    private long id=-1;
    private long nAdjustConditionId=-1;
    private long nContractId=-1;
    private long nBankInterestId=-1;
    private long nAdjustSerial=-1;
    private Timestamp dtStartDate=null;
    private Timestamp dtEndDate=null;
    private double mRate=0.0;
    private long nIsCountInterest=-1;
    private long nextEndApplyId=-1;
    private double mStaidAdjustRate=0.0;
    private double mAdjustRate=0.0;

    public long getNContractId() {
        return nContractId;
    }
    public void setNContractId(long contractId) {
        nContractId = contractId;
    }
    public Timestamp getDtEndDate() {
        return dtEndDate;
    }
    public void setDtEndDate(Timestamp dtEndDate) {
        this.dtEndDate = dtEndDate;
    }
    public Timestamp getDtStartDate() {
        return dtStartDate;
    }
    public void setDtStartDate(Timestamp dtStartDate) {
        this.dtStartDate = dtStartDate;
    }
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public double getMAdjustRate() {
        return mAdjustRate;
    }
    public void setMAdjustRate(double adjustRate) {
        mAdjustRate = adjustRate;
    }
    public double getMRate() {
        return mRate;
    }
    public void setMRate(double rate) {
        mRate = rate;
    }
    public double getMStaidAdjustRate() {
        return mStaidAdjustRate;
    }
    public void setMStaidAdjustRate(double staidAdjustRate) {
        mStaidAdjustRate = staidAdjustRate;
    }
    public long getNAdjustConditionId() {
        return nAdjustConditionId;
    }
    public void setNAdjustConditionId(long adjustConditionId) {
        nAdjustConditionId = adjustConditionId;
    }
    public long getNAdjustSerial() {
        return nAdjustSerial;
    }
    public void setNAdjustSerial(long adjustSerial) {
        nAdjustSerial = adjustSerial;
    }
    public long getNBankInterestId() {
        return nBankInterestId;
    }
    public void setNBankInterestId(long bankInterestId) {
        nBankInterestId = bankInterestId;
    }
    public long getNextEndApplyId() {
        return nextEndApplyId;
    }
    public void setNextEndApplyId(long nextEndApplyId) {
        this.nextEndApplyId = nextEndApplyId;
    }
    public long getNIsCountInterest() {
        return nIsCountInterest;
    }
    public void setNIsCountInterest(long isCountInterest) {
        nIsCountInterest = isCountInterest;
    }
}
