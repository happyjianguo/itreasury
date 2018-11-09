package com.iss.itreasury.credit.setting.dataentity;

import java.sql.Timestamp;

import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.settlement.util.UtilOperation;
import com.iss.itreasury.system.approval.dataentity.InutParameterInfo;
import com.iss.itreasury.util.ITreasuryBaseDataEntity;

public class AmountSetupInfo extends ITreasuryBaseDataEntity {

	private long id = -1;
	private String creditCode = "";
	private long clientId = -1;
	private long creditModel = -1;
	private long groupCreditId = -1;
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
	private Timestamp inputDate = null;
	private long state = -1;
	private long activeUserId = -1;
	private Timestamp activeDate = null;
	private long activeState = -1;
	
	private InutParameterInfo inutParameterInfo = null ;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
		putUsedField("id", id);
	}

	public String getCreditCode() {
		return creditCode;
	}

	public void setCreditCode(String creditCode) {
		this.creditCode = creditCode;
		putUsedField("creditCode", creditCode);
	}

	public long getClientId() {
		return clientId;
	}

	public void setClientId(long clientId) {
		this.clientId = clientId;
		putUsedField("clientId", clientId);
	}

	public long getCreditModel() {
		return creditModel;
	}

	public void setCreditModel(long creditModel) {
		this.creditModel = creditModel;
		putUsedField("creditModel", creditModel);
	}

	public long getGroupCreditId() {
		return groupCreditId;
	}

	public void setGroupCreditId(long groupCreditId) {
		this.groupCreditId = groupCreditId;
		putUsedField("groupCreditId", groupCreditId);
	}

	public long getOperationType() {
		return operationType;
	}

	public void setOperationType(long operationType) {
		this.operationType = operationType;
		putUsedField("operationType", operationType);
	}

	public long getOperationSign() {
		return operationSign;
	}

	public void setOperationSign(long operationSign) {
		this.operationSign = operationSign;
		putUsedField("operationSign", operationSign);
	}
	

	public double getHistoryCreditAmount() {
		return historyCreditAmount;
	}

	public void setHistoryCreditAmount(double historyCreditAmount) {
		this.historyCreditAmount = historyCreditAmount;
		putUsedField("historyCreditAmount", historyCreditAmount);
	}

	public double getCreditAmount() {
		return creditAmount;
	}

	public void setCreditAmount(double creditAmount) {
		this.creditAmount = creditAmount;
		putUsedField("creditAmount", creditAmount);
	}

	public long getControlType() {
		return controlType;
	}

	public void setControlType(long controlType) {
		this.controlType = controlType;
		putUsedField("controlType", controlType);
	}

	public Timestamp getStartDate() {
		return startDate;
	}

	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
		putUsedField("startDate", startDate);
	}

	public Timestamp getEndDate() {
		return endDate;
	}

	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
		putUsedField("endDate", endDate);
	}

	public long getOfficeId() {
		return officeId;
	}

	public void setOfficeId(long officeId) {
		this.officeId = officeId;
		putUsedField("officeId", officeId);
	}

	public long getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(long currencyId) {
		this.currencyId = currencyId;
		putUsedField("currencyId", currencyId);
	}

	public long getInputUserId() {
		return inputUserId;
	}

	public void setInputUserId(long inputUserId) {
		this.inputUserId = inputUserId;
		putUsedField("inputUserId", inputUserId);
	}

	public Timestamp getInputDate() {
		return inputDate;
	}

	public void setInputDate(Timestamp inputDate) {
		this.inputDate = inputDate;
		putUsedField("inputDate", inputDate);
	}

	public long getState() {
		return state;
	}

	public void setState(long state) {
		this.state = state;
		putUsedField("state", state);
	}

	public long getActiveUserId() {
		return activeUserId;
	}

	public void setActiveUserId(long activeUserId) {
		this.activeUserId = activeUserId;
		putUsedField("activeUserId", activeUserId);
	}

	public Timestamp getActiveDate() {
		return activeDate;
	}

	public void setActiveDate(Timestamp activeDate) {
		this.activeDate = activeDate;
		putUsedField("activeDate", activeDate);
	}

	public long getActiveState() {
		return activeState;
	}

	public void setActiveState(long activeState) {
		this.activeState = activeState;
		putUsedField("activeState", activeState);
	}

	public long getAmountFormId() {
		return amountFormId;
	}

	public void setAmountFormId(long amountFormId) {
		this.amountFormId = amountFormId;
		putUsedField("amountFormId", amountFormId);
	}

	public InutParameterInfo getInutParameterInfo() {
		return inutParameterInfo;
	}

	public void setInutParameterInfo(InutParameterInfo inutParameterInfo) {
		this.inutParameterInfo = inutParameterInfo;
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
}
