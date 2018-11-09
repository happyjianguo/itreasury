package com.iss.itreasury.settlement.ebankPrint.dataentity;

import java.sql.Timestamp;

public class QueryEbankPrint 
{
    private long ID = -1;  
	private String TransNo = ""; 
	private long TransactionTypeID = -1; 
	private Timestamp execute = null; 
	private long InputUserID = -1; 
	private String Abstract = ""; 
	private String PayAccountNo = ""; 
	private String ReceiveAccountNo = "";
	private double transAmount = 0.0;  //½»Ò×½ð¶î
	
	public String getAbstract() {
		return Abstract;
	}
	public void setAbstract(String abstract1) {
		Abstract = abstract1;
	}
	public Timestamp getExecute() {
		return execute;
	}
	public void setExecute(Timestamp execute) {
		this.execute = execute;
	}
	public long getID() {
		return ID;
	}
	public void setID(long id) {
		ID = id;
	}
	public long getInputUserID() {
		return InputUserID;
	}
	public void setInputUserID(long inputUserID) {
		InputUserID = inputUserID;
	}
	public String getPayAccountNo() {
		return PayAccountNo;
	}
	public void setPayAccountNo(String payAccountNo) {
		PayAccountNo = payAccountNo;
	}
	public String getReceiveAccountNo() {
		return ReceiveAccountNo;
	}
	public void setReceiveAccountNo(String receiveAccountNo) {
		ReceiveAccountNo = receiveAccountNo;
	}
	public long getTransactionTypeID() {
		return TransactionTypeID;
	}
	public void setTransactionTypeID(long transactionTypeID) {
		TransactionTypeID = transactionTypeID;
	}
	public double getTransAmount() {
		return transAmount;
	}
	public void setTransAmount(double transAmount) {
		this.transAmount = transAmount;
	}
	public String getTransNo() {
		return TransNo;
	}
	public void setTransNo(String transNo) {
		TransNo = transNo;
	}
}