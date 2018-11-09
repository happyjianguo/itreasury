/*
 * Created on 2004-2-6
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.iss.itreasury.clientcenter.query.resultinfo;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author ruixie
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class QueryClientTransResultInfo
{
	public QueryClientTransResultInfo()
	{
		super();
		// TODO Auto-generated constructor stub
	}
	
	private long OfficeID = -1;
	private long CurrencyID = -1;
	private long ClientID = -1;//客户ID
	private String ClientCode = "";//客户编号
	private long AccountID = -1;//账户ID
	private String AccountNo = "";//账户号
	private long SubAccountID = -1;//子账户ID
	
	private long AccountTypeID = -1;//账户种类
	private String AccountType = "";
	private double AccountInterestRate = 0.0;//利率
	private Timestamp OpenDate = null;//开户日期
	private Timestamp FinishDate = null;//销户日期
	private long AccountStatusID = -1;//账户状态
	private String AccountStatus = "";
	private long IsNegotiate = -1;//是否协定存款
	private double LimitBalance  = 0.0;//最低余额限制
	private double NegotiateAmount = 0.0;//协定存款
	private double AccountBalance = 0.0;//账户余额
//	private double DepositAverageBalance = 0.0;//存款日均余额
	

	/**
	 * @return
	 */
	public double getAccountBalance()
	{
		return AccountBalance;
	}

	/**
	 * @return
	 */
	public long getAccountID()
	{
		return AccountID;
	}

	/**
	 * @return
	 */
	public String getAccountNo()
	{
		return AccountNo;
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
	public long getAccountTypeID()
	{
		return AccountTypeID;
	}

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
	public Timestamp getFinishDate()
	{
		return FinishDate;
	}

	/**
	 * @return
	 */
	public long getIsNegotiate()
	{
		return IsNegotiate;
	}

	/**
	 * @return
	 */
	public double getLimitBalance()
	{
		return LimitBalance;
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
	public Timestamp getOpenDate()
	{
		return OpenDate;
	}

	/**
	 * @return
	 */
	public long getSubAccountID()
	{
		return SubAccountID;
	}

	/**
	 * @param d
	 */
	public void setAccountBalance(double d)
	{
		AccountBalance = d;
	}

	/**
	 * @param l
	 */
	public void setAccountID(long l)
	{
		AccountID = l;
	}

	/**
	 * @param string
	 */
	public void setAccountNo(String string)
	{
		AccountNo = string;
	}

	/**
	 * @param l
	 */
	public void setAccountStatusID(long l)
	{
		AccountStatusID = l;
	}

	/**
	 * @param l
	 */
	public void setAccountTypeID(long l)
	{
		AccountTypeID = l;
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
	 * @param timestamp
	 */
	public void setFinishDate(Timestamp timestamp)
	{
		FinishDate = timestamp;
	}

	/**
	 * @param l
	 */
	public void setIsNegotiate(long l)
	{
		IsNegotiate = l;
	}

	/**
	 * @param d
	 */
	public void setLimitBalance(double d)
	{
		LimitBalance = d;
	}

	/**
	 * @param l
	 */
	public void setOfficeID(long l)
	{
		OfficeID = l;
	}

	/**
	 * @param timestamp
	 */
	public void setOpenDate(Timestamp timestamp)
	{
		OpenDate = timestamp;
	}

	/**
	 * @param l
	 */
	public void setSubAccountID(long l)
	{
		SubAccountID = l;
	}

	/**
	 * @return
	 */
	public String getAccountStatus()
	{
		return AccountStatus;
	}

	/**
	 * @return
	 */
	public String getAccountType()
	{
		return AccountType;
	}

	/**
	 * @param string
	 */
	public void setAccountStatus(String string)
	{
		AccountStatus = string;
	}

	/**
	 * @param string
	 */
	public void setAccountType(String string)
	{
		AccountType = string;
	}

	/**
	 * @return
	 */
	public double getAccountInterestRate()
	{
		return AccountInterestRate;
	}

	/**
	 * @param d
	 */
	public void setAccountInterestRate(double d)
	{
		AccountInterestRate = d;
	}

	/**
	 * @return Returns the negotiateAmount.
	 */
	public double getNegotiateAmount() {
		return NegotiateAmount;
	}
	/**
	 * @param negotiateAmount The negotiateAmount to set.
	 */
	public void setNegotiateAmount(double negotiateAmount) {
		NegotiateAmount = negotiateAmount;
	}
}
