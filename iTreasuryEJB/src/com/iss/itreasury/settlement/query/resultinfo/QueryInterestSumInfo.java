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
public class QueryInterestSumInfo implements Serializable
{

	/**
	 * 
	 */
	public QueryInterestSumInfo()
	{
		super();
		// TODO Auto-generated constructor stub
	}
    public double BalanceSum = 0.0;    //���ϼ�
    public double InterestSum = 0.0;   //��Ϣ�ϼ�
    public double AssureSum = 0.0;  //�����Ѻϼ�
    public double InterestTaxSum = 0.0;  //��Ϣ˰�Ѻϼ�
    public double CommisionSum = 0.0;    //�����Ѻϼ�
	public double ForfeitInterestSum = 0.0;    //��Ϣ�ϼ�
	public double CompoundInterestSum = 0.0;    //�����ϼ�
    
	
		
    	
	/**
	 * @return
	 */
	public double getAssureSum()
	{
		return AssureSum;
	}

	/**
	 * @return
	 */
	public double getBalanceSum()
	{
		return BalanceSum;
	}

	/**
	 * @return
	 */
	public double getCommisionSum()
	{
		return CommisionSum;
	}

	/**
	 * @return
	 */
	public double getInterestSum()
	{
		return InterestSum;
	}

	/**
	 * @return
	 */
	public double getInterestTaxSum()
	{
		return InterestTaxSum;
	}

	/**
	 * @param d
	 */
	public void setAssureSum(double d)
	{
		AssureSum = d;
	}

	/**
	 * @param d
	 */
	public void setBalanceSum(double d)
	{
		BalanceSum = d;
	}

	/**
	 * @param d
	 */
	public void setCommisionSum(double d)
	{
		CommisionSum = d;
	}

	/**
	 * @param d
	 */
	public void setInterestSum(double d)
	{
		InterestSum = d;
	}

	/**
	 * @param d
	 */
	public void setInterestTaxSum(double d)
	{
		InterestTaxSum = d;
	}

	/**
	 * @return
	 */
	public double getCompoundInterestSum()
	{
		return CompoundInterestSum;
	}

	/**
	 * @return
	 */
	public double getForfeitInterestSum()
	{
		return ForfeitInterestSum;
	}

	/**
	 * @param d
	 */
	public void setCompoundInterestSum(double d)
	{
		CompoundInterestSum = d;
	}

	/**
	 * @param d
	 */
	public void setForfeitInterestSum(double d)
	{
		ForfeitInterestSum = d;
	}

}
