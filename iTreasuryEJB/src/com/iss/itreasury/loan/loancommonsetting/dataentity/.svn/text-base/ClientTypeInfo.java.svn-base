/*
 * CustomerTypeInfo.java
 *
 * Created on 2002年2月19日, 下午5:53
 */

package com.iss.itreasury.loan.loancommonsetting.dataentity;

//import java.beans.*;
import java.sql.Timestamp;

/**
 *
 * @author  yzhang
 * @version
 */
public class ClientTypeInfo implements java.io.Serializable {

    /**
     * CustomerTypeInfo 构造子注解。
     * use the DB Table  :   SETT_ClientType
     */
    public ClientTypeInfo()
    {
        super();
    }

    //ID:标识
    private long ClientTypeID;

    //sCode:编码
    private String Code;

    //sName:名称
    private String Name;

    //nInputUserID:录入人标识
    private long InputUserID;

    //录入人名称
    private String InputUserName;

    //dtInput:录入日期
    private Timestamp dtInput;
    
    //nUpdateUserID:更改人标识
    private long UpdateUserID;

    //更改人姓名
    private String UpdateUserName;

    //dtUpdate:更改日期
    private Timestamp dtUpdate;

    //nStatusID:状态：是否可用
    private long StatusID;

    //分页显示总页数记录
    private long PageCount;

    /**
     * function 得到标识
     * return lID
     */
    public long getClientTypeID()
    {
        return ClientTypeID;
    }

    /**
     * @param lID
     * function 设置标识
     * return void
     */
    public void setClientTypeID(long lID)
    {
        this.ClientTypeID = lID;
    }

    /**
     * function 得到编码
     * return Code
     */
    public String getCode()
    {
        return Code;
    }

    /**
     * @param strCode
     * function 设置编码
     * return void
     */
    public void setCode(String strCode)
    {
        this.Code = strCode;
    }

    /**
     * function 得到名称
     * return Name
     */
    public String getName()
    {
        return Name;
    }

    /**
     * @param strName
     * function 得到/设置
     * return void
     */
    public void setName(String strName)
    {
        this.Name = strName;
    }

    /**
     * function 得到录入人名称
     * return InputUserName
     */
    public String getInputUserName()
    {
        return InputUserName;
    }

    /**
     * @param strInputUserName
     * function 得到/设置录入人名称
     * return void
     */
    public void setInputUserName(String strInputUserName)
    {
        this.InputUserName = strInputUserName;
    }

    /**
     * function 得到录入时间
     * return dtInput
     */
    public Timestamp getDtInput()
    {
        return dtInput;
    }

    /**
     * @param dtInput
     * function 设置录入时间
     * return void
     */
    public void setDtInput(Timestamp dtInput)
    {
        this.dtInput = dtInput;
    }

    /**
     * function 得到页数
     * return lPageCount
     */
    public long getPageCount()
    {
        return PageCount;
    }

    /**
     * @param lPageCount
     * function设置页数
     * return void
     */
    public void setPageCount(long lPageCount)
    {
        this.PageCount = lPageCount;
    }

}
