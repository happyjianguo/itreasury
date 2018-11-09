// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   Sys_GroupEntity.java

package com.iss.itreasury.system.privilege.dataentity;

import com.iss.itreasury.util.ITreasuryBaseDataEntity;
import java.sql.Timestamp;

public class Sys_GroupInfo extends ITreasuryBaseDataEntity
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 2579448245240471149L;
	
	private long Id;
    private String name;
    private long officeID;
    private long moduleID;
    private String rank;
    private long inputUserID;
    private Timestamp inputDate;
    private long nStatusID = -1;
    private long checkUserID =-1;    //复核人
    private Timestamp dtCheck = null;       //复核日期
    private String checkStract = "";//取消复核摘要
    
    

    public Sys_GroupInfo()
    {
        Id = -1L;
        name = "";
        officeID = -1L;
        moduleID = -1L;
        rank = "";
        inputUserID = -1L;
        inputDate = null;
        nStatusID = -1;
        checkUserID = -1;
        dtCheck = null;
        checkStract = "";
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

    public long getModuleID()
    {
        return moduleID;
    }

    public void setModuleID(long moduleID)
    {
        this.moduleID = moduleID;
        putUsedField("moduleID", moduleID);
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
        putUsedField("name", name);
    }

    public long getOfficeID()
    {
        return officeID;
    }

    public void setOfficeID(long officeID)
    {
        this.officeID = officeID;
        putUsedField("officeID", officeID);
    }

    public String getRank()
    {
        return rank;
    }

    public void setRank(String rank)
    {
        this.rank = rank;
        putUsedField("Rank", rank);
    }

    public long getInputUserID()
    {
        return inputUserID;
    }

    public void setInputUserID(long inputUserID)
    {
        this.inputUserID = inputUserID;
        putUsedField("inputUserID", inputUserID);
    }

    public Timestamp getInputDate()
    {
        return inputDate;
    }

    public void setInputDate(Timestamp inputDate)
    {
        this.inputDate = inputDate;
        putUsedField("inputDate", inputDate);
    }
	public String getCheckStract() {
		return checkStract;
	}

	public void setCheckStract(String checkStract) {
		this.checkStract = checkStract;
		putUsedField("SCHECKSTRACT", checkStract);
	}

	public void setStatusID(long statusID){
		this.nStatusID = statusID;
		putUsedField("nStatusID", statusID);
	}
	
	public long getStatusID(){
		return nStatusID;
	}
	
	public long getCheckUserID() {
		return checkUserID;
	}

	public void setCheckUserID(long checkUserID) {
		this.checkUserID = checkUserID;
		putUsedField("CHECKUSERID", checkUserID);
	}

	public Timestamp getDtCheck() {
		return dtCheck;
	}

	public void setDtCheck(Timestamp dtCheck) {
		this.dtCheck = dtCheck;
		putUsedField("DTCHECK", dtCheck);
	}
}
