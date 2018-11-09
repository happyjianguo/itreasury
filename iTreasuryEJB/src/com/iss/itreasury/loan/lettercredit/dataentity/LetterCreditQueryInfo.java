package com.iss.itreasury.loan.lettercredit.dataentity;

public class LetterCreditQueryInfo {
	private long officeId;
	private String applyCompanyCode;	//开证申请单位
	private String agreementNo;			//合同/协议号
	private String letterCreditNo;		//信用证号
	private int bankCreditextID;		//授信合同号
	private String bankName;			//开证银行
	private int paymentMode;			//支付方式
	private int type;					//种类
	private int status;					//状态
	private String fromStartDate;		//开证日期 
	private String endStartDate;   		//开证日期
	
	
	public long getOfficeId() {
		return officeId;
	}

	public void setOfficeId(long officeId) {
		this.officeId = officeId;
	}

	public String getEndStartDate() {
		return endStartDate;
	}

	public void setEndStartDate(String endStartDate) {
		this.endStartDate = endStartDate;
	}

	public String getFromStartDate() {
		return fromStartDate;
	}

	public void setFromStartDate(String fromStartDate) {
		this.fromStartDate = fromStartDate;
	}

	public String getApplyCompanyCode() {
		return this.applyCompanyCode;
	}
	
	public void setApplyCompanyCode(String applyCompanyCode) {
		this.applyCompanyCode = applyCompanyCode;
	}
	
	public String getAgreementNo() {
		return this.agreementNo;
	}
	
	public void setAgreementNo(String agreementNo) {
		this.agreementNo = agreementNo;
	}
	
	public String getLetterCreditNo() {
		return this.letterCreditNo;
	}
	
	public void setLetterCreditNo(String letterCreditNo) {
		this.letterCreditNo = letterCreditNo;
	}
	
	public int getBankCreditextID() {
		return this.bankCreditextID;
	}
	
	public void setBankCreditextID(int bankCreditextID) {
		this.bankCreditextID = bankCreditextID;
	}
	
	public String getBankName() {
		return this.bankName;
	}
	
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	
	public int getPaymentMode() {
		return this.paymentMode;
	}
	
	public void setPaymentMode(int paymentMode) {
		this.paymentMode = paymentMode;
	}
	
	public int getType() {
		return this.type;
	}
	
	public void setType(int type) {
		this.type = type;
	}
	
	public int getStatus() {
		return this.status;
	}
	
	public void setStatus(int status) {
		this.status = status;
	}
}
