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
public class CopyEncrypt
{

	String spassword = null;
	long id = -1;
	
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getSpassword() {
		return spassword;
	}

	public void setSpassword(String spassword) {
		this.spassword = spassword;
	}
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
	 * 密码初始化，将所有系统用户的密码初始化为 strPass
	 * @param strPass
	 */
	public void initPassword(CopyEncrypt en)
	{
		PreparedStatement ps = null;    
		Connection conn = null;
		String strSQL = null;
		try
		{
			byte[] bPass = this.encrypt(en.getSpassword());
			conn = Database.getConnection();
			
			conn.setAutoCommit(false);
			java.io.ByteArrayInputStream is = new java.io.ByteArrayInputStream(bPass);
			strSQL = " update userinfo set EncryptPassword =? where 1=1 and id =?";
			ps = conn.prepareStatement(strSQL);
			ps.setBinaryStream(1,is,is.available());
			ps.setLong(2,en.getId());
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
	public void initOBPassword(CopyEncrypt en)
	{
		PreparedStatement ps = null;    
		Connection conn = null;
		String strSQL = null;
		try
		{
			byte[] bPass = this.encrypt(en.getSpassword());
			conn = Database.getConnection();
			
			conn.setAutoCommit(false);
			java.io.ByteArrayInputStream is = new java.io.ByteArrayInputStream(bPass);
			strSQL = " update ob_user set EncryptPassword=? where 1=1 and id=?";
			ps = conn.prepareStatement(strSQL);
			ps.setBinaryStream(1,is,is.available());
			ps.setLong(2,en.getId());
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
	
	public byte[] initializePassword()
	{
		byte[] bPass = null;
		PreparedStatement ps = null;    
		Connection conn = null;
		String strSQL = null;
		try
		{
			
			conn = Database.getConnection();
			
			conn.setAutoCommit(false);
			strSQL = " select id,spassword from userinfo";
			ps = conn.prepareStatement(strSQL);
		
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				CopyEncrypt en = new CopyEncrypt();
				en.setId(rs.getLong("id"));
				en.setSpassword(rs.getString("spassword"));
				en.initPassword(en);
			}
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
		return bPass;
	}
	
	public String initializeOBPassword()
	{
		String bPass = null;
		PreparedStatement ps = null;    
		Connection conn = null;
		String strSQL = null;
		
		try
		{
			
			conn = Database.getConnection();
			
			conn.setAutoCommit(false);
			strSQL = " select id,spassword from ob_user";
			ps = conn.prepareStatement(strSQL);
		
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				CopyEncrypt en = new CopyEncrypt();
				en.setId(rs.getLong("id"));
				en.setSpassword(rs.getString("spassword"));
				en.initOBPassword(en);
			}
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
		return bPass;
	}
	
	
	public static void main(String[] args)
	{
		CopyEncrypt en = new CopyEncrypt();
		long lUserID = 4;
		byte[] bPass = null;
	///*	
		try
		{
			//en.initializeOBPassword();
			en.initializePassword();
		} 
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//*/
	}
}
