package com.iss.itreasury.loan.contract.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;

public class TXContractDetailInfo implements Serializable{
	
	//ID:合同标识
	private long ContractID;
	
	//sContractCode:合同编号
	private String ContractCode;
	
	//nLoanTypeID:贷款种类
	private long LoanTypeID;
	private String LoanTypeName;
	private long SubTypeID;
	
	//sName:贷款单位名称
	private String BorrowClientName;

	//ExamineAmount 批准金额
	private double ExamineAmount;
	
	//汇总贴现核定金额
	private double CheckAmount;

	//贴现利息
	private double DiscountInterest;
	//sInputUserName 录入人姓名
	private String InputUserName;
	private Timestamp InputDate; //合同录入日期

	//DiscountRate 贴现利率
	private double DiscountRate;

	//nStatusID:合同状态
	private long StatusID;
	private String Status;
	
	//nDraftTypeID:贴现汇票种类
	private long tsDiscountTypeID;
	private String tsDiscountType;

	//买方付息
	private long isPurchaserInterest = 2;			//是否买方付息
    private long discountClientID = -1;				//出票人
    private String discountClientName = "";			//实际贴现人名称
    private double purchaserInterestRate = 0;		//实际贴现人
    private double discountPurchaserInterest = 0;	//买方付贴现利息
    
    private double mDiscountAccrual=0;              //贴现人付息
    
   // 申请编号
	private String ApplyCode;
	
	//dtLoanStart借款起始日期
	private Timestamp LoanStart;
	
	//dtDiscountDate贴现日期
	private Timestamp discountDate;

	//dtLoanStart借款结束日期
	private Timestamp LoanEnd;


	public long getContractID() {
		return ContractID;
	}

	public void setContractID(long contractID) {
		ContractID = contractID;
	}

	public String getContractCode() {
		return ContractCode;
	}

	public void setContractCode(String contractCode) {
		ContractCode = contractCode;
	}

	public long getLoanTypeID() {
		return LoanTypeID;
	}

	public void setLoanTypeID(long loanTypeID) {
		LoanTypeID = loanTypeID;
	}

	public String getLoanTypeName() {
		return LoanTypeName;
	}

	public void setLoanTypeName(String loanTypeName) {
		LoanTypeName = loanTypeName;
	}

	public long getSubTypeID() {
		return SubTypeID;
	}

	public void setSubTypeID(long subTypeID) {
		SubTypeID = subTypeID;
	}

	public String getBorrowClientName() {
		return BorrowClientName;
	}

	public void setBorrowClientName(String borrowClientName) {
		BorrowClientName = borrowClientName;
	}

	public double getExamineAmount() {
		return ExamineAmount;
	}

	public void setExamineAmount(double examineAmount) {
		ExamineAmount = examineAmount;
	}

	public double getCheckAmount() {
		return CheckAmount;
	}

	public void setCheckAmount(double checkAmount) {
		CheckAmount = checkAmount;
	}

	public double getDiscountInterest() {
		return DiscountInterest;
	}

	public void setDiscountInterest(double discountInterest) {
		DiscountInterest = discountInterest;
	}

	public String getInputUserName() {
		return InputUserName;
	}

	public void setInputUserName(String inputUserName) {
		InputUserName = inputUserName;
	}

	public Timestamp getInputDate() {
		return InputDate;
	}

	public void setInputDate(Timestamp inputDate) {
		InputDate = inputDate;
	}

	public double getDiscountRate() {
		return DiscountRate;
	}

	public void setDiscountRate(double discountRate) {
		DiscountRate = discountRate;
	}

	public long getStatusID() {
		return StatusID;
	}

	public void setStatusID(long statusID) {
		StatusID = statusID;
	}

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}

	public long getTsDiscountTypeID() {
		return tsDiscountTypeID;
	}

	public void setTsDiscountTypeID(long tsDiscountTypeID) {
		this.tsDiscountTypeID = tsDiscountTypeID;
	}

	public String getTsDiscountType() {
		return tsDiscountType;
	}

	public void setTsDiscountType(String tsDiscountType) {
		this.tsDiscountType = tsDiscountType;
	}

	public long getIsPurchaserInterest() {
		return isPurchaserInterest;
	}

	public void setIsPurchaserInterest(long isPurchaserInterest) {
		this.isPurchaserInterest = isPurchaserInterest;
	}

	public long getDiscountClientID() {
		return discountClientID;
	}

	public void setDiscountClientID(long discountClientID) {
		this.discountClientID = discountClientID;
	}

	public String getDiscountClientName() {
		return discountClientName;
	}

	public void setDiscountClientName(String discountClientName) {
		this.discountClientName = discountClientName;
	}

	public double getPurchaserInterestRate() {
		return purchaserInterestRate;
	}

	public void setPurchaserInterestRate(double purchaserInterestRate) {
		this.purchaserInterestRate = purchaserInterestRate;
	}

	public double getDiscountPurchaserInterest() {
		return discountPurchaserInterest;
	}

	public void setDiscountPurchaserInterest(double discountPurchaserInterest) {
		this.discountPurchaserInterest = discountPurchaserInterest;
	}

	public double getMDiscountAccrual() {
		return mDiscountAccrual;
	}

	public void setMDiscountAccrual(double discountAccrual) {
		mDiscountAccrual = discountAccrual;
	}

	public String getApplyCode() {
		return ApplyCode;
	}

	public void setApplyCode(String applyCode) {
		ApplyCode = applyCode;
	}

	public Timestamp getLoanStart() {
		return LoanStart;
	}

	public void setLoanStart(Timestamp loanStart) {
		LoanStart = loanStart;
	}

	public Timestamp getDiscountDate() {
		return discountDate;
	}

	public void setDiscountDate(Timestamp discountDate) {
		this.discountDate = discountDate;
	}

	public Timestamp getLoanEnd() {
		return LoanEnd;
	}

	public void setLoanEnd(Timestamp loanEnd) {
		LoanEnd = loanEnd;
	}
}
