/*
 * LoanDrawNoticeInfo.java
 *
 * Created on 2002年4月15日
 */

package com.iss.itreasury.loan.loandrawnotice.dataentity;

import java.rmi.RemoteException;
import java.beans.*;
import java.sql.*;
import java.util.*;

import com.iss.itreasury.system.approval.dataentity.InutParameterInfo;

/**
 *
 * @author
 * @version
 */
public class LoanDrawNoticeInfo implements java.io.Serializable
{
	/** Creates new LoanDrawNoticeInfo */
	public LoanDrawNoticeInfo()
	{
		super();
	}

	private long ID = -1;                    //银团提款标识
	private String Code = "";                //代码，提款通知单编号
	private double DrawAmount = 0;			 //本期总提款金额
    private double DrawAgentAmount = 0;      //本期代理费总计
	private long ContractID = -1;            //合同ID	
    private String ContractCode = "";        //合同编号 
    private String ClientName = "";          //贷款单位
    private double ContractAmount = 0;       //合同金额
    private double AgentRate = 0;            //手续费率
	private long StatusID = -1;              //提款通知单状态
	private long InputUserID = -1;           //录入用户ID
    private String InputUserName = "";       //录入人
	private Timestamp InputDate = null;      //录入日期，提交日期
	private long NextCheckUserID = -1;       //下一个审核人ID
    private String CheckUserName = "";       //审核人
    private long NextCheckLevel = -1;		 //下一个审核级别

	private long Count = -1; // 记录数
	private long PageCount = -1; // 记录总页数
	
	private Collection YTDrawInfo = null;//银团提款单明细信息
	

	private InutParameterInfo inutParameterInfo=null;
	

	public InutParameterInfo getInutParameterInfo() {
		return inutParameterInfo;
	}

	public void setInutParameterInfo(InutParameterInfo inutParameterInfo) {
		this.inutParameterInfo = inutParameterInfo;
	}

    
    /**
     * function 得到/设置银团提款标识
     * return long
     */
    public long getID()
    {
        return ID;
    }

    /**
     * @param l
     * function 得到/设置银团提款标识
     * return void
     */
    public void setID(long l)
    {
        this.ID = l;
    }

    /**
     * function 得到/设置提款通知单编号
     * return String
     */
    public String getCode()
    {
        return Code;
    }

    /**
     * @param s
     * function 得到/设置提款通知单编号
     * return void
     */
    public void setCode(String s)
    {
        this.Code = s;
    }

    /**
     * function 得到/设置本期总提款金额
     * return double
     */
    public double getDrawAmount()
    {
        return DrawAmount;
    }

    /**
     * @param d
     * function 得到/设置本期总提款金额
     * return void
     */
    public void setDrawAmount(double d)
    {
        this.DrawAmount = d;
    }

    /**
     * function 得到/设置本期代理费总计
     * return double
     */
    public double getDrawAgentAmount()
    {
        return DrawAgentAmount;
    }

    /**
     * @param d
     * function 得到/设置本期代理费总计
     * return void
     */
    public void setDrawAgentAmount(double d)
    {
        this.DrawAgentAmount = d;
    }

    /**
     * function 得到/设置合同编号 
     * return String
     */
    public String getContractCode()
    {
        return ContractCode;
    }

    /**
     * @param s
     * function 得到/设置合同编号 
     * return void
     */
    public void setContractCode(String s)
    {
        this.ContractCode = s;
    }

    /**
     * function 得到/设置贷款单位
     * return String
     */
    public String getClientName()
    {
        return ClientName;
    }

    /**
     * @param s
     * function 得到/设置贷款单位
     * return void
     */
    public void setClientName(String s)
    {
        this.ClientName = s;
    }

    /**
     * function 得到/设置合同金额
     * return double
     */
    public double getContractAmount()
    {
        return ContractAmount;
    }

    /**
     * @param d
     * function 得到/设置合同金额
     * return void
     */
    public void setContractAmount(double d)
    {
        this.ContractAmount = d;
    }

    /**
     * function 得到/设置提款通知单状态
     * return long
     */
    public long getStatusID()
    {
        return StatusID;
    }

    /**
     * @param l
     * function 得到/设置提款通知单状态
     * return void
     */
    public void setStatusID(long l)
    {
        this.StatusID = l;
    }

    /**
     * function 得到/设置录入日期，提交日期
     * return Timestamp InputDate
     */
    public Timestamp getInputDate()
    {
        return InputDate;
    }

    /**
     * @param ts
     * function 得到/设置录入日期，提交日期
     * return void
     */
    public void setInputDate(Timestamp ts)
    {
        this.InputDate = ts;
    }

    /**
     * function 得到/设置录入用户ID
     * return long
     */
    public long getInputUserID()
    {
        return InputUserID;
    }

    /**
     * @param l
     * function 得到/设置录入用户ID
     * return void
     */
    public void setInputUserID(long l)
    {
        this.InputUserID = l;
    }

    /**
     * function 得到/设置录入人
     * return String
     */
    public String getInputUserName()
    {
        return InputUserName;
    }

    /**
     * @param s
     * function 得到/设置录入人
     * return void
     */
    public void setInputUserName(String s)
    {
        this.InputUserName = s;
    }

    /**
     * function 得到/设置下一个审核人ID
     * return long
     */
    public long getNextCheckUserID()
    {
        return NextCheckUserID;
    }

    /**
     * @param l
     * function 得到/设置下一个审核人ID
     * return void
     */
    public void setNextCheckUserID(long l)
    {
        this.NextCheckUserID = l;
    }

    /**
     * function 得到/设置审核人
     * return String
     */
    public String getCheckUserName()
    {
        return CheckUserName;
    }

    /**
     * @param s
     * function 得到/设置审核人
     * return void
     */
    public void setCheckUserName(String s)
    {
        this.CheckUserName = s;
    }

    /**
     * function 得到/设置记录数
     * return long
     */
    public long getCount()
    {
        return Count;
    }

    /**
     * @param l
     * function 得到/设置记录数
     * return void
     */
    public void setCount(long l)
    {
        this.Count = l;
    }

    /**
     * function 得到/设置记录总页数
     * return long
     */
    public long getPageCount()
    {
        return PageCount;
    }

    /**
     * @param l
     * function 得到/设置记录总页数
     * return void
     */
    public void setPageCount(long l)
    {
        this.PageCount = l;
    }

    /**
     * function 得到/设置合同ID   
     * return long
     */
    public long getContractID()
    {
        return ContractID;
    }

    /**
     * @param l
     * function 得到/设置合同ID   
     * return void
     */
    public void setContractID(long l)
    {
        this.ContractID = l;
    }

    /**
     * function 得到/设置手续费率
     * return double
     */
    public double getAgentRate()
    {
        return AgentRate;
    }

    /**
     * @param d
     * function 得到/设置手续费率
     * return void
     */
    public void setAgentRate(double d)
    {
        this.AgentRate = d;
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
	 * @return
	 */
	public Collection getYTDrawInfo()
	{
		return YTDrawInfo;
	}

	/**
	 * @param collection
	 */
	public void setYTDrawInfo(Collection collection)
	{
		YTDrawInfo = collection;
	}

}