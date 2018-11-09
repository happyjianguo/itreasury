/*
 * Created on 2004-7-12
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.system.user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.util.Database;

/**
 * @author yiwang
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class UserInfoDAO extends ITreasuryDAO
{
	
	/**
	 * 
	 */
	public UserInfoDAO ( )
	{
		super ("UserInfo" ) ;
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param tableName
	 */
	public UserInfoDAO ( String tableName )
	{
		super ( tableName ) ;
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param tableName
	 * @param conn
	 */
	public UserInfoDAO ( String tableName , Connection conn )
	{
		super ( tableName , conn ) ;
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param isNeedPrefix
	 */
	public UserInfoDAO ( boolean isNeedPrefix )
	{
		super ( isNeedPrefix ) ;
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param tableName
	 * @param isNeedPrefix
	 */
	public UserInfoDAO ( String tableName , boolean isNeedPrefix )
	{
		super ( tableName , isNeedPrefix ) ;
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param tableName
	 * @param isNeedPrefix
	 * @param conn
	 */
	public UserInfoDAO ( String tableName , boolean isNeedPrefix , Connection conn )
	{
		super ( tableName , isNeedPrefix , conn ) ;
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * add by fxzhang 2007-1-8
	 * @param lUserID
	 * @return
	 * @throws Exception 
	 */
	public String getNameByID ( long lUserID ) throws Exception
	{
		String name = "";
		StringBuffer strSQL = new StringBuffer();	
		Connection conn = null;
		PreparedStatement pstm  = null;
		ResultSet rs = null;
		strSQL.append("select SNAME from  USERINFO where ID=?");
		try
        {          
			conn = Database.getConnection();
            pstm = conn.prepareStatement(strSQL.toString());
            pstm.setLong(1,lUserID);
            rs = pstm.executeQuery();
            if( rs.next() )
            {
            	name = rs.getString(1);
            }
            
        } 
        catch (ITreasuryDAOException e) 
        {
        	e.printStackTrace();
        	throw e;
        }
        finally
        {
        	try 
            {
    			if (rs != null) 
    			{
    				rs.close();
    				rs = null;
    			}

    			if (pstm != null) 
    			{
    				pstm.close();
    				pstm = null;
    			}

    			if (conn != null) 
    			{
    				conn.close();
    				conn = null;
    			}
    		} catch (SQLException e) 
    		{
    			throw new ITreasuryDAOException("数据库关闭异常发生",e);			
    	    }
        }

		return name;
	}
}
