package com.iss.itreasury.loan.lettercredit.dataentity;

import java.util.Date;

import com.iss.itreasury.util.DataFormat;

public class LetterCreditResultInfo {
	private String innerCode;		//内部流水号
	private String applyCompanyName;//开证申请单位名称
	private String letterCreditNo;	//信用证号
	private String contractNo;		//授信合同号
	private String bankName;		//授信银行
	private String startDate;		//开证日期
	private long currencyType;		//币种
	private double applyAmount;		//信用证金额
	private double paymentAmount;	//实际支付总额
	private long status;			//状态
	

	public String getInnerCode() {
		return this.innerCode;
	}
	
	public void setInnerCode(String innerCode) {
		this.innerCode = innerCode;
	}
	
	public String getApplyCompanyName() {
		return this.applyCompanyName;
	}
	
	public void setApplyCompanyName(String applyCompanyName) {
		this.applyCompanyName = applyCompanyName;
	}
	
	public String getLetterCreditNo() {
		return this.letterCreditNo;
	}
	
	public void setLetterCreditNo(String letterCreditNo) {
		this.letterCreditNo = letterCreditNo;
	}
	
	public String getContractNo() {
		return this.contractNo;
	}
	
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	
	public String getBankName() {
		return this.bankName;
	}
	
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	
	public String getStartDate() {
		return this.startDate;
	}
	
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	
	public long getCurrencyType() {
		return this.currencyType;
	}
	
	public void setCurrencyType(long currencyType) {
		this.currencyType = currencyType;
	}
	
	public double getApplyAmount() {
		return this.applyAmount;
	}
	
	public void setApplyAmount(double applyAmount) {
		this.applyAmount = applyAmount;
	}
	
	public double getPaymentAmount() {
		return this.paymentAmount;
	}
	
	public void setPaymentAmount(double paymentAmount) {
		this.paymentAmount = paymentAmount;
	}
	
	public long getStatus() {
		return this.status;
	}
	
	public void setStatus(long status) {
		this.status = status;
	}
	
	public void setStartDate(Date startDate){
		this.startDate = DataFormat.formatDate(startDate,1);
	}
}
