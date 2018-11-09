/*
 * AheadRepayNoticeInfo.java
 *
 * Created on 2003年2月12日
 */

package com.iss.itreasury.loan.aheadrepaynotice.dataentity;

import java.sql.*;

import com.iss.itreasury.system.approval.dataentity.InutParameterInfo;
import com.iss.itreasury.util.*;

/**
 *
 * @author  yfan
 * @version 
 */
public class AheadRepayNoticeInfo implements java.io.Serializable
{

	/** Creates new AheadRepayNoticeInfo */
	public AheadRepayNoticeInfo()
	{
		super();
	}

	private long ID = -1; //提前还款通知单ID标识
	private String Code = ""; //提前还款通知单编号
	private double Amount = 0; //提前还款金额
	private long CurrencyID = -1; //币种
	private long OfficeID = -1; //办事处标示
	private long StatusID = -1; //状态
	private String Status = "";

	private long InputUserID = -1; //录入人标示
	private String InputUserName = ""; //录入人名称
	private Timestamp InputDate; //录入时间

	private long NextCheckUserID = -1; //下一个审核人标示
	private long NextCheckLevel = -1;	//下一个审核级别
	private long LsReviewUserID = -1; //最后审核人ID
	private String LsReviewUserName = ""; //最后审核人名称
	private long ReviewStatusID = -1; //最后审核状态

	private long ContractID = -1; //合同标识
	private String ContractCode = ""; //合同编号
	private double ContractAmount = 0; //合同金额
	private double ContractBalance = 0; //合同余额
	
	private long ClientID = -1; //借款单位标识
	private String ClientName = ""; //借款单位名称
	
	private long consignClientID =-1;//委托单位ID
	private String consignClientName= ""; //委托单位名称
	
	private long LoanID = -1; //贷款标识
	private long IntervalNum = 0; //贷款期限

    private long PlanID = -1; //计划标示
    
	private long LetoutNoticeID = -1; //放款通知单标识
	private String LetoutNoticeCode = ""; //放款通知单编号
	private double LetoutNoticeAmount = 0; //放款通知单金额
	private double LetoutNoticeRate = 0; //放款通知单利率
	private long LetoutNoticeIntervalNum = 0; //贷款期限
	private double LetoutNoticeBalance = 0; //放款通知单余额
	
	private long IsAhead = -1;				//是否提前还款

	private long PageCount = 0; //记录数
	
	private long loanType = -1;//贷款类型
	private String loanTypeName=""; //贷款子类型名称
	
	private long loanSubType = -1;
	
	private Timestamp PBackDate; //还款日期
	private double balanceAmount; //利息金额
	private long ApprovalPersonID;//审核人ID
	private String ApprovalPersonName; //审核人名称
	
	InutParameterInfo inutParameterInfo = null;
	
	private long BatchID  = -1;//批量还款ID
	public long getBatchID() {
		return BatchID;
	}

	public void setBatchID(long batchID) {
		BatchID = batchID;
	}

	/**
	 * @return
	 */
	public double getAmount()
	{
		return Amount;
	}

	public String getFormatAmount()
	{
		return DataFormat.formatDisabledAmount(Amount);
	}

	/**
	 * @return
	 */
	public long getClientID()
	{
		return ClientID;
	}

	/**
	 * @return
	 */
	public String getClientName()
	{
		return ClientName;
	}

	/**
	 * @return
	 */
	public String getCode()
	{
		return Code;
	}

	/**
	 * @return
	 */
	public double getContractAmount()
	{
		return ContractAmount;
	}

	/**
	 * @return
	 */
	public String getContractCode()
	{
		return ContractCode;
	}

	/**
	 * @return
	 */
	public long getContractID()
	{
		return ContractID;
	}

	/**
	 * @return
	 */
	public long getCurrencyID()
	{
		return CurrencyID;
	}

	/**
	 * @return
	 */
	public long getID()
	{
		return ID;
	}

	/**
	 * @return
	 */
	public Timestamp getInputDate()
	{
		return InputDate;
	}

	public String getFormatInputDate()
	{
		return DataFormat.formatDate(InputDate);
	}

