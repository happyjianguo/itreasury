/*
 * Created on 2003-12-24
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.iss.itreasury.settlement.query.resultinfo;

/**
 * @author ruixie
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class QueryDepositLoanResultInfo
{
	public QueryDepositLoanResultInfo()
	{
		super();
		// TODO Auto-generated constructor stub
	}
	
	private long AccountTypeID = -1;
	private String AccountTypeName = "";
	private double SumBalance = 0.0;
	
	private long DepositLoanSearchID = -1; //设置表中的ID
	
	private long FixedTerm = -1;//定期期限
	private String FixedTermName = "";//期限名称
	
    private long LoanTypeID = -1; // 贷款类型
    private long ConsignClientID = -1; // 委托方ID
    private long LoanTerm = -1; // 贷款期限
    private long LoanYear = -1; // 贷款年期
	
	/**
	 * @return
	 */
	public long getAccountTypeID()
	{
		return AccountTypeID;
	}

	/**
	 * @return
	 */
	public String getAccountTypeName()
	{
		return AccountTypeName;
	}

	/**
	 * @return
	 */
	public double getSumBalance()
	{
		return SumBalance;
	}

	/**
	 * @param l
	 */
	public void setAccountTypeID(long l)
	{
		AccountTypeID = l;
	}

	/**
	 * @param string
	 */
	public void setAccountTypeName(String string)
	{
		AccountTypeName = string;
	}

	/**
	 * @param d
	 */
	public void setSumBalance(double d)
	{
		SumBalance = d;
	}

	/**
	 * @return
	 */
	public long getConsignClientID()
	{
		return ConsignClientID;
	}

	/**
	 * @param consignClientID
	 */
	public void setConsignClientID(long consignClientID)
	{
		ConsignClientID = consignClientID;
	}

	/**
	 * @return
	 */
	public long getLoanTerm()
	{
		return LoanTerm;
	}

	/**
	 * @param loanTerm
	 */
	public void setLoanTerm(long loanTerm)
	{
		LoanTerm = loanTerm;
	}

	/**
	 * @return
	 */
	public long getLoanTypeID()
	{
		return LoanTypeID;
	}

	/**
	 * @param loanTypeID
	 */
	public void setLoanTypeID(long loanTypeID)
	{
		LoanTypeID = loanTypeID;
	}

	/**
	 * @return
	 */
	public long getLoanYear()
	{
		return LoanYear;
	}

	/**
	 * @param loanYear
	 */
	public void setLoanYear(long loanYear)
	{
		LoanYear = loanYear;
	}

	/**
	 * @return
	 */
	public long getFixedTerm()
	{
		return FixedTerm;
	}

	/**
	 * @param l
	 */
	public void setFixedTerm(long l)
	{
		FixedTerm = l;
	}

	/**
	 * @return
	 */
	public String getFixedTermName()
	{
		return FixedTermName;
	}

	/**
	 * @param string
	 */
	public void setFixedTermName(String string)
	{
		FixedTermName = string;
	}

	/**
	 * @return
	 */
	public long getDepositLoanSearchID()
	{
		return DepositLoanSearchID;
	}

	/**
	 * @param l
	 */
	public void setDepositLoanSearchID(long l)
	{
		DepositLoanSearchID = l;
	}

}
