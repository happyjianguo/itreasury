package com.iss.itreasury.project.wisgfc.loan.settcontract.dataentity;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
public class UploadContract {
	//合同编号
    private String sContractCode = "";
    //贷款类型
    private long nTypeID = -1;
    //借款单位
    private long nBorrowClientID = -1;
    //贷款金额
    private double mLoanAmount = 0;
    //借款原因
    private String sLoanReason = "";
    //贷款开始时间
    private Timestamp dtStartDate = null;
    //贷款结束时间
    private Timestamp dtEndDate = null;
    //贴现利率
    private double mDiscountRate = 0;
    //贷款利率ID
    private long mInterestRateID = 0;
    //贷款类型ID
    private long lLoanID =-1;
    //合同期限
    private long intervalNum = -1;
    //委托单位ID
    private long consignClientID = -1;
    //是否买方付息
    private long isPurchaserInterest = -1;
    //买方付息比例
    private double purchaserInterestRate = 0.0;
	public long getConsignClientID() {
		return consignClientID;
	}
	public void setConsignClientID(long consignClientID) {
		this.consignClientID = consignClientID;
	}
	public long getLLoanID() {
		return lLoanID;
	}
	public void setLLoanID(long loanID) {
		lLoanID = loanID;
	}
	public String getSContractCode() {
		return sContractCode;
	}
	public void setSContractCode(String contractCode) {
		sContractCode = contractCode;
	}
	public long getNTypeID() {
		return nTypeID;
	}
	public void setNTypeID(long typeID) {
		nTypeID = typeID;
	}
	public long getNBorrowClientID() {
		return nBorrowClientID;
	}
	public void setNBorrowClientID(long borrowClientID) {
		nBorrowClientID = borrowClientID;
	}
	public double getMLoanAmount() {
		return mLoanAmount;
	}
	public void setMLoanAmount(double loanAmount) {
		mLoanAmount = loanAmount;
	}
	public String getSLoanReason() {
		return sLoanReason;
	}
	public void setSLoanReason(String loanReason) {
		sLoanReason = loanReason;
	}
	public Timestamp getDtStartDate() {
		return dtStartDate;
	}
	public void setDtStartDate(String dtStartDate) throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		this.dtStartDate = new Timestamp(dateFormat.parse(dtStartDate).getTime());
	}
	public Timestamp getDtEndDate() {
		return dtEndDate;
	}
	public void setDtEndDate(String dtEndDate) throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		this.dtStartDate = new Timestamp(dateFormat.parse(dtEndDate).getTime());
	}
	public double getMDiscountRate() {
		return mDiscountRate;
	}
	public void setMDiscountRate(double discountRate) {
		mDiscountRate = discountRate;
	}
	public long getMInterestRateID() {
		return mInterestRateID;
	}
	public void setMInterestRateID(long interestRateID) {
		mInterestRateID = interestRateID;
	}
	public long getIntervalNum() {
		return intervalNum;
	}
	public void setIntervalNum(long intervalNum) {
		this.intervalNum = intervalNum;
	}
	public long getIsPurchaserInterest() {
		return isPurchaserInterest;
	}
	public void setIsPurchaserInterest(long isPurchaserInterest) {
		this.isPurchaserInterest = isPurchaserInterest;
	}
	public double getPurchaserInterestRate() {
		return purchaserInterestRate;
	}
	public void setPurchaserInterestRate(double purchaserInterestRate) {
		this.purchaserInterestRate = purchaserInterestRate;
	}

}
