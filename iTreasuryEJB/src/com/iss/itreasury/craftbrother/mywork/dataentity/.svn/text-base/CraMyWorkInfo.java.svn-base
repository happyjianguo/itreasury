package com.iss.itreasury.craftbrother.mywork.dataentity;

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

public class CraMyWorkInfo implements CraMyWorkInterface{

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

	private long queryPurpose = -1; // 查询目的（查询待办、已办、办结）
 
    //NTYPEID   
    private long nTypeId = -1;    
    //NCURRENCYID
    private long nSubtypeid= -1;//贷款子类id
    private long nCurrencyId = -1;  
    //NOFFICEID
    private long nOfficeId = -1;  
    //SAPPLYCODE
    private String sApplyCode = "";  
    //NCONSIGNCLIENTID
    private long nConsignClientId = -1;   
    //NBORROWCLIENTID
    private long nBorrowClientId = -1;    
    //MLOANAMOUNT
    private double mLoanAmount = 0;  
    //SLOANREASON
    private String sLoanReason = "";  
    //SLOANPURPOSE
    private String sLoanPurpose = "";  
    //SOTHER
    private String sOther = "";    
    //NISCIRCLE
    private long nIsCircle = -1;    
    //NISSALEBUY
    private long nIsSaleBuy = -1;    
    //NISTECHNICAL
    private long nIsTechnical = -1;    
    //NINPUTUSERID
    private long nInputUserId = -1;    
    //DTINPUTDATE
    private Timestamp dtInputDate = null;  
    //NISCREDIT
    private long nIsCredit = -1;    
    //NISASSURE
    private long nIsAssure = -1;    
    //NISIMPAWN
    private long nIsImpawn = -1;    
    //NISPLEDGE
    private long nIsPledgge = -1;    
    //NINTERESTTYPEID
    private long nInterestTypeId = -1;    
    //MEXAMINEAMOUNT
    private double mExamineAmount = 0;  
    //NINTERVALNUM
    private long nIntervalNum = -1;    
    //NBANKINTERESTID
    private long nBankInterestId = -1;    
    //NSTATUSID
    private long nStatusId = -1;    
    //NNEXTCHECKUSERID
    private long nNextCheckUserId = -1;    
    //MCHARGERATE
    private double mChargeRate = 0;  
    //DTSTARTDATE
    private Timestamp dtStartDate = null;  
    //DTENDDATE
    private Timestamp dtEndDate = null;  
    //ISCANMODIFY
    private long nIsCanModify = -1;    
    //NCHARGERATETYPEID
    private long nChargeRateTypeId = -1;   
    //SCLIENTINFO
    private String sClientInfo = "";  
    //MSELFAMOUNT
    private double mSelfAmount = 0;  
    //NRISKLEVEL
    private long nRiskLevel = -1;    
    //NTYPEID1
    private long nTypeId1 = -1;   
    //NTYPEID2
    private long nTypeId2 = -1;   
    //NTYPEID3
    private long nTypeId3 = -1;      
    //
    private long nAcceptPO = -1; 
    //NBANKACCEPTPO
    private long nBankAcceptPO = -1;    
    //NBIZACCEPTPO
    private long nBizAcceptPO = -1;    
    //MCHECKAMOUNT
    private double mCheckAmount = 0;  
    //MDISCOUNTRATE
    private double mDiscountRate = 0;  
    //DTDISCOUNTDATE
    private Timestamp dtDiscountDate = null;  
    //MINTERESTRATE
    private double mInterestRate = 0;  
    //MADJUSTRATE
    private double mAdjustRate = 0;  
    //NNEXTCHECKLEVEL
    private long nNextCheckLevel = -1; 
    private long IsLowLevel= -1;
    //MSTAIDADJUSTRATE
    private double mStaidAdjustRate = 0;  
    //NINOROUT
    private long nInOrOut = -1;    
    //NDISCOUNTTYPEID
    private long nDiscountTypeId = -1;    
    //NREPURCHASETYPEID
    private long nRepurchaseTypeId = -1;   
    //最终审核人－－中油
    private long sLastCheckUserId = -1;
	

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
	
	//合同执行利率变更新增
//	private String contractRateChangeReason = ""; //合同变更原因
	private String  reason = ""; //合同变更原因
	
	private Timestamp dtValiDate = null;// 生效日期
	
	private double Rate = 0; //利率
	
	
	private HashMap workList = new HashMap();
	//
	private InutApprovalWorkInfo inutWorkInfo = null;// inut info
	
	private long craMyWorkInterfaceType = 1;

