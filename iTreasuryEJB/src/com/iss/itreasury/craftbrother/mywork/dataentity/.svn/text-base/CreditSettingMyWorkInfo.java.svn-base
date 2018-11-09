package com.iss.itreasury.craftbrother.mywork.dataentity;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import com.iss.itreasury.system.approval.dataentity.InutApprovalWorkInfo;
import com.iss.itreasury.util.Constant;

public class CreditSettingMyWorkInfo implements CraMyWorkInterface {

	// 审批关联信息及查询条件信息
	private long id = -1; // 记录ID

	private long officeID = -1; // 办事处ID

	private long currencyID = -1; // 币种ID

	private long moduleID = Constant.ModuleType.CRAFTBROTHER; // 模块标示

	private long actionID = -1; // 操作标识

	private String actionName = "";// 操作名称

	private long approvalEntryID = -1; // 审批实例id

	private String linkUrl = ""; // 审批链接地址

	private long userID = -1; // 待办人
	
	private long statusID = -1; // 记录状态

	private long queryPurpose = -1; // 查询目的（查询待办、已办、办结）
	
	// 授信设置信息
	private long csID = -1;//授信记录ID
	private long creditClientID = -1;//授信方ID
	private String creditClientCode = "";//授信方客户编号
	private String creditClientName = "";//授信方客户名称
	private long creditedClientID = -1;//被授信方ID
	private String creditedClientCode = "";//被授信方客户编号
	private String creditedClientName = "";//被授信方客户名称
	private double amount = 0.00;//授信额度
	private long transactionType = -1;//交易类型
	private long creditDirection = -1;//授信方向 1-财务公司对同行授信 2-同行对财务公司及成员单位授信
	private Timestamp startDate = null;//授信开始时间
	private Timestamp endDate = null;//授信结束时间
	private long term = -1;//授信期限
	private String remark = "";//备注
	private long inputUserID = -1;//录入人ID
	private String inputUser = "";//录入人
	private Timestamp inputDate = null;//录入时间

	
	// 辅助信息
	private long desc = -1;

	private long orderField = -1;

	// 审批相关新增
	private long taskID = -1; // 任务id

	private long entryID = -1; // 审批实例id

	private long stepID = -1; // 审批环节id

	private long stepCode = -1; // 审批环节code

	private long actionCode = -1; // 审批动作code

	private String entryName = ""; // 审批实例名称

	private String wfDefineName = ""; // 审批流程名称

	private String stepName = ""; // 审批环节名称

	private Date approvalStartDate = null; // 审批开始时间

	private Date dueDate = null; // 最迟完成时间

	private String owner = ""; // 上一级审批人

	private String status = ""; // 状态

	private String approvalEntryIDs= "";
	
	private HashMap workList = new HashMap();

	private InutApprovalWorkInfo inutWorkInfo = null;// inut info
	
	private long craMyWorkInterfaceType = 2;

	public long getActionCode() {
		return actionCode;
	}

	public void setActionCode(long actionCode) {
		this.actionCode = actionCode;
	}

	public long getActionID() {
		return actionID;
	}

	public void setActionID(long actionID) {
		this.actionID = actionID;
	}

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public long getApprovalEntryID() {
		return approvalEntryID;
	}

	public void setApprovalEntryID(long approvalEntryID) {
		this.approvalEntryID = approvalEntryID;
	}

	public String getApprovalEntryIDs() {
		return approvalEntryIDs;
	}

	public void setApprovalEntryIDs(String approvalEntryIDs) {
		this.approvalEntryIDs = approvalEntryIDs;
	}

	public Date getApprovalStartDate() {
		return approvalStartDate;
	}

	public void setApprovalStartDate(Date approvalStartDate) {
		this.approvalStartDate = approvalStartDate;
	}

	public String getCreditClientCode() {
		return creditClientCode;
	}

	public void setCreditClientCode(String creditClientCode) {
		this.creditClientCode = creditClientCode;
	}

	public long getCreditClientID() {
		return creditClientID;
	}

	public void setCreditClientID(long creditClientID) {
		this.creditClientID = creditClientID;
	}

	public String getCreditClientName() {
		return creditClientName;
	}

	public void setCreditClientName(String creditClientName) {
		this.creditClientName = creditClientName;
	}

