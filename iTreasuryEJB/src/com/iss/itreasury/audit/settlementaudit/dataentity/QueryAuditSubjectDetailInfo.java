package com.iss.itreasury.audit.settlementaudit.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;

public class QueryAuditSubjectDetailInfo implements Serializable{

	private Timestamp billDate = null;
	private String credenceNo = "";
	private String paySubjectCode = "";
	private String paySubjectName = "";
	private String loanSubjectCode = "";
	private String loanSubjectName = "";
	private String subjectDigest = "";
	private double paySubjectBalance = 0.0;
	private double loanSubjectBalance = 0.0;
	public Timestamp getBillDate() {
		return billDate;
	}
	public void setBillDate(Timestamp billDate) {
		this.billDate = billDate;
	}
	public String getCredenceNo() {
		return credenceNo;
	}
	public void setCredenceNo(String credenceNo) {
		this.credenceNo = credenceNo;
	}
	public double getLoanSubjectBalance() {
		return loanSubjectBalance;
	}
	public void setLoanSubjectBalance(double loanSubjectBalance) {
		this.loanSubjectBalance = loanSubjectBalance;
	}
	public String getLoanSubjectCode() {
		return loanSubjectCode;
	}
	public void setLoanSubjectCode(String loanSubjectCode) {
		this.loanSubjectCode = loanSubjectCode;
	}
	public String getLoanSubjectName() {
		return loanSubjectName;
	}
	public void setLoanSubjectName(String loanSubjectName) {
		this.loanSubjectName = loanSubjectName;
	}
	public double getPaySubjectBalance() {
		return paySubjectBalance;
	}
	public void setPaySubjectBalance(double paySubjectBalance) {
		this.paySubjectBalance = paySubjectBalance;
	}
	public String getPaySubjectCode() {
		return paySubjectCode;
	}
	public void setPaySubjectCode(String paySubjectCode) {
		this.paySubjectCode = paySubjectCode;
	}
	public String getPaySubjectName() {
		return paySubjectName;
	}
	public void setPaySubjectName(String paySubjectName) {
		this.paySubjectName = paySubjectName;
	}
	public String getSubjectDigest() {
		return subjectDigest;
	}
	public void setSubjectDigest(String subjectDigest) {
		this.subjectDigest = subjectDigest;
	}
}
