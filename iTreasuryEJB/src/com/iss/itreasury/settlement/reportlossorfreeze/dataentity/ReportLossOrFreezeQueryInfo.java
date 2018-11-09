package com.iss.itreasury.settlement.reportlossorfreeze.dataentity;

import java.sql.Timestamp;

import com.iss.itreasury.settlement.base.SettlementBaseDataEntity;

/**
 * Title: iTreasury Description: Copyright: Copyright (c) 2003 Company:
 * iSoftStone
 * <p>Description:　挂失冻结业务查询条件类 </p>
 * @author jinchen
 * @version Date of Creation 2004-11-24
 */
public class ReportLossOrFreezeQueryInfo extends SettlementBaseDataEntity
{
    private long Id = -1;	//   Number ID
    private String TransNo = "";	//交易号
    private long TransActionType = -1;	//交易类型
    private long ClientId = -1;	//客户ID   
    private long AccountId = -1;	// 账户ID     
    private String OldDepositNo = "";	//旧存单号    
    
    //add by wjliu 2007年5月29日 添加币种ID和办事处ID
    private long officeId = -1;	// 机构号ID  
    private long currencyId = -1;	//币种ID
    /**
     * @return 返回 checkDate。
     */
    public Timestamp getCheckDate()
    {
        return CheckDate;
    }
    /**
     * @param checkDate 要设置的 checkDate。
     */
    public void setCheckDate(Timestamp checkDate)
    {
        CheckDate = checkDate;
    }
    /**
     * @return 返回 checkUserId。
     */
    public long getCheckUserId()
    {
        return CheckUserId;
    }
    /**
     * @param checkUserId 要设置的 checkUserId。
     */
    public void setCheckUserId(long checkUserId)
    {
        CheckUserId = checkUserId;
    }
    private String NewDepositNo = "";	//新存单号  
    private Timestamp EffectivedDate = null;	//生效日期
    private long FreezeTerm = -1;	//冻结期限
    private Timestamp FreezeEndDate = null;	//冻结终止日  
    private double FreezeAmount = -1;	//冻结金额
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
    private Timestamp startDate = null;
    private long Desc = 1;
	private long OrderField = 1;
    /**
     * @return 返回 desc。
     */
    public long getDesc()
    {
        return Desc;
    }
    /**
     * @param desc 要设置的 desc。
     */
    public void setDesc(long desc)
    {
        Desc = desc;
    }
    /**
     * @return 返回 orderField。
     */
    public long getOrderField()
    {
        return OrderField;
    }
    /**
     * @param orderField 要设置的 orderField。
     */
    public void setOrderField(long orderField)
    {
        OrderField = orderField;
    }
	
    /**
     * @return 返回 status。
     */
    public long getStatus()
    {
        return Status;
    }
    /**
     * @param status 要设置的 status。
     */
    public void setStatus(long status)
    {
        Status = status;
    }
    /**
     * @return 返回 transActionType。
     */
    public long getTransActionType()
    {
        return TransActionType;
    }
    /**
     * @param transActionType 要设置的 transActionType。
     */
    public void setTransActionType(long transActionType)
    {
        TransActionType = transActionType;
    }
  
    /**
     * @return 返回 clientId。
     */
    public long getClientId()
    {
        return ClientId;
    }
    /**
     * @param clientId 要设置的 clientId。
     */
    public void setClientId(long clientId)
    {
        ClientId = clientId;
    }
    /**
     * @return 返回 endDate。
     */
    public Timestamp getEndDate()
    {
        return endDate;
    }
    /**
     * @param endDate 要设置的 endDate。
     */
    public void setEndDate(Timestamp endDate)
    {
        this.endDate = endDate;
    }
    /**
     * @return 返回 id。
     */
    public long getId()
    {
        return Id;
    }
    /**
     * @param id 要设置的 id。
     */
    public void setId(long id)
    {
        Id = id;
    }
    /**
     * @return 返回 startDate。
     */
    public Timestamp getStartDate()
    {
        return startDate;
    }
    /**
     * @param startDate 要设置的 startDate。
     */
    public void setStartDate(Timestamp startDate)
    {
        this.startDate = startDate;
    }
    private Timestamp endDate = null;
    /**
     * @return 返回 inputUserId。
     */
    public long getInputUserId()
    {
        return InputUserId;
    }
    /**
     * @param inputUserId 要设置的 inputUserId。
     */
    public void setInputUserId(long inputUserId)
    {
        InputUserId = inputUserId;
    }
    /**
     * @return 返回 inputDate。
     */
    public Timestamp getInputDate()
    {
        return InputDate;
    }
    /**
     * @param inputDate 要设置的 inputDate。
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