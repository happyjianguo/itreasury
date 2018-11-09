/*
 * Created on 2003-9-12
 *
 */
package com.iss.itreasury.settlement.generalledger.dataentity;

import java.sql.Timestamp;
import com.iss.itreasury.settlement.util.*;
import java.io.Serializable;
/**
 *
 *  会计分录
 *  @author yqwu
 *
 */
public class GLEntryInfo implements Serializable
{
    long ID = -1;
    
	long OfficeID = -1;//机构ID
	long CurrencyID = -1;//币种ID
	String SubjectCode = "";//科目代码
	String TransNo = "";//交易编号
	long TransactionTypeID = -1;//交易类型
	long TransDirection = -1;//交易方向
	double Amount = 0.0;//交易发生额
	Timestamp Execute = null;//执行日
	Timestamp InterestStart = null;//起息日
	String Abstract = "";//摘要
	String MultiCode = "";//多维码
	long InputUserID = -1;//录入人
	long CheckUserID = -1;//复核人
	long StatusID = -1;//状态
	
	//保留
	long Group = -1;
	long Type = -1;
	long PostStatusID = -1;
	
	//辅助属性
	String OfficeName = "";//机构名称
	String CurrencyName = "";//币种名称
	String TransDirectionName = "";//交易方向名称
	String InputUserName = "";//录入人
	String CheckUserName = "";//复核人
	String StatusName = "";//状态名称
	
	//辅助核算信息
	String assitantName = ""; //辅助核算名称
	String assitantValue = ""; //辅助核算值（取客户编号"-"后的串 如：01-000001 取“000001”）

	//新奥增加客户信息
	String clientCode = "";//客户编号
	String clientName = "";//客户名称
	String clientShortName = "";//客户简称

    /**
     * @return
     */
    public String getAbstract()
    {
        return Abstract;
    }

    /**
     * @return
     */
    public double getAmount()
    {
        return Amount;
    }

    /**
     * @return
     */
    public long getCheckUserID()
    {
        return CheckUserID;
    }

    /**
     * @return
     */
    public long getCurrencyID()
    {
        return CurrencyID;
    }

    /**
     * @return
     */
    public Timestamp getExecute()
    {
        return Execute;
    }

    /**
     * @return
     */
    public long getGroup()
    {
        return Group;
    }

    /**
     * @return
     */
    public long getID()
    {
        return ID;
    }

    /**
     * @return
     */
    public long getInputUserID()
    {
        return InputUserID;
    }

    /**
     * @return
     */
    public Timestamp getInterestStart()
    {
        return InterestStart;
    }

    /**
     * @return
     */
    public String getMultiCode()
    {
        return MultiCode;
    }

    /**
     * @return
     */
    public long getOfficeID()
    {
        return OfficeID;
    }

    /**
     * @return
     */
    public long getStatusID()
    {
        return StatusID;
    }

    /**
     * @return
     */
    public String getSubjectCode()
    {
        return SubjectCode;
    }

    /**
     * @return
     */
    public long getTransactionTypeID()
    {
        return TransactionTypeID;
    }

    /**
     * @return
     */
    public long getTransDirection()
    {
        return TransDirection;
    }

    /**
     * @return
     */
    public String getTransNo()
    {
        return TransNo;
    }

    /**
     * @return
     */
    public long getType()
    {
        return Type;
    }

    /**
     * @param string
     */
    public void setAbstract(String string)
    {
        Abstract = string;
    }

    /**
     * @param d
     */
    public void setAmount(double d)
    {
        Amount = d;
    }

    /**
     * @param l
     */
    public void setCheckUserID(long l)
    {
        CheckUserID = l;
    }

    /**
     * @param l
     */
    public void setCurrencyID(long l)
    {
        CurrencyID = l;
        
		this.CurrencyName=SETTConstant.CurrencyType.getName(l);
    }

    /**
     * @param Timestamp
     */
    public void setExecute(Timestamp date)
    {
        Execute = date;
    }

    /**
     * @param l
     */
    public void setGroup(long l)
    {
        Group = l;
    }

    /**
     * @param l
     */
    public void setID(long l)
    {
        ID = l;
    }

    /**
     * @param l
     */
    public void setInputUserID(long l)
    {
        InputUserID = l;
    }

    /**
     * @param Timestamp
     */
    public void setInterestStart(Timestamp date)
    {
        InterestStart = date;
    }

    /**
     * @param l
     */
    public void setMultiCode(String str)
    {
        MultiCode = str;
    }

    /**
     * @param l
     */
    public void setOfficeID(long l)
    {
        OfficeID = l;
        
		//在NameRef相应方法取消Exception后取消try block
//		try
//		{
//			NameRef.getOfficeNameByID(l);
//		}
//		catch (Exception e)
//		{
//			e.printStackTrace();
//		}        
    }

    /**
     * @param l
     */
    public void setStatusID(long l)
    {
        StatusID = l;
        this.StatusName=SETTConstant.EntryStatus.getName(l);
    }

    /**
     * @param l
     */
    public void setSubjectCode(String str)
    {
        SubjectCode = str;
    }

    /**
     * @param l
     */
    public void setTransactionTypeID(long l)
    {
        TransactionTypeID = l;
        this.TransDirectionName=SETTConstant.DebitOrCredit.getName(l);
    }

    /**
     * @param l
     */
    public void setTransDirection(long l)
    {
        TransDirection = l;
    }

    /**
     * @param l
     */
    public void setTransNo(String str)
    {
        TransNo = str;
    }

    /**
     * @param l
     */
    public void setType(long l)
    {
        Type = l;
    }

    /**
     * @return
     */
    public String getCheckUserName()
    {
        return CheckUserName;
    }

    /**
     * @return
     */
    public String getCurrencyName()
    {
        return CurrencyName;
    }

    /**
     * @return
     */
    public String getInputUserName()
    {
        return InputUserName;
    }

    /**
     * @return
     */
    public String getOfficeName()
    {
        return OfficeName;
    }

    /**
     * @return
     */
    public String getStatusName()
    {
        return StatusName;
    }

    /**
     * @return
     */
    public String getTransDirectionName()
    {
        return TransDirectionName;
    }

	/**
	 * @return
	 */
	public long getPostStatusID()
	{
		return PostStatusID;
	}

	/**
	 * @param l
	 */
	public void setPostStatusID(long l)
	{
		PostStatusID = l;
	}

	public String getAssitantName()
	{
		return assitantName;
	}

	public void setAssitantName(String assitantName)
	{
		this.assitantName = assitantName;
	}

	public String getAssitantValue()
	{
		return assitantValue;
	}

	public void setAssitantValue(String assitantValue)
	{
		this.assitantValue = assitantValue;
	}
	
	public String getClientCode() {
		return clientCode;
	}

	public void setClientCode(String clientCode) {
		this.clientCode = clientCode;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getClientShortName() {
		return clientShortName;
	}

	public void setClientShortName(String clientShortName) {
		this.clientShortName = clientShortName;
	}

	public void setInputUserName(String inputUserName) {
		InputUserName = inputUserName;
	}

	public void setCheckUserName(String checkUserName) {
		CheckUserName = checkUserName;
	}

	
}