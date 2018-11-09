package com.iss.itreasury.codingrule.dataentity;

import java.sql.Timestamp;

import com.iss.itreasury.util.ITreasuryBaseDataEntity;

public class SerialnumberInfo extends ITreasuryBaseDataEntity{
	private long id= -1;  //ID
	private String name = ""; //��ˮ������
	//private long rulerelationid = -1; // ����id
	private long officeid = -1; // ���´�ID
	private long iscurrency = -1; //��ˮ���Ƿ����ֱ���
	private long iscontract = -1; //��ˮ���Ƿ����ֺ�ͬ(�ſ�֪ͨ��ר��)
	private long  statusid = -1; //״̬
	private long periodtype = -1; //��ˮ��ѭ�����ڣ�1���ꣻ2���£�3���գ�
	
	//������Ϣ(�Ǳ��ֶ�),�������ˮ������ݱ��ֺͺ�ͬ����,���ǰ̨�����������Գ�ʼ��
	private long currencyID = -1;
	private long contractID = -1;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
		putUsedField("id", id);
	}



	public long getOfficeid() {
		return officeid;
	}

	public void setOfficeid(long officeid) {
		this.officeid = officeid;
		putUsedField("officeid", officeid);
	}

	public long getIscontract() {
		return iscontract;
	}

	public void setIscontract(long iscontract) {
		this.iscontract = iscontract;
		putUsedField("iscontract", iscontract);
	}

	public long getIscurrency() {
		return iscurrency;
	}

	public void setIscurrency(long iscurrency) {
		this.iscurrency = iscurrency;
		putUsedField("iscurrency", iscurrency);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		putUsedField("name", name);
	}

	public long getPeriodtype() {
		return periodtype;
	}

	public void setPeriodtype(long periodtype) {
		this.periodtype = periodtype;
		putUsedField("periodtype", periodtype);
	}

	public long getStatusid() {
		return statusid;
	}

	public void setStatusid(long statusid) {
		this.statusid = statusid;
		putUsedField("statusid", statusid);
	}
	
	//������Ϣ��set get����
	public long getContractID() {
		return contractID;
	}

	public void setContractID(long contractID) {
		this.contractID = contractID;
	}

	public long getCurrencyID() {
		return currencyID;
	}

	public void setCurrencyID(long currencyID) {
		this.currencyID = currencyID;
	}

	
}
