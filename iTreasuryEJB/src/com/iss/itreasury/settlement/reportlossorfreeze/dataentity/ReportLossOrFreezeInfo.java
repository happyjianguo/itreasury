package com.iss.itreasury.settlement.reportlossorfreeze.dataentity;

import java.sql.Timestamp;

import com.iss.itreasury.settlement.base.SettlementBaseDataEntity;
import com.iss.itreasury.system.approval.dataentity.InutParameterInfo;
/**
 * Title: iTreasury Description: Copyright: Copyright (c) 2003 Company:
 * iSoftStone
 * <p>Description:����ʧ����ҵ��,��Ӧdb���ݿ���SETT_REPORTLOSSORFREEZE�� </p>
 * @author jinchen
 * @version Date of Creation 2004-11-24
 */
public class ReportLossOrFreezeInfo extends SettlementBaseDataEntity
{
    
//    Name                Type          Nullable Default Comments     
//    ------------------- ------------- -------- ------- ------------ 
//    ID                  NUMBER                                      
//    TRANSNO             VARCHAR2(30)  Y                ���׺�       
//    TRANSACTIONTYPE     NUMBER        Y                ��������     
//    CLIENTID            NUMBER        Y                �ͻ�ID       
//    ACCOUNTID           NUMBER        Y                �˻�ID       
//    OLDDEPOSITNO        VARCHAR2(30)  Y                �ɴ浥��     
//    NEWDEPOSITNO        VARCHAR2(30)  Y                �´浥��     
//    EFFECTIVEDATE       DATE          Y                ��Ч����     
//    FREEZETERM          NUMBER        Y                ��������     
//    FREEZEENDDATE       DATE          Y                ������ֹ��   
//    FREEZEAMOUNT        NUMBER(21,6)  Y                ������     
//    SUBACCOUNTOLDSTATUS NUMBER        Y                ���˻���״̬ 
//    SUBACCOUNTNEWSTATUS NUMBER        Y                ���˻���״̬ 
//    EXECUTEGOVERNMENT   VARCHAR2(100) Y                ִ�в���     
//    APPLYCOMPANY        VARCHAR2(100) Y                ���뵥λ     
//    LAWWRITNO           VARCHAR2(100) Y                ���������� 
//    ABSTRACT            VARCHAR2(100) Y                ժҪ         
//    EXECUTEDATE         DATE          Y                ִ����       
//    INPUTUSERID         NUMBER        Y                ¼����       
//    INPUTDATE           DATE          Y                ¼������     
//    CHECKUSERID         NUMBER        Y                ������       
//    CHECKDATE           DATE          Y                ��������     
//    MODIFYDATE          DATE          Y                �޸�����     
//    STATUS              NUMBER        Y                ״̬         
//    OFFICEID            NUMBER        Y                ������ID     
//    CURRENCYID          NUMBER        Y                ���ֺ�       
// 	  FREEZETYPE          NUMBER        Y                ���᷽ʽ  
    
