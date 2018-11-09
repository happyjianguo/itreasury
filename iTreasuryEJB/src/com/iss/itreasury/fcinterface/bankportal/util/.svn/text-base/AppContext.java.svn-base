package com.iss.itreasury.fcinterface.bankportal.util;

import java.sql.Connection;

import com.iss.system.IAppContext;
import com.iss.system.action.ActionException;

/**
 * @author rongyang
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class AppContext implements IAppContext,java.io.Serializable
{
	/**
	 * @see com.iss.system.IAppContext#getConnection()
	 */
	public Connection getConnection() throws ActionException
	{
		Connection conn = null;

		try
		{
			conn = Database.getConnection();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new ActionException("", e.getMessage());
		}
		
		return conn;
	}

	/**
	 * @see com.iss.system.IAppContext#releaseConnection(java.sql.Connection)
	 */
	public void releaseConnection(Connection conn) throws ActionException
	{
		if (null != conn)
		{
			try
			{
				conn.close();
			}
			catch (Exception e)
			{
				throw new ActionException("","Failed to close datasource connection." + e.getMessage());
			}
		}
	}

}
