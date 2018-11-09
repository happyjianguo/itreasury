package com.iss.itreasury.credit.setting.dataentity;

import java.sql.Timestamp;

import com.iss.itreasury.util.ITreasuryBaseDataEntity;

public class ApplyUsedAmountInfo extends ITreasuryBaseDataEntity {

	private long id = -1;
	private String clientName = "";//�ͻ�����
	private long loanType = -1; //��������
	private String applyNo = "";//���뵥���
	private double applyAmount = 0.0;//�����
	private Timestamp startDate = null;//��ʼ����
	private Timestamp endDate = null;//��������
	private long term = -1;//����
	private long applyState = -1;//���뵥״̬
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
		putUsedField("id", id);
	}
	
	public double getApplyAmount() {
		return applyAmount;
	}
	public void setApplyAmount(double applyAmount) {
		this.applyAmount = applyAmount;
	}
	public String getApplyNo() {
		return applyNo;
	}
	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
	}
	public long getApplyState() {
		return applyState;
	}
	public void setApplyState(long applyState) {
		this.applyState = applyState;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public Timestamp getEndDate() {
		return endDate;
	}
	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
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
	public long getLoanType() {
		return loanType;
	}
	public void setLoanType(long loanType) {
		this.loanType = loanType;
	}
}
