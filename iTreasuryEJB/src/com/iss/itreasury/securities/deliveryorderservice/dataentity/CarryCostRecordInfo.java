/**
 * Title:        		iTreasury
 * Description:         
 * Copyright:           Copyright (c) 2003
 * Company:             iSoftStone
 * @author             yehuang 
 * @version
 *  Date of Creation    2004-5-9
 */
package com.iss.itreasury.securities.deliveryorderservice.dataentity;

import java.sql.Timestamp;

import com.iss.itreasury.securities.deliveryorder.dataentity.DeliveryOrderInfo;
import com.iss.itreasury.securities.stock.dataentity.DailyStockInfo;
import com.iss.itreasury.securities.util.SECBaseDataEntity;
import com.iss.itreasury.util.Env;

/**
 * @author yehuang
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class CarryCostRecordInfo extends SECBaseDataEntity {
	private	long	id	       =	-1	;	//	ID
	private	long	clientID	       =	-1	;	//	业务单位ID
	private	long	accountID	       =	-1	;	//	开户资金帐户ID
	private	long	securitiesID	       =	-1	;	//	证券ID
	private	long	deliveryOrderID	       =	-1	;	//	交割单编号ID
	private	long	transactionTypeID	       =	-1	;	//	交易类型ID
	private	Timestamp	deliveryDate	       =	null	;	//	交割日
	private	double	inQuantity	       =	0	;	//	本日买入数量
	private	double	inAmount	       =	0	;	//	本日买入金额
	private	double	outQuantity	       =	0	;	//	本次售出数量
	private	double	outAmount	       =	0	;	//	售出金额
	private	double	carryCostQuantity	       =	0	;	//	结转数量
	private	double	unitCost	       =	0	;	//	单位成本
	private	double	carryCostAmount	       =	0	;	//	结转金额
	private	double	heldQuantity	       =	0	;	//	持仓数量
	private	double	heldAmount	       =	0	;	//	持仓金额
	private	Timestamp	carryCostDate	       =	null	;	//	结转日期
	private	long	inputUserID	       =	-1	;	//	录入人
	private	Timestamp	inputDate	       =	null	;	//	录入时间
								

	public CarryCostRecordInfo(DeliveryOrderInfo doInfo,DailyStockInfo dailyStockInfo){
		this.setInAmount(dailyStockInfo.getInAmount());
		this.setInQuantity(dailyStockInfo.getInQuantity());
		this.setOutAmount(doInfo.getAmount());
		this.setOutQuantity(doInfo.getQuantity());
		this.setCarryCostQuantity(doInfo.getQuantity());
		this.setUnitCost(doInfo.getUnitCost());
		this.setCarryCostAmount(doInfo.getQuantity()*doInfo.getUnitCost());
		this.setHeldAmount(dailyStockInfo.getCost());
		this.setHeldQuantity(dailyStockInfo.getQuantity());
		this.setInputDate(doInfo.getInputDate());
		this.setInputUserID(doInfo.getInputUserId());
		this.setCarryCostDate(Env.getSecuritiesSystemDate(doInfo.getOfficeId(), doInfo.getCurrencyId()));
		this.setDeliveryDate(doInfo.getDeliveryDate());
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
	 * @return Returns the carryCostAmount.
	 */
	public double getCarryCostAmount() {
		return carryCostAmount;
	}
	/**
	 * @param carryCostAmount The carryCostAmount to set.
	 */
	public void setCarryCostAmount(double carryCostAmount) {
		putUsedField("carryCostAmount", carryCostAmount);
		this.carryCostAmount = carryCostAmount;
	}
	/**
	 * @return Returns the carryCostDate.
	 */
	public Timestamp getCarryCostDate() {
		return carryCostDate;
	}
	/**
	 * @param carryCostDate The carryCostDate to set.
	 */
	public void setCarryCostDate(Timestamp carryCostDate) {
		putUsedField("carryCostDate", carryCostDate);
		this.carryCostDate = carryCostDate;
	}
	/**
	 * @return Returns the carryCostQuantity.
	 */
	public double getCarryCostQuantity() {
		return carryCostQuantity;
	}
	/**
	 * @param carryCostQuantity The carryCostQuantity to set.
	 */
	public void setCarryCostQuantity(double carryCostQuantity) {
		putUsedField("carryCostQuantity", carryCostQuantity);
		this.carryCostQuantity = carryCostQuantity;
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
	 * @return Returns the heldAmount.
	 */
	public double getHeldAmount() {
		return heldAmount;
	}
	/**
	 * @param heldAmount The heldAmount to set.
	 */
	public void setHeldAmount(double heldAmount) {
		putUsedField("heldAmount", heldAmount);
		this.heldAmount = heldAmount;
	}
	/**
	 * @return Returns the heldQuantity.
	 */
	public double getHeldQuantity() {
		return heldQuantity;
	}
	/**
	 * @param heldQuantity The heldQuantity to set.
	 */
	public void setHeldQuantity(double heldQuantity) {
		putUsedField("heldQuantity", heldQuantity);
		this.heldQuantity = heldQuantity;
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
	 * @return Returns the inputDate.
	 */
	public Timestamp getInputDate() {
		return inputDate;
	}
	/**
	 * @param inputDate The inputDate to set.
	 */
	public void setInputDate(Timestamp inputDate) {
		putUsedField("inputDate", inputDate);
		this.inputDate = inputDate;
	}
	/**
	 * @return Returns the inputUserID.
	 */
	public long getInputUserID() {
		return inputUserID;
	}
	/**
	 * @param inputUserID The inputUserID to set.
	 */
	public void setInputUserID(long inputUserID) {
		putUsedField("inputUserID", inputUserID);
		this.inputUserID = inputUserID;
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
}
