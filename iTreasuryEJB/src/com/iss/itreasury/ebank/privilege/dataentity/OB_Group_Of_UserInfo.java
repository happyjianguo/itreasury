// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   Sys_Group_Of_UserEntity.java

package com.iss.itreasury.ebank.privilege.dataentity;

import com.iss.itreasury.util.ITreasuryBaseDataEntity;

public class OB_Group_Of_UserInfo extends ITreasuryBaseDataEntity
{

    private long Id;
    private long userID;
    private long groupID;
    private long clientID =-1;

    public OB_Group_Of_UserInfo()
    {
        Id = -1L;
        userID = -1L;
        groupID = -1L;
    }

    public long getGroupID()
    {
        return groupID;
    }

    public void setGroupID(long groupID)
    {
        this.groupID = groupID;
        putUsedField("groupID", groupID);
    }

    public long getId()
    {
        return Id;
    }

    public void setId(long id)
    {
        Id = id;
        putUsedField("Id", id);
    }

    public long getUserID()
    {
        return userID;
    }

    public void setUserID(long userID)
    {
        this.userID = userID;
        putUsedField("userID", userID);
    }

	public long getClientID() {
		return clientID;
	}

	public void setClientID(long clientID) {
		this.clientID = clientID;
		putUsedField("clientID", clientID);
	}
}
