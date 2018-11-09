/*
 * Created on 2005-7-27
 */
package com.iss.itreasury.fcinterface.bankportal.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.iss.itreasury.fcinterface.bankportal.constant.DatabaseType;
import com.iss.itreasury.fcinterface.bankportal.sysframe.exp.system.SystemException;

/**
 * @author jsxie
 */
public class TransIDGenerator
{
	/**日志对象*/
	private static Logger log = new Logger(TransIDGenerator.class);
	
	public static long CURTRANSID = -1;//当前交易id	
		
	public synchronized static long nextTransID() throws Exception
	{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			//查询sql语句
			String sql=null;
            String dbType = Env.getEnvConfigItem(Env.DB_TYPE);
			//增加数据库类型的判断逻辑
			if(DatabaseType.getName(DatabaseType.DB2).equalsIgnoreCase(dbType))
			{
				//按db2方式，获取数据库最大ID值
				sql=getDB2Sql();
			}
			else if(DatabaseType.getName(DatabaseType.ORACLE).equalsIgnoreCase(dbType))
			{
				//按oracle方式，获取数据库最大ID值
				sql=getOracleSql();
			}
			else
			{
				throw new SystemException("******数据库类型指定不对******");
			}
		    //按原来的方式走,取两张交易表的最大ID		
			if(Env.getEnvConfigItem(Env.transID_isSequence).equals("false"))
			{
				if(CURTRANSID<0)
				{
					conn = Database.getConnection();
					ps = conn.prepareStatement(sql);
					rs = ps.executeQuery();
					if (rs.next())
					{
						CURTRANSID = rs.getLong("id");
					}					
				}		
			    CURTRANSID++;
			}
			else//取sequence
			{						
				conn = Database.getConnection();				
				ps = conn.prepareStatement(sql);
				rs = ps.executeQuery();				
				if (rs.next())
				{
					CURTRANSID = rs.getLong("nextid");
				}				
			}
		}
		catch (Exception e)
		{
			throw new SystemException("获取最大交易ID异常："+e.getMessage(), e);
		}
		finally
		{
			try
			{
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			}
			catch (SQLException e)
			{
				log.error("数据库发生异常", e);
			}
		}
		return CURTRANSID;		
	}
	
	private static String getOracleSql()
	{
		//按原来的方式走,取两张交易表的最大ID	
		if(Env.getEnvConfigItem(Env.transID_isSequence).equals("false"))
		{
			return "select nvl(max(id),0) id from ( select max(n_id)  id from BS_ACCTCURTRANSINFO " +
			"union select max(n_id)  id from   BS_ACCTHISTRANSINFO)";	
		}
		//取sequence
		else
		{
			return "SELECT SEQ_BS_ACCTTRANSINFO.nextval nextid from dual";	
		}
	}
	
	private static String getDB2Sql()
	{
		if(Env.getEnvConfigItem(Env.transID_isSequence).equals("false"))
		{
			return "select coalesce(max(id),1) id from ( select max(n_id)  id from BS_ACCTCURTRANSINFO " +
			"union select max(n_id)  id from   BS_ACCTHISTRANSINFO) as ACCTTRANSINFO";	
		}
		else
		{
			return "select nextval for SEQ_BS_ACCTTRANSINFO as nextid  from sysibm.sysdummy1";
		}
	}
}