    private long Id = -1;	//   Number ID
    private String TransNo = "";	//���׺�
    private long TransActionType = -1;	//��������
    private long ClientId = -1;	//�ͻ�ID 
    private long AccountId = -1;	// �˻�ID     
    private String OldDepositNo = "";	//�ɴ浥��     
    private String NewDepositNo = "";	//�´浥��  
    private Timestamp EffectiveDate = null;	//��Ч����
    private long FreezeTerm = 0;	//��������
    private Timestamp FreezeEndDate = null;	//������ֹ��  
    private double FreezeAmount = 0;	//������
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
    private String CheckUserName ="";	//������
    private String ClientCode = "";	//�ͻ����
    private String accountNo = "";	//�˺ź�
    private String ClientName = "";	//�ͻ�����
    private long FreezeType = -1;	//���᷽ʽ
    private InutParameterInfo inutParameterInfo = null;
    /**
     * @return ���� freezeType��
     */
    public long getFreezeType()
    {
        return FreezeType;
    }
    /**
     * @param freezeType Ҫ���õ� freezeType��
     */
    public void setFreezeType(long freezeType)
    {
        FreezeType = freezeType;
        putUsedField("FreezeType", freezeType);
        
    }
    /**
     * @return ���� clientName��
     */
    public String getClientName()
    {
        return ClientName;
    }
    /**
     * @param clientName Ҫ���õ� clientName��
     */
    public void setClientName(String clientName)
    {
        ClientName = clientName;
    }
    /**
     * @return ���� checkUserName��
     */
    public String getCheckUserName()
    {
        return CheckUserName;
    }
    /**
     * @param checkUserName Ҫ���õ� checkUserName��
     */
    public void setCheckUserName(String checkUserName)
    {
        CheckUserName = checkUserName;
    }
    /**
     * @return ���� clientCode��
     */
    public String getClientCode()
    {
        return ClientCode;
    }
    /**
     * @param clientCode Ҫ���õ� clientCode��
     */
    public void setClientCode(String clientCode)
    {
        ClientCode = clientCode;
    }
    /**
     * @return ���� currencyId��
     */
    public long getCurrencyId()
    {
        return CurrencyId;
    }
    /**
     * @param currencyId Ҫ���õ� currencyId��
     */
    public void setCurrencyId(long currencyId)
    {
        CurrencyId = currencyId;
        putUsedField("CurrencyId", currencyId);
    }
    /**
     * @return ���� lawWritNo��
     */
    public String getLawWritNo()
    {
        return LawWritNo;
    }
    /**
     * @param lawWritNo Ҫ���õ� lawWritNo��
     */
    public void setLawWritNo(String lawWritNo)
    {
        LawWritNo = lawWritNo;
        putUsedField("LawWritNo", lawWritNo);
    }
    /**
     * @return ���� officeId��
     */
    public long getOfficeId()
    {
        return OfficeId;
    }
    /**
     * @param officeId Ҫ���õ� officeId��
     */
    public void setOfficeId(long officeId)
    {
        OfficeId = officeId;
        putUsedField("OfficeId", officeId);
    }
    /**
     * @return ���� abstract��
     */
    public String getAbstract()
    {
        return Abstract;
    }
    /**
     * @return ���� accountId��
     */
    public long getAccountId()
    {
        return AccountId;
    }
    /**
     * @return ���� applyCompany��
     */
    public String getApplyCompany()
    {
        return ApplyCompany;
    }
    /**
     * @return ���� checkDate��
     */
    public Timestamp getCheckDate()
    {
        return CheckDate;
    }
    /**
     * @return ���� checkUserId��
     */
    public long getCheckUserId()
    {
        return CheckUserId;
    }
    /**
     * @return ���� clientId��
     */
    public long getClientId()
    {
        return ClientId;
    }
    /**
     * @return ���� effectivedDate��
     */
    public Timestamp getEffectiveDate()
    {
        return EffectiveDate;
    }
    /**
     * @return ���� executeDate��
     */
    public Timestamp getExecuteDate()
    {
        return ExecuteDate;
    }
    /**
     * @return ���� executeGovernment��
     */
    public String getExecuteGovernment()
    {
        return ExecuteGovernment;
    }
    /**
     * @return ���� freezeAmount��
     */
    public double getFreezeAmount()
    {
        return FreezeAmount;
    }
    /**
     * @return ���� freezeEndDate��
     */
    public Timestamp getFreezeEndDate()
    {
        return FreezeEndDate;
    }
    /**
     * @return ���� freezeTerm��
     */
    public long getFreezeTerm()
    {
        return FreezeTerm;
    }
/**
 * @return ���� id��
 */
public long getId()
{
    return Id;
}
    /**
     * @return ���� inputDate��
     */
    public Timestamp getInputDate()
    {
        return InputDate;
    }
    /**
     * @return ���� inputUserId��
     */
    public long getInputUserId()
    {
        return InputUserId;
    }
   
