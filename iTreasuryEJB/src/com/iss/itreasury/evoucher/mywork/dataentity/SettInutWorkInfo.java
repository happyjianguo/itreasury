/*
 * Created on 2007-4-12
 *
 * Title:				iTreasury
 * @author             	刘琰 
 * Company:             iSoftStone
 * @version				iTreasury3.2新增
 * Description:         产品化3.2在结算,网银新增审批流,且审批流引擎采用北京研发中心的产品iNut,
 * 						为将iNut审批流与业务关联,新增关联设置功能
 */
package com.iss.itreasury.evoucher.mywork.dataentity;

import java.sql.Timestamp;
import java.util.Date;

import com.iss.itreasury.util.Constant;
import com.iss.itreasury.settlement.account.dataentity.AccountInfo;
import com.iss.itreasury.system.approval.dataentity.InutApprovalWorkInfo;

public class SettInutWorkInfo extends InutApprovalWorkInfo
{
	//审批关联信息及查询条件信息
	private long id = -1; 										//记录ID	
	private long officeID = -1;									//办事处ID
    private long currencyID = -1;								//币种ID	
    private long moduleID = Constant.ModuleType.EVOUCHER;		//模块标示 
	private long actionID = -1;									//操作标识
	private long approvalEntryID = -1;   						//审批实例id
	private String linkUrl = "";								//审批链接地址
	private long userID = -1;									//待办人		
	private long queryPurpose = -1;								//查询目的（查询待办、已办、办结）
	
	//交易业务信息
	private String nprintcontentno = ""; ////交易号
	private long nprintcontentid = -1; ////交易id
	private long transtypeid = -1; ////交易类型
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
	private String payAccountNo = ""; ////付款方账户编码
	private String receiveAccountNo = ""; ////收款方账户编码
	private String payClientName = ""; ////付款方客户名称
	private String receiveClientName = ""; ////收款方客户编码	
	private String bankAccountCode = "";////银行账户编号
	private long nclientid = -1; //客户id
	private double Receiveamount = -1; //交易金额
	private long ndeptid = -1; //部门id
	
	private String username = ""; //用户名称
	private String clientname = "";//客户名称
	
	private String billname = ""; //用户名称
	private String tempname = "";//客户名称
	
	
	private String AccountNo = ""; // 账户编号
	private long StatusID = -1; // 账户状态
	private Timestamp OpenDate = null; // 开户日期
	private long InputUserID = -1; // 录入人
	private long AccountTypeID = -1; // 账户类型
	private Timestamp ninputdate = null;//申请时间
	private String transid = ""; //审批关联
	
	//辅助信息
	private long desc = -1;
	private long orderField =-1;
	private Timestamp executeStart = null;
	private Timestamp executeEnd = null;
	//新增
	private long taskID = -1;			//任务id
	private long entryID = -1;			//审批实例id	
	private long stepID = -1;			//审批环节id
	private long stepCode = -1;			//审批环节code
	//private long actionID = -1;			//审批动作id
	private long actionCode = -1;		//审批动作code
	private String entryName = "";		//审批实例名称
	private String wfDefineName = "";	//审批流程名称
	private String stepName = "";		//审批环节名称
	private Date startDate = null;		//审批开始时间
	private Date dueDate = null;		//最迟完成时间
	private String owner = "";			//上一级审批人
	private String status = "";			//状态
	//
	private InutApprovalWorkInfo inutWorkInfo = null;//inut info
	
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


	public long getTranstypeid() {
		return transtypeid;
	}
	public void setTranstypeid(long transtypeid) {
		this.transtypeid = transtypeid;
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
	public Timestamp getExecuteEnd() {
		return executeEnd;
	}
	public void setExecuteEnd(Timestamp executeEnd) {
		this.executeEnd = executeEnd;
	}
	public Timestamp getExecuteStart() {
		return executeStart;
	}
	public void setExecuteStart(Timestamp executeStart) {
		this.executeStart = executeStart;
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
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
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

	public String getAccountNo() {
		return AccountNo;
	}
	public void setAccountNo(String accountNo) {
		AccountNo = accountNo;
	}
	public Timestamp getOpenDate() {
		return OpenDate;
	}
	public void setOpenDate(Timestamp openDate) {
		OpenDate = openDate;
	}
	public long getAccountTypeID() {
		return AccountTypeID;
	}
	public void setAccountTypeID(long accountTypeID) {
		AccountTypeID = accountTypeID;
	}
	public Timestamp getNinputdate() {
		return ninputdate;
	}
	public void setNinputdate(Timestamp ninputdate) {
		this.ninputdate = ninputdate;
	}
	public long getNprintcontentid() {
		return nprintcontentid;
	}
	public void setNprintcontentid(long nprintcontentid) {
		this.nprintcontentid = nprintcontentid;
	}
	public String getNprintcontentno() {
		return nprintcontentno;
	}
	public void setNprintcontentno(String nprintcontentno) {
		this.nprintcontentno = nprintcontentno;
	}
	public long getNclientid() {
		return nclientid;
	}
	public void setNclientid(long nclientid) {
		this.nclientid = nclientid;
	}
	public double getReceiveamount() {
		return Receiveamount;
	}
	public void setReceiveamount(double receiveamount) {
		Receiveamount = receiveamount;
	}
	public long getNdeptid() {
		return ndeptid;
	}
	public void setNdeptid(long ndeptid) {
		this.ndeptid = ndeptid;
	}
	public String getClientname() {
		return clientname;
	}
	public void setClientname(String clientname) {
		this.clientname = clientname;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getBillname() {
		return billname;
	}
	public void setBillname(String billname) {
		this.billname = billname;
	}
	public String getTempname() {
		return tempname;
	}
	public void setTempname(String tempname) {
		this.tempname = tempname;
	}
	public String getTransid() {
		return transid;
	}
	public void setTransid(String transid) {
		this.transid = transid;
	}

	
}