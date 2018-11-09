package com.iss.itreasury.settlement.transinternallend.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;



public class QueryInternalLendConditionInfo implements Serializable{
	
	private long OfficeID = -1;//办事处ID
	private long CurrencyID = -1;//币种ID
	private long UserID = -1;//用户ID
	private long TypeID = -1;// 查询类型：0，（处理的查找）；1，（复核的查找）
	private long StatusID = -1;//交易状态
	private long TransactionTypeID = -1; //交易类型（内部拆借，内部拆借返款）	
	int OrderByType = -1 ;  //排序类型
	boolean isDesc =false;  //升序或降序
	private long[]  Status =null;  //交易状态
	private Timestamp Date = null; //查询日期
	
	public long[] getStatus() {
		return Status;
	}
	public void setStatus(long[] status) {
		Status = status;
	}
	public long getOfficeID() {
		return OfficeID;
	}
	public void setOfficeID(long officeID) {
		OfficeID = officeID;
	}
	public long getCurrencyID() {
		return CurrencyID;
	}
	public void setCurrencyID(long currencyID) {
		CurrencyID = currencyID;
	}
	public long getUserID() {
		return UserID;
	}
	public void setUserID(long userID) {
		UserID = userID;
	}
	public long getTypeID() {
		return TypeID;
	}
	public void setTypeID(long typeID) {
		TypeID = typeID;
	}
	public long getStatusID() {
		return StatusID;
	}
	public void setStatusID(long statusID) {
		StatusID = statusID;
	}
	public long getTransactionTypeID() {
		return TransactionTypeID;
	}
	public void setTransactionTypeID(long transactionTypeID) {
		TransactionTypeID = transactionTypeID;
	}
	public int getOrderByType() {
		return OrderByType;
	}
	public void setOrderByType(int orderByType) {
		OrderByType = orderByType;
	}
	public boolean isDesc() {
		return isDesc;
	}
	public void setDesc(boolean isDesc) {
		this.isDesc = isDesc;
	}
	public Timestamp getDate() {
		return Date;
	}
	public void setDate(Timestamp date) {
		Date = date;
	}

}
