package com.iss.itreasury.craftbrother.mywork.dataentity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;

import com.iss.itreasury.system.approval.dataentity.InutApprovalWorkInfo;

public interface CraMyWorkInterface extends Serializable {
	
	//用于区分实体类型
	public long getCraMyWorkInterfaceType();
	
	
	//审批关联信息及查询条件信息
	public long getActionID();

	public void setActionID(long actionID);

	public String getActionName();

	public void setActionName(String actionName);

	public long getApprovalEntryID();

	public void setApprovalEntryID(long approvalEntryID);

	public long getCurrencyID();

	public void setCurrencyID(long currencyID);

	public long getId();

	public void setId(long id);

	public String getLinkUrl();

	public void setLinkUrl(String linkUrl);

	public long getModuleID();

	public void setModuleID(long moduleID);

	public long getOfficeID();

	public void setOfficeID(long officeID);

	public long getQueryPurpose();

	public void setQueryPurpose(long queryPurpose);

	public long getStatusID();

	public void setStatusID(long statusID);

	public long getUserID();

	public void setUserID(long userID);
	
	
	//辅助信息
	
	public long getDesc();

	public void setDesc(long desc);
	
	public long getOrderField();

	public void setOrderField(long orderField);
	
	//审批相关新增
	public long getActionCode();

	public void setActionCode(long actionCode);

	public Date getApprovalStartDate();

	public void setApprovalStartDate(Date approvalStartDate);

	public long getEntryID();

	public void setEntryID(long entryID);

	public String getEntryName();

	public void setEntryName(String entryName);

	public long getStepCode();

	public void setStepCode(long stepCode);

	public long getStepID();

	public void setStepID(long stepID);

	public String getStepName();

	public void setStepName(String stepName);

	public long getTaskID();

	public void setTaskID(long taskID);

	public String getWfDefineName();

	public void setWfDefineName(String wfDefineName);

	public String getApprovalEntryIDs();

	public void setApprovalEntryIDs(String approvalEntryIDs);

	public Date getDueDate();

	public void setDueDate(Date dueDate);

	public InutApprovalWorkInfo getInutWorkInfo();

	public void setInutWorkInfo(InutApprovalWorkInfo inutWorkInfo);

	public String getOwner();

	public void setOwner(String owner);

	public String getStatus();

	public void setStatus(String status);

	public HashMap getWorkList();

	public void setWorkList(HashMap workList);
}
