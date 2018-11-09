package com.iss.itreasury.settlement.interest.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Title:        		iTreasury
 * Description:         
 * Copyright:           Copyright (c) 2003
 * Company:             iSoftStone
 * @author             yehuang 
 * @version
 *  Date of Creation    2003-11-10
 */
public class PredrawInterestInfo implements Serializable {
	private Timestamp sDate = null;
	private Timestamp eDate = null;
	private long days = -1;
	private double balance = 0.0;
	private double rate = 0.0;
	private double predrawInterestInfo = 0.0; 
	/**
	 * Returns the balance.
	 * @return double
	 */
	public double getBalance() {
		return balance;
	}

	/**
	 * Returns the days.
	 * @return long
	 */
	public long getDays() {
		return days;
	}

	/**
	 * Returns the eDate.
	 * @return Timestamp
	 */
	public Timestamp getEDate() {
		return eDate;
	}

	/**
	 * Returns the predrawInterestInfo.
	 * @return double
	 */
	public double getPredrawInterestInfo() {
		return predrawInterestInfo;
	}

	/**
	 * Returns the rate.
	 * @return double
	 */
	public double getRate() {
		return rate;
	}

	/**
	 * Returns the sDate.
	 * @return Timestamp
	 */
	public Timestamp getSDate() {
		return sDate;
	}

	/**
	 * Sets the balance.
	 * @param balance The balance to set
	 */
	public void setBalance(double balance) {
		this.balance = balance;
	}

	/**
	 * Sets the days.
	 * @param days The days to set
	 */
	public void setDays(long days) {
		this.days = days;
	}

	/**
	 * Sets the eDate.
	 * @param eDate The eDate to set
	 */
	public void setEDate(Timestamp eDate) {
		this.eDate = eDate;
	}

	/**
	 * Sets the predrawInterestInfo.
	 * @param predrawInterestInfo The predrawInterestInfo to set
	 */
	public void setPredrawInterestInfo(double predrawInterestInfo) {
		this.predrawInterestInfo = predrawInterestInfo;
	}

	/**
	 * Sets the rate.
	 * @param rate The rate to set
	 */
	public void setRate(double rate) {
		this.rate = rate;
	}

	/**
	 * Sets the sDate.
	 * @param sDate The sDate to set
	 */
	public void setSDate(Timestamp sDate) {
		this.sDate = sDate;
	}

}