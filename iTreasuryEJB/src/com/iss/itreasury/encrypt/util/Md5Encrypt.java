package com.iss.itreasury.encrypt.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.iss.itreasury.encrypt.impl.Encrypt;
import com.iss.itreasury.util.Config;
import com.iss.itreasury.util.ConfigConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log;

public class Md5Encrypt implements Encrypt{

	public void changeOBUserPassword(long userID, String strPass) throws Exception 
	{
		try
		{
			strPass = encrypt(strPass);
			updateOBPassword(userID,strPass);
			
		} catch (IException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
	}
	
	public void changeOBUserPassword(long userID, String strPass,Connection conn) throws Exception 
	{
		try
		{
			strPass = encrypt(strPass);
			updateOBPassword(userID,strPass,conn);
			
		} catch (IException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
	}

	public void changeUserPassword(long userID, String strPass) throws Exception 
	{
		try
		{
			strPass = encrypt(strPass);
			updatePassword(userID,strPass);
			
		} catch (IException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
	}
	
	public void changeUserPassword(long userID, String strPass,Connection conn) throws Exception 
	{
		try
		{
			strPass = encrypt(strPass);
			updatePassword(userID,strPass,conn);
			
		} catch (IException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
	}	

	public boolean checkOBPassword(String strLoginNo, String strCheck) throws Exception 
	{
		boolean result = false;
		try
		{
			String truePass = getOBPasswordByLogin(strLoginNo);
			strCheck = encrypt(strCheck);
			
			if (truePass.equals(strCheck))
			{
				Log.print(" check password success!!!");
				result = true;
			}
			else
			{
				Log.print(" check password failed ");
			}
		}catch (Exception e)
		{
			Log.print("验证网银用户密码出错！");
			e.printStackTrace();
		}
		return result;
	
	}

	public boolean checkPassword(String strLoginNo, String strCheck) throws Exception 
	{
		boolean result = false;
		try
		{
			String truePass = getPasswordByLogin(strLoginNo);
			strCheck = encrypt(strCheck);
			
			if (truePass.equals(strCheck))
			{
				Log.print(" check password success!!!");
				result = true;
			}
			else
			{
				Log.print(" check password failed ");
			}
		}catch (Exception e)
		{
			Log.print("验证加密密码出错！");
			//e.printStackTrace();
		}
		return result;
	
	}

	public String getOBPasswordByLogin(String strLoginNo) {

		String result = null;
		PreparedStatement ps = null;
		ResultSet rs = null;   
		Connection conn = null;
		String strSQL = null;
		
		try
    	{
			conn = Database.getConnection();	
			//-------------------------------------
			//读取数据库存储的加密后的字符串
			strSQL = " select SPASSWORD from  ob_user where sLoginNo='"+strLoginNo+"' and NSTATUS="+Constant.RecordStatus.VALID;
			ps = conn.prepareStatement(strSQL);
			rs = ps.executeQuery();
        	if (rs != null && rs.next())
        	{
        		result = rs.getString("SPASSWORD");
        	}        	 
    	}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if (rs != null){ rs.close();rs=null;}
				if (ps != null) {ps.close();ps=null;}
				if (conn != null) {conn.close();conn=null;}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return result;
	
	}

	public byte[] getOBPasswordByLoginNo(String strLoginNo) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getPasswordByLogin(String strLoginNo) 
	{
		String result = null;
		PreparedStatement ps = null;    
		ResultSet rs = null;   
		Connection conn = null;
		String strSQL = null;
		
		try
    	{
			conn = Database.getConnection();	
			//-------------------------------------
			//读取数据库存储的加密后的字符串
			strSQL = " select SPASSWORD from userinfo where sLoginNo='"+strLoginNo+"' and nStatusId="+Constant.RecordStatus.VALID;
			ps = conn.prepareStatement(strSQL);
			rs = ps.executeQuery();
        	if (rs != null && rs.next())
        	{
        		result = rs.getString("SPASSWORD");				
        	}        	 
    	}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if (rs != null){ rs.close();rs=null;}
				if (ps != null) {ps.close();ps=null;}
				if (conn != null) {conn.close();conn=null;}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return result;

	}

	public byte[] getPasswordByLoginNo(String strLoginNo) {
		// TODO Auto-generated method stub
		return null;
	}

	public void initOBPassword(String strPass) 
	{
		PreparedStatement ps = null;    
		Connection conn = null;
		String strSQL = null;
		try
		{
			String bPass = this.encrypt(strPass);
			conn = Database.getConnection();
			
			conn.setAutoCommit(false);
			strSQL = " update ob_user set SPASSWORD=? where 1=1 ";
			ps = conn.prepareStatement(strSQL);
			ps.setString(1,bPass);
			ps.executeUpdate();
			ps.close();
			conn.commit();
        	if(conn!=null)
        	{
        		conn.close();
        		conn=null;
        	}	  
		}catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if (ps != null) {ps.close();ps=null;}
				if (conn != null) {conn.close();conn=null;}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		
	}

	public void initPassword(String strPass) 
	{
		PreparedStatement ps = null;    
		Connection conn = null;
		String strSQL = null;
		try
		{
			String bPass = this.encrypt(strPass);
			conn = Database.getConnection();
			conn.setAutoCommit(false);
			strSQL = " update userinfo set SPASSWORD=? where 1=1 ";
			ps = conn.prepareStatement(strSQL);
			ps.setString(1,bPass);
			ps.executeUpdate();
			ps.close();
			conn.commit();
        	if(conn!=null)
        	{
        		conn.close();
        		conn=null;
        	}	  
		}catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if (ps != null) {ps.close();ps=null;}
				if (conn != null) {conn.close();conn=null;}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		
	}

	public void updateOBPassword(long userID, byte[] newPass) throws IException {
		// TODO Auto-generated method stub
		
	}

	public void updateOBPassword(long userID, String newPass) throws IException 
	{
		PreparedStatement ps = null;
		ResultSet rs = null;   
		Connection conn = null;
		String strSQL = null;
		
		try
    	{
			conn = Database.getConnection();
			conn.setAutoCommit(false);
			strSQL = " update ob_user set SPASSWORD=?, DTCHANGEPASSWORD=? where id=? ";
			ps = conn.prepareStatement(strSQL);
			ps.setString(1, newPass);
			ps.setTimestamp(2, Env.getSystemDate());
			ps.setLong(3, userID);
			ps.executeUpdate();
			ps.close();
			conn.commit();
        	if(conn!=null)
        	{
        		conn.close();
        		conn=null;
        	}	  
    	}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IException("修改密码－更新数据库出错",e);
		}
		finally
		{
			try
			{
				if (rs != null){ rs.close();rs=null;}
				if (ps != null) {ps.close();ps=null;}
				if (conn != null) {conn.close();conn=null;}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		
	}
	
	public void updateOBPassword(long userID, String newPass,Connection conn) throws IException 
	{
		PreparedStatement ps = null;
		String strSQL = null;
		try
    	{

			strSQL = " update ob_user set SPASSWORD=?, DTCHANGEPASSWORD=? where id=? ";
			ps = conn.prepareStatement(strSQL);
			ps.setString(1, newPass);
			ps.setTimestamp(2, Env.getSystemDate());
			ps.setLong(3, userID);
			ps.executeUpdate();
			ps.close();
	  
    	}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IException("修改密码－更新数据库出错",e);
		}
		finally
		{
			try
			{
				if (ps != null) {ps.close();ps=null;}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		
	}

	public void updatePassword(long userID, byte[] newPass) throws IException {
		// TODO Auto-generated method stub
		
	}

	public void updatePassword(long userID, String newPass) throws IException 
	{
		PreparedStatement ps = null;
		ResultSet rs = null;   
		Connection conn = null;
		String strSQL = null;
		
		try
    	{
			conn = Database.getConnection();
			conn.setAutoCommit(false);
			strSQL = " update userinfo set SPASSWORD=?, DTCHANGEPASSWORD=? where id=? ";
			ps = conn.prepareStatement(strSQL);
			ps.setString(1, newPass);
			ps.setTimestamp(2, Env.getSystemDate());
			ps.setLong(3, userID);
			ps.executeUpdate();
			ps.close();
			conn.commit();
        	if(conn!=null)
        	{
        		conn.close();
        		conn=null;
        	}	  
    	}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IException("修改密码－更新数据库出错",e);
		}
		finally
		{
			try
			{
				if (rs != null){ rs.close();rs=null;}
				if (ps != null) {ps.close();ps=null;}
				if (conn != null) {conn.close();conn=null;}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		
	}

	public void updatePassword(long userID, String newPass,Connection conn) throws IException 
	{
		PreparedStatement ps = null;
		ResultSet rs = null;   
		String strSQL = null;
		try
    	{
			strSQL = " update userinfo set SPASSWORD=?, DTCHANGEPASSWORD=? where id=? ";
			ps = conn.prepareStatement(strSQL);
			ps.setString(1, newPass);
			ps.setTimestamp(2, Env.getSystemDate());
			ps.setLong(3, userID);
			ps.executeUpdate();
    	}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IException("修改密码－更新数据库出错",e);
		}
		finally
		{
			try
			{
				if (rs != null){ rs.close();rs=null;}
				if (ps != null) {ps.close();ps=null;}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		
	}
	/**
	 * 用MD5加密
	 * @param strPass 需要加密的字符串
	 * @return 加密后的数据
	 */
	public String encrypt(String strPass) throws Exception
	{
		String result= null;
		try
		{
			result = com.iss.itreasury.util.Md5.md5(strPass);
			System.out.println(" Md5====>"+ result);
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException("加密失败",e);
		} 
		return result;
	}

}
