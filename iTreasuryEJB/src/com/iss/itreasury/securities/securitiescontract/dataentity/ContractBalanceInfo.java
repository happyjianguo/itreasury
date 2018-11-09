/*
 * Created on 2004-6-24
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.securities.securitiescontract.dataentity;

import java.sql.Timestamp;

import com.iss.itreasury.securities.util.SECBaseDataEntity;
/**
 * @author gdzhao
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class ContractBalanceInfo extends SECBaseDataEntity
{
	private long id = -1;
	private double totalReceivedAmount = 0;
	private double totalPaiedAmount = 0;
	private double balance = 0;
	private Timestamp date = null;
	
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
	public double getBalance()
	{
		return balance;
	}

	/**
	 * @return
	 */
	public double getTotalPaiedAmount()
	{
		return totalPaiedAmount;
	}

	/**
	 * @return
	 */
	public double getTotalReceivedAmount()
	{
		return totalReceivedAmount;
	}

	/**
	 * @param d
	 */
	public void setBalance(double d)
	{
		balance = d;
	}

	/**
	 * @param d
	 */
	public void setTotalPaiedAmount(double d)
	{
		totalPaiedAmount = d;
	}

	/**
	 * @param d
	 */
	public void setTotalReceivedAmount(double d)
	{
		totalReceivedAmount = d;
	}

    /**
     * @return Returns the date.
     */
    public Timestamp getDate()
    {
        return date;
    }
    /**
     * @param date The date to set.
     */
    public void setDate(Timestamp date)
    {
        this.date = date;
    }
}
