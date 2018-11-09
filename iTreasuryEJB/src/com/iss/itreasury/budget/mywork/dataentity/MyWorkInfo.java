/*
 * Created on 2004-07-9
 *
 * To change the template for this generated type comment go to 
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.budget.mywork.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;

import com.iss.itreasury.budget.dataentity.BudgetBaseDataEntity;



/**
 * Title:        		iTreasury
 * Description:         
 * Copyright:           Copyright (c) 2003
 * Company:             iSoftStone
 * @author              kewen hu
 * @version
 *  Date of Creation    2004-07-29
 */
public class MyWorkInfo extends BudgetBaseDataEntity implements Serializable {
	//ID	NOT NULL	NUMBER
	private long Id = -1;
	//OFFICEID		NUMBER
	private long officeID = -1;
	//CURRENCYID		NUMBER
	private long currencyID = -1;
	
	private long clientID = -1;

	
	//NEXTCHECKUSERID		NUMBER
	private long nextCheckUserID = -1;
	
	private long inputUserID = -1;
	
	//STATUSID		NUMBER
	private long statusID = -1;



	

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
		this.clientID = clientID;
	}
	/**
	 * @return Returns the currencyID.
	 */
	public long getCurrencyID() {
		return currencyID;
	}
	/**
	 * @param currencyID The currencyID to set.
	 */
	public void setCurrencyID(long currencyID) {
		this.currencyID = currencyID;
	}
	/**
	 * @return Returns the id.
	 */
	public long getId() {
		return Id;
	}
	/**
	 * @param id The id to set.
	 */
	public void setId(long id) {
		Id = id;
	}
	
	/**
	 * @return Returns the nextCheckUserID.
	 */
	public long getNextCheckUserID() {
		return nextCheckUserID;
	}
	/**
	 * @param nextCheckUserID The nextCheckUserID to set.
	 */
	public void setNextCheckUserID(long nextCheckUserID) {
		this.nextCheckUserID = nextCheckUserID;
	}
	/**
	 * @return Returns the officeID.
	 */
	public long getOfficeID() {
		return officeID;
	}
	/**
	 * @param officeID The officeID to set.
	 */
	public void setOfficeID(long officeID) {
		this.officeID = officeID;
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
		this.statusID = statusID;
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
		this.inputUserID = inputUserID;
	}
}
