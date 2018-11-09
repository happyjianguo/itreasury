package com.iss.itreasury.system.loginlog.bizlogic;


import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import com.iss.itreasury.ebank.util.SessionOB;
import com.iss.itreasury.system.loginlog.dao.LoginLogDao;
import com.iss.itreasury.system.loginlog.dataentity.LoginLogInfo;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.Log4j;
import com.iss.itreasury.util.SessionMng;
import com.iss.system.dao.PageLoader;


public class LoginLogBean {
	
	private Log4j logger = new Log4j(Constant.ModuleType.SYSTEM, this);
	
	//新增登录日志，财务
	public void addLoginLog(SessionMng sessionMng, long type, String clientIp,long status,String reason){
		try{
			LoginLogInfo loginLogInfo = new LoginLogInfo();
			loginLogInfo.setAccessTime(Env.getSystemDateTime());	//设置登录时间
			if(type==Constant.SETT_USER){							//判断是财务则客户ID为1，是网银则取session信息，暂未用到，预留
				loginLogInfo.setClientId(-1);
			}else if(type==Constant.EBANK_USER){
				loginLogInfo.setClientId(sessionMng.m_lClientID);
			}
			loginLogInfo.setClientIp(clientIp);
			loginLogInfo.setCurrencyId(sessionMng.m_lCurrencyID);
			String hostIp = null;
			try{
				hostIp = InetAddress.getLocalHost().getHostAddress();
			}catch(UnknownHostException e){
				hostIp = "unknown host";
			}	
			loginLogInfo.setHostIp(hostIp);
			loginLogInfo.setOfficeId(sessionMng.m_lOfficeID);
			loginLogInfo.setUserId(sessionMng.m_lUserID);
			loginLogInfo.setUserName(sessionMng.m_strUserName);
			loginLogInfo.setUserType(type);
			loginLogInfo.setStatus(status);
			loginLogInfo.setReason(reason);

			LoginLogDao loginLogDao = new LoginLogDao();
			loginLogDao.addLoginLog(loginLogInfo);
		}catch(Exception e){
			logger.error("用户登录时记录登录日志失败，原因：" + e.getMessage());
			e.printStackTrace();
		}
	}
	
	//新增登录日志，网银，除传入参数其他与财务相同
	public void addLoginLog(SessionOB sessionMng, long type, String clientIp,long status,String reason){
	
		try{
			LoginLogInfo loginLogInfo = new LoginLogInfo();
			loginLogInfo.setAccessTime(Env.getSystemDateTime());
			if(type==Constant.SETT_USER){							//判断是财务则客户ID为1，是网银则取session信息，暂未用到，预留
				loginLogInfo.setClientId(-1);
			}else if(type==Constant.EBANK_USER){
				loginLogInfo.setClientId(sessionMng.m_lClientID);
			}
			loginLogInfo.setClientIp(clientIp);
			loginLogInfo.setCurrencyId(sessionMng.m_lCurrencyID);
			
			String hostIp = null;
			try{
				hostIp = InetAddress.getLocalHost().getHostAddress();
			}catch(UnknownHostException e){
				hostIp = "unknown host";
			}		
			loginLogInfo.setHostIp(hostIp);
			loginLogInfo.setOfficeId(sessionMng.m_lOfficeID);
			loginLogInfo.setUserId(sessionMng.m_lUserID);
			loginLogInfo.setUserName(sessionMng.m_strUserName);
			loginLogInfo.setUserType(type);
			loginLogInfo.setStatus(status);
			loginLogInfo.setReason(reason);
			
			LoginLogDao loginLogDao = new LoginLogDao();

			loginLogDao.addLoginLog(loginLogInfo);
		}catch(Exception e){
			logger.error("网银用户登录时记录登录日志失败，原因：" + e.getMessage());
			e.printStackTrace();
		}
	}
	
	//取得上次登录时间
	public Timestamp getLastLoginTime(long userId,long type){
		Timestamp lastLoginTime = null;
		LoginLogDao loginLogDao = new LoginLogDao();
		try{
			lastLoginTime = loginLogDao.getLastLoginTime(userId,type);
		}catch(Exception e){
			e.printStackTrace();
		}
		return lastLoginTime;
	}
	
	//翻页查询
	public PageLoader queryTransactionInfo(LoginLogInfo loginLogInfo) throws Exception{
		PageLoader pageLoader = null;
		LoginLogDao dao = new LoginLogDao();
		pageLoader = dao.queryTransactionInfo(loginLogInfo);
		return pageLoader;
	}
}
