// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   Sys_UserEntity.java

package com.iss.itreasury.system.privilege.dataentity;

import com.iss.itreasury.util.ITreasuryBaseDataEntity;
import java.sql.Timestamp;

public class QueryOfficeInfo extends ITreasuryBaseDataEntity
{

	private long id = -1;
	private long lUserID = -1;
	private long lModelID = -1;
	private long lOfficeID = -1;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getLUserID() {
		return lUserID;
	}
	public void setLUserID(long userID) {
		lUserID = userID;
	}
	public long getLModelID() {
		return lModelID;
	}
	public void setLModelID(long modelID) {
		lModelID = modelID;
	}
	public long getLOfficeID() {
		return lOfficeID;
	}
	public void setLOfficeID(long officeID) {
		lOfficeID = officeID;
	}


}
