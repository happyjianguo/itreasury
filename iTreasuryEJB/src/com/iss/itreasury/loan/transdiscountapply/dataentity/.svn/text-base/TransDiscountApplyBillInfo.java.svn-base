/*
 * Created on 2004-8-3
 *
 * Title:				iTreasury
 * @author             	yfan 
 * Company:             iSoftStone
 * Copyright:           Copyright (c) 2003
 * @version
 * Description:         
 */
package com.iss.itreasury.loan.transdiscountapply.dataentity;

import java.sql.Timestamp;
import com.iss.itreasury.loan.base.LoanBaseDataEntity;

import com.iss.itreasury.system.approval.dataentity.InutParameterInfo;
/**
 * @author yfan
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class TransDiscountApplyBillInfo extends LoanBaseDataEntity
{
    /*数据库对应属性*/
    private long id = -1;
	private long CurrencyId = -1;//nCurrencyID:币种标识
	private long OfficeId = -1;//nOfficeID:办事处标识
    private long nLoanID = -1;  
    private long nSerialNo = -1;
    private String sUserName = "";
    private String sBank = "";
    private long nIsLocal = -1;
    private Timestamp dtCreate = null;
    private Timestamp dtEnd = null; 
    private String sCode = "";
    private double mAmount = 0.0;
    private long nStatusID = -1;
    private long nAddDays = -1;
    private long nDiscountCredenceID = -1;
    private long OB_nDiscountCredenceID = -1;
    private long nAcceptPoTypeID = -1;
    private String sFormerOwner = "";
    private double mCheckAmount = 0.0; 
    /*票据其他信息*/
    private long nContractID = -1; 
	private double mAccrual = 0.0;					//票据利息
	private Timestamp dtRedeemDate = null;			//回购日期
	private long nRedeemTerm = -1;					//回购期限
	private Timestamp dtDiscountDate = null;		//转贴现日（合同）
	private long nRealDiscountDays = -1;			//实际转贴现天数
    private long nDiscountTypeID = -1;				//所属转贴现类型（买入，卖出）
    private long nRepurchaseTypeID = -1;			//所属转贴现的回购种类（买断，回购）
    private Timestamp repurchaseDate = null;          //回购日期
    private long repurchaseTerm = 0;                  //回购期限
	private long billSourceType = -1;					//票据来源

    private long CountDays = 0;                  //计息天数
    
    private long inOrOut = -1;//申请是买入还是卖出 
    private long nDiscountTypeId = -1;//买断型还是回购型  

    private double BillInterest;            //贴现利息
    //private long BillCount;                 //汇票总张数
    //private double BillAmount;              //汇票总金额
  
    private long recordCount = -1;//总记录数
    private long bankCount;         //记录数
    private long bizCount;          //记录数
    
    private long pageCount = 0;//总页数
    private long pageNo = 0;//当前页码 
    
    private double totalBillAmount = 0;//总汇票金额
    private double realAmount = 0;//单票据票据实付金额
    private double totalRealAmount = 0;//总票据实付金额
    private double totalAccrual = 0;//总票据利息
    
    private long borrowclientid=-1; // 借款单位ID
    
    private InutParameterInfo inutParameterInfo;
    
    //add by dwj 票据同步方正
    private String payee = "";//mbshoukuanr
    
    public String getPayee() {
		return payee;
	}
	public void setPayee(String payee) {
		this.payee = payee;
		putUsedField("payee", payee);
	}
	
    /**
     * @return the inutParameterInfo
     */
    public InutParameterInfo getInutParameterInfo() {
    	return inutParameterInfo;
    }
    /**
     * @param inutParameterInfo the inutParameterInfo to set
     */
    public void setInutParameterInfo(InutParameterInfo inutParameterInfo) {
    	this.inutParameterInfo = inutParameterInfo;
    }
    
    /**
     * @return Returns the id.
     */
    public long getBorrowclientid(){
    	return 	borrowclientid;
    }
    public void setBorrowclientid(long borrowclientid){
    	this.borrowclientid=borrowclientid;
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
        putUsedField("id", id);
    }
	/**
	 * @return
	 */
	public Timestamp getCreate()
	{
		return dtCreate;
	}

	/**
	 * @return
	 */
	public Timestamp getEnd()
	{
		return dtEnd;
	}

	/**
	 * @return
	 */
	public double getAmount()
	{
		return mAmount;
	}

	/**
	 * @return
	 */
	public double getCheckAmount()
	{
		return mCheckAmount;
	}

	/**
	 * @return
	 */
	public long getAcceptPoTypeID()
	{
		return nAcceptPoTypeID;
	}

	/**
	 * @return
	 */
	public long getAddDays()
	{
		return nAddDays;
	}

	/**
	 * @return
	 */
	public long getLoanID()
	{
		return nLoanID;
	}

	/**
	 * @return
	 */
	public long getDiscountCredenceID()
	{
		return nDiscountCredenceID;
	}

	/**
	 * @return
	 */
	public long getIsLocal()
	{
		return nIsLocal;
	}

	/**
	 * @return
	 */
	public long getSerialNo()
	{
		return nSerialNo;
	}

	/**
	 * @return
	 */
	public long getStatusID()
	{
		return nStatusID;
	}

	/**
	 * @return
	 */
	public long getOB_nDiscountCredenceID()
	{
		return OB_nDiscountCredenceID;
	}

	/**
	 * @return
	 */
	public String getBank()
	{
		return sBank;
	}

	/**
	 * @return
	 */
	public String getCode()
	{
		return sCode;
	}

	/**
	 * @return
	 */
	public String getFormerOwner()
	{
		return sFormerOwner;
	}

	/**
	 * @return
	 */
	public String getUserName()
	{
		return sUserName;
	}

	/**
	 * @param timestamp
	 */
	public void setCreate(Timestamp timestamp)
	{
		dtCreate = timestamp;
		putUsedField("dtCreate", timestamp);
	}

	/**
	 * @param timestamp
	 */
	public void setEnd(Timestamp timestamp)
	{
		dtEnd = timestamp;
		putUsedField("dtEnd", timestamp);
	}

	/**
	 * @param d
	 */
	public void setAmount(double d)
	{
		mAmount = d;
		putUsedField("mAmount", d);
	}

	/**
	 * @param d
	 */
	public void setCheckAmount(double d)
	{
		mCheckAmount = d;
		putUsedField("mCheckAmount", d);
	}

	/**
	 * @param l
	 */
	public void setAcceptPoTypeID(long l)
	{
		nAcceptPoTypeID = l;
		putUsedField("nAcceptPoTypeID", l);
	}

	/**
	 * @param l
	 */
	public void setAddDays(long l)
	{
		nAddDays = l;
		putUsedField("nAddDays", l);
	}

	/**
	 * @param l
	 */
	public void setLoanID(long l)
	{
		nLoanID = l;
		putUsedField("nLoanID", l);
	}

	/**
	 * @param l
	 */
	public void setDiscountCredenceID(long l)
	{
		nDiscountCredenceID = l;
		putUsedField("nDiscountCredenceID", l);
	}

	/**
	 * @param l
	 */
	public void setIsLocal(long l)
	{
		nIsLocal = l;
		putUsedField("nIsLocal", l);
	}

	/**
	 * @param l
	 */
	public void setSerialNo(long l)
	{
		nSerialNo = l;
		putUsedField("nSerialNo", l);
	}

	/**
	 * @param l
	 */
	public void setStatusID(long l)
	{
		nStatusID = l;
		putUsedField("nStatusID", l);
	}

	/**
	 * @param l
	 */
	public void setOB_nDiscountCredenceID(long l)
	{
		OB_nDiscountCredenceID = l;
		putUsedField("OB_nDiscountCredenceID", l);
	}

	/**
	 * @param string
	 */
	public void setBank(String string)
	{
		sBank = string;
		putUsedField("sBank", string);
	}

	/**
	 * @param string
	 */
	public void setCode(String string)
	{
		sCode = string;
		putUsedField("sCode", string);
	}

	/**
	 * @param string
	 */
	public void setFormerOwner(String string)
	{
		sFormerOwner = string;
		putUsedField("sFormerOwner", string);
	}

	/**
	 * @param string
	 */
	public void setUserName(String string)
	{
		sUserName = string;
		putUsedField("sUserName", string);
	}

	/**
	 * @return
	 */
	public Timestamp getRedeemDate()
	{
		return dtRedeemDate;
	}

	/**
	 * @return
	 */
	public double getAccrual()
	{
		return mAccrual;
	}

	/**
	 * @return
	 */
	public long getRealDiscountDays()
	{
		return nRealDiscountDays;
	}

	/**
	 * @return
	 */
	public long getRedeemTerm()
	{
		return nRedeemTerm;
	}

	/**
	 * @param timestamp
	 */
	public void setRedeemDate(Timestamp timestamp)
	{
		dtRedeemDate = timestamp;
	}

	/**
	 * @param d
	 */
	public void setAccrual(double d)
	{
		mAccrual = d;
	}

	/**
	 * @param l
	 */
	public void setRealDiscountDays(long l)
	{
		nRealDiscountDays = l;
	}

	/**
	 * @param l
	 */
	public void setRedeemTerm(long l)
	{
		nRedeemTerm = l;
	}

	/**
	 * @return
	 */
	public long getDiscountTypeID()
	{
		return nDiscountTypeID;
	}

	/**
	 * @return
	 */
	public long getRepurchaseTypeID()
	{
		return nRepurchaseTypeID;
	}

	/**
	 * @param l
	 */
	public void setDiscountTypeID(long l)
	{
		nDiscountTypeID = l;
	}

	/**
	 * @param l
	 */
	public void setRepurchaseTypeID(long l)
	{
		nRepurchaseTypeID = l;
	}

	/**
	 * @return
	 */
	public Timestamp getDiscountDate()
	{
		return dtDiscountDate;
	}

	/**
	 * @param timestamp
	 */
	public void setDiscountDate(Timestamp timestamp)
	{
		dtDiscountDate = timestamp;
	}

    /**
     * @param 
     * function 得到/设置
     * return long
     */
    public long getContractID()
    {
        return nContractID;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setContractID(long l)
    {
        nContractID = l;
    }

    /**
     * @param 
     * return double
     */
    public double getBillInterest()
    {
        return BillInterest;
    }

    /**
     * @param 
     * return long
     */
    public long getPageCount()
    {
        return pageCount;
    }

    /**
     * @param 
     * return long
     */
    public long getPageNo()
    {
        return pageNo;
    }

    /**
     * @param 
     * return long
     */
    public long getRecordCount()
    {
        return recordCount;
    }

    /**
     * @param 
     * return double
     */
    public double getTotalBillAmount()
    {
        return totalBillAmount;
    }

    /**
     * @param 
     * return void
     */
    public void setBillInterest(double d)
    {
        BillInterest = d;
    }

    /**
     * @param 
     * return void
     */
    public void setPageCount(long l)
    {
        pageCount = l;
    }

    /**
     * @param 
     * return void
     */
    public void setPageNo(long l)
    {
        pageNo = l;
    }

    /**
     * @param 
     * return void
     */
    public void setRecordCount(long l)
    {
        recordCount = l;
    }

    /**
     * @param 
     * return void
     */
    public void setTotalBillAmount(double d)
    {
        totalBillAmount = d;
    }

    /**
     * @param 
     * return double
     */
    public double getRealAmount()
    {
        return realAmount;
    }

    /**
     * @param 
     * return double
     */
    public double getTotalAccrual()
    {
        return totalAccrual;
    }

    /**
     * @param 
     * return double
     */
    public double getTotalRealAmount()
    {
        return totalRealAmount;
    }

    /**
     * @param 
     * return void
     */
    public void setRealAmount(double d)
    {
        realAmount = d;
    }

    /**
     * @param 
     * return void
     */
    public void setTotalAccrual(double d)
    {
        totalAccrual = d;
    }

    /**
     * @param 
     * return void
     */
    public void setTotalRealAmount(double d)
    {
        totalRealAmount = d;
    }

    /**
     * @param 
     * return long
     */
    public long getBankCount()
    {
        return bankCount;
    }

    /**
     * @param 
     * return long
     */
    public long getBizCount()
    {
        return bizCount;
    }

    /**
     * @param 
     * return void
     */
    public void setBankCount(long l)
    {
        bankCount = l;
    }

    /**
     * @param 
     * return void
     */
    public void setBizCount(long l)
    {
        bizCount = l;
    }

    /**
     * @param 
     * return Timestamp
     */
    public Timestamp getRepurchaseDate()
    {
        return repurchaseDate;
    }

    /**
     * @param 
     * return long
     */
    public long getRepurchaseTerm()
    {
        return repurchaseTerm;
    }

    /**
     * @param 
     * return void
     */
    public void setRepurchaseDate(Timestamp timestamp)
    {
        repurchaseDate = timestamp;
        putUsedField("dtRepurchaseDate", repurchaseDate);
    }

    /**
     * @param 
     * return void
     */
    public void setRepurchaseTerm(long l)
    {
        repurchaseTerm = l;
        putUsedField("nRepurchaseTerm", repurchaseTerm);
    }

    /**
     * @param 
     * return long
     */
    public long getCountDays()
    {
        return CountDays;
    }

    /**
     * @param 
     * return void
     */
    public void setCountDays(long l)
    {
        CountDays = l;
    }

    /**
     * @param 
     * return long
     */
    public long getInOrOut()
    {
        return inOrOut;
    }

    /**
     * @param 
     * return void
     */
    public void setInOrOut(long l)
    {
        inOrOut = l;
    }

    /**
     * @param 
     * return long
     */
    public long getDiscountTypeId()
    {
        return nDiscountTypeId;
    }

    /**
     * @param 
     * return void
     */
    public void setDiscountTypeId(long l)
    {
        nDiscountTypeId = l;
    }

	/**
	 * @return
	 */
	public long getBillSourceType()
	{
		return billSourceType;
	}

	/**
	 * @param l
	 */
	public void setBillSourceType(long l)
	{
		billSourceType = l;
	}

	/**
	 * Returns the currencyId.
	 * @return long
	 */
	public long getCurrencyId() {
		return CurrencyId;
	}

	/**
	 * Returns the officeId.
	 * @return long
	 */
	public long getOfficeId() {
		return OfficeId;
	}

	/**
	 * Sets the currencyId.
	 * @param currencyId The currencyId to set
	 */
	public void setCurrencyId(long currencyId) {
		CurrencyId = currencyId;
		putUsedField("nCurrencyId", CurrencyId);
	}

	/**
	 * Sets the officeId.
	 * @param officeId The officeId to set
	 */
	public void setOfficeId(long officeId) {
		OfficeId = officeId;
		putUsedField("nOfficeId", OfficeId);
	}

}
