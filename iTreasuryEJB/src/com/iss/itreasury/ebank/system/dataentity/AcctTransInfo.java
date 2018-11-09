package com.iss.itreasury.ebank.system.dataentity;

import java.util.Date;

/**
 *
 * @name: AcctTransInfo
 * @description:银行账户交易(当日/历史)明细信息
 * @author: gqfang
 * @create: 2005-6-15
 *
 */
public class AcctTransInfo
{
    private long   id                   = -1;        // 唯一标识

    private long   accountId            = -1;        // 本方账户Id

    private long   clientId             = -1;        // 客户Id

    private long   countryId            = -1;        // 国家Id

    private String accountNo            = "";        // 账号

    private String accountName          = "";        // 账户名称

    private long   currencyType         = -1;        // 币种

    private String branchName           = "";        // 银行名称

    private String oppAccountNo         = "";        // 对方账号

    private String oppAccountName       = "";        // 对方账户名称

    private String oppBranchSwiftCode   = "";        // 对方开户行SWIFT代码

    private String oppBranchName        = "";        // 对方行名称

    private long   oppCountryId         = -1;        // 对方开户行所在国

    private String oppAddress           = "";        // 对方开户行所在地

    private double amount               = Double.NaN; // 交易金额

    private long   direction            = -1;        // 借贷方向

    private Date   transactionTime      = null;      // 交易日期

    private Date   valueDate            = null;      // 起息日

    private String abstractInfo         = "";        // 摘要

    private String checkNo              = "";        // 票据号

    private String useInfo              = "";        // 用途信息

    private String remarkInfo           = "";        // 备注信息

    private long   bankId               = -1;        // 交易银行

    private String interBranchName      = "";        // 中转银行名称

    private String interBranchSwiftCode = "";        // 中转银行SWIFT代码

    private String interMediaryBank     = "";        // 中转银行

    private String transNoOfUpacct      = "";        // 对应主账户的交易号

    private long   isDeletedByBank      = -1;        // 是否已经被银行删除

    private Date   modifyTime           = null;      // 修改时间

    private double debitAmount          = Double.NaN; // 借方金额

    private double creditAmount         = Double.NaN; // 贷方金额
    
    private String isDirectLink         = "";  //         归集方式
    
    private long isDirectLinklong=-1;      // 归集方式
    
    /**
     * 针对入账清单查询功能，增加显示字段
     * Edit by MXZhou, 2006-04-14
     */
	private long isToTurnIn				= -1;//是否需要入账
	private long turnInResult			= -1;//入账结果
	private String turnInRemind			= null;//入账结果描述
	private Date turnIn					= null;//入账日期
	private String transactionNo		= null;//对应外部系统的交易号
	
	private long isCurTrans = -1;      //是否是当前交易

    /**
     * @return Returns the abstractInfo.
     */
    public String getAbstractInfo()
    {
        return abstractInfo;
    }

    /**
     * @param abstractInfo The abstractInfo to set.
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
     * @param accountId The accountId to set.
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
     * @param accountNo The accountNo to set.
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
     * @param amount The amount to set.
     */
    public void setAmount(double amount)
    {
        this.amount = amount;
    }

    /**
     * @return Returns the bankId.
     */
    public long getBankId()
    {
        return bankId;
    }

    /**
     * @param bankId The bankId to set.
     */
    public void setBankId(long bankId)
    {
        this.bankId = bankId;
    }

    /**
     * @return Returns the branchName.
     */
    public String getBranchName()
    {
        return branchName;
    }

    /**
     * @param branchName The branchName to set.
     */
    public void setBranchName(String branchName)
    {
        this.branchName = branchName;
    }

    /**
     * @return Returns the checkNo.
     */
    public String getCheckNo()
    {
        return checkNo;
    }

    /**
     * @param checkNo The checkNo to set.
     */
    public void setCheckNo(String checkNo)
    {
        this.checkNo = checkNo;
    }

    /**
     * @return Returns the clientId.
     */
    public long getClientId()
    {
        return clientId;
    }

