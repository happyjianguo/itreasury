package com.iss.itreasury.settlement.setting.dataentity;

import java.io.Serializable;

/**
 * @author xqyu
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class EnterpriceTypeSetInfo implements Serializable
{
    private long ID = -1;         //PK     
    private long StatusID = -1;   //是否有效，0无效，1有效
    private String Name = "";     //企业类型名称
    private long nOfficeID = -1;	//办事处
    private long nCurrencyID = -1;	//币种
 
	/**
	 * Returns the iD.
	 * @return long
	 */
	public long getID()
	{
		return ID;
	}

	/**
	 * Returns the name.
	 * @return String
	 */
	public String getName()
	{
		return Name;
	}

	/**
	 * Returns the statusID.
	 * @return long
	 */
	public long getStatusID()
	{
		return StatusID;
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
	 * Sets the name.
	 * @param name The name to set
	 */
	public void setName(String name)
	{
		Name = name;
	}

	/**
	 * Sets the statusID.
	 * @param statusID The statusID to set
	 */
	public void setStatusID(long statusID)
	{
		StatusID = statusID;
	}

	/**
	 * @return Returns the nCurrencyID.
	 */
	public long getCurrencyID()
	{
		return nCurrencyID;
	}
	/**
	 * @param currencyID The nCurrencyID to set.
	 */
	public void setCurrencyID(long currencyID)
	{
		nCurrencyID = currencyID;
	}
	/**
	 * @return Returns the nOfficeID.
	 */
	public long getOfficeID()
	{
		return nOfficeID;
	}
	/**
	 * @param officeID The nOfficeID to set.
	 */
	public void setOfficeID(long officeID)
	{
		nOfficeID = officeID;
	}
}