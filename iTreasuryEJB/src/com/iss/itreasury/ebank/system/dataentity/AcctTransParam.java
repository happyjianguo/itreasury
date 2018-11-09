package com.iss.itreasury.ebank.system.dataentity;

import java.util.Date;

import com.iss.itreasury.util.DataFormat;

/**
 * @name: AcctTransParam
 * @description:银行账户交易(当日/历史)查询条件
 * @author: gqfang
 * @create: 2005-6-15
 */
public class AcctTransParam 
{

    private long id                   = -1;  // 唯一标识

    private long clientId             = -1;  // 客户标识

    private long countryId            = -1;  // 国家Id

    private long oppContryId          = -1;  // 对方开户行所在国家标识

    private long bankId               = -1;  // 银行类型

    private long currencyType         = -1;  // 币种

    private long ownerType            = -1;  // 账户所有者类型

    private long inputOrOutput        = -1;  // 收支属性

    private long isDirectLink         = -1;  // 是否是直连账户 1：是，2：不是

    private long accountId            = -1;  // 本方账户Id

    private long accountPropertyOne   = -1;  // 账户属性一

    private long accountPropertyTwo   = -1;  // 账户属性二

    private long accountPropertyThree = -1;  // 账户属性三

    private Date transactionStartDate = null; // 查询日期起

    private Date transactionEndDate   = null; // 查询日期止
    
    private long   isDeletedByBank      = -1;        // 是否已经被银行删除

    private long   isUpdateTrans      = -1;        // 是否补录交易信息
    
    private long isToTurnIn = -1;//是否需要入账(手工补录交易信息时用)
    /**
     * 针对入账清单查询功能，增加查询字段
     * Edit by MXZhou, 2006-04-14
     */
	private long turnInSuccess			= -1;//是否需要入账
	private long turnResult=-1 ;             //入账结果
    private long desc                 = -1;  // 排序方式
    private long orderField           = -1;  // 排序字段
    private Date noticeStartDate=null;//通知时间起
    private Date noticeEndDate=null;//通知日期止
    
    private double amount = Double.NaN;

    public double getAmount()
	{
		return amount;
	}

	public void setAmount(double amount)
	{
		this.amount = amount;
	}

	/**
     * @return Returns the accountId.
     */
    public long getAccountId()
    {
        return accountId;
    }

    /**
     * @param accountId The accountId to set.
     */
    public void setAccountId(long accountId)
    {
        this.accountId = accountId;
    }

    /**
     * @return Returns the accountPropertyOne.
     */
    public long getAccountPropertyOne()
    {
        return accountPropertyOne;
    }

    /**
     * @param accountPropertyOne The accountPropertyOne to set.
     */
    public void setAccountPropertyOne(long accountPropertyOne)
    {
        this.accountPropertyOne = accountPropertyOne;
    }

    /**
     * @return Returns the accountPropertyThree.
     */
    public long getAccountPropertyThree()
    {
        return accountPropertyThree;
    }

    /**
     * @param accountPropertyThree The accountPropertyThree to set.
     */
    public void setAccountPropertyThree(long accountPropertyThree)
    {
        this.accountPropertyThree = accountPropertyThree;
    }

    /**
     * @return Returns the accountPropertyTwo.
     */
    public long getAccountPropertyTwo()
    {
        return accountPropertyTwo;
    }

    /**
     * @param accountPropertyTwo The accountPropertyTwo to set.
     */
    public void setAccountPropertyTwo(long accountPropertyTwo)
    {
        this.accountPropertyTwo = accountPropertyTwo;
    }

    /**
     * @return Returns the bankId.
     */
    public long getBankId()
    {
        return bankId;
    }

    /**
     * @param bankId The bankId to set.
     */
    public void setBankId(long bankId)
    {
        this.bankId = bankId;
    }

    /**
     * @return Returns the clientId.
     */
    public long getClientId()
    {
        return clientId;
    }

    /**
     * @param clientId The clientId to set.
     */
    public void setClientId(long clientId)
    {
        this.clientId = clientId;
    }

    /**
     * @return Returns the countryId.
     */
    public long getCountryId()
    {
        return countryId;
    }

    /**
     * @param countryId The countryId to set.
     */
    public void setCountryId(long countryId)
    {
        this.countryId = countryId;
    }

    /**
     * @return Returns the currencyType.
     */
    public long getCurrencyType()
    {
        return currencyType;
    }

    /**
     * @param currencyType The currencyType to set.
     */
    public void setCurrencyType(long currencyType)
    {
        this.currencyType = currencyType;
    }

    /**
     * @return Returns the desc.
     */
    public long getDesc()
    {
        return desc;
    }

    /**
     * @param desc The desc to set.
     */
    public void setDesc(long desc)
    {
        this.desc = desc;
    }

    /**
     * @return Returns the id.
     */
    public long getId()
    {
        return id;
    }

    /**
     * @param id The id to set.
     */
    public void setId(long id)
    {
        this.id = id;
    }

    /**
     * @return Returns the inputOrOutput.
     */
    public long getInputOrOutput()
    {
        return inputOrOutput;
    }

    /**
     * @param inputOrOutput The inputOrOutput to set.
     */
    public void setInputOrOutput(long inputOrOutput)
    {
        this.inputOrOutput = inputOrOutput;
    }

