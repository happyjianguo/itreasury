package com.iss.itreasury.archivesmanagement.dataentity;

public class ArchivesManagementInfo {
private long id=-1;
private String name="";
private long userID=-1; 
private long officeID=-1;
private long statusID=-1;
private String remark="";
public long getId() {
	return id;
}
public void setId(long id) {
	this.id = id;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public long getOfficeID() {
	return officeID;
}
public void setOfficeID(long officeID) {
	this.officeID = officeID;
}
public String getRemark() {
	return remark;
}
public void setRemark(String remark) {
	this.remark = remark;
}
public long getStatusID() {
	return statusID;
}
public void setStatusID(long statusID) {
	this.statusID = statusID;
}
public long getUserID() {
	return userID;
}
public void setUserID(long userID) {
	this.userID = userID;
}
}
