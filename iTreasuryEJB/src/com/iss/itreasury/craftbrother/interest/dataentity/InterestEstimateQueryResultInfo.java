package com.iss.itreasury.craftbrother.interest.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;
/**
 *
 * �ʲ�ת�ú�ͬ��Ϣ����ҳ���ѯ�����Ϣʵ�塣
 * ������Ҫ��������ҳ����ʾ�Ĳ�ѯ�������ҳ������ʾ��
 */
public class InterestEstimateQueryResultInfo implements Serializable{
	
	public InterestEstimateQueryResultInfo()
	{
		super();
		// TODO Auto-generated constructor stub
	}
	
	private long transactionTypeId = -1;	//�ʲ�ת������
	private long counterpartId = -1;		//���׶���
	private String contractNo = "";     //��ͬ���
	private Timestamp contractStartDate = null;	//��ͬ��ʼ���ڣ�ת�����ڣ�
	private Timestamp contractEndDate = null;	//��ͬ�������ڣ��ع����ڣ�
	private double amount = 0.0;				//��ͬ��ת�ý�
	private double rate = 0.0;        //���� 
	private long interestDays = -1;   //��Ϣ����
	private double interestPayment = 0.00;   //Ӧ����Ϣ 
	private double interestReceived = 0.00;	 //Ӧ����Ϣ
	
	public long getTransactionTypeId() {
		return transactionTypeId;
	}
	public void setTransactionTypeId(long transactionTypeId) {
		this.transactionTypeId = transactionTypeId;
	}
	public long getCounterpartId() {
		return counterpartId;
	}
	public void setCounterpartId(long counterpartId) {
		this.counterpartId = counterpartId;
	}
	public String getContractNo() {
		return contractNo;
	}
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	public Timestamp getContractStartDate() {
		return contractStartDate;
	}
	public void setContractStartDate(Timestamp contractStartDate) {
		this.contractStartDate = contractStartDate;
	}
	public Timestamp getContractEndDate() {
		return contractEndDate;
	}
	public void setContractEndDate(Timestamp contractEndDate) {
		this.contractEndDate = contractEndDate;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public double getAmount() {
		return amount;
	}
	public void setRate(double rate) {
		this.rate = rate;
	}
	public double getRate() {
		return rate;
	}
	public long getInterestDays() {
		return interestDays;
	}
	public void setInterestDays(long interestDays) {
		this.interestDays = interestDays;
	}
	public double getInterestPayment() {
		return interestPayment;
	}
	public void setInterestPayment(double interestPayment) {
		this.interestPayment = interestPayment;
	}
	public double getInterestReceived() {
		return interestReceived;
	}
	public void setInterestReceived(double interestReceived) {
		this.interestReceived = interestReceived;
	}
	
	

}
