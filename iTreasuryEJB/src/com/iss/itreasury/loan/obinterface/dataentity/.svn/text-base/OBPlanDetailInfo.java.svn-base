/*
 * Copyright (c) 1999-2002 ISoftStone. All Rights Reserved.
 *
 * 说明：网银计划明细信息
 *
 * 作者：ninh
 *
 * 更改人员：
 *
 */
package com.iss.itreasury.loan.obinterface.dataentity;

import java.beans.*;
import java.sql.*;

/**
 *
 * @author  yzhang
 */
public class OBPlanDetailInfo extends Object implements java.io.Serializable
{

	private long ID; // 计划标示

    private long PlanID; 
	private long CurrencyID; //币种标示

	private long LoanID; //  贷款标示
	private long VersionNo; //  版本号
	private Timestamp PlanDate; // 原始计划日期
	private long PayType; // 贷/还
	private double Amount; //  金额
	private String Type; //  类型（本金）
	private Timestamp InputDate; //  新增时间/修改时间
	private double ExecuteInterestRate; //  执行利率
	private String sExecuteInterestRate;
	private long ContractPayPlanVersionID; // 版本标识

	private long InputUserID; //录入人标示
	private String InputUserName; //录入人名称
	private long CheckUserID; //复核人标示
	private String CheckUserName; //复核人名称

	private long ContractID; //  委托通知单编号
	private String ContractNo; // 委托合同标示

	private long Count; //  记录数
	private double PayCounter; //  放款记数
	private double RePayCounter; //  还款记数

	//** -----  add by wli@isoftstone.com
	private double PlanPayAmount; //计划还款金额
	private double PlanBalance; //计划余额
	private double FineAmount; //罚息金额
	private Timestamp FineDate; //罚息日期
	private double FineInterestRate; //罚息利率
	private long OverdueStatusID; //逾期申请状态
	private long OVERDUEINFONEWID; //逾期表的标识ID

	//**  -------------------
	private long LastExtendID; // 对应的展期标识
	private long LastOverDueID; // 对应逾期标识
	private long IsOverDue; // 是否逾期
	private long UserType; //修改来源的类型
	private long LastVersionPlanID; //对应的上一版本的计划明细标示

	private Timestamp ExtendStartDate; // 展期起始日期
	private Timestamp ExtendEndDate; // 展期结束日期
	private long ExtendNum; //展期明细中的展期月数
	private long ExtendListID; // 展期明细标识

	private double HiddenBalance; // 传计划余额 用 E2-E3.jsp

	private long PageNo;
	private long PageCount;
	private long Desc;
	private String OrderParam;

