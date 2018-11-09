/*
 * Created on 2003-12-23
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.treasuryplan.awake.dataentity;

import java.util.HashMap;
import java.sql.*;

/**
 * @author wlming
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class AwakeInfo  implements java.io.Serializable
{
    private long OfficeID = -1;//办事处标识
    private long CurrencyID = -1;//币种标识
    private long DepartmentID = -1;//部门标识

    private Connection con = null;
    
    //业务提醒信息
    public static HashMap AwakeMSG = new HashMap();
    
    
    
    /**
     * function 得到/设置
     * return HashMap
     */
    public static HashMap getAwakeMSG()
    {
        return AwakeMSG;
    }

    /**
     * @param map
     * function 得到/设置
     * return void
     */
    public static void setAwakeMSG(HashMap map)
    {
        AwakeMSG = map;
    }

    /**
     * @param 
     * function 得到/设置
     * return long
     */
    public long getOfficeID()
    {
        return OfficeID;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setOfficeID(long l)
    {
        this.OfficeID = l;
    }

    /**
     * @param 
     * function 得到/设置
     * return long
     */
    public long getCurrencyID()
    {
        return CurrencyID;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setCurrencyID(long l)
    {
        this.CurrencyID = l;
    }

    /**
     * @param 
     * function 得到/设置
     * return Connection
     */
    public Connection getCon()
    {
        return con;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setCon(Connection connection)
    {
        this.con = connection;
    }

    /**
     * @return Returns the departmentID.
     */
    public long getDepartmentID()
    {
        return DepartmentID;
    }
    /**
     * @param departmentID The departmentID to set.
     */
    public void setDepartmentID(long departmentID)
    {
        DepartmentID = departmentID;
    }
}
