/*
 * Created on 2003-11-11
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.settlement.query.paraminfo;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author xrli
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class QuerySecuritiesInfo implements Serializable
{

	/**
	 * 
	 */
	public QuerySecuritiesInfo()
	{
		super();
		// TODO Auto-generated constructor stub
	}
	private long OfficeID = -1;                       //办事处  
	private long CurrencyID = -1;                     //币种
	private long TransactionTypeID;                   //业务类型  
	private String SecuritiesNoticeNoFrom;            //证券业务通知单 
	private String SecuritiesNoticeNoto;              //证券业务通知单
	private Timestamp DateFrom = null;                //收付款日期      
	private Timestamp DateTo = null;                  //收付款日期 
	private double AmountFrom = 0.0;                  //金额      
	private double AmountTo = 0.0;                    //金额
	private String TransNoFrom;                       //交易号 
	private String TransNoto;                         //交易号  
	private Timestamp ExecuteDateFrom = null;         //执行日      
	private Timestamp  ExecuteDateTo = null;          //执行日
	private long StatusID;                            //交易状态 
	private long InputUserID;                         //录入人 
	private long CheckUserID;                         //复核人 
	private long BankID = -1;                         //财务公司开户行      
	private long Desc = 1;                            //排序方式
	private long OrderField = 1;                      //排序字段



	/**
	 * @return
	 */
	public double getAmountFrom() {
		return AmountFrom;
	}

	/**
	 * @return
	 */
	public double getAmountTo() {
		return AmountTo;
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
	public long getCheckUserID() {
		return CheckUserID;
	}

	/**
	 * @return
	 */
	public long getCurrencyID() {
		return CurrencyID;
	}

	/**
	 * @return
	 */
	public Timestamp getDateFrom() {
		return DateFrom;
	}

	/**
	 * @return
	 */
	public Timestamp getDateTo() {
		return DateTo;
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
	public Timestamp getExecuteDateFrom() {
		return ExecuteDateFrom;
	}

	/**
	 * @return
	 */
	public Timestamp getExecuteDateTo() {
		return ExecuteDateTo;
	}

	/**
	 * @return
	 */
	public long getInputUserID() {
		return InputUserID;
	}

	/**
	 * @return
	 */
	public long getOfficeID() {
		return OfficeID;
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
	public String getSecuritiesNoticeNoFrom() {
		return SecuritiesNoticeNoFrom;
	}

	/**
	 * @return
	 */
	public String getSecuritiesNoticeNoto() {
		return SecuritiesNoticeNoto;
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
	 * @return
	 */
	public String getTransNoFrom() {
		return TransNoFrom;
	}

	/**
	 * @return
	 */
	public String getTransNoto() {
		return TransNoto;
	}

	/**
	 * @param d
	 */
	public void setAmountFrom(double d) {
		AmountFrom = d;
	}

	/**
	 * @param d
	 */
	public void setAmountTo(double d) {
		AmountTo = d;
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
	public void setCheckUserID(long l) {
		CheckUserID = l;
	}

	/**
	 * @param l
	 */
	public void setCurrencyID(long l) {
		CurrencyID = l;
	}

	/**
	 * @param timestamp
	 */
	public void setDateFrom(Timestamp timestamp) {
		DateFrom = timestamp;
	}

	/**
	 * @param timestamp
	 */
	public void setDateTo(Timestamp timestamp) {
		DateTo = timestamp;
	}

	/**
	 * @param l
	 */
	public void setDesc(long l) {
		Desc = l;
	}

	/**
	 * @param timestamp
	 */
	public void setExecuteDateFrom(Timestamp timestamp) {
		ExecuteDateFrom = timestamp;
	}

	/**
	 * @param timestamp
	 */
	public void setExecuteDateTo(Timestamp timestamp) {
		ExecuteDateTo = timestamp;
	}

	/**
	 * @param l
	 */
	public void setInputUserID(long l) {
		InputUserID = l;
	}

	/**
	 * @param l
	 */
	public void setOfficeID(long l) {
		OfficeID = l;
	}

	/**
	 * @param l
	 */
	public void setOrderField(long l) {
		OrderField = l;
	}

	/**
	 * @param string
	 */
	public void setSecuritiesNoticeNoFrom(String string) {
		SecuritiesNoticeNoFrom = string;
	}

	/**
	 * @param string
	 */
	public void setSecuritiesNoticeNoto(String string) {
		SecuritiesNoticeNoto = string;
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
	 * @param string
	 */
	public void setTransNoFrom(String string) {
		TransNoFrom = string;
	}

	/**
	 * @param string
	 */
	public void setTransNoto(String string) {
		TransNoto = string;
	}

}
