package com.iss.itreasury.loan.obinterface.dataentity;
import java.sql.*;
/**
 * @author yzhang
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class AcceptInfo implements java.io.Serializable
{
    /**
     * AcceptInfo 构造子注解。
     */
    public AcceptInfo()
    {
        super();
    }
    
    private long Type = -1;             //列的数据类型
    
    private String ColumnName = "";     //列名
    
    private Object ColumnValue = null;  //列值
    /**
     * @param 
     * function 得到/设置
     * return String
     */
    public String getColumnName()
    {
        return ColumnName;
    }

    /**
     * @param 
     * function 得到/设置
     * return Object
     */
    public Object getColumnValue()
    {
        return ColumnValue;
    }

    /**
     * @param 
     * function 得到/设置
     * return long
     */
    public long getType()
    {
        return Type;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setColumnName(String string)
    {
        ColumnName = string;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setColumnValue(Object object)
    {
        ColumnValue = object;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setType(long l)
    {
        Type = l;
    }

}
