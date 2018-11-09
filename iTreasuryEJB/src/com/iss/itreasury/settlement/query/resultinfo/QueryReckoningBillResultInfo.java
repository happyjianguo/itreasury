package com.iss.itreasury.settlement.query.resultinfo;
import java.io.Serializable;
import java.sql.Timestamp;

public class QueryReckoningBillResultInfo implements Serializable
{
	long ReckoningTypeID = -1;           //��������ID
	Timestamp InputDate = null;          //¼������
	String ExtBankNo = "";               //�����к�
	String ExtAccountNo = "";            //�Է��˻���
	String ExtClientName = "";           //�Է��˻�����
	long AccountID = -1;                 //��Ӧ�ڲ��˻�ID
    String AccountNo = "";               //��Ӧ�ڲ��˻���  
	String ReckoningBillTypeDesc = "";   //ƾ֤����
	double Amount = 0.0;                 //���
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
	public double getAmount()
	{
		return Amount;
	}

	/**
	 * @return
	 */
	public String getExtAccountNo()
	{
		return ExtAccountNo;
	}

	/**
	 * @return
	 */
	public String getExtBankNo()
	{
		return ExtBankNo;
	}

	/**
	 * @return
	 */
	public String getExtClientName()
	{
		return ExtClientName;
	}

	/**
	 * @return
	 */
	public Timestamp getInputDate()
	{
		return InputDate;
	}

	/**
	 * @return
	 */
	public String getReckoningBillTypeDesc()
	{
		return ReckoningBillTypeDesc;
	}

	/**
	 * @return
	 */
	public long getReckoningTypeID()
	{
		return ReckoningTypeID;
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
	 * @param d
	 */
	public void setAmount(double d)
	{
		Amount = d;
	}

	/**
	 * @param string
	 */
	public void setExtAccountNo(String string)
	{
		ExtAccountNo = string;
	}

	/**
	 * @param string
	 */
	public void setExtBankNo(String string)
	{
		ExtBankNo = string;
	}

	/**
	 * @param string
	 */
	public void setExtClientName(String string)
	{
		ExtClientName = string;
	}

	/**
	 * @param timestamp
	 */
	public void setInputDate(Timestamp timestamp)
	{
		InputDate = timestamp;
	}

	/**
	 * @param string
	 */
	public void setReckoningBillTypeDesc(String string)
	{
		ReckoningBillTypeDesc = string;
	}

	/**
	 * @param l
	 */
	public void setReckoningTypeID(long l)
	{
		ReckoningTypeID = l;
	}

}
