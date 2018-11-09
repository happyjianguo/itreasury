package com.iss.itreasury.ebank.obquery.dataentity;

import java.sql.Timestamp;


/**
 * 历史余额查询：包括汇总历史余额查询和下属单位历史余额查询
 * 返回值的INFO
 * @author liuyang
 *
 */
public class OBHisBalanceResultInfo implements java.io.Serializable
{

	private long accounttype		=	-1;		//账户类型id
	private long   areaID			=	-2;		//区域中心id 
	private String areaName			=	"";		//区域中心名称
	private long   currencyID		=	-1;		//币种id
	private long   bankid			= 	-1;		//银行id
	private String bankName			= 	"";		//银行名称 
	private long   countryID		=   -1;     //国家id
	private String countryName		=   "";		//国家名称
	private long   accountID		=	-1;		//账户id
	private String accountNo		= 	"";		//账号
	private String accountname      =   "";		//账户名称
	private double hisBalance	=Double.NaN;    //历史余额
	private long   clientID			=   -1;		//客户id 
	private String clientname		=	"";		//客户名称
	private Timestamp executeDate   = null;		//日期
		
	//透支查询	
	private double thisOverdraftAmount=0.0;//本期透支额度
	private double currOverdraftAmount=0.0;//当前已透支金额	
	private double useOverdraftAmount=0.0;//可用透支额度
	
	
		
	
		
	 
	
    /**
     * @return Returns the accountID.
     */
    public long getAccountID()
    {
        return accountID;
    }
    /**
     * @param accountID The accountID to set.
     */
    public void setAccountID(long accountID)
    {
        this.accountID = accountID;
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
     * @return Returns the accounttype.
     */
    public long getAccounttype()
    {
        return accounttype;
    }
    /**
     * @param accounttype The accounttype to set.
     */
    public void setAccounttype(long accounttype)
    {
        this.accounttype = accounttype;
    }
    /**
     * @return Returns the areaID.
     */
    public long getAreaID()
    {
        return areaID;
    }
    /**
     * @param areaID The areaID to set.
     */
    public void setAreaID(long areaID)
    {
        this.areaID = areaID;
    }
    /**
     * @return Returns the areaName.
     */
    public String getAreaName()
    {
        return areaName;
    }
    /**
     * @param areaName The areaName to set.
     */
    public void setAreaName(String areaName)
    {
        this.areaName = areaName;
    }
    /**
     * @return Returns the bankid.
     */
    public long getBankid()
    {
        return bankid;
    }
    /**
     * @param bankid The bankid to set.
     */
    public void setBankid(long bankid)
    {
        this.bankid = bankid;
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
     * @return Returns the clientID.
     */
    public long getClientID()
    {
        return clientID;
    }
    /**
     * @param clientID The clientID to set.
     */
    public void setClientID(long clientID)
    {
        this.clientID = clientID;
    }
    /**
     * @return Returns the clientname.
     */
    public String getClientname()
    {
        return clientname;
    }
    /**
     * @param clientname The clientname to set.
     */
    public void setClientname(String clientname)
    {
        this.clientname = clientname;
    }
    /**
     * @return Returns the countryID.
     */
    public long getCountryID()
    {
        return countryID;
    }
    /**
     * @param countryID The countryID to set.
     */
    public void setCountryID(long countryID)
    {
        this.countryID = countryID;
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
     * @return Returns the currencyID.
     */
    public long getCurrencyID()
    {
        return currencyID;
    }
    /**
     * @param currencyID The currencyID to set.
     */
    public void setCurrencyID(long currencyID)
    {
        this.currencyID = currencyID;
    }
    /**
     * @return Returns the currOverdraftAmount.
     */
    public double getCurrOverdraftAmount()
    {
        return currOverdraftAmount;
    }
    /**
     * @param currOverdraftAmount The currOverdraftAmount to set.
     */
    public void setCurrOverdraftAmount(double currOverdraftAmount)
    {
        this.currOverdraftAmount = currOverdraftAmount;
    }
    /**
     * @return Returns the executeDate.
     */
    public Timestamp getExecuteDate()
    {
        return executeDate;
    }
    /**
     * @param executeDate The executeDate to set.
     */
    public void setExecuteDate(Timestamp executeDate)
    {
        this.executeDate = executeDate;
    }
    /**
     * @return Returns the hisBalance.
     */
    public double getHisBalance()
    {
        return hisBalance;
    }
    /**
     * @param hisBalance The hisBalance to set.
     */
    public void setHisBalance(double hisBalance)
    {
        this.hisBalance = hisBalance;
    }
    /**
     * @return Returns the thisOverdraftAmount.
     */
    public double getThisOverdraftAmount()
    {
        return thisOverdraftAmount;
    }
    /**
     * @param thisOverdraftAmount The thisOverdraftAmount to set.
     */
    public void setThisOverdraftAmount(double thisOverdraftAmount)
    {
        this.thisOverdraftAmount = thisOverdraftAmount;
    }
    /**
     * @return Returns the useOverdraftAmount.
     */
    public double getUseOverdraftAmount()
    {
        return useOverdraftAmount;
    }
    /**
     * @param useOverdraftAmount The useOverdraftAmount to set.
     */
    public void setUseOverdraftAmount(double useOverdraftAmount)
    {
        this.useOverdraftAmount = useOverdraftAmount;
    }
    
    /**
     * @return Returns the accountname.
     */
    public String getAccountname()
    {
        return accountname;
    }
    /**
     * @param accountname The accountname to set.
     */
    public void setAccountname(String accountname)
    {
        this.accountname = accountname;
    }
}