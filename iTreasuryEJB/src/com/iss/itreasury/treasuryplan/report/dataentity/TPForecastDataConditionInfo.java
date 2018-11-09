/*
 * Created on 2004-7-13
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.treasuryplan.report.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author wlming
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class TPForecastDataConditionInfo implements Serializable
{
	private long OfficeID = -1;//办事处
	private long CurrencyID = -1;//币种
	private Timestamp TransactionDateStart = null;//被预测的交易数据日期(由)
	private Timestamp TransactionDateEnd = null;//被预测的交易数据日期(至)
	private long AuthorizedDepartmentID = -1;//所属部门
	private long AuthorizedUserID = -1;//所属用户
	private long gatherType = -1; //汇总方式
	public long getGatherType() {
		return gatherType;
	}
	public void setGatherType(long gatherType) {
		this.gatherType = gatherType;
	}
	/**
	 * @return Returns the authorizedDepartmentID.
	 */
	public long getAuthorizedDepartmentID()
	{
		return AuthorizedDepartmentID;
	}
	/**
	 * @param authorizedDepartmentID The authorizedDepartmentID to set.
	 */
	public void setAuthorizedDepartmentID(long authorizedDepartmentID)
	{
		AuthorizedDepartmentID = authorizedDepartmentID;
	}
	/**
	 * @return Returns the authorizedUserID.
	 */
	public long getAuthorizedUserID()
	{
		return AuthorizedUserID;
	}
	/**
	 * @param authorizedUserID The authorizedUserID to set.
	 */
	public void setAuthorizedUserID(long authorizedUserID)
	{
		AuthorizedUserID = authorizedUserID;
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
	 * @return Returns the transactionDateEnd.
	 */
	public Timestamp getTransactionDateEnd()
	{
		return TransactionDateEnd;
	}
	/**
	 * @param transactionDateEnd The transactionDateEnd to set.
	 */
	public void setTransactionDateEnd(Timestamp transactionDateEnd)
	{
		TransactionDateEnd = transactionDateEnd;
	}
	/**
	 * @return Returns the transactionDateStart.
	 */
	public Timestamp getTransactionDateStart()
	{
		return TransactionDateStart;
	}
	/**
	 * @param transactionDateStart The transactionDateStart to set.
	 */
	public void setTransactionDateStart(Timestamp transactionDateStart)
	{
		TransactionDateStart = transactionDateStart;
	}
}
