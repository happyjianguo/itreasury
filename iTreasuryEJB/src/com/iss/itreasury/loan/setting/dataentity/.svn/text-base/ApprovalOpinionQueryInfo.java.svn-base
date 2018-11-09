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

public class ApprovalOpinionQueryInfo extends ITreasuryBaseDataEntity
{
    //ID           NUMBER not null,
    private long id = -1;//审批意见标示
    //CODE         VARCHAR2(5),
    private String code = "";//审批意见编号
    //DESCRIPTION  VARCHAR2(500),
    private String description = "";//审批意见描述
    
    private long pageLineCount = -1;
    private long pageNo = -1;
    private long orderParam = -1;
    private long desc = -1;
    private String orderParamString = "";
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
     * return long
     */
    public long getDesc()
    {
        return desc;
    }

    /**
     * @param 
     * function 得到/设置
     * return long
     */
    public long getOrderParam()
    {
        return orderParam;
    }

    /**
     * @param 
     * function 得到/设置
     * return long
     */
    public long getPageLineCount()
    {
        return pageLineCount;
    }

    /**
     * @param 
     * function 得到/设置
     * return long
     */
    public long getPageNo()
    {
        return pageNo;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setDesc(long l)
    {
        desc = l;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setOrderParam(long l)
    {
        orderParam = l;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setPageLineCount(long l)
    {
        pageLineCount = l;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setPageNo(long l)
    {
        pageNo = l;
    }

    /**
     * @param 
     * function 得到/设置
     * return String
     */
    public String getOrderParamString()
    {
        return orderParamString;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setOrderParamString(String string)
    {
        orderParamString = string;
    }

}