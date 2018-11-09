package com.iss.itreasury.settlement.reportlossorfreeze.dataentity;

import java.sql.Timestamp;

import com.iss.itreasury.settlement.base.SettlementBaseDataEntity;
import com.iss.itreasury.system.approval.dataentity.InutParameterInfo;
/**
 * Title: iTreasury Description: Copyright: Copyright (c) 2003 Company:
 * iSoftStone
 * <p>Description:　挂失冻结业务,对应db数据库中SETT_REPORTLOSSORFREEZE表 </p>
 * @author jinchen
 * @version Date of Creation 2004-11-24
 */
public class ReportLossOrFreezeInfo extends SettlementBaseDataEntity
{
    
//    Name                Type          Nullable Default Comments     
//    ------------------- ------------- -------- ------- ------------ 
//    ID                  NUMBER                                      
//    TRANSNO             VARCHAR2(30)  Y                交易号       
//    TRANSACTIONTYPE     NUMBER        Y                交易类型     
//    CLIENTID            NUMBER        Y                客户ID       
//    ACCOUNTID           NUMBER        Y                账户ID       
//    OLDDEPOSITNO        VARCHAR2(30)  Y                旧存单号     
//    NEWDEPOSITNO        VARCHAR2(30)  Y                新存单号     
//    EFFECTIVEDATE       DATE          Y                生效日期     
//    FREEZETERM          NUMBER        Y                冻结期限     
//    FREEZEENDDATE       DATE          Y                冻结终止日   
//    FREEZEAMOUNT        NUMBER(21,6)  Y                冻结金额     
//    SUBACCOUNTOLDSTATUS NUMBER        Y                子账户旧状态 
//    SUBACCOUNTNEWSTATUS NUMBER        Y                子账户新状态 
//    EXECUTEGOVERNMENT   VARCHAR2(100) Y                执行部门     
//    APPLYCOMPANY        VARCHAR2(100) Y                申请单位     
//    LAWWRITNO           VARCHAR2(100) Y                法律文书编号 
//    ABSTRACT            VARCHAR2(100) Y                摘要         
//    EXECUTEDATE         DATE          Y                执行日       
//    INPUTUSERID         NUMBER        Y                录入人       
//    INPUTDATE           DATE          Y                录入日期     
//    CHECKUSERID         NUMBER        Y                复核人       
//    CHECKDATE           DATE          Y                复核日期     
//    MODIFYDATE          DATE          Y                修改日期     
//    STATUS              NUMBER        Y                状态         
//    OFFICEID            NUMBER        Y                机构号ID     
//    CURRENCYID          NUMBER        Y                币种号       
// 	  FREEZETYPE          NUMBER        Y                冻结方式  
    
