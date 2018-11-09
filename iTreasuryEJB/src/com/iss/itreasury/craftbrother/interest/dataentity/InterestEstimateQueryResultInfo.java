package com.iss.itreasury.craftbrother.interest.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;
/**
 *
 * 资产转让合同利息匡算页面查询结果信息实体。
 * 该类主要用来保存页面显示的查询结果，在页面上显示。
 */
public class InterestEstimateQueryResultInfo implements Serializable{
	
	public InterestEstimateQueryResultInfo()
	{
		super();
		// TODO Auto-generated constructor stub
	}
	
	private long transactionTypeId = -1;	//资产转让类型
	private long counterpartId = -1;		//交易对手
	private String contractNo = "";     //合同编号
	private Timestamp contractStartDate = null;	//合同开始日期（转让日期）
	private Timestamp contractEndDate = null;	//合同结束日期（回购日期）
	private double amount = 0.0;				//合同金额（转让金额）
	private double rate = 0.0;        //利率 
	private long interestDays = -1;   //计息天数
	private double interestPayment = 0.00;   //应付利息 
	private double interestReceived = 0.00;	 //应收利息
	
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
