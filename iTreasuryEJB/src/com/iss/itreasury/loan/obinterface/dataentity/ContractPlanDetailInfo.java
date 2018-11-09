package com.iss.itreasury.loan.obinterface.dataentity;
import java.sql.Timestamp;
/**
 * @author gqzhang
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class ContractPlanDetailInfo implements java.io.Serializable
{
	/**
	* @see java.lang.Object#Object()
	*/
	public ContractPlanDetailInfo()
	{
	}
	private long lID = -1; //��������                   
	private long lPlanID = -1; //����ob_contractplan���е�ID 
	private Timestamp dtPlanDate = null; //ԭʼ�ƻ�����               
	private long lPayTypeID = -1; //��/����                    
	private double dAmount = 0.00; //���                       
	private String strType = ""; //����                       
	private Timestamp dtModifyDate = null; //��������                   
	private long lLastExtendID = -1; //��Ӧ��չ��ID               
	private long lLastOverDueID = -1; //��Ӧ����ID                 
	private long lLastVersionPlanID = -1; //��Ӧ����һ�汾�ļƻ���ϸID 
	
	/**
	 * Returns the amount.
	 * @return double
	 */
	public double getAmount()
	{
		return dAmount;
	}

	/**
	 * Returns the modifyDate.
	 * @return Timestamp
	 */
	public Timestamp getModifyDate()
	{
		return dtModifyDate;
	}

	/**
	 * Returns the planDate.
	 * @return Timestamp
	 */
	public Timestamp getPlanDate()
	{
		return dtPlanDate;
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
	 * Returns the lastExtendID.
	 * @return long
	 */
	public long getLastExtendID()
	{
		return lLastExtendID;
	}

	/**
	 * Returns the lastOverDueID.
	 * @return long
	 */
	public long getLastOverDueID()
	{
		return lLastOverDueID;
	}

	/**
	 * Returns the lastVersionPlanID.
	 * @return long
	 */
	public long getLastVersionPlanID()
	{
		return lLastVersionPlanID;
	}

	/**
	 * Returns the payTypeID.
	 * @return long
	 */
	public long getPayTypeID()
	{
		return lPayTypeID;
	}

	/**
	 * Returns the planID.
	 * @return long
	 */
	public long getPlanID()
	{
		return lPlanID;
	}

	/**
	 * Returns the type.
	 * @return String
	 */
	public String getType()
	{
		return strType;
	}

	/**
	 * Sets the amount.
	 * @param amount The amount to set
	 */
	public void setAmount(double amount)
	{
		dAmount = amount;
	}

	/**
	 * Sets the modifyDate.
	 * @param modifyDate The modifyDate to set
	 */
	public void setModifyDate(Timestamp modifyDate)
	{
		dtModifyDate = modifyDate;
	}

	/**
	 * Sets the planDate.
	 * @param planDate The planDate to set
	 */
	public void setPlanDate(Timestamp planDate)
	{
		dtPlanDate = planDate;
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
	 * Sets the lastExtendID.
	 * @param lastExtendID The lastExtendID to set
	 */
	public void setLastExtendID(long lastExtendID)
	{
		lLastExtendID = lastExtendID;
	}

	/**
	 * Sets the lastOverDueID.
	 * @param lastOverDueID The lastOverDueID to set
	 */
	public void setLastOverDueID(long lastOverDueID)
	{
		lLastOverDueID = lastOverDueID;
	}

	/**
	 * Sets the lastVersionPlanID.
	 * @param lastVersionPlanID The lastVersionPlanID to set
	 */
	public void setLastVersionPlanID(long lastVersionPlanID)
	{
		lLastVersionPlanID = lastVersionPlanID;
	}

	/**
	 * Sets the payTypeID.
	 * @param payTypeID The payTypeID to set
	 */
	public void setPayTypeID(long payTypeID)
	{
		lPayTypeID = payTypeID;
	}

	/**
	 * Sets the planID.
	 * @param planID The planID to set
	 */
	public void setPlanID(long planID)
	{
		lPlanID = planID;
	}

	/**
	 * Sets the type.
	 * @param type The type to set
	 */
	public void setType(String type)
	{
		strType = type;
	}

}