/*
 * Created on 2003-12-14
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.iss.itreasury.settlement.print.templateinfo;

/**
 * @author ruixie
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ShowAccountBalanceDetailInfo
{
	public String CurrencyName = "";
	public String BalanceType = "";
	public String AccountNo = "";
	public String AccountName = "";
	public String DepositNo = "";
	public String Date = "";
	public String Balance = "";
	public String AccountTypeName = "";
	private String Contractcode = "";//ºÏÍ¬±àºÅ
	private long nAccountGroupID = -1;

	public String getContractcode() {
		return Contractcode;
	}

	public void setContractcode(String contractcode) {
		Contractcode = contractcode;
	}

	public long getNAccountGroupID() {
		return nAccountGroupID;
	}

	public void setNAccountGroupID(long accountGroupID) {
		nAccountGroupID = accountGroupID;
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
	public String getBalance()
	{
		return Balance;
	}

	/**
	 * @return
	 */
	public String getDate()
	{
		return Date;
	}

	/**
	 * @return
	 */
	public String getDepositNo()
	{
		return DepositNo;
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
	public void setBalance(String string)
	{
		Balance = string;
	}

	/**
	 * @param string
	 */
	public void setDate(String string)
	{
		Date = string;
	}

	/**
	 * @param string
	 */
	public void setDepositNo(String string)
	{
		DepositNo = string;
	}

	/**
	 * @return
	 */
	public String getAccountTypeName()
	{
		return AccountTypeName;
	}

	/**
	 * @param string
	 */
	public void setAccountTypeName(String string)
	{
		AccountTypeName = string;
	}

	/**
	 * @return
	 */
	public String getCurrencyName()
	{
		return CurrencyName;
	}

	/**
	 * @param string
	 */
	public void setCurrencyName(String string)
	{
		CurrencyName = string;
	}

	/**
	 * @return
	 */
	public String getBalanceType()
	{
		return BalanceType;
	}

	/**
	 * @param string
	 */
	public void setBalanceType(String string)
	{
		BalanceType = string;
	}

}