    /**
     * @param clientId The clientId to set.
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
     * @param countryId The countryId to set.
     */
    public void setCountryId(long countryId)
    {
        this.countryId = countryId;
    }

    /**
     * @return Returns the creditAmount.
     */
    public double getCreditAmount()
    {
        return creditAmount;
    }

    /**
     * @param creditAmount The creditAmount to set.
     */
    public void setCreditAmount(double creditAmount)
    {
        this.creditAmount = creditAmount;
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
     * @return Returns the debitAmount.
     */
    public double getDebitAmount()
    {
        return debitAmount;
    }

    /**
     * @param debitAmount The debitAmount to set.
     */
    public void setDebitAmount(double debitAmount)
    {
        this.debitAmount = debitAmount;
    }

    /**
     * @return Returns the direction.
     */
    public long getDirection()
    {
        return direction;
    }

    /**
     * @param direction The direction to set.
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
     * @param id The id to set.
     */
    public void setId(long id)
    {
        this.id = id;
    }

    /**
     * @return Returns the interBranchName.
     */
    public String getInterBranchName()
    {
        return interBranchName;
    }

    /**
     * @param interBranchName The interBranchName to set.
     */
    public void setInterBranchName(String interBranchName)
    {
        this.interBranchName = interBranchName;
    }

    /**
     * @return Returns the interBranchSwiftCode.
     */
    public String getInterBranchSwiftCode()
    {
        return interBranchSwiftCode;
    }

    /**
     * @param interBranchSwiftCode The interBranchSwiftCode to set.
     */
    public void setInterBranchSwiftCode(String interBranchSwiftCode)
    {
        this.interBranchSwiftCode = interBranchSwiftCode;
    }

    /**
     * @return Returns the interMediaryBank.
     */
    public String getInterMediaryBank()
    {
        return interMediaryBank;
    }

    /**
     * @param interMediaryBank The interMediaryBank to set.
     */
    public void setInterMediaryBank(String interMediaryBank)
    {
        this.interMediaryBank = interMediaryBank;
    }

    /**
     * @return Returns the isDeletedByBank.
     */
    public long getIsDeletedByBank()
    {
        return isDeletedByBank;
    }

    /**
     * @param isDeletedByBank The isDeletedByBank to set.
     */
    public void setIsDeletedByBank(long isDeletedByBank)
    {
        this.isDeletedByBank = isDeletedByBank;
    }

    /**
     * @return Returns the modifyTime.
     */
    public Date getModifyTime()
    {
        return modifyTime;
    }

    /**
     * @param modifyTime The modifyTime to set.
     */
    public void setModifyTime(Date modifyTime)
    {
        this.modifyTime = modifyTime;
    }

    /**
     * @return Returns the oppAccountName.
     */
    public String getOppAccountName()
    {
        return oppAccountName;
    }

    /**
     * @param oppAccountName The oppAccountName to set.
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
     * @param oppAccountNo The oppAccountNo to set.
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
     * @param oppAddress The oppAddress to set.
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
     * @param oppBranchName The oppBranchName to set.
     */
    public void setOppBranchName(String oppBranchName)
    {
        this.oppBranchName = oppBranchName;
    }

    /**
     * @return Returns the oppBranchSwiftCode.
     */
    public String getOppBranchSwiftCode()
    {
        return oppBranchSwiftCode;
    }

    /**
     * @param oppBranchSwiftCode The oppBranchSwiftCode to set.
     */
    public void setOppBranchSwiftCode(String oppBranchSwiftCode)
    {
        this.oppBranchSwiftCode = oppBranchSwiftCode;
    }

    /**
     * @return Returns the oppCountryId.
     */
    public long getOppCountryId()
    {
        return oppCountryId;
    }

    /**
     * @param oppCountryId The oppCountryId to set.
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
     * @param remarkInfo The remarkInfo to set.
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
     * @param transactionTime The transactionTime to set.
     */
    public void setTransactionTime(Date transactionTime)
    {
        this.transactionTime = transactionTime;
    }

    /**
     * @return Returns the transNoOfUpacct.
     */
    public String getTransNoOfUpacct()
    {
        return transNoOfUpacct;
    }

    /**
     * @param transNoOfUpacct The transNoOfUpacct to set.
     */
    public void setTransNoOfUpacct(String transNoOfUpacct)
    {
        this.transNoOfUpacct = transNoOfUpacct;
    }

    /**
     * @return Returns the useInfo.
     */
    public String getUseInfo()
    {
        return useInfo;
    }

    /**
     * @param useInfo The useInfo to set.
     */
    public void setUseInfo(String useInfo)
    {
        this.useInfo = useInfo;
    }

    /**
     * @return Returns the valueDate.
     */
    public Date getValueDate()
    {
        return valueDate;
    }

    /**
     * @param valueDate The valueDate to set.
     */
    public void setValueDate(Date valueDate)
    {
        this.valueDate = valueDate;
    }
    public static final long DIRECTLINK				= 1; //直连
    public static final long BANKHANDWORK			= 0; //银行落地
    public static final long EBANKINCEPT			= 2; //网银接收
    
    private static final String NAME_DIRECTLINK    		= "直连";
    private static final String NAME_BANKHANDWORK     	= "银行落地";
    private static final String NAME_EBANKINCEPT     	= "网银接收";
    
    public static final String getName(long lCode)
    {
        String strReturn = ""; //初始化返回值
        switch ((int) lCode)
        {
            case (int) DIRECTLINK:
                strReturn = NAME_DIRECTLINK;
                break;
            case (int) BANKHANDWORK:
                strReturn = NAME_BANKHANDWORK;
                break;
            case (int) EBANKINCEPT:
                strReturn = NAME_EBANKINCEPT;
                break;
        }
        return strReturn;
    }
    /**
     * @return Returns the isDirectLink.
     */
    public String isDirectLink()
    {
        return getName(getIsDirectLinklong());
    }

	public String getIsDirectLink() {
		return isDirectLink;
	}

	public void setIsDirectLink(String isDirectLink) {
		this.isDirectLink = isDirectLink;
	}

	public long getIsDirectLinklong() {
		return isDirectLinklong;
	}

	public void setIsDirectLinklong(long isDirectLinklong) {
		this.isDirectLinklong = isDirectLinklong;
	}

	/**
	 * @return Returns the isToTurnIn.
	 */
	public long getIsToTurnIn()
	{
		return isToTurnIn;
	}

	/**
	 * @param isToTurnIn The isToTurnIn to set.
	 */
	public void setIsToTurnIn(long isToTurnIn)
	{
		this.isToTurnIn = isToTurnIn;
	}

	/**
	 * @return Returns the transactionNo.
	 */
	public String getTransactionNo()
	{
		return transactionNo;
	}

	/**
	 * @param transactionNo The transactionNo to set.
	 */
	public void setTransactionNo(String transactionNo)
	{
		this.transactionNo = transactionNo;
	}

	/**
	 * @return Returns the turnIn.
	 */
	public Date getTurnIn()
	{
		return turnIn;
	}

	/**
	 * @param turnIn The turnIn to set.
	 */
	public void setTurnIn(Date turnIn)
	{
		this.turnIn = turnIn;
	}

	/**
	 * @return Returns the turnInRemind.
	 */
	public String getTurnInRemind()
	{
		return turnInRemind;
	}

	/**
	 * @param turnInRemind The turnInRemind to set.
	 */
	public void setTurnInRemind(String turnInRemind)
	{
		this.turnInRemind = turnInRemind;
	}

	/**
	 * @return Returns the turnInResult.
	 */
	public long getTurnInResult()
	{
		return turnInResult;
	}

	/**
	 * @param turnInResult The turnInResult to set.
	 */
	public void setTurnInResult(long turnInResult)
	{
		this.turnInResult = turnInResult;
	}

	public long getIsCurTrans()
	{
		return isCurTrans;
	}

	public void setIsCurTrans(long isCurTrans)
	{
		this.isCurTrans = isCurTrans;
	}

   
}