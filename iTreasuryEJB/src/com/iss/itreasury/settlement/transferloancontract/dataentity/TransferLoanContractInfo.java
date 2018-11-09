package com.iss.itreasury.settlement.transferloancontract.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Collection;

public class TransferLoanContractInfo implements Serializable {
	private long ID = -1;
	private long OFFICEID = -1;
	private long CURRENCYID = -1;
	private String STRANSNO = "";
	private long TRANSACTIONTYPEID = -1;//通知单业务类型（信贷资产转让收款，信贷资产转让付款，信贷资产转让代收款）
	private long NOTICEID = -1;
	private long PAYBANKID = -1;
	private long PAYGENRALLEDGERTYPEID = -1;
	private double AMOUNT = 0.00;
	private double INTEREST = 0.00;
	private double COMMISSION = 0.00;
	private long RECEIVEBANKID = -1;
	private long RECGENERALLEDGERTYPEID = -1;
	private Timestamp INTERESTSTART = null;
	private Timestamp EXECUTE = null;
	private Timestamp NMODIFY = null;
	private long INPUTUSERID = -1;
	private String SABSTRACT = "";
	private String SCHECKABSTRACT = "";
	private long STATUSID = -1;
	private long CHECKUSERID = -1;
	private Timestamp INPUTDATE = null;
	private long ABSTRACTID = -1;
	private long TRANSFERCONTRACTID = -1;//转让合同ID
	private long TRANSFERTYPE = -1;//转让操作类型（卖出回购，卖出卖断）
	private long COUNTERPARTID = -1;  //交易对手ID
	private double unPreDrawInterest = 0.0;//未计提利息
	private double preDrawInterest = 0.0;//已提利息
	
	private long[] statusIDs = null;
	
	private Collection coll = null;
	
