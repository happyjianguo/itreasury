/*
 * Copyright (c) 1999-2002 ISoftStone. All Rights Reserved.
 *
 * 说明：网银计划版本信息
 *
 * 作者：张颜
 *
 * 更改人员：
 *
 */
package com.iss.itreasury.loan.obinterface.dataentity;

import java.beans.*;
import java.sql.*;

/**
 *
 * @author  ninghao
 */
public class OBPlanInfo implements java.io.Serializable
{

	private String InstructionNo; //指令号
	private long ID; //标识
	private long LoanID; //只有第一个版本关联申请书
	private String LoanCode;
	private long ContractID; //合同标识
	private String ContractCode;
	private long PlanVersion; //计划版本号
    private long InPlanID; //内部计划ID
    private long InPlanDetailID; //内部计划版本细节ID
	private long InputUserID; //录入用户标识
	private Timestamp Input; //录入日期
	private long CheckUserID; //复核用户标识
	private Timestamp Check; //复核日期
	private long StatusID; //状态标识
	private String InputUserName; // 提交人名称
	private String CheckUserName; // 复核人名称
	private long CheckNum;
	private long IsUsed; //是否在使用中  1--是   2--否
	private long UserType; //使用来源
    private long HandleUserID; //复核用户标识

	private long Count; // 记录数
	private long PageCount; // Page Count
	private long Desc;
	private String OrderParam;
	private long PageNo;

        //2003-4-2
        private long LastVersionCheck;        //判断上一个版本是否被复核  1－复核  0－未复核（提交）

        /**
         * Returns the LastVersionCheck.
         * @return long
         */
        public long getLastVersionCheck()
        {
                return LastVersionCheck;
        }


	/**
	 * Returns the check.
	 * @return Timestamp
	 */
	public Timestamp getCheck()
	{
		return Check;
	}

	/**
	 * Returns the checkNum.
	 * @return long
	 */
	public long getCheckNum()
	{
		return CheckNum;
	}

	/**
	 * Returns the checkUserID.
	 * @return long
	 */
	public long getCheckUserID()
	{
		return CheckUserID;
	}

	/**
	 * Returns the checkUserName.
	 * @return String
	 */
	public String getCheckUserName()
	{
		if (CheckUserName == null)
		{
			CheckUserName = "";
		}
		return CheckUserName;
	}

	/**
	 * Returns the contractID.
	 * @return long
	 */
	public long getContractID()
	{
		return ContractID;
	}

	/**
	 * Returns the count.
	 * @return long
	 */
	public long getCount()
	{
		return Count;
	}

	/**
	 * Returns the iD.
	 * @return long
	 */
	public long getID()
	{
		return ID;
	}

	/**
	 * Returns the input.
	 * @return Timestamp
	 */
	public Timestamp getInput()
	{
		return Input;
	}

	/**
	 * Returns the inputUserID.
	 * @return long
	 */
	public long getInputUserID()
	{
		return InputUserID;
	}

	/**
	 * Returns the inputUserName.
	 * @return String
	 */
	public String getInputUserName()
	{
		if (InputUserName == null)
		{
			InputUserName = "";
		}
		return InputUserName;
	}

	/**
	 * Returns the isUsed.
	 * @return long
	 */
	public long getIsUsed()
	{
		//注意：网银Notes中此常量与一期不同
		//public static final long CODE_YESORNO_TYPE_NO=0;// 否
    	//public static final long CODE_YESORNO_TYPE_YES=1;// 是
    	//Notes中的常量是否
    	//public static final long CODE_YESORNO_YES=1;//是
    	//public static final long CODE_YESORNO_NO=2;//否
		if (IsUsed == 0)
		{
			IsUsed = 2;
		}
		else if (IsUsed == 1)
		{
			IsUsed = 1;
		}
		return IsUsed;
	}

	/**
	 * Returns the loanID.
	 * @return long
	 */
	public long getLoanID()
	{
		return LoanID;
	}

	/**
	 * Returns the pageCount.
	 * @return long
	 */
	public long getPageCount()
	{
		return PageCount;
	}

	/**
	 * Returns the planVersion.
	 * @return long
	 */
	public long getPlanVersion()
	{
		return PlanVersion;
	}

	/**
	 * Returns the statusID.
	 * @return long
	 */
	public long getStatusID()
	{
		return StatusID;
	}

	/**
	 * Returns the userType.
	 * @return long
	 */
	public long getUserType()
	{
		return UserType;
	}

        /**
         * Sets the LastVersionCheck.
         * @param lastVersionCheck The check to set
         */
        public void setLastVersionCheck(long lastVersionCheck)
        {
                LastVersionCheck = lastVersionCheck;
        }


	/**
	 * Sets the check.
	 * @param check The check to set
	 */
	public void setCheck(Timestamp check)
	{
		Check = check;
	}

	/**
	 * Sets the checkNum.
	 * @param checkNum The checkNum to set
	 */
	public void setCheckNum(long checkNum)
	{
		CheckNum = checkNum;
	}

	/**
	 * Sets the checkUserID.
	 * @param checkUserID The checkUserID to set
	 */
	public void setCheckUserID(long checkUserID)
	{
		CheckUserID = checkUserID;
	}

