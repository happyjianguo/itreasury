package com.iss.itreasury.settlement.setting.dataentity;

import java.sql.Timestamp;

import com.iss.itreasury.settlement.base.SettlementBaseDataEntity;

public class SettOfficeTimeInfo extends SettlementBaseDataEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -9000272713403087917L;
	
	long nOfficeId = -1 ;					//���´�
	long nCurrencyId = -1;					//����
	long nSystemStatusId = -1;				//ϵͳ״̬
	Timestamp dtOpenDate = null;			//����ʱ�䣬������
	Timestamp dtCalInterest = null;			//������
	long nStatusId = -1;					//״̬
	long nCloseSystemStatusId = -1;			//
	String sSystemStatusDesc = "";			//ϵͳ״̬
	Timestamp dtApplyTime = null;			//�ύ����ʱ�䣬������ʱ����
	
	public Timestamp getDtApplyTime() {
		return dtApplyTime;
	}
	public void setDtApplyTime(Timestamp dtApplyTime) {
		this.dtApplyTime = dtApplyTime;
		putUsedField("dtApplyTime", dtApplyTime);
	}
	public Timestamp getDtCalInterest() {
		return dtCalInterest;
	}
	public void setDtCalInterest(Timestamp dtCalInterest) {
		this.dtCalInterest = dtCalInterest;
		putUsedField("dtCalInterest", dtCalInterest);
	}
	public Timestamp getDtOpenDate() {
		return dtOpenDate;
	}
	public void setDtOpenDate(Timestamp dtOpenDate) {
		this.dtOpenDate = dtOpenDate;
		putUsedField("dtOpenDate", dtOpenDate);
	}
	public long getNCloseSystemStatusId() {
		return nCloseSystemStatusId;
	}
	public void setNCloseSystemStatusId(long closeSystemStatusId) {
		nCloseSystemStatusId = closeSystemStatusId;
		putUsedField("nCloseSystemStatusId", closeSystemStatusId);
	}
	public long getNCurrencyId() {
		return nCurrencyId;
	}
	public void setNCurrencyId(long currencyId) {
		nCurrencyId = currencyId;
		putUsedField("nCurrencyId", currencyId);
	}
	public long getNOfficeId() {
		return nOfficeId;
	}
	public void setNOfficeId(long officeId) {
		nOfficeId = officeId;
		putUsedField("nOfficeId", officeId);
	}
	public long getNStatusId() {
		return nStatusId;
	}
	public void setNStatusId(long statusId) {
		nStatusId = statusId;
		putUsedField("nStatusId", statusId);
	}
	public long getNSystemStatusId() {
		return nSystemStatusId;
	}
	public void setNSystemStatusId(long systemStatusId) {
		nSystemStatusId = systemStatusId;
		putUsedField("nSystemStatusId", systemStatusId);
	}
	public String getSSystemStatusDesc() {
		return sSystemStatusDesc;
	}
	public void setSSystemStatusDesc(String systemStatusDesc) {
		sSystemStatusDesc = systemStatusDesc;
		putUsedField("sSystemStatusDesc", systemStatusDesc);
	}
}
