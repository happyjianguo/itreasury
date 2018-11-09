/*
 * atTermAwakeInfo.java
 *
 * Created on 2004年4月21日, 下午
 */

package com.iss.itreasury.loan.setting.dataentity;

import java.beans.*;

import java.sql.Timestamp;
import com.iss.itreasury.util.ITreasuryBaseDataEntity;
/**
 *
 * @author  ninh
 * @version
 */

public class ApprovalOpinionInfo extends ITreasuryBaseDataEntity
{
    //ID           NUMBER not null,
    private long id = -1;//审批意见标示
    //CODE         VARCHAR2(5),
    private String code = "";//审批意见编号
    //DESCRIPTION  VARCHAR2(500),
    private String description = "";//审批意见描述
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
    
	private long		nOfficeID				= -1 ;     //办事处
	private long 		nCurrencyID			    = -1 ;     //币种
    
	/**
     * @param 
     * function 得到/设置
     * return long
     */
    public long getNOfficeID()
    {
        return nOfficeID;
    }
    
	/**
     * @param 
     * function 得到/设置
     * return long
     */
    public long getNCurrencyID()
    {
        return nCurrencyID;
    }

	/**
     * @param 
     * function 得到/设置
     * return String
     */
    public String getCode()
    {
        return code;
    }

    /**
     * @param 
     * function 得到/设置
     * return String
     */
    public String getDescription()
    {
        return description;
    }

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
    public void setNOfficeID(long l)
    {
        nOfficeID = l;
        putUsedField("nOfficeID", nOfficeID);
    }
    
    
    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setNCurrencyID(long l)
    {
        nCurrencyID = l;
        putUsedField("nCurrencyID", nCurrencyID);
    }
    
    
    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setCode(String string)
    {
        code = string;
        putUsedField("code", code);
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setDescription(String string)
    {
        description = string;
        putUsedField("description", description);
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
     * return void
     */
    public void setInputDate(Timestamp timestamp)
    {
        inputDate = timestamp;
        putUsedField("inputDate", inputDate);
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setInputUserId(long l)
    {
        inputUserId = l;
        putUsedField("inputUserId", inputUserId);
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setStatusId(long l)
    {
        statusId = l;
        putUsedField("statusId", statusId);
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setUpdateDate(Timestamp timestamp)
    {
        updateDate = timestamp;
        putUsedField("updateDate", updateDate);
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

}