/**
 * Title:        		iTreasury
 * Description:         
 * Copyright:           Copyright (c) 2003
 * Company:             iSoftStone
 * @author             yehuang 
 * @version
 *  Date of Creation    2004-5-26
 */
package com.iss.itreasury.securities.deliveryorderservice.dataentity;

import com.iss.itreasury.securities.util.SECBaseDataEntity;

/**
 * @author yehuang
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class UsableCreditLineOfSecuritiesInfo extends SECBaseDataEntity {
	
	private double creditLine = 0.0;//授信总额度
	private double cost = 0.0;//库存余额
	private double amountOfApprovedAndUsing = 0.0;//已审批未使用的申请书金额
	private double amountOfUsed = 0.0;//已被部份使用的申请书余额
	private double amount = 0.0;//可用额度
	private long securitiesId = -1;//证券Id
	private long securitiesTypeId = -1;//证券类别Id

	
	
	
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
	 * @return Returns the amountOfApprovedAndUsing.
	 */
	public double getAmountOfApprovedAndUsing() {
		return amountOfApprovedAndUsing;
	}
	/**
	 * @param amountOfApprovedAndUsing The amountOfApprovedAndUsing to set.
	 */
	public void setAmountOfApprovedAndUsing(double amountOfApprovedAndUsing) {
		putUsedField("amountOfApprovedAndUsing", amountOfApprovedAndUsing);
		this.amountOfApprovedAndUsing = amountOfApprovedAndUsing;
	}
	/**
	 * @return Returns the amountOfUsed.
	 */
	public double getAmountOfUsed() {
		return amountOfUsed;
	}
	/**
	 * @param amountOfUsed The amountOfUsed to set.
	 */
	public void setAmountOfUsed(double amountOfUsed) {
		putUsedField("amountOfUsed", amountOfUsed);
		this.amountOfUsed = amountOfUsed;
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
	 * @return Returns the creditLine.
	 */
	public double getCreditLine() {
		return creditLine;
	}
	/**
	 * @param creditLine The creditLine to set.
	 */
	public void setCreditLine(double creditLine) {
		putUsedField("creditLine", creditLine);
		this.creditLine = creditLine;
	}
	/**
	 * @return Returns the securitiesId.
	 */
	public long getSecuritiesId() {
		return securitiesId;
	}
	/**
	 * @param securitiesId The securitiesId to set.
	 */
	public void setSecuritiesId(long securitiesId) {
		putUsedField("securitiesId", securitiesId);
		this.securitiesId = securitiesId;
	}
	/**
	 * @return Returns the securitiesTypeId.
	 */
	public long getSecuritiesTypeId() {
		return securitiesTypeId;
	}
	/**
	 * @param securitiesTypeId The securitiesTypeId to set.
	 */
	public void setSecuritiesTypeId(long securitiesTypeId) {
		putUsedField("securitiesTypeId", securitiesTypeId);
		this.securitiesTypeId = securitiesTypeId;
	}
}
