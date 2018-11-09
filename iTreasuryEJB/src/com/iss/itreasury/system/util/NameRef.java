
/*
 * Created on 2003-9-5
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.system.util;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashMap;

import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Log;
/**
 * @author yiwang
 * 
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class NameRef
{
	public static String findUserInfoByID(long userID)
	{
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		String userName = "";
		try
		{
			con = Database.getConnection();
			StringBuffer sb = new StringBuffer();
			sb.append(" select sName from userInfo ");
			sb.append(" where id = "+userID+"");
			ps = con.prepareStatement(sb.toString());
			rs = ps.executeQuery();
			while (rs.next())
			{
				userName = rs.getString("sName");
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			con.close();
			con = null;
		}
		catch (Exception sqle)
		{

			try
			{
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (con != null)
				{
					con.close();
					con = null;
				}
			}
			catch (Exception e)
			{
				;
			}

		}
		return userName;
	}
	public static boolean verifyRelationByWorkflowID(long wfid)
	{
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		boolean isExist = false;
		try
		{
			con = Database.getConnection();
			StringBuffer sb = new StringBuffer();
			sb.append(" select * from sys_approvalrelation ");
			sb.append(" where APPROVALID = "+wfid+"");
			ps = con.prepareStatement(sb.toString());
			System.out.print(sb.toString());
			rs = ps.executeQuery();
			while (rs.next())
			{
				isExist =true;
			}		
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			con.close();
			con = null;
		}
		catch (Exception sqle)
		{	
			try
			{
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (con != null)
				{
					con.close();
					con = null;
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
	
		}
		return isExist;
	}
}
