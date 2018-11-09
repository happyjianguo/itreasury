package com.iss.itreasury.settlement.transfinancialmargin.dataentity;

import com.iss.itreasury.settlement.base.SettlementBaseDataEntity;

public class PayAmountForRecogInfo extends SettlementBaseDataEntity
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7297987800888795484L;
	
	private long    id               = -1;     //ID
	private long    nAccountID       = -1;     //主账户ID
	private long    nSubAccountID    = -1;     //子账户ID
	private double  payAmount        = 0.0;    //扣减金额
	private long    nContractID      = -1;     //合同ID
	private long    nStatusID        = -1;     //状态ID 1,已扣减 0，已扣回
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
		putUsedField("ID", id);
	}
	public long getNAccountID() {
		return nAccountID;
	}
	public void setNAccountID(long accountID) {
		nAccountID = accountID;
		putUsedField("nAccountID", accountID);
	}
	public long getNSubAccountID() {
		return nSubAccountID;
	}
	public void setNSubAccountID(long subAccountID) {
		nSubAccountID = subAccountID;
		putUsedField("nSubAccountID", subAccountID);
	}
	public double getPayAmount() {
		return payAmount;
	}
	public void setPayAmount(double payAmount) {
		this.payAmount = payAmount;
		putUsedField("payAmount", payAmount);
	}
	public long getNContractID() {
		return nContractID;
	}
	public void setNContractID(long contractID) {
		nContractID = contractID;
		putUsedField("nContractID", contractID);
	}
	public long getNStatusID() {
		return nStatusID;
	}
	public void setNStatusID(long statusID) {
		nStatusID = statusID;
		putUsedField("nStatusID", statusID);
	}
	
}	
