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
public class OBAccountBalanceLoanInfo implements Serializable
{
	//账户类型
	private long naccounttypeid=-1;
	//账户ID
	private long AccountID=-1;
	//账号
	private String saccountno="";
	//合同号
	private String SCONTRACTCODE="";
	//合同ID
	private long ContractID=-1;
	//借款单位
	private long nborrowclientid=-1;
	//起始日
	private Timestamp dtStartDate=null;
	//到期日
	private Timestamp dtEndDate=null;
	//期限
	private long nIntervalNum=-1;
	//贷款金额
	private double mAmount=0.0;
	//贷款余额
	private double loanBalance=0.0;
	//利率
	private double rate=0.0;
	//合同状态
	private long nstatusid=-1;
	//小计
	private double subSum=0.0;	
	
	/**
	 * @return
	 */
	public Timestamp getDtEndDate()
	{
		return dtEndDate;
	}

	/**
	 * @return
	 */
	public Timestamp getDtStartDate()
	{
		return dtStartDate;
	}

	/**
	 * @return
	 */
	public double getLoanBalance()
	{
		return loanBalance;
	}

	/**
	 * @return
	 */
	public double getMAmount()
	{
		return mAmount;
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
	public long getNborrowclientid()
	{
		return nborrowclientid;
	}

	/**
	 * @return
	 */
	public long getNIntervalNum()
	{
		return nIntervalNum;
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
	public double getRate()
	{
		return rate;
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
	public String getSCONTRACTCODE()
	{
		return SCONTRACTCODE;
	}

	/**
	 * @return
	 */
	public double getSubSum()
	{
		return subSum;
	}

	/**
	 * @param timestamp
	 */
	public void setDtEndDate(Timestamp timestamp)
	{
		dtEndDate = timestamp;
	}

	/**
	 * @param timestamp
	 */
	public void setDtStartDate(Timestamp timestamp)
	{
		dtStartDate = timestamp;
	}

	/**
	 * @param d
	 */
	public void setLoanBalance(double d)
	{
		loanBalance = d;
	}

	/**
	 * @param d
	 */
	public void setMAmount(double d)
	{
		mAmount = d;
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
	public void setNborrowclientid(long l)
	{
		nborrowclientid = l;
	}

	/**
	 * @param l
	 */
	public void setNIntervalNum(long l)
	{
		nIntervalNum = l;
	}

	/**
	 * @param l
	 */
	public void setNstatusid(long l)
	{
		nstatusid = l;
	}

	/**
	 * @param d
	 */
	public void setRate(double d)
	{
		rate = d;
	}

	/**
	 * @param string
	 */
	public void setSaccountno(String string)
	{
		saccountno = string;
	}

	/**
	 * @param string
	 */
	public void setSCONTRACTCODE(String string)
	{
		SCONTRACTCODE = string;
	}

	/**
	 * @param d
	 */
	public void setSubSum(double d)
	{
		subSum = d;
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
	public long getContractID() {
		return ContractID;
	}

	/**
	 * @param l
	 */
	public void setContractID(long l) {
		ContractID = l;
	}

}
