/*
 * 创建日期 2003-10-6
 */
package com.iss.itreasury.settlement.transloan.dataentity;
import java.io.Serializable;
import java.sql.Timestamp;
/**
 * 信托贷款和委托贷款收回的通用info类
 * @author yqwu
 */
public class RepaymentInterestConditionInfo implements Serializable
{
		private TransRepaymentLoanInfo info = null;
		/**
		 * 默认构建器
		 *
		 */
		public RepaymentInterestConditionInfo() {
			this.info = new TransRepaymentLoanInfo();
		}
		/**
		 * 构建器
		 * @param info
		 */
		public RepaymentInterestConditionInfo(TransRepaymentLoanInfo info) {
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


		//	----------------------------默认查询条件

		
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
		/**实际支付手续费
		 * @return Returns the dRealCommission.
		 */
		public double getRealCommission()
		{
			return info.getRealCommission();
		}
		
		/**实际支付手续费
		 * @param realCommission The dRealCommission to set.
		 */
		public void setRealCommission(double realCommission)
		{
			info.setRealCommission(realCommission);
		}
		
		/**实际支付复利
		 * @return Returns the dRealCompoundInterest.
		 */
		public double getRealCompoundInterest()
		{
			return info.getRealCompoundInterest();
		}
		
		/**实际支付复利
		 * @param realCompoundInterest The dRealCompoundInterest to set.
		 */
		public void setRealCompoundInterest(double realCompoundInterest)
		{
			info.setRealCompoundInterest(realCompoundInterest);
		}
		
		/**实际支付利息
		 * @return Returns the dRealInterest.
		 */
		public double getRealInterest()
		{
			return info.getRealInterest();
		}
		
		/**实际支付利息
		 * @param realInterest The dRealInterest to set.
		 */
		public void setRealInterest(double realInterest)
		{
			info.setRealInterest(realInterest);
		}
		
		/**实际支付逾期罚息
		 * @return Returns the dRealOverDueInterest.
		 */
		public double getRealOverDueInterest()
		{
			return info.getRealOverDueInterest();
		}
		
		/**实际支付逾期罚息
		 * @param realOverDueInterest The dRealOverDueInterest to set.
		 */
		public void setRealOverDueInterest(double realOverDueInterest)
		{
			info.setRealOverDueInterest(realOverDueInterest);
		}
		
		/**实际支付担保费
		 * @return Returns the dRealSuretyFee.
		 */
		public double getRealSuretyFee()
		{
			return info.getRealSuretyFee();
		}
		
		/**实际支付担保费
		 * @param realSuretyFee The dRealSuretyFee to set.
		 */
		public void setRealSuretyFee(double realSuretyFee)
		{
			info.setRealSuretyFee(realSuretyFee);
		}
		
		/**还款客户
		 * @return Returns the lClientID.
		 */
		public long getClientID()
		{
			return info.getClientID();
		}
		
		/**还款客户
		 * @param clientID The lClientID to set.
		 */
		public void setClientID(long clientID)
		{
			info.setClientID(clientID);
		}
		
		/**付息银行
		 * @return Returns the lInterestBankID.
		 */
		public long getInterestBankID()
		{
			return info.getInterestBankID();
		}
		
		/**付息银行
		 * @param interestBankID The lInterestBankID to set.
		 */
		public void setInterestBankID(long interestBankID)
		{
			info.setInterestBankID(interestBankID);
		}
		
		/**是否免还手续费
		 * @return Returns the lIsRemitCommission.
		 */
		public long getIsRemitCommission()
		{
			return info.getIsRemitCommission();
		}
		
		/**是否免还手续费
		 * @param isRemitCommission The lIsRemitCommission to set.
		 */
		public void setIsRemitCommission(long isRemitCommission)
		{
			info.setIsRemitCommission(isRemitCommission);
		}
		
		/**是否免还复利
		 * @return Returns the lIsRemitCompoundInterest.
		 */
		public long getIsRemitCompoundInterest()
		{
			return info.getIsRemitCompoundInterest();
		}
		
		/**是否免还复利
		 * @param isRemitCompoundInterest The lIsRemitCompoundInterest to set.
		 */
		public void setIsRemitCompoundInterest(long isRemitCompoundInterest)
		{
			info.setIsRemitCompoundInterest(isRemitCompoundInterest);
		}
		
		/**是否免还利息
		 * @return Returns the lIsRemitInterest.
		 */
		public long getIsRemitInterest()
		{
			return info.getIsRemitInterest();
		}
		
		/**是否免还利息
		 * @param isRemitInterest The lIsRemitInterest to set.
		 */
		public void setIsRemitInterest(long isRemitInterest)
		{
			info.setIsRemitInterest(isRemitInterest);
		}
		
		/**是否免还逾期罚息
		 * @return Returns the lIsRemitOverDueInterest.
		 */
		public long getIsRemitOverDueInterest()
		{
			return info.getIsRemitOverDueInterest();
		}
		
		/**是否免还逾期罚息
		 * @param isRemitOverDueInterest The lIsRemitOverDueInterest to set.
		 */
		public void setIsRemitOverDueInterest(long isRemitOverDueInterest)
		{
			info.setIsRemitOverDueInterest(isRemitOverDueInterest);
		}
		
		/**是否免还手续费
		 * @return Returns the lIsRemitSuretyFee.
		 */
		public long getIsRemitSuretyFee()
		{
			return info.getIsRemitSuretyFee();
		}
		
		/**是否免还手续费
		 * @param isRemitSuretyFee The lIsRemitSuretyFee to set.
		 */
		public void setIsRemitSuretyFee(long isRemitSuretyFee)
		{
			info.setIsRemitSuretyFee(isRemitSuretyFee);
		}
		
		/**贷款账户
		 * @return Returns the lLoanAccountID.
		 */
		public long getLoanAccountID()
		{
			return info.getLoanAccountID();
		}
		
		/**贷款账户
		 * @param loanAccountID The lLoanAccountID to set.
		 */
		public void setLoanAccountID(long loanAccountID)
		{
			info.setLoanAccountID(loanAccountID);
		}
		
		/**
		 * 合同号
		 * @return
		 */
		public long getLoanContractID(){
			return info.getLoanContractID();
		}
		
		/**
		 * 合同号
		 * @param loanContractID
		 */
		public void setLoanContractID(long loanContractID){
			info.setLoanContractID(loanContractID);
		}
		/**放款通知单
		 * @return Returns the lLoanNoteID.
		 */
		public long getLoanNoteID()
		{
			return info.getLoanNoteID();
		}
		
		/**放款通知单
		 * @param loanNoteID The lLoanNoteID to set.
		 */
		public void setLoanNoteID(long loanNoteID)
		{
			info.setLoanNoteID(loanNoteID);
		}
		
		/**付息账户号
		 * @return Returns the lPayInterestAccountID.
		 */
		public long getPayInterestAccountID()
		{
			return info.getPayInterestAccountID();
		}
		
		/**付息账户号
		 * @param payInterestAccountID The lPayInterestAccountID to set.
		 */
		public void setPayInterestAccountID(long payInterestAccountID)
		{
			info.setPayInterestAccountID(payInterestAccountID);
		}
		
		/**
		 * 收担保费账户号
		 * @return
		 */
		public long getReceiveSuertyFeeAccountID(){
			return info.getReceiveSuretyAccountID();
		}
		/**
		 * 收担保费账户号
		 * @param paySuretyAccountID
		 */
		public void setReceiveSuertyFeeAccountID(long receiveSuretyAccountID){
			info.setReceiveSuretyAccountID(receiveSuretyAccountID);
		}
		
		/**收息账户号
		 * @return Returns the lReceiveInterestAccountID.
		 */
		public long getReceiveInterestAccountID()
		{
			return info.getReceiveInterestAccountID();
		}
		
		/**收息账户号
		 * @param receiveInterestAccountID The lReceiveInterestAccountID to set.
		 */
		public void setReceiveInterestAccountID(long receiveInterestAccountID)
		{
			info.setReceiveInterestAccountID(receiveInterestAccountID);
		}
		
		/**结息日
		 * @return Returns the tsInterestClear.
		 */
		public Timestamp getInterestClear()
		{
			return info.getInterestClear();
		}
		
		/**结息日
		 * @param tsInterestClear The tsInterestClear to set.
		 */
		public void setInterestClear(Timestamp interestClear)
		{
			info.setInterestClear(interestClear);
		}
		
		/**起息日
		 * @return Returns the tsInterestStart.
		 */
		public Timestamp getInterestStart()
		{
			return info.getInterestStart();
		}
		
		/**起息日
		 * @param tsInterestStart The tsInterestStart to set.
		 */
		public void setInterestStart(Timestamp interestStart)
		{
			info.setInterestStart(interestStart);
		}
		/**收息账户号
		 * @param receiveInterestAccountID The lReceiveInterestAccountID to set.
		 */
		public void setCommissionAccountID(long CommissionAccountID)
		{
			info.setCommissionAccountID(CommissionAccountID);
		}
		public long getCommissionAccountID()
		{
			return info.getCommissionAccountID();
		}
		public void setCommissionBankID(long CommissionBankID)
		{
			info.setCommissionBankID(CommissionBankID);
		}
		public long getCommissionBankID()
		{
			return info.getCommissionBankID();
		}
		
}
