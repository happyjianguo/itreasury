/*
 * Created on 2004-8-13
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.loan.report.dataentity;

import java.sql.Timestamp;
import com.iss.itreasury.util.ITreasuryBaseDataEntity;
/**
 * @author gdzhao
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class CreditReportCondition extends ITreasuryBaseDataEntity
{
	private long id = -1;
	private long startClientID = -1;
	private long endClientID = -1;
	private String clientName="";
	private long reportType = -1;
	private Timestamp startDate = null;
	private Timestamp endDate = null;
	private Timestamp stopDate = null;  
	private long currencyID=-1;
	private long officeID=-1;
	/**
	 * @return
	 */
	public long getId()
	{
		return id;
	}

	/**
	 * @param l
	 */
	public void setId(long l)
	{
		id = l;
	}

	/**
	 * @return
	 */
	public String getClientName()
	{
		return clientName;
	}

	/**
	 * @return
	 */
	public long getEndClientID()
	{
		return endClientID;
	}

	/**
	 * @return
	 */
	public Timestamp getEndDate()
	{
		return endDate;
	}

	/**
	 * @return
	 */
	public long getReportType()
	{
		return reportType;
	}

	/**
	 * @return
	 */
	public long getStartClientID()
	{
		return startClientID;
	}

	/**
	 * @return
	 */
	public Timestamp getStartDate()
	{
		return startDate;
	}

	/**
	 * @param string
	 */
	public void setClientName(String string)
	{
		clientName = string;
	}

	/**
	 * @param l
	 */
	public void setEndClientID(long l)
	{
		endClientID = l;
	}

	/**
	 * @param timestamp
	 */
	public void setEndDate(Timestamp timestamp)
	{
		endDate = timestamp;
	}

	/**
	 * @param l
	 */
	public void setReportType(long l)
	{
		reportType = l;
	}

	/**
	 * @param l
	 */
	public void setStartClientID(long l)
	{
		startClientID = l;
	}

	/**
	 * @param timestamp
	 */
	public void setStartDate(Timestamp timestamp)
	{
		startDate = timestamp;
	}

	/**
	 * @return
	 */
	public Timestamp getStopDate()
	{
		return stopDate;
	}

	/**
	 * @param timestamp
	 */
	public void setStopDate(Timestamp timestamp)
	{
		stopDate = timestamp;
	}

	/**
	 * @return
	 */
	public long getCurrencyID()
	{
		return currencyID;
	}

	/**
	 * @return
	 */
	public long getOfficeID()
	{
		return officeID;
	}

	/**
	 * @param l
	 */
	public void setCurrencyID(long l)
	{
		currencyID = l;
	}

	/**
	 * @param l
	 */
	public void setOfficeID(long l)
	{
		officeID = l;
	}

}
