package com.iss.itreasury.settlement.query.resultinfo;

import java.io.Serializable;
import java.sql.Timestamp;

public class QueryCraTransInterestResultInfo implements Serializable
{
   private Timestamp tsCleardate=null;
   private long payIntrestAcountId=-1;
   private String payIntrestCode="";
   private String craContractCode="";
   private String loanContractCode="";
   private String payNoticeForm="";
   private String countpartName="";
   private String clientName="";
   private double rate=0.00;
   private double amout=0.0;
   private double intrest=0.0;
   private String tranNo="";
   private long inputUser=-1;
   private String username="";
   private Timestamp inputdate=null;
   private long ninteresttype=-1;
public long getNinteresttype() {
	return ninteresttype;
}
public void setNinteresttype(long ninteresttype) {
	this.ninteresttype = ninteresttype;
}
public Timestamp getInputdate() {
	return inputdate;
}
public void setInputdate(Timestamp inputdate) {
	this.inputdate = inputdate;
}
public String getUsername() {
	return username;
}
public void setUsername(String username) {
	this.username = username;
}
public Timestamp getTsCleardate() {
	return tsCleardate;
}
public void setTsCleardate(Timestamp tsCleardate) {
	this.tsCleardate = tsCleardate;
}
public long getPayIntrestAcountId() {
	return payIntrestAcountId;
}
public void setPayIntrestAcountId(long payIntrestAcountId) {
	this.payIntrestAcountId = payIntrestAcountId;
}
public String getPayIntrestCode() {
	return payIntrestCode;
}
public void setPayIntrestCode(String payIntrestCode) {
	this.payIntrestCode = payIntrestCode;
}
public String getCraContractCode() {
	return craContractCode;
}
public void setCraContractCode(String craContractCode) {
	this.craContractCode = craContractCode;
}
public String getLoanContractCode() {
	return loanContractCode;
}
public void setLoanContractCode(String loanContractCode) {
	this.loanContractCode = loanContractCode;
}
public String getPayNoticeForm() {
	return payNoticeForm;
}
public void setPayNoticeForm(String payNoticeForm) {
	this.payNoticeForm = payNoticeForm;
}
public String getCountpartName() {
	return countpartName;
}
public void setCountpartName(String countpartName) {
	this.countpartName = countpartName;
}
public String getClientName() {
	return clientName;
}
public void setClientName(String clientName) {
	this.clientName = clientName;
}
public double getRate() {
	return rate;
}
public void setRate(double rate) {
	this.rate = rate;
}
public double getAmout() {
	return amout;
}
public void setAmout(double amout) {
	this.amout = amout;
}
public double getIntrest() {
	return intrest;
}
public void setIntrest(double intrest) {
	this.intrest = intrest;
}
public String getTranNo() {
	return tranNo;
}
public void setTranNo(String tranNo) {
	this.tranNo = tranNo;
}
public long getInputUser() {
	return inputUser;
}
public void setInputUser(long inputUser) {
	this.inputUser = inputUser;
}
   
   
   
   
   
}
