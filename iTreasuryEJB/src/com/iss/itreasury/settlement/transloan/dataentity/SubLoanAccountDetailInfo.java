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
public class SubLoanAccountDetailInfo implements Serializable
{
	
	private long OfficeID = -1;					//办事处ID
	private long CurrencyID = -1;				//币种
	
	private long ClientID = -1;					//客户ID
	private long DepositAccountID = -1;			//活期存款账号
	private long BankID = -1;					//开户行

	//----------为委托新添加
	private long ConsignAccountID = -1;			//委托存款账户号
	private long CommissionAccountID = -1;		//手续费付出账户号
	private long CommissionBankID = -1;			//收手续费银行
	private double InterestTaxRate = 0.0;		//利息税费率
	private long InterestTaxRatePlan = -1;      //利息税费率计划
	
	private long ContractID = -1;				//合同号
	private long LoanAccountID = -1;			//贷款账号
	private long PayFormID = -1 ;				//放款通知单LoanNote
	private long AheadRepaymentFormID = -1;		//提前还款通知单
	private double Balance= -1;					//余额
	
	private double SavedAmount = 0.0;			//修改是保存修改前的数值
	private double Amount = 0.0;				//还款金额
	private Timestamp InterestStart = null;		//起息日
	private Timestamp ClearInterest = null;		//结息日
	private Timestamp LatestClearInterest = null;//上次结息日
	private Timestamp Execute 		= null;		//执行日
	private long AbstractID = -1;				//摘要ID
	private String Abstract = "";				//摘要
	
	private long PayInterestAccountID = -1;		//付利息账户
	private long PayInterestBankID = -1;		//付利息银行
	private long PaySuretyFeeAccountID = -1;	//担保费付出账户
	private long PaySuretyFeeBankID = -1;		//付担保费银行
	private long ReceiveSuretyFeeAccountID = -1;//收担保费账户
	private long ReceiveInterestAccountID = -1;//收息账户号


	private double Interest = 0.0;				//利息
	private double InterestReceiveAble = 0.0;	//计提利息
	private double InterestIncome = 0.0;		//本次利息
	
	private long interestPlanId = -1;           //利率计划id
	private double InterestTax = 0.0;			//利息税费
	private double CompoundInterest = 0.0;		//复利
	private double OverDueInterest = 0.0;		//逾期罚息
	private double SuretyFee = 0.0;				//担保费
	private double Commisson = 0.0;				//手续费
	private long TransactionType = -1;			//交易类型
	private long ContractType = -1;				//合同类型
	private long SubAccountID = -1;				//子账户ID
	
	/**
	 * 为打印添加的利息信息
	 */
	   
	private Timestamp CompoundInterestStart = null;	//复利起息日
	private double CompoundAmount = 0.0;			//复利本金
	private double CompoundRate = 0.0;				//复利利率
	
	private Timestamp OverDueStart = null;			//逾期罚息起息日
	private double OverDueAmount = 0.0;				//逾期罚息本金
	private double OverDueRate = 0.0;				//逾期罚息利率
	
	/**
	 * 为打印新添加信息2
	 */
	double LoanRepaymentRate = 0.0;					//还款利率
	Timestamp SuretyFeeStart = null;				//担保费起息日
	double SuretyFeeRate = 0.0;						//担保费率
	Timestamp CommissionStart = null;				//手续费起息日
	double CommissionRate = 0.0;					//手续费率
	
	/**
	 * 网银加上的参数
	 */
	String InstructionNo					= "";		//
	
	private boolean isAccountClear = false;		//账户是否结清
	
	private boolean isTempSave = false;         //是否是委托贷款收回的暂存
	
	public boolean isTempSave() {
		return isTempSave;
	}

	public void setTempSave(boolean isTempSave) {
		this.isTempSave = isTempSave;
	}

	/**
	 * @return Returns the subAccountID.
	 */
	public long getSubAccountID()
	{
		return SubAccountID;
	}

