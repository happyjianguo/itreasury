/*
 * Created on 2003-10-23
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.settlement.interest.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;


public class InterestRateInfo implements Serializable
{
	private long ID = -1;
	private long serialNo = -1;
	private String name = "";
	private Timestamp effective = null;
	private long inputUserID = -1;
	private Timestamp input = null;
	private double rate = 0.0;
	private long statusID = -1;

	/**
	 * Returns the effective.
	 * @return Timestamp
	 */
	public Timestamp getEffective() {
		return effective;
	}

	/**
	 * Returns the iD.
	 * @return long
	 */
	public long getID() {
		return ID;
	}

	/**
	 * Returns the input.
	 * @return Timestamp
	 */
	public Timestamp getInput() {
		return input;
	}

	/**
	 * Returns the inputUserID.
	 * @return long
	 */
	public long getInputUserID() {
		return inputUserID;
	}

	/**
	 * Returns the name.
	 * @return String
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the rate.
	 * @return double
	 */
	public double getRate() {
		return rate;
	}

	/**
	 * Returns the serialNo.
	 * @return long
	 */
	public long getSerialNo() {
		return serialNo;
	}

	/**
	 * Returns the statusID.
	 * @return long
	 */
	public long getStatusID() {
		return statusID;
	}

	/**
	 * Sets the effective.
	 * @param effective The effective to set
	 */
	public void setEffective(Timestamp effective) {
		this.effective = effective;
	}

	/**
	 * Sets the iD.
	 * @param iD The iD to set
	 */
	public void setID(long iD) {
		ID = iD;
	}

	/**
	 * Sets the input.
	 * @param input The input to set
	 */
	public void setInput(Timestamp input) {
		this.input = input;
	}

	/**
	 * Sets the inputUserID.
	 * @param inputUserID The inputUserID to set
	 */
	public void setInputUserID(long inputUserID) {
		this.inputUserID = inputUserID;
	}

	/**
	 * Sets the name.
	 * @param name The name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Sets the rate.
	 * @param rate The rate to set
	 */
	public void setRate(double rate) {
		this.rate = rate;
	}

	/**
	 * Sets the serialNo.
	 * @param serialNo The serialNo to set
	 */
	public void setSerialNo(long serialNo) {
		this.serialNo = serialNo;
	}

	/**
	 * Sets the statusID.
	 * @param statusID The statusID to set
	 */
	public void setStatusID(long statusID) {
		this.statusID = statusID;
	}

}
