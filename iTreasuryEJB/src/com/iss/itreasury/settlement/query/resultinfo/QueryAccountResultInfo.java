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
	private String QueryPassWord = "";//��ѯ����
	private String ClientName = "";
	private long SubAccountID = -1;
    private Timestamp BalanceDate = null; // ����ѯ����
    private long isToday = 0; // ��ʾ��ѯ�����Ƿ��ǵ���

	// �˻�״̬
	private long MainAccountStatusID = -1; // ����ʹ��
	private long SubAccountStatusID = -1; // ���ںʹ���ʹ��
	private long AccountStatusID = -1; //  ����ʾʹ��
	//
    private double UncheckPaymentAmount = 0.0; // �ۼ�δ���˽��
    //
	private Timestamp OpenDate = null; // ������
	private Timestamp ClearDate = null; // �廧��
	private double Balance = 0.0;
	//
	private long IsWithInterest = 0; // �Ƿ��Ϣ
	private long InterestPlanID = -1; // ���ʼƻ�
	private Timestamp InterestPlanEffectDate = null; // ���ʼƻ���Ч��
	//����
	private double CurrentInterestRate = 0.0; // ��������
	private double FixInterestRate = 0.0; // ��������
	private double LoanInterestRate = 0.0; // ��������
	private double InterestRate = 0.0; // ����ʾʹ��

	private double Interest = 0.0; // �ۼ���Ϣ
	// ��Ϣ�˻�
	private long CurrentInterestAccountID = -1; // ���ڽ�Ϣ�˻�
	private long FixInterestAccountID = -1; // ���ڽ�Ϣ�˻�
	private long IntrustLoanInterestAccountID = -1; // ���д�����Ϣ�˻�
	private long ConsignLoanInterestAccountID = -1; // ί�д����Ϣ�˻�
	private long InterestAccountID = -1; // ����ʾʹ��
	//������
	private Timestamp CurrentMaturityDate = null; // ���ڵ�����
	private Timestamp FixMaturityDate = null; // ���ڵ�����
	private Timestamp LoanMaturityDate = null; // �������
	private Timestamp MaturityDate = null; // ����ʾʹ��

	// ���ݺ�
	private String DepositNo = ""; // ���ڵ��ݺ�
	private String LoanPayForm = ""; // �ſ�֪ͨ��
	private String FormNo = ""; // ����ʾʹ��

	private long IsOverDraft = 0; // �Ƿ�����͸֧

	private long IsCommission = 0; // �Ƿ���������
	private long IsSuretyFee = 0; // �Ƿ��յ�����
	private long CommissionAccountID = -1; // �������˻�
	private long SuretyFeeAccountID = -1; // �������˻�
	//
	private double CommissionRate = 0.0; // ��������
	private double SuretyFeeRate = 0.0; // ��������
	private long IsNegotiate = 0; // �Ƿ�Э�����
	// added by chengjd 2008/10/13 Э��������ʵ�������
	private double MNegotiateRate=0.0; //Э���������
	private double MNegotiateInterest=0.0; //��ǰЭ����Ϣ
	
	private double AvailableBalance = 0.0; // �������
	private double LimitAmount = 0.0; 	//�ʽ��޶�
	private long SubAccountStatus = -1;	//���˻�״̬
	private String Sabstract = "";//��ע
	
	// added by mzh_fu 2008/05/08 ����˻�����ѯ��ͳһ����,��������7��
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
     * @return ���� limitAmount��
     */ 
    public double getLimitAmount()
    {
        return LimitAmount;
    }
    /**
     * @param limitAmount Ҫ���õ� limitAmount��
     */
    public void setLimitAmount(double limitAmount)
    {
        LimitAmount = limitAmount;
    }
    /**
     * @return ���� subAccountStatus��
     */
    public long getSubAccountStatus() 
    {
        return SubAccountStatus;
    }
    /**
     * @param subAccountStatus Ҫ���õ� subAccountStatus��
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