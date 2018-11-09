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
public class SettRateAdjustContractDetailInfo extends SettlementBaseDataEntity
{
    /*
     * SQL> desc LOAN_RATEADJUSTCONTRACTDETAIL 
		Name               Type          Nullable Default Comments               
		------------------ ------------- -------- ------- ---------------------- 
		ID                 NUMBER                                                
		NADJUSTCONDITIONID NUMBER                         ��������               
		NCONTRACTID        NUMBER                         ��ͬID                 
		NBANKINTERESTID    NUMBER        Y                ��������ID             
		NADJUSTSERIAL      NUMBER        Y                �������к�             
		DTSTARTDATE        DATE          Y                ���ʵ�����ʼ��         
		DTENDDATE          DATE          Y                ���ʵ���������         
		MRATE              NUMBER(15,12) Y                ����                   
		NISCOUNTINTEREST   NUMBER        Y                �Ƿ���Ϣ������ʹ�ã� 
		NEXTENDAPPLYID     NUMBER        Y                չ������ID             
		MSTAIDADJUSTRATE   NUMBER(15,12) Y                �̶���������           
		MADJUSTRATE        NUMBER(15,12) Y                ��������          
     */
    
    
    private long ID;
    private long nAdjustConditionId;
    private long nContractId;
    private long nBankInterestId;
    private long nAdjustSerial;
    private Timestamp dtStartDate;
    private Timestamp dtEndDate;
    private double mRate;
    private long IsConutInterest;
    private long NextEndApplyId;
    private double mStaidAdjustRate;
    private double mAdjustRate;
    
    
    
    public static void main(String[] args)
    {
    }
    /**
     * @return Returns the endDate.
     */
    public Timestamp getEndDate()
    {
        return dtEndDate;
    }
    /**
     * @param endDate The endDate to set.
     */
    public void setEndDate(Timestamp endDate)
    {
        dtEndDate = endDate;
        this.putUsedField("dtEndDate",endDate);
    }
    /**
     * @return Returns the startDate.
     */
    public Timestamp getStartDate()
    {
        return dtStartDate;
    }
    /**
     * @param startDate The startDate to set.
     */
    public void setStartDate(Timestamp startDate)
    {
        dtStartDate = startDate;
        this.putUsedField("dtStartDate",startDate);
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
     * @return Returns the adjustConditionId.
     */
    public long getAdjustConditionId()
    {
        return nAdjustConditionId;
    }
    /**
     * @param adjustConditionId The adjustConditionId to set.
     */
    public void setAdjustConditionId(long adjustConditionId)
    {
        nAdjustConditionId = adjustConditionId;
        this.putUsedField("nAdjustConditionId",adjustConditionId);
    }
    /**
     * @return Returns the adjustSerial.
     */
    public long getAdjustSerial()
    {
        return nAdjustSerial;
    }
    /**
     * @param adjustSerial The adjustSerial to set.
     */
    public void setAdjustSerial(long adjustSerial)
    {
        nAdjustSerial = adjustSerial;
        this.putUsedField("nAdjustSerial",adjustSerial);
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
     * @return Returns the isConutInterest.
     */
    public long getIsConutInterest()
    {
        return IsConutInterest;
    }
    /**
     * @param isConutInterest The isConutInterest to set.
     */
    public void setIsConutInterest(long isConutInterest)
    {
        IsConutInterest = isConutInterest;
        this.putUsedField("IsConutInterest",isConutInterest);
    }
    /**
     * @return Returns the nextEndApplyId.
     */
    public long getNextEndApplyId()
    {
        return NextEndApplyId;
    }
    /**
     * @param nextEndApplyId The nextEndApplyId to set.
     */
    public void setNextEndApplyId(long nextEndApplyId)
    {
        NextEndApplyId = nextEndApplyId;
        this.putUsedField("NextEndApplyId",nextEndApplyId);
    }
}
