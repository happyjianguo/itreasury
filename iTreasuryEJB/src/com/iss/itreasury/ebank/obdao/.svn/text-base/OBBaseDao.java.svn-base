/*
 * Created on 2004-1-12
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.ebank.obdao;

import java.sql.*;
import com.iss.itreasury.ebank.util.*;
/**
 * @author gdzhao
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class OBBaseDao
{

	protected void cleanup(ResultSet rs) throws SQLException
	{
		try
		{
			if (rs != null)
			{
				rs.close();
				rs = null;
			}
		}catch (SQLException sqle){ }
	}
	protected void cleanup(CallableStatement cs) throws SQLException
	{
		try
		{
			if (cs != null)
			{
				cs.close();
				cs = null;
			}
		}catch (SQLException sqle){ }
	 }
	 protected void cleanup(PreparedStatement ps) throws SQLException
	 {
		 try
		 {
			if (ps != null)
			{
				ps.close();
				ps = null;
			}
		}catch (SQLException sqle){}
	 }
	protected void cleanup(PagedStatement ps) throws SQLException
	{
	   if (ps != null)
	   {
		   ps.close();
		   ps = null;
	   }
	}
	 
	 protected void cleanup(Connection con) throws SQLException
	 {
		try
		{
			if (con != null)
			{
				con.close();
				con = null;
			}
		}catch (SQLException sqle){ }
	}

}
