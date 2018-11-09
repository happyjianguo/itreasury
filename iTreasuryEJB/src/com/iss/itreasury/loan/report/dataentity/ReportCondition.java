package com.iss.itreasury.loan.report.dataentity;

/**
 * @author gdzhao
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
import java.io.Serializable;
import java.sql.Timestamp;

public class ReportCondition implements Serializable
{
	//officeID
	long officeID=-1;
	
	//currencyID
	long currencyID=-1;
	
	//��Ϣ�տ�ʼ--�ĳɿ�ʼ����
	Timestamp clearDateFrom=null;
	
	//��Ϣ����ֹ--�ĳɽ�������
	Timestamp clearDateTo=null;
	
	//��ͬ��ſ�ʼ
	String contractCodeFrom=null;
	
	//��ͬ��Ž���
	String contractCodeTo=null;
	
	//��ͬID ��ʼ
	long contractIDFrom=-1;
	
	//��ͬID����
	long contractIDTo=-1;
	
	//ί�е�λ��ʼ
	long consignIDFrom=-1;
	
	//ί�е�λ����
	long consignIDTo=-1;
	
	//��λ��ʼ
	long borrowIDFrom=-1;
	
	//��λ����	
	long borrowIDTo=-1;
	/**
	 * @return
	 */
	public long getBorrowIDFrom()
	{
		return borrowIDFrom;
	}

	/**
	 * @return
	 */
	public long getBorrowIDTo()
	{
		return borrowIDTo;
	}

	/**
	 * @return
	 */
	public Timestamp getClearDateFrom()
	{
		return clearDateFrom;
	}

	/**
	 * @return
	 */
	public Timestamp getClearDateTo()
	{
		return clearDateTo;
	}

	/**
	 * @return
	 */
	public long getConsignIDFrom()
	{
		return consignIDFrom;
	}

	/**
	 * @return
	 */
	public long getConsignIDTo()
	{
		return consignIDTo;
	}

	/**
	 * @return
	 */
	public String getContractCodeFrom()
	{
		return contractCodeFrom;
	}

	/**
	 * @return
	 */
	public String getContractCodeTo()
	{
		return contractCodeTo;
	}

	/**
	 * @return
	 */
	public long getContractIDFrom()
	{
		return contractIDFrom;
	}

	/**
	 * @return
	 */
	public long getContractIDTo()
	{
		return contractIDTo;
	}

	/**
	 * @param l
	 */
	public void setBorrowIDFrom(long l)
	{
		borrowIDFrom = l;
	}

	/**
	 * @param l
	 */
	public void setBorrowIDTo(long l)
	{
		borrowIDTo = l;
	}

	/**
	 * @param timestamp
	 */
	public void setClearDateFrom(Timestamp timestamp)
	{
		clearDateFrom = timestamp;
	}

	/**
	 * @param timestamp
	 */
	public void setClearDateTo(Timestamp timestamp)
	{
		clearDateTo = timestamp;
	}

	/**
	 * @param l
	 */
	public void setConsignIDFrom(long l)
	{
		consignIDFrom = l;
	}

	/**
	 * @param l
	 */
	public void setConsignIDTo(long l)
	{
		consignIDTo = l;
	}

	/**
	 * @param string
	 */
	public void setContractCodeFrom(String string)
	{
		contractCodeFrom = string;
	}

	/**
	 * @param string
	 */
	public void setContractCodeTo(String string)
	{
		contractCodeTo = string;
	}

	/**
	 * @param l
	 */
	public void setContractIDFrom(long l)
	{
		contractIDFrom = l;
	}

	/**
	 * @param l
	 */
	public void setContractIDTo(long l)
	{
		contractIDTo = l;
	}

	/**
	 * @return
	 */
	public long getCurrencyID()
	{
		return currencyID;
	}

	/**
	 * @return
	 */
	public long getOfficeID()
	{
		return officeID;
	}

	/**
	 * @param l
	 */
	public void setCurrencyID(long l)
	{
		currencyID = l;
	}

	/**
	 * @param l
	 */
	public void setOfficeID(long l)
	{
		officeID = l;
	}

}