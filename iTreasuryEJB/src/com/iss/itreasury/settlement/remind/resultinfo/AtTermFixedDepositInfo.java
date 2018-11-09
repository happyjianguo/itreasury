/*
 * Created on 2003-12-23
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.settlement.remind.resultinfo;

import java.sql.Timestamp;

/**
 * @author wlming
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class AtTermFixedDepositInfo implements java.io.Serializable
{
	private long SubAccountID = -1; //子账户ID
	private String FixedDepositNo = ""; //定期存单号
	private long AccountID = -1; //账户ID
	private String AccountNo = ""; //账户编号
	private long ClientID = -1; //客户ID
	private String ClientNo = ""; //客户编号
	private String ClientName = ""; //客户名称
	private double mbalance = 0.00;		//存款金额
	private double af_mrate = 0.00;		//存款利率
	private Timestamp EndDate = null; //到期日期
	private long depsitterm = -1; //定期存款期限   NDEPOSITTERM
	private long isautocontinue = -1; //是否到期自动续存   isautocontinue
	private long autocontinuetype = -1; //自动续存方式   autocontinuetype
	
	/**
	 * @return
	 */
	public long getAccountID()
	{
		return AccountID;
	}

	/**
	 * @return
	 */
	public String getAccountNo()
	{
		return AccountNo;
	}

	/**
	 * @return
	 */
	public long getClientID()
	{
		return ClientID;
	}

	/**
	 * @return
	 */
	public String getClientName()
	{
		return ClientName;
	}

	/**
	 * @return
	 */
	public String getClientNo()
	{
		return ClientNo;
	}

	/**
	 * @return
	 */
	public Timestamp getEndDate()
	{
		return EndDate;
	}

	/**
	 * @return
	 */
	public String getFixedDepositNo()
	{
		return FixedDepositNo;
	}

	/**
	 * @return
	 */
	public long getSubAccountID()
	{
		return SubAccountID;
	}

	/**
	 * @param l
	 */
	public void setAccountID(long l)
	{
		AccountID = l;
	}

	/**
	 * @param string
	 */
	public void setAccountNo(String string)
	{
		AccountNo = string;
	}

	/**
	 * @param l
	 */
	public void setClientID(long l)
	{
		ClientID = l;
	}

	/**
	 * @param string
	 */
	public void setClientName(String string)
	{
		ClientName = string;
	}

	/**
	 * @param string
	 */
	public void setClientNo(String string)
	{
		ClientNo = string;
	}

	/**
	 * @param timestamp
	 */
	public void setEndDate(Timestamp timestamp)
	{
		EndDate = timestamp;
	}

	/**
	 * @param string
	 */
	public void setFixedDepositNo(String string)
	{
		FixedDepositNo = string;
	}

	/**
	 * @param l
	 */
	public void setSubAccountID(long l)
	{
		SubAccountID = l;
	}

	/**
	 * @return 返回 af_mrate。
	 */
	public double getAf_mrate() {
		return af_mrate;
	}

	/**
	 * @param af_mrate 要设置的 af_mrate。
	 */
	public void setAf_mrate(double af_mrate) {
		this.af_mrate = af_mrate;
	}

	/**
	 * @return 返回 mbalance。
	 */
	public double getMbalance() {
		return mbalance;
	}

	/**
	 * @param mbalance 要设置的 mbalance。
	 */
	public void setMbalance(double mbalance) {
		this.mbalance = mbalance;
	}
	/**
	 * @return 返回 depsitterm。
	 */
	public long getDepsitterm() {
		return depsitterm;
	}
	/**
	 * @param mbalance 要设置的 depsitterm。
	 */
	public void setDepsitterm(long depsitterm) {
		this.depsitterm = depsitterm;
	}
	/**
	 * @return 返回 isautocontinue。
	 */
	public long getIsautocontinue() {
		return isautocontinue;
	}
	/**
	 * @param mbalance 要设置的 isautocontinue。
	 */
	public void setIsautocontinue(long isautocontinue) {
		this.isautocontinue = isautocontinue;
	}
	/**
	 * @return 返回 autocontinuetype。
	 */
	public long getAutocontinuetype() {
		return autocontinuetype;
	}
	/**
	 * @param mbalance 要设置的 autocontinuetype。
	 */
	public void setAutocontinuetype(long autocontinuetype) {
		this.autocontinuetype = autocontinuetype;
	}

}
