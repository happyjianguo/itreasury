package com.iss.itreasury.ebank.approval.dataentity;

import java.sql.Date;
import java.sql.Timestamp;

import com.iss.itreasury.util.ITreasuryBaseDataEntity;


public class OBInutResultInfo extends ITreasuryBaseDataEntity
{

 
	//在使用翻页时，不能对父类的属性赋值
	private long id = -1;
	 
	//private long hisID = -1;			//任务id
	//private long taskID = -1;			//任务id
	private long entryID = -1;			//审批实例id	
	private long actionCode = -1;		//审批动作code
	private long stepCode = -1;			//审批环节code
	private String stepName = "";		//审批环节名称
	private String wfDefineName = "";	//审批流程名称
	private String owner = "";			//上一级审批人
	 
	private long payAccountID = -1;//付款方账户ID
	private long receiveAccountID = -1; //收款方账户ID
	private long financeinstrID = -1;			//网银交易表ID
	private long transactionTypeID = -1; ////交易类型
	private long inputUserID = -1; ////交易录入人
	private Timestamp execute = null; ////交易执行日
	private Timestamp budgetStartDate = null; //用款计划开始日期
	private Timestamp budgetEndDate = null;  //用款计划结束日期
	private long nremittype=-1;       //汇款方式
	private long fixedInterestToAccountID = -1;//定期续存利息账户
	
	
	private double payAmount = 0.0; ////交易付款金额
	//private double receiveAmount = 0.0; ////交易收款金额
	
	private String linkUrl = "";
	
	

//	public long getHisID()
//	{
//	
//		return hisID;
//	}
//
//
//
//
//
//	
//	public void setHisID(long hisID)
//	{
//	
//		this.hisID = hisID;
//		putUsedField("hisID", hisID);
//	}


	public String getLinkUrl()
	{
	
		return linkUrl;
	}




	
	public void setLinkUrl(String linkUrl)
	{
	
		this.linkUrl = linkUrl;
		putUsedField("linkUrl", linkUrl);
	}




	public double getPayAmount()
	{
	
		return payAmount;
	}



	
	public void setPayAmount(double payAmount)
	{
	
		this.payAmount = payAmount;
		 putUsedField("payAmount", payAmount);
	}



	
//	public double getReceiveAmount()
//	{
//	
//		return receiveAmount;
//	}
//
//
//
//	
//	public void setReceiveAmount(double receiveAmount)
//	{
//	
//		this.receiveAmount = receiveAmount;
//		 putUsedField("receiveAmount", receiveAmount);
//	}



	public long getPayAccountID()
	{
	
		return payAccountID;
	}


	
	public void setPayAccountID(long payAccountID)
	{
	
		this.payAccountID = payAccountID;
		 putUsedField("payAccountID", payAccountID);
	}


	
	public long getReceiveAccountID()
	{
	
		return receiveAccountID;
	}


	
	public void setReceiveAccountID(long receiveAccountID)
	{
	
		this.receiveAccountID = receiveAccountID;
		putUsedField("receiveAccountID", receiveAccountID);
	}


	public long getId()
	{
	
		return id;
	}

	
	public void setId(long id)
	{
	
		this.id = id;
		 putUsedField("ID", id);
	}

	public long getActionCode()
	{
	
		return actionCode;
	}
	
	public void setActionCode(long actionCode)
	{
	
		this.actionCode = actionCode;
		putUsedField("actionCode", actionCode);
	}
	
	public long getEntryID()
	{
	
		return entryID;
	}
	
	public void setEntryID(long entryID)
	{
	
		this.entryID = entryID;
		 putUsedField("entryID", entryID);
	}
	
	public Timestamp getExecute()
	{
	
		return execute;
	}
	
	public void setExecute(Timestamp execute)
	{
	
		this.execute = execute;
		 putUsedField("execute", execute);
	}
	
	public long getFinanceinstrID()
	{
	
		return financeinstrID;
	}
	
	public void setFinanceinstrID(long financeinstrID)
	{
	
		this.financeinstrID = financeinstrID;
		 putUsedField("financeinstrID", financeinstrID);
	}
	
	public long getInputUserID()
	{
	
		return inputUserID;
	}
	
	public void setInputUserID(long inputUserID)
	{
	
		this.inputUserID = inputUserID;
		 putUsedField("inputUserID", inputUserID);
	}
	
	public String getOwner()
	{
	
		return owner;
	}
	
	public void setOwner(String owner)
	{
	
		this.owner = owner;
		 putUsedField("owner", owner);
	}
	
	public long getStepCode()
	{
	
		return stepCode;
		
	}
	
	public void setStepCode(long stepCode)
	{
	
		this.stepCode = stepCode;
		 putUsedField("stepCode", stepCode);
	}
	
	public String getStepName()
	{
	
		return stepName;
	}
	
	public void setStepName(String stepName)
	{
	
		this.stepName = stepName;
		 putUsedField("stepName", stepName);
	}
	
//	public long getTaskID()
//	{
//	
//		return taskID;
//	}
//	
//	public void setTaskID(long taskID)
//	{
//	
//		this.taskID = taskID;
//		 putUsedField("taskID", taskID);
//	}
	
	public long getTransactionTypeID()
	{
	
		return transactionTypeID;
	}
	
	public void setTransactionTypeID(long transactionTypeID)
	{
	
		this.transactionTypeID = transactionTypeID;
		 putUsedField("transactionTypeID", transactionTypeID);
	}
	
	public String getWfDefineName()
	{
	
		return wfDefineName;
	}
	
	public void setWfDefineName(String wfDefineName)
	{
	
		this.wfDefineName = wfDefineName;
		 putUsedField("wfDefineName", wfDefineName);
	}






	
	public long getFixedInterestToAccountID()
	{
	
		return fixedInterestToAccountID;
	}






	
	public void setFixedInterestToAccountID(long fixedInterestToAccountID)
	{
	
		this.fixedInterestToAccountID = fixedInterestToAccountID;
		putUsedField("fixedInterestToAccountID", fixedInterestToAccountID);
	}





	public Timestamp getBudgetEndDate() {
		return budgetEndDate;
	}





	public void setBudgetEndDate(Timestamp budgetEndDate) {
		this.budgetEndDate = budgetEndDate;
	}





	public Timestamp getBudgetStartDate() {
		return budgetStartDate;
	}





	public void setBudgetStartDate(Timestamp budgetStartDate) {
		this.budgetStartDate = budgetStartDate;
	}





	public long getNremittype() {
		return nremittype;
	}





	public void setNremittype(long nremittype) {
		this.nremittype = nremittype;
	}
	
	
}
