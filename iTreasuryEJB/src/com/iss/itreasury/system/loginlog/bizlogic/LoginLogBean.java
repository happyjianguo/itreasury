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
	
	//������¼��־������
	public void addLoginLog(SessionMng sessionMng, long type, String clientIp,long status,String reason){
		try{
			LoginLogInfo loginLogInfo = new LoginLogInfo();
			loginLogInfo.setAccessTime(Env.getSystemDateTime());	//���õ�¼ʱ��
			if(type==Constant.SETT_USER){							//�ж��ǲ�����ͻ�IDΪ1����������ȡsession��Ϣ����δ�õ���Ԥ��
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
			logger.error("�û���¼ʱ��¼��¼��־ʧ�ܣ�ԭ��" + e.getMessage());
			e.printStackTrace();
		}
	}
	
	//������¼��־����������������������������ͬ
	public void addLoginLog(SessionOB sessionMng, long type, String clientIp,long status,String reason){
	
		try{
			LoginLogInfo loginLogInfo = new LoginLogInfo();
			loginLogInfo.setAccessTime(Env.getSystemDateTime());
			if(type==Constant.SETT_USER){							//�ж��ǲ�����ͻ�IDΪ1����������ȡsession��Ϣ����δ�õ���Ԥ��
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
			logger.error("�����û���¼ʱ��¼��¼��־ʧ�ܣ�ԭ��" + e.getMessage());
			e.printStackTrace();
		}
	}
	
	//ȡ���ϴε�¼ʱ��
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
	
	//��ҳ��ѯ
	public PageLoader queryTransactionInfo(LoginLogInfo loginLogInfo) throws Exception{
		PageLoader pageLoader = null;
		LoginLogDao dao = new LoginLogDao();
		pageLoader = dao.queryTransactionInfo(loginLogInfo);
		return pageLoader;
	}
}
