/*
 * Created on 2006-03-20
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */

package com.iss.itreasury.settlement.transnoteacceptance.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;



/**
 * @author feiye 商业票据承兑交易的--垫付本息收回--实体类：
 *         1、所有变量都为Private,设置只能用setXXX方法，得到只能用getXXX方法。 2、包含变量、类型、默认值、说明 To
 *         change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */

public class TransAdvancedReceviceNoteAcceptanceInfo implements Serializable
{
	//公用的信息
	private long ID = -1; //唯一标识
	private String TransNo = ""; //交易编号
	private long TransactionTypeID = -1; //交易类型（定期开立、定期支取、定期续期转存、通知开立、通知支取）
	private long OfficeID = -1; //办事处标识
	private long CurrencyID = -1; //币种标识

	private long StatusID = -1; //交易状态	
	private Timestamp ModifyDate = null; //修改时间：时分秒
	private long InputUserID = -1; //录入人ID
	private String InputUserName = ""; //录入人名称
	private long CheckUserID = -1; //复核人ID
	private String CheckUserName = ""; //复核人名称
	private long AbstractID = -1; //摘要ID
	private String Abstract = ""; //摘要
	private String CheckAbstract = ""; //复核/取消复核摘要
	
	private Timestamp InputDate = null; //录入日
	private Timestamp InterestStartDate = null; //起息日	
	private Timestamp ExecuteDate = null; //执行日

	//具体的商业票据承兑业务(到期承兑)
	private long IsAdvanced = -1; //是否到期垫付
	private long AcceptancePayAccountID = -1; //承兑付款帐户ID	申请内部帐户
	private long AcceptancePayBankID = -1; //承兑付款银行ID
	private long AcceptanceReceiveAccountID = -1; //承兑收款帐户ID
	private long AcceptanceReceiveBankID = -1; //承兑收款银行ID
	private double AcceptanceAmount = 0.0; 		//承兑金额
	
	/**
	 * @return Returns the contractID.
	 */
	public long getContractID() {
		return ContractID;
	}
	/**
	 * @param contractID The contractID to set.
	 */
	public void setContractID(long contractID) {
		ContractID = contractID;
	}
	/**
	 * @return Returns the receiveFormID.
	 */
	public long getReceiveFormID() {
		return ReceiveFormID;
	}
	/**
	 * @param receiveFormID The receiveFormID to set.
	 */
	public void setReceiveFormID(long receiveFormID) {
		ReceiveFormID = receiveFormID;
	}
	private long ContractID = -1; //合同ID（loan_contractForm表）
	private long ReceiveFormID = -1; //收款通知单ID(loan_AssureChargeForm表)
	
