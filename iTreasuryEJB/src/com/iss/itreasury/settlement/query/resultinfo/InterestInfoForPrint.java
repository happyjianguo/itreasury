package com.iss.itreasury.settlement.query.resultinfo;
import java.sql.Timestamp;
/**
 * @author gqzhang
 * 此info供打印贷款利息通知单以及活期利息通知单
 * To change this generated comment edit the template
 * variable "typecomment": Window>Preferences>Java>Templates. To enable and
 * disable the creation of type comments go to Window>Preferences>Java>Code
 * Generation.
 */
public class InterestInfoForPrint
{
	//贷款利息通知单、利息通知单共有信息
	/**
	 * 交易的ID
	 */
	long TransId = -1;
	/**
	 *借款人
	 */
	String BrrowClientName = "";
	/**
	 *委托客户名称
	 */
	private long ConsignClientID = -1;
	/**
	 *账户类型
	 */
	long AccountTypeID = -1;
	/**
	 *交易号
	 */
	String TransNo = "";
	/**
	 *账户，如委托贷款账户号、自营贷款账户号等
	 */
	long AccountID = -1;
	/**
	 *付息账户号
	 */
	long PayInterestAccountID = -1;
	/**
	 *收息账户号
	 */
	long ReceiveInterestAccountID = -1;
	/**
	 *合同号
	 */
	long ConractID = -1;
	/**
	 *借据号，如放款通知单号
	 */
	String LoanBillNo = "";
	/**
	 *借据ID，如放款通知单号
	 */
	long LoanBillID = -1;
	/**
	 *存单号，如定期支取中存单号
	 */
	String DepositNo = "";
	/**
	 *录入人
	 */
	long InputUserID = -1;
	/**
	 *复合人
	 */
	long CheckerUserID = -1;
	//贷款利息通知单
	/**
	 *正常利息
	 */
	double NormalInterest = 0.0;
	/**
	 *正常利息起息日
	 */
	Timestamp NormalStartDate = null;
	/**
	 *正常利息终息日
	 */
	Timestamp NormalEndDate = null;
	/**
	 *正常利息本金
	 */
	double NormalAmount = 0.0;
	/**
	 *正常利息利率
	 */
	double NormalRate = 0.0;
	/**
	 *复利
	 */
	double CompoundInterest = 0.0;
	/**
	 *复利起息日
	 */
	Timestamp CompoundStartDate = null;
	/**
	 *复利终息日
	 */
	Timestamp CompoundEndDate = null;
	/**
	 *复利本金
	 */
	double CompoundAmount = 0.0;
	/**
	 *复利利率
	 */
	double CompoundRate = 0.0;
	/**
	 *逾期罚息
	 */
	double OverDueInterest = 0.0;
	/**
	 *逾期罚息起息日
	 */
	Timestamp OverDueStartDate = null;
	/**
	 *逾期罚息终息日
	 */
	Timestamp OverDueEndDate = null;
	/**
	 *逾期罚息本金
	 */
	double OverDueAmount = 0.0;
	/**
	 *逾期罚息利率
	 */
	double OverDueRate = 0.0;
	/**
	 *手续费
	 */
	double Commission = 0.0;
	/**
	 *手续费起息日
	 */
	Timestamp CommissionStartDate = null;
	/**
	 *手续费终息日
	 */
	Timestamp CommissionEndDate = null;
	/**
	 *手续费本金
	 */
	double CommissionAmount = 0.0;
	/**
	 *手续费利率
	 */
	double CommissionRate = 0.0;
	/**
	 *担保费
	 */
	double SuretyFee = 0.0;
	/**
	 *担保费起息日
	 */
	Timestamp SuretyFeeStartDate = null;
	/**
	 *担保费终息日
	 */
	Timestamp SuretyFeeEndDate = null;
	/**
	 *担保费本金
	 */
	double SuretyFeeAmount = 0.0;
	/**
	 *担保费利率
	 */
	double SuretyFeeRate = 0.0;
	//活期利息通知单
	/**
	 *活期利息
	 */
	double CurrentInterest = 0.0;
	/**
	 *活期利息起息日
	 *
	 */
	Timestamp CurrentStartDate = null;
	/**
	 *活期利息终息日
	 */
	Timestamp CurrentEndDate = null;
	/**
	 *活期利息本金
	 */
	double CurrentAmount = 0.0;
	/**
	 *活期利息利率
	 */
	double CurrenttRate = 0.0;
	/**
	 *协定利息
	 */
	double ConcertedInterest = 0.0;
	/**
	 *协定利息起息日
	 */
	Timestamp ConcertedStartDate = null;
	/**
	 *协定利息终息日
	 */
	Timestamp ConcertedEndDate = null;
	/**
	 *协定利息本金
	 */
	double ConcertedAmount = 0.0;
	/**
	 *协定利息利率
	 */
	double ConcertedRate = 0.0;
	/**
	 *执行日，即转账日
	 */
	Timestamp ExecuteDate = null;
	/**
	*交易类型
	 */
	long TransactionTypeID = -1;
	/**
	 * Returns the accountID.
	 * @return long
	 */
	public long getAccountID()
	{
		return AccountID;
	}
	/**
	 * Returns the accountTypeID.
	 * @return long
	 */
	public long getAccountTypeID()
	{
		return AccountTypeID;
	}
	/**
	 * Returns the brrowClientName.
	 * @return String
	 */
	public String getBrrowClientName()
	{
		return BrrowClientName;
	}
	/**
	 * Returns the checkerUserID.
	 * @return long
	 */
	public long getCheckerUserID()
	{
		return CheckerUserID;
	}
	/**
	 * Returns the commission.
	 * @return double
	 */
	public double getCommission()
	{
		return Commission;
	}
	/**
	 * Returns the commissionAmount.
	 * @return double
	 */
	public double getCommissionAmount()
	{
		return CommissionAmount;
	}
	/**
	 * Returns the commissionEndDate.
	 * @return Timestamp
	 */
	public Timestamp getCommissionEndDate()
	{
		return CommissionEndDate;
	}
	/**
	 * Returns the commissionRate.
	 * @return double
	 */
	public double getCommissionRate()
	{
		return CommissionRate;
	}
	/**
	 * Returns the commissionStartDate.
	 * @return Timestamp
	 */
	public Timestamp getCommissionStartDate()
	{
		return CommissionStartDate;
	}
	/**
	 * Returns the compoundAmount.
	 * @return double
	 */
	public double getCompoundAmount()
	{
		return CompoundAmount;
	}
	/**
	 * Returns the compoundEndDate.
	 * @return Timestamp
	 */
	public Timestamp getCompoundEndDate()
	{
		return CompoundEndDate;
	}
	/**
	 * Returns the compoundInterest.
	 * @return double
	 */
	public double getCompoundInterest()
	{
		return CompoundInterest;
	}
	/**
	 * Returns the compoundRate.
	 * @return double
	 */
	public double getCompoundRate()
	{
		return CompoundRate;
	}
	/**
	 * Returns the compoundStartDate.
	 * @return Timestamp
	 */
	public Timestamp getCompoundStartDate()
	{
		return CompoundStartDate;
	}
	/**
	 * Returns the concertedAmount.
	 * @return double
	 */
	public double getConcertedAmount()
	{
		return ConcertedAmount;
	}
	/**
	 * Returns the concertedEndDate.
	 * @return Timestamp
	 */
	public Timestamp getConcertedEndDate()
	{
		return ConcertedEndDate;
	}
	/**
	 * Returns the concertedInterest.
	 * @return double
	 */
	public double getConcertedInterest()
	{
		return ConcertedInterest;
	}
	/**
	 * Returns the concertedRate.
	 * @return double
	 */
	public double getConcertedRate()
	{
		return ConcertedRate;
	}
	/**
	 * Returns the concertedStartDate.
	 * @return Timestamp
	 */
	public Timestamp getConcertedStartDate()
	{
		return ConcertedStartDate;
	}
	/**
	 * Returns the conractID.
	 * @return long
	 */
	public long getConractID()
	{
		return ConractID;
	}
	/**
	 * Returns the currentAmount.
	 * @return double
	 */
	public double getCurrentAmount()
	{
		return CurrentAmount;
	}
	/**
	 * Returns the currentEndDate.
	 * @return Timestamp
	 */
	public Timestamp getCurrentEndDate()
	{
		return CurrentEndDate;
	}
	/**
	 * Returns the currentInterest.
	 * @return double
	 */
	public double getCurrentInterest()
	{
		return CurrentInterest;
	}
	/**
	 * Returns the currentStartDate.
	 * @return Timestamp
	 */
	public Timestamp getCurrentStartDate()
	{
		return CurrentStartDate;
	}
	/**
	 * Returns the currenttRate.
	 * @return double
	 */
	public double getCurrenttRate()
	{
		return CurrenttRate;
	}
	/**
	 * Returns the executeDate.
	 * @return Timestamp
	 */
	public Timestamp getExecuteDate()
	{
		return ExecuteDate;
	}
	/**
	 * Returns the inputUserID.
	 * @return long
	 */
	public long getInputUserID()
	{
		return InputUserID;
	}
	/**
	 * Returns the loanBillNo.
	 * @return String
	 */
	public String getLoanBillNo()
	{
		return LoanBillNo;
	}
	/**
	 * Returns the normalAmount.
	 * @return double
	 */
	public double getNormalAmount()
	{
		return NormalAmount;
	}
	/**
	 * Returns the normalEndDate.
	 * @return Timestamp
	 */
	public Timestamp getNormalEndDate()
	{
		return NormalEndDate;
	}
	/**
	 * Returns the normalInterest.
	 * @return double
	 */
	public double getNormalInterest()
	{
		return NormalInterest;
	}
	/**
	 * Returns the normalRate.
	 * @return double
	 */
	public double getNormalRate()
	{
		return NormalRate;
	}
	/**
	 * Returns the normalStartDate.
	 * @return Timestamp
	 */
	public Timestamp getNormalStartDate()
	{
		return NormalStartDate;
	}
	/**
	 * Returns the overDueAmount.
	 * @return double
	 */
	public double getOverDueAmount()
	{
		return OverDueAmount;
	}
	/**
	 * Returns the overDueEndDate.
	 * @return Timestamp
	 */
	public Timestamp getOverDueEndDate()
	{
		return OverDueEndDate;
	}
	/**
	 * Returns the overDueInterest.
	 * @return double
	 */
	public double getOverDueInterest()
	{
		return OverDueInterest;
	}
	/**
	 * Returns the overDueRate.
	 * @return double
	 */
	public double getOverDueRate()
	{
		return OverDueRate;
	}
	/**
	 * Returns the overDueStartDate.
	 * @return Timestamp
	 */
	public Timestamp getOverDueStartDate()
	{
		return OverDueStartDate;
	}
	/**
	 * Returns the receiveInterestAccountID.
	 * @return long
	 */
	public long getReceiveInterestAccountID()
	{
		return ReceiveInterestAccountID;
	}
	/**
	 * Returns the suretyFee.
	 * @return double
	 */
	public double getSuretyFee()
	{
		return SuretyFee;
	}
	/**
	 * Returns the suretyFeeAmount.
	 * @return double
	 */
	public double getSuretyFeeAmount()
	{
		return SuretyFeeAmount;
	}
	/**
	 * Returns the suretyFeeEndDate.
	 * @return Timestamp
	 */
	public Timestamp getSuretyFeeEndDate()
	{
		return SuretyFeeEndDate;
	}
	/**
	 * Returns the suretyFeeRate.
	 * @return double
	 */
	public double getSuretyFeeRate()
	{
		return SuretyFeeRate;
	}
	/**
	 * Returns the suretyFeeStartDate.
	 * @return Timestamp
	 */
	public Timestamp getSuretyFeeStartDate()
	{
		return SuretyFeeStartDate;
	}
	/**
	 * Returns the transNo.
	 * @return String
	 */
	public String getTransNo()
	{
		return TransNo;
	}
	/**
	 * Sets the accountID.
	 * @param accountID The accountID to set
	 */
	public void setAccountID(long accountID)
	{
		AccountID = accountID;
	}
	/**
	 * Sets the accountTypeID.
	 * @param accountTypeID The accountTypeID to set
	 */
	public void setAccountTypeID(long accountTypeID)
	{
		AccountTypeID = accountTypeID;
	}
	/**
	 * Sets the brrowClientName.
	 * @param brrowClientName The brrowClientName to set
	 */
	public void setBrrowClientName(String brrowClientName)
	{
		BrrowClientName = brrowClientName;
	}
	/**
	 * Sets the checkerUserID.
	 * @param checkerUserID The checkerUserID to set
	 */
	public void setCheckerUserID(long checkerUserID)
	{
		CheckerUserID = checkerUserID;
	}
	/**
	 * Sets the commission.
	 * @param commission The commission to set
	 */
	public void setCommission(double commission)
	{
		Commission = commission;
	}
	/**
	 * Sets the commissionAmount.
	 * @param commissionAmount The commissionAmount to set
	 */
	public void setCommissionAmount(double commissionAmount)
	{
		CommissionAmount = commissionAmount;
	}
	/**
	 * Sets the commissionEndDate.
	 * @param commissionEndDate The commissionEndDate to set
	 */
	public void setCommissionEndDate(Timestamp commissionEndDate)
	{
		CommissionEndDate = commissionEndDate;
	}
	/**
	 * Sets the commissionRate.
	 * @param commissionRate The commissionRate to set
	 */
	public void setCommissionRate(double commissionRate)
	{
		CommissionRate = commissionRate;
	}
	/**
	 * Sets the commissionStartDate.
	 * @param commissionStartDate The commissionStartDate to set
	 */
	public void setCommissionStartDate(Timestamp commissionStartDate)
	{
		CommissionStartDate = commissionStartDate;
	}
	/**
	 * Sets the compoundAmount.
	 * @param compoundAmount The compoundAmount to set
	 */
	public void setCompoundAmount(double compoundAmount)
	{
		CompoundAmount = compoundAmount;
	}
	/**
	 * Sets the compoundEndDate.
	 * @param compoundEndDate The compoundEndDate to set
	 */
	public void setCompoundEndDate(Timestamp compoundEndDate)
	{
		CompoundEndDate = compoundEndDate;
	}
	/**
	 * Sets the compoundInterest.
	 * @param compoundInterest The compoundInterest to set
	 */
	public void setCompoundInterest(double compoundInterest)
	{
		CompoundInterest = compoundInterest;
	}
	/**
	 * Sets the compoundRate.
	 * @param compoundRate The compoundRate to set
	 */
	public void setCompoundRate(double compoundRate)
	{
		CompoundRate = compoundRate;
	}
	/**
	 * Sets the compoundStartDate.
	 * @param compoundStartDate The compoundStartDate to set
	 */
	public void setCompoundStartDate(Timestamp compoundStartDate)
	{
		CompoundStartDate = compoundStartDate;
	}
	/**
	 * Sets the concertedAmount.
	 * @param concertedAmount The concertedAmount to set
	 */
	public void setConcertedAmount(double concertedAmount)
	{
		ConcertedAmount = concertedAmount;
	}
	/**
	 * Sets the concertedEndDate.
	 * @param concertedEndDate The concertedEndDate to set
	 */
	public void setConcertedEndDate(Timestamp concertedEndDate)
	{
		ConcertedEndDate = concertedEndDate;
	}
	/**
	 * Sets the concertedInterest.
	 * @param concertedInterest The concertedInterest to set
	 */
	public void setConcertedInterest(double concertedInterest)
	{
		ConcertedInterest = concertedInterest;
	}
	/**
	 * Sets the concertedRate.
	 * @param concertedRate The concertedRate to set
	 */
	public void setConcertedRate(double concertedRate)
	{
		ConcertedRate = concertedRate;
	}
	/**
	 * Sets the concertedStartDate.
	 * @param concertedStartDate The concertedStartDate to set
	 */
	public void setConcertedStartDate(Timestamp concertedStartDate)
	{
		ConcertedStartDate = concertedStartDate;
	}
	/**
	 * Sets the conractID.
	 * @param conractID The conractID to set
	 */
	public void setConractID(long conractID)
	{
		ConractID = conractID;
	}
	/**
	 * Sets the currentAmount.
	 * @param currentAmount The currentAmount to set
	 */
	public void setCurrentAmount(double currentAmount)
	{
		CurrentAmount = currentAmount;
	}
	/**
	 * Sets the currentEndDate.
	 * @param currentEndDate The currentEndDate to set
	 */
	public void setCurrentEndDate(Timestamp currentEndDate)
	{
		CurrentEndDate = currentEndDate;
	}
	/**
	 * Sets the currentInterest.
	 * @param currentInterest The currentInterest to set
	 */
	public void setCurrentInterest(double currentInterest)
	{
		CurrentInterest = currentInterest;
	}
	/**
	 * Sets the currentStartDate.
	 * @param currentStartDate The currentStartDate to set
	 */
	public void setCurrentStartDate(Timestamp currentStartDate)
	{
		CurrentStartDate = currentStartDate;
	}
	/**
	 * Sets the currenttRate.
	 * @param currenttRate The currenttRate to set
	 */
	public void setCurrenttRate(double currenttRate)
	{
		CurrenttRate = currenttRate;
	}
	/**
	 * Sets the executeDate.
	 * @param executeDate The executeDate to set
	 */
	public void setExecuteDate(Timestamp executeDate)
	{
		ExecuteDate = executeDate;
	}
	/**
	 * Sets the inputUserID.
	 * @param inputUserID The inputUserID to set
	 */
	public void setInputUserID(long inputUserID)
	{
		InputUserID = inputUserID;
	}
	/**
	 * Sets the loanBillNo.
	 * @param loanBillNo The loanBillNo to set
	 */
	public void setLoanBillNo(String loanBillNo)
	{
		LoanBillNo = loanBillNo;
	}
	/**
	 * Sets the normalAmount.
	 * @param normalAmount The normalAmount to set
	 */
	public void setNormalAmount(double normalAmount)
	{
		NormalAmount = normalAmount;
	}
	/**
	 * Sets the normalEndDate.
	 * @param normalEndDate The normalEndDate to set
	 */
	public void setNormalEndDate(Timestamp normalEndDate)
	{
		NormalEndDate = normalEndDate;
	}
	/**
	 * Sets the normalInterest.
	 * @param normalInterest The normalInterest to set
	 */
	public void setNormalInterest(double normalInterest)
	{
		NormalInterest = normalInterest;
	}
	/**
	 * Sets the normalRate.
	 * @param normalRate The normalRate to set
	 */
	public void setNormalRate(double normalRate)
	{
		NormalRate = normalRate;
	}
	/**
	 * Sets the normalStartDate.
	 * @param normalStartDate The normalStartDate to set
	 */
	public void setNormalStartDate(Timestamp normalStartDate)
	{
		NormalStartDate = normalStartDate;
	}
	/**
	 * Sets the overDueAmount.
	 * @param overDueAmount The overDueAmount to set
	 */
	public void setOverDueAmount(double overDueAmount)
	{
		OverDueAmount = overDueAmount;
	}
	/**
	 * Sets the overDueEndDate.
	 * @param overDueEndDate The overDueEndDate to set
	 */
	public void setOverDueEndDate(Timestamp overDueEndDate)
	{
		OverDueEndDate = overDueEndDate;
	}
	/**
	 * Sets the overDueInterest.
	 * @param overDueInterest The overDueInterest to set
	 */
	public void setOverDueInterest(double overDueInterest)
	{
		OverDueInterest = overDueInterest;
	}
	/**
	 * Sets the overDueRate.
	 * @param overDueRate The overDueRate to set
	 */
	public void setOverDueRate(double overDueRate)
	{
		OverDueRate = overDueRate;
	}
	/**
	 * Sets the overDueStartDate.
	 * @param overDueStartDate The overDueStartDate to set
	 */
	public void setOverDueStartDate(Timestamp overDueStartDate)
	{
		OverDueStartDate = overDueStartDate;
	}
	/**
	 * Sets the receiveInterestAccountID.
	 * @param receiveInterestAccountID The receiveInterestAccountID to set
	 */
	public void setReceiveInterestAccountID(long receiveInterestAccountID)
	{
		ReceiveInterestAccountID = receiveInterestAccountID;
	}
	/**
	 * Sets the suretyFee.
	 * @param suretyFee The suretyFee to set
	 */
	public void setSuretyFee(double suretyFee)
	{
		SuretyFee = suretyFee;
	}
	/**
	 * Sets the suretyFeeAmount.
	 * @param suretyFeeAmount The suretyFeeAmount to set
	 */
	public void setSuretyFeeAmount(double suretyFeeAmount)
	{
		SuretyFeeAmount = suretyFeeAmount;
	}
	/**
	 * Sets the suretyFeeEndDate.
	 * @param suretyFeeEndDate The suretyFeeEndDate to set
	 */
	public void setSuretyFeeEndDate(Timestamp suretyFeeEndDate)
	{
		SuretyFeeEndDate = suretyFeeEndDate;
	}
	/**
	 * Sets the suretyFeeRate.
	 * @param suretyFeeRate The suretyFeeRate to set
	 */
	public void setSuretyFeeRate(double suretyFeeRate)
	{
		SuretyFeeRate = suretyFeeRate;
	}
	/**
	 * Sets the suretyFeeStartDate.
	 * @param suretyFeeStartDate The suretyFeeStartDate to set
	 */
	public void setSuretyFeeStartDate(Timestamp suretyFeeStartDate)
	{
		SuretyFeeStartDate = suretyFeeStartDate;
	}
	/**
	 * Sets the transNo.
	 * @param transNo The transNo to set
	 */
	public void setTransNo(String transNo)
	{
		TransNo = transNo;
	}
	/**
	 * @return
	 */
	public long getConsignClientID()
	{
		return ConsignClientID;
	}
	/**
	 * @param l
	 */
	public void setConsignClientID(long l)
	{
		ConsignClientID = l;
	}
	/**
	 * @return
	 */
	public String getDepositNo()
	{
		return DepositNo;
	}
	/**
	 * @param string
	 */
	public void setDepositNo(String string)
	{
		DepositNo = string;
	}
	/**
	 * @return
	 */
	public long getLoanBillID()
	{
		return LoanBillID;
	}
	/**
	 * @param l
	 */
	public void setLoanBillID(long l)
	{
		LoanBillID = l;
	}
	/**
	 * Returns the payInterestAccountID.
	 * @return long
	 */
	public long getPayInterestAccountID()
	{
		return PayInterestAccountID;
	}
	
	public long getTransId()
	{
	
		return TransId;
	}
	
	public void setTransId(long transId)
	{
	
		TransId = transId;
	}
	/**
	 * Sets the payInterestAccountID.
	 * @param payInterestAccountID The payInterestAccountID to set
	 */
	public void setPayInterestAccountID(long payInterestAccountID)
	{
		PayInterestAccountID = payInterestAccountID;
	}
	/**
	 * Returns the transactionTypeID.
	 * @return long
	 */
	public long getTransactionTypeID()
	{
		return TransactionTypeID;
	}

	/**
	 * Sets the transactionTypeID.
	 * @param transactionTypeID The transactionTypeID to set
	 */
	public void setTransactionTypeID(long transactionTypeID)
	{
		TransactionTypeID = transactionTypeID;
	}

}