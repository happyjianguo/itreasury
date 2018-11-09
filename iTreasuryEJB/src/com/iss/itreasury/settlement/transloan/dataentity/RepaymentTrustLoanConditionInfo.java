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
public class RepaymentTrustLoanConditionInfo implements Serializable
{
	private TransRepaymentLoanInfo info=null;
	/**
	 * 默认构建器
	 *
	 */
	public RepaymentTrustLoanConditionInfo(){
		this.info = new TransRepaymentLoanInfo();
	}
	/**
	 * 构建器
	 * @param info
	 */
	public RepaymentTrustLoanConditionInfo(TransRepaymentLoanInfo info){
		this.info=info;
	}
	/**
	 * 返回信贷还款信息
	 * @return
	 */
	public TransRepaymentLoanInfo getTransRepaymentLoanInfo(){
		return this.info;
	}
//----------------------------默认查询条件  1、办事处 2、币种 3、录入人 4、

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

//----------------------------默认查询条件
	
	
  	/**
  	 * 付款方客户编号
  	 */
  	public void setClientID(long lClientID){
  		info.setClientID(lClientID);
  	}
  
	/**付款方客户编号
	 * @return
	 */
	public long getClientID() 
	{
		return info.getClientID();
	}
	/**还款金额
	 * @return Returns the amount.
	 */
	public double getAmount() {
		return this.info.getAmount();
	}

