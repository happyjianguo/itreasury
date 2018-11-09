/*
 * Created on 2005-4-14
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.settlement.setting.dataentity;

import com.iss.itreasury.settlement.base.SettlementBaseDataEntity;

/**
 * @author weilu
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class StandardAbstractInfo extends SettlementBaseDataEntity
{
	/**
	 * 
	 */
	private long nOfficeID = -1;
	private String sCode = "";
	private String sDesc = "";
	private long nAbstractTypeID = -1;
	private long nStatusID = -1;
	private long nDetailID = -1;
	
	public StandardAbstractInfo()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	
	
	/**
	 * @return Returns the nAbstractTypeID.
	 */
	public long getNAbstractTypeID()
	{
		return nAbstractTypeID;
	}
	/**
	 * @param abstractTypeID The nAbstractTypeID to set.
	 */
	public void setNAbstractTypeID(long abstractTypeID)
	{
		nAbstractTypeID = abstractTypeID;
		putUsedField("nAbstractTypeID", abstractTypeID);
	}
	/**
	 * @return Returns the nDetailID.
	 */
	public long getNDetailID()
	{
		return nDetailID;
	}
	/**
	 * @param detailID The nDetailID to set.
	 */
	public void setNDetailID(long detailID)
	{
		nDetailID = detailID;
		putUsedField("nDetailID", detailID);
	}
	/**
	 * @return Returns the nOfficeID.
	 */
	public long getNOfficeID()
	{
		return nOfficeID;
	}
	/**
	 * @param officeID The nOfficeID to set.
	 */
	public void setNOfficeID(long officeID)
	{
		nOfficeID = officeID;
		putUsedField("nOfficeID", officeID);
	}
	/**
	 * @return Returns the nStatusID.
	 */
	public long getNStatusID()
	{
		return nStatusID;
	}
	/**
	 * @param statusID The nStatusID to set.
	 */
	public void setNStatusID(long statusID)
	{
		nStatusID = statusID;
		putUsedField("nStatusID", statusID);
	}
	/**
	 * @return Returns the sCode.
	 */
	public String getSCode()
	{
		return sCode;
	}
	/**
	 * @param code The sCode to set.
	 */
	public void setSCode(String code)
	{
		sCode = code;
		putUsedField("sCode", code);
	}
	/**
	 * @return Returns the sDesc.
	 */
	public String getSDesc()
	{
		return sDesc;
	}
	/**
	 * @param desc The sDesc to set.
	 */
	public void setSDesc(String desc)
	{
		sDesc = desc;
		putUsedField("sDesc", desc);
	}
}
