/*
 * Created on 2004-9-6
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.settlement.setting.dataentity;

import com.iss.itreasury.settlement.base.SettlementBaseDataEntity;

/**
 * @author ruixie
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class AccountTypeSettingInfo extends SettlementBaseDataEntity{
	
	long Id = -1;
	long AccountGroupId = -1;
	String AccountType = "";
	String AccountTypeCode = "";
	String SubjectCode = "";//科目号
	String InterestSubjectCode = "";//利息支出/收入科目号
	String NegotiateInterestSubjectCode = "";//计提利息科目号
	String BookedInterestSubjectCode="";// 协定利率利率
	long IsExistSubClass = -1;
	long IsLoanType = -1;
	long IsLoanMonth = -1;
	long IsLoanYear = -1;
	long IsConsign = -1;
	long IsDraftType = -1;
	String DefaultDocCode = "";
	long AutoClearAccount = -1;
	long StatusId = -1;
	long IsClient = -1;
	long BalanceDirection = -1;
	long IsTransDiscountType = -1;
	long IsAssure = -1;
	long CurrencyID = -1;
	long OfficeID = -1;
    long accountModule = -1;// 账户模式
    //Modify by leiyang  date 2007/06/19
    long interestCalculationMode = -1;
    long payModule = -1;// 付款方式       2007-7-19
    
    // added by mzh_fu 2008/04/29 国电需求活期增加账户下级分类；
    // 定期、通知增加账户和存单号下级分类；贷款类增加合同号和放款通知单下级分类
    long isAccount = -1;
    long isDeposit = -1;
    long isContract = -1;
    long isLoanNote = -1;    
    
    //added by mzh_fu 2008/04/29 国电需求，所做特殊修改（为了不修改取分录的接口）
    SubAccountTypeCurrentSettingInfo subAccountTypeCurrentSettingInfo = null;
    SubAccountTypeFixedDepositInfo subAccountTypeFixedDepositInfo = null;
    SubAccountTypeLoanSettingInfo subAccountTypeLoanSettingInfo = null;
    
	/**
	 * 
	 * @return
	 */
    public long getInterestCalculationMode() {
		return interestCalculationMode;
	}
    
    /**
     * 
     * @param interestCalculationMode
     */
	public void setInterestCalculationMode(long interestCalculationMode) {
		this.interestCalculationMode = interestCalculationMode;
		putUsedField("interestCalculationMode", interestCalculationMode);
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
	 * @return Returns the accountGroupId.
	 */
	public long getAccountGroupId() {
		return AccountGroupId;
	}
	/**
	 * @param accountGroupId The accountGroupId to set.
	 */
	public void setAccountGroupId(long accountGroupId) {
		AccountGroupId = accountGroupId;
		putUsedField("nAccountGroupId", accountGroupId);
	}
	/**
	 * @return Returns the accountType.
	 */
	public String getAccountType() {
		return AccountType;
	}
	/**
	 * @param accountType The accountType to set.
	 */
	public void setAccountType(String accountType) {
		AccountType = accountType;
		putUsedField("sAccountType", accountType);
	}
	/**
	 * @return Returns the accountTypeCode.
	 */
	public String getAccountTypeCode() {
		return AccountTypeCode;
	}
	/**
	 * @param accountTypeCode The accountTypeCode to set.
	 */
	public void setAccountTypeCode(String accountTypeCode) {
		AccountTypeCode = accountTypeCode;
		putUsedField("sAccountTypeCode", accountTypeCode);
	}
	/**
	 * @return Returns the autoClearAccount.
	 */
	public long getAutoClearAccount() {
		return AutoClearAccount;
	}
	/**
	 * @param autoClearAccount The autoClearAccount to set.
	 */
	public void setAutoClearAccount(long autoClearAccount) {
		AutoClearAccount = autoClearAccount;
		putUsedField("nAutoClearAccount", autoClearAccount);
	}
	/**
	 * @return Returns the balanceDirection.
	 */
	public long getBalanceDirection() {
		return BalanceDirection;
	}
	/**
	 * @param balanceDirection The balanceDirection to set.
	 */
	public void setBalanceDirection(long balanceDirection) {
		BalanceDirection = balanceDirection;
		putUsedField("nBalanceDirection", balanceDirection);
	}
	/**
	 * @return Returns the defaultDocCode.
	 */
	public String getDefaultDocCode() {
		return DefaultDocCode;
	}
	/**
	 * @param defaultDocCode The defaultDocCode to set.
	 */
	public void setDefaultDocCode(String defaultDocCode) {
		DefaultDocCode = defaultDocCode;
		putUsedField("sDefaultDocCode", defaultDocCode);
	}
	/**
	 * @return Returns the id.
	 */
	public long getId() {
		return Id;
	}
	/**
	 * @param id The id to set.
	 */
	public void setId(long id) {
		Id = id;
		putUsedField("Id", id);
	}
	/**
	 * @return Returns the isClient.
	 */
	public long getIsClient() {
		return IsClient;
	}
	/**
	 * @param isClient The isClient to set.
	 */
	public void setIsClient(long isClient) {
		IsClient = isClient;
		putUsedField("nIsClient", isClient);
	}
	/**
	 * @return Returns the isConsign.
	 */
	public long getIsConsign() {
		return IsConsign;
	}
	/**
	 * @param isConsign The isConsign to set.
	 */
	public void setIsConsign(long isConsign) {
		IsConsign = isConsign;
		putUsedField("nIsConsign", isConsign);
	}
	/**
	 * @return Returns the isDraftType.
	 */
	public long getIsDraftType() {
		return IsDraftType;
	}
	/**
	 * @param isDraftType The isDraftType to set.
	 */
	public void setIsDraftType(long isDraftType) {
		IsDraftType = isDraftType;
		putUsedField("nIsDraftType", isDraftType);
	}
	/**
	 * @return Returns the isLoanMonth.
	 */
	public long getIsLoanMonth() {
		return IsLoanMonth;
	}
	/**
	 * @param isLoanMonth The isLoanMonth to set.
	 */
	public void setIsLoanMonth(long isLoanMonth) {
		IsLoanMonth = isLoanMonth;
		putUsedField("nIsLoanMonth", isLoanMonth);
	}
	/**
	 * @return Returns the isLoanType.
	 */
	public long getIsLoanType() {
		return IsLoanType;
	}
	/**
	 * @param isLoanType The isLoanType to set.
	 */
	public void setIsLoanType(long isLoanType) {
		IsLoanType = isLoanType;
		putUsedField("nIsLoanType", isLoanType);
	}
	/**
	 * @return Returns the isLoanYear.
	 */
	public long getIsLoanYear() {
		return IsLoanYear;
	}
	/**
	 * @param isLoanYear The isLoanYear to set.
	 */
	public void setIsLoanYear(long isLoanYear) {
		IsLoanYear = isLoanYear;
		putUsedField("nIsLoanYear", isLoanYear);
	}
	/**
	 * @return Returns the statusId.
	 */
	public long getStatusId() {
		return StatusId;
	}
	/**
	 * @param statusId The statusId to set.
	 */
	public void setStatusId(long statusId) {
		StatusId = statusId;
		putUsedField("nStatusId", statusId);
	}
	/**
	 * @return Returns the isExistSubClass.
	 */
	public long getIsExistSubClass() {
		return IsExistSubClass;
	}
	/**
	 * @param isExistSubClass The isExistSubClass to set.
	 */
	public void setIsExistSubClass(long isExistSubClass) {
		IsExistSubClass = isExistSubClass;
		putUsedField("nIsExistSubClass", isExistSubClass);
	}
	/**
	 * @return Returns the subjectCode.
	 */
	public String getSubjectCode() {
		return SubjectCode;
	}
	/**
	 * @param subjectCode The subjectCode to set.
	 */
	public void setSubjectCode(String subjectCode) {
		SubjectCode = subjectCode;
		putUsedField("sSubjectCode", subjectCode);
	}
	
	/**
	 * @return the interestSubjectCode
	 */
	public String getInterestSubjectCode() {
		return InterestSubjectCode;
	}

	/**
	 * @param interestSubjectCode the interestSubjectCode to set
	 */
	public void setInterestSubjectCode(String interestSubjectCode) {
		this.InterestSubjectCode = interestSubjectCode;
		putUsedField("sInterestSubjectCode", interestSubjectCode);
	}

	/**
	 * @return the negotiateInterestSubjectCode
	 */
	public String getNegotiateInterestSubjectCode() {
		return NegotiateInterestSubjectCode;
	}

	/**
	 * @param negotiateInterestSubjectCode the negotiateInterestSubjectCode to set
	 */
	public void setNegotiateInterestSubjectCode(String negotiateInterestSubjectCode) {
		this.NegotiateInterestSubjectCode = negotiateInterestSubjectCode;
		putUsedField("sNegotiateInterestSubjectCode", negotiateInterestSubjectCode);
	}

	/**
	 * @return the negotiateInterestSubjectCode
	 */
	public String getBookedInterestSubjectCode() {
		return BookedInterestSubjectCode;
	}

	/**
	 * @param negotiateInterestSubjectCode the negotiateInterestSubjectCode to set
	 */
	public void setBookedInterestSubjectCode(String bookedInterestSubjectCode) {
		this.BookedInterestSubjectCode = bookedInterestSubjectCode;
		putUsedField("sBookedInterestSubjectCode", bookedInterestSubjectCode);
	}
	/**
	 * @return Returns the isTransDiscountType.
	 */
	public long getIsTransDiscountType() {
		return IsTransDiscountType;
	}
	/**
	 * @param isTransDiscountType The isTransDiscountType to set.
	 */
	public void setIsTransDiscountType(long isTransDiscountType) {
		IsTransDiscountType = isTransDiscountType;
		putUsedField("nIsTransDiscountType", isTransDiscountType);
	}
	/**
	 * Returns the isAssure.
	 * @return long
	 */
	public long getIsAssure()
	{
		return IsAssure;
	}

	/**
	 * Sets the isAssure.
	 * @param isAssure The isAssure to set
	 */
	public void setIsAssure(long isAssure)
	{
		IsAssure = isAssure;
		putUsedField("NISASSURE", isAssure);
	}
	public long getAccountModule() {
		return accountModule;
	}
	public void setAccountModule(long accountModule) {
		this.accountModule = accountModule;
		putUsedField("accountModule", accountModule);
	}

	//增加 收款方式
	public long getpayModule() {
		return payModule;
	}
	public void setpayModule(long payModule) {
		this.payModule = payModule;
		putUsedField("payModule", payModule);
	}
    //－－－－－－－－－－结束－－－－－－－－－－－－－－

	public long getIsAccount() {
		return isAccount;
	}

	public void setIsAccount(long isAccount) {
		this.isAccount = isAccount;
		putUsedField("nIsAccount", isAccount);
	}

	public long getIsContract() {
		return isContract;
	}

	public void setIsContract(long isContract) {
		this.isContract = isContract;
		putUsedField("NISCONTRACT", isContract);
	}

	public long getIsDeposit() {
		return isDeposit;
	}

	public void setIsDeposit(long isDeposit) {
		this.isDeposit = isDeposit;
		putUsedField("nIsDeposit", isDeposit);
	}

	public long getIsLoanNote() {
		return isLoanNote;
	}

	public void setIsLoanNote(long isLoanNote) {
		this.isLoanNote = isLoanNote;
		putUsedField("NISLOANNOTE", isLoanNote);
	}

	public long getPayModule() {
		return payModule;
	}

	public void setPayModule(long payModule) {
		this.payModule = payModule;
	}

	public SubAccountTypeCurrentSettingInfo getSubAccountTypeCurrentSettingInfo() {
		return subAccountTypeCurrentSettingInfo;
	}

	public void setSubAccountTypeCurrentSettingInfo(
			SubAccountTypeCurrentSettingInfo subAccountTypeCurrentSettingInfo) {
		this.subAccountTypeCurrentSettingInfo = subAccountTypeCurrentSettingInfo;
	}

	public SubAccountTypeFixedDepositInfo getSubAccountTypeFixedDepositInfo() {
		return subAccountTypeFixedDepositInfo;
	}

	public void setSubAccountTypeFixedDepositInfo(
			SubAccountTypeFixedDepositInfo subAccountTypeFixedDepositInfo) {
		this.subAccountTypeFixedDepositInfo = subAccountTypeFixedDepositInfo;
	}

	public SubAccountTypeLoanSettingInfo getSubAccountTypeLoanSettingInfo() {
		return subAccountTypeLoanSettingInfo;
	}

	public void setSubAccountTypeLoanSettingInfo(
			SubAccountTypeLoanSettingInfo subAccountTypeLoanSettingInfo) {
		this.subAccountTypeLoanSettingInfo = subAccountTypeLoanSettingInfo;
	}
}
