
package com.iss.itreasury.settlement.transmargindeposit.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author gqfang 保证金存款支取交易实体类： 1、所有变量都为Private,设置只能用setXXX方法，得到只能用getXXX方法。
 *         2、包含变量、类型、默认值、说明 To change the template for this generated type
 *         comment go to Window>Preferences>Java>Code Generation>Code and
 *         Comments
 */
public class TransMarginInterestInfo implements Serializable
{

	// 主信息
	private long		ID									= -1;	// 唯一标识

	private long		OfficeID							= -1;	// 办事处标识

	private long		CurrencyID							= -1;	// 币种标识

	private String		TransNo								= "";	// 交易编号

	private long		TransactionTypeID					= -1;	// 交易类型（定期开立、定期支取、定期续期转存、通知开立、通知支取）

	private long		AccountID							= -1;	// 定期账户ID

	private long		SubAccountID						= -1;	// 子定期账户ID
	
	private long		AccountTypeID                       = -1;	//账户类型
	
	private long		InterestType						= -1;	//利息类型
	
	private long		OperationType						= -1;	//操作类型
	
	private Timestamp	InterestSettment					= null;	//结息日
	
	private Timestamp	InterestStart						= null;	//起息日
	
	private Timestamp	InterestEnd							= null;	//终息日
	
	private	long		InterestDays						= -1;	//计息天数
	
	private long		BaseBalance							= -1;	//计息金额
	
	private double		Rate								= 0.0;	//执行利率
	
	private long		PecisionInterest					= -1;	//精确的利息值
	
	private double 		Interest							= 0.0;	//利息值
	
	private long		NegotiateBalance					= -1;
	
	private double		NegotiateRate						= 0.0;
	
	private long 		NegotiatePecisionInterest			= -1;
	
	private long		NegotiateInterest					= -1;
	
	private double 		InterestTaxRate						= 0.0;
	
	private long		InterestTax							= -1;
	
	private long 		PayInterestAccountID				= -1;
	
	private long		ReceiveInterestAccountID			= -1;	// 收息账户ID
	
	private Timestamp 	Execute								= null;

	private long 		InputUserID							= -1;
	
	private String		Abstract							= "";
	
	private String		CheckAbstract						= "";
	
	private long 		IsSave								= -1;
	
	private long 		IsKeepAccount						= -1;
	
	private long		IsSuccess							= -1;
	
	private String		FaultReason							= "";
	
	private long		InterestFeeSettingDetailID			= -1;
	
	private Timestamp 	AutoExecute							= null;
	
	private long		BatchNo								= -1;
	
