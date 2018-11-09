/*
 * Created on 2003-10-23
*/
package com.iss.itreasury.settlement.interest.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author yiwang
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class InterestRatePlanItemInfo implements Serializable
{
	private long ID = -1;
	private long interestRatePlanID  = -1;
	private long serialNo = -1;
	private long dayType = -1;
	private long dayCount = -1;
	private long balanceType = -1;
	private double balance = 0.0;
	private Timestamp dateStart = null;
	private Timestamp dateEnd = null;	
	private double interestRate = 0.0;
	private long interestRateID = -1;

	
	/**
	 * Returns the balance.
	 * @return double
	 */
	public double getBalance() {
		return balance;
	}

	/**
	 * Returns the balanceType.
	 * @return long
	 */
	public long getBalanceType() {
		return balanceType;
	}

	/**
	 * Returns the dateEnd.
	 * @return Timestamp
	 */
	public Timestamp getDateEnd() {
		return dateEnd;
	}

	/**
	 * Returns the dateStart.
	 * @return Timestamp
	 */
	public Timestamp getDateStart() {
		return dateStart;
	}

	/**
	 * Returns the dayCount.
	 * @return long
	 */
	public long getDayCount() {
		return dayCount;
	}

	/**
	 * Returns the dayType.
	 * @return long
	 */
	public long getDayType() {
		return dayType;
	}

	/**
	 * Returns the iD.
	 * @return long
	 */
	public long getID() {
		return ID;
	}

	/**
	 * Returns the interestRate.
	 * @return double
	 */
	public double getInterestRate() {
		return interestRate;
	}

	/**
	 * Returns the interestRatePlanID.
	 * @return long
	 */
	public long getInterestRatePlanID() {
		return interestRatePlanID;
	}

	/**
	 * Returns the serialNo.
	 * @return long
	 */
	public long getSerialNo() {
		return serialNo;
	}

	/**
	 * Sets the balance.
	 * @param balance The balance to set
	 */
	public void setBalance(double balance) {
		this.balance = balance;
	}

	/**
	 * Sets the balanceType.
	 * @param balanceType The balanceType to set
	 */
	public void setBalanceType(long balanceType) {
		this.balanceType = balanceType;
	}

	/**
	 * Sets the dateEnd.
	 * @param dateEnd The dateEnd to set
	 */
	public void setDateEnd(Timestamp dateEnd) {
		this.dateEnd = dateEnd;
	}

	/**
	 * Sets the dateStart.
	 * @param dateStart The dateStart to set
	 */
	public void setDateStart(Timestamp dateStart) {
		this.dateStart = dateStart;
	}

	/**
	 * Sets the dayCount.
	 * @param dayCount The dayCount to set
	 */
	public void setDayCount(long dayCount) {
		this.dayCount = dayCount;
	}

	/**
	 * Sets the dayType.
	 * @param dayType The dayType to set
	 */
	public void setDayType(long dayType) {
		this.dayType = dayType;
	}

	/**
	 * Sets the iD.
	 * @param iD The iD to set
	 */
	public void setID(long iD) {
		ID = iD;
	}

	/**
	 * Sets the interestRate.
	 * @param interestRate The interestRate to set
	 */
	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}

	/**
	 * Sets the interestRatePlanID.
	 * @param interestRatePlanID The interestRatePlanID to set
	 */
	public void setInterestRatePlanID(long interestRatePlanID) {
		this.interestRatePlanID = interestRatePlanID;
	}

	/**
	 * Sets the serialNo.
	 * @param serialNo The serialNo to set
	 */
	public void setSerialNo(long serialNo) {
		this.serialNo = serialNo;
	}

	/**
	 * Returns the interestRateID.
	 * @return long
	 */
	public long getInterestRateID() {
		return interestRateID;
	}

	/**
	 * Sets the interestRateID.
	 * @param interestRateID The interestRateID to set
	 */
	public void setInterestRateID(long interestRateID) {
		this.interestRateID = interestRateID;
	}

}
