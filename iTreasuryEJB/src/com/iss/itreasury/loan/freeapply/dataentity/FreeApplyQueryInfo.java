/*
 * Created on 2004-8-3
 *
 * Title:				iTreasury
 * @author             	ninh
 * Company:             iSoftStone Copyright: Copyright (c) 2003
 * @version
 * Description:         
 */

package com.iss.itreasury.loan.freeapply.dataentity;

import java.sql.Timestamp;

import com.iss.itreasury.loan.base.LoanBaseDataEntity;

/**
 * @author ninh
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class FreeApplyQueryInfo extends LoanBaseDataEntity 
{
	private long currencyId = -1;//
	private long officeId = -1;//
	private int  type=-1;
	private long userID=-1;
	private long actionID=-1;
	private long contractIDFrom=-1;
	private long contractIDTo=-1;
	private long clientID=-1;
	private double amountFrom=0;
	private double amountTo=0;
	private Timestamp tsFrom=null;
	private Timestamp tsTo=null;
	private long intervalNum=-1;
	private long statusID=-1;
	private long pageLineCount=10;
	private long pageNo=1;
	private long orderParam=1;
	private long desc = 1;
	
	/**
	 * Returns the actionID.
	 * @return long
	 */
	public long getActionID() {
		return actionID;
	}

	/**
	 * Returns the amountFrom.
	 * @return double
	 */
	public double getAmountFrom() {
		return amountFrom;
	}

	/**
	 * Returns the amountTo.
	 * @return double
	 */
	public double getAmountTo() {
		return amountTo;
	}

	/**
	 * Returns the clientID.
	 * @return long
	 */
	public long getClientID() {
		return clientID;
	}

	/**
	 * Returns the contractIDFrom.
	 * @return long
	 */
	public long getContractIDFrom() {
		return contractIDFrom;
	}

	/**
	 * Returns the contractIDTo.
	 * @return long
	 */
	public long getContractIDTo() {
		return contractIDTo;
	}

	/**
	 * Returns the currencyId.
	 * @return long
	 */
	public long getCurrencyId() {
		return currencyId;
	}

	/**
	 * Returns the desc.
	 * @return long
	 */
	public long getDesc() {
		return desc;
	}

	/**
	 * Returns the intervalNum.
	 * @return long
	 */
	public long getIntervalNum() {
		return intervalNum;
	}

	/**
	 * Returns the officeId.
	 * @return long
	 */
	public long getOfficeId() {
		return officeId;
	}

	/**
	 * Returns the orderParam.
	 * @return long
	 */
	public long getOrderParam() {
		return orderParam;
	}

	/**
	 * Returns the pageLineCount.
	 * @return long
	 */
	public long getPageLineCount() {
		return pageLineCount;
	}

	/**
	 * Returns the pageNo.
	 * @return long
	 */
	public long getPageNo() {
		return pageNo;
	}

	/**
	 * Returns the statusID.
	 * @return long
	 */
	public long getStatusID() {
		return statusID;
	}

	/**
	 * Returns the tsFrom.
	 * @return Timestamp
	 */
	public Timestamp getTsFrom() {
		return tsFrom;
	}

	/**
	 * Returns the tsTo.
	 * @return Timestamp
	 */
	public Timestamp getTsTo() {
		return tsTo;
	}

	/**
	 * Returns the type.
	 * @return int
	 */
	public int getType() {
		return type;
	}

	/**
	 * Returns the userID.
	 * @return long
	 */
	public long getUserID() {
		return userID;
	}

	/**
	 * Sets the actionID.
	 * @param actionID The actionID to set
	 */
	public void setActionID(long actionID) {
		this.actionID = actionID;
	}

	/**
	 * Sets the amountFrom.
	 * @param amountFrom The amountFrom to set
	 */
	public void setAmountFrom(double amountFrom) {
		this.amountFrom = amountFrom;
	}

	/**
	 * Sets the amountTo.
	 * @param amountTo The amountTo to set
	 */
	public void setAmountTo(double amountTo) {
		this.amountTo = amountTo;
	}

	/**
	 * Sets the clientID.
	 * @param clientID The clientID to set
	 */
	public void setClientID(long clientID) {
		this.clientID = clientID;
	}

	/**
	 * Sets the contractIDFrom.
	 * @param contractIDFrom The contractIDFrom to set
	 */
	public void setContractIDFrom(long contractIDFrom) {
		this.contractIDFrom = contractIDFrom;
	}

	/**
	 * Sets the contractIDTo.
	 * @param contractIDTo The contractIDTo to set
	 */
	public void setContractIDTo(long contractIDTo) {
		this.contractIDTo = contractIDTo;
	}

	/**
	 * Sets the currencyId.
	 * @param currencyId The currencyId to set
	 */
	public void setCurrencyId(long currencyId) {
		this.currencyId = currencyId;
	}

	/**
	 * Sets the desc.
	 * @param desc The desc to set
	 */
	public void setDesc(long desc) {
		this.desc = desc;
	}

	/**
	 * Sets the intervalNum.
	 * @param intervalNum The intervalNum to set
	 */
	public void setIntervalNum(long intervalNum) {
		this.intervalNum = intervalNum;
	}

	/**
	 * Sets the officeId.
	 * @param officeId The officeId to set
	 */
	public void setOfficeId(long officeId) {
		this.officeId = officeId;
	}

	/**
	 * Sets the orderParam.
	 * @param orderParam The orderParam to set
	 */
	public void setOrderParam(long orderParam) {
		this.orderParam = orderParam;
	}

	/**
	 * Sets the pageLineCount.
	 * @param pageLineCount The pageLineCount to set
	 */
	public void setPageLineCount(long pageLineCount) {
		this.pageLineCount = pageLineCount;
	}

	/**
	 * Sets the pageNo.
	 * @param pageNo The pageNo to set
	 */
	public void setPageNo(long pageNo) {
		this.pageNo = pageNo;
	}

	/**
	 * Sets the statusID.
	 * @param statusID The statusID to set
	 */
	public void setStatusID(long statusID) {
		this.statusID = statusID;
	}

	/**
	 * Sets the tsFrom.
	 * @param tsFrom The tsFrom to set
	 */
	public void setTsFrom(Timestamp tsFrom) {
		this.tsFrom = tsFrom;
	}

	/**
	 * Sets the tsTo.
	 * @param tsTo The tsTo to set
	 */
	public void setTsTo(Timestamp tsTo) {
		this.tsTo = tsTo;
	}

	/**
	 * Sets the type.
	 * @param type The type to set
	 */
	public void setType(int type) {
		this.type = type;
	}

	/**
	 * Sets the userID.
	 * @param userID The userID to set
	 */
	public void setUserID(long userID) {
		this.userID = userID;
	}

}
