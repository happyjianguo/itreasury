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
	private long lContractID = -1;//��ͬID
	private String strContractNo = "";//��ͬ���
	private long lContractTypeID = -1;//��ͬ����
	private long lPayFormID = -1;//�ſ�֪ͨ��ID
	private String strPayFormNo = "";//�ſ�֪ͨ�����
	private long lClientID = -1;//����ͻ�ID
	private String strClientNo = "";//����ͻ����
	private Timestamp tsOutDate = null;//�ſ�����
	
	private double amount = 0.00;		//������
	private double rate = 0.00;		//��������
	private Timestamp dtStart = null;		//���ʼ��
	private Timestamp dtEnd = null;		//�������
	

	
	/**
	 * @return ���� amount��
	 */
	public double getAmount() {
		return amount;
	}

	/**
	 * @param amount Ҫ���õ� amount��
	 */
	public void setAmount(double amount) {
		this.amount = amount;
	}

	/**
	 * @return ���� dtEnd��
	 */
	public Timestamp getDtEnd() {
		return dtEnd;
	}

	/**
	 * @param dtEnd Ҫ���õ� dtEnd��
	 */
	public void setDtEnd(Timestamp dtEnd) {
		this.dtEnd = dtEnd;
	}

	/**
	 * @return ���� dtStart��
	 */
	public Timestamp getDtStart() {
		return dtStart;
	}

	/**
	 * @param dtStart Ҫ���õ� dtStart��
	 */
	public void setDtStart(Timestamp dtStart) {
		this.dtStart = dtStart;
	}

	/**
	 * @return ���� rate��
	 */
	public double getRate() {
		return rate;
	}

	/**
	 * @param rate Ҫ���õ� rate��
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
