/*
 * Created on 2005-8-10
 */
package com.iss.itreasury.settlement.bankinstruction.entity;

import java.io.Serializable;

/**
 * @author weilu 创建银行指令所需的参数类
 */
public class CreateInstructionParam implements Serializable {

	private long lTransactionTypeID = -1; // 交易类型ID
	
	private Object objInfo = null;  //结算交易对象

	private long lOfficeID = -1; // 办事处

	private long lCurrencyID = -1; // 币种
	
	private long lBankType = -1;  //银行类型
	
	private long lInputUserID = -1; //录入人ID
	
	private long lCheckUserID = -1; //复核人ID
	
	private String sTransNo = ""; //结算交易号 

	/**
	 * @return Returns the lCurrencyID.
	 */
	public long getCurrencyID() {
		return lCurrencyID;
	}

	/**
	 * @param currencyID
	 *            The lCurrencyID to set.
	 */
	public void setCurrencyID(long currencyID) {
		lCurrencyID = currencyID;
	}

	/**
	 * @return Returns the lOfficeID.
	 */
	public long getOfficeID() {
		return lOfficeID;
	}

	/**
	 * @param officeID
	 *            The lOfficeID to set.
	 */
	public void setOfficeID(long officeID) {
		lOfficeID = officeID;
	}
	

	/**
	 * @return Returns the lTransType.
	 */
	public long getTransactionTypeID() {
		return lTransactionTypeID;
	}

	/**
	 * @param transType
	 *            The lTransType to set.
	 */
	public void setTransactionTypeID(long transactionTypeID) {
		lTransactionTypeID = transactionTypeID;
	}

	/**
	 * @return Returns the lBankType.
	 */
	public long getBankType() {
		return lBankType;
	}

	/**
	 * @param bankType The lBankType to set.
	 */
	public void setBankType(long bankType) {
		lBankType = bankType;
	}

	/**
	 * @return Returns the lCheckUserID.
	 */
	public long getCheckUserID() {
		return lCheckUserID;
	}

	/**
	 * @param checkUserID The lCheckUserID to set.
	 */
	public void setCheckUserID(long checkUserID) {
		lCheckUserID = checkUserID;
	}
	
	/**
	 * @return Returns the lInputUserID.
	 */
	public long getInputUserID() {
		return lInputUserID;
	}

	/**
	 * @param inputUserID The lInputUserID to set.
	 */
	public void setInputUserID(long inputUserID) {
		lInputUserID = inputUserID;
	}

	/**
	 * @return Returns the objInfo.
	 */
	public Object getObjInfo() {
		return objInfo;
	}

	/**
	 * @param objInfo The objInfo to set.
	 */
	public void setObjInfo(Object objInfo) {
		this.objInfo = objInfo;
	}

	/**
	 * @return Returns the sTransNo.
	 */
	public String getTransNo() {
		return sTransNo;
	}

	/**
	 * @param transNo The sTransNo to set.
	 */
	public void setTransNo(String transNo) {
		sTransNo = transNo;
	}
	
}
