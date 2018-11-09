/*
 * Created on 2005-6-2
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.ebank.system.dataentity;

import java.util.Date;

import com.iss.itreasury.util.ITreasuryBaseDataEntity;

/**
 * @author jsxie
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class QAccountHisBalanceInfo extends ITreasuryBaseDataEntity
{
	private String clientName = null;//客户名称
	private String countryName = null;//国家
	private String bankName = null;//银行类型
	private long   bankId=-1;
	private String accountNo = null;//账号
	private String accountName = null;//账户名称		
	private Date balanceDate = null;//	余额日期	Date
	private long currencyType = -1;//	币种	Type
    private String currencyName = null;
	private double n_balance = Double.NaN;//	日终余额	Amount
	private double usableBalance = Double.NaN;//	日终可用余额	Amount
	private double amountOfCreditTrans = Double.NaN;//	贷方交易金额汇总	Amount
	private double amountOfDebitTrans = Double.NaN;//	借方交易金额汇总	Amount	
	private long isCheck = -1;//是否已核对
	private Date modifyTime = null;//修改时间
	private long accountId = -1;    //账户Id
	private Date date      = null;  //余额日期
	
	private Date importTime = null;// 导入时间

	private long dataSourceType = -1; // 归集方式
	
	private long dataSourceType1 = -1; // 数据来源
	
	private long notUpdateDays = -1;//为更新天数
    
    private String accountPropertyOneName = null;//账户属性一
    
    private String accountPropertyTwoName = null;//账户属性二
    
	private long inputOrOutput = -1; //收支属性
	
	public long getNotUpdateDays()
	{
		return notUpdateDays;
	}

	public void setNotUpdateDays(long notUpdateDays)
	{
		this.notUpdateDays = notUpdateDays;
	}

	public long getDataSourceType1() {
		
		return dataSourceType1;
	}

	public void setDataSourceType1(long dataSourceType1) {
		this.dataSourceType1 = dataSourceType1;
	}

	
	public long getDataSourceType() {
		return dataSourceType;
	}

	public void setDataSourceType(long dataSourceType) {
		this.dataSourceType = dataSourceType;
	}

	public Date getImportTime() {
		return importTime;
	}

	public void setImportTime(Date importTime) {
		this.importTime = importTime;
	}

	public long getId()
	{		
		return 0;
	}
	
	public void setId(long id)
	{		
	}	
		
	/**
	 * @return Returns the accountName.
	 */
	public String getAccountName()
	{
		return accountName;
	}
	/**
	 * @param accountName The accountName to set.
	 */
	public void setAccountName(String accountName)
	{
		this.accountName = accountName;
	}
	/**
	 * @return Returns the accountNo.
	 */
	public String getAccountNo()
	{
		return accountNo;
	}
	/**
	 * @return Returns the modifyTime.
	 */
	public Date getModifyTime() {
		return modifyTime;
	}

	/**
	 * @param modifyTime The modifyTime to set.
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	/**
	 * @param accountNo The accountNo to set.
	 */
	public void setAccountNo(String accountNo)
	{
		this.accountNo = accountNo;
	}
	/**
	 * @return Returns the amountOfCreditTrans.
	 */
	public double getAmountOfCreditTrans()
	{
		return amountOfCreditTrans;
	}
    /**
     * @return Returns the accountId.
     */
    public long getAccountId()
    {
        return accountId;
    }
    /**
     * @param accountId The accountId to set.
     */
    public void setAccountId(long accountId)
    {
        this.accountId = accountId;
        putUsedField("n_accountId", accountId);
    }
    /**
     * @return Returns the date.
     */
    public Date getDate()
    {
        return date;
    }
    /**
     * @param date The date to set.
     */
    public void setDate(Date date)
    {
        this.date = date;
        putUsedField("dt_date", this.date);
    }
	/**
	 * @param amountOfCreditTrans The amountOfCreditTrans to set.
	 */
	public void setAmountOfCreditTrans(double amountOfCreditTrans)
	{
		this.amountOfCreditTrans = amountOfCreditTrans;
	}
	/**
	 * @return Returns the amountOfDebitTrans.
	 */
	public double getAmountOfDebitTrans()
	{
		return amountOfDebitTrans;
	}
	/**
	 * @param amountOfDebitTrans The amountOfDebitTrans to set.
	 */
	public void setAmountOfDebitTrans(double amountOfDebitTrans)
	{
		this.amountOfDebitTrans = amountOfDebitTrans;
	}
	/**
	 * @return Returns the balance.
	 */
	public double getN_balance()
	{
		return n_balance;
	}
	/**
	 * @param balance The balance to set.
	 */
	public void setN_balance(double n_balance)
	{
		this.n_balance = n_balance;
	}
	/**
	 * @return Returns the bankName.
	 */
	public String getBankName()
	{
		return bankName;
	}
	/**
	 * @param bankName The bankName to set.
	 */
	public void setBankName(String bankName)
	{
		this.bankName = bankName;
	}
	/**
	 * @return Returns the clientName.
	 */
	public String getClientName()
	{
		return clientName;
	}
	/**
	 * @param clientName The clientName to set.
	 */
	public void setClientName(String clientName)
	{
		this.clientName = clientName;
	}
	/**
	 * @return Returns the countryName.
	 */
	public String getCountryName()
	{
		return countryName;
	}
	/**
	 * @param countryName The countryName to set.
	 */
	public void setCountryName(String countryName)
	{
		this.countryName = countryName;
	}
	/**
	 * @return Returns the currencyType.
	 */
	public long getCurrencyType()
	{
		return currencyType;
	}
	/**
	 * @param currencyType The currencyType to set.
	 */
	public void setCurrencyType(long currencyType)
	{
		this.currencyType = currencyType;
	}	
	/**
	 * @return Returns the usableBalance.
	 */
	public double getUsableBalance()
	{
		return usableBalance;
	}
	/**
	 * @param usableBalance The usableBalance to set.
	 */
	public void setUsableBalance(double usableBalance)
	{
		this.usableBalance = usableBalance;
	}
	/**
	 * @return Returns the balanceDate.
	 */
	public Date getBalanceDate()
	{
		return balanceDate;
	}
	/**
	 * @param balanceDate The balanceDate to set.
	 */
	public void setBalanceDate(Date balanceDate)
	{
		this.balanceDate = balanceDate;
	}

	/**
	 * @return Returns the isCheck.
	 */
	public long getIsCheck() {
		return isCheck;
	}

	/**
	 * @param isCheck The isCheck to set.
	 */
	public void setIsCheck(long isCheck) {
		this.isCheck = isCheck;
	}

	public long getBankId() {
		return bankId;
	}

	public void setBankId(long bankId) {
		this.bankId = bankId;
	}

    /**
     * @return Returns the accountPropertyOneName.
     */
    public String getAccountPropertyOneName() {
        return accountPropertyOneName;
    }

    /**
     * @param accountPropertyOneName The accountPropertyOneName to set.
     */
    public void setAccountPropertyOneName(String accountPropertyOneName) {
        this.accountPropertyOneName = accountPropertyOneName;
    }

    /**
     * @return Returns the accountPropertyTwoName.
     */
    public String getAccountPropertyTwoName() {
        return accountPropertyTwoName;
    }

    /**
     * @param accountPropertyTwoName The accountPropertyTwoName to set.
     */
    public void setAccountPropertyTwoName(String accountPropertyTwoName) {
        this.accountPropertyTwoName = accountPropertyTwoName;
    }

    /**
     * @return Returns the currencyName.
     */
    public String getCurrencyName() {
        return currencyName;
    }

    /**
     * @param currencyName The currencyName to set.
     */
    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    /**
     * @return Returns the inputOrOutput.
     */
    public long getInputOrOutput() {
        return inputOrOutput;
    }

    /**
     * @param inputOrOutput The inputOrOutput to set.
     */
    public void setInputOrOutput(long inputOrOutput) {
        this.inputOrOutput = inputOrOutput;
    }
	
}
