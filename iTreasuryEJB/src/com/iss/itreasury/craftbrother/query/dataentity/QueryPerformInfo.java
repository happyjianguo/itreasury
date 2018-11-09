package com.iss.itreasury.craftbrother.query.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;
public class QueryPerformInfo implements Serializable
{
	//收付款日期
	private Timestamp performDate = null;
	
	//通知单ID
	private long noticeId = -1;
	
	//结算交易类型
	private long transactionTypeId = -1;
	
	//结算交易子类型
	private long subTransactionTypeId = -1;
	
	//金额
	private double performAmount = 0;

	public Timestamp getPerformDate() {
		return performDate;
	}

	public void setPerformDate(Timestamp performDate) {
		this.performDate = performDate;
	}

	public long getNoticeId() {
		return noticeId;
	}

	public void setNoticeId(long noticeId) {
		this.noticeId = noticeId;
	}
	
	public long getTransactionTypeId() {
		return transactionTypeId;
	}

	public void setTransactionTypeId(long transactionTypeId) {
		this.transactionTypeId = transactionTypeId;
	}

	public double getPerformAmount() {
		return performAmount;
	}

	public void setPerformAmount(double performAmount) {
		this.performAmount = performAmount;
	}

	public long getSubTransactionTypeId() {
		return subTransactionTypeId;
	}

	public void setSubTransactionTypeId(long subTransactionTypeId) {
		this.subTransactionTypeId = subTransactionTypeId;
	}
	
	

}