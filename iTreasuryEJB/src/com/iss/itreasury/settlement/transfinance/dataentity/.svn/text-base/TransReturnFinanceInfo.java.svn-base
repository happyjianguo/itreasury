/*
 * Created on 2006-03-20
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.settlement.transfinance.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;

import com.iss.itreasury.system.approval.dataentity.InutParameterInfo;



/**
 * @author feiye 融资租凭存款交易的--还款--实体类：
 *         1、所有变量都为Private,设置只能用setXXX方法，得到只能用getXXX方法。 2、包含变量、类型、默认值、说明 To
 *         change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */

public class TransReturnFinanceInfo implements Serializable
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
	private Timestamp CheckDate = null; //复核日
	
	private Timestamp InterestStartDate = null; //起息日	  (可考虑有没有)
	private Timestamp ExecuteDate = null; //执行日

	//具体的融资租赁业务(收款)
	private long ContractID = -1; //合同ID（loan_contractForm表）
	private long ReturnFormID = -1; //还款通知单ID(loan_leaseholdrepayform表)
	private long ReturnCorpusAccountID = -1; //还本金账户ID
	private long ReturnCorpusBankID = -1; //还本金银行ID
	private double CorpusAmount = 0.0; //本次还本金金额
	private long ReturnInterestAccountID = -1; //还利息账户ID
	private long ReturnInterestBankID = -1; //还利息银行ID
	private double InterestAmount = 0.0; //本次还利息金额
	private long ReturnBailAccountID = -1; //扣除保证金账户ID
	private double BailAmount = 0.0; //本次扣除保证金金额
	
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

	//比收款多一个合同下的租金
	private double ContractHireAmount = 0.0; 		//合同租金金额
	private double ContractHireAmountForYS = 0.0; //已收租金金额	只读	显示此合同下已收取的租金金额
	private double ContractHireAmountForWS = 0.0; 		//未收租金金额	只读	租金金额-已收租金金额
	//Added by zwsun, 2007-06-20, 审批流
	private InutParameterInfo inutParameterInfo=null;
	
	//added by xiong fei 2010-07-19
	private long issue = 0;//融资租赁还款单的期数
	private long loanContractPlanDetailID = 0;//还款计划明细ID
	private long isDelay = 0;//是否延付
	
	/*
	 * add by yunchang
	 * date 2010-08-19
	 * function 累计已经收取保证金金额（结算--贷款--融资租赁还款--业务处理）
	 */
	private double ContractHireAmountForYSALL = 0.0d;//累计已经收取保证金金额
	
	//还款时必须知道收款通知单的ID才可以操作
	private long ReceiveFormID = -1;		//收款通知单ID
	
	public String getAbstract() {
		return Abstract;
	}
	public void setAbstract(String abstract1) {
		this.Abstract = abstract1;
	}
	public long getAbstractID() {
		return AbstractID;
	}
	public void setAbstractID(long abstractID) {
		this.AbstractID = abstractID;
	}
	public double getBailAmount() {
		return BailAmount;
	}
	public void setBailAmount(double bailAmount) {
		this.BailAmount = bailAmount;
	}
	public String getCashFlowDesc() {
		return CashFlowDesc;
	}
	public void setCashFlowDesc(String cashFlowDesc) {
		this.CashFlowDesc = cashFlowDesc;
	}
	public long getCashFlowID() {
		return CashFlowID;
	}
	public void setCashFlowID(long cashFlowID) {
		this.CashFlowID = cashFlowID;
	}
	public String getCheckAbstract() {
		return CheckAbstract;
	}
	public void setCheckAbstract(String checkAbstract) {
		this.CheckAbstract = checkAbstract;
	}
	public Timestamp getCheckDate() {
		return CheckDate;
	}
	public void setCheckDate(Timestamp checkDate) {
		this.CheckDate = checkDate;
	}
	public long getCheckUserID() {
		return CheckUserID;
	}
	public void setCheckUserID(long checkUserID) {
		this.CheckUserID = checkUserID;
	}
	public String getCheckUserName() {
		return CheckUserName;
	}
	public void setCheckUserName(String checkUserName) {
		this.CheckUserName = checkUserName;
	}
	public double getContractBailAmount() {
		return ContractBailAmount;
	}
	public void setContractBailAmount(double contractBailAmount) {
		this.ContractBailAmount = contractBailAmount;
	}
	public double getContractBailAmountForWS() {
		return ContractBailAmountForWS;
	}
	public void setContractBailAmountForWS(double contractBailAmountForWS) {
		this.ContractBailAmountForWS = contractBailAmountForWS;
	}
	public double getContractBailAmountForYS() {
		return ContractBailAmountForYS;
	}
	public void setContractBailAmountForYS(double contractBailAmountForYS) {
		this.ContractBailAmountForYS = contractBailAmountForYS;
	}
	public String getContractCode() {
		return ContractCode;
	}
	public void setContractCode(String contractCode) {
		this.ContractCode = contractCode;
	}
	public long getContractFinanceClientID() {
		return ContractFinanceClientID;
	}
	public void setContractFinanceClientID(long contractFinanceClientID) {
		this.ContractFinanceClientID = contractFinanceClientID;
	}
	public String getContractFinanceClientName() {
		return ContractFinanceClientName;
	}
	public void setContractFinanceClientName(String contractFinanceClientName) {
		this.ContractFinanceClientName = contractFinanceClientName;
	}
	public Timestamp getContractFinanceEndDate() {
		return ContractFinanceEndDate;
	}
	public void setContractFinanceEndDate(Timestamp contractFinanceEndDate) {
		this.ContractFinanceEndDate = contractFinanceEndDate;
	}
	public double getContractFinanceRate() {
		return ContractFinanceRate;
	}
	public void setContractFinanceRate(double contractFinanceRate) {
		this.ContractFinanceRate = contractFinanceRate;
	}
	public Timestamp getContractFinanceStartDate() {
		return ContractFinanceStartDate;
	}
	public void setContractFinanceStartDate(Timestamp contractFinanceStartDate) {
		this.ContractFinanceStartDate = contractFinanceStartDate;
	}
	public long getContractFinanceTerm() {
		return ContractFinanceTerm;
	}
	public void setContractFinanceTerm(long contractFinanceTerm) {
		this.ContractFinanceTerm = contractFinanceTerm;
	}
	public double getContractHireAmount() {
		return ContractHireAmount;
	}
	public void setContractHireAmount(double contractHireAmount) {
		this.ContractHireAmount = contractHireAmount;
	}
	public double getContractHireAmountForWS() {
		return ContractHireAmountForWS;
	}
	public void setContractHireAmountForWS(double contractHireAmountForWS) {
		this.ContractHireAmountForWS = contractHireAmountForWS;
	}
	public double getContractHireAmountForYS() {
		return ContractHireAmountForYS;
	}
	public void setContractHireAmountForYS(double contractHireAmountForYS) {
		this.ContractHireAmountForYS = contractHireAmountForYS;
	}
	public long getContractID() {
		return ContractID;
	}
	public void setContractID(long contractID) {
		this.ContractID = contractID;
	}
	public double getCorpusAmount() {
		return CorpusAmount;
	}
	public void setCorpusAmount(double corpusAmount) {
		this.CorpusAmount = corpusAmount;
	}
	public long getCurrencyID() {
		return CurrencyID;
	}
	public void setCurrencyID(long currencyID) {
		this.CurrencyID = currencyID;
	}
	public Timestamp getExecuteDate() {
		return ExecuteDate;
	}
	public void setExecuteDate(Timestamp executeDate) {
		this.ExecuteDate = executeDate;
	}
	public long getID() {
		return ID;
	}
	public void setID(long id) {
		this.ID = id;
	}
	public Timestamp getInputDate() {
		return InputDate;
	}
	public void setInputDate(Timestamp inputDate) {
		this.InputDate = inputDate;
	}
	public long getInputUserID() {
		return InputUserID;
	}
	public void setInputUserID(long inputUserID) {
		this.InputUserID = inputUserID;
	}
	public String getInputUserName() {
		return InputUserName;
	}
	public void setInputUserName(String inputUserName) {
		this.InputUserName = inputUserName;
	}
	public String getInstructionNo() {
		return InstructionNo;
	}
	public void setInstructionNo(String instructionNo) {
		this.InstructionNo = instructionNo;
	}
	public double getInterestAmount() {
		return InterestAmount;
	}
	public void setInterestAmount(double interestAmount) {
		this.InterestAmount = interestAmount;
	}
	public Timestamp getInterestStartDate() {
		return InterestStartDate;
	}
	public void setInterestStartDate(Timestamp interestStartDate) {
		this.InterestStartDate = interestStartDate;
	}
	public Timestamp getModifyDate() {
		return ModifyDate;
	}
	public void setModifyDate(Timestamp modifyDate) {
		this.ModifyDate = modifyDate;
	}
	public long getOfficeID() {
		return OfficeID;
	}
	public void setOfficeID(long officeID) {
		this.OfficeID = officeID;
	}
	public double getRate() {
		return Rate;
	}
	public void setRate(double rate) {
		this.Rate = rate;
	}
	public String getReceiveFormCode() {
		return ReceiveFormCode;
	}
	public void setReceiveFormCode(String receiveFormCode) {
		this.ReceiveFormCode = receiveFormCode;
	}
	public Timestamp getReceiveFormDate() {
		return ReceiveFormDate;
	}
	public void setReceiveFormDate(Timestamp receiveFormDate) {
		this.ReceiveFormDate = receiveFormDate;
	}
	public long getReturnBailAccountID() {
		return ReturnBailAccountID;
	}
	public void setReturnBailAccountID(long ReturnBailAccountID) {
		this.ReturnBailAccountID = ReturnBailAccountID;
	}
	public long getReturnCorpusAccountID() {
		return ReturnCorpusAccountID;
	}
	public void setReturnCorpusAccountID(long ReturnCorpusAccountID) {
		this.ReturnCorpusAccountID = ReturnCorpusAccountID;
	}
	public long getReturnCorpusBankID() {
		return ReturnCorpusBankID;
	}
	public void setReturnCorpusBankID(long ReturnCorpusBankID) {
		this.ReturnCorpusBankID = ReturnCorpusBankID;
	}
	public long getReturnInterestAccountID() {
		return ReturnInterestAccountID;
	}
	public void setReturnInterestAccountID(long ReturnInterestAccountID) {
		this.ReturnInterestAccountID = ReturnInterestAccountID;
	}
	public long getReturnInterestBankID() {
		return ReturnInterestBankID;
	}
	public void setReturnInterestBankID(long ReturnInterestBankID) {
		this.ReturnInterestBankID = ReturnInterestBankID;
	}
	public long getReturnFormID() {
		return ReturnFormID;
	}
	public void setReturnFormID(long returnFormID) {
		this.ReturnFormID = returnFormID;
	}
	public long getStatusID() {
		return StatusID;
	}
	public void setStatusID(long statusID) {
		this.StatusID = statusID;
	}
	public long getTransactionTypeID() {
		return TransactionTypeID;
	}
	public void setTransactionTypeID(long transactionTypeID) {
		this.TransactionTypeID = transactionTypeID;
	}
	public String getTransNo() {
		return TransNo;
	}
	public void setTransNo(String transNo) {
		this.TransNo = transNo;
	}
	public String getContractFinanceClientCode() {
		return ContractFinanceClientCode;
	}
	public void setContractFinanceClientCode(String contractFinanceClientCode) {
		ContractFinanceClientCode = contractFinanceClientCode;
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
	public InutParameterInfo getInutParameterInfo() {
		return inutParameterInfo;
	}
	public void setInutParameterInfo(InutParameterInfo inutParameterInfo) {
		this.inutParameterInfo = inutParameterInfo;
	}
	public long getIssue() {
		return issue;
	}
	public void setIssue(long issue) {
		this.issue = issue;
	}
	public long getLoanContractPlanDetailID() {
		return loanContractPlanDetailID;
	}
	public void setLoanContractPlanDetailID(long loanContractPlanDetailID) {
		this.loanContractPlanDetailID = loanContractPlanDetailID;
	}
	public long getIsDelay() {
		return isDelay;
	}
	public void setIsDelay(long isDelay) {
		this.isDelay = isDelay;
	}
	public double getContractHireAmountForYSALL() {
		return ContractHireAmountForYSALL;
	}
	public void setContractHireAmountForYSALL(double contractHireAmountForYSALL) {
		ContractHireAmountForYSALL = contractHireAmountForYSALL;
	}
}