/*
 * Created on 2003-11-11
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.settlement.query.resultinfo;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author xrli
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class QuerySecuritiesNoticeResultInfo implements Serializable
{

	/**
	 * 
	 */
	public QuerySecuritiesNoticeResultInfo()
	{
		super();
		// TODO Auto-generated constructor stub
	}
	private String TransNo="";                  //交易号
	private long  ID=-1;                //交易ID	 	
	private long  SeCuritiesNoticeID=-1;                //证券业务通知单 
	private long TransactionTypeID=-1;                   //业务类型  
	private String SecTransTypeDesc="";                  //证券业务类型
	private Timestamp TransDate = null;               	 //收付款日期	 
	private double Amount = 0.0;                 	 //金额 
	private long PayOrReceiveType;                   //收付款方向		
	private long StatusID=-1;                            //状态	 
	private long BankID = -1;                         //财务公司开户行      
	private long Desc = 1;                            //排序方式
	private long OrderField = 1;                      //排序字段
	private String CounterpartBankName = "";   //交易对手开户行名称	
	private String CounterpartAccountName = "";    //交易对手账户名称	
	
	


	/**
	 * @return
	 */
	public double getAmount() {
		return Amount;
	}

	/**
	 * @return
	 */
	public long getBankID() {
		return BankID;
	}

	
	

	/**
	 * @return
	 */
	public long getDesc() {
		return Desc;
	}

	/**
	 * @return
	 */
	public long getOrderField() {
		return OrderField;
	}

	/**
	 * @return
	 */
	public long getPayOrReceiveType() {
		return PayOrReceiveType;
	}

	

	/**
	 * @return
	 */
	public long getStatusID() {
		return StatusID;
	}

	/**
	 * @return
	 */
	public long getTransactionTypeID() {
		return TransactionTypeID;
	}

	/**
	 * @param d
	 */
	public void setAmount(double d) {
		Amount = d;
	}

	/**
	 * @param l
	 */
	public void setBankID(long l) {
		BankID = l;
	}

	


	/**
	 * @param l
	 */
	public void setDesc(long l) {
		Desc = l;
	}

	/**
	 * @param l
	 */
	public void setOrderField(long l) {
		OrderField = l;
	}

	/**
	 * @param l
	 */
	public void setPayOrReceiveType(long l) {
		PayOrReceiveType = l;
	}

	
	/**
	 * @param l
	 */
	public void setStatusID(long l) {
		StatusID = l;
	}

	/**
	 * @param l
	 */
	public void setTransactionTypeID(long l) {
		TransactionTypeID = l;
	}

	/**
	 * @return
	 */
	public String getSecTransTypeDesc() {
		return SecTransTypeDesc;
	}

	/**
	 * @param string
	 */
	public void setSecTransTypeDesc(String string) {
		SecTransTypeDesc = string;
	}

	/**
	 * @return
	 */
	public String getCounterpartAccountName() {
		return CounterpartAccountName;
	}

	/**
	 * @return
	 */
	public String getCounterpartBankName() {
		return CounterpartBankName;
	}

	/**
	 * @param string
	 */
	public void setCounterpartAccountName(String string) {
		CounterpartAccountName = string;
	}

	/**
	 * @param string
	 */
	public void setCounterpartBankName(String string) {
		CounterpartBankName = string;
	}

	

	/**
	 * @return
	 */
	public Timestamp getTransDate() {
		return TransDate;
	}

	/**
	 * @param timestamp
	 */
	public void setTransDate(Timestamp timestamp) {
		TransDate = timestamp;
	}

	/**
	 * @return
	 */
	public String getTransNo() {
		return TransNo;
	}

	/**
	 * @param string
	 */
	public void setTransNo(String string) {
		TransNo = string;
	}

	/**
	 * @return
	 */
	public long getID() {
		return ID;
	}

	/**
	 * @return
	 */
	public long getSeCuritiesNoticeID() {
		return SeCuritiesNoticeID;
	}

	/**
	 * @param l
	 */
	public void setID(long l) {
		ID = l;
	}

	/**
	 * @param l
	 */
	public void setSeCuritiesNoticeID(long l) {
		SeCuritiesNoticeID = l;
	}

}
