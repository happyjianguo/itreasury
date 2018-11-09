/*
 * Created on 2004-8-3
 *
 * Title:				iTreasury
 * @author             	yanliu
 * Company:             iSoftStone
 * Copyright:           Copyright (c) 2003
 * @version
 * Description:         
 */

package com.iss.itreasury.loan.transdiscountcredence.dataentity;

import com.iss.itreasury.loan.base.LoanBaseDataEntity;
import com.iss.itreasury.loan.util.LOANConstant;
import java.sql.Timestamp;

/**
 * @author yfan
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class TransDiscountCredenceQueryInfo extends LoanBaseDataEntity {

    private long id = -1;
    
    private long officeID = -1;
    private long currencyID= -1;
    private long clientID = -1;
    private String clientName = "";
    private long contractIDFrom = -1;
    private long contractIDTo = -1;
    private String contractCodeFrom = "";
    private String contractCodeTo = "";
    private double amountFrom = 0.0;
    private double amountTo = 0.0;
    private Timestamp startDate =  null;
    private Timestamp endDate = null;
    private long userID = -1;
    private long statusID = -1;
    private long actionID = -1;						//修改或是审核查询
    private long credenceTypeID = -1;				//转贴现凭证或回购凭证
    private double amount = 0.0;
    private double minterest = 0.0;
    
    /*查询票据用*/
    private long contractID = -1;					//合同ID
    private long credenceID = -1;					//凭证ID
    private String contractCode = "";
    private String credenceCode = "";				
    private long billSourceType = -1;				//票据来源
    private long discountTypeID = -1;				//转贴现类型
    private long repurchaseTypeID = -1;				//回购类型
    private long inOrOut = -1;
    
    
    private long pageLineCount = LOANConstant.PageControl.CODE_PAGELINECOUNT; 
    private long pageNo = -1;
    private long orderParam = -1;
    private long desc = -1;
    
    //private long billSourceTypeID = -1; 
    
    
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
	 * @return
	 */
	public double getAmountFrom()
	{
		return amountFrom;
	}

	/**
	 * @return
	 */
	public double getAmountTo()
	{
		return amountTo;
	}

	/**
	 * @return
	 */
	public long getClientID()
	{
		return clientID;
	}

	/**
	 * @return
	 */
	public long getContractIDFrom()
	{
		return contractIDFrom;
	}

	/**
	 * @return
	 */
	public long getContractIDTo()
	{
		return contractIDTo;
	}

	/**
	 * @return
	 */
	public long getDesc()
	{
		return desc;
	}

	/**
	 * @return
	 */
	public Timestamp getEndDate()
	{
		return endDate;
	}

	/**
	 * @return
	 */
	public long getOfficeID()
	{
		return officeID;
	}

	/**
	 * @return
	 */
	public long getOrderParam()
	{
		return orderParam;
	}

	/**
	 * @return
	 */
	public long getPageLineCount()
	{
		return pageLineCount;
	}

	/**
	 * @return
	 */
	public long getPageNo()
	{
		return pageNo;
	}

	/**
	 * @return
	 */
	public Timestamp getStartDate()
	{
		return startDate;
	}

	/**
	 * @return
	 */
	public long getStatusID()
	{
		return statusID;
	}

	/**
	 * @return
	 */
	public long getUserID()
	{
		return userID;
	}

	/**
	 * @param d
	 */
	public void setAmountFrom(double d)
	{
		amountFrom = d;
	}

	/**
	 * @param d
	 */
	public void setAmountTo(double d)
	{
		amountTo = d;
	}

	/**
	 * @param l
	 */
	public void setClientID(long l)
	{
		clientID = l;
	}

	/**
	 * @param l
	 */
	public void setContractIDFrom(long l)
	{
		contractIDFrom = l;
	}

	/**
	 * @param l
	 */
	public void setContractIDTo(long l)
	{
		contractIDTo = l;
	}

	/**
	 * @param l
	 */
	public void setDesc(long l)
	{
		desc = l;
	}

	/**
	 * @param timestamp
	 */
	public void setEndDate(Timestamp timestamp)
	{
		endDate = timestamp;
	}

	/**
	 * @param l
	 */
	public void setOfficeID(long l)
	{
		officeID = l;
	}

	/**
	 * @param l
	 */
	public void setOrderParam(long l)
	{
		orderParam = l;
	}

	/**
	 * @param l
	 */
	public void setPageLineCount(long l)
	{
		pageLineCount = l;
	}

	/**
	 * @param l
	 */
	public void setPageNo(long l)
	{
		pageNo = l;
	}

	/**
	 * @param timestamp
	 */
	public void setStartDate(Timestamp timestamp)
	{
		startDate = timestamp;
	}

	/**
	 * @param l
	 */
	public void setStatusID(long l)
	{
		statusID = l;
	}

	/**
	 * @param l
	 */
	public void setUserID(long l)
	{
		userID = l;
	}

	/**
	 * @return
	 */
	public long getActionID()
	{
		return actionID;
	}

	/**
	 * @param l
	 */
	public void setActionID(long l)
	{
		actionID = l;
	}

	/**
	 * @return
	 */
	public long getBillSourceType()
	{
		return billSourceType;
	}

	/**
	 * @return
	 */
	public long getContractID()
	{
		return contractID;
	}

	/**
	 * @param l
	 */
	public void setBillSourceType(long l)
	{
		billSourceType = l;
	}

	/**
	 * @param l
	 */
	public void setContractID(long l)
	{
		contractID = l;
	}

	/**
	 * @return
	 */
	public long getDiscountTypeID()
	{
		return discountTypeID;
	}

	/**
	 * @param l
	 */
	public void setDiscountTypeID(long l)
	{
		discountTypeID = l;
	}

	/**
	 * @return
	 */
	public long getRepurchaseTypeID()
	{
		return repurchaseTypeID;
	}

	/**
	 * @param l
	 */
	public void setRepurchaseTypeID(long l)
	{
		repurchaseTypeID = l;
	}

	/**
	 * @return
	 */
	public long getInOrOut()
	{
		return inOrOut;
	}

	/**
	 * @param l
	 */
	public void setInOrOut(long l)
	{
		inOrOut = l;
	}

	/**
	 * @return
	 */
	public String getContractCode()
	{
		return contractCode;
	}

	/**
	 * @return
	 */
	public String getContractCodeFrom()
	{
		return contractCodeFrom;
	}

	/**
	 * @return
	 */
	public String getContractCodeTo()
	{
		return contractCodeTo;
	}

	/**
	 * @param string
	 */
	public void setContractCode(String string)
	{
		contractCode = string;
	}

	/**
	 * @param string
	 */
	public void setContractCodeFrom(String string)
	{
		contractCodeFrom = string;
	}

	/**
	 * @param string
	 */
	public void setContractCodeTo(String string)
	{
		contractCodeTo = string;
	}

	/**
	 * @return
	 */
	public String getClientName()
	{
		return clientName;
	}

	/**
	 * @param string
	 */
	public void setClientName(String string)
	{
		clientName = string;
	}

	/**
	 * @return
	 */
	public long getCredenceID()
	{
		return credenceID;
	}

	/**
	 * @param l
	 */
	public void setCredenceID(long l)
	{
		credenceID = l;
	}

	/**
	 * @return
	 */
	public String getCredenceCode()
	{
		return credenceCode;
	}

	/**
	 * @param string
	 */
	public void setCredenceCode(String string)
	{
		credenceCode = string;
	}

	/**
	 * @return
	 */
	public long getCredenceTypeID()
	{
		return credenceTypeID;
	}

	/**
	 * @param l
	 */
	public void setCredenceTypeID(long l)
	{
		credenceTypeID = l;
	}

    /**
     * @return Returns the currencyId.
     */
    public long getCurrencyID() {
        return currencyID;
    }
    /**
     * @param currencyId The currencyId to set.
     */
    public void setCurrencyID(long currencyId) {
        this.currencyID = currencyId;
    }
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public double getMinterest() {
		return minterest;
	}
	public void setMinterest(double minterest) {
		this.minterest = minterest;
	}
}
