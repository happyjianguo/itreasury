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

import com.iss.itreasury.util.ITreasuryBaseDataEntity;

/**
 *
 * @author  yfan
 */
public class ApprovalTracingInfo extends ITreasuryBaseDataEntity implements java.io.Serializable {

    private long ID = -1;          			//标示
	private long ApprovalID = -1;           //审批设置标识
    private long ModuleID = -1;				//模块标示
	private long LoanTypeID = -1;			//贷款类型标识
	private long ActionID = -1;				//操作标识
	
	private long OfficeID = -1;				//办事处ID
    private long CurrencyID = -1;			//币种ID

    private long ApprovalContentID = -1;    //审批内容标识
    private long[] ApprovalContentIDList;	//批量审核ID列表
    private long SerialID = -1;             //序列号
    private long UserID = -1;               //审批人标示
	private String UserName = "";           //审批人姓名
	private long Level = -1;				//审批级别
    private long NextUserID = -1;           //下一个审批人标示
    private long NextLevel = -1;            //下一个审批级别
    private String Opinion = "";            //审批意见
    private Timestamp ApprovalDate = null;  //审批时间
    private long ResultID = -1;             //审批结果标示
    private long IsFinish = -1;             //是否审批结束（1：是；2：否）
    private long StatusID = -1;				//审批记录状态（0：无效；1：有效）
    
    private long CheckActionID = -1;		//审批操作标示（通过；拒绝；返回修改）
    private long InputUserID = -1;			//录入人标示

    public void setID(long iD)
    {
        ID = iD;
    }

    public long getID()
    {
        return ID;
    }

    public void setApprovalID(long approvalID)
    {
        ApprovalID = approvalID;
    }

    public long getApprovalID()
    {
        return ApprovalID;
    }

    public void setModuleID(long moduleID)
    {
        ModuleID = moduleID;
    }

    public long getModuleID()
    {
        return ModuleID;
    }

    public void setLoanTypeID(long loanTypeID)
    {
        LoanTypeID = loanTypeID;
    }

    public long getLoanTypeID()
    {
        return LoanTypeID;
    }

    public void setActionID(long actionID)
    {
        ActionID = actionID;
    }

    public long getActionID()
    {
        return ActionID;
    }

    public void setApprovalContentID(long approvalContentID)
    {
        ApprovalContentID = approvalContentID;
    }

    public long getApprovalContentID()
    {
        return ApprovalContentID;
    }

    public void setSerialID(long serialID)
    {
        SerialID = serialID;
    }

    public long getSerialID()
    {
        return SerialID;
    }

    public void setUserID(long userID)
    {
        UserID = userID;
    }

    public long getUserID()
    {
        return UserID;
    }

    public void setNextUserID(long nextUserID)
    {
        NextUserID = nextUserID;
    }

    public long getNextUserID()
    {
        return NextUserID;
    }

    public void setOpinion(String opinion)
    {
        Opinion = opinion;
    }

    public String getOpinion()
    {
        return Opinion;
    }

    public void setApprovalDate(Timestamp approvalDate)
    {
        ApprovalDate = approvalDate;
    }

    public Timestamp getApprovalDate()
    {
        return ApprovalDate;
    }

    public void setResultID(long resultID)
    {
        ResultID = resultID;
    }

    public long getResultID()
    {
        return ResultID;
    }

    public void setIsFinish(long isFinish)
    {
        IsFinish = isFinish;
    }

    public long getIsFinish()
    {
        return IsFinish;
    }

    public void setStatusID(long statusID)
    {
        StatusID = statusID;
    }

    public long getStatusID()
    {
        return StatusID;
    }

	public String getUserName()
	{
		return UserName;
	}

	public void setUserName(String UserName)
	{
		this.UserName = UserName;
	}

    /**
     * @return Returns the checkActionID.
     */
    public long getCheckActionID() {
        return CheckActionID;
    }
    
    /**
     * @param checkActionID The checkActionID to set.
     */
    public void setCheckActionID(long checkActionID) {
        CheckActionID = checkActionID;
    }
    /**
     * @return Returns the inputUserID.
     */
    public long getInputUserID() {
        return InputUserID;
    }
    /**
     * @param inputUserID The inputUserID to set.
     */
    public void setInputUserID(long inputUserID) {
        InputUserID = inputUserID;
    }
    /**
     * @return Returns the iDList.
     */
    public long[] getApprovalContentIDList() {
        return ApprovalContentIDList;
    }
    /**
     * @param list The iDList to set.
     */
    public void setApprovalContentIDList(long[] list) {
        ApprovalContentIDList = list;
    }
    /**
     * @return Returns the nextLevel.
     */
    public long getNextLevel()
    {
        return NextLevel;
    }
    /**
     * @param nextLevel The nextLevel to set.
     */
    public void setNextLevel(long nextLevel)
    {
        NextLevel = nextLevel;
    }
    /**
     * @return Returns the level.
     */
    public long getLevel()
    {
        return Level;
    }
    /**
     * @param level The level to set.
     */
    public void setLevel(long level)
    {
        Level = level;
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

    public long getId() {return 0;}
    public void setId(long id) {}
}
