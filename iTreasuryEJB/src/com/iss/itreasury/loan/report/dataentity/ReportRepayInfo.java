/*
 * Created on 2003-12-8
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
import java.sql.Timestamp;

public class ReportRepayInfo implements java.io.Serializable
{
	private Timestamp LoanDate;//����
	private String Type = "";//��Ϣ��������
	private double Amount = 0;//�ջؽ��
	/**
	 * @return
	 */
	public double getAmount()
	{
		return Amount;
	}

	/**
	 * @return
	 */
	public Timestamp getLoanDate()
	{
		return LoanDate;
	}

	/**
	 * @return
	 */
	public String getType()
	{
		return Type;
	}

	/**
	 * @param d
	 */
	public void setAmount(double d)
	{
		Amount = d;
	}

	/**
	 * @param timestamp
	 */
	public void setLoanDate(Timestamp timestamp)
	{
		LoanDate = timestamp;
	}

	/**
	 * @param string
	 */
	public void setType(String string)
	{
		Type = string;
	}

}
