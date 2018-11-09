package com.iss.itreasury.loan.query.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;

public class QuestCredenceInfo extends Object implements Serializable {
	


	// ��ͬ��ſ�ʼ
	private String minContractCode = "";
	// ��ͬ��Ž���
	private String maxContractCode = "";

	// ���뵥λ��ʼ
	private long minborrowClientID = -1;
	// ���뵥λ����
	private long maxborrowClientID = -1;

	// �������ڿ�ʼ
	private Timestamp minDiscountDate = null;
	// ��������ֹ
	private Timestamp maxDiscountDate = null;

	// ����ƾ֤¼�����ڿ�ʼ
	private Timestamp minDisccountCredenceInputDate = null;
	// ����ƾ֤¼�����ڿ�ʼ
	private Timestamp maxDisccountCredenceInputDate = null;
	
	//���ƾ֤״̬
	private String credenceStatusIDs="";
	
	//���´�
	private long officeID = -1;
	
	//����
	private long currencyID = -1;

	//��ѯ����ƾ֤ʱ�ĺ�ͬID
	private long contractID=-1;  
	
	
	
	public long getContractID() {
		return contractID;
	}

	public void setContractID(long contractID) {
		this.contractID = contractID;
	}

	public String getMinContractCode() {
		return minContractCode;
	}

	public void setMinContractCode(String minContractCode) {
		this.minContractCode = minContractCode;
	}

	public String getMaxContractCode() {
		return maxContractCode;
	}

	public void setMaxContractCode(String maxContractCode) {
		this.maxContractCode = maxContractCode;
	}

	public long getMinborrowClientID() {
		return minborrowClientID;
	}

	public void setMinborrowClientID(long minborrowClientID) {
		this.minborrowClientID = minborrowClientID;
	}

	public long getMaxborrowClientID() {
		return maxborrowClientID;
	}

	public void setMaxborrowClientID(long maxborrowClientID) {
		this.maxborrowClientID = maxborrowClientID;
	}

	public Timestamp getMinDiscountDate() {
		return minDiscountDate;
	}

	public void setMinDiscountDate(Timestamp minDiscountDate) {
		this.minDiscountDate = minDiscountDate;
	}

	public Timestamp getMaxDiscountDate() {
		return maxDiscountDate;
	}

	public void setMaxDiscountDate(Timestamp maxDiscountDate) {
		this.maxDiscountDate = maxDiscountDate;
	}

	public Timestamp getMinDisccountCredenceInputDate() {
		return minDisccountCredenceInputDate;
	}

	public void setMinDisccountCredenceInputDate(Timestamp minDisccountCredenceInputDate) {
		this.minDisccountCredenceInputDate = minDisccountCredenceInputDate;
	}

	public Timestamp getMaxDisccountCredenceInputDate() {
		return maxDisccountCredenceInputDate;
	}

	public void setMaxDisccountCredenceInputDate(Timestamp maxDisccountCredenceInputDate) {
		this.maxDisccountCredenceInputDate = maxDisccountCredenceInputDate;
	}

	public String getCredenceStatusIDs() {
		return credenceStatusIDs;
	}

	public void setCredenceStatusIDs(String credenceStatusIDs) {
		this.credenceStatusIDs = credenceStatusIDs;
	}

	public long getOfficeID() {
		return officeID;
	}

	public void setOfficeID(long officeID) {
		this.officeID = officeID;
	}

	public long getCurrencyID() {
		return currencyID;
	}

	public void setCurrencyID(long currencyID) {
		this.currencyID = currencyID;
	}



}
