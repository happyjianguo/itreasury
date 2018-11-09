/* Generated by Together */

package com.iss.itreasury.bill.query.dataentity;

import java.sql.Timestamp;

import com.iss.itreasury.bill.util.BillDataEntity;

public class QueryBlackListInfo extends BillDataEntity 
{

	private long billTypeID = -1;
	private String startBillCode = "";
	private String endBillCode = "";
	private Timestamp startCreateDate = null;
	private Timestamp endCreateDate = null;
	private Timestamp startMaturityDate = null;
	private Timestamp endMaturityDate = null;
	private String Acceptor = "";
	private double startAmount = 0;
	private double endAmount = 0;
	private long statusID = -1;

	private long desc = -1;
	private String orderParamString = "";
	
	/**
	 * Returns the acceptor.
	 * @return String
	 */
	public String getAcceptor() {
		return Acceptor;
	}

	/**
	 * Returns the billTypeID.
	 * @return long
	 */
	public long getBillTypeID() {
		return billTypeID;
	}

	/**
	 * Returns the desc.
	 * @return long
	 */
	public long getDesc() {
		return desc;
	}

	/**
	 * Returns the endAmount.
	 * @return double
	 */
	public double getEndAmount() {
		return endAmount;
	}

	/**
	 * Returns the endBillCode.
	 * @return String
	 */
	public String getEndBillCode() {
		return endBillCode;
	}

	/**
	 * Returns the endCreateDate.
	 * @return Timestamp
	 */
	public Timestamp getEndCreateDate() {
		return endCreateDate;
	}

	/**
	 * Returns the endMaturityDate.
	 * @return Timestamp
	 */
	public Timestamp getEndMaturityDate() {
		return endMaturityDate;
	}

	/**
	 * Returns the orderParamString.
	 * @return String
	 */
	public String getOrderParamString() {
		return orderParamString;
	}

	/**
	 * Returns the startAmount.
	 * @return double
	 */
	public double getStartAmount() {
		return startAmount;
	}

	/**
	 * Returns the startBillCode.
	 * @return String
	 */
	public String getStartBillCode() {
		return startBillCode;
	}

	/**
	 * Returns the startCreateDate.
	 * @return Timestamp
	 */
	public Timestamp getStartCreateDate() {
		return startCreateDate;
	}

	/**
	 * Returns the startMaturityDate.
	 * @return Timestamp
	 */
	public Timestamp getStartMaturityDate() {
		return startMaturityDate;
	}

	/**
	 * Returns the statusID.
	 * @return long
	 */
	public long getStatusID() {
		return statusID;
	}

	/**
	 * Sets the acceptor.
	 * @param acceptor The acceptor to set
	 */
	public void setAcceptor(String acceptor) {
		Acceptor = acceptor;
	}

	/**
	 * Sets the billTypeID.
	 * @param billTypeID The billTypeID to set
	 */
	public void setBillTypeID(long billTypeID) {
		this.billTypeID = billTypeID;
	}

	/**
	 * Sets the desc.
	 * @param desc The desc to set
	 */
	public void setDesc(long desc) {
		this.desc = desc;
	}

	/**
	 * Sets the endAmount.
	 * @param endAmount The endAmount to set
	 */
	public void setEndAmount(double endAmount) {
		this.endAmount = endAmount;
	}

	/**
	 * Sets the endBillCode.
	 * @param endBillCode The endBillCode to set
	 */
	public void setEndBillCode(String endBillCode) {
		this.endBillCode = endBillCode;
	}

	/**
	 * Sets the endCreateDate.
	 * @param endCreateDate The endCreateDate to set
	 */
	public void setEndCreateDate(Timestamp endCreateDate) {
		this.endCreateDate = endCreateDate;
	}

	/**
	 * Sets the endMaturityDate.
	 * @param endMaturityDate The endMaturityDate to set
	 */
	public void setEndMaturityDate(Timestamp endMaturityDate) {
		this.endMaturityDate = endMaturityDate;
	}

	/**
	 * Sets the orderParamString.
	 * @param orderParamString The orderParamString to set
	 */
	public void setOrderParamString(String orderParamString) {
		this.orderParamString = orderParamString;
	}

	/**
	 * Sets the startAmount.
	 * @param startAmount The startAmount to set
	 */
	public void setStartAmount(double startAmount) {
		this.startAmount = startAmount;
	}

	/**
	 * Sets the startBillCode.
	 * @param startBillCode The startBillCode to set
	 */
	public void setStartBillCode(String startBillCode) {
		this.startBillCode = startBillCode;
	}

	/**
	 * Sets the startCreateDate.
	 * @param startCreateDate The startCreateDate to set
	 */
	public void setStartCreateDate(Timestamp startCreateDate) {
		this.startCreateDate = startCreateDate;
	}

	/**
	 * Sets the startMaturityDate.
	 * @param startMaturityDate The startMaturityDate to set
	 */
	public void setStartMaturityDate(Timestamp startMaturityDate) {
		this.startMaturityDate = startMaturityDate;
	}

	/**
	 * Sets the statusID.
	 * @param statusID The statusID to set
	 */
	public void setStatusID(long statusID) {
		this.statusID = statusID;
	}

}
