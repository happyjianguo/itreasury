package com.iss.itreasury.loan.aftercredit.dataentity;

import java.sql.Timestamp;

public class AfterCreditSearchInfo {
String clientFrom="";
String clientTo="";
String clientGrade="";
String alarmDateFrom;
String alarmDateTo;
String alarmCodeFrom="";
String alarmCodeTo="";
long status;

public String getAlarmCodeFrom() { 
	return alarmCodeFrom;
}
public void setAlarmCodeFrom(String alarmCodeFrom) {
	this.alarmCodeFrom = alarmCodeFrom;
}
public String getAlarmCodeTo() {
	return alarmCodeTo;
}
public void setAlarmCodeTo(String alarmCodeTo) {
	this.alarmCodeTo = alarmCodeTo;
}

public String getClientFrom() {
	return clientFrom;
}
public void setClientFrom(String clientFrom) {
	this.clientFrom = clientFrom;
}
public String getClientGrade() {
	return clientGrade;
}
public void setClientGrade(String clientGrade) {
	this.clientGrade = clientGrade;
}
public String getClientTo() {
	return clientTo;
}
public void setClientTo(String clientTo) {
	this.clientTo = clientTo;
}
public long getStatus() {
	return status;
}
public void setStatus(long status) {
	this.status = status;
}
public String getAlarmDateFrom() {
	return alarmDateFrom;
}
public void setAlarmDateFrom(String alarmDateFrom) {
	this.alarmDateFrom = alarmDateFrom;
}
public String getAlarmDateTo() {
	return alarmDateTo;
}
public void setAlarmDateTo(String alarmDateTo) {
	this.alarmDateTo = alarmDateTo;
}
}
