/*
 * Created on 2004-11-25
 *
 * Title:				iTreasury
 * @author             	yfan 
 * Company:             iSoftStone
 * Copyright:           Copyright (c) 2003
 * @version
 * Description:         
 */
package com.iss.itreasury.loan.assuremanagementnotice.dataentity;
import com.iss.itreasury.loan.base.LoanBaseDataEntity;
import com.iss.itreasury.util.Constant;

import java.sql.Timestamp;

/**
 * @author yfan
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class AssureManagementQueryInfo extends LoanBaseDataEntity
{
    //Loan_AssureChargeForm表中有的字段
    private long ID = -1;						//ID
    private long CurrencyID = -1;				//币种
    private long OfficeID = -1;					//办事处
    private long ContractID = -1;				//合同ID
    private long TypeID = -1;					//保后处理方式
    private Timestamp ExecuteDate = null;		//收款日期
    private String Code = "";					//收款通知单编号
    private double AssureAmount = 0;			//撤保金额
    private long AssureChargeAccountID = -1;	//担保手续费账户
    private double AssureChargeAmount = 0;		//担保手续费金额
    private long RecognizanceAccountID = -1;	//保证金账户
    private double RecognizanceAmount = 0;		//保证金金额
    private double AssureChargeRate = 0;		//担保费率
    private Timestamp QueryStartDate = null;			//担保开始日期
    private Timestamp QueryEndDate = null;			//担保结束日期
    private long IntervalNum = -1;				//担保期限	单位：月
    private String ReceiveAccount = "";			//受益人账户号
    private String ReceiveClientName = "";		//受益人名称
    private String RemitInProvince = "";		//汇入地（省）
    private String RemitInCity = "";			//汇入地（市）
    private String RemitInBank = "";			//汇入行名称
    private long CurrentAccountD = -1;			//活期账户
    private double CurrentAccountBalance = 0;	//活期账户余额
    private double AmendsAmount = 0;			//代偿金额
    private long InputUserID = -1;				//录入人
    private Timestamp InputDate = null;			//录入时间
    private long NextCheckUserID = -1;			//下一级审核人
    private long NextCheckLevel = -1;			//下一个审核级别
    private long StatusID = -1;					//状态
    
	//Loan_AssureChargeForm表中没有的字段
    private long ContractIDStart = -1;			//合同ID
    private long ContractIDEnd = -1;			//合同ID
    private String ContractCodeStart = "";		//合同Code
    private String ContractCodeEnd = "";		//合同Code
    private long ClientID = -1;					//被担保人
    private long UserID = -1;
    private String strUser = "";
        
    private String InputUserName = "";			//录入人名称
    private String NextCheckUserName = "";		//下一级审核人名称
    
    private long queryPurpose = 1;
    private long recordCount = 0;
    private long pageLineCount = 0;
    private long pageCount = 0;
    private long pageNo = 0;
    private long rowNumStart = 0;
    private long rowNumEnd = 0;
    private long orderParam = -1;
    private long desc = Constant.PageControl.CODE_ASCORDESC_ASC;
    private String orderParamString = "";
    
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
    }
    /**
     * @return Returns the assureChargeRate.
     */
    public double getAssureChargeRate()
    {
        return AssureChargeRate;
    }
    /**
     * @param assureChargeRate The assureChargeRate to set.
     */
    public void setAssureChargeRate(double assureChargeRate)
    {
        AssureChargeRate = assureChargeRate;
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
     * @return Returns the currentAccountD.
     */
    public long getCurrentAccountD()
    {
        return CurrentAccountD;
    }
    /**
     * @param currentAccountD The currentAccountD to set.
     */
    public void setCurrentAccountD(long currentAccountD)
    {
        CurrentAccountD = currentAccountD;
    }
    /**
     * @return Returns the endDate.
     */
    public Timestamp getQueryEndDate()
    {
        return QueryEndDate;
    }
    /**
     * @param endDate The endDate to set.
     */
    public void setQueryEndDate(Timestamp endDate)
    {
    	QueryEndDate = endDate;
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
    }
	/**
	 * @return Returns the iD.
	 */
	public long getID()
	{
	    return ID;
	}
	/**
	 * @param id The iD to set.
	 */
	public void setID(long id)
	{
	    ID = id;
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
    }
    /**
     * @return Returns the receiveAccount.
     */
    public String getReceiveAccount()
    {
        return ReceiveAccount;
    }
    /**
     * @param receiveAccount The receiveAccount to set.
     */
    public void setReceiveAccount(String receiveAccount)
    {
        ReceiveAccount = receiveAccount;
    }
    /**
     * @return Returns the receiveClientName.
     */
    public String getReceiveClientName()
    {
        return ReceiveClientName;
    }
    /**
     * @param receiveClientName The receiveClientName to set.
     */
    public void setReceiveClientName(String receiveClientName)
    {
        ReceiveClientName = receiveClientName;
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
    }
    /**
     * @return Returns the remitInBank.
     */
    public String getRemitInBank()
    {
        return RemitInBank;
    }
    /**
     * @param remitInBank The remitInBank to set.
     */
    public void setRemitInBank(String remitInBank)
    {
        RemitInBank = remitInBank;
    }
    /**
     * @return Returns the remitInCity.
     */
    public String getRemitInCity()
    {
        return RemitInCity;
    }
    /**
     * @param remitInCity The remitInCity to set.
     */
    public void setRemitInCity(String remitInCity)
    {
        RemitInCity = remitInCity;
    }
    /**
     * @return Returns the remitInProvince.
     */
    public String getRemitInProvince()
    {
        return RemitInProvince;
    }
    /**
     * @param remitInProvince The remitInProvince to set.
     */
    public void setRemitInProvince(String remitInProvince)
    {
        RemitInProvince = remitInProvince;
    }
    /**
     * @return Returns the startDate.
     */
    public Timestamp getQueryStartDate()
    {
        return QueryStartDate;
    }
    /**
     * @param startDate The startDate to set.
     */
    public void setQueryStartDate(Timestamp startDate)
    {
    	QueryStartDate = startDate;
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
    }
    /**
     * @return Returns the typeID.
     */
    public long getTypeID()
    {
        return TypeID;
    }
    /**
     * @param typeID The typeID to set.
     */
    public void setTypeID(long typeID)
    {
        TypeID = typeID;
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
     * @return Returns the contractCodeEnd.
     */
    public String getContractCodeEnd()
    {
        return ContractCodeEnd;
    }
    /**
     * @param contractCodeEnd The contractCodeEnd to set.
     */
    public void setContractCodeEnd(String contractCodeEnd)
    {
        ContractCodeEnd = contractCodeEnd;
    }
    /**
     * @return Returns the contractCodeStart.
     */
    public String getContractCodeStart()
    {
        return ContractCodeStart;
    }
    /**
     * @param contractCodeStart The contractCodeStart to set.
     */
    public void setContractCodeStart(String contractCodeStart)
    {
        ContractCodeStart = contractCodeStart;
    }
    /**
     * @return Returns the contractIDEnd.
     */
    public long getContractIDEnd()
    {
        return ContractIDEnd;
    }
    /**
     * @param contractIDEnd The contractIDEnd to set.
     */
    public void setContractIDEnd(long contractIDEnd)
    {
        ContractIDEnd = contractIDEnd;
    }
    /**
     * @return Returns the contractIDStart.
     */
    public long getContractIDStart()
    {
        return ContractIDStart;
    }
    /**
     * @param contractIDStart The contractIDStart to set.
     */
    public void setContractIDStart(long contractIDStart)
    {
        ContractIDStart = contractIDStart;
    }
    /**
     * @return Returns the desc.
     */
    public long getDesc()
    {
        return desc;
    }
    /**
     * @param desc The desc to set.
     */
    public void setDesc(long desc)
    {
        this.desc = desc;
    }
    /**
     * @return Returns the orderParam.
     */
    public long getOrderParam()
    {
        return orderParam;
    }
    /**
     * @param orderParam The orderParam to set.
     */
    public void setOrderParam(long orderParam)
    {
        this.orderParam = orderParam;
    }
    /**
     * @return Returns the orderParamString.
     */
    public String getOrderParamString()
    {
        return orderParamString;
    }
    /**
     * @param orderParamString The orderParamString to set.
     */
    public void setOrderParamString(String orderParamString)
    {
        this.orderParamString = orderParamString;
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
     * @return Returns the pageLineCount.
     */
    public long getPageLineCount()
    {
        return pageLineCount;
    }
    /**
     * @param pageLineCount The pageLineCount to set.
     */
    public void setPageLineCount(long pageLineCount)
    {
        this.pageLineCount = pageLineCount;
    }
    /**
     * @return Returns the pageNo.
     */
    public long getPageNo()
    {
        return pageNo;
    }
    /**
     * @param pageNo The pageNo to set.
     */
    public void setPageNo(long pageNo)
    {
        this.pageNo = pageNo;
    }
    /**
     * @return Returns the queryPurpose.
     */
    public long getQueryPurpose()
    {
        return queryPurpose;
    }
    /**
     * @param queryPurpose The queryPurpose to set.
     */
    public void setQueryPurpose(long queryPurpose)
    {
        this.queryPurpose = queryPurpose;
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
     * @return Returns the rowNumEnd.
     */
    public long getRowNumEnd()
    {
        return rowNumEnd;
    }
    /**
     * @param rowNumEnd The rowNumEnd to set.
     */
    public void setRowNumEnd(long rowNumEnd)
    {
        this.rowNumEnd = rowNumEnd;
    }
    /**
     * @return Returns the rowNumStart.
     */
    public long getRowNumStart()
    {
        return rowNumStart;
    }
    /**
     * @param rowNumStart The rowNumStart to set.
     */
    public void setRowNumStart(long rowNumStart)
    {
        this.rowNumStart = rowNumStart;
    }
    /**
     * @return Returns the strUser.
     */
    public String getStrUser()
    {
        return strUser;
    }
    /**
     * @param strUser The strUser to set.
     */
    public void setStrUser(String strUser)
    {
        this.strUser = strUser;
    }
    /**
     * @return Returns the userID.
     */
    public long getUserID()
    {
        return UserID;
    }
    /**
     * @param userID The userID to set.
     */
    public void setUserID(long userID)
    {
        UserID = userID;
    }
    /**
     * @return Returns the amendsAmount.
     */
    public double getAmendsAmount()
    {
        return AmendsAmount;
    }
    /**
     * @param amendsAmount The amendsAmount to set.
     */
    public void setAmendsAmount(double amendsAmount)
    {
        AmendsAmount = amendsAmount;
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
    }
    /**
     * @return Returns the currentAccountBalance.
     */
    public double getCurrentAccountBalance()
    {
        return CurrentAccountBalance;
    }
    /**
     * @param currentAccountBalance The currentAccountBalance to set.
     */
    public void setCurrentAccountBalance(double currentAccountBalance)
    {
        CurrentAccountBalance = currentAccountBalance;
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
    }
}
