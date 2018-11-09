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
package com.iss.itreasury.loan.transdiscountcontract.dataentity;

import java.sql.Timestamp;

import com.iss.itreasury.loan.base.LoanBaseDataEntity;


/**
 * @author yfan
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class TransDiscountContractBillInfo extends LoanBaseDataEntity
{
    /*数据库对应属性*/
    private long id = -1;
	private long CurrencyId = -1;//nCurrencyID:币种标识
	private long OfficeId = -1;//nOfficeID:办事处标识
    private long nContractID = -1; 
    private long nSerialNo = -1;
    private String sUserName = "";
    private String sBank = "";
    private long nIsLocal = -1;
    private Timestamp dtCreate = null;
    private Timestamp dtEnd = null; 
    private String sCode = "";
    private double mAmount = 0.0;
    private long nStatusID = -1;
    private long nSellStatusID = -1;
    private long nAddDays = -1;
    private long nDiscountCredenceID = -1;
    private long OB_nDiscountCredenceID = -1;
    private long nAcceptPoTypeID = -1;//票据类型
    private long nDraftTypeId = -1;//汇票类型
    private long nBillSourceTypeID = -1;//票据来源
    private String sFormerOwner = "";
    private double draftAmortizeInterest = 0.00;  //票据已摊销利息

	public double getDraftAmortizeInterest() {
		return draftAmortizeInterest;
	}
	public void setDraftAmortizeInterest(double draftAmortizeInterest) {
		this.draftAmortizeInterest = draftAmortizeInterest;
	}
	public long getDraftTypeId() {
		return nDraftTypeId;
	}
	public void setDraftTypeId(long nDraftTypeId) {
		this.nDraftTypeId = nDraftTypeId;
		putUsedField("nDraftTypeId", nDraftTypeId);
	}
	private double mCheckAmount = 0.0; 
    /*票据其他信息*/
	private boolean bCanUse = false;				//能否被新凭证用（既有无其他凭证占用此票据）
	private double mAccrual = 0.0;					//票据利息
	private Timestamp dtRepurchaseDate = null;			//回购日期
	private long nRepurchaseTerm = -1;					//回购期限
	private Timestamp dtDiscountDate = null;		//转贴现日（合同）
	private long nRealDiscountDays = -1;			//实际转贴现天数
    private long nInOrOut = -1;						//转贴现种类（买入，卖出）
    private long nDiscountTypeID = -1;				//所属转贴现类型（买断，回购）
    private long nRepurchaseTypeID = -1;			//所属转贴现的回购种类（一次，分次）
    private double dDiscountRate = 0.0;				//转贴现利率 
    private long nRepurchaseCredenceID = -1;		//所属回购凭证
    private long nstoragestatusid = -1;             //票据的出入库状态
    private long nstoragetransid = -1;				//票据出入库交易ID
    private String payee = "";						//收款人
    
    private double breaknotifyAccrual = 0.0;         //卖出买断票据利息(供卖出买断转贴现凭证查询使用)
    public String getPayee() {
		return payee;
	}
	public void setPayee(String payee) {
		this.payee = payee;
		putUsedField("payee", payee);
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
	public long getContractID()
	{
		return nContractID;
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
	public void setContractID(long l)
	{
		nContractID = l;
		putUsedField("nContractID", l);
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
	 * @return
	 */
	public Timestamp getRepurchaseDate()
	{
		return dtRepurchaseDate;
	}

	/**
	 * @return
	 */
	public long getRepurchaseTerm()
	{
		return nRepurchaseTerm;
	}

	/**
	 * @param timestamp
	 */
	public void setRepurchaseDate(Timestamp timestamp)
	{
		dtRepurchaseDate = timestamp;
        putUsedField("repurchaseDate", dtRepurchaseDate);
	}

	/**
	 * @param l
	 */
	public void setRepurchaseTerm(long l)
	{
		nRepurchaseTerm = l;
        putUsedField("repurchaseTerm", nRepurchaseTerm);
	}



	/**
	 * @return
	 */
	public double getDiscountRate()
	{
		return dDiscountRate;
	}

	/**
	 * @param d
	 */
	public void setDiscountRate(double d)
	{
		dDiscountRate = d;
	}

	/**
	 * @return
	 */
	public long getInOrOut()
	{
		return nInOrOut;
	}

	/**
	 * @param l
	 */
	public void setInOrOut(long l)
	{
		nInOrOut = l;
	}

	/**
	 * @return
	 */
	public boolean isCanUse()
	{
		return bCanUse;
	}

	/**
	 * @param b
	 */
	public void setCanUse(boolean b)
	{
		bCanUse = b;
	}

	/**
	 * @return
	 */
	public long getRepurchaseCredenceID()
	{
		return nRepurchaseCredenceID;
	}

	/**
	 * @param l
	 */
	public void setRepurchaseCredenceID(long l)
	{
		nRepurchaseCredenceID = l;
	}

    /**
     * @param 
     * return long
     */
    public long getBillSourceTypeID()
    {
        return nBillSourceTypeID;
    }

    /**
     * @param 
     * return void
     */
    public void setBillSourceTypeID(long l)
    {
        nBillSourceTypeID = l;
        putUsedField("nBillSourceTypeID", l);
    }

    /**
     * @param 
     * return long
     */
    public long getSellStatusID()
    {
        return nSellStatusID;
    }

    /**
     * @param 
     * return void
     */
    public void setSellStatusID(long l)
    {
        nSellStatusID = l;
        putUsedField("nSellStatusID", l);
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
	public long getNstoragestatusid() {
		return nstoragestatusid;
	}
	public void setNstoragestatusid(long storagestatusid) {
		nstoragestatusid = storagestatusid;
		putUsedField("nstoragestatusid", nstoragestatusid);
	}
	public long getNstoragetransid() {
		return nstoragetransid;
	}
	public void setNstoragetransid(long nstoragetransid) {
		this.nstoragetransid = nstoragetransid;
	}
	public double getBreaknotifyAccrual() {
		return breaknotifyAccrual;
	}
	public void setBreaknotifyAccrual(double breaknotifyAccrual) {
		this.breaknotifyAccrual = breaknotifyAccrual;
	}
	
}
