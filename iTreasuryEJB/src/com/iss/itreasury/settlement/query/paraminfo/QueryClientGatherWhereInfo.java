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
public class QueryClientGatherWhereInfo implements Serializable
{

	public QueryClientGatherWhereInfo()
	{
		super();
		// TODO Auto-generated constructor stub
	}
	// 查询条件
	private long OfficeID = -1; // 办事处
	private long CurrencyID = -1; // 币种
	private String StartClientCode = ""; // 客户号-从
	private String EndClientCode = ""; // 客户号-到
	private long StartClientID = -1;
	private long EndClientID = -1;
	private String AccountType = ""; // 账户类型
	private Timestamp Date = null;//查询日期
	private long AccountStatusID = -1;//账户状态
	private long IsIncludeParent = -1;//包括母公司
	private long IsIncluedSub = -1;//包括子公司
	private long IsFilter = -1; // 是否滤空
	private long ldesc = 1;
	
	public long getLdesc() {
		return ldesc;
	}

	public void setLdesc(long ldesc) {
		this.ldesc = ldesc;
	}

	/**
	 * @return
	 */
	public long getAccountStatusID()
	{
		return AccountStatusID;
	}

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
	public String getEndClientCode()
	{
		return EndClientCode;
	}

	/**
	 * @return
	 */
	public long getIsFilter()
	{
		return IsFilter;
	}

	/**
	 * @return
	 */
	public long getIsIncludeParent()
	{
		return IsIncludeParent;
	}

	/**
	 * @return
	 */
	public long getIsIncluedSub()
	{
		return IsIncluedSub;
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
	public String getStartClientCode()
	{
		return StartClientCode;
	}

	/**
	 * @param l
	 */
	public void setAccountStatusID(long l)
	{
		AccountStatusID = l;
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
	 * @param string
	 */
	public void setEndClientCode(String string)
	{
		EndClientCode = string;
	}

	/**
	 * @param l
	 */
	public void setIsFilter(long l)
	{
		IsFilter = l;
	}

	/**
	 * @param l
	 */
	public void setIsIncludeParent(long l)
	{
		IsIncludeParent = l;
	}

	/**
	 * @param l
	 */
	public void setIsIncluedSub(long l)
	{
		IsIncluedSub = l;
	}

	/**
	 * @param l
	 */
	public void setOfficeID(long l)
	{
		OfficeID = l;
	}

	/**
	 * @param string
	 */
	public void setStartClientCode(String string)
	{
		StartClientCode = string;
	}

	/**
	 * @return
	 */
	public Timestamp getDate()
	{
		return Date;
	}

	/**
	 * @param timestamp
	 */
	public void setDate(Timestamp timestamp)
	{
		Date = timestamp;
	}

	/**
	 * @return
	 */
	public long getEndClientID()
	{
		return EndClientID;
	}

	/**
	 * @return
	 */
	public long getStartClientID()
	{
		return StartClientID;
	}

	/**
	 * @param l
	 */
	public void setEndClientID(long l)
	{
		EndClientID = l;
	}

	/**
	 * @param l
	 */
	public void setStartClientID(long l)
	{
		StartClientID = l;
	}

}
