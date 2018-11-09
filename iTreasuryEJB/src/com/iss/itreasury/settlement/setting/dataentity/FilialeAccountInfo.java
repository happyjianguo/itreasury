package com.iss.itreasury.settlement.setting.dataentity;

import java.io.Serializable;

/**
 * @author rongyang
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class FilialeAccountInfo implements Serializable
{

	/**
	 * Constructor for FilialeAccountInfo.
	 */
	public FilialeAccountInfo()
	{
		super();
	}
	
	private long ID = -1; //ID
	private String FilialeName = "";  //电厂名称
	private String BankAccountNo = "";  //账户号
	private String BankAccountName = ""; //账户名称

	private long AccountType = -1; //账户类型
	private String BranchName = "";  //开户行名称
	private long BankType = -1; //银行类型
	private long ClientID = -1; //客户编号（所属单位）
	private long CurrencyID = -1; //币种
	private long WithinAccountID = -1 ;//对应的内部账户的id
	private String WithinAccountNo = "";//内部账户编号
	private long UpBankAccountID= -1;//隶属银行账户的id
	private String NameOfProvince = ""; //开户行所在省的名称
	private String NameOfCity = ""; //开户行所在的城市名称
	private long  OfficeID = -1;//办事处id
	
	private double Balance = 0.0;
	private double UsableBalance = 0.0;
	private boolean IsOperateFailed = false;
	private String ErrorMessage = "";
	private String CurrencyTypeName = "";
	private long UpClientID = -1;//所属上级单位的客户编号，华能有用
	
	//新增中行联行号和机构号（qijiang添加）
	private String BankExchangeCode = "";      //联行号
	private String BranchCodeOfBank = "";       //机构号
	
	//用于判断账户是否可进行上收(qijiang)
	private boolean IsVaildAutoTurnIn = true; 
	
	//南航新增是否需要财司记账，qijiang添加
	private long IsKeepAccounts = -1;     //是否需要财司记账
	
	
	/**
	 * @return Returns the upClientID.
	 */
	public long getUpClientID()
	{
		return UpClientID;
	}
	/**
	 * @param upClientID The upClientID to set.
	 */
	public void setUpClientID(long upClientID)
	{
		UpClientID=upClientID;
	}
	/**
	 * @return
	 */
	public long getAccountType() {
		return AccountType;
	}

	/**
	 * @return
	 */
	public String getBankAccountName() {
		return BankAccountName;
	}

	/**
	 * @return
	 */
	public String getBankAccountNo() {
		return BankAccountNo;
	}

	/**
	 * @return
	 */
	public long getBankType() {
		return BankType;
	}

	/**
	 * @return
	 */
	public String getBranchName() {
		return BranchName;
	}

	/**
	 * @return
	 */
	public long getClientID() {
		return ClientID;
	}

	/**
	 * @return
	 */
	public String getFilialeName() {
		return FilialeName;
	}

	/**
	 * @return
	 */
	public long getID() {
		return ID;
	}

	/**
	 * @param l
	 */
	public void setAccountType(long l) {
		AccountType = l;
	}

	/**
	 * @param string
	 */
	public void setBankAccountName(String string) {
		BankAccountName = string;
	}

	/**
	 * @param string
	 */
	public void setBankAccountNo(String string) {
		BankAccountNo = string;
	}

	/**
	 * @param l
	 */
	public void setBankType(long l) {
		BankType = l;
	}

	/**
	 * @param string
	 */
	public void setBranchName(String string) {
		BranchName = string;
	}

	/**
	 * @param l
	 */
	public void setClientID(long l) {
		ClientID = l;
	}

	/**
	 * @param string
	 */
	public void setFilialeName(String string) {
		FilialeName = string;
	}

	/**
	 * @param l
	 */
	public void setID(long l) {
		ID = l;
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
	 * @return
	 */
	public long getCurrencyID() {
		return CurrencyID;
	}

	/**
	 * @param l
	 */
	public void setCurrencyID(long l) {
		CurrencyID = l;
	}

	/**
	 * @return Returns the nameOfCity.
	 */
	public String getNameOfCity()
	{
		return NameOfCity;
	}
	/**
	 * @param nameOfCity The nameOfCity to set.
	 */
	public void setNameOfCity(String nameOfCity)
	{
		NameOfCity=nameOfCity;
	}
	/**
	 * @return Returns the nameOfProvince.
	 */
	public String getNameOfProvince()
	{
		return NameOfProvince;
	}
	/**
	 * @param nameOfProvince The nameOfProvince to set.
	 */
	public void setNameOfProvince(String nameOfProvince)
	{
		NameOfProvince=nameOfProvince;
	}
	/**
	 * @return Returns the officeID.
	 */
	public long getOfficeID()
	{
		return OfficeID;
	}
	/**
	 * @param officeID The officeID to set.
	 */
	public void setOfficeID(long officeID)
	{
		OfficeID=officeID;
	}
	/**
	 * @return Returns the upBankAccountID.
	 */
	public long getUpBankAccountID()
	{
		return UpBankAccountID;
	}
	/**
	 * @param upBankAccountID The upBankAccountID to set.
	 */
	public void setUpBankAccountID(long upBankAccountID)
	{
		UpBankAccountID=upBankAccountID;
	}
	/**
	 * @return Returns the withinAccountID.
	 */
	public long getWithinAccountID()
	{
		return WithinAccountID;
	}
	/**
	 * @param withinAccountID The withinAccountID to set.
	 */
	public void setWithinAccountID(long withinAccountID)
	{
		WithinAccountID=withinAccountID;
	}
	/**
	 * @return Returns the withinAccountNo.
	 */
	public String getWithinAccountNo()
	{
		return WithinAccountNo;
	}
	/**
	 * @param withinAccountNo The withinAccountNo to set.
	 */
	public void setWithinAccountNo(String withinAccountNo)
	{
		WithinAccountNo=withinAccountNo;
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
	/**
	 * @return Returns the isKeepAccounts.
	 */
	public long getIsKeepAccounts() {
		return IsKeepAccounts;
	}
	/**
	 * @param isKeepAccounts The isKeepAccounts to set.
	 */
	public void setIsKeepAccounts(long isKeepAccounts) {
		IsKeepAccounts = isKeepAccounts;
	}
	/**
	 * @return Returns the isVaildAutoTurnIn.
	 */
	public boolean getIsVaildAutoTurnIn() {
		return IsVaildAutoTurnIn;
	}
	/**
	 * @param isVaildAutoTurnIn The isVaildAutoTurnIn to set.
	 */
	public void setIsVaildAutoTurnIn(boolean isVaildAutoTurnIn) {
		IsVaildAutoTurnIn = isVaildAutoTurnIn;
	}
}