    private long Id = -1;	//   Number ID
    private String TransNo = "";	//交易号
    private long TransActionType = -1;	//交易类型
    private long ClientId = -1;	//客户ID 
    private long AccountId = -1;	// 账户ID     
    private String OldDepositNo = "";	//旧存单号     
    private String NewDepositNo = "";	//新存单号  
    private Timestamp EffectiveDate = null;	//生效日期
    private long FreezeTerm = 0;	//冻结期限
    private Timestamp FreezeEndDate = null;	//冻结终止日  
    private double FreezeAmount = 0;	//冻结金额
    private long SubAccountOldStatus = -1;	//子账户旧状态 
    private long SubAccountNewStatus = -1;	//子账户新状态
    private String ExecuteGovernment = "";	//执行部门 
    private String ApplyCompany = "";	//申请单位
    private String LawWritNo = "";	//法律文书编号 
    private String Abstract = "";	//摘要 
    private Timestamp ExecuteDate = null;	//执行日
    private long  InputUserId = -1;	//录入人ID
    private Timestamp InputDate = null;	//录入日期
    private long CheckUserId = -1;	//复核人ID	
    private Timestamp CheckDate = null;	//复核日期
    private Timestamp ModifyDate = null;	//复核日期
    private long Status = -1;	//状态
    private long OfficeId = -1;	// 机构号ID     
    private long CurrencyId = -1;	//币种号
    private String CheckUserName ="";	//复核人
    private String ClientCode = "";	//客户编号
    private String accountNo = "";	//账号号
    private String ClientName = "";	//客户名称
    private long FreezeType = -1;	//冻结方式
    private InutParameterInfo inutParameterInfo = null;
    /**
     * @return 返回 freezeType。
     */
    public long getFreezeType()
    {
        return FreezeType;
    }
    /**
     * @param freezeType 要设置的 freezeType。
     */
    public void setFreezeType(long freezeType)
    {
        FreezeType = freezeType;
        putUsedField("FreezeType", freezeType);
        
    }
    /**
     * @return 返回 clientName。
     */
    public String getClientName()
    {
        return ClientName;
    }
    /**
     * @param clientName 要设置的 clientName。
     */
    public void setClientName(String clientName)
    {
        ClientName = clientName;
    }
    /**
     * @return 返回 checkUserName。
     */
    public String getCheckUserName()
    {
        return CheckUserName;
    }
    /**
     * @param checkUserName 要设置的 checkUserName。
     */
    public void setCheckUserName(String checkUserName)
    {
        CheckUserName = checkUserName;
    }
    /**
     * @return 返回 clientCode。
     */
    public String getClientCode()
    {
        return ClientCode;
    }
    /**
     * @param clientCode 要设置的 clientCode。
     */
    public void setClientCode(String clientCode)
    {
        ClientCode = clientCode;
    }
    /**
     * @return 返回 currencyId。
     */
    public long getCurrencyId()
    {
        return CurrencyId;
    }
    /**
     * @param currencyId 要设置的 currencyId。
     */
    public void setCurrencyId(long currencyId)
    {
        CurrencyId = currencyId;
        putUsedField("CurrencyId", currencyId);
    }
    /**
     * @return 返回 lawWritNo。
     */
    public String getLawWritNo()
    {
        return LawWritNo;
    }
    /**
     * @param lawWritNo 要设置的 lawWritNo。
     */
    public void setLawWritNo(String lawWritNo)
    {
        LawWritNo = lawWritNo;
        putUsedField("LawWritNo", lawWritNo);
    }
    /**
     * @return 返回 officeId。
     */
    public long getOfficeId()
    {
        return OfficeId;
    }
    /**
     * @param officeId 要设置的 officeId。
     */
    public void setOfficeId(long officeId)
    {
        OfficeId = officeId;
        putUsedField("OfficeId", officeId);
    }
    /**
     * @return 返回 abstract。
     */
    public String getAbstract()
    {
        return Abstract;
    }
    /**
     * @return 返回 accountId。
     */
    public long getAccountId()
    {
        return AccountId;
    }
    /**
     * @return 返回 applyCompany。
     */
    public String getApplyCompany()
    {
        return ApplyCompany;
    }
    /**
     * @return 返回 checkDate。
     */
    public Timestamp getCheckDate()
    {
        return CheckDate;
    }
    /**
     * @return 返回 checkUserId。
     */
    public long getCheckUserId()
    {
        return CheckUserId;
    }
    /**
     * @return 返回 clientId。
     */
    public long getClientId()
    {
        return ClientId;
    }
    /**
     * @return 返回 effectivedDate。
     */
    public Timestamp getEffectiveDate()
    {
        return EffectiveDate;
    }
    /**
     * @return 返回 executeDate。
     */
    public Timestamp getExecuteDate()
    {
        return ExecuteDate;
    }
    /**
     * @return 返回 executeGovernment。
     */
    public String getExecuteGovernment()
    {
        return ExecuteGovernment;
    }
    /**
     * @return 返回 freezeAmount。
     */
    public double getFreezeAmount()
    {
        return FreezeAmount;
    }
    /**
     * @return 返回 freezeEndDate。
     */
    public Timestamp getFreezeEndDate()
    {
        return FreezeEndDate;
    }
    /**
     * @return 返回 freezeTerm。
     */
    public long getFreezeTerm()
    {
        return FreezeTerm;
    }
/**
 * @return 返回 id。
 */
public long getId()
{
    return Id;
}
    /**
     * @return 返回 inputDate。
     */
    public Timestamp getInputDate()
    {
        return InputDate;
    }
    /**
     * @return 返回 inputUserId。
     */
    public long getInputUserId()
    {
        return InputUserId;
    }
   
