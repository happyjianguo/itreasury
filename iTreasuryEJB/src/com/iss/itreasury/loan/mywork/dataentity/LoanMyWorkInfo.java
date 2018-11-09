package com.iss.itreasury.loan.mywork.dataentity;

/*
 * Created on 2007-06-21
 *
 * Title:				iTreasury
 * @author             	付明正 
 * Company:             iSoftStone
 * @version				iTreasury4.0新增
 * Description:         产品化4.0在信贷新增审批流,且审批流引擎采用北京研发中心的产品iNut,
 * 						为将iNut审批流与业务关联,新增关联设置功能
 */

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;

import com.iss.itreasury.util.Constant;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.system.approval.dataentity.InutApprovalWorkInfo;

public class LoanMyWorkInfo implements Serializable {

	// 审批关联信息及查询条件信息
	private long id = -1; // 记录ID

	private long officeID = -1; // 办事处ID

	private long currencyID = -1; // 币种ID

	private long moduleID = Constant.ModuleType.LOAN; // 模块标示

	private long actionID = -1; // 操作标识

	private String actionName = "";// 操作名称

	private long approvalEntryID = -1; // 审批实例id

	private String linkUrl = ""; // 审批链接地址

	private long userID = -1; // 待办人

	private long queryPurpose = -1; // 查询目的（查询待办、已办、办结）

	// 贷款业务信息

	private long oldStatus =-1;//合同变更前的状态
	
	private long changeStatus =-1;//合同变更后的状态
	
	private long contractID = -1; // /合同号ID

	private String contractCode = "";// 合同编号

	private long loanTypeId = -1;// 贷款类型ID
	
	private long loanSubTypeId = -1;// 贷款子类型ID

	private String loanSubTypeName = "";// 贷款子类型名称
	
	private double amount = 0.0d;// 金额

	private String code = ""; // 单据号

	private Timestamp startDate = null;// 贷款开始日期

	private Timestamp endDate = null;// 贷款结束日期
	
	private Timestamp inputDate = null;// 录入日期

	private long borrowClientId = -1;// 借款单位ID

	private String borrowClientName = ""; // 借款单位名称

	private long statusID = -1; // 记录状态

	private long inputUserID = -1; // 记录录入人ID
	
	private String inputUserName = ""; //记录录入人名称

	// 辅助信息
	private long desc = -1;

	private long orderField = -1;

	// 新增
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
	///合同执行计划变更新增
	private String contractChangeReason = ""; //合同变更原因
	
	private long contractPlanVersion = -1; //合同计划版本
	
	private long planVersion = -1;
	
	//合同执行利率变更新增
//	private String contractRateChangeReason = ""; //合同变更原因
	private String  reason = ""; //合同变更原因
	
	private Timestamp dtValiDate = null;// 生效日期
	
	private double Rate = 0; //利率
	
	//授信专用
	private long creditModel = -1; //授信模式
	
	private long amountFormId = -1; //授信额度主表
	
	private long operationType = -1; //新增 or 变更
	
	private long operationSign = -1; //+ or -
	
	private long batchid = -1;//批次ID
	
	private String qSingleOrBatch = "";
	
	
	private HashMap workList = new HashMap();
	//
	private InutApprovalWorkInfo inutWorkInfo = null;// inut info

	public long getActionID() {
		return actionID;
	}

	public void setActionID(long actionID) {
		this.actionID = actionID;
	}

	public long getCurrencyID() {
		return currencyID;
	}

