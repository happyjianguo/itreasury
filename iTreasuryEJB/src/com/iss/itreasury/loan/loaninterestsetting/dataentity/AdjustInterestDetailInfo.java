package com.iss.itreasury.loan.loaninterestsetting.dataentity;

import java.sql.Timestamp;

public class AdjustInterestDetailInfo {
	public String numberRow;
	public long NADJUSTCONDITIONID;
	public long NLOANPAYNOTICEID;
	public long NCONTRACTID;
	public long NBANKINTERESTID;
	public long NADJUSTSERIAL;
	public Timestamp DTSTARTDATE;
	public Timestamp DTENDDATE;
	public double MRATE;
	public long NISCOUNTINTEREST;
	public double MSTAIDADJUSTRATE;
	public double MADJUSTRATE;
	public long STATUS;
	public long NadjustStatus;// 此处为 新增 的 放款通知单 的状态  全哨。2010-4-29
	
	public double beforeBaseRate; //调整前基准利率
	public double beforeMadjustRate;//调整前浮动比例
	public double beforeMstaidAdjustRate;//调整前固定浮动利率
	
	public long nType;// 贷款类型
	
	public String nSubTypeName ;//贷款子类型
	
	public String loanClientName;//借款单位名称
	
	public String consignClientName;//委托单位名称
	
	public String nInputUser;
	
	public Timestamp dtInputDate;
	
	
	
	public long nofficeID;
	
	public long nCurrencyID;
	
	public long batchID=-1;//批次号
	
	public long getBatchID() {
		return batchID;
	}
	public void setBatchID(long batchID) {
		this.batchID = batchID;
	}
	public String getNInputUser() {
		return nInputUser;
	}
	public void setNInputUser(String inputUser) {
		nInputUser = inputUser;
	}
	public long getNType() {
		return nType;
	}
	public void setNType(long type) {
		nType = type;
	}
	public long getNadjustStatus() {
		return NadjustStatus;
	}
	public void setNadjustStatus(long nadjustStatus) {
		NadjustStatus = nadjustStatus;
	}
	public Timestamp getDTENDDATE() {
		return DTENDDATE;
	}
	public void setDTENDDATE(Timestamp dtenddate) {
		DTENDDATE = dtenddate;
	}
	public Timestamp getDTSTARTDATE() {
		return DTSTARTDATE;
	}
	public void setDTSTARTDATE(Timestamp dtstartdate) {
		DTSTARTDATE = dtstartdate;
	}
	public double getMADJUSTRATE() {
		return MADJUSTRATE;
	}
	public void setMADJUSTRATE(double madjustrate) {
		MADJUSTRATE = madjustrate;
	}
	public double getMRATE() {
		return MRATE;
	}
	public void setMRATE(double mrate) {
		MRATE = mrate;
	}
	public double getMSTAIDADJUSTRATE() {
		return MSTAIDADJUSTRATE;
	}
	public void setMSTAIDADJUSTRATE(double mstaidadjustrate) {
		MSTAIDADJUSTRATE = mstaidadjustrate;
	}
	public long getNADJUSTCONDITIONID() {
		return NADJUSTCONDITIONID;
	}
	public void setNADJUSTCONDITIONID(long nadjustconditionid) {
		NADJUSTCONDITIONID = nadjustconditionid;
	}
	public long getNADJUSTSERIAL() {
		return NADJUSTSERIAL;
	}
	public void setNADJUSTSERIAL(long nadjustserial) {
		NADJUSTSERIAL = nadjustserial;
	}
	public long getNBANKINTERESTID() {
		return NBANKINTERESTID;
	}
	public void setNBANKINTERESTID(long nbankinterestid) {
		NBANKINTERESTID = nbankinterestid;
	}
	public long getNCONTRACTID() {
		return NCONTRACTID;
	}
	public void setNCONTRACTID(long ncontractid) {
		NCONTRACTID = ncontractid;
	}
	public long getNISCOUNTINTEREST() {
		return NISCOUNTINTEREST;
	}
	public void setNISCOUNTINTEREST(long niscountinterest) {
		NISCOUNTINTEREST = niscountinterest;
	}
	public long getNLOANPAYNOTICEID() {
		return NLOANPAYNOTICEID;
	}
	public void setNLOANPAYNOTICEID(long nloanpaynoticeid) {
		NLOANPAYNOTICEID = nloanpaynoticeid;
	}
	public long getSTATUS() {
		return STATUS;
	}
	public void setSTATUS(long status) {
		STATUS = status;
	}
	public String getLoanClientName() {
		return loanClientName;
	}
	public void setLoanClientName(String loanClientName) {
		this.loanClientName = loanClientName;
	}
	public String getConsignClientName() {
		return consignClientName;
	}
	public void setConsignClientName(String consignClientName) {
		this.consignClientName = consignClientName;
	}
	public Timestamp getDtInputDate() {
		return dtInputDate;
	}
	public void setDtInputDate(Timestamp dtInputDate) {
		this.dtInputDate = dtInputDate;
	}
	public double getBeforeBaseRate() {
		return beforeBaseRate;
	}
	public void setBeforeBaseRate(double beforeBaseRate) {
		this.beforeBaseRate = beforeBaseRate;
	}
	public double getBeforeMadjustRate() {
		return beforeMadjustRate;
	}
	public void setBeforeMadjustRate(double beforeMadjustRate) {
		this.beforeMadjustRate = beforeMadjustRate;
	}
	public double getBeforeMstaidAdjustRate() {
		return beforeMstaidAdjustRate;
	}
	public void setBeforeMstaidAdjustRate(double beforeMstaidAdjustRate) {
		this.beforeMstaidAdjustRate = beforeMstaidAdjustRate;
	}
	public String getNumberRow() {
		return numberRow;
	}
	public void setNumberRow(String numberRow) {
		this.numberRow = numberRow;
	}
	public String getNSubTypeName() {
		return nSubTypeName;
	}
	public void setNSubTypeName(String subTypeName) {
		nSubTypeName = subTypeName;
	}
	public long getNofficeID() {
		return nofficeID;
	}
	public void setNofficeID(long nofficeID) {
		this.nofficeID = nofficeID;
	}
	public long getNCurrencyID() {
		return nCurrencyID;
	}
	public void setNCurrencyID(long currencyID) {
		nCurrencyID = currencyID;
	}

	
}
