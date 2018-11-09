/*
 * Created on 2004-4-14
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.iss.itreasury.settlement.query.paraminfo;

import java.io.Serializable;


/**
 * @author ruixie
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class QueryCapitalSuperviseWhereInfo implements Serializable 
{

	public QueryCapitalSuperviseWhereInfo()
	{
		super();
		// TODO Auto-generated constructor stub
	}
	
	//用于 资金集中管理资金处理查询 的参数
	long OfficeID = -1;
	long CurrencyID = -1;
	String StartCurrentAccountNo = ""; // 活期存款账户编号
	String EndCurrentAccountNo = ""; // 活期存款至账户编号
	String StartDebitAccountNo = "";// 负息账户编号
	String EndDebitAccountNo = "";// 负息账户编号
	long TransactionTypeID = -1;//交易类型

	/**
	 * @return Returns the currencyID.
	 */
	public long getCurrencyID() {
		return CurrencyID;
	}
	/**
	 * @param currencyID The currencyID to set.
	 */
	public void setCurrencyID(long currencyID) {
		CurrencyID = currencyID;
	}
	/**
	 * @return Returns the endCurrentAccountNo.
	 */
	public String getEndCurrentAccountNo() {
		return EndCurrentAccountNo;
	}
	/**
	 * @param endCurrentAccountNo The endCurrentAccountNo to set.
	 */
	public void setEndCurrentAccountNo(String endCurrentAccountNo) {
		EndCurrentAccountNo = endCurrentAccountNo;
	}
	/**
	 * @return Returns the endDebitAccountNo.
	 */
	public String getEndDebitAccountNo() {
		return EndDebitAccountNo;
	}
	/**
	 * @param endDebitAccountNo The endDebitAccountNo to set.
	 */
	public void setEndDebitAccountNo(String endDebitAccountNo) {
		EndDebitAccountNo = endDebitAccountNo;
	}
	/**
	 * @return Returns the officeID.
	 */
	public long getOfficeID() {
		return OfficeID;
	}
	/**
	 * @param officeID The officeID to set.
	 */
	public void setOfficeID(long officeID) {
		OfficeID = officeID;
	}
	/**
	 * @return Returns the startCurrentAccountNo.
	 */
	public String getStartCurrentAccountNo() {
		return StartCurrentAccountNo;
	}
	/**
	 * @param startCurrentAccountNo The startCurrentAccountNo to set.
	 */
	public void setStartCurrentAccountNo(String startCurrentAccountNo) {
		StartCurrentAccountNo = startCurrentAccountNo;
	}
	/**
	 * @return Returns the startDebitAccountNo.
	 */
	public String getStartDebitAccountNo() {
		return StartDebitAccountNo;
	}
	/**
	 * @param startDebitAccountNo The startDebitAccountNo to set.
	 */
	public void setStartDebitAccountNo(String startDebitAccountNo) {
		StartDebitAccountNo = startDebitAccountNo;
	}
	/**
	 * @return Returns the transactionTypeID.
	 */
	public long getTransactionTypeID() {
		return TransactionTypeID;
	}
	/**
	 * @param transactionTypeID The transactionTypeID to set.
	 */
	public void setTransactionTypeID(long transactionTypeID) {
		TransactionTypeID = transactionTypeID;
	}
}
