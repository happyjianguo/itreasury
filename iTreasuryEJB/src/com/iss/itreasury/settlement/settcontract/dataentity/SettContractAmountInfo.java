/*
 * Created on 2003-11-21
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.settlement.settcontract.dataentity;

/**
 * @author hyzeng
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class SettContractAmountInfo implements java.io.Serializable
{
	private double BalanceAmount = 0;//��ͬ���
	private double OpenAmount = 0;//��ͬ�ѷ��Ž��
	private double RepayAmount = 0;//��ͬ�ѻ����
	private double UnPayAmount = 0;//��ͬδ���Ž��
	private double AheadAmount = 0;//���ύ����ǰ������

	/**
	 * @return
	 */
	public double getBalanceAmount()
	{
		return BalanceAmount;
	}

	/**
	 * @return
	 */
	public double getOpenAmount()
	{
		return OpenAmount;
	}

	/**
	 * @return
	 */
	public double getRepayAmount()
	{
		return RepayAmount;
	}

	/**
	 * @param d
	 */
	public void setBalanceAmount(double d)
	{
		BalanceAmount = d;
	}

	/**
	 * @param d
	 */
	public void setOpenAmount(double d)
	{
		OpenAmount = d;
	}

	/**
	 * @param d
	 */
	public void setRepayAmount(double d)
	{
		RepayAmount = d;
	}

	/**
	 * @return
	 */
	public double getUnPayAmount()
	{
		return UnPayAmount;
	}

	/**
	 * @param d
	 */
	public void setUnPayAmount(double d)
	{
		UnPayAmount = d;
	}

	/**
	 * @return
	 */
	public double getAheadAmount()
	{
		return AheadAmount;
	}

	/**
	 * @param d
	 */
	public void setAheadAmount(double d)
	{
		AheadAmount = d;
	}

}
