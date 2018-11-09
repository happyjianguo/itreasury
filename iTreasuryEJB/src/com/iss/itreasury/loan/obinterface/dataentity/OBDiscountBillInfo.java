/*
 * Copyright (c) 1999-2002 ISoftStone. All Rights Reserved.
 *
 * ˵������������Ʊ����Ϣ
 *
 * ���ߣ�ninghao
 *
 * ������Ա��
 *
 */

package com.iss.itreasury.loan.obinterface.dataentity;

import java.sql.*;

/**
 *
 * @author  yzhang
 */
public class OBDiscountBillInfo implements java.io.Serializable {

	private long ID; //ID��ʶ
    private long LoanID; //LoanID��ʶ    
    private long AcceptPotype; //Ʊ������ 
	private long DiscountApplyID; //���������ʶ
	private long DiscountCredenceID; //����ƾ֤��ʶ
	private long SerialNo; //���к�
	private String UserName; //ԭʼ��Ʊ��
    private String FormerOwner; //ֱ��ǰ��
	private String Bank; //�ж�����
	private long IsBeijing; //�ж��������ڵأ��Ƿ񱱾���
	private Timestamp Create; //��Ʊ��
	private Timestamp End; //������
	private long Days; //ʵ����������
	private String Code; //��Ʊ����
	private double Amount; //��Ʊ���
	private double DiscountRate; //��������
	private double Accrual; //������Ϣ
	private double RealAmount; //ʵ�����ֽ��
	private long StatusID; //�Ƿ�ɾ��
	private long AddDays; //�ڼ������Ӽ�Ϣ����
	private Timestamp DiscountDate; //��Ϣ��

	private long Count; //��¼��
	private double TotalAmount; //�ܻ�Ʊ���
	private double TotalAccrual; //��Ʊ����Ϣ
	private double TotalRealAmount; //��Ʊ��ʵ�����
	
	private long PageCount;
	private long PageNo;


	/**
	 * Returns the accrual.
	 * @return double
	 */
	public double getAccrual()
	{
		return Accrual;
	}

	/**
	 * Returns the addDays.
	 * @return long
	 */
	public long getAddDays()
	{
		return AddDays;
	}

	/**
	 * Returns the amount.
	 * @return double
	 */
	public double getAmount()
	{
		return Amount;
	}

	/**
	 * Returns the bank.
	 * @return String
	 */
	public String getBank()
	{
		if ( Bank == null)
		{
			Bank = "";
		}
		return Bank;
	}

