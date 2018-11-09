package com.iss.itreasury.securities.mywork.dataentity;

/*
 * Created on 2007-09-05
 *
 * Title:				iTreasury
 * @author             	���ݡ���С��
 * Company:             iSoftStone
 * @version				iTreasury4.0����
 * Description:         ��Ʒ��4.0��֤ȯ����������,��������������ñ����з����ĵĲ�ƷiNut,
 * 						Ϊ��iNut��������ҵ�����,�����������ù���
 */

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.system.approval.dataentity.InutApprovalWorkInfo;

public class SecMyWorkInfo implements Serializable {

	// ����������Ϣ����ѯ������Ϣ
	private long id = -1; // ��¼ID

	private long officeID = -1; // ���´�ID

	private long currencyID = -1; // ����ID

	private long moduleID = Constant.ModuleType.SECURITIES; // ģ���ʾ

	private long actionID = -1; // ������ʶ

	private String actionName = "";// ��������

	private long approvalEntryID = -1; // ����ʵ��id

	private String linkUrl = ""; // �������ӵ�ַ

	private long userID = -1; // ������
	
	private long statusID = -1; // ��¼״̬

	private long queryPurpose = -1; // ��ѯĿ�ģ���ѯ���졢�Ѱ졢��ᣩ

	// ֤ȯҵ����Ϣ
	private long secId = -1; //����ID
	
	private String secCode = ""; //���ݱ��
	
	private String clientName = ""; //ҵ��λ����
	
	private String stockHolderAccountName = ""; //�ɶ��ʻ�����
	
	private long businessTypeId = -1; //ҵ������ID
	
	private String businessTypeName = ""; //ҵ����������
	
	private long transactionTypeId = -1; //��������ID
	
	private String transactionTypeName = ""; //������������
	
	private double amount = 0.0d; // ���
	
	private Timestamp inputDate = null; //¼������
	
	private long bankOfDepositId = -1; //����Ӫҵ��ID
	
	private String bankOfDepositName = ""; //����Ӫҵ������
	
	private String AccountCode = ""; //�ʽ��ʺ�
	
	private long inputUserID = -1; //¼����ID
	
	private String inputUserName = ""; //¼��������

	// ������Ϣ
	private long desc = -1;

	private long orderField = -1;

	// �����������
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

	public String getAccountCode() {
		return AccountCode;
	}

	public void setAccountCode(String accountCode) {
		AccountCode = accountCode;
	}

	public long getBankOfDepositId() {
		return bankOfDepositId;
	}

	public void setBankOfDepositId(long bankOfDepositId) {
		this.bankOfDepositId = bankOfDepositId;
	}

	public String getBankOfDepositName() {
		return bankOfDepositName;
	}

	public void setBankOfDepositName(String bankOfDepositName) {
		this.bankOfDepositName = bankOfDepositName;
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

	public String getSecCode() {
		return secCode;
	}

	public void setSecCode(String secCode) {
		this.secCode = secCode;
	}

	public long getSecId() {
		return secId;
	}

	public void setSecId(long secId) {
		this.secId = secId;
	}

	public long getStatusID() {
		return statusID;
	}

	public void setStatusID(long statusID) {
		this.statusID = statusID;
	}

	public String getStockHolderAccountName() {
		return stockHolderAccountName;
	}

	public void setStockHolderAccountName(String stockHolderAccountName) {
		this.stockHolderAccountName = stockHolderAccountName;
	}

	public long getTransactionTypeId() {
		return transactionTypeId;
	}

	public void setTransactionTypeId(long transactionTypeId) {
		this.transactionTypeId = transactionTypeId;
	}

	public String getTransactionTypeName() {
		return transactionTypeName;
	}

	public void setTransactionTypeName(String transactionTypeName) {
		this.transactionTypeName = transactionTypeName;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}
}