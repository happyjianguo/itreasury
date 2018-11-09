/*
 * Created on 2003-11-24
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.iss.itreasury.settlement.print.templateinfo;
/**
 * @author ruixie
 * 通知存款开户证实书
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ShowNoticeOpenInfo
{
	public String DepositBillNo = ""; //存单号
	public String TransNo = "";//交易编号
	public String DateOpenAccount = ""; //开户日
	public String CurrencyName = ""; //币种
	public String AccountName = ""; //户名
	public String AccountNo = ""; //账号
	public String ChineseAmount = ""; //大写金额 
	public String Amount = ""; //小写金额
	public String Type = ""; //品种
	public String StartInterestDate = ""; //起息日
	public String InputUserName = ""; //录入人
	public String CheckUserName = ""; //复核人
	public String DepartmentManager = ""; //部门经理
//	办事处
	private long OfficeID = -1;
//	币种
	private long CurrencyID = -1;
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
	public String getAmount()
	{
		return Amount;
	}
	/**
	 * @return
	 */
	public String getCheckUserName()
	{
		return CheckUserName;
	}
	/**
	 * @return
	 */
	public String getChineseAmount()
	{
		return ChineseAmount;
	}
	/**
	 * @return
	 */
	public String getCurrencyName()
	{
		return CurrencyName;
	}
	/**
	 * @return
	 */
	public String getDateOpenAccount()
	{
		return DateOpenAccount;
	}
	/**
	 * @return
	 */
	public String getDepositBillNo()
	{
		return DepositBillNo;
	}
	/**
	 * @return
	 */
	public String getInputUserName()
	{
		return InputUserName;
	}
	/**
	 * @return
	 */
	public String getStartInterestDate()
	{
		return StartInterestDate;
	}
	/**
	 * @return
	 */
	public String getType()
	{
		return Type;
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
	public void setAmount(String string)
	{
		Amount = string;
	}
	/**
	 * @param string
	 */
	public void setCheckUserName(String string)
	{
		CheckUserName = string;
	}
	/**
	 * @param string
	 */
	public void setChineseAmount(String string)
	{
		ChineseAmount = string;
	}
	/**
	 * @param string
	 */
	public void setCurrencyName(String string)
	{
		CurrencyName = string;
	}
	/**
	 * @param string
	 */
	public void setDateOpenAccount(String string)
	{
		DateOpenAccount = string;
	}
	/**
	 * @param string
	 */
	public void setDepositBillNo(String string)
	{
		DepositBillNo = string;
	}
	/**
	 * @param string
	 */
	public void setInputUserName(String string)
	{
		InputUserName = string;
	}
	/**
	 * @param string
	 */
	public void setStartInterestDate(String string)
	{
		StartInterestDate = string;
	}
	/**
	 * @param string
	 */
	public void setType(String string)
	{
		Type = string;
	}
	/**
	 * @return
	 */
	public String getDepartmentManager()
	{
		return DepartmentManager;
	}
	/**
	 * @param string
	 */
	public void setDepartmentManager(String string)
	{
		DepartmentManager = string;
	}
	/**
	 * Returns the transNo.
	 * @return String
	 */
	public String getTransNo()
	{
		return TransNo;
	}

	/**
	 * Sets the transNo.
	 * @param transNo The transNo to set
	 */
	public void setTransNo(String transNo)
	{
		TransNo = transNo;
	}

	/**
	 * @return Returns the currencyID.
	 */
	public long getCurrencyID()
	{
		return CurrencyID;
	}
	/**
	 * @param currencyID The currencyID to set.
	 */
	public void setCurrencyID(long currencyID)
	{
		CurrencyID = currencyID;
	}
	/**
	 * @return Returns the officeID.
	 */
	public long getOfficeID()
	{
		return OfficeID;
	}
	/**
	 * @param officeID The officeID to set.
	 */
	public void setOfficeID(long officeID)
	{
		OfficeID = officeID;
	}
}
