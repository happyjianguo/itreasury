package com.iss.itreasury.ebank.approval.dataentity;

import com.iss.itreasury.util.ITreasuryBaseDataEntity;

public class InutApprovalRecordInfo extends ITreasuryBaseDataEntity {
  
	private long id = -1;          			//����
	private long officeID = -1;				//���´�ID
    private long currencyID = -1;			//����ID	
    private long moduleID = -1;				//ģ���ʾ 
	private long transTypeID = -1;			//ҵ�����ͱ�ʶ 
	private long actionID = -1;				//������ʶ
	private String transID = "";			//���׼�¼��ʶ
	private long approvalEntryID = -1;   	//����ʵ��id
	private String linkUrl = "";			//�������ӵ�ַ
	private long clientID = -1;             //�ͻ�id
	private long lastAppUserID = -1;		//���һ��������
	private long statusID = -1;				//״̬
	private long nextLevel = -1;			//��һ����������
	
	public long getId() 
	{
		return id;
	}
	public void setId(long id) 
	{
		this.id = id;
		putUsedField("id", id);
	}
	public long getActionID() 
	{
		return actionID;
	}
	public void setActionID(long actionID) 
	{
		this.actionID = actionID;
		putUsedField("actionID", actionID);
	}
	public long getCurrencyID() 
	{
		return currencyID;
	}
	public void setCurrencyID(long currencyID) 
	{
		this.currencyID = currencyID;
		putUsedField("currencyID", currencyID);
	}
	public long getModuleID() 
	{
		return moduleID;
	}
	public void setModuleID(long moduleID) 
	{
		this.moduleID = moduleID;
		putUsedField("moduleID", moduleID);
	}
	public long getOfficeID() 
	{
		return officeID;
	}
	public void setOfficeID(long officeID) 
	{
		this.officeID = officeID;
		putUsedField("officeID", officeID);
	}
	public long getTransTypeID() 
	{
		return transTypeID;
	}
	public void setTransTypeID(long transTypeID) 
	{
		this.transTypeID = transTypeID;
		putUsedField("transTypeID", transTypeID);
	}
	public String getTransID() 
	{
		return transID;
	}
	public void setTransID(String transID) 
	{
		this.transID = transID;
		putUsedField("transID", transID);
	}
	public long getApprovalEntryID() 
	{
		return approvalEntryID;
	}
	public void setApprovalEntryID(long approvalEntryID) 
	{
		this.approvalEntryID = approvalEntryID;
		putUsedField("approvalEntryID", approvalEntryID);
	}
	public String getLinkUrl() 
	{
		return linkUrl;
	}
	public void setLinkUrl(String linkUrl) 
	{
		this.linkUrl = linkUrl;
		putUsedField("linkUrl", linkUrl);
	}
	public long getClientID() {
		return clientID;
	}
	public void setClientID(long clientID) {
		this.clientID = clientID;
		putUsedField("clientID", clientID);
	}
	public long getLastAppUserID() 
	{
		return lastAppUserID;
	}
	public void setLastAppUserID(long lastAppUserID) 
	{
		this.lastAppUserID = lastAppUserID;
		putUsedField("lastAppUserID", lastAppUserID);
	}
	public long getNextLevel() 
	{
		return nextLevel;
	}
	public void setNextLevel(long nextLevel) 
	{
		this.nextLevel = nextLevel;
		putUsedField("nextLevel", nextLevel);
	}
	public long getStatusID() 
	{
		return statusID;
	}
	public void setStatusID(long statusID) 
	{
		this.statusID = statusID;
		putUsedField("statusID", statusID);
	}
	
}