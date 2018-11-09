/*
 * Created on 2004-4-7
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.settlement.remind.resultinfo;

import java.sql.Timestamp;

/**
 * @author wlming
 * 
 * To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Generation - Code and Comments
 */
public class SecTransactionInfo implements java.io.Serializable 
{
	private long SecNoticeID = -1;//业务通知单ID
	private String SecNoticeNo = "";//业务通知单编号
	private String TransTypeDesc = "";//对应证券业务类型
	private long CapitalType = -1;//收付款方向
	private String CompanyBankName = "";//财务公司开户行名称
	private String CounterBankName = "";//对方开户行名称
	private String CounterBankAccountName = "";//对方账户名称
	private double Amount = 0.00;//收/付款金额
	private Timestamp ExecuteDate = null;//应收/付款日期
	 	

	/**
	 * @return Returns the amount.
	 */
	public double getAmount() {
		return Amount;
	}
	/**
	 * @param amount The amount to set.
	 */
	public void setAmount(double amount) {
		Amount = amount;
	}
	/**
	 * @return Returns the capitalType.
	 */
	public long getCapitalType() {
		return CapitalType;
	}
	/**
	 * @param capitalType The capitalType to set.
	 */
	public void setCapitalType(long capitalType) {
		CapitalType = capitalType;
	}
	/**
	 * @return Returns the companyBankName.
	 */
	public String getCompanyBankName() {
		return CompanyBankName;
	}
	/**
	 * @param companyBankName The companyBankName to set.
	 */
	public void setCompanyBankName(String companyBankName) {
		CompanyBankName = companyBankName;
	}
	/**
	 * @return Returns the counterBankAccountName.
	 */
	public String getCounterBankAccountName() {
		return CounterBankAccountName;
	}
	/**
	 * @param counterBankAccountName The counterBankAccountName to set.
	 */
	public void setCounterBankAccountName(String counterBankAccountName) {
		CounterBankAccountName = counterBankAccountName;
	}
	/**
	 * @return Returns the counterBankName.
	 */
	public String getCounterBankName() {
		return CounterBankName;
	}
	/**
	 * @param counterBankName The counterBankName to set.
	 */
	public void setCounterBankName(String counterBankName) {
		CounterBankName = counterBankName;
	}
	/**
	 * @return Returns the secNoticeID.
	 */
	public long getSecNoticeID() {
		return SecNoticeID;
	}
	/**
	 * @param secNoticeID The secNoticeID to set.
	 */
	public void setSecNoticeID(long secNoticeID) {
		SecNoticeID = secNoticeID;
	}
	/**
	 * @return Returns the secNoticeNo.
	 */
	public String getSecNoticeNo() {
		return SecNoticeNo;
	}
	/**
	 * @param secNoticeNo The secNoticeNo to set.
	 */
	public void setSecNoticeNo(String secNoticeNo) {
		SecNoticeNo = secNoticeNo;
	}
	/**
	 * @return Returns the transTypeDesc.
	 */
	public String getTransTypeDesc() {
		return TransTypeDesc;
	}
	/**
	 * @param transTypeDesc The transTypeDesc to set.
	 */
	public void setTransTypeDesc(String transTypeDesc) {
		TransTypeDesc = transTypeDesc;
	}
	/**
	 * @return Returns the executeDate.
	 */
	public Timestamp getExecuteDate() {
		return ExecuteDate;
	}
	/**
	 * @param executeDate The executeDate to set.
	 */
	public void setExecuteDate(Timestamp executeDate) {
		ExecuteDate = executeDate;
	}
}
