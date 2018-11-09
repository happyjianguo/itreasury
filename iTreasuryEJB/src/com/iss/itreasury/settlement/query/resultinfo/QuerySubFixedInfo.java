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
public class QuerySubFixedInfo implements Serializable
{
	private long ID=0;  //�����˻�ID
	private long AccountID=0;  //�������˻�ID
	private String AF_sDepositNo=""; //���ڴ��ݺ�
	private Timestamp AF_dtStart=null;//��ʼ����
	private long AF_nDepositTerm=0;//���ڴ������
	private Timestamp AF_dtEnd=null;//��������
	private double AF_mRate=0.0;//����
	private double mOpenAmount=0.0;//�浥���
	private double mBalance=0.0;//��ǰ���
	private long nStatusID=0;//���ڴ��״̬
	private Timestamp dtOpen=null;//��������
	private Timestamp dtFinish=null;//�廧����
	private double AF_mPreDrawInterest=0.0;	//�Ѽ�����Ϣ
	private double AL_mPreDrawInterest=0.0;	//�Ѽ�����Ϣ(��֤��)
	private double AF_mInterest=0.0;//Ӧ����Ϣ
	private long  lConstractID = -1; //��������ר��
	private long  lPayFormID = -1; //��������ר��
	
	private long  isAutoContinue = -1; //�Ƿ��Զ�����
	private long  autoContinueType = -1; //�Զ����淽ʽ
	private long  autoContinueAccountID = -1; //������Ϣ�����˻�
	
	public double getAL_mPreDrawInterest() {
		return AL_mPreDrawInterest;
	}
	public void setAL_mPreDrawInterest(double preDrawInterest) {
		AL_mPreDrawInterest = preDrawInterest;
	}
	public double getAF_mInterest() {
		return AF_mInterest;
	}
	public void setAF_mInterest(double interest) {
		AF_mInterest = interest;
	}
	public QuerySubFixedInfo()
	{
	}
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
	public double getAF_mPreDrawInterest()
	{
		return AF_mPreDrawInterest;
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
	public long getAF_nDepositTerm()
	{
		return AF_nDepositTerm;
	}

	/**
	 * @return
	 */
	public String getAF_sDepositNo()
	{
		return AF_sDepositNo;
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
	public double getMBalance()
	{
		return mBalance;
	}

	/**
	 * @return
	 */
	public double getMOpenAmount()
	{
		return mOpenAmount;
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
	public void setAF_mPreDrawInterest(double d)
	{
		AF_mPreDrawInterest = d;
	}

	/**
	 * @param d
	 */
	public void setAF_mRate(double d)
	{
		AF_mRate = d;
	}

	/**
	 * @param l
	 */
	public void setAF_nDepositTerm(long l)
	{
		AF_nDepositTerm = l;
	}

	/**
	 * @param string
	 */
	public void setAF_sDepositNo(String string)
	{
		AF_sDepositNo = string;
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
	 * @param d
	 */
	public void setMBalance(double d)
	{
		mBalance = d;
	}

	/**
	 * @param d
	 */
	public void setMOpenAmount(double d)
	{
		mOpenAmount = d;
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
	public long getLConstractID() {
		return lConstractID;
	}
	public void setLConstractID(long constractID) {
		lConstractID = constractID;
	}
	public long getLPayFormID() {
		return lPayFormID;
	}
	public void setLPayFormID(long payFormID) {
		lPayFormID = payFormID;
	}
	
	/**
	 * @param isAutoContinue
	 */
	public long getIsAutoContinue() {
		return isAutoContinue;
	}
	/**
	 * @param isAutoContinue
	 */
	public void setIsAutoContinue(long isAutoContinue) {
		this.isAutoContinue = isAutoContinue;
	}
	/**
	 * @param autoContinueType
	 */
	public long getAutoContinueType() {
		return autoContinueType;
	}
	/**
	 * @param autoContinueType
	 */
	public void setAutoContinueType(long autoContinueType) {
		this.autoContinueType = autoContinueType;
	}
	/**
	 * @param autoContinueAccountID
	 */
	public long getAutoContinueAccountID() {
		return autoContinueAccountID;
	}
	/**
	 * @param autoContinueAccountID
	 */
	public void setAutoContinueAccountID(long autoContinueAccountID) {
		this.autoContinueAccountID = autoContinueAccountID;
	}

}
