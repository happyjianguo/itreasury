/*
 * Created on 2003-10-14
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.settlement.setting.dataentity;

import java.io.Serializable;

/**
 * @author yiwang
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class BranchInfo implements Serializable
{
	/**
	 * 
	 */
	public BranchInfo()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	private long ID = -1; 
	private long OfficeID = 1; 
	private String BranchCode = "";
	private String BranchName = "";
	private String SubjectCode = "";
	private String BranchProvince = "";
	private String BranchCity = "";
	private String BankAccountCode = "";
	private String CreditBookedAccount ="";
	private String DebitBookedAccount = "";
	private long IsSingle = -1; 
	private long CurrencyID = -1;
	private String CashCreditBookedAccount = ""; 
	private String CashDebitBookedAccount = "";
	private String TransferCreditBookedAccount = "";
	private String TransferDebitBookedAccount = "";
	private String PrintName = "";
	//Add for bank withdraw and deposit by Rongyang	
	private long[] statusIDs = null; //is '状态'
	
	private long bankTypeID = -1; //银行类型ID                                    
	private long isAutoVirementByBank = -1; //是否可以通过银行付款                     
	private String bankServiceName = ""; //转账、查询时使用的银行服务名                          
	private String enterpriseName = ""; //账户的开户单位名称       
	
	private double Balance = 0.0;
	private double UsableBalance = 0.0;
	private boolean IsOperateFailed = false;
	private String ErrorMessage = "";
	private String CurrencyTypeName = "";
	
	//新增中行的联行号和机构号（qijiang添加）
	private String BankExchangeCode = "";      //联行号 
	private String BranchCodeOfBank = "";       //机构号	 
	private long AccountModule =-1;   //增加账户模式
	
	private long bankSubjectType =1;//开户行科目类型(mzh_fu 2007/04/19添加)
	
	public long getAccountModule() {
		return AccountModule;
	}

	public void setAccountModule(long accountModule) {
		AccountModule = accountModule;
	}

	/**
	 * @return
	 */
	public String getBankAccountCode()
	{
		return this.BankAccountCode;
	}

	/**
	 * @param bankAccountCode
	 */
	public void setBankAccountCode(String bankAccountCode)
	{
		if(bankAccountCode!=null)
		{
			bankAccountCode=bankAccountCode.trim();
		}
		this.BankAccountCode = bankAccountCode;
	}

	/**
	 * @return
	 */
	public String getBranchCity()
	{
		return this.BranchCity;
	}

	/**
	 * @param branchCity
	 */
	public void setBranchCity(String branchCity)
	{
		this.BranchCity = branchCity;
	}

	/**
	 * @return
	 */
	public String getBranchCode()
	{
		return this.BranchCode;
	}

	/**
	 * @param branchCode
	 */
	public void setBranchCode(String branchCode)
	{
		this.BranchCode = branchCode;
	}

	/**
	 * @return
	 */
	public String getBranchName()
	{
		return this.BranchName;
	}

	/**
	 * @param branchName
	 */
	public void setBranchName(String branchName)
	{
		this.BranchName = branchName;
	}

	/**
	 * @return
	 */
	public String getBranchProvince()
	{
		return this.BranchProvince;
	}

	/**
	 * @param branchProvince
	 */
	public void setBranchProvince(String branchProvince)
	{
		this.BranchProvince = branchProvince;
	}

	/**
	 * @return
	 */
	public String getCashCreditBookedAccount()
	{
		return this.CashCreditBookedAccount;
	}

	/**
	 * @param cashCreditBookedAccount
	 */
	public void setCashCreditBookedAccount(String cashCreditBookedAccount)
	{
		this.CashCreditBookedAccount = cashCreditBookedAccount;
	}

	/**
	 * @return
	 */
	public String getCashDebitBookedAccount()
	{
		return this.CashDebitBookedAccount;
	}

	/**
	 * @param cashDebitBookedAccount
	 */
	public void setCashDebitBookedAccount(String cashDebitBookedAccount)
	{
		this.CashDebitBookedAccount = cashDebitBookedAccount;
	}

	/**
	 * @return
	 */
	public String getCreditBookedAccount()
	{
		return this.CreditBookedAccount;
	}

	/**
	 * @param creditBookedAccount
	 */
	public void setCreditBookedAccount(String creditBookedAccount)
	{
		this.CreditBookedAccount = creditBookedAccount;
	}

	/**
	 * @return
	 */
	public String getDebitBookedAccount()
	{
		return this.DebitBookedAccount;
	}

	/**
	 * @param debitBookedAccount
	 */
	public void setDebitBookedAccount(String debitBookedAccount)
	{
		this.DebitBookedAccount = debitBookedAccount;
	}

	/**
	 * @return
	 */
	public long getID()
	{
		return this.ID;
	}

	/**
	 * @param id
	 */
	public void setID(long id)
	{
		this.ID = id;
	}

	/**
	 * @return
	 */
	public long getIsSingle()
	{
		return this.IsSingle;
	}

	/**
	 * @param isSingle
	 */
	public void setIsSingle(long isSingle)
	{
		this.IsSingle = isSingle;
	}

	/**
	 * @return
	 */
	public long getOfficeID()
	{
		return this.OfficeID;
	}

	/**
	 * @param officeID
	 */
	public void setOfficeID(long officeID)
	{
		this.OfficeID = officeID;
	}

	/**
	 * @return
	 */
	public long getCurrencyID()
	{
		return this.CurrencyID;
	}

	/**
	 * @param currencyID
	 */
	public void setCurrencyID(long currencyID)
	{
		this.CurrencyID = currencyID;
	}

	/**
	 * @return
	 */
	public String getPrintName()
	{
		return this.PrintName;
	}

	/**
	 * @param printName
	 */
	public void setPrintName(String printName)
	{
		this.PrintName = printName;
	}

	/**
	 * @return
	 */
	public String getSubjectCode()
	{
		return this.SubjectCode;
	}

	/**
	 * @param subjectCode
	 */
	public void setSubjectCode(String subjectCode)
	{
		this.SubjectCode = subjectCode;
	}

	/**
	 * @return
	 */
	public String getTransferCreditBookedAccount()
	{
		return this.TransferCreditBookedAccount;
	}

	/**
	 * @param transferCreditBookedAccount
	 */
	public void setTransferCreditBookedAccount(String transferCreditBookedAccount)
	{
		this.TransferCreditBookedAccount = transferCreditBookedAccount;
	}

	/**
	 * @return
	 */
	public String getTransferDebitBookedAccount()
	{
		return this.TransferDebitBookedAccount;
	}

	/**
	 * @param transferDebitBookedAccount
	 */
	public void setTransferDebitBookedAccount(String transferDebitBookedAccount)
	{
		this.TransferDebitBookedAccount = transferDebitBookedAccount;
	}

	/**
	 * Returns the bankTypeID.
	 * @return long
	 */
	public long getBankTypeID() {
		return bankTypeID;
	}

	/**
	 * Sets the bankTypeID.
	 * @param bankTypeID The bankTypeID to set
	 */
	public void setBankTypeID(long bankTypeID) {
		this.bankTypeID = bankTypeID;
	}

	/**
	 * Returns the bankServiceName.
	 * @return String
	 */
	public String getBankServiceName()
	{
		return bankServiceName;
	}

	/**
	 * Returns the enterpriseName.
	 * @return String
	 */
	public String getEnterpriseName()
	{
		return enterpriseName;
	}

	/**
	 * Returns the isAutoVirementByBank.
	 * @return long
	 */
	public long getIsAutoVirementByBank()
	{
		return isAutoVirementByBank;
	}

	/**
	 * Sets the bankServiceName.
	 * @param bankServiceName The bankServiceName to set
	 */
	public void setBankServiceName(String bankServiceName)
	{
		this.bankServiceName = bankServiceName;
	}

	/**
	 * Sets the enterpriseName.
	 * @param enterpriseName The enterpriseName to set
	 */
	public void setEnterpriseName(String enterpriseName)
	{
		this.enterpriseName = enterpriseName;
	}

	/**
	 * Sets the isAutoVirementByBank.
	 * @param isAutoVirementByBank The isAutoVirementByBank to set
	 */
	public void setIsAutoVirementByBank(long isAutoVirementByBank)
	{
		this.isAutoVirementByBank = isAutoVirementByBank;
	}

	/**
	 * Returns the statusID.
	 * @return long
	 */
	public long getStatusID()
	{
		long lResult = -1;
		if (this.statusIDs != null && this.statusIDs.length > 0)
		{
			lResult = this.statusIDs[0];
		}

		return lResult;
	}

	/**
	 * 用于当前Entity作为查询条件时
	 * @return long[]
	 */
	public long[] getStatusIDs()
	{
		return this.statusIDs;
	}
	
	/**
	 * Sets the statusID.
	 * @param statusID The statusID to set
	 */
	public void setStatusID(long statusID)
	{
		this.statusIDs = new long[] { statusID };
	}

	/**
	 * 用于当前Entity作为查询条件时
	 * @param statusIDs
	 */
	public void setStatusIDs(long[] statusIDs)
	{
		this.statusIDs = statusIDs;
	}
	/**
	 * @return
	 */
	public double getBalance() {
		return Balance;
	}

	/**
	 * @return
	 */
	public String getErrorMessage() {
		return ErrorMessage;
	}

	/**
	 * @return
	 */
	public boolean isOperateFailed() {
		return IsOperateFailed;
	}

	/**
	 * @return
	 */
	public double getUsableBalance() {
		return UsableBalance;
	}

	/**
	 * @param d
	 */
	public void setBalance(double d) {
		Balance = d;
	}

	/**
	 * @param string
	 */
	public void setErrorMessage(String string) {
		ErrorMessage = string;
	}

	/**
	 * @param b
	 */
	public void setOperateFailed(boolean b) {
		IsOperateFailed = b;
	}

	/**
	 * @param d
	 */
	public void setUsableBalance(double d) {
		UsableBalance = d;
	}

	/**
	 * @return
	 */
	public String getCurrencyTypeName() {
		return CurrencyTypeName;
	}

	/**
	 * @param string
	 */
	public void setCurrencyTypeName(String string) {
		CurrencyTypeName = string;
	}

	/**
	 * @return Returns the bankExchangeCode.
	 */
	public String getBankExchangeCode() {
		return BankExchangeCode;
	}

	/**
	 * @param bankExchangeCode The bankExchangeCode to set.
	 */
	public void setBankExchangeCode(String bankExchangeCode) {
		BankExchangeCode = bankExchangeCode;
	}

	/**
	 * @return Returns the branchCodeOfBank.
	 */
	public String getBranchCodeOfBank() {
		return BranchCodeOfBank;
	}

	/**
	 * @param branchCodeOfBank The branchCodeOfBank to set.
	 */
	public void setBranchCodeOfBank(String branchCodeOfBank) {
		BranchCodeOfBank = branchCodeOfBank;
	}

	public long getBankSubjectType() {
		return bankSubjectType;
	}

	public void setBankSubjectType(long bankSubjectType) {
		this.bankSubjectType = bankSubjectType;
	}

}
