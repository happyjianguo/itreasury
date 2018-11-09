package com.iss.itreasury.settlement.query.resultinfo;
import java.io.Serializable;
import java.sql.Timestamp;

public class QueryReckoningBillResultInfo implements Serializable
{
	long ReckoningTypeID = -1;           //清算类型ID
	Timestamp InputDate = null;          //录入日期
	String ExtBankNo = "";               //提入行号
	String ExtAccountNo = "";            //对方账户号
	String ExtClientName = "";           //对方账户名称
	long AccountID = -1;                 //对应内部账户ID
    String AccountNo = "";               //对应内部账户号  
	String ReckoningBillTypeDesc = "";   //凭证种类
	double Amount = 0.0;                 //金额
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
	public double getAmount()
	{
		return Amount;
	}

	/**
	 * @return
	 */
	public String getExtAccountNo()
	{
		return ExtAccountNo;
	}

	/**
	 * @return
	 */
	public String getExtBankNo()
	{
		return ExtBankNo;
	}

	/**
	 * @return
	 */
	public String getExtClientName()
	{
		return ExtClientName;
	}

	/**
	 * @return
	 */
	public Timestamp getInputDate()
	{
		return InputDate;
	}

	/**
	 * @return
	 */
	public String getReckoningBillTypeDesc()
	{
		return ReckoningBillTypeDesc;
	}

	/**
	 * @return
	 */
	public long getReckoningTypeID()
	{
		return ReckoningTypeID;
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
	 * @param d
	 */
	public void setAmount(double d)
	{
		Amount = d;
	}

	/**
	 * @param string
	 */
	public void setExtAccountNo(String string)
	{
		ExtAccountNo = string;
	}

	/**
	 * @param string
	 */
	public void setExtBankNo(String string)
	{
		ExtBankNo = string;
	}

	/**
	 * @param string
	 */
	public void setExtClientName(String string)
	{
		ExtClientName = string;
	}

	/**
	 * @param timestamp
	 */
	public void setInputDate(Timestamp timestamp)
	{
		InputDate = timestamp;
	}

	/**
	 * @param string
	 */
	public void setReckoningBillTypeDesc(String string)
	{
		ReckoningBillTypeDesc = string;
	}

	/**
	 * @param l
	 */
	public void setReckoningTypeID(long l)
	{
		ReckoningTypeID = l;
	}

}