	/**
	 * @param subAccountID The subAccountID to set.
	 */
	public void setSubAccountID(long subAccountID)
	{
		SubAccountID = subAccountID;
	}

	/**
	 * @return Returns the contractType.
	 */
	public long getContractType()
	{
		return ContractType;
	}

	/**
	 * @param contractType The contractType to set.
	 */
	public void setContractType(long contractType)
	{
		ContractType = contractType;
	}

	/**
	 * @return Returns the lTransactionType.
	 */
	public long getTransactionType()
	{
		return TransactionType;
	}

	/**
	 * @param transactionType The lTransactionType to set.
	 */
	public void setTransactionType(long transactionType)
	{
		TransactionType = transactionType;
	}

	/**
	 * @return Returns the interestTaxRate.
	 */
	public double getInterestTaxRate()
	{
		return InterestTaxRate;
	}

	/**
	 * @param interestTaxRate The interestTaxRate to set.
	 */
	public void setInterestTaxRate(double interestTaxRate)
	{
		InterestTaxRate = interestTaxRate;
	}

	/**
	 * @return Returns the commissionAccountID.
	 */
	public long getCommissionAccountID()
	{
		return CommissionAccountID;
	}

	/**
	 * @param commissionAccountID The commissionAccountID to set.
	 */
	public void setCommissionAccountID(long commissionAccountID)
	{
		CommissionAccountID = commissionAccountID;
	}

	/**
	 * @return Returns the consignAccountID.
	 */
	public long getConsignAccountID()
	{
		return ConsignAccountID;
	}

	/**
	 * @param consignAccountID The consignAccountID to set.
	 */
	public void setConsignAccountID(long consignAccountID)
	{
		ConsignAccountID = consignAccountID;
	}

	/**
	 * @return Returns the balance.
	 */
	public double getBalance()
	{
		return Balance;
	}

	/**
	 * @param balance The balance to set.
	 */
	public void setBalance(double balance)
	{
		Balance = balance;
	}

	/**
	 * @return
	 */
	public long getPayInterestAccountID()
	{
		return PayInterestAccountID;
	}

	/**
	 * @return
	 */
	public long getPayInterestBankID()
	{
		return PayInterestBankID;
	}

	
	 

	/**
	 * @return
	 */
	public long getReceiveSuretyFeeAccountID()
	{
		return ReceiveSuretyFeeAccountID;
	}

	/**
	 * @param l
	 */
	public void setPayInterestAccountID(long l)
	{
		PayInterestAccountID = l;
	}

	/**
	 * @param l
	 */
	public void setPayInterestBankID(long l)
	{
		PayInterestBankID = l;
	}

	
	/**
	 * @param l
	 */
	public void setReceiveSuretyFeeAccountID(long l)
	{
		ReceiveSuretyFeeAccountID = l;
	}

