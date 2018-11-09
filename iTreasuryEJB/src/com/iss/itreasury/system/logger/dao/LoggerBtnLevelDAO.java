package com.iss.itreasury.system.logger.dao;

import java.sql.Connection;

import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.system.logger.dataentity.QueryLoggerInfo;
import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Constant;
import com.iss.system.dao.PageLoader;

/**
 * 业务操作日志dao
 * @author fxzhang
 *
 */
public class LoggerBtnLevelDAO extends ITreasuryDAO {

	StringBuffer m_sbSelect = null;
	StringBuffer m_sbFrom = null;
	StringBuffer m_sbWhere = null;
	StringBuffer m_sbOrderBy = null;
	
	public LoggerBtnLevelDAO(){
		super ("sys_logger_btnlevel" ) ;
	}

	/**
	 * @param tableName
	 */
	public LoggerBtnLevelDAO ( String tableName )
	{
		super ( tableName ) ;
	}
	/**
	 * @param tableName
	 * @param conn
	 */
	public LoggerBtnLevelDAO ( Connection conn )
	{
		super ( "sys_logger_btnlevel"  , conn ) ;
	}
	/**
	 * @param tableName
	 * @param conn
	 */
	public LoggerBtnLevelDAO ( String tableName , Connection conn )
	{
		super ( tableName , conn ) ;
	}
	/**
	 * @param isNeedPrefix
	 */
	public LoggerBtnLevelDAO ( boolean isNeedPrefix )
	{
		super ( isNeedPrefix ) ;
	}
	/**
	 * @param tableName
	 * @param isNeedPrefix
	 */
	public LoggerBtnLevelDAO ( String tableName , boolean isNeedPrefix )
	{
		super ( tableName , isNeedPrefix ) ;
	}
	/**
	 * @param tableName
	 * @param isNeedPrefix
	 * @param conn
	 */
	public LoggerBtnLevelDAO ( String tableName , boolean isNeedPrefix , Connection conn )
	{
		super ( tableName , isNeedPrefix , conn ) ;
	}
	
	public PageLoader findLoggerByCondition(QueryLoggerInfo qli) {
		getQuerySQL(qli);
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");
		pageLoader.initPageLoader(
			new AppContext(),
			m_sbFrom.toString(),
			m_sbSelect.toString(),
			m_sbWhere.toString(),
			(int) Constant.PageControl.CODE_PAGELINECOUNT,
			"com.iss.itreasury.system.logger.dataentity.LoggerBtnLevelInfo",
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
		m_sbFrom.append("   select s.id as id, s.moduleID as moduleid,s.officeID as officeid,s.currencyID as currencyid,s.accessTime as accesstime,s.userID as userid,s.userName as username,s.functionPointCode as functionpointcode,s.functionPointDescription as functionpointdescription,s.remoteIP as remoteip,s.remoteHost as remotehost ");
		m_sbFrom.append("	,s.sbusinesstype as businesstype,s.stranscode as transcode,s.sactiontype as actiontype,s.sresult as result,s.sfailreason as failreason, d.departmentname ");
		m_sbFrom.append("   from sys_logger_btnlevel s, department d,userinfo u ");
		m_sbFrom.append("   where 1=1 and s.officeid="+qli.getOfficeID() );
		m_sbFrom.append("   and s.userid=u.id and u.ndepartmentid = d.id(+)" );
		if(qli.getModuleID() > 0)
		{
			m_sbFrom.append(" and  s.moduleid= "+qli.getModuleID() );
		}
		if(qli.getCurrencyID()>0)
		{
			m_sbFrom.append(" and  s.currencyid= "+qli.getCurrencyID() );
		}
		if( qli.getUserName()!=null && qli.getUserName().length() > 0 )
			m_sbFrom.append("   and s.username='"+qli.getUserName()+"'");
		if( qli.getStartDate() != null && qli.getStartDate().length() > 0)
			m_sbFrom.append("   and s.accesstime>=to_date('" + qli.getStartDate()+ "','yyyy-mm-dd hh24:mi:ss')");
		if( qli.getEndDate() != null  && qli.getEndDate().length() > 0)
			m_sbFrom.append("   and s.accesstime<=(to_date('" + qli.getEndDate() + "','yyyy-mm-dd hh24:mi:ss'))");
		if (qli.getResult()!=null && (!qli.getResult().trim().equals("")))
			m_sbFrom.append("   and s.sresult='"+qli.getResult()+"'");
		if (qli.getDepartmentid() > 0)
			m_sbFrom.append("   and d.id="+qli.getDepartmentid());
		System.out.println(qli.getDepartmentid());
		m_sbFrom.append("\n ) rr \n");
 		// where
		m_sbWhere = new StringBuffer();
		//
		m_sbOrderBy = new StringBuffer();
		m_sbOrderBy.append(" \n order by accesstime desc");
		System.out.println("m_sbFrom ============ " + m_sbFrom);
 	}
}
