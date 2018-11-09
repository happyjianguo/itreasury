
/*
 * Copyright (c) 1999-2002 ISoftStone. All Rights Reserved.
 *
 * ˵�������ݶ���
 *
 * ���ߣ�����
 *
 * ������Ա��
 *
 */
package com.iss.itreasury.system.approval.dataentity;

import java.sql.Timestamp;
import java.util.*;

import com.iss.itreasury.util.ITreasuryBaseDataEntity;

/**
 *
 * @author  yfan
 */
public class ApprovalQueryInfo extends ITreasuryBaseDataEntity implements java.io.Serializable {

    private long ID = -1;          			//�������ñ�ʾ
    private long ModuleID = -1;				//ģ���ʾ
	private long LoanTypeID = -1;			//�������ͱ�ʶ
	private long ActionID = -1;				//������ʶ
	private long ActionTypeID = -1;			//�������ͱ�ʶ
	private String sActionName = "";		//�������ƣ��粿�����ƣ�

    private long TotalLevel = 0;   			//������������
    private long Level = 0;					//��ǰ��������
    private long IsPass = 2;				//�Ƿ����Խ������
    private long IsReduplicateAssign = 2;	//�Ƿ�����������ظ�������Ա
    private long IsReduplicateCheck = 2;	//�Ƿ�����ͬһ��Ա��������
    private long LowLevel = 0;				//�����������𣨲��߷���ίԱ�ᣩ
    private long StatusID = 0;				//��������״̬

    private Vector ApprovalUser = new Vector();	    //ѡ����û�����
    private Vector SelectUser = new Vector();	    //�Ѿ�ѡ����û�����
    private Vector NotSelectUser = new Vector();    //�ɹ�ѡ����û�����

    private Vector NextUser = new Vector();         //��һ�������˼���
    
    private long ApprovalUserID = -1;
    private String ApprovalUserName = "";
    private long ApprovalLevel = -1;
    
    //
    private long Id = -1;//û��
    private long inputUserID = -1;//¼����
    private Timestamp startDate = null;//¼������(��)
    private Timestamp endDate = null;//¼������(��)
    private long statusID = -1;//״̬
    //

    public void setID(long iD)
    {
        ID = iD;
    }

    public long getID()
    {
        return ID;
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
     * @return Returns the isReduplicateAssign.
     */
    public long getIsReduplicateAssign()
    {
        return IsReduplicateAssign;
    }
    /**
     * @param isReduplicateAssign The isReduplicateAssign to set.
     */
    public void setIsReduplicateAssign(long isReduplicateAssign)
    {
        IsReduplicateAssign = isReduplicateAssign;
    }
    /**
     * @return Returns the isReduplicateCheck.
     */
    public long getIsReduplicateCheck()
    {
        return IsReduplicateCheck;
    }
    /**
     * @param isReduplicateCheck The isReduplicateCheck to set.
     */
    public void setIsReduplicateCheck(long isReduplicateCheck)
    {
        IsReduplicateCheck = isReduplicateCheck;
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
     * @return Returns the actionName.
     */
    public String getActionName()
    {
        return sActionName;
    }
    /**
     * @param actionName The actionName to set.
     */
    public void setActionName(String actionName)
    {
        sActionName = actionName;
    }
    /**
     * @return Returns the actionTypeID.
     */
    public long getActionTypeID()
    {
        return ActionTypeID;
    }
    /**
     * @param actionTypeID The actionTypeID to set.
     */
    public void setActionTypeID(long actionTypeID)
    {
        ActionTypeID = actionTypeID;
    }
    /**
     * @return Returns the approvalLevel.
     */
    public long getApprovalLevel()
    {
        return ApprovalLevel;
    }
    /**
     * @param approvalLevel The approvalLevel to set.
     */
    public void setApprovalLevel(long approvalLevel)
    {
        ApprovalLevel = approvalLevel;
    }
    /**
     * @return Returns the approvalUserID.
     */
    public long getApprovalUserID()
    {
        return ApprovalUserID;
    }
    /**
     * @param approvalUserID The approvalUserID to set.
     */
    public void setApprovalUserID(long approvalUserID)
    {
        ApprovalUserID = approvalUserID;
    }
    /**
     * @return Returns the approvalUserName.
     */
    public String getApprovalUserName()
    {
        return ApprovalUserName;
    }
    /**
     * @param approvalUserName The approvalUserName to set.
     */
    public void setApprovalUserName(String approvalUserName)
    {
        ApprovalUserName = approvalUserName;
    }
    
    /**
     * @return Returns the endDate.
     */
    public Timestamp getEndDate()
    {
        return endDate;
    }
    /**
     * @param endDate The endDate to set.
     */
    public void setEndDate(Timestamp endDate)
    {
        this.endDate = endDate;
    }
    /**
     * @return Returns the id.
     */
    public long getId()
    {
        return Id;
    }
    /**
     * @param id The id to set.
     */
    public void setId(long id)
    {
        Id = id;
    }
    /**
     * @return Returns the inputUserID.
     */
    public long getInputUserID()
    {
        return inputUserID;
    }
    /**
     * @param inputUserID The inputUserID to set.
     */
    public void setInputUserID(long inputUserID)
    {
        this.inputUserID = inputUserID;
    }
    /**
     * @return Returns the sActionName.
     */
    public String getSActionName()
    {
        return sActionName;
    }
    /**
     * @param actionName The sActionName to set.
     */
    public void setSActionName(String actionName)
    {
        sActionName = actionName;
    }
    /**
     * @return Returns the startDate.
     */
    public Timestamp getStartDate()
    {
        return startDate;
    }
    /**
     * @param startDate The startDate to set.
     */
    public void setStartDate(Timestamp startDate)
    {
        this.startDate = startDate;
    }
}
