package com.iss.itreasury.craftbrother.query.dataentity;

import java.sql.Timestamp;

public class AssetInterestCalResultInfo{
	public long contractType = -1;//ת�ú�ͬ����
	public long borrowclientId = -1;//��λID
	public String loanContractNo = "";//�����ͬ��
	public String payFormNo = "";//	�ſ�֪ͨ����
	public String attornContractNo = "";//ת�ú�ͬ��
	public long bankId = -1;//��������ID
	public Timestamp interestStartDate = null;// ��Ϣ��ʼʱ��
	public Timestamp interestEndDate = null;//��Ϣ����ʱ��
	public double receiveInterestTotalAmount = 0.0;//Ӧ����Ϣ�ܶ�
	public double selfHaveAmount = 0.0;//������Ϣ���
	public double comReceiveInterestAmount = 0.0;//������Ϣ���
	public double comReceivePoundage = 0.0;//Ӧ��������
	public double payInterestAmount =0.0;//Ӧ����Ϣ���
	public double rate = 0.0;//����
	public double commissionchargerate = 0.0; //����������
	public double payformrate = 0.0;//�ſ�֪ͨ������
	public double mstaidadjustrate = 0.0;//�̶���������
	public double madjustrate = 0.0;//��������
	public long payid = -1;//�ſ�֪ͨ��ID
	public Timestamp outdate = null;  //�ſ�֪ͨ���ķſ�����
	
	public Timestamp getOutdate() {
		return outdate;
	}
	public void setOutdate(Timestamp outdate) {
		this.outdate = outdate;
	}
	public long getPayid() {
		return payid;
	}
	public void setPayid(long payid) {
		this.payid = payid;
	}
	public String getAttornContractNo() {
		return attornContractNo;
	}
	public void setAttornContractNo(String attornContractNo) {
		this.attornContractNo = attornContractNo;
	}
	public long getBankId() {
		return bankId;
	}
	public void setBankId(long bankId) {
		this.bankId = bankId;
	}
	public long getBorrowclientId() {
		return borrowclientId;
	}
	public void setBorrowclientId(long borrowclientId) {
		this.borrowclientId = borrowclientId;
	}
	public double getComReceiveInterestAmount() {
		return comReceiveInterestAmount;
	}
	public void setComReceiveInterestAmount(double comReceiveInterestAmount) {
		this.comReceiveInterestAmount = comReceiveInterestAmount;
	}
	public double getComReceivePoundage() {
		return comReceivePoundage;
	}
	public void setComReceivePoundage(double comReceivePoundage) {
		this.comReceivePoundage = comReceivePoundage;
	}
	public long getContractType() {
		return contractType;
	}
	public void setContractType(long contractType) {
		this.contractType = contractType;
	}
	public Timestamp getInterestEndDate() {
		return interestEndDate;
	}
	public void setInterestEndDate(Timestamp interestEndDate) {
		this.interestEndDate = interestEndDate;
	}
	public Timestamp getInterestStartDate() {
		return interestStartDate;
	}
	public void setInterestStartDate(Timestamp interestStartDate) {
		this.interestStartDate = interestStartDate;
	}
	public String getLoanContractNo() {
		return loanContractNo;
	}
	public void setLoanContractNo(String loanContractNo) {
		this.loanContractNo = loanContractNo;
	}
	public String getPayFormNo() {
		return payFormNo;
	}
	public void setPayFormNo(String payFormNo) {
		this.payFormNo = payFormNo;
	}
	public double getPayInterestAmount() {
		return payInterestAmount;
	}
	public void setPayInterestAmount(double payInterestAmount) {
		this.payInterestAmount = payInterestAmount;
	}
	public double getReceiveInterestTotalAmount() {
		return receiveInterestTotalAmount;
	}
	public void setReceiveInterestTotalAmount(double receiveInterestTotalAmount) {
		this.receiveInterestTotalAmount = receiveInterestTotalAmount;
	}
	public double getSelfHaveAmount() {
		return selfHaveAmount;
	}
	public void setSelfHaveAmount(double selfHaveAmount) {
		this.selfHaveAmount = selfHaveAmount;
	}
	public double getCommissionchargerate() {
		return commissionchargerate;
	}
	public void setCommissionchargerate(double commissionchargerate) {
		this.commissionchargerate = commissionchargerate;
	}
	public double getRate() {
		return rate;
	}
	public void setRate(double rate) {
		this.rate = rate;
	}
	public double getPayformrate() {
		return payformrate;
	}
	public void setPayformrate(double payformrate) {
		this.payformrate = payformrate;
	}
	public double getMadjustrate() {
		return madjustrate;
	}
	public void setMadjustrate(double madjustrate) {
		this.madjustrate = madjustrate;
	}
	public double getMstaidadjustrate() {
		return mstaidadjustrate;
	}
	public void setMstaidadjustrate(double mstaidadjustrate) {
		this.mstaidadjustrate = mstaidadjustrate;
	}
}