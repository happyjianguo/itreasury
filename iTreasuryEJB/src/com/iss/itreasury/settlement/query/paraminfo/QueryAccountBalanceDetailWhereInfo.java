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
public class QueryAccountBalanceDetailWhereInfo implements Serializable
{

	public QueryAccountBalanceDetailWhereInfo()
	{
		super();
		// TODO Auto-generated constructor stub
	}
	// ��ѯ����
	private long OfficeID = -1; // ���´�
	private long CurrencyID = -1; // ����
	private String StartClientCode = ""; // �ͻ���-��
	private String EndClientCode = ""; // �ͻ���-��
	private long AccountID = -1;
	private long AccountTypeID = -1; // �˻�����
	private Timestamp Date = null;//��ѯ����

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
	public String getStartClientCode()
	{
		return StartClientCode;
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
	 * @param string
	 */
	public void setStartClientCode(String string)
	{
		StartClientCode = string;
	}

	/**
	 * @return
	 */
	public long getAccountID()
	{
		return AccountID;
	}

	/**
	 * @param l
	 */
	public void setAccountID(long l)
	{
		AccountID = l;
	}

	/**
	 * @return
	 */
	public long getAccountTypeID()
	{
		return AccountTypeID;
	}

	/**
	 * @param l
	 */
	public void setAccountTypeID(long l)
	{
		AccountTypeID = l;
	}

}
