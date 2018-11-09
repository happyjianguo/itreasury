/*
 * Created on 2003-9-26
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.settlement.transdiscount.dataentity;
import java.io.Serializable;
import java.sql.Timestamp;
/**
 * ���⽻�׵İ�״̬��ѯ����ʵ���ࣺ
 * 1�����б�����ΪPrivate,����ֻ����setXXX�������õ�ֻ����getXXX������
 * 2���������������͡�Ĭ��ֵ��˵��
 * @author wlming
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class QueryConditionInfo implements Serializable
{
	private long OfficeID = -1; //���´�ID
	private long CurrencyID = -1; //����ID
	private long UserID = -1; //�û�ID
	private long TypeID = -1; // ��ѯ���ͣ�0��������Ĳ��ң���1�������˵Ĳ��ң�
	private long StatusID = -1; //����״̬
	private Timestamp Date = null; //��ѯ����
	private long TransactionTypeID = -1; //  ҵ������
	private long OrderByType = -1;//��������
	private boolean isDesc = false;//������
	private boolean isPayForm = false;//������

	public boolean isPayForm() {
		return isPayForm;
	}
	public void setPayForm(boolean isPayForm) {
		this.isPayForm = isPayForm;
	}
	/**
	 * @return
	 */
	public long getCurrencyID()
	{
		return CurrencyID;
	}
	/**
	 * @return
	 */
	public Timestamp getDate()
	{
		return Date;
	}
	/**
	 * @return
	 */
	public long getOfficeID()
	{
		return OfficeID;
	}
	/**
	 * @return
	 */
	public long getStatusID()
	{
		return StatusID;
	}
	/**
	 * @return
	 */
	public long getTypeID()
	{
		return TypeID;
	}
	/**
	 * @return
	 */
	public long getUserID()
	{
		return UserID;
	}
	/**
	 * @param l
	 */
	public void setCurrencyID(long l)
	{
		CurrencyID = l;
	}
	/**
	 * @param timestamp
	 */
	public void setDate(Timestamp timestamp)
	{
		Date = timestamp;
	}
	/**
	 * @param l
	 */
	public void setOfficeID(long l)
	{
		OfficeID = l;
	}
	/**
	 * @param l
	 */
	public void setStatusID(long l)
	{
		StatusID = l;
	}
	/**
	 * @param l
	 */
	public void setTypeID(long l)
	{
		TypeID = l;
	}
	/**
	 * @param l
	 */
	public void setUserID(long l)
	{
		UserID = l;
	}
	/**
	 * @return
	 */
	public long getTransactionTypeID()
	{
		return this.TransactionTypeID;
	}
	/**
	 * @param transactionTypeID
	 */
	public void setTransactionTypeID(long transactionTypeID)
	{
		this.TransactionTypeID = transactionTypeID;
	}
	/**
	 * @return
	 */
	public boolean getDesc()
	{
		return this.isDesc;
	}

	/**
	 * @return
	 */
	public long getOrderByType()
	{
		return OrderByType;
	}

	/**
	 * @param l
	 */
	public void setDesc(boolean b)
	{
		this.isDesc = b;
	}

	/**
	 * @param l
	 */
	public void setOrderByType(long l)
	{
		OrderByType = l;
	}

}