	/**
	 * Returns the code.
	 * @return String
	 */
	public String getCode()
	{
		if ( Code == null)
		{
			Code = "";
		}
		return Code;
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
	 * Returns the create.
	 * @return Timestamp
	 */
	public Timestamp getCreate()
	{
		return Create;
	}

	/**
	 * Returns the days.
	 * @return long
	 */
	public long getDays()
	{
		return Days;
	}

	/**
	 * Returns the discountApplyID.
	 * @return long
	 */
	public long getDiscountApplyID()
	{
		return DiscountApplyID;
	}

	/**
	 * Returns the discountCredenceID.
	 * @return long
	 */
	public long getDiscountCredenceID()
	{
		return DiscountCredenceID;
	}

	/**
	 * Returns the discountDate.
	 * @return Timestamp
	 */
	public Timestamp getDiscountDate()
	{
		return DiscountDate;
	}

	/**
	 * Returns the discountRate.
	 * @return double
	 */
	public double getDiscountRate()
	{
		return DiscountRate;
	}

	/**
	 * Returns the end.
	 * @return Timestamp
	 */
	public Timestamp getEnd()
	{
		return End;
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
	 * Returns the isBeijing.
	 * @return long
	 */
	public long getIsBeijing()
	{
		//ע�⣺����-1���Ǳ���-0
		return IsBeijing;
	}

	/**
	 * Returns the PageCount.
	 * @return long
	 */
	public long getPageCount()
	{
		return PageCount;
	}

	/**
	 * Returns the lPageNo.
	 * @return long
	 */
	public long getPageNo()
	{
		return PageNo;
	}

	/**
	 * Returns the realAmount.
	 * @return double
	 */
	public double getRealAmount()
	{
		return RealAmount;
	}

	/**
	 * Returns the serialNo.
	 * @return long
	 */
	public long getSerialNo()
	{
		return SerialNo;
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
	 * Returns the totalAccrual.
	 * @return double
	 */
	public double getTotalAccrual()
	{
		return TotalAccrual;
	}

	/**
	 * Returns the totalAmount.
	 * @return double
	 */
	public double getTotalAmount()
	{
		return TotalAmount;
	}

	/**
	 * Returns the totalRealAmount.
	 * @return double
	 */
	public double getTotalRealAmount()
	{
		return TotalRealAmount;
	}

	/**
	 * Returns the userName.
	 * @return String
	 */
	public String getUserName()
	{
		if ( UserName == null)
		{
			UserName = "";
		}
		return UserName;
	}

	/**
	 * Sets the accrual.
	 * @param accrual The accrual to set
	 */
	public void setAccrual(double accrual)
	{
		Accrual = accrual;
	}

	/**
	 * Sets the addDays.
	 * @param addDays The addDays to set
	 */
	public void setAddDays(long addDays)
	{
		AddDays = addDays;
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
	 * Sets the bank.
	 * @param bank The bank to set
	 */
	public void setBank(String bank)
	{
		Bank = bank;
	}

	/**
	 * Sets the code.
	 * @param code The code to set
	 */
	public void setCode(String code)
	{
		Code = code;
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
	 * Sets the create.
	 * @param create The create to set
	 */
	public void setCreate(Timestamp create)
	{
		Create = create;
	}

	/**
	 * Sets the days.
	 * @param days The days to set
	 */
	public void setDays(long days)
	{
		Days = days;
	}

	/**
	 * Sets the discountApplyID.
	 * @param discountApplyID The discountApplyID to set
	 */
	public void setDiscountApplyID(long discountApplyID)
	{
		DiscountApplyID = discountApplyID;
	}

	/**
	 * Sets the discountCredenceID.
	 * @param discountCredenceID The discountCredenceID to set
	 */
	public void setDiscountCredenceID(long discountCredenceID)
	{
		DiscountCredenceID = discountCredenceID;
	}

	/**
	 * Sets the discountDate.
	 * @param discountDate The discountDate to set
	 */
	public void setDiscountDate(Timestamp discountDate)
	{
		DiscountDate = discountDate;
	}

	/**
	 * Sets the discountRate.
	 * @param discountRate The discountRate to set
	 */
	public void setDiscountRate(double discountRate)
	{
		DiscountRate = discountRate;
	}

	/**
	 * Sets the end.
	 * @param end The end to set
	 */
	public void setEnd(Timestamp end)
	{
		End = end;
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
	 * Sets the isBeijing.
	 * @param isBeijing The isBeijing to set
	 */
	public void setIsBeijing(long isBeijing)
	{
		IsBeijing = isBeijing;
	}

	/**
	 * Sets the PageCount.
	 * @param PageCount The PageCount to set
	 */
	public void setPageCount(long PageCount)
	{
		this.PageCount = PageCount;
	}

	/**
	 * Sets the PageNo.
	 * @param PageNo The PageNo to set
	 */
	public void setPageNo(long PageNo)
	{
		this.PageNo = PageNo;
	}

	/**
	 * Sets the realAmount.
	 * @param realAmount The realAmount to set
	 */
	public void setRealAmount(double realAmount)
	{
		RealAmount = realAmount;
	}

	/**
	 * Sets the serialNo.
	 * @param serialNo The serialNo to set
	 */
	public void setSerialNo(long serialNo)
	{
		SerialNo = serialNo;
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
	 * Sets the totalAccrual.
	 * @param totalAccrual The totalAccrual to set
	 */
	public void setTotalAccrual(double totalAccrual)
	{
		TotalAccrual = totalAccrual;
	}

	/**
	 * Sets the totalAmount.
	 * @param totalAmount The totalAmount to set
	 */
	public void setTotalAmount(double totalAmount)
	{
		TotalAmount = totalAmount;
	}

	/**
	 * Sets the totalRealAmount.
	 * @param totalRealAmount The totalRealAmount to set
	 */
	public void setTotalRealAmount(double totalRealAmount)
	{
		TotalRealAmount = totalRealAmount;
	}

	/**
	 * Sets the userName.
	 * @param userName The userName to set
	 */
	public void setUserName(String userName)
	{
		UserName = userName;
	}

    /**
     * @param 
     * function �õ�/����
     * return long
     */
    public long getLoanID()
    {
        return LoanID;
    }

    /**
     * @param 
     * function �õ�/����
     * return void
     */
    public void setLoanID(long l)
    {
        LoanID = l;
    }

    /**
     * @param 
     * function �õ�/����
     * return long
     */
    public long getAcceptPotype()
    {
        return AcceptPotype;
    }

    /**
     * @param 
     * function �õ�/����
     * return void
     */
    public void setAcceptPotype(long l)
    {
        AcceptPotype = l;
    }

    /**
     * @param 
     * function �õ�/����
     * return String
     */
    public String getFormerOwner()
    {
        return FormerOwner;
    }

    /**
     * @param 
     * function �õ�/����
     * return void
     */
    public void setFormerOwner(String string)
    {
        FormerOwner = string;
    }

}
