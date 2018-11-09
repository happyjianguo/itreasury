/*
 * Created on 2003-9-4
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.iss.itreasury.settlement.account.dataentity;

import java.sql.Timestamp;
import java.io.Serializable;

/**
 * @author ruixie
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class SubAccountCurrentInfo implements Serializable
{
	private long ID = -1; //���˻�ID
	private long AccountID = -1; //���˻�ID
	private double Interest = 0.0; //��ǰ��Ϣ
	private double Balance = 0.0; //�˻���ǰ���
	private double OpenAmount = 0.0; //�������
	private Timestamp OpenDate = null; //��������
	private Timestamp FinishDate = null; //��������
	private long IsInterest = -1; //�Ƿ��Ϣ
	private Timestamp ClearInterestDate = null; //��Ϣ����
	private long StatusID = -1; //״̬
	private long InterestAccountID = -1; //����Ϣ�˻�ID
	private long IsOverDraft = -1; //�Ƿ�����͸֧
	private long FirstLimitTypeID = -1; //��һ��͸֧����
	private double FirstLimitAmount = 0.0; //͸֧���
	private long SecondLimitTypeID = -1;
	private double SecondLimitAmount = 0.0;
	private long ThirdLimitTypeID = -1;
	private double ThirdLimitAmount = 0.0;
	private long InterestRatePlanID = -1; //���ʼƻ�ID
	private Timestamp InterestRatePlanDate = null; //���ʼƻ���Ч����
	private double CapitalLimitAmount = 0.0; //�ʽ����ƽ��
	private long IsNegotiate = -1; //�Ƿ�Э�����
	private double NegotiateAmount = 0.0; //Э�����
	private double NegotiateUnit = 0.0; //Э����λ��Ԫ��
	private double NegotiateRate = 0.0; //Э��������ʼƻ�
	private Timestamp NegotiationStartDate = null;//Э�������ʼ��
	private Timestamp NegotiationEndDate = null;//Э�������ֹ��
	private Timestamp NegotiateRateDate = null; //Э��������ʼƻ���Ч����
	private double DailyUncheckAmount = 0.0; // �����ۼ�δ���˸�����
	private double NegotiateInterest = 0.0; //��ǰЭ����Ϣ
	private String SealNo = ""; //ӡ������
	private long SealBankID = -1;  //��������
	private double MonthLimitAmount = 0.0; // �¶��ۼ�֧�����ƽ��
	private double DayLimitAmount = 0.0;//���ۼ����ƽ��
	private long IsAllBranch = -1;//�Ƿ��������п����� add by rxie
	private Timestamp PreDrawDate = null; //��������
	private double DrawInterest = 0.0;  //������Ϣ
	
	public double getDrawInterest() 
	{
		return DrawInterest;
	}

	public void setDrawInterest(double drawInterest) 
	{
		DrawInterest = drawInterest;
	}

	public Timestamp getPreDrawDate() 
	{
		return PreDrawDate;
	}

	public void setPreDrawDate(Timestamp preDrawDate) 
	{
		PreDrawDate = preDrawDate;
	}

	/**
	 * Returns the accountID.
	 * @return long
	 */
	public long getAccountID()
	{
		return AccountID;
	}

	/**
	 * Returns the balance.
	 * @return double
	 */
	public double getBalance()
	{
		return Balance;
	}

	/**
	 * Returns the capitalLimitAmount.
	 * @return double
	 */
	public double getCapitalLimitAmount()
	{
		return CapitalLimitAmount;
	}

	/**
	 * Returns the firstLimitAmount.
	 * @return double
	 */
	public double getFirstLimitAmount()
	{
		return FirstLimitAmount;
	}

	/**
	 * Returns the firstLimitTypeID.
	 * @return long
	 */
	public long getFirstLimitTypeID()
	{
		return FirstLimitTypeID;
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
	 * Returns the interest.
	 * @return double
	 */
	public double getInterest()
	{
		return Interest;
	}

	/**
	 * Returns the interestAccountID.
	 * @return long
	 */
	public long getInterestAccountID()
	{
		return InterestAccountID;
	}

	/**
	 * Returns the interestRatePlanID.
	 * @return long
	 */
	public long getInterestRatePlanID()
	{
		return InterestRatePlanID;
	}

	/**
	 * Returns the isNegotiate.
	 * @return long
	 */
	public long getIsNegotiate()
	{
		return IsNegotiate;
	}

	/**
	 * Returns the isOverDraft.
	 * @return long
	 */
	public long getIsOverDraft()
	{
		return IsOverDraft;
	}

	/**
	 * Returns the negotiateAmount.
	 * @return double
	 */
	public double getNegotiateAmount()
	{
		return NegotiateAmount;
	}

	/**
	 * Returns the negotiateUnit.
	 * @return double
	 */
	public double getNegotiateUnit()
	{
		return NegotiateUnit;
	}

	/**
	 * Returns the secondLimitAmount.
	 * @return double
	 */
	public double getSecondLimitAmount()
	{
		return SecondLimitAmount;
	}

	/**
	 * Returns the secondLimitTypeID.
	 * @return long
	 */
	public long getSecondLimitTypeID()
	{
		return SecondLimitTypeID;
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
	 * Returns the thirdLimitAmount.
	 * @return double
	 */
	public double getThirdLimitAmount()
	{
		return ThirdLimitAmount;
	}

	/**
	 * Returns the thirdLimitTypeID.
	 * @return long
	 */
	public long getThirdLimitTypeID()
	{
		return ThirdLimitTypeID;
	}
	//--------------------------------------------------------------------------------
	/**
		 * Returns the thirdLimitTypeID.
		 * @return long
		 */
	public Timestamp getInterestRatePlanDate()
	{
		return InterestRatePlanDate;
	}

	/**
	 * Sets the accountID.
	 * @param accountID The accountID to set
	 */
	public void setAccountID(long accountID)
	{
		AccountID = accountID;
	}

	/**
	 * Sets the balance.
	 * @param balance The balance to set
	 */
	public void setBalance(double balance)
	{
		Balance = balance;
	}

	/**
	 * Sets the capitalLimitAmount.
	 * @param capitalLimitAmount The capitalLimitAmount to set
	 */
	public void setCapitalLimitAmount(double capitalLimitAmount)
	{
		CapitalLimitAmount = capitalLimitAmount;
	}

	/**
	 * Sets the firstLimitAmount.
	 * @param firstLimitAmount The firstLimitAmount to set
	 */
	public void setFirstLimitAmount(double firstLimitAmount)
	{
		FirstLimitAmount = firstLimitAmount;
	}

	/**
	 * Sets the firstLimitTypeID.
	 * @param firstLimitTypeID The firstLimitTypeID to set
	 */
	public void setFirstLimitTypeID(long firstLimitTypeID)
	{
		FirstLimitTypeID = firstLimitTypeID;
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
	 * Sets the interest.
	 * @param interest The interest to set
	 */
	public void setInterest(double interest)
	{
		Interest = interest;
	}

	/**
	 * Sets the interestAccountID.
	 * @param interestAccountID The interestAccountID to set
	 */
	public void setInterestAccountID(long interestAccountID)
	{
		InterestAccountID = interestAccountID;
	}

	/**
	 * Sets the interestRatePlanID.
	 * @param interestRatePlanID The interestRatePlanID to set
	 */
	public void setInterestRatePlanID(long interestRatePlanID)
	{
		InterestRatePlanID = interestRatePlanID;
	}

	/**
	 * Sets the isNegotiate.
	 * @param isNegotiate The isNegotiate to set
	 */
	public void setIsNegotiate(long isNegotiate)
	{
		IsNegotiate = isNegotiate;
	}

	/**
	 * Sets the isOverDraft.
	 * @param isOverDraft The isOverDraft to set
	 */
	public void setIsOverDraft(long isOverDraft)
	{
		IsOverDraft = isOverDraft;
	}

	/**
	 * Sets the negotiateAmount.
	 * @param negotiateAmount The negotiateAmount to set
	 */
	public void setNegotiateAmount(double negotiateAmount)
	{
		NegotiateAmount = negotiateAmount;
	}

	/**
	 * Sets the negotiateUnit.
	 * @param negotiateUnit The negotiateUnit to set
	 */
	public void setNegotiateUnit(double negotiateUnit)
	{
		NegotiateUnit = negotiateUnit;
	}

	/**
	 * Sets the secondLimitAmount.
	 * @param secondLimitAmount The secondLimitAmount to set
	 */
	public void setSecondLimitAmount(double secondLimitAmount)
	{
		SecondLimitAmount = secondLimitAmount;
	}

	/**
	 * Sets the secondLimitTypeID.
	 * @param secondLimitTypeID The secondLimitTypeID to set
	 */
	public void setSecondLimitTypeID(long secondLimitTypeID)
	{
		SecondLimitTypeID = secondLimitTypeID;
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
	 * Sets the thirdLimitAmount.
	 * @param thirdLimitAmount The thirdLimitAmount to set
	 */
	public void setThirdLimitAmount(double thirdLimitAmount)
	{
		ThirdLimitAmount = thirdLimitAmount;
	}

	/**
	 * Sets the thirdLimitTypeID.
	 * @param thirdLimitTypeID The thirdLimitTypeID to set
	 */
	public void setThirdLimitTypeID(long thirdLimitTypeID)
	{
		ThirdLimitTypeID = thirdLimitTypeID;
	}

	/**
		 * Sets the InterestRatePlan.
		 * @param InterestRatePlan The InterestRatePlan to set
		 */
	public void setInterestRatePlanDate(Timestamp interestRatePlan)
	{
		InterestRatePlanDate = interestRatePlan;
	}

	/**
	 * @return
	 */
	public double getDailyUncheckAmount()
	{
		return this.DailyUncheckAmount;
	}

	/**
	 * @param dailyUncheckAmount
	 */
	public void setDailyUncheckAmount(double dailyUncheckAmount)
	{
		this.DailyUncheckAmount = dailyUncheckAmount;
	}

	/**
	 * @return
	 */
	public Timestamp getClearInterestDate()
	{
		return ClearInterestDate;
	}

	/**
	 * @return
	 */
	public Timestamp getFinishDate()
	{
		return FinishDate;
	}

	/**
	 * @return
	 */
	public long getIsInterest()
	{
		return IsInterest;
	}

	/**
	 * @return
	 */
	public double getNegotiateInterest()
	{
		return NegotiateInterest;
	}

	/**
	 * @return
	 */
	public double getOpenAmount()
	{
		return OpenAmount;
	}

	/**
	 * @return
	 */
	public Timestamp getOpenDate()
	{
		return OpenDate;
	}

	/**
	 * @return
	 */
	public long getSealBankID()
	{
		return SealBankID;
	}

	/**
	 * @return
	 */
	public String getSealNo()
	{
		return SealNo;
	}

	/**
	 * @param timestamp
	 */
	public void setClearInterestDate(Timestamp timestamp)
	{
		ClearInterestDate = timestamp;
	}

	/**
	 * @param timestamp
	 */
	public void setFinishDate(Timestamp timestamp)
	{
		FinishDate = timestamp;
	}

	/**
	 * @param l
	 */
	public void setIsInterest(long l)
	{
		IsInterest = l;
	}

	/**
	 * @param d
	 */
	public void setNegotiateInterest(double d)
	{
		NegotiateInterest = d;
	}

	/**
	 * @param d
	 */
	public void setOpenAmount(double d)
	{
		OpenAmount = d;
	}

	/**
	 * @param timestamp
	 */
	public void setOpenDate(Timestamp timestamp)
	{
		OpenDate = timestamp;
	}

	/**
	 * @param l
	 */
	public void setSealBankID(long l)
	{
		SealBankID = l;
	}

	/**
	 * @param string
	 */
	public void setSealNo(String string)
	{
		SealNo = string;
	}

	/**
	 * @return
	 */
	public double getMonthLimitAmount()
	{
		return this.MonthLimitAmount;
	}

	/**
	 * @param monthLimitAmount
	 */
	public void setMonthLimitAmount(double monthLimitAmount)
	{
		this.MonthLimitAmount = monthLimitAmount;
	}

	/**
	 * @return
	 */
	public double getDayLimitAmount()
	{
		return this.DayLimitAmount;
	}

	/**
	 * @param monthLimitAmount
	 */
	public void setDayLimitAmount(double dayLimitAmount)
	{
		this.DayLimitAmount = dayLimitAmount;
	}
	
	/**
	 * @return
	 */
	public double getNegotiateRate()
	{
		return NegotiateRate;
	}

	/**
	 * @return
	 */
	public Timestamp getNegotiateRateDate()
	{
		return NegotiateRateDate;
	}

	/**
	 * @param d
	 */
	public void setNegotiateRate(double d)
	{
		NegotiateRate = d;
	}

	/**
	 * @param timestamp
	 */
	public void setNegotiateRateDate(Timestamp timestamp)
	{
		NegotiateRateDate = timestamp;
	}

	/**
	 * @return Returns the negotiationEndDate.
	 */
	public Timestamp getNegotiationEndDate() {
		return NegotiationEndDate;
	}
	
	/**
	 * @param negotiationEndDate The negotiationEndDate to set.
	 */
	public void setNegotiationEndDate(Timestamp negotiationEndDate) {
		NegotiationEndDate = negotiationEndDate;
	}
	/**
	 * @return Returns the negotiationStartDate.
	 */
	public Timestamp getNegotiationStartDate() {
		return NegotiationStartDate;
	}
	/**
	 * @param negotiationStartDate The negotiationStartDate to set.
	 */
	public void setNegotiationStartDate(Timestamp negotiationStartDate) {
		NegotiationStartDate = negotiationStartDate;
	}
	/**
	 * @return
	 */
	public long getIsAllBranch()
	{
		return IsAllBranch;
	}

	/**
	 * @param l
	 */
	public void setIsAllBranch(long l)
	{
		IsAllBranch = l;
	}

}
