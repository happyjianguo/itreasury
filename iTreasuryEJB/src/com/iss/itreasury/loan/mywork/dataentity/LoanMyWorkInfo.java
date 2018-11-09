package com.iss.itreasury.loan.mywork.dataentity;

/*
 * Created on 2007-06-21
 *
 * Title:				iTreasury
 * @author             	������ 
 * Company:             iSoftStone
 * @version				iTreasury4.0����
 * Description:         ��Ʒ��4.0���Ŵ�����������,��������������ñ����з����ĵĲ�ƷiNut,
 * 						Ϊ��iNut��������ҵ�����,�����������ù���
 */

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;

import com.iss.itreasury.util.Constant;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.system.approval.dataentity.InutApprovalWorkInfo;

public class LoanMyWorkInfo implements Serializable {

	// ����������Ϣ����ѯ������Ϣ
	private long id = -1; // ��¼ID

	private long officeID = -1; // ���´�ID

	private long currencyID = -1; // ����ID

	private long moduleID = Constant.ModuleType.LOAN; // ģ���ʾ

	private long actionID = -1; // ������ʶ

	private String actionName = "";// ��������

	private long approvalEntryID = -1; // ����ʵ��id

	private String linkUrl = ""; // �������ӵ�ַ

	private long userID = -1; // ������

	private long queryPurpose = -1; // ��ѯĿ�ģ���ѯ���졢�Ѱ졢��ᣩ

	// ����ҵ����Ϣ

	private long oldStatus =-1;//��ͬ���ǰ��״̬
	
	private long changeStatus =-1;//��ͬ������״̬
	
	private long contractID = -1; // /��ͬ��ID

	private String contractCode = "";// ��ͬ���

	private long loanTypeId = -1;// ��������ID
	
	private long loanSubTypeId = -1;// ����������ID

	private String loanSubTypeName = "";// ��������������
	
	private double amount = 0.0d;// ���

	private String code = ""; // ���ݺ�

	private Timestamp startDate = null;// ���ʼ����

	private Timestamp endDate = null;// �����������
	
	private Timestamp inputDate = null;// ¼������

	private long borrowClientId = -1;// ��λID

	private String borrowClientName = ""; // ��λ����

	private long statusID = -1; // ��¼״̬

	private long inputUserID = -1; // ��¼¼����ID
	
	private String inputUserName = ""; //��¼¼��������

	// ������Ϣ
	private long desc = -1;

	private long orderField = -1;

	// ����
	private long taskID = -1; // ����id

	private long entryID = -1; // ����ʵ��id

	private long stepID = -1; // ��������id

	private long stepCode = -1; // ��������code

	private long actionCode = -1; // ��������code

	private String entryName = ""; // ����ʵ������

	private String wfDefineName = ""; // ������������

	private String stepName = ""; // ������������

	private Date approvalStartDate = null; // ������ʼʱ��

	private Date dueDate = null; // ������ʱ��

	private String owner = ""; // ��һ��������

	private String status = ""; // ״̬

	private String approvalEntryIDs= "";
	///��ִͬ�мƻ��������
	private String contractChangeReason = ""; //��ͬ���ԭ��
	
	private long contractPlanVersion = -1; //��ͬ�ƻ��汾
	
	private long planVersion = -1;
	
	//��ִͬ�����ʱ������
//	private String contractRateChangeReason = ""; //��ͬ���ԭ��
	private String  reason = ""; //��ͬ���ԭ��
	
	private Timestamp dtValiDate = null;// ��Ч����
	
	private double Rate = 0; //����
	
	//����ר��
	private long creditModel = -1; //����ģʽ
	
	private long amountFormId = -1; //���Ŷ������
	
	private long operationType = -1; //���� or ���
	
	private long operationSign = -1; //+ or -
	
	private long batchid = -1;//����ID
	
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