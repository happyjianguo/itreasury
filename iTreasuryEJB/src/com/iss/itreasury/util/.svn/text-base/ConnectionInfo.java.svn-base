/*
 * Created on 2005-3-11
 * 
 * TODO To change the template for this generated file go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.util;

import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.sql.Connection;

/**
 * @author yychen
 * 弱引用实现，不会造成connection的悬挂
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class ConnectionInfo implements Serializable
{
    
    private WeakReference connectionReference=null;

    private long currentTimeMillis = -1;

    /**
     * @return Returns the conn.
     */
    public Connection getConn()
    {
        if(connectionReference==null){
            return null;
        }
        return (Connection)connectionReference.get();
    }

    /**
     * @param conn
     *            The conn to set.
     */
    public void setConn(Connection conn)
    {
        if(conn==null){
            connectionReference=null;
        }
        else{
            connectionReference=new WeakReference(conn);
        }
    }

    /**
     * @return Returns the currentTimeMillis.
     */
    public long getCurrentTimeMillis()
    {
        return currentTimeMillis;
    }

    /**
     * @param currentTimeMillis
     *            The currentTimeMillis to set.
     */
    public void setCurrentTimeMillis()
    {
        this.currentTimeMillis = System.currentTimeMillis();
    }

    /**
     * @return Returns the date.
     */
    public long getIntervalMinute()
    {
        //System.out.println("有连接需要释放:" + System.currentTimeMillis() + "=====" + currentTimeMillis);
        return (System.currentTimeMillis() - currentTimeMillis) / 60000;
    }

    /**
     * @return Returns the conn.
     */
    public void CloseConn()
    {
        try
        {
            if(connectionReference==null){
                return;
            }
            else if(connectionReference.get()==null){
                connectionReference=null;
                return;
            }
            else{
                ((Connection)connectionReference.get()).close();
                connectionReference=null;
            }
        }
        catch (Exception e)
        {

        }
    }

}