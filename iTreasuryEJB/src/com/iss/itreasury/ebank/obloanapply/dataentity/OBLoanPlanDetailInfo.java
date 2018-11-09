/*
 * Created on 2003-12-30
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.ebank.obloanapply.dataentity;
import com.iss.itreasury.ebank.obdataentity.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author gdzhao
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class OBLoanPlanDetailInfo implements Serializable
{

	private long        ID=-1;
	private long        planID=-1;
	private Timestamp   planDate=null;
	private long        payTypeID=-1;
	private double      amount=0;
	private String      type="";
	private Timestamp   modifyDate=null;
	private long        lastExtendID=-1;
	private long        lastOverdueID=-1;
	private long        lastVersionPlanID=-1;
	private OBSecurityInfo sInfo=null;			//安全信息   
    
	/**
	 * @return
	 */
	public double getAmount()
	{
		return amount;
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
	public long getLastExtendID()
	{
		return lastExtendID;
	}

	/**
	 * @return
	 */
	public long getLastOverdueID()
	{
		return lastOverdueID;
	}

	/**
	 * @return
	 */
	public long getLastVersionPlanID()
	{
		return lastVersionPlanID;
	}

	/**
	 * @return
	 */
	public Timestamp getModifyDate()
	{
		return modifyDate;
	}

	/**
	 * @return
	 */
	public long getPayTypeID()
	{
		return payTypeID;
	}

	/**
	 * @return
	 */
	public Timestamp getPlanDate()
	{
		return planDate;
	}

	/**
	 * @return
	 */
	public long getPlanID()
	{
		return planID;
	}

	/**
	 * @return
	 */
	public String getType()
	{
		return type;
	}

	/**
	 * @param d
	 */
	public void setAmount(double d)
	{
		amount = d;
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
	public void setLastExtendID(long l)
	{
		lastExtendID = l;
	}

	/**
	 * @param l
	 */
	public void setLastOverdueID(long l)
	{
		lastOverdueID = l;
	}

	/**
	 * @param l
	 */
	public void setLastVersionPlanID(long l)
	{
		lastVersionPlanID = l;
	}

	/**
	 * @param timestamp
	 */
	public void setModifyDate(Timestamp timestamp)
	{
		modifyDate = timestamp;
	}

	/**
	 * @param l
	 */
	public void setPayTypeID(long l)
	{
		payTypeID = l;
	}

	/**
	 * @param timestamp
	 */
	public void setPlanDate(Timestamp timestamp)
	{
		planDate = timestamp;
	}

	/**
	 * @param l
	 */
	public void setPlanID(long l)
	{
		planID = l;
	}

	/**
	 * @param string
	 */
	public void setType(String string)
	{
		type = string;
	}

	/**
	 * @return
	 */
	public OBSecurityInfo getInfo()
	{
		return sInfo;
	}

	/**
	 * @param info
	 */
	public void setInfo(OBSecurityInfo info)
	{
		sInfo = info;
	}

}
