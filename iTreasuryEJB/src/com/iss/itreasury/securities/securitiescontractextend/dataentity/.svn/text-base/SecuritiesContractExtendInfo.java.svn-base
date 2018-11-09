package com.iss.itreasury.securities.securitiescontractextend.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;

import com.iss.itreasury.securities.util.SECBaseDataEntity;

/**
 *
 * @Name:        SecuritiesContractExtendInfo.java
 * @Description: 委托理财展期合同
 * @Author :     gqfang
 * @Create Date: 2005-4-20
 * 
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class SecuritiesContractExtendInfo extends SECBaseDataEntity implements Serializable
{
	private long id                    =  -1; //唯一标示
	private long officeId              =  -1; //办事处
	private long currencyId            =  -1; //币种
	private String code                =  ""; //合同号
	private long applyContractId       =  -1;//原合同ID
	private long serialNo              =  -1;//展期序列号
	private double balance             =  0.00; //展期合同余额
	private double amount              =  0.00; //展期合同金额
	private Timestamp extendStartDate  =  null; //展期起始日
	private Timestamp extendEndDate    =  null; //展期到期日
	private long term                  =  -1;//展期期限
	private double rate                =  0.00;//展期预期收益利率
	private String extendReason        =  "";//展期原因
	private String otherRemark         =  "";//其他事项
	private long nextCheckUserId       =  -1;//下一级审核人
	private long isLowLevel            =  -1;//是否走最少审核级次的流程
	private String checkNotes          =  "";//审核批语
	private long nextCheckLevel        =  -1;//下一个审核级别
	private long statusId              =  -1;//展期合同的状态
	private long inputUserId           =  -1;//录入人
	private Timestamp inputDate        =  null;//录入日期
	private Timestamp timeStamp        =  null;//时间戳
	private long updateUserId          =  -1;//修改人
	private Timestamp updateDate       =  null;//修改日期
	private long checkUserId           =  -1;//审核人
	private Timestamp checkDate        =  null;//审核日期	
	private long transactionTypeId     = -1;//交易类型
	
	private long clientId              = -1; //业务单位ID
	private String clientName          = ""; //业务单位名称
	private long counterpartId         = -1; //交易对手，券商，委托方，存款行，发行人
	private String counterpartName     = ""; //交易对手名称
	private String counterpartCode     = ""; //交易对手编号
	private String inputUserName       = ""; //录入人姓名
	private String nextCheckUserName   = ""; //下一级审核人姓名
	private String	lastCheckUserName  = ""; //最后审核人姓名
	private long userCheckLevel        = -1; //当前用户的审核级别
	private long planDetailCount       = -1; //当前版本的值行计划明细条数
	
	
	/**
	 * @return
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param l
	 */
	public void setId(long id) {
		this.id = id;
		putUsedField("id", id);
	}
	
	/**
	 * @return Returns the amount.
	 */
	public double getAmount()
	{
		return amount;
	}
	/**
	 * @param amount The amount to set.
	 */
	public void setAmount(double amount)
	{
		this.amount = amount;
		putUsedField("amount", amount);
	}
	/**
	 * @return Returns the applyContractId.
	 */
	public long getApplyContractId()
	{
		return applyContractId;
	}
	/**
	 * @param applyContractId The applyContractId to set.
	 */
	public void setApplyContractId(long applyContractId)
	{
		this.applyContractId = applyContractId;
		putUsedField("applyContractId", applyContractId);
	}
	/**
	 * @return Returns the balance.
	 */
	public double getBalance()
	{
		return balance;
	}
	/**
	 * @param balance The balance to set.
	 */
	public void setBalance(double balance)
	{
		this.balance = balance;
		putUsedField("balance", balance);
	}
	/**
	 * @return Returns the checkDate.
	 */
	public Timestamp getCheckDate()
	{
		return checkDate;
	}
	/**
	 * @param checkDate The checkDate to set.
	 */
	public void setCheckDate(Timestamp checkDate)
	{
		this.checkDate = checkDate;
		putUsedField("checkDate", checkDate);
	}
	/**
	 * @return Returns the checkNotes.
	 */
	public String getCheckNotes()
	{
		return checkNotes;
	}
	/**
	 * @param checkNotes The checkNotes to set.
	 */
	public void setCheckNotes(String checkNotes)
	{
		this.checkNotes = checkNotes;
		putUsedField("checkNotes", checkNotes);
	}
	/**
	 * @return Returns the checkUserId.
	 */
	public long getCheckUserId()
	{
		return checkUserId;
	}
	/**
	 * @param checkUserId The checkUserId to set.
	 */
	public void setCheckUserId(long checkUserId)
	{
		this.checkUserId = checkUserId;
		putUsedField("checkUserId", checkUserId);
	}
	/**
	 * @return Returns the code.
	 */
	public String getCode()
	{
		return code;
	}
	/**
	 * @param code The code to set.
	 */
	public void setCode(String code)
	{
		this.code = code;
		putUsedField("code", code);
	}
	/**
	 * @return Returns the currencyId.
	 */
	public long getCurrencyId()
	{
		return currencyId;
	}
	/**
	 * @param currencyId The currencyId to set.
	 */
	public void setCurrencyId(long currencyId)
	{
		this.currencyId = currencyId;
		putUsedField("currencyId", currencyId);
	}
	/**
	 * @return Returns the extendEndDate.
	 */
	public Timestamp getExtendEndDate()
	{
		return extendEndDate;
	}
	/**
	 * @param extendEndDate The extendEndDate to set.
	 */
	public void setExtendEndDate(Timestamp extendEndDate)
	{
		this.extendEndDate = extendEndDate;
		putUsedField("extendEndDate", extendEndDate);
	}
	/**
	 * @return Returns the extendReason.
	 */
	public String getExtendReason()
	{
		return extendReason;
	}
	/**
	 * @param extendReason The extendReason to set.
	 */
	public void setExtendReason(String extendReason)
	{
		this.extendReason = extendReason;
		putUsedField("extendReason", extendReason);
	}
	/**
	 * @return Returns the extendStartDate.
	 */
	public Timestamp getExtendStartDate()
	{
		return extendStartDate;
	}
	/**
	 * @param extendStartDate The extendStartDate to set.
	 */
	public void setExtendStartDate(Timestamp extendStartDate)
	{
		this.extendStartDate = extendStartDate;
		putUsedField("extendStartDate", extendStartDate);
	}
	/**
	 * @return Returns the inputDate.
	 */
	public Timestamp getInputDate()
	{
		return inputDate;
	}
	/**
	 * @param inputDate The inputDate to set.
	 */
	public void setInputDate(Timestamp inputDate)
	{
		this.inputDate = inputDate;
		putUsedField("inputDate", inputDate);
	}
	/**
	 * @return Returns the inputUserID.
	 */
	public long getInputUserId()
	{
		return inputUserId;
	}
	/**
	 * @param inputUserID The inputUserID to set.
	 */
	public void setInputUserId(long inputUserId)
	{
		this.inputUserId = inputUserId;
		putUsedField("inputUserId", inputUserId);
	}
	/**
	 * @return Returns the isLowLevel.
	 */
	public long getIsLowLevel()
	{
		return isLowLevel;
	}
	/**
	 * @param isLowLevel The isLowLevel to set.
	 */
	public void setIsLowLevel(long isLowLevel)
	{
		this.isLowLevel = isLowLevel;
		putUsedField("isLowLevel", isLowLevel);
	}
	/**
	 * @return Returns the nextCheckLevel.
	 */
	public long getNextCheckLevel()
	{
		return nextCheckLevel;
	}
	/**
	 * @param nextCheckLevel The nextCheckLevel to set.
	 */
	public void setNextCheckLevel(long nextCheckLevel)
	{
		this.nextCheckLevel = nextCheckLevel;
		putUsedField("nextCheckLevel", nextCheckLevel);
	}
	/**
	 * @return Returns the nextCheckUserId.
	 */
	public long getNextCheckUserId()
	{
		return nextCheckUserId;
	}
	/**
	 * @param nextCheckUserId The nextCheckUserId to set.
	 */
	public void setNextCheckUserId(long nextCheckUserId)
	{
		this.nextCheckUserId = nextCheckUserId;
		putUsedField("nextCheckUserId", nextCheckUserId);
	}
	/**
	 * @return Returns the officeId.
	 */
	public long getOfficeId()
	{
		return officeId;
	}
	/**
	 * @param officeId The officeId to set.
	 */
	public void setOfficeId(long officeId)
	{
		this.officeId = officeId;
		putUsedField("officeId", officeId);
	}
	/**
	 * @return Returns the otherRemark.
	 */
	public String getOtherRemark()
	{
		return otherRemark;
	}
	/**
	 * @param otherRemark The otherRemark to set.
	 */
	public void setOtherRemark(String otherRemark)
	{
		this.otherRemark = otherRemark;
		putUsedField("otherRemark", otherRemark);
	}
	/**
	 * @return Returns the rate.
	 */
	public double getRate()
	{
		return rate;
	}
	/**
	 * @param rate The rate to set.
	 */
	public void setRate(double rate)
	{
		this.rate = rate;
		putUsedField("rate", rate);
	}
	/**
	 * @return Returns the serialNo.
	 */
	public long getSerialNo()
	{
		return serialNo;
	}
	/**
	 * @param serialNo The serialNo to set.
	 */
	public void setSerialNo(long serialNo)
	{
		this.serialNo = serialNo;
		putUsedField("serialNo", serialNo);
	}
	/**
	 * @return Returns the statusId.
	 */
	public long getStatusId()
	{
		return statusId;
	}
	/**
	 * @param statusId The statusId to set.
	 */
	public void setStatusId(long statusId)
	{
		this.statusId = statusId;
		putUsedField("statusId", statusId);
	}
	/**
	 * @return Returns the term.
	 */
	public long getTerm()
	{
		return term;
	}
	/**
	 * @param term The term to set.
	 */
	public void setTerm(long term)
	{
		this.term = term;
		putUsedField("term", term);
	}
	/**
	 * @return Returns the timeStamp.
	 */
	public Timestamp getTimeStamp()
	{
		return timeStamp;
	}
	/**
	 * @param timeStamp The timeStamp to set.
	 */
	public void setTimeStamp(Timestamp timeStamp)
	{
		this.timeStamp = timeStamp;
		putUsedField("timeStamp", timeStamp);
	}
	/**
	 * @return Returns the updateDate.
	 */
	public Timestamp getUpdateDate()
	{
		return updateDate;
	}
	/**
	 * @param updateDate The updateDate to set.
	 */
	public void setUpdateDate(Timestamp updateDate)
	{
		this.updateDate = updateDate;
		putUsedField("updateDate", updateDate);
	}
	/**
	 * @return Returns the updateUserID.
	 */
	public long getUpdateUserId()
	{
		return updateUserId;
	}
	/**
	 * @param updateUserID The updateUserID to set.
	 */
	public void setUpdateUserId(long updateUserId)
	{
		this.updateUserId = updateUserId;
		putUsedField("updateUserId", updateUserId);
	}
	/**
	 * @return Returns the transactionTypeId.
	 */
	public long getTransactionTypeId()
	{
		return transactionTypeId;
	}
	/**
	 * @param transactionTypeId The transactionTypeId to set.
	 */
	public void setTransactionTypeId(long transactionTypeId)
	{
		this.transactionTypeId = transactionTypeId;
		putUsedField("transactionTypeId", transactionTypeId);
	}
	/**
	 * @return Returns the clientId.
	 */
	public long getClientId()
	{
		return clientId;
	}
	/**
	 * @param clientId The clientId to set.
	 */
	public void setClientId(long clientId)
	{
		this.clientId = clientId;
	}
	/**
	 * @return Returns the counterpartId.
	 */
	public long getCounterpartId()
	{
		return counterpartId;
	}
	/**
	 * @param counterpartId The counterpartId to set.
	 */
	public void setCounterpartId(long counterpartId)
	{
		this.counterpartId = counterpartId;
	}
	/**
	 * @return Returns the inputUserName.
	 */
	public String getInputUserName()
	{
		return inputUserName;
	}
	/**
	 * @param inputUserName The inputUserName to set.
	 */
	public void setInputUserName(String inputUserName)
	{
		this.inputUserName = inputUserName;
	}
	/**
	 * @return Returns the lastCheckUserName.
	 */
	public String getLastCheckUserName()
	{
		return lastCheckUserName;
	}
	/**
	 * @param lastCheckUserName The lastCheckUserName to set.
	 */
	public void setLastCheckUserName(String lastCheckUserName)
	{
		this.lastCheckUserName = lastCheckUserName;
	}
	/**
	 * @return Returns the nextCheckUserName.
	 */
	public String getNextCheckUserName()
	{
		return nextCheckUserName;
	}
	/**
	 * @param nextCheckUserName The nextCheckUserName to set.
	 */
	public void setNextCheckUserName(String nextCheckUserName)
	{
		this.nextCheckUserName = nextCheckUserName;
	}
	/**
	 * @return Returns the planDetailCount.
	 */
	public long getPlanDetailCount()
	{
		return planDetailCount;
	}
	/**
	 * @param planDetailCount The planDetailCount to set.
	 */
	public void setPlanDetailCount(long planDetailCount)
	{
		this.planDetailCount = planDetailCount;
	}
	/**
	 * @return Returns the userCheckLevel.
	 */
	public long getUserCheckLevel()
	{
		return userCheckLevel;
	}
	/**
	 * @param userCheckLevel The userCheckLevel to set.
	 */
	public void setUserCheckLevel(long userCheckLevel)
	{
		this.userCheckLevel = userCheckLevel;
	}
	/**
	 * @return Returns the clientName.
	 */
	public String getClientName()
	{
		return clientName;
	}
	/**
	 * @param clientName The clientName to set.
	 */
	public void setClientName(String clientName)
	{
		this.clientName = clientName;
	}
	/**
	 * @return Returns the counterpartCode.
	 */
	public String getCounterpartCode()
	{
		return counterpartCode;
	}
	/**
	 * @param counterpartCode The counterpartCode to set.
	 */
	public void setCounterpartCode(String counterpartCode)
	{
		this.counterpartCode = counterpartCode;
	}
	/**
	 * @return Returns the counterpartName.
	 */
	public String getCounterpartName()
	{
		return counterpartName;
	}
	/**
	 * @param counterpartName The counterpartName to set.
	 */
	public void setCounterpartName(String counterpartName)
	{
		this.counterpartName = counterpartName;
	}
}