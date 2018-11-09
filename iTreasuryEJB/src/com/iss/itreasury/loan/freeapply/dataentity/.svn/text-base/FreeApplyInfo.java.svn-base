/*
 * FreeApplyInfo.java
 *
 * Created on 2002年3月28日, 上午10:14
 */

package com.iss.itreasury.loan.freeapply.dataentity;

import java.beans.*;
import java.sql.*;
import java.util.*;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.system.approval.dataentity.InutParameterInfo;

/**
 *
 * @author  yzhang
 * @version 
 */
public class FreeApplyInfo implements java.io.Serializable {

     /** 
     * FreeApplyInfo 构造子注解。
     */ 
    public FreeApplyInfo()
    {
        super();
        
    }
    
    private long ID = -1;                       //免还申请标识
    private String FreeCode = "";               //免还编号
        
    private long ContractID = -1;               //合同标示
    private String ContractCode = "";           //合同编号
    //private long LoanInfoID;    
    //private long ConsignClientID;             //委托贷款单位编号
    private String ConsignClientName = "";      //委托贷款单位名称
    private String ConsignClientAccount = "";   //委托贷款单位账户号
    
    private long ClientID =-1;                  //借款单位ID
    private String ClientName = "";             //借款单位名称
    //放款单位名称
    private String PayClientName = Env.getClientName(); 
        
    private long  LoanTypeID=-1;       //贷款类型
    private long CurrencyID;          //币种
    private long OfficeID;            //办事处标示
    //private String OfficeName;        //办事处名称
           
    private double Amount;              //借款金额
    private double Balance;             //借款余额（未还金额）
    private long IntervalNum;           //贷款期限
    private String LoanPurpose;         //贷款用途
    private float InterestRate;         //贷款利率
    private Timestamp StartDate;        //贷款开始时间
    private Timestamp EndDate;          //贷款结束时间
    
    private long LoanPayID = -1;        //放款通知单ID
    private String LoanPayCode = "";    //放款通知单编号
    private double LoanPayAmount;       //放款金额
    private double LoanPayBalance;      //放款余额
    private double FreeAmount;          //免还金额
    private double FreeRate;            //免还利息
    private Timestamp FreeDate;         //免还开始时间
    private String FreeReason;          //免还原因
    //private String Status;            //状态描述
    private long StatusID;              //免还状态 已提交、已审核
     
    private long  InputUserID;          //录入人标示
    private String InputUserName;       //录入人名称 一级审核人
    private Timestamp InputDate;        //录入时间
    private long CheckUserID;           //审核人
    private String CheckUserName;       //审核人名称
    private long NextCheckLevel = -1;	//下一个审核级别

    private long RecordCount;             //总记录数
    private long PageCount;             //总页数
    private double AllLoanAmount = 0 ;  //总申请贷款金额
    private double AllFreeAmount = 0 ;  //总免还金额
	//added by xwhe 2007/06/28审批流
	private InutParameterInfo inutParameterInfo = null;
    /**
     * function 得到免还申请标识
     * return lID
     */
    public long getID()
    {
        return ID;
    }

    /**
     * @param lID
     * function 设置免还申请标识
     * return void
     */
    public void setID(long lID)
    {
        this.ID = lID;
    }

    /**
     * function 得到合同标示
     * return lContactID
     */
    public long getContractID()
    {
        return ContractID;
    }

    /**
     * @param lContactID
     * function 设置合同标示
     * return void
     */
    public void setContractID(long lContactID)
    {
        this.ContractID = lContactID;
    }

    /**
     * function 得到合同编号
     * return ContractCode
     */
    public String getContractCode()
    {
        return ContractCode;
    }

    /**
     * @param string
     * function 设置合同编号
     * return void
     */
    public void setContractCode(String string)
    {
        this.ContractCode = string;
    }

    /**
     * function 得到委托贷款单位名称
     * return ConsignClientName
     */
    public String getConsignClientName()
    {
        return ConsignClientName;
    }

    /**
     * @param string
     * function 设置委托贷款单位名称
     * return void
     */
    public void setConsignClientName(String string)
    {
        this.ConsignClientName = string;
    }

    /**
     * function 得到委托贷款单位账户号
     * return ConsignClientAccount
     */
    public String getConsignClientAccount()
    {
        return ConsignClientAccount;
    }

    /**
     * @param string
     * function 设置委托贷款单位账户号
     * return void
     */
    public void setConsignClientAccount(String string)
    {
        this.ConsignClientAccount = string;
    }

    /**
     * function 得到借款单位名称
     * return ClientName
     */
    public String getClientName()
    {
        return ClientName;
    }

    /**
     * @param string
     * function 设置借款单位名称
     * return void
     */
    public void setClientName(String string)
    {
        this.ClientName = string;
    }

