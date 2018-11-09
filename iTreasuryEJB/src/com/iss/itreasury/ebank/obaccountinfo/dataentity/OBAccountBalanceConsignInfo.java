/*
 * Created on 2004-2-4
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.iss.itreasury.ebank.obaccountinfo.dataentity;

import java.io.Serializable;

/**
 * @author yfsu
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class OBAccountBalanceConsignInfo implements Serializable
{
	//账户类型
	private long naccounttypeid=0;
	//账户ID
	private long AccountID=-1;
	//账号
	private String saccountno="";
	//资金余额
	private double mbalance=0.0;
	//最低余额限制
	private double ac_mcapitallimitamount=0.0;
	//小计
	private double SubSum=0.0;
	/**
	 * @return
	 */
	public double getAc_mcapitallimitamount()
	{
		return ac_mcapitallimitamount;
	}

	/**
	 * @return
	 */
	public double getMbalance()
	{
		return mbalance;
	}

	/**
	 * @return
	 */
	public long getNaccounttypeid()
	{
		return naccounttypeid;
	}

	/**
	 * @return
	 */
	public String getSaccountno()
	{
		return saccountno;
	}

	/**
	 * @return
	 */
	public double getSubSum()
	{
		return SubSum;
	}

	/**
	 * @param d
	 */
	public void setAc_mcapitallimitamount(double d)
	{
		ac_mcapitallimitamount = d;
	}

	/**
	 * @param d
	 */
	public void setMbalance(double d)
	{
		mbalance = d;
	}

	/**
	 * @param l
	 */
	public void setNaccounttypeid(long l)
	{
		naccounttypeid = l;
	}

	/**
	 * @param string
	 */
	public void setSaccountno(String string)
	{
		saccountno = string;
	}

	/**
	 * @param d
	 */
	public void setSubSum(double d)
	{
		SubSum = d;
	}

	/**
	 * @return
	 */
	public long getAccountID() {
		return AccountID;
	}

	/**
	 * @param l
	 */
	public void setAccountID(long l) {
		AccountID = l;
	}

}
