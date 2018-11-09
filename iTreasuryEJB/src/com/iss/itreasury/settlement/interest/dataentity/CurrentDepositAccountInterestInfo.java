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
 *  Date of Creation    2003-10-27
 */
public class CurrentDepositAccountInterestInfo implements Serializable {

	private double normalBalance = 0.0; //正常余额
	private double normalInterest = 0.0; //正常利息
	private double termNormalInterest = 0.0; //本段正常利息
	private double negotiationBalance = 0.0; //协定余额
	private double negotiationInterest = 0.0; //协定利息
	private double termNegotiationInterest = 0.0; //本段协定利息
	
	private Timestamp sDate = null;  //起息日
	private Timestamp eDate = null;  //止息日
	private long intervalDays = -1;  //天数
	private double normalInterestRate = 0.0; //正常利率	
	private double averageNormalBalance = 0.0;  //平均正常余额
	private double sumOfNormalInterest = 0.0;   //正常利率
	private double averageNegotiationBalance = 0.0;  //平均协定余额
	private double negotiationInterestRate = 0.0; //平均协定利率
	private double drawInterest = 0.0; //正常利息
	 	
	public double getDrawInterest() 
	{
		return drawInterest;
	}

	public void setDrawInterest(double drawInterest) 
	{
		this.drawInterest = drawInterest;
	}

	/**
	 * Returns the negotiationBalance.
	 * @return double
	 */
	public double getNegotiationBalance() {
		return negotiationBalance;
	}

	/**
	 * Returns the negotiationInterest.
	 * @return double
	 */
	public double getNegotiationInterest() {
		return negotiationInterest;
	}

	/**
	 * Returns the normalBalance.
	 * @return double
	 */
	public double getNormalBalance() {
		return normalBalance;
	}

	/**
	 * Returns the normalInterest.
	 * @return double
	 */
	public double getNormalInterest() {
		return normalInterest;
	}

	/**
	 * Returns the termNegotiationInterest.
	 * @return double
	 */
	public double getTermNegotiationInterest() {
		return termNegotiationInterest;
	}

	/**
	 * Returns the termNormalInterest.
	 * @return double
	 */
	public double getTermNormalInterest() {
		return termNormalInterest;
	}

	/**
	 * Sets the negotiationBalance.
	 * @param negotiationBalance The negotiationBalance to set
	 */
	public void setNegotiationBalance(double negotiationBalance) {
		this.negotiationBalance = negotiationBalance;
	}

	/**
	 * Sets the negotiationInterest.
	 * @param negotiationInterest The negotiationInterest to set
	 */
	public void setNegotiationInterest(double negotiationInterest) {
		this.negotiationInterest = negotiationInterest;
	}

	/**
	 * Sets the normalBalance.
	 * @param normalBalance The normalBalance to set
	 */
	public void setNormalBalance(double normalBalance) {
		this.normalBalance = normalBalance;
	}

	/**
	 * Sets the normalInterest.
	 * @param normalInterest The normalInterest to set
	 */
	public void setNormalInterest(double normalInterest) {
		this.normalInterest = normalInterest;
	}

	/**
	 * Sets the termNegotiationInterest.
	 * @param termNegotiationInterest The termNegotiationInterest to set
	 */
	public void setTermNegotiationInterest(double termNegotiationInterest) {
		this.termNegotiationInterest = termNegotiationInterest;
	}

	/**
	 * Sets the termNormalInterest.
	 * @param termNormalInterest The termNormalInterest to set
	 */
	public void setTermNormalInterest(double termNormalInterest) {
		this.termNormalInterest = termNormalInterest;
	}

	/**
	 * Returns the averageNegotiationBalance.
	 * @return double
	 */
	public double getAverageNegotiationBalance() {
		return averageNegotiationBalance;
	}

	/**
	 * Returns the averageNormalBalance.
	 * @return double
	 */
	public double getAverageNormalBalance() {
		return averageNormalBalance;
	}

	/**
	 * Returns the eDate.
	 * @return Timestamp
	 */
	public Timestamp getEDate() {
		return eDate;
	}

	/**
	 * Returns the intervalDays.
	 * @return long
	 */
	public long getIntervalDays() {
		return intervalDays;
	}

	/**
	 * Returns the negotiationInterestRate.
	 * @return double
	 */
	public double getNegotiationInterestRate() {
		return negotiationInterestRate;
	}

	/**
	 * Returns the sDate.
	 * @return Timestamp
	 */
	public Timestamp getSDate() {
		return sDate;
	}

	/**
	 * Returns the sumOfNormalInterest.
	 * @return double
	 */
	public double getSumOfNormalInterest() {
		return sumOfNormalInterest;
	}

	/**
	 * Sets the averageNegotiationBalance.
	 * @param averageNegotiationBalance The averageNegotiationBalance to set
	 */
	public void setAverageNegotiationBalance(double averageNegotiationBalance) {
		this.averageNegotiationBalance = averageNegotiationBalance;
	}

	/**
	 * Sets the averageNormalBalance.
	 * @param averageNormalBalance The averageNormalBalance to set
	 */
	public void setAverageNormalBalance(double averageNormalBalance) {
		this.averageNormalBalance = averageNormalBalance;
	}

	/**
	 * Sets the eDate.
	 * @param eDate The eDate to set
	 */
	public void setEDate(Timestamp eDate) {
		this.eDate = eDate;
	}

	/**
	 * Sets the intervalDays.
	 * @param intervalDays The intervalDays to set
	 */
	public void setIntervalDays(long intervalDays) {
		this.intervalDays = intervalDays;
	}

	/**
	 * Sets the negotiationInterestRate.
	 * @param negotiationInterestRate The negotiationInterestRate to set
	 */
	public void setNegotiationInterestRate(double negotiationInterestRate) {
		this.negotiationInterestRate = negotiationInterestRate;
	}

	/**
	 * Sets the sDate.
	 * @param sDate The sDate to set
	 */
	public void setSDate(Timestamp sDate) {
		this.sDate = sDate;
	}

	/**
	 * Sets the sumOfNormalInterest.
	 * @param sumOfNormalInterest The sumOfNormalInterest to set
	 */
	public void setSumOfNormalInterest(double sumOfNormalInterest) {
		this.sumOfNormalInterest = sumOfNormalInterest;
	}

	/**
	 * Returns the normalInterestRate.
	 * @return double
	 */
	public double getNormalInterestRate() {
		return normalInterestRate;
	}

	/**
	 * Sets the normalInterestRate.
	 * @param normalInterestRate The normalInterestRate to set
	 */
	public void setNormalInterestRate(double normalInterestRate) {
		this.normalInterestRate = normalInterestRate;
	}

}
