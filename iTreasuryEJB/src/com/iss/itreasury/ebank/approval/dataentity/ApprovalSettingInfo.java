
/*
 * Copyright (c) 1999-2002 ISoftStone. All Rights Reserved.
 *
 * 说明：数据对象
 *
 * 作者：樊羿
 *
 * 更改人员：
 *
 */
package com.iss.itreasury.ebank.approval.dataentity;

import java.sql.Timestamp;
import java.util.Vector;

import com.iss.itreasury.util.ITreasuryBaseDataEntity;

/**
 *
 * @author  yfan
 */
public class ApprovalSettingInfo extends ITreasuryBaseDataEntity implements java.io.Serializable {

    private long ID = -1;          			//审批设置标示
    private String Name = "";	//审批流名称
    private String Desc = "";	//审批流描述
    private long InputUserID = -1;//录入人
    private Timestamp InputDate = null;//录入时间
    
    private long TotalLevel = 0;   			//审批级别总数
    private long Level = 0;					//当前审批级别
    private long IsPass = 2;				//是否可以越级审批
    
    private long LowLevel = 0;				//最少审批级别（不走风险委员会）
    private long StatusID = 0;				//审批设置状态

    private long OfficeID = -1;				//办事处ID
    private long CurrencyID = -1;			//币种ID
    
    private Vector ApprovalUser = new Vector();	    //选择的用户集合
    private Vector SelectUser = new Vector();	    //已经选择的用户集合
    private Vector NotSelectUser = new Vector();    //可供选择的用户集合

    private Vector NextUser = new Vector();         //下一级审批人集合

    public void setID(long iD)
    {
        ID = iD;
    }

    public long getID()
    {
        return ID;
    }
    public void setId(long iD)
    {
        ID = iD;
    }

    public long getId()
    {
        return ID;
    }
    

    public void setTotalLevel(long totalLevel)
    {
        TotalLevel = totalLevel;
    }

    public long getTotalLevel()
    {
        return TotalLevel;
    }

    public void setLevel(long level)
    {
        Level = level;
    }

    public long getLevel()
    {
        return Level;
    }

    public void setIsPass(long isPass)
    {
        IsPass = isPass;
    }

    public long getIsPass()
    {
        return IsPass;
    }

    public void setStatusID(long statusID)
    {
        StatusID = statusID;
    }

    public long getStatusID()
    {
        return StatusID;
    }

    public void setApprovalUser(Vector approvalUser)
    {
        ApprovalUser = approvalUser;
    }

    public Vector getApprovalUser()
    {
        return ApprovalUser;
    }

    public void setSelectUser(Vector selectUser)
    {
        SelectUser = selectUser;
    }

    public Vector getSelectUser()
    {
        return SelectUser;
    }

    public void setNotSelectUser(Vector notSelectUser)
    {
        NotSelectUser = notSelectUser;
    }

    public Vector getNotSelectUser()
    {
        return NotSelectUser;
    }

    public void setNextUser(Vector nextUser)
    {
        NextUser = nextUser;
    }

    public Vector getNextUser()
    {
        return NextUser;
    }    
    /**
     * @return Returns the lowLevel.
     */
    public long getLowLevel()
    {
        return LowLevel;
    }
    /**
     * @param lowLevel The lowLevel to set.
     */
    public void setLowLevel(long lowLevel)
    {
        LowLevel = lowLevel;
    }    
    /**
     * @return Returns the currencyID.
     */
    public long getCurrencyID()
    {
        return CurrencyID;
    }
    /**
     * @param currencyID The currencyID to set.
     */
    public void setCurrencyID(long currencyID)
    {
        CurrencyID = currencyID;
    }
    /**
     * @return Returns the officeID.
     */
    public long getOfficeID()
    {
        return OfficeID;
    }
    /**
     * @param officeID The officeID to set.
     */
    public void setOfficeID(long officeID)
    {
        OfficeID = officeID;
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
    /**
     * @return Returns the inputDate.
     */
    public Timestamp getInputDate()
    {
        return InputDate;
    }
    /**
     * @param inputDate The inputDate to set.
     */
    public void setInputDate(Timestamp inputDate)
    {
        InputDate = inputDate;
    }
    /**
     * @return Returns the inputUserID.
     */
    public long getInputUserID()
    {
        return InputUserID;
    }
    /**
     * @param inputUserID The inputUserID to set.
     */
    public void setInputUserID(long inputUserID)
    {
        InputUserID = inputUserID;
    }
}
