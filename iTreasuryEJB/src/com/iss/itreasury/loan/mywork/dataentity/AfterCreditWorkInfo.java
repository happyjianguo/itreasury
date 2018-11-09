package com.iss.itreasury.loan.mywork.dataentity;

import java.sql.Timestamp;

import com.iss.itreasury.system.approval.dataentity.InutApprovalWorkInfo;
import com.iss.itreasury.util.Constant;

public class AfterCreditWorkInfo {
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
	private long contractID = -1; // /��ͬ��ID

	private String contractCode = "";// ��ͬ���

	private long loanTypeId = -1;// ��������ID
	
	private long loanSubTypeId = -1;// ����������ID
	private String inputUserName = ""; // ��¼¼��������
	private String checkReportCode = "";//��鱨����
	private long advice = -1;//�弶�������
	private long checkType = -1;//�����������
	private String checkYear = "";//���������
	Timestamp inputDate = null;
	private long clientid = -1;
	private String clientName = "";//�ͻ�����
	private long status = -1;//����״̬
	private String wfDefineName = ""; // ������������
	private long taskID = -1; // ����id

	private long entryID = -1; // ����ʵ��id

	private long stepID = -1; // ��������id

	private long stepCode = -1; // ��������code

	private long actionCode = -1; // ��������code

	private String stepName = ""; // ������������
	private InutApprovalWorkInfo inutWorkInfo = null;// inut info
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getOfficeID() {
		return officeID;
	}
	public void setOfficeID(long officeID) {
		this.officeID = officeID;
	}
	public long getCurrencyID() {
		return currencyID;
	}
	public void setCurrencyID(long currencyID) {
		this.currencyID = currencyID;
	}
	public long getModuleID() {
		return moduleID;
	}
	public void setModuleID(long moduleID) {
		this.moduleID = moduleID;
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
	public long getUserID() {
		return userID;
	}
	public void setUserID(long userID) {
		this.userID = userID;
	}
	public long getQueryPurpose() {
		return queryPurpose;
	}
	public void setQueryPurpose(long queryPurpose) {
		this.queryPurpose = queryPurpose;
	}
	public long getContractID() {
		return contractID;
	}
	public void setContractID(long contractID) {
		this.contractID = contractID;
	}
	public String getContractCode() {
		return contractCode;
	}
	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}
	public long getLoanTypeId() {
		return loanTypeId;
	}
	public void setLoanTypeId(long loanTypeId) {
		this.loanTypeId = loanTypeId;
	}
	public long getLoanSubTypeId() {
		return loanSubTypeId;
	}
	public void setLoanSubTypeId(long loanSubTypeId) {
		this.loanSubTypeId = loanSubTypeId;
	}
	public String getInputUserName() {
		return inputUserName;
	}
	public void setInputUserName(String inputUserName) {
		this.inputUserName = inputUserName;
	}
	public String getCheckReportCode() {
		return checkReportCode;
	}
	public void setCheckReportCode(String checkReportCode) {
		this.checkReportCode = checkReportCode;
	}
	public long getAdvice() {
		return advice;
	}
	public void setAdvice(long advice) {
		this.advice = advice;
	}
	public long getCheckType() {
		return checkType;
	}
	public void setCheckType(long checkType) {
		this.checkType = checkType;
	}
	public String getCheckYear() {
		return checkYear;
	}
	public void setCheckYear(String checkYear) {
		this.checkYear = checkYear;
	}
	public Timestamp getInputDate() {
		return inputDate;
	}
	public void setInputDate(Timestamp inputDate) {
		this.inputDate = inputDate;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public long getStatus() {
		return status;
	}
	public void setStatus(long status) {
		this.status = status;
	}
	public InutApprovalWorkInfo getInutWorkInfo() {
		return inutWorkInfo;
	}
	public void setInutWorkInfo(InutApprovalWorkInfo inutWorkInfo) {
		this.inutWorkInfo = inutWorkInfo;
	}
	public String getWfDefineName() {
		return wfDefineName;
	}
	public void setWfDefineName(String wfDefineName) {
		this.wfDefineName = wfDefineName;
	}
	public String getStepName() {
		return stepName;
	}
	public void setStepName(String stepName) {
		this.stepName = stepName;
	}
	public long getClientid() {
		return clientid;
	}
	public void setClientid(long clientid) {
		this.clientid = clientid;
	}
	public long getTaskID() {
		return taskID;
	}
	public void setTaskID(long taskID) {
		this.taskID = taskID;
	}
	public long getEntryID() {
		return entryID;
	}
	public void setEntryID(long entryID) {
		this.entryID = entryID;
	}
	public long getStepID() {
		return stepID;
	}
	public void setStepID(long stepID) {
		this.stepID = stepID;
	}
	public long getStepCode() {
		return stepCode;
	}
	public void setStepCode(long stepCode) {
		this.stepCode = stepCode;
	}
	public long getActionCode() {
		return actionCode;
	}
	public void setActionCode(long actionCode) {
		this.actionCode = actionCode;
	}
}
