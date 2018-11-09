/*
 * Created on 2004-9-21
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.settlement.query.paraminfo;

import java.io.Serializable;

/**
 * @author weilu
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class QueryContractSumInfo implements Serializable
{
	/**
	 * 
	 */
	//贷款申请总金额
    private double TotalApplyAmount=0;
    
    //放款合计
    private double TotalPayAmount=0;
    
    //还款合计
    private double TotalRepayAmount=0;
    
    //利息合计
    private double TotalInterestAmount=0;
    
    //手续费合计
    private double TotalChargeAmount=0;
    
    //贷款余额合计
    private double TotalBalance=0;
    
    //担保费合计
    private double TotalCreditAmount=0;

	//逾期余额
	private double sumOverdueAmount=0.0;

	//表外利息
	private double sumPunishInterest=0.0;

    private long AllRecord = 0;
    
	public QueryContractSumInfo()
	{
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * @return Returns the allRecord.
	 */
	public long getAllRecord()
	{
		return AllRecord;
	}
	/**
	 * @param allRecord The allRecord to set.
	 */
	public void setAllRecord(long allRecord)
	{
		AllRecord = allRecord;
	}
	/**
	 * @return Returns the sumOverdueAmount.
	 */
	public double getSumOverdueAmount()
	{
		return sumOverdueAmount;
	}
	/**
	 * @param sumOverdueAmount The sumOverdueAmount to set.
	 */
	public void setSumOverdueAmount(double sumOverdueAmount)
	{
		this.sumOverdueAmount = sumOverdueAmount;
	}
	/**
	 * @return Returns the sumPunishInterest.
	 */
	public double getSumPunishInterest()
	{
		return sumPunishInterest;
	}
	/**
	 * @param sumPunishInterest The sumPunishInterest to set.
	 */
	public void setSumPunishInterest(double sumPunishInterest)
	{
		this.sumPunishInterest = sumPunishInterest;
	}
	/**
	 * @return Returns the totalApplyAmount.
	 */
	public double getTotalApplyAmount()
	{
		return TotalApplyAmount;
	}
	/**
	 * @param totalApplyAmount The totalApplyAmount to set.
	 */
	public void setTotalApplyAmount(double totalApplyAmount)
	{
		TotalApplyAmount = totalApplyAmount;
	}
	/**
	 * @return Returns the totalBalance.
	 */
	public double getTotalBalance()
	{
		return TotalBalance;
	}
	/**
	 * @param totalBalance The totalBalance to set.
	 */
	public void setTotalBalance(double totalBalance)
	{
		TotalBalance = totalBalance;
	}
	/**
	 * @return Returns the totalChargeAmount.
	 */
	public double getTotalChargeAmount()
	{
		return TotalChargeAmount;
	}
	/**
	 * @param totalChargeAmount The totalChargeAmount to set.
	 */
	public void setTotalChargeAmount(double totalChargeAmount)
	{
		TotalChargeAmount = totalChargeAmount;
	}
	/**
	 * @return Returns the totalCreditAmount.
	 */
	public double getTotalCreditAmount()
	{
		return TotalCreditAmount;
	}
	/**
	 * @param totalCreditAmount The totalCreditAmount to set.
	 */
	public void setTotalCreditAmount(double totalCreditAmount)
	{
		TotalCreditAmount = totalCreditAmount;
	}
	/**
	 * @return Returns the totalInterestAmount.
	 */
	public double getTotalInterestAmount()
	{
		return TotalInterestAmount;
	}
	/**
	 * @param totalInterestAmount The totalInterestAmount to set.
	 */
	public void setTotalInterestAmount(double totalInterestAmount)
	{
		TotalInterestAmount = totalInterestAmount;
	}
	/**
	 * @return Returns the totalPayAmount.
	 */
	public double getTotalPayAmount()
	{
		return TotalPayAmount;
	}
	/**
	 * @param totalPayAmount The totalPayAmount to set.
	 */
	public void setTotalPayAmount(double totalPayAmount)
	{
		TotalPayAmount = totalPayAmount;
	}
	/**
	 * @return Returns the totalRepayAmount.
	 */
	public double getTotalRepayAmount()
	{
		return TotalRepayAmount;
	}
	/**
	 * @param totalRepayAmount The totalRepayAmount to set.
	 */
	public void setTotalRepayAmount(double totalRepayAmount)
	{
		TotalRepayAmount = totalRepayAmount;
	}
}
