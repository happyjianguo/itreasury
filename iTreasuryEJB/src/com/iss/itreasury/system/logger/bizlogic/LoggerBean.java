/*
 * Created on 2004-6-7
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.system.logger.bizlogic;

import java.rmi.RemoteException;
import java.sql.Connection;

import com.iss.itreasury.glinterface.dataentity.GLVoucherLogInfo;
import com.iss.itreasury.system.logger.dao.LoggerBtnLevelDAO;
import com.iss.itreasury.system.logger.dao.LoggerDAO;
import com.iss.itreasury.system.logger.dataentity.LoggerBtnLevelInfo;
import com.iss.itreasury.system.logger.dataentity.LoggerInfo;
import com.iss.itreasury.system.logger.dataentity.QueryLoggerInfo;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.IException; 
import com.iss.system.dao.PageLoader;

/**
 * @author yiwang
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class LoggerBean {
	/**
	 * 
	 */
	public LoggerBean ( )
	{
		super ( ) ;
		// TODO Auto-generated constructor stub
	}
	/*public static void main ( String[] args )
	{
		try
		{
			LoggerDAO dao = new LoggerDAO();
			LoggerInfo info = new LoggerInfo();
			info.setCurrencyID(1);
			info.setId(1);
			info.setFunctionPointCode("1111");
			info.setOfficeID(1);
			
			dao.add(info);
			
			QueryLoggerInfo qli = new QueryLoggerInfo();
			qli.setCurrencyID(1);
			qli.setModuleID(1);
			qli.setOfficeID(1);
			qli.setUserID(91);
			qli.setStartDate("2004-06-10");
			qli.setEndDate("2004-06-10");
			dao.getQuerySQL(qli);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}*/
	// 新增日志
	public void add(LoggerInfo li) throws Exception
	{
		LoggerDAO dao = new LoggerDAO();
		dao.add(li);
	}
	// 查询日志
	public PageLoader findLoggerByCondition(QueryLoggerInfo qli) throws Exception
	{
		return (new LoggerDAO()).findLoggerByCondition(qli);
	}

	public PageLoader findGLLogInfo(GLVoucherLogInfo searchInfo) throws RemoteException {
		LoggerDAO dao = new LoggerDAO();
		return dao.findGLLogInfo(searchInfo);
	}
	
	public GLVoucherLogInfo findLogByID(String id){
		LoggerDAO dao = new LoggerDAO();
		return dao.findLogByID(id);
	}
	
	/**
	 * add by jbpan 2012-5-29
	 * 保存日志（业务操作日志）
	 * @param lblInfo
	 * @throws IException
	 */
	public void add(LoggerBtnLevelInfo lblInfo) throws IException
	{
		try
		{
			LoggerBtnLevelDAO lblDao = new LoggerBtnLevelDAO();
			lblInfo.setAccessTime(Env.getSystemDateTime());
			String failReason = lblInfo.getFailReason();
			if(failReason != null && failReason.length()>50)
			{
				//截取50个字符
				lblInfo.setFailReason(failReason.substring(0, 50));
				
			}
			lblDao.add(lblInfo);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001");
		}		
	}
	
	/**
	 * add by jbpan 2012-5-29
	 * 查询日志（业务操作日志）
	 * @param qli
	 * @return
	 * @throws IException
	 */
	public PageLoader findLoggerBtnByCondition(QueryLoggerInfo qli) throws IException
	{
		return (new LoggerBtnLevelDAO()).findLoggerByCondition(qli);
	}
}
