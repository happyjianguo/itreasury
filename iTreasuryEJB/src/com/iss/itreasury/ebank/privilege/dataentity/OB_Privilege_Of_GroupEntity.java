// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   OB_Privilege_Of_GroupEntity.java

package com.iss.itreasury.ebank.privilege.dataentity;

import com.iss.itreasury.util.ITreasuryBaseDataEntity;

public class OB_Privilege_Of_GroupEntity extends ITreasuryBaseDataEntity
{

    private long Id;
    private long groupID;
    private long privilegeID;
    private long operateType;

    public OB_Privilege_Of_GroupEntity()
    {
        Id = -1L;
        groupID = -1L;
        privilegeID = -1L;
        operateType = -1L;
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

    public long getOperateType()
    {
        return operateType;
    }

    public void setOperateType(long operateType)
    {
        this.operateType = operateType;
        putUsedField("operateType", operateType);
    }

    public long getPrivilegeID()
    {
        return privilegeID;
    }

    public void setPrivilegeID(long privilegeID)
    {
        this.privilegeID = privilegeID;
        putUsedField("privilegeID", privilegeID);
    }
}
