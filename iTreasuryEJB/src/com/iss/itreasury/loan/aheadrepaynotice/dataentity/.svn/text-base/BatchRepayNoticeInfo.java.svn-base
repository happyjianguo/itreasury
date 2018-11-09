/*
 * BatchRepayNoticeInfo.java
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
public class BatchRepayNoticeInfo implements java.io.Serializable
{

	/** Creates new AheadRepayNoticeInfo */
	public BatchRepayNoticeInfo()
	{
		super();
	}

	private long ID = -1; //批量还款通知单ID标识
	private String sCode = ""; //批量还款通知单编号
	private String rePayID = "";//还款单ID
	private double mAmount = 0; //批量还款金额
	private long nCurrencyID = -1; //币种
	private long nOfficeID = -1; //办事处标示
	private long nStatusID = -1; //状态
	private String Status = "";

	private long nInputUserID = -1; //录入人标示
	private String InputUserName = ""; //录入人名称
	private Timestamp dtInputDate; //录入时间
	private Timestamp PayDate; //还款日期
	private long nContractID = -1; //合同标识
	
	
	
	private String ContractCode = ""; //合同编号
	private double ContractAmount = 0; //合同金额
	private double ContractBalance = 0; //合同余额
	private long ClientID = -1; //借款单位标识
	private String ClientName = ""; //借款单位名称
	private long LoanID = -1; //贷款标识
	private long IntervalNum = 0; //贷款期限
   
	private long LetoutNoticeID = -1; //放款通知单标识
	private String LetoutNoticeCode = ""; //放款通知单编号
	private double LetoutNoticeAmount = 0; //放款通知单金额
	private double LetoutNoticeRate = 0; //放款通知单利率
	private long LetoutNoticeIntervalNum = 0; //贷款期限
	private double LetoutNoticeBalance = 0; //放款通知单余额
	
	private long nIsAhead = -1;				//是否提前还款

	private long PageCount = 0; //记录数
	
	private long loanType = -1;//贷款类型
	
	private long loanSubType = -1;
	
	
	private double balanceAmount; //利息金额

	private long nisrepayinterest; //是否归还利息
	
	private double minterest; //批量还款总利息
	
	InutParameterInfo inutParameterInfo = null;
	/**
	 * @return
	 */
	public double getAmount()
	{
		return mAmount;
	}

	public String getFormatAmount()
	{
		return DataFormat.formatDisabledAmount(mAmount);
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
		return nContractID;
	}

	/**
	 * @return
	 */
	public long getCurrencyID()
	{
		return nCurrencyID;
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
		return dtInputDate;
	}

	public String getFormatInputDate()
	{
		return DataFormat.formatDate(dtInputDate);
	}

	/**
	 * @return
	 */
	public long getInputUserID()
	{
		return nInputUserID;
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
	public long getOfficeID()
	{
		return nOfficeID;
	}

	/**
	 * @return
	 */
	public long getStatusID()
	{
		return nStatusID;
	}

	/**
	 * @param d
	 */
	public void setAmount(double d)
	{
		mAmount = d;
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
		sCode = string;
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
		nContractID = l;
	}

	/**
	 * @param l
	 */
	public void setCurrencyID(long l)
	{
		nCurrencyID = l;
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
		dtInputDate = timestamp;
	}

	/**
	 * @param l
	 */
	public void setInputUserID(long l)
	{
		nInputUserID = l;
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
	public void setOfficeID(long l)
	{
		nOfficeID = l;
	}

	/**
	 * @param l
	 */
	public void setStatusID(long l)
	{
		nStatusID = l;
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

	public long getIsAhead() {
		return nIsAhead;
	}

	public void setIsAhead(long isAhead) {
		nIsAhead = isAhead;
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

	public double getBalanceAmount() {
		return balanceAmount;
	}

	public void setBalanceAmount(double balanceAmount) {
		this.balanceAmount = balanceAmount;
	}

	public Timestamp getPBackDate() {
		return PayDate;
	}

	public void setPBackDate(Timestamp backDate) {
		PayDate = backDate;
	}

	public String getSCode() {
		return sCode;
	}

	public void setSCode(String code) {
		sCode = code;
	}

	public String getRePayID() {
		return rePayID;
	}

	public void setRePayID(String rePayID) {
		this.rePayID = rePayID;
	}

	public long getNisrepayinterest() {
		return nisrepayinterest;
	}

	public void setNisrepayinterest(long nisrepayinterest) {
		this.nisrepayinterest = nisrepayinterest;
	}

	public double getMinterest() {
		return minterest;
	}

	public void setMinterest(double minterest) {
		this.minterest = minterest;
	}
}
