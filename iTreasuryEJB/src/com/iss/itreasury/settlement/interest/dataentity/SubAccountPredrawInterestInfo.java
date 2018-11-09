package com.iss.itreasury.settlement.interest.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Title:        		iTreasury
 * Description:         
 * Copyright:           Copyright (c) 2003
 * Company:             iSoftStone
 * @author              hjLiu 
 * @version
 *  Date of Creation    2003-11-17
 */
public class SubAccountPredrawInterestInfo implements Serializable {
	private Timestamp sDate = null;  // 起息日
	private Timestamp eDate = null;  //结息日
	private long days = -1;          //天数
	private double balance = 0.0;    //当前余额
	private double rate = 0.0;       //当前利率
	private double predrawInterest = 0.0;   //当前计提利息（贷款、定期）
	private Timestamp predrawDate = null;  //计提日期
	private String fixedDepositNo = "";
	
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

	/**
	 * @return
	 */
	public String getFixedDepositNo()
	{
		return fixedDepositNo;
	}

	/**
	 * @return
	 */
	public double getPredrawInterest()
	{
		return predrawInterest;
	}

	/**
	 * @param string
	 */
	public void setFixedDepositNo(String string)
	{
		fixedDepositNo = string;
	}

	/**
	 * @param d
	 */
	public void setPredrawInterest(double d)
	{
		predrawInterest = d;
	}

	/**
	 * @return
	 */
	public Timestamp getPredrawDate()
	{
		return predrawDate;
	}

	/**
	 * @param timestamp
	 */
	public void setPredrawDate(Timestamp timestamp)
	{
		predrawDate = timestamp;
	}

}
