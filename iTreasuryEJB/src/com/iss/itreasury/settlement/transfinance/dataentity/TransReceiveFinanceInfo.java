/*
 * Created on 2006-03-20
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */

package com.iss.itreasury.settlement.transfinance.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;



/**
 * @author feiye 融资租凭存款交易的--还款--实体类：
 *         1、所有变量都为Private,设置只能用setXXX方法，得到只能用getXXX方法。 2、包含变量、类型、默认值、说明 To
 *         change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */

public class TransReceiveFinanceInfo implements Serializable
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

	//具体的融资租赁业务(收款)
	private long ContractID = -1; //合同ID（loan_contractForm表）
	private long ReceiveFormID = -1; //收款通知单ID(loan_AssureChargeForm表)
	private long ReceviceBailAccountID = -1; //收保证金账户ID	(有可能从贷款来，如果没有则结算填入相应的值)
	private long PayBailAccountID = -1; //付保证金账户ID
	private long PayBailBankID = -1; //付保证金银行ID
	private double BailAmount = 0.0; //收取保证金金额
	private long IsBailInterest = -1; //是否计息(保证金账户)		----值得注意一下
	private long PayPoundageAccountID = -1; //付手续费账户ID
	private long PayPoundageBankID = -1; //付手续费银行ID
	private double PoundageAmount = 0.0; //收取手续费金额
	
	private long InterestAccountID=-1;//记息账户ID
	
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
	
	//add by yunchang 
	//date 2010-06-28 18:48
	private double surplusRecognizanceAmount = 0d; //收款通知单余额
	
		
	public double getSurplusRecognizanceAmount() {
		return surplusRecognizanceAmount;
	}
	public void setSurplusRecognizanceAmount(double surplusRecognizanceAmount) {
		this.surplusRecognizanceAmount = surplusRecognizanceAmount;
	}
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
	 * @return Returns the bailAmount.
	 */
	public double getBailAmount() {
		return BailAmount;
	}
	/**
	 * @param bailAmount The bailAmount to set.
	 */
	public void setBailAmount(double bailAmount) {
		BailAmount = bailAmount;
	}
	/**
	 * @return Returns the cashFlowDesc.
	 */
	public String getCashFlowDesc() {
		return CashFlowDesc;
	}
	/**
	 * @param cashFlowDesc The cashFlowDesc to set.
	 */
	public void setCashFlowDesc(String cashFlowDesc) {
		this.CashFlowDesc = cashFlowDesc;
	}
	/**
	 * @return Returns the cashFlowID.
	 */
	public long getCashFlowID() {
		return CashFlowID;
	}
	/**
	 * @param cashFlowID The cashFlowID to set.
	 */
	public void setCashFlowID(long cashFlowID) {
		this.CashFlowID = cashFlowID;
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
		this.CheckAbstract = checkAbstract;
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
		this.CheckUserID = checkUserID;
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
		this.CheckUserName = checkUserName;
	}
	/**
	 * @return Returns the contractBailAmount.
	 */
	public double getContractBailAmount() {
		return ContractBailAmount;
	}
	/**
	 * @param contractBailAmount The contractBailAmount to set.
	 */
	public void setContractBailAmount(double contractBailAmount) {
		this.ContractBailAmount = contractBailAmount;
	}
	/**
	 * @return Returns the contractBailAmountForWS.
	 */
	public double getContractBailAmountForWS() {
		return ContractBailAmountForWS;
	}
	/**
	 * @param contractBailAmountForWS The contractBailAmountForWS to set.
	 */
	public void setContractBailAmountForWS(double contractBailAmountForWS) {
		this.ContractBailAmountForWS = contractBailAmountForWS;
	}
	/**
	 * @return Returns the contractBailAmountForYS.
	 */
	public double getContractBailAmountForYS() {
		return ContractBailAmountForYS;
	}
	/**
	 * @param contractBailAmountForYS The contractBailAmountForYS to set.
	 */
	public void setContractBailAmountForYS(double contractBailAmountForYS) {
		this.ContractBailAmountForYS = contractBailAmountForYS;
	}
	/**
	 * @return Returns the contractCode.
	 */
	public String getContractCode() {
		return ContractCode;
	}
	/**
	 * @param contractCode The contractCode to set.
	 */
	public void setContractCode(String contractCode) {
		this.ContractCode = contractCode;
	}
	/**
	 * @return Returns the contractFinanceClientID.
	 */
	public long getContractFinanceClientID() {
		return ContractFinanceClientID;
	}
	/**
	 * @param contractFinanceClientID The contractFinanceClientID to set.
	 */
	public void setContractFinanceClientID(long contractFinanceClientID) {
		this.ContractFinanceClientID = contractFinanceClientID;
	}
	/**
	 * @return Returns the contractFinanceClientName.
	 */
	public String getContractFinanceClientName() {
		return ContractFinanceClientName;
	}
	/**
	 * @param contractFinanceClientName The contractFinanceClientName to set.
	 */
	public void setContractFinanceClientName(String contractFinanceClientName) {
		this.ContractFinanceClientName = contractFinanceClientName;
	}
	/**
	 * @return Returns the contractFinanceEndDate.
	 */
	public Timestamp getContractFinanceEndDate() {
		return ContractFinanceEndDate;
	}
	/**
	 * @param contractFinanceEndDate The contractFinanceEndDate to set.
	 */
	public void setContractFinanceEndDate(Timestamp contractFinanceEndDate) {
		this.ContractFinanceEndDate = contractFinanceEndDate;
	}
	/**
	 * @return Returns the contractFinanceRate.
	 */
	public double getContractFinanceRate() {
		return ContractFinanceRate;
	}
	/**
	 * @param contractFinanceRate The contractFinanceRate to set.
	 */
	public void setContractFinanceRate(double contractFinanceRate) {
		this.ContractFinanceRate = contractFinanceRate;
	}
	/**
	 * @return Returns the contractFinanceStartDate.
	 */
	public Timestamp getContractFinanceStartDate() {
		return ContractFinanceStartDate;
	}
	/**
	 * @param contractFinanceStartDate The contractFinanceStartDate to set.
	 */
	public void setContractFinanceStartDate(Timestamp contractFinanceStartDate) {
		this.ContractFinanceStartDate = contractFinanceStartDate;
	}
	/**
	 * @return Returns the contractFinanceTerm.
	 */
	public long getContractFinanceTerm() {
		return ContractFinanceTerm;
	}
	/**
	 * @param contractFinanceTerm The contractFinanceTerm to set.
	 */
	public void setContractFinanceTerm(long contractFinanceTerm) {
		this.ContractFinanceTerm = contractFinanceTerm;
	}
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
		this.ContractID = contractID;
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
		this.CurrencyID = currencyID;
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
		this.ExecuteDate = executeDate;
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
		this.ID = id;
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
		this.InputDate = inputDate;
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
		this.InputUserID = inputUserID;
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
		this.InputUserName = inputUserName;
	}
	/**
	 * @return Returns the instructionNo.
	 */
	public String getInstructionNo() {
		return InstructionNo;
	}
	/**
	 * @param instructionNo The instructionNo to set.
	 */
	public void setInstructionNo(String instructionNo) {
		this.InstructionNo = instructionNo;
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
		this.InterestStartDate = interestStartDate;
	}
	/**
	 * @return Returns the isBailInterest.
	 */
	public long getIsBailInterest() {
		return IsBailInterest;
	}
	/**
	 * @param isBailInterest The isBailInterest to set.
	 */
	public void setIsBailInterest(long isBailInterest) {
		this.IsBailInterest = isBailInterest;
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
		this.ModifyDate = modifyDate;
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
		this.OfficeID = officeID;
	}
	/**
	 * @return Returns the payBailAccountID.
	 */
	public long getPayBailAccountID() {
		return PayBailAccountID;
	}
	/**
	 * @param payBailAccountID The payBailAccountID to set.
	 */
	public void setPayBailAccountID(long payBailAccountID) {
		this.PayBailAccountID = payBailAccountID;
	}
	/**
	 * @return Returns the payBailBankID.
	 */
	public long getPayBailBankID() {
		return PayBailBankID;
	}
	/**
	 * @param payBailBankID The payBailBankID to set.
	 */
	public void setPayBailBankID(long payBailBankID) {
		this.PayBailBankID = payBailBankID;
	}
	/**
	 * @return Returns the payPoundageAccountID.
	 */
	public long getPayPoundageAccountID() {
		return PayPoundageAccountID;
	}
	/**
	 * @param payPoundageAccountID The payPoundageAccountID to set.
	 */
	public void setPayPoundageAccountID(long payPoundageAccountID) {
		this.PayPoundageAccountID = payPoundageAccountID;
	}
	/**
	 * @return Returns the payPoundageBankID.
	 */
	public long getPayPoundageBankID() {
		return PayPoundageBankID;
	}
	/**
	 * @param payPoundageBankID The payPoundageBankID to set.
	 */
	public void setPayPoundageBankID(long payPoundageBankID) {
		this.PayPoundageBankID = payPoundageBankID;
	}
	/**
	 * @return Returns the poundageAmount.
	 */
	public double getPoundageAmount() {
		return PoundageAmount;
	}
	/**
	 * @param poundageAmount The poundageAmount to set.
	 */
	public void setPoundageAmount(double poundageAmount) {
		this.PoundageAmount = poundageAmount;
	}
	/**
	 * @return Returns the rate.
	 */
	public double getRate() {
		return Rate;
	}
	/**
	 * @param rate The rate to set.
	 */
	public void setRate(double rate) {
		this.Rate = rate;
	}
	/**
	 * @return Returns the receiveFormCode.
	 */
	public String getReceiveFormCode() {
		return ReceiveFormCode;
	}
	/**
	 * @param receiveFormCode The receiveFormCode to set.
	 */
	public void setReceiveFormCode(String receiveFormCode) {
		this.ReceiveFormCode = receiveFormCode;
	}
	/**
	 * @return Returns the receiveFormDate.
	 */
	public Timestamp getReceiveFormDate() {
		return ReceiveFormDate;
	}
	/**
	 * @param receiveFormDate The receiveFormDate to set.
	 */
	public void setReceiveFormDate(Timestamp receiveFormDate) {
		this.ReceiveFormDate = receiveFormDate;
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
		this.ReceiveFormID = receiveFormID;
	}
	/**
	 * @return Returns the receviceBailAccountID.
	 */
	public long getReceviceBailAccountID() {
		return ReceviceBailAccountID;
	}
	/**
	 * @param receviceBailAccountID The receviceBailAccountID to set.
	 */
	public void setReceviceBailAccountID(long receviceBailAccountID) {
		this.ReceviceBailAccountID = receviceBailAccountID;
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
		this.StatusID = statusID;
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
		this.TransactionTypeID = transactionTypeID;
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
		this.TransNo = transNo;
	}
	public String getContractFinanceClientCode() {
		return ContractFinanceClientCode;
	}
	public void setContractFinanceClientCode(String contractFinanceClientCode) {
		this.ContractFinanceClientCode = contractFinanceClientCode;
	}
	public long getInterestAccountID() {
		return InterestAccountID;
	}
	public void setInterestAccountID(long interestAccountID) {
		this.InterestAccountID = interestAccountID;
	}
}