    /**
     * @return ���� modifyDate��
     */
    public Timestamp getModifyDate()
    {
        return ModifyDate;
    }
    /**
     * @return ���� newDepositNo��
     */
    public String getNewDepositNo()
    {
        return NewDepositNo;
    }
    /**
     * @return ���� oldDepositNo��
     */
    public String getOldDepositNo()
    {
        return OldDepositNo;
    }
    /**
     * @return ���� status��
     */
    public long getStatus()
    {
        return Status;
    }
    /**
     * @return ���� subAccountNewStatus��
     */
    public long getSubAccountNewStatus()
    {
        return SubAccountNewStatus;
    }
    /**
     * @return ���� subAccountOldStatus��
     */
    public long getSubAccountOldStatus()
    {
        return SubAccountOldStatus;
    }
    /**
     * @return ���� transActionType��
     */
    public long getTransActionType()
    {
        return TransActionType;
    }
    /**
     * @return ���� transNo��
     */
    public String getTransNo()
    {
        return TransNo;
    }
    /**
     * @param abstract1 Ҫ���õ� abstract��
     */
    public void setAbstract(String abstract1)
    {
        Abstract = abstract1;
        putUsedField("Abstract", abstract1);
    }
    /**
     * @param accountId Ҫ���õ� accountId��
     */
    public void setAccountId(long accountId)
    {
        AccountId = accountId;
        putUsedField("AccountId", accountId);
    }
    /**
     * @param applyCompany Ҫ���õ� applyCompany��
     */
    public void setApplyCompany(String applyCompany)
    {
        ApplyCompany = applyCompany;
        putUsedField("ApplyCompany", applyCompany);
    }
    /**
     * @param checkDate Ҫ���õ� checkDate��
     */
    public void setCheckDate(Timestamp checkDate)
    {
        CheckDate = checkDate;
        putUsedField("CheckDate", checkDate);
    }
    /**
     * @param checkUserId Ҫ���õ� checkUserId��
     */
    public void setCheckUserId(long checkUserId)
    {
        CheckUserId = checkUserId;
        putUsedField("CheckUserId", checkUserId);
    }
    /**
     * @param clientId Ҫ���õ� clientId��
     */
    public void setClientId(long clientId)
    {
        ClientId = clientId;
        putUsedField("ClientId", clientId);
    }
    /**
     * @param effectivedDate Ҫ���õ� effectivedDate��
     */
    public void setEffectiveDate(Timestamp effectiveDate)
    {
        EffectiveDate = effectiveDate;
        putUsedField("EffectiveDate", effectiveDate);
    }
    /**
     * @param executeDate Ҫ���õ� executeDate��
     */
    public void setExecuteDate(Timestamp executeDate)
    {
        ExecuteDate = executeDate;
        putUsedField("ExecuteDate", executeDate);
    }
    /**
     * @param executeGovernment Ҫ���õ� executeGovernment��
     */
    public void setExecuteGovernment(String executeGovernment)
    {
        ExecuteGovernment = executeGovernment;
        putUsedField("ExecuteGovernment", executeGovernment);
    }
    /**
     * @param freezeAmount Ҫ���õ� freezeAmount��
     */
    public void setFreezeAmount(double freezeAmount)
    {
        FreezeAmount = freezeAmount;
        putUsedField("FreezeAmount", freezeAmount);
    }
    /**
     * @param freezeEndDate Ҫ���õ� freezeEndDate��
     */
    public void setFreezeEndDate(Timestamp freezeEndDate)
    {
        FreezeEndDate = freezeEndDate;
        putUsedField("FreezeEndDate", freezeEndDate);
    }
    /**
     * @param freezeTerm Ҫ���õ� freezeTerm��
     */
    public void setFreezeTerm(long freezeTerm)
    {
        FreezeTerm = freezeTerm;
        putUsedField("FreezeTerm", freezeTerm);
    }
	/**
	 * @param id Ҫ���õ� id��
	 */
	public void setId(long id)
	{
	    Id = id;
	    putUsedField("Id", id);
	}
    /**
     * @param inputDate Ҫ���õ� inputDate��
     */
    public void setInputDate(Timestamp inputDate)
    {
        InputDate = inputDate;
        putUsedField("InputDate", inputDate);
    }
    /**
     * @param inputUserId Ҫ���õ� inputUserId��
     */
    public void setInputUserId(long inputUserId)
    {
        InputUserId = inputUserId;
        putUsedField("InputUserId", inputUserId);
    }
    /**
     * @param modifyDate Ҫ���õ� modifyDate��
     */
    public void setModifyDate(Timestamp modifyDate)
    {
        ModifyDate = modifyDate;
        putUsedField("ModifyDate", modifyDate);
    }
    /**
     * @param newDepositNo Ҫ���õ� newDepositNo��
     */
    public void setNewDepositNo(String newDepositNo)
    {
        NewDepositNo = newDepositNo;
        putUsedField("NewDepositNo", newDepositNo);
    }
    /**
     * @param oldDepositNo Ҫ���õ� oldDepositNo��
     */
    public void setOldDepositNo(String oldDepositNo)
    {
        OldDepositNo = oldDepositNo;
        putUsedField("OldDepositNo", oldDepositNo);
    }
    /**
     * @param status Ҫ���õ� status��
     */
    public void setStatus(long status)
    {
        Status = status;
        putUsedField("Status", status);
    }
    /**
     * @param subAccountNewStatus Ҫ���õ� subAccountNewStatus��
     */
    public void setSubAccountNewStatus(long subAccountNewStatus)
    {
        SubAccountNewStatus = subAccountNewStatus;
        putUsedField("SubAccountNewStatus", subAccountNewStatus);
    }
    /**
     * @param subAccountOldStatus Ҫ���õ� subAccountOldStatus��
     */
    public void setSubAccountOldStatus(long subAccountOldStatus)
    {
        SubAccountOldStatus = subAccountOldStatus;
        putUsedField("SubAccountOldStatus", subAccountOldStatus);
    }
    /**
     * @param transActionType Ҫ���õ� transActionType��
     */
    public void setTransActionType(long transActionType)
    {
        TransActionType = transActionType;
        putUsedField("TransActionType", transActionType);
    }
    /**
     * @param transNo Ҫ���õ� transNo��
     */
    public void setTransNo(String transNo)
    {
        TransNo = transNo;
        putUsedField("TransNo", transNo);
    }
    /**
     * @return ���� accountNo��
     */
    public String getAccountNo()
    {
        return accountNo;
    }
    /**
     * @param accountNo Ҫ���õ� accountNo��
     */
    public void setAccountNo(String accountNo) 
    {
        this.accountNo = accountNo;
    }
	public InutParameterInfo getInutParameterInfo() {
		return inutParameterInfo;
	}
	public void setInutParameterInfo(InutParameterInfo inutParameterInfo) {
		this.inutParameterInfo = inutParameterInfo;
	}
}