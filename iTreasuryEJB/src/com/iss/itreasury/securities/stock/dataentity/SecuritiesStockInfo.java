/**
 * Title:        		iTreasury
 * Description:         
 * Copyright:           Copyright (c) 2003
 * Company:             iSoftStone
 * @author             yehuang 
 * @version
 *  Date of Creation    2004-3-8
 */
package com.iss.itreasury.securities.stock.dataentity;

import com.iss.itreasury.securities.util.SECBaseDataEntity;

/**
 * @author yehuang
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class SecuritiesStockInfo extends SECBaseDataEntity {
	private long id = -1;                             //
	private long securitiesID = -1;                   //证券ID 
	private long accountID = -1;                      //开户资金帐户ID									
	private long clientID = -1;						  //业务单位ID	
	private double initQuantity = 0.0;					  //初始化库存	
	private double  initCost = 0.0;					  //初始化库存总成本(全价)	
	private double  initNetCost = 0.0;                //初始化库存净价总成本
	private double  initProfitLoss = 0.0;			  //初始化实际盈亏(全价)	
	private double  initNetProfitLoss = 0.0;		  //初始化净价实际盈亏	
	private double quantity = 0.0;                       //当前库存数量
	private double cost = 0.0;						  //当前库存总成本	
	private double  netCost = 0.0;					  //当前净价总成本	
	private double  profitLoss = 0.0;				  //当前实际盈亏(全价)	
	private double  netProfitLoss = 0.0;              //当前净价实际盈亏
	private double frozenQuantity = 0.0;				  //库存冻结量	
	private double initFrozenQuantity = 0.0;              //初使库存冻结量
	private double suspenseInQuantity = 0.0;             //待交割入库挂库数量
	private double  suspenseInAmount = 0.0;           //待交割入库挂库金额
	private double  suspenseInNetAmount = 0.0;		  //待交割入库挂库净价金额	
	private double suspenseOutQuantity = 0.0;            //待交割出库挂库数量
	private double  suspenseOutAmount = 0.0;		  //待交割出库挂库金额	
	private double  suspenseOutNetAmount = 0.0;       //待交割出库挂库
	
	/**初使化初始值*/
	public void resetInitValue(){
		this.setCost(0.0);
		this.setFrozenQuantity(0);
		this.setInitCost(0.0);
		this.setInitNetCost(0.0);
		this.setInitNetProfitLoss(0.0);
		this.setInitProfitLoss(0.0);
		this.setInitQuantity(0);
		this.setNetCost(0.0);
		this.setNetProfitLoss(0.0);
		this.setProfitLoss(0.0);
		this.setQuantity(0);
		this.setSuspenseInAmount(0.0);
		this.setSuspenseInNetAmount(0.0);
		this.setSuspenseInQuantity(0);
		this.setSuspenseOutAmount(0.0);
		this.setSuspenseOutNetAmount(0.0);
		this.setSuspenseOutQuantity(0);
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
	 * @return Returns the client.
	 */
	public long getClientID() {
		return clientID;
	}
	/**
	 * @param client The client to set.
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
	 * @return Returns the id.
	 */
	public long getId() {
		return id;
	}
	/**
	 * @param id The id to set.
	 */
	public void setId(long id) {
		putUsedField("id", id);
		this.id = id;
	}
	/**
	 * @return Returns the initCost.
	 */
	public double getInitCost() {
		return initCost;
	}
	/**
	 * @param initCost The initCost to set.
	 */
	public void setInitCost(double initCost) {
		putUsedField("initCost", initCost);
		this.initCost = initCost;
	}
	/**
	 * @return Returns the initNetCost.
	 */
	public double getInitNetCost() {
		return initNetCost;
	}
	/**
	 * @param initNetCost The initNetCost to set.
	 */
	public void setInitNetCost(double initNetCost) {
		putUsedField("initNetCost", initNetCost);
		this.initNetCost = initNetCost;
	}
	/**
	 * @return Returns the initNetProfitLoss.
	 */
	public double getInitNetProfitLoss() {
		return initNetProfitLoss;
	}
	/**
	 * @param initNetProfitLoss The initNetProfitLoss to set.
	 */
	public void setInitNetProfitLoss(double initNetProfitLoss) {
		putUsedField("initNetProfitLoss", initNetProfitLoss);
		this.initNetProfitLoss = initNetProfitLoss;
	}
	/**
	 * @return Returns the initProfitLoss.
	 */
	public double getInitProfitLoss() {
		return initProfitLoss;
	}
	/**
	 * @param initProfitLoss The initProfitLoss to set.
	 */
	public void setInitProfitLoss(double initProfitLoss) {
		putUsedField("initProfitLoss", initProfitLoss);
		this.initProfitLoss = initProfitLoss;
	}
	/**
	 * @return Returns the initQuantity.
	 */
	public double getInitQuantity() {
		return initQuantity;
	}
	/**
	 * @param initQuantity The initQuantity to set.
	 */
	public void setInitQuantity(double initQuantity) {
		putUsedField("initQuantity", initQuantity);
		this.initQuantity = initQuantity;
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
	/**
	 * @return Returns the suspenseInAmount.
	 */
	public double getSuspenseInAmount() {
		return suspenseInAmount;
	}
	/**
	 * @param suspenseInAmount The suspenseInAmount to set.
	 */
	public void setSuspenseInAmount(double suspenseInAmount) {
		putUsedField("suspenseInAmount", suspenseInAmount);
		this.suspenseInAmount = suspenseInAmount;
	}
	/**
	 * @return Returns the suspenseInNetAmount.
	 */
	public double getSuspenseInNetAmount() {
		return suspenseInNetAmount;
	}
	/**
	 * @param suspenseInNetAmount The suspenseInNetAmount to set.
	 */
	public void setSuspenseInNetAmount(double suspenseInNetAmount) {
		putUsedField("suspenseInNetAmount", suspenseInNetAmount);
		this.suspenseInNetAmount = suspenseInNetAmount;
	}
	/**
	 * @return Returns the suspenseInQuantity.
	 */
	public double getSuspenseInQuantity() {
		return suspenseInQuantity;
	}
	/**
	 * @param suspenseInQuantity The suspenseInQuantity to set.
	 */
	public void setSuspenseInQuantity(double suspenseInQuantity) {
		putUsedField("suspenseInQuantity", suspenseInQuantity);
		this.suspenseInQuantity = suspenseInQuantity;
	}
	/**
	 * @return Returns the suspenseOutAmount.
	 */
	public double getSuspenseOutAmount() {
		return suspenseOutAmount;
	}
	/**
	 * @param suspenseOutAmount The suspenseOutAmount to set.
	 */
	public void setSuspenseOutAmount(double suspenseOutAmount) {
		putUsedField("suspenseOutAmount", suspenseOutAmount);
		this.suspenseOutAmount = suspenseOutAmount;
	}
	/**
	 * @return Returns the suspenseOutNetAmount.
	 */
	public double getSuspenseOutNetAmount() {
		return suspenseOutNetAmount;
	}
	/**
	 * @param suspenseOutNetAmount The suspenseOutNetAmount to set.
	 */
	public void setSuspenseOutNetAmount(double suspenseOutNetAmount) {
		putUsedField("suspenseOutNetAmount", suspenseOutNetAmount);
		this.suspenseOutNetAmount = suspenseOutNetAmount;
	}
	/**
	 * @return Returns the suspenseOutQuantity.
	 */
	public double getSuspenseOutQuantity() {
		return suspenseOutQuantity;
	}
	/**
	 * @param suspenseOutQuantity The suspenseOutQuantity to set.
	 */
	public void setSuspenseOutQuantity(double suspenseOutQuantity) {
		putUsedField("suspenseOutQuantity", suspenseOutQuantity);
		this.suspenseOutQuantity = suspenseOutQuantity;
	}
	/**
	 * @return Returns the initFrozenQuantity.
	 */
	public double getInitFrozenQuantity() {
		return initFrozenQuantity;
	}
	/**
	 * @param initFrozenQuantity The initFrozenQuantity to set.
	 */
	public void setInitFrozenQuantity(double initFrozenQuantity) {
		putUsedField("initFrozenQuantity", initFrozenQuantity);
		this.initFrozenQuantity = initFrozenQuantity;
	}
}
