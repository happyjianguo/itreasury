package com.iss.itreasury.ebank.obdiscountapply.dataentity;

import java.sql.Timestamp;

/**
 * @author gqzhang
 *贴现申请主要信息 To change this generated comment edit the template variable
 *"typecomment": Window>Preferences>Java>Templates. To enable and disable the
 *creation of type comments go to Window>Preferences>Java>Code Generation.
 */
public class DiscountMainInfo implements java.io.Serializable
{
	public DiscountMainInfo()
	{
		super();
	}
	private long lID = -1; //   贴现标识
	private long lClientID = -1; // 客户标识
	private long lCurrencyID = -1; // 币种
	private long lUserID = -1; //录入人标识
	private Timestamp tsDate = null; //录入时间
	private long lOfficeID = -1; // 办事处标识
	private long lStatusID = -1;// 状态
	/**
	 * Returns the clientID.
	 * @return long
	 */
	public long getClientID()
	{
		return lClientID;
	}

	/**
	 * Returns the currencyID.
	 * @return long
	 */
	public long getCurrencyID()
	{
		return lCurrencyID;
	}

	/**
	 * Returns the iD.
	 * @return long
	 */
	public long getID()
	{
		return lID;
	}

	/**
	 * Returns the officeID.
	 * @return long
	 */
	public long getOfficeID()
	{
		return lOfficeID;
	}

	/**
	 * Returns the userID.
	 * @return long
	 */
	public long getUserID()
	{
		return lUserID;
	}

	/**
	 * Returns the date.
	 * @return Timestamp
	 */
	public Timestamp getDate()
	{
		return tsDate;
	}

	/**
	 * Sets the clientID.
	 * @param clientID The clientID to set
	 */
	public void setClientID(long clientID)
	{
		lClientID = clientID;
	}

	/**
	 * Sets the currencyID.
	 * @param currencyID The currencyID to set
	 */
	public void setCurrencyID(long currencyID)
	{
		lCurrencyID = currencyID;
	}

	/**
	 * Sets the iD.
	 * @param iD The iD to set
	 */
	public void setID(long iD)
	{
		lID = iD;
	}

	/**
	 * Sets the officeID.
	 * @param officeID The officeID to set
	 */
	public void setOfficeID(long officeID)
	{
		lOfficeID = officeID;
	}

	/**
	 * Sets the userID.
	 * @param userID The userID to set
	 */
	public void setUserID(long userID)
	{
		lUserID = userID;
	}

	/**
	 * Sets the date.
	 * @param date The date to set
	 */
	public void setDate(Timestamp date)
	{
		tsDate = date;
	}

	/**
	 * Returns the statusID.
	 * @return long
	 */
	public long getStatusID()
	{
		return lStatusID;
	}

	/**
	 * Sets the statusID.
	 * @param statusID The statusID to set
	 */
	public void setStatusID(long statusID)
	{
		lStatusID = statusID;
	}

}	