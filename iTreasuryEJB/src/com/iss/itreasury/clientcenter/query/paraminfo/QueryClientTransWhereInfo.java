/*
 * Created on 2004-2-6
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.iss.itreasury.clientcenter.query.paraminfo;

/**
 * @author ruixie
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class QueryClientTransWhereInfo
{
	public QueryClientTransWhereInfo()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	private long OfficeID = -1;
	private long CurrencyID = -1;
	private long ClientID = -1; //�ͻ�ID
	private String ClientCode = ""; //�ͻ����
	private long IsDepositOrLoan = -1; //1��Ϊ����˻����� 2��Ϊ�����˻�����
	/**
	 * @return
	 */
	public String getClientCode()
	{
		return ClientCode;
	}

	/**
	 * @return
	 */
	public long getClientID()
	{
		return ClientID;
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
	public long getOfficeID()
	{
		return OfficeID;
	}

	/**
	 * @param string
	 */
	public void setClientCode(String string)
	{
		ClientCode = string;
	}

	/**
	 * @param l
	 */
	public void setClientID(long l)
	{
		ClientID = l;
	}

	/**
	 * @param l
	 */
	public void setCurrencyID(long l)
	{
		CurrencyID = l;
	}

	/**
	 * @param l
	 */
	public void setOfficeID(long l)
	{
		OfficeID = l;
	}

	/**
	 * @return
	 */
	public long getIsDepositOrLoan()
	{
		return IsDepositOrLoan;
	}

	/**
	 * @param l
	 */
	public void setIsDepositOrLoan(long l)
	{
		IsDepositOrLoan = l;
	}

}
