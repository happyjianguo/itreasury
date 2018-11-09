package com.iss.itreasury.loan.repayplan.dataentity;

import java.io.Serializable;
import java.sql.*;

import com.iss.sysframe.base.dataentity.BaseDataEntity;
/**
 * @author yzhang
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class PlanVersionInfo  extends BaseDataEntity implements Serializable
{

	public static void main(String[] args)
	{
	}

	private long ID; //标识
	private long lModifyID;//合同执行计划修改标示
	private long nLoanID; //只有第一个版本关联申请书
	private long nContractID; //合同标识
	private long nPlanVersion; //计划版本号
	private long nInputUserID; //录入用户标识
	private Timestamp dtInput; //录入日期  
	private long nCheckUserID; //复核用户标识
	private Timestamp dtCheck; //复核日期     
	private long nStatusID; //状态标识
	private String sInputUserName; // 提交人名称
	private String sCheckUserName; // 复核人名称
	private long lCheckNum;
	private long lIsUsed; //是否在使用中  1--是   2--否
	private long lUserType; //使用来源

	private long lCount; // 记录数
	private long lPageCount; // Page Count
	
	private long lNextCheckLevel = -1;	//下一个审核级别
	private String sReason;//原因

	/**
	 * Returns the check.
	 * @return Timestamp
	 */
	public Timestamp getCheck()
	{
		return dtCheck;
	}

	/**
	 * Returns the input.
	 * @return Timestamp
	 */
	public Timestamp getInput()
	{
		return dtInput;
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
	 * Returns the checkUserID.
	 * @return long
	 */
	public long getCheckUserID()
	{
		return nCheckUserID;
	}

	/**
	 * Returns the contractID.
	 * @return long
	 */
	public long getContractID()
	{
		return nContractID;
	}

	/**
	 * Returns the inputUserID.
	 * @return long
	 */
	public long getInputUserID()
	{
		return nInputUserID;
	}

	/**
	 * Returns the loanID.
	 * @return long
	 */
	public long getLoanID()
	{
		return nLoanID;
	}

	/**
	 * Returns the planVersion.
	 * @return long
	 */
	public long getPlanVersion()
	{
		return nPlanVersion;
	}

	/**
	 * Returns the statusID.
	 * @return long
	 */
	public long getStatusID()
	{
		return nStatusID;
	}

	/**
	 * Returns the checkUserName.
	 * @return String
	 */
	public String getCheckUserName()
	{
		return sCheckUserName;
	}

	/**
	 * Returns the inputUserName.
	 * @return String
	 */
	public String getInputUserName()
	{
		return sInputUserName;
	}

	/**
	 * Sets the check.
	 * @param check The check to set
	 */
	public void setCheck(Timestamp check)
	{
		dtCheck = check;
	}

	/**
	 * Sets the input.
	 * @param input The input to set
	 */
	public void setInput(Timestamp input)
	{
		dtInput = input;
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
	 * Sets the checkUserID.
	 * @param checkUserID The checkUserID to set
	 */
	public void setCheckUserID(long checkUserID)
	{
		nCheckUserID = checkUserID;
	}

	/**
	 * Sets the contractID.
	 * @param contractID The contractID to set
	 */
	public void setContractID(long contractID)
	{
		nContractID = contractID;
	}

	/**
	 * Sets the inputUserID.
	 * @param inputUserID The inputUserID to set
	 */
	public void setInputUserID(long inputUserID)
	{
		nInputUserID = inputUserID;
	}

	/**
	 * Sets the loanID.
	 * @param loanID The loanID to set
	 */
	public void setLoanID(long loanID)
	{
		nLoanID = loanID;
	}

	/**
	 * Sets the planVersion.
	 * @param planVersion The planVersion to set
	 */
	public void setPlanVersion(long planVersion)
	{
		nPlanVersion = planVersion;
	}

	/**
	 * Sets the statusID.
	 * @param statusID The statusID to set
	 */
	public void setStatusID(long statusID)
	{
		nStatusID = statusID;
	}

	/**
	 * Sets the checkUserName.
	 * @param checkUserName The checkUserName to set
	 */
	public void setCheckUserName(String checkUserName)
	{
		sCheckUserName = checkUserName;
	}

	/**
	 * Sets the inputUserName.
	 * @param inputUserName The inputUserName to set
	 */
	public void setInputUserName(String inputUserName)
	{
		sInputUserName = inputUserName;
	}

    /**
     * @return Returns the lCheckNum.
     */
    public long getCheckNum()
    {
        return lCheckNum;
    }
    /**
     * @param checkNum The lCheckNum to set.
     */
    public void setCheckNum(long checkNum)
    {
        lCheckNum = checkNum;
    }
    /**
     * @return Returns the lCount.
     */
    public long getCount()
    {
        return lCount;
    }
    /**
     * @param count The lCount to set.
     */
    public void setCount(long count)
    {
        lCount = count;
    }
    /**
     * @return Returns the lIsUsed.
     */
    public long getIsUsed()
    {
        return lIsUsed;
    }
    /**
     * @param isUsed The lIsUsed to set.
     */
    public void setIsUsed(long isUsed)
    {
        lIsUsed = isUsed;
    }
    /**
     * @return Returns the lModifyID.
     */
    public long getModifyID()
    {
        return lModifyID;
    }
    /**
     * @param modifyID The lModifyID to set.
     */
    public void setModifyID(long modifyID)
    {
        lModifyID = modifyID;
    }
    /**
     * @return Returns the lNextCheckLevel.
     */
    public long getNextCheckLevel()
    {
        return lNextCheckLevel;
    }
    /**
     * @param nextCheckLevel The lNextCheckLevel to set.
     */
    public void setNextCheckLevel(long nextCheckLevel)
    {
        lNextCheckLevel = nextCheckLevel;
    }
    /**
     * @return Returns the lPageCount.
     */
    public long getPageCount()
    {
        return lPageCount;
    }
    /**
     * @param pageCount The lPageCount to set.
     */
    public void setPageCount(long pageCount)
    {
        lPageCount = pageCount;
    }
    /**
     * @return Returns the lUserType.
     */
    public long getUserType()
    {
        return lUserType;
    }
    /**
     * @param userType The lUserType to set.
     */
    public void setUserType(long userType)
    {
        lUserType = userType;
    }

	public String getSReason() {
		return sReason;
	}

	public void setSReason(String reason) {
		sReason = reason;
	}

	@Override
	public long getId() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setId(long id) {
		// TODO Auto-generated method stub
		
	}
}
