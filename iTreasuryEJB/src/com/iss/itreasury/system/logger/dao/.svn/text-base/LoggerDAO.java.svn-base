/*
 * Created on 2004-6-7
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.system.logger.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.glinterface.dataentity.GLVoucherLogInfo;
import com.iss.itreasury.system.logger.dataentity.QueryLoggerInfo;
import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Database;
import com.iss.system.dao.PageLoader;

/**
 * @author yiwang
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class LoggerDAO extends ITreasuryDAO {
	StringBuffer m_sbSelect = null;
	StringBuffer m_sbFrom = null;
	StringBuffer m_sbWhere = null;
	StringBuffer m_sbOrderBy = null;
	
	/**
	 * 
	 */
	public LoggerDAO ( )
	{
		super ("SYS_LOGGER" ) ;
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param tableName
	 */
	public LoggerDAO ( String tableName )
	{
		super ( tableName ) ;
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param tableName
	 * @param conn
	 */
	public LoggerDAO ( String tableName , Connection conn )
	{
		super ( tableName , conn ) ;
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param isNeedPrefix
	 */
	public LoggerDAO ( boolean isNeedPrefix )
	{
		super ( isNeedPrefix ) ;
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param tableName
	 * @param isNeedPrefix
	 */
	public LoggerDAO ( String tableName , boolean isNeedPrefix )
	{
		super ( tableName , isNeedPrefix ) ;
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param tableName
	 * @param isNeedPrefix
	 * @param conn
	 */
	public LoggerDAO ( String tableName , boolean isNeedPrefix , Connection conn )
	{
		super ( tableName , isNeedPrefix , conn ) ;
		// TODO Auto-generated constructor stub
	}
	public PageLoader findLoggerByCondition(QueryLoggerInfo qli) throws Exception
	{
		getQuerySQL(qli);
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");
		pageLoader.initPageLoader(
			new AppContext(),
			m_sbFrom.toString(),
			m_sbSelect.toString(),
			m_sbWhere.toString(),
			(int) Constant.PageControl.CODE_PAGELINECOUNT*3,
			"com.iss.itreasury.system.logger.dataentity.LoggerInfo",
			null);
		pageLoader.setOrderBy(" " + m_sbOrderBy.toString() + " ");

		return pageLoader;
	}
	public void getQuerySQL(QueryLoggerInfo qli)
	{
		// select
		m_sbSelect = new StringBuffer();
		m_sbSelect.append(" \n * \n");
		// from
		m_sbFrom = new StringBuffer();
		m_sbFrom.append(" ( \n");
		m_sbFrom.append("   select id, moduleID,officeID,currencyID,accessTime,userID,userName,functionPointCode,functionPointDescription,remoteIP,remoteHost ");
		m_sbFrom.append("   from sys_logger ");
		m_sbFrom.append("   where officeID="+qli.getOfficeID() );
		if(qli.getModuleID() > 0)
		{
			m_sbFrom.append(" and  moduleID= "+qli.getModuleID() );
		}
		if(qli.getCurrencyID()>0)
		{
			m_sbFrom.append(" and  currencyid= "+qli.getCurrencyID() );
		}
		if( qli.getUserID() > 0 )
			m_sbFrom.append("   and userID="+qli.getUserID());
		if( qli.getUserName()!=null && qli.getUserName().length() > 0 )
			m_sbFrom.append("   and userName='"+qli.getUserName()+"'");
		if( qli.getStartDate() != null && qli.getStartDate().length() > 0)
			m_sbFrom.append("   and accessTime>=to_date('" + qli.getStartDate()+ "','yyyy-mm-dd')");
		if( qli.getEndDate() != null  && qli.getEndDate().length() > 0)
			m_sbFrom.append("   and accessTime<=(to_date('" + qli.getEndDate() + "','yyyy-mm-dd')+1)");
		m_sbFrom.append("\n ) rr \n");
 		// where
		m_sbWhere = new StringBuffer();
		//
		m_sbOrderBy = new StringBuffer();
		m_sbOrderBy.append(" \n order by accessTime desc");
 	}
	
	public PageLoader findGLLogInfo(GLVoucherLogInfo searchInfo) {
		
		getGLQuerySQL(searchInfo);
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");
		pageLoader.initPageLoader(
			new AppContext(),
			m_sbFrom.toString(),
			m_sbSelect.toString(),
			m_sbWhere.toString(),
			(int) Constant.PageControl.CODE_PAGELINECOUNT,
			"com.iss.itreasury.glinterface.dataentity.GLVoucherLogInfo",
			null);
		pageLoader.setOrderBy(" " + m_sbOrderBy.toString() + " ");

		return pageLoader;
	}
	
	public void getGLQuerySQL(GLVoucherLogInfo info)
	{
		// select
		m_sbSelect = new StringBuffer();
		m_sbSelect.append(" log.id id,o.sname OFFICE,c.name CURRENCY,log.nstatusid,log.inputdate,u.sname inputUserName");
		// from
		m_sbFrom = new StringBuffer();
		m_sbFrom.append(" SETT_GLVoucher_LOG log,office o,currencyinfo c,userinfo u ");
 		// where
		m_sbWhere = new StringBuffer();
		m_sbWhere.append(" log.officeid = o.id and log.currencyid = c.id and log.inputuserid = u.id ");
		if(info.getOFFICEID()>0)
		{
			m_sbWhere.append(" and log.officeid = "+info.getOFFICEID() );
		}
		if(info.getCURRENCYID()>0)
		{
			m_sbWhere.append(" and log.currencyid = "+info.getCURRENCYID() );
		}
		if( info.getStartDate() != null && info.getStartDate().length() > 0)
			m_sbWhere.append(" and log.inputdate>=to_date('" + info.getStartDate()+ "','yyyy-mm-dd')");
		if( info.getEndDate() != null  && info.getEndDate().length() > 0)
			m_sbWhere.append(" and log.inputdate<=(to_date('" + info.getEndDate() + "','yyyy-mm-dd')+1)");
		//
		m_sbOrderBy = new StringBuffer();
		m_sbOrderBy.append(" order by log.inputdate desc");
 	}
	
	public GLVoucherLogInfo findLogByID(String id) {
		
		StringBuffer sbSQL = new StringBuffer();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		GLVoucherLogInfo info = null;
		try {
			conn = Database.getConnection();
			sbSQL.append(" select log.id id,o.sname OFFICE,c.name CURRENCY,log.nstatusid,log.inputdate,u.sname inputUserName,log.log ");
			sbSQL.append(" from SETT_GLVoucher_LOG log,office o,currencyinfo c,userinfo u ");
			sbSQL.append(" where log.officeid = o.id and log.currencyid = c.id and log.inputuserid = u.id and log.id = ?");
			
			ps = conn.prepareStatement(sbSQL.toString());
			System.out.println(sbSQL.toString());
			int index = 1;
			ps.setString(index++, id);
			rs = ps.executeQuery();
			if(rs.next())
			{
				info = new GLVoucherLogInfo();
				info.setOFFICE(rs.getString("OFFICE"));
				info.setCURRENCY(rs.getString("CURRENCY"));
				info.setNStatusID(rs.getLong("nstatusid"));
				info.setInputDate(rs.getTimestamp("inputdate"));
				info.setInputUserName(rs.getString("inputUserName"));
				info.setLog(rs.getString("log"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
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
				e.printStackTrace();
			}
		}
		
		
		return info;
	}

}
