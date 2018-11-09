/*
 * Created on 2006-4-14
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.dataconvert.importdataToDB.dataentity;

/**
 * @author yinghu
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Loan_InterestRateTypeInfoInfo {
    
    private long id=-1;
    private String sInterestRateNo="";
    private String sInterestRateName="";
    private long nCurrencyId=-1;
    private long nOfficeId=-1;


    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public long getNCurrencyId() {
        return nCurrencyId;
    }
    public void setNCurrencyId(long currencyId) {
        nCurrencyId = currencyId;
    }
    public long getNOfficeId() {
        return nOfficeId;
    }
    public void setNOfficeId(long officeId) {
        nOfficeId = officeId;
    }
    public String getSInterestRateName() {
        return sInterestRateName;
    }
    public void setSInterestRateName(String interestRateName) {
        sInterestRateName = interestRateName;
    }
    public String getSInterestRateNo() {
        return sInterestRateNo;
    }
    public void setSInterestRateNo(String interestRateNo) {
        sInterestRateNo = interestRateNo;
    }
}
