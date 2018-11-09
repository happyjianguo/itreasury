package com.iss.itreasury.ebank.system.dataentity;

import java.util.Date;

/**
 * @author gqfang
 * 
 * TODO To change the template for this generated type comment go to Window - Preferences - Java - Code Style - Code Templates
 */
public class QueryBillPrintInfo
{
    private long   id               = -1;        //Ψһ��ʶ

    private long   accountId        = -1;        //�����˻�Id

    private long   clientId         = -1;        //�ͻ�Id

    private long   countryId        = -1;        //����Id

    private String accountNo        = "";        //�˺�

    private String accountName      = "";        //�˻�����

    private long   currencyType     = -1;        //����

    private String branchName       = "";        //��������

    private String oppAccountNo     = "";        //�Է��˺�

    private String oppAccountName   = "";        //�Է��˻�����

    private String oppBranchName    = "";        //�Է�������

    private long   oppCountryId     = -1;        //�Է����������ڹ�

    private String oppAddress       = "";        //�Է����������ڵ�

    private double amount           = 0.0; //���׽��

    private long   direction        = -1;        //�������

    private Date   transactionTime  = null;      //��������

    private String abstractInfo     = "";        //ժҪ

    private String useInfo          = "";        //��;��Ϣ

    private String remarkInfo       = "";        //��ע��Ϣ

    private long   bankId           = -1;        //��������
    
    private String   bankType           = "";        //��������

    private double balance          = 0.0; //�˻��������

    private Date   balanceStartDate = null;      //��ѯ�ڼ��ڣ����˻�������ʼ����

    private Date   balanceEndDate   = null;      //��ѯ�ڼ��ڣ����˻����Ľ�ֹ����
    
    private String   queryStartDateString   = null;      //��ѯ��ʼ����
    
    private String   queryEndDateString     = null;      //��ѯ��������

    /**
     * @return Returns the abstractInfo.
     */
    public String getAbstractInfo()
    {
        return abstractInfo;
    }

    /**
     * @param abstractInfo
     *            The abstractInfo to set.
     */
    public void setAbstractInfo(String abstractInfo)
    {
        this.abstractInfo = abstractInfo;
    }

    /**
     * @return Returns the accountId.
     */
    public long getAccountId()
    {
        return accountId;
    }

    /**
     * @param accountId
     *            The accountId to set.
     */
    public void setAccountId(long accountId)
    {
        this.accountId = accountId;
    }

    /**
     * @return Returns the accountName.
     */
    public String getAccountName()
    {
        return accountName;
    }

    /**
     * @param accountName
     *            The accountName to set.
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
     * @param accountNo
     *            The accountNo to set.
     */
    public void setAccountNo(String accountNo)
    {
        this.accountNo = accountNo;
    }

    /**
     * @return Returns the amount.
     */
    public double getAmount()
    {
        return amount;
    }

    /**
     * @param amount
     *            The amount to set.
     */
    public void setAmount(double amount)
    {
        this.amount = amount;
    }

    /**
     * @return Returns the balance.
     */
    public double getBalance()
    {
        return balance;
    }

    /**
     * @param balance
     *            The balance to set.
     */
    public void setBalance(double balance)
    {
        this.balance = balance;
    }

    /**
     * @return Returns the balanceEndDate.
     */
    public Date getBalanceEndDate()
    {
        return balanceEndDate;
    }

    /**
     * @param balanceEndDate
     *            The balanceEndDate to set.
     */
    public void setBalanceEndDate(Date balanceEndDate)
    {
        this.balanceEndDate = balanceEndDate;
    }

    /**
     * @return Returns the balanceStartDate.
     */
    public Date getBalanceStartDate()
    {
        return balanceStartDate;
    }

    /**
     * @param balanceStartDate
     *            The balanceStartDate to set.
     */
    public void setBalanceStartDate(Date balanceStartDate)
    {
        this.balanceStartDate = balanceStartDate;
    }

    public long getBankId() {
		return bankId;
	}

	public void setBankId(long bankId) {
		this.bankId = bankId;
	}

	/**
     * @return Returns the bankType.
     */
    public String getBankType()
    {
        return bankType;
    }

    /**
     * @param bankType
     *            The bankType to set.
     */
    public void setBankType(String bankType)
    {
        this.bankType = bankType;
    }

    /**
     * @return Returns the branchName.
     */
    public String getBranchName()
    {
        return branchName;
    }

    /**
     * @param branchName
     *            The branchName to set.
     */
    public void setBranchName(String branchName)
    {
        this.branchName = branchName;
    }

