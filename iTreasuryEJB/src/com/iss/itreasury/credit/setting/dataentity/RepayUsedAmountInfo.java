package com.iss.itreasury.credit.setting.dataentity;

import java.sql.Timestamp;

import com.iss.itreasury.util.ITreasuryBaseDataEntity;

public class RepayUsedAmountInfo extends ITreasuryBaseDataEntity {

	private long id = -1;
	private String clientName = "";//�ͻ�����
	private String contractNo = "";//��ͬ���
	private double repayAmount = 0.0;//������
	private Timestamp startDate = null;//��ʼ����
	private Timestamp endDate = null;//��������
	private long term = -1;//����
	private long contractState = -1;//��ͬ״̬
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
		putUsedField("id", id);
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public String getContractNo() {
		return contractNo;
	}
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	public long getContractState() {
		return contractState;
	}
	public void setContractState(long contractState) {
		this.contractState = contractState;
	}
	public Timestamp getEndDate() {
		return endDate;
	}
	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}
	public double getRepayAmount() {
		return repayAmount;
	}
	public void setRepayAmount(double repayAmount) {
		this.repayAmount = repayAmount;
	}
	public Timestamp getStartDate() {
		return startDate;
	}
	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}
	public long getTerm() {
		return term;
	}
	public void setTerm(long term) {
		this.term = term;
	}
	
}
