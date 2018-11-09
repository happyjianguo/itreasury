/*
 * Created on 2004-10-14
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.settlement.settadjustinterestrate.dataentity;

import java.sql.Timestamp;

import com.iss.itreasury.settlement.base.SettlementBaseDataEntity;

/**
 * @author jinchen
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class SettRateAdjustPayConditionInfo extends SettlementBaseDataEntity
{

   /*
    *SQL> desc LOAN_RATEADJUSTPAYCONDITION 
	Name             Type          Nullable Default Comments                                           
	---------------- ------------- -------- ------- -------------------------------------------------- 
	ID               NUMBER                                                                            
	NBANKINTERESTID  NUMBER        Y                银行利率(FK_reference_loan_InterestRateHistory_ID) 
	NCONTRACTID      VARCHAR2(100)                  合同ID                                             
	MRATE            NUMBER(15,12) Y                利率                                               
	DTVALIDATE       DATE          Y                生效时间                                           
	SREASON          VARCHAR2(100) Y                原因                                               
	NINPUTUSERID     NUMBER        Y                录入人                                             
	DTINPUTDATE      DATE          Y                录入时间                                           
	NNEXTCHECKUSERID NUMBER        Y                审核人                                             
	NSTATUSID        NUMBER                         状态                                               
	NOFFICEID        NUMBER        Y                办事处                                             
	NLOANPAYNOTICEID NUMBER                         放款通知单ID                                       
	NNEXTCHECKLEVEL  NUMBER        Y                下一个审批级别                                     
	MSTAIDADJUSTRATE NUMBER(15,12) Y                固定浮动利率                                       
	MADJUSTRATE      NUMBER(15,12) Y                浮动比例      
    */
    
    private long ID;
    private long nBankInterestId;
    private long nContractId;
    private double mRate;
    private Timestamp dtValidate;
    private String sReason;
    private long nInputUserId;
    private Timestamp dtInputDate;
    private long nNextCheckUserId;
    private long nStatusId;
    private long nOfficeId;
    private long nLoanPayNoticeId;
    private long nNextCheckLevel;
    private double mStaidAdjustRate;
    private double mAdjustRate;
    
    private long lCurrencyId;	//币种ID备用
    /**
     * @return Returns the currencyId.
     */
    public long getCurrencyId()
    {
        return lCurrencyId;
    }
    /**
     * @param currencyId The currencyId to set.
     */
    public void setCurrencyId(long currencyId)
    {
        lCurrencyId = currencyId;
    }
    /**
     * @return Returns the staidAdjustRate.
     */
    public double getStaidAdjustRate()
    {
        return mStaidAdjustRate;
        
    }
    /**
     * @param staidAdjustRate The staidAdjustRate to set.
     */
    public void setStaidAdjustRate(double staidAdjustRate)
    {
        mStaidAdjustRate = staidAdjustRate;
        this.putUsedField("mStaidAdjustRate",staidAdjustRate);
    }
   
    
    /**
     * @return Returns the inputDate.
     */
    public Timestamp getInputDate()
    {
        return dtInputDate;
    }
    /**
     * @param inputDate The inputDate to set.
     */
    public void setInputDate(Timestamp inputDate)
    {
        dtInputDate = inputDate;
        this.putUsedField("dtInputDate",inputDate);
        
    }
    /**
     * @return Returns the validate.
     */
    public Timestamp getValidate()
    {
        return dtValidate;
    }
    /**
     * @param validate The validate to set.
     */
    public void setValidate(Timestamp validate)
    {
        dtValidate = validate;
        this.putUsedField("dtValidate",validate);
    }
/**
 * @return Returns the iD.
 */
public long getId()
{
    return ID;
}
/**
 * @param id The iD to set.
 */
public void setId(long id)
{
    ID = id;
    this.putUsedField("ID",id);
}
    /**
     * @return Returns the adjustRate.
     */
    public double getAdjustRate()
    {
        return mAdjustRate;
    }
    /**
     * @param adjustRate The adjustRate to set.
     */
    public void setAdjustRate(double adjustRate)
    {
        mAdjustRate = adjustRate;
        this.putUsedField("mAdjustRate",adjustRate);
    }
    /**
     * @return Returns the rate.
     */
    public double getRate()
    {
        return mRate;
    }
    /**
     * @param rate The rate to set.
     */
    public void setRate(double rate)
    {
        mRate = rate;
        this.putUsedField("mRate",rate);
    }
    /**
     * @return Returns the bankInterestId.
     */
    public long getBankInterestId()
    {
        return nBankInterestId;
    }
    /**
     * @param bankInterestId The bankInterestId to set.
     */
    public void setBankInterestId(long bankInterestId)
    {
        nBankInterestId = bankInterestId;
        this.putUsedField("nBankInterestId",bankInterestId);
    }
    /**
     * @return Returns the contractId.
     */
    public long getContractId()
    {
        return nContractId;
    }
    /**
     * @param contractId The contractId to set.
     */
    public void setContractId(long contractId)
    {
        nContractId = contractId;
        this.putUsedField("nContractId",contractId);
    }
    /**
     * @return Returns the inputUserId.
     */
    public long getInputUserId()
    {
        return nInputUserId;
    }
    /**
     * @param inputUserId The inputUserId to set.
     */
    public void setInputUserId(long inputUserId)
    {
        nInputUserId = inputUserId;
        this.putUsedField("nInputUserId",inputUserId);
    }
    /**
     * @return Returns the loanPayNoticeId.
     */
    public long getLoanPayNoticeId()
    {
        return nLoanPayNoticeId;
    }
    /**
     * @param loanPayNoticeId The loanPayNoticeId to set.
     */
    public void setLoanPayNoticeId(long loanPayNoticeId)
    {
        nLoanPayNoticeId = loanPayNoticeId;
        this.putUsedField("nLoanPayNoticeId",loanPayNoticeId);
    }
    /**
     * @return Returns the nextCheckLevel.
     */
    public long getNextCheckLevel()
    {
        return nNextCheckLevel;
    }
    /**
     * @param nextCheckLevel The nextCheckLevel to set.
     */
    public void setNextCheckLevel(long nextCheckLevel)
    {
        nNextCheckLevel = nextCheckLevel;
        this.putUsedField("nNextCheckLevel",nextCheckLevel);
    }
    /**
     * @return Returns the nextCheckUserId.
     */
    public long getNextCheckUserId()
    {
        return nNextCheckUserId;
    }
    /**
     * @param nextCheckUserId The nextCheckUserId to set.
     */
    public void setNextCheckUserId(long nextCheckUserId)
    {
        nNextCheckUserId = nextCheckUserId;
        this.putUsedField("nNextCheckUserId",nextCheckUserId);
    }
    /**
     * @return Returns the officeId.
     */
    public long getOfficeId()
    {
        return nOfficeId;
    }
    /**
     * @param officeId The officeId to set.
     */
    public void setOfficeId(long officeId)
    {
        nOfficeId = officeId;
        this.putUsedField("nOfficeId",officeId);
    }
    /**
     * @return Returns the statusId.
     */
    public long getStatusId()
    {
        return nStatusId;
    }
    /**
     * @param statusId The statusId to set.
     */
    public void setStatusId(long statusId)
    {
        nStatusId = statusId;
        this.putUsedField("nStatusId",statusId);
    }
    /**
     * @return Returns the reason.
     */
    public String getReason()
    {
        return sReason;
    }
    /**
     * @param reason The reason to set.
     */
    public void setReason(String reason)
    {
        sReason = reason;
        this.putUsedField("sReason",reason);
    }
    public static void main(String[] args)
    {
    }
}
