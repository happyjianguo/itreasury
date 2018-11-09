package com.iss.itreasury.evoucher.print.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;

public class QueryPrintConditionInfo implements Serializable {
	
	private long OfficeID = -1;//办事处
	private long CurrencyID = -1;//币种
	
	private String TransactionTypeIDs = "";//业务类型
	private long ClientIDStart = -1;//客户ID（由）
	private long ClientIDEnd = -1;//客户ID（由）
	private String ClientNoStart = "";//客户编号（由）
	private String ClientNoEnd = "";//客户编号（由）
	private double AmountStart = 0.0;//交易金额（由）
	private double AmountEnd = 0.0;//交易金额（至）
	
	private String TransNoStart = "";//交易号（由）
	private String TransNoEnd = "";//交易号（至）
	
	private Timestamp dtTransStart = null;//交易日（由）
	private Timestamp dtTransEnd = null;//交易日（至）
	
	private long InputuserID = -1;//录入人
	private long CheckUserID = -1;//复核人id
    //交易状态 add by xwhe 2008-09-25
	private long transactionStatusID = -1;//交易状态
	//是否按照客户编号排序 add by xwhe 2009-01-06
	
	//增加收款方客户编号2009.3.20 by minzhao
	private long lClientIDStartIn = -1;
	
	private long lClientIDEndIn = -1;
	
	private String lClientIDStartInCtrl="";
	
	private String lClientIDEndInCtrl="";
	
	//add end minzhao
	
	private long lIsClient = -1;
	private long signer=-1;//过滤已设置授权客户的交易
	public long getSigner() {
		return signer;
	}

	public void setSigner(long signer) {
		this.signer = signer;
	}

	public long getLIsClient() {
		return lIsClient;
	}

	public void setLIsClient(long isClient) {
		lIsClient = isClient;
	}

	public double getAmountEnd() {
		return AmountEnd;
	}

	public void setAmountEnd(double amountEnd) {
		AmountEnd = amountEnd;
	}

	public double getAmountStart() {
		return AmountStart;
	}

	public void setAmountStart(double amountStart) {
		AmountStart = amountStart;
	}

	public long getClientIDStart() {
		return ClientIDStart;
	}

	public void setClientIDStart(long clientIDStart) {
		ClientIDStart = clientIDStart;
	}

	public String getClientNoStart() {
		return ClientNoStart;
	}

	public void setClientNoStart(String clientNoStart) {
		ClientNoStart = clientNoStart;
	}

	public long getCurrencyID() {
		return CurrencyID;
	}

	public void setCurrencyID(long currencyID) {
		CurrencyID = currencyID;
	}

	public Timestamp getDtTransEnd() {
		return dtTransEnd;
	}

	public void setDtTransEnd(Timestamp dtTransEnd) {
		this.dtTransEnd = dtTransEnd;
	}

	public Timestamp getDtTransStart() {
		return dtTransStart;
	}

	public void setDtTransStart(Timestamp dtTransStart) {
		this.dtTransStart = dtTransStart;
	}

	public long getInputuserID() {
		return InputuserID;
	}

	public void setInputuserID(long inputuserID) {
		InputuserID = inputuserID;
	}

	public long getCheckUserID() {
		return CheckUserID;
	}

	public void setCheckUserID(long checkUserID) {
		CheckUserID = checkUserID;
	}

	public long getOfficeID() {
		return OfficeID;
	}

	public void setOfficeID(long officeID) {
		OfficeID = officeID;
	}

	public String getTransactionTypeIDs() {
		return TransactionTypeIDs;
	}

	public void setTransactionTypeIDs(String transactionTypeIDs) {
		TransactionTypeIDs = transactionTypeIDs;
	}

	public String getTransNoEnd() {
		return TransNoEnd;
	}

	public void setTransNoEnd(String transNoEnd) {
		TransNoEnd = transNoEnd;
	}

	public String getTransNoStart() {
		return TransNoStart;
	}

	public void setTransNoStart(String transNoStart) {
		TransNoStart = transNoStart;
	}

	public long getClientIDEnd() {
		return ClientIDEnd;
	}

	public void setClientIDEnd(long clientIDEnd) {
		ClientIDEnd = clientIDEnd;
	}

	public String getClientNoEnd() {
		return ClientNoEnd;
	}

	public void setClientNoEnd(String clientNoEnd) {
		ClientNoEnd = clientNoEnd;
	}

	public long getTransactionStatusID() {
		return transactionStatusID;
	}

	public void setTransactionStatusID(long transactionStatusID) {
		this.transactionStatusID = transactionStatusID;
	}

	public long getLClientIDEndIn() {
		return lClientIDEndIn;
	}

	public void setLClientIDEndIn(long clientIDEndIn) {
		lClientIDEndIn = clientIDEndIn;
	}

	public String getLClientIDEndInCtrl() {
		return lClientIDEndInCtrl;
	}

	public void setLClientIDEndInCtrl(String clientIDEndInCtrl) {
		lClientIDEndInCtrl = clientIDEndInCtrl;
	}

	public long getLClientIDStartIn() {
		return lClientIDStartIn;
	}

	public void setLClientIDStartIn(long clientIDStartIn) {
		lClientIDStartIn = clientIDStartIn;
	}

	public String getLClientIDStartInCtrl() {
		return lClientIDStartInCtrl;
	}

	public void setLClientIDStartInCtrl(String clientIDStartInCtrl) {
		lClientIDStartInCtrl = clientIDStartInCtrl;
	}	
}
