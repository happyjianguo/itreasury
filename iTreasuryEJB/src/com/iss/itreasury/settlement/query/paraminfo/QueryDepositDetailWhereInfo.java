/*
 * Created on 2003-10-30
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.settlement.query.paraminfo;

import java.io.Serializable;
import java.sql.Timestamp;
/**
 * @author yiwang
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class QueryDepositDetailWhereInfo implements Serializable
{

	/**
	 * 
	 */
	public QueryDepositDetailWhereInfo()
	{
		super();
		// TODO Auto-generated constructor stub
	}
    private long OfficeID = -1;
    private long CurrencyID = -1;
    private String StartClientCode = "";//客户编号由
    private String EndClientCode = "";//客户编号到
	private Timestamp Date = null;//日期
    private String AccountType = "";//账户类型
    
    private long AccountStatusID = -1;//账户状态	
	private long IsFilter = -1; //是否滤空
	
    private long Desc = 1;
    private long OrderField = 1;



	/**
	 * @return
	 */
	public String getAccountType()
	{
		return AccountType;
	}

	/**
	 * @return
	 */
	public long getCurrencyID()
	{
		return CurrencyID;
	}

	/**
	 * @return
	 */
	public Timestamp getDate()
	{
		return Date;
	}

	/**
	 * @return
	 */
	public long getDesc()
	{
		return Desc;
	}

	/**
	 * @return
	 */
	public String getEndClientCode()
	{
		return EndClientCode;
	}

	/**
	 * @return
	 */
	public long getOfficeID()
	{
		return OfficeID;
	}

	/**
	 * @return
	 */
	public long getOrderField()
	{
		return OrderField;
	}

	/**
	 * @return
	 */
	public String getStartClientCode()
	{
		return StartClientCode;
	}

	/**
	 * @param string
	 */
	public void setAccountType(String string)
	{
		AccountType = string;
	}

	/**
	 * @param l
	 */
	public void setCurrencyID(long l)
	{
		CurrencyID = l;
	}

	/**
	 * @param timestamp
	 */
	public void setDate(Timestamp timestamp)
	{
		Date = timestamp;
	}

	/**
	 * @param l
	 */
	public void setDesc(long l)
	{
		Desc = l;
	}

	/**
	 * @param string
	 */
	public void setEndClientCode(String string)
	{
		EndClientCode = string;
	}

	/**
	 * @param l
	 */
	public void setOfficeID(long l)
	{
		OfficeID = l;
	}

	/**
	 * @param l
	 */
	public void setOrderField(long l)
	{
		OrderField = l;
	}

	/**
	 * @param string
	 */
	public void setStartClientCode(String string)
	{
		StartClientCode = string;
	}

    /**
     * @return Returns the accountStatusID.
     */
    public long getAccountStatusID()
    {
        return AccountStatusID;
    }
    /**
     * @param accountStatusID The accountStatusID to set.
     */
    public void setAccountStatusID(long accountStatusID)
    {
        AccountStatusID = accountStatusID;
    }
    /**
     * @return Returns the isFilter.
     */
    public long getIsFilter()
    {
        return IsFilter;
    }
    /**
     * @param isFilter The isFilter to set.
     */
    public void setIsFilter(long isFilter)
    {
        IsFilter = isFilter;
    }
}
