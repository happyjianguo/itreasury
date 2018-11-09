package com.iss.itreasury.settlement.query.resultinfo;

public class BankAccountRecordInfo implements java.io.Serializable
{
	private long id = -1; //������ID
	private String m_strBankCode =" "; //���д���
	private String m_strBankName = ""; //��������
	private long subjectId = -1; //��ĿID
	private String m_strRootSubject = ""; //��Ŀ��
	private long m_lsubjectType = -1; //��Ŀ����
	private double m_dDebitAmount = 0.0; //�跽�ϼƽ��
	private double m_dLoanAmount = 0.0; //�����ϼƽ��
	private long m_lDebitNumber = -1; //�跽�ϼ�����
	private long m_lCreditNumber = -1; //�����ϼ�����
	private double m_dStartBalance = 0.0; //�ڳ����
	private double m_dEndBalance = 0.0; // ��ĩ���
    public long m_lPageCount = -1; //ҳ��
    
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public double getM_dDebitAmount() {
		return m_dDebitAmount;
	}
	public void setM_dDebitAmount(double debitAmount) {
		m_dDebitAmount = debitAmount;
	}
	public double getM_dEndBalance() {
		return m_dEndBalance;
	}
	public void setM_dEndBalance(double endBalance) {
		m_dEndBalance = endBalance;
	}
	public double getM_dLoanAmount() {
		return m_dLoanAmount;
	}
	public void setM_dLoanAmount(double loanAmount) {
		m_dLoanAmount = loanAmount;
	}
	public double getM_dStartBalance() {
		return m_dStartBalance;
	}
	public void setM_dStartBalance(double startBalance) {
		m_dStartBalance = startBalance;
	}
	public long getM_lCreditNumber() {
		return m_lCreditNumber;
	}
	public void setM_lCreditNumber(long creditNumber) {
		m_lCreditNumber = creditNumber;
	}
	public long getM_lDebitNumber() {
		return m_lDebitNumber;
	}
	public void setM_lDebitNumber(long debitNumber) {
		m_lDebitNumber = debitNumber;
	}
	public long getM_lPageCount() {
		return m_lPageCount;
	}
	public void setM_lPageCount(long pageCount) {
		m_lPageCount = pageCount;
	}
	public long getM_lsubjectType() {
		return m_lsubjectType;
	}
	public void setM_lsubjectType(long type) {
		m_lsubjectType = type;
	}
	public String getM_strBankCode() {
		return m_strBankCode;
	}
	public void setM_strBankCode(String bankCode) {
		m_strBankCode = bankCode;
	}
	public String getM_strBankName() {
		return m_strBankName;
	}
	public void setM_strBankName(String bankName) {
		m_strBankName = bankName;
	}
	public String getM_strRootSubject() {
		return m_strRootSubject;
	}
	public void setM_strRootSubject(String rootSubject) {
		m_strRootSubject = rootSubject;
	}
	public long getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(long subjectId) {
		this.subjectId = subjectId;
	}
    

}
