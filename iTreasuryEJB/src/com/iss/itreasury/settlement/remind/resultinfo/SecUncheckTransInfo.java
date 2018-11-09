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
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class SecUncheckTransInfo  implements java.io.Serializable
{
	private Timestamp ExecuteDate = null;//执行日
	private long ID = -1;//交易ID
	private String TransNo = "";//交易号
	private String SecNoticeNo = "";//业务通知单
	private long TransTypeID = -1;//业务类型
	private String CompanyBankName = "";//财务公司开户行名称
	private double Amount = 0.0;//收/付款金额
	private long StatusID = -1;//交易状态
	private String Abstract = "";//摘要
	private long InputUserID = -1;//录入人
	/**
	 * @return Returns the abstract.
	 */
	public String getAbstract() {
		return Abstract;
	}
	/**
	 * @param abstract1 The abstract to set.
	 */
	public void setAbstract(String abstract1) {
		Abstract = abstract1;
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
	/**
	 * @return Returns the iD.
	 */
	public long getID() {
		return ID;
	}
	/**
	 * @param id The iD to set.
	 */
	public void setID(long id) {
		ID = id;
	}
	/**
	 * @return Returns the inputUserID.
	 */
	public long getInputUserID() {
		return InputUserID;
	}
	/**
	 * @param inputUserID The inputUserID to set.
	 */
	public void setInputUserID(long inputUserID) {
		InputUserID = inputUserID;
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
	 * @return Returns the statusID.
	 */
	public long getStatusID() {
		return StatusID;
	}
	/**
	 * @param statusID The statusID to set.
	 */
	public void setStatusID(long statusID) {
		StatusID = statusID;
	}
	/**
	 * @return Returns the transNo.
	 */
	public String getTransNo() {
		return TransNo;
	}
	/**
	 * @param transNo The transNo to set.
	 */
	public void setTransNo(String transNo) {
		TransNo = transNo;
	}
	/**
	 * @return Returns the transTypeID.
	 */
	public long getTransTypeID() {
		return TransTypeID;
	}
	/**
	 * @param transTypeID The transTypeID to set.
	 */
	public void setTransTypeID(long transTypeID) {
		TransTypeID = transTypeID;
	}
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
}
