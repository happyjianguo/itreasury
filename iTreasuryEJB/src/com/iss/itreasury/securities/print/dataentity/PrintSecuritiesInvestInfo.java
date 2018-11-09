/*
 * Created on 2004-09-07
 *
 * To change the template for this generated type comment go to 
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.securities.print.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;
 
import com.iss.itreasury.securities.util.SECBaseDataEntity;

/**
 * Title:        		iTreasury
 * Description:         
 * Copyright:           Copyright (c) 2004
 * Company:             iSoftStone
 * @author              kewen hu
 * @version
 *  Date of Creation    2004-09-07
 */
public class PrintSecuritiesInvestInfo extends SECBaseDataEntity implements Serializable {
	//ID
	private long id = -1;
	//类别
	private long transactionTypeID = -1;
	private long typeID = -1;
	//名称
	private String shortName = "";
	//代码
	private String securitiesCode1 = "";
	private String securitiesCode2 = "";
	//单价
	private double unitCost = 0.0;
	//单位净价成本
	private double unitNetCost = 0.0;
	//买入股数（中签股数）
	private double inQuantity = 0.0;
	//申购资金
	private double applyAmount = 0.0;
	//买入金额（中签金额）
	private double inAmount = 0.0;
	//买出金额
	private double outAmount = 0.0;
	//成交净价金额
	private double netPriceAmount = 0.0;
	//已售出数量
	private double outQuantity = 0.0; 
	//已实现盈亏
	private double unitProfitLoss = 0.0;
	//单位净价实际盈亏
	private double unitNetProfitLoss = 0.0;
	//剩余数量
	private double remainQuantity = 0.0;
	//剩余金额
	private double remainAmount = 0.0;
	//现价
	private double currentPrice = 0.0;
	//浮动盈亏
	private double payoff = 0.0;
	//浮动净价盈亏
	private double netPayoff = 0.0;
	//备注
	private String remark = "";

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
	 * @return Returns the currentPrice.
	 */
	public double getCurrentPrice() {
		return currentPrice;
	}
	/**
	 * @param currentPrice The currentPrice to set.
	 */
	public void setCurrentPrice(double currentPrice) {
		putUsedField("currentPrice", currentPrice);
		this.currentPrice = currentPrice;
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
	 * @return Returns the netPayoff.
	 */
	public double getNetPayoff() {
		return netPayoff;
	}
	/**
	 * @param netPayoff The netPayoff to set.
	 */
	public void setNetPayoff(double netPayoff) {
		putUsedField("netPayoff", netPayoff);
		this.netPayoff = netPayoff;
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
	 * @return Returns the payoff.
	 */
	public double getPayoff() {
		return payoff;
	}
	/**
	 * @param payoff The payoff to set.
	 */
	public void setPayoff(double payoff) {
		putUsedField("payoff", payoff);
		this.payoff = payoff;
	}
	/**
	 * @return Returns the remainAmount.
	 */
	public double getRemainAmount() {
		return remainAmount;
	}
	/**
	 * @param remainAmount The remainAmount to set.
	 */
	public void setRemainAmount(double remainAmount) {
		putUsedField("remainAmount", remainAmount);
		this.remainAmount = remainAmount;
	}
	/**
	 * @return Returns the remainQuantity.
	 */
	public double getRemainQuantity() {
		return remainQuantity;
	}
	/**
	 * @param remainQuantity The remainQuantity to set.
	 */
	public void setRemainQuantity(double remainQuantity) {
		putUsedField("remainQuantity", remainQuantity);
		this.remainQuantity = remainQuantity;
	}
	/**
	 * @return Returns the remark.
	 */
	public String getRemark() {
		return remark;
	}
	/**
	 * @param remark The remark to set.
	 */
	public void setRemark(String remark) {
		putUsedField("remark", remark);
		this.remark = remark;
	}
	/**
	 * @return Returns the securitiesCode1.
	 */
	public String getSecuritiesCode1() {
		return securitiesCode1;
	}
	/**
	 * @param securitiesCode1 The securitiesCode1 to set.
	 */
	public void setSecuritiesCode1(String securitiesCode1) {
		putUsedField("securitiesCode1", securitiesCode1);
		this.securitiesCode1 = securitiesCode1;
	}
	/**
	 * @return Returns the securitiesCode2.
	 */
	public String getSecuritiesCode2() {
		return securitiesCode2;
	}
	/**
	 * @param securitiesCode2 The securitiesCode2 to set.
	 */
	public void setSecuritiesCode2(String securitiesCode2) {
		putUsedField("securitiesCode2", securitiesCode2);
		this.securitiesCode2 = securitiesCode2;
	}
	/**
	 * @return Returns the shortName.
	 */
	public String getShortName() {
		return shortName;
	}
	/**
	 * @param shortName The shortName to set.
	 */
	public void setShortName(String shortName) {
		putUsedField("shortName", shortName);
		this.shortName = shortName;
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
	 * @return Returns the applyAmount.
	 */
	public double getApplyAmount() {
		return applyAmount;
	}
	/**
	 * @param applyAmount The applyAmount to set.
	 */
	public void setApplyAmount(double applyAmount) {
		putUsedField("applyAmount", applyAmount);
		this.applyAmount = applyAmount;
	}
	/**
	 * @return Returns the typeID.
	 */
	public long getTypeID() {
		return typeID;
	}
	/**
	 * @param typeID The typeID to set.
	 */
	public void setTypeID(long typeID) {
		putUsedField("typeID", typeID);
		this.typeID = typeID;
	}
}