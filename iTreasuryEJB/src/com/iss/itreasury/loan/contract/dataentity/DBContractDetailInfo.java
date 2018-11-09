package com.iss.itreasury.loan.contract.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;

public class DBContractDetailInfo implements Serializable {
	
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
	
	//sName:委托单位名称
	private String ClientName;
	
	//mLoanAmount 金额
	private double LoanAmount;
	
	// 申请编号
	private String ApplyCode;
	
	//dtLoanStart借款起始日期
	private Timestamp LoanStart;
	
	//dtDiscountDate贴现日期
	private Timestamp discountDate;

	//dtLoanStart借款结束日期
	private Timestamp LoanEnd;
	//nIntervalNum:贷款期限
	private long IntervalNum;
	//合同当前余额
	private double Balance = 0;
	//nStatusID:合同状态
	private long StatusID;
	
	
	
	 //担保
	private double assureChargeRate = 0; 		//担保费率
	private long assureChargeTypeID = -1;		//担保费收取方式
	private String beneficiary = "";	 		//受益人
	private long assureTypeID1 = -1;	 		//担保类型1
	private long assureTypeID2 = -1;	 		//担保类型2
	private double recognizanceAmount = 0.0;	//保证金金额
	//担保--收款通知单、保后处理显示用
	private double sumAssureAmount = 0.0;		//该合同的被担保人所有的担保合同的总金额
	private double sumAssureBanlance = 0.0;		//该合同的被担保人所有的担保合同的总余额
	private double currentRecognizanceAmount = 0.0;	//该合同下累计已收保证金金额（已收-已还）
	private double AssureBalance = 0.0;//担保余额
    //private double receiveAssureChargeAmount = 0.0; //该合同下当前已收手续费
	//private double receiveRecognizanceAmount = 0.0; //该合同下当前已收保证金
	private String lateRateString = "";         //LIBOR利率，字符串格式
	
	//合同结束日期 2006-3-15
	private Timestamp lastExecDate = null;

	public double getAssureChargeRate() {
		return assureChargeRate;
	}

	public void setAssureChargeRate(double assureChargeRate) {
		this.assureChargeRate = assureChargeRate;
	}

	public long getAssureChargeTypeID() {
		return assureChargeTypeID;
	}

	public void setAssureChargeTypeID(long assureChargeTypeID) {
		this.assureChargeTypeID = assureChargeTypeID;
	}

	public String getBeneficiary() {
		return beneficiary;
	}

	public void setBeneficiary(String beneficiary) {
		this.beneficiary = beneficiary;
	}

	public long getAssureTypeID1() {
		return assureTypeID1;
	}

	public void setAssureTypeID1(long assureTypeID1) {
		this.assureTypeID1 = assureTypeID1;
	}

	public long getAssureTypeID2() {
		return assureTypeID2;
	}

	public void setAssureTypeID2(long assureTypeID2) {
		this.assureTypeID2 = assureTypeID2;
	}

	public double getRecognizanceAmount() {
		return recognizanceAmount;
	}

	public void setRecognizanceAmount(double recognizanceAmount) {
		this.recognizanceAmount = recognizanceAmount;
	}

	public double getSumAssureAmount() {
		return sumAssureAmount;
	}

	public void setSumAssureAmount(double sumAssureAmount) {
		this.sumAssureAmount = sumAssureAmount;
	}

	public double getSumAssureBanlance() {
		return sumAssureBanlance;
	}

	public void setSumAssureBanlance(double sumAssureBanlance) {
		this.sumAssureBanlance = sumAssureBanlance;
	}

	public double getCurrentRecognizanceAmount() {
		return currentRecognizanceAmount;
	}

	public void setCurrentRecognizanceAmount(double currentRecognizanceAmount) {
		this.currentRecognizanceAmount = currentRecognizanceAmount;
	}

	public double getAssureBalance() {
		return AssureBalance;
	}

	public void setAssureBalance(double assureBalance) {
		AssureBalance = assureBalance;
	}

	public String getLateRateString() {
		return lateRateString;
	}

	public void setLateRateString(String lateRateString) {
		this.lateRateString = lateRateString;
	}

	public Timestamp getLastExecDate() {
		return lastExecDate;
	}

	public void setLastExecDate(Timestamp lastExecDate) {
		this.lastExecDate = lastExecDate;
	}

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

	public String getClientName() {
		return ClientName;
	}

	public void setClientName(String clientName) {
		ClientName = clientName;
	}

	public double getLoanAmount() {
		return LoanAmount;
	}

	public void setLoanAmount(double loanAmount) {
		LoanAmount = loanAmount;
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

	public long getIntervalNum() {
		return IntervalNum;
	}

	public void setIntervalNum(long intervalNum) {
		IntervalNum = intervalNum;
	}

	public double getBalance() {
		return Balance;
	}

	public void setBalance(double balance) {
		Balance = balance;
	}

	public long getStatusID() {
		return StatusID;
	}

	public void setStatusID(long statusID) {
		StatusID = statusID;
	}
	

}