    /**
     * @return 返回 modifyDate。
     */
    public Timestamp getModifyDate()
    {
        return ModifyDate;
    }
    /**
     * @return 返回 newDepositNo。
     */
    public String getNewDepositNo()
    {
        return NewDepositNo;
    }
    /**
     * @return 返回 oldDepositNo。
     */
    public String getOldDepositNo()
    {
        return OldDepositNo;
    }
    /**
     * @return 返回 status。
     */
    public long getStatus()
    {
        return Status;
    }
    /**
     * @return 返回 subAccountNewStatus。
     */
    public long getSubAccountNewStatus()
    {
        return SubAccountNewStatus;
    }
    /**
     * @return 返回 subAccountOldStatus。
     */
    public long getSubAccountOldStatus()
    {
        return SubAccountOldStatus;
    }
    /**
     * @return 返回 transActionType。
     */
    public long getTransActionType()
    {
        return TransActionType;
    }
    /**
     * @return 返回 transNo。
     */
    public String getTransNo()
    {
        return TransNo;
    }
    /**
     * @param abstract1 要设置的 abstract。
     */
    public void setAbstract(String abstract1)
    {
        Abstract = abstract1;
        putUsedField("Abstract", abstract1);
    }
    /**
     * @param accountId 要设置的 accountId。
     */
    public void setAccountId(long accountId)
    {
        AccountId = accountId;
        putUsedField("AccountId", accountId);
    }
    /**
     * @param applyCompany 要设置的 applyCompany。
     */
    public void setApplyCompany(String applyCompany)
    {
        ApplyCompany = applyCompany;
        putUsedField("ApplyCompany", applyCompany);
    }
    /**
     * @param checkDate 要设置的 checkDate。
     */
    public void setCheckDate(Timestamp checkDate)
    {
        CheckDate = checkDate;
        putUsedField("CheckDate", checkDate);
    }
    /**
     * @param checkUserId 要设置的 checkUserId。
     */
    public void setCheckUserId(long checkUserId)
    {
        CheckUserId = checkUserId;
        putUsedField("CheckUserId", checkUserId);
    }
    /**
     * @param clientId 要设置的 clientId。
     */
    public void setClientId(long clientId)
    {
        ClientId = clientId;
        putUsedField("ClientId", clientId);
    }
    /**
     * @param effectivedDate 要设置的 effectivedDate。
     */
    public void setEffectiveDate(Timestamp effectiveDate)
    {
        EffectiveDate = effectiveDate;
        putUsedField("EffectiveDate", effectiveDate);
    }
    /**
     * @param executeDate 要设置的 executeDate。
     */
    public void setExecuteDate(Timestamp executeDate)
    {
        ExecuteDate = executeDate;
        putUsedField("ExecuteDate", executeDate);
    }
    /**
     * @param executeGovernment 要设置的 executeGovernment。
     */
    public void setExecuteGovernment(String executeGovernment)
    {
        ExecuteGovernment = executeGovernment;
        putUsedField("ExecuteGovernment", executeGovernment);
    }
    /**
     * @param freezeAmount 要设置的 freezeAmount。
     */
    public void setFreezeAmount(double freezeAmount)
    {
        FreezeAmount = freezeAmount;
        putUsedField("FreezeAmount", freezeAmount);
    }
    /**
     * @param freezeEndDate 要设置的 freezeEndDate。
     */
    public void setFreezeEndDate(Timestamp freezeEndDate)
    {
        FreezeEndDate = freezeEndDate;
        putUsedField("FreezeEndDate", freezeEndDate);
    }
    /**
     * @param freezeTerm 要设置的 freezeTerm。
     */
    public void setFreezeTerm(long freezeTerm)
    {
        FreezeTerm = freezeTerm;
        putUsedField("FreezeTerm", freezeTerm);
    }
	/**
	 * @param id 要设置的 id。
	 */
	public void setId(long id)
	{
	    Id = id;
	    putUsedField("Id", id);
	}
    /**
     * @param inputDate 要设置的 inputDate。
     */
    public void setInputDate(Timestamp inputDate)
    {
        InputDate = inputDate;
        putUsedField("InputDate", inputDate);
    }
    /**
     * @param inputUserId 要设置的 inputUserId。
     */
    public void setInputUserId(long inputUserId)
    {
        InputUserId = inputUserId;
        putUsedField("InputUserId", inputUserId);
    }
    /**
     * @param modifyDate 要设置的 modifyDate。
     */
    public void setModifyDate(Timestamp modifyDate)
    {
        ModifyDate = modifyDate;
        putUsedField("ModifyDate", modifyDate);
    }
    /**
     * @param newDepositNo 要设置的 newDepositNo。
     */
    public void setNewDepositNo(String newDepositNo)
    {
        NewDepositNo = newDepositNo;
        putUsedField("NewDepositNo", newDepositNo);
    }
    /**
     * @param oldDepositNo 要设置的 oldDepositNo。
     */
    public void setOldDepositNo(String oldDepositNo)
    {
        OldDepositNo = oldDepositNo;
        putUsedField("OldDepositNo", oldDepositNo);
    }
    /**
     * @param status 要设置的 status。
     */
    public void setStatus(long status)
    {
        Status = status;
        putUsedField("Status", status);
    }
    /**
     * @param subAccountNewStatus 要设置的 subAccountNewStatus。
     */
    public void setSubAccountNewStatus(long subAccountNewStatus)
    {
        SubAccountNewStatus = subAccountNewStatus;
        putUsedField("SubAccountNewStatus", subAccountNewStatus);
    }
    /**
     * @param subAccountOldStatus 要设置的 subAccountOldStatus。
     */
    public void setSubAccountOldStatus(long subAccountOldStatus)
    {
        SubAccountOldStatus = subAccountOldStatus;
        putUsedField("SubAccountOldStatus", subAccountOldStatus);
    }
    /**
     * @param transActionType 要设置的 transActionType。
     */
    public void setTransActionType(long transActionType)
    {
        TransActionType = transActionType;
        putUsedField("TransActionType", transActionType);
    }
    /**
     * @param transNo 要设置的 transNo。
     */
    public void setTransNo(String transNo)
    {
        TransNo = transNo;
        putUsedField("TransNo", transNo);
    }
    /**
     * @return 返回 accountNo。
     */
    public String getAccountNo()
    {
        return accountNo;
    }
    /**
     * @param accountNo 要设置的 accountNo。
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