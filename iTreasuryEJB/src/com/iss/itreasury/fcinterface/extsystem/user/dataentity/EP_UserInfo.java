package com.iss.itreasury.fcinterface.extsystem.user.dataentity;

import java.sql.Timestamp;

public class EP_UserInfo {
	private long ID=-1;;
    private String sName="";
    private long nExtSystemID=-1;
    private long nInputUserID=-1;;
    private Timestamp dtInputDate=null;
    private long nStatus=-1;
    
	public long getID() {
		return ID;
	}
	public void setID(long id) {
		ID = id;
	}
	public String getSName() {
		return sName;
	}
	public void setSName(String name) {
		sName = name;
	}
	public long getNExtSystemID() {
		return nExtSystemID;
	}
	public void setNExtSystemID(long extSystemID) {
		nExtSystemID = extSystemID;
	}
	public long getNInputUserID() {
		return nInputUserID;
	}
	public void setNInputUserID(long inputUserID) {
		nInputUserID = inputUserID;
	}
	public Timestamp getDtInputDate() {
		return dtInputDate;
	}
	public void setDtInputDate(Timestamp dtInputDate) {
		this.dtInputDate = dtInputDate;
	}
	public long getNStatus() {
		return nStatus;
	}
	public void setNStatus(long status) {
		nStatus = status;
	}
    
    

}
