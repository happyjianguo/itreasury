/*
 * atTermAwakeInfo.java
 *
 * Created on 2004��4��21��, ����
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
    private long id = -1;//���������ʾ
    //CODE         VARCHAR2(5),
    private String code = "";//����������
    //DESCRIPTION  VARCHAR2(500),
    private String description = "";//�����������
    
    private long pageLineCount = -1;
    private long pageNo = -1;
    private long orderParam = -1;
    private long desc = -1;
    private String orderParamString = "";
    /**
     * @param 
     * function �õ�/����
     * return String
     */
    public String getCode()
    {
        return code;
    }

    /**
     * @param 
     * function �õ�/����
     * return String
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * @param 
     * function �õ�/����
     * return long
     */
    public long getId()
    {
        return id;
    }

    /**
     * @param 
     * function �õ�/����
     * return void
     */
    public void setCode(String string)
    {
        code = string;
        putUsedField("code", code);
    }

    /**
     * @param 
     * function �õ�/����
     * return void
     */
    public void setDescription(String string)
    {
        description = string;
        putUsedField("description", description);
    }

    /**
     * @param 
     * function �õ�/����
     * return void
     */
    public void setId(long l)
    {
        id = l;
        putUsedField("id", id);
    }

    /**
     * @param 
     * function �õ�/����
     * return long
     */
    public long getDesc()
    {
        return desc;
    }

    /**
     * @param 
     * function �õ�/����
     * return long
     */
    public long getOrderParam()
    {
        return orderParam;
    }

    /**
     * @param 
     * function �õ�/����
     * return long
     */
    public long getPageLineCount()
    {
        return pageLineCount;
    }

    /**
     * @param 
     * function �õ�/����
     * return long
     */
    public long getPageNo()
    {
        return pageNo;
    }

    /**
     * @param 
     * function �õ�/����
     * return void
     */
    public void setDesc(long l)
    {
        desc = l;
    }

    /**
     * @param 
     * function �õ�/����
     * return void
     */
    public void setOrderParam(long l)
    {
        orderParam = l;
    }

    /**
     * @param 
     * function �õ�/����
     * return void
     */
    public void setPageLineCount(long l)
    {
        pageLineCount = l;
    }

    /**
     * @param 
     * function �õ�/����
     * return void
     */
    public void setPageNo(long l)
    {
        pageNo = l;
    }

    /**
     * @param 
     * function �õ�/����
     * return String
     */
    public String getOrderParamString()
    {
        return orderParamString;
    }

    /**
     * @param 
     * function �õ�/����
     * return void
     */
    public void setOrderParamString(String string)
    {
        orderParamString = string;
    }

}