/*
 * Created on 2007-4-12
 *
 * Title:				iTreasury
 * @author             	刘琰 
 * Company:             iSoftStone
 * @version				iTreasury3.2新增
 * Description:         产品化3.2在结算,网银新增审批流,且审批流引擎采用北京研发中心的产品iNut。
 * 						审批流每审批一笔业务，在该表内新增一条记录，记录业务id与审批流实例id对应关系
 */
package com.iss.itreasury.system.approval.dataentity;

import com.iss.itreasury.util.ITreasuryBaseDataEntity;

public class InutApprovalRecordInfo extends ITreasuryBaseDataEntity 
{
	private long id = -1;          			//主键
	private long officeID = -1;				//办事处ID
    private long currencyID = -1;			//币种ID	
    private long moduleID = -1;				//模块标示 
	private long transTypeID = -1;			//业务类型标识 
	private long actionID = -1;				//操作标识
	private String transID = "";			//交易记录标识
	private long approvalEntryID = -1;   	//审批实例id
	private String linkUrl = "";			//审批链接地址
	private long lastAppUserID = -1;		//最后一级审批人
	private long statusID = -1;				//状态
	
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
	public long getLastAppUserID() 
	{
		return lastAppUserID;
	}
	public void setLastAppUserID(long lastAppUserID) 
	{
		this.lastAppUserID = lastAppUserID;
		putUsedField("lastAppUserID", lastAppUserID);
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
