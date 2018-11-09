/*
 * BatchRepayNoticeInfo.java
 *
 * Created on 2003��2��12��
 */

package com.iss.itreasury.loan.aheadrepaynotice.dataentity;

import java.sql.*;

import com.iss.itreasury.system.approval.dataentity.InutParameterInfo;
import com.iss.itreasury.util.*;

/**
 *
 * @author  yfan
 * @version 
 */
public class BatchRepayNoticeInfo implements java.io.Serializable
{

	/** Creates new AheadRepayNoticeInfo */
	public BatchRepayNoticeInfo()
	{
		super();
	}

	private long ID = -1; //��������֪ͨ��ID��ʶ
	private String sCode = ""; //��������֪ͨ�����
	private String rePayID = "";//���ID
	private double mAmount = 0; //����������
	private long nCurrencyID = -1; //����
	private long nOfficeID = -1; //���´���ʾ
	private long nStatusID = -1; //״̬
	private String Status = "";

	private long nInputUserID = -1; //¼���˱�ʾ
	private String InputUserName = ""; //¼��������
	private Timestamp dtInputDate; //¼��ʱ��
	private Timestamp PayDate; //��������
	private long nContractID = -1; //��ͬ��ʶ
	
	
	
	private String ContractCode = ""; //��ͬ���
	private double ContractAmount = 0; //��ͬ���
	private double ContractBalance = 0; //��ͬ���
	private long ClientID = -1; //��λ��ʶ
	private String ClientName = ""; //��λ����
	private long LoanID = -1; //�����ʶ
	private long IntervalNum = 0; //��������
   
	private long LetoutNoticeID = -1; //�ſ�֪ͨ����ʶ
	private String LetoutNoticeCode = ""; //�ſ�֪ͨ�����
	private double LetoutNoticeAmount = 0; //�ſ�֪ͨ�����
	private double LetoutNoticeRate = 0; //�ſ�֪ͨ������
	private long LetoutNoticeIntervalNum = 0; //��������
	private double LetoutNoticeBalance = 0; //�ſ�֪ͨ�����
	
	private long nIsAhead = -1;				//�Ƿ���ǰ����

	private long PageCount = 0; //��¼��
	
	private long loanType = -1;//��������
	
	private long loanSubType = -1;
	
	
	private double balanceAmount; //��Ϣ���

	private long nisrepayinterest; //�Ƿ�黹��Ϣ
	
	private double minterest; //������������Ϣ
	
	InutParameterInfo inutParameterInfo = null;
	/**
	 * @return
	 */
	public double getAmount()
	{
		return mAmount;
	}

	public String getFormatAmount()
	{
		return DataFormat.formatDisabledAmount(mAmount);
	}

	/**
	 * @return
	 */
	public long getClientID()
	{
		return ClientID;
	}

	/**
	 * @return
	 */
	public String getClientName()
	{
		return ClientName;
	}


	/**
	 * @return
	 */
	public double getContractAmount()
	{
		return ContractAmount;
	}

	/**
	 * @return
	 */
	public String getContractCode()
	{
		return ContractCode;
	}

	/**
	 * @return
	 */
	public long getContractID()
	{
		return nContractID;
	}