	/**
	 * @return
	 */
	public long getAheadRepaymentFormID()
	{
		return AheadRepaymentFormID;
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
	public long getLoanAccountID()
	{
		return LoanAccountID;
	}

	/**
	 * @return
	 */
	public long getPayFormID()
	{
		return PayFormID;
	}

	/**
	 * @param l
	 */
	public void setAheadRepaymentFormID(long l)
	{
		AheadRepaymentFormID = l;
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
	public void setLoanAccountID(long l)
	{
		LoanAccountID = l;
	}

	/**
	 * @param l
	 */
	public void setPayFormID(long l)
	{
		PayFormID = l;
	}

	
	/**
	 * @return
	 */
	public double getCommisson()
	{
		return Commisson;
	}

	/**
	 * @return
	 */
	public double getCompoundInterest()
	{
		return CompoundInterest;
	}

	/**
	 * @return
	 */
	public double getInterestIncome()
	{
		return InterestIncome;
	}

	/**
	 * @return
	 */
	public double getInterestReceiveAble()
	{
		return InterestReceiveAble;
	}

	/**
	 * @return
	 */
	public double getInterestTax()
	{
		return InterestTax;
	}

	/**
	 * @return
	 */
	public double getOverDueInterest()
	{
		return OverDueInterest;
	}

	/**
	 * @return
	 */
	public double getSuretyFee()
	{
		return SuretyFee;
	}

	/**
	 * @param d
	 */
	public void setCommisson(double d)
	{
		Commisson = d;
	}

	/**
	 * @param d
	 */
	public void setCompoundInterest(double d)
	{
		CompoundInterest = d;
	}

	/**
	 * @param d
	 */
	public void setInterestIncome(double d)
	{
		InterestIncome = d;
	}

	/**
	 * @param d
	 */
	public void setInterestReceiveAble(double d)
	{
		InterestReceiveAble = d;
	}

	/**
	 * @param d
	 */
	public void setInterestTax(double d)
	{
		InterestTax = d;
	}

	/**
	 * @param d
	 */
	public void setOverDueInterest(double d)
	{
		OverDueInterest = d;
	}

	/**
	 * @param d
	 */
	public void setSuretyFee(double d)
	{
		SuretyFee = d;
	}

	/**
	 * @return
	 */
	public double getInterest()
	{
		return Interest;
	}

	/**
	 * @param d
	 */
	public void setInterest(double d)
	{
		Interest = d;
	}

	/**
	 * Returns the paySuretyFeeAccountID.
	 * @return long
	 */
	public long getPaySuretyFeeAccountID() {
		return PaySuretyFeeAccountID;
	}

	/**
	 * Returns the paySuretyFeeBankID.
	 * @return long
	 */
	public long getPaySuretyFeeBankID() {
		return PaySuretyFeeBankID;
	}

	/**
	 * Sets the paySuretyFeeAccountID.
	 * @param paySuretyFeeAccountID The paySuretyFeeAccountID to set
	 */
	public void setPaySuretyFeeAccountID(long paySuretyFeeAccountID) {
		PaySuretyFeeAccountID = paySuretyFeeAccountID;
	}

	/**
	 * Sets the paySuretyFeeBankID.
	 * @param paySuretyFeeBankID The paySuretyFeeBankID to set
	 */
	public void setPaySuretyFeeBankID(long paySuretyFeeBankID) {
		PaySuretyFeeBankID = paySuretyFeeBankID;
	}

	/**
	 * Returns the a.
	 * @return String
	 */
	public String getAbstract() {
		return Abstract;
	}

	/**
	 * Returns the abstractID.
	 * @return long
	 */
	public long getAbstractID() {
		return AbstractID;
	}

	/**
	 * Returns the amount.
	 * @return double
	 */
	public double getAmount() {
		return Amount;
	}

	/**
	 * Returns the bankID.
	 * @return long
	 */
	public long getBankID() {
		return BankID;
	}

	/**
	 * Returns the clientID.
	 * @return long
	 */
	public long getClientID() {
		return ClientID;
	}

	/**
	 * Returns the depositAccountID.
	 * @return long
	 */
	public long getDepositAccountID() {
		return DepositAccountID;
	}

	/**
	 * Returns the interestStart.
	 * @return Timestamp
	 */
	public Timestamp getInterestStart() {
		return InterestStart;
	}

	/**
	 * Sets the a.
	 * @param a The a to set
	 */
	public void setAbstract(String a) {
		Abstract = a;
	}

	/**
	 * Sets the abstractID.
	 * @param abstractID The abstractID to set
	 */
	public void setAbstractID(long abstractID) {
		AbstractID = abstractID;
	}

	/**
	 * Sets the amount.
	 * @param amount The amount to set
	 */
	public void setAmount(double amount) {
		Amount = amount;
	}

	/**
	 * Sets the bankID.
	 * @param bankID The bankID to set
	 */
	public void setBankID(long bankID) {
		BankID = bankID;
	}

	/**
	 * Sets the clientID.
	 * @param clientID The clientID to set
	 */
	public void setClientID(long clientID) {
		ClientID = clientID;
	}

	/**
	 * Sets the depositAccountID.
	 * @param depositAccountID The depositAccountID to set
	 */
	public void setDepositAccountID(long depositAccountID) {
		DepositAccountID = depositAccountID;
	}

	/**
	 * Sets the interestStart.
	 * @param interestStart The interestStart to set
	 */
	public void setInterestStart(Timestamp interestStart) {
		InterestStart = interestStart;
	}

	/**
	 * Returns the receiveInterestAccountID.
	 * @return long
	 */
	public long getReceiveInterestAccountID() {
		return ReceiveInterestAccountID;
	}

	/**
	 * Sets the receiveInterestAccountID.
	 * @param receiveInterestAccountID The receiveInterestAccountID to set
	 */
	public void setReceiveInterestAccountID(long receiveInterestAccountID) {
		ReceiveInterestAccountID = receiveInterestAccountID;
	}

	/**
	 * @return Returns the dSavedAmount.
	 */
	public double getSavedAmount()
	{
		return SavedAmount;
	}

	/**
	 * @param savedAmount The dSavedAmount to set.
	 */
	public void setSavedAmount(double savedAmount)
	{
		SavedAmount = savedAmount;
	}

	/**
	 * @return Returns the clearInterest.
	 */
	public Timestamp getClearInterest()
	{
		return ClearInterest;
	}

	/**
	 * @param clearInterestStart The clearInterestStart to set.
	 */
	public void setClearInterest(Timestamp clearInterest)
	{
		ClearInterest = clearInterest;
	}

	/**
	 * @return Returns the lCurrencyID.
	 */
	public long getCurrencyID()
	{
		return CurrencyID;
	}

	/**
	 * @param currencyID The lCurrencyID to set.
	 */
	public void setCurrencyID(long currencyID)
	{
		CurrencyID = currencyID;
	}

	/**
	 * @return Returns the officeID.
	 */
	public long getOfficeID()
	{
		return OfficeID;
	}

	/**
	 * @param officeID The officeID to set.
	 */
	public void setOfficeID(long officeID)
	{
		OfficeID = officeID;
	}

	/**
	 * @return Returns the execute.
	 */
	public Timestamp getExecute()
	{
		return Execute;
	}

	/**
	 * @param execute The execute to set.
	 */
	public void setExecute(Timestamp execute)
	{
		Execute = execute;
	}

	/**
	 * @return Returns the isAccountClear.
	 */
	public boolean isAccountClear()
	{
		return isAccountClear;
	}

	/**
	 * @param isAccountClear The isAccountClear to set.
	 */
	public void setAccountClear(boolean isAccountClear)
	{
		this.isAccountClear = isAccountClear;
	}

	/**
	 * @return Returns the latestClearInterest.
	 */
	public Timestamp getLatestClearInterest()
	{
		return LatestClearInterest;
	}

	/**
	 * @param latestClearInterest The latestClearInterest to set.
	 */
	public void setLatestClearInterest(Timestamp latestClearInterest)
	{
		LatestClearInterest = latestClearInterest;
	}

	/**
	 * @return Returns the commissionBankID.
	 */
	public long getCommissionBankID()
	{
		return CommissionBankID;
	}

	/**
	 * @param commissionBankID The commissionBankID to set.
	 */
	public void setCommissionBankID(long commissionBankID)
	{
		CommissionBankID = commissionBankID;
	}

	/**
	 * @return Returns the compoundAmount.
	 */
	public double getCompoundAmount()
	{
		return CompoundAmount;
	}

	/**
	 * @param compoundAmount The compoundAmount to set.
	 */
	public void setCompoundAmount(double compoundAmount)
	{
		CompoundAmount = compoundAmount;
	}

	/**
	 * @return Returns the compoundInterestStart.
	 */
	public Timestamp getCompoundInterestStart()
	{
		return CompoundInterestStart;
	}

	/**
	 * @param compoundInterestStart The compoundInterestStart to set.
	 */
	public void setCompoundInterestStart(Timestamp compoundInterestStart)
	{
		CompoundInterestStart = compoundInterestStart;
	}

	/**
	 * @return Returns the compoundRate.
	 */
	public double getCompoundRate()
	{
		return CompoundRate;
	}

	/**
	 * @param compoundRate The compoundRate to set.
	 */
	public void setCompoundRate(double compoundRate)
	{
		CompoundRate = compoundRate;
	}

	/**
	 * @return Returns the overDueAmount.
	 */
	public double getOverDueAmount()
	{
		return OverDueAmount;
	}

	/**
	 * @param overDueAmount The overDueAmount to set.
	 */
	public void setOverDueAmount(double overDueAmount)
	{
		OverDueAmount = overDueAmount;
	}

	/**
	 * @return Returns the overDueRate.
	 */
	public double getOverDueRate()
	{
		return OverDueRate;
	}

	/**
	 * @param overDueRate The overDueRate to set.
	 */
	public void setOverDueRate(double overDueRate)
	{
		OverDueRate = overDueRate;
	}

	/**
	 * @return Returns the overDueStart.
	 */
	public Timestamp getOverDueStart()
	{
		return OverDueStart;
	}

	/**
	 * @param overDueStart The overDueStart to set.
	 */
	public void setOverDueStart(Timestamp overDueStart)
	{
		OverDueStart = overDueStart;
	}

	/**
	 * @return Returns the commissionRate.
	 */
	public double getCommissionRate()
	{
		return CommissionRate;
	}

	/**
	 * @param commissionRate The commissionRate to set.
	 */
	public void setCommissionRate(double commissionRate)
	{
		CommissionRate = commissionRate;
	}

	/**
	 * @return Returns the commissionStart.
	 */
	public Timestamp getCommissionStart()
	{
		return CommissionStart;
	}

	/**
	 * @param commissionStart The commissionStart to set.
	 */
	public void setCommissionStart(Timestamp commissionStart)
	{
		CommissionStart = commissionStart;
	}

	/**
	 * @return Returns the loanRepaymentRate.
	 */
	public double getLoanRepaymentRate()
	{
		return LoanRepaymentRate;
	}

	/**
	 * @param loanRepaymentRate The loanRepaymentRate to set.
	 */
	public void setLoanRepaymentRate(double loanRepaymentRate)
	{
		LoanRepaymentRate = loanRepaymentRate;
	}

	/**
	 * @return Returns the suretyFeeRate.
	 */
	public double getSuretyFeeRate()
	{
		return SuretyFeeRate;
	}

	/**
	 * @param suretyFeeRate The suretyFeeRate to set.
	 */
	public void setSuretyFeeRate(double suretyFeeRate)
	{
		SuretyFeeRate = suretyFeeRate;
	}

	/**
	 * @return Returns the suretyFeeStart.
	 */
	public Timestamp getSuretyFeeStart()
	{
		return SuretyFeeStart;
	}

	/**
	 * @param suretyFeeStart The suretyFeeStart to set.
	 */
	public void setSuretyFeeStart(Timestamp suretyFeeStart)
	{
		SuretyFeeStart = suretyFeeStart;
	}

	/**
	 * @return Returns the instructionNo.
	 */
	public String getInstructionNo()
	{
		return InstructionNo;
	}

	/**
	 * @param instructionNo The instructionNo to set.
	 */
	public void setInstructionNo(String instructionNo)
	{
		InstructionNo = instructionNo;
	}

	
	public long getInterestTaxRatePlan()
	{
	
		return InterestTaxRatePlan;
	}

	
	public void setInterestTaxRatePlan(long interestTaxRatePlan)
	{
	
		InterestTaxRatePlan = interestTaxRatePlan;
	}

	public long getInterestPlanId() {
		return interestPlanId;
	}

	public void setInterestPlanId(long interestPlanId) {
		this.interestPlanId = interestPlanId;
	}

}
