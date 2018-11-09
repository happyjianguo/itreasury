/**
 * Title:        		iTreasury
 * Description:         
 * Copyright:           Copyright (c) 2003
 * Company:             iSoftStone
 * @author             yehuang 
 * @version
 *  Date of Creation    2004-3-15
 */
package com.iss.itreasury.securities.stock.dataentity;

import com.iss.itreasury.securities.util.SECBaseDataEntity;

/**
 * @author yehuang
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class SecuritiesStockReturn extends SECBaseDataEntity {
	private double newQuanitity = 0.0;                //新库存数量
	private double newCost = 0.0;                     //新成本
	private double newNetCost = 0.0;                  //新净价总成本  
	private double newProfitLoss = 0.0;               //新累计实际盈亏
	private double newNetProfitLoss = 0.0;            //新累计净价实际盈亏
	private double unitCost = 0.0;                    //单位成本 
	private double unitNetCost = 0.0;                 //单位净价成本
	private double unitProfitLoss = 0.0;              //单位实际盈亏
	private double unitNetProfitLoss = 0.0;           //单位净价实际盈亏
	
	
	public long getId() {
		return 0;
	}


	public void setId(long id) {		
	}
	/**
	 * @return Returns the outCost.
	 */
	public double getNewCost() {
		return newCost;
	}
	/**
	 * @param outCost The outCost to set.
	 */
	public void setNewCost(double outCost) {
		putUsedField("outCost", outCost);
		this.newCost = outCost;
	}
	/**
	 * @return Returns the outNetCost.
	 */
	public double getNewNetCost() {
		return newNetCost;
	}
	/**
	 * @param outNetCost The outNetCost to set.
	 */
	public void setNewNetCost(double outNetCost) {
		putUsedField("outNetCost", outNetCost);
		this.newNetCost = outNetCost;
	}
	/**
	 * @return Returns the outNetProfitLoss.
	 */
	public double getNewNetProfitLoss() {
		return newNetProfitLoss;
	}
	/**
	 * @param outNetProfitLoss The outNetProfitLoss to set.
	 */
	public void setNewNetProfitLoss(double outNetProfitLoss) {
		putUsedField("outNetProfitLoss", outNetProfitLoss);
		this.newNetProfitLoss = outNetProfitLoss;
	}
	/**
	 * @return Returns the outProfitLoss.
	 */
	public double getNewProfitLoss() {
		return newProfitLoss;
	}
	/**
	 * @param outProfitLoss The outProfitLoss to set.
	 */
	public void setNewProfitLoss(double outProfitLoss) {
		putUsedField("outProfitLoss", outProfitLoss);
		this.newProfitLoss = outProfitLoss;
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
	 * @return Returns the unitNetProfitLoss.
	 */
	public double getUnitNetProfitLoss() {
		return unitNetProfitLoss;
	}
	/**
	 * @param unitNetProfitLoss The unitNetProfitLoss to set.
	 */
	public void setUnitNetProfitLoss(double unitNetProfitLoss) {
		putUsedField("unitNetProfitLoss", unitNetProfitLoss);
		this.unitNetProfitLoss = unitNetProfitLoss;
	}
	/**
	 * @return Returns the unitProfitLoss.
	 */
	public double getUnitProfitLoss() {
		return unitProfitLoss;
	}
	/**
	 * @param unitProfitLoss The unitProfitLoss to set.
	 */
	public void setUnitProfitLoss(double unitProfitLoss) {
		putUsedField("unitProfitLoss", unitProfitLoss);
		this.unitProfitLoss = unitProfitLoss;
	}
	/**
	 * @return Returns the newQuanitity.
	 */
	public double getNewQuanitity() {
		return newQuanitity;
	}
	/**
	 * @param newQuanitity The newQuanitity to set.
	 */
	public void setNewQuanitity(double newQuanitity) {
		putUsedField("newQuanitity", newQuanitity);
		this.newQuanitity = newQuanitity;
	}
}
