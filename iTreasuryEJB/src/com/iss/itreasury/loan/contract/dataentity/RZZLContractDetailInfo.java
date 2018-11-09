package com.iss.itreasury.loan.contract.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;

public class RZZLContractDetailInfo  implements Serializable{
	
	
	
	
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
	
	
	
	//融资租赁新增
	private long interestCountTypeID = -1;	//利息计算方式
	private double chargeAmount = 0.0;		//手续费金额
	//private double recognizanceAmount = 0.0;//保证金金额
	private double matureNominalAmount = 0.0;//到期名义货价
	private double receivedRecognizanceAmount = 0.0;	//该合同下已收保证金总额（融资租赁）
	private double returnedRecognizanceAmount = 0.0;	//该合同下已还保证金总额（融资租赁）
	
	//added by xiong fei 2010/05/26 融资租赁新增
	private double origionAmount = 0.0;//租赁物原价
	private double preAmount = 0.0;//首付款
	private double chargeAmountRate = 0.0;//手续费率
	//回购
    private long IsRepurchase = -1;
	
	
	private double recognizanceAmounDeductible = 0.0d;//已扣除保证金金额
	private double rePayAmount = 0.0d;//已还本金金额
	private long   nPayType=-1;//租金偿还方式
	private long   lInterestCountType=-1;//利息计算方式
	//融资租赁新增结束
	
	//担保
	private double assureChargeRate = 0; 		//担保费率
	
	//fInterestRate:利率
	private double InterestRate;
	
	
	//信用
	private long IsCredit = -1;

	//保证
	private long IsAssure = -1;

	//抵押
	private long IsImpawn = -1;

	//质押
	private long IsPledge = -1;
	
	//保证金
    private long IsRecognizance = -1;
	
	public double getInterestRate() {
		return InterestRate;
	}
	public void setInterestRate(double interestRate) {
		InterestRate = interestRate;
	}
	public long getIsCredit() {
		return IsCredit;
	}
	public void setIsCredit(long isCredit) {
		IsCredit = isCredit;
	}
	public long getIsAssure() {
		return IsAssure;
	}
	public void setIsAssure(long isAssure) {
		IsAssure = isAssure;
	}
	public long getIsImpawn() {
		return IsImpawn;
	}
	public void setIsImpawn(long isImpawn) {
		IsImpawn = isImpawn;
	}
	public long getIsPledge() {
		return IsPledge;
	}
	public void setIsPledge(long isPledge) {
		IsPledge = isPledge;
	}
	public long getIsRecognizance() {
		return IsRecognizance;
	}
	public void setIsRecognizance(long isRecognizance) {
		IsRecognizance = isRecognizance;
	}
	public double getAssureChargeRate() {
		return assureChargeRate;
	}
	public void setAssureChargeRate(double assureChargeRate) {
		this.assureChargeRate = assureChargeRate;
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
	public long getInterestCountTypeID() {
		return interestCountTypeID;
	}
	public void setInterestCountTypeID(long interestCountTypeID) {
		this.interestCountTypeID = interestCountTypeID;
	}
	public double getChargeAmount() {
		return chargeAmount;
	}
	public void setChargeAmount(double chargeAmount) {
		this.chargeAmount = chargeAmount;
	}
	public double getMatureNominalAmount() {
		return matureNominalAmount;
	}
	public void setMatureNominalAmount(double matureNominalAmount) {
		this.matureNominalAmount = matureNominalAmount;
	}
	public double getReceivedRecognizanceAmount() {
		return receivedRecognizanceAmount;
	}
	public void setReceivedRecognizanceAmount(double receivedRecognizanceAmount) {
		this.receivedRecognizanceAmount = receivedRecognizanceAmount;
	}
	public double getReturnedRecognizanceAmount() {
		return returnedRecognizanceAmount;
	}
	public void setReturnedRecognizanceAmount(double returnedRecognizanceAmount) {
		this.returnedRecognizanceAmount = returnedRecognizanceAmount;
	}
	public double getOrigionAmount() {
		return origionAmount;
	}
	public void setOrigionAmount(double origionAmount) {
		this.origionAmount = origionAmount;
	}
	public double getPreAmount() {
		return preAmount;
	}
	public void setPreAmount(double preAmount) {
		this.preAmount = preAmount;
	}
	public double getChargeAmountRate() {
		return chargeAmountRate;
	}
	public void setChargeAmountRate(double chargeAmountRate) {
		this.chargeAmountRate = chargeAmountRate;
	}
	public long getIsRepurchase() {
		return IsRepurchase;
	}
	public void setIsRepurchase(long isRepurchase) {
		IsRepurchase = isRepurchase;
	}
	public double getRecognizanceAmounDeductible() {
		return recognizanceAmounDeductible;
	}
	public void setRecognizanceAmounDeductible(double recognizanceAmounDeductible) {
		this.recognizanceAmounDeductible = recognizanceAmounDeductible;
	}
	public double getRePayAmount() {
		return rePayAmount;
	}
	public void setRePayAmount(double rePayAmount) {
		this.rePayAmount = rePayAmount;
	}
	public long getNPayType() {
		return nPayType;
	}
	public void setNPayType(long payType) {
		nPayType = payType;
	}
	public long getLInterestCountType() {
		return lInterestCountType;
	}
	public void setLInterestCountType(long interestCountType) {
		lInterestCountType = interestCountType;
	}

}
