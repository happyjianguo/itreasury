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
    private long OfficeID = -1;//���´���ʶ
    private long CurrencyID = -1;//���ֱ�ʶ
    private long DepartmentID = -1;//���ű�ʶ

    private Connection con = null;
    
    //ҵ��������Ϣ
    public static HashMap AwakeMSG = new HashMap();
    
    
    
    /**
     * function �õ�/����
     * return HashMap
     */
    public static HashMap getAwakeMSG()
    {
        return AwakeMSG;
    }

    /**
     * @param map
     * function �õ�/����
     * return void
     */
    public static void setAwakeMSG(HashMap map)
    {
        AwakeMSG = map;
    }

    /**
     * @param 
     * function �õ�/����
     * return long
     */
    public long getOfficeID()
    {
        return OfficeID;
    }

    /**
     * @param 
     * function �õ�/����
     * return void
     */
    public void setOfficeID(long l)
    {
        this.OfficeID = l;
    }

    /**
     * @param 
     * function �õ�/����
     * return long
     */
    public long getCurrencyID()
    {
        return CurrencyID;
    }

    /**
     * @param 
     * function �õ�/����
     * return void
     */
    public void setCurrencyID(long l)
    {
        this.CurrencyID = l;
    }

    /**
     * @param 
     * function �õ�/����
     * return Connection
     */
    public Connection getCon()
    {
        return con;
    }

    /**
     * @param 
     * function �õ�/����
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
