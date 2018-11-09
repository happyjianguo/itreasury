package com.iss.itreasury.settlement.query.paraminfo;

import java.sql.Timestamp;

/**
 * @author rongyang
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class QueryStatementInfo
{
	public final static int REMITTYPE_IN = 1;//Èë£»
	public final static int REMITTYPE_OUT = 2;//³ö
	
	private Timestamp date = null;
	
	private long remitType = -1;

	/**
	 * Constructor for QueryClearAccountInfo.
	 */
	public QueryStatementInfo()
	{
		super();
	}

	/**
	 * Returns the date.
	 * @return Timestamp
	 */
	public Timestamp getDate()
	{
		return date;
	}

	/**
	 * Returns the remitType.
	 * @return long
	 */
	public long getRemitType()
	{
		return remitType;
	}

	/**
	 * Sets the date.
	 * @param date The date to set
	 */
	public void setDate(Timestamp date)
	{
		this.date = date;
	}

	/**
	 * Sets the remitType.
	 * @param remitType The remitType to set
	 */
	public void setRemitType(long remitType)
	{
		this.remitType = remitType;
	}

}
