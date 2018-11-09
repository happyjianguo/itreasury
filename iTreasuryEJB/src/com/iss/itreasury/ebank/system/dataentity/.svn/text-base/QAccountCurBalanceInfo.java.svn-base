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
 * To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Generation - Code and Comments
 */
public class QAccountCurBalanceInfo extends ITreasuryBaseDataEntity
{
	private String clientName = null;// 客户名称

	private String countryName = null;// 国家

	private String bankName = null;// 银行类型

	private String accountNo = null;// 账号

	private String accountName = null;// 账户名称

	private long currencyType = -1;// 币种

	private double n_balance = Double.NaN;// 余额

	private double usableBalance = Double.NaN;// 可用余额

	private Date importTime = null;// 导入时间

	private long dataSourceType = -1; // 划拨方式
	
	private long dataSourceType1 = -1; // 数据来源
    
    private long accountId = -1;
    
    private String currencyName = null;//币种名称
    
    private String accountPropertyOneName = null;//账户属性一
    
    private String accountPropertyTwoName = null;//账户属性二
    
	private long inputOrOutput = -1;   //收支属性 
	
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

	public long getId() {
		return 0;
	}

	public void setId(long id) {
	}

	/**
	 * @return Returns the accountName.
	 */
	public String getAccountName() {
		return accountName;
	}

	/**
	 * @param accountName
	 *            The accountName to set.
	 */
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	/**
	 * @return Returns the accountNo.
	 */
	public String getAccountNo() {
		return accountNo;
	}

	/**
	 * @param accountNo
	 *            The accountNo to set.
	 */
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	/**
	 * @return Returns the balance.
	 */
	public double getN_balance() {
		return n_balance;
	}

	/**
	 * @param balance
	 *            The balance to set.
	 */
	public void setN_balance(double n_balance) {
		this.n_balance = n_balance;
	}

	/**
	 * @return Returns the bankName.
	 */
	public String getBankName() {
		return bankName;
	}

	/**
	 * @param bankName
	 *            The bankName to set.
	 */
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	/**
	 * @return Returns the clientName.
	 */
	public String getClientName() {
		return clientName;
	}

	/**
	 * @param clientName
	 *            The clientName to set.
	 */
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	/**
	 * @return Returns the countryName.
	 */
	public String getCountryName() {
		return countryName;
	}

	/**
	 * @param countryName
	 *            The countryName to set.
	 */
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	/**
	 * @return Returns the currencyType.
	 */
	public long getCurrencyType() {
		return currencyType;
	}

	/**
	 * @param currencyType
	 *            The currencyType to set.
	 */
	public void setCurrencyType(long currencyType) {
		this.currencyType = currencyType;
	}

	/**
	 * @return Returns the importTime.
	 */
	public Date getImportTime() {
		return importTime;
	}

	/**
	 * @param importTime
	 *            The importTime to set.
	 */
	public void setImportTime(Date importTime) {
		this.importTime = importTime;
        putUsedField("dt_importTime", importTime);
	}

	/**
	 * @return Returns the usableBalance.
	 */
	public double getUsableBalance() {
		return usableBalance;
	}

	/**
	 * @param usableBalance
	 *            The usableBalance to set.
	 */
	public void setUsableBalance(double usableBalance) {
		this.usableBalance = usableBalance;
	}

    /**
     * @return Returns the accountId.
     */
    public long getAccountId() {
        return accountId;
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
     * @param accountId The accountId to set.
     */
    public void setAccountId(long accountId) {
        this.accountId = accountId;
        putUsedField("n_accountId", accountId);
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
