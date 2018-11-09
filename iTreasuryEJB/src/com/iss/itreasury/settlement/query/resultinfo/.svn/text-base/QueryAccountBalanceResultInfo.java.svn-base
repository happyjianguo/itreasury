/*
 * Created on 2003-11-16
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.settlement.query.resultinfo;

import java.io.Serializable;
import java.sql.Timestamp;

import com.iss.itreasury.settlement.query.queryobj.BaseQueryObject;
import com.iss.itreasury.settlement.util.SETTConstant;

/**
 * @author yiwang
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class QueryAccountBalanceResultInfo implements Serializable
{

	/**
	 * 
	 */
	public QueryAccountBalanceResultInfo()
	{
		super();
		// TODO Auto-generated constructor stub
	}
	// 公用信息
	private long OfficeID = -1;
	private long CurrencyID = -1;
	private String AccountNo = "";
	private long AccountID = -1;
	private long AccountTypeID = -1; // 账户类型
	private String AccountName = "";
	private long ClientID = -1;
	private String ClientCode = "";
	private String ClientName = "";
	private long SubAccountID = -1;
	private long AccountStatusID = -1; // 主账户状态
	private long SubAccountStatusID = -1; // 子账户状态，包括（定期、贷款状态）
	private Timestamp BalanceDate = null; // 余额查询日期
	private double InterestRate = 0.0; // 利率
	private long isToday = 0; // 标示查询日期是否是当天
	//
	//
	private double OpenAmount = 0.0; // 金额(定期开立和放款金额)
	private double Balance = 0.0; // 余额
	private double Interest = 0.0; // 利息
	private Timestamp InterestDate = null; // 起息日（定期和贷款使用）
	private Timestamp FinishDate = null; // 销户日期（定期和贷款使用）
	private Timestamp ClearInterestDate = null; // 结息日期
    private Timestamp AccountOpenDate = null;  // 开户日
    
    private double Minterestbalance=0.0;//每日计息余额

	// 活期信息
	private double CurrentInterestRate = 0.0; // 活期利率
	private long IsInterest = -1; // 是否计息
	private double UncheckPaymentAmount = 0.0; // 累计未复核金额
	private long IsOverdraft = -1; // 是否允许透支
	private long IsNegotiate = 0; // 是否协定存款
	private double NegotiateAmount = 0.0; // 协定存款金额
	private double NegotiateUnit = 0.0; // 协定存款单位
	private double NegotiateRate = 0.0; // 协定存款利率
	private double NegotiateInterest = 0.0; // 协定存款利息
	private double NegotiateBalance = 0.0; // 协定存款余额
    private long InterestPlanID = -1; // 活期利率计划
	//
	// 定期信息
	private String DepositNo = ""; // 定期单据号
	private long FixPeriod = -1; // 定期存款期限
	private Timestamp FixDepositStartDate = null; // 定期开始日
	private Timestamp FixDepositEndDate = null; // 定期到期日
	private double FixInterestRate = 0.0; // 定期利率 
	private long NoticeDay = 0; // 通知存款天数
	private double FixPreDrawInterest = 0.0; // 当前已计提利息
	private Timestamp FixPreDrawDate = null; // 上次计提日期
	//贷款信息
	private String ContractCode = ""; // 合同号
	private long ContractID = -1; // 合同ID
	private long ContractStatusID = -1; // 合同状态 
	private String LoanPayCode = ""; // 放款通知单号
	private long LoanPayID = -1; // 放款通知单ID
	private long LoanTypeID = -1; // 贷款类型
	private long ContractPeriod = -1; // 贷款合同期限
	private long ContractYear = -1; // 贷款合同年期
	private long ConsignClientID = -1; // 委托方
	private double ContractAmount = 0.0; // 合同金额
	private Timestamp ContractStartDate = null; // 合同开始日期 
	private Timestamp ContractEndDate = null; // 合同到期日 
	private Timestamp LoanPayStartDate = null; // 放款开始日期 
	private Timestamp LoanpayEnddate = null; // 放款结束日期
	private double ContractInterestRate = 0.0; // 合同利率 
	private double LoanPayRate = 0.0; // 放款利率
	private double LoanPayAmount = 0.0; // 放款金额
	private double CommissionRate = 0.0; // 手续费率
	private double SecutyFeeRate = 0.0; // 担保费率
	private double CommissionTypeID = -1; // 手续费收取方式
    private double LoanPreDrawInterest = 0.0; // 贷款计提利息
    private String LoanpayEndDate ="";
	
	//以下字段由苏元锋添加
	private long LoanPayStatusID =-1;  // 放款通知单状态
	// 累计还息

	/**
	 * @return
	 */
	public long getAccountID()
	{
		return AccountID;
	}

	/**
	 * @param accountID
	 */
	public void setAccountID(long accountID)
	{
		AccountID = accountID;
	}

	/**
	 * @return
	 */
	public String getAccountName()
	{
		return AccountName;
	}

	/**
	 * @param accountName
	 */
	public void setAccountName(String accountName)
	{
		AccountName = accountName;
	}

	/**
	 * @return
	 */
	public String getAccountNo()
	{
		return AccountNo;
	}

	/**
	 * @param accountNo
	 */
	public void setAccountNo(String accountNo)
	{
		AccountNo = accountNo;
	}

	/**
	 * @return
	 */
	public long getAccountStatusID()
	{
		return AccountStatusID;
	}

	/**
	 * @param accountStatusID
	 */
	public void setAccountStatusID(long accountStatusID)
	{
		AccountStatusID = accountStatusID;
	}

	/**
	 * @return
	 */
	public long getAccountTypeID()
	{
		return AccountTypeID;
	}

	/**
	 * @param accountTypeID
	 */
	public void setAccountTypeID(long accountTypeID)
	{
		AccountTypeID = accountTypeID;
	}

	/**
	 * @return
	 */
	public double getBalance()
	{
		return Balance;
	}

	/**
	 * @param balance
	 */
	public void setBalance(double balance)
	{
		Balance = balance;
	}

	/**
	 * @return
	 */
	public Timestamp getBalanceDate()
	{
		return BalanceDate;
	}

	/**
	 * @param balanceDate
	 */
	public void setBalanceDate(Timestamp balanceDate)
	{
		BalanceDate = balanceDate;
	}

	/**
	 * @return
	 */
	public Timestamp getClearInterestDate()
	{
		return ClearInterestDate;
	}

	/**
	 * @param clearInterestDate
	 */
	public void setClearInterestDate(Timestamp clearInterestDate)
	{
		ClearInterestDate = clearInterestDate;
	}

	/**
	 * @return
	 */
	public String getClientCode()
	{
		return ClientCode;
	}

	/**
	 * @param clientCode
	 */
	public void setClientCode(String clientCode)
	{
		ClientCode = clientCode;
	}

	/**
	 * @return
	 */
	public long getClientID()
	{
		return ClientID;
	}

	/**
	 * @param clientID
	 */
	public void setClientID(long clientID)
	{
		ClientID = clientID;
	}

	/**
	 * @return
	 */
	public String getClientName()
	{
		return ClientName;
	}

	/**
	 * @param clientName
	 */
	public void setClientName(String clientName)
	{
		ClientName = clientName;
	}

	/**
	 * @return
	 */
	public double getCommissionRate()
	{
		return CommissionRate;
	}

	/**
	 * @param commissionRate
	 */
	public void setCommissionRate(double commissionRate)
	{
		CommissionRate = commissionRate;
	}

	/**
	 * @return
	 */
	public double getCommissionTypeID()
	{
		return CommissionTypeID;
	}

	/**
	 * @param commissionTypeID
	 */
	public void setCommissionTypeID(double commissionTypeID)
	{
		CommissionTypeID = commissionTypeID;
	}

	/**
	 * @return
	 */
	public long getConsignClientID()
	{
		return ConsignClientID;
	}

	/**
	 * @param consignClientID
	 */
	public void setConsignClientID(long consignClientID)
	{
		ConsignClientID = consignClientID;
	}

	/**
	 * @return
	 */
	public double getContractAmount()
	{
		return ContractAmount;
	}

	/**
	 * @param contractAmount
	 */
	public void setContractAmount(double contractAmount)
	{
		ContractAmount = contractAmount;
	}

	/**
	 * @return
	 */
	public String getContractCode()
	{
		return ContractCode;
	}

	/**
	 * @param contractCode
	 */
	public void setContractCode(String contractCode)
	{
		ContractCode = contractCode;
	}

	/**
	 * @return
	 */
	public Timestamp getContractEndDate()
	{
		return ContractEndDate;
	}

	/**
	 * @param contractEndDate
	 */
	public void setContractEndDate(Timestamp contractEndDate)
	{
		ContractEndDate = contractEndDate;
	}

	/**
	 * @return
	 */
	public long getContractID()
	{
		return ContractID;
	}

	/**
	 * @param contractID
	 */
	public void setContractID(long contractID)
	{
		ContractID = contractID;
	}

	/**
	 * @return
	 */
	public double getContractInterestRate()
	{
		return ContractInterestRate;
	}

	/**
	 * @param contractInterestRate
	 */
	public void setContractInterestRate(double contractInterestRate)
	{
		ContractInterestRate = contractInterestRate;
	}

	/**
	 * @return
	 */
	public long getContractPeriod()
	{
		return ContractPeriod;
	}

	/**
	 * @param contractPeriod
	 */
	public void setContractPeriod(long contractPeriod)
	{
		ContractPeriod = contractPeriod;
	}

	/**
	 * @return
	 */
	public Timestamp getContractStartDate()
	{
		return ContractStartDate;
	}

	/**
	 * @param contractStartDate
	 */
	public void setContractStartDate(Timestamp contractStartDate)
	{
		ContractStartDate = contractStartDate;
	}

	/**
	 * @return
	 */
	public long getContractStatusID()
	{
		return ContractStatusID;
	}

	/**
	 * @param contractStatusID
	 */
	public void setContractStatusID(long contractStatusID)
	{
		ContractStatusID = contractStatusID;
	}

	/**
	 * @return
	 */
	public long getContractYear()
	{
		return ContractYear;
	}

	/**
	 * @param contractYear
	 */
	public void setContractYear(long contractYear)
	{
		ContractYear = contractYear;
	}

	/**
	 * @return
	 */
	public long getCurrencyID()
	{
		return CurrencyID;
	}

	/**
	 * @param currencyID
	 */
	public void setCurrencyID(long currencyID)
	{
		CurrencyID = currencyID;
	}

	/**
	 * @return
	 */
	public double getCurrentInterestRate()
	{
		return CurrentInterestRate;
	}

	/**
	 * @param currentInterestRate
	 */
	public void setCurrentInterestRate(double currentInterestRate)
	{
		CurrentInterestRate = currentInterestRate;
	}

	/**
	 * @return
	 */
	public String getDepositNo()
	{
		return DepositNo;
	}

	/**
	 * @param depositNo
	 */
	public void setDepositNo(String depositNo)
	{
		DepositNo = depositNo;
	}

	/**
	 * @return
	 */
	public Timestamp getFinishDate()
	{
		return FinishDate;
	}

	/**
	 * @param finishDate
	 */
	public void setFinishDate(Timestamp finishDate)
	{
		FinishDate = finishDate;
	}

	/**
	 * @return
	 */
	public Timestamp getFixDepositEndDate()
	{
		return FixDepositEndDate;
	}

	/**
	 * @param fixDepositEndDate
	 */
	public void setFixDepositEndDate(Timestamp fixDepositEndDate)
	{
		FixDepositEndDate = fixDepositEndDate;
	}

	/**
	 * @return
	 */
	public Timestamp getFixDepositStartDate()
	{
		return FixDepositStartDate;
	}

	/**
	 * @param fixDepositStartDate
	 */
	public void setFixDepositStartDate(Timestamp fixDepositStartDate)
	{
		FixDepositStartDate = fixDepositStartDate;
	}

	/**
	 * @return
	 */
	public double getFixInterestRate()
	{
		return FixInterestRate;
	}

	/**
	 * @param fixInterestRate
	 */
	public void setFixInterestRate(double fixInterestRate)
	{
		FixInterestRate = fixInterestRate;
	}

	/**
	 * @return
	 */
	public long getFixPeriod()
	{
		return FixPeriod;
	}

	/**
	 * @param fixPeriod
	 */
	public void setFixPeriod(long fixPeriod)
	{
		FixPeriod = fixPeriod;
	}

	/**
	 * @return
	 */
	public Timestamp getFixPreDrawDate()
	{
		return FixPreDrawDate;
	}

	/**
	 * @param fixPreDrawDate
	 */
	public void setFixPreDrawDate(Timestamp fixPreDrawDate)
	{
		FixPreDrawDate = fixPreDrawDate;
	}

	/**
	 * @return
	 */
	public double getFixPreDrawInterest()
	{
		return FixPreDrawInterest;
	}

	/**
	 * @param fixPreDrawInterest
	 */
	public void setFixPreDrawInterest(double fixPreDrawInterest)
	{
		FixPreDrawInterest = fixPreDrawInterest;
	}

	/**
	 * @return
	 */
	public double getInterest()
	{
		return Interest;
	}

	/**
	 * @param interest
	 */
	public void setInterest(double interest)
	{
		Interest = interest;
	}

	/**
	 * @return
	 */
	public Timestamp getInterestDate()
	{
		return InterestDate;
	}

	/**
	 * @param interestDate
	 */
	public void setInterestDate(Timestamp interestDate)
	{
		InterestDate = interestDate;
	}

	/**
	 * @return
	 */
	public double getInterestRate()
	{
		if (isToday == 0)
		{
            // 如果查询日期不是当天，利率取自sett_DailyAccountBalance
			return InterestRate;
		}
		else
		{
            // 如果查询日期是当日,利率取法如下
			if (SETTConstant.AccountType.isCurrentAccountType(AccountTypeID)
			        ||SETTConstant.AccountType.isOtherDepositAccountType(AccountTypeID))
				return BaseQueryObject.getCurrentInterestRate(AccountOpenDate, BalanceDate, InterestPlanID, Balance);
			else
				if (SETTConstant.AccountType.isFixAccountType(AccountTypeID)
				        ||SETTConstant.AccountType.isNotifyAccountType(AccountTypeID))
					return getFixInterestRate();
				else
					return getContractInterestRate();
		}
	}

	/**
	 * @param interestRate
	 */
	public void setInterestRate(double interestRate)
	{
		InterestRate = interestRate;
	}

	/**
	 * @return
	 */
	public long getIsInterest()
	{
		return IsInterest;
	}

	/**
	 * @param isInterest
	 */
	public void setIsInterest(long isInterest)
	{
		IsInterest = isInterest;
	}

	/**
	 * @return
	 */
	public long getIsNegotiate()
	{
		return IsNegotiate;
	}

	/**
	 * @param isNegotiate
	 */
	public void setIsNegotiate(long isNegotiate)
	{
		IsNegotiate = isNegotiate;
	}

	/**
	 * @return
	 */
	public long getIsOverdraft()
	{
		return IsOverdraft;
	}

	/**
	 * @param isOverdraft
	 */
	public void setIsOverdraft(long isOverdraft)
	{
		IsOverdraft = isOverdraft;
	}

	/**
	 * @return
	 */
	public double getLoanPayAmount()
	{
		return LoanPayAmount;
	}

	/**
	 * @param loanPayAmount
	 */
	public void setLoanPayAmount(double loanPayAmount)
	{
		LoanPayAmount = loanPayAmount;
	}

	/**
	 * @return
	 */
	public String getLoanPayCode()
	{
		return LoanPayCode;
	}

	/**
	 * @param loanPayCode
	 */
	public void setLoanPayCode(String loanPayCode)
	{
		LoanPayCode = loanPayCode;
	}

	/**
	 * @return
	 */
	public Timestamp getLoanpayEnddate()
	{
		return LoanpayEnddate;
	}

	/**
	 * @param loanpayEnddate
	 */
	public void setLoanpayEnddate(Timestamp loanpayEnddate)
	{
		LoanpayEnddate = loanpayEnddate;
	}

	/**
	 * @return
	 */
	public long getLoanPayID()
	{
		return LoanPayID;
	}

	/**
	 * @param loanPayID
	 */
	public void setLoanPayID(long loanPayID)
	{
		LoanPayID = loanPayID;
	}

	/**
	 * @return
	 */
	public double getLoanPayRate()
	{
		return LoanPayRate;
	}

	/**
	 * @param loanPayRate
	 */
	public void setLoanPayRate(double loanPayRate)
	{
		LoanPayRate = loanPayRate;
	}

	/**
	 * @return
	 */
	public Timestamp getLoanPayStartDate()
	{
		return LoanPayStartDate;
	}

	/**
	 * @param loanPayStartDate
	 */
	public void setLoanPayStartDate(Timestamp loanPayStartDate)
	{
		LoanPayStartDate = loanPayStartDate;
	}

	/**
	 * @return
	 */
	public long getLoanTypeID()
	{
		return LoanTypeID;
	}

	/**
	 * @param loanTypeID
	 */
	public void setLoanTypeID(long loanTypeID)
	{
		LoanTypeID = loanTypeID;
	}

	/**
	 * @return
	 */
	public double getNegotiateAmount()
	{
		return NegotiateAmount;
	}

	/**
	 * @param negotiateAmount
	 */
	public void setNegotiateAmount(double negotiateAmount)
	{
		NegotiateAmount = negotiateAmount;
	}

	/**
	 * @return
	 */
	public double getNegotiateBalance()
	{
		return NegotiateBalance;
	}

	/**
	 * @param negotiateBalance
	 */
	public void setNegotiateBalance(double negotiateBalance)
	{
		NegotiateBalance = negotiateBalance;
	}

	/**
	 * @return
	 */
	public double getNegotiateInterest()
	{
		return NegotiateInterest;
	}

	/**
	 * @param negotiateInterest
	 */
	public void setNegotiateInterest(double negotiateInterest)
	{
		NegotiateInterest = negotiateInterest;
	}

	/**
	 * @return
	 */
	public double getNegotiateRate()
	{
		return NegotiateRate;
	}

	/**
	 * @param negotiateRate
	 */
	public void setNegotiateRate(double negotiateRate)
	{
		NegotiateRate = negotiateRate;
	}

	/**
	 * @return
	 */
	public double getNegotiateUnit()
	{
		return NegotiateUnit;
	}

	/**
	 * @param negotiateUnit
	 */
	public void setNegotiateUnit(double negotiateUnit)
	{
		NegotiateUnit = negotiateUnit;
	}

	/**
	 * @return
	 */
	public long getNoticeDay()
	{
		return NoticeDay;
	}

	/**
	 * @param noticeDay
	 */
	public void setNoticeDay(long noticeDay)
	{
		NoticeDay = noticeDay;
	}

	/**
	 * @return
	 */
	public long getOfficeID()
	{
		return OfficeID;
	}

	/**
	 * @param officeID
	 */
	public void setOfficeID(long officeID)
	{
		OfficeID = officeID;
	}

	/**
	 * @return
	 */
	public double getOpenAmount()
	{
		return OpenAmount;
	}

	/**
	 * @param openAmount
	 */
	public void setOpenAmount(double openAmount)
	{
		OpenAmount = openAmount;
	}

	/**
	 * @return
	 */
	public double getSecutyFeeRate()
	{
		return SecutyFeeRate;
	}

	/**
	 * @param secutyFeeRate
	 */
	public void setSecutyFeeRate(double secutyFeeRate)
	{
		SecutyFeeRate = secutyFeeRate;
	}

	/**
	 * @return
	 */
	public long getSubAccountID()
	{
		return SubAccountID;
	}

	/**
	 * @param subAccountID
	 */
	public void setSubAccountID(long subAccountID)
	{
		SubAccountID = subAccountID;
	}

	/**
	 * @return
	 */
	public long getSubAccountStatusID()
	{
		return SubAccountStatusID;
	}

	/**
	 * @param subAccountStatusID
	 */
	public void setSubAccountStatusID(long subAccountStatusID)
	{
		SubAccountStatusID = subAccountStatusID;
	}

	/**
	 * @return
	 */
	public double getUncheckPaymentAmount()
	{
		return UncheckPaymentAmount;
	}

	/**
	 * @param uncheckPaymentAmount
	 */
	public void setUncheckPaymentAmount(double uncheckPaymentAmount)
	{
		UncheckPaymentAmount = uncheckPaymentAmount;
	}

	/**
	 * @return
	 */
	public Timestamp getAccountOpenDate()
	{
		return AccountOpenDate;
	}

	/**
	 * @param accountOpenDate
	 */
	public void setAccountOpenDate(Timestamp accountOpenDate)
	{
		AccountOpenDate = accountOpenDate;
	}

	/**
	 * @return
	 */
	public long getInterestPlanID()
	{
		return InterestPlanID;
	}

	/**
	 * @param interestPlanID
	 */
	public void setInterestPlanID(long interestPlanID)
	{
		InterestPlanID = interestPlanID;
	}

	/**
	 * @return
	 */
	public long getIsToday()
	{
		return isToday;
	}

	/**
	 * @param isToday
	 */
	public void setIsToday(long isToday)
	{
		this.isToday = isToday;
	}

	/**
	 * @return
	 */
	public long getLoanPayStatusID()
	{
		return LoanPayStatusID;
	}

	/**
	 * @param l
	 */
	public void setLoanPayStatusID(long l)
	{
		LoanPayStatusID = l;
	}

	/**
	 * @return
	 */
	public double getLoanPreDrawInterest()
	{
		return LoanPreDrawInterest;
	}

	/**
	 * @param loanPreDrawInterest
	 */
	public void setLoanPreDrawInterest(double loanPreDrawInterest)
	{
		LoanPreDrawInterest = loanPreDrawInterest;
	}

	public double getMinterestbalance() {
		return Minterestbalance;
	}

	public void setMinterestbalance(double minterestbalance) {
		Minterestbalance = minterestbalance;
	}

	public String getLoanpayEndDate() {
		return LoanpayEndDate;
	}

	public void setLoanpayEndDate(String loanpayEndDate) {
		LoanpayEndDate = loanpayEndDate;
	}



}