    /**
     * @return Returns the isDirectLink.
     */
    public long getIsDirectLink()
    {
        return isDirectLink;
    }

    /**
     * @param isDirectLink The isDirectLink to set.
     */
    public void setIsDirectLink(long isDirectLink)
    {
        this.isDirectLink = isDirectLink;
    }

    /**
     * @return Returns the oppContryId.
     */
    public long getOppContryId()
    {
        return oppContryId;
    }

    /**
     * @param oppContryId The oppContryId to set.
     */
    public void setOppContryId(long oppContryId)
    {
        this.oppContryId = oppContryId;
    }

    /**
     * @return Returns the orderField.
     */
    public long getOrderField()
    {
        return orderField;
    }

    /**
     * @param orderField The orderField to set.
     */
    public void setOrderField(long orderField)
    {
        this.orderField = orderField;
    }

    /**
     * @return Returns the ownerType.
     */
    public long getOwnerType()
    {
        return ownerType;
    }

    /**
     * @param ownerType The ownerType to set.
     */
    public void setOwnerType(long ownerType)
    {
        this.ownerType = ownerType;
    }

    /**
     * @return Returns the transactionEndDate.
     */
    public Date getTransactionEndDate()
    {
        return transactionEndDate;
    }

    /**
     * @param transactionEndDate The transactionEndDate to set.
     */
    public void setTransactionEndDate(Date transactionEndDate)
    {
        this.transactionEndDate = transactionEndDate;
    }
    /**
	 * @return Returns the accountByOpenDate.
	 */
	public String getTransactionEndDateString()
	{
		return "";
	}	
    /**
     * @return Returns the transactionStartDate.
     */
    /**
     * @return AcctTransParam.java Date 2005-6-15
     */
    public Date getTransactionStartDate()
    {
        return transactionStartDate;
    }

    /**
     * @param transactionStartDate The transactionStartDate to set.
     */
    public void setTransactionStartDate(Date transactionStartDate)
    {
        this.transactionStartDate = transactionStartDate;
    }
    /**
	 * @return Returns the accountByOpenDate.
	 */
	public String getTransactionStartDateString()
	{
		return "";
	}
	/**
	 * @return Returns the turnInSuccess.
	 */
	public long getTurnInSuccess()
	{
		return turnInSuccess;
	}

	/**
	 * @param turnInSuccess The turnInSuccess to set.
	 */
	public void setTurnInSuccess(long turnInSuccess)
	{
		this.turnInSuccess = turnInSuccess;
	}

	public Date getNoticeEndDate() {
		return noticeEndDate;
	}

	public void setNoticeEndDate(Date noticeEndDate) {
		this.noticeEndDate = noticeEndDate;
	}	
	public Date getNoticeStartDate() {
		return noticeStartDate;
	}
	
	public void setNoticeStartDate(Date noticeStartDate) {
		this.noticeStartDate = noticeStartDate;
	}	
	public long getIsDeletedByBank() {
		return isDeletedByBank;
	}

	public void setIsDeletedByBank(long isDeletedByBank) {
		this.isDeletedByBank = isDeletedByBank;
	}

	public long getTurnResult() {
		return turnResult;
	}

	public void setTurnResult(long turnResult) {
		this.turnResult = turnResult;
	}
	public void setNoticeEndDateString(String noticeEndDateString)
	{		
		try
		{
			if(noticeEndDateString!=null && !noticeEndDateString.equals(""))
			{
				setNoticeEndDate(DataFormat.parseDate(noticeEndDateString, DataFormat.FMT_DATE_YYYYMMDD));	
			}			
		} catch (Exception e)
		{
			e.printStackTrace();
		}	
	}
	public void setNoticeStartDateString(String noticeStartDateString)
	{
		try
		{
			if(noticeStartDateString!=null && !noticeStartDateString.equals(""))
			{
				setNoticeStartDate(DataFormat.parseDate(noticeStartDateString, DataFormat.FMT_DATE_YYYYMMDD));	
			}			
		} catch (Exception e)
		{
			e.printStackTrace();
		}	
	}
	public void setTransactionStartDateString(String startDateString)
	{
		try
		{
			if(startDateString!=null && !startDateString.equals(""))
			{
				setTransactionStartDate(DataFormat.parseDate(startDateString, DataFormat.FMT_DATE_YYYYMMDD));
			}			
		} catch (Exception e)
		{
			e.printStackTrace();
		}	
	}
	public void setTransactionEndDateString(String endDateString)
	{
		try
		{
			if(endDateString!=null && !endDateString.equals(""))
			{
				setTransactionEndDate(DataFormat.parseDate(endDateString, DataFormat.FMT_DATE_YYYYMMDD));	
			}			
		} catch (Exception e)
		{
			e.printStackTrace();
		}	
	}

	public long getIsUpdateTrans()
	{
		return isUpdateTrans;
	}

	public void setIsUpdateTrans(long isUpdateTrans)
	{
		this.isUpdateTrans = isUpdateTrans;
	}

	public long getIsToTurnIn()
	{
		return isToTurnIn;
	}

	public void setIsToTurnIn(long isToTurnIn)
	{
		this.isToTurnIn = isToTurnIn;
	}	
	
}