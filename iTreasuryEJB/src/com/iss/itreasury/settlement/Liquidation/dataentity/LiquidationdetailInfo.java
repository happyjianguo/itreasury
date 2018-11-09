package com.iss.itreasury.settlement.Liquidation.dataentity;

import java.sql.Timestamp;

import com.iss.itreasury.settlement.base.SettlementBaseDataEntity;

public class LiquidationdetailInfo extends SettlementBaseDataEntity{
	
	private long nOfficeid = -1;
	private long nCurrencyid = -1;//����
	private long nLiquidationid = -1;//����ID
	private String sDebit = null;//�跽��Ϣ
	private String sCredit = null;//������Ϣ
	private long nStatusid = -1;//״̬
	public long getNOfficeid() {
		return nOfficeid;
	}
	public void setNOfficeid(long officeid) {
		nOfficeid = officeid;
		putUsedField("nOfficeid", officeid);
	}
	public long getNCurrencyid() {
		return nCurrencyid;
	}
	public void setNCurrencyid(long currencyid) {
		nCurrencyid = currencyid;
		putUsedField("nCurrencyid", currencyid);
	}
	public long getLiquidationid() {
		return nLiquidationid;
	}
	public void setLiquidationid(long liquidationid) {
		nLiquidationid = liquidationid;
		putUsedField("nLiquidationid", liquidationid);
	}
	public String getSDebit() {
		return sDebit;
	}
	public void setSDebit(String debit) {
		sDebit = debit;
		putUsedField("sDebit", debit);
	}
	public String getSCredit() {
		return sCredit;
	}
	public void setSCredit(String credit) {
		sCredit = credit;
		putUsedField("sCredit", credit);
	}
	public long getNStatusid() {
		return nStatusid;
	}
	public void setNStatusid(long statusid) {
		nStatusid = statusid;
		putUsedField("nStatusid", statusid);
	}
}
