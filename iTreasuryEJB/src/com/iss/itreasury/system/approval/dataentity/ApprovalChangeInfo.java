/*
 * Copyright (c) 1999-2002 ISoftStone. All Rights Reserved.
 *
 * ˵�������ݶ���
 *
 * ���ߣ��
 *
 * ������Ա��
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

	private long SerialID = 0; //���
	private String ModuleName = ""; //ģ������
	private long ModuleID = 0;
	private String LoanTypeName = ""; //ҵ������
	private long LoanTypeID = 0;
	private String ActionName = ""; //��������
	private long ActionID = 0;
	private long ApprovalID = -1;   //��������id
	private long UserID = -1; //�û���ʾ
	private String UserName = ""; //�û�����
	private long NewUserID = -1; //ת�Ƶ��û�ID
	private String NewUserName = ""; //ת�Ƶ��û�����
	private Timestamp Date = null; //ת��ʱ��
	private Timestamp EndDate = null; //��ֹʱ��
	private long RecordCount = -1;  //��¼��
	private long Level = 0;        //��������
	private long TotalLevel = 0;  //�����ܼ���
	private long IsPass = 0;	   //�Ƿ����Խ������
    private long StatusID = 0;	   //��������״̬
    
    private String Name = "";	//����������
    private String Desc = "";	//����������

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
