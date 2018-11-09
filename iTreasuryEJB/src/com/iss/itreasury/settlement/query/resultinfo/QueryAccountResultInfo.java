/*
 * Created on 2003-10-30
 * 
 * To change the template for this generated file go to Window - Preferences - Java - Code Generation - Code and
 * Comments
 */
package com.iss.itreasury.settlement.query.resultinfo;

import java.io.Serializable;
import java.sql.Timestamp;

import com.iss.itreasury.settlement.util.NameRef;
import com.iss.itreasury.settlement.query.queryobj.BaseQueryObject;
import com.iss.itreasury.settlement.util.SETTConstant;

/**
 * @author yiwang
 * 
 * To change the template for this generated type comment go to Window - Preferences - Java - Code Generation - Code
 * and Comments
 */
public class QueryAccountResultInfo implements Serializable
{

	/**
	 *  
	 */
	public QueryAccountResultInfo()
	{
		super();
		// TODO Auto-generated constructor stub
	}
	private String AccountNo = "";
	private long AccountID = -1;
	private long AccountTypeID = -1;
	private String AccountName = "";
	private long ClientID = -1;
	private String ClientCode = "";
	private String QueryPassWord = "";//查询密码
	private String ClientName = "";
	private long SubAccountID = -1;
    private Timestamp BalanceDate = null; // 余额查询日期
    private long isToday = 0; // 标示查询日期是否是当天

	// 账户状态
	private long MainAccountStatusID = -1; // 活期使用
	private long SubAccountStatusID = -1; // 定期和贷款使用
	private long AccountStatusID = -1; //  供显示使用
	//
    private double UncheckPaymentAmount = 0.0; // 累计未复核金额
    //
	private Timestamp OpenDate = null; // 开户日
	private Timestamp ClearDate = null; // 清户日
	private double Balance = 0.0;
	//
	private long IsWithInterest = 0; // 是否计息
	private long InterestPlanID = -1; // 利率计划
	private Timestamp InterestPlanEffectDate = null; // 利率计划生效日
	//利率
	private double CurrentInterestRate = 0.0; // 活期利率
	private double FixInterestRate = 0.0; // 定期利率
	private double LoanInterestRate = 0.0; // 贷款利率
	private double InterestRate = 0.0; // 供显示使用

	private double Interest = 0.0; // 累计利息
	// 结息账户
	private long CurrentInterestAccountID = -1; // 活期结息账户
	private long FixInterestAccountID = -1; // 定期结息账户
	private long IntrustLoanInterestAccountID = -1; // 信托贷款款结息账户
	private long ConsignLoanInterestAccountID = -1; // 委托贷款结息账户
	private long InterestAccountID = -1; // 供显示使用
	//到期日
	private Timestamp CurrentMaturityDate = null; // 活期到期日
	private Timestamp FixMaturityDate = null; // 定期到期日
	private Timestamp LoanMaturityDate = null; // 贷款到期日
	private Timestamp MaturityDate = null; // 供显示使用

	// 单据号
	private String DepositNo = ""; // 定期单据号
	private String LoanPayForm = ""; // 放款通知单
	private String FormNo = ""; // 供显示使用

	private long IsOverDraft = 0; // 是否允许透支

	private long IsCommission = 0; // 是否收手续费
	private long IsSuretyFee = 0; // 是否收担保费
	private long CommissionAccountID = -1; // 手续费账户
	private long SuretyFeeAccountID = -1; // 担保费账户
	//
	private double CommissionRate = 0.0; // 手续费率
	private double SuretyFeeRate = 0.0; // 担保费率
	private long IsNegotiate = 0; // 是否协定存款
	// added by chengjd 2008/10/13 协定存款利率调整功能
	private double MNegotiateRate=0.0; //协定存款利率
	private double MNegotiateInterest=0.0; //当前协定利息
	
	private double AvailableBalance = 0.0; // 可用余额
	private double LimitAmount = 0.0; 	//资金限额
	private long SubAccountStatus = -1;	//子账户状态
	private String Sabstract = "";//备注
	
