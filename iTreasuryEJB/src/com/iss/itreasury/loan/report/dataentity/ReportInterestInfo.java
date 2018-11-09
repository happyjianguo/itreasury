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
public class ReportInterestInfo implements java.io.Serializable
{
	private String ReceiveInterest = "";//应收利息
	private String RealInterest = "";//实收利息
	private String LackOfInterest = "";//欠息

	/**
	 * @return
	 */
	public String getLackOfInterest()
	{
		return LackOfInterest;
	}

	/**
	 * @return
	 */
	public String getRealInterest()
	{
		return RealInterest;
	}

	/**
	 * @return
	 */
	public String getReceiveInterest()
	{
		return ReceiveInterest;
	}

	/**
	 * @param string
	 */
	public void setLackOfInterest(String string)
	{
		LackOfInterest = string;
	}

	/**
	 * @param string
	 */
	public void setRealInterest(String string)
	{
		RealInterest = string;
	}

	/**
	 * @param string
	 */
	public void setReceiveInterest(String string)
	{
		ReceiveInterest = string;
	}

}
