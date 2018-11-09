
package com.iss.itreasury.loan.obinterface.dataentity;

import java.sql.*;
/**
 * @author ninghao
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class OBDocInfo
{
    private long ID; //标示
    private long TypeID;
    private long ParentTypeID;
    private long ParentID;
    private long FileID;
    private long StatusID;
    private long OrderNo;
    private String Remark;
    /**
     * @param 
     * function 得到/设置
     * return long
     */
    public long getFileID()
    {
        return FileID;
    }

    /**
     * @param 
     * function 得到/设置
     * return long
     */
    public long getID()
    {
        return ID;
    }

    /**
     * @param 
     * function 得到/设置
     * return long
     */
    public long getOrderNo()
    {
        return OrderNo;
    }

    /**
     * @param 
     * function 得到/设置
     * return long
     */
    public long getParentID()
    {
        return ParentID;
    }

    /**
     * @param 
     * function 得到/设置
     * return long
     */
    public long getParentTypeID()
    {
        return ParentTypeID;
    }

    /**
     * @param 
     * function 得到/设置
     * return String
     */
    public String getRemark()
    {
        return Remark;
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
    public long getTypeID()
    {
        return TypeID;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setFileID(long l)
    {
        FileID = l;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setID(long l)
    {
        ID = l;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setOrderNo(long l)
    {
        OrderNo = l;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setParentID(long l)
    {
        ParentID = l;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setParentTypeID(long l)
    {
        ParentTypeID = l;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setRemark(String string)
    {
        Remark = string;
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
    public void setTypeID(long l)
    {
        TypeID = l;
    }

}
