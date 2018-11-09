package com.iss.itreasury.settlement.account.dataentity;

import java.io.Serializable;

import com.iss.itreasury.settlement.setting.dataentity.SubAccountTypeCurrentSettingInfo;
import com.iss.itreasury.settlement.setting.dataentity.SubAccountTypeFixedDepositInfo;
import com.iss.itreasury.settlement.setting.dataentity.SubAccountTypeLoanSettingInfo;

/**
 * Title:        		iTreasury
 * Description:         
 * Copyright:           Copyright (c) 2003
 * Company:             iSoftStone
 * @author             yehuang 
 * @version
 *  Date of Creation    2003-10-16
 */
public class AccountTypeInfo implements Serializable {

	private long id = -1;
	private long accountGroupID = -1;
	private String accountType = "";
	private String accountTypeCode = "";	
	private String subjectCode = "";	
	private long isExistSubClass = -1;
	private long isLoanType = -1;
	private long isLoanMonth = -1;
	private long isLoanYear = -1;
	private long isCosign = -1;
	private long isDraftType = -1;
	private long isClientID = -1;
	private long isReDiscountType = -1;
	private long isAssure = -1;
	private String defaultDocCode = "";
	private long autoClearAccount = -1;
	private long statusID = -1;
	private long balanceDirection = -1;
	
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
	 * Returns the accountType.
	 * @return String
	 */
	public String getAccountType() {
		return accountType;
	}

	/**
	 * Returns the accountTypeCode.
	 * @return String
	 */
	public String getAccountTypeCode() {
		return accountTypeCode;
	}



	/**
	 * Returns the defaultDocCode.
	 * @return String
	 */
	public String getDefaultDocCode() {
		return defaultDocCode;
	}

	/**
	 * Returns the id.
	 * @return long
	 */
	public long getId() {
		return id;
	}

	/**
	 * Returns the isCosign.
	 * @return long
	 */
	public long getIsCosign() {
		return isCosign;
	}

	/**
	 * Returns the isDraftType.
	 * @return long
	 */
	public long getIsDraftType() {
		return isDraftType;
	}

	/**
	 * Returns the isExistSubClass.
	 * @return long
	 */
	public long getIsExistSubClass() {
		return isExistSubClass;
	}

	/**
	 * Returns the isLoanMonth.
	 * @return long
	 */
	public long getIsLoanMonth() {
		return isLoanMonth;
	}

	/**
	 * Returns the isLoanType.
	 * @return long
	 */
	public long getIsLoanType() {
		return isLoanType;
	}

	/**
	 * Returns the isLoanYear.
	 * @return long
	 */
	public long getIsLoanYear() {
		return isLoanYear;
	}

	/**
	 * Returns the statusID.
	 * @return long
	 */
	public long getStatusID() {
		return statusID;
	}

	/**
	 * Returns the subjectCode.
	 * @return String
	 */
	public String getSubjectCode() {
		return subjectCode;
	}

	/**
	 * Sets the accountType.
	 * @param accountType The accountType to set
	 */
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	/**
	 * Sets the accountTypeCode.
	 * @param accountTypeCode The accountTypeCode to set
	 */
	public void setAccountTypeCode(String accountTypeCode) {
		this.accountTypeCode = accountTypeCode;
	}


	/**
	 * Sets the defaultDocCode.
	 * @param defaultDocCode The defaultDocCode to set
	 */
	public void setDefaultDocCode(String defaultDocCode) {
		this.defaultDocCode = defaultDocCode;
	}

	/**
	 * Sets the id.
	 * @param id The id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Sets the isCosign.
	 * @param isCosign The isCosign to set
	 */
	public void setIsCosign(long isCosign) {
		this.isCosign = isCosign;
	}

	/**
	 * Sets the isDraftType.
	 * @param isDraftType The isDraftType to set
	 */
	public void setIsDraftType(long isDraftType) {
		this.isDraftType = isDraftType;
	}

