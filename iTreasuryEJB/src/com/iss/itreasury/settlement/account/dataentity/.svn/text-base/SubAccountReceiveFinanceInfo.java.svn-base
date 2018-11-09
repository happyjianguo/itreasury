/*
 * Created on 2006-4-30
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.iss.itreasury.settlement.account.dataentity;

import java.sql.Timestamp;
import java.io.Serializable;

/**
 * @author feiye
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class SubAccountReceiveFinanceInfo extends SubAccountLoanInfo  implements Serializable
{
	private long ID = -1; //子账户ID
	private long AccountID = -1; //主账户ID
	private double Interest = 0.0; //当前利息
	private double Balance = 0.0; //账户当前余额
	private double OpenAmount = 0.0; //开户金额
	private Timestamp OpenDate = null; //开户日期
	private Timestamp FinishDate = null; //销户日期
	private long IsInterest = -1; //是否计息
	private Timestamp ClearInterestDate = null; //结息日期
	private long StatusID = -1; //状态

	private long LoanNoteID = -1; //放款单号
	private long IsCycLoan = -1; //是否循环贷款
	private Timestamp CalculateInterestDate = null; //算息日期
	private long PayInterestAccountID = -1; //贷款方付息账户ID
	private long ReceiveInterestAccountID = -1; //委托方收息账户ID
	private double PreDrawInterest = 0.0; //计提利息
	private Timestamp PreDrawDate = null; //计提日期
	private long PaySuretyAccountID = -1; //担保费账户ID
	private long ReceiveSuretyAccountID = -1; //担保费账户ID
	private long CommissionAccountID = -1; //手续费账户ID
	private double SuretyFee = 0.0; //担保费
	private Timestamp ClearSureFeeDate = null; //担保费结息日期
	private double Commission = 0.0; //手续费
	private Timestamp ClearCommissionDate = null; //手续费结息日期
	private long InterestTaxAccountID = -1; //利息税费账户ID
	private double InterestTax = 0.0;
	private double InterestTaxRate = 0.0; //利息税费率
	private long InterestTaxRatePlanID = -1;//利息税费率计划
	private Timestamp ClearInterestTaxDate = null;
	private Timestamp EffectiveTaxDate = null; //生效日期
	private long OverDueAccountID = -1; //罚息账户ID
	private double OverDueInterest = 0.0;
	private Timestamp ClearOverDueDate = null;
	private long CompoundAccountID = -1; //复利账户号
	private double CompoundInterest = 0.0;
	private Timestamp ClearCompoundDate = null; //计提复利日期
	private double ArrearageInterest = 0.0;//欠息金额
	private double DailyUncheckAmount = 0.0; // 当天累计未复核付款金额
	private long ConsignAccountID = -1;//委托存款账户号
	//加上一个字段：贷款方账户ID
	private long Al_nPaysuretyAccountID=-1;
	
	private double OverDueArrearageInterest = 0.0;//逾期欠息
	/**
	 * Returns the accountID.
	 * @return long
	 */
	public long getAccountID()
	{
		return AccountID;
	}

	/**
	 * Returns the balance.
	 * @return double
	 */
	public double getBalance()
	{
		return Balance;
	}

	/**
	 * Returns the calculateInterestDate.
	 * @return Timestamp
	 */
	public Timestamp getCalculateInterestDate()
	{
		return CalculateInterestDate;
	}

	/**
	 * Returns the clearCommissionDate.
	 * @return Timestamp
	 */
	public Timestamp getClearCommissionDate()
	{
		return ClearCommissionDate;
	}

	/**
	 * Returns the clearCompoundDate.
	 * @return Timestamp
	 */
	public Timestamp getClearCompoundDate()
	{
		return ClearCompoundDate;
	}

	/**
	 * Returns the clearInterestDate.
	 * @return Timestamp
	 */
	public Timestamp getClearInterestDate()
	{
		return ClearInterestDate;
	}

	/**
	 * Returns the clearInterestTaxDate.
	 * @return Timestamp
	 */
	public Timestamp getClearInterestTaxDate()
	{
		return ClearInterestTaxDate;
	}

	/**
	 * Returns the clearOverDueDate.
	 * @return Timestamp
	 */
	public Timestamp getClearOverDueDate()
	{
		return ClearOverDueDate;
	}

	/**
	 * Returns the clearSureFeeDate.
	 * @return Timestamp
	 */
	public Timestamp getClearSureFeeDate()
	{
		return ClearSureFeeDate;
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
	 * Returns the commissionAccountID.
	 * @return long
	 */
	public long getCommissionAccountID()
	{
		return CommissionAccountID;
	}

	/**
	 * Returns the compoundAccountID.
	 * @return long
	 */
	public long getCompoundAccountID()
	{
		return CompoundAccountID;
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
	 * Returns the effectiveTaxDate.
	 * @return Timestamp
	 */
	public Timestamp getEffectiveTaxDate()
	{
		return EffectiveTaxDate;
	}

	/**
	 * Returns the iD.
	 * @return long
	 */
	public long getID()
	{
		return ID;
	}

	/**
	 * Returns the interest.
	 * @return double
	 */
	public double getInterest()
	{
		return Interest;
	}

	/**
	 * Returns the interestTax.
	 * @return double
	 */
	public double getInterestTax()
	{
		return InterestTax;
	}

	/**
	 * Returns the interestTaxAccountID.
	 * @return long
	 */
	public long getInterestTaxAccountID()
	{
		return InterestTaxAccountID;
	}

	/**
	 * Returns the interestTaxRate.
	 * @return double
	 */
	public double getInterestTaxRate()
	{
		return InterestTaxRate;
	}

	/**
	 * Returns the isCycLoan.
	 * @return long
	 */
	public long getIsCycLoan()
	{
		return IsCycLoan;
	}

	/**
	 * Returns the isInterest.
	 * @return long
	 */
	public long getIsInterest()
	{
		return IsInterest;
	}

	/**
	 * Returns the loanNoteID.
	 * @return long
	 */
	public long getLoanNoteID()
	{
		return LoanNoteID;
	}

	/**
	 * Returns the overDueAccountID.
	 * @return long
	 */
	public long getOverDueAccountID()
	{
		return OverDueAccountID;
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
	 * Returns the payInterestAccountID.
	 * @return long
	 */
	public long getPayInterestAccountID()
	{
		return PayInterestAccountID;
	}

	/**
	 * Returns the preDrawDate.
	 * @return Timestamp
	 */
	public Timestamp getPreDrawDate()
	{
		return PreDrawDate;
	}

	/**
	 * Returns the preDrawInterest.
	 * @return double
	 */
	public double getPreDrawInterest()
	{
		return PreDrawInterest;
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
	 * Returns the statusID.
	 * @return long
	 */
	public long getStatusID()
	{
		return StatusID;
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
	 * Sets the accountID.
	 * @param accountID The accountID to set
	 */
	public void setAccountID(long accountID)
	{
		AccountID = accountID;
	}

	/**
	 * Sets the balance.
	 * @param balance The balance to set
	 */
	public void setBalance(double balance)
	{
		Balance = balance;
	}

	/**
	 * Sets the calculateInterestDate.
	 * @param calculateInterestDate The calculateInterestDate to set
	 */
	public void setCalculateInterestDate(Timestamp calculateInterestDate)
	{
		CalculateInterestDate = calculateInterestDate;
	}

	/**
	 * Sets the clearCommissionDate.
	 * @param clearCommissionDate The clearCommissionDate to set
	 */
	public void setClearCommissionDate(Timestamp clearCommissionDate)
	{
		ClearCommissionDate = clearCommissionDate;
	}

	/**
	 * Sets the clearCompoundDate.
	 * @param clearCompoundDate The clearCompoundDate to set
	 */
	public void setClearCompoundDate(Timestamp clearCompoundDate)
	{
		ClearCompoundDate = clearCompoundDate;
	}

	/**
	 * Sets the clearInterestTaxDate.
	 * @param clearInterestTaxDate The clearInterestTaxDate to set
	 */
	public void setClearInterestTaxDate(Timestamp clearInterestTaxDate)
	{
		ClearInterestTaxDate = clearInterestTaxDate;
	}

	/**
	 * Sets the clearOverDueDate.
	 * @param clearOverDueDate The clearOverDueDate to set
	 */
	public void setClearOverDueDate(Timestamp clearOverDueDate)
	{
		ClearOverDueDate = clearOverDueDate;
	}

	/**
	 * Sets the clearSureFeeDate.
	 * @param clearSureFeeDate The clearSureFeeDate to set
	 */
	public void setClearSureFeeDate(Timestamp clearSureFeeDate)
	{
		ClearSureFeeDate = clearSureFeeDate;
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
	 * Sets the commissionAccountID.
	 * @param commissionAccountID The commissionAccountID to set
	 */
	public void setCommissionAccountID(long commissionAccountID)
	{
		CommissionAccountID = commissionAccountID;
	}

	/**
	 * Sets the compoundAccountID.
	 * @param compoundAccountID The compoundAccountID to set
	 */
	public void setCompoundAccountID(long compoundAccountID)
	{
		CompoundAccountID = compoundAccountID;
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
	 * Sets the effectiveTaxDate.
	 * @param effectiveTaxDate The effectiveTaxDate to set
	 */
	public void setEffectiveTaxDate(Timestamp effectiveTaxDate)
	{
		EffectiveTaxDate = effectiveTaxDate;
	}

	/**
	 * Sets the iD.
	 * @param iD The iD to set
	 */
	public void setID(long iD)
	{
		ID = iD;
	}

	/**
	 * Sets the interest.
	 * @param interest The interest to set
	 */
	public void setInterest(double interest)
	{
		Interest = interest;
	}

	/**
	 * Sets the interestTax.
	 * @param interestTax The interestTax to set
	 */
	public void setInterestTax(double interestTax)
	{
		InterestTax = interestTax;
	}

	/**
	 * Sets the interestTaxAccountID.
	 * @param interestTaxAccountID The interestTaxAccountID to set
	 */
	public void setInterestTaxAccountID(long interestTaxAccountID)
	{
		InterestTaxAccountID = interestTaxAccountID;
	}

	/**
	 * Sets the interestTaxRate.
	 * @param interestTaxRate The interestTaxRate to set
	 */
	public void setInterestTaxRate(double interestTaxRate)
	{
		InterestTaxRate = interestTaxRate;
	}

	
	public long getInterestTaxRatePlanID()
	{
	
		return InterestTaxRatePlanID;
	}

	
	public void setInterestTaxRatePlanID(long interestTaxRatePlanID)
	{
	
		InterestTaxRatePlanID = interestTaxRatePlanID;
	}

	/**
	 * Sets the isCycLoan.
	 * @param isCycLoan The isCycLoan to set
	 */
	public void setIsCycLoan(long isCycLoan)
	{
		IsCycLoan = isCycLoan;
	}

	/**
	 * Sets the isInterest.
	 * @param isInterest The isInterest to set
	 */
	public void setIsInterest(long isInterest)
	{
		IsInterest = isInterest;
	}

	/**
	 * Sets the loanNoteID.
	 * @param loanNoteID The loanNoteID to set
	 */
	public void setLoanNoteID(long loanNoteID)
	{
		LoanNoteID = loanNoteID;
	}

	/**
	 * Sets the overDueAccountID.
	 * @param overDueAccountID The overDueAccountID to set
	 */
	public void setOverDueAccountID(long overDueAccountID)
	{
		OverDueAccountID = overDueAccountID;
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
	 * Sets the payInterestAccountID.
	 * @param payInterestAccountID The payInterestAccountID to set
	 */
	public void setPayInterestAccountID(long payInterestAccountID)
	{
		PayInterestAccountID = payInterestAccountID;
	}

	/**
	 * Sets the preDrawDate.
	 * @param preDrawDate The preDrawDate to set
	 */
	public void setPreDrawDate(Timestamp preDrawDate)
	{
		PreDrawDate = preDrawDate;
	}

	/**
	 * Sets the preDrawInterest.
	 * @param preDrawInterest The preDrawInterest to set
	 */
	public void setPreDrawInterest(double preDrawInterest)
	{
		PreDrawInterest = preDrawInterest;
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
	 * Sets the statusID.
	 * @param statusID The statusID to set
	 */
	public void setStatusID(long statusID)
	{
		StatusID = statusID;
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
	 * @return
	 */
	public double getDailyUncheckAmount()
	{
		return this.DailyUncheckAmount;
	}

	/**
	 * @param dailyUncheckAmount
	 */
	public void setDailyUncheckAmount(double dailyUncheckAmount)
	{
		this.DailyUncheckAmount = dailyUncheckAmount;
	}

	/**
	 * @return
	 */
	public double getArrearageInterest()
	{
		return ArrearageInterest;
	}

	/**
	 * @return
	 */
	public Timestamp getFinishDate()
	{
		return FinishDate;
	}

	/**
	 * @return
	 */
	public double getOpenAmount()
	{
		return OpenAmount;
	}

	/**
	 * @return
	 */
	public Timestamp getOpenDate()
	{
		return OpenDate;
	}

	/**
	 * @param d
	 */
	public void setArrearageInterest(double d)
	{
		ArrearageInterest = d;
	}

	/**
	 * @param timestamp
	 */
	public void setFinishDate(Timestamp timestamp)
	{
		FinishDate = timestamp;
	}

	/**
	 * @param d
	 */
	public void setOpenAmount(double d)
	{
		OpenAmount = d;
	}

	/**
	 * @param timestamp
	 */
	public void setOpenDate(Timestamp timestamp)
	{
		OpenDate = timestamp;
	}

	/**
	 * @param timestamp
	 */
	public void setClearInterestDate(Timestamp timestamp)
	{
		ClearInterestDate = timestamp;
	}

	/**
	 * @return
	 */
	public long getPaySuretyAccountID()
	{
		return PaySuretyAccountID;
	}

	/**
	 * @return
	 */
	public long getReceiveSuretyAccountID()
	{
		return ReceiveSuretyAccountID;
	}

	/**
	 * @param l
	 */
	public void setPaySuretyAccountID(long l)
	{
		PaySuretyAccountID = l;
	}

	/**
	 * @param l
	 */
	public void setReceiveSuretyAccountID(long l)
	{
		ReceiveSuretyAccountID = l;
	}

	/**
	 * @return
	 */
	public long getConsignAccountID()
	{
		return ConsignAccountID;
	}

	/**
	 * @param l
	 */
	public void setConsignAccountID(long l)
	{
		ConsignAccountID = l;
	}

	/**
	 * @return
	 */
	public long getAl_nPaysuretyAccountID()
	{
		return Al_nPaysuretyAccountID;
	}

	/**
	 * @param l
	 */
	public void setAl_nPaysuretyAccountID(long l)
	{
		Al_nPaysuretyAccountID = l;
	}

	/**
	 * @return
	 */
	public double getOverDueArrearageInterest()
	{
		return OverDueArrearageInterest;
	}

	/**
	 * @param d
	 */
	public void setOverDueArrearageInterest(double d)
	{
		OverDueArrearageInterest = d;
	}

}
