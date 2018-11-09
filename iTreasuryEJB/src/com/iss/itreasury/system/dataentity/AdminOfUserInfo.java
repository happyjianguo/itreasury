// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   Sys_UserEntity.java

package com.iss.itreasury.system.dataentity;


import com.iss.itreasury.util.ITreasuryBaseDataEntity;


public class AdminOfUserInfo extends ITreasuryBaseDataEntity
{
	long id = -1;
	long initialUserID = -1;
	long clientID = -1;
	long isBelongToClient = -1;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
		putUsedField("id", id);
	}

	public long getInitialUserID() {
		return initialUserID;
	}
	public void setInitialUserID(long initialUserID) {
		this.initialUserID = initialUserID;
		putUsedField("initialUserID", initialUserID);
	}
	public long getClientID() {
		return clientID;
	}
	public void setClientID(long clientID) {
		this.clientID = clientID;
		putUsedField("clientID", clientID);
	}
	public long getIsBelongToClient() {
		return isBelongToClient;
	}
	public void setIsBelongToClient(long isBelongToClient) {
		this.isBelongToClient = isBelongToClient;
		putUsedField("isBelongToClient", isBelongToClient);
	}


   
}
