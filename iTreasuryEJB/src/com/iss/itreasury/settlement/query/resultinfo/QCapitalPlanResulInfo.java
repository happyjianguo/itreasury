package com.iss.itreasury.settlement.query.resultinfo;

import java.sql.Timestamp;

public class QCapitalPlanResulInfo
{
	long clientID = -1;// �ͻ�ID

	Timestamp startDate = null;// ��ʼʱ��

	Timestamp endDate = null;// ��ʼʱ��

	double planPayout = -1;// �ƻ�֧��

	double planEarning = -1;// �ƻ�����

	double factPayout = -1;// ʵ��֧��

	double factEarning = -1;// ʵ������

	double payoutBalance = -1;// ʵ��֧��

	double earningBalance = -1;// ʵ������

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
