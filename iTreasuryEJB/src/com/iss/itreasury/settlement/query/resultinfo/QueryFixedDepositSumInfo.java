/*
 * Created on 2003-10-30
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.settlement.query.resultinfo;

import java.io.Serializable;

/**
 * @author yiwang
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class QueryFixedDepositSumInfo implements Serializable
{

	/**
	 * 
	 */
	public QueryFixedDepositSumInfo()
	{
		super();
		// TODO Auto-generated constructor stub
	}
    public double DepositBalanceSum = 0.0;   //存款余额
    public double DepositAmountSum = 0.0;   //存款金额 
    public double DepositInterestSum = 0.0;   //存款利息 
	/**
	 * @return Returns the depositBalanceSum.
	 */
	public double getDepositBalanceSum()
	{
		return DepositBalanceSum;
	}
	/**
	 * @param depositBalanceSum The depositBalanceSum to set.
	 */
	public void setDepositBalanceSum(double depositBalanceSum)
	{
		DepositBalanceSum = depositBalanceSum;
	}
	
	/**
	 * @return
	 */
	public double getDepositAmountSum()
	{
		return DepositAmountSum;
	}

	/**
	 * @return
	 */
	public double getDepositInterestSum()
	{
		return DepositInterestSum;
	}

	/**
	 * @param d
	 */
	public void setDepositAmountSum(double d)
	{
		DepositAmountSum = d;
	}

	/**
	 * @param d
	 */
	public void setDepositInterestSum(double d)
	{
		DepositInterestSum = d;
	}

}