	public long getCreditDirection() {
		return creditDirection;
	}

	public void setCreditDirection(long creditDirection) {
		this.creditDirection = creditDirection;
	}

	public String getCreditedClientCode() {
		return creditedClientCode;
	}

	public void setCreditedClientCode(String creditedClientCode) {
		this.creditedClientCode = creditedClientCode;
	}

	public long getCreditedClientID() {
		return creditedClientID;
	}

	public void setCreditedClientID(long creditedClientID) {
		this.creditedClientID = creditedClientID;
	}

	public String getCreditedClientName() {
		return creditedClientName;
	}

	public void setCreditedClientName(String creditedClientName) {
		this.creditedClientName = creditedClientName;
	}

	public long getCsID() {
		return csID;
	}

	public void setCsID(long csID) {
		this.csID = csID;
	}

	public long getCurrencyID() {
		return currencyID;
	}

	public void setCurrencyID(long currencyID) {
		this.currencyID = currencyID;
	}

	public long getDesc() {
		return desc;
	}

	public void setDesc(long desc) {
		this.desc = desc;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public Timestamp getEndDate() {
		return endDate;
	}

	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}

	public long getEntryID() {
		return entryID;
	}

	public void setEntryID(long entryID) {
		this.entryID = entryID;
	}

	public String getEntryName() {
		return entryName;
	}

	public void setEntryName(String entryName) {
		this.entryName = entryName;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Timestamp getInputDate() {
		return inputDate;
	}

	public void setInputDate(Timestamp inputDate) {
		this.inputDate = inputDate;
	}

	public String getInputUser() {
		return inputUser;
	}

	public void setInputUser(String inputUser) {
		this.inputUser = inputUser;
	}

	public long getInputUserID() {
		return inputUserID;
	}

	public void setInputUserID(long inputUserID) {
		this.inputUserID = inputUserID;
	}

	public InutApprovalWorkInfo getInutWorkInfo() {
		return inutWorkInfo;
	}

	public void setInutWorkInfo(InutApprovalWorkInfo inutWorkInfo) {
		this.inutWorkInfo = inutWorkInfo;
	}

	public String getLinkUrl() {
		return linkUrl;
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}

	public long getModuleID() {
		return moduleID;
	}

	public void setModuleID(long moduleID) {
		this.moduleID = moduleID;
	}

	public long getOfficeID() {
		return officeID;
	}

	public void setOfficeID(long officeID) {
		this.officeID = officeID;
	}

	public long getOrderField() {
		return orderField;
	}

	public void setOrderField(long orderField) {
		this.orderField = orderField;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public long getQueryPurpose() {
		return queryPurpose;
	}

	public void setQueryPurpose(long queryPurpose) {
		this.queryPurpose = queryPurpose;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Timestamp getStartDate() {
		return startDate;
	}

	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public long getStatusID() {
		return statusID;
	}

	public void setStatusID(long statusID) {
		this.statusID = statusID;
	}

	public long getStepCode() {
		return stepCode;
	}

	public void setStepCode(long stepCode) {
		this.stepCode = stepCode;
	}

	public long getStepID() {
		return stepID;
	}

	public void setStepID(long stepID) {
		this.stepID = stepID;
	}

	public String getStepName() {
		return stepName;
	}

	public void setStepName(String stepName) {
		this.stepName = stepName;
	}

	public long getTaskID() {
		return taskID;
	}

	public void setTaskID(long taskID) {
		this.taskID = taskID;
	}

	public long getTerm() {
		return term;
	}

	public void setTerm(long term) {
		this.term = term;
	}

	public long getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(long transactionType) {
		this.transactionType = transactionType;
	}

	public long getUserID() {
		return userID;
	}

	public void setUserID(long userID) {
		this.userID = userID;
	}

	public String getWfDefineName() {
		return wfDefineName;
	}

	public void setWfDefineName(String wfDefineName) {
		this.wfDefineName = wfDefineName;
	}

	public HashMap getWorkList() {
		return workList;
	}

	public void setWorkList(HashMap workList) {
		this.workList = workList;
	}

	public long getCraMyWorkInterfaceType() {
		return craMyWorkInterfaceType;
	}
}
