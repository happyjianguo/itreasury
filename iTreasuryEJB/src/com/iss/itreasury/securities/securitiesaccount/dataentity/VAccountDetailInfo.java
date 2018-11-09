/**
 * Title:        		iTreasury
 * Description:         
 * Copyright:           Copyright (c) 2003
 * Company:             iSoftStone
 * @author             yehuang 
 * @version
 *  Date of Creation    2004-4-2
 */
package com.iss.itreasury.securities.securitiesaccount.dataentity;

import java.sql.Timestamp;

import com.iss.itreasury.securities.util.SECBaseDataEntity;

/**
 * @author yehuang
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class VAccountDetailInfo extends SECBaseDataEntity {
	
	private long deliveryOrderID = -1;
	private String deliveryOrderCode = null;
	private long transactionTypeID = -1;
	private Timestamp transactionDate = null;
	private Timestamp deliveryDate = null;
	private long clientID = -1;
	private long accountID = -1;
	private long direction = -1;
	private double netIncome = 0.0;
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
}
