package com.iss.itreasury.settlement.transcommission.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @name: TransCommissionConditionInfo
 * @description:手续费查询条件类
 * @author: gqfang
 * @create: 2005-8-25
 */
public class TransCommissionConditionInfo implements Serializable
{
    private long      officeId      = -1;  // 办事处标识

    private long      currencyId    = -1;  // 币种标识
    
    private long     clientIdFrom   = -1;  //客户Id 由
    
    private long     clientIdTo     =- 1;  //客户Id 到

    private long      accountIdFrom = -1;  // 账户Id 由

    private long      accountIdTo   = -1;  // 账户Id 到

    private Timestamp startDate     = null; // 开始日期

    private Timestamp endDate       = null; // 结束日期

    private long      desc          = -1;  // 升序降序

    private long      orderField    = -1;  // 排序字段

    private long      isClearNull   = -1;  // 是否滤空

    /**
     * @return Returns the accountIdFrom.
     */
    public long getAccountIdFrom()
    {
        return accountIdFrom;
    }

    /**
     * @param accountIdFrom The accountIdFrom to set.
     */
    public void setAccountIdFrom(long accountIdFrom)
    {
        this.accountIdFrom = accountIdFrom;
    }

    /**
     * @return Returns the accountIdTo.
     */
    public long getAccountIdTo()
    {
        return accountIdTo;
    }

    /**
     * @param accountIdTo The accountIdTo to set.
     */
    public void setAccountIdTo(long accountIdTo)
    {
        this.accountIdTo = accountIdTo;
    }

    /**
     * @return Returns the currencyId.
     */
    public long getCurrencyId()
    {
        return currencyId;
    }

    /**
     * @param currencyId The currencyId to set.
     */
    public void setCurrencyId(long currencyId)
    {
        this.currencyId = currencyId;
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
    }

    /**
     * @return Returns the officeId.
     */
    public long getOfficeId()
    {
        return officeId;
    }

    /**
     * @param officeId The officeId to set.
     */
    public void setOfficeId(long officeId)
    {
        this.officeId = officeId;
    }

    /**
     * @return Returns the startDate.
     */
    public Timestamp getStartDate()
    {
        return startDate;
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
     * @return Returns the isClearNull.
     */
    public long getIsClearNull()
    {
        return isClearNull;
    }

    /**
     * @param isClearNull The isClearNull to set.
     */
    public void setIsClearNull(long isClearNull)
    {
        this.isClearNull = isClearNull;
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
     * @param startDate The startDate to set.
     */
    public void setStartDate(Timestamp startDate)
    {
        this.startDate = startDate;
    }

	/**
	 * @return
	 */
	public long getClientIdFrom() 
	{
		return clientIdFrom;
	}

	/**
	 * @param clientIdFrom
	 */
	public void setClientIdFrom(long clientIdFrom) 
	{
		this.clientIdFrom = clientIdFrom;
	}

	/**
	 * @return
	 */
	public long getClientIdTo() 
	{
		return clientIdTo;
	}

	/**
	 * @param clientIdTo
	 */
	public void setClientIdTo(long clientIdTo) 
	{
		this.clientIdTo = clientIdTo;
	}
}
