/*
 * Created on 2003-9-2
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.settlement.account.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;

import com.iss.itreasury.settlement.base.SettlementBaseDataEntity;
import com.iss.itreasury.system.approval.dataentity.InutParameterInfo;

/**
 * @author yiwang
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class AccountInfo extends SettlementBaseDataEntity  implements Serializable
{
	public static void main(String[] args)
	{
	}
	private long AccountID = -1; // 账户ID
	private String AccountNo = ""; // 账户编号
	private long OfficeID = -1; // 办事处
	private long CurrencyID = -1; //币种
	private long AccountTypeID = -1; // 账户类型
	private long AccountGroupTypeID = -1; // 账户类型
	private long ClientID = -1; // 客户
	private String AccountName = ""; // 账户名称
	private Timestamp OpenDate = null; // 开户日期
	private long InputUserID = -1; // 录入人
	private Timestamp InputDate = null; // 录入日期
	private long CheckUserID = -1; // 复核人
	private Timestamp CheckDate = null; // 复核日期
	private long CheckStatusID = -1; // 复核状态
	private long StatusID = -1; // 账户状态
	private Timestamp FinishDate = null;//主账户清户日期
	private String Abstact = ""; // 摘要
	private String Subject = ""; // 对应科目号
	//累计发放金额限制
	private double MonthSumAmount = 0.0; //累计发放金额
	private double DaySumAmount = 0.0;//日累计金额
	private double MaxSinglePayAmount = 0.0; // 单笔最高金额限制
	private double MinSinglePayAmount = 0.0; // 单笔最低金额限制
	private Timestamp StartDate = null; // 开始日期
	private Timestamp EndDate = null; // 结束日期
	private long BatchUpdateID = -1;//批量复核序号
	private long BatchUpdateTypeID = -1;//批量复核内容
	private InutParameterInfo inutParameterInfo = null;
	private AccountExtendInfo accountExtendInfo = null; //Account类的扩展
	private String AccountTypeCode="";//账户类型编码 add by kevin(刘连凯) 2011-08-05
	
	

	public String getAccountTypeCode() {
		return AccountTypeCode;
	}

	public void setAccountTypeCode(String accountTypeCode) {
		AccountTypeCode = accountTypeCode;
	}

	public InutParameterInfo getInutParameterInfo() {
		return inutParameterInfo;
	}

	public void setInutParameterInfo(InutParameterInfo inutParameterInfo) {
		this.inutParameterInfo = inutParameterInfo;
	}

	/**
	 * Returns the abstact.
	 * @return String
	 */
	public String getAbstact()
	{
		return Abstact;
	}

	/**
	 * Returns the accountID.
	 * @return long
	 */
	public long getAccountID()
	{
		return AccountID;
	}

	/**
	 * Returns the accountName.
	 * @return String
	 */
	public String getAccountName()
	{
		return AccountName;
	}

	/**
	 * Returns the accountNo.
	 * @return String
	 */
	public String getAccountNo()
	{
		return AccountNo;
	}

	/**
	 * Returns the accountTypeID.
	 * @return long
	 */
	public long getAccountTypeID()
	{
		return AccountTypeID;
	}


	/**
	 * Returns the checkDate.
	 * @return Timestamp
	 */
	public Timestamp getCheckDate()
	{
		return CheckDate;
	}

	/**
	 * Returns the checkStatusID.
	 * @return long
	 */
	public long getCheckStatusID()
	{
		return CheckStatusID;
	}

	/**
	 * Returns the checkUserID.
	 * @return long
	 */
	public long getCheckUserID()
	{
		return CheckUserID;
	}

	/**
	 * Returns the clientID.
	 * @return long
	 */
	public long getClientID()
	{
		return ClientID;
	}

	/**
	 * Returns the currencyID.
	 * @return long
	 */
	public long getCurrencyID()
	{
		return CurrencyID;
	}

	/**
	 * Returns the endDate.
	 * @return Timestamp
	 */
	public Timestamp getEndDate()
	{
		return EndDate;
	}

	/**
	 * Returns the inputDate.
	 * @return Timestamp
	 */
	public Timestamp getInputDate()
	{
		return InputDate;
	}

	/**
	 * Returns the inputUserID.
	 * @return long
	 */
	public long getInputUserID()
	{
		return InputUserID;
	}

	/**
	 * Returns the maxSinglePayAmount.
	 * @return double
	 */
	public double getMaxSinglePayAmount()
	{
		return MaxSinglePayAmount;
	}


	/**
	 * Returns the minSinglePayAmount.
	 * @return double
	 */
	public double getMinSinglePayAmount()
	{
		return MinSinglePayAmount;
	}

	/**
	 * Returns the officeID.
	 * @return long
	 */
	public long getOfficeID()
	{
		return OfficeID;
	}

	/**
	 * Returns the openDate.
	 * @return Timestamp
	 */
	public Timestamp getOpenDate()
	{
		return OpenDate;
	}

	/**
	 * Returns the startDate.
	 * @return Timestamp
	 */
	public Timestamp getStartDate()
	{
		return StartDate;
	}

	/**
	 * Returns the statusID.
	 * @return long
	 */
	public long getStatusID()
	{
		return StatusID;
	}

	/**
	 * Returns the subject.
	 * @return String
	 */
	public String getSubject()
	{
		return Subject;
	}

	/**
	 * Sets the abstact.
	 * @param abstact The abstact to set
	 */
	public void setAbstact(String abstact)
	{
		Abstact = abstact;
	}

	/**
	 * Sets the accountID.
	 * @param accountID The accountID to set
	 */
	public void setAccountID(long accountID)
	{
		AccountID = accountID;
	}

	/**
	 * Sets the accountName.
	 * @param accountName The accountName to set
	 */
	public void setAccountName(String accountName)
	{
		AccountName = accountName;
	}

	/**
	 * Sets the accountNo.
	 * @param accountNo The accountNo to set
	 */
	public void setAccountNo(String accountNo)
	{
		AccountNo = accountNo;
	}

	/**
	 * Sets the accountTypeID.
	 * @param accountTypeID The accountTypeID to set
	 */
	public void setAccountTypeID(long accountTypeID)
	{
		AccountTypeID = accountTypeID;
	}

	/**
	 * Sets the checkDate.
	 * @param checkDate The checkDate to set
	 */
	public void setCheckDate(Timestamp checkDate)
	{
		CheckDate = checkDate;
	}

	/**
	 * Sets the checkStatusID.
	 * @param checkStatusID The checkStatusID to set
	 */
	public void setCheckStatusID(long checkStatusID)
	{
		CheckStatusID = checkStatusID;
	}

	/**
	 * Sets the checkUserID.
	 * @param checkUserID The checkUserID to set
	 */
	public void setCheckUserID(long checkUserID)
	{
		CheckUserID = checkUserID;
	}

	/**
	 * Sets the clientID.
	 * @param clientID The clientID to set
	 */
	public void setClientID(long clientID)
	{
		ClientID = clientID;
	}

	/**
	 * Sets the currencyID.
	 * @param currencyID The currencyID to set
	 */
	public void setCurrencyID(long currencyID)
	{
		CurrencyID = currencyID;
	}

	/**
	 * Sets the endDate.
	 * @param endDate The endDate to set
	 */
	public void setEndDate(Timestamp endDate)
	{
		EndDate = endDate;
	}

	/**
	 * Sets the inputDate.
	 * @param inputDate The inputDate to set
	 */
	public void setInputDate(Timestamp inputDate)
	{
		InputDate = inputDate;
	}

	/**
	 * Sets the inputUserID.
	 * @param inputUserID The inputUserID to set
	 */
	public void setInputUserID(long inputUserID)
	{
		InputUserID = inputUserID;
	}

	/**
	 * Sets the maxSinglePayAmount.
	 * @param maxSinglePayAmount The maxSinglePayAmount to set
	 */
	public void setMaxSinglePayAmount(double maxSinglePayAmount)
	{
		MaxSinglePayAmount = maxSinglePayAmount;
	}

	/**
	 * Sets the minSinglePayAmount.
	 * @param minSinglePayAmount The minSinglePayAmount to set
	 */
	public void setMinSinglePayAmount(double minSinglePayAmount)
	{
		MinSinglePayAmount = minSinglePayAmount;
	}

	/**
	 * Sets the officeID.
	 * @param officeID The officeID to set
	 */
	public void setOfficeID(long officeID)
	{
		OfficeID = officeID;
	}

	/**
	 * Sets the openDate.
	 * @param openDate The openDate to set
	 */
	public void setOpenDate(Timestamp openDate)
	{
		OpenDate = openDate;
	}

	/**
	 * Sets the startDate.
	 * @param startDate The startDate to set
	 */
	public void setStartDate(Timestamp startDate)
	{
		StartDate = startDate;
	}

	/**
	 * Sets the statusID.
	 * @param statusID The statusID to set
	 */
	public void setStatusID(long statusID)
	{
		StatusID = statusID;
	}

	/**
	 * Sets the subject.
	 * @param subject The subject to set
	 */
	public void setSubject(String subject)
	{
		Subject = subject;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		StringBuffer result = new StringBuffer(255);
		result.append("Abstact=");
		result.append(this.getAbstact());
		result.append("\r\n");
		result.append("AccountID=");
		result.append(this.getAccountID());
		result.append("\r\n");
		result.append("AccountName=");
		result.append(this.getAccountName());
		result.append("\r\n");
		result.append("AccountNo=");
		result.append(this.getAccountNo());
		result.append("\r\n");
		result.append("AccountTypeID=");
		result.append(this.getAccountTypeID());
		result.append("\r\n");
		result.append("CheckDate=");
		result.append(this.getCheckDate());
		result.append("\r\n");
		result.append("CheckStatusID=");
		result.append(this.getCheckStatusID());
		result.append("\r\n");
		result.append("CheckUserID=");
		result.append(this.getCheckUserID());
		result.append("\r\n");
		result.append("ClientID=");
		result.append(this.getClientID());
		result.append("\r\n");
		result.append("CurrencyID=");
		result.append(this.getCurrencyID());
		result.append("\r\n");
		result.append("EndDate=");
		result.append(this.getEndDate());
		result.append("\r\n");
		result.append("InputDate=");
		result.append(this.getInputDate());
		result.append("\r\n");
		result.append("InputUserID=");
		result.append(this.getInputUserID());
		result.append("\r\n");
		result.append("MaxSinglePayAmount=");
		result.append(this.getMaxSinglePayAmount());
		result.append("\r\n");
		result.append("MinSinglePayAmount=");
		result.append(this.getMinSinglePayAmount());
		result.append("\r\n");
		result.append("OfficeID=");
		result.append(this.getOfficeID());
		result.append("\r\n");
		result.append("OpenDate=");
		result.append(this.getOpenDate());
		result.append("\r\n");
		result.append("StartDate=");
		result.append(this.getStartDate());
		result.append("\r\n");
		result.append("StatusID=");
		result.append(this.getStatusID());
		result.append("\r\n");
		result.append("Subject=");
		result.append(this.getSubject());
		result.append("\r\n");
		
		return result.toString();
	}

	/**
	 * @return
	 */
	public long getBatchUpdateID()
	{
		return BatchUpdateID;
	}

	/**
	 * @param l
	 */
	public void setBatchUpdateID(long l)
	{
		BatchUpdateID = l;
	}

	/**
	 * @return
	 */
	public long getBatchUpdateTypeID()
	{
		return BatchUpdateTypeID;
	}

	/**
	 * @param l
	 */
	public void setBatchUpdateTypeID(long l)
	{
		BatchUpdateTypeID = l;
	}

	/**
	 * @return
	 */
	public Timestamp getFinishDate()
	{
		return FinishDate;
	}

	/**
	 * @param timestamp
	 */
	public void setFinishDate(Timestamp timestamp)
	{
		FinishDate = timestamp;
	}

	/**
	 * @return
	 */
	public double getMonthSumAmount()
	{
		return MonthSumAmount;
	}

	/**
	 * @param d
	 */
	public void setMonthSumAmount(double d)
	{
		MonthSumAmount = d;
	}

	public AccountExtendInfo getAccountExtendInfo() {
		return accountExtendInfo;
	}

	public void setAccountExtendInfo(AccountExtendInfo accountExtendInfo) {
		this.accountExtendInfo = accountExtendInfo;
	}

	public double getDaySumAmount() {
		return DaySumAmount;
	}

	public void setDaySumAmount(double daySumAmount) {
		DaySumAmount = daySumAmount;
	}

	/**
	 * @return the accountGroupTypeID
	 */
	public long getAccountGroupTypeID() {
		return AccountGroupTypeID;
	}

	/**
	 * @param accountGroupTypeID the accountGroupTypeID to set
	 */
	public void setAccountGroupTypeID(long accountGroupTypeID) {
		this.AccountGroupTypeID = accountGroupTypeID;
	}

}