	public void setCurrencyID(long currencyID) {
		this.currencyID = currencyID;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getInputUserID() {
		return inputUserID;
	}

	public void setInputUserID(long inputUserID) {
		this.inputUserID = inputUserID;
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

	public long getStatusID() {
		return statusID;
	}

	public void setStatusID(long statusID) {
		this.statusID = statusID;
	}

	public long getUserID() {
		return userID;
	}

	public void setUserID(long userID) {
		this.userID = userID;
	}

	public InutApprovalWorkInfo getInutWorkInfo() {
		return inutWorkInfo;
	}

	public void setInutWorkInfo(InutApprovalWorkInfo inutWorkInfo) {
		this.inutWorkInfo = inutWorkInfo;
	}

	public long getApprovalEntryID() {
		return approvalEntryID;
	}

	public void setApprovalEntryID(long approvalEntryID) {
		this.approvalEntryID = approvalEntryID;
	}

	public String getLinkUrl() {
		return linkUrl;
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}

	public long getQueryPurpose() {
		return queryPurpose;
	}

	public void setQueryPurpose(long queryPurpose) {
		this.queryPurpose = queryPurpose;
	}

	public long getDesc() {
		return desc;
	}

	public void setDesc(long desc) {
		this.desc = desc;
	}

	public long getOrderField() {
		return orderField;
	}

	public void setOrderField(long orderField) {
		this.orderField = orderField;
	}

	public long getActionCode() {
		return actionCode;
	}

	public void setActionCode(long actionCode) {
		this.actionCode = actionCode;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
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

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public String getWfDefineName() {
		return wfDefineName;
	}

	public void setWfDefineName(String wfDefineName) {
		this.wfDefineName = wfDefineName;
	}

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	public Date getApprovalStartDate() {
		return approvalStartDate;
	}

	public void setApprovalStartDate(Date approvalStartDate) {
		this.approvalStartDate = approvalStartDate;
	}

	public String getContractCode() {
		return contractCode;
	}

	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}

	public long getContractID() {
		return contractID;
	}

	public void setContractID(long contractID) {
		this.contractID = contractID;
	}

	public String getLoanSubTypeName() {
		return loanSubTypeName;
	}

	public void setLoanSubTypeName(String loanSubTypeName) {
		this.loanSubTypeName = loanSubTypeName;
	}

	public long getLoanTypeId() {
		return loanTypeId;
	}

	public void setLoanTypeId(long loanTypeId) {
		this.loanTypeId = loanTypeId;
	}

	public String getLoanTypeName() {
		return LOANConstant.LoanType.getName(loanTypeId);
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public long getBorrowClientId() {
		return borrowClientId;
	}

	public void setBorrowClientId(long borrowClientId) {
		this.borrowClientId = borrowClientId;
	}

	public String getBorrowClientName() {
		return borrowClientName;
	}

	public void setBorrowClientName(String borrowClientName) {
		this.borrowClientName = borrowClientName;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Timestamp getEndDate() {
		return endDate;
	}

	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}

	public Timestamp getStartDate() {
		return startDate;
	}

	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}

	public String getApprovalEntryIDs() {
		return approvalEntryIDs;
	}

	public void setApprovalEntryIDs(String approvalEntryIDs) {
		this.approvalEntryIDs = approvalEntryIDs;
	}

	public Timestamp getInputDate() {
		return inputDate;
	}

	public void setInputDate(Timestamp inputDate) {
		this.inputDate = inputDate;
	}

	public long getLoanSubTypeId() {
		return loanSubTypeId;
	}

	public void setLoanSubTypeId(long loanSubTypeId) {
		this.loanSubTypeId = loanSubTypeId;
	}

	public HashMap getWorkList() {
		return workList;
	}

	public void setWorkList(HashMap workList) {
		this.workList = workList;
	}

	public String getInputUserName() {
		return inputUserName;
	}

	public void setInputUserName(String inputUserName) {
		this.inputUserName = inputUserName;
	}

	public long getChangeStatus() {
		return changeStatus;
	}

	public void setChangeStatus(long changeStatus) {
		this.changeStatus = changeStatus;
	}

	public long getOldStatus() {
		return oldStatus;
	}

	public void setOldStatus(long oldStatus) {
		this.oldStatus = oldStatus;
	}

	public String getContractChangeReason() {
		return contractChangeReason;
	}

	public void setContractChangeReason(String contractChangeReason) {
		this.contractChangeReason = contractChangeReason;
	}

	public long getContractPlanVersion() {
		return contractPlanVersion;
	}

	public void setContractPlanVersion(long contractPlanVersion) {
		this.contractPlanVersion = contractPlanVersion;
	}


	public Timestamp getDtValiDate() {
		return dtValiDate;
	}

	public void setDtValiDate(Timestamp dtValiDate) {
		this.dtValiDate = dtValiDate;
	}

	public double getRate() {
		return Rate;
	}

	public void setRate(double rate) {
		Rate = rate;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public long getPlanVersion() {
		return planVersion;
	}

	public void setPlanVersion(long planVersion) {
		this.planVersion = planVersion;
	}

	public long getCreditModel() {
		return creditModel;
	}

	public void setCreditModel(long creditModel) {
		this.creditModel = creditModel;
	}

	public long getAmountFormId() {
		return amountFormId;
	}

	public void setAmountFormId(long amountFormId) {
		this.amountFormId = amountFormId;
	}

	public long getOperationType() {
		return operationType;
	}

	public void setOperationType(long operationType) {
		this.operationType = operationType;
	}

	public long getOperationSign() {
		return operationSign;
	}

	public void setOperationSign(long operationSign) {
		this.operationSign = operationSign;
	}

	public long getBatchid() {
		return batchid;
	}

	public void setBatchid(long batchid) {
		this.batchid = batchid;
	}

	public String getQSingleOrBatch() {
		return qSingleOrBatch;
	}

	public void setQSingleOrBatch(String singleOrBatch) {
		qSingleOrBatch = singleOrBatch;
	}
}