	public long getCraMyWorkInterfaceType(){
		return craMyWorkInterfaceType;
	}

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

	public Timestamp getDtDiscountDate() {
		return dtDiscountDate;
	}

	public void setDtDiscountDate(Timestamp dtDiscountDate) {
		this.dtDiscountDate = dtDiscountDate;
	}

	public Timestamp getDtEndDate() {
		return dtEndDate;
	}

	public void setDtEndDate(Timestamp dtEndDate) {
		this.dtEndDate = dtEndDate;
	}

	public Timestamp getDtInputDate() {
		return dtInputDate;
	}

	public void setDtInputDate(Timestamp dtInputDate) {
		this.dtInputDate = dtInputDate;
	}

	public Timestamp getDtStartDate() {
		return dtStartDate;
	}

	public void setDtStartDate(Timestamp dtStartDate) {
		this.dtStartDate = dtStartDate;
	}

	public long getIsLowLevel() {
		return IsLowLevel;
	}

	public void setIsLowLevel(long isLowLevel) {
		IsLowLevel = isLowLevel;
	}

	public double getMAdjustRate() {
		return mAdjustRate;
	}

	public void setMAdjustRate(double adjustRate) {
		mAdjustRate = adjustRate;
	}

	public double getMChargeRate() {
		return mChargeRate;
	}

	public void setMChargeRate(double chargeRate) {
		mChargeRate = chargeRate;
	}

	public double getMCheckAmount() {
		return mCheckAmount;
	}

	public void setMCheckAmount(double checkAmount) {
		mCheckAmount = checkAmount;
	}

	public double getMDiscountRate() {
		return mDiscountRate;
	}

	public void setMDiscountRate(double discountRate) {
		mDiscountRate = discountRate;
	}

	public double getMExamineAmount() {
		return mExamineAmount;
	}

	public void setMExamineAmount(double examineAmount) {
		mExamineAmount = examineAmount;
	}

	public double getMInterestRate() {
		return mInterestRate;
	}

	public void setMInterestRate(double interestRate) {
		mInterestRate = interestRate;
	}

	public double getMLoanAmount() {
		return mLoanAmount;
	}

	public void setMLoanAmount(double loanAmount) {
		mLoanAmount = loanAmount;
	}

	public double getMSelfAmount() {
		return mSelfAmount;
	}

	public void setMSelfAmount(double selfAmount) {
		mSelfAmount = selfAmount;
	}

	public double getMStaidAdjustRate() {
		return mStaidAdjustRate;
	}

	public void setMStaidAdjustRate(double staidAdjustRate) {
		mStaidAdjustRate = staidAdjustRate;
	}

	public long getNAcceptPO() {
		return nAcceptPO;
	}

	public void setNAcceptPO(long acceptPO) {
		nAcceptPO = acceptPO;
	}

	public long getNBankAcceptPO() {
		return nBankAcceptPO;
	}

	public void setNBankAcceptPO(long bankAcceptPO) {
		nBankAcceptPO = bankAcceptPO;
	}

	public long getNBankInterestId() {
		return nBankInterestId;
	}

	public void setNBankInterestId(long bankInterestId) {
		nBankInterestId = bankInterestId;
	}

	public long getNBizAcceptPO() {
		return nBizAcceptPO;
	}

	public void setNBizAcceptPO(long bizAcceptPO) {
		nBizAcceptPO = bizAcceptPO;
	}

	public long getNBorrowClientId() {
		return nBorrowClientId;
	}

	public void setNBorrowClientId(long borrowClientId) {
		nBorrowClientId = borrowClientId;
	}

	public long getNChargeRateTypeId() {
		return nChargeRateTypeId;
	}

	public void setNChargeRateTypeId(long chargeRateTypeId) {
		nChargeRateTypeId = chargeRateTypeId;
	}

	public long getNConsignClientId() {
		return nConsignClientId;
	}

	public void setNConsignClientId(long consignClientId) {
		nConsignClientId = consignClientId;
	}

	public long getNCurrencyId() {
		return nCurrencyId;
	}

	public void setNCurrencyId(long currencyId) {
		nCurrencyId = currencyId;
	}

	public long getNDiscountTypeId() {
		return nDiscountTypeId;
	}

	public void setNDiscountTypeId(long discountTypeId) {
		nDiscountTypeId = discountTypeId;
	}

	public long getNInOrOut() {
		return nInOrOut;
	}

	public void setNInOrOut(long inOrOut) {
		nInOrOut = inOrOut;
	}

	public long getNInputUserId() {
		return nInputUserId;
	}

	public void setNInputUserId(long inputUserId) {
		nInputUserId = inputUserId;
	}

