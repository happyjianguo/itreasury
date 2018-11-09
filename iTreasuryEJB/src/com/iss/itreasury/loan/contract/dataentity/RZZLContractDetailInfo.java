package com.iss.itreasury.loan.contract.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;

public class RZZLContractDetailInfo  implements Serializable{
	
	
	
	
	//ID:��ͬ��ʶ
	private long ContractID;
	
	//sContractCode:��ͬ���
	private String ContractCode;
	
	//nLoanTypeID:��������
	private long LoanTypeID;
	private String LoanTypeName;
	private long SubTypeID;
	
	//sName:���λ����
	private String BorrowClientName;

	//ExamineAmount ��׼���
	private double ExamineAmount;
	
	//�������ֺ˶����
	private double CheckAmount;

	//������Ϣ
	private double DiscountInterest;
	//sInputUserName ¼��������
	private String InputUserName;
	private Timestamp InputDate; //��ͬ¼������
	
	//sName:ί�е�λ����
	private String ClientName;
	
	//mLoanAmount ���
	private double LoanAmount;
	
	// ������
	private String ApplyCode;
	
	//dtLoanStart�����ʼ����
	private Timestamp LoanStart;
	
	//dtDiscountDate��������
	private Timestamp discountDate;

	//dtLoanStart����������
	private Timestamp LoanEnd;
	//nIntervalNum:��������
	private long IntervalNum;
	//��ͬ��ǰ���
	private double Balance = 0;
	//nStatusID:��ͬ״̬
	private long StatusID;
	
	
	
	//������������
	private long interestCountTypeID = -1;	//��Ϣ���㷽ʽ
	private double chargeAmount = 0.0;		//�����ѽ��
	//private double recognizanceAmount = 0.0;//��֤����
	private double matureNominalAmount = 0.0;//�����������
	private double receivedRecognizanceAmount = 0.0;	//�ú�ͬ�����ձ�֤���ܶ�������ޣ�
	private double returnedRecognizanceAmount = 0.0;	//�ú�ͬ���ѻ���֤���ܶ�������ޣ�
	
	//added by xiong fei 2010/05/26 ������������
	private double origionAmount = 0.0;//������ԭ��
	private double preAmount = 0.0;//�׸���
	private double chargeAmountRate = 0.0;//��������
	//�ع�
    private long IsRepurchase = -1;
	
	
	private double recognizanceAmounDeductible = 0.0d;//�ѿ۳���֤����
	private double rePayAmount = 0.0d;//�ѻ�������
	private long   nPayType=-1;//��𳥻���ʽ
	private long   lInterestCountType=-1;//��Ϣ���㷽ʽ
	//����������������
	
	//����
	private double assureChargeRate = 0; 		//��������
	
	//fInterestRate:����
	private double InterestRate;
	
	
	//����
	private long IsCredit = -1;

	//��֤
	private long IsAssure = -1;

	//��Ѻ
	private long IsImpawn = -1;

	//��Ѻ
	private long IsPledge = -1;
	
	//��֤��
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
