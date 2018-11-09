package com.iss.itreasury.clientmanage.client.dataentity;

import java.sql.Timestamp;

public class QueryLogInfo {
 private long ID=-1;
 private long userID=-1;
 private long officeID=-1;
 private long insertLogTypeName=-1;
 String content="";
 Timestamp executeTime=null;
 private long clientID=-1;
public long getClientID() {
	return clientID;
}
public void setClientID(long clientID) {
	this.clientID = clientID;
}
public String getContent() {
	return content;
}
public void setContent(String content) {
	this.content = content;
}
public Timestamp getExecuteTime() {
	return executeTime;
}
public void setExecuteTime(Timestamp executeTime) {
	this.executeTime = executeTime;
}
public long getID() {
	return ID;
}
public void setID(long id) {
	ID = id;
}
public long getInsertLogTypeName() {
	return insertLogTypeName;
}
public void setInsertLogTypeName(long insertLogTypeName) {
	this.insertLogTypeName = insertLogTypeName;
}
public long getOfficeID() {
	return officeID;
}
public void setOfficeID(long officeID) {
	this.officeID = officeID;
}
public long getUserID() {
	return userID;
}
public void setUserID(long userID) {
	this.userID = userID;
}
}