    /**
     * function 得到放款单位名称
     * return PayClientName
     */
    public String getPayClientName()
    {
        return PayClientName;
    }


    /**
     * function 得到借款金额
     * return double
     */
    public double getAmount()
    {
        return Amount;
    }

    /**
     * function 设置借款金额
     * return void
     */
    public void setAmount(double d)
    {
        this.Amount = d;
    }

    /**
     * function 得到借款余额（未还金额）
     * return Balance
     */
    public double getBalance()
    {
        return Balance;
    }

    /**
     * @param d
     * function 设置借款余额（未还金额）
     * return void
     */
    public void setBalance(double d)
    {
        this.Balance = d;
    }

    /**
     * function 得到贷款期限
     * return IntervalNum
     */
    public long getIntervalNum()
    {
        return IntervalNum;
    }

    /**
     * @param l
     * function 设置贷款期限
     * return void
     */
    public void setIntervalNum(long l)
    {
        this.IntervalNum = l;
    }

    /**
     * function 得到贷款用途
     * return LoanPurpose
     */
    public String getLoanPurpose()
    {
        return LoanPurpose;
    }

    /**
     * @param string
     * function 设置贷款用途
     * return void
     */
    public void setLoanPurpose(String string)
    {
        this.LoanPurpose = string;
    }

    /**
     * function 得到贷款利率
     * return InterestRate
     */
    public float getInterestRate()
    {
        return InterestRate;
    }

    /**
     * @param f
     * function 设置贷款利率
     * return void
     */
    public void setInterestRate(float f)
    {
        this.InterestRate = f;
    }

    /**
     * function 得到贷款开始时间
     * return EndDate
     */
    public Timestamp getStartDate()
    {
        return StartDate;
    }

    /**
     * @param timestamp
     * function 设置贷款开始时间
     * return void
     */
    public void setStartDate(Timestamp timestamp)
    {
        this.StartDate = timestamp;
    }

    /**
     * function 得到贷款结束时间
     * return EndDate
     */
    public Timestamp getEndDate()
    {
        return EndDate;
    }

    /**
     * @param timestamp
     * function 设置贷款结束时间
     * return void
     */
    public void setEndDate(Timestamp timestamp)
    {
        this.EndDate = timestamp;
    }

    /**
     * function 得到放款余额
     * return FreeAmount
     */
    public double getLoanPayBalance()
    {
        return LoanPayBalance;
    }

    /**
     * @param d
     * function 设置放款余额
     * return void
     */
    public void setLoanPayBalance(double d)
    {
        this.LoanPayBalance = d;
    }

    /**
     * function 得到放款金额
     * return FreeAmount
     */
    public double getLoanPayAmount()
    {
        return LoanPayAmount;
    }

    /**
     * @param d
     * function 设置放款金额
     * return void
     */
    public void setLoanPayAmount(double d)
    {
        this.LoanPayAmount = d;
    }

    /**
     * function 得到/设置免还金额
     * return FreeAmount
     */
    public double getFreeAmount()
    {
        return FreeAmount;
    }

    /**
     * @param d
     * function 得到/设置免还金额
     * return void
     */
    public void setFreeAmount(double d)
    {
        this.FreeAmount = d;
    }

    /**
     * function 得到/设置免还利息
     * return FreeAmount
     */
    public double getFreeRate()
    {
        return FreeRate;
    }

    /**
     * @param d
     * function 得到/设置免还利息
     * return void
     */
    public void setFreeRate(double d)
    {
        this.FreeRate = d;
    }


    /**
     * function 得到免还开始时间
     * return FreeDate
     */
    public Timestamp getFreeDate()
    {
        return FreeDate;
    }

    /**
     * @param timestamp
     * function 设置免还开始时间
     * return void
     */
    public void setFreeDate(Timestamp timestamp)
    {
        this.FreeDate = timestamp;
    }

    /**
     * function 得到免还原因
     * return FreeReason
     */
    public String getFreeReason()
    {
        return FreeReason;
    }

    /**
     * @param string
     * function 设置免还原因
     * return void
     */
    public void setFreeReason(String string)
    {
        this.FreeReason = string;
    }

    /**
     * function 得到录入人
     * return InputUserName
     */
    public String getInputUserName()
    {
        return InputUserName;
    }

    /**
     * @param string
     * function 设置录入人
     * return void
     */
    public void setInputUserName(String string)
    {
        this.InputUserName = string;
    }

    /**
     * function 得到审核人
     * return CheckUserName
     */
    public String getCheckUserName()
    {
        return CheckUserName;
    }

    /**
     * @param string
     * function 设置审核人
     * return void
     */
    public void setCheckUserName(String string)
    {
        CheckUserName = string;
    }

    /**
     * @param 
     * function 得到状态ID
     * return long
     */
    public long getStatusID()
    {
        return StatusID;
    }

