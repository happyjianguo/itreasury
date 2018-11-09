package com.iss.itreasury.ebank.approval.dataentity;

import java.sql.Date;
import java.sql.Timestamp;

import com.iss.itreasury.ebank.util.SessionOB;
import com.iss.itreasury.system.approval.dataentity.InutApprovalWorkInfo;
import com.iss.itreasury.util.Constant;


public class OBInutWorkInfo extends InutApprovalWorkInfo
{
	//����������Ϣ����ѯ������Ϣ
	private long id = -1; 										//��¼ID	
	private long officeID = -1;									//���´�ID
	private long clientID = -1;                                 //�ͻ�ID
    private long currencyID = -1;								//����ID	
    private long moduleID = Constant.ModuleType.SETTLEMENT;		//ģ���ʾ 
	private long actionID = -1;									//������ʶ
	private long approvalEntryID = -1;   						//����ʵ��id
	private String linkUrl = "";								//�������ӵ�ַ
	private long userID = -1;									//������		
	private long queryPurpose = -1;								//��ѯĿ�ģ���ѯ���졢�Ѱ졢��ᣩ
	
	//����ҵ����Ϣ
	private String transNo = ""; ////���׺�
	private long transactionTypeID = -1; ////��������
	private Timestamp interestStart = null; ////������Ϣ��
	private Timestamp execute = null; ////����ִ����
	private long statusID = -1; ////����״̬
	private long inputUserID = -1; ////����¼����
	private double payAmount = 0.0; ////���׸�����
	private double receiveAmount = 0.0; ////�����տ���
	private long bankID = -1; ////������ID
	private long contractID = -1; ///��ͬ��ID
	private long loanFormID = -1; ///֪ͨ����ID
	private String depositNo = ""; ///�浥��
	private long payAccountID = -1;//����˻�ID
	private String payAccountNo = ""; //����˻�����
	private long receiveAccountID = -1; //�տ�˻�ID
	private String receiveAccountNo = ""; ////�տ�˻�����
	private String payClientName = ""; ////����ͻ�����
	private String receiveClientName = ""; ////�տ�ͻ�����	
	private String bankAccountCode = "";////�����˺�
	private Date budgetStartDate = null;//�ÿ�ƻ���ʼ����
	private Date budgetEndDate = null;//�ÿ�ƻ���������	
	private long nremittype=-1;       //��ʽ
	
	private SessionOB sessionMng = null;		//����session	
	
	private Timestamp dtExecuteFrom = null; //ִ������
	private Timestamp dtExecuteTo = null; //ִ���յ�
	
	private InutApprovalWorkInfo inutWorkInfo = null;//inut info
	
	 
	private long financeinstrID = -1;			//�������ױ�ID
	
	
	public long getFinanceinstrID()
	{
	
		return financeinstrID;
	}


	
	public void setFinanceinstrID(long financeinstrID)
	{
	
		this.financeinstrID = financeinstrID;
	}


	public long getPayAccountID()
	{
	
		return payAccountID;
	}

	
	public void setPayAccountID(long payAccountID)
	{
	
		this.payAccountID = payAccountID;
	}

	
	public long getReceiveAccountID()
	{
	
		return receiveAccountID;
	}

	
	public void setReceiveAccountID(long receiveAccountID)
	{
	
		this.receiveAccountID = receiveAccountID;
	}

	
	public long getActionID() {
		return actionID;
	}
	public void setActionID(long actionID) {
		this.actionID = actionID;
	}
	public String getBankAccountCode() {
		return bankAccountCode;
	}
	public void setBankAccountCode(String bankAccountCode) {
		this.bankAccountCode = bankAccountCode;
	}
	public long getBankID() {
		return bankID;
	}
	public void setBankID(long bankID) {
		this.bankID = bankID;
	}
	public long getContractID() {
		return contractID;
	}
	public void setContractID(long contractID) {
		this.contractID = contractID;
	}
	public long getCurrencyID() {
		return currencyID;
	}
	public void setCurrencyID(long currencyID) {
		this.currencyID = currencyID;
		
	}
	public String getDepositNo() {
		return depositNo;
	}
	public void setDepositNo(String depositNo) {
		this.depositNo = depositNo;
	}
	public Timestamp getExecute() {
		return execute;
	}
	public void setExecute(Timestamp execute) {
		this.execute = execute;
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
	public Timestamp getInterestStart() {
		return interestStart;
	}
	public void setInterestStart(Timestamp interestStart) {
		this.interestStart = interestStart;
	}
	public long getLoanFormID() {
		return loanFormID;
	}
	public void setLoanFormID(long loanFormID) {
		this.loanFormID = loanFormID;
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
	public String getPayAccountNo() {
		return payAccountNo;
	}
	public void setPayAccountNo(String payAccountNo) {
		this.payAccountNo = payAccountNo;
	}
	public double getPayAmount() {
		return payAmount;
	}
	public void setPayAmount(double payAmount) {
		this.payAmount = payAmount;
	}
	public String getPayClientName() {
		return payClientName;
	}
	public void setPayClientName(String payClientName) {
		this.payClientName = payClientName;
	}
	public String getReceiveAccountNo() {
		return receiveAccountNo;
	}
	public void setReceiveAccountNo(String receiveAccountNo) {
		this.receiveAccountNo = receiveAccountNo;
	}
	public double getReceiveAmount() {
		return receiveAmount;
	}
	public void setReceiveAmount(double receiveAmount) {
		this.receiveAmount = receiveAmount;
	}
	public String getReceiveClientName() {
		return receiveClientName;
	}
	public void setReceiveClientName(String receiveClientName) {
		this.receiveClientName = receiveClientName;
	}
	public long getStatusID() {
		return statusID;
	}
	public void setStatusID(long statusID) {
		this.statusID = statusID;
	}
	public long getTransactionTypeID() {
		return transactionTypeID;
	}
	public void setTransactionTypeID(long transactionTypeID) {
		this.transactionTypeID = transactionTypeID;
	}
	public String getTransNo() {
		return transNo;
	}
	public void setTransNo(String transNo) {
		this.transNo = transNo;
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
	
	public long getClientID()
	{
	
		return clientID;
	}
	
	public void setClientID(long clientID)
	{
	
		this.clientID = clientID;
	}
	
	public SessionOB getSessionMng()
	{
	
		return sessionMng;
	}
	
	public void setSessionMng(SessionOB sessionMng)
	{
	
		this.sessionMng = sessionMng;
	}
	
	public Timestamp getDtExecuteFrom()
	{
	
		return dtExecuteFrom;
	}
	
	public void setDtExecuteFrom(Timestamp dtExecuteFrom)
	{
	
		this.dtExecuteFrom = dtExecuteFrom;
	}
	
	public Timestamp getDtExecuteTo()
	{
	
		return dtExecuteTo;
	}
	
	public void setDtExecuteTo(Timestamp dtExecuteTo)
	{
	
		this.dtExecuteTo = dtExecuteTo;
	}



	public Date getBudgetEndDate() {
		return budgetEndDate;
	}



	public void setBudgetEndDate(Date budgetEndDate) {
		this.budgetEndDate = budgetEndDate;
	}



	public Date getBudgetStartDate() {
		return budgetStartDate;
	}



	public void setBudgetStartDate(Date budgetStartDate) {
		this.budgetStartDate = budgetStartDate;
	}



	public long getNremittype() {
		return nremittype;
	}



	public void setNremittype(long nremittype) {
		this.nremittype = nremittype;
	}




	
	
}