    /**
     * @return Returns the clientId.
     */
    public long getClientId()
    {
        return clientId;
    }

    /**
     * @param clientId
     *            The clientId to set.
     */
    public void setClientId(long clientId)
    {
        this.clientId = clientId;
    }

    /**
     * @return Returns the countryId.
     */
    public long getCountryId()
    {
        return countryId;
    }

    /**
     * @param countryId
     *            The countryId to set.
     */
    public void setCountryId(long countryId)
    {
        this.countryId = countryId;
    }

    /**
     * @return Returns the currencyType.
     */
    public long getCurrencyType()
    {
        return currencyType;
    }

    /**
     * @param currencyType
     *            The currencyType to set.
     */
    public void setCurrencyType(long currencyType)
    {
        this.currencyType = currencyType;
    }

    /**
     * @return Returns the direction.
     */
    public long getDirection()
    {
        return direction;
    }

    /**
     * @param direction
     *            The direction to set.
     */
    public void setDirection(long direction)
    {
        this.direction = direction;
    }

    /**
     * @return Returns the id.
     */
    public long getId()
    {
        return id;
    }

    /**
     * @param id
     *            The id to set.
     */
    public void setId(long id)
    {
        this.id = id;
    }

    /**
     * @return Returns the oppAccountName.
     */
    public String getOppAccountName()
    {
        return oppAccountName;
    }

    /**
     * @param oppAccountName
     *            The oppAccountName to set.
     */
    public void setOppAccountName(String oppAccountName)
    {
        this.oppAccountName = oppAccountName;
    }

    /**
     * @return Returns the oppAccountNo.
     */
    public String getOppAccountNo()
    {
        return oppAccountNo;
    }

    /**
     * @param oppAccountNo
     *            The oppAccountNo to set.
     */
    public void setOppAccountNo(String oppAccountNo)
    {
        this.oppAccountNo = oppAccountNo;
    }

    /**
     * @return Returns the oppAddress.
     */
    public String getOppAddress()
    {
        return oppAddress;
    }

    /**
     * @param oppAddress
     *            The oppAddress to set.
     */
    public void setOppAddress(String oppAddress)
    {
        this.oppAddress = oppAddress;
    }

    /**
     * @return Returns the oppBranchName.
     */
    public String getOppBranchName()
    {
        return oppBranchName;
    }

    /**
     * @param oppBranchName
     *            The oppBranchName to set.
     */
    public void setOppBranchName(String oppBranchName)
    {
        this.oppBranchName = oppBranchName;
    }

    /**
     * @return Returns the oppCountryId.
     */
    public long getOppCountryId()
    {
        return oppCountryId;
    }

    /**
     * @param oppCountryId
     *            The oppCountryId to set.
     */
    public void setOppCountryId(long oppCountryId)
    {
        this.oppCountryId = oppCountryId;
    }

    /**
     * @return Returns the remarkInfo.
     */
    public String getRemarkInfo()
    {
        return remarkInfo;
    }

    /**
     * @param remarkInfo
     *            The remarkInfo to set.
     */
    public void setRemarkInfo(String remarkInfo)
    {
        this.remarkInfo = remarkInfo;
    }

    /**
     * @return Returns the transactionTime.
     */
    public Date getTransactionTime()
    {
        return transactionTime;
    }

    /**
     * @param transactionTime
     *            The transactionTime to set.
     */
    public void setTransactionTime(Date transactionTime)
    {
        this.transactionTime = transactionTime;
    }

    /**
     * @return Returns the useInfo.
     */
    public String getUseInfo()
    {
        return useInfo;
    }

    /**
     * @param useInfo
     *            The useInfo to set.
     */
    public void setUseInfo(String useInfo)
    {
        this.useInfo = useInfo;
    }

    /**
     * @return Returns the queryEndDateString.
     */
    public String getQueryEndDateString()
    {
        return queryEndDateString;
    }

    /**
     * @param queryEndDateString The queryEndDateString to set.
     */
    public void setQueryEndDateString(String queryEndDateString)
    {
        this.queryEndDateString = queryEndDateString;
    }

    /**
     * @return Returns the queryStartDateString.
     */
    public String getQueryStartDateString()
    {
        return queryStartDateString;
    }

    /**
     * @param queryStartDateString The queryStartDateString to set.
     */
    public void setQueryStartDateString(String queryStartDateString)
    {
        this.queryStartDateString = queryStartDateString;
    }

   
}