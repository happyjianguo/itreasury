/*
 * Created on 2005-3-2
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.configtool.constantmanage.dataentity;

/**
 * @author hyzeng
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class ConstantManageInfo implements java.io.Serializable {

	private long constantID = -1;
	private long officeID = -1;
	private long currencyID = -1;
	private long value = -1;
	private String name = "";
	private boolean check = false;
	private long allOffice = -1;
	private long allForeignCurrency = -1;
	private long allCurrency = -1;

	/**
	 * @return Returns the check.
	 */
	public boolean getCheck() {
		return check;
	}
	/**
	 * @param check The check to set.
	 */
	public void setCheck(boolean check) {
		this.check = check;
	}
	/**
	 * @return Returns the constantID.
	 */
	public long getConstantID() {
		return constantID; 
	}
	/**
	 * @param constantID
	 *            The constantID to set.
	 */
	public void setConstantID(long constantID) {
		this.constantID = constantID;
	}
	/**
	 * @return Returns the currencyID.
	 */
	public long getCurrencyID() {
		return currencyID;
	}
	/**
	 * @param currencyID
	 *            The currencyID to set.
	 */
	public void setCurrencyID(long currencyID) {
		this.currencyID = currencyID;
	}
	/**
	 * @return Returns the officeID.
	 */
	public long getOfficeID() {
		return officeID;
	}
	/**
	 * @param officeID
	 *            The officeID to set.
	 */
	public void setOfficeID(long officeID) {
		this.officeID = officeID;
	}
	/**
	 * @return Returns the value.
	 */
	public long getValue() {
		return value;
	}
	/**
	 * @param value
	 *            The value to set.
	 */
	public void setValue(long value) {
		this.value = value;
	}
	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return Returns the allCurrency.
	 */
	public long getAllCurrency()
	{
		return allCurrency;
	}
	/**
	 * @param allCurrency The allCurrency to set.
	 */
	public void setAllCurrency(long allCurrency)
	{
		this.allCurrency = allCurrency;
	}
	/**
	 * @return Returns the allForeignCurrency.
	 */
	public long getAllForeignCurrency()
	{
		return allForeignCurrency;
	}
	/**
	 * @param allForeignCurrency The allForeignCurrency to set.
	 */
	public void setAllForeignCurrency(long allForeignCurrency)
	{
		this.allForeignCurrency = allForeignCurrency;
	}
	/**
	 * @return Returns the allOffice.
	 */
	public long getAllOffice()
	{
		return allOffice;
	}
	/**
	 * @param allOffice The allOffice to set.
	 */
	public void setAllOffice(long allOffice)
	{
		this.allOffice = allOffice;
	}
}