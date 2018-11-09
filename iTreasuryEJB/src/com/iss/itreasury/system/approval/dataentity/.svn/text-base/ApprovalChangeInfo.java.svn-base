/*
 * Copyright (c) 1999-2002 ISoftStone. All Rights Reserved.
 *
 * 说明：数据对象
 *
 * 作者：杨帆
 *
 * 更改人员：
 *
 */
package com.iss.itreasury.system.approval.dataentity;

import java.sql.Timestamp;

/**
 *
 * @author  fanyang
 */
public class ApprovalChangeInfo implements java.io.Serializable
{

	private long SerialID = 0; //序号
	private String ModuleName = ""; //模块名称
	private long ModuleID = 0;
	private String LoanTypeName = ""; //业务类型
	private long LoanTypeID = 0;
	private String ActionName = ""; //操作类型
	private long ActionID = 0;
	private long ApprovalID = -1;   //审批设置id
	private long UserID = -1; //用户标示
	private String UserName = ""; //用户名称
	private long NewUserID = -1; //转移的用户ID
	private String NewUserName = ""; //转移的用户名称
	private Timestamp Date = null; //转移时间
	private Timestamp EndDate = null; //截止时间
	private long RecordCount = -1;  //记录数
	private long Level = 0;        //审批级别
	private long TotalLevel = 0;  //审批总级数
	private long IsPass = 0;	   //是否可以越级审批
    private long StatusID = 0;	   //审批设置状态
    
    private String Name = "";	//审批流名称
    private String Desc = "";	//审批流描述

	public void setUserID(long userID)
	{
		UserID = userID;
	}

	public long getUserID()
	{
		return UserID;
	}

	public void setUserName(String userName)
	{
		UserName = userName;
	}

	public String getUserName()
	{
		return UserName;
	}

	public String getActionName()
	{
		return ActionName;
	}

	public void setActionName(String ActionName)
	{
		this.ActionName = ActionName;
	}

	public String getModuleName()
	{
		return ModuleName;
	}

	public void setModuleName(String ModuleName)
	{
		this.ModuleName = ModuleName;
	}

	public String getLoanTypeName()
	{
		return LoanTypeName;
	}

	public void setLoanTypeName(String LoanTypeName)
	{
		this.LoanTypeName = LoanTypeName;
	}

	public long getNewUserID()
	{
		return NewUserID;
	}

	public void setNewUserID(long NewUserID)
	{
		this.NewUserID = NewUserID;
	}

	public String getNewUserName()
	{
		return NewUserName;
	}

	public void setNewUserName(String NewUserName)
	{
		this.NewUserName = NewUserName;
	}

	public long getSerialID()
	{
		return SerialID;
	}

	public void setSerialID(long SerialID)
	{
		this.SerialID = SerialID;
	}

	public Timestamp getDate()
	{
		return Date;
	}

	public void setDate(Timestamp Date)
	{
		this.Date = Date;
	}

	public Timestamp getEndDate() {
		return EndDate;
	}

	public void setEndDate(Timestamp endDate) {
		EndDate = endDate;
	}
	
	public long getRecordCount()
	{
		return RecordCount;
	}

	public void setRecordCount(long RecordCount)
	{
		this.RecordCount = RecordCount;
	}

	public long getLevel()
	{
		return Level;
	}

	public void setLevel(long Level)
	{
		this.Level = Level;
    }

	public long getApprovalID()
	{
		return ApprovalID;
	}

	public void setApprovalID(long ApprovalID)
	{
		this.ApprovalID = ApprovalID;
    }

	public long getIsPass()
	{
		return IsPass;
	}

	public void setIsPass(long IsPass)
	{
		this.IsPass = IsPass;
	}

	public long getTotalLevel()
	{
		return TotalLevel;
	}

	public void setTotalLevel(long TotalLevel)
	{
		this.TotalLevel = TotalLevel;
	}

	public long getStatusID()
	{
		return StatusID;
	}

	public void setStatusID(long StatusID)
	{
		this.StatusID = StatusID;
    }

	public long getActionID()
	{
		return ActionID;
	}

	public void setActionID(long ActionID)
	{
		this.ActionID = ActionID;
	}

	public long getModuleID()
	{
		return ModuleID;
	}

	public void setModuleID(long ModuleID)
	{
		this.ModuleID = ModuleID;
	}

	public long getLoanTypeID()
	{
		return LoanTypeID;
	}

	public void setLoanTypeID(long LoanTypeID)
	{
		this.LoanTypeID = LoanTypeID;
    }

    /**
     * @return Returns the desc.
     */
    public String getDesc()
    {
        return Desc;
    }
    /**
     * @param desc The desc to set.
     */
    public void setDesc(String desc)
    {
        Desc = desc;
    }
    /**
     * @return Returns the name.
     */
    public String getName()
    {
        return Name;
    }
    /**
     * @param name The name to set.
     */
    public void setName(String name)
    {
        Name = name;
    }
}
