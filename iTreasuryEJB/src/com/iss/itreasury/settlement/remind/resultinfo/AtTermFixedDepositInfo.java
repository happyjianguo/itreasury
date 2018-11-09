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
	private long SubAccountID = -1; //���˻�ID
	private String FixedDepositNo = ""; //���ڴ浥��
	private long AccountID = -1; //�˻�ID
	private String AccountNo = ""; //�˻����
	private long ClientID = -1; //�ͻ�ID
	private String ClientNo = ""; //�ͻ����
	private String ClientName = ""; //�ͻ�����
	private double mbalance = 0.00;		//�����
	private double af_mrate = 0.00;		//�������
	private Timestamp EndDate = null; //��������
	private long depsitterm = -1; //���ڴ������   NDEPOSITTERM
	private long isautocontinue = -1; //�Ƿ����Զ�����   isautocontinue
	private long autocontinuetype = -1; //�Զ����淽ʽ   autocontinuetype
	
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
	 * @return ���� af_mrate��
	 */
	public double getAf_mrate() {
		return af_mrate;
	}

	/**
	 * @param af_mrate Ҫ���õ� af_mrate��
	 */
	public void setAf_mrate(double af_mrate) {
		this.af_mrate = af_mrate;
	}

	/**
	 * @return ���� mbalance��
	 */
	public double getMbalance() {
		return mbalance;
	}

	/**
	 * @param mbalance Ҫ���õ� mbalance��
	 */
	public void setMbalance(double mbalance) {
		this.mbalance = mbalance;
	}
	/**
	 * @return ���� depsitterm��
	 */
	public long getDepsitterm() {
		return depsitterm;
	}
	/**
	 * @param mbalance Ҫ���õ� depsitterm��
	 */
	public void setDepsitterm(long depsitterm) {
		this.depsitterm = depsitterm;
	}
	/**
	 * @return ���� isautocontinue��
	 */
	public long getIsautocontinue() {
		return isautocontinue;
	}
	/**
	 * @param mbalance Ҫ���õ� isautocontinue��
	 */
	public void setIsautocontinue(long isautocontinue) {
		this.isautocontinue = isautocontinue;
	}
	/**
	 * @return ���� autocontinuetype��
	 */
	public long getAutocontinuetype() {
		return autocontinuetype;
	}
	/**
	 * @param mbalance Ҫ���õ� autocontinuetype��
	 */
	public void setAutocontinuetype(long autocontinuetype) {
		this.autocontinuetype = autocontinuetype;
	}

}
