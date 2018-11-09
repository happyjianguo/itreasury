package com.iss.itreasury.loan.query.dataentity;

/**
 * @author haoning
 * time:   2003-11-23
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
import java.io.Serializable;

public class QueryMyJobCountInfo implements Serializable
{
    //------------------------------����--------------------------------//
    //�������롪��׫д-����
    private long[] LoanApplySaveCount =null;
    
    //�������롪���ύ-����
    private long[] LoanApplySubmitCount =null;
    
    //�������롪�������-����
    private long[] LoanApplyForCheckCount =null;
    
    //չ�����롪���ύ-����
    private long[] ExtendApplySubmitCount =null;
    
    //չ�����롪�������-����
    private long[] ExtendApplyForCheckCount =null;
    
    //�⻹���롪���ύ-����
    private long[] FreeApplySubmitCount ={0,0,0,0,0};
    
    //�⻹���롪�������-����
    private long[] FreeApplyForCheckCount ={0,0,0,0,0};
    
    //�������롪��׫д-����
    private long DiscountApplySaveCount =0;
    
    //�������롪���ύ-����
    private long DiscountApplySubmitCount =0;
    
    //�������롪�������-����
    private long DiscountApplyForCheckCount =0;
    
    //����ƾ֤�����ύ-����
    private long DiscountCredenceSubmitCount =0;
    
    //����ƾ֤���������-����
    private long DiscountCredenceForCheckCount =0;
    
	//ת�������롪��׫д-����
	private long TransDiscountApplySaveCount =0;
	
	//ת�������롪���ύ-����
	private long TransDiscountApplySubmitCount =0;
    
	//ת�������롪�������-����
	private long TransDiscountApplyForCheckCount =0;
    
	//ת����ƾ֤�����ύ-����
	private long TransDiscountCredenceSubmitCount =0;
    
	//ת����ƾ֤���������-����
	private long TransDiscountCredenceForCheckCount =0;
	
	//ת���ֻع�ƾ֤�����ύ-����
	private long RepurchaseCredenceSubmitCount =0;
    
	//ת���ֻع�ƾ֤���������-����
	private long RepurchaseCredenceForCheckCount =0;	
    
    //�ſ�֪ͨ�������ύ-����
    private long PaySubmitCount =0;
    
    //�ſ�֪ͨ�����������-����
    private long PayForCheckCount =0;
    
    //�����տ�֪ͨ��--�ύ--�������Ϻ����������������ͣ� 2004-12-8
    private long AssureChargeNoticeSubmitCount = 0;
    
    //�����տ�֪ͨ��-- �����--���� ���Ϻ����������������ͣ� 2004-12-8
    private long AssureChargeNoticeForCheckCount = 0;
    
    //������֪ͨ��--�ύ--���� ���Ϻ����������������ͣ� 2004-12-8
    private long AssureManagementNoticeSubmitCount = 0;
    
    //������֪ͨ��--�����--���� ���Ϻ����������������ͣ� 2004-12-8
    private long AssureManagementNoticeForCheckCount = 0;
    
    //���ڴ������ύ-����
    private long OverDueSubmitCount =0;
    
    //���ڴ����������-����
    private long OverDueForCheckCount =0;
    
    //��ִͬ�мƻ������ύ-����
    private long ContractPlanSubmitCount =0;
    
    //��ִͬ�мƻ����������-����
    private long ContractPlanForCheckCount =0;
    
    //���ʵ��������ύ-����
    private long RateAdjustSubmitCount =0;
    
    //���ʵ������������-����
    private long RateAdjustForCheckCount =0;
    
    //��ǰ������ύ-����
    private long AheadRePaySubmitCount =0;
    
    //��ǰ����������-����
    private long AheadRePayForCheckCount =0;
    
    //����״̬��������ύ-����
    private long RiskStatusAdjustSubmitCount =0;
    
    //����״̬������������-����
    private long RiskStatusAdjustForCheckCount =0;
    
    //��ͬ״̬��������ύ-����
    private long ContractStatusAdjustSubmitCount =0;
    
    //��ͬ״̬������������-����
    private long ContractStatusAdjustForCheckCount =0;
    
    //���ŷſ�֪ͨ�������ύ-����
    private long YTPaySubmitCount =0;
    
    //���ŷſ�֪ͨ�����������-����
    private long YTPayForCheckCount =0;
    
    //�������֪ͨ�������ύ-����
    private long YTDrawNoticeSubmitCount =0;
    
    //�������֪ͨ�����������-����
    private long YTDrawNoticeForCheckCount =0;
    
    //��˴���ת�����롪��׫д-����
      private long AttornmentApplySaveCount =0;
      
    //��˴���ת�����롪���ύ-����
    private long AttornmentApplySubmitCount =0;
    
    //��˴���ת�����롪�������-����
    private long AttornmentApplyForCheckCount =0;
    
    
    //-------------------------------��ͬ-------------------------------//
    //�����ͬ����׫д-����
    private long[] ContractApplySaveCount =null;
    
    //�����ͬ�����ύ-����
    private long[] ContractApplySubmitCount =null;
    
    //�����ͬ���������-����
    private long[] ContractApplyForCheckCount =null;
    
    //�����ͬ����������-����
    private long ContractApplyForActiveCount =0;
    
    //չ�ں�ͬ����׫д-����
    private long[] ExtendContractSaveCount =null;
    
    //չ�ں�ͬ�����ύ-����
    private long[] ExtendContractSubmitCount =null;
    
    //չ�ں�ͬ���������-����
    private long[] ExtendContractForCheckCount =null;
    
    //���ֺ�ͬ����׫д-����
    private long DiscountContractSaveCount =0;
    
    //���ֺ�ͬ�����ύ-����
    private long DiscountContractSubmitCount =0;
    
    //���ֺ�ͬ���������-����
    private long DiscountContractForCheckCount =0;

	//ת���ֺ�ͬ����׫д-����
	private long TransDiscountContractSaveCount =0;
    
	//ת���ֺ�ͬ�����ύ-����
	private long TransDiscountContractSubmitCount =0;
    
	//ת���ֺ�ͬ���������-����
	private long TransDiscountContractForCheckCount =0;    
    
    //=========================��������==========================//
    //
    private long OfficeID = -1;
    
    //
    private long CurrencyID = -1;
    
    //
    private long UserID = -1;
    
    //
    private long StatusID = -1;
    
    
    /**
     * @param 
     * function �õ�/���ô������롪��׫д-����
     * return long[]
     */
    public long[] getLoanApplySaveCount()
    {
        return LoanApplySaveCount;
    }

    /**
     * @param 
     * function �õ�/���ô������롪��׫д-����
     * return void
     */
    public void setLoanApplySaveCount(long[] ds)
    {
        this.LoanApplySaveCount = ds;
    }

    /**
     * @param 
     * function �õ�/���ô������롪���ύ-����
     * return long[]
     */
    public long[] getLoanApplySubmitCount()
    {
        return LoanApplySubmitCount;
    }

    /**
     * @param 
     * function �õ�/���ô������롪���ύ-����
     * return void
     */
    public void setLoanApplySubmitCount(long[] ds)
    {
        this.LoanApplySubmitCount = ds;
    }

    /**
     * @param 
     * function �õ�/���ô������롪�������-����
     * return long[]
     */
    public long[] getLoanApplyForCheckCount()
    {
        return LoanApplyForCheckCount;
    }

    /**
     * @param 
     * function �õ�/���ô������롪�������-����
     * return void
     */
    public void setLoanApplyForCheckCount(long[] ds)
    {
        this.LoanApplyForCheckCount = ds;
    }

    /**
     * @param 
     * function �õ�/����չ�����롪���ύ-����
     * return long
     */
    public long[] getExtendApplySubmitCount()
    {
        return ExtendApplySubmitCount;
    }

    /**
     * @param 
     * function �õ�/����չ�����롪���ύ-����
     * return void
     */
    public void setExtendApplySubmitCount(long[] d)
    {
        this.ExtendApplySubmitCount = d;
    }

    /**
     * @param 
     * function �õ�/����չ�����롪�������-����
     * return long
     */
    public long[] getExtendApplyForCheckCount()
    {
        return ExtendApplyForCheckCount;
    }

    /**
     * @param 
     * function �õ�/����չ�����롪�������-����
     * return void
     */
    public void setExtendApplyForCheckCount(long[] d)
    {
        this.ExtendApplyForCheckCount = d;
    }

    /**
     * @param 
     * function �õ�/�����⻹���롪���ύ-����
     * return long[]
     */
    public long[] getFreeApplySubmitCount()
    {
        return FreeApplySubmitCount;
    }

    /**
     * @param 
     * function �õ�/�����⻹���롪���ύ-����
     * return void
     */
    public void setFreeApplySubmitCount(long[] ds)
    {
        this.FreeApplySubmitCount = ds;
    }

    /**
     * @param 
     * function �õ�/�����⻹���롪�������-����
     * return long[]
     */
    public long[] getFreeApplyForCheckCount()
    {
        return FreeApplyForCheckCount;
    }

    /**
     * @param 
     * function �õ�/�����⻹���롪�������-����
     * return void
     */
    public void setFreeApplyForCheckCount(long[] ds)
    {
        this.FreeApplyForCheckCount = ds;
    }

    /**
     * @param 
     * function �õ�/�����������롪��׫д-����
     * return long
     */
    public long getDiscountApplySaveCount()
    {
        return DiscountApplySaveCount;
    }

    /**
     * @param 
     * function �õ�/�����������롪��׫д-����
     * return void
     */
    public void setDiscountApplySaveCount(long d)
    {
        this.DiscountApplySaveCount = d;
    }

    /**
     * @param 
     * function �õ�/�����������롪���ύ-����
     * return long
     */
    public long getDiscountApplySubmitCount()
    {
        return DiscountApplySubmitCount;
    }

    /**
     * @param 
     * function �õ�/�����������롪���ύ-����
     * return void
     */
    public void setDiscountApplySubmitCount(long d)
    {
        this.DiscountApplySubmitCount = d;
    }

    /**
     * @param 
     * function �õ�/�����������롪�������-����
     * return long
     */
    public long getDiscountApplyForCheckCount()
    {
        return DiscountApplyForCheckCount;
    }

    /**
     * @param 
     * function �õ�/�����������롪�������-����
     * return void
     */
    public void setDiscountApplyForCheckCount(long d)
    {
        this.DiscountApplyForCheckCount = d;
    }

    /**
     * @param 
     * function �õ�/��������ƾ֤�����ύ-����
     * return long
     */
    public long getDiscountCredenceSubmitCount()
    {
        return DiscountCredenceSubmitCount;
    }

    /**
     * @param 
     * function �õ�/��������ƾ֤�����ύ-����
     * return void
     */
    public void setDiscountCredenceSubmitCount(long d)
    {
        this.DiscountCredenceSubmitCount = d;
    }

    /**
     * @param 
     * function �õ�/��������ƾ֤���������-����
     * return long
     */
    public long getDiscountCredenceForCheckCount()
    {
        return DiscountCredenceForCheckCount;
    }

    /**
     * @param 
     * function �õ�/��������ƾ֤���������-����
     * return void
     */
    public void setDiscountCredenceForCheckCount(long d)
    {
        this.DiscountCredenceForCheckCount = d;
    }

    /**
     * @param 
     * function �õ�/���÷ſ�֪ͨ�������ύ-����
     * return long
     */
    public long getPaySubmitCount()
    {
        return PaySubmitCount;
    }

    /**
     * @param 
     * function �õ�/���÷ſ�֪ͨ�������ύ-����
     * return void
     */
    public void setPaySubmitCount(long d)
    {
        this.PaySubmitCount = d;
    }

    /**
     * @param 
     * function �õ�/���÷ſ�֪ͨ�����������-����
     * return long
     */
    public long getPayForCheckCount()
    {
        return PayForCheckCount;
    }

    /**
     * @param 
     * function �õ�/���÷ſ�֪ͨ�����������-����
     * return void
     */
    public void setPayForCheckCount(long d)
    {
        this.PayForCheckCount = d;
    }

    /**
     * @param 
     * function �õ�/�������ڴ������ύ-����
     * return long
     */
    public long getOverDueSubmitCount()
    {
        return OverDueSubmitCount;
    }

    /**
     * @param 
     * function �õ�/�������ڴ������ύ-����
     * return void
     */
    public void setOverDueSubmitCount(long d)
    {
        this.OverDueSubmitCount = d;
    }

    /**
     * @param 
     * function �õ�/�������ڴ����������-����
     * return long
     */
    public long getOverDueForCheckCount()
    {
        return OverDueForCheckCount;
    }

    /**
     * @param 
     * function �õ�/�������ڴ����������-����
     * return void
     */
    public void setOverDueForCheckCount(long d)
    {
        this.OverDueForCheckCount = d;
    }

    /**
     * @param 
     * function �õ�/���ú�ִͬ�мƻ������ύ-����
     * return long
     */
    public long getContractPlanSubmitCount()
    {
        return ContractPlanSubmitCount;
    }

    /**
     * @param 
     * function �õ�/���ú�ִͬ�мƻ������ύ-����
     * return void
     */
    public void setContractPlanSubmitCount(long d)
    {
        this.ContractPlanSubmitCount = d;
    }

    /**
     * @param 
     * function �õ�/���ú�ִͬ�мƻ����������-����
     * return long
     */
    public long getContractPlanForCheckCount()
    {
        return ContractPlanForCheckCount;
    }

    /**
     * @param 
     * function �õ�/���ú�ִͬ�мƻ����������-����
     * return void
     */
    public void setContractPlanForCheckCount(long d)
    {
        this.ContractPlanForCheckCount = d;
    }

    /**
     * @param 
     * function �õ�/�������ʵ��������ύ-����
     * return long
     */
    public long getRateAdjustSubmitCount()
    {
        return RateAdjustSubmitCount;
    }

    /**
     * @param 
     * function �õ�/�������ʵ��������ύ-����
     * return void
     */
    public void setRateAdjustSubmitCount(long d)
    {
        this.RateAdjustSubmitCount = d;
    }

    /**
     * @param 
     * function �õ�/�������ʵ������������-����
     * return long
     */
    public long getRateAdjustForCheckCount()
    {
        return RateAdjustForCheckCount;
    }

    /**
     * @param 
     * function �õ�/�������ʵ������������-����
     * return void
     */
    public void setRateAdjustForCheckCount(long d)
    {
        this.RateAdjustForCheckCount = d;
    }

    /**
     * @param 
     * function �õ�/������ǰ������ύ-����
     * return long
     */
    public long getAheadRePaySubmitCount()
    {
        return AheadRePaySubmitCount;
    }

    /**
     * @param 
     * function �õ�/������ǰ������ύ-����
     * return void
     */
    public void setAheadRePaySubmitCount(long d)
    {
        this.AheadRePaySubmitCount = d;
    }

    /**
     * @param 
     * function �õ�/������ǰ����������-����
     * return long
     */
    public long getAheadRePayForCheckCount()
    {
        return AheadRePayForCheckCount;
    }

    /**
     * @param 
     * function �õ�/������ǰ����������-����
     * return void
     */
    public void setAheadRePayForCheckCount(long d)
    {
        this.AheadRePayForCheckCount = d;
    }

    /**
     * @param 
     * function �õ�/���÷���״̬��������ύ-����
     * return long
     */
    public long getRiskStatusAdjustSubmitCount()
    {
        return RiskStatusAdjustSubmitCount;
    }

    /**
     * @param 
     * function �õ�/���÷���״̬��������ύ-����
     * return void
     */
    public void setRiskStatusAdjustSubmitCount(long d)
    {
        this.RiskStatusAdjustSubmitCount = d;
    }

    /**
     * @param 
     * function �õ�/���÷���״̬������������-����
     * return long
     */
    public long getRiskStatusAdjustForCheckCount()
    {
        return RiskStatusAdjustForCheckCount;
    }

    /**
     * @param 
     * function �õ�/���÷���״̬������������-����
     * return void
     */
    public void setRiskStatusAdjustForCheckCount(long d)
    {
        this.RiskStatusAdjustForCheckCount = d;
    }

    /**
     * @param 
     * function �õ�/���ú�ͬ״̬��������ύ-����
     * return long
     */
    public long getContractStatusAdjustSubmitCount()
    {
        return ContractStatusAdjustSubmitCount;
    }

    /**
     * @param 
     * function �õ�/���ú�ͬ״̬��������ύ-����
     * return void
     */
    public void setContractStatusAdjustSubmitCount(long d)
    {
        this.ContractStatusAdjustSubmitCount = d;
    }

    /**
     * @param 
     * function �õ�/���ú�ͬ״̬������������-����
     * return long
     */
    public long getContractStatusAdjustForCheckCount()
    {
        return ContractStatusAdjustForCheckCount;
    }

    /**
     * @param 
     * function �õ�/���ú�ͬ״̬������������-����
     * return void
     */
    public void setContractStatusAdjustForCheckCount(long d)
    {
        this.ContractStatusAdjustForCheckCount = d;
    }

    /**
     * @param 
     * function �õ�/���ô����ͬ����׫д-����
     * return long[]
     */
    public long[] getContractApplySaveCount()
    {
        return ContractApplySaveCount;
    }

    /**
     * @param 
     * function �õ�/���ô����ͬ����׫д-����
     * return void
     */
    public void setContractApplySaveCount(long[] ds)
    {
        this.ContractApplySaveCount = ds;
    }

    /**
     * @param 
     * function �õ�/���ô����ͬ�����ύ-����
     * return long[]
     */
    public long[] getContractApplySubmitCount()
    {
        return ContractApplySubmitCount;
    }

    /**
     * @param 
     * function �õ�/���ô����ͬ�����ύ-����
     * return void
     */
    public void setContractApplySubmitCount(long[] ds)
    {
        this.ContractApplySubmitCount = ds;
    }

    /**
     * @param 
     * function �õ�/���ô����ͬ���������-����
     * return long[]
     */
    public long[] getContractApplyForCheckCount()
    {
        return ContractApplyForCheckCount;
    }

    /**
     * @param 
     * function �õ�/���ô����ͬ���������-����
     * return void
     */
    public void setContractApplyForCheckCount(long[] ds)
    {
        this.ContractApplyForCheckCount = ds;
    }

    /**
     * @param 
     * function �õ�/���ô����ͬ����������-����
     * return long[]
     */
    public long getContractApplyForActiveCount()
    {
        return ContractApplyForActiveCount;
    }

    /**
     * @param 
     * function �õ�/���ô����ͬ����������-����
     * return void
     */
    public void setContractApplyForActiveCount(long ds)
    {
        this.ContractApplyForActiveCount = ds;
    }

    /**
     * @param 
     * function �õ�/����չ�ں�ͬ����׫д-����
     * return long
     */
    public long[] getExtendContractSaveCount()
    {
        return ExtendContractSaveCount;
    }

    /**
     * @param 
     * function �õ�/����չ�ں�ͬ����׫д-����
     * return void
     */
    public void setExtendContractSaveCount(long[] d)
    {
        this.ExtendContractSaveCount = d;
    }

    /**
     * @param 
     * function �õ�/����չ�ں�ͬ�����ύ-����
     * return long
     */
    public long[] getExtendContractSubmitCount()
    {
        return ExtendContractSubmitCount;
    }

    /**
     * @param 
     * function �õ�/����չ�ں�ͬ�����ύ-����
     * return void
     */
    public void setExtendContractSubmitCount(long[] d)
    {
        this.ExtendContractSubmitCount = d;
    }

    /**
     * @param 
     * function �õ�/����չ�ں�ͬ���������-����
     * return long
     */
    public long[] getExtendContractForCheckCount()
    {
        return ExtendContractForCheckCount;
    }

    /**
     * @param 
     * function �õ�/����չ�ں�ͬ���������-����
     * return void
     */
    public void setExtendContractForCheckCount(long[] d)
    {
        this.ExtendContractForCheckCount = d;
    }

    /**
     * @param 
     * function �õ�/�������ֺ�ͬ����׫д-����
     * return long
     */
    public long getDiscountContractSaveCount()
    {
        return DiscountContractSaveCount;
    }

    /**
     * @param 
     * function �õ�/�������ֺ�ͬ����׫д-����
     * return void
     */
    public void setDiscountContractSaveCount(long d)
    {
        this.DiscountContractSaveCount = d;
    }

    /**
     * @param 
     * function �õ�/�������ֺ�ͬ�����ύ-����
     * return long
     */
    public long getDiscountContractSubmitCount()
    {
        return DiscountContractSubmitCount;
    }

    /**
     * @param 
     * function �õ�/�������ֺ�ͬ�����ύ-����
     * return void
     */
    public void setDiscountContractSubmitCount(long d)
    {
        this.DiscountContractSubmitCount = d;
    }

    /**
     * @param 
     * function �õ�/�������ֺ�ͬ���������-����
     * return long
     */
    public long getDiscountContractForCheckCount()
    {
        return DiscountContractForCheckCount;
    }

    /**
     * @param 
     * function �õ�/�������ֺ�ͬ���������-����
     * return void
     */
    public void setDiscountContractForCheckCount(long d)
    {
        this.DiscountContractForCheckCount = d;
    }

    /**
     * @param 
     * function �õ�/����
     * return long
     */
    public long getOfficeID()
    {
        return OfficeID;
    }

    /**
     * @param 
     * function �õ�/����
     * return void
     */
    public void setOfficeID(long l)
    {
        this.OfficeID = l;
    }

    /**
     * @param 
     * function �õ�/����
     * return long
     */
    public long getCurrencyID()
    {
        return CurrencyID;
    }

    /**
     * @param 
     * function �õ�/����
     * return void
     */
    public void setCurrencyID(long l)
    {
        this.CurrencyID = l;
    }

    /**
     * @param 
     * function �õ�/����
     * return long
     */
    public long getUserID()
    {
        return UserID;
    }

    /**
     * @param 
     * function �õ�/����
     * return void
     */
    public void setUserID(long l)
    {
        this.UserID = l;
    }

    /**
     * @param 
     * function �õ�/����
     * return long
     */
    public long getStatusID()
    {
        return StatusID;
    }

    /**
     * @param 
     * function �õ�/����
     * return void
     */
    public void setStatusID(long l)
    {
        this.StatusID = l;
    }

    /**
     * function �õ�/�������ŷſ�֪ͨ�������ύ-����
     * return long
     */
    public long getYTPaySubmitCount()
    {
        return YTPaySubmitCount;
    }

    /**
     * @param l
     * function �õ�/�������ŷſ�֪ͨ�������ύ-����
     * return void
     */
    public void setYTPaySubmitCount(long l)
    {
        this.YTPaySubmitCount = l;
    }

    /**
     * function �õ�/�����������֪ͨ�����������-����
     * return long
     */
    public long getYTPayForCheckCount()
    {
        return YTPayForCheckCount;
    }

    /**
     * @param l
     * function �õ�/�����������֪ͨ�����������-����
     * return void
     */
    public void setYTPayForCheckCount(long l)
    {
        this.YTPayForCheckCount = l;
    }

    /**
     * function �õ�/�����������֪ͨ�������ύ-����
     * return long
     */
    public long getYTDrawNoticeSubmitCount()
    {
        return YTDrawNoticeSubmitCount;
    }

    /**
     * @param l
     * function �õ�/�����������֪ͨ�������ύ-����
     * return void
     */
    public void setYTDrawNoticeSubmitCount(long l)
    {
        this.YTDrawNoticeSubmitCount = l;
    }

    /**
     * function �õ�/�����������֪ͨ�����������-����
     * return long
     */
    public long getYTDrawNoticeForCheckCount()
    {
        return YTDrawNoticeForCheckCount;
    }

    /**
     * @param l
     * function �õ�/�����������֪ͨ�����������-����
     * return void
     */
    public void setYTDrawNoticeForCheckCount(long l)
    {
        this.YTDrawNoticeForCheckCount = l;
    }

    /**
     * @param 
     * function �õ�/����
     * return long
     */
    public long getAttornmentApplyForCheckCount()
    {
        return AttornmentApplyForCheckCount;
    }

    /**
     * @param 
     * function �õ�/����
     * return void
     */
    public void setAttornmentApplyForCheckCount(long l)
    {
        AttornmentApplyForCheckCount = l;
    }

    /**
     * @param 
     * function �õ�/����
     * return long
     */
    public long getAttornmentApplySaveCount()
    {
        return AttornmentApplySaveCount;
    }

    /**
     * @param 
     * function �õ�/����
     * return void
     */
    public void setAttornmentApplySaveCount(long l)
    {
        AttornmentApplySaveCount = l;
    }

    /**
     * @param 
     * function �õ�/����
     * return long
     */
    public long getAttornmentApplySubmitCount()
    {
        return AttornmentApplySubmitCount;
    }

    /**
     * @param 
     * function �õ�/����
     * return void
     */
    public void setAttornmentApplySubmitCount(long l)
    {
        AttornmentApplySubmitCount = l;
    }

	/**
	 * @return
	 */
	public long getRepurchaseCredenceForCheckCount()
	{
		return RepurchaseCredenceForCheckCount;
	}

	/**
	 * @return
	 */
	public long getRepurchaseCredenceSubmitCount()
	{
		return RepurchaseCredenceSubmitCount;
	}

	/**
	 * @return
	 */
	public long getTransDiscountApplyForCheckCount()
	{
		return TransDiscountApplyForCheckCount;
	}

	/**
	 * @return
	 */
	public long getTransDiscountApplySaveCount()
	{
		return TransDiscountApplySaveCount;
	}

	/**
	 * @return
	 */
	public long getTransDiscountApplySubmitCount()
	{
		return TransDiscountApplySubmitCount;
	}

	/**
	 * @return
	 */
	public long getTransDiscountCredenceForCheckCount()
	{
		return TransDiscountCredenceForCheckCount;
	}

	/**
	 * @return
	 */
	public long getTransDiscountCredenceSubmitCount()
	{
		return TransDiscountCredenceSubmitCount;
	}

	/**
	 * @param l
	 */
	public void setRepurchaseCredenceForCheckCount(long l)
	{
		RepurchaseCredenceForCheckCount = l;
	}

	/**
	 * @param l
	 */
	public void setRepurchaseCredenceSubmitCount(long l)
	{
		RepurchaseCredenceSubmitCount = l;
	}

	/**
	 * @param l
	 */
	public void setTransDiscountApplyForCheckCount(long l)
	{
		TransDiscountApplyForCheckCount = l;
	}

	/**
	 * @param l
	 */
	public void setTransDiscountApplySaveCount(long l)
	{
		TransDiscountApplySaveCount = l;
	}

	/**
	 * @param l
	 */
	public void setTransDiscountApplySubmitCount(long l)
	{
		TransDiscountApplySubmitCount = l;
	}

	/**
	 * @param l
	 */
	public void setTransDiscountCredenceForCheckCount(long l)
	{
		TransDiscountCredenceForCheckCount = l;
	}

	/**
	 * @param l
	 */
	public void setTransDiscountCredenceSubmitCount(long l)
	{
		TransDiscountCredenceSubmitCount = l;
	}

	/**
	 * @return
	 */
	public long getTransDiscountContractForCheckCount()
	{
		return TransDiscountContractForCheckCount;
	}

	/**
	 * @return
	 */
	public long getTransDiscountContractSaveCount()
	{
		return TransDiscountContractSaveCount;
	}

	/**
	 * @return
	 */
	public long getTransDiscountContractSubmitCount()
	{
		return TransDiscountContractSubmitCount;
	}

	/**
	 * @param l
	 */
	public void setTransDiscountContractForCheckCount(long l)
	{
		TransDiscountContractForCheckCount = l;
	}

	/**
	 * @param l
	 */
	public void setTransDiscountContractSaveCount(long l)
	{
		TransDiscountContractSaveCount = l;
	}

	/**
	 * @param l
	 */
	public void setTransDiscountContractSubmitCount(long l)
	{
		TransDiscountContractSubmitCount = l;
	}

	/**
	 * @return Returns the assureChargeNoticeForCheckCount.
	 */
	public long getAssureChargeNoticeForCheckCount()
	{
		return AssureChargeNoticeForCheckCount;
	}
	/**
	 * @param assureChargeNoticeForCheckCount The assureChargeNoticeForCheckCount to set.
	 */
	public void setAssureChargeNoticeForCheckCount(
			long assureChargeNoticeForCheckCount)
	{
		AssureChargeNoticeForCheckCount = assureChargeNoticeForCheckCount;
	}
	/**
	 * @return Returns the assureChargeNoticeSubmitCount.
	 */
	public long getAssureChargeNoticeSubmitCount()
	{
		return AssureChargeNoticeSubmitCount;
	}
	/**
	 * @param assureChargeNoticeSubmitCount The assureChargeNoticeSubmitCount to set.
	 */
	public void setAssureChargeNoticeSubmitCount(
			long assureChargeNoticeSubmitCount)
	{
		AssureChargeNoticeSubmitCount = assureChargeNoticeSubmitCount;
	}
	/**
	 * @return Returns the assureManagementNoticeForCheckCount.
	 */
	public long getAssureManagementNoticeForCheckCount()
	{
		return AssureManagementNoticeForCheckCount;
	}
	/**
	 * @param assureManagementNoticeForCheckCount The assureManagementNoticeForCheckCount to set.
	 */
	public void setAssureManagementNoticeForCheckCount(
			long assureManagementNoticeForCheckCount)
	{
		AssureManagementNoticeForCheckCount = assureManagementNoticeForCheckCount;
	}
	/**
	 * @return Returns the assureManagementNoticeSubmitCount.
	 */
	public long getAssureManagementNoticeSubmitCount()
	{
		return AssureManagementNoticeSubmitCount;
	}
	/**
	 * @param assureManagementNoticeSubmitCount The assureManagementNoticeSubmitCount to set.
	 */
	public void setAssureManagementNoticeSubmitCount(
			long assureManagementNoticeSubmitCount)
	{
		AssureManagementNoticeSubmitCount = assureManagementNoticeSubmitCount;
	}
}