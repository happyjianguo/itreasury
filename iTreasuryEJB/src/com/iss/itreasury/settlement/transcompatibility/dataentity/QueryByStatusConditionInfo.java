/*
 * Created on 2004-8-02
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.settlement.transcompatibility.dataentity;
import java.sql.Timestamp;
import com.iss.itreasury.settlement.base.SettlementBaseDataEntity;
/**
 * 兼容交易的按状态查询条件实体类： 1、所有变量都为Private,设置只能用setXXX方法，得到只能用getXXX方法。 2、包含变量、类型、默认 值、
 * 说 明
 * @author gqzhang
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class QueryByStatusConditionInfo extends SettlementBaseDataEntity
{
	private long lID = -1;
	private long lTransTypeID = -1;
	private long lOperationTypeID = -1;
	private long lOfficeID = -1;
	private long lCurrencyID = -1;
	private long lInputUserID = -1;
	private Timestamp tsExecuteDate = null;
	private long lCheckUserID = -1;
	private long[] lStatusIDs = null;
	private long lOrderByID = -1;
	private boolean bDesc = false;
	/**
	 * @see com.iss.itreasury.util.ITreasuryBaseDataEntity#getId()
	 */
	public long getId()
	{
		return -1;
	}
	/**
	 * @see com.iss.itreasury.util.ITreasuryBaseDataEntity#setId(long)
	 */
	public void setId(long Id)
	{
		lID = Id;
	}
	/**
	 * Returns the transTypeID.
	 * @return long
	 */
	public long getTransTypeID()
	{
		return lTransTypeID;
	}
	/**
	 * Sets the transTypeID.
	 * @param transTypeID The transTypeID to set
	 */
	public void setTransTypeID(long transTypeID)
	{
		lTransTypeID = transTypeID;
	}
	/**
	 * Returns the orderByID.
	 * @return long
	 */
	public long getOrderByID()
	{
		return lOrderByID;
	}
	/**
	 * Sets the orderByID.
	 * @param orderByID The orderByID to set
	 */
	public void setOrderByID(long orderByID)
	{
		lOrderByID = orderByID;
	}
	/**
	 * Returns the desc.
	 * @return boolean
	 */
	public boolean isDesc()
	{
		return bDesc;
	}
	/**
	 * Sets the desc.
	 * @param desc The desc to set
	 */
	public void setDesc(boolean desc)
	{
		bDesc = desc;
	}
	/**
	 * Returns the statusIDs.
	 * @return long[]
	 */
	public long[] getStatusIDs()
	{
		return lStatusIDs;
	}
	/**
	 * Sets the statusIDs.
	 * @param statusIDs The statusIDs to set
	 */
	public void setStatusIDs(long[] statusIDs)
	{
		lStatusIDs = statusIDs;
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
	 * Returns the officeID.
	 * @return long
	 */
	public long getOfficeID()
	{
		return lOfficeID;
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
	 * Sets the officeID.
	 * @param officeID The officeID to set
	 */
	public void setOfficeID(long officeID)
	{
		lOfficeID = officeID;
	}
	/**
	 * Returns the checkUserID.
	 * @return long
	 */
	public long getCheckUserID()
	{
		return lCheckUserID;
	}

	/**
	 * Returns the inputUserID.
	 * @return long
	 */
	public long getInputUserID()
	{
		return lInputUserID;
	}

	
	/**
	 * Sets the checkUserID.
	 * @param checkUserID The checkUserID to set
	 */
	public void setCheckUserID(long checkUserID)
	{
		lCheckUserID = checkUserID;
	}

	/**
	 * Sets the inputUserID.
	 * @param inputUserID The inputUserID to set
	 */
	public void setInputUserID(long inputUserID)
	{
		lInputUserID = inputUserID;
	}

	

	/**
	 * Returns the executeDate.
	 * @return Timestamp
	 */
	public Timestamp getExecuteDate()
	{
		return tsExecuteDate;
	}

	/**
	 * Sets the executeDate.
	 * @param executeDate The executeDate to set
	 */
	public void setExecuteDate(Timestamp executeDate)
	{
		tsExecuteDate = executeDate;
	}

	/**
	 * Returns the operationTypeID.
	 * @return long
	 */
	public long getOperationTypeID()
	{
		return lOperationTypeID;
	}

	/**
	 * Sets the operationTypeID.
	 * @param operationTypeID The operationTypeID to set
	 */
	public void setOperationTypeID(long operationTypeID)
	{
		lOperationTypeID = operationTypeID;
	}

}
