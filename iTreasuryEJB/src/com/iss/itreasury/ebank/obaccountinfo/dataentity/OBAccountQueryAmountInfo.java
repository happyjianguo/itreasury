package com.iss.itreasury.ebank.obaccountinfo.dataentity;

/**
 * Title:        		iTreasury
 * Description:                   
 * Copyright:           Copyright (c) 2004
 * Company:             iSoftStone
 * @author             kewen hu 
 * @version
 * Date of Creation     2004-01-12
 */
public class OBAccountQueryAmountInfo {
	private long LoanClientTypeID = 0;	// 自营贷款客户分类ID
	private String LoanClientTypeName = "";// 自营贷款客户分类名称
	private long ClientID = 0;			// 客户ID
	private String ClientCode = "";		// 客户编号
	private String ClientName = "";		// 客户名称
	private long AccountGroupID = 0;		// 账户组ID
	private String AccountGroupName = "";	// 账户组名称
	private long AccountTypeID = 0;		// 账户类型ID
	private String OpenAccountName = "";	// 开户名称
	private double AccountBalance = 0.0;	// 资金余额
	private String OpenAccountAddress = "";// 开户地
	private String OpenAccountNo = "";		// 账号
	private long OpenAccountID = 0;		// 账户ID
	private long Levelid = 0;     //级别ID
	private String LevelCode = "";    //级别码

	public String getLevelCode()
	{
		return LevelCode;
	}
	public void setLevelCode(String levelCode)
	{
		LevelCode = levelCode;
	}
	public long getLevelid()
	{
		return Levelid;
	}
	public void setLevelid(long levelid)
	{
		Levelid = levelid;
	}
	/**
	 * 构造函数
	 * @param  nothing
	 * @return nothing
	 * @exception nothing
	 */
	public OBAccountQueryAmountInfo() {
	}
	/**
	 * Returns the clientID.
	 * @return long
	 */
	public long getClientID() {
		return ClientID;
	}

	/**
	 * Sets the clientID.
	 * @param clientID The clientID to set
	 */
	public void setClientID(long clientID) {
		ClientID = clientID;
	}

	/**
	 * Returns the clientCode.
	 * @return String
	 */
	public String getClientCode() {
		return ClientCode;
	}

	/**
	 * Returns the clientName.
	 * @return String
	 */
	public String getClientName() {
		return ClientName;
	}

	/**
	 * Sets the clientCode.
	 * @param clientCode The clientCode to set
	 */
	public void setClientCode(String clientCode) {
		ClientCode = clientCode;
	}

	/**
	 * Sets the clientName.
	 * @param clientName The clientName to set
	 */
	public void setClientName(String clientName) {
		ClientName = clientName;
	}

	/**
	 * Returns the accountGroupID.
	 * @return long
	 */
	public long getAccountGroupID() {
		return AccountGroupID;
	}

	/**
	 * Returns the accountGroupName.
	 * @return String
	 */
	public String getAccountGroupName() {
		return AccountGroupName;
	}

	/**
	 * Sets the accountGroupID.
	 * @param accountGroupID The accountGroupID to set
	 */
	public void setAccountGroupID(long accountGroupID) {
		AccountGroupID = accountGroupID;
	}

	/**
	 * Sets the accountGroupName.
	 * @param accountGroupName The accountGroupName to set
	 */
	public void setAccountGroupName(String accountGroupName) {
		AccountGroupName = accountGroupName;
	}

	/**
	 * Returns the accountBalance.
	 * @return double
	 */
	public double getAccountBalance() {
		return AccountBalance;
	}

	/**
	 * Returns the accountTypeID.
	 * @return long
	 */
	public long getAccountTypeID() {
		return AccountTypeID;
	}

	/**
	 * Returns the openAccountAddress.
	 * @return String
	 */
	public String getOpenAccountAddress() {
		return OpenAccountAddress;
	}

	/**
	 * Returns the openAccountName.
	 * @return String
	 */
	public String getOpenAccountName() {
		return OpenAccountName;
	}

	/**
	 * Returns the openAccountNo.
	 * @return String
	 */
	public String getOpenAccountNo() {
		return OpenAccountNo;
	}

	/**
	 * Sets the accountBalance.
	 * @param accountBalance The accountBalance to set
	 */
	public void setAccountBalance(double accountBalance) {
		AccountBalance = accountBalance;
	}

	/**
	 * Sets the accountTypeID.
	 * @param accountTypeID The accountTypeID to set
	 */
	public void setAccountTypeID(long accountTypeID) {
		AccountTypeID = accountTypeID;
	}

	/**
	 * Sets the openAccountAddress.
	 * @param openAccountAddress The openAccountAddress to set
	 */
	public void setOpenAccountAddress(String openAccountAddress) {
		OpenAccountAddress = openAccountAddress;
	}

	/**
	 * Sets the openAccountName.
	 * @param openAccountName The openAccountName to set
	 */
	public void setOpenAccountName(String openAccountName) {
		OpenAccountName = openAccountName;
	}

	/**
	 * Sets the openAccountNo.
	 * @param openAccountNo The openAccountNo to set
	 */
	public void setOpenAccountNo(String openAccountNo) {
		OpenAccountNo = openAccountNo;
	}

	/**
	 * Returns the loanClientTypeID.
	 * @return long
	 */
	public long getLoanClientTypeID() {
		return LoanClientTypeID;
	}

	/**
	 * Sets the loanClientTypeID.
	 * @param loanClientTypeID The loanClientTypeID to set
	 */
	public void setLoanClientTypeID(long loanClientTypeID) {
		LoanClientTypeID = loanClientTypeID;
	}

	/**
	 * Returns the openAccountID.
	 * @return long
	 */
	public long getOpenAccountID() {
		return OpenAccountID;
	}

	/**
	 * Sets the openAccountID.
	 * @param openAccountID The openAccountID to set
	 */
	public void setOpenAccountID(long openAccountID) {
		OpenAccountID = openAccountID;
	}

	/**
	 * Returns the loanClientTypeName.
	 * @return String
	 */
	public String getLoanClientTypeName() {
		return LoanClientTypeName;
	}

	/**
	 * Sets the loanClientTypeName.
	 * @param loanClientTypeName The loanClientTypeName to set
	 */
	public void setLoanClientTypeName(String loanClientTypeName) {
		LoanClientTypeName = loanClientTypeName;
	}

}
