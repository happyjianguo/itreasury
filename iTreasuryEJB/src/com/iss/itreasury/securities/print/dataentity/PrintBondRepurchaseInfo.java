/*
 * Created on 2004-5-13
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.iss.itreasury.securities.print.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;

import com.iss.itreasury.securities.util.SECBaseDataEntity;
/**
 * @author ygzhao
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class PrintBondRepurchaseInfo extends SECBaseDataEntity implements Serializable 
{
	/* (non-Javadoc)
	 * @see com.iss.itreasury.util.ITreasuryBaseDataEntity#getId()
	 */
	public long getId() 
	{
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.iss.itreasury.util.ITreasuryBaseDataEntity#setId(long)
	 */
	public void setId(long id) 
	{		
		
	}
//	回购交割单编号
	private String  repurchaseCode          = "";
//  购回交割单编号
	private String  repayCode          = "";
//	业务单位ID
	private long clientID = -1 ;
//  股东账户ID
	private long stockHolderAccountId = -1;
//	开户营业部ID
//	private long bankOfDepositID = -1;
//	资金帐号ID
	private long accountID = -1;
//  交易对手ID
	private long counterpartID = -1;
//  购回日期
	private Timestamp deliveryDate = null;
//	成交日期
	private Timestamp transactionDate  = null;
//  品种:证券名称
	private long securitiesId = -1;

//  购回金额
	private double repayAmount = 0.0;
//  业务收益
	private double businessIncome = 0.0;
//  业务本金
	private double businessCapital = 0.0;
//  业务费用
	private double businessExpenditure = 0.0;
//  占用天数
	private long occupyDays = -1;
//  业务资金占用
	private long businessCapitalOccupy = -1;
//  会计资金占用
	private long accountCapitalOccupy = -1;
//  单笔业务收益率(除税)
	private double singleBusinessIncomeRate = 0.0;
//  交易类型
	private long transactionTypeId = -1;
	
/**
 * @return
 */
public long getAccountCapitalOccupy() {
	return accountCapitalOccupy;
}

/**
 * @return
 */
public long getAccountID() {
	return accountID;
}


/**
 * @return
 */
public double getBusinessCapital() {
	return businessCapital;
}

/**
 * @return
 */
public long getBusinessCapitalOccupy() {
	return businessCapitalOccupy;
}

/**
 * @return
 */
public double getBusinessExpenditure() {
	return businessExpenditure;
}

/**
 * @return
 */
public double getBusinessIncome() {
	return businessIncome;
}

/**
 * @return
 */
public long getClientID() {
	return clientID;
}

/**
 * @return
 */
public long getCounterpartID() {
	return counterpartID;
}

/**
 * @return
 */
public Timestamp getDeliveryDate() {
	return deliveryDate;
}

/**
 * @return
 */
public long getOccupyDays() {
	return occupyDays;
}

/**
 * @return
 */
public double getRepayAmount() {
	return repayAmount;
}

/**
 * @return
 */
public String getRepayCode() {
	return repayCode;
}

/**
 * @return
 */
public String getRepurchaseCode() {
	return repurchaseCode;
}

/**
 * @return
 */
public long getSecuritiesId() {
	return securitiesId;
}

/**
 * @return
 */
public double getSingleBusinessIncomeRate() {
	return singleBusinessIncomeRate;
}

/**
 * @return
 */
public long getStockHolderAccountId() {
	return stockHolderAccountId;
}

/**
 * @return
 */
public Timestamp getTransactionDate() {
	return transactionDate;
}

/**
 * @param l
 */
public void setAccountCapitalOccupy(long l) {
	accountCapitalOccupy = l;
}

/**
 * @param l
 */
public void setAccountID(long l) {
	accountID = l;
}


/**
 * @param d
 */
public void setBusinessCapital(double d) {
	businessCapital = d;
}

/**
 * @param l
 */
public void setBusinessCapitalOccupy(long l) {
	businessCapitalOccupy = l;
}

/**
 * @param d
 */
public void setBusinessExpenditure(double d) {
	businessExpenditure = d;
}

/**
 * @param d
 */
public void setBusinessIncome(double d) {
	businessIncome = d;
}

/**
 * @param l
 */
public void setClientID(long l) {
	clientID = l;
}

/**
 * @param l
 */
public void setCounterpartID(long l) {
	counterpartID = l;
}

/**
 * @param timestamp
 */
public void setDeliveryDate(Timestamp timestamp) {
	deliveryDate = timestamp;
}

/**
 * @param l
 */
public void setOccupyDays(long l) {
	occupyDays = l;
}

/**
 * @param d
 */
public void setRepayAmount(double d) {
	repayAmount = d;
}

/**
 * @param string
 */
public void setRepayCode(String string) {
	repayCode = string;
}

/**
 * @param string
 */
public void setRepurchaseCode(String string) {
	repurchaseCode = string;
}

/**
 * @param l
 */
public void setSecuritiesId(long l) {
	securitiesId = l;
}

/**
 * @param d
 */
public void setSingleBusinessIncomeRate(double d) {
	singleBusinessIncomeRate = d;
}

/**
 * @param l
 */
public void setStockHolderAccountId(long l) {
	stockHolderAccountId = l;
}

/**
 * @param timestamp
 */
public void setTransactionDate(Timestamp timestamp) {
	transactionDate = timestamp;
}

/**
 * @return
 */
public long getTransactionTypeId() {
	return transactionTypeId;
}

/**
 * @param l
 */
public void setTransactionTypeId(long l) {
	transactionTypeId = l;
}

}
