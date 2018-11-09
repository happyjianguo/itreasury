/*
 * Created on 2003-9-3
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.iss.itreasury.settlement.account.dataentity;

import java.io.Serializable;



/**
 * @author ruixie
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class SubAccountAssemblerInfo implements Serializable
{

	private SubAccountCurrentInfo SACI = null; //活期子账户实体类
	private SubAccountFixedInfo SAFI = null; //定期子账户实体类
	private SubAccountLoanInfo SALI = null; //贷款子账户实体类

	/**
	 * Returns the sACI.
	 * @return SubAccountCurrentInfo
	 */
	public SubAccountCurrentInfo getSubAccountCurrenctInfo()
	{
		return SACI;
	}

	/**
	 * Returns the sAFI.
	 * @return SubAccountFixedInfo
	 */
	public SubAccountFixedInfo getSubAccountFixedInfo()
	{
		return SAFI;
	}

	/**
	 * Returns the sALI.
	 * @return SubAccountLoanInfo
	 */
	public SubAccountLoanInfo getSubAccountLoanInfo()
	{
		return SALI;
	}

	/**
	 * Sets the sACI.
	 * @param sACI The sACI to set
	 */
	public void setSubAccountCurrentInfo(SubAccountCurrentInfo sACI)
	{
		SACI = sACI;
	}

	/**
	 * Sets the sAFI.
	 * @param sAFI The sAFI to set
	 */
	public void setSubAccountFixedInfo(SubAccountFixedInfo sAFI)
	{
		SAFI = sAFI;
	}

	/**
	 * Sets the sALI.
	 * @param sALI The sALI to set
	 */
	public void setSubAccountLoanInfo(SubAccountLoanInfo sALI)
	{
		SALI = sALI;
	}

}
