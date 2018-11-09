/*
 * Created on 2003-10-29
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.iss.itreasury.settlement.transloan.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author yychen
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class RepaymentConsignLoanConditionInfo implements Serializable {
	private TransRepaymentLoanInfo info = null;
	/**
	 * 默认构建器
	 *
	 */
	public RepaymentConsignLoanConditionInfo() {
		this.info = new TransRepaymentLoanInfo();
	}
	/**
	 * 构建器
	 * @param info
	 */
	public RepaymentConsignLoanConditionInfo(TransRepaymentLoanInfo info) {
		this.info = info;
	}
	/**
	 * 返回信贷还款信息
	 * @return
	 */
	public TransRepaymentLoanInfo getTransRepaymentLoanInfo() {
		return this.info;
	}
	//	----------------------------默认查询条件  1、办事处 2、币种 3、录入人 4、

	/**币种
	 * @return Returns the currencyID.
	 */
	public long getCurrencyID() {
		return this.info.getCurrencyID();
	}

	/**币种
	 * @param currencyID The currencyID to set.
	 */
	public void setCurrencyID(long currencyID) {
		this.info.setCurrencyID(currencyID);
	}

	/**办事处ID
	 * @return Returns the officeID.
	 */
	public long getOfficeID() {
		return this.info.getOfficeID();
	}

	/**办事处ID
	 * @param officeID The officeID to set.
	 */
	public void setOfficeID(long officeID) {
		this.info.setOfficeID(officeID);
	}
	/**输入人
	 * @return Returns the inputUserID.
	 */
	public long getInputUserID() {
		return this.info.getInputUserID();
	}

	/**输入人
	 * @param inputUserID The inputUserID to set.
	 */
	public void setInputUserID(long inputUserID) {
		this.info.setInputUserID(inputUserID);
	}

	/**执行日
	 * @return Returns the execute.
	 */
	public Timestamp getExecute() {
		return this.info.getExecute();
	}

	/**执行日
	 * @param execute The execute to set.
	 */
	public void setExecute(Timestamp execute) {
		this.info.setExecute(execute);
	}

	/**
		 * 状态
		 * @param statusID
		 */
	public void setStatusID(long statusID) {
		this.info.setStatusID(statusID);
	}
	/**
	 * 状态
	 * @param statusID
	 */
	public long getStatusID() {
		return this.info.getStatusID();
	}

	//	----------------------------默认查询条件

	/**
	 * @return
	 */
	public long getClientID() //付款方客户编号
	{
		return info.getClientID();
	}

	/**
	 * @return
	 */
	public long getBankID() //收款银行
	{
		return info.getBankID();
	}

	/**
	 * @return
	 */
	public long getCommissionAccountID() //付手续费账户号
	{
		return info.getCommissionAccountID();
	}

	/**
	 * @return
	 */
	public long getCommissionBankID() //付手续费银行
	{
		return info.getCommissionBankID();
	}

	/**
	 * @return
	 */
	public long getConsignAccountID() //委托存款账户号
	{
		return info.getConsignAccountID();
	}

	/**
	 * @return
	 */
	public long getConsignDepositAccountID() //委托方活期存款账户号
	{
		return info.getConsignDepositAccountID();
	}

	/**
	 * @return
	 */
	public long getPreFormID() //提前还款通知单
	{
		return info.getPreFormID();
	}

	/**
	 * @return
	 */
	public long getDepositAccountID() //还款方活期账户号
	{
		return info.getDepositAccountID();
	}

	/**
	 * @return
	 */
	public long getFreeFormID() //免还通知单
	{
		return info.getFreeFormID();
	}

	/**
	 * @return
	 */
	public long getInterestBankID() //付息银行
	{
		return info.getInterestBankID();
	}

	/**
	 * @return
	 */
	public Timestamp getInterestStart() //起息日
	{
		return info.getInterestStart();
	}

	/**
	 * @return
	 */
	public long getIsRemitCommission() //是否免还手续费
	{
		return info.getIsRemitCommission();
	}

	/**
	 * @return
	 */
	public long getIsRemitCompoundInterest() //是否免还复利
	{
		return info.getIsRemitCompoundInterest();
	}

	/**
	 * @return
	 */
	public long getIsRemitInterest() //是否免还利息
	{
		return info.getIsRemitInterest();
	}

	/**
	 * @return
	 */
	public long getIsRemitOverDueInterest() //是否免还逾期罚息
	{
		return info.getIsRemitOverDueInterest();
	}

	/**
	 * @return
	 */
	public long getLoanAccountID() //委托贷款账户号
	{
		return info.getLoanAccountID();
	}

	/**
	 * @return
	 */
	public long getPayInterestAccountID() //付利息账户号
	{
		return info.getPayInterestAccountID();
	}

	/**
	 * @return
	 */
	public double getRealCommission() //实际支付手续费
	{
		return info.getRealCommission();
	}

	/**
	 * @return
	 */
	public double getRealCompoundInterest() //实际支付复利
	{
		return info.getRealCompoundInterest();
	}

	/**
	 * @return
	 */
	public double getRealInterest() //实际支付利息
	{
		return info.getRealInterest();
	}

	/**
	 * @return
	 */
	public double getRealOverDueInterest() //实际支付逾期罚息
	{
		return info.getRealOverDueInterest();
	}

	/**
	 * @return
	 */
	public long getReceiveInterestAccountID() //利息收入账户号
	{
		return info.getReceiveInterestAccountID();
	}

	/**
	 * @return
	 */
	public double getAmount() //金额
	{
		return info.getAmount();
	}

	/**
	 * @param d
	 */
	public void setAmount(double d) {
		info.setAmount(d);
	}

	/**
	 * @param l
	 */
	public void setClientID(long l) {
		info.setClientID(l);
	}

	/**贷款合同ID
	 * @return Returns the loanContractID.
	 */
	public long getLoanContractID() {
		return this.info.getLoanContractID();
	}

	/**贷款合同ID
	 * @param loanContractID The loanContractID to set.
	 */
	public void setLoanContractID(long loanContractID) {
		this.info.setLoanContractID(loanContractID);
	}
	
	/**放款通知单
	 * @return Returns the loanNoteID.
	 */
	public long getLoanNoteID() {
		return this.info.getLoanNoteID();
	}

	/**放款通知单
	 * @param loanNoteID The loanNoteID to set.
	 */
	public void setLoanNoteID(long loanNoteID) {
		this.info.setLoanNoteID(loanNoteID);
	}
	
	/**
	 * @param l
	 */
	public void setCommissionAccountID(long l) {
		info.setCommissionAccountID(l);
	}

	/**
	 * @param l
	 */
	public void setCommissionBankID(long l) {
		info.setCommissionBankID(l);
	}

	/**
	 * @param l
	 */
	public void setConsignAccountID(long l) {
		info.setConsignAccountID(l);
	}

	/**
	 * @param l
	 */
	public void setConsignDepositAccountID(long l) {
		info.setConsignDepositAccountID(l);
	}

	/**
	 * @param l
	 */
	public void setPreFormID(long l) {
		info.setPreFormID(l);
	}

	/**
	 * @param l
	 */
	public void setDepositAccountID(long l) {
		info.setDepositAccountID(l);
	}

	/**
	 * @param l
	 */
	public void setFreeFormID(long l) {
		info.setFreeFormID(l);
	}

	/**
	 * @param l
	 */
	public void setInterestBankID(long l) {
		info.setInterestBankID(l);
	}

	/**
	 * @param timestamp
	 */
	public void setInterestStart(Timestamp timestamp) {
		info.setInterestStart(timestamp);
	}

	/**
	 * @param l
	 */
	public void setIsRemitCommission(long l) {
		info.setIsRemitCommission(l);
	}

	/**
	 * @param l
	 */
	public void setIsRemitCompoundInterest(long l) {
		info.setIsRemitCompoundInterest(l);
	}

	/**
	 * @param l
	 */
	public void setIsRemitInterest(long l) {
		info.setIsRemitInterest(l);
	}

	/**
	 * @param l
	 */
	public void setIsRemitOverDueInterest(long l) {
		info.setIsRemitOverDueInterest(l);
	}

	/**
	 * @param l
	 */
	public void setLoanAccountID(long l) {
		info.setLoanAccountID(l);
	}



	/**
	 * @param l
	 */
	public void setPayInterestAccountID(long l) {
		info.setPayInterestAccountID(l);
	}

	/**
	 * @param d
	 */
	public void setRealCommission(double d) {
		info.setRealCommission(d);
	}

	/**
	 * @param d
	 */
	public void setRealCompoundInterest(double d) {
		info.setRealCompoundInterest(d);
	}

	/**
	 * @param d
	 */
	public void setRealInterest(double d) {
		info.setRealInterest(d);
	}

	/**
	 * @param d
	 */
	public void setRealOverDueInterest(double d) {
		info.setRealOverDueInterest(d);
	}

	/**
	 * @param l
	 */
	public void setReceiveInterestAccountID(long l) {
		info.setReceiveInterestAccountID(l);
	}

	/**
	 * @param l
	 */
	public void setBankID(long l) {
		info.setBankID(l);
	}

	/**
		 * 本金/利息处理方式
		 * @return
		 */
	public long getCapitalAndInterestDealWay() {
		return info.getCapitalAndInterstDealway();
	}

	/**
	 * 本金/利息处理方式
	 * @param capitalAndInterestDealWay
	 */
	public void setCapitalAndInterestDealWay(long capitalAndInterestDealWay) {
		info.setCapitalAndInterstDealway(capitalAndInterestDealWay);
	}
	/**
	 * 交易方向
	 * @param lMultiLoanType
	 */
	public void setTransDirectionID(long lMultiLoanType){
		this.info.setTransDirectionID(lMultiLoanType);
	}
	/**
	 * 交易方向
	 * @return
	 */
	public long getTransDirectionID(){
		return this.info.getTransDirectionID();
	}
	/**
	 * 报单号
	 * @param strDeclarationNo
	 */
	public void setDeclarationNo(String strDeclarationNo){
		this.info.setDeclarationNo(strDeclarationNo);
	}
	/**
	 * 报单号
	 * @return
	 */
	public String getDeclarationNo(){
		return this.info.getDeclarationNo();
	}
}