	/**
	 * @return
	 */
	public long getInputUserID()
	{
		return InputUserID;
	}

	/**
	 * @return
	 */
	public String getInputUserName()
	{
		return InputUserName;
	}

	/**
	 * @return
	 */
	public double getLetoutNoticeAmount()
	{
		return LetoutNoticeAmount;
	}

	public String getFormatLetoutNoticeAmount()
	{
		return DataFormat.formatDisabledAmount(LetoutNoticeAmount);
	}

	/**
	 * @return
	 */
	public double getLetoutNoticeBalance()
	{
		return LetoutNoticeBalance;
	}

	/**
	 * @return
	 */
	public String getLetoutNoticeCode()
	{
		return LetoutNoticeCode;
	}

	/**
	 * @return
	 */
	public long getLetoutNoticeID()
	{
		return LetoutNoticeID;
	}

	/**
	 * @return
	 */
	public long getLetoutNoticeIntervalNum()
	{
		return LetoutNoticeIntervalNum;
	}

	/**
	 * @return
	 */
	public double getLetoutNoticeRate()
	{
		return LetoutNoticeRate;
	}

	/**
	 * @return
	 */
	public long getLoanID()
	{
		return LoanID;
	}

	/**
	 * @return
	 */
	public long getLsReviewUserID()
	{
		return LsReviewUserID;
	}

	/**
	 * @return
	 */
	public String getLsReviewUserName()
	{
		return LsReviewUserName;
	}

	/**
	 * @return
	 */
	public long getNextCheckUserID()
	{
		return NextCheckUserID;
	}

	/**
	 * @return
	 */
	public long getOfficeID()
	{
		return OfficeID;
	}

	/**
	 * @return
	 */
	public long getReviewStatusID()
	{
		return ReviewStatusID;
	}

	/**
	 * @return
	 */
	public long getStatusID()
	{
		return StatusID;
	}

	/**
	 * @param d
	 */
	public void setAmount(double d)
	{
		Amount = d;
	}

	/**
	 * @param l
	 */
	public void setClientID(long l)
	{
		ClientID = l;
	}

	/**
	 * @param string
	 */
	public void setClientName(String string)
	{
		ClientName = string;
	}

	/**
	 * @param string
	 */
	public void setCode(String string)
	{
		Code = string;
	}

	/**
	 * @param d
	 */
	public void setContractAmount(double d)
	{
		ContractAmount = d;
	}

	/**
	 * @param string
	 */
	public void setContractCode(String string)
	{
		ContractCode = string;
	}

	/**
	 * @param l
	 */
	public void setContractID(long l)
	{
		ContractID = l;
	}

	/**
	 * @param l
	 */
	public void setCurrencyID(long l)
	{
		CurrencyID = l;
	}

	/**
	 * @param l
	 */
	public void setID(long l)
	{
		ID = l;
	}

	/**
	 * @param timestamp
	 */
	public void setInputDate(Timestamp timestamp)
	{
		InputDate = timestamp;
	}

	/**
	 * @param l
	 */
	public void setInputUserID(long l)
	{
		InputUserID = l;
	}

	/**
	 * @param string
	 */
	public void setInputUserName(String string)
	{
		InputUserName = string;
	}

	/**
	 * @param d
	 */
	public void setLetoutNoticeAmount(double d)
	{
		LetoutNoticeAmount = d;
	}

	/**
	 * @param d
	 */
	public void setLetoutNoticeBalance(double d)
	{
		LetoutNoticeBalance = d;
	}

	/**
	 * @param string
	 */
	public void setLetoutNoticeCode(String string)
	{
		LetoutNoticeCode = string;
	}

	/**
	 * @param l
	 */
	public void setLetoutNoticeID(long l)
	{
		LetoutNoticeID = l;
	}

	/**
	 * @param l
	 */
	public void setLetoutNoticeIntervalNum(long l)
	{
		LetoutNoticeIntervalNum = l;
	}

	/**
	 * @param d
	 */
	public void setLetoutNoticeRate(double d)
	{
		LetoutNoticeRate = d;
	}

	/**
	 * @param l
	 */
	public void setLoanID(long l)
	{
		LoanID = l;
	}

