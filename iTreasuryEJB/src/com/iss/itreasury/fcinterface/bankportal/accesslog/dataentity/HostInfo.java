/*
 * Created on 2007-2-6
 *
 */
package com.iss.itreasury.fcinterface.bankportal.accesslog.dataentity;

/**
 * @author xintan
 *
 */
public class HostInfo {

	private String remoteIP="";
	private String remoteHost="";

	

	/**
	 * @return Returns the remoteHost.
	 */
	public String getRemoteHost() {
		return remoteHost;
	}
	/**
	 * @param remoteHost The remoteHost to set.
	 */
	public void setRemoteHost(String remoteHost) {
		this.remoteHost = remoteHost;
	}
	/**
	 * @return Returns the remoteIP.
	 */
	public String getRemoteIP() {
		return remoteIP;
	}
	/**
	 * @param remoteIP The remoteIP to set.
	 */
	public void setRemoteIP(String remoteIP) {
		this.remoteIP = remoteIP;
	}
}
