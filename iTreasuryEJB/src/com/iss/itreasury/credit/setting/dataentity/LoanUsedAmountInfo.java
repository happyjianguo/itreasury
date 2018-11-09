package com.iss.itreasury.credit.setting.dataentity;

import java.sql.Timestamp;

import com.iss.itreasury.util.ITreasuryBaseDataEntity;

public class LoanUsedAmountInfo extends ITreasuryBaseDataEntity {
	private long id = -1;
	private String clientName = "";//客户名称
	private String clientCode = "";//客户编号
	private String contractNo = "";//合同编号
	private String loanpayformNo = "";//放款通知单编号
	private double loanpayformAmount = 0.0;//放款通知单金额
	private Timestamp startDate = null;//放款通知单开始日期
	private Timestamp endDate = null;//放款通知单结束日期
	private long loanpayformState = -1;//放款通知单状态
	
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
	public String getClientCode() {
		return clientCode;
	}
	public void setClientCode(String clientCode) {
		this.clientCode = clientCode;
	}
	public double getLoanpayformAmount() {
		return loanpayformAmount;
	}
	public void setLoanpayformAmount(double loanpayformAmount) {
		this.loanpayformAmount = loanpayformAmount;
	}
	public String getLoanpayformNo() {
		return loanpayformNo;
	}
	public void setLoanpayformNo(String loanpayformNo) {
		this.loanpayformNo = loanpayformNo;
	}
	public long getLoanpayformState() {
		return loanpayformState;
	}
	public void setLoanpayformState(long loanpayformState) {
		this.loanpayformState = loanpayformState;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
}
