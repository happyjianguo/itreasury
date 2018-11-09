package com.iss.itreasury.credit.setting.dataentity;

import java.sql.Timestamp;

import com.iss.itreasury.util.ITreasuryBaseDataEntity;

public class ContractUsedAmountInfo extends ITreasuryBaseDataEntity {

	private long id = -1;
	private String clientName = "";//客户名称
	private String contractNo = "";//合同编号
	private long loanType = -1;
	private double contractAmount = 0.0;//贷款金额
	private double contractBalance = 0.0;//贷款余额
	private Timestamp startDate = null;//开始日期
	private Timestamp endDate = null;//结束日期
	private long term = -1;//期限
	private long contractState = -1;//合同状态
	
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
	public double getContractAmount() {
		return contractAmount;
	}
	public void setContractAmount(double contractAmount) {
		this.contractAmount = contractAmount;
	}
	public double getContractBalance() {
		return contractBalance;
	}
	public void setContractBalance(double contractBalance) {
		this.contractBalance = contractBalance;
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
