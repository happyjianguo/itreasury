/*
 * ContractInfo.java
 *
 * Created on 2004年9月20日, 下午17:39
 */

package com.iss.itreasury.settlement.query.resultinfo;

import java.sql.Timestamp;

/**
 *
 * @author  weilu
 * @version
 */
public class QueryContractInfo implements java.io.Serializable
{

	/**
	 * ContractInfo 构造子注解。
	 */
	public QueryContractInfo()
	{
		super();
	}

	//合同信息
	//ID:合同标识
	private long ContractID;

	//ID:合同标识
	private long LoanID;

	//nCurrencyID:币种标识
	private long CurrencyID;

	//nOfficeID:办事处标识
	private long OfficeID;

	//sContractCode:合同编号
	private String ContractCode;

	//nLoanTypeID:贷款种类
	private long LoanTypeID;
	private String LoanTypeName;

	//nBorrowClientID:贷款单位ID
	private long BorrowClientID;

	//nClientID 委托单位ID
	private long ClientID;

	//mLoanAmount 金额
	private double LoanAmount;

	//ExamineAmount 批准金额
	private double ExamineAmount;
	
    //汇总贴现核定金额
    private double CheckAmount = 0;
	
	//合同当前余额
	private double Balance = 0;
	
	//fInterestRate:利率
	private double InterestRate;

	//fInterestRate:银行利率ID
	private long BankInterestID;
	
	//nIntervalNum:贷款期限
	private long IntervalNum;

	//nInputUserID:录入人标识 亦是合同管理人
	private long lInputUserID;

	private Timestamp InputDate; //合同录入日期


	//nStatusID:合同状态
	private long StatusID;

	//分页显示总页数
	private long PageCount;


	//dtLoanStart借款起始日期
	private Timestamp LoanStart;

	//dtLoanStart借款结束日期
	private Timestamp LoanEnd;

	//审核人ID
	private long CheckUserID = -1;
	
