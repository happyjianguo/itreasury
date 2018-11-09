/*
 * Created on 2010-06-09
 *
 * Title:				iTreasury
 * @author             	quanshao 
 * Company:             iSoftStone
 * Copyright:           Copyright (c) 2003
 * @version
 * Description:         
 */
package com.iss.itreasury.loan.recognizancenotice.dataentity;
import com.iss.itreasury.loan.base.LoanBaseDataEntity;
import com.iss.itreasury.system.approval.dataentity.InutParameterInfo;
import java.sql.Timestamp;

/**
 * @author quanshao
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class RecognizanceNoticeInfo extends LoanBaseDataEntity
{
	//Loan_RecognizaceNoticeForm表中有的字段
    private long Id = -1;						//ID
    private long CurrencyID = -1;				//币种
    private long OfficeID = -1;					//办事处
    private long ContractID = -1;				//合同ID
    private long TypeID = -1;					//保后处理方式
    private Timestamp ExecuteDate = null;		//收款日期
    private String Code = "";					//保证金保后处理通知单编号
    private long RecognizanceAccountID = -1;	//保证金账户ID
    private double RecognizanceAmount = 0;		//退/扣保证金金额
    private double RZZLbalance = 0;		        //融资租赁余额
    private double SumRecognizance = 0;		    //累计收保证金金额
    private long InputUserID = -1;				//录入人
    private Timestamp InputDate = null;			//录入时间
    private long NextCheckUserID = -1;			//下一级审核人
    private long NextCheckLevel = -1;			//下一个审核级别
    private long IsLowLevel = -1;				//是否走最低级审批流
    private long StatusID = -1;					//状态
    private long lSubTypeID = -1 ;              //业务子类型
    private long lTypeID    = -1 ;              //业务类型

    private InutParameterInfo inutParameterInfo = null;
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
        putUsedField("TypeID", TypeID);
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
	public InutParameterInfo getInutParameterInfo() {
		return inutParameterInfo;
	}
	public void setInutParameterInfo(InutParameterInfo inutParameterInfo) {
		this.inutParameterInfo = inutParameterInfo;
	}
	public double getRZZLbalance() {
		return RZZLbalance;
	}
	public void setRZZLbalance(double lbalance) {
		RZZLbalance = lbalance;
		putUsedField("RZZLbalance", RZZLbalance);
	}
	public double getSumRecognizance() {
		return SumRecognizance;
	}
	public void setSumRecognizance(double sumRecognizance) {
		SumRecognizance = sumRecognizance;
		putUsedField("SumRecognizance", SumRecognizance);
	}
	public long getLSubTypeID() {
		return lSubTypeID;
	}
	public void setLSubTypeID(long subTypeID) {
		lSubTypeID = subTypeID;
		putUsedField("LSubTypeID", lSubTypeID);
	}
	public long getLTypeID() {
		return lTypeID;
	}
	public void setLTypeID(long typeID) {
		lTypeID = typeID;
		putUsedField("lTypeID", lTypeID);
	}
}
