package com.iss.itreasury.craftbrother.transferloancontract.transfernotice.dataentity;

import com.iss.itreasury.util.ITreasuryBaseDataEntity;

public class NoticeQueryInfo extends ITreasuryBaseDataEntity
{
	private long noticeTypeId        = -1;      //通知单类型
	private long cracontractIdFrom   = -1;      //转让合同ID 由
	private long cracontractIdTo     = -1;      //转让合同ID 到 
	private double startAmount       = 0.00;    //转让本金 由
	private double endAmount         = 0.00;    //转让本金 到
	private String startInputDate = "";    //录入日期 由
	private String endInputDate   = "";    //录入日期 到
	private long isurrogatePay       = -1;      //是否代理收款
	private long currencyID          = -1;      //币种
    private long officeID            = -1;      //办事处
	private long inputUserId = -1;				//录入人
	private long statusId = -1;					//状态
	
	private String strCraContractNoTo = "";     //转让合同到
	private String strCraContractNoFrom = "";   //转让合同从
	public long getCracontractIdFrom() {
		return cracontractIdFrom;
	}
	public void setCracontractIdFrom(long cracontractIdFrom) {
		this.cracontractIdFrom = cracontractIdFrom;
	}
	public long getCracontractIdTo() {
		return cracontractIdTo;
	}
	public void setCracontractIdTo(long cracontractIdTo) {
		this.cracontractIdTo = cracontractIdTo;
	}
	public double getEndAmount() {
		return endAmount;
	}
	public void setEndAmount(double endAmount) {
		this.endAmount = endAmount;
	}	
	public long getIsurrogatePay() {
		return isurrogatePay;
	}
	public void setIsurrogatePay(long isurrogatePay) {
		this.isurrogatePay = isurrogatePay;
	}
	public long getNoticeTypeId() {
		return noticeTypeId;
	}
	public void setNoticeTypeId(long noticeTypeId) {
		this.noticeTypeId = noticeTypeId;
	}
	public double getStartAmount() {
		return startAmount;
	}
	public void setStartAmount(double startAmount) {
		this.startAmount = startAmount;
	}
	public long getCurrencyID() {
		return currencyID;
	}
	public void setCurrencyID(long currencyID) {
		this.currencyID = currencyID;
	}
	public long getOfficeID() {
		return officeID;
	}
	public void setOfficeID(long officeID) {
		this.officeID = officeID;
	}
	public long getId() {
		// TODO Auto-generated method stub
		return 0;
	}
	public void setId(long id) {
		// TODO Auto-generated method stub
		
	}
	public long getInputUserId() {
		return inputUserId;
	}
	public void setInputUserId(long inputUserId) {
		this.inputUserId = inputUserId;
	}
	public long getStatusId() {
		return statusId;
	}
	public void setStatusId(long statusId) {
		this.statusId = statusId;
	}
	public String getEndInputDate() {
		return endInputDate;
	}
	public void setEndInputDate(String endInputDate) {
		this.endInputDate = endInputDate;
	}
	public String getStartInputDate() {
		return startInputDate;
	}
	public void setStartInputDate(String startInputDate) {
		this.startInputDate = startInputDate;
	}
	public String getStrCraContractNoFrom() {
		return strCraContractNoFrom;
	}
	public void setStrCraContractNoFrom(String strCraContractNoFrom) {
		this.strCraContractNoFrom = strCraContractNoFrom;
	}
	public String getStrCraContractNoTo() {
		return strCraContractNoTo;
	}
	public void setStrCraContractNoTo(String strCraContractNoTo) {
		this.strCraContractNoTo = strCraContractNoTo;
	}
	

}
