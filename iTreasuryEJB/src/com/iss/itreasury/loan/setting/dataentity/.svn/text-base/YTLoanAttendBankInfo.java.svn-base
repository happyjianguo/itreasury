/*
 * YTLoanAttendBankInfo.java
 *
 * Created on 2004年5月31日, 下午
 */

package com.iss.itreasury.loan.setting.dataentity;

import java.sql.Timestamp;
import com.iss.itreasury.util.ITreasuryBaseDataEntity;
/**
 *
 * @author  ninh
 * @version
 */

public class YTLoanAttendBankInfo extends ITreasuryBaseDataEntity 
{
    private long id = -1;  
    //NAME             VARCHAR2(100),
    private String name = "";//
    //BANK             VARCHAR2(100),   
    private String bank = "";//
    //BANKACCOUNTNO    VARCHAR2(100),
    private String bankAccountNo = "";//
    //OTHERGLSUBJECTCODE    VARCHAR2(100),
    private String otherGLSubjectCode = "";//
    //STATUSID     NUMBER,
    private long isLeaderBank = -1;//
    //STATUSID     NUMBER,
    private long statusId = -1;//状态 是否有效
    //INPUTUSERID  NUMBER,
    private long inputUserId = -1;//录入人
    //INPUTDATE    DATE,
    private Timestamp inputDate = null;//录入时间
    //UPDATEUSERID NUMBER,
    private long updateUserId = -1;//修改人
    //UPDATEDATE   DATE
    private Timestamp updateDate = null;//修改时间
    //modify by xwhe 2007-08-06
    private long currencyID = -1;				//币种
    private long officeID = -1;					//办事处
    
    /**
     * @param 
     * function 得到/设置
     * return long
     */
    public long getId()
    {
        return id;
    }
    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setId(long l)
    {
        id = l;
        putUsedField("id", id);
    }
    /**
     * @param 
     * function 得到/设置
     * return String
     */
    public String getBank()
    {
        return bank;
    }

    /**
     * @param 
     * function 得到/设置
     * return String
     */
    public String getBankAccountNo()
    {
        return bankAccountNo;
    }

    /**
     * @param 
     * function 得到/设置
     * return Timestamp
     */
    public Timestamp getInputDate()
    {
        return inputDate;
    }

    /**
     * @param 
     * function 得到/设置
     * return long
     */
    public long getInputUserId()
    {
        return inputUserId;
    }

    /**
     * @param 
     * function 得到/设置
     * return long
     */
    public long getIsLeaderBank()
    {
        return isLeaderBank;
    }

    /**
     * @param 
     * function 得到/设置
     * return String
     */
    public String getName()
    {
        return name;
    }

    /**
     * @param 
     * function 得到/设置
     * return long
     */
    public String getOtherGLSubjectCode()
    {
        return otherGLSubjectCode;
    }

    /**
     * @param 
     * function 得到/设置
     * return long
     */
    public long getStatusId()
    {
        return statusId;
    }

    /**
     * @param 
     * function 得到/设置
     * return Timestamp
     */
    public Timestamp getUpdateDate()
    {
        return updateDate;
    }

    /**
     * @param 
     * function 得到/设置
     * return long
     */
    public long getUpdateUserId()
    {
        return updateUserId;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setBank(String string)
    {
        bank = string;
        putUsedField("bank",bank);
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setBankAccountNo(String string)
    {
        bankAccountNo = string;
        putUsedField("bankAccountNo",bankAccountNo);
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setInputDate(Timestamp timestamp)
    {
        inputDate = timestamp;
        putUsedField("inputDate",inputDate);
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setInputUserId(long l)
    {
        inputUserId = l;
        putUsedField("inputUserId",inputUserId);
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setIsLeaderBank(long l)
    {
        isLeaderBank = l;
        putUsedField("isLeaderBank",isLeaderBank);
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setName(String string)
    {
        name = string;
        putUsedField("name",name);
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setOtherGLSubjectCode(String s)
    {
        otherGLSubjectCode = s;
        putUsedField("otherGLSubjectCode",otherGLSubjectCode);
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setStatusId(long l)
    {
        statusId = l;
        putUsedField("statusId",statusId);
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setUpdateDate(Timestamp timestamp)
    {
        updateDate = timestamp;
        putUsedField("updateDate",updateDate);
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setUpdateUserId(long l)
    {
        updateUserId = l;
        putUsedField("updateUserId", updateUserId);
    }
	public long getCurrencyID() {
		return currencyID;
	}
	public void setCurrencyID(long currencyID) {
		this.currencyID = currencyID;
		putUsedField("currencyID", currencyID);
	}
	public long getOfficeID() {
		return officeID;
	}
	public void setOfficeID(long officeID) {
		this.officeID = officeID;
		putUsedField("officeID", officeID);
	}

}