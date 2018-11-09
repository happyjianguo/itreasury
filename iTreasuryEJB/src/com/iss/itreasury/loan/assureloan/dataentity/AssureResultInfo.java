package com.iss.itreasury.loan.assureloan.dataentity;



public class AssureResultInfo {
	private String contractNo;
	private double amount;
	private String clientCode;
	private String clientName;
	private double shortTermAmount;
	private double longTermAmount;
	private double letterCreditAmount;
	private double letterGuaranteeAmount;
	private double creditProveAmount;
	private double acceptBillAmount;
	
	public double getAcceptBillAmount() {
		return acceptBillAmount;
	}
	public void setAcceptBillAmount(double acceptBillAmount) {
		this.acceptBillAmount = acceptBillAmount;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getClientCode() {
		return clientCode;
	}
	public void setClientCode(String clientCode) {
		this.clientCode = clientCode;
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
	public double getCreditProveAmount() {
		return creditProveAmount;
	}
	public void setCreditProveAmount(double creditProveAmount) {
		this.creditProveAmount = creditProveAmount;
	}
	public double getLetterCreditAmount() {
		return letterCreditAmount;
	}
	public void setLetterCreditAmount(double letterCreditAmount) {
		this.letterCreditAmount = letterCreditAmount;
	}
	public double getLetterGuaranteeAmount() {
		return letterGuaranteeAmount;
	}
	public void setLetterGuaranteeAmount(double letterGuaranteeAmount) {
		this.letterGuaranteeAmount = letterGuaranteeAmount;
	}
	public double getLongTermAmount() {
		return longTermAmount;
	}
	public void setLongTermAmount(double longTermAmount) {
		this.longTermAmount = longTermAmount;
	}
	public double getShortTermAmount() {
		return shortTermAmount;
	}
	public void setShortTermAmount(double shortTermAmount) {
		this.shortTermAmount = shortTermAmount;
	}
	
	public String toString() {
		return this.contractNo+"\t"+this.getClientCode()+"\t"+this.getClientName();
	}
}
