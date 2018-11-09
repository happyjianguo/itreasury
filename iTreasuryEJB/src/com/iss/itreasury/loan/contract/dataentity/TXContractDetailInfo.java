package com.iss.itreasury.loan.contract.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;

public class TXContractDetailInfo implements Serializable{
	
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

	//DiscountRate ��������
	private double DiscountRate;

	//nStatusID:��ͬ״̬
	private long StatusID;
	private String Status;
	
	//nDraftTypeID:���ֻ�Ʊ����
	private long tsDiscountTypeID;
	private String tsDiscountType;

	//�򷽸�Ϣ
	private long isPurchaserInterest = 2;			//�Ƿ��򷽸�Ϣ
    private long discountClientID = -1;				//��Ʊ��
    private String discountClientName = "";			//ʵ������������
    private double purchaserInterestRate = 0;		//ʵ��������
    private double discountPurchaserInterest = 0;	//�򷽸�������Ϣ
    
    private double mDiscountAccrual=0;              //�����˸�Ϣ
    
   // ������
	private String ApplyCode;
	
	//dtLoanStart�����ʼ����
	private Timestamp LoanStart;
	
	//dtDiscountDate��������
	private Timestamp discountDate;

	//dtLoanStart����������
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
