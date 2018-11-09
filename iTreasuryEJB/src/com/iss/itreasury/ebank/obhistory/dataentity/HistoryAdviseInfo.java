package com.iss.itreasury.ebank.obhistory.dataentity;

import java.sql.Timestamp;

import com.iss.itreasury.util.ITreasuryBaseDataEntity;

public class HistoryAdviseInfo extends ITreasuryBaseDataEntity{	
	private long id = -1;         //����
	private String operator="";//������
	private Timestamp opTime=null;//����ʱ��
	private String action="";//����-����
	private String advise="";//���
	private long entryID=-1;//����ʵ����
	private String actionKey="";//����-��ֵ
	private long statusID=-1;//״̬
	
	public long getEntryID() {
		return entryID;
	}
	public void setEntryID(long entryID) {
		this.entryID = entryID;
		putUsedField("ENTRY_ID", entryID);
	}
	public String getActionKey() {
		return actionKey;
	}
	public void setActionKey(String actionKey) {
		this.actionKey = actionKey;
		putUsedField("ACTION_KEY", actionKey);
	}
	public long getStatusID() {
		return statusID;
	}
	public void setStatusID(long statusID) {
		this.statusID = statusID;
		putUsedField("STATUSID", statusID);
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
		putUsedField("CALLER", operator);
	}
	public Timestamp getOpTime() {
		return opTime;
	}
	public void setOpTime(Timestamp opTime) {
		this.opTime = opTime;
		putUsedField("EXECUTE_DATE", opTime);
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
		putUsedField("ACTION_NAME", action);
	}
	public String getAdvise() {
		return advise;
	}
	public void setAdvise(String advise) {
		this.advise = advise;
		putUsedField("ADVISE_VALUE", advise);
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
		putUsedField("id", id);
	}	
	
}
