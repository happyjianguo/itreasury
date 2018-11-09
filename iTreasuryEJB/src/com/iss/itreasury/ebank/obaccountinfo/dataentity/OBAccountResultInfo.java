/*
 * Created on 2004-2-4
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.iss.itreasury.ebank.obaccountinfo.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author yfsu
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class OBAccountResultInfo implements Serializable
{	
	
	private long AccountID=-1;                //�˻�ID	
	private String AccountNo="";              //�˺�
	private String DepositNo="";              //���ݺ�
	private long PayFormID=-1;                //�ſ�֪ͨ��ID
	private String PayFormNo="";              //�ſ�֪ͨ����
	private Timestamp StartDate=null;        //������	
	private Timestamp EndDate=null;          //������	
	private long DepositTerm=-1;             //����	
	private double Rate=0.0;                 //����
	private double Interest=0.0;             //��Ϣ		
	private double OpenAmount=0.0;           //�������	
	private double Balance=0.0;              //������	
	private long StatusID=-1;                //״̬ID 
	private double Amount=0.0;               //���׽��
	private long TransDirection = -1;        //���׷���
	  
	private long SerialNo = -1;        //�Ѹ��������к�  
	private String TransNo="";              //���׺�
	private long TransactionTypeID=-1;       //ҵ������	
	private String Abstract="";              //ժҪ
	private long OppAccountID = -1;        //	�Է��˻�ID
	private String BillNo="";              //֧Ʊ��/ƾ֤��
	private double DebitAmount=0.0;             //�跽���
	private double CreditAmount=0.0;             //�������
	private double Banlance=0.0;             //���
	private Timestamp ExecuteDate=null;          //ִ������
	private double EarlyBanlance=0.0;             //�ڳ����
	
	/**
	 * @return
	 */
	public String getAbstract() {
		return Abstract;
	}

	/**
	 * @return
	 */
	public long getAccountID() {
		return AccountID;
	}

	/**
	 * @return
	 */
	public String getAccountNo() {
		return AccountNo;
	}

	/**
	 * @return
	 */
	public double getAmount() {
		return Amount;
	}

	/**
	 * @return
	 */
	public double getBalance() {
		return Balance;
	}

	/**
	 * @return
	 */
	public double getBanlance() {
		return Banlance;
	}

	/**
	 * @return
	 */
	public String getBillNo() {
		return BillNo;
	}

	/**
	 * @return
	 */
	public double getCreditAmount() {
		return CreditAmount;
	}

	/**
	 * @return
	 */
	public double getDebitAmount() {
		return DebitAmount;
	}

	/**
	 * @return
	 */
	public long getDepositTerm() {
		return DepositTerm;
	}

	

	/**
	 * @return
	 */
	public double getEarlyBanlance() {
		return EarlyBanlance;
	}

	/**
	 * @return
	 */
	public Timestamp getEndDate() {
		return EndDate;
	}

	/**
	 * @return
	 */
	public Timestamp getExecuteDate() {
		return ExecuteDate;
	}

	/**
	 * @return
	 */
	public double getInterest() {
		return Interest;
	}

	/**
	 * @return
	 */
	public double getOpenAmount() {
		return OpenAmount;
	}

	/**
	 * @return
	 */
	public long getOppAccountID() {
		return OppAccountID;
	}

	/**
	 * @return
	 */
	public double getRate() {
		return Rate;
	}

	/**
	 * @return
	 */
	public Timestamp getStartDate() {
		return StartDate;
	}

	/**
	 * @return
	 */
	public long getStatusID() {
		return StatusID;
	}

	/**
	 * @return
	 */
	public long getTransactionTypeID() {
		return TransactionTypeID;
	}

	/**
	 * @return
	 */
	public long getTransDirection() {
		return TransDirection;
	}

	/**
	 * @return
	 */
	public String getTransNo() {
		return TransNo;
	}

	/**
	 * @param string
	 */
	public void setAbstract(String string) {
		Abstract = string;
	}

	/**
	 * @param l
	 */
	public void setAccountID(long l) {
		AccountID = l;
	}

	/**
	 * @param string
	 */
	public void setAccountNo(String string) {
		AccountNo = string;
	}

	/**
	 * @param d
	 */
	public void setAmount(double d) {
		Amount = d;
	}

	/**
	 * @param d
	 */
	public void setBalance(double d) {
		Balance = d;
	}

	/**
	 * @param d
	 */
	public void setBanlance(double d) {
		Banlance = d;
	}

	/**
	 * @param string
	 */
	public void setBillNo(String string) {
		BillNo = string;
	}

	/**
	 * @param d
	 */
	public void setCreditAmount(double d) {
		CreditAmount = d;
	}

	/**
	 * @param d
	 */
	public void setDebitAmount(double d) {
		DebitAmount = d;
	}

	/**
	 * @param l
	 */
	public void setDepositTerm(long l) {
		DepositTerm = l;
	}

	
	/**
	 * @param d
	 */
	public void setEarlyBanlance(double d) {
		EarlyBanlance = d;
	}

	/**
	 * @param timestamp
	 */
	public void setEndDate(Timestamp timestamp) {
		EndDate = timestamp;
	}

	/**
	 * @param timestamp
	 */
	public void setExecuteDate(Timestamp timestamp) {
		ExecuteDate = timestamp;
	}

	/**
	 * @param d
	 */
	public void setInterest(double d) {
		Interest = d;
	}

	/**
	 * @param d
	 */
	public void setOpenAmount(double d) {
		OpenAmount = d;
	}

	/**
	 * @param l
	 */
	public void setOppAccountID(long l) {
		OppAccountID = l;
	}

	/**
	 * @param d
	 */
	public void setRate(double d) {
		Rate = d;
	}

	/**
	 * @param timestamp
	 */
	public void setStartDate(Timestamp timestamp) {
		StartDate = timestamp;
	}

	/**
	 * @param l
	 */
	public void setStatusID(long l) {
		StatusID = l;
	}

	/**
	 * @param l
	 */
	public void setTransactionTypeID(long l) {
		TransactionTypeID = l;
	}

	/**
	 * @param l
	 */
	public void setTransDirection(long l) {
		TransDirection = l;
	}

	/**
	 * @param string
	 */
	public void setTransNo(String string) {
		TransNo = string;
	}

	/**
	 * @return
	 */
	public String getDepositNo() {
		return DepositNo;
	}

	/**
	 * @param string
	 */
	public void setDepositNo(String string) {
		DepositNo = string;
	}

	/**
	 * @return
	 */
	public long getPayFormID() {
		return PayFormID;
	}

	/**
	 * @return
	 */
	public String getPayFormNo() {
		return PayFormNo;
	}

	/**
	 * @param l
	 */
	public void setPayFormID(long l) {
		PayFormID = l;
	}

	/**
	 * @param string
	 */
	public void setPayFormNo(String string) {
		PayFormNo = string;
	}

	/**
	 * @return
	 */
	public long getSerialNo() {
		return SerialNo;
	}

	/**
	 * @param l
	 */
	public void setSerialNo(long l) {
		SerialNo = l;
	}

}