	// added by mzh_fu 2008/05/08 解决账户余额查询不统一问题,增加下面7行
	private long firstLimitTypeId = -1;
	private double firstLimitAmount = 0.0;
	private long secondLimitTypeId = -1;
	private double secondLimitAmount = 0.0;
	private long thirdLimitTypeId = -1;
	private double thirdLimitAmount = 0.0;
	private double capitalLimitAmount = 0.0;	

	/**  
	 * @return Returns the accountID.
	 */
	public long getAccountID()
	{
		return AccountID;
	}
	/**
	 * @param accountID
	 *            The accountID to set.
	 */
	public void setAccountID(long accountID)
	{
		AccountID = accountID;
	}
	/**
	 * @return Returns the accountNo.
	 */
	public String getAccountNo()
	{
		return AccountNo;
	}
	/**
	 * @param accountNo
	 *            The accountNo to set.
	 */
	public void setAccountNo(String accountNo)
	{
		AccountNo = accountNo;
	}
	/**
	 * @return Returns the accountStatusID.
	 */
	public long getSubAccountStatusID()
	{
        return SubAccountStatusID;
	}
	/**
	 * @param accountStatusID
	 *            The accountStatusID to set.
	 */
	public void setSubAccountStatusID(long accountStatusID)
	{
		SubAccountStatusID = accountStatusID;
	}
	/**
	 * @return Returns the accountTypeID.
	 */
	public long getAccountTypeID()
	{
		return AccountTypeID;
	}
	/**
	 * @param accountTypeID
	 *            The accountTypeID to set.
	 */
	public void setAccountTypeID(long accountTypeID)
	{
		AccountTypeID = accountTypeID;
	}
	/**
	 * @return Returns the availableBalance.
	 */
	public double getAvailableBalance()
	{
		return AvailableBalance;
	}
	/**
	 * @param availableBalance
	 *            The availableBalance to set.
	 */
	public void setAvailableBalance(double availableBalance)
	{
		AvailableBalance = availableBalance;
	}
	/**
	 * @return Returns the balance.
	 */
	public double getBalance()
	{
		return Balance;
	}
	/**
	 * @param balance
	 *            The balance to set.
	 */
	public void setBalance(double balance)
	{
		Balance = balance;
	}
	/**
	 * @return Returns the clearDate.
	 */
	public Timestamp getClearDate()
	{
		return ClearDate;
	}
	/**
	 * @param clearDate
	 *            The clearDate to set.
	 */
	public void setClearDate(Timestamp clearDate)
	{
		ClearDate = clearDate;
	}
	/**
	 * @return Returns the clientCode.
	 */
	public String getClientCode()
	{
		return ClientCode;
	}
	/**
	 * @param clientCode
	 *            The clientCode to set.
	 */
	public void setClientCode(String clientCode)
	{
		ClientCode = clientCode;
	}
	/**
	 * @return Returns the clientID.
	 */
	public long getClientID()
	{
		return ClientID;
	}
	/**
	 * @param clientID
	 *            The clientID to set.
	 */
	public void setClientID(long clientID)
	{
		ClientID = clientID;
	}
	/**
	 * @return Returns the commissionRate.
	 */
	public double getCommissionRate()
	{
		return CommissionRate;
	}
	/**
	 * @param commissionRate
	 *            The commissionRate to set.
	 */
	public void setCommissionRate(double commissionRate)
	{
		CommissionRate = commissionRate;
	}
	/**
	 * @return Returns the formNo.
	 */
	public String getDepositNo()
	{
		return DepositNo;
	}
	/**
	 * @param formNo
	 *            The formNo to set.
	 */
	public void setDepositNo(String formNo)
	{
		DepositNo = formNo;
	}
	/**
	 * @return Returns the interest.
	 */
	public double getInterest()
	{
		return Interest;
	}
	/**
	 * @param interest
	 *            The interest to set.
	 */
	public void setInterest(double interest)
	{
		Interest = interest;
	}
	/**
	 * @return Returns the interestAccount.
	 */
	public long getInterestAccountID()
	{
		if (SETTConstant.AccountType.isCurrentAccountType(AccountTypeID)
		        ||SETTConstant.AccountType.isOtherDepositAccountType(AccountTypeID))
			InterestAccountID = getCurrentInterestAccountID();
		else
			if (SETTConstant.AccountType.isFixAccountType(AccountTypeID)
			        ||SETTConstant.AccountType.isNotifyAccountType(AccountTypeID))
				InterestAccountID = getFixInterestAccountID();
			else
				if (SETTConstant.AccountType.isConsignAccountType(AccountTypeID))
					InterestAccountID = getConsignLoanInterestAccountID();
				else
					if (SETTConstant.AccountType.isTrustAccountType(AccountTypeID))
						InterestAccountID = getIntrustLoanInterestAccountID();

		return InterestAccountID;
	}
	/**
	 * @param interestAccount
	 *            The interestAccount to set.
	 */
	public void setInterestAccountID(long interestAccount)
	{
		InterestAccountID = interestAccount;
	}
	/**
	 * @return Returns the interestPlanEffectDate.
	 */
	public Timestamp getInterestPlanEffectDate()
	{
		return InterestPlanEffectDate;
	}
	/**
	 * @param interestPlanEffectDate
	 *            The interestPlanEffectDate to set.
	 */
	public void setInterestPlanEffectDate(Timestamp interestPlanEffectDate)
	{
		InterestPlanEffectDate = interestPlanEffectDate;
	}
	/**
	 * @return Returns the interestPlanID.
	 */
	public long getInterestPlanID()
	{
		return InterestPlanID;
	}
	/**
	 * @param interestPlanID
	 *            The interestPlanID to set.
	 */
	public void setInterestPlanID(long interestPlanID)
	{
		InterestPlanID = interestPlanID;
	}
	/**
	 * @return Returns the interestRate.
	 */
	public double getCurrentInterestRate(Timestamp tsTerminateRateDate)
	{
        if(NameRef.getInterestRatePlanTypeIDByID(InterestPlanID)==SETTConstant.InterestRatePlanType.BALANCE_AVERAGE)
        {
        	double dAverageBalance = 0.0;
        	CurrentInterestRate = BaseQueryObject.getCurrentInterestRate(OpenDate, tsTerminateRateDate, InterestPlanID, Balance); 
        }
        else
        {
        	CurrentInterestRate = BaseQueryObject.getCurrentInterestRate(OpenDate, tsTerminateRateDate, InterestPlanID, Balance);
        }	
        return CurrentInterestRate;
	}
	