	/**
	 * @param l
	 */
	public void setLsReviewUserID(long l)
	{
		LsReviewUserID = l;
	}

	/**
	 * @param string
	 */
	public void setLsReviewUserName(String string)
	{
		LsReviewUserName = string;
	}

	/**
	 * @param l
	 */
	public void setNextCheckUserID(long l)
	{
		NextCheckUserID = l;
	}

	/**
	 * @param l
	 */
	public void setOfficeID(long l)
	{
		OfficeID = l;
	}

	/**
	 * @param l
	 */
	public void setReviewStatusID(long l)
	{
		ReviewStatusID = l;
	}

	/**
	 * @param l
	 */
	public void setStatusID(long l)
	{
		StatusID = l;
	}

	/**
	 * @return
	 */
	public String getStatus()
	{
		return Status;
	}

	/**
	 * @param string
	 */
	public void setStatus(String string)
	{
		Status = string;
	}

	/**
	 * @return
	 */
	public long getIntervalNum()
	{
		return IntervalNum;
	}

	/**
	 * @param l
	 */
	public void setIntervalNum(long l)
	{
		IntervalNum = l;
	}

	/**
	 * @return
	 */
	public double getContractBalance()
	{
		return ContractBalance;
	}

	/**
	 * @param d
	 */
	public void setContractBalance(double d)
	{
		ContractBalance = d;
	}

	/**
	 * @return
	 */
	public long getPageCount()
	{
		return PageCount;
	}

	/**
	 * @param l
	 */
	public void setPageCount(long l)
	{
		PageCount = l;
	}

    /**
     * function 得到/设置计划标示
     * return long
     */
    public long getPlanID()
    {
        return PlanID;
    }

    /**
     * @param l
     * function 得到/设置计划标示
     * return void
     */
    public void setPlanID(long l)
    {
        this.PlanID = l;
    }

    /**
     * @return Returns the nextCheckLevel.
     */
    public long getNextCheckLevel()
    {
        return NextCheckLevel;
    }
    /**
     * @param nextCheckLevel The nextCheckLevel to set.
     */
    public void setNextCheckLevel(long nextCheckLevel)
    {
        NextCheckLevel = nextCheckLevel;
    }

	public long getIsAhead() {
		return IsAhead;
	}

	public void setIsAhead(long isAhead) {
		IsAhead = isAhead;
	}

	public long getLoanSubType() {
		return loanSubType;
	}

	public void setLoanSubType(long loanSubType) {
		this.loanSubType = loanSubType;
	}

	public long getLoanType() {
		return loanType;
	}

	public void setLoanType(long loanType) {
		this.loanType = loanType;
	}

	public InutParameterInfo getInutParameterInfo() {
		return inutParameterInfo;
	}

	public void setInutParameterInfo(InutParameterInfo inutParameterInfo) {
		this.inutParameterInfo = inutParameterInfo;
	}

	public long getApprovalPersonID() {
		return ApprovalPersonID;
	}

	public void setApprovalPersonID(long approvalPersonID) {
		ApprovalPersonID = approvalPersonID;
	}

	public String getApprovalPersonName() {
		return ApprovalPersonName;
	}

	public void setApprovalPersonName(String approvalPersonName) {
		ApprovalPersonName = approvalPersonName;
	}

	public double getBalanceAmount() {
		return balanceAmount;
	}

	public void setBalanceAmount(double balanceAmount) {
		this.balanceAmount = balanceAmount;
	}

	public Timestamp getPBackDate() {
		return PBackDate;
	}

	public void setPBackDate(Timestamp backDate) {
		PBackDate = backDate;
	}

	public long getConsignClientID() {
		return consignClientID;
	}

	public void setConsignClientID(long consignClientID) {
		this.consignClientID = consignClientID;
	}

	public String getConsignClientName() {
		return consignClientName;
	}

	public void setConsignClientName(String consignClientName) {
		this.consignClientName = consignClientName;
	}

	public String getLoanTypeName() {
		return loanTypeName;
	}

	public void setLoanTypeName(String loanTypeName) {
		this.loanTypeName = loanTypeName;
	}
}
