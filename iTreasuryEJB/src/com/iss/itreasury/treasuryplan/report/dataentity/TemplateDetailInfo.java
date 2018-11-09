/*
 * Created on 2004-6-29
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.treasuryplan.report.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author wlming
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class TemplateDetailInfo implements Serializable
{
	private Timestamp TransactionDate = null;//被预测的交易数据日期
	private double ForecastAmount = 0.0;//预测数
	private double ActualAmount = 0.0;//实际数
	private double PlanAmount = 0.0;//计划数
	private double DifferenceAmount = 0.0;//差值
	private long Ischange = -1;//是否被修改过
	
	/**
	 * @return Returns the forecastAmount.
	 */
	public double getForecastAmount()
	{
		return ForecastAmount;
	}
	/**
	 * @param forecastAmount The forecastAmount to set.
	 */
	public void setForecastAmount(double forecastAmount)
	{
		ForecastAmount = forecastAmount;
	}
	/**
	 * @return Returns the planAmount.
	 */
	public double getPlanAmount()
	{
		return PlanAmount;
	}
	/**
	 * @param planAmount The planAmount to set.
	 */
	public void setPlanAmount(double planAmount)
	{
		PlanAmount = planAmount;
	}
	/**
	 * @return Returns the transactionDate.
	 */
	public Timestamp getTransactionDate()
	{
		return TransactionDate;
	}
	/**
	 * @param transactionDate The transactionDate to set.
	 */
	public void setTransactionDate(Timestamp transactionDate)
	{
		TransactionDate = transactionDate;
	}
	/**
	 * @return Returns the differenceAmount.
	 */
	public double getDifferenceAmount()
	{
		return DifferenceAmount;
	}
	/**
	 * @param differenceAmount The differenceAmount to set.
	 */
	public void setDifferenceAmount(double differenceAmount)
	{
		DifferenceAmount = differenceAmount;
	}
	/**
	 * @return Returns the actualAmount.
	 */
	public double getActualAmount() {
		return ActualAmount;
	}
	/**
	 * @param actualAmount The actualAmount to set.
	 */
	public void setActualAmount(double actualAmount) {
		ActualAmount = actualAmount;
	}
	/**
	 * @return Returns the ischange.
	 */
	public long getIschange()
	{
		return Ischange;
	}
	/**
	 * @param ischange The ischange to set.
	 */
	public void setIschange(long ischange)
	{
		Ischange = ischange;
	}
}