package com.iss.itreasury.system.translog.bizlogic;


import com.iss.itreasury.ebank.util.SessionOB;
import com.iss.itreasury.system.translog.dao.TransLogDao;
import com.iss.itreasury.system.translog.dataentity.ILogTemplate;
import com.iss.itreasury.system.translog.dataentity.LogBaseInfo;
import com.iss.itreasury.system.translog.dataentity.QueryTransLogInfo;
import com.iss.itreasury.system.translog.dataentity.TransInfo;
import com.iss.itreasury.system.translog.dataentity.TranslogInfo;
import com.iss.itreasury.util.Config;
import com.iss.itreasury.util.ConfigConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log4j;
import com.iss.itreasury.util.SessionMng;
import com.iss.system.dao.PageLoader;

public class TranslogBiz 
{
	
	private  Log4j log4j = new Log4j(Constant.ModuleType.SYSTEM, this);

	
	/**
	 * @TranslogBiz.java Create on Jun 3, 2009 9:41:37 AM
	 * @author minzhao
	 * @param session
	 * @param obj
	 * @param transinfo
	 * @throws Exception
	 * void 结算使用此方法
	 */
	public void saveTransLogInfo(SessionMng session,Object obj,TransInfo transinfo) throws Exception
	{
		TranslogInfo logInfo = new TranslogInfo();
		
		logInfo.setModuleid(session.m_lModuleID);
		logInfo.setOfficeid(session.m_lOfficeID);
		logInfo.setUserid(session.m_lUserID);
		logInfo.setUsername(session.m_strUserName);
		logInfo.setNcurrencyid(session.m_lCurrencyID);
		logInfo.setAccesstime(Env.getSystemDateTime());
		logInfo.setFunctionpointdescription("");//这里错误等待URL权限过滤完成后修改
		logInfo.setClientIp(transinfo.getHostip());
		logInfo.setClientName(transinfo.getHostname());
		logInfo.setStatus(transinfo.getStatus());
		String logClassPath = obj.getClass().getName() + "LogInfo";
		String[] temp=logClassPath.split("\\.");
		String logClassName="com.iss.itreasury.system.translog.dataentity."+temp[temp.length-1];
		
		try{
			
			ClassLoader carLoader=Thread.currentThread().getContextClassLoader();
			Class logClass=carLoader.loadClass(logClassName);
			ILogTemplate logImpl = (LogBaseInfo) logClass.newInstance();
			logImpl.setOriginObjInfo(obj);
			logImpl.setActionType(transinfo.getActionType());
			logInfo.setRemark(logImpl.getRemark(transinfo.getTransType()));
			logInfo.setTranstype(logImpl.getTranstype());
		}catch(Exception ex){
			try
			{
			Class logClass=Class.forName(logClassName);
			ILogTemplate logImpl = (LogBaseInfo) logClass.newInstance();
			logImpl.setOriginObjInfo(obj);
			logImpl.setActionType(transinfo.getActionType());
			logInfo.setRemark(logImpl.getRemark(transinfo.getTransType()));
			logInfo.setTranstype(logImpl.getTranstype());
			}
			catch(Exception e)
			{
			e.printStackTrace();
			throw new IException("结算业务日志初始化失败");
			}
			
		}

		try
		{
			TransLogDao translogdao=new TransLogDao();
			translogdao.save(logInfo);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			throw new IException("结算业务日志数据插入数据库错误");
		}
		
		
	}	
	
	