	public long getNInterestTypeId() {
		return nInterestTypeId;
	}

	public void setNInterestTypeId(long interestTypeId) {
		nInterestTypeId = interestTypeId;
	}

	public long getNIntervalNum() {
		return nIntervalNum;
	}

	public void setNIntervalNum(long intervalNum) {
		nIntervalNum = intervalNum;
	}

	public long getNIsAssure() {
		return nIsAssure;
	}

	public void setNIsAssure(long isAssure) {
		nIsAssure = isAssure;
	}

	public long getNIsCanModify() {
		return nIsCanModify;
	}

	public void setNIsCanModify(long isCanModify) {
		nIsCanModify = isCanModify;
	}

	public long getNIsCircle() {
		return nIsCircle;
	}

	public void setNIsCircle(long isCircle) {
		nIsCircle = isCircle;
	}

	public long getNIsCredit() {
		return nIsCredit;
	}

	public void setNIsCredit(long isCredit) {
		nIsCredit = isCredit;
	}

	public long getNIsImpawn() {
		return nIsImpawn;
	}

	public void setNIsImpawn(long isImpawn) {
		nIsImpawn = isImpawn;
	}

	public long getNIsPledgge() {
		return nIsPledgge;
	}

	public void setNIsPledgge(long isPledgge) {
		nIsPledgge = isPledgge;
	}

	public long getNIsSaleBuy() {
		return nIsSaleBuy;
	}

	public void setNIsSaleBuy(long isSaleBuy) {
		nIsSaleBuy = isSaleBuy;
	}

	public long getNIsTechnical() {
		return nIsTechnical;
	}

	public void setNIsTechnical(long isTechnical) {
		nIsTechnical = isTechnical;
	}

	public long getNNextCheckLevel() {
		return nNextCheckLevel;
	}

	public void setNNextCheckLevel(long nextCheckLevel) {
		nNextCheckLevel = nextCheckLevel;
	}

	public long getNNextCheckUserId() {
		return nNextCheckUserId;
	}

	public void setNNextCheckUserId(long nextCheckUserId) {
		nNextCheckUserId = nextCheckUserId;
	}

	public long getNOfficeId() {
		return nOfficeId;
	}

	public void setNOfficeId(long officeId) {
		nOfficeId = officeId;
	}

	public long getNRepurchaseTypeId() {
		return nRepurchaseTypeId;
	}

	public void setNRepurchaseTypeId(long repurchaseTypeId) {
		nRepurchaseTypeId = repurchaseTypeId;
	}

	public long getNRiskLevel() {
		return nRiskLevel;
	}

	public void setNRiskLevel(long riskLevel) {
		nRiskLevel = riskLevel;
	}

	public long getNStatusId() {
		return nStatusId;
	}

	public void setNStatusId(long statusId) {
		nStatusId = statusId;
	}

	public long getNSubtypeid() {
		return nSubtypeid;
	}

	public void setNSubtypeid(long subtypeid) {
		nSubtypeid = subtypeid;
	}

	public long getNTypeId() {
		return nTypeId;
	}

	public void setNTypeId(long typeId) {
		nTypeId = typeId;
	}

	public long getNTypeId1() {
		return nTypeId1;
	}

	public void setNTypeId1(long typeId1) {
		nTypeId1 = typeId1;
	}

	public long getNTypeId2() {
		return nTypeId2;
	}

	public void setNTypeId2(long typeId2) {
		nTypeId2 = typeId2;
	}

	public long getNTypeId3() {
		return nTypeId3;
	}

	public void setNTypeId3(long typeId3) {
		nTypeId3 = typeId3;
	}

	public String getSApplyCode() {
		return sApplyCode;
	}

	public void setSApplyCode(String applyCode) {
		sApplyCode = applyCode;
	}

	public String getSClientInfo() {
		return sClientInfo;
	}

	public void setSClientInfo(String clientInfo) {
		sClientInfo = clientInfo;
	}

	public long getSLastCheckUserId() {
		return sLastCheckUserId;
	}

	public void setSLastCheckUserId(long lastCheckUserId) {
		sLastCheckUserId = lastCheckUserId;
	}

	public String getSLoanPurpose() {
		return sLoanPurpose;
	}

	public void setSLoanPurpose(String loanPurpose) {
		sLoanPurpose = loanPurpose;
	}

	public String getSLoanReason() {
		return sLoanReason;
	}

	public void setSLoanReason(String loanReason) {
		sLoanReason = loanReason;
	}

	public String getSOther() {
		return sOther;
	}

	public void setSOther(String other) {
		sOther = other;
	}
	
}