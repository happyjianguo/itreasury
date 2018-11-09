package com.iss.itreasury.ebank.obquery.dataentity;

import java.sql.Timestamp;
import java.util.Collection;


/**
 * 当日余额查询：包括汇总当日余额查询和下属单位当日余额查询
 * 账户组和账户可以用这个对象拼成想要的结果，详细信息可以用OBTodayBalanceDetailResultInfo这个对象
 * 返回值的INFO
 * @author liuyang
 *
 */  
public class OBTodayBalanceResultInfo implements java.io.Serializable
{					
	private long accountgroupid		=	-1;		//账户组类型id
	private long accounttype		=	-1;		//账户类型id
	private long   areaid			=	-2;		//区域中心id 
	private String AreaName			=	"";		//区域中心名称
	private long   currencyID		=	-1;		//币种id
	private long   Bankid			= 	-1;		//银行id
	private String bankName			= 	"";		//银行名称
	private long   accountID		=	-1;		//账户id
	private String accountNo		= 	"";		//账号
	private double CurrenBalance	=Double.NaN;//当日余额
	private long   clientID			=   -1;		//客户id 
	private String clientName		=	"";		//客户名称
	private String  billNO	        = 	"";		//单据号
	private Timestamp StartDate		= null;		//存入日期
	private Timestamp EndDate		= null;		//到期日期
	private long 	Term 			= -1;   	//期限
	public double Rate				=Double.NaN;//利率
	private long	mark			=-1;		//备注 1为已到期   0位未到期
 
	 
   
	
	
    
	
	    /**
     * @return Returns the accountgroupid.
     */
    public long getAccountgroupid()
    {
        return accountgroupid;
    }
    /**
     * @param accountgroupid The accountgroupid to set.
     */
    public void setAccountgroupid(long accountgroupid)
    {
        this.accountgroupid = accountgroupid;
    }
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
     * @return Returns the areaid.
     */
    public long getAreaid()
    {
        return areaid;
    }
    /**
     * @param areaid The areaid to set.
     */
    public void setAreaid(long areaid)
    {
        this.areaid = areaid;
    }
    /**
     * @return Returns the areaName.
     */
    public String getAreaName()
    {
        return AreaName;
    }
    /**
     * @param areaName The areaName to set.
     */
    public void setAreaName(String areaName)
    {
        AreaName = areaName;
    }
    /**
     * @return Returns the bankid.
     */
    public long getBankid()
    {
        return Bankid;
    }
    /**
     * @param bankid The bankid to set.
     */
    public void setBankid(long bankid)
    {
        Bankid = bankid;
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
     * @return Returns the currenBalance.
     */
    public double getCurrenBalance()
    {
        return CurrenBalance;
    }
    /**
     * @param currenBalance The currenBalance to set.
     */
    public void setCurrenBalance(double currenBalance)
    {
        CurrenBalance = currenBalance;
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
     * @return Returns the billNO.
     */
    public String getBillNO()
    {
        return billNO;
    }
    /**
     * @param billNO The billNO to set.
     */
    public void setBillNO(String billNO)
    {
        this.billNO = billNO;
    }
    /**
     * @return Returns the endDate.
     */
    public Timestamp getEndDate()
    {
        return EndDate;
    }
    /**
     * @param endDate The endDate to set.
     */
    public void setEndDate(Timestamp endDate)
    {
        EndDate = endDate;
    }
    /**
     * @return Returns the rate.
     */
    public double getRate()
    {
        return Rate;
    }
    /**
     * @param rate The rate to set.
     */
    public void setRate(double rate)
    {
        Rate = rate;
    }
    /**
     * @return Returns the startDate.
     */
    public Timestamp getStartDate()
    {
        return StartDate;
    }
    /**
     * @param startDate The startDate to set.
     */
    public void setStartDate(Timestamp startDate)
    {
        StartDate = startDate;
    }
    /**
     * @return Returns the term.
     */
    public long getTerm()
    {
        return Term;
    }
    /**
     * @param term The term to set.
     */
    public void setTerm(long term)
    {
        Term = term;
    }
    
    /**
     * @return Returns the mark.
     */
    public long getMark()
    {
        return mark;
    }
    /**
     * @param mark The mark to set.
     */
    public void setMark(long mark)
    {
        this.mark = mark;
    }
}
	
	
	
	
	
	
	
 