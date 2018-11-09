package com.iss.itreasury.loan.integrationcredit.dataentity;

import java.sql.Timestamp;

import com.iss.itreasury.system.approval.dataentity.InutParameterInfo;
import com.iss.itreasury.util.ITreasuryBaseDataEntity;

public class CreditLimitChangeInfo  extends ITreasuryBaseDataEntity{

	private long id = -1;
	private long creditLimitID = -1; //授信额度ID
	private long changeTypeID = -1;  //变更类型（增加/减少）
	private double amount = 0;        //最初授信额度
	private double changeAmount = 0;  //变更金额
	private double endAmount = 0;   //变更后金额
	private long inputUserID = -1;  //录入人
	private Timestamp inputDate = null;  //录入日期
	private long statusID = -1;   
	//
	private String applyCode = ""; //业务编号
	private long officeID = -1; //办事处ID
	private long currencyID = -1; //币种ID
	private long clientID = -1; //客户ID
	private long creditModeID = -1;//授信方式ID
	private long creditTypeID = -1;//授信产品分类ID
	private Timestamp startDate = null; //授信起始日期 
	private Timestamp endDate =  null; //授信结束日期  
	
	private long activeUserID = -1;   //激活人
	private Timestamp checkDate = null;  //激活日期
	
	private int operate = -1;     //操作（1激活 2取消激活）
	private long activeStatusID = -1;
	private InutParameterInfo inutParameterInfo =null ;
	
	
	public InutParameterInfo getInutParameterInfo() {
		return inutParameterInfo;
	}
	public void setInutParameterInfo(InutParameterInfo inutParameterInfo) {
		this.inutParameterInfo = inutParameterInfo;
	}
	public double getChangeAmount() {
		return changeAmount;
	}
	public void setChangeAmount(double changeAmount) {
		this.changeAmount = changeAmount;
		putUsedField("changeAmount", changeAmount);
	}
	public long getChangeTypeID() {
		return changeTypeID;
	}
	public void setChangeTypeID(long changeTypeID) {
		this.changeTypeID = changeTypeID;
		putUsedField("changeTypeID", changeTypeID);
	}
	public Timestamp getCheckDate() {
		return checkDate;
	}
	public void setCheckDate(Timestamp checkDate) {
		this.checkDate = checkDate;
	}
	public long getCreditLimitID() {
		return creditLimitID;
	}
	public void setCreditLimitID(long creditLimitID) {
		this.creditLimitID = creditLimitID;
		putUsedField("creditLimitID", creditLimitID);
	}
	public double getEndAmount() {
		return endAmount;
	}
	public void setEndAmount(double endAmount) {
		this.endAmount = endAmount;
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
	public long getStatusID() {
		return statusID;
	}
	public void setStatusID(long statusID) {
		this.statusID = statusID;
		putUsedField("statusID", statusID);
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public int getOperate() {
		return operate;
	}
	public void setOperate(int operate) {
		this.operate = operate;
	}
	public long getActiveUserID() {
		return activeUserID;
	}
	public void setActiveUserID(long activeUserID) {
		this.activeUserID = activeUserID;
	}
	public String getApplyCode() {
		return applyCode;
	}
	public void setApplyCode(String applyCode) {
		this.applyCode = applyCode;
	}
	public long getClientID() {
		return clientID;
	}
	public void setClientID(long clientID) {
		this.clientID = clientID;
	}
	public long getCreditModeID() {
		return creditModeID;
	}
	public void setCreditModeID(long creditModeID) {
		this.creditModeID = creditModeID;
	}
	public long getCreditTypeID() {
		return creditTypeID;
	}
	public void setCreditTypeID(long creditTypeID) {
		this.creditTypeID = creditTypeID;
	}
	public long getCurrencyID() {
		return currencyID;
	}
	public void setCurrencyID(long currencyID) {
		this.currencyID = currencyID;
	}
	public Timestamp getEndDate() {
		return endDate;
	}
	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}
	public long getOfficeID() {
		return officeID;
	}
	public void setOfficeID(long officeID) {
		this.officeID = officeID;
	}
	public Timestamp getStartDate() {
		return startDate;
	}
	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}
	public long getActiveStatusID() {
		return activeStatusID;
	}
	public void setActiveStatusID(long activeStatusID) {
		this.activeStatusID = activeStatusID;
	}
	
	
	

}
