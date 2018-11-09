/*
 * Created on 2004-2-11
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.iss.itreasury.glinterface.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author yychen
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class GLEntryInfo implements Serializable
{
	private long ID = -1;
	private long OfficeID = -1;
	private long CurrencyID = -1;
	private String TransNo = "";
	private Timestamp Execute = null;
	private Timestamp InterestStart = null;
	private double Amount = 0;
	private String BankChequeNo = "";
	private String Abstract = "";
	private String Subject = "";
	private long DirectionID = -1;
	private long TransactionTypeID = -1;//交易类型
	private long StatusID = -1;
	private String multiCode = "";		//多维码（oracle_cpf现金流量用）
	
	
    //辅助属性 yyhe于2006-11-02添加，用于中交项目
	
	String CheckUserName = "";//复核人
	String InputUserName = "";//录入人
	
	//辅助核算信息
	String assitantName = ""; 
	String assitantValue = ""; 
	
	String clientCode = "";//客户编号
	String clientName = "";//客户名称
	String clientShortName = "";//客户简称
	
	/**
	 * @return
	 */
	public String getAbstract()
	{
		return Abstract;
	}

	/**
	 * @return
	 */
	public double getAmount()
	{
		return Amount;
	}

	/**
	 * @return
	 */
	public String getBankChequeNo()
	{
		return BankChequeNo;
	}

	/**
	 * @return
	 */
	public long getCurrencyID()
	{
		return CurrencyID;
	}

	/**
	 * @return
	 */
	public long getDirectionID()
	{
		return DirectionID;
	}

	/**
	 * @return
	 */
	public Timestamp getExecute()
	{
		return Execute;
	}

	/**
	 * @return
	 */
	public long getID()
	{
		return ID;
	}

	/**
	 * @return
	 */
	public Timestamp getInterestStart()
	{
		return InterestStart;
	}

	/**
	 * @return
	 */
	public long getOfficeID()
	{
		return OfficeID;
	}

	/**
	 * @return
	 */
	public String getSubject()
	{
		return Subject;
	}

	/**
	 * @return
	 */
	public String getTransNo()
	{
		return TransNo;
	}

	/**
	 * @return
	 */
	public String getMultiCode()
	{
		return multiCode;
	}
	
	/**
	 * @param string
	 */
	public void setMultiCode(String string)
	{
		multiCode = string;
	}
	
	/**
	 * @param string
	 */
	public void setAbstract(String string)
	{
		Abstract = string;
	}

	/**
	 * @param d
	 */
	public void setAmount(double d)
	{
		Amount = d;
	}

	/**
	 * @param string
	 */
	public void setBankChequeNo(String string)
	{
		BankChequeNo = string;
	}

	/**
	 * @param l
	 */
	public void setCurrencyID(long l)
	{
		CurrencyID = l;
	}

	/**
	 * @param l
	 */
	public void setDirectionID(long l)
	{
		DirectionID = l;
	}

	/**
	 * @param timestamp
	 */
	public void setExecute(Timestamp timestamp)
	{
		Execute = timestamp;
	}

	/**
	 * @param l
	 */
	public void setID(long l)
	{
		ID = l;
	}

	/**
	 * @param timestamp
	 */
	public void setInterestStart(Timestamp timestamp)
	{
		InterestStart = timestamp;
	}

	/**
	 * @param l
	 */
	public void setOfficeID(long l)
	{
		OfficeID = l;
	}

	/**
	 * @param string
	 */
	public void setSubject(String string)
	{
		Subject = string;
	}

	/**
	 * @param string
	 */
	public void setTransNo(String string)
	{
		TransNo = string;
	}

	/**
	 * @return
	 */
	public long getStatusID()
	{
		return StatusID;
	}

	/**
	 * @param l
	 */
	public void setStatusID(long l)
	{
		StatusID = l;
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

	public String getCheckUserName() {
		return CheckUserName;
	}

	public void setCheckUserName(String checkUserName) {
		CheckUserName = checkUserName;
	}

	public String getAssitantName()
	{
		return assitantName;
	}

	public void setAssitantName(String assitantName)
	{
		this.assitantName = assitantName;
	}

	public String getAssitantValue()
	{
		return assitantValue;
	}

	public void setAssitantValue(String assitantValue)
	{
		this.assitantValue = assitantValue;
	}
	
	public String getClientCode() {
		return clientCode;
	}

	public void setClientCode(String clientCode) {
		this.clientCode = clientCode;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getClientShortName() {
		return clientShortName;
	}

	public void setClientShortName(String clientShortName) {
		this.clientShortName = clientShortName;
	}
	
	public String getInputUserName()
    {
        return InputUserName;
    }
    
    public void setInputUserName(String inputUserName) {
		InputUserName = inputUserName;
	}

}
