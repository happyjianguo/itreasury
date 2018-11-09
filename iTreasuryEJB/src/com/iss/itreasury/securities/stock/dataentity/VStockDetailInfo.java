/**
 * Title:        		iTreasury
 * Description:         
 * Copyright:           Copyright (c) 2003
 * Company:             iSoftStone
 * @author             yehuang 
 * @version
 *  Date of Creation    2004-3-31
 */
package com.iss.itreasury.securities.stock.dataentity;

import java.sql.Timestamp;

import com.iss.itreasury.securities.util.SECBaseDataEntity;

/**
 * @author yehuang
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class VStockDetailInfo extends SECBaseDataEntity {
	
	private long deliveryOrderID = -1;
	private String deliveryOrderCode = null;
	private long transactionTypeID = -1;
	private Timestamp transactionDate = null;
	private Timestamp deliveryDate = null;
	private long clientID = -1;
	private long accountID = -1;
	private long securitiesID = -1;
	private long direction = -1;
	private double price = 0.0;
	private double netPrice = 0.0;
	private double quantity = 0.0;
	private double amount = 0.0;
	private double netPriceAmount = 0.0;
	private double netIncome = 0.0;
	private double unitCost = 0.0;
	private double unitNetCost = 0.0;
	private double unitProfitloss = 0.0;
	private double unitNetProfitloss = 0.0;
	private long statusID = -1;
	
	
	/* (non-Javadoc)
	 * @see com.iss.itreasury.util.ITreasuryBaseDataEntity#getId()
	 */
	public long getId() {
		// TODO Auto-generated method stub
		return 0;
	}
	/* (non-Javadoc)
	 * @see com.iss.itreasury.util.ITreasuryBaseDataEntity#setId(long)
	 */
	public void setId(long id) {
		// TODO Auto-generated method stub
	}
	/**
	 * @return Returns the accountID.
	 */
	public long getAccountID() {
		return accountID;
	}
	/**
	 * @param accountID The accountID to set.
	 */
	public void setAccountID(long accountID) {
		putUsedField("accountID", accountID);
		this.accountID = accountID;
	}
	/**
	 * @return Returns the amount.
	 */
	public double getAmount() {
		return amount;
	}
	/**
	 * @param amount The amount to set.
	 */
	public void setAmount(double amount) {
		putUsedField("amount", amount);
		this.amount = amount;
	}
	/**
	 * @return Returns the clientID.
	 */
	public long getClientID() {
		return clientID;
	}
	/**
	 * @param clientID The clientID to set.
	 */
	public void setClientID(long clientID) {
		putUsedField("clientID", clientID);
		this.clientID = clientID;
	}
	/**
	 * @return Returns the deliveryDate.
	 */
	public Timestamp getDeliveryDate() {
		return deliveryDate;
	}
	/**
	 * @param deliveryDate The deliveryDate to set.
	 */
	public void setDeliveryDate(Timestamp deliveryDate) {
		putUsedField("deliveryDate", deliveryDate);
		this.deliveryDate = deliveryDate;
	}
	/**
	 * @return Returns the deliveryOrderCode.
	 */
	public String getDeliveryOrderCode() {
		return deliveryOrderCode;
	}
	/**
	 * @param deliveryOrderCode The deliveryOrderCode to set.
	 */
	public void setDeliveryOrderCode(String deliveryOrderCode) {
		putUsedField("deliveryOrderCode", deliveryOrderCode);
		this.deliveryOrderCode = deliveryOrderCode;
	}
	/**
	 * @return Returns the deliveryOrderID.
	 */
	public long getDeliveryOrderID() {
		return deliveryOrderID;
	}
	/**
	 * @param deliveryOrderID The deliveryOrderID to set.
	 */
	public void setDeliveryOrderID(long deliveryOrderID) {
		putUsedField("deliveryOrderID", deliveryOrderID);
		this.deliveryOrderID = deliveryOrderID;
	}
	/**
	 * @return Returns the direction.
	 */
	public long getDirection() {
		return direction;
	}
	/**
	 * @param direction The direction to set.
	 */
	public void setDirection(long direction) {
		putUsedField("direction", direction);
		this.direction = direction;
	}
	/**
	 * @return Returns the netIncome.
	 */
	public double getNetIncome() {
		return netIncome;
	}
	/**
	 * @param netIncome The netIncome to set.
	 */
	public void setNetIncome(double netIncome) {
		putUsedField("netIncome", netIncome);
		this.netIncome = netIncome;
	}
	/**
	 * @return Returns the netPrice.
	 */
	public double getNetPrice() {
		return netPrice;
	}
	/**
	 * @param netPrice The netPrice to set.
	 */
	public void setNetPrice(double netPrice) {
		putUsedField("netPrice", netPrice);
		this.netPrice = netPrice;
	}
	/**
	 * @return Returns the netPriceAmount.
	 */
	public double getNetPriceAmount() {
		return netPriceAmount;
	}
	/**
	 * @param netPriceAmount The netPriceAmount to set.
	 */
	public void setNetPriceAmount(double netPriceAmount) {
		putUsedField("netPriceAmount", netPriceAmount);
		this.netPriceAmount = netPriceAmount;
	}
	/**
	 * @return Returns the price.
	 */
	public double getPrice() {
		return price;
	}
	/**
	 * @param price The price to set.
	 */
	public void setPrice(double price) {
		putUsedField("price", price);
		this.price = price;
	}
	/**
	 * @return Returns the quantity.
	 */
	public double getQuantity() {
		return quantity;
	}
	/**
	 * @param quantity The quantity to set.
	 */
	public void setQuantity(double quantity) {
		putUsedField("quantity", quantity);
		this.quantity = quantity;
	}
	/**
	 * @return Returns the securitiesID.
	 */
	public long getSecuritiesID() {
		return securitiesID;
	}
	/**
	 * @param securitiesID The securitiesID to set.
	 */
	public void setSecuritiesID(long securitiesID) {
		putUsedField("securitiesID", securitiesID);
		this.securitiesID = securitiesID;
	}
	/**
	 * @return Returns the statusID.
	 */
	public long getStatusID() {
		return statusID;
	}
	/**
	 * @param statusID The statusID to set.
	 */
	public void setStatusID(long statusID) {
		putUsedField("statusID", statusID);
		this.statusID = statusID;
	}
	/**
	 * @return Returns the transactionDate.
	 */
	public Timestamp getTransactionDate() {
		return transactionDate;
	}
	/**
	 * @param transactionDate The transactionDate to set.
	 */
	public void setTransactionDate(Timestamp transactionDate) {
		putUsedField("transactionDate", transactionDate);
		this.transactionDate = transactionDate;
	}
	/**
	 * @return Returns the transactionTypeID.
	 */
	public long getTransactionTypeID() {
		return transactionTypeID;
	}
	/**
	 * @param transactionTypeID The transactionTypeID to set.
	 */
	public void setTransactionTypeID(long transactionTypeID) {
		putUsedField("transactionTypeID", transactionTypeID);
		this.transactionTypeID = transactionTypeID;
	}
	/**
	 * @return Returns the unitCost.
	 */
	public double getUnitCost() {
		return unitCost;
	}
	/**
	 * @param unitCost The unitCost to set.
	 */
	public void setUnitCost(double unitCost) {
		putUsedField("unitCost", unitCost);
		this.unitCost = unitCost;
	}
	/**
	 * @return Returns the unitNetCost.
	 */
	public double getUnitNetCost() {
		return unitNetCost;
	}
	/**
	 * @param unitNetCost The unitNetCost to set.
	 */
	public void setUnitNetCost(double unitNetCost) {
		putUsedField("unitNetCost", unitNetCost);
		this.unitNetCost = unitNetCost;
	}
	/**
	 * @return Returns the unitNetProfitloss.
	 */
	public double getUnitNetProfitloss() {
		return unitNetProfitloss;
	}
	/**
	 * @param unitNetProfitloss The unitNetProfitloss to set.
	 */
	public void setUnitNetProfitloss(double unitNetProfitloss) {
		putUsedField("unitNetProfitloss", unitNetProfitloss);
		this.unitNetProfitloss = unitNetProfitloss;
	}
	/**
	 * @return Returns the unitProfitloss.
	 */
	public double getUnitProfitloss() {
		return unitProfitloss;
	}
	/**
	 * @param unitProfitloss The unitProfitloss to set.
	 */
	public void setUnitProfitloss(double unitProfitloss) {
		putUsedField("unitProfitloss", unitProfitloss);
		this.unitProfitloss = unitProfitloss;
	}
}
