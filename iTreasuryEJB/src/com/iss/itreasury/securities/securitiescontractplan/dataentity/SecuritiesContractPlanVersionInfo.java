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
public class SecuritiesContractPlanVersionInfo extends SECBaseDataEntity{

	private long id = -1;
	private long applyID = -1;
	private long contractID = -1;
	private long planVersion = -1;
	private long inputUserID = -1;
	private Timestamp inputTime = null;
	private long isUsed = -1;
	private long statusID = -1;
	
	
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
		putUsedField("id", id);
	}

	/**
	 * @return
	 */
	public long getApplyID()
	{
		return applyID;
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
	public Timestamp getInputTime()
	{
		return inputTime;
	}

	/**
	 * @return
	 */
	public long getInputUserID()
	{
		return inputUserID;
	}

	/**
	 * @return
	 */
	public long getIsUsed()
	{
		return isUsed;
	}

	/**
	 * @return
	 */
	public long getPlanVersion()
	{
		return planVersion;
	}

	/**
	 * @return
	 */
	public long getStatusID()
	{
		return statusID;
	}

	/**
	 * @param l
	 */
	public void setApplyID(long l)
	{
		applyID = l;
		putUsedField("applyID", applyID);
	}

	/**
	 * @param l
	 */
	public void setContractID(long l)
	{
		contractID = l;
		putUsedField("contractID", contractID);
	}

	/**
	 * @param timestamp
	 */
	public void setInputTime(Timestamp timestamp)
	{
		inputTime = timestamp;
		putUsedField("inputTime", inputTime);
	}

	/**
	 * @param l
	 */
	public void setInputUserID(long l)
	{
		inputUserID = l;
		putUsedField("inputUserID", inputUserID);
	}

	/**
	 * @param l
	 */
	public void setIsUsed(long l)
	{
		isUsed = l;
		putUsedField("isUsed", isUsed);
	}

	/**
	 * @param l
	 */
	public void setPlanVersion(long l)
	{
		planVersion = l;
		putUsedField("planVersion", planVersion);
	}

	/**
	 * @param l
	 */
	public void setStatusID(long l)
	{
		statusID = l;
		putUsedField("statusID", statusID);
	}

}
