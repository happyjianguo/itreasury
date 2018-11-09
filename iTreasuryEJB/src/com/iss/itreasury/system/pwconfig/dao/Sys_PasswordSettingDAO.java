package com.iss.itreasury.system.pwconfig.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.system.pwconfig.dataentity.PasswordInfo;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.Log;

public class Sys_PasswordSettingDAO extends ITreasuryDAO{
	//构造函数
	public Sys_PasswordSettingDAO()
    {
        super("sys_passwordsetting");
    }

    public Sys_PasswordSettingDAO(String tableName)
    {
        super(tableName);
    }

    public Sys_PasswordSettingDAO(String tableName, Connection conn)
    {
        super(tableName, true, conn);
    }

    public Sys_PasswordSettingDAO(boolean isNeedPrefix)
    {
        super(isNeedPrefix);
    }

    public Sys_PasswordSettingDAO(String tableName, boolean isNeedPrefix)
    {
        super(tableName, isNeedPrefix);
    }

    public Sys_PasswordSettingDAO(String tableName, boolean isNeedPrefix, Connection conn)
    {
        super(tableName, isNeedPrefix, conn);
    }
    
    //获得用户密码和上次修改密码日期
    public List getUserPwdInfo(long id) throws ITreasuryDAOException, SQLException{
    	List list = new ArrayList();
    	initDAO();
    	String strSql = "select spassword,dtChangePassword from userinfo where id = "+id;
    	try{
    		transPS = transConn.prepareStatement(strSql);
    		transRS = transPS.executeQuery();
    		while(transRS.next()){
    			list.add(transRS.getString(1));
    			list.add(transRS.getTimestamp(2));
    		}
    	}catch(Exception e){
    		e.printStackTrace();
    	}finally{
    		finalizeDAO();
    	}
    	return list;
    }
    
    //新增密码设置信息
    public void addPasswordInfo(PasswordInfo passwordInfo)throws ITreasuryDAOException, SQLException{
    	setUseMaxID();
    	try{
    		add(passwordInfo);
    	}catch(Exception e){
    		e.printStackTrace();
    	}finally{
    		finalizeDAO();
    	}
    }
    
    //更新密码设置信息
    public void updatePasswordInfo(PasswordInfo passwordInfo)throws ITreasuryDAOException, SQLException{
    	try{
    		update(passwordInfo);
    	}catch(Exception e){
    		e.printStackTrace();
    	}finally{
    		finalizeDAO();
    	}
    }
    
    //修改用户密码
    public void changeUserPassword(long lUserID, String strPassword) throws Exception{
    	PreparedStatement ps = null;
		Connection conn = null;
		StringBuffer sb = new StringBuffer();
		long lResult = -1;
		try{
			conn = Database.getConnection();
			sb.append(" update userinfo set SPASSWORD=? , DTCHANGEPASSWORD=? where id=? ");
			Log.print(sb.toString());
			ps = conn.prepareStatement(sb.toString());
			ps.setString(1, strPassword);
			ps.setTimestamp(2, Env.getSystemDate());
			ps.setLong(3, lUserID);
			lResult = ps.executeUpdate();
			//关闭资源
			ps.close();
			ps = null;
			conn.close();
			conn = null;
		}catch (Exception e){
			e.printStackTrace();
			if (ps != null)
				ps.close();
			if (conn != null)
				conn.close();
			throw new Exception(e.getMessage());
		}finally{
			try{
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			}catch (Exception ex){
				throw new Exception(ex.getMessage());
			}
		}
    	
    }
}
