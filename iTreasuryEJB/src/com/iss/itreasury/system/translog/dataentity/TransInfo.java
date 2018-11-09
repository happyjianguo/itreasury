package com.iss.itreasury.system.translog.dataentity;

public class TransInfo 
{
	public long TransType=-1;
	public long ActionType=-1;
	
	public String hostip="";
	public String hostname = "";
	
	public long status = -1;
	
	public String searchremark = "";
	public String getHostip() {
		return hostip;
	}
	public void setHostip(String hostip) {
		this.hostip = hostip;
	}
	public String getHostname() {
		return hostname;
	}
	public void setHostname(String hostname) {
		this.hostname = hostname;
	}
	
	public long getActionType() {
		return ActionType;
	}
	public void setActionType(long actionType) {
		ActionType = actionType;
	}
	public long getTransType() {
		return TransType;
	}
	public void setTransType(long transType) {
		TransType = transType;
	}
	public long getStatus() {
		return status;
	}
	public void setStatus(long status) {
		this.status = status;
	}
	public String getSearchremark() {
		return searchremark;
	}
	public void setSearchremark(String searchremark) {
		this.searchremark = searchremark;
	}
	
	

}
