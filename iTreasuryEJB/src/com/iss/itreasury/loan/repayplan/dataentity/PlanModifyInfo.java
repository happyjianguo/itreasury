package com.iss.itreasury.loan.repayplan.dataentity;

import java.sql.*;

import com.iss.itreasury.system.approval.dataentity.InutParameterInfo;

/**
 * @author yzhang
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class PlanModifyInfo implements java.io.Serializable
{

	public static void main(String[] args)
	{
	}

	private long lID = -1;
	private long lContractID = -1;
	private long lPlanID = -1;
	private long lInputUserID = -1;
	private Timestamp tsInputDate = null;
	private long lNextCheckUserID = -1;
	private long lNextCheckUserLevel = -1;
	private long lStatusID = -1;

	private long lOfficeID = -1;
	private long lCurrencyID = -1;
	
	private long loanType = -1;  //贷款类型
	private long loanSubType = -1;//贷款子类
	
	private long planVersion; //计划版本号
	
	
	private long adjustID;//利率调整ID (融资租赁使用)
	private InutParameterInfo inutParameterInfo=null;
	
	
	public InutParameterInfo getInutParameterInfo()
	{
	
		return inutParameterInfo;
	}

	
	public void setInutParameterInfo(InutParameterInfo inutParameterInfo)
	{
	
		this.inutParameterInfo = inutParameterInfo;
	}

	/**
	 * Returns the contractID.
	 * @return long
	 */
	public long getContractID()
	{
		return lContractID;
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
	 * Returns the inputUserID.
	 * @return long
	 */
	public long getInputUserID()
	{
		return lInputUserID;
	}

	/**
	 * Returns the nextCheckUserID.
	 * @return long
	 */
	public long getNextCheckUserID()
	{
		return lNextCheckUserID;
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
	 * Returns the statusID.
	 * @return long
	 */
	public long getStatusID()
	{
		return lStatusID;
	}

	/**
	 * Returns the inputDate.
	 * @return Timestamp
	 */
	public Timestamp getInputDate()
	{
		return tsInputDate;
	}

	/**
	 * Sets the contractID.
	 * @param contractID The contractID to set
	 */
	public void setContractID(long contractID)
	{
		lContractID = contractID;
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
	 * Sets the inputUserID.
	 * @param inputUserID The inputUserID to set
	 */
	public void setInputUserID(long inputUserID)
	{
		lInputUserID = inputUserID;
	}

	/**
	 * Sets the nextCheckUserID.
	 * @param nextCheckUserID The nextCheckUserID to set
	 */
	public void setNextCheckUserID(long nextCheckUserID)
	{
		lNextCheckUserID = nextCheckUserID;
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
	 * Sets the statusID.
	 * @param statusID The statusID to set
	 */
	public void setStatusID(long statusID)
	{
		lStatusID = statusID;
	}

	/**
	 * Sets the inputDate.
	 * @param inputDate The inputDate to set
	 */
	public void setInputDate(Timestamp inputDate)
	{
		tsInputDate = inputDate;
	}

	/**
	 * Returns the currencyID.
	 * @return long
	 */
	public long getCurrencyID() {
		return lCurrencyID;
	}

	/**
	 * Returns the officeID.
	 * @return long
	 */
	public long getOfficeID() {
		return lOfficeID;
	}

	/**
	 * Sets the currencyID.
	 * @param currencyID The currencyID to set
	 */
	public void setCurrencyID(long currencyID) {
		lCurrencyID = currencyID;
	}

	/**
	 * Sets the officeID.
	 * @param officeID The officeID to set
	 */
	public void setOfficeID(long officeID) {
		lOfficeID = officeID;
	}
	/**
	 * Returns the lNextCheckUserLevel.
	 * @return long
	 */
	public long getNextCheckUserLevel() {
		return lNextCheckUserLevel;
	}

	/**
	 * Sets the lNextCheckUserLevel.
	 * @param lNextCheckUserLevel The lNextCheckUserLevel to set
	 */
	public void setNextCheckUserLevel(long lNextCheckUserLevel) {
		this.lNextCheckUserLevel = lNextCheckUserLevel;
	}


	
	public long getLoanSubType()
	{
	
		return loanSubType;
	}


	
	public long getLoanType()
	{
	
		return loanType;
	}


	
	public void setLoanSubType(long loanSubType)
	{
	
		this.loanSubType = loanSubType;
	}


	
	public void setLoanType(long loanType)
	{
	
		this.loanType = loanType;
	}


	
	public long getPlanVersion()
	{
	
		return planVersion;
	}


	
	public void setPlanVersion(long planVersion)
	{
	
		this.planVersion = planVersion;
	}


	public long getAdjustID() {
		return adjustID;
	}


	public void setAdjustID(long adjustID) {
		this.adjustID = adjustID;
	}

	
}