	/**还款金额
	 * @param amount The amount to set.
	 */
	public void setAmount(double amount) {
		this.info.setAmount(amount);
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

	/**收款银行ID
	 * @return Returns the bankID.
	 */
	public long getBankID() {
		return this.info.getBankID();
	}

	/**收款银行ID
	 * @param bankID The bankID to set.
	 */
	public void setBankID(long bankID) {
		this.info.setBankID(bankID);
	}

	/**活期账户号
	 * @return Returns the depositAccountID.
	 */
	public long getDepositAccountID() {
		return this.info.getDepositAccountID();
	}

	/**活期账户号
	 * @param depositAccountID The depositAccountID to set.
	 */
	public void setDepositAccountID(long depositAccountID) {
		this.info.setDepositAccountID(depositAccountID);
	}


	/**	起息日
	 * @return Returns the interestStart.
	 */
	public Timestamp getInterestStart() {
		return this.info.getInterestStart();
	}

	/**起息日
	 * @param interestStart The interestStart to set.
	 */
	public void setInterestStart(Timestamp interestStart) {
		this.info.setInterestStart(interestStart);
	}

	/**贷款账户
	 * @return Returns the loanAccountID.
	 */
	public long getLoanAccountID() {
		return this.info.getLoanAccountID();
	}

	/**贷款账户
	 * @param loanAccountID The loanAccountID to set.
	 */
	public void setLoanAccountID(long loanAccountID) {
		this.info.setLoanAccountID(loanAccountID);
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


	/**付息账户号
	 * @return Returns the payInterestAccountID.
	 */
	public long getPayInterestAccountID() {
		return this.info.getPayInterestAccountID();
	}

	/**付息账户号
	 * @param payInterestAccountID The payInterestAccountID to set.
	 */
	public void setPayInterestAccountID(long payInterestAccountID) {
		this.info.setPayInterestAccountID(payInterestAccountID);
	}

	/**提前还款通知单
	 * @return Returns the preFormID.
	 */
	public long getPreFormID() {
		return this.info.getPreFormID();
	}

	/**提前还款通知单
	 * @param preFormID The preFormID to set.
	 */
	public void setPreFormID(long preFormID) {
		this.info.setPreFormID(preFormID);
	}
	
	/**
	 * 付息开户行
	 * @return
	 */
	public long getInterestBankID()
	{
		return info.getInterestBankID();
	}
	
	/**
	 * 付息开户行
	 * @return
	 */
	public void setInterestBankID(long interestBankDI)
	{
		info.setInterestBankID(interestBankDI);
	}
	
	/**
	 * 付保险费账号
	 * @return
	 */
	public long getPaySuertyFeeAccountID(){
		return info.getPaySuretyAccountID();
	}
	
	/**
	 * 付保险费账号
	 * @return
	 */
	public void setPaySuertyFeeAccountID(long paySuertyFeeAccountID){
		info.setPaySuretyAccountID(paySuertyFeeAccountID);
	}
	
	/**
	 * 付保险费开户行
	 * @return
	 */
	public long getSuertyFeeBankID(){
		return info.getSuretyBankID();
	}
	
	/**
	 * 付保险费开户行
	 * @param suretyBankID
	 */
	public void setSuertyFeeBankID(long suretyBankID){
		info.setSuretyBankID(suretyBankID);
	}
	
	/**
	 * 担保费收入账户号
	 * @return
	 */
	public long getReceiveSuertyFeeAccountID(){
		return info.getReceiveSuretyAccountID();
	}
	
	/**
	 * 付担保费收入账户号
	 * @param receiveSuretyAccountID
	 */
	public void setReceiveSuertyFeeAccountID(long receiveSuretyAccountID){
		info.setReceiveSuretyAccountID(receiveSuretyAccountID);
	}
	
	/**
	 * 利息
	 * @return
	 */
	public double getRealInterest(){
		return info.getRealInterest();
	}
	
	/**
	 * 利息
	 * @return
	 */
	public void setRealInterest(double realInterest){
		info.setRealInterest(realInterest);
	}
	
	/**
	 * 是否免还剩余利息
	 * @return
	 */
	public long getIsRemitInterest(){
		return info.getIsRemitInterest();
	}
	
	/**
	 * 是否免还剩余利息
	 * @param isRemitInterest
	 */
	public void setIsRemitInterest(long isRemitInterest){
		info.setIsRemitInterest(isRemitInterest);
	}
	
	/**
	 * 是否免还剩余复利
	 * @return
	 */
	public long getIsRemitCompoundInterest(){
		return info.getIsRemitCompoundInterest();
	}
	
	/**
	 * 是否免还剩余复利
	 * @param isRemitCompoundInterest
	 */
	public void setIsRemitCompoundInterest(long isRemitCompoundInterest){
		info.setIsRemitCompoundInterest(isRemitCompoundInterest);
	}
	
	/**
	 * 是否免还剩余逾期罚息
	 * @return
	 */
	public long getIsRemitOverDueInterest(){
		return info.getIsRemitOverDueInterest();
	}
	
	/**
	 * 是否免还剩余逾期罚息
	 * @param isRemitOverDueInterest
	 */
	public void setIsRemitOverDueInterest(long isRemitOverDueInterest){
		info.setIsRemitOverDueInterest(isRemitOverDueInterest);
	}
	
	/**
	 * 是否免还剩余担保费
	 * @return
	 */
	public long getIsRemitSuretyFee(){
		return info.getIsRemitSuretyFee();
	}
	
	/**
	 * 是否免还剩余担保费
	 * @param isRemitSuretyFee
	 */
	public void setIsRemitSuretyFee(long isRemitSuretyFee){
		info.setIsRemitSuretyFee(isRemitSuretyFee);
	}
	
	/**
	 * 复利
	 * @return
	 */
	public double getRealCompoundInterest(){
		return info.getRealCompoundInterest();
	}
	
	/**
	 * 复利
	 * @param compoundInterest
	 */
	public void setRealCompoundInterest(double realCompoundInterest){
		info.setRealCompoundInterest(realCompoundInterest);
	}
	
	/**
	 * 逾期罚息
	 * @return
	 */
	public double getRealOverDueInterest(){
		return info.getRealOverDueInterest();
	}
	
	/**
	 * 逾期罚息
	 * @param overDueInterest
	 */
	public void setRealOverDueInterest(double realOverDueInterest){
		info.setRealOverDueInterest(realOverDueInterest);
	}
	
	/**
	 * 担保费
	 * @return
	 */
	public double getRealSuretyFee(){
		return info.getRealSuretyFee();
	}
	
	/**
	 * 担保费
	 * @param suretyFee
	 */
	public void setRealSuretyFee(double realSuretyFee){
		info.setRealSuretyFee(realSuretyFee);
	}
	
	/**
	 * 本金/利息处理方式
	 * @return
	 */
	public long getCapitalAndInterestDealWay(){
		return info.getCapitalAndInterstDealway();
	}
	
	/**
	 * 本金/利息处理方式
	 * @param capitalAndInterestDealWay
	 */
	public void setCapitalAndInterestDealWay(long capitalAndInterestDealWay){
		info.setCapitalAndInterstDealway(capitalAndInterestDealWay);
	}
	
	/**
	 * 状态
	 * @param statusID
	 */
	public void setStatusID(long statusID){
		this.info.setStatusID(statusID);
	}
	/**
	 * 状态
	 * @param statusID
	 */
	public long getStatusID(){
		return this.info.getStatusID();
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
