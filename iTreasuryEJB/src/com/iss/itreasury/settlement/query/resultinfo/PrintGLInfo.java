package com.iss.itreasury.settlement.query.resultinfo;

import java.sql.Timestamp;

public class PrintGLInfo {
	//查询条件
	private Timestamp StartDate = null;//开始日期
	private Timestamp EndDate = null;//结束日期
	private String ssubjectcode=""; //科目编号
	private long id = -1; //科目ID
	//查询结果
	private String TransNo = "";//交易编号
	private long TransTypeID = -1;//交易类型
	private String DepositNo = "";//存单号
	private String BillNo = "";//票据号
	private String BankChequeNo = "";//银行支票号
	private double PayAmount = 0.0;//付款金额
	private double ReceiveAmount = 0.0;//收款金额
	private double Balance = 0.0;//余额
	private Timestamp ExecuteDate = null;//日期
	private long ExecuteDay = -1;//天
	private long ExecuteMonth = -1;//月份
	private long ExecuteYear = -1;//年
	private String Abstract = "";//摘要
	private long nsubjecttype=-1;//科目属性
	private long lPageCount=0;
	public long getNsubjecttype() {
		return nsubjecttype;
	}
	public void setNsubjecttype(long nsubjecttype) {
		this.nsubjecttype = nsubjecttype;
	}
	public String getAbstract() {
		return Abstract;
	}
	public void setAbstract(String abstract1) {
		Abstract = abstract1;
	}
	public double getBalance() {
		return Balance;
	}
	public void setBalance(double balance) {
		Balance = balance;
	}
	public String getBankChequeNo() {
		return BankChequeNo;
	}
	public void setBankChequeNo(String bankChequeNo) {
		BankChequeNo = bankChequeNo;
	}
	public String getBillNo() {
		return BillNo;
	}
	public void setBillNo(String billNo) {
		BillNo = billNo;
	}
	public String getDepositNo() {
		return DepositNo;
	}
	public void setDepositNo(String depositNo) {
		DepositNo = depositNo;
	}
	public Timestamp getEndDate() {
		return EndDate;
	}
	public void setEndDate(Timestamp endDate) {
		EndDate = endDate;
	}
	public Timestamp getExecuteDate() {
		return ExecuteDate;
	}
	public void setExecuteDate(Timestamp executeDate) {
		ExecuteDate = executeDate;
	}
	public long getExecuteDay() {
		return ExecuteDay;
	}
	public void setExecuteDay(long executeDay) {
		ExecuteDay = executeDay;
	}
	public long getExecuteMonth() {
		return ExecuteMonth;
	}
	public void setExecuteMonth(long executeMonth) {
		ExecuteMonth = executeMonth;
	}
	public long getExecuteYear() {
		return ExecuteYear;
	}
	public void setExecuteYear(long executeYear) {
		ExecuteYear = executeYear;
	}
	public double getPayAmount() {
		return PayAmount;
	}
	public void setPayAmount(double payAmount) {
		PayAmount = payAmount;
	}
	public double getReceiveAmount() {
		return ReceiveAmount;
	}
	public void setReceiveAmount(double receiveAmount) {
		ReceiveAmount = receiveAmount;
	}
	public String getSsubjectcode() {
		return ssubjectcode;
	}
	public void setSsubjectcode(String ssubjectcode) {
		this.ssubjectcode = ssubjectcode;
	}
	public Timestamp getStartDate() {
		return StartDate;
	}
	public void setStartDate(Timestamp startDate) {
		StartDate = startDate;
	}
	public String getTransNo() {
		return TransNo;
	}
	public void setTransNo(String transNo) {
		TransNo = transNo;
	}
	public long getTransTypeID() {
		return TransTypeID;
	}
	public void setTransTypeID(long transTypeID) {
		TransTypeID = transTypeID;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getLPageCount() {
		return lPageCount;
	}
	public void setLPageCount(long pageCount) {
		lPageCount = pageCount;
	}
	
}
