/*
 * Created on 2005-9-15
 *
 */
package com.iss.itreasury.settlement.bankinterface.dataentity;

import java.sql.Timestamp;
import com.iss.itreasury.settlement.util.*;

import java.io.Serializable;
/**
 *
 *  企业对账凭证
 *  @author qijiang
 *
 */
public class BankAccountMappingInfo implements Serializable
{
    long ID = -1;
    
    String accountNo = "";
	String SubjectCode = "";//科目代码
	long TransDirection = -1;//交易方向
	double Amount = 0.0;//交易发生额
	Timestamp transDate = null;//交易发生日期
	String Abstract = "";//摘要
	long StatusID = -1;//对账状态
	long MappingID = -1; //匹配号
	
	
	//辅助属性
	String OfficeName = "";//机构名称
	String CurrencyName = "";//币种名称
	String TransDirectionName = "";//交易方向名称
	String StatusName = "";//状态名称	
	
	

    public BankAccountMappingInfo() {
		super();
	}

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
    public long getID()
    {
        return ID;
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
    public long getTransDirection()
    {
        return TransDirection;
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
    public void setID(long l)
    {
        ID = l;
    }

    /**
     * @param l
     */
    public void setStatusID(long lStatusID)
    {
        StatusID = lStatusID;
        this.StatusName=SETTConstant.CheckAccountBookStatus.getName(lStatusID);
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
    public void setTransDirection(long l)
    {
        TransDirection = l;
        this.TransDirectionName = SETTConstant.DebitOrCredit.getName(l);
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
	 * @return Returns the mappingID.
	 */
	public long getMappingID() {
		return MappingID;
	}

	/**
	 * @param mappingID The mappingID to set.
	 */
	public void setMappingID(long mappingID) {
		MappingID = mappingID;
	}

	/**
	 * @return Returns the accountNo.
	 */
	public String getAccountNo() {
		return accountNo;
	}

	/**
	 * @param accountNo The accountNo to set.
	 */
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	/**
	 * @return Returns the transDate.
	 */
	public Timestamp getTransDate() {
		return transDate;
	}

	/**
	 * @param transDate The transDate to set.
	 */
	public void setTransDate(Timestamp transDate) {
		this.transDate = transDate;
	}

}