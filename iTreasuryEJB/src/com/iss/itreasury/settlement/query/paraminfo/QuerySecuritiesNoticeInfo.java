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
public class QuerySecuritiesNoticeInfo implements Serializable
{

	/**
	 * 
	 */
	public QuerySecuritiesNoticeInfo()
	{
		super();
		// TODO Auto-generated constructor stub
	}
	private long OfficeID = -1;                       //���´�  
	private long CurrencyID = -1;                     //����
	private long PayOrReceiveType=-1;                   //�ո�������  
	private String SecuritiesNoticeNoFrom="";            //֤ȯҵ��֪ͨ�� 
	private String SecuritiesNoticeNoto="";              //֤ȯҵ��֪ͨ��
	private Timestamp DateFrom = null;                //�ո�������      
	private Timestamp DateTo = null;                  //�ո������� 
	private double AmountFrom = 0.0;                  //���      
	private double AmountTo = 0.0;                    //���	
	private long StatusID=-1;                            //ҵ��֪ͨ��״̬	 
	private long BankID = -1;                         //����˾������      
	private long Desc = 1;                            //����ʽ
	private long OrderField = 1;                      //�����ֶ�





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
	public long getPayOrReceiveType() {
		return PayOrReceiveType;
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
	 * @param l
	 */
	public void setPayOrReceiveType(long l) {
		PayOrReceiveType = l;
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

}
