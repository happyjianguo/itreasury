package com.iss.itreasury.loan.assurechargenotice.dataentity;

import java.sql.Timestamp;

import com.iss.itreasury.loan.base.LoanBaseDataEntity;
import com.iss.itreasury.util.Constant;

public class AssureAdjustInfo extends LoanBaseDataEntity {

	public AssureAdjustInfo() 
	{
		super();
		// TODO Auto-generated constructor stub
	}
	
	private long id = -1;						//ID
    private long currencyID = -1;				//币种
    private long officeID = -1;	
    private long contractID = -1;
    private long assureChargeFormID = -1;
    private String code = "";
    private String reason = "";
    private Timestamp effectDate = null;
    private Timestamp adjustEndDate = null;
    private double adjustRate = 0.0;
    private long isInterest = -1;
    private long inputUserID = -1;
    private Timestamp inputDate = null;
    private long nextCheckUserID = -1;
    private long nextCheckLevel = -1;
    private long statusID = -1;
    
    //非数据库字段
    private long clientID = -1;
    private String clientName = "";
    private String contractCode = "";
    private String checkUser = "";
    
    private Timestamp effectDateFrom = null;
    private Timestamp effectDateTo = null;
    private long queryPurpose = 1;
    private long recordCount = 0;
    private long pageLineCount = 0;
    private long pageCount = 0;
    private long pageNo = 0;
    private long rowNumStart = 0;
    private long rowNumEnd = 0;
    private long orderParam = -1;
    private long desc = Constant.PageControl.CODE_ASCORDESC_ASC;
    private String orderParamString = "";
    
    private AssureChargeNoticeInfo noticeInfo = null;
    
    
	public Timestamp getAdjustEndDate() {
		return adjustEndDate;
	}
	public void setAdjustEndDate(Timestamp adjustEndDate) {
		this.adjustEndDate = adjustEndDate;
		putUsedField("adjustEndDate", adjustEndDate);
	}
	public double getAdjustRate() {
		return adjustRate;
	}
	public void setAdjustRate(double adjustRate) {
		this.adjustRate = adjustRate;
		putUsedField("adjustRate", adjustRate);
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
		putUsedField("code", code);
	}
	public long getAssureChargeFormID() {
		return assureChargeFormID;
	}
	public void setAssureChargeFormID(long assureChargeFormID) {
		this.assureChargeFormID = assureChargeFormID;
		putUsedField("assureChargeFormID", assureChargeFormID);
	}
	public long getContractID() {
		return contractID;
	}
	public void setContractID(long contractID) {
		this.contractID = contractID;
		putUsedField("contractID", contractID);
	}
	public long getCurrencyID() {
		return currencyID;
	}
	public void setCurrencyID(long currencyID) {
		this.currencyID = currencyID;
		putUsedField("currencyID", currencyID);
	}
	public Timestamp getEffectDate() {
		return effectDate;
	}
	public void setEffectDate(Timestamp effectDate) {
		this.effectDate = effectDate;
		putUsedField("effectDate", effectDate);
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
		putUsedField("id", id);
	}
	public Timestamp getInputDate() {
		return inputDate;
	}
	public void setInputDate(Timestamp inputDate) {
		this.inputDate = inputDate;
		putUsedField("inputDate", inputDate);
	}
	public long getInputUserID() {
		return inputUserID;
	}
	public void setInputUserID(long inputUserID) {
		this.inputUserID = inputUserID;
		putUsedField("inputUserID", inputUserID);
	}
	public long getIsInterest() {
		return isInterest;
	}
	public void setIsInterest(long isInterest) {
		this.isInterest = isInterest;
		putUsedField("isInterest", isInterest);
	}
	public long getNextCheckLevel() {
		return nextCheckLevel;
	}
	public void setNextCheckLevel(long nextCheckLevel) {
		this.nextCheckLevel = nextCheckLevel;
		putUsedField("nextCheckLevel", nextCheckLevel);
	}
	public long getNextCheckUserID() {
		return nextCheckUserID;
	}
	public void setNextCheckUserID(long nextCheckUserID) {
		this.nextCheckUserID = nextCheckUserID;
		putUsedField("nextCheckUserID", nextCheckUserID);
	}
	public long getOfficeID() {
		return officeID;
	}
	public void setOfficeID(long officeID) {
		this.officeID = officeID;
		putUsedField("officeID", officeID);
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
		putUsedField("reason", reason);
	}
	public long getStatusID() {
		return statusID;
	}
	public void setStatusID(long statusID) {
		this.statusID = statusID;
		putUsedField("statusID", statusID);
	}	
	
    //非数据库字段
	public long getDesc() {
		return desc;
	}
	public void setDesc(long desc) {
		this.desc = desc;
	}
	public long getOrderParam() {
		return orderParam;
	}
	public void setOrderParam(long orderParam) {
		this.orderParam = orderParam;
	}
	public String getOrderParamString() {
		return orderParamString;
	}
	public void setOrderParamString(String orderParamString) {
		this.orderParamString = orderParamString;
	}
	public long getPageCount() {
		return pageCount;
	}
	public void setPageCount(long pageCount) {
		this.pageCount = pageCount;
	}
	public long getPageLineCount() {
		return pageLineCount;
	}
	public void setPageLineCount(long pageLineCount) {
		this.pageLineCount = pageLineCount;
	}
	public long getPageNo() {
		return pageNo;
	}
	public void setPageNo(long pageNo) {
		this.pageNo = pageNo;
	}
	public long getQueryPurpose() {
		return queryPurpose;
	}
	public void setQueryPurpose(long queryPurpose) {
		this.queryPurpose = queryPurpose;
	}
	public long getRecordCount() {
		return recordCount;
	}
	public void setRecordCount(long recordCount) {
		this.recordCount = recordCount;
	}
	public long getRowNumEnd() {
		return rowNumEnd;
	}
	public void setRowNumEnd(long rowNumEnd) {
		this.rowNumEnd = rowNumEnd;
	}
	public long getRowNumStart() {
		return rowNumStart;
	}
	public void setRowNumStart(long rowNumStart) {
		this.rowNumStart = rowNumStart;
	}
	public Timestamp getEffectDateFrom() {
		return effectDateFrom;
	}
	public void setEffectDateFrom(Timestamp effectDateFrom) {
		this.effectDateFrom = effectDateFrom;
	}
	public Timestamp getEffectDateTo() {
		return effectDateTo;
	}
	public void setEffectDateTo(Timestamp effectDateTo) {
		this.effectDateTo = effectDateTo;
	}
	public long getClientID() {
		return clientID;
	}
	public void setClientID(long clientID) {
		this.clientID = clientID;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public String getContractCode() {
		return contractCode;
	}
	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}
	public String getCheckUser() {
		return checkUser;
	}
	public void setCheckUser(String checkUser) {
		this.checkUser = checkUser;
	}
	public AssureChargeNoticeInfo getNoticeInfo() {
		return noticeInfo;
	}
	public void setNoticeInfo(AssureChargeNoticeInfo noticeInfo) {
		this.noticeInfo = noticeInfo;
	}


}