    /**
     * @param l
     * function 设置状态ID
     * return void
     */
    public void setStatusID(long l)
    {
        this.StatusID = l;
    }

    /**
     * function 得到放款通知单编号
     * return LoanPayCode
     */
    public String getLoanPayCode()
    {
        return LoanPayCode;
    }

    /**
     * @param string
     * function 设置放款通知单编号
     * return void
     */
    public void setLoanPayCode(String string)
    {
        this.LoanPayCode = string;
    }

    /**
     * function 得到总页数
     * return PageCount
     */
    public long getPageCount()
    {
        return PageCount;
    }

    /**
     * @param l
     * function 设置总页数
     * return void
     */
    public void setPageCount(long l)
    {
        this.PageCount = l;
    }

    /**
     * function 得到录入人
     * return InputUserID
     */
    public long getInputUserID()
    {
        return InputUserID;
    }

    /**
     * @param l
     * function 设置录入人
     * return void
     */
    public void setInputUserID(long l)
    {
        this.InputUserID = l;
    }

    /**
     * function 得到录入时间
     * return InputDate
     */
    public Timestamp getInputDate()
    {
        return InputDate;
    }

    /**
     * @param timestamp
     * function 设置录入时间
     * return void
     */
    public void setInputDate(Timestamp timestamp)
    {
        InputDate = timestamp;
    }

    /**
     * function 得到放款通知单ID
     * return LoanPayID
     */
    public long getLoanPayID()
    {
        return LoanPayID;
    }

    /**
     * @param l
     * function 设置放款通知单ID
     * return void
     */
    public void setLoanPayID(long l)
    {
        this.LoanPayID = l;
    }

    /**
     * @param 
     * function 得到审核人
     * return CheckUserID
     */
    public long getCheckUserID()
    {
        return CheckUserID;
    }

    /**
     * @param l
     * function 设置审核人
     * return void
     */
    public void setCheckUserID(long l)
    {
        this.CheckUserID = l;
    }

    /**
     * function 得到总申请贷款金额
     * return double
     */
    public double getAllLoanAmount()
    {
        return AllLoanAmount;
    }

    /**
     * @param 
     * function 设置总申请贷款金额
     * return void
     */
    public void setAllLoanAmount(double d)
    {
        this.AllLoanAmount = d;
    }

    /**
     * function 得到总免还金额
     * return double
     */
    public double getAllFreeAmount()
    {
        return AllFreeAmount;
    }

    /**
     * @param d
     * function 设置总免还金额
     * return void
     */
    public void setAllFreeAmount(double d)
    {
        this.AllFreeAmount = d;
    }

    /**
     * function 得到总记录数
     * return long
     */
    public long getRecordCount()
    {
        return RecordCount;
    }

    /**
     * @param l
     * function 得到/设置总记录数
     * return void
     */
    public void setRecordCount(long l)
    {
        this.RecordCount = l;
    }

    /**
     * function 得到/设置贷款类型
     * return long
     */
    public long getLoanTypeID()
    {
        return LoanTypeID;
    }

    /**
     * @param l
     * function 得到/设置贷款类型
     * return void
     */
    public void setLoanTypeID(long l)
    {
        this.LoanTypeID = l;
    }

    /**
     * function 得到/设置免还编号
     * return FreeCode
     */
    public String getFreeCode()
    {
        return FreeCode;
    }

    /**
     * @param string
     * function 得到/设置免还编号
     * return void
     */
    public void setFreeCode(String string)
    {
        this.FreeCode = string;
    }

    /**
     * @return Returns the nextCheckLevel.
     */
    public long getNextCheckLevel()
    {
        return NextCheckLevel;
    }
    /**
     * @param nextCheckLevel The nextCheckLevel to set.
     */
    public void setNextCheckLevel(long nextCheckLevel)
    {
        NextCheckLevel = nextCheckLevel;
    }
	/**
	 * Returns the currencyID.
	 * @return long
	 */
	public long getCurrencyID() {
		return CurrencyID;
	}

	/**
	 * Returns the officeID.
	 * @return long
	 */
	public long getOfficeID() {
		return OfficeID;
	}

	/**
	 * Sets the currencyID.
	 * @param currencyID The currencyID to set
	 */
	public void setCurrencyID(long currencyID) {
		CurrencyID = currencyID;
	}

	/**
	 * Sets the officeID.
	 * @param officeID The officeID to set
	 */
	public void setOfficeID(long officeID) {
		OfficeID = officeID;
	}
   
	public InutParameterInfo getInutParameterInfo() {
		return inutParameterInfo;
	}
	public void setInutParameterInfo(InutParameterInfo inutParameterInfo) {
		this.inutParameterInfo = inutParameterInfo;
	}
}
