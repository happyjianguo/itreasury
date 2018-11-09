package com.iss.itreasury.evoucher.print.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;

import com.iss.sysframe.base.dataentity.BaseDataEntity;

public class QueryPrintConditionInfo_New extends BaseDataEntity implements Serializable {
	
	private String strTransactionType = "";//业务类型
	
	private long ID = -1;
	private long OfficeID = -1;//办事处
	private long CurrencyID = -1;//币种
	
	private long lClientIDStart = -1;//客户ID（由）
	private long lClientIDEnd = -1;//客户ID（由）
	private String ClientNoStart = "";//客户编号（由）
	private String ClientNoEnd = "";//客户编号（由）
	private String strClientNoStart = "";	//客户编号（start）
	private String strClientNoEnd = "";		//客户编号（end）
	private String strTransNoStart = "";	//交易编号（start）
	private String strTransNoEnd = "";		//交易编号（end）
	private String transIDs = "";
	
	private double dMoneyStart = 0.0;	//交易金额（start）
	private double dMoneyEnd = 0.0;	//交易金额（end）
	
	private Timestamp tsTransStartDate = null;//交易日（由）
	private Timestamp tsTransEndDate = null;//交易日（至）
	
	private long lInputuserID = -1;//录入人
	private long lCheckUserID = -1;//复核人id
    //交易状态 add by xwhe 2008-09-25
	private long lTransactionStatusID = -1;//交易状态
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


	public long getOfficeID() {
		return OfficeID;
	}

	public void setOfficeID(long officeID) {
		OfficeID = officeID;
	}




	public String getClientNoEnd() {
		return ClientNoEnd;
	}

	public void setClientNoEnd(String clientNoEnd) {
		ClientNoEnd = clientNoEnd;
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

	@Override
	public long getId() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setId(long id) {
		// TODO Auto-generated method stub
		
	}

	public String getStrTransactionType() {
		return strTransactionType;
	}

	public void setStrTransactionType(String strTransactionType) {
		this.strTransactionType = strTransactionType;
	}


	public long getLClientIDStart() {
		return lClientIDStart;
	}

	public void setLClientIDStart(long clientIDStart) {
		lClientIDStart = clientIDStart;
	}

	public long getLClientIDEnd() {
		return lClientIDEnd;
	}

	public void setLClientIDEnd(long clientIDEnd) {
		lClientIDEnd = clientIDEnd;
	}

	public String getStrClientNoStart() {
		return strClientNoStart;
	}

	public void setStrClientNoStart(String strClientNoStart) {
		this.strClientNoStart = strClientNoStart;
	}

	public String getStrClientNoEnd() {
		return strClientNoEnd;
	}

	public void setStrClientNoEnd(String strClientNoEnd) {
		this.strClientNoEnd = strClientNoEnd;
	}

	public String getStrTransNoStart() {
		return strTransNoStart;
	}

	public void setStrTransNoStart(String strTransNoStart) {
		this.strTransNoStart = strTransNoStart;
	}

	public String getStrTransNoEnd() {
		return strTransNoEnd;
	}

	public void setStrTransNoEnd(String strTransNoEnd) {
		this.strTransNoEnd = strTransNoEnd;
	}

	public double getDMoneyStart() {
		return dMoneyStart;
	}

	public void setDMoneyStart(double moneyStart) {
		dMoneyStart = moneyStart;
	}

	public double getDMoneyEnd() {
		return dMoneyEnd;
	}

	public void setDMoneyEnd(double moneyEnd) {
		dMoneyEnd = moneyEnd;
	}

	public Timestamp getTsTransStartDate() {
		return tsTransStartDate;
	}

	public void setTsTransStartDate(Timestamp tsTransStartDate) {
		this.tsTransStartDate = tsTransStartDate;
	}

	public Timestamp getTsTransEndDate() {
		return tsTransEndDate;
	}

	public void setTsTransEndDate(Timestamp tsTransEndDate) {
		this.tsTransEndDate = tsTransEndDate;
	}

	public long getLInputuserID() {
		return lInputuserID;
	}

	public void setLInputuserID(long inputuserID) {
		lInputuserID = inputuserID;
	}

	public long getLCheckUserID() {
		return lCheckUserID;
	}

	public void setLCheckUserID(long checkUserID) {
		lCheckUserID = checkUserID;
	}

	public long getLTransactionStatusID() {
		return lTransactionStatusID;
	}

	public void setLTransactionStatusID(long transactionStatusID) {
		lTransactionStatusID = transactionStatusID;
	}

	public long getID() {
		return ID;
	}

	public void setID(long id) {
		ID = id;
	}

	public String getTransIDs() {
		return transIDs;
	}

	public void setTransIDs(String transIDs) {
		this.transIDs = transIDs;
	}	
}
