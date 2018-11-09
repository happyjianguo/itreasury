package com.iss.itreasury.ebank.obdrawnotice.dataentity;

import java.io.Serializable;
import java.sql.*;
import com.iss.itreasury.loan.contract.dataentity.*;
/**
 * @author yanhuang
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class DrawNoticeInfo implements Serializable {
	private long ID = -1; //银团提款标识
	private String Code = ""; //代码，提款通知单编号
	private double DrawAmount = 0; //本期总提款金额
	private double DrawAgentAmount = 0; //本期代理费总计
	private long ContractID = -1; //合同ID	
	private String ContractCode = ""; //合同编号 
	private String ClientName = ""; //贷款单位
	private String ClientCode = ""; //贷款单位号
	private Timestamp LoanStart = null;
	private Timestamp LoanEnd = null;
	private double ExamineAmount = 0;
	private double ContractAmount = 0; //合同金额
	private double AgentRate = 0; //手续费率
	private long StatusID = -1; //提款通知单状态
	private long InputUserID = -1; //录入用户ID
	private String InputUserName = ""; //录入人
	private Timestamp InputDate = null; //录入日期，提交日期
	private long NextCheckUserID = -1; //下一个审核人ID
	private String CheckUserName = ""; //审核人
	private long Count = -1; // 记录数
	private long PageCount = -1; // 记录总页数
	private YTFormatInfo YTInfo = null;
	private double UnPayAmount = 0;
	private double InterestRate = 0; //贷款执行利率
	private long IntervalNum = -1;

	/**
	 * Returns the agentRate.
	 * @return double
	 */
	public double getAgentRate() {
		return AgentRate;
	}

	/**
	 * Returns the checkUserName.
	 * @return String
	 */
	public String getCheckUserName() {
		return CheckUserName;
	}

	/**
	 * Returns the clientName.
	 * @return String
	 */
	public String getClientName() {
		return ClientName;
	}

	/**
	 * Returns the code.
	 * @return String
	 */
	public String getCode() {
		return Code;
	}

	/**
	 * Returns the contractAmount.
	 * @return double
	 */
	public double getContractAmount() {
		return ContractAmount;
	}

	/**
	 * Returns the contractCode.
	 * @return String
	 */
	public String getContractCode() {
		return ContractCode;
	}

	/**
	 * Returns the contractID.
	 * @return long
	 */
	public long getContractID() {
		return ContractID;
	}

	/**
	 * Returns the count.
	 * @return long
	 */
	public long getCount() {
		return Count;
	}

	/**
	 * Returns the drawAgentAmount.
	 * @return double
	 */
	public double getDrawAgentAmount() {
		return DrawAgentAmount;
	}

	/**
	 * Returns the drawAmount.
	 * @return double
	 */
	public double getDrawAmount() {
		return DrawAmount;
	}

	/**
	 * Returns the iD.
	 * @return long
	 */
	public long getID() {
		return ID;
	}

	/**
	 * Returns the inputDate.
	 * @return Timestamp
	 */
	public Timestamp getInputDate() {
		return InputDate;
	}

	/**
	 * Returns the inputUserID.
	 * @return long
	 */
	public long getInputUserID() {
		return InputUserID;
	}

	/**
	 * Returns the inputUserName.
	 * @return String
	 */
	public String getInputUserName() {
		return InputUserName;
	}

	/**
	 * Returns the nextCheckUserID.
	 * @return long
	 */
	public long getNextCheckUserID() {
		return NextCheckUserID;
	}

	/**
	 * Returns the pageCount.
	 * @return long
	 */
	public long getPageCount() {
		return PageCount;
	}

	/**
	 * Returns the statusID.
	 * @return long
	 */
	public long getStatusID() {
		return StatusID;
	}

	/**
	 * Sets the agentRate.
	 * @param agentRate The agentRate to set
	 */
	public void setAgentRate(double agentRate) {
		AgentRate = agentRate;
	}

	/**
	 * Sets the checkUserName.
	 * @param checkUserName The checkUserName to set
	 */
	public void setCheckUserName(String checkUserName) {
		CheckUserName = checkUserName;
	}

	/**
	 * Sets the clientName.
	 * @param clientName The clientName to set
	 */
	public void setClientName(String clientName) {
		ClientName = clientName;
	}

	/**
	 * Sets the code.
	 * @param code The code to set
	 */
	public void setCode(String code) {
		Code = code;
	}

	/**
	 * Sets the contractAmount.
	 * @param contractAmount The contractAmount to set
	 */
	public void setContractAmount(double contractAmount) {
		ContractAmount = contractAmount;
	}

	/**
	 * Sets the contractCode.
	 * @param contractCode The contractCode to set
	 */
	public void setContractCode(String contractCode) {
		ContractCode = contractCode;
	}

	/**
	 * Sets the contractID.
	 * @param contractID The contractID to set
	 */
	public void setContractID(long contractID) {
		ContractID = contractID;
	}

	/**
	 * Sets the count.
	 * @param count The count to set
	 */
	public void setCount(long count) {
		Count = count;
	}

	/**
	 * Sets the drawAgentAmount.
	 * @param drawAgentAmount The drawAgentAmount to set
	 */
	public void setDrawAgentAmount(double drawAgentAmount) {
		DrawAgentAmount = drawAgentAmount;
	}

	/**
	 * Sets the drawAmount.
	 * @param drawAmount The drawAmount to set
	 */
	public void setDrawAmount(double drawAmount) {
		DrawAmount = drawAmount;
	}

	/**
	 * Sets the iD.
	 * @param iD The iD to set
	 */
	public void setID(long iD) {
		ID = iD;
	}

	/**
	 * Sets the inputDate.
	 * @param inputDate The inputDate to set
	 */
	public void setInputDate(Timestamp inputDate) {
		InputDate = inputDate;
	}

	/**
	 * Sets the inputUserID.
	 * @param inputUserID The inputUserID to set
	 */
	public void setInputUserID(long inputUserID) {
		InputUserID = inputUserID;
	}

	/**
	 * Sets the inputUserName.
	 * @param inputUserName The inputUserName to set
	 */
	public void setInputUserName(String inputUserName) {
		InputUserName = inputUserName;
	}

	/**
	 * Sets the nextCheckUserID.
	 * @param nextCheckUserID The nextCheckUserID to set
	 */
	public void setNextCheckUserID(long nextCheckUserID) {
		NextCheckUserID = nextCheckUserID;
	}

	/**
	 * Sets the pageCount.
	 * @param pageCount The pageCount to set
	 */
	public void setPageCount(long pageCount) {
		PageCount = pageCount;
	}

	/**
	 * Sets the statusID.
	 * @param statusID The statusID to set
	 */
	public void setStatusID(long statusID) {
		StatusID = statusID;
	}

	/**
	 * Returns the unPayAmount.
	 * @return double
	 */
	public double getUnPayAmount() {
		return UnPayAmount;
	}

	/**
	 * Returns the yTInfo.
	 * @return YTFormatInfo
	 */
	public YTFormatInfo getYTInfo() {
		return YTInfo;
	}

	/**
	 * Sets the unPayAmount.
	 * @param unPayAmount The unPayAmount to set
	 */
	public void setUnPayAmount(double unPayAmount) {
		UnPayAmount = unPayAmount;
	}

	/**
	 * Sets the yTInfo.
	 * @param yTInfo The yTInfo to set
	 */
	public void setYTInfo(YTFormatInfo yTInfo) {
		YTInfo = yTInfo;
	}

	/**
	 * Returns the clientCode.
	 * @return String
	 */
	public String getClientCode() {
		return ClientCode;
	}

	/**
	 * Returns the interestRate.
	 * @return double
	 */
	public double getInterestRate() {
		return InterestRate;
	}

	/**
	 * Returns the intervalNum.
	 * @return long
	 */
	public long getIntervalNum() {
		return IntervalNum;
	}

	/**
	 * Returns the loanEnd.
	 * @return Timestamp
	 */
	public Timestamp getLoanEnd() {
		return LoanEnd;
	}

	/**
	 * Returns the loanStart.
	 * @return Timestamp
	 */
	public Timestamp getLoanStart() {
		return LoanStart;
	}

	/**
	 * Sets the clientCode.
	 * @param clientCode The clientCode to set
	 */
	public void setClientCode(String clientCode) {
		ClientCode = clientCode;
	}

	/**
	 * Sets the interestRate.
	 * @param interestRate The interestRate to set
	 */
	public void setInterestRate(double interestRate) {
		InterestRate = interestRate;
	}

	/**
	 * Sets the intervalNum.
	 * @param intervalNum The intervalNum to set
	 */
	public void setIntervalNum(long intervalNum) {
		IntervalNum = intervalNum;
	}

	/**
	 * Sets the loanEnd.
	 * @param loanEnd The loanEnd to set
	 */
	public void setLoanEnd(Timestamp loanEnd) {
		LoanEnd = loanEnd;
	}

	/**
	 * Sets the loanStart.
	 * @param loanStart The loanStart to set
	 */
	public void setLoanStart(Timestamp loanStart) {
		LoanStart = loanStart;
	}

	/**
	 * Returns the examineAmount.
	 * @return double
	 */
	
	public double getExamineAmount() {
		return ExamineAmount;
	}

	/**
	 * Sets the examineAmount.
	 * @param examineAmount The examineAmount to set
	 */
	public void setExamineAmount(double examineAmount) {
		ExamineAmount = examineAmount;
	}

}