	private double 		BillAmount;        //凭证金额
	private double 		BillInterest;      //凭证利息
	private double DiscountRate;            //利率
	/**
	 * @return Returns the borrowClientID.
	 */
	public long getBorrowClientID()
	{
		return BorrowClientID;
	}
	/**
	 * @param borrowClientID The borrowClientID to set.
	 */
	public void setBorrowClientID(long borrowClientID)
	{
		BorrowClientID = borrowClientID;
	}
	/**
	 * @return Returns the checkUserID.
	 */
	public long getCheckUserID()
	{
		return CheckUserID;
	}
	/**
	 * @param checkUserID The checkUserID to set.
	 */
	public void setCheckUserID(long checkUserID)
	{
		CheckUserID = checkUserID;
	}
	/**
	 * @return Returns the clientID.
	 */
	public long getClientID()
	{
		return ClientID;
	}
	/**
	 * @param clientID The clientID to set.
	 */
	public void setClientID(long clientID)
	{
		ClientID = clientID;
	}
	/**
	 * @return Returns the contractCode.
	 */
	public String getContractCode()
	{
		return ContractCode;
	}
	/**
	 * @param contractCode The contractCode to set.
	 */
	public void setContractCode(String contractCode)
	{
		ContractCode = contractCode;
	}
	/**
	 * @return Returns the contractID.
	 */
	public long getContractID()
	{
		return ContractID;
	}
	/**
	 * @param contractID The contractID to set.
	 */
	public void setContractID(long contractID)
	{
		ContractID = contractID;
	}
	/**
	 * @return Returns the currencyID.
	 */
	public long getCurrencyID()
	{
		return CurrencyID;
	}
	/**
	 * @param currencyID The currencyID to set.
	 */
	public void setCurrencyID(long currencyID)
	{
		CurrencyID = currencyID;
	}
	/**
	 * @return Returns the inputDate.
	 */
	public Timestamp getInputDate()
	{
		return InputDate;
	}
	/**
	 * @param inputDate The inputDate to set.
	 */
	public void setInputDate(Timestamp inputDate)
	{
		InputDate = inputDate;
	}
	/**
	 * @return Returns the interestRate.
	 */
	public double getInterestRate()
	{
		return InterestRate;
	}
	/**
	 * @param interestRate The interestRate to set.
	 */
	public void setInterestRate(double interestRate)
	{
		InterestRate = interestRate;
	}
	/**
	 * @return Returns the intervalNum.
	 */
	public long getIntervalNum()
	{
		return IntervalNum;
	}
	/**
	 * @param intervalNum The intervalNum to set.
	 */
	public void setIntervalNum(long intervalNum)
	{
		IntervalNum = intervalNum;
	}
	/**
	 * @return Returns the lInputUserID.
	 */
	public long getInputUserID()
	{
		return lInputUserID;
	}
	/**
	 * @param inputUserID The lInputUserID to set.
	 */
	public void setInputUserID(long inputUserID)
	{
		lInputUserID = inputUserID;
	}
	/**
	 * @return Returns the loanAmount.
	 */
	public double getLoanAmount()
	{
		return LoanAmount;
	}
	/**
	 * @param loanAmount The loanAmount to set.
	 */
	public void setLoanAmount(double loanAmount)
	{
		LoanAmount = loanAmount;
	}
	/**
	 * @return Returns the loanEnd.
	 */
	public Timestamp getLoanEnd()
	{
		return LoanEnd;
	}
	/**
	 * @param loanEnd The loanEnd to set.
	 */
	public void setLoanEnd(Timestamp loanEnd)
	{
		LoanEnd = loanEnd;
	}
	/**
	 * @return Returns the loanID.
	 */
	public long getLoanID()
	{
		return LoanID;
	}
	/**
	 * @param loanID The loanID to set.
	 */
	public void setLoanID(long loanID)
	{
		LoanID = loanID;
	}
	/**
	 * @return Returns the loanStart.
	 */
	public Timestamp getLoanStart()
	{
		return LoanStart;
	}
	/**
	 * @param loanStart The loanStart to set.
	 */
	public void setLoanStart(Timestamp loanStart)
	{
		LoanStart = loanStart;
	}
	/**
	 * @return Returns the loanTypeID.
	 */
	public long getLoanTypeID()
	{
		return LoanTypeID;
	}
	/**
	 * @param loanTypeID The loanTypeID to set.
	 */
	public void setLoanTypeID(long loanTypeID)
	{
		LoanTypeID = loanTypeID;
	}
	/**
	 * @return Returns the loanTypeName.
	 */
	public String getLoanTypeName()
	{
		return LoanTypeName;
	}
	/**
	 * @param loanTypeName The loanTypeName to set.
	 */
	public void setLoanTypeName(String loanTypeName)
	{
		LoanTypeName = loanTypeName;
	}
	/**
	 * @return Returns the officeID.
	 */
	public long getOfficeID()
	{
		return OfficeID;
	}
	/**
	 * @param officeID The officeID to set.
	 */
	public void setOfficeID(long officeID)
	{
		OfficeID = officeID;
	}
	/**
	 * @return Returns the pageCount.
	 */
	public long getPageCount()
	{
		return PageCount;
	}
	/**
	 * @param pageCount The pageCount to set.
	 */
	public void setPageCount(long pageCount)
	{
		PageCount = pageCount;
	}
	/**
	 * @return Returns the statusID.
	 */
	public long getStatusID()
	{
		return StatusID;
	}
	/**
	 * @param statusID The statusID to set.
	 */
	public void setStatusID(long statusID)
	{
		StatusID = statusID;
	}
	/**
	 * @return Returns the balance.
	 */
	public double getBalance()
	{
		return Balance;
	}

	/**
	 * @return Returns the bankInterestID.
	 */
	public long getBankInterestID()
	{
		return BankInterestID;
	}
	/**
	 * @param bankInterestID The bankInterestID to set.
	 */
	public void setBankInterestID(long bankInterestID)
	{
		BankInterestID = bankInterestID;
	}
	/**
	 * @return Returns the examineAmount.
	 */
	public double getExamineAmount()
	{
		return ExamineAmount;
	}
	/**
	 * @param examineAmount The examineAmount to set.
	 */
	public void setExamineAmount(double examineAmount)
	{
		ExamineAmount = examineAmount;
	}
	/**
	 * @return Returns the lInputUserID.
	 */
	public long getLInputUserID()
	{
		return lInputUserID;
	}
	/**
	 * @param inputUserID The lInputUserID to set.
	 */
	public void setLInputUserID(long inputUserID)
	{
		lInputUserID = inputUserID;
	}
	/**
	 * @return Returns the billAmount.
	 */
	public double getBillAmount()
	{
		return BillAmount;
	}
	/**
	 * @param billAmount The billAmount to set.
	 */
	public void setBillAmount(double billAmount)
	{
		BillAmount = billAmount;
	}
	/**
	 * @return Returns the billInterest.
	 */
	public double getBillInterest()
	{
		return BillInterest;
	}
	/**
	 * @param billInterest The billInterest to set.
	 */
	public void setBillInterest(double billInterest)
	{
		BillInterest = billInterest;
	}
	/**
	 * @return Returns the discountRate.
	 */
	public double getDiscountRate()
	{
		return DiscountRate;
	}
	/**
	 * @param discountRate The discountRate to set.
	 */
	public void setDiscountRate(double discountRate)
	{
		DiscountRate = discountRate;
	}
}
