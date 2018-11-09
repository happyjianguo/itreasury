package com.iss.itreasury.settlement.reportlossorfreeze.dataentity;

import java.sql.Timestamp;

import com.iss.itreasury.settlement.base.SettlementBaseDataEntity;

/**
 * Title: iTreasury Description: Copyright: Copyright (c) 2003 Company:
 * iSoftStone
 * <p>Description:����ʧ����ҵ���ѯ������ </p>
 * @author jinchen
 * @version Date of Creation 2004-11-24
 */
public class ReportLossOrFreezeQueryInfo extends SettlementBaseDataEntity
{
    private long Id = -1;	//   Number ID
    private String TransNo = "";	//���׺�
    private long TransActionType = -1;	//��������
    private long ClientId = -1;	//�ͻ�ID   
    private long AccountId = -1;	// �˻�ID     
    private String OldDepositNo = "";	//�ɴ浥��    
    
    //add by wjliu 2007��5��29�� ��ӱ���ID�Ͱ��´�ID
    private long officeId = -1;	// ������ID  
    private long currencyId = -1;	//����ID
    /**
     * @return ���� checkDate��
     */
    public Timestamp getCheckDate()
    {
        return CheckDate;
    }
    /**
     * @param checkDate Ҫ���õ� checkDate��
     */
    public void setCheckDate(Timestamp checkDate)
    {
        CheckDate = checkDate;
    }
    /**
     * @return ���� checkUserId��
     */
    public long getCheckUserId()
    {
        return CheckUserId;
    }
    /**
     * @param checkUserId Ҫ���õ� checkUserId��
     */
    public void setCheckUserId(long checkUserId)
    {
        CheckUserId = checkUserId;
    }
    private String NewDepositNo = "";	//�´浥��  
    private Timestamp EffectivedDate = null;	//��Ч����
    private long FreezeTerm = -1;	//��������
    private Timestamp FreezeEndDate = null;	//������ֹ��  
    private double FreezeAmount = -1;	//������
    private long SubAccountOldStatus = -1;	//���˻���״̬ 
    private long SubAccountNewStatus = -1;	//���˻���״̬
    private String ExecuteGovernment = "";	//ִ�в��� 
    private String ApplyCompany = "";	//���뵥λ
    private String LawWritNo = "";	//���������� 
    private String Abstract = "";	//ժҪ 
    private Timestamp ExecuteDate = null;	//ִ����
    private long  InputUserId = -1;	//¼����ID
    private Timestamp InputDate = null;	//¼������
    private long CheckUserId = -1;	//������ID	
    private Timestamp CheckDate = null;	//��������
    private Timestamp ModifyDate = null;	//��������
    private long Status = -1;	//״̬
    private long OfficeId = -1;	// ������ID     
    private long CurrencyId = -1;	//���ֺ�
    private Timestamp startDate = null;
    private long Desc = 1;
	private long OrderField = 1;
    /**
     * @return ���� desc��
     */
    public long getDesc()
    {
        return Desc;
    }
    /**
     * @param desc Ҫ���õ� desc��
     */
    public void setDesc(long desc)
    {
        Desc = desc;
    }
    /**
     * @return ���� orderField��
     */
    public long getOrderField()
    {
        return OrderField;
    }
    /**
     * @param orderField Ҫ���õ� orderField��
     */
    public void setOrderField(long orderField)
    {
        OrderField = orderField;
    }
	
    /**
     * @return ���� status��
     */
    public long getStatus()
    {
        return Status;
    }
    /**
     * @param status Ҫ���õ� status��
     */
    public void setStatus(long status)
    {
        Status = status;
    }
    /**
     * @return ���� transActionType��
     */
    public long getTransActionType()
    {
        return TransActionType;
    }
    /**
     * @param transActionType Ҫ���õ� transActionType��
     */
    public void setTransActionType(long transActionType)
    {
        TransActionType = transActionType;
    }
  
    /**
     * @return ���� clientId��
     */
    public long getClientId()
    {
        return ClientId;
    }
    /**
     * @param clientId Ҫ���õ� clientId��
     */
    public void setClientId(long clientId)
    {
        ClientId = clientId;
    }
    /**
     * @return ���� endDate��
     */
    public Timestamp getEndDate()
    {
        return endDate;
    }
    /**
     * @param endDate Ҫ���õ� endDate��
     */
    public void setEndDate(Timestamp endDate)
    {
        this.endDate = endDate;
    }
    /**
     * @return ���� id��
     */
    public long getId()
    {
        return Id;
    }
    /**
     * @param id Ҫ���õ� id��
     */
    public void setId(long id)
    {
        Id = id;
    }
    /**
     * @return ���� startDate��
     */
    public Timestamp getStartDate()
    {
        return startDate;
    }
    /**
     * @param startDate Ҫ���õ� startDate��
     */
    public void setStartDate(Timestamp startDate)
    {
        this.startDate = startDate;
    }
    private Timestamp endDate = null;
    /**
     * @return ���� inputUserId��
     */
    public long getInputUserId()
    {
        return InputUserId;
    }
    /**
     * @param inputUserId Ҫ���õ� inputUserId��
     */
    public void setInputUserId(long inputUserId)
    {
        InputUserId = inputUserId;
    }
    /**
     * @return ���� inputDate��
     */
    public Timestamp getInputDate()
    {
        return InputDate;
    }
    /**
     * @param inputDate Ҫ���õ� inputDate��
     */
    public void setInputDate(Timestamp inputDate)
    {
        InputDate = inputDate;
    }
	
	public long getCurrencyId()
	{
	
		return currencyId;
	}
	
	public long getOfficeId()
	{
	
		return officeId;
	}
	
	public void setCurrencyId(long currencyId)
	{
	
		this.currencyId = currencyId;
	}
	
	public void setOfficeId(long officeId)
	{
	
		this.officeId = officeId;
	}
}