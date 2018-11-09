/*
 * Created on 2003-10-30
 * 
 * To change the template for this generated file go to Window - Preferences - Java - Code Generation - Code and
 * Comments
 */
package com.iss.itreasury.settlement.query.resultinfo;

import java.io.Serializable;
import java.sql.Timestamp;

import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.settlement.util.UtilOperation;
import com.iss.itreasury.util.IDate;

/**
 * @author rxie
 * 
 * To change the template for this generated type comment go to Window - Preferences - Java - Code Generation - Code
 * and Comments
 */
public class QueryDepositDetailResultInfo implements Serializable
{

	/**
	 *  
	 */
	public QueryDepositDetailResultInfo()
	{
		super();
		// TODO Auto-generated constructor stub
	}
	private long ClientID = -1;
	private String ClientCode = "";//客户编号
	private long AccountID = -1;
	private String AccountNo = "";
	private String AccountName = "";
	private String AccountType = "";
	private long AccountTypeID = -1;
	private String AccountTypeCode = "";
	private String AccountStatus = "";
	private Timestamp OpenDate = null;
	private String InterestRatePlanName = "";
	private double AccountCurrentBalance = 0.0;//账户的当前余额
	private double AccountHistoryBalance = 0.0;//账户的历史余额
	private double AccountBalance = 0.0;//查询中显示的账户余额

	/**
	 * @return
	 */
	public double getAccountCurrentBalance()
	{
		return AccountCurrentBalance;
	}

	/**
	 * @return
	 */
	public double getAccountHistoryBalance()
	{
		return AccountHistoryBalance;
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
	public String getAccountName()
	{
		return AccountName;
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
	public Timestamp getOpenDate()
	{
		return OpenDate;
	}

	/**
	 * @param d
	 */
	public void setAccountCurrentBalance(double d)
	{
		AccountCurrentBalance = d;
	}

	/**
	 * @param d
	 */
	public void setAccountHistoryBalance(double d)
	{
		AccountHistoryBalance = d;
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
	public void setAccountName(String string)
	{
		AccountName = string;
	}

	/**
	 * @param string
	 */
	public void setAccountNo(String string)
	{
		AccountNo = string;
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
	 * @param timestamp
	 */
	public void setOpenDate(Timestamp timestamp)
	{
		OpenDate = timestamp;
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
	public String getInterestRatePlanName()
	{
		return InterestRatePlanName;
	}

	/**
	 * @param string
	 */
	public void setInterestRatePlanName(String string)
	{
		InterestRatePlanName = string;
	}

	/**
	 * @return
	 */
	public double getAccountBalance()
	{
		return AccountBalance;
	}

	/**
	 * @param d
	 */
	public void setAccountBalance(double d)
	{
		AccountBalance = d;
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

	public String getAccountTypeCode() {
		return AccountTypeCode;
	}

	public void setAccountTypeCode(String accountTypeCode) {
		AccountTypeCode = accountTypeCode;
	}

}