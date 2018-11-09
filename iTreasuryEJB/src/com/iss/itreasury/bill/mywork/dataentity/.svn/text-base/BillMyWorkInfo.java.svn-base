/**
 * 
 */
package com.iss.itreasury.bill.mywork.dataentity;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;

import com.iss.itreasury.system.approval.dataentity.InutApprovalWorkInfo;
import com.iss.itreasury.util.Constant;

/**
 * @author user
 *
 */
public class BillMyWorkInfo {


	// 审批关联信息及查询条件信息
	private long id = -1; // 记录ID

	private long officeID = -1; // 办事处ID

	private long currencyID = -1; // 币种ID

	private long moduleID = Constant.ModuleType.BILL; // 模块标示

	private long actionID = -1; // 操作标识

	private String actionName = "";// 操作名称

	private long approvalEntryID = -1; // 审批实例id

	private String linkUrl = ""; // 审批链接地址

	private long userID = -1; // 待办人
	
	private long statusID = -1; // 记录状态

	private long queryPurpose = -1; // 查询目的（查询待办、已办、办结）

	//汇票业务信息
	private long dTransID = -1 ;//交易id 
	private String transcode="";	//交易编号
	
	private long billId = -1; //单据ID
	private String billCode = ""; //票据编号
	private String clientName = ""; //业务单位名称
	private long businessTypeId = -1; //业务类型ID
	private String businessTypeName = ""; //业务类型名称
	private long transactionTypeId = -1; //交易类型ID
	private String transactionTypeName = ""; //交易类型名称
	private Timestamp inputDate = null; //录入日期
	private String stracceptoraccount = ""; //资金帐号
	
	private long inputUserID = -1; //录入人ID
	private String inputUserName = ""; //录入人名称
	
	//转贴现出库
	private long applyid = -1;
	private String lApplyCode = ""; //转贴现申请编号
	private long inOrOut = -1; //转贴现买入卖出类型
	private double transAmount = 0.0;//转贴现金额
	private Timestamp discountDate = null; //贴现日
	private long clientID = -1;//转贴现单位
	private long discountTypeID = -1; //转贴现类型
	private long billStatus = -1;//转贴现票据状态
	
	
	
	
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

//	private String status = ""; // 状态

	private String approvalEntryIDs= "";
	
	private HashMap workList = new HashMap();

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

//	public String getStatus() {
//		return status;
//	}
//
//	public void setStatus(String status) {
//		this.status = status;
//	}

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

	public String getApprovalEntryIDs() {
		return approvalEntryIDs;
	}

	public void setApprovalEntryIDs(String approvalEntryIDs) {
		this.approvalEntryIDs = approvalEntryIDs;
	}

	public HashMap getWorkList() {
		return workList;
	}

	public void setWorkList(HashMap workList) {
		this.workList = workList;
	}

	/**
	 * @return the stracceptoraccount
	 */
	public String getStracceptoraccount() {
		return stracceptoraccount;
	}

	/**
	 * @param stracceptoraccount the stracceptoraccount to set
	 */
	public void setStracceptoraccount(String stracceptoraccount) {
		this.stracceptoraccount = stracceptoraccount;
	}

	public long getBusinessTypeId() {
		return businessTypeId;
	}

	public void setBusinessTypeId(long businessTypeId) {
		this.businessTypeId = businessTypeId;
	}

	public String getBusinessTypeName() {
		return businessTypeName;
	}

	public void setBusinessTypeName(String businessTypeName) {
		this.businessTypeName = businessTypeName;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public Timestamp getInputDate() {
		return inputDate;
	}

	public void setInputDate(Timestamp inputDate) {
		this.inputDate = inputDate;
	}

	public long getInputUserID() {
		return inputUserID;
	}

	public void setInputUserID(long inputUserID) {
		this.inputUserID = inputUserID;
	}

	public String getInputUserName() {
		return inputUserName;
	}

	public void setInputUserName(String inputUserName) {
		this.inputUserName = inputUserName;
	}

	public String getBillCode() {
		return billCode;
	}

	public void setBillCode(String secCode) {
		this.billCode = secCode;
	}

	public long getBillId() {
		return billId;
	}

	public void setBillId(long billId) {
		this.billId = billId;
	}

	public long getStatusID() {
		return statusID;
	}

	public void setStatusID(long statusID) {
		this.statusID = statusID;
	}


	/**
	 * @return the transcode
	 */
	public String getTranscode() {
		return transcode;
	}

	/**
	 * @param transcode the transcode to set
	 */
	public void setTranscode(String transcode) {
		this.transcode = transcode;
	}

	public String getTransactionTypeName() {
		return transactionTypeName;
	}

	public void setTransactionTypeName(String transactionTypeName) {
		this.transactionTypeName = transactionTypeName;
	}

	/**
	 * @return the transactionTypeId
	 */
	public long getTransactionTypeId() {
		return transactionTypeId;
	}

	/**
	 * @param transactionTypeId the transactionTypeId to set
	 */
	public void setTransactionTypeId(long transactionTypeId) {
		this.transactionTypeId = transactionTypeId;
	}

	/**
	 * @return the billStatus
	 */
	public long getBillStatus() {
		return billStatus;
	}

	/**
	 * @param billStatus the billStatus to set
	 */
	public void setBillStatus(long billStatus) {
		this.billStatus = billStatus;
	}

	/**
	 * @return the clientID
	 */
	public long getClientID() {
		return clientID;
	}

	/**
	 * @param clientID the clientID to set
	 */
	public void setClientID(long clientID) {
		this.clientID = clientID;
	}

	/**
	 * @return the discountDate
	 */
	public Timestamp getDiscountDate() {
		return discountDate;
	}

	/**
	 * @param discountDate the discountDate to set
	 */
	public void setDiscountDate(Timestamp discountDate) {
		this.discountDate = discountDate;
	}

	/**
	 * @return the discountTypeID
	 */
	public long getDiscountTypeID() {
		return discountTypeID;
	}

	/**
	 * @param discountTypeID the discountTypeID to set
	 */
	public void setDiscountTypeID(long discountTypeID) {
		this.discountTypeID = discountTypeID;
	}

	/**
	 * @return the inOrOut
	 */
	public long getInOrOut() {
		return inOrOut;
	}

	/**
	 * @param inOrOut the inOrOut to set
	 */
	public void setInOrOut(long inOrOut) {
		this.inOrOut = inOrOut;
	}

	/**
	 * @return the lApplyCode
	 */
	public String getLApplyCode() {
		return lApplyCode;
	}

	/**
	 * @param applyCode the lApplyCode to set
	 */
	public void setLApplyCode(String applyCode) {
		lApplyCode = applyCode;
	}

	/**
	 * @return the transAmount
	 */
	public double getTransAmount() {
		return transAmount;
	}

	/**
	 * @param transAmount the transAmount to set
	 */
	public void setTransAmount(double transAmount) {
		this.transAmount = transAmount;
	}

	/**
	 * @return the dTransID
	 */
	public long getDTransID() {
		return dTransID;
	}

	/**
	 * @param transID the dTransID to set
	 */
	public void setDTransID(long transID) {
		dTransID = transID;
	}

	/**
	 * @return the applyid
	 */
	public long getApplyid() {
		return applyid;
	}

	/**
	 * @param applyid the applyid to set
	 */
	public void setApplyid(long applyid) {
		this.applyid = applyid;
	}


}
