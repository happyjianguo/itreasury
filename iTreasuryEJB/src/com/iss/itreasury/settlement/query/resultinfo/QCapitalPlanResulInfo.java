package com.iss.itreasury.settlement.query.resultinfo;

import java.sql.Timestamp;

public class QCapitalPlanResulInfo
{
	long clientID = -1;// 客户ID

	Timestamp startDate = null;// 开始时间

	Timestamp endDate = null;// 开始时间

	double planPayout = -1;// 计划支出

	double planEarning = -1;// 计划收入

	double factPayout = -1;// 实际支出

	double factEarning = -1;// 实际收入

	double payoutBalance = -1;// 实际支出

	double earningBalance = -1;// 实际收入

	public long getClientID()
	{
		return clientID;
	}

	public void setClientID(long clientID)
	{
		this.clientID = clientID;
	}


	public double getEarningBalance()
	{
		return earningBalance;
	}

	public void setEarningBalance(double earningBalance)
	{
		this.earningBalance = earningBalance;
	}

	public Timestamp getEndDate()
	{
		return endDate;
	}

	public void setEndDate(Timestamp endDate)
	{
		this.endDate = endDate;
	}

	public double getFactEarning()
	{
		return factEarning;
	}

	public void setFactEarning(double factEarning)
	{
		this.factEarning = factEarning;
	}

	public double getFactPayout()
	{
		return factPayout;
	}

	public void setFactPayout(double factPayout)
	{
		this.factPayout = factPayout;
	}

	public double getPayoutBalance()
	{
		return payoutBalance;
	}

	public void setPayoutBalance(double payoutBalance)
	{
		this.payoutBalance = payoutBalance;
	}

	public double getPlanEarning()
	{
		return planEarning;
	}

	public void setPlanEarning(double planEarning)
	{
		this.planEarning = planEarning;
	}

	public double getPlanPayout()
	{
		return planPayout;
	}

	public void setPlanPayout(double planPayout)
	{
		this.planPayout = planPayout;
	}

	public Timestamp getStartDate()
	{
		return startDate;
	}

	public void setStartDate(Timestamp startDate)
	{
		this.startDate = startDate;
	}
}
