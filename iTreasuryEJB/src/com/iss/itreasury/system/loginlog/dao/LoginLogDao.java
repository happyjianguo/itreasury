package com.iss.itreasury.system.loginlog.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.system.loginlog.dataentity.LoginLogInfo;
import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Constant;
import com.iss.system.dao.PageLoader;

public class LoginLogDao extends ITreasuryDAO{
		
	//构造函数
	public LoginLogDao(Connection conn)
    {
        super("sys_loginrecord","seq_sys_loginrecord",conn);
    }
	
	
	public LoginLogDao()
    {
        super("sys_loginrecord","seq_sys_loginrecord");
    }
	
	//public final static int OrderBy_AccountNo = 1;
	//
	StringBuffer m_sbSelect = null;
	StringBuffer m_sbFrom = null;
	StringBuffer m_sbWhere = null;
	StringBuffer m_sbOrderBy = null;
	//翻页查询
	public PageLoader queryTransactionInfo(LoginLogInfo info) throws Exception
	{
		getTransactionSQL(info);

		//
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");
		pageLoader.initPageLoader(
			new AppContext(),
			m_sbFrom.toString(),
			m_sbSelect.toString(),
			m_sbWhere.toString(),
			(int) Constant.PageControl.CODE_PAGELINECOUNT,
			"com.iss.itreasury.system.loginlog.dataentity.LoginLogInfo",
			null);
		pageLoader.setOrderBy(" " + m_sbOrderBy.toString() + " ");
		return pageLoader;
	}
	
	/**
	 * 产生查询SQL
	 * @param info
	 */
	public void getTransactionSQL(LoginLogInfo info){
		m_sbSelect = new StringBuffer();
		// select 
		m_sbSelect.append(" ID,CurrencyID,OfficeID,ClientId,UserType,AccessTime,UserName,ClientIp,status,reason \n");
		// from 
		m_sbFrom = new StringBuffer();
		m_sbFrom.append(" SYS_LOGINRECORD \n");
		// where 
		m_sbWhere = new StringBuffer();
		//m_sbWhere.append(" StatusID>0 ");
		
		m_sbWhere.append(" 1=1 ");
		
		if (info.getOfficeId() > 0)
			m_sbWhere.append(" and OfficeID=" + info.getOfficeId() + "");
		if (info.getCurrencyId() > 0)
			m_sbWhere.append(" and CurrencyID=" + info.getCurrencyId() + "");
		if(info.getUserType()!=-1){
			m_sbWhere.append(" and UserType = " + info.getUserType() + "");
		}
		if(info.getUserName()!=null&&!info.getUserName().equals("")){
			m_sbWhere.append(" and UserName like '%" + info.getUserName() + "%'");
		}
		if(info.getStartDate()!=null&&!info.getStartDate().equals("")){
			m_sbWhere.append(" and AccessTime >= to_date('" + info.getStartDate() + "','yyyy-mm-dd')");
		}
		if(info.getEndDate()!=null&&!info.getEndDate().equals("")){
			Date date = Date.valueOf(info.getEndDate());
			date.setTime(date.getTime()+24*60*60*1000);
			String endDate = date.toString();
			//m_sbWhere.append(" and AccessTime <= to_date('" + info.getEndDate() + "','yyyy-mm-dd')");
			m_sbWhere.append(" and AccessTime < to_date('" + endDate + "','yyyy-mm-dd')");
		}
		//
		
		if(info.getStatus()!=-1)
		{
			m_sbWhere.append(" and Status ="+info.getStatus());
		}
		m_sbOrderBy = new StringBuffer();
		m_sbOrderBy.append(" order by id desc");
	}
    
    //新增登录日志
	public void addLoginLog(LoginLogInfo loginLogInfo) throws ITreasuryDAOException
	{
		add(loginLogInfo);
	}
	
	//取得上次登录时间
	public Timestamp getLastLoginTime (long userId,long type)throws ITreasuryDAOException, SQLException{
		initDAO();
		Timestamp lastLoginTime = null;
		String strSql = "select accesstime from (select accesstime from sys_loginrecord where userid = " + userId + " and usertype = " + type + " order by id desc) where rownum <= 1";
		try{
    		transPS = transConn.prepareStatement(strSql);
    		transRS = transPS.executeQuery();
    		while(transRS.next()){
    			lastLoginTime = transRS.getTimestamp(1);
    		} 		
    	}catch(Exception e){
    		e.printStackTrace();
    	}finally{
			finalizeDAO();
		}
		return lastLoginTime;
	}
	
}
