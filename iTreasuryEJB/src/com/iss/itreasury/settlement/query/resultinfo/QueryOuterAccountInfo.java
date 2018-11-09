/*
 * Created on 2003-12-16
 * 
 * To change the template for this generated file go to 
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.settlement.query.resultinfo;

import java.io.Serializable;

/**
 * @author kewen hu 2003-12-16
 *
 * To change the template for this generated file go to 
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class QueryOuterAccountInfo implements Serializable
{
	private long No=0;						// 序号
	private String DepositCorp="";			// 存款单位
	private double MonthHandIn=0.0;			// 本月累计-存入
	private double MonthCost=0.0;			// 本月累计-支取
	private double DailyHandIn=0.0;			// 当日变动-存入
	private double DailyCost=0.0;			// 当日变动-支取
	private double MonthSumHandIn=0.0;		// 本月合计-存入
	private double MonthSumCost=0.0;		// 本月合计-支取
	private double NowDeposit=0.0;			// 当期存款
	private double YearHandIn=0.0;			// 本年累计-上交
	private double YearCost=0.0;			// 本年累计-拨款
	private double YearSumHandIn=0.0;		// 本年合计-上交
	private double YearSumCost=0.0;			// 本年合计-拨款
	private double YearSumMargin=0.0;		// 本年合计-差额
	private String Abstract="";				// 备注
	private double YesterdayBalance = 0.0;	//昨日余额
	private double TodayBalance = 0.0;		//今日余额
	/**
	 * 构造函数
	 * @see java.lang.Object#Object()
	 */
	public QueryOuterAccountInfo() {
		super();
	}
	/**
	 * Returns the a.
	 * @return String
	 */
	public String getAbstract() {
		return Abstract;
	}

	/**
	 * Returns the dailyCost.
	 * @return double
	 */
	public double getDailyCost() {
		return DailyCost;
	}

	/**
	 * Returns the dailyHandIn.
	 * @return double
	 */
	public double getDailyHandIn() {
		return DailyHandIn;
	}

	/**
	 * Returns the depositCorp.
	 * @return String
	 */
	public String getDepositCorp() {
		return DepositCorp;
	}

	/**
	 * Returns the monthCost.
	 * @return double
	 */
	public double getMonthCost() {
		return MonthCost;
	}

	/**
	 * Returns the monthHandIn.
	 * @return double
	 */
	public double getMonthHandIn() {
		return MonthHandIn;
	}

	/**
	 * Returns the monthSumCost.
	 * @return double
	 */
	public double getMonthSumCost() {
		return MonthSumCost;
	}

	/**
	 * Returns the monthSumHandIn.
	 * @return double
	 */
	public double getMonthSumHandIn() {
		return MonthSumHandIn;
	}

	/**
	 * Returns the no.
	 * @return long
	 */
	public long getNo() {
		return No;
	}

	/**
	 * Returns the nowDeposit.
	 * @return double
	 */
	public double getNowDeposit() {
		return NowDeposit;
	}

	/**
	 * Returns the yearCost.
	 * @return double
	 */
	public double getYearCost() {
		return YearCost;
	}

	/**
	 * Returns the yearHandIn.
	 * @return double
	 */
	public double getYearHandIn() {
		return YearHandIn;
	}

	/**
	 * Returns the yearSumCost.
	 * @return double
	 */
	public double getYearSumCost() {
		return YearSumCost;
	}

	/**
	 * Returns the yearSumHandIn.
	 * @return double
	 */
	public double getYearSumHandIn() {
		return YearSumHandIn;
	}

	/**
	 * Returns the yearSumMargin.
	 * @return double
	 */
	public double getYearSumMargin() {
		return YearSumMargin;
	}

	/**
	 * Sets the a.
	 * @param a The a to set
	 */
	public void setAbstract(String a) {
		Abstract = a;
	}

	/**
	 * Sets the dailyCost.
	 * @param dailyCost The dailyCost to set
	 */
	public void setDailyCost(double dailyCost) {
		DailyCost = dailyCost;
	}

	/**
	 * Sets the dailyHandIn.
	 * @param dailyHandIn The dailyHandIn to set
	 */
	public void setDailyHandIn(double dailyHandIn) {
		DailyHandIn = dailyHandIn;
	}

	/**
	 * Sets the depositCorp.
	 * @param depositCorp The depositCorp to set
	 */
	public void setDepositCorp(String depositCorp) {
		DepositCorp = depositCorp;
	}

	/**
	 * Sets the monthCost.
	 * @param monthCost The monthCost to set
	 */
	public void setMonthCost(double monthCost) {
		MonthCost = monthCost;
	}

	/**
	 * Sets the monthHandIn.
	 * @param monthHandIn The monthHandIn to set
	 */
	public void setMonthHandIn(double monthHandIn) {
		MonthHandIn = monthHandIn;
	}

	/**
	 * Sets the monthSumCost.
	 * @param monthSumCost The monthSumCost to set
	 */
	public void setMonthSumCost(double monthSumCost) {
		MonthSumCost = monthSumCost;
	}

	/**
	 * Sets the monthSumHandIn.
	 * @param monthSumHandIn The monthSumHandIn to set
	 */
	public void setMonthSumHandIn(double monthSumHandIn) {
		MonthSumHandIn = monthSumHandIn;
	}

	/**
	 * Sets the no.
	 * @param no The no to set
	 */
	public void setNo(long no) {
		No = no;
	}

	/**
	 * Sets the nowDeposit.
	 * @param nowDeposit The nowDeposit to set
	 */
	public void setNowDeposit(double nowDeposit) {
		NowDeposit = nowDeposit;
	}

	/**
	 * Sets the yearCost.
	 * @param yearCost The yearCost to set
	 */
	public void setYearCost(double yearCost) {
		YearCost = yearCost;
	}

	/**
	 * Sets the yearHandIn.
	 * @param yearHandIn The yearHandIn to set
	 */
	public void setYearHandIn(double yearHandIn) {
		YearHandIn = yearHandIn;
	}

	/**
	 * Sets the yearSumCost.
	 * @param yearSumCost The yearSumCost to set
	 */
	public void setYearSumCost(double yearSumCost) {
		YearSumCost = yearSumCost;
	}

	/**
	 * Sets the yearSumHandIn.
	 * @param yearSumHandIn The yearSumHandIn to set
	 */
	public void setYearSumHandIn(double yearSumHandIn) {
		YearSumHandIn = yearSumHandIn;
	}

	/**
	 * Sets the yearSumMargin.
	 * @param yearSumMargin The yearSumMargin to set
	 */
	public void setYearSumMargin(double yearSumMargin) {
		YearSumMargin = yearSumMargin;
	}

	/**
	 * @return Returns the todayBalance.
	 */
	public double getTodayBalance() {
		return TodayBalance;
	}
	/**
	 * @param todayBalance The todayBalance to set.
	 */
	public void setTodayBalance(double todayBalance) {
		TodayBalance = todayBalance;
	}
	/**
	 * @return Returns the yesterdayBalance.
	 */
	public double getYesterdayBalance() {
		return YesterdayBalance;
	}
	/**
	 * @param yesterdayBalance The yesterdayBalance to set.
	 */
	public void setYesterdayBalance(double yesterdayBalance) {
		YesterdayBalance = yesterdayBalance;
	}
}