	/**
	 * Sets the checkUserName.
	 * @param checkUserName The checkUserName to set
	 */
	public void setCheckUserName(String checkUserName)
	{
		CheckUserName = checkUserName;
	}

	/**
	 * Sets the contractID.
	 * @param contractID The contractID to set
	 */
	public void setContractID(long contractID)
	{
		ContractID = contractID;
	}

	/**
	 * Sets the count.
	 * @param count The count to set
	 */
	public void setCount(long count)
	{
		Count = count;
	}

	/**
	 * Sets the iD.
	 * @param iD The iD to set
	 */
	public void setID(long iD)
	{
		ID = iD;
	}

	/**
	 * Sets the input.
	 * @param input The input to set
	 */
	public void setInput(Timestamp input)
	{
		Input = input;
	}

	/**
	 * Sets the inputUserID.
	 * @param inputUserID The inputUserID to set
	 */
	public void setInputUserID(long inputUserID)
	{
		InputUserID = inputUserID;
	}

	/**
	 * Sets the inputUserName.
	 * @param inputUserName The inputUserName to set
	 */
	public void setInputUserName(String inputUserName)
	{
		InputUserName = inputUserName;
	}

	/**
	 * Sets the isUsed.
	 * @param isUsed The isUsed to set
	 */
	public void setIsUsed(long isUsed)
	{
		IsUsed = isUsed;
	}

	/**
	 * Sets the loanID.
	 * @param loanID The loanID to set
	 */
	public void setLoanID(long loanID)
	{
		LoanID = loanID;
	}

	/**
	 * Sets the pageCount.
	 * @param pageCount The pageCount to set
	 */
	public void setPageCount(long pageCount)
	{
		PageCount = pageCount;
	}

	/**
	 * Sets the planVersion.
	 * @param planVersion The planVersion to set
	 */
	public void setPlanVersion(long planVersion)
	{
		PlanVersion = planVersion;
	}

	/**
	 * Sets the statusID.
	 * @param statusID The statusID to set
	 */
	public void setStatusID(long statusID)
	{
		StatusID = statusID;
	}

	/**
	 * Sets the userType.
	 * @param userType The userType to set
	 */
	public void setUserType(long userType)
	{
		UserType = userType;
	}

	/**
	 * Returns the contractCode.
	 * @return String
	 */
	public String getContractCode()
	{
		if (ContractCode == null)
		{
			ContractCode = "";
		}
		return ContractCode;
	}

	/**
	 * Returns the loanCode.
	 * @return String
	 */
	public String getLoanCode()
	{
		if (LoanCode == null)
		{
			LoanCode = "";
		}
		return LoanCode;
	}

	/**
	 * Sets the contractCode.
	 * @param contractCode The contractCode to set
	 */
	public void setContractCode(String contractCode)
	{
		ContractCode = contractCode;
	}

	/**
	 * Sets the loanCode.
	 * @param loanCode The loanCode to set
	 */
	public void setLoanCode(String loanCode)
	{
		LoanCode = loanCode;
	}

	/**
	 * Returns the instructionNo.
	 * @return String
	 */
	public String getInstructionNo()
	{
		if (InstructionNo == null)
		{
			InstructionNo = "";
		}
		return InstructionNo;
	}

	/**
	 * Sets the instructionNo.
	 * @param instructionNo The instructionNo to set
	 */
	public void setInstructionNo(String instructionNo)
	{
		InstructionNo = instructionNo;
	}

	/**
	 * Returns the desc.
	 * @return long
	 */
	public long getDesc()
	{
		return Desc;
	}

	/**
	 * Returns the orderParam.
	 * @return String
	 */
	public String getOrderParam()
	{
		if (OrderParam == null)
		{
			OrderParam = "";
		}
		return OrderParam;
	}

	/**
	 * Returns the pageNo.
	 * @return long
	 */
	public long getPageNo()
	{
		return PageNo;
	}

	/**
	 * Sets the desc.
	 * @param desc The desc to set
	 */
	public void setDesc(long desc)
	{
		Desc = desc;
	}

	/**
	 * Sets the orderParam.
	 * @param orderParam The orderParam to set
	 */
	public void setOrderParam(String orderParam)
	{
		OrderParam = orderParam;
	}

	/**
	 * Sets the pageNo.
	 * @param pageNo The pageNo to set
	 */
	public void setPageNo(long pageNo)
	{
		PageNo = pageNo;
	}

    /**
     * @param 
     * function 得到/设置
     * return long
     */
    public long getInPlanDetailID()
    {
        return InPlanDetailID;
    }

    /**
     * @param 
     * function 得到/设置
     * return long
     */
    public long getInPlanID()
    {
        return InPlanID;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setInPlanDetailID(long l)
    {
        InPlanDetailID = l;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setInPlanID(long l)
    {
        InPlanID = l;
    }

    /**
     * @param 
     * function 得到/设置
     * return long
     */
    public long getHandleUserID()
    {
        return HandleUserID;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setHandleUserID(long l)
    {
        HandleUserID = l;
    }

}