	private long		StatusID							= -1;
	
	
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
	 * @return Returns the accountID.
	 */
	public long getAccountID() {
		return AccountID;
	}
	/**
	 * @param accountID The accountID to set.
	 */
	public void setAccountID(long accountID) {
		AccountID = accountID;
	}
	/**
	 * @return Returns the accountTypeID.
	 */
	public long getAccountTypeID() {
		return AccountTypeID;
	}
	/**
	 * @param accountTypeID The accountTypeID to set.
	 */
	public void setAccountTypeID(long accountTypeID) {
		AccountTypeID = accountTypeID;
	}
	/**
	 * @return Returns the autoExecute.
	 */
	public Timestamp getAutoExecute() {
		return AutoExecute;
	}
	/**
	 * @param autoExecute The autoExecute to set.
	 */
	public void setAutoExecute(Timestamp autoExecute) {
		AutoExecute = autoExecute;
	}
	/**
	 * @return Returns the baseBalance.
	 */
	public long getBaseBalance() {
		return BaseBalance;
	}
	/**
	 * @param baseBalance The baseBalance to set.
	 */
	public void setBaseBalance(long baseBalance) {
		BaseBalance = baseBalance;
	}
	/**
	 * @return Returns the batchNo.
	 */
	public long getBatchNo() {
		return BatchNo;
	}
	/**
	 * @param batchNo The batchNo to set.
	 */
	public void setBatchNo(long batchNo) {
		BatchNo = batchNo;
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
	 * @return Returns the execute.
	 */
	public Timestamp getExecute() {
		return Execute;
	}
	/**
	 * @param execute The execute to set.
	 */
	public void setExecute(Timestamp execute) {
		Execute = execute;
	}
	/**
	 * @return Returns the faultReason.
	 */
	public String getFaultReason() {
		return FaultReason;
	}
	/**
	 * @param faultReason The faultReason to set.
	 */
	public void setFaultReason(String faultReason) {
		FaultReason = faultReason;
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
	 * @return Returns the interestDays.
	 */
	public long getInterestDays() {
		return InterestDays;
	}
	/**
	 * @param interestDays The interestDays to set.
	 */
	public void setInterestDays(long interestDays) {
		InterestDays = interestDays;
	}
	/**
	 * @return Returns the interestEnd.
	 */
	public Timestamp getInterestEnd() {
		return InterestEnd;
	}
	/**
	 * @param interestEnd The interestEnd to set.
	 */
	public void setInterestEnd(Timestamp interestEnd) {
		InterestEnd = interestEnd;
	}
	/**
	 * @return Returns the interestFeeSettingDetailID.
	 */
	public long getInterestFeeSettingDetailID() {
		return InterestFeeSettingDetailID;
	}
	/**
	 * @param interestFeeSettingDetailID The interestFeeSettingDetailID to set.
	 */
	public void setInterestFeeSettingDetailID(long interestFeeSettingDetailID) {
		InterestFeeSettingDetailID = interestFeeSettingDetailID;
	}
	/**
	 * @return Returns the interestSettment.
	 */
	public Timestamp getInterestSettment() {
		return InterestSettment;
	}
	/**
	 * @param interestSettment The interestSettment to set.
	 */
	public void setInterestSettment(Timestamp interestSettment) {
		InterestSettment = interestSettment;
	}
	/**
	 * @return Returns the interestStart.
	 */
	public Timestamp getInterestStart() {
		return InterestStart;
	}
	/**
	 * @param interestStart The interestStart to set.
	 */
	public void setInterestStart(Timestamp interestStart) {
		InterestStart = interestStart;
	}
	/**
	 * @return Returns the interestTax.
	 */
	public long getInterestTax() {
		return InterestTax;
	}
	/**
	 * @param interestTax The interestTax to set.
	 */
	public void setInterestTax(long interestTax) {
		InterestTax = interestTax;
	}
	/**
	 * @return Returns the interestTaxRate.
	 */
	public double getInterestTaxRate() {
		return InterestTaxRate;
	}
	/**
	 * @param interestTaxRate The interestTaxRate to set.
	 */
	public void setInterestTaxRate(double interestTaxRate) {
		InterestTaxRate = interestTaxRate;
	}
	/**
	 * @return Returns the interestType.
	 */
	public long getInterestType() {
		return InterestType;
	}
	/**
	 * @param interestType The interestType to set.
	 */
	public void setInterestType(long interestType) {
		InterestType = interestType;
	}
	/**
	 * @return Returns the isKeepAccount.
	 */
	public long getIsKeepAccount() {
		return IsKeepAccount;
	}
	/**
	 * @param isKeepAccount The isKeepAccount to set.
	 */
	public void setIsKeepAccount(long isKeepAccount) {
		IsKeepAccount = isKeepAccount;
	}
	/**
	 * @return Returns the isSave.
	 */
	public long getIsSave() {
		return IsSave;
	}
	/**
	 * @param isSave The isSave to set.
	 */
	public void setIsSave(long isSave) {
		IsSave = isSave;
	}
	/**
	 * @return Returns the isSuccess.
	 */
	public long getIsSuccess() {
		return IsSuccess;
	}
	/**
	 * @param isSuccess The isSuccess to set.
	 */
	public void setIsSuccess(long isSuccess) {
		IsSuccess = isSuccess;
	}
	/**
	 * @return Returns the negotiateBalance.
	 */
	public long getNegotiateBalance() {
		return NegotiateBalance;
	}
	/**
	 * @param negotiateBalance The negotiateBalance to set.
	 */
	public void setNegotiateBalance(long negotiateBalance) {
		NegotiateBalance = negotiateBalance;
	}
	/**
	 * @return Returns the negotiateInterest.
	 */
	public long getNegotiateInterest() {
		return NegotiateInterest;
	}
	/**
	 * @param negotiateInterest The negotiateInterest to set.
	 */
	public void setNegotiateInterest(long negotiateInterest) {
		NegotiateInterest = negotiateInterest;
	}
	/**
	 * @return Returns the negotiatePecisionInterest.
	 */
	public long getNegotiatePecisionInterest() {
		return NegotiatePecisionInterest;
	}
	/**
	 * @param negotiatePecisionInterest The negotiatePecisionInterest to set.
	 */
	public void setNegotiatePecisionInterest(long negotiatePecisionInterest) {
		NegotiatePecisionInterest = negotiatePecisionInterest;
	}
	/**
	 * @return Returns the negotiateRate.
	 */
	public double getNegotiateRate() {
		return NegotiateRate;
	}
	/**
	 * @param negotiateRate The negotiateRate to set.
	 */
	public void setNegotiateRate(double negotiateRate) {
		NegotiateRate = negotiateRate;
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
	 * @return Returns the operationType.
	 */
	public long getOperationType() {
		return OperationType;
	}
	/**
	 * @param operationType The operationType to set.
	 */
	public void setOperationType(long operationType) {
		OperationType = operationType;
	}
	/**
	 * @return Returns the payInterestAccountID.
	 */
	public long getPayInterestAccountID() {
		return PayInterestAccountID;
	}
	/**
	 * @param payInterestAccountID The payInterestAccountID to set.
	 */
	public void setPayInterestAccountID(long payInterestAccountID) {
		PayInterestAccountID = payInterestAccountID;
	}
	/**
	 * @return Returns the pecisionInterest.
	 */
	public long getPecisionInterest() {
		return PecisionInterest;
	}
	/**
	 * @param pecisionInterest The pecisionInterest to set.
	 */
	public void setPecisionInterest(long pecisionInterest) {
		PecisionInterest = pecisionInterest;
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
		Rate = rate;
	}
	/**
	 * @return Returns the receiveInterestAccountID.
	 */
	public long getReceiveInterestAccountID() {
		return ReceiveInterestAccountID;
	}
	/**
	 * @param receiveInterestAccountID The receiveInterestAccountID to set.
	 */
	public void setReceiveInterestAccountID(long receiveInterestAccountID) {
		ReceiveInterestAccountID = receiveInterestAccountID;
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
	 * @return Returns the subAccountID.
	 */
	public long getSubAccountID() {
		return SubAccountID;
	}
	/**
	 * @param subAccountID The subAccountID to set.
	 */
	public void setSubAccountID(long subAccountID) {
		SubAccountID = subAccountID;
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
	
	/**
	 * @return Returns the interest.
	 */
	public double getInterest() {
		return Interest;
	}
	/**
	 * @param interest The interest to set.
	 */
	public void setInterest(double interest) {
		Interest = interest;
	}
}