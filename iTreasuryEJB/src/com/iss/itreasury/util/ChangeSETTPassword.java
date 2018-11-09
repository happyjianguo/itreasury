package com.iss.itreasury.util;

import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.system.bizdelegation.PrivilegeDelegation;
import com.iss.itreasury.system.privilege.dataentity.Sys_UserInfo;


public class ChangeSETTPassword {

	
	public void  Password()
	{
		boolean result = false;
		Collection c = null;
		try
		{			
			Sys_UserInfo info = new Sys_UserInfo() ;
			c = findalluser(info);
			if(c!=null){
				
		    Iterator it = c.iterator ( ) ;
			
			while (it.hasNext ( ))
			{
			info = (Sys_UserInfo) it.next ( ) ;
			String strCheck1 = info.getPassword();
			java.security.MessageDigest alga  = java.security.MessageDigest.getInstance("MD5");
			alga.update(strCheck1.getBytes());
			byte[] btemp = alga.digest();	
			updatePassword(info.getId(),btemp);
		  } 
		 }
		}
			catch (Exception e)
		{
			Log.print("验证加密密码出错！");
		}
	}
	
	
    public Collection findalluser(Sys_UserInfo sinfo) throws ITreasuryDAOException
    {
        Vector vectTemp = new Vector();
        Connection Conn = null;
        PreparedStatement PS = null;
        ResultSet RS = null;
        try
        {
        	try {
    			if (Conn == null)
    				Conn = get_jdbc_connection();
    		} catch (Exception e) {
    			throw new ITreasuryDAOException("数据库初使化异常发生", e);
    		}
            StringBuffer sb = new StringBuffer();
            sb.append("select * from userinfo  where nstatusid in (1) ");       
            System.out.println(" find by condition sql:"+sb.toString());
            PS = Conn.prepareStatement(sb.toString());
            RS = PS.executeQuery();
            while (RS!= null && RS.next())
            {
            	Sys_UserInfo info = new Sys_UserInfo();
            	
            	info.setId(RS.getLong("ID"));            	
            	info.setPassword(RS.getString("sPassword"));           
            	vectTemp.add(info);
            }
            if (RS != null) {
				RS.close();
				RS = null;
			}

			if (PS != null) {
				PS.close();
				PS = null;
			}

			if (Conn != null ) {
				Conn.close();
				Conn = null;
			}
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
    		
        }
        return vectTemp.size() > 0 ? vectTemp : null;

    }
    public static Connection get_jdbc_connection() throws Exception
    {
        Connection conn = null;
        try
        {
            String jdbcDriver = "oracle.jdbc.driver.OracleDriver";
            String dbURL = "jdbc:oracle:thin:@10.10.19.79:1521:orcl";
            Class.forName(jdbcDriver).newInstance();
            conn = DriverManager.getConnection(dbURL, "gdf0520", "gdf0520");
        }
        catch (SQLException sqe)
        {
            Log.print("connect db failed by oracle jdbc driver. " + sqe.toString());
            throw sqe;
        }
        return conn;
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
		
		try
    	{
			conn = get_jdbc_connection();
			
			conn.setAutoCommit(false);
			java.io.ByteArrayInputStream is = new java.io.ByteArrayInputStream(newPass);
			strSQL = " update userinfo set EncryptPassword=? ,spassword = ? where id=? ";
			ps = conn.prepareStatement(strSQL);
			
			ps.setBinaryStream(1,is,is.available());
			System.out.print("ss"+is);
			//ps.setTimestamp(2, Env.getSystemDate());
			//ps.setLong(2, lUserID);
			ps.setString(2,"");
			ps.setLong(3,lUserID);
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
	public static void main(String[] args) {
		try
		{
			ChangeSETTPassword c = new ChangeSETTPassword();
			c.Password();
		} 
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

}
