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
public class PlanModifyInfo extends SECBaseDataEntity{

	private long id = -1;
	private long contractID = -1;
	private long planID = -1;
	private long inputUserID = -1;
	private Timestamp inputDate = null;
	private long nextCheckUserID = -1;
	private long statusID = -1;
	private long nextCheckLevel = -1;
	
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
	public long getContractID()
	{
		return contractID;
	}

	/**
	 * @return
	 */
	public Timestamp getInputDate()
	{
		return inputDate;
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
	public long getNextCheckLevel()
	{
		return nextCheckLevel;
	}

	/**
	 * @return
	 */
	public long getNextCheckUserID()
	{
		return nextCheckUserID;
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
	public long getStatusID()
	{
		return statusID;
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
	public void setInputDate(Timestamp timestamp)
	{
		inputDate = timestamp;
		putUsedField("inputDate", inputDate);
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
	public void setNextCheckLevel(long l)
	{
		nextCheckLevel = l;
		putUsedField("nextCheckLevel", nextCheckLevel);
	}

	/**
	 * @param l
	 */
	public void setNextCheckUserID(long l)
	{
		nextCheckUserID = l;
		putUsedField("nextCheckUserID", nextCheckUserID);
	}

	/**
	 * @param l
	 */
	public void setPlanID(long l)
	{
		planID = l;
		putUsedField("planID", planID);
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
