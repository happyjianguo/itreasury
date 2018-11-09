package com.iss.itreasury.credit.setting.dataentity;

import java.sql.Timestamp;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.settlement.util.UtilOperation;
import com.iss.itreasury.util.ITreasuryBaseDataEntity;

public class SubAmountSetupInfo extends ITreasuryBaseDataEntity {

	private long id = -1;
	private long amountSetupId = -1;
	private long creditType = -1;
	private long creditShare = -1;
	private long operationSign = -1;
	private double historyCreditAmount = 0.0;
	private double creditAmount = 0.0;
	private double excessPercent = 0.0;
	private long officeId = -1;
	private long currencyId = -1;
	private long inputUserId = -1;
	private Timestamp inputDate = null;
	private long state = -1;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
		putUsedField("id", id);
	}
	public long getAmountSetupId() {
		return amountSetupId;
	}
	public void setAmountSetupId(long amountSetupId) {
		this.amountSetupId = amountSetupId;
		putUsedField("amountSetupId", amountSetupId);
	}
	public long getCreditType() {
		return creditType;
	}
	public void setCreditType(long creditType) {
		this.creditType = creditType;
		putUsedField("creditType", creditType);
	}
	public long getCreditShare() {
		return creditShare;
	}
	public void setCreditShare(long creditShare) {
		this.creditShare = creditShare;
		putUsedField("creditShare", creditShare);
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
	public double getExcessPercent() {
		return excessPercent;
	}
	public void setExcessPercent(double excessPercent) {
		this.excessPercent = excessPercent;
		putUsedField("excessPercent", excessPercent);
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
