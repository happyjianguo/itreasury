package com.iss.itreasury.settlement.setting.dataentity;
import java.io.Serializable;

/**
 * @author gqfang
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class BatchCheckSetInfo implements Serializable
{
	private long ID = -1;
	private long nIsBatchCheck = -1;
	private long nTransactionTypeID = -1;

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
	public long getNIsBatchCheck()
	{
		return nIsBatchCheck;
	}

	/**
	 * @return
	 */
	public long getNTransactionTypeID()
	{
		return nTransactionTypeID;
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
	public void setNIsBatchCheck(long l)
	{
		nIsBatchCheck = l;
	}

	/**
	 * @param l
	 */
	public void setNTransactionTypeID(long l)
	{
		nTransactionTypeID = l;
	}

	

} 