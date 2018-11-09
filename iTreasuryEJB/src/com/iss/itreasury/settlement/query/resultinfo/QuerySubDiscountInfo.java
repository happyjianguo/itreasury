/*
 * Created on 2003-11-21
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
public class QuerySubDiscountInfo implements Serializable
{

	private long ID = -1; //�����˻���
	private long AccountID = -1; //�������˻���
	private long nStatusID = -1; //״̬
	private Timestamp AF_dtStart = null; //��ʼ����	
	private double mBalance = 0.0; //��ǰ���	
	private Timestamp dtOpen = null; //��������
	private Timestamp dtFinish = null; //�廧����
	private double AL_mInterestTax = 0.0; //�� Ϣ

	//������Ϣ��loan_DiscountCredence�в��
	private String AL_nLoanNoteNo = ""; //����ƾ֤���	
	private long AL_nLoanNoteID = -1;// 
	private long nDraftTypeID = -1; //��Ʊ����            ?????????????     
	private long LoanContractID = -1; //��ͬ��
	private double Amount = 0.0; //��Ʊ���
	private double DiscountAmount = 0.0; //�˶����
	
	private long TypeID1 = -1;//��������
	private long TypeID2 = -1;//��ҵ����1
	private long TypeID3 = -1;//��ҵ����2
	
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
	public double getAL_mInterestTax()
	{
		return AL_mInterestTax;
	}

	/**
	 * @return
	 */
	public String getAL_nLoanNoteNo()
	{
		return AL_nLoanNoteNo;
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
	public long getID()
	{
		return ID;
	}

	/**
	 * @return
	 */
	public long getLoanContractID()
	{
		return LoanContractID;
	}

	/**
	 * @return
	 */
	public double getMBalance()
	{
		return mBalance;
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
	public void setAF_dtStart(Timestamp timestamp)
	{
		AF_dtStart = timestamp;
	}

	/**
	 * @param d
	 */
	public void setAL_mInterestTax(double d)
	{
		AL_mInterestTax = d;
	}

	/**
	 * @param l
	 */
	public void setAL_nLoanNoteNo(String s)
	{
		AL_nLoanNoteNo = s;
	}

	/**
	 * @param d
	 */
	public void setAmount(double d)
	{
		Amount = d;
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
	 * @param l
	 */
	public void setID(long l)
	{
		ID = l;
	}

	/**
	 * @param l
	 */
	public void setLoanContractID(long l)
	{
		LoanContractID = l;
	}

	/**
	 * @param d
	 */
	public void setMBalance(double d)
	{
		mBalance = d;
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
	public long getNDraftTypeID()
	{
		return nDraftTypeID;
	}

	/**
	 * @param l
	 */
	public void setNDraftTypeID(long l)
	{
		nDraftTypeID = l;
	}

	/**
	 * @return
	 */
	public long getAL_nLoanNoteID()
	{
		return AL_nLoanNoteID;
	}

	/**
	 * @param l
	 */
	public void setAL_nLoanNoteID(long l)
	{
		AL_nLoanNoteID = l;
	}

	/**
	 * @return
	 */
	public double getDiscountAmount()
	{
		return DiscountAmount;
	}

	/**
	 * @param d
	 */
	public void setDiscountAmount(double d)
	{
		DiscountAmount = d;
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

}
