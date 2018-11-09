/**
 * Title:        		iTreasury
 * Description:         
 * Copyright:           Copyright (c) 2003
 * Company:             iSoftStone
 * @author             yehuang 
 * @version
 *  Date of Creation    2004-3-30
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
public class DailyStockInfo extends SECBaseDataEntity {
	private long id = -1;
	private Timestamp stockDate = null;		//日期
	private long securitiesID = -1;		//证券ID
	private long accountID = -1;    	//开户资金帐户ID
	private long clientID = -1;			//业务单位ID
	private double quantity = 0.0;	        //本日库存数量
	private double frozenQuantity = 0.0;	//本日累计库存冻结量
	private double cost = 0.0;			//本日累计库存总成本(全价)
	private double netCost = 0.0;		//本日累计净价总成本
	private double profitLoss = 0.0;	//本日累计实际盈亏(全价)
	private double netProfitLoss = 0.0;	//本日累计净价实际盈亏
	private long inNumber = 0;			//本日入库笔数
	private double inQuantity = 0.0;	//本日入库数量
	private double inAmount = 0.0;		//本日入库金额（全价）
	private double inNetAmount = 0.0;	//本日入库金额（净价）
	private long outNumber = 0;			//本日出库笔数
	private double outQuantity = 0.0;	//本日出库数量
	private double outCost = 0.0;		//本日出库成本（全价）
	private double outNetCost = 0.0;	//本日出库成本（净价）
	private double outAmount = 0.0;		//本日出库金额（全价）
	private double outNetAmount = 0.0;	//本日出库金额（净价）
	
	public void setAllValuesToDefault(){
		this.setQuantity(0.0);
		this.setFrozenQuantity(0.0);
		this.setCost(0.0);
		this.setNetCost(0.0);
		this.setProfitLoss(0.0);
		this.setNetProfitLoss(0.0);
		this.setInAmount(0);
		this.setInNumber(0);
		this.setInQuantity(0.0);
		this.setInNetAmount(0.0);
		this.setOutNumber(0);
		this.setOutQuantity(0.0);
		this.setOutCost(0.0);
		this.setOutCost(0.0);
		this.setOutNetCost(0.0);
		this.setOutAmount(0.0);
		this.setOutNetAmount(0.0);
	}

	/* (non-Javadoc)
	 * @see com.iss.itreasury.util.ITreasuryBaseDataEntity#getId()
	 */
	public long getId() {
		return -1;
	}
	/* (non-Javadoc)
	 * @see com.iss.itreasury.util.ITreasuryBaseDataEntity#setId(long)
	 */
	public void setId(long id) {
		this.id = id;
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
	 * @return Returns the cost.
	 */
	public double getCost() {
		return cost;
	}
	/**
	 * @param cost The cost to set.
	 */
	public void setCost(double cost) {
		putUsedField("cost", cost);
		this.cost = cost;
	}
	/**
	 * @return Returns the date.
	 */
	public Timestamp getStockDate() {
		return stockDate;
	}
	/**
	 * @param date The date to set.
	 */
	public void setStockDate(Timestamp stockDate) {
		putUsedField("stockDate", stockDate);
		this.stockDate = stockDate;
	}
	/**
	 * @return Returns the frozenQuantity.
	 */
	public double getFrozenQuantity() {
		return frozenQuantity;
	}
	/**
	 * @param frozenQuantity The frozenQuantity to set.
	 */
	public void setFrozenQuantity(double frozenQuantity) {
		putUsedField("frozenQuantity", frozenQuantity);
		this.frozenQuantity = frozenQuantity;
	}
	/**
	 * @return Returns the inAmount.
	 */
	public double getInAmount() {
		return inAmount;
	}
	/**
	 * @param inAmount The inAmount to set.
	 */
	public void setInAmount(double inAmount) {
		putUsedField("inAmount", inAmount);
		this.inAmount = inAmount;
	}
	/**
	 * @return Returns the inNetAmount.
	 */
	public double getInNetAmount() {
		return inNetAmount;
	}
	/**
	 * @param inNetAmount The inNetAmount to set.
	 */
	public void setInNetAmount(double inNetAmount) {
		putUsedField("inNetAmount", inNetAmount);
		this.inNetAmount = inNetAmount;
	}
	/**
	 * @return Returns the inNumber.
	 */
	public long getInNumber() {
		return inNumber;
	}
	/**
	 * @param inNumber The inNumber to set.
	 */
	public void setInNumber(long inNumber) {
		putUsedField("inNumber", inNumber);
		this.inNumber = inNumber;
	}
	/**
	 * @return Returns the inQuantity.
	 */
	public double getInQuantity() {
		return inQuantity;
	}
	/**
	 * @param inQuantity The inQuantity to set.
	 */
	public void setInQuantity(double inQuantity) {
		putUsedField("inQuantity", inQuantity);
		this.inQuantity = inQuantity;
	}
	/**
	 * @return Returns the netCost.
	 */
	public double getNetCost() {
		return netCost;
	}
	/**
	 * @param netCost The netCost to set.
	 */
	public void setNetCost(double netCost) {
		putUsedField("netCost", netCost);
		this.netCost = netCost;
	}
	/**
	 * @return Returns the netProfitLoss.
	 */
	public double getNetProfitLoss() {
		return netProfitLoss;
	}
	/**
	 * @param netProfitLoss The netProfitLoss to set.
	 */
	public void setNetProfitLoss(double netProfitLoss) {
		putUsedField("netProfitLoss", netProfitLoss);
		this.netProfitLoss = netProfitLoss;
	}
	/**
	 * @return Returns the outAmount.
	 */
	public double getOutAmount() {
		return outAmount;
	}
	/**
	 * @param outAmount The outAmount to set.
	 */
	public void setOutAmount(double outAmount) {
		putUsedField("outAmount", outAmount);
		this.outAmount = outAmount;
	}
	/**
	 * @return Returns the outCost.
	 */
	public double getOutCost() {
		return outCost;
	}
	/**
	 * @param outCost The outCost to set.
	 */
	public void setOutCost(double outCost) {
		putUsedField("outCost", outCost);
		this.outCost = outCost;
	}
	/**
	 * @return Returns the outNetAmount.
	 */
	public double getOutNetAmount() {
		return outNetAmount;
	}
	/**
	 * @param outNetAmount The outNetAmount to set.
	 */
	public void setOutNetAmount(double outNetAmount) {
		putUsedField("outNetAmount", outNetAmount);
		this.outNetAmount = outNetAmount;
	}
	/**
	 * @return Returns the outNetCost.
	 */
	public double getOutNetCost() {
		return outNetCost;
	}
	/**
	 * @param outNetCost The outNetCost to set.
	 */
	public void setOutNetCost(double outNetCost) {
		putUsedField("outNetCost", outNetCost);
		this.outNetCost = outNetCost;
	}
	/**
	 * @return Returns the outNumber.
	 */
	public long getOutNumber() {
		return outNumber;
	}
	/**
	 * @param outNumber The outNumber to set.
	 */
	public void setOutNumber(long outNumber) {
		putUsedField("outNumber", outNumber);
		this.outNumber = outNumber;
	}
	/**
	 * @return Returns the outQuantity.
	 */
	public double getOutQuantity() {
		return outQuantity;
	}
	/**
	 * @param outQuantity The outQuantity to set.
	 */
	public void setOutQuantity(double outQuantity) {
		putUsedField("outQuantity", outQuantity);
		this.outQuantity = outQuantity;
	}
	/**
	 * @return Returns the profitLoss.
	 */
	public double getProfitLoss() {
		return profitLoss;
	}
	/**
	 * @param profitLoss The profitLoss to set.
	 */
	public void setProfitLoss(double profitLoss) {
		putUsedField("profitLoss", profitLoss);
		this.profitLoss = profitLoss;
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
}
