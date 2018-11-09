/*
 * Created on 2003-12-9
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.loan.report.dataentity;

/**
 * @author hyzeng
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class ReportCorpusInfo implements java.io.Serializable
{
	private String CorpusDate; //贷款本金发放，收回日期
	private String PayAmount = ""; //发放金额
	private String RepayAmount = ""; //收回金额
	private String BalanceAmount = ""; //贷款余额

	/**
	 * @return
	 */
	public String getBalanceAmount()
	{
		return BalanceAmount;
	}

	/**
	 * @return
	 */
	public String getCorpusDate()
	{
		return CorpusDate;
	}

	/**
	 * @return
	 */
	public String getPayAmount()
	{
		return PayAmount;
	}

	/**
	 * @return
	 */
	public String getRepayAmount()
	{
		return RepayAmount;
	}

	/**
	 * @param string
	 */
	public void setBalanceAmount(String string)
	{
		BalanceAmount = string;
	}

	/**
	 * @param string
	 */
	public void setCorpusDate(String string)
	{
		CorpusDate = string;
	}

	/**
	 * @param string
	 */
	public void setPayAmount(String string)
	{
		PayAmount = string;
	}

	/**
	 * @param string
	 */
	public void setRepayAmount(String string)
	{
		RepayAmount = string;
	}

}
