/*
 * Created on 2005-3-28
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.settlement.interest.dataentity;

import java.sql.Timestamp;

/**
 * @author weilu
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class LiborInterestAdjustInfo
{
	private long accountID = -1;    //主账户ID
	private long subAccountID = -1; //子账户ID
	private Timestamp backDate = null; //倒填日期
	private long loanLiborInformID = -1; //表LOAN_LIBORINFORM的ID
	
	public LiborInterestAdjustInfo()
	{
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * @return Returns the accountID.
	 */
	public long getAccountID()
	{
		return accountID;
	}
	/**
	 * @param accountID The accountID to set.
	 */
	public void setAccountID(long accountID)
	{
		this.accountID = accountID;
	}
	/**
	 * @return Returns the backDate.
	 */
	public Timestamp getBackDate()
	{
		return backDate;
	}
	/**
	 * @param backDate The backDate to set.
	 */
	public void setBackDate(Timestamp backDate)
	{
		this.backDate = backDate;
	}
	/**
	 * @return Returns the loanLiborInformID.
	 */
	public long getLoanLiborInformID()
	{
		return loanLiborInformID;
	}
	/**
	 * @param loanLiborInformID The loanLiborInformID to set.
	 */
	public void setLoanLiborInformID(long loanLiborInformID)
	{
		this.loanLiborInformID = loanLiborInformID;
	}
	/**
	 * @return Returns the subAccountID.
	 */
	public long getSubAccountID()
	{
		return subAccountID;
	}
	/**
	 * @param subAccountID The subAccountID to set.
	 */
	public void setSubAccountID(long subAccountID)
	{
		this.subAccountID = subAccountID;
	}
}
