package com.iss.itreasury.codingrule.dataentity;

import java.sql.Timestamp;

import com.iss.itreasury.util.ITreasuryBaseDataEntity;

public class SerialnumberDetailInfo extends ITreasuryBaseDataEntity{
	private long id= -1;  //ID
	private long serialnumberid = -1; //��ˮ������ID
	private long serialno = -1; //��ˮ��
	private Timestamp serialnodate = null; //��ˮ��ʹ��ʱ��
	private long  contractid = -1; //��ͬ�ţ����֪ͨ��ר�ã�
	private long currencyid = -1;  //����ID
	public long getCurrencyid() {
		return currencyid;
	}

	public void setCurrencyid(long currencyid) {
		this.currencyid = currencyid;
		putUsedField("currencyid", currencyid);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
		putUsedField("id", id);
	}

	public long getContractid() {
		return contractid;
	}

	public void setContractid(long contractid) {
		this.contractid = contractid;
		putUsedField("contractid", contractid);
	}

	public long getSerialno() {
		return serialno;
	}

	public void setSerialno(long serialno) {
		this.serialno = serialno;
		putUsedField("serialno", serialno);
	}

	public Timestamp getSerialnodate() {
		return serialnodate;
	}

	public void setSerialnodate(Timestamp serialnodate) {
		this.serialnodate = serialnodate;
		putUsedField("serialnodate", serialnodate);
	}

	public long getSerialnumberid() {
		return serialnumberid;
	}

	public void setSerialnumberid(long serialnumberid) {
		this.serialnumberid = serialnumberid;
		putUsedField("serialnumberid", serialnumberid);
	}

}
