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
public class PlanDetailInfo implements Serializable
{

	public static void main(String[] args)
	{
	}
	private long lID = -1;
	private long lLoanID = -1;
	private long lContractID = -1;
	private Timestamp tsPlanDate = null;
	private int nLoanOrRepay = -1;
	private double dAmount = 0;
	private String strType = "";
	private long lInputUserID = -1;
	private Timestamp tsInputDate = null;
	private long lExtendApplyID = -1;
	private long lOverdueApplyID = -1;
	private long lIsOverdue = -1;
	private long lUserTypeID = -1;
	private int nAddANewVersion = -1;
	private long lOfficeID = -1;
	private long        lastVersionPlanID=-1;
	private double 		interestAmount = 0.0;
    private double 		recognizanceAmount = 0.0;
    private long        planID=-1;
    private long        payTypeID=-1;
    private Timestamp   modifyDate=null;
	public Timestamp getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Timestamp modifyDate) {
		this.modifyDate = modifyDate;
	}

	public long getPayTypeID() {
		return payTypeID;
	}

	public void setPayTypeID(long payTypeID) {
		this.payTypeID = payTypeID;
	}

	public long getPlanID() {
		return planID;
	}

	public void setPlanID(long planID) {
		this.planID = planID;
	}

	/**
	 * Returns the amount.
	 * @return double
	 */
	public double getAmount()
	{
		return dAmount;
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
	 * Returns the extendApplyID.
	 * @return long
	 */
	public long getExtendApplyID()
	{
		return lExtendApplyID;
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
	 * Returns the isOverdue.
	 * @return long
	 */
	public long getIsOverdue()
	{
		return lIsOverdue;
	}

	/**
	 * Returns the loanID.
	 * @return long
	 */
	public long getLoanID()
	{
		return lLoanID;
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
	 * Returns the overdueApplyID.
	 * @return long
	 */
	public long getOverdueApplyID()
	{
		return lOverdueApplyID;
	}

	/**
	 * Returns the userTypeID.
	 * @return long
	 */
	public long getUserTypeID()
	{
		return lUserTypeID;
	}

	/**
	 * Returns the addANewVersion.
	 * @return int
	 */
	public int getAddANewVersion()
	{
		return nAddANewVersion;
	}

	/**
	 * Returns the loanOrRepay.
	 * @return int
	 */
	public int getLoanOrRepay()
	{
		return nLoanOrRepay;
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
	 * Returns the planDate.
	 * @return Timestamp
	 */
	public Timestamp getPlanDate()
	{
		return tsPlanDate;
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
	 * Sets the contractID.
	 * @param contractID The contractID to set
	 */
	public void setContractID(long contractID)
	{
		lContractID = contractID;
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
	 * Sets the isOverdue.
	 * @param isOverdue The isOverdue to set
	 */
	public void setIsOverdue(long isOverdue)
	{
		lIsOverdue = isOverdue;
	}

	/**
	 * Sets the loanID.
	 * @param loanID The loanID to set
	 */
	public void setLoanID(long loanID)
	{
		lLoanID = loanID;
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
	 * Sets the overdueApplyID.
	 * @param overdueApplyID The overdueApplyID to set
	 */
	public void setOverdueApplyID(long overdueApplyID)
	{
		lOverdueApplyID = overdueApplyID;
	}

	/**
	 * Sets the userTypeID.
	 * @param userTypeID The userTypeID to set
	 */
	public void setUserTypeID(long userTypeID)
	{
		lUserTypeID = userTypeID;
	}

	/**
	 * Sets the addANewVersion.
	 * @param addANewVersion The addANewVersion to set
	 */
	public void setAddANewVersion(int addANewVersion)
	{
		nAddANewVersion = addANewVersion;
	}

	/**
	 * Sets the loanOrRepay.
	 * @param loanOrRepay The loanOrRepay to set
	 */
	public void setLoanOrRepay(int loanOrRepay)
	{
		nLoanOrRepay = loanOrRepay;
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
	 * Sets the planDate.
	 * @param planDate The planDate to set
	 */
	public void setPlanDate(Timestamp planDate)
	{
		tsPlanDate = planDate;
	}

	public double getInterestAmount() {
		return interestAmount;
	}

	public void setInterestAmount(double interestAmount) {
		this.interestAmount = interestAmount;
	}

	public long getLastVersionPlanID() {
		return lastVersionPlanID;
	}

	public void setLastVersionPlanID(long lastVersionPlanID) {
		this.lastVersionPlanID = lastVersionPlanID;
	}

	public double getRecognizanceAmount() {
		return recognizanceAmount;
	}

	public void setRecognizanceAmount(double recognizanceAmount) {
		this.recognizanceAmount = recognizanceAmount;
	}

}
