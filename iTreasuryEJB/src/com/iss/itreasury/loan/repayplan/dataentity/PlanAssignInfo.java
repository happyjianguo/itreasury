package com.iss.itreasury.loan.repayplan.dataentity;

import java.io.Serializable;
import java.sql.*;
/**
 * @author yzhang
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class PlanAssignInfo implements Serializable
{

	public static void main(String[] args)
	{
	}


	private long lContractID = -1;
	private int nPayType = -1;
	private Timestamp tsLoanStartDate = null;
	private Timestamp tsLoanEndDate = null;
	private int nRepayType = -1;
	private Timestamp tsRepayStartDate = null;
	private Timestamp tsRepayEndDate = null;
	private Timestamp tsInputDate = null;
	private String strType = "";
	private long lUserType = -1;
	private long lUserID = -1;
	private long lOfficeID = -1;
	
	/**
	 * Returns the contractID.
	 * @return long
	 */
	public long getContractID()
	{
		return lContractID;
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
	 * Returns the userID.
	 * @return long
	 */
	public long getUserID()
	{
		return lUserID;
	}

	/**
	 * Returns the payType.
	 * @return int
	 */
	public int getPayType()
	{
		return nPayType;
	}

	/**
	 * Returns the repayType.
	 * @return int
	 */
	public int getRepayType()
	{
		return nRepayType;
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
	 * Returns the inputDate.
	 * @return Timestamp
	 */
	public Timestamp getInputDate()
	{
		return tsInputDate;
	}

	/**
	 * Returns the loanEndDate.
	 * @return Timestamp
	 */
	public Timestamp getLoanEndDate()
	{
		return tsLoanEndDate;
	}

	/**
	 * Returns the loanStartDate.
	 * @return Timestamp
	 */
	public Timestamp getLoanStartDate()
	{
		return tsLoanStartDate;
	}

	/**
	 * Returns the repayEndDate.
	 * @return Timestamp
	 */
	public Timestamp getRepayEndDate()
	{
		return tsRepayEndDate;
	}

	/**
	 * Returns the repayStartDate.
	 * @return Timestamp
	 */
	public Timestamp getRepayStartDate()
	{
		return tsRepayStartDate;
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
	 * Sets the officeID.
	 * @param officeID The officeID to set
	 */
	public void setOfficeID(long officeID)
	{
		lOfficeID = officeID;
	}

	/**
	 * Sets the userID.
	 * @param userID The userID to set
	 */
	public void setUserID(long userID)
	{
		lUserID = userID;
	}

	/**
	 * Sets the payType.
	 * @param payType The payType to set
	 */
	public void setPayType(int payType)
	{
		nPayType = payType;
	}

	/**
	 * Sets the repayType.
	 * @param repayType The repayType to set
	 */
	public void setRepayType(int repayType)
	{
		nRepayType = repayType;
	}

	/**
	 * Sets the type.
	 * @param type The type to set
	 */
	public void setType(String type)
	{
		strType = type;
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
	 * Sets the loanEndDate.
	 * @param loanEndDate The loanEndDate to set
	 */
	public void setLoanEndDate(Timestamp loanEndDate)
	{
		tsLoanEndDate = loanEndDate;
	}

	/**
	 * Sets the loanStartDate.
	 * @param loanStartDate The loanStartDate to set
	 */
	public void setLoanStartDate(Timestamp loanStartDate)
	{
		tsLoanStartDate = loanStartDate;
	}

	/**
	 * Sets the repayEndDate.
	 * @param repayEndDate The repayEndDate to set
	 */
	public void setRepayEndDate(Timestamp repayEndDate)
	{
		tsRepayEndDate = repayEndDate;
	}

	/**
	 * Sets the repayStartDate.
	 * @param repayStartDate The repayStartDate to set
	 */
	public void setRepayStartDate(Timestamp repayStartDate)
	{
		tsRepayStartDate = repayStartDate;
	}

	/**
	 * Returns the userType.
	 * @return long
	 */
	public long getUserType()
	{
		return lUserType;
	}

	/**
	 * Sets the userType.
	 * @param userType The userType to set
	 */
	public void setUserType(long userType)
	{
		lUserType = userType;
	}

}