	/**
	 * @param interestRate
	 *            The interestRate to set.
	 */
	public void setCurrentInterestRate(double interestRate)
	{
		CurrentInterestRate = interestRate;
	}
	/**
	 * @param isNegotiate
	 *            The isNegotiate to set.
	 */
	public void setNegotiate(long lIsNegotiate)
	{
		this.IsNegotiate = lIsNegotiate;
	}
	/**
	 * @param isOverDraft
	 *            The isOverDraft to set.
	 */
	public void setOverDraft(long lng)
	{
		this.IsOverDraft = lng;
	}
	/**
	 * @param isWithInterest
	 *            The isWithInterest to set.
	 */
	public void setWithInterest(long lWithInterest)
	{
		this.IsWithInterest = lWithInterest;
	}
	/**
	 * @return Returns the maturityDate.
	 */
	public Timestamp getMaturityDate()
	{
		if (SETTConstant.AccountType.isCurrentAccountType(AccountTypeID)
		        ||SETTConstant.AccountType.isOtherDepositAccountType(AccountTypeID))
			return getCurrentMaturityDate();
		else
			if (SETTConstant.AccountType.isFixAccountType(AccountTypeID)
			        ||SETTConstant.AccountType.isNotifyAccountType(AccountTypeID))
				return getFixMaturityDate();
			else
				return getLoanMaturityDate();
	}
	/**
	 * @return Returns the openDate.
	 */
	public Timestamp getOpenDate()
	{
		return OpenDate;
	}
	/**
	 * @param openDate
	 *            The openDate to set.
	 */
	public void setOpenDate(Timestamp openDate)
	{
		OpenDate = openDate;
	}
	/**
	 * @return Returns the suretyFeeRate.
	 */
	public double getSuretyFeeRate()
	{
		return SuretyFeeRate;
	}
	/**
	 * @param suretyFeeRate
	 *            The suretyFeeRate to set.
	 */
	public void setSuretyFeeRate(double suretyFeeRate)
	{
		SuretyFeeRate = suretyFeeRate;
	}
	/**
	 * @return Returns the accountName.
	 */
	public String getAccountName()
	{
		return AccountName;
	}
	/**
	 * @param accountName
	 *            The accountName to set.
	 */
	public void setAccountName(String accountName)
	{
		AccountName = accountName;
	}
	/**
	 * @return Returns the clientName.
	 */
	public String getClientName()
	{
		return ClientName;
	}
	/**
	 * @param clientName
	 *            The clientName to set.
	 */
	public void setClientName(String clientName)
	{
		ClientName = clientName;
	}
	/**
	 * @return Returns the mainAccountStatusID.
	 */
	public long getMainAccountStatusID()
	{
		return MainAccountStatusID;
	}
	/**
	 * @param mainAccountStatusID The mainAccountStatusID to set.
	 */
	public void setMainAccountStatusID(long mainAccountStatusID)
	{
		MainAccountStatusID = mainAccountStatusID;
	}
	/**
	 * @return Returns the currentInterestAccount.
	 */
	public long getCurrentInterestAccountID()
	{
		return CurrentInterestAccountID;
	}
	/**
	 * @param currentInterestAccount The currentInterestAccount to set.
	 */
	public void setCurrentInterestAccountID(long currentInterestAccount)
	{
		CurrentInterestAccountID = currentInterestAccount;
	}
	/**
	 * @return Returns the currentMaturityDate.
	 */
	public Timestamp getCurrentMaturityDate()
	{
		return CurrentMaturityDate;
	}
	/**
	 * @param currentMaturityDate The currentMaturityDate to set.
	 */
	public void setCurrentMaturityDate(Timestamp currentMaturityDate)
	{
		CurrentMaturityDate = currentMaturityDate;
	}
	/**
	 * @return Returns the fixInterestAccount.
	 */
	public long getFixInterestAccountID()
	{
		return FixInterestAccountID;
	}
	/**
	 * @param fixInterestAccount The fixInterestAccount to set.
	 */
	public void setFixInterestAccountID(long fixInterestAccount)
	{
		FixInterestAccountID = fixInterestAccount;
	}
	/**
	 * @return Returns the fixInterestRate.
	 */
	public double getFixInterestRate()
	{
		return FixInterestRate;
	}
	/**
	 * @param fixInterestRate The fixInterestRate to set.
	 */
	public void setFixInterestRate(double fixInterestRate)
	{
		FixInterestRate = fixInterestRate;
	}
	/**
	 * @return Returns the fixMaturityDate.
	 */
	public Timestamp getFixMaturityDate()
	{
		return FixMaturityDate;
	}
	/**
	 * @param fixMaturityDate The fixMaturityDate to set.
	 */
	public void setFixMaturityDate(Timestamp fixMaturityDate)
	{
		FixMaturityDate = fixMaturityDate;
	}
	/**
	 * @return Returns the loanInterestAccount.
	 */
	public long getIntrustLoanInterestAccountID()
	{
		return IntrustLoanInterestAccountID;
	}
	/**
	 * @param loanInterestAccount The loanInterestAccount to set.
	 */
	public void setIntrustLoanInterestAccountID(long loanInterestAccount)
	{
		IntrustLoanInterestAccountID = loanInterestAccount;
	}
	/**
	 * @return Returns the loanInterestRate.
	 */
	public double getLoanInterestRate()
	{
		return LoanInterestRate;
	}
	/**
	 * @param loanInterestRate The loanInterestRate to set.
	 */
	public void setLoanInterestRate(double loanInterestRate)
	{
		LoanInterestRate = loanInterestRate;
	}
	/**
	 * @return Returns the loanMaturityDate.
	 */
	public Timestamp getLoanMaturityDate()
	{
		return LoanMaturityDate;
	}
	/**
	 * @param loanMaturityDate The loanMaturityDate to set.
	 */
	public void setLoanMaturityDate(Timestamp loanMaturityDate)
	{
		LoanMaturityDate = loanMaturityDate;
	}
	/**
	 * @return Returns the loanPayForm.
	 */
	public String getLoanPayForm()
	{
		return LoanPayForm;
	}
	/**
	 * @param loanPayForm The loanPayForm to set.
	 */
	public void setLoanPayForm(String loanPayForm)
	{
		LoanPayForm = loanPayForm;
	}
	/**
	 * @return Returns the accountStatusID.
	 */
	public long getAccountStatusID()
	{
        if (SETTConstant.AccountType.isCurrentAccountType(getAccountTypeID())
                ||SETTConstant.AccountType.isOtherDepositAccountType(getAccountTypeID()))
            return getMainAccountStatusID();
        else
            return getSubAccountStatusID();
	}
	/**
	 * @return Returns the formNo.
	 */
	public String getFormNo()
	{
		if (SETTConstant.AccountType.isCurrentAccountType(AccountTypeID))
			return "";
		else
			if (SETTConstant.AccountType.isFixAccountType(AccountTypeID)
			        ||SETTConstant.AccountType.isNotifyAccountType(AccountTypeID))
				return getDepositNo();
			else
				return getLoanPayForm();
	}
	/**
	 * @return Returns the interestRate.
	 */
	public double getInterestRate(Timestamp tsTerminateInterestRateDate)
	{
		if (SETTConstant.AccountType.isCurrentAccountType(AccountTypeID)
		        ||SETTConstant.AccountType.isOtherDepositAccountType(AccountTypeID))
			return getCurrentInterestRate(tsTerminateInterestRateDate);
		else
			if (SETTConstant.AccountType.isFixAccountType(AccountTypeID)
			        ||SETTConstant.AccountType.isNotifyAccountType(AccountTypeID))
				return getFixInterestRate();
			else
				return getLoanInterestRate();
	}
//	public double getMNegotiateRate(Timestamp tsTerminateInterestRateDate)
//	{
//		if (SETTConstant.AccountType.isCurrentAccountType(AccountTypeID)
//		        ||SETTConstant.AccountType.isOtherDepositAccountType(AccountTypeID))
//			return getCurrentInterestRate(tsTerminateInterestRateDate);
//		else
//			if (SETTConstant.AccountType.isFixAccountType(AccountTypeID)
//			        ||SETTConstant.AccountType.isNotifyAccountType(AccountTypeID))
//				return getFixInterestRate();
//			else
//				return getLoanInterestRate();
//	}
	/**
	 * @return Returns the commissionAccount.
	 */
	public long getCommissionAccountID()
	{
		return CommissionAccountID;
	}
	/**
	 * @param commissionAccount The commissionAccount to set.
	 */
	public void setCommissionAccountID(long commissionAccount)
	{
		CommissionAccountID = commissionAccount;
	}
	/**
	 * @return Returns the suretyFeeAccount.
	 */
	public long getSuretyFeeAccountID()
	{
		return SuretyFeeAccountID;
	}
	/**
	 * @param suretyFeeAccount The suretyFeeAccount to set.
	 */
	public void setSuretyFeeAccountID(long suretyFeeAccount)
	{
		SuretyFeeAccountID = suretyFeeAccount;
	}
	/**
	 * @return Returns the consignLoanInterestAccountID.
	 */
	public long getConsignLoanInterestAccountID()
	{
		return ConsignLoanInterestAccountID;
	}
	/**
	 * @param consignLoanInterestAccountID The consignLoanInterestAccountID to set.
	 */
	public void setConsignLoanInterestAccountID(long consignLoanInterestAccountID)
	{
		ConsignLoanInterestAccountID = consignLoanInterestAccountID;
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
	 * @return
	 */
	public double getInterestRate()
	{
		return InterestRate;
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
	public long getIsCommission()
	{
		return IsCommission;
	}

	/**
	 * @param isCommission
	 */
	public void setIsCommission(long isCommission)
	{
		IsCommission = isCommission;
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
	public long getIsOverDraft()
	{
		return IsOverDraft;
	}

	/**
	 * @param isOverDraft
	 */
	public void setIsOverDraft(long isOverDraft)
	{
		IsOverDraft = isOverDraft;
	}

	/**
	 * @return
	 */
	public long getIsSuretyFee()
	{
		return IsSuretyFee;
	}

	/**
	 * @param isSuretyFee
	 */
	public void setIsSuretyFee(long isSuretyFee)
	{
		IsSuretyFee = isSuretyFee;
	}

	/**
	 * @return
	 */
	public long getIsWithInterest()
	{
		return IsWithInterest;
	}

	/**
	 * @param isWithInterest
	 */
	public void setIsWithInterest(long isWithInterest)
	{
		IsWithInterest = isWithInterest;
	}

	/**
	 * @return
	 */
	public double getCurrentInterestRate()
	{
		return CurrentInterestRate;
	}

	/**
	 * @param accountStatusID
	 */
	public void setAccountStatusID(long accountStatusID)
	{
		AccountStatusID = accountStatusID;
	}

	/**
	 * @param formNo
	 */
	public void setFormNo(String formNo)
	{
		FormNo = formNo;
	}

	/**
	 * @param maturityDate
	 */
	public void setMaturityDate(Timestamp maturityDate)
	{
		MaturityDate = maturityDate;
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
	 * @return Returns the queryPassWord.
	 */
	public String getQueryPassWord()
	{
		return QueryPassWord;
	}
	/**
	 * @param queryPassWord The queryPassWord to set.
	 */
	public void setQueryPassWord(String queryPassWord)
	{
		QueryPassWord = queryPassWord;
	}
    /**
     * @return 返回 limitAmount。
     */ 
    public double getLimitAmount()
    {
        return LimitAmount;
    }
    /**
     * @param limitAmount 要设置的 limitAmount。
     */
    public void setLimitAmount(double limitAmount)
    {
        LimitAmount = limitAmount;
    }
    /**
     * @return 返回 subAccountStatus。
     */
    public long getSubAccountStatus() 
    {
        return SubAccountStatus;
    }
    /**
     * @param subAccountStatus 要设置的 subAccountStatus。
     */
    public void setSubAccountStatus(long subAccountStatus)
    {
        SubAccountStatus = subAccountStatus;
    }

	public void setSabstract(String sabstract){
		Sabstract = sabstract;
	}
	public String getSabstract(){
		return Sabstract;
	}
	public double getFirstLimitAmount() {
		return firstLimitAmount;
	}
	public void setFirstLimitAmount(double firstLimitAmount) {
		this.firstLimitAmount = firstLimitAmount;
	}
	public long getFirstLimitTypeId() {
		return firstLimitTypeId;
	}
	public void setFirstLimitTypeId(long firstLimitTypeId) {
		this.firstLimitTypeId = firstLimitTypeId;
	}
	public double getSecondLimitAmount() {
		return secondLimitAmount;
	}
	public void setSecondLimitAmount(double secondLimitAmount) {
		this.secondLimitAmount = secondLimitAmount;
	}
	public long getSecondLimitTypeId() {
		return secondLimitTypeId;
	}
	public void setSecondLimitTypeId(long secondLimitTypeId) {
		this.secondLimitTypeId = secondLimitTypeId;
	}
	public double getThirdLimitAmount() {
		return thirdLimitAmount;
	}
	public void setThirdLimitAmount(double thirdLimitAmount) {
		this.thirdLimitAmount = thirdLimitAmount;
	}
	public long getThirdLimitTypeId() {
		return thirdLimitTypeId;
	}
	public void setThirdLimitTypeId(long thirdLimitTypeId) {
		this.thirdLimitTypeId = thirdLimitTypeId;
	}
	public double getCapitalLimitAmount() {
		return capitalLimitAmount;
	}
	public void setCapitalLimitAmount(double capitalLimitAmount) {
		this.capitalLimitAmount = capitalLimitAmount;
	}
	public double getMNegotiateInterest() {
		return MNegotiateInterest;
	}
	public void setMNegotiateInterest(double negotiateInterest) {
		MNegotiateInterest = negotiateInterest;
	}
	public double getMNegotiateRate() {
		return MNegotiateRate;
	}
	public void setMNegotiateRate(double negotiateRate) {
		MNegotiateRate = negotiateRate;
	}
}