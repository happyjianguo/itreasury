package com.iss.itreasury.loan.query.dataentity;

import com.iss.sysframe.base.dataentity.BaseDataEntity;

public class QueryNoticeInfo extends BaseDataEntity {

	private long contractID = -1;			//查询放款通知单明细时需要的参数 ：合同ID
	private String  contractCodeFrom = null;//合同编号起始
	private String  contractCodeTo = null;	//合同编号终止
	
	private long  loanClientIDFrom = -1;	//借款单位起始
	private long  loanClientIDTo = -1;		//借款单位终止
	
	private long  consignIDFrom = -1;		//委托单位起始
	private long  consignIDTo = -1;		    //委托单位终止
	
	private double mPayAmountFrom = 0.00;	//放款金额起始
	private double mPayAmountTo = 0.00;		//放款金额终止
	
	private double mPayInterestFrom = 0.00;	//放款金额起始
	private double mPayInterestTo = 0.00;	//放款金额终止
	
	private String dtInputDateFrom = null;	//录入日期起始
	private String dtInputDateTo = null;	//录入日期终止
	
	private String dtRepayDateFrom = null;	//还款日期起始
	private String dtRepayDateTo = null;	//还款日期终止
	
	private String dtLoanPayDateFrom = null;//放款日期起始
	private String dtLoanPayDateTo = null;	//放款日期终止
	
	private String nLoanType = null;		//贷款类型列表

	private String nPayNoticeStatus = null;	//放款通知单状态列表
	private long nstatusid = -1;         	//是否转表外
	
	private double mRePayAmountFrom = 0.00;			//还款金额起始
	private double mRePayAmountTo = 0.00;			//还款金额终止
	
	private double DrawAmountInterestFrom = 0.00;	//归还利息起始
	private double DrawAmountInterestTo = 0.00;  	//归还利息终止
	
	private String nRePayNoticeStatus = null;    	//还款通知单状态列表
	private long IsHead = -1;
	
	private long nOfficeID = -1;
	private long nCurrencyID = -1;
	
	public long getContractID() {
		return contractID;
	}

	public void setContractID(long contractID) {
		this.contractID = contractID;
	}

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

	public String getDtInputDateFrom() {
		return dtInputDateFrom;
	}

	public void setDtInputDateFrom(String dtInputDateFrom) {
		this.dtInputDateFrom = dtInputDateFrom;
	}

	public String getDtInputDateTo() {
		return dtInputDateTo;
	}

	public void setDtInputDateTo(String dtInputDateTo) {
		this.dtInputDateTo = dtInputDateTo;
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

	public long getNstatusid() {
		return nstatusid;
	}

	public void setNstatusid(long nstatusid) {
		this.nstatusid = nstatusid;
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

	public double getMRePayAmountFrom() {
		return mRePayAmountFrom;
	}

	public void setMRePayAmountFrom(double rePayAmountFrom) {
		mRePayAmountFrom = rePayAmountFrom;
	}

	public double getMRePayAmountTo() {
		return mRePayAmountTo;
	}

	public void setMRePayAmountTo(double rePayAmountTo) {
		mRePayAmountTo = rePayAmountTo;
	}

	public double getDrawAmountInterestFrom() {
		return DrawAmountInterestFrom;
	}

	public void setDrawAmountInterestFrom(double drawAmountInterestFrom) {
		DrawAmountInterestFrom = drawAmountInterestFrom;
	}

	public double getDrawAmountInterestTo() {
		return DrawAmountInterestTo;
	}

	public void setDrawAmountInterestTo(double drawAmountInterestTo) {
		DrawAmountInterestTo = drawAmountInterestTo;
	}

	public String getNRePayNoticeStatus() {
		return nRePayNoticeStatus;
	}

	public void setNRePayNoticeStatus(String rePayNoticeStatus) {
		nRePayNoticeStatus = rePayNoticeStatus;
	}

	public long getIsHead() {
		return IsHead;
	}

	public void setIsHead(long isHead) {
		IsHead = isHead;
	}

	@Override
	public long getId() {
		return 0;
	}

	@Override
	public void setId(long id) {
		
	}

}
