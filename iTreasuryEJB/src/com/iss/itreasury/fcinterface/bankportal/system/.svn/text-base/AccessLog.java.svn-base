/*
 * Created on 2003-5-14
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.fcinterface.bankportal.system;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import com.iss.itreasury.fcinterface.bankportal.util.Database;
/**
 * @author yiwang
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class AccessLog
{
	public static void main(String[] args)
	{
		try
		{
			AccessLog al = new AccessLog();
			LoginInfo li = new LoginInfo();
			li.setUserID(1);
			li.setUserName("1");
			li.setAccessType(1);
			li.setOffice(1);
			al.addLoginInfo(li); 
		}
		catch (Exception e)
		{
		}
	}
	public long addLoginInfo(LoginInfo li) throws Exception
	{
		long lReturn = -1;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			con = Database.getConnection();
			StringBuffer sb = new StringBuffer();
			//
			sb.setLength(0);
			sb.append(" insert into Login(NUSERID,SUSERNAME,SACCESSTYPE,SOFFICE,sCurrency,SMODULE,DTLOGIN,SIPADDRESS) values (?,?,?,?,?,?,sysdate,?) ");
			int nIndex = 1;
			ps = con.prepareStatement(sb.toString());
			ps.setLong(nIndex++, li.getUserID());
			ps.setString(nIndex++, li.getUserName());
			ps.setString(nIndex++, li.getAccessType());
			ps.setString(nIndex++, li.getOffice());
			ps.setString(nIndex++, li.getCurrency());
			ps.setString(nIndex++, li.getModule());
			ps.setString(nIndex++, li.getSIpAddress());
			lReturn = ps.executeUpdate();
			ps.close();
			ps = null;
			con.close();
			con = null;
		}
		catch (Exception e)  
		{
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		finally
		{
			try
			{
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (con != null)
					con.close();
			}
			catch (Exception ex)
			{
				throw new Exception(ex.getMessage());
			}
		}
		return lReturn;
	}	
}
