
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
    private long ID; //��ʾ
    private long TypeID;
    private long ParentTypeID;
    private long ParentID;
    private long FileID;
    private long StatusID;
    private long OrderNo;
    private String Remark;
    /**
     * @param 
     * function �õ�/����
     * return long
     */
    public long getFileID()
    {
        return FileID;
    }

    /**
     * @param 
     * function �õ�/����
     * return long
     */
    public long getID()
    {
        return ID;
    }

    /**
     * @param 
     * function �õ�/����
     * return long
     */
    public long getOrderNo()
    {
        return OrderNo;
    }

    /**
     * @param 
     * function �õ�/����
     * return long
     */
    public long getParentID()
    {
        return ParentID;
    }

    /**
     * @param 
     * function �õ�/����
     * return long
     */
    public long getParentTypeID()
    {
        return ParentTypeID;
    }

    /**
     * @param 
     * function �õ�/����
     * return String
     */
    public String getRemark()
    {
        return Remark;
    }

    /**
     * @param 
     * function �õ�/����
     * return long
     */
    public long getStatusID()
    {
        return StatusID;
    }

    /**
     * @param 
     * function �õ�/����
     * return long
     */
    public long getTypeID()
    {
        return TypeID;
    }

    /**
     * @param 
     * function �õ�/����
     * return void
     */
    public void setFileID(long l)
    {
        FileID = l;
    }

    /**
     * @param 
     * function �õ�/����
     * return void
     */
    public void setID(long l)
    {
        ID = l;
    }

    /**
     * @param 
     * function �õ�/����
     * return void
     */
    public void setOrderNo(long l)
    {
        OrderNo = l;
    }

    /**
     * @param 
     * function �õ�/����
     * return void
     */
    public void setParentID(long l)
    {
        ParentID = l;
    }

    /**
     * @param 
     * function �õ�/����
     * return void
     */
    public void setParentTypeID(long l)
    {
        ParentTypeID = l;
    }

    /**
     * @param 
     * function �õ�/����
     * return void
     */
    public void setRemark(String string)
    {
        Remark = string;
    }

    /**
     * @param 
     * function �õ�/����
     * return void
     */
    public void setStatusID(long l)
    {
        StatusID = l;
    }

    /**
     * @param 
     * function �õ�/����
     * return void
     */
    public void setTypeID(long l)
    {
        TypeID = l;
    }

}
