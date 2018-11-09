/*
 * Created on 2003-10-30
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.settlement.query.resultinfo;

import java.io.Serializable;

/**
 * @author yiwang
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class QueryAccountSumInfo implements Serializable
{

	/**
	 * 
	 */
	public QueryAccountSumInfo()
	{
		super();
		// TODO Auto-generated constructor stub
	}
    public double DepositBalanceSum = 0.0;
    public double LoanBalanceSum = 0.0;
    public double BalanceSum = 0.0;
    public double InterestSum = 0.0;
    public double AmountSum = 0.0;
    public double NegotiateBalanceSum = 0.0;
    public double CurrentBalanceSum = 0.0;
    public double FixBalanceSum = 0.0;
    public double NoticeBalanceSum = 0.0; 
	public double NegotiateInterestSum = 0.0;//协定利息合计
	
	public double MinterestbalanceSum=0.0;//计息余额合计
	
    //
    public String RemarkIndex = ""; 
	/**
	 * @return Returns the depositBalanceSum.
	 */
	public double getDepositBalanceSum()
	{
		return DepositBalanceSum;
	}
	/**
	 * @param depositBalanceSum The depositBalanceSum to set.
	 */
	public void setDepositBalanceSum(double depositBalanceSum)
	{
		DepositBalanceSum = depositBalanceSum;
	}
	/**
	 * @return Returns the loanBalanceSum.
	 */
	public double getLoanBalanceSum()
	{
		return LoanBalanceSum;
	}
	/**
	 * @param loanBalanceSum The loanBalanceSum to set.
	 */
	public void setLoanBalanceSum(double loanBalanceSum)
	{
		LoanBalanceSum = loanBalanceSum;
	}
	/**
	 * @return
	 */
	public double getBalanceSum()
	{
		return BalanceSum;
	}

	/**
	 * @param balnaceSum
	 */
	public void setBalanceSum(double balnaceSum)
	{
		BalanceSum = balnaceSum;
	}

	/**
	 * @return
	 */
	public double getInterestSum()
	{
		return InterestSum;
	}

	/**
	 * @param interestSum
	 */
	public void setInterestSum(double interestSum)
	{
		InterestSum = interestSum;
	}

	/**
	 * @return
	 */
	public double getAmountSum()
	{
		return AmountSum;
	}

	/**
	 * @param amountSum
	 */
	public void setAmountSum(double amountSum)
	{
		AmountSum = amountSum;
	}

	/**
	 * @return
	 */
	public String getRemarkIndex()
	{
		return RemarkIndex;
	}

	/**
	 * @param remarkIndex
	 */
	public void setRemarkIndex(String remarkIndex)
	{
		RemarkIndex = remarkIndex;
	}

	/**
	 * @return
	 */
	public double getCurrentBalanceSum()
	{
		return CurrentBalanceSum;
	}

	/**
	 * @param currentBalanceSum
	 */
	public void setCurrentBalanceSum(double currentBalanceSum)
	{
		CurrentBalanceSum = currentBalanceSum;
	}

	/**
	 * @return
	 */
	public double getFixBalanceSum()
	{
		return FixBalanceSum;
	}

	/**
	 * @param fixBalanceSum
	 */
	public void setFixBalanceSum(double fixBalanceSum)
	{
		FixBalanceSum = fixBalanceSum;
	}

	/**
	 * @return
	 */
	public double getNegotiateBalanceSum()
	{
		return NegotiateBalanceSum;
	}

	/**
	 * @param negotiateBalanceSum
	 */
	public void setNegotiateBalanceSum(double negotiateBalanceSum)
	{
		NegotiateBalanceSum = negotiateBalanceSum;
	}

	/**
	 * @return
	 */
	public double getNoticeBalanceSum()
	{
		return NoticeBalanceSum;
	}

	/**
	 * @param noticeBalanceSum
	 */
	public void setNoticeBalanceSum(double noticeBalanceSum)
	{
		NoticeBalanceSum = noticeBalanceSum;
	}

	/**
	 * @return
	 */
	public double getNegotiateInterestSum()
	{
		return NegotiateInterestSum;
	}

	/**
	 * @param d
	 */
	public void setNegotiateInterestSum(double d)
	{
		NegotiateInterestSum = d;
	}
	public double getMinterestbalanceSum() {
		return MinterestbalanceSum;
	}
	public void setMinterestbalanceSum(double minterestbalanceSum) {
		MinterestbalanceSum = minterestbalanceSum;
	}

}
