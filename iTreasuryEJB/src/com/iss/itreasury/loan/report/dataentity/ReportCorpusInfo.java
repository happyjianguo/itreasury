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
	private String CorpusDate; //����𷢷ţ��ջ�����
	private String PayAmount = ""; //���Ž��
	private String RepayAmount = ""; //�ջؽ��
	private String BalanceAmount = ""; //�������

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
