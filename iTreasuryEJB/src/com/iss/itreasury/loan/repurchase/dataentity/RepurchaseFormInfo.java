package com.iss.itreasury.loan.repurchase.dataentity;

import java.sql.Timestamp;

import com.iss.itreasury.loan.base.LoanBaseDataEntity;

/**
 * @author shantao
 * 
 * @version 1.0 2005-12-27
 */
public class RepurchaseFormInfo extends LoanBaseDataEntity
{
    private String code ;

    private double amount ;

    private double rate;

    private Timestamp startDate;

    private Timestamp endDate;

    private long bankID;

    private String remark;

    private long inputUserID;

    private Timestamp inputDate;

    private long statusID;

    private long officeID;

    private long currencyID;
    
    private Timestamp repurchaseDate;
    
    
    /**
     * @return Returns the repurchaseDate.
     */
    public Timestamp getRepurchaseDate()
    {
        return repurchaseDate;
    }
    /**
     * @param repurchaseDate The repurchaseDate to set.
     */
    public void setRepurchaseDate(Timestamp repurchaseDate)
    {
        this.repurchaseDate = repurchaseDate;
    }
    /**
     * @return Returns the amount.
     */
    public double getAmount()
    {
        return amount;
    }
    /**
     * @param amount The amount to set.
     */
    public void setAmount(double amount)
    {
        this.amount = amount;
        this.putUsedField("amount",amount);
    }
    /**
     * @return Returns the bankID.
     */
    public long getBankID()
    {
        return bankID;
    }
    /**
     * @param bankID The bankID to set.
     */
    public void setBankID(long bankID)
    {
        this.bankID = bankID;
        this.putUsedField("bankID",bankID);
    }
    /**
     * @return Returns the code.
     */
    public String getCode()
    {
        return code;
    }
    /**
     * @param code The code to set.
     */
    public void setCode(String code)
    {
        this.code = code;
        this.putUsedField("code",code);
    }
    /**
     * @return Returns the currencyID.
     */
    public long getCurrencyID()
    {
        return currencyID;
    }
    /**
     * @param currencyID The currencyID to set.
     */
    public void setCurrencyID(long currencyID)
    {
        this.currencyID = currencyID;
        this.putUsedField("currencyID",currencyID);
    }
    /**
     * @return Returns the endDate.
     */
    public Timestamp getEndDate()
    {
        return endDate;
    }
    /**
     * @param endDate The endDate to set.
     */
    public void setEndDate(Timestamp endDate)
    {
        this.endDate = endDate;
        this.putUsedField("endDate",endDate);
    }

    /**
     * @return Returns the inputDate.
     */
    public Timestamp getInputDate()
    {
        return inputDate;
    }
    /**
     * @param inputDate The inputDate to set.
     */
    public void setInputDate(Timestamp inputDate)
    {
        this.inputDate = inputDate;
        this.putUsedField("inputDate",inputDate);
    }
    /**
     * @return Returns the inputUserID.
     */
    public long getInputUserID()
    {
        return inputUserID;
    }
    /**
     * @param inputUserID The inputUserID to set.
     */
    public void setInputUserID(long inputUserID)
    {
        this.inputUserID = inputUserID;
        this.putUsedField("inputUserID",inputUserID);
    }
    /**
     * @return Returns the officeID.
     */
    public long getOfficeID()
    {
        return officeID;
    }
    /**
     * @param officeID The officeID to set.
     */
    public void setOfficeID(long officeID)
    {
        this.officeID = officeID;
        this.putUsedField("officeID",officeID);
    }
    /**
     * @return Returns the rate.
     */
    public double getRate()
    {
        return rate;
    }
    /**
     * @param rate The rate to set.
     */
    public void setRate(double rate)
    {
        this.rate = rate;
        this.putUsedField("rate",rate);
    }
    /**
     * @return Returns the remark.
     */
    public String getRemark()
    {
        return remark;
    }
    /**
     * @param remark The remark to set.
     */
    public void setRemark(String remark)
    {
        this.remark = remark;
        this.putUsedField("remark",remark);
    }
    /**
     * @return Returns the startDate.
     */
    public Timestamp getStartDate()
    {
        return startDate;
    }
    /**
     * @param startDate The startDate to set.
     */
    public void setStartDate(Timestamp startDate)
    {
        this.startDate = startDate;
        this.putUsedField("startDate",startDate);
    }
    /**
     * @return Returns the statusID.
     */
    public long getStatusID()
    {
        return statusID;
    }
    /**
     * @param statusID The statusID to set.
     */
    public void setStatusID(long statusID)
    {
        this.statusID = statusID;
        this.putUsedField("statusID",statusID);
    }
}