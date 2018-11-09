// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   Sys_UserEntity.java

package com.iss.itreasury.system.privilege.dataentity;

import com.iss.itreasury.util.ITreasuryBaseDataEntity;
import java.sql.Timestamp;

public class Sys_UserAuthorityInfo extends ITreasuryBaseDataEntity
{
	private long id = -1; //Ö÷¼üid
	private long userId = -1;
	private long authorizedOfficeId = -1;            
	private long nStatusId = -1;
	private long nInputUserId = -1;
	private Timestamp dtInput = null;
	private long checkUserId = -1;
	private Timestamp dtCheck = null;
	private String sCurrencyId = "";
	private long userResponsibility = -2;
	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
		putUsedField("userId", userId);
	}

	public long getAuthorizedOfficeId() {
		return authorizedOfficeId;
	}

	public void setAuthorizedOfficeId(long authorizedOfficeId) {
		this.authorizedOfficeId = authorizedOfficeId;
		putUsedField("authorizedOfficeId", authorizedOfficeId);
	}

	public long getNStatusId() {
		return nStatusId;
	}

	public void setNStatusId(long statusId) {
		nStatusId = statusId;
		putUsedField("nStatusId", nStatusId);
	}

	public long getnInputUserId() {
		return nInputUserId;
	}

	public void setnInputUserId(long nInputUserId) {
		this.nInputUserId = nInputUserId;
		putUsedField("nInputUserId", nInputUserId);
	}

	public Timestamp getDtInput() {
		return dtInput;
	}

	public void setDtInput(Timestamp dtInput) {
		this.dtInput = dtInput;
		putUsedField("dtInput", dtInput);
	}

	public long getCheckUserId() {
		return checkUserId;
	}

	public void setCheckUserId(long checkUserId) {
		this.checkUserId = checkUserId;
		putUsedField("checkUserId", checkUserId);
	}

	public Timestamp getDtCheck() {
		return dtCheck;
	}

	public void setDtCheck(Timestamp dtCheck) {
		this.dtCheck = dtCheck;
		putUsedField("dtCheck", dtCheck);
	}

	public String getSCurrencyId() {
		return sCurrencyId;
	}

	public void setSCurrencyId(String currencyId) {
		sCurrencyId = currencyId;
		putUsedField("sCurrencyId", sCurrencyId);
	}

	public long getUserResponsibility() {
		return userResponsibility;
	}

	public void setUserResponsibility(long userResponsibility) {
		this.userResponsibility = userResponsibility;
		putUsedField("userResponsibility", userResponsibility);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
		putUsedField("id", id);
	}


}
