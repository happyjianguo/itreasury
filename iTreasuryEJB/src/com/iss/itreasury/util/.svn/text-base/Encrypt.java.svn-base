package com.iss.itreasury.util;

/*
 * Created on 2005-7-21
 * 由于中电子要求密码加密而新增<p>
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.iss.itreasury.util.Database;



/**
 * @author zntan
 * 采用MD5消息摘要加密<p>
 * 实现从数据库取出密文、校验密码是否正确、更新数据库密码、调用java.security.MessageDigest加密等功能
 *
 */
public class Encrypt
{
	/**
	 * 密码加密测试算法
	 *
	 */
	public void testDigest()
	{
		try
		{
			String myinfo = "cws123";
			java.security.MessageDigest alga = java.security.MessageDigest.getInstance("MD5");
			//java.security.MessageDigest alga = java.security.MessageDigest.getInstance("SHA-1");
			alga.update(myinfo.getBytes());
			byte[] digesta = alga.digest();			
			System.out.println("本信息摘要是:" + digesta);
			System.out.println("本信息摘要是 tostring:" + digesta.toString());
			System.out.println("本信息摘要是 tostring:" + new String(digesta));
			//通过某中方式传给其他人你的信息(myinfo)和摘要(digesta) 对方可以判断是否更改或传输正常
			java.security.MessageDigest algb = java.security.MessageDigest.getInstance("MD5");
			//java.security.MessageDigest algb = java.security.MessageDigest.getInstance("SHA-1");
			algb.update(myinfo.getBytes());	
			byte[] digestb = algb.digest();	
			if (MessageDigest.isEqual(digesta, digestb))
			{
				System.out.println("信息检查正常");
				System.out.println("1" + digesta.toString());
				System.out.println("2" + digestb.toString());
			}
			else
			{
				System.out.println("摘要不相同");
			}
			if ((new String(digesta,"UTF8")).equals(new String(digestb,"UTF8")))
			{
				System.out.println(" 转换成字符串匹配一样 !");
			}
			//
		}
		catch (java.security.NoSuchAlgorithmException ex)
		{
			System.out.println("非法摘要算法");
			ex.printStackTrace();
		} catch (UnsupportedEncodingException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 系统校验密码正确性
	 * @param strLoginNo 用户登录名
	 * @param strCheck 登录输入的密码
	 * @return 如果密码匹配成功返回 true,否则返回 false
	 */	
	public boolean checkPassword(String strLoginNo,String strCheck)
	{
		Log.print(" ###### enter method checkPassword");
		boolean result = false;
		try
		{
			System.out.println("<><><><><><><><><><><><>");
			System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&");
			System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^");
			System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!");
			System.out.println("检测");
			System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!");
			System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^");
			System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&");
			System.out.println("<><><><><><><><><><><><>");
			byte[] truePass = getPasswordByLoginNo(strLoginNo);
			java.security.MessageDigest alga  = java.security.MessageDigest.getInstance("MD5");
			alga.update(strCheck.getBytes());
			byte[] btemp = alga.digest();
			if (MessageDigest.isEqual(btemp,truePass))
			{
				System.out.println("<><><><><><><><><><><><>");
				System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&");
				System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^");
				System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!");
				System.out.println("正确");
				System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!");
				System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^");
				System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&");
				System.out.println("<><><><><><><><><><><><>");
				Log.print(" check password success!!!");
				result = true;
			}
			else
			{
				System.out.println("<><><><><><><><><><><><>");
				System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&");
				System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^");
				System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!");
				System.out.println("出错");
				System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!");
				System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^");
				System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&");
				System.out.println("<><><><><><><><><><><><>");
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
	
	/**
	 * 系统校验密码正确性
	 * @param strLoginNo 用户登录名
	 * @param strCheck 登录输入的用户名
	 * @return 如果密码匹配成功返回 true,否则返回 false
	 */	
	public boolean checkOBPassword(String strLoginNo,String strCheck)
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
	
	/**
	 * 更新密码，把加密得到的二进制数据写入数据库，操作数据库表userinfo
	 * @param lUserID 用户id
	 * @param newPass 密码
	 */
	public void updatePassword(long lUserID,byte[] newPass) throws IException
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
			ps.setLong(3,lUserID);
			
			Log.print(" updated UserID:"+ lUserID);
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
	
	/**
	 * @deprecated
	 * 
	 * 更新密码，把加密得到的二进制数据写入数据库，操作数据库表ob_user
	 * @param lUserID 网银用户id
	 * @param newPass 密码
	 */
	public void updateOBPassword(long lUserID,byte[] newPass) throws IException
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
			ps.setLong(4,lUserID);
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
	/**
	 * 根据登录名取得用户密码，操作数据库表 userinfo
	 * @param strLoginNo 用户登录名
	 * @return 密码
	 */
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
	
	/**
	 * 根据网银登录名取得用户密码，操作数据库表 ob_user
	 * @param strLoginNo 网银用户登录名
	 * @return 密码
	 */
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
	
	/**
	 * 修改系统用户的密码
	 * @param lUserID 用户id
	 * @param strPass 密码
	 * @throws IException
	 */
	public void changeUserPassword(long lUserID,String strPass) throws IException
	{
		try
		{
			Log.print(" ######## enter method changeUserPassword ####");
			byte[] bbPass = encrypt(strPass);
			updatePassword(lUserID,bbPass);
		} catch (IException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
	}
	
	/**
	 * @deprecated
	 * 
	 * 修改网银用户的密码
	 * @param lUserID 用户id
	 * @param strPass 密码
	 * @throws IException
	 */
	public void changeOBUserPassword(long lUserID,String strPass) throws IException
	{
		try
		{
			byte[] bbPass = encrypt(strPass);
			updateOBPassword(lUserID,bbPass);
		} catch (IException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
	}
	/**
	 * 密码初始化，将所有系统用户的密码初始化为 strPass
	 * @param strPass
	 */
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
	/**
	 * 密码初始化，将所有网银用户的密码初始化为 strPass
	 * @param strPass
	 */
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
	
	public static void main(String[] args)
	{
		Encrypt en = new Encrypt();
		long lUserID = 4;
	///*	
		try
		{
			en.changeOBUserPassword(321,"101010");
			en.checkOBPassword("10test2","101010");
			//en.encrypt("");
		} 
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//*/
	}
}
