/*
 * Created on 2003-12-23
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.ebank.obawake.dataentity;

import java.util.HashMap;
import java.sql.*;

/**
 * @author wlming
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class OBAwakeCondition  implements java.io.Serializable
{
    private long ClientID = -1;//�ͻ���ʶ
    
    private long CurrencyID = -1;//���ֱ�ʶ
    private long UserID = -1;
    private long OfficeID = -1;//���´���ʶ��δʹ��
    
    //Modify by leiyang date 2007/06/23
    private long OperationFate = 0;  //������������

    private Connection con = null;
    
	long[] lAwakeDays1 = { 0, 0, 0, 0 };
	long[] lAheadAwakeDays1 = { 10, 25, 10, 30 };
    
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
    public long getClientID()
    {
        return ClientID;
    }

    /**
     * @param 
     * function �õ�/����
     * return void
     */
    public void setClientID(long l)
    {
        this.ClientID = l;
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
	 * @return
	 */
	public long[] getAheadAwakeDays1()
	{
		return lAheadAwakeDays1;
	}

	/**
	 * @return
	 */
	public long[] getAwakeDays1()
	{
		return lAwakeDays1;
	}

	/**
	 * @param ls
	 */
	public void setAheadAwakeDays1(long[] ls)
	{
		lAheadAwakeDays1 = ls;
	}

	/**
	 * @param ls
	 */
	public void setAwakeDays1(long[] ls)
	{
		lAwakeDays1 = ls;
	}

    /**
     * @return Returns the userID.
     */
    public long getUserID() {
        return UserID;
    }
    /**
     * @param userID The userID to set.
     */
    public void setUserID(long userID) {
        UserID = userID;
    }
    
    /**
     * @return Returns the officeID.
     */
    public long getOfficeID() {
        return OfficeID;
    }
    /**
     * @param officeID The officeID to set.
     */
    public void setOfficeID(long officeID) {
        OfficeID = officeID;
    }

	public long getOperationFate() {
		return OperationFate;
	}

	public void setOperationFate(long operationFate) {
		OperationFate = operationFate;
	}
}