	/**
	 * Returns the amount.
	 * @return double
	 */
	public double getAmount()
	{
		return Amount;
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
		if (CheckUserName == null )
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
	 * Returns the contractNo.
	 * @return String
	 */
	public String getContractNo()
	{
		if ( ContractNo == null)
		{
			ContractNo = "";
		}
		return ContractNo;
	}

	/**
	 * Returns the contractPayPlanVersionID.
	 * @return long
	 */
	public long getContractPayPlanVersionID()
	{
		return ContractPayPlanVersionID;
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
	 * Returns the currencyID.
	 * @return long
	 */
	public long getCurrencyID()
	{
		return CurrencyID;
	}

	/**
	 * Returns the executeInterestRate.
	 * @return double
	 */
	public double getExecuteInterestRate()
	{
		return ExecuteInterestRate;
	}

	/**
	 * Returns the extendEndDate.
	 * @return Timestamp
	 */
	public Timestamp getExtendEndDate()
	{
		return ExtendEndDate;
	}

	/**
	 * Returns the extendListID.
	 * @return long
	 */
	public long getExtendListID()
	{
		return ExtendListID;
	}

	/**
	 * Returns the extendNum.
	 * @return long
	 */
	public long getExtendNum()
	{
		return ExtendNum;
	}

	/**
	 * Returns the extendStartDate.
	 * @return Timestamp
	 */
	public Timestamp getExtendStartDate()
	{
		return ExtendStartDate;
	}

	/**
	 * Returns the fineAmount.
	 * @return double
	 */
	public double getFineAmount()
	{
		return FineAmount;
	}

	/**
	 * Returns the fineDate.
	 * @return Timestamp
	 */
	public Timestamp getFineDate()
	{
		return FineDate;
	}

	/**
	 * Returns the fineInterestRate.
	 * @return double
	 */
	public double getFineInterestRate()
	{
		return FineInterestRate;
	}

	/**
	 * Returns the hiddenBalance.
	 * @return double
	 */
	public double getHiddenBalance()
	{
		return HiddenBalance;
	}

	/**
	 * Returns the inputDate.
	 * @return Timestamp
	 */
	public Timestamp getInputDate()
	{
		return InputDate;
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
		if ( InputUserName == null)
		{
			InputUserName = "";
		}
		return InputUserName;
	}

	/**
	 * Returns the isOverDue.
	 * @return long
	 */
	public long getIsOverDue()
	{
		//注意：网银Notes中此常量与一期不同
		//public static final long CODE_YESORNO_TYPE_NO=0;// 否
    	//public static final long CODE_YESORNO_TYPE_YES=1;// 是
    	//Notes中的常量是否
    	//public static final long CODE_YESORNO_YES=1;//是
    	//public static final long CODE_YESORNO_NO=2;//否
		if (IsOverDue == 0)
		{
			IsOverDue = 2;
		}
		else if (IsOverDue == 1)
		{
			IsOverDue = 1;
		}
		return IsOverDue;
	}

	/**
	 * Returns the lastExtendID.
	 * @return long
	 */
	public long getLastExtendID()
	{
		return LastExtendID;
	}

	/**
	 * Returns the lastOverDueID.
	 * @return long
	 */
	public long getLastOverDueID()
	{
		return LastOverDueID;
	}

	/**
	 * Returns the lastVersionPlanID.
	 * @return long
	 */
	public long getLastVersionPlanID()
	{
		return LastVersionPlanID;
	}

	/**
	 * Returns the ID.
	 * @return long
	 */
	public long getID()
	{
		return ID;
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
	 * Returns the oVERDUEINFONEWID.
	 * @return long
	 */
	public long getOVERDUEINFONEWID()
	{
		return OVERDUEINFONEWID;
	}

	/**
	 * Returns the overdueStatusID.
	 * @return long
	 */
	public long getOverdueStatusID()
	{
		return OverdueStatusID;
	}

	/**
	 * Returns the payCounter.
	 * @return double
	 */
	public double getPayCounter()
	{
		return PayCounter;
	}

	/**
	 * Returns the planBalance.
	 * @return double
	 */
	public double getPlanBalance()
	{
		return PlanBalance;
	}

	/**
	 * Returns the planDate.
	 * @return Timestamp
	 */
	public Timestamp getPlanDate()
	{
		return PlanDate;
	}

	/**
	 * Returns the planPayAmount.
	 * @return double
	 */
	public double getPlanPayAmount()
	{
		return PlanPayAmount;
	}

	/**
	 * Returns the rePayCounter.
	 * @return double
	 */
	public double getRePayCounter()
	{
		return RePayCounter;
	}

	/**
	 * Returns the type.
	 * @return String
	 */
	public String getType()
	{
		if ( Type == null)
		{
			Type = "";
		}
		return Type;
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
	 * Returns the versionNo.
	 * @return long
	 */
	public long getVersionNo()
	{
		return VersionNo;
	}

	/**
	 * Sets the amount.
	 * @param amount The amount to set
	 */
	public void setAmount(double amount)
	{
		Amount = amount;
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
	 * Sets the contractNo.
	 * @param contractNo The contractNo to set
	 */
	public void setContractNo(String contractNo)
	{
		ContractNo = contractNo;
	}

	/**
	 * Sets the contractPayPlanVersionID.
	 * @param contractPayPlanVersionID The contractPayPlanVersionID to set
	 */
	public void setContractPayPlanVersionID(long contractPayPlanVersionID)
	{
		ContractPayPlanVersionID = contractPayPlanVersionID;
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
	 * Sets the currencyID.
	 * @param currencyID The currencyID to set
	 */
	public void setCurrencyID(long currencyID)
	{
		CurrencyID = currencyID;
	}

	/**
	 * Sets the executeInterestRate.
	 * @param executeInterestRate The executeInterestRate to set
	 */
	public void setExecuteInterestRate(double executeInterestRate)
	{
		ExecuteInterestRate = executeInterestRate;
	}

	/**
	 * Sets the extendEndDate.
	 * @param extendEndDate The extendEndDate to set
	 */
	public void setExtendEndDate(Timestamp extendEndDate)
	{
		ExtendEndDate = extendEndDate;
	}

	/**
	 * Sets the extendListID.
	 * @param extendListID The extendListID to set
	 */
	public void setExtendListID(long extendListID)
	{
		ExtendListID = extendListID;
	}

	/**
	 * Sets the extendNum.
	 * @param extendNum The extendNum to set
	 */
	public void setExtendNum(long extendNum)
	{
		ExtendNum = extendNum;
	}

	/**
	 * Sets the extendStartDate.
	 * @param extendStartDate The extendStartDate to set
	 */
	public void setExtendStartDate(Timestamp extendStartDate)
	{
		ExtendStartDate = extendStartDate;
	}

	/**
	 * Sets the fineAmount.
	 * @param fineAmount The fineAmount to set
	 */
	public void setFineAmount(double fineAmount)
	{
		FineAmount = fineAmount;
	}

	/**
	 * Sets the fineDate.
	 * @param fineDate The fineDate to set
	 */
	public void setFineDate(Timestamp fineDate)
	{
		FineDate = fineDate;
	}

	/**
	 * Sets the fineInterestRate.
	 * @param fineInterestRate The fineInterestRate to set
	 */
	public void setFineInterestRate(double fineInterestRate)
	{
		FineInterestRate = fineInterestRate;
	}

	/**
	 * Sets the hiddenBalance.
	 * @param hiddenBalance The hiddenBalance to set
	 */
	public void setHiddenBalance(double hiddenBalance)
	{
		HiddenBalance = hiddenBalance;
	}

	/**
	 * Sets the inputDate.
	 * @param inputDate The inputDate to set
	 */
	public void setInputDate(Timestamp inputDate)
	{
		InputDate = inputDate;
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
	 * Sets the isOverDue.
	 * @param isOverDue The isOverDue to set
	 */
	public void setIsOverDue(long isOverDue)
	{
		IsOverDue = isOverDue;
	}

	/**
	 * Sets the lastExtendID.
	 * @param lastExtendID The lastExtendID to set
	 */
	public void setLastExtendID(long lastExtendID)
	{
		LastExtendID = lastExtendID;
	}

	/**
	 * Sets the lastOverDueID.
	 * @param lastOverDueID The lastOverDueID to set
	 */
	public void setLastOverDueID(long lastOverDueID)
	{
		LastOverDueID = lastOverDueID;
	}

	/**
	 * Sets the lastVersionPlanID.
	 * @param lastVersionPlanID The lastVersionPlanID to set
	 */
	public void setLastVersionPlanID(long lastVersionPlanID)
	{
		LastVersionPlanID = lastVersionPlanID;
	}

	/**
	 * Sets the ID.
	 * @param ID The ID to set
	 */
	public void setID(long ID)
	{
		this.ID = ID;
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
	 * Sets the oVERDUEINFONEWID.
	 * @param oVERDUEINFONEWID The oVERDUEINFONEWID to set
	 */
	public void setOVERDUEINFONEWID(long oVERDUEINFONEWID)
	{
		OVERDUEINFONEWID = oVERDUEINFONEWID;
	}

	/**
	 * Sets the overdueStatusID.
	 * @param overdueStatusID The overdueStatusID to set
	 */
	public void setOverdueStatusID(long overdueStatusID)
	{
		OverdueStatusID = overdueStatusID;
	}

	/**
	 * Sets the payCounter.
	 * @param payCounter The payCounter to set
	 */
	public void setPayCounter(double payCounter)
	{
		PayCounter = payCounter;
	}

	/**
	 * Sets the planBalance.
	 * @param planBalance The planBalance to set
	 */
	public void setPlanBalance(double planBalance)
	{
		PlanBalance = planBalance;
	}

	/**
	 * Sets the planDate.
	 * @param planDate The planDate to set
	 */
	public void setPlanDate(Timestamp planDate)
	{
		PlanDate = planDate;
	}

	/**
	 * Sets the planPayAmount.
	 * @param planPayAmount The planPayAmount to set
	 */
	public void setPlanPayAmount(double planPayAmount)
	{
		PlanPayAmount = planPayAmount;
	}

	/**
	 * Sets the rePayCounter.
	 * @param rePayCounter The rePayCounter to set
	 */
	public void setRePayCounter(double rePayCounter)
	{
		RePayCounter = rePayCounter;
	}

	/**
	 * Sets the type.
	 * @param type The type to set
	 */
	public void setType(String type)
	{
		Type = type;
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
	 * Sets the versionNo.
	 * @param versionNo The versionNo to set
	 */
	public void setVersionNo(long versionNo)
	{
		VersionNo = versionNo;
	}

	/**
	 * Returns the sExecuteInterestRate.
	 * @return String
	 */
	public String getSExecuteInterestRate()
	{
		if ( sExecuteInterestRate == null)
		{
			sExecuteInterestRate = "";
		}
		return sExecuteInterestRate;
	}

	/**
	 * Sets the sExecuteInterestRate.
	 * @param sExecuteInterestRate The sExecuteInterestRate to set
	 */
	public void setSExecuteInterestRate(String sExecuteInterestRate)
	{
		this.sExecuteInterestRate = sExecuteInterestRate;
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
	 * Returns the pageNo.
	 * @return long
	 */
	public long getPageNo()
	{
		return PageNo;
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
	 * Sets the pageCount.
	 * @param pageCount The pageCount to set
	 */
	public void setPageCount(long pageCount)
	{
		PageCount = pageCount;
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
	 * Sets the desc.
	 * @param desc The desc to set
	 */
	public void setDesc(long desc)
	{
		Desc = desc;
	}
	
	/**
	 * Returns the OrderParam.
	 * @return String
	 */
	public String getOrderParam()
	{
		if ( OrderParam == null)
		{
			OrderParam = "";
		}
		return OrderParam;
	}
	
	/**
	 * Sets the OrderParam.
	 * @param OrderParam The OrderParam to set
	 */
	public void setOrderParam(String orderParam)
	{
		this.OrderParam = orderParam;
	}

    /**
     * @param 
     * function 得到/设置
     * return long
     */
    public long getPlanID()
    {
        return PlanID;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setPlanID(long l)
    {
        PlanID = l;
    }

    /**
     * @param 
     * function 得到/设置
     * return long
     */
    public long getPayType()
    {
        return PayType;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setPayType(long l)
    {
        PayType = l;
    }

}
