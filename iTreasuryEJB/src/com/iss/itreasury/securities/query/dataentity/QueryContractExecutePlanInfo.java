/*
 * Created on 2004-4-13
 * 
 * To change the template for this generated file go to Window - Preferences -
 * Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.securities.query.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;
 
import com.iss.itreasury.securities.util.SECBaseDataEntity;

/**
 * @author 王怡
 * 
 * To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Generation - Code and Comments
 */
public class QueryContractExecutePlanInfo extends SECBaseDataEntity	implements Serializable {
	
    //合同id
    private long contractId = -1;
    
    //收款日期
    private Timestamp receivedDate = null;
    
    //付款日期
    private Timestamp paiedDate = null;
    
    //收款1
    private double receivedAmount1 = 0.0;
    
	//收款2
	private double receivedAmount2 = 0.0;
    
	//付款1
	private double paiedAmount1 = 0.0;
    
	//付款2
	private double paiedAmount2 = 0.0;

    //金额,交易金额,承销金额
	private double amount              = 0.0;
	
	//手续费率
	private double commissionChargeRate = 0.0;
	
	//币种
	private long currencyID = -1;
	
	//已付利息,实际收益
	private double realInterest = 0.0;
	
	//执行利率,收益率
	private double executeRate = 0.0;
	
	//利息合计
	private double interestBalance = 0.0;
	
	//修改日期
	private Timestamp modifyDate = null;
	
	//交易类型id
	private long transactionTypeID = -1;
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.iss.itreasury.util.ITreasuryBaseDataEntity#getId()
	 */
	public long getId() {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.iss.itreasury.util.ITreasuryBaseDataEntity#setId(long)
	 */
	public void setId(long id) {
		// TODO Auto-generated method stub

	}




	/**
	 * @return
	 */
	public double getAmount()
	{
		return amount;
	}

	/**
	 * @return
	 */
	public double getCommissionChargeRate()
	{
		return commissionChargeRate;
	}

	/**
	 * @return
	 */
	public long getContractId()
	{
		return contractId;
	}

	/**
	 * @return
	 */
	public long getCurrencyID()
	{
		return currencyID;
	}

	/**
	 * @return
	 */
	public double getExecuteRate()
	{
		return executeRate;
	}

	/**
	 * @return
	 */
	public Timestamp getModifyDate()
	{
		return modifyDate;
	}

	/**
	 * @return
	 */
	public double getPaiedAmount1()
	{
		return paiedAmount1;
	}

	/**
	 * @return
	 */
	public double getPaiedAmount2()
	{
		return paiedAmount2;
	}

	/**
	 * @return
	 */
	public Timestamp getPaiedDate()
	{
		return paiedDate;
	}

	/**
	 * @return
	 */
	public double getRealInterest()
	{
		return realInterest;
	}

	/**
	 * @return
	 */
	public double getReceivedAmount1()
	{
		return receivedAmount1;
	}

	/**
	 * @return
	 */
	public double getReceivedAmount2()
	{
		return receivedAmount2;
	}

	/**
	 * @return
	 */
	public Timestamp getReceivedDate()
	{
		return receivedDate;
	}

	/**
	 * @return
	 */
	public long getTransactionTypeID()
	{
		return transactionTypeID;
	}

	/**
	 * @param d
	 */
	public void setAmount(double d)
	{
		amount = d;
	}

	/**
	 * @param d
	 */
	public void setCommissionChargeRate(double d)
	{
		commissionChargeRate = d;
	}

	/**
	 * @param l
	 */
	public void setContractId(long l)
	{
		contractId = l;
	}

	/**
	 * @param l
	 */
	public void setCurrencyID(long l)
	{
		currencyID = l;
	}

	/**
	 * @param d
	 */
	public void setExecuteRate(double d)
	{
		executeRate = d;
	}

	/**
	 * @param timestamp
	 */
	public void setModifyDate(Timestamp timestamp)
	{
		modifyDate = timestamp;
	}

	/**
	 * @param d
	 */
	public void setPaiedAmount1(double d)
	{
		paiedAmount1 = d;
	}

	/**
	 * @param d
	 */
	public void setPaiedAmount2(double d)
	{
		paiedAmount2 = d;
	}

	/**
	 * @param timestamp
	 */
	public void setPaiedDate(Timestamp timestamp)
	{
		paiedDate = timestamp;
	}

	/**
	 * @param d
	 */
	public void setRealInterest(double d)
	{
		realInterest = d;
	}

	/**
	 * @param d
	 */
	public void setReceivedAmount1(double d)
	{
		receivedAmount1 = d;
	}

	/**
	 * @param d
	 */
	public void setReceivedAmount2(double d)
	{
		receivedAmount2 = d;
	}

	/**
	 * @param timestamp
	 */
	public void setReceivedDate(Timestamp timestamp)
	{
		receivedDate = timestamp;
	}

	/**
	 * @param l
	 */
	public void setTransactionTypeID(long l)
	{
		transactionTypeID = l;
	}

	/**
	 * @return
	 */
	public double getInterestBalance()
	{
		return interestBalance;
	}

	/**
	 * @param d
	 */
	public void setInterestBalance(double d)
	{
		interestBalance = d;
	}

}
