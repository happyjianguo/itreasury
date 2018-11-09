package com.iss.itreasury.loan.obinterface.dataentity;
import java.sql.*;
/**
 * @author yzhang
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class OBApplyInfo implements java.io.Serializable
{
    /**
     * OBApplyInfo 构造子注解。
     */
    public OBApplyInfo()
    {
        super();
    }
    
    //ID:标识
    private long ID;

    //loan标识
    private long LoanID;

    //贷款类型
    private long LoanTypeID;
    
    //申请书编号
    private String OBApplyCode;

    //合同标识
    private long ContractID;
    
    //合同编号
    private String ContractCode;

    //委托合同标识
    //private long ConsignContractID;
    
    //委托合同编号
    //private String ConsignContractCode;
    
    //指令类型
    private long LoanInstrType = -1;
    
    //金额
    private double Amount;
    
    //申请日期 
    private Timestamp ApplyDate;

    //申请单位
    private long ClientID;
    private String ClientName;
    
    //指令状态  
    private long StatusID;
    private String Status;
    
    //处理人
    private long UserID;
    private String UserName;
    
    //总页数
    private long PageCount;
    private long OfficeID = -1;
    private long CurrencyID = -1;

    private long InID = -1;
    private String InCode = "";
    
    private String InstructionNo = "" ;

    //--------------------------------
    /**
     * function 得到标识
     * return ContractID
     */
    public long getID()
    {
        return ID;
    }

    /**
     * @param lID
     * function 设置标识
     * return void
     */
    public void setID(long lID)
    {
        this.ID = lID;
    }

    /**
     * function 得到申请书编号
     * return OBApplyCode
     */
    public String getOBApplyCode()
    {
        return OBApplyCode;
    }

    /**
     * @param ContractCode
     * function 设置合同编号
     * return void
     */
    public void setOBApplyCode(String Code)
    {
        this.OBApplyCode = Code;
    }

    /**
     * function 得到合同标识
     * return ContractID
     */
    public long getContractID()
    {
        return ContractID;
    }

    /**
     * @param lID
     * function 设置合同标识
     * return void
     */
    public void setContractID(long lID)
    {
        this.ContractID = lID;
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
     * @param ContractCode
     * function 设置合同编号
     * return void
     */
    public void setContractCode(String ContractCode)
    {
        this.ContractCode = ContractCode;
    }

    /**
     * function 得到委托合同标识
     * return ConsignContractID
     *
    public long getConsignContractID()
    {
        return ConsignContractID;
    }

    /**
     * @param lID
     * function 设置委托合同标识
     * return void
     *
    public void setConsignContractID(long lID)
    {
        this.ConsignContractID = lID;
    }

    /**
     * function 得到委托合同编号
     * return ContractCode
     *
    public String getConsignContractCode()
    {
        return ConsignContractCode;
    }

    /**
     * @param Code
     * function 设置委托合同编号
     * return void
     *
    public void setConsignContractCode(String Code)
    {
        this.ConsignContractCode = Code;
    }


    /**
     * @param 
     * function 得到/设置
     * return double
     */
    public double getAmount()
    {
        return Amount;
    }

    /**
     * @param 
     * function 得到/设置
     * return Timestamp
     */
    public Timestamp getApplyDate()
    {
        return ApplyDate;
    }

    /**
     * @param 
     * function 得到/设置
     * return long
     */
    public long getClientID()
    {
        return ClientID;
    }

    /**
     * @param 
     * function 得到/设置
     * return String
     */
    public String getClientName()
    {
        return ClientName;
    }

    /**
     * @param 
     * function 得到/设置
     * return long
     */
    public long getLoanInstrType()
    {
        return LoanInstrType;
    }

    /**
     * @param 
     * function 得到/设置
     * return String
     */
    public String getStatus()
    {
        return Status;
    }

    /**
     * @param 
     * function 得到/设置
     * return long
     */
    public long getStatusID()
    {
        return StatusID;
    }

    /**
     * @param 
     * function 得到/设置
     * return long
     */
    public long getUserID()
    {
        return UserID;
    }

    /**
     * @param 
     * function 得到/设置
     * return String
     */
    public String getUserName()
    {
        return UserName;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setAmount(double d)
    {
        Amount = d;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setApplyDate(Timestamp timestamp)
    {
        ApplyDate = timestamp;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setClientID(long l)
    {
        ClientID = l;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setClientName(String string)
    {
        ClientName = string;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setLoanInstrType(long l)
    {
        LoanInstrType = l;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setStatus(String string)
    {
        Status = string;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setStatusID(long l)
    {
        StatusID = l;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setUserID(long l)
    {
        UserID = l;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setUserName(String string)
    {
        UserName = string;
    }

    /**
     * @param 
     * function 得到/设置
     * return long
     */
    public long getPageCount()
    {
        return PageCount;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setPageCount(long l)
    {
        PageCount = l;
    }

    /**
     * @param 
     * function 得到/设置
     * return long
     */
    public long getCurrencyID()
    {
        return CurrencyID;
    }

    /**
     * @param 
     * function 得到/设置
     * return long
     */
    public long getOfficeID()
    {
        return OfficeID;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setCurrencyID(long l)
    {
        CurrencyID = l;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setOfficeID(long l)
    {
        OfficeID = l;
    }

    /**
     * @param 
     * function 得到/设置
     * return String
     */
    public String getInCode()
    {
        return InCode;
    }

    /**
     * @param 
     * function 得到/设置
     * return long
     */
    public long getInID()
    {
        return InID;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setInCode(String string)
    {
        InCode = string;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setInID(long l)
    {
        InID = l;
    }

    /**
     * @param 
     * function 得到/设置
     * return long
     */
    public long getLoanID()
    {
        return LoanID;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setLoanID(long l)
    {
        LoanID = l;
    }

    /**
     * @param 
     * function 得到/设置
     * return String
     */
    public String getInstructionNo()
    {
        return InstructionNo;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setInstructionNo(String string)
    {
        InstructionNo = string;
    }

    /**
     * @param 
     * function 得到/设置
     * return long
     */
    public long getLoanTypeID()
    {
        return LoanTypeID;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setLoanTypeID(long l)
    {
        LoanTypeID = l;
    }

}