	/*
	private long ContractID = -1; //合同ID（loan_contractForm表）
	private long ReceiveFormID = -1; //收款通知单ID(loan_AssureChargeForm表)
	private long ReceviceBailAccountID = -1; //收保证金帐户ID	(有可能从贷款来，如果没有则结算填入相应的值)
	private long PayBailAccountID = -1; //付保证金帐户ID
	private long PayBailBankID = -1; //付保证金银行ID
	private double BailAmount = 0.0; //收取保证金金额

	//private long IsBailInterest = -1; //是否计息(保证金帐户)		----值得注意一下
	//private long PayPoundageAccountID = -1; //付手续费帐户ID
	//private long PayPoundageBankID = -1; //付手续费银行ID
	//private double PoundageAmount = 0.0; //收取手续费金额
	//private long InterestAccountID=-1;//记息帐户ID
	
	//扩展属性
	private long CashFlowID = -1; //现金流向ID
	private String CashFlowDesc = ""; //现金流向描述
	private String InstructionNo = ""; //标识非结算系统产生的流水号
	private double Rate = 0.0; //利率(保证金)
	
	//为页面增添的一些属性(合同信息，收款通知单信息)
		//合同的辅助信息
	private String ContractCode = ""; //合同编号
	private long ContractFinanceClientID = -1; //承租单位客户ID
	private String ContractFinanceClientCode = ""; //承租单位客户编号	
	private String ContractFinanceClientName = ""; //承租单位客户名称
	
	private Timestamp ContractFinanceStartDate = null; //租赁开始日期
	private Timestamp ContractFinanceEndDate = null; //租赁结束日期
	private long ContractFinanceTerm = -1;			//租凭期限
	private double ContractFinanceRate = 0.0;		//租赁利率
	private double ContractBailAmount = 0.0; 		//合同保证金金额
	
	private double ContractBailAmountForYS = 0.0; //已收保证金金额	只读	显示此合同下已收取的保证金金额
	private double ContractBailAmountForWS = 0.0; 		//未收保证金金额	只读	保证金金额-已收保证金金额
	
	//收款通知单辅助信息
	private Timestamp ReceiveFormDate = null; //收款日期	只读	收款通知单信息
	private String ReceiveFormCode = ""; //收款通知单编号	只读	收款通知单信息
	*/
		
	
	/**
	 * @return Returns the abstract.
	 */
	public String getAbstract() {
		return Abstract;
	}
	/**
	 * @param abstract1 The abstract to set.
	 */
	public void setAbstract(String abstract1) {
		Abstract = abstract1;
	}
	/**
	 * @return Returns the abstractID.
	 */
	public long getAbstractID() {
		return AbstractID;
	}
	/**
	 * @param abstractID The abstractID to set.
	 */
	public void setAbstractID(long abstractID) {
		AbstractID = abstractID;
	}
	/**
	 * @return Returns the acceptanceAmount.
	 */
	public double getAcceptanceAmount() {
		return AcceptanceAmount;
	}
	/**
	 * @param acceptanceAmount The acceptanceAmount to set.
	 */
	public void setAcceptanceAmount(double acceptanceAmount) {
		AcceptanceAmount = acceptanceAmount;
	}
	/**
	 * @return Returns the acceptancePayAccountID.
	 */
	public long getAcceptancePayAccountID() {
		return AcceptancePayAccountID;
	}
	/**
	 * @param acceptancePayAccountID The acceptancePayAccountID to set.
	 */
	public void setAcceptancePayAccountID(long acceptancePayAccountID) {
		AcceptancePayAccountID = acceptancePayAccountID;
	}
	/**
	 * @return Returns the acceptancePayBankID.
	 */
	public long getAcceptancePayBankID() {
		return AcceptancePayBankID;
	}
	/**
	 * @param acceptancePayBankID The acceptancePayBankID to set.
	 */
	public void setAcceptancePayBankID(long acceptancePayBankID) {
		AcceptancePayBankID = acceptancePayBankID;
	}
	/**
	 * @return Returns the acceptanceReceiveAccountID.
	 */
	public long getAcceptanceReceiveAccountID() {
		return AcceptanceReceiveAccountID;
	}
	/**
	 * @param acceptanceReceiveAccountID The acceptanceReceiveAccountID to set.
	 */
	public void setAcceptanceReceiveAccountID(long acceptanceReceiveAccountID) {
		AcceptanceReceiveAccountID = acceptanceReceiveAccountID;
	}
	/**
	 * @return Returns the acceptanceReceiveBankID.
	 */
	public long getAcceptanceReceiveBankID() {
		return AcceptanceReceiveBankID;
	}
	/**
	 * @param acceptanceReceiveBankID The acceptanceReceiveBankID to set.
	 */
	public void setAcceptanceReceiveBankID(long acceptanceReceiveBankID) {
		AcceptanceReceiveBankID = acceptanceReceiveBankID;
	}
	/**
	 * @return Returns the checkAbstract.
	 */
	public String getCheckAbstract() {
		return CheckAbstract;
	}
	/**
	 * @param checkAbstract The checkAbstract to set.
	 */
	public void setCheckAbstract(String checkAbstract) {
		CheckAbstract = checkAbstract;
	}
	/**
	 * @return Returns the checkUserID.
	 */
	public long getCheckUserID() {
		return CheckUserID;
	}
	/**
	 * @param checkUserID The checkUserID to set.
	 */
	public void setCheckUserID(long checkUserID) {
		CheckUserID = checkUserID;
	}
	/**
	 * @return Returns the checkUserName.
	 */
	public String getCheckUserName() {
		return CheckUserName;
	}
	/**
	 * @param checkUserName The checkUserName to set.
	 */
	public void setCheckUserName(String checkUserName) {
		CheckUserName = checkUserName;
	}
	/**
	 * @return Returns the currencyID.
	 */
	public long getCurrencyID() {
		return CurrencyID;
	}
	/**
	 * @param currencyID The currencyID to set.
	 */
	public void setCurrencyID(long currencyID) {
		CurrencyID = currencyID;
	}
	/**
	 * @return Returns the executeDate.
	 */
	public Timestamp getExecuteDate() {
		return ExecuteDate;
	}
	/**
	 * @param executeDate The executeDate to set.
	 */
	public void setExecuteDate(Timestamp executeDate) {
		ExecuteDate = executeDate;
	}
	/**
	 * @return Returns the iD.
	 */
	public long getID() {
		return ID;
	}
	/**
	 * @param id The iD to set.
	 */
	public void setID(long id) {
		ID = id;
	}
	/**
	 * @return Returns the inputDate.
	 */
	public Timestamp getInputDate() {
		return InputDate;
	}
	/**
	 * @param inputDate The inputDate to set.
	 */
	public void setInputDate(Timestamp inputDate) {
		InputDate = inputDate;
	}
	/**
	 * @return Returns the inputUserID.
	 */
	public long getInputUserID() {
		return InputUserID;
	}
	/**
	 * @param inputUserID The inputUserID to set.
	 */
	public void setInputUserID(long inputUserID) {
		InputUserID = inputUserID;
	}
	/**
	 * @return Returns the inputUserName.
	 */
	public String getInputUserName() {
		return InputUserName;
	}
	/**
	 * @param inputUserName The inputUserName to set.
	 */
	public void setInputUserName(String inputUserName) {
		InputUserName = inputUserName;
	}
	/**
	 * @return Returns the interestStartDate.
	 */
	public Timestamp getInterestStartDate() {
		return InterestStartDate;
	}
	/**
	 * @param interestStartDate The interestStartDate to set.
	 */
	public void setInterestStartDate(Timestamp interestStartDate) {
		InterestStartDate = interestStartDate;
	}
	/**
	 * @return Returns the isAdvanced.
	 */
	public long getIsAdvanced() {
		return IsAdvanced;
	}
	/**
	 * @param isAdvanced The isAdvanced to set.
	 */
	public void setIsAdvanced(long isAdvanced) {
		IsAdvanced = isAdvanced;
	}
	/**
	 * @return Returns the modifyDate.
	 */
	public Timestamp getModifyDate() {
		return ModifyDate;
	}
	/**
	 * @param modifyDate The modifyDate to set.
	 */
	public void setModifyDate(Timestamp modifyDate) {
		ModifyDate = modifyDate;
	}
	/**
	 * @return Returns the officeID.
	 */
	public long getOfficeID() {
		return OfficeID;
	}
	/**
	 * @param officeID The officeID to set.
	 */
	public void setOfficeID(long officeID) {
		OfficeID = officeID;
	}
	/**
	 * @return Returns the statusID.
	 */
	public long getStatusID() {
		return StatusID;
	}
	/**
	 * @param statusID The statusID to set.
	 */
	public void setStatusID(long statusID) {
		StatusID = statusID;
	}
	/**
	 * @return Returns the transactionTypeID.
	 */
	public long getTransactionTypeID() {
		return TransactionTypeID;
	}
	/**
	 * @param transactionTypeID The transactionTypeID to set.
	 */
	public void setTransactionTypeID(long transactionTypeID) {
		TransactionTypeID = transactionTypeID;
	}
	/**
	 * @return Returns the transNo.
	 */
	public String getTransNo() {
		return TransNo;
	}
	/**
	 * @param transNo The transNo to set.
	 */
	public void setTransNo(String transNo) {
		TransNo = transNo;
	}
}