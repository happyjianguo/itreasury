/*
 * Created on 2003-11-19
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.iss.itreasury.settlement.query.resultinfo;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author yfsu
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class QuerySubAffianceInfo implements Serializable
{
	private long ID = -1; //���˻�
	private long AccountID = -1; //���˻�
	private Timestamp AF_dtStart = null; //��ʼ����
	private Timestamp AF_dtEnd = null; //��������	
	private long AL_nLoanNoteID = -1; //�ſ�֪ͨ��	
	private long nStatusID = -1; //״̬
	private double AF_mRate = 0.0; //����	
	private double CommissionRate = 0.0; //����������
	private long nPayInterestAccountID = -1; //��Ϣ�˻�
	private double AL_mSuretyFee = 0.0; //��������
	private long AL_nPayInterestAccountID = -1; //����˻���
	private long AL_nSuretyAccountID = -1; //�������˻���	
	private String contractCode = ""; // ��ͬ��
	private long loanType = -1; //	��������    ���ݷſ�֪ͨ���ӱ�  �в��
	private double amount = 0.0; //������
	private double mBalance = 0.0; //��ǰ���
	private double AC_mNegotiateInterest = 0.0; //�ۼ���Ϣ
	private double AF_mPreDrawInterest = 0.0; //�Ѽ�����Ϣ
	private double TotalRepaymentInterest = 0.0; //�ۼƻ�Ϣ
	private Timestamp dtOpen = null; //��������
	private Timestamp dtFinish = null; //�廧����
	private long SubAccountStatusID = -1; // ���˻�״̬
	private double LoanInterestRate = 0.0; // ��������
	private long LoanTerm = 0; // ��������
	private Timestamp dtOut = null; //�ſ�����
	
	private long TypeID1 = -1;//��������
	private long TypeID2 = -1;//��ҵ����1
	private long TypeID3 = -1;//��ҵ����2

	/**
	 * @return
	 */
	public Timestamp getAF_dtEnd()
	{
		return AF_dtEnd;
	}

	/**
	 * @return
	 */
	public Timestamp getAF_dtStart()
	{
		return AF_dtStart;
	}

	/**
	 * @return
	 */
	public double getAF_mRate()
	{
		return AF_mRate;
	}

	/**
	 * @return
	 */
	public double getAL_mSuretyFee()
	{
		return AL_mSuretyFee;
	}

	/**
	 * @return
	 */
	public long getAL_nLoanNoteID()
	{
		return AL_nLoanNoteID;
	}

	/**
	 * @return
	 */
	public long getAL_nSuretyAccountID()
	{
		return AL_nSuretyAccountID;
	}

	/**
	 * @return
	 */
	public String getContractCode()
	{
		return contractCode;
	}

	/**
	 * @return
	 */
	public long getID()
	{
		return ID;
	}


	/**
	 * @return
	 */
	public long getLoanType()
	{
		return loanType;
	}

	/**
	 * @return
	 */
	public long getNPayInterestAccountID()
	{
		return nPayInterestAccountID;
	}

	/**
	 * @return
	 */
	public long getNStatusID()
	{
		return nStatusID;
	}

	/**
	 * @param timestamp
	 */
	public void setAF_dtEnd(Timestamp timestamp)
	{
		AF_dtEnd = timestamp;
	}

	/**
	 * @param timestamp
	 */
	public void setAF_dtStart(Timestamp timestamp)
	{
		AF_dtStart = timestamp;
	}

	/**
	 * @param d
	 */
	public void setAF_mRate(double d)
	{
		AF_mRate = d;
	}

	/**
	 * @param d
	 */
	public void setAL_mSuretyFee(double d)
	{
		AL_mSuretyFee = d;
	}

	/**
	 * @param l
	 */
	public void setAL_nLoanNoteID(long l)
	{
		AL_nLoanNoteID = l;
	}

	/**
	 * @param l
	 */
	public void setAL_nSuretyAccountID(long l)
	{
		AL_nSuretyAccountID = l;
	}

	/**
	 * @param string
	 */
	public void setContractCode(String string)
	{
		contractCode = string;
	}

	/**
	 * @param l
	 */
	public void setID(long l)
	{
		ID = l;
	}

	/**
	 * @param l
	 */
	public void setLoanTerm(long l)
	{
		LoanTerm = l;
	}

	/**
	 * @param l
	 */
	public void setLoanType(long l)
	{
		loanType = l;
	}

	/**
	 * @param l
	 */
	public void setNPayInterestAccountID(long l)
	{
		nPayInterestAccountID = l;
	}

	/**
	 * @param l
	 */
	public void setNStatusID(long l)
	{
		nStatusID = l;
	}

	/**
	 * @return
	 */
	public long getAL_nPayInterestAccountID()
	{
		return AL_nPayInterestAccountID;
	}

	/**
	 * @param l
	 */
	public void setAL_nPayInterestAccountID(long l)
	{
		AL_nPayInterestAccountID = l;
	}

	/**
	 * @return
	 */
	public double getAC_mNegotiateInterest()
	{
		return AC_mNegotiateInterest;
	}

	/**
	 * @return
	 */
	public double getAF_mPreDrawInterest()
	{
		return AF_mPreDrawInterest;
	}

	/**
	 * @return
	 */
	public double getAmount()
	{
		return amount;
	}

	/**
	 * @return
	 */
	public Timestamp getDtFinish()
	{
		return dtFinish;
	}

	/**
	 * @return
	 */
	public Timestamp getDtOpen()
	{
		return dtOpen;
	}

	/**
	 * @return
	 */
	public double getMBalance()
	{
		return mBalance;
	}

	/**
	 * @param d
	 */
	public void setAC_mNegotiateInterest(double d)
	{
		AC_mNegotiateInterest = d;
	}

	/**
	 * @param d
	 */
	public void setAF_mPreDrawInterest(double d)
	{
		AF_mPreDrawInterest = d;
	}

	/**
	 * @param d
	 */
	public void setAmount(double d)
	{
		amount = d;
	}

	/**
	 * @param timestamp
	 */
	public void setDtFinish(Timestamp timestamp)
	{
		dtFinish = timestamp;
	}

	/**
	 * @param timestamp
	 */
	public void setDtOpen(Timestamp timestamp)
	{
		dtOpen = timestamp;
	}

	/**
	 * @param d
	 */
	public void setMBalance(double d)
	{
		mBalance = d;
	}

	/**
	 * @return
	 */
	public double getLoanInterestRate()
	{
		return LoanInterestRate;
	}

	/**
	 * @param loanInterestRate
	 */
	public void setLoanInterestRate(double loanInterestRate)
	{
		LoanInterestRate = loanInterestRate;
	}

	/**
	 * @return
	 */
	public long getSubAccountStatusID()
	{
		return SubAccountStatusID;
	}

	/**
	 * @param sunAccountStatusID
	 */
	public void setSubAccountStatusID(long sunAccountStatusID)
	{
		SubAccountStatusID = sunAccountStatusID;
	}

	/**
	 * @return
	 */
	public long getLoanTerm()
	{
		return LoanTerm;
	}

	/**
	 * @return
	 */
	public Timestamp getDtOut()
	{
		return dtOut;
	}

	/**
	 * @param timestamp
	 */
	public void setDtOut(Timestamp timestamp)
	{
		dtOut = timestamp;
	}

	/**
	 * @return
	 */
	public long getAccountID()
	{
		return AccountID;
	}

	/**
	 * @param l
	 */
	public void setAccountID(long l)
	{
		AccountID = l;
	}

	/**
	 * @return
	 */
	public double getTotalRepaymentInterest()
	{
		return TotalRepaymentInterest;
	}

	/**
	 * @param d
	 */
	public void setTotalRepaymentInterest(double d)
	{
		TotalRepaymentInterest = d;
	}

	/**
	 * @return
	 */
	public long getTypeID1()
	{
		return TypeID1;
	}

	/**
	 * @return
	 */
	public long getTypeID2()
	{
		return TypeID2;
	}

	/**
	 * @return
	 */
	public long getTypeID3()
	{
		return TypeID3;
	}

	/**
	 * @param l
	 */
	public void setTypeID1(long l)
	{
		TypeID1 = l;
	}

	/**
	 * @param l
	 */
	public void setTypeID2(long l)
	{
		TypeID2 = l;
	}

	/**
	 * @param l
	 */
	public void setTypeID3(long l)
	{
		TypeID3 = l;
	}

	/**
	 * @return Returns the commissionRate.
	 */
	public double getCommissionRate() {
		return CommissionRate;
	}
	/**
	 * @param commissionRate The commissionRate to set.
	 */
	public void setCommissionRate(double commissionRate) {
		CommissionRate = commissionRate;
	}
}
