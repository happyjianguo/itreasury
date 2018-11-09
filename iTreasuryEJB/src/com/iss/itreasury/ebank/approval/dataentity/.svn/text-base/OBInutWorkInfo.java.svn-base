package com.iss.itreasury.ebank.approval.dataentity;

import java.sql.Date;
import java.sql.Timestamp;

import com.iss.itreasury.ebank.util.SessionOB;
import com.iss.itreasury.system.approval.dataentity.InutApprovalWorkInfo;
import com.iss.itreasury.util.Constant;


public class OBInutWorkInfo extends InutApprovalWorkInfo
{
	//审批关联信息及查询条件信息
	private long id = -1; 										//记录ID	
	private long officeID = -1;									//办事处ID
	private long clientID = -1;                                 //客户ID
    private long currencyID = -1;								//币种ID	
    private long moduleID = Constant.ModuleType.SETTLEMENT;		//模块标示 
	private long actionID = -1;									//操作标识
	private long approvalEntryID = -1;   						//审批实例id
	private String linkUrl = "";								//审批链接地址
	private long userID = -1;									//待办人		
	private long queryPurpose = -1;								//查询目的（查询待办、已办、办结）
	
	//交易业务信息
	private String transNo = ""; ////交易号
	private long transactionTypeID = -1; ////交易类型
	private Timestamp interestStart = null; ////交易起息日
	private Timestamp execute = null; ////交易执行日
	private long statusID = -1; ////交易状态
	private long inputUserID = -1; ////交易录入人
	private double payAmount = 0.0; ////交易付款金额
	private double receiveAmount = 0.0; ////交易收款金额
	private long bankID = -1; ////开户行ID
	private long contractID = -1; ///合同号ID
	private long loanFormID = -1; ///通知单号ID
	private String depositNo = ""; ///存单号
	private long payAccountID = -1;//付款方账户ID
	private String payAccountNo = ""; //付款方账户编码
	private long receiveAccountID = -1; //收款方账户ID
	private String receiveAccountNo = ""; ////收款方账户编码
	private String payClientName = ""; ////付款方客户名称
	private String receiveClientName = ""; ////收款方客户编码	
	private String bankAccountCode = "";////银行账号
	private Date budgetStartDate = null;//用款计划开始日期
	private Date budgetEndDate = null;//用款计划结束日期	
	private long nremittype=-1;       //汇款方式
	
	private SessionOB sessionMng = null;		//网银session	
	
	private Timestamp dtExecuteFrom = null; //执行日由
	private Timestamp dtExecuteTo = null; //执行日到
	
	private InutApprovalWorkInfo inutWorkInfo = null;//inut info
	
	 
	private long financeinstrID = -1;			//网银交易表ID
	
	
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
