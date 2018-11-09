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
public class ExtendDetailInfo implements java.io.Serializable
{
	/**
	 * @see java.lang.Object#Object()
	 */
	public ExtendDetailInfo()
	{
	}
	private long lID = -1; //����
	private long lPlanID = -1; //�ƻ�ID               
	private double dPlanBalance = 0.00; //�ƻ����             
	private double dExtendAmount = 0.00; //չ�ڽ��             
	private Timestamp dtExtendBeginDate = null; //չ����ʼ����         
	private Timestamp dtExtendEndDate = null; //չ�ڽ�������         
	private long lExtendIntervalNum = -1; //չ����ϸ�е�չ������ 
	private long lExtendApplyID = -1; //չ��ָ��ID 
	

	

	/**
	 * Returns the extendAmount.
	 * @return double
	 */
	public double getExtendAmount()
	{
		return dExtendAmount;
	}

	/**
	 * Returns the planBalance.
	 * @return double
	 */
	public double getPlanBalance()
	{
		return dPlanBalance;
	}

	/**
	 * Returns the extendBeginDate.
	 * @return Timestamp
	 */
	public Timestamp getExtendBeginDate()
	{
		return dtExtendBeginDate;
	}

	/**
	 * Returns the extendEndDate.
	 * @return Timestamp
	 */
	public Timestamp getExtendEndDate()
	{
		return dtExtendEndDate;
	}

	/**
	 * Returns the extendApplyID.
	 * @return long
	 */
	public long getExtendApplyID()
	{
		return lExtendApplyID;
	}

	/**
	 * Returns the extendIntervalNum.
	 * @return long
	 */
	public long getExtendIntervalNum()
	{
		return lExtendIntervalNum;
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
	 * Returns the planID.
	 * @return long
	 */
	public long getPlanID()
	{
		return lPlanID;
	}

	/**
	 * Sets the extendAmount.
	 * @param extendAmount The extendAmount to set
	 */
	public void setExtendAmount(double extendAmount)
	{
		dExtendAmount = extendAmount;
	}

	/**
	 * Sets the planBalance.
	 * @param planBalance The planBalance to set
	 */
	public void setPlanBalance(double planBalance)
	{
		dPlanBalance = planBalance;
	}

	/**
	 * Sets the extendBeginDate.
	 * @param extendBeginDate The extendBeginDate to set
	 */
	public void setExtendBeginDate(Timestamp extendBeginDate)
	{
		dtExtendBeginDate = extendBeginDate;
	}

	/**
	 * Sets the extendEndDate.
	 * @param extendEndDate The extendEndDate to set
	 */
	public void setExtendEndDate(Timestamp extendEndDate)
	{
		dtExtendEndDate = extendEndDate;
	}

	/**
	 * Sets the extendApplyID.
	 * @param extendApplyID The extendApplyID to set
	 */
	public void setExtendApplyID(long extendApplyID)
	{
		lExtendApplyID = extendApplyID;
	}

	/**
	 * Sets the extendIntervalNum.
	 * @param extendIntervalNum The extendIntervalNum to set
	 */
	public void setExtendIntervalNum(long extendIntervalNum)
	{
		lExtendIntervalNum = extendIntervalNum;
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
	 * Sets the planID.
	 * @param planID The planID to set
	 */
	public void setPlanID(long planID)
	{
		lPlanID = planID;
	}

}