/*
 * Created on 2004-11-24
 *
 * Title:				iTreasury
 * @author             	yfan 
 * Company:             iSoftStone
 * Copyright:           Copyright (c) 2003
 * @version
 * Description:         
 */
package com.iss.itreasury.loan.assurechargenotice.dataentity;
import com.iss.itreasury.loan.base.LoanBaseDataEntity;
import com.iss.itreasury.system.approval.dataentity.InutParameterInfo;

import java.sql.Timestamp;

/**
 * @author yfan
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class AssureChargeNoticeInfo extends LoanBaseDataEntity
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4488506295812857087L;
	//Loan_AssureChargeForm表中有的字段
    private long Id = -1;						//ID
    private long CurrencyID = -1;				//币种
    private long OfficeID = -1;					//办事处
    private long ContractID = -1;				//合同ID
    private Timestamp ExecuteDate = null;		//收款日期
    private String Code = "";					//收款通知单编号
    private double AssureAmount = 0;			//承保金额
	private long RecognizanceAccountID = -1;	//保证金账户
    private double RecognizanceAmount = 0;		//保证金金额
    private long AssureChargeAccountID = -1;	//担保手续费账户
    private double AssureChargeAmount = 0;		//担保手续费金额
    private Timestamp StartDate = null;			//担保开始日期
    private Timestamp EndDate = null;			//担保结束日期
    private long IntervalNum = -1;				//担保期限	单位：月
    private long InputUserID = -1;				//录入人
    private Timestamp InputDate = null;			//录入时间
    private long NextCheckUserID = -1;			//下一级审核人
    private long NextCheckLevel = -1;			//下一个审核级别
    private long IsLowLevel = -1;				//是否走最低级审批流
    private long StatusID = -1;					//状态

	//Loan_AssureChargeForm表中没有的字段
    private long ClientID = -1;
    private String ClientName = "";
    private String ContractCode = "";
    private String InputUserName = "";			//录入人名称
    private String NextCheckUserName = "";		//下一级审核人名称
    
    private long recordCount = 0;
    private long pageCount = 0;

	//2006-3-20
	private long RecrecognizanceAccountID = -1l;   //保证金收款账户
	private long Isinterest = 0;                 //是否计息
	private double Rate =0d;                     //利率
	
	//added by qhzhou 2007.07.03
    private InutParameterInfo inutParameterInfo = null;

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
        putUsedField("Id", Id);
    }
    /**
     * @return Returns the assureChargeAmount.
     */
    public double getAssureChargeAmount()
    {
        return AssureChargeAmount;
    }
    /**
     * @param assureChargeAmount The assureChargeAmount to set.
     */
    public void setAssureChargeAmount(double assureChargeAmount)
    {
        AssureChargeAmount = assureChargeAmount;
        putUsedField("AssureChargeAmount", AssureChargeAmount);
    }
    /**
     * @return Returns the code.
     */
    public String getCode()
    {
        return Code;
    }
    /**
     * @param code The code to set.
     */
    public void setCode(String code)
    {
        Code = code;
        putUsedField("Code", Code);
    }
    /**
     * @return Returns the contractID.
     */
    public long getContractID()
    {
        return ContractID;
    }
    /**
     * @param contractID The contractID to set.
     */
    public void setContractID(long contractID)
    {
        ContractID = contractID;
        putUsedField("ContractID", ContractID);
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
        putUsedField("CurrencyID", CurrencyID);
    }    
    /**
     * @return Returns the endDate.
     */
    public Timestamp getEndDate()
    {
        return EndDate;
    }
    /**
     * @param endDate The endDate to set.
     */
    public void setEndDate(Timestamp endDate)
    {
        EndDate = endDate;
        putUsedField("EndDate", EndDate);
    }
    /**
     * @return Returns the executeDate.
     */
    public Timestamp getExecuteDate()
    {
        return ExecuteDate;
    }
    /**
     * @param executeDate The executeDate to set.
     */
    public void setExecuteDate(Timestamp executeDate)
    {
        ExecuteDate = executeDate;
        putUsedField("ExecuteDate", ExecuteDate);
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
        putUsedField("InputDate", InputDate);
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
        putUsedField("InputUserID", InputUserID);
    }
    /**
     * @return Returns the inputUserName.
     */
    public String getInputUserName()
    {
        return InputUserName;
    }
    /**
     * @param inputUserName The inputUserName to set.
     */
    public void setInputUserName(String inputUserName)
    {
        InputUserName = inputUserName;
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
        putUsedField("NextCheckLevel", NextCheckLevel);
    }
    /**
     * @return Returns the nextCheckUserID.
     */
    public long getNextCheckUserID()
    {
        return NextCheckUserID;
    }
    /**
     * @param nextCheckUserID The nextCheckUserID to set.
     */
    public void setNextCheckUserID(long nextCheckUserID)
    {
        NextCheckUserID = nextCheckUserID;
        putUsedField("NextCheckUserID", NextCheckUserID);
    }
    /**
     * @return Returns the nextCheckUserName.
     */
    public String getNextCheckUserName()
    {
        return NextCheckUserName;
    }
    /**
     * @param nextCheckUserName The nextCheckUserName to set.
     */
    public void setNextCheckUserName(String nextCheckUserName)
    {
        NextCheckUserName = nextCheckUserName;
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
        putUsedField("OfficeID", OfficeID);
    }
    /**
     * @return Returns the recognizanceAmount.
     */
    public double getRecognizanceAmount()
    {
        return RecognizanceAmount;
    }
    /**
     * @param recognizanceAmount The recognizanceAmount to set.
     */
    public void setRecognizanceAmount(double recognizanceAmount)
    {
        RecognizanceAmount = recognizanceAmount;
        putUsedField("RecognizanceAmount", RecognizanceAmount);
    }
    /**
     * @return Returns the startDate.
     */
    public Timestamp getStartDate()
    {
        return StartDate;
    }
    /**
     * @param startDate The startDate to set.
     */
    public void setStartDate(Timestamp startDate)
    {
        StartDate = startDate;
        putUsedField("StartDate", StartDate);
    }
    /**
     * @return Returns the statusID.
     */
    public long getStatusID()
    {
        return StatusID;
    }
    /**
     * @param statusID The statusID to set.
     */
    public void setStatusID(long statusID)
    {
        StatusID = statusID;
        putUsedField("StatusID", StatusID);
    }
    /**
     * @return Returns the isLowLevel.
     */
    public long getIsLowLevel()
    {
        return IsLowLevel;
    }
    /**
     * @param isLowLevel The isLowLevel to set.
     */
    public void setIsLowLevel(long isLowLevel)
    {
        IsLowLevel = isLowLevel;
        putUsedField("IsLowLevel", IsLowLevel);
    }
    /**
     * @return Returns the clientID.
     */
    public long getClientID()
    {
        return ClientID;
    }
    /**
     * @param clientID The clientID to set.
     */
    public void setClientID(long clientID)
    {
        ClientID = clientID;
    }
    /**
     * @return Returns the clientName.
     */
    public String getClientName()
    {
        return ClientName;
    }
    /**
     * @param clientName The clientName to set.
     */
    public void setClientName(String clientName)
    {
        ClientName = clientName;
    }
    /**
     * @return Returns the contractCode.
     */
    public String getContractCode()
    {
        return ContractCode;
    }
    /**
     * @param contractCode The contractCode to set.
     */
    public void setContractCode(String contractCode)
    {
        ContractCode = contractCode;
    }    
    /**
     * @return Returns the pageCount.
     */
    public long getPageCount()
    {
        return pageCount;
    }
    /**
     * @param pageCount The pageCount to set.
     */
    public void setPageCount(long pageCount)
    {
        this.pageCount = pageCount;
    }
    /**
     * @return Returns the recordCount.
     */
    public long getRecordCount()
    {
        return recordCount;
    }
    /**
     * @param recordCount The recordCount to set.
     */
    public void setRecordCount(long recordCount)
    {
        this.recordCount = recordCount;
    }
    /**
     * @return Returns the assureAmount.
     */
    public double getAssureAmount()
    {
        return AssureAmount;
    }
    /**
     * @param assureAmount The assureAmount to set.
     */
    public void setAssureAmount(double assureAmount)
    {
        AssureAmount = assureAmount;
        putUsedField("AssureAmount", AssureAmount);
    }
    /**
     * @return Returns the assureChargeAccountID.
     */
    public long getAssureChargeAccountID()
    {
        return AssureChargeAccountID;
    }
    /**
     * @param assureChargeAccountID The assureChargeAccountID to set.
     */
    public void setAssureChargeAccountID(long assureChargeAccountID)
    {
        AssureChargeAccountID = assureChargeAccountID;
        putUsedField("AssureChargeAccountID", AssureChargeAccountID);
    }
    /**
     * @return Returns the intervalNum.
     */
    public long getIntervalNum()
    {
        return IntervalNum;
    }
    /**
     * @param intervalNum The intervalNum to set.
     */
    public void setIntervalNum(long intervalNum)
    {
        IntervalNum = intervalNum;
        putUsedField("IntervalNum", IntervalNum);
    }
    /**
     * @return Returns the recognizanceAccountID.
     */
    public long getRecognizanceAccountID()
    {
        return RecognizanceAccountID;
    }
    /**
     * @param recognizanceAccountID The recognizanceAccountID to set.
     */
    public void setRecognizanceAccountID(long recognizanceAccountID)
    {
        RecognizanceAccountID = recognizanceAccountID;
        putUsedField("RecognizanceAccountID", RecognizanceAccountID);
    }
    
    //2006-3-20
	public long getIsinterest() {
		return Isinterest;
	}
	public void setIsinterest(long isinterest) {
		Isinterest = isinterest;
		putUsedField("Isinterest", Isinterest);
	}
	public double getRate() {
		return Rate;
	}
	public void setRate(double rate) {
		Rate = rate;
		putUsedField("Rate", Rate);
	}
	public long getRecrecognizanceAccountID() {
		return RecrecognizanceAccountID;
	}
	public void setRecrecognizanceAccountID(long recrecognizanceAccountID) {
		RecrecognizanceAccountID = recrecognizanceAccountID;
		putUsedField("RecrecognizanceAccountID", RecrecognizanceAccountID);
	}
	
	public InutParameterInfo getInutParameterInfo() {
		return inutParameterInfo;
	}
	public void setInutParameterInfo(InutParameterInfo inutParameterInfo) {
		this.inutParameterInfo = inutParameterInfo;
	}  
}