	/**
	 * @TranslogBiz.java Create on Jun 3, 2009 9:43:05 AM
	 * @author minzhao
	 * @param session
	 * @param obj
	 * @param transinfo
	 * @throws Exception
	 * void 网银使用此方法
	 */
	public void saveTransLogInfo(SessionOB session,Object obj,TransInfo transinfo ) throws Exception
	{
		boolean isNeedTransLog=false;
		try
		{
			isNeedTransLog= Config.getBoolean(ConfigConstant.GLOBAL_EBANK_TRANSLOG, false);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		if(isNeedTransLog)
		{
			TranslogInfo logInfo = new TranslogInfo();
			
			logInfo.setModuleid(session.m_lModuleID);
			logInfo.setOfficeid(session.m_lOfficeID);
			logInfo.setUserid(session.m_lUserID);
			logInfo.setUsername(session.m_strUserName);
			logInfo.setNcurrencyid(session.m_lCurrencyID);
			logInfo.setAccesstime(Env.getSystemDateTime());
			logInfo.setFunctionpointdescription(session.m_NowPrivilegeName);
			logInfo.setClientIp(transinfo.getHostip());
			logInfo.setClientName(transinfo.getHostname());
			logInfo.setStatus(transinfo.getStatus());
			String logClassPath = obj.getClass().getName() + "LogInfo";
			String[] temp=logClassPath.split("\\.");
			String logClassName="com.iss.itreasury.system.translog.dataentity."+temp[temp.length-1];
			
			try{
				
				ClassLoader carLoader=Thread.currentThread().getContextClassLoader();
				Class logClass=carLoader.loadClass(logClassName);
				ILogTemplate logImpl = (LogBaseInfo) logClass.newInstance();
				logImpl.setOriginObjInfo(obj);
				logImpl.setActionType(transinfo.getActionType());
				logInfo.setRemark(logImpl.getRemark(transinfo.getTransType()));
				logInfo.setTranstype(logImpl.getTranstype());
				
			}catch(Exception ex){
				try
				{
				Class logClass=Class.forName(logClassName);
				ILogTemplate logImpl = (LogBaseInfo) logClass.newInstance();
				logImpl.setOriginObjInfo(obj);
				logImpl.setActionType(transinfo.getActionType());
				logInfo.setRemark(logImpl.getRemark(transinfo.getTransType()));
				logInfo.setTranstype(logImpl.getTranstype());
				}
				catch(Exception e)
				{
				e.printStackTrace();
				throw new IException("网银业务日志初始化失败");
				}
				
			}
			
			
			try
			{
				TransLogDao translogdao=new TransLogDao();
				translogdao.save(logInfo);
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				throw new IException("网银日志数据插入数据库错误");
			}
			
			
		}
		else
		{
			log4j.print("global.xml配置文件中isNeedTransLog为"+isNeedTransLog+"不保存业务日志");
		}
	}
	
	
	/**
	 * @TranslogBiz.java Create on Jun 3, 2009 9:43:12 AM
	 * @author minzhao
	 * @param session
	 * @param transinfo
	 * @throws Exception
	 * void 查询使用此方法
	 */
	public void saveTransLogInfo(SessionOB session,TransInfo transinfo ) throws Exception
	{
		TranslogInfo logInfo = new TranslogInfo();
		
		logInfo.setModuleid(session.m_lModuleID);
		logInfo.setOfficeid(session.m_lOfficeID);
		logInfo.setUserid(session.m_lUserID);
		logInfo.setUsername(session.m_strUserName);
		logInfo.setNcurrencyid(session.m_lCurrencyID);
		logInfo.setAccesstime(Env.getSystemDateTime());
		logInfo.setFunctionpointdescription(session.m_NowPrivilegeName);
		logInfo.setClientIp(transinfo.getHostip());
		logInfo.setClientName(transinfo.getHostname());
		logInfo.setStatus(Constant.SUCCESSFUL);//查询状态永远为成功，需要讨论
	
			
		logInfo.setRemark(transinfo.getSearchremark());
		logInfo.setTranstype(Constant.TransLogActionType.getName(Constant.TransLogActionType.search)+session.m_NowPrivilegeName);
			
		
		
		try
		{
			TransLogDao translogdao=new TransLogDao();
			translogdao.save(logInfo);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			throw new IException("网银日志数据插入数据库错误");
		}
		
		
	}
	
//	翻页查询
	public PageLoader queryTransLogInfo(QueryTransLogInfo queryConditionTransLogInfo) throws Exception
	{
		PageLoader pageLoader = null;
		TransLogDao transLogDao = new TransLogDao();
		pageLoader = transLogDao.queryTransLogInfo(queryConditionTransLogInfo);
		return pageLoader;
	}
}
