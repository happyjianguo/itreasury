/*
 * Copyright (c) 1999-2002 ISoftStone. All Rights Reserved.
 *
 * ˵����������֤��Ϣ
 *
 * ���ߣ�����
 *
 * ������Ա��
 *
 */
package com.iss.itreasury.loan.obinterface.dataentity;

import java.beans.*;

/**
 *
 * @author  yzhang
 */
public class OBAssureInfo implements java.io.Serializable
{

	private long ID; //��ʶ

	private long CurrencyID; //����

	private long LoanID; //�����ʶ
    private long AssureTypeID; //��������
	//private int Type; //��������
	private long ClientID; //�ͻ���ʶ
	private String ClientCode; //�ͻ����
	private String ClientName; //�ͻ�����
	private String ContactName; //��ϵ������
	private String PhoneNo; //�绰

	private double Amount; //�������/��������ֵ��/ʵ�ʵ�Ѻ��

	private String Name; //��Ѻ��������
	private String Quality; //����
	private String Status; //״��

	private double PledgeSum; //��Ѻ�ܶ�
	private double PledgeRate; //��Ѻ��

	private long Count; //��¼��

	private long StatusID; //��¼״̬
	private long FillQuestionary;

	/**
	 * Returns the amount.
	 * @return double
	 */
	public double getAmount()
	{
		return Amount;
	}

	/**
	 * Returns the clientCode.
	 * @return String
	 */
	public String getClientCode()
	{
		if ( ClientCode == null)
		{
			ClientCode = "";
		}
		return ClientCode;
	}

	/**
	 * Returns the clientID.
	 * @return long
	 */
	public long getClientID()
	{
		return ClientID;
	}

	/**
	 * Returns the clientName.
	 * @return String
	 */
	public String getClientName()
	{
		if ( ClientName == null)
		{
			ClientName = "";
		}
		return ClientName;
	}

	/**
	 * Returns the contactName.
	 * @return String
	 */
	public String getContactName()
	{
		if ( ContactName == null)
		{
			ContactName = "";
		}
		return ContactName;
	}

	/**
	 * Returns the count.
	 * @return long
	 */
	public long getCount()
	{
		return Count;
	}

	/**
	 * Returns the currencyID.
	 * @return long
	 */
	public long getCurrencyID()
	{
		return CurrencyID;
	}

	/**
	 * Returns the iD.
	 * @return long
	 */
	public long getID()
	{
		return ID;
	}

	/**
	 * Returns the loanID.
	 * @return long
	 */
	public long getLoanID()
	{
		return LoanID;
	}

	/**
	 * Returns the name.
	 * @return String
	 */
	public String getName()
	{
		if ( Name == null)
		{
			Name = "";
		}
		return Name;
	}

	/**
	 * Returns the phoneNo.
	 * @return String
	 */
	public String getPhoneNo()
	{
		if ( PhoneNo == null)
		{
			PhoneNo = "";
		}
		return PhoneNo;
	}

	/**
	 * Returns the pledgeRate.
	 * @return double
	 */
	public double getPledgeRate()
	{
		return PledgeRate;
	}

	/**
	 * Returns the pledgeSum.
	 * @return double
	 */
	public double getPledgeSum()
	{
		return PledgeSum;
	}

	/**
	 * Returns the quality.
	 * @return String
	 */
	public String getQuality()
	{
		if ( Quality == null)
		{
			Quality = "";
		}
		return Quality;
	}

	/**
	 * Returns the status.
	 * @return String
	 */
	public String getStatus()
	{
		if ( Status == null)
		{
			Status = "";
		}
		return Status;
	}

	/**
	 * Returns the type.
	 * @return int
	 * /
	public int getType()
	{
		return Type;
	}

	/**
	 * Sets the amount.
	 * @param amount The amount to set
	 */
	public void setAmount(double amount)
	{
		Amount = amount;
	}

	/**
	 * Sets the clientCode.
	 * @param clientCode The clientCode to set
	 */
	public void setClientCode(String clientCode)
	{
		ClientCode = clientCode;
	}

	/**
	 * Sets the clientID.
	 * @param clientID The clientID to set
	 */
	public void setClientID(long clientID)
	{
		ClientID = clientID;
	}

	/**
	 * Sets the clientName.
	 * @param clientName The clientName to set
	 */
	public void setClientName(String clientName)
	{
		ClientName = clientName;
	}

	/**
	 * Sets the contactName.
	 * @param contactName The contactName to set
	 */
	public void setContactName(String contactName)
	{
		ContactName = contactName;
	}

	/**
	 * Sets the count.
	 * @param count The count to set
	 */
	public void setCount(long count)
	{
		Count = count;
	}

	/**
	 * Sets the currencyID.
	 * @param currencyID The currencyID to set
	 */
	public void setCurrencyID(long currencyID)
	{
		CurrencyID = currencyID;
	}

	/**
	 * Sets the iD.
	 * @param iD The iD to set
	 */
	public void setID(long iD)
	{
		ID = iD;
	}

	/**
	 * Sets the loanID.
	 * @param loanID The loanID to set
	 */
	public void setLoanID(long loanID)
	{
		LoanID = loanID;
	}

	/**
	 * Sets the name.
	 * @param name The name to set
	 */
	public void setName(String name)
	{
		Name = name;
	}

	/**
	 * Sets the phoneNo.
	 * @param phoneNo The phoneNo to set
	 */
	public void setPhoneNo(String phoneNo)
	{
		PhoneNo = phoneNo;
	}

	/**
	 * Sets the pledgeRate.
	 * @param pledgeRate The pledgeRate to set
	 */
	public void setPledgeRate(double pledgeRate)
	{
		PledgeRate = pledgeRate;
	}

	/**
	 * Sets the pledgeSum.
	 * @param pledgeSum The pledgeSum to set
	 */
	public void setPledgeSum(double pledgeSum)
	{
		PledgeSum = pledgeSum;
	}

	/**
	 * Sets the quality.
	 * @param quality The quality to set
	 */
	public void setQuality(String quality)
	{
		Quality = quality;
	}

	/**
	 * Sets the status.
	 * @param status The status to set
	 */
	public void setStatus(String status)
	{
		Status = status;
	}

	/**
	 * Sets the type.
	 * @param type The type to set
	 * /
	public void setType(int type)
	{
		Type = type;
	}

	/**
	 * Returns the statusID.
	 * @return long
	 */
	public long getStatusID()
	{
		return StatusID;
	}

	/**
	 * Sets the statusID.
	 * @param statusID The statusID to set
	 */
	public void setStatusID(long statusID)
	{
		StatusID = statusID;
	}

	/**
	 * Returns the fillQuestionary.
	 * @return long
	 */
	public long getFillQuestionary()
	{
		return FillQuestionary;
	}

	/**
	 * Sets the fillQuestionary.
	 * @param fillQuestionary The fillQuestionary to set
	 */
	public void setFillQuestionary(long fillQuestionary)
	{
		FillQuestionary = fillQuestionary;
	}

    /**
     * @param 
     * function �õ�/����
     * return long
     */
    public long getAssureTypeID()
    {
        return AssureTypeID;
    }

    /**
     * @param 
     * function �õ�/����
     * return void
     */
    public void setAssureTypeID(long l)
    {
        AssureTypeID = l;
    }

}
