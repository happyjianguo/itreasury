package com.iss.itreasury.craftbrother.query.dataentity;

import java.sql.Timestamp;

import com.iss.itreasury.securities.util.SECBaseDataEntity;

public class QueryAttornContractResultInfo  extends SECBaseDataEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private long id = -1;

	private long officeId = -1;				//办事处ID
	private long currencyId = -1;			//币种ID
	private long transcationTypeId = -1;    //交易类型ID
	private String transcationType = "";    //交易类型名称
	private double totalSaleAmount = 0.0;   //累计卖出金额/累计买入金额
	private double totalBuyAmount = 0.0;   //累计收回金额/累计买入回购金额
	private String contractCode = "";      //合同编号
	private double contractAmount = 0.0;   //合同金额
	private double buyAmount = 0.0;        //买入的金额/卖出金额
	private double saleAmount = 0.0;       //收回的金额/买入回购金额
	private double contractRate = 0.0;     //合同利率
	private double noticeRate = 0.0;       //通知单利率
	private Timestamp inputDate = null;    //合同录入时间
	private Timestamp startDate = null;    //查询开始时间
	private Timestamp endDate = null;    //查询结束时间
	
	public double getContractAmount() {
		return contractAmount;
	}
	public void setContractAmount(double contractAmount) {
		this.contractAmount = contractAmount;
	}
	public String getContractCode() {
		return contractCode;
	}
	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}
	public double getContractRate() {
		return contractRate;
	}
	public void setContractRate(double contractRate) {
		this.contractRate = contractRate;
	}
	public long getCurrencyId() {
		return currencyId;
	}
	public void setCurrencyId(long currencyId) {
		this.currencyId = currencyId;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Timestamp getInputDate() {
		return inputDate;
	}
	public void setInputDate(Timestamp inputDate) {
		this.inputDate = inputDate;
	}
	public long getOfficeId() {
		return officeId;
	}
	public void setOfficeId(long officeId) {
		this.officeId = officeId;
	}
	public double getTotalBuyAmount() {
		return totalBuyAmount;
	}
	public void setTotalBuyAmount(double totalBuyAmount) {
		this.totalBuyAmount = totalBuyAmount;
	}
	public double getTotalSaleAmount() {
		return totalSaleAmount;
	}
	public void setTotalSaleAmount(double totalSaleAmount) {
		this.totalSaleAmount = totalSaleAmount;
	}
	public String getTranscationType() {
		return transcationType;
	}
	public void setTranscationType(String transcationType) {
		this.transcationType = transcationType;
	}
	public long getTranscationTypeId() {
		return transcationTypeId;
	}
	public void setTranscationTypeId(long transcationTypeId) {
		this.transcationTypeId = transcationTypeId;
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
	public double getBuyAmount() {
		return buyAmount;
	}
	public void setBuyAmount(double buyAmount) {
		this.buyAmount = buyAmount;
	}
	public double getSaleAmount() {
		return saleAmount;
	}
	public void setSaleAmount(double saleAmount) {
		this.saleAmount = saleAmount;
	}
	public double getNoticeRate() {
		return noticeRate;
	}
	public void setNoticeRate(double noticeRate) {
		this.noticeRate = noticeRate;
	}
	
}
