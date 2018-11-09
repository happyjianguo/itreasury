package com.iss.itreasury.settlement.query.resultinfo;
/**
 * 此处插入类型说明。
 * 创建日期：(2002-1-15 17:20:10)
 * @author：
 */
public class AccountRecordInfo implements java.io.Serializable
{
	public long id = -1; //科目ID
	public String m_strAccount = ""; //科目号
	public String m_strName = "";//科目名称
	public double m_dStartBalance = 0; //期初余额
	public double m_dEndBalance = 0; //期末余额
	public double m_dStartDebitBalance = 0; //期初借方余额
	public double m_dStartCreditBalance = 0; //期初贷方余额
	public double m_dEndDebitBalance = 0; //期末借方余额
	public double m_dEndCreditBalance = 0; //期末贷方余额
	public double m_dDebitAmount = 0; //借方合计
	public double m_dLoanAmount = 0; //贷方合计
	public long m_lNumber = 0; //凭证数量 
	public long m_lDebitNumber = 0; //借方凭证
	public long m_lCreditNumber = 0; //贷方凭证
	public long m_lPageCount = 0; //页号
	public double m_dDebitsSumAmount = 0; //满足条件全部借方合计,(上海电气记录表内借方合计)
	public double m_dCreditSumAmount = 0; //满足条件全部贷方合计,(上海电气记录表内借方合计)
	
	//2005-1-26 modified by zntan （上海电气）
	public double m_dOffDebitSumAmount = 0;//满足条件的表外业务借方合计
	public double m_dOffCreditSumAmount = 0;//满足条件的表外业务贷方合计
	public double m_lSubjectType = 0;//科目属性
	//
	public String m_strSubject = "";
	public long m_lCurrencyID = -1;
	public long m_lOfficeID = -1;
	public long m_lAccountID = -1;
	public boolean m_bIsRoot = false;
	private long nParentSubjectId = -1; //子父关系
	
	public long getNParentSubjectId() {
		return nParentSubjectId;
	}
	public void setNParentSubjectId(long parentSubjectId) {
		nParentSubjectId = parentSubjectId;
	}
	public boolean isM_bIsRoot() {
		return m_bIsRoot;
	}
	public void setM_bIsRoot(boolean isRoot) {
		m_bIsRoot = isRoot;
	}
	public double getM_dCreditSumAmount() {
		return m_dCreditSumAmount;
	}
	public void setM_dCreditSumAmount(double creditSumAmount) {
		m_dCreditSumAmount = creditSumAmount;
	}
	public double getM_dDebitAmount() {
		return m_dDebitAmount;
	}
	public void setM_dDebitAmount(double debitAmount) {
		m_dDebitAmount = debitAmount;
	}
	public double getM_dDebitsSumAmount() {
		return m_dDebitsSumAmount;
	}
	public void setM_dDebitsSumAmount(double debitsSumAmount) {
		m_dDebitsSumAmount = debitsSumAmount;
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
	public double getM_dOffCreditSumAmount() {
		return m_dOffCreditSumAmount;
	}
	public void setM_dOffCreditSumAmount(double offCreditSumAmount) {
		m_dOffCreditSumAmount = offCreditSumAmount;
	}
	public double getM_dOffDebitSumAmount() {
		return m_dOffDebitSumAmount;
	}
	public void setM_dOffDebitSumAmount(double offDebitSumAmount) {
		m_dOffDebitSumAmount = offDebitSumAmount;
	}
	public double getM_dStartBalance() {
		return m_dStartBalance;
	}
	public void setM_dStartBalance(double startBalance) {
		m_dStartBalance = startBalance;
	}
	public long getM_lAccountID() {
		return m_lAccountID;
	}
	public void setM_lAccountID(long accountID) {
		m_lAccountID = accountID;
	}
	public long getM_lCreditNumber() {
		return m_lCreditNumber;
	}
	public void setM_lCreditNumber(long creditNumber) {
		m_lCreditNumber = creditNumber;
	}
	public long getM_lCurrencyID() {
		return m_lCurrencyID;
	}
	public void setM_lCurrencyID(long currencyID) {
		m_lCurrencyID = currencyID;
	}
	public long getM_lDebitNumber() {
		return m_lDebitNumber;
	}
	public void setM_lDebitNumber(long debitNumber) {
		m_lDebitNumber = debitNumber;
	}
	public long getM_lNumber() {
		return m_lNumber;
	}
	public void setM_lNumber(long number) {
		m_lNumber = number;
	}
	public long getM_lOfficeID() {
		return m_lOfficeID;
	}
	public void setM_lOfficeID(long officeID) {
		m_lOfficeID = officeID;
	}
	public long getM_lPageCount() {
		return m_lPageCount;
	}
	public void setM_lPageCount(long pageCount) {
		m_lPageCount = pageCount;
	}
	public double getM_lSubjectType() {
		return m_lSubjectType;
	}
	public void setM_lSubjectType(double subjectType) {
		m_lSubjectType = subjectType;
	}
	public String getM_strAccount() {
		return m_strAccount;
	}
	public void setM_strAccount(String account) {
		m_strAccount = account;
	}
	public String getM_strName() {
		return m_strName;
	}
	public void setM_strName(String name) {
		m_strName = name;
	}
	public String getM_strSubject() {
		return m_strSubject;
	}
	public void setM_strSubject(String subject) {
		m_strSubject = subject;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public double getM_dStartDebitBalance() {
		return m_dStartDebitBalance;
	}
	public void setM_dStartDebitBalance(double startDebitBalance) {
		m_dStartDebitBalance = startDebitBalance;
	}
	public double getM_dStartCreditBalance() {
		return m_dStartCreditBalance;
	}
	public void setM_dStartCreditBalance(double startCreditBalance) {
		m_dStartCreditBalance = startCreditBalance;
	}
	public double getM_dEndDebitBalance() {
		return m_dEndDebitBalance;
	}
	public void setM_dEndDebitBalance(double endDebitBalance) {
		m_dEndDebitBalance = endDebitBalance;
	}
	public double getM_dEndCreditBalance() {
		return m_dEndCreditBalance;
	}
	public void setM_dEndCreditBalance(double endCreditBalance) {
		m_dEndCreditBalance = endCreditBalance;
	}
	
}
