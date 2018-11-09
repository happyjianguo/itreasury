/*
 * Created on 2004-2-4
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.iss.itreasury.ebank.obaccountinfo.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author yfsu
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class OBAccountBalanceFixedInfo implements Serializable
{
	//账户类型
	private long naccounttypeid=-1;
	//账户ID
	private long AccountID=-1;
	//子账户ID
	 private long SubAccountID=-1;
	//主账号
	private long naccountid=-1;
	//账号/单据号
	private String saccountno="";
	//存入日
	private Timestamp af_dtstart=null;
	//到期日
	private Timestamp af_dtend=null;
	//期限/品种
	private long af_ndepositterm=-1;
	//利率
	private double af_mrate=0.0;
	//存款金额
	private double mopenamount=0.0;
	//存款余额
	private double mbalance=0.0;
	//备注
	private long nstatusid=-1;
	//小计
	private double subsum=0.0;
	//存款汇总余额
	private double sum=0.0;
	//期限或品种的类型
	private long ntype=-1;
	/**
	 * @return
	 */
	public Timestamp getAf_dtend()
	{
		return af_dtend;
	}

	/**
	 * @return
	 */
	public Timestamp getAf_dtstart()
	{
		return af_dtstart;
	}

	/**
	 * @return
	 */
	public double getAf_mrate()
	{
		return af_mrate;
	}

	/**
	 * @return
	 */
	public long getAf_ndepositterm()
	{
		return af_ndepositterm;
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
	public double getMopenamount()
	{
		return mopenamount;
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
	public long getNstatusid()
	{
		return nstatusid;
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
	public double getSubsum()
	{
		return subsum;
	}

	/**
	 * @return
	 */
	public double getSum()
	{
		return sum;
	}

	/**
	 * @param timestamp
	 */
	public void setAf_dtend(Timestamp timestamp)
	{
		af_dtend = timestamp;
	}

	/**
	 * @param timestamp
	 */
	public void setAf_dtstart(Timestamp timestamp)
	{
		af_dtstart = timestamp;
	}

	/**
	 * @param d
	 */
	public void setAf_mrate(double d)
	{
		af_mrate = d;
	}

	/**
	 * @param l
	 */
	public void setAf_ndepositterm(long l)
	{
		af_ndepositterm = l;
	}

	/**
	 * @param d
	 */
	public void setMbalance(double d)
	{
		mbalance = d;
	}

	/**
	 * @param d
	 */
	public void setMopenamount(double d)
	{
		mopenamount = d;
	}

	/**
	 * @param l
	 */
	public void setNaccounttypeid(long l)
	{
		naccounttypeid = l;
	}

	/**
	 * @param l
	 */
	public void setNstatusid(long l)
	{
		nstatusid = l;
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
	public void setSubsum(double d)
	{
		subsum = d;
	}

	/**
	 * @param d
	 */
	public void setSum(double d)
	{
		sum = d;
	}

	/**
	 * @return
	 */
	public long getNaccountid()
	{
		return naccountid;
	}

	/**
	 * @param l
	 */
	public void setNaccountid(long l)
	{
		naccountid = l;
	}

	/**
	 * @return
	 */
	public long getNtype()
	{
		return ntype;
	}

	/**
	 * @param l
	 */
	public void setNtype(long l)
	{
		ntype = l;
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

	/**
	 * @return
	 */
	public long getSubAccountID() {
		return SubAccountID;
	}

	/**
	 * @param l
	 */
	public void setSubAccountID(long l) {
		SubAccountID = l;
	}

}