	/**
	 * @return
	 */
	public long getCurrencyID()
	{
		return nCurrencyID;
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
	public Timestamp getInputDate()
	{
		return dtInputDate;
	}

	public String getFormatInputDate()
	{
		return DataFormat.formatDate(dtInputDate);
	}

	/**
	 * @return
	 */
	public long getInputUserID()
	{
		return nInputUserID;
	}

	/**
	 * @return
	 */
	public String getInputUserName()
	{
		return InputUserName;
	}

	/**
	 * @return
	 */
	public double getLetoutNoticeAmount()
	{
		return LetoutNoticeAmount;
	}

	public String getFormatLetoutNoticeAmount()
	{
		return DataFormat.formatDisabledAmount(LetoutNoticeAmount);
	}

	/**
	 * @return
	 */
	public double getLetoutNoticeBalance()
	{
		return LetoutNoticeBalance;
	}

	/**
	 * @return
	 */
	public String getLetoutNoticeCode()
	{
		return LetoutNoticeCode;
	}

	/**
	 * @return
	 */
	public long getLetoutNoticeID()
	{
		return LetoutNoticeID;
	}

	/**
	 * @return
	 */
	public long getLetoutNoticeIntervalNum()
	{
		return LetoutNoticeIntervalNum;
	}

	/**
	 * @return
	 */
	public double getLetoutNoticeRate()
	{
		return LetoutNoticeRate;
	}

	/**
	 * @return
	 */
	public long getLoanID()
	{
		return LoanID;
	}

	
	/**
	 * @return
	 */
	public long getOfficeID()
	{
		return nOfficeID;
	}

	/**
	 * @return
	 */
	public long getStatusID()
	{
		return nStatusID;
	}

	/**
	 * @param d
	 */
	public void setAmount(double d)
	{
		mAmount = d;
	}

	/**
	 * @param l
	 */
	public void setClientID(long l)
	{
		ClientID = l;
	}

	/**
	 * @param string
	 */
	public void setClientName(String string)
	{
		ClientName = string;
	}

	/**
	 * @param string
	 */
	public void setCode(String string)
	{
		sCode = string;
	}

	/**
	 * @param d
	 */
	public void setContractAmount(double d)
	{
		ContractAmount = d;
	}

	/**
	 * @param string
	 */
	public void setContractCode(String string)
	{
		ContractCode = string;
	}

	/**
	 * @param l
	 */
	public void setContractID(long l)
	{
		nContractID = l;
	}

	/**
	 * @param l
	 */
	public void setCurrencyID(long l)
	{
		nCurrencyID = l;
	}

	/**
	 * @param l
	 */
	public void setID(long l)
	{
		ID = l;
	}

	/**
	 * @param timestamp
	 */
	public void setInputDate(Timestamp timestamp)
	{
		dtInputDate = timestamp;
	}

	/**
	 * @param l
	 */
	public void setInputUserID(long l)
	{
		nInputUserID = l;
	}

	/**
	 * @param string
	 */
	public void setInputUserName(String string)
	{
		InputUserName = string;
	}

	/**
	 * @param d
	 */
	public void setLetoutNoticeAmount(double d)
	{
		LetoutNoticeAmount = d;
	}

	/**
	 * @param d
	 */
	public void setLetoutNoticeBalance(double d)
	{
		LetoutNoticeBalance = d;
	}

	/**
	 * @param string
	 */
	public void setLetoutNoticeCode(String string)
	{
		LetoutNoticeCode = string;
	}

	/**
	 * @param l
	 */
	public void setLetoutNoticeID(long l)
	{
		LetoutNoticeID = l;
	}

	/**
	 * @param l
	 */
	public void setLetoutNoticeIntervalNum(long l)
	{
		LetoutNoticeIntervalNum = l;
	}

	/**
	 * @param d
	 */
	public void setLetoutNoticeRate(double d)
	{
		LetoutNoticeRate = d;
	}

	/**
	 * @param l
	 */
	public void setLoanID(long l)
	{
		LoanID = l;
	}

	

	/**
	 * @param l
	 */
	public void setOfficeID(long l)
	{
		nOfficeID = l;
	}

	/**
	 * @param l
	 */
	public void setStatusID(long l)
	{
		nStatusID = l;
	}

	/**
	 * @return
	 */
	public String getStatus()
	{
		return Status;
	}

	/**
	 * @param string
	 */
	public void setStatus(String string)
	{
		Status = string;
	}

	/**
	 * @return
	 */
	public long getIntervalNum()
	{
		return IntervalNum;
	}

	/**
	 * @param l
	 */
	public void setIntervalNum(long l)
	{
		IntervalNum = l;
	}

	/**
	 * @return
	 */
	public double getContractBalance()
	{
		return ContractBalance;
	}

	/**
	 * @param d
	 */
	public void setContractBalance(double d)
	{
		ContractBalance = d;
	}

	/**
	 * @return
	 */
	public long getPageCount()
	{
		return PageCount;
	}

	/**
	 * @param l
	 */
	public void setPageCount(long l)
	{
		PageCount = l;
	}

	public long getIsAhead() {
		return nIsAhead;
	}

	public void setIsAhead(long isAhead) {
		nIsAhead = isAhead;
	}

	public long getLoanSubType() {
		return loanSubType;
	}

	public void setLoanSubType(long loanSubType) {
		this.loanSubType = loanSubType;
	}

	public long getLoanType() {
		return loanType;
	}

	public void setLoanType(long loanType) {
		this.loanType = loanType;
	}

	public InutParameterInfo getInutParameterInfo() {
		return inutParameterInfo;
	}

	public void setInutParameterInfo(InutParameterInfo inutParameterInfo) {
		this.inutParameterInfo = inutParameterInfo;
	}

	public double getBalanceAmount() {
		return balanceAmount;
	}

	public void setBalanceAmount(double balanceAmount) {
		this.balanceAmount = balanceAmount;
	}

	public Timestamp getPBackDate() {
		return PayDate;
	}

	public void setPBackDate(Timestamp backDate) {
		PayDate = backDate;
	}

	public String getSCode() {
		return sCode;
	}

	public void setSCode(String code) {
		sCode = code;
	}

	public String getRePayID() {
		return rePayID;
	}

	public void setRePayID(String rePayID) {
		this.rePayID = rePayID;
	}

	public long getNisrepayinterest() {
		return nisrepayinterest;
	}

	public void setNisrepayinterest(long nisrepayinterest) {
		this.nisrepayinterest = nisrepayinterest;
	}

	public double getMinterest() {
		return minterest;
	}

	public void setMinterest(double minterest) {
		this.minterest = minterest;
	}
}
