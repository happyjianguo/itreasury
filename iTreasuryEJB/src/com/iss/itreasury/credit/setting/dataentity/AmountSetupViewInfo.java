package com.iss.itreasury.credit.setting.dataentity;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.settlement.util.UtilOperation;
import com.iss.itreasury.util.ITreasuryBaseDataEntity;

public class AmountSetupViewInfo extends ITreasuryBaseDataEntity {

	private long id = -1;
	private String creditCode = "";
	private long clientId = -1;
	private String clientName = "";
	private long creditModel = -1;
	private long groupCreditId = -1;
	private String groupCreditCode = "";
	private Timestamp groupStartDate = null;
	private Timestamp groupEndDate = null;
	private long operationType = -1;
	private long operationSign = -1;
	private long amountFormId = -1;
	private double historyCreditAmount = 0.0;
	private double creditAmount = 0.0;
	private long controlType = -1;
	private Timestamp startDate = null;
	private Timestamp endDate = null;
	private long officeId = -1;
	private long currencyId = -1;
	private long inputUserId = -1;
	private String inputUserName = "";
	private Timestamp inputDate = null;
	private long state = -1;
	private long activeUserId = -1;
	private String activeUserName = "";
	private Timestamp activeDate = null;
	private long activeState = -1;
	
	private long lDesc = -1;
	private long lOrderParam = -1;
	private Collection subAmountSetupCollection = null;
	
	public AmountSetupInfo getAmountSetupInfo(){
		AmountSetupInfo asInfo = new AmountSetupInfo();
		asInfo.setId(id);
		asInfo.setClientId(clientId);
		asInfo.setCreditCode(creditCode);
		asInfo.setCreditModel(creditModel);
		asInfo.setGroupCreditId(groupCreditId);
		asInfo.setOperationType(operationType);
		asInfo.setOperationSign(operationSign);
		asInfo.setAmountFormId(amountFormId);
		asInfo.setHistoryCreditAmount(historyCreditAmount);
		asInfo.setCreditAmount(creditAmount);
		asInfo.setControlType(controlType);
		asInfo.setStartDate(startDate);
		asInfo.setEndDate(endDate);
		asInfo.setOfficeId(officeId);
		asInfo.setCurrencyId(currencyId);
		asInfo.setInputUserId(inputUserId);
		asInfo.setInputDate(inputDate);
		asInfo.setState(state);
		asInfo.setActiveUserId(activeUserId);
		asInfo.setActiveDate(activeDate);
		asInfo.setActiveState(activeState);
		return asInfo;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getCreditCode() {
		return creditCode;
	}
	public void setCreditCode(String creditCode) {
		this.creditCode = creditCode;
	}
	public long getClientId() {
		return clientId;
	}
	public void setClientId(long clientId) {
		this.clientId = clientId;
	}
	public long getCreditModel() {
		return creditModel;
	}
	public void setCreditModel(long creditModel) {
		this.creditModel = creditModel;
	}
	public long getGroupCreditId() {
		return groupCreditId;
	}
	public void setGroupCreditId(long groupCreditId) {
		this.groupCreditId = groupCreditId;
	}
	public String getGroupCreditCode() {
		return groupCreditCode;
	}
	public void setGroupCreditCode(String groupCreditCode) {
		this.groupCreditCode = groupCreditCode;
	}
	public long getOperationType() {
		return operationType;
	}
	public void setOperationType(long operationType) {
		this.operationType = operationType;
	}
	public long getOperationSign() {
		return operationSign;
	}
	public void setOperationSign(long operationSign) {
		this.operationSign = operationSign;
	}
	public long getAmountFormId() {
		return amountFormId;
	}
	public void setAmountFormId(long amountFormId) {
		this.amountFormId = amountFormId;
	}
	public double getHistoryCreditAmount() {
		return historyCreditAmount;
	}
	public void setHistoryCreditAmount(double historyCreditAmount) {
		this.historyCreditAmount = historyCreditAmount;
	}
	public double getCreditAmount() {
		return creditAmount;
	}
	public void setCreditAmount(double creditAmount) {
		this.creditAmount = creditAmount;
	}
	public long getControlType() {
		return controlType;
	}
	public void setControlType(long controlType) {
		this.controlType = controlType;
	}
	public Timestamp getStartDate() {
		return startDate;
	}
	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}
	public Timestamp getEndDate() {
		return endDate;
	}
	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}
	public long getOfficeId() {
		return officeId;
	}
	public void setOfficeId(long officeId) {
		this.officeId = officeId;
	}
	public long getCurrencyId() {
		return currencyId;
	}
	public void setCurrencyId(long currencyId) {
		this.currencyId = currencyId;
	}
	public long getInputUserId() {
		return inputUserId;
	}
	public void setInputUserId(long inputUserId) {
		this.inputUserId = inputUserId;
	}
	public Timestamp getInputDate() {
		return inputDate;
	}
	public void setInputDate(Timestamp inputDate) {
		this.inputDate = inputDate;
	}
	public long getState() {
		return state;
	}
	public void setState(long state) {
		this.state = state;
	}
	public long getActiveUserId() {
		return activeUserId;
	}
	public void setActiveUserId(long activeUserId) {
		this.activeUserId = activeUserId;
	}
	public Timestamp getActiveDate() {
		return activeDate;
	}
	public void setActiveDate(Timestamp activeDate) {
		this.activeDate = activeDate;
	}
	public long getActiveState() {
		return activeState;
	}
	public void setActiveState(long activeState) {
		this.activeState = activeState;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getInputUserName() {
		return inputUserName;
	}

	public void setInputUserName(String inputUserName) {
		this.inputUserName = inputUserName;
	}

	public String getActiveUserName() {
		return activeUserName;
	}

	public void setActiveUserName(String activeUserName) {
		this.activeUserName = activeUserName;
	}

	public Timestamp getGroupStartDate() {
		return groupStartDate;
	}

	public void setGroupStartDate(Timestamp groupStartDate) {
		this.groupStartDate = groupStartDate;
	}

	public Timestamp getGroupEndDate() {
		return groupEndDate;
	}

	public void setGroupEndDate(Timestamp groupEndDate) {
		this.groupEndDate = groupEndDate;
	}
	
	public long getLDesc() {
		return lDesc;
	}

	public void setLDesc(long desc) {
		lDesc = desc;
	}

	public long getLOrderParam() {
		return lOrderParam;
	}

	public void setLOrderParam(long orderParam) {
		lOrderParam = orderParam;
	}
	
	public double getSumCreditAmount() {
		double dSumCreditAmount = 0.0;
		if(operationSign >0){
			if(operationSign == LOANConstant.OperationSign.ADDITION){
				dSumCreditAmount = UtilOperation.Arith.add(historyCreditAmount , creditAmount);
			}
			else {
				dSumCreditAmount = UtilOperation.Arith.sub(historyCreditAmount , creditAmount);
			}
		}
		return dSumCreditAmount;
	}

	public Collection getSubAmountSetupCollection() {
		return subAmountSetupCollection;
	}

	public void setSubAmountSetupCollection(Collection subAmountSetupCollection) {
		this.subAmountSetupCollection = subAmountSetupCollection;
	}
}
