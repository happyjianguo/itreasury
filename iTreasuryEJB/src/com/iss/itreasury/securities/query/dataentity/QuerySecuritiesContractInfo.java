/*
 * Created on 2004-6-25
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.securities.query.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;

import com.iss.itreasury.securities.util.SECBaseDataEntity;

/**
 * @author gdzhao
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class QuerySecuritiesContractInfo extends SECBaseDataEntity	implements Serializable 
{
	
	private long contractId           = -1;   //合同id
	private String code               = "";   //合同编号
	private long applyID              = -1;   //对应申请书编号
	private long businessTypeId       = -1;   //业务类型id
	private long transactionTypeId    = -1;   //交易类型id
	private long counterpartID        = -1;   //交易对手id
	private Timestamp inputDate       = null; //合同录入日期
	private long statusID             = -1;   //合同状态
	private long nextCheckUserID      = -1;   //审核人，也作为参数查出审核状态
	private long inputUserID          = -1;   //录入人
	private double amount             = 0.00; //合同金额
	private Timestamp transactionDate = null; //(成交日期，委托理财业务的委托日期)
	private long term                 = -1;   //期限




	/*
	 * (non-Javadoc)
	 * 
	 * @see com.iss.itreasury.util.ITreasuryBaseDataEntity#getId()
	 */
	public long getId() {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.iss.itreasury.util.ITreasuryBaseDataEntity#setId(long)
	 */
	public void setId(long id) {
		// TODO Auto-generated method stub

	}






	/**
	 * @return
	 */
	public long getApplyID() {
		return applyID;
	}

	/**
	 * @return
	 */
	public long getBusinessTypeId() {
		return businessTypeId;
	}

	/**
	 * @return
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @return
	 */
	public long getContractId() {
		return contractId;
	}

	/**
	 * @return
	 */
	public long getCounterpartID() {
		return counterpartID;
	}

	/**
	 * @return
	 */
	public Timestamp getInputDate() {
		return inputDate;
	}

	/**
	 * @return
	 */
	public long getInputUserID() {
		return inputUserID;
	}

	/**
	 * @return
	 */
	public long getNextCheckUserID() {
		return nextCheckUserID;
	}

	/**
	 * @return
	 */
	public long getStatusID() {
		return statusID;
	}

	/**
	 * @param l
	 */
	public void setApplyID(long l) {
		applyID = l;
	}

	/**
	 * @param l
	 */
	public void setBusinessTypeId(long l) {
		businessTypeId = l;
	}

	/**
	 * @param string
	 */
	public void setCode(String string) {
		code = string;
	}

	/**
	 * @param l
	 */
	public void setContractId(long l) {
		contractId = l;
	}

	/**
	 * @param l
	 */
	public void setCounterpartID(long l) {
		counterpartID = l;
	}

	/**
	 * @param timestamp
	 */
	public void setInputDate(Timestamp timestamp) {
		inputDate = timestamp;
	}

	/**
	 * @param l
	 */
	public void setInputUserID(long l) {
		inputUserID = l;
	}

	/**
	 * @param l
	 */
	public void setNextCheckUserID(long l) {
		nextCheckUserID = l;
	}

	/**
	 * @param l
	 */
	public void setStatusID(long l) {
		statusID = l;
	}

	/**
	 * @return
	 */
	public long getTransactionTypeId() {
		return transactionTypeId;
	}

	/**
	 * @param l
	 */
	public void setTransactionTypeId(long l) {
		transactionTypeId = l;
	}

	/**
	 * @return Returns the amount.
	 */
	public double getAmount() {
		return amount;
	}
	/**
	 * @param amount The amount to set.
	 */
	public void setAmount(double amount) {
		this.amount = amount;
	}
	/**
	 * @return Returns the term.
	 */
	public long getTerm() {
		return term;
	}
	/**
	 * @param term The term to set.
	 */
	public void setTerm(long term) {
		this.term = term;
	}
	/**
	 * @return Returns the transactionDate.
	 */
	public Timestamp getTransactionDate() {
		return transactionDate;
	}
	/**
	 * @param transactionDate The transactionDate to set.
	 */
	public void setTransactionDate(Timestamp transactionDate) {
		this.transactionDate = transactionDate;
	}
}
