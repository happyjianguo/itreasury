/*
 * Created on 2004-4-19
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.securities.securitiescontractplan.dataentity;

import com.iss.itreasury.securities.util.SECBaseDataEntity;
import java.sql.Timestamp;

/**
 * @author gdzhao
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class SecuritiesContractAutoPlanInfo extends SECBaseDataEntity{

	private long id = -1;
	private long contractID=-1;
	private long planID = -1;
	private long payType=-1;
	private Timestamp payStart=null;
	private Timestamp payEnd=null;
	private long repayType=-1;
	private Timestamp repayStart=null;
	private Timestamp repayEnd=null;
	private long settlementTypeID = -1;
	private Timestamp interestStart = null;
	private Timestamp interestEnd = null;
	
	
	/**
	 * @return
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param l
	 */
	public void setId(long l) {
		id = l;
	}

	/**
	 * @return
	 */
	public long getContractID()
	{
		return contractID;
	}

	/**
	 * @return
	 */
	public Timestamp getInterestEnd()
	{
		return interestEnd;
	}

	/**
	 * @return
	 */
	public Timestamp getInterestStart()
	{
		return interestStart;
	}

	/**
	 * @return
	 */
	public Timestamp getPayEnd()
	{
		return payEnd;
	}

	/**
	 * @return
	 */
	public Timestamp getPayStart()
	{
		return payStart;
	}

	/**
	 * @return
	 */
	public long getPayType()
	{
		return payType;
	}

	/**
	 * @return
	 */
	public Timestamp getRepayEnd()
	{
		return repayEnd;
	}

	/**
	 * @return
	 */
	public Timestamp getRepayStart()
	{
		return repayStart;
	}

	/**
	 * @return
	 */
	public long getRepayType()
	{
		return repayType;
	}

	/**
	 * @return
	 */
	public long getSettlementTypeID()
	{
		return settlementTypeID;
	}

	/**
	 * @param l
	 */
	public void setContractID(long l)
	{
		contractID = l;
	}

	/**
	 * @param timestamp
	 */
	public void setInterestEnd(Timestamp timestamp)
	{
		interestEnd = timestamp;
	}

	/**
	 * @param timestamp
	 */
	public void setInterestStart(Timestamp timestamp)
	{
		interestStart = timestamp;
	}

	/**
	 * @param timestamp
	 */
	public void setPayEnd(Timestamp timestamp)
	{
		payEnd = timestamp;
	}

	/**
	 * @param timestamp
	 */
	public void setPayStart(Timestamp timestamp)
	{
		payStart = timestamp;
	}

	/**
	 * @param l
	 */
	public void setPayType(long l)
	{
		payType = l;
	}

	/**
	 * @param timestamp
	 */
	public void setRepayEnd(Timestamp timestamp)
	{
		repayEnd = timestamp;
	}

	/**
	 * @param timestamp
	 */
	public void setRepayStart(Timestamp timestamp)
	{
		repayStart = timestamp;
	}

	/**
	 * @param l
	 */
	public void setRepayType(long l)
	{
		repayType = l;
	}

	/**
	 * @param l
	 */
	public void setSettlementTypeID(long l)
	{
		settlementTypeID = l;
	}

	/**
	 * @return
	 */
	public long getPlanID()
	{
		return planID;
	}

	/**
	 * @param l
	 */
	public void setPlanID(long l)
	{
		planID = l;
	}

}
