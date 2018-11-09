/*
 * Created on 2006-4-13
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
public class Loan_LoanContractPlanInfo {
    
    private long id=-1;
    private long nLoanId=-1;
    private long nContractId=-1;
    private long nPlanVersion=-1;
    private long nStatusId=-1;
    private String sReason="";
    private long nIsUsed=-1;
    private long nUserType=-1;
    private Timestamp dtInputDate=null;

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
    public long getNContractId() {
        return nContractId;
    }
    public void setNContractId(long contractId) {
        nContractId = contractId;
    }
    public long getNIsUsed() {
        return nIsUsed;
    }
    public void setNIsUsed(long isUsed) {
        nIsUsed = isUsed;
    }
    public long getNLoanId() {
        return nLoanId;
    }
    public void setNLoanId(long loanId) {
        nLoanId = loanId;
    }
    public long getNPlanVersion() {
        return nPlanVersion;
    }
    public void setNPlanVersion(long planVersion) {
        nPlanVersion = planVersion;
    }
    public long getNStatusId() {
        return nStatusId;
    }
    public void setNStatusId(long statusId) {
        nStatusId = statusId;
    }
    public long getNUserType() {
        return nUserType;
    }
    public void setNUserType(long userType) {
        nUserType = userType;
    }
    public String getSReason() {
        return sReason;
    }
    public void setSReason(String reason) {
        sReason = reason;
    }
}
