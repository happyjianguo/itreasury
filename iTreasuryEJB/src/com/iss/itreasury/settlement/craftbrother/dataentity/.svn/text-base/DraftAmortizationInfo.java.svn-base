package com.iss.itreasury.settlement.craftbrother.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;

import com.iss.itreasury.util.ITreasuryBaseDataEntity;

public class DraftAmortizationInfo extends ITreasuryBaseDataEntity implements Serializable {

	private long id = -1;
	private long AmortizationID = -1;  //转贴现摊销 id
	private long DraftID = -1;  //票据id
	private double AmortizationAmount = 0.00; //摊销金额
	private long nstatus = -1; //状态
	public long getAmortizationID() {
		return AmortizationID;
	}
	public void setAmortizationID(long amortizationID) {
		AmortizationID = amortizationID;
		putUsedField("AmortizationID",amortizationID );
	}
	public long getDraftID() {
		return DraftID;
	}
	public void setDraftID(long draftID) {
		DraftID = draftID;
		putUsedField("DraftID",draftID );
	}
	public double getAmortizationAmount() {
		return AmortizationAmount;
	}
	public void setAmortizationAmount(double amortizationAmount) {
		AmortizationAmount = amortizationAmount;
		putUsedField("AmortizationAmount",amortizationAmount );
	}
	public long getNstatus() {
		return nstatus;
	}
	public void setNstatus(long nstatus) {
		this.nstatus = nstatus;
		putUsedField("nstatus",nstatus );
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
		putUsedField("id",id );
	}

	
	
}
