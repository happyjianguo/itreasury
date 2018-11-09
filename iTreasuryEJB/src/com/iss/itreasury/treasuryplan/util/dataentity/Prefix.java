/*
 * Created on 2005-1-13
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.treasuryplan.util.dataentity;

import java.util.Vector;

/**
 * @author ycliu
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Prefix {
	
	private String prefixString;
	private long fromOrTo;
	private Vector accountTypes = new Vector();
	

	/**
	 * @return Returns the fromOrTo.
	 */
	public long getFromOrTo() {
		return fromOrTo;
	}
	/**
	 * @param fromOrTo The fromOrTo to set.
	 */
	public void setFromOrTo(long fromOrTo) {
		this.fromOrTo = fromOrTo;
	}
	/**
	 * @return Returns the prefixString.
	 */
	public String getPrefixString() {
		return prefixString;
	}
	/**
	 * @param prefixString The prefixString to set.
	 */
	public void setPrefixString(String prefixString) {
		this.prefixString = prefixString;
	}
	/**
	 * @return Returns the accountTypes.
	 */
	public Vector getAccountTypes() {
		return accountTypes;
	}
	
	public void addAccountType(String accountTypeId){
		if(accountTypeId!=null)
			accountTypes.add(accountTypeId);
	}
	public static void main(String[] args) {
	}
}
