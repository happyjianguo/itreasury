package com.iss.itreasury.ebank.fundplan.dataentity;

import java.sql.Timestamp;

import com.iss.itreasury.util.ITreasuryBaseDataEntity;

/*
 * ���info�Ƕ�Ӧ����ģ����ʽ�ƻ����ܲ�ѯ������Ҫ���ֶΣ�û�б��������ݿ���
 */
public class CapitalPlanGatherInfo extends ITreasuryBaseDataEntity{
     private long id = -1;
     private Timestamp datefrom = null;
     private Timestamp dateto = null ;
     private long capitalTotle = -1;
     private double AllmoneyIn = 0.0;
     private double AllmoneyOut = 0.0;
     private long officeId = -1;
     private long currencyId = -1;
     private long modelId = -1;
     
     
	public long getModelId() {
		return modelId;
	}
	public void setModelId(long modelId) {
		this.modelId = modelId;
	}
	public long getCurrencyId() {
		return currencyId;
	}
	public void setCurrencyId(long currencyId) {
		this.currencyId = currencyId;
	}
	public long getOfficeId() {
		return officeId;
	}
	public void setOfficeId(long officeId) {
		this.officeId = officeId;
	}
	public double getAllmoneyIn() {
		return AllmoneyIn;
	}
	public void setAllmoneyIn(double allmoneyIn) {
		AllmoneyIn = allmoneyIn;
	}
	public double getAllmoneyOut() {
		return AllmoneyOut;
	}
	public void setAllmoneyOut(double allmoneyOut) {
		AllmoneyOut = allmoneyOut;
	}
	public long getCapitalTotle() {
		return capitalTotle;
	}
	public void setCapitalTotle(long capitalTotle) {
		this.capitalTotle = capitalTotle;
	}
	public Timestamp getDatefrom() {
		return datefrom;
	}
	public void setDatefrom(Timestamp datefrom) {
		this.datefrom = datefrom;
	}
	public Timestamp getDateto() {
		return dateto;
	}
	public void setDateto(Timestamp dateto) {
		this.dateto = dateto;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
     
	

}
