package com.iss.itreasury.settlement.integration.client.info;

import java.io.Serializable;

public class SettTransInfo implements Serializable{
	private String transNo   = null;   //���㽻�׺�
	private double amount    = 0.00;   //���
	private long   officeID  = -1;     //�������´�
	private String moduleType = null;  //ģ������

	
	public String getTransNo() {
		return transNo;
	}
	public void setTransNo(String transNo) {
		this.transNo = transNo;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public long getOfficeID() {
		return officeID;
	}
	public void setOfficeID(long officeID) {
		this.officeID = officeID;
	}
	public String getModuleType() {
		return moduleType;
	}
	public void setModuleType(String moduleType) {
		this.moduleType = moduleType;
	}

}
