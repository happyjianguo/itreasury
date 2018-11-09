/*
 * Created on 2006-11-6
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.ebank.obsystem.dataentity;

/**
 * @author lenovo
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ParentClientInfo implements java.io.Serializable{
	
	private long clientid = -1;//客户id
	private String clientName = "";//客户名称

	/**
	 * @return Returns the clientid.
	 */
	public long getClientid() {
		return clientid;
	}
	/**
	 * @param clientid The clientid to set.
	 */
	public void setClientid(long clientid) {
		this.clientid = clientid;
	}
	/**
	 * @return Returns the clientName.
	 */
	public String getClientName() {
		return clientName;
	}
	/**
	 * @param clientName The clientName to set.
	 */
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
}
