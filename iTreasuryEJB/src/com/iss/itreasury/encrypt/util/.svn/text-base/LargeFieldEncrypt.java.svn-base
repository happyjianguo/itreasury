package com.iss.itreasury.encrypt.util;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.iss.itreasury.encrypt.impl.Encrypt;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log;

/**
 * 
 * @author xiang
 *
 */
public class LargeFieldEncrypt implements Encrypt{

	public void changeOBUserPassword(long userID, String strPass) throws Exception 
	{
		try
		{
			byte[] bbPass = encrypt(strPass);
			updateOBPassword(userID,bbPass);
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
			byte[] bbPass = encrypt(strPass);
			updateOBPassword(userID,bbPass,conn);
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
			byte[] bbPass = encrypt(strPass);
			updatePassword(userID,bbPass);
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
			byte[] bbPass = encrypt(strPass);
			updatePassword(userID,bbPass,conn);
		} catch (IException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
	
	}	

	public boolean checkOBPassword(String strLoginNo, String strCheck)
	{
		boolean result = false;
		try
		{
			byte[] truePass = getOBPasswordByLoginNo(strLoginNo);
			java.security.MessageDigest alga = java.security.MessageDigest.getInstance("MD5");
			alga.update(strCheck.getBytes());
			byte[] btemp = alga.digest();
			Log.print(" check:"+ this.byte2hex(btemp));
			Log.print(" true pass:"+ this.byte2hex(truePass));
			if (MessageDigest.isEqual(btemp,truePass))
			{
				Log.print(" check password success!!!");
				result = true;
			}
			else
			{
				Log.print(" check password failed ");
			}
		} catch (NoSuchAlgorithmException e)
		{
			Log.print("验证网银用户密码出错！");
			e.printStackTrace();
		}
		catch (Exception e)
		{
			Log.print("验证网银用户密码出错！");
			e.printStackTrace();
		}
		return result;
	
	}

	public boolean checkPassword(String strLoginNo, String strCheck)
	{
		Log.print(" ###### enter method checkPassword");
		boolean result = false;
		try
		{
			byte[] truePass = getPasswordByLoginNo(strLoginNo);
			java.security.MessageDigest alga  = java.security.MessageDigest.getInstance("MD5");
			alga.update(strCheck.getBytes());
			byte[] btemp = alga.digest();
			if (MessageDigest.isEqual(btemp,truePass))
			{
				Log.print(" check password success!!!");
				result = true;
			}
			else
			{
				Log.print(" check password failed ");
			}
		} catch (NoSuchAlgorithmException e)
		{
			Log.print("验证加密密码出错！");
			//e.printStackTrace();
		} catch (Exception e)
		{
			Log.print("验证加密密码出错！");
			//e.printStackTrace();
		}
		return result;
	
	}

	public String getOBPasswordByLogin(String strLoginNo){
		return null;
	}

	public String getPasswordByLogin(String strLoginNo){
		return null;
	}

	public byte[] getOBPasswordByLoginNo(String strLoginNo)
	{
		byte[] result = null;

		PreparedStatement ps = null;    
		ResultSet rs = null;   
		Connection conn = null;
		String strSQL = null;
		
		try
    	{
			conn = Database.getConnection();	
			//-------------------------------------
			//读取数据库存储的加密后的字符串
			strSQL = " select EncryptPassword from  ob_user where sLoginNo='"+strLoginNo+"' and nStatus="+Constant.RecordStatus.VALID;
			ps = conn.prepareStatement(strSQL);
			rs = ps.executeQuery();
        	if (rs != null && rs.next())
        	{
        		result = rs.getBytes("EncryptPassword");				
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

	public byte[] getPasswordByLoginNo(String strLoginNo) 
	{
		Log.print(" ###### enter method getPasswordByLoginNo");
		byte[] result = null;

		PreparedStatement ps = null;    
		ResultSet rs = null;   
		Connection conn = null;
		String strSQL = null;
		
		try
    	{
			conn = Database.getConnection();	
			//-------------------------------------
			//读取数据库存储的加密后的字符串
			strSQL = " select EncryptPassword from  userinfo where sLoginNo='"+strLoginNo+"' and nStatusId="+Constant.RecordStatus.VALID;
			ps = conn.prepareStatement(strSQL);
			rs = ps.executeQuery();
        	if (rs != null && rs.next())
        	{
        		result = rs.getBytes("EncryptPassword");				
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

	public void initOBPassword(String strPass) 
	{
		PreparedStatement ps = null;    
		Connection conn = null;
		String strSQL = null;
		try
		{
			byte[] bPass = this.encrypt(strPass);
			conn = Database.getConnection();
			
			conn.setAutoCommit(false);
			java.io.ByteArrayInputStream is = new java.io.ByteArrayInputStream(bPass);
			strSQL = " update ob_user set EncryptPassword=? where 1=1 ";
			ps = conn.prepareStatement(strSQL);
			ps.setBinaryStream(1,is,is.available());
			ps.executeUpdate();
			is.close();
			ps.close();
			conn.commit();
        	if(conn!=null)
        	{
        		conn.close();
        		conn=null;
        	}	  
		} catch (IException e)
		{
			e.printStackTrace();
		} catch (SQLException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		} catch (Exception e)
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
			byte[] bPass = this.encrypt(strPass);
			conn = Database.getConnection();
			
			conn.setAutoCommit(false);
			java.io.ByteArrayInputStream is = new java.io.ByteArrayInputStream(bPass);
			strSQL = " update userinfo set EncryptPassword=? where 1=1 ";
			ps = conn.prepareStatement(strSQL);
			ps.setBinaryStream(1,is,is.available());
			ps.executeUpdate();
			is.close();
			ps.close();
			conn.commit();
        	if(conn!=null)
        	{
        		conn.close();
        		conn=null;
        	}	  
		} catch (IException e)
		{
			e.printStackTrace();
		} catch (SQLException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		} catch (Exception e)
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

	public void updateOBPassword(long userID, String newPass) throws IException {
		// TODO Auto-generated method stub
		
	}

	public void updatePassword(long userID, String newPass) throws IException {
		// TODO Auto-generated method stub
		
	}
	
	public void updateOBPassword(long userID, byte[] newPass) throws IException 
	{
		PreparedStatement ps = null;    
		ResultSet rs = null;   
		Connection conn = null;
		String strSQL = null;
		
		try
    	{
			conn = Database.getConnection();
			
			conn.setAutoCommit(false);
			java.io.ByteArrayInputStream is = new java.io.ByteArrayInputStream(newPass);
			strSQL = " update ob_user set EncryptPassword=?, spassword = ?, dtchangepassword = ? where id=? ";
			ps = conn.prepareStatement(strSQL);
			ps.setBinaryStream(1,is,is.available());
			ps.setString(2,"");
			ps.setTimestamp(3, Env.getSystemDateTime());
			ps.setLong(4,userID);
			Log.print("strSQL:"+ strSQL);
			Log.print(" updated UserID:"+is.available()+"^^^^^^^");
			int i = ps.executeUpdate();
			Log.print("=====影响行数："+i);
			is.close();
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
			throw new IException("修改网银用户密码－更新数据库出错",e);
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
	
	public void updateOBPassword(long userID, byte[] newPass,Connection conn) throws IException 
	{
		PreparedStatement ps = null;    
		String strSQL = null;
		try
    	{
			java.io.ByteArrayInputStream is = new java.io.ByteArrayInputStream(newPass);
			strSQL = " update ob_user set EncryptPassword=?, exchangepassword = ?, dtchangepassword = ? where id=? ";
			ps = conn.prepareStatement(strSQL);
			ps.setBinaryStream(1,is,is.available());
			ps.setString(2,"");
			ps.setTimestamp(3, Env.getSystemDateTime());
			ps.setLong(4,userID);
			Log.print("strSQL:"+ strSQL);
			Log.print(" updated UserID:"+is.available()+"^^^^^^^");
			int i = ps.executeUpdate();
			Log.print("=====影响行数："+i);
			is.close();
			ps.close();
	  
    	}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IException("修改网银用户密码－更新数据库出错",e);
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

	public void updatePassword(long userID, byte[] newPass) throws IException 
	{
		PreparedStatement ps = null;    
		ResultSet rs = null;   
		Connection conn = null;
		String strSQL = null;
		
		Log.print(" ######## enter method updatePassword ####");
		
		try
    	{
			conn = Database.getConnection();
			
			conn.setAutoCommit(false);
			java.io.ByteArrayInputStream is = new java.io.ByteArrayInputStream(newPass);
			strSQL = " update userinfo set EncryptPassword=?, DTCHANGEPASSWORD=? where id=? ";
			ps = conn.prepareStatement(strSQL);
			
			ps.setBinaryStream(1,is,is.available());
			ps.setTimestamp(2, Env.getSystemDate());
			ps.setLong(3,userID);
			
			Log.print(" updated UserID:"+ userID);
			ps.executeUpdate();
			is.close();
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

	
	public void updatePassword(long userID, byte[] newPass,Connection conn) throws IException 
	{
		PreparedStatement ps = null;    
		ResultSet rs = null;   
		String strSQL = null;
		Log.print(" ######## enter method updatePassword ####");
		try
    	{
			java.io.ByteArrayInputStream is = new java.io.ByteArrayInputStream(newPass);
			strSQL = " update userinfo set EncryptPassword=?, DTCHANGEPASSWORD=? where id=? ";
			ps = conn.prepareStatement(strSQL);
			ps.setBinaryStream(1,is,is.available());
			ps.setTimestamp(2, Env.getSystemDate());
			ps.setLong(3,userID);
			Log.print(" updated UserID:"+ userID);
			ps.executeUpdate();
			is.close();
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
	 * 将二进制数组转换成字符串<p>
	 * 用于打印二进制数组，检查两个二进制数组是否完全一样<p>
	 * @param b 二进制数组
	 * @return 字符串
	 */
	public String byte2hex(byte[] b) //二行制转字符串
    {
		String hs="";
		String stmp="";
		for (int n=0;n<b.length;n++)
		{
			stmp=(java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length()==1) hs=hs+"0"+stmp;
			else hs=hs+stmp;
			if (n<b.length-1)  hs=hs+":";
		}
		return hs.toUpperCase();
    }
	
	/**
	 * 用MD5加密
	 * @param strPass 需要加密的字符串
	 * @return 加密后的二进制数据
	 */
	public byte[] encrypt(String strPass) throws IException
	{
		byte[] result= null;
		try
		{
			java.security.MessageDigest alga = java.security.MessageDigest.getInstance("MD5");
			alga.update(strPass.getBytes());
			result = alga.digest();	
			System.out.println(" after digest :"+ result);
		} catch (NoSuchAlgorithmException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException("加密失败－没有这种加密算法",e);
		} 
		return result;
	}

}
