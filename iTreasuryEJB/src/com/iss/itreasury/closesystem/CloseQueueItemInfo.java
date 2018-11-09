/*
 * Created on 2005-9-13
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.closesystem;

import java.sql.Timestamp;

/**
 * @author gdzhao
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CloseQueueItemInfo {
	/**
	 * @return Returns the applyTime.
	 */
	public Timestamp getApplyTime() {
		return applyTime;
	}
	/**
	 * @param applyTime The applyTime to set.
	 */
	public void setApplyTime(Timestamp applyTime) {
		this.applyTime = applyTime;
	}
	/**
	 * @return Returns the closeSystemStatusID.
	 */
	public long getCloseSystemStatusID() {
		return closeSystemStatusID;
	}
	/**
	 * @param closeSystemStatusID The closeSystemStatusID to set.
	 */
	public void setCloseSystemStatusID(long closeSystemStatusID) {
		this.closeSystemStatusID = closeSystemStatusID;
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
	 * @return Returns the iD.
	 */
	public long getID() {
		return ID;
	}
	/**
	 * @param id The iD to set.
	 */
	public void setID(long id) {
		ID = id;
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
	 * @return Returns the closeSystemStatusName.
	 */
	public String getCloseSystemStatusName() {
		return closeSystemStatusName;
	}
	/**
	 * @param closeSystemStatusName The closeSystemStatusName to set.
	 */
	public void setCloseSystemStatusName(String closeSystemStatusName) {
		this.closeSystemStatusName = closeSystemStatusName;
	}
	/**
	 * @return Returns the currencyName.
	 */
	public String getCurrencyName() {
		return currencyName;
	}
	/**
	 * @param currencyName The currencyName to set.
	 */
	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}
	/**
	 * @return Returns the officeName.
	 */
	public String getOfficeName() {
		return officeName;
	}
	/**
	 * @param officeName The officeName to set.
	 */
	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}
	private long ID=-1;
	private long officeID=-1;
	private String officeName="";
	private long closeSystemStatusID=-1;
	private String closeSystemStatusName="";
	private long currencyID=-1;
	private String currencyName="";
	private Timestamp applyTime=null;
	
}
