/*
 * Created on 2003-12-23
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.settlement.remind.resultinfo;

import java.sql.Timestamp;

/**
 * @author wlming
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class UncheckTransactionInfo implements java.io.Serializable
{
	private long ID = -1;//纪录id
	private String transNo = "";//交易号          
	private long transactionTypeID = -1;//业务类型
	private String payAccountNo = "";//付款方账户号
	private String receiveAccountNo = "";//收款方账户号
	private double amount = 0.0;//金额
	private Timestamp startInterestDate = null;//起息日
	private Timestamp executeDate = null;//执行日
	private String strAbstract = "";//摘要
	private long statusID = -1;//状态
	/**
	 * Returns the amount.
	 * @return double
	 */
	public double getAmount()
	{
		return amount;
	}

	/**
	 * Returns the executeDate.
	 * @return Timestamp
	 */
	public Timestamp getExecuteDate()
	{
		return executeDate;
	}

	/**
	 * Returns the iD.
	 * @return long
	 */
	public long getID()
	{
		return ID;
	}

	/**
	 * Returns the payAccountNo.
	 * @return String
	 */
	public String getPayAccountNo()
	{
		return payAccountNo;
	}

	/**
	 * Returns the receiveAccountNo.
	 * @return String
	 */
	public String getReceiveAccountNo()
	{
		return receiveAccountNo;
	}

	/**
	 * Returns the startInterestDate.
	 * @return Timestamp
	 */
	public Timestamp getStartInterestDate()
	{
		return startInterestDate;
	}

	/**
	 * Returns the statusID.
	 * @return long
	 */
	public long getStatusID()
	{
		return statusID;
	}

	/**
	 * Returns the a.
	 * @return String
	 */
	public String getAbstract()
	{
		return strAbstract;
	}

	/**
	 * Returns the transactionTypeID.
	 * @return long
	 */
	public long getTransactionTypeID()
	{
		return transactionTypeID;
	}

	/**
	 * Returns the transNo.
	 * @return String
	 */
	public String getTransNo()
	{
		return transNo;
	}

	/**
	 * Sets the amount.
	 * @param amount The amount to set
	 */
	public void setAmount(double amount)
	{
		this.amount = amount;
	}

	/**
	 * Sets the executeDate.
	 * @param executeDate The executeDate to set
	 */
	public void setExecuteDate(Timestamp executeDate)
	{
		this.executeDate = executeDate;
	}

	/**
	 * Sets the iD.
	 * @param iD The iD to set
	 */
	public void setID(long iD)
	{
		ID = iD;
	}

	/**
	 * Sets the payAccountNo.
	 * @param payAccountNo The payAccountNo to set
	 */
	public void setPayAccountNo(String payAccountNo)
	{
		this.payAccountNo = payAccountNo;
	}

	/**
	 * Sets the receiveAccountNo.
	 * @param receiveAccountNo The receiveAccountNo to set
	 */
	public void setReceiveAccountNo(String receiveAccountNo)
	{
		this.receiveAccountNo = receiveAccountNo;
	}

	/**
	 * Sets the startInterestDate.
	 * @param startInterestDate The startInterestDate to set
	 */
	public void setStartInterestDate(Timestamp startInterestDate)
	{
		this.startInterestDate = startInterestDate;
	}

	/**
	 * Sets the statusID.
	 * @param statusID The statusID to set
	 */
	public void setStatusID(long statusID)
	{
		this.statusID = statusID;
	}

	/**
	 * Sets the a.
	 * @param a The a to set
	 */
	public void setAbstract(String a)
	{
		strAbstract = a;
	}

	/**
	 * Sets the transactionTypeID.
	 * @param transactionTypeID The transactionTypeID to set
	 */
	public void setTransactionTypeID(long transactionTypeID)
	{
		this.transactionTypeID = transactionTypeID;
	}

	/**
	 * Sets the transNo.
	 * @param transNo The transNo to set
	 */
	public void setTransNo(String transNo)
	{
		this.transNo = transNo;
	}

}
