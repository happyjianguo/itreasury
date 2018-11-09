package com.iss.itreasury.settlement.accountsystem.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

public class CapitalTransferStatParam implements Serializable {
	private long accountSystemId; // �˻���ϵ��ϵID
	private Timestamp beginDate; // ��ѯ���ڴ�
	private Timestamp endDate; // ��ѯ������
	private long upAccountId; // �ϼ��˻�ID
	private String upAccountCode; // �ϼ��˻���
	private String upAccountName; // �ϼ��˻�����
	private long lOfficeID; // ���´�
	private long lCurrencyID; // ����

	public long getAccountSystemId() {
		return accountSystemId;
	}

	public void setAccountSystemId(long accountSystemId) {
		this.accountSystemId = accountSystemId;
	}

	public Timestamp getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Timestamp beginDate) {
		this.beginDate = beginDate;
	}

	public Timestamp getEndDate() {
		return endDate;
	}

	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}

	public long getUpAccountId() {
		return upAccountId;
	}

	public void setUpAccountId(long upAccountId) {
		this.upAccountId = upAccountId;
	}

	public String getUpAccountCode() {
		return upAccountCode;
	}

	public void setUpAccountCode(String upAccountCode) {
		this.upAccountCode = upAccountCode;
	}

	public String getUpAccountName() {
		return upAccountName;
	}

	public void setUpAccountName(String upAccountName) {
		this.upAccountName = upAccountName;
	}

	public long getLOfficeID() {
		return lOfficeID;
	}

	public void setLOfficeID(long officeID) {
		lOfficeID = officeID;
	}

	public long getLCurrencyID() {
		return lCurrencyID;
	}

	public void setLCurrencyID(long currencyID) {
		lCurrencyID = currencyID;
	}

}
