/**
 * Title:        		iTreasury
 * Description:         
 * Copyright:           Copyright (c) 2003
 * Company:             iSoftStone
 * @author             yehuang 
 * @version
 *  Date of Creation    2004-3-4
 */
package com.iss.itreasury.securities.register.dataentity;

import java.sql.Timestamp;

import com.iss.itreasury.securities.util.SECBaseDataEntity;

/**
 *���/�ع��Ǽǲ����ݶ���
 */
public class RepurchaseRegisterInfo extends SECBaseDataEntity{
	private long id = -1;                               //
	private long transactionTypeID = -1;                //�״ν��׵�����ID
	private String systemTransactionCode = null;		//����ϵͳ�ɽ����
	private long accountID = -1;						//�����ʽ��ʻ�ID
	private long clientID = -1;							//ҵ��λID
	private long counterpartID = -1;					//���׶���/����Ӫҵ��ID
	private long pledgeSecuritiesID = -1;				//����Ѻ֤ȯID
	private double pledgeAmount = 0.0;					//��Ѻȯ�ܶ�
	private double pledgeQuantity = -1;					//��Ѻȯ�������ţ�
	private double pledgeRate = 0.0;					//��Ѻ����%
	private double amount = 0.0;						//���׽��/����
	private double balance = 0.0;						//���
	private Timestamp valueDate = null;					//��Ϣ��
	private Timestamp maturityDate = null;				//������
	private long term = -1;								//����
	private long termTypeID = -1;							//��������
	private double interestRate = 0.0;					//����%(������)
	private long firstDeliveryOrderID = -1;				//�״ν��ID(����/���ʱ�Ľ�����)
	private long lastDeliveryOrderID = -1;				//ĩ�ν��ID(����/���/����/��ȯ����ʱ�Ľ�����)
	private long statusID = -1;							//״̬(0=��ɾ�� 1=����)

