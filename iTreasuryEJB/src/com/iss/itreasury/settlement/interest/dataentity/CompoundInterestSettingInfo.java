/*
 * Created on 2003-10-28
 *
 * InterestQueryResultInfo.java
 */
package com.iss.itreasury.settlement.interest.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;
/**
 * @author xrli
 *
 * 复利设置信息实体。 
 * 
 */
public class CompoundInterestSettingInfo implements Serializable
{
	//主信息
	private long ID = -1;                           //唯一标识	
	private long OfficeID = -1;                     //办事处标识
	private long CurrencyID = -1;                   //币种标识	
	private String SettingName = "";                //结息日期设置名称
	private Timestamp CompoundInterestDate = null;  //复利计算日期
	private long StatusID = -1;                    //定期账户ID
	
	

	/**
	 * @return
	 */
	public Timestamp getCompoundInterestDate()
	{
		return CompoundInterestDate;
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
	public long getID()
	{
		return ID;
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
	public String getSettingName()
	{
		return SettingName;
	}
	
	/**
	 * @param timestamp
	 */
	public void setCompoundInterestDate(Timestamp timestamp)
	{
		CompoundInterestDate = timestamp;
	}

	/**
	 * @param l
	 */
	public void setCurrencyID(long l)
	{
		CurrencyID = l;
	}

	/**
	 * @param l
	 */
	public void setID(long l)
	{
		ID = l;
	}

	/**
	 * @param l
	 */
	public void setOfficeID(long l)
	{
		OfficeID = l;
	}

	/**
	 * @param string
	 */
	public void setSettingName(String string)
	{
		SettingName = string;
	}

	/**
	 * @return
	 */
	public long getStatusID()
	{
		return StatusID;
	}

	/**
	 * @param l
	 */
	public void setStatusID(long l)
	{
		StatusID = l;
	}

}