package com.iss.itreasury.loan.query.dataentity;

import java.io.Serializable;


public class QuestPayNoticeInfo extends Object implements Serializable {
	
	private long contractID;//查询放款通知单明细时需要的参数 ：合同ID
	private String  contractCodeFrom;	//合同编号起始
	private String  contractCodeTo;		//合同编号终止
	
	private long  loanClientIDFrom;		//借款单位起始
	private long  loanClientIDTo;		//借款单位终止
	
	private long  consignIDFrom;		//委托单位起始
	private long  consignIDTo;		    //委托单位终止
	
	private double mPayAmountFrom;		//放款金额起始
	private double mPayAmountTo;		//放款金额终止
	
	private double mPayInterestFrom;		//放款金额起始
	private double mPayInterestTo;		//放款金额终止
	
	private String dtInputDateFrom;	    //录入日期起始
	private String dtInputDateTo;	    //录入日期终止
	
	private String dtRepayDateFrom;	     //还款日期起始
	private String dtRepayDateTo;	     //还款日期终止
	
	private String dtLoanPayDateFrom;	 //放款日期起始
	private String dtLoanPayDateTo;	     //放款日期终止

	private String nPayNoticeStatus;     //放款通知单状态列表
	
	private String nLoanType;            //贷款类型列表
	private long nstatusid =-1;         //是否转表外
	
	private long nOfficeID;
	
	private long nCurrencyID;
	
	
	

	public String getContractCodeFrom() {
		return contractCodeFrom;
	}

	public void setContractCodeFrom(String contractCodeFrom) {
		this.contractCodeFrom = contractCodeFrom;
	}

	public String getContractCodeTo() {
		return contractCodeTo;
	}

	public void setContractCodeTo(String contractCodeTo) {
		this.contractCodeTo = contractCodeTo;
	}

	public long getLoanClientIDFrom() {
		return loanClientIDFrom;
	}

	public void setLoanClientIDFrom(long loanClientIDFrom) {
		this.loanClientIDFrom = loanClientIDFrom;
	}

	public long getLoanClientIDTo() {
		return loanClientIDTo;
	}

	public void setLoanClientIDTo(long loanClientIDTo) {
		this.loanClientIDTo = loanClientIDTo;
	}



	public String getDtInputDateFrom() {
		return dtInputDateFrom;
	}

	public void setDtInputDateFrom(String dtInputDateFrom) {
		this.dtInputDateFrom = dtInputDateFrom;
	}

	public String getDtInputDateTo() {
		return dtInputDateTo;
	}

	public long getConsignIDFrom() {
		return consignIDFrom;
	}

	public void setConsignIDFrom(long consignIDFrom) {
		this.consignIDFrom = consignIDFrom;
	}

	public long getConsignIDTo() {
		return consignIDTo;
	}

	public void setConsignIDTo(long consignIDTo) {
		this.consignIDTo = consignIDTo;
	}

	public double getMPayAmountFrom() {
		return mPayAmountFrom;
	}

	public void setMPayAmountFrom(double payAmountFrom) {
		mPayAmountFrom = payAmountFrom;
	}

	public double getMPayAmountTo() {
		return mPayAmountTo;
	}

	public void setMPayAmountTo(double payAmountTo) {
		mPayAmountTo = payAmountTo;
	}

	public String getDtRepayDateFrom() {
		return dtRepayDateFrom;
	}

	public void setDtRepayDateFrom(String dtRepayDateFrom) {
		this.dtRepayDateFrom = dtRepayDateFrom;
	}

	public String getDtRepayDateTo() {
		return dtRepayDateTo;
	}

	public void setDtRepayDateTo(String dtRepayDateTo) {
		this.dtRepayDateTo = dtRepayDateTo;
	}

	public String getDtLoanPayDateFrom() {
		return dtLoanPayDateFrom;
	}

	public void setDtLoanPayDateFrom(String dtLoanPayDateFrom) {
		this.dtLoanPayDateFrom = dtLoanPayDateFrom;
	}

	public String getDtLoanPayDateTo() {
		return dtLoanPayDateTo;
	}

	public void setDtLoanPayDateTo(String dtLoanPayDateTo) {
		this.dtLoanPayDateTo = dtLoanPayDateTo;
	}

	public String getNPayNoticeStatus() {
		return nPayNoticeStatus;
	}

	public void setNPayNoticeStatus(String payNoticeStatus) {
		nPayNoticeStatus = payNoticeStatus;
	}

	public String getNLoanType() {
		return nLoanType;
	}

	public void setNLoanType(String loanType) {
		nLoanType = loanType;
	}

	public void setDtInputDateTo(String dtInputDateTo) {
		this.dtInputDateTo = dtInputDateTo;
	}
	public long getNOfficeID() {
		return nOfficeID;
	}

	public void setNOfficeID(long officeID) {
		nOfficeID = officeID;
	}

	public long getNCurrencyID() {
		return nCurrencyID;
	}

	public void setNCurrencyID(long currencyID) {
		nCurrencyID = currencyID;
	}

	public double getMPayInterestFrom() {
		return mPayInterestFrom;
	}

	public void setMPayInterestFrom(double payInterestFrom) {
		mPayInterestFrom = payInterestFrom;
	}

	public double getMPayInterestTo() {
		return mPayInterestTo;
	}

	public void setMPayInterestTo(double payInterestTo) {
		mPayInterestTo = payInterestTo;
	}

	public long getContractID() {
		return contractID;
	}

	public void setContractID(long contractID) {
		this.contractID = contractID;
	}

	public long getNstatusid() {
		return nstatusid;
	}

	public void setNstatusid(long nstatusid) {
		this.nstatusid = nstatusid;
	}
	

}