	/**
	 * @return Returns the accountID.
	 */
	public long getAccountID() {
		return accountID;
	}
	/**
	 * @param accountID The accountID to set.
	 */
	public void setAccountID(long accountID) {
		putUsedField("accountID", accountID);
		this.accountID = accountID;
	}
	/**
	 * @return Returns the amount.
	 */
	public double getAmount() {
		return amount;
	}
	/**
	 * @param amount The amount to set.
	 */
	public void setAmount(double amount) {
		putUsedField("amount", amount);
		this.amount = amount;
	}
	/**
	 * @return Returns the balance.
	 */
	public double getBalance() {
		return balance;
	}
	/**
	 * @param balance The balance to set.
	 */
	public void setBalance(double balance) {
		putUsedField("balance", balance);
		this.balance = balance;
	}
	/**
	 * @return Returns the clientID.
	 */
	public long getClientID() {
		return clientID;
	}
	/**
	 * @param clientID The clientID to set.
	 */
	public void setClientID(long clientID) {
		putUsedField("clientID", clientID);
		this.clientID = clientID;
	}
	/**
	 * @return Returns the counterpartID.
	 */
	public long getCounterpartID() {
		return counterpartID;
	}
	/**
	 * @param counterpartID The counterpartID to set.
	 */
	public void setCounterpartID(long counterpartID) {
		putUsedField("counterpartID", counterpartID);
		this.counterpartID = counterpartID;
	}
	/**
	 * @return Returns the firstDeliveryOrderID.
	 */
	public long getFirstDeliveryOrderID() {
		return firstDeliveryOrderID;
	}
	/**
	 * @param firstDeliveryOrderID The firstDeliveryOrderID to set.
	 */
	public void setFirstDeliveryOrderID(long firstDeliveryOrderID) {
		putUsedField("firstDeliveryOrderID", firstDeliveryOrderID);
		this.firstDeliveryOrderID = firstDeliveryOrderID;
	}
	/**
	 * @return Returns the id.
	 */
	public long getId() {
		return id;
	}
	/**
	 * @param id The id to set.
	 */
	public void setId(long id) {
		putUsedField("id", id);
		this.id = id;
	}
	/**
	 * @return Returns the interestRate.
	 */
	public double getInterestRate() {
		return interestRate;
	}
	/**
	 * @param interestRate The interestRate to set.
	 */
	public void setInterestRate(double interestRate) {
		putUsedField("interestRate", interestRate);
		this.interestRate = interestRate;
	}
	/**
	 * @return Returns the lastDeliveryOrderID.
	 */
	public long getLastDeliveryOrderID() {
		return lastDeliveryOrderID;
	}
	/**
	 * @param lastDeliveryOrderID The lastDeliveryOrderID to set.
	 */
	public void setLastDeliveryOrderID(long lastDeliveryOrderID) {
		putUsedField("lastDeliveryOrderID", lastDeliveryOrderID);
		this.lastDeliveryOrderID = lastDeliveryOrderID;
	}
	/**
	 * @return Returns the ltatusID.
	 */
	public long getStatusID() {
		return statusID;
	}
	/**
	 * @param ltatusID The ltatusID to set.
	 */
	public void setStatusID(long statusID) {
		putUsedField("statusID", statusID);
		this.statusID = statusID;
	}
	/**
	 * @return Returns the maturityDate.
	 */
	public Timestamp getMaturityDate() {
		return maturityDate;
	}
	/**
	 * @param maturityDate The maturityDate to set.
	 */
	public void setMaturityDate(Timestamp maturityDate) {
		putUsedField("maturityDate", maturityDate);
		this.maturityDate = maturityDate;
	}
	/**
	 * @return Returns the pledgeAmount.
	 */
	public double getPledgeAmount() {
		return pledgeAmount;
	}
	/**
	 * @param pledgeAmount The pledgeAmount to set.
	 */
	public void setPledgeAmount(double pledgeAmount) {
		putUsedField("pledgeAmount", pledgeAmount);
		this.pledgeAmount = pledgeAmount;
	}
	/**
	 * @return Returns the pledgeQuantity.
	 */
	public double getPledgeQuantity() {
		return pledgeQuantity;
	}
	/**
	 * @param pledgeQuantity The pledgeQuantity to set.
	 */
	public void setPledgeQuantity(double pledgeQuantity) {
		putUsedField("pledgeQuantity", pledgeQuantity);
		this.pledgeQuantity = pledgeQuantity;
	}
	/**
	 * @return Returns the pledgeRate.
	 */
	public double getPledgeRate() {
		return pledgeRate;
	}
	/**
	 * @param pledgeRate The pledgeRate to set.
	 */
	public void setPledgeRate(double pledgeRate) {
		putUsedField("pledgeRate", pledgeRate);
		this.pledgeRate = pledgeRate;
	}
	/**
	 * @return Returns the pledgeSecuritiesID.
	 */
	public long getPledgeSecuritiesID() {
		return pledgeSecuritiesID;
	}
	/**
	 * @param pledgeSecuritiesID The pledgeSecuritiesID to set.
	 */
	public void setPledgeSecuritiesID(long pledgeSecuritiesID) {
		putUsedField("pledgeSecuritiesID", pledgeSecuritiesID);
		this.pledgeSecuritiesID = pledgeSecuritiesID;
	}
	/**
	 * @return Returns the systemTransactionCode.
	 */
	public String getSystemTransactionCode() {
		return systemTransactionCode;
	}
	/**
	 * @param systemTransactionCode The systemTransactionCode to set.
	 */
	public void setSystemTransactionCode(String systemTransactionCode) {
		putUsedField("systemTransactionCode", systemTransactionCode);
		this.systemTransactionCode = systemTransactionCode;
	}
	/**
	 * @return Returns the term.
	 */
	public long getTerm() {
		return term;
	}
	/**
	 * @param term The term to set.
	 */
	public void setTerm(long term) {
		putUsedField("term", term);
		this.term = term;
	}
	/**
	 * @return Returns the termType.
	 */
	public long getTermTypeID() {
		return termTypeID;
	}
	/**
	 * @param termType The termType to set.
	 */
	public void setTermTypeID(long termTypeID) {
		putUsedField("termTypeID", termTypeID);
		this.termTypeID = termTypeID;
	}
	/**
	 * @return Returns the transactionTypeID.
	 */
	public long getTransactionTypeID() {
		return transactionTypeID;
	}
	/**
	 * @param transactionTypeID The transactionTypeID to set.
	 */
	public void setTransactionTypeID(long transactionTypeID) {
		putUsedField("transactionTypeID", transactionTypeID);
		this.transactionTypeID = transactionTypeID;
	}
	/**
	 * @return Returns the valueDate.
	 */
	public Timestamp getValueDate() {
		return valueDate;
	}
	/**
	 * @param valueDate The valueDate to set.
	 */
	public void setValueDate(Timestamp valueDate) {
		putUsedField("valueDate", valueDate);
		this.valueDate = valueDate;
	}
}