	/**
	 * Sets the isExistSubClass.
	 * @param isExistSubClass The isExistSubClass to set
	 */
	public void setIsExistSubClass(long isExistSubClass) {
		this.isExistSubClass = isExistSubClass;
	}

	/**
	 * Sets the isLoanMonth.
	 * @param isLoanMonth The isLoanMonth to set
	 */
	public void setIsLoanMonth(long isLoanMonth) {
		this.isLoanMonth = isLoanMonth;
	}

	/**
	 * Sets the isLoanType.
	 * @param isLoanType The isLoanType to set
	 */
	public void setIsLoanType(long isLoanType) {
		this.isLoanType = isLoanType;
	}

	/**
	 * Sets the isLoanYear.
	 * @param isLoanYear The isLoanYear to set
	 */
	public void setIsLoanYear(long isLoanYear) {
		this.isLoanYear = isLoanYear;
	}

	/**
	 * Sets the statusID.
	 * @param statusID The statusID to set
	 */
	public void setStatusID(long statusID) {
		this.statusID = statusID;
	}

	/**
	 * Sets the subjectCode.
	 * @param subjectCode The subjectCode to set
	 */
	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}

	/**
	 * Returns the accountGroupID.
	 * @return long
	 */
	public long getAccountGroupID() {
		return accountGroupID;
	}

	/**
	 * Sets the accountGroupID.
	 * @param accountGroupID The accountGroupID to set
	 */
	public void setAccountGroupID(long accountGroupID) {
		this.accountGroupID = accountGroupID;
	}

	/**
	 * Returns the autoClearAccount.
	 * @return String
	 */
	public long getAutoClearAccount() {
		return autoClearAccount;
	}

	/**
	 * Sets the autoClearAccount.
	 * @param autoClearAccount The autoClearAccount to set
	 */
	public void setAutoClearAccount(long autoClearAccount) {
		this.autoClearAccount = autoClearAccount;
	}

	/**
	 * @return Returns the balanceDirection.
	 */
	public long getBalanceDirection() {
		return balanceDirection;
	}
	/**
	 * @param balanceDirection The balanceDirection to set.
	 */
	public void setBalanceDirection(long balanceDirection) {
		this.balanceDirection = balanceDirection;
	}
	/**
	 * Returns the isClientID.
	 * @return long
	 */
	public long getIsClientID()
	{
		return isClientID;
	}

	/**
	 * Returns the isReDiscountTypeID.
	 * @return long
	 */
	public long getIsReDiscountType()
	{
		return isReDiscountType;
	}

	/**
	 * Sets the isClientID.
	 * @param isClientID The isClientID to set
	 */
	public void setIsClientID(long isClientID)
	{
		this.isClientID = isClientID;
	}

	/**
	 * Sets the isReDiscountTypeID.
	 * @param isReDiscountTypeID The isReDiscountTypeID to set
	 */
	public void setIsReDiscountType(long isReDiscountType)
	{
		this.isReDiscountType = isReDiscountType;
	}

	/**
	 * Returns the isAssure.
	 * @return long
	 */
	public long getIsAssure()
	{
		return isAssure;
	}

	/**
	 * Sets the isAssure.
	 * @param isAssure The isAssure to set
	 */
	public void setIsAssure(long isAssure)
	{
		this.isAssure = isAssure;
	}

	public long getIsAccount() {
		return isAccount;
	}

	public void setIsAccount(long isAccount) {
		this.isAccount = isAccount;
	}

	public long getIsContract() {
		return isContract;
	}

	public void setIsContract(long isContract) {
		this.isContract = isContract;
	}

	public long getIsDeposit() {
		return isDeposit;
	}

	public void setIsDeposit(long isDeposit) {
		this.isDeposit = isDeposit;
	}

	public long getIsLoanNote() {
		return isLoanNote;
	}

	public void setIsLoanNote(long isLoanNote) {
		this.isLoanNote = isLoanNote;
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
