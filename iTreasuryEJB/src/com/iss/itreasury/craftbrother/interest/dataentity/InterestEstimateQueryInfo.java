package com.iss.itreasury.craftbrother.interest.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;
/**
 *
 * �ʲ�ת�ú�ͬ��Ϣ����ҳ���ѯ��Ϣʵ�塣
 * ������Ҫ��������ҳ��Ĳ�ѯ������
 */
public class InterestEstimateQueryInfo implements Serializable{
	
	public InterestEstimateQueryInfo()
	{
		super();
		// TODO Auto-generated constructor stub
	}
	
	private long transactionTypeId = -1;	//�ʲ�ת������
	private String contractNoFrom = "";     //��ͬ��ſ�ʼ
	private String contractNoTo = "";       //��ͬ��Ž���
	private Timestamp estimateDate = null;  //��������  
	
	public long getTransactionTypeId() {
		return transactionTypeId;
	}
	public void setTransactionTypeId(long transactionTypeId) {
		this.transactionTypeId = transactionTypeId;
	}
	public String getContractNoFrom() {
		return contractNoFrom;
	}
	public void setContractNoFrom(String contractNoFrom) {
		this.contractNoFrom = contractNoFrom;
	}
	public String getContractNoTo() {
		return contractNoTo;
	}
	public void setContractNoTo(String contractNoTo) {
		this.contractNoTo = contractNoTo;
	}
	public Timestamp getEstimateDate() {
		return estimateDate;
	}
	public void setEstimateDate(Timestamp estimateDate) {
		this.estimateDate = estimateDate;
	}
	
	

}
