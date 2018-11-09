package com.iss.itreasury.settlement.integration.client.info;

import java.sql.Timestamp;

import com.iss.itreasury.settlement.integration.client.constant.ResultStatus;

public class ImpAccountResultItem implements java.io.Serializable {
	private long code=ResultStatus.FAIL;
	private String msg="";
	private String transNoOfBank="";
	private Timestamp systemDate = null;
	
	public ImpAccountResultItem(long impResultCode,String message,String transNo,Timestamp openDate){
		this.code=impResultCode;
		this.msg=message;
		this.transNoOfBank=transNo;
		this.systemDate=openDate;
	}
	public String getMessage(){
		return msg;
	}
	public long getImpResultCode(){
		return code;
	}
	public String getTransNoOfBank() {
		return transNoOfBank;
	}
	public Timestamp getSystemDate() {
		return systemDate;
	}
}
