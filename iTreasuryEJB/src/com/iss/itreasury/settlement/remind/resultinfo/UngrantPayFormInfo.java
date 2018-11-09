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
public class UngrantPayFormInfo implements java.io.Serializable
{
	private long lContractID = -1;//合同ID
	private String strContractNo = "";//合同编号
	private long lContractTypeID = -1;//合同类型
	private long lPayFormID = -1;//放款通知单ID
	private String strPayFormNo = "";//放款通知单编号
	private long lClientID = -1;//贷款客户ID
	private String strClientNo = "";//贷款客户编号
	private Timestamp tsOutDate = null;//放款日期
	
	private double amount = 0.00;		//还款金额
	private double rate = 0.00;		//还款利率
	private Timestamp dtStart = null;		//还款开始日
	private Timestamp dtEnd = null;		//还款到期日
	

	
	/**
	 * @return 返回 amount。
	 */
	public double getAmount() {
		return amount;
	}

	/**
	 * @param amount 要设置的 amount。
	 */
	public void setAmount(double amount) {
		this.amount = amount;
	}

	/**
	 * @return 返回 dtEnd。
	 */
	public Timestamp getDtEnd() {
		return dtEnd;
	}

	/**
	 * @param dtEnd 要设置的 dtEnd。
	 */
	public void setDtEnd(Timestamp dtEnd) {
		this.dtEnd = dtEnd;
	}

	/**
	 * @return 返回 dtStart。
	 */
	public Timestamp getDtStart() {
		return dtStart;
	}

	/**
	 * @param dtStart 要设置的 dtStart。
	 */
	public void setDtStart(Timestamp dtStart) {
		this.dtStart = dtStart;
	}

	/**
	 * @return 返回 rate。
	 */
	public double getRate() {
		return rate;
	}

	/**
	 * @param rate 要设置的 rate。
	 */
	public void setRate(double rate) {
		this.rate = rate;
	}

	/**
	 * @return
	 */
	public long getLClientID()
	{
		return lClientID;
	}

	/**
	 * @return
	 */
	public long getLContractID()
	{
		return lContractID;
	}

	/**
	 * @return
	 */
	public long getLPayFormID()
	{
		return lPayFormID;
	}

	/**
	 * @return
	 */
	public String getStrClientNo()
	{
		return strClientNo;
	}

	/**
	 * @return
	 */
	public String getStrContractNo()
	{
		return strContractNo;
	}

	/**
	 * @return
	 */
	public String getStrPayFormNo()
	{
		return strPayFormNo;
	}

	/**
	 * @return
	 */
	public Timestamp getTsOutDate()
	{
		return tsOutDate;
	}

	/**
	 * @param l
	 */
	public void setLClientID(long l)
	{
		lClientID = l;
	}

	/**
	 * @param l
	 */
	public void setLContractID(long l)
	{
		lContractID = l;
	}

	/**
	 * @param l
	 */
	public void setLPayFormID(long l)
	{
		lPayFormID = l;
	}

	/**
	 * @param string
	 */
	public void setStrClientNo(String string)
	{
		strClientNo = string;
	}

	/**
	 * @param string
	 */
	public void setStrContractNo(String string)
	{
		strContractNo = string;
	}

	/**
	 * @param string
	 */
	public void setStrPayFormNo(String string)
	{
		strPayFormNo = string;
	}

	/**
	 * @param timestamp
	 */
	public void setTsOutDate(Timestamp timestamp)
	{
		tsOutDate = timestamp;
	}

	/**
	 * @return
	 */
	public long getLContractTypeID()
	{
		return lContractTypeID;
	}

	/**
	 * @param l
	 */
	public void setLContractTypeID(long l)
	{
		lContractTypeID = l;
	}

}