	public void setStatusIDs(long[] statusIDs) {
		this.statusIDs = statusIDs;
	}
	public long[] getStatusIDs() {
		return this.statusIDs;
	}	
	private Collection repaycoll=null;//卖出卖断收款明细存储
	public long getTRANSFERCONTRACTID() {
		return TRANSFERCONTRACTID;
	}
	public void setTRANSFERCONTRACTID(long transfercontractid) {
		TRANSFERCONTRACTID = transfercontractid;
	}
	public long getABSTRACTID() {
		return ABSTRACTID;
	}
	public void setABSTRACTID(long abstractid) {
		ABSTRACTID = abstractid;
	}
	public Timestamp getINPUTDATE() {
		return INPUTDATE;
	}
	public void setINPUTDATE(Timestamp inputdate) {
		INPUTDATE = inputdate;
	}
	public double getAMOUNT() {
		return AMOUNT;
	}
	public void setAMOUNT(double amount) {
		AMOUNT = amount;
	}
	public long getCHECKUSERID() {
		return CHECKUSERID;
	}
	public void setCHECKUSERID(long checkuserid) {
		CHECKUSERID = checkuserid;
	}
	public double getCOMMISSION() {
		return COMMISSION;
	}
	public void setCOMMISSION(double commission) {
		COMMISSION = commission;
	}
	public long getCURRENCYID() {
		return CURRENCYID;
	}
	public void setCURRENCYID(long currencyid) {
		CURRENCYID = currencyid;
	}
	public Timestamp getEXECUTE() {
		return EXECUTE;
	}
	public void setEXECUTE(Timestamp execute) {
		EXECUTE = execute;
	}
	public long getID() {
		return ID;
	}
	public void setID(long id) {
		ID = id;
	}
	public long getINPUTUSERID() {
		return INPUTUSERID;
	}
	public void setINPUTUSERID(long inputuserid) {
		INPUTUSERID = inputuserid;
	}
	public double getINTEREST() {
		return INTEREST;
	}
	public void setINTEREST(double interest) {
		INTEREST = interest;
	}
	public Timestamp getINTERESTSTART() {
		return INTERESTSTART;
	}
	public void setINTERESTSTART(Timestamp intereststart) {
		INTERESTSTART = intereststart;
	}
	public Timestamp getNMODIFY() {
		return NMODIFY;
	}
	public void setNMODIFY(Timestamp nmodify) {
		NMODIFY = nmodify;
	}
	public long getNOTICEID() {
		return NOTICEID;
	}
	public void setNOTICEID(long noticeid) {
		NOTICEID = noticeid;
	}
	public long getOFFICEID() {
		return OFFICEID;
	}
	public void setOFFICEID(long officeid) {
		OFFICEID = officeid;
	}
	public long getPAYBANKID() {
		return PAYBANKID;
	}
	public void setPAYBANKID(long paybankid) {
		PAYBANKID = paybankid;
	}
	public long getPAYGENRALLEDGERTYPEID() {
		return PAYGENRALLEDGERTYPEID;
	}
	public void setPAYGENRALLEDGERTYPEID(long paygenralledgertypeid) {
		PAYGENRALLEDGERTYPEID = paygenralledgertypeid;
	}
	public long getRECEIVEBANKID() {
		return RECEIVEBANKID;
	}
	public void setRECEIVEBANKID(long receivebankid) {
		RECEIVEBANKID = receivebankid;
	}
	public long getRECGENERALLEDGERTYPEID() {
		return RECGENERALLEDGERTYPEID;
	}
	public void setRECGENERALLEDGERTYPEID(long recgeneralledgertypeid) {
		RECGENERALLEDGERTYPEID = recgeneralledgertypeid;
	}
	public String getSABSTRACT() {
		return SABSTRACT;
	}
	public void setSABSTRACT(String sabstract) {
		SABSTRACT = sabstract;
	}
	public String getSCHECKABSTRACT() {
		return SCHECKABSTRACT;
	}
	public void setSCHECKABSTRACT(String scheckabstract) {
		SCHECKABSTRACT = scheckabstract;
	}
	public long getSTATUSID() {
		return STATUSID;
	}
	public void setSTATUSID(long statusid) {
		STATUSID = statusid;
	}
	public String getSTRANSNO() {
		return STRANSNO;
	}
	public void setSTRANSNO(String stransno) {
		STRANSNO = stransno;
	}
	public long getTRANSACTIONTYPEID() {
		return TRANSACTIONTYPEID;
	}
	public void setTRANSACTIONTYPEID(long transactiontypeid) {
		TRANSACTIONTYPEID = transactiontypeid;
	}
	public boolean isNeedMatch(String fieldName){
		if (fieldName.compareToIgnoreCase("StatusID") == 0
				|| fieldName.compareToIgnoreCase("TransactionTypeID") == 0
				|| fieldName.compareToIgnoreCase("OfficeID") == 0
				|| fieldName.compareToIgnoreCase("CurrencyID") == 0
				|| fieldName.compareToIgnoreCase("InputUserID") == 0
				|| fieldName.compareToIgnoreCase("PayBankID") == 0
				|| fieldName.compareToIgnoreCase("PAYGENRALLEDGERTYPEID") == 0
				|| fieldName.compareToIgnoreCase("Amount") == 0	
				|| fieldName.compareToIgnoreCase("INTEREST") == 0	
				|| fieldName.compareToIgnoreCase("COMMISSION") == 0
				|| fieldName.compareToIgnoreCase("NOTICEID") == 0	
				|| fieldName.compareToIgnoreCase("dtInterestStart") == 0
			//	|| fieldName.compareToIgnoreCase("INTERESTSTART") == 0 不对起息日进行匹配
				|| fieldName.compareToIgnoreCase("RECEIVEBANKID") == 0
				|| fieldName.compareToIgnoreCase("RECGENERALLEDGERTYPEID") == 0)
			
		{
			return true;
		}
		return false;
	}
	public long getTRANSFERTYPE() {
		return TRANSFERTYPE;
	}
	public void setTRANSFERTYPE(long transfertype) {
		TRANSFERTYPE = transfertype;
	}
	public Collection getColl() {
		return coll;
	}
	public void setColl(Collection coll) {
		this.coll = coll;
	}
	public long getCOUNTERPARTID() {
		return COUNTERPARTID;
	}
	public void setCOUNTERPARTID(long counterpartid) {
		COUNTERPARTID = counterpartid;
	}
	public Collection getRepaycoll() {
		return repaycoll;
	}
	public void setRepaycoll(Collection repaycoll) {
		this.repaycoll = repaycoll;
	}
	public double getPreDrawInterest() {
		return preDrawInterest;
	}
	public void setPreDrawInterest(double preDrawInterest) {
		this.preDrawInterest = preDrawInterest;
	}
	public double getUnPreDrawInterest() {
		return unPreDrawInterest;
	}
	public void setUnPreDrawInterest(double unPreDrawInterest) {
		this.unPreDrawInterest = unPreDrawInterest;
	}
	
}
