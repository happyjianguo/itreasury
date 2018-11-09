/*
 * SessionMng.java
 *
 * Created on 2001年12月6日, 下午2:51
 *  修改  2004-10-24 by jinchen  ldap-oracle 移植
 */
package com.iss.itreasury.ebank.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSessionBindingListener;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.ebank.privilege.dao.OB_PrivilegeDAO;
import com.iss.itreasury.ebank.privilege.dao.OB_UserDAO;
import com.iss.itreasury.ebank.privilege.dataentity.OB_PrivilegeInfo;
import com.iss.itreasury.ebank.privilege.dataentity.OB_UserInfo;
import com.iss.itreasury.system.loginlog.bizlogic.LoginLogBean;
import com.iss.itreasury.system.bizdelegation.LoggerDelegation;
import com.iss.itreasury.system.logger.dataentity.HostInfo;
import com.iss.itreasury.system.logger.dataentity.LoggerInfo;
import com.iss.itreasury.system.privilege.dataentity.Sys_UserInfo;
import com.iss.itreasury.system.user.dataentity.LoginUserInfo;
import com.iss.itreasury.util.ActionMessages;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Encrypt;
import com.iss.itreasury.util.Encryptiontools;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log4j;
import com.iss.itreasury.util.RequestAlteredValidator;
import com.iss.system.dao.PageLoader;

/**
 * 
 * @author yiwang
 * @version
 */
public class SessionOB extends java.lang.Object implements
        java.io.Serializable, HttpSessionBindingListener
{
    public long m_lOfficeID = -1;

    public String m_strLogin = "";

    public String m_strOfficeName = "";
    
    public String m_strOfficeNo	= "" ;

    public long m_lClientID = -1;

    public String m_strClientCode = "";

    public String m_strClientName = "";

    public String m_strClientShortName = "";

    public long m_lCurrencyID = -1;

    public boolean m_bIsStockCompany = false;

    public long m_lUserID = -1;

    public String m_strUserName = "";

    public String m_strCurrencySymbol = "￥";

    public String m_strSignature = "";

    public String m_strCertIssuer = "";    
    
    public String m_strCertSerialNumber = ""; //added by mzh_fu 2007/09/03 证书序列号
    
    public String m_strCertCN=""; //added by mzh_fu 2008/01/30 证书CN
    
    public long m_lSAID = -1;

    public Collection cPrivilegeOfUser = null;

    public String m_strMenu = "";

    public long m_lModuleID = Constant.ModuleType.EBANK;

    //	分页控制类
    private HashMap hmPageLoader = null;

    private HashMap hmQueryCondition = null;

    //随机数管理器
    private Random random = null;

    private long currentKey = -1;

    // 数字签名
    private String DigitalSign = null;

    // 证书主题
    private String certdn = "";
    
	// 证书号
	private String certcn = "";
	
	// 证书序列号
	private String certsn = "";

    /*
     * 添加权限相关集合 by jinchen 2004-10-24
     */
    //private Vector m_vPrivilegeNo;

    //public Vector m_vGroupID;

    //private Collection m_userPrivilege;

    //private HashMap m_privilegeNameRef = new HashMap();
    
    //minzhao 20090519  增加url过滤 start
    private Collection m_userPrivilege;
    private Collection m_allPrivilege;
    public String m_NowPrivilegeName="";
    private HashMap m_privilegeNameRef = new HashMap();
    
    
    private static Log4j	log4j				= null ;
    
    private ActionMessages	m_actionMessages	= new ActionMessages ( ) ;
    
    private HashMap			sumResult			= null;				//统计结果信息	

    /** Creates new SessionMng */
    public Timestamp dLastLoginTime = null;
    
    public SessionOB()
    {
    	log4j = new Log4j ( Constant.ModuleType.EBANK , this ) ;
    }

    public void valueUnbound(
            javax.servlet.http.HttpSessionBindingEvent httpSessionBindingEvent)
    {
        logout();
    }

	public void setServletInfo(int port,String name){
		if(com.iss.itreasury.evoucher.setting.dao.PrintSettingDao.servletPort==0){
			com.iss.itreasury.evoucher.setting.dao.PrintSettingDao.servletPort = port;
			com.iss.itreasury.evoucher.setting.dao.PrintSettingDao.servletName = name;
		}
	}
	
	public void setServletInfo(int port,String name,String URLContextPath)
	{
		if(com.iss.itreasury.evoucher.setting.dao.PrintSettingDao.servletPort==0){
			com.iss.itreasury.evoucher.setting.dao.PrintSettingDao.servletPort = port;
			com.iss.itreasury.evoucher.setting.dao.PrintSettingDao.servletName = name;
			com.iss.itreasury.evoucher.setting.dao.PrintSettingDao.servletContextPath = URLContextPath;
		}		
	}
	
    public void valueBound(
            javax.servlet.http.HttpSessionBindingEvent httpSessionBindingEvent)
    {
    }

    /**
     * 登录
     * 
     * @param strLogin
     * @param strPassword
     * @param lModuleID
     * @throws IException
     * @throws ITreasuryDAOException
     * @throws SQLException
     */
    public void init(String strLogin, String strPassword,String clientIp) throws Exception
    {
        try
        {
        	OB_UserDAO userdao = new OB_UserDAO();
        	LoginUserInfo loginInfo = userdao.getLoginUserInfo(strLogin,strPassword);
            if(loginInfo != null){
                m_lUserID = loginInfo.getId();
                m_lClientID = loginInfo.getClientID();
                m_lCurrencyID = loginInfo.getCurrencyId();
                m_strCurrencySymbol = loginInfo.getCurrencyIdSymbol();
                m_strLogin = strLogin;
                m_strClientName = loginInfo.getClientName();
                m_strClientShortName = loginInfo.getSimpleName();
                m_lOfficeID = loginInfo.getOfficeID();
                m_strOfficeNo = loginInfo.getOfficeNo();
                m_strClientCode = loginInfo.getClientCode();
                m_strOfficeName = loginInfo.getOfficeName();
                m_strUserName = loginInfo.getUsername();
                m_lSAID = loginInfo.getSaId();
                if(loginInfo.getCertSerialNumber() != null){
                	m_strCertSerialNumber = loginInfo.getCertSerialNumber();
                }
                if(loginInfo.getSubjectCommonName() != null){
                	m_strCertCN = loginInfo.getSubjectCommonName();
                }
            	certcn = loginInfo.getCertcn();
        		certsn = loginInfo.getCertsn();
        		dLastLoginTime = loginInfo.getLastLoginTime();
                random = new Random();
            }
            else {
            	log4j.print("用户名或密码不正确, 请重新登录");
            	throw new IException("用户名或密码不正确, 请重新登录");
            }

            //Modify by leiyang 2008-11-19
            //判断该用户是否是网银系统管理员
            if (userdao.isSA(m_lUserID))
            {
                m_lSAID = m_lUserID;
            }

            
            OB_PrivilegeDAO privilegeDao = new OB_PrivilegeDAO();
            m_userPrivilege = privilegeDao.getPrivilegeOfUser(m_lUserID, m_lModuleID);
            m_allPrivilege = privilegeDao.getAllPrivilege();
            if(m_userPrivilege == null){
            	log4j.print("用户没有可用权限");
            	throw new IException("用户没有可用权限");
            }
            else {
            	StringBuffer strPrivName = new StringBuffer();
                StringBuffer strPrivNo = new StringBuffer();
                StringBuffer strPrivLevel = new StringBuffer();
                StringBuffer strPrivUrl = new StringBuffer();
                OB_PrivilegeInfo privilegeInfo = null;
                Iterator iterator = m_userPrivilege.iterator();
                while (iterator.hasNext())
                {
                	privilegeInfo = (OB_PrivilegeInfo)iterator.next();
                	strPrivName.append(privilegeInfo.getName() + ";;");
                	strPrivNo.append(privilegeInfo.getPrivilegeNo() + ";;");
                	strPrivLevel.append(privilegeInfo.getPlevel() + ";;");
                	strPrivUrl.append(privilegeInfo.getMenuUrl() + ";;");
                	m_privilegeNameRef.put(privilegeInfo.getPrivilegeNo(), privilegeInfo.getName() );
                }
                m_strMenu = strPrivName.substring(0,strPrivName.length()-2) + "::" + strPrivNo.substring(0, strPrivNo.length()-2) + "::" + strPrivLevel.substring(0,strPrivLevel.length()-2) + "::" + strPrivUrl.substring(0,strPrivUrl.length()-2);
            }

        }catch (IException e) 
        {
        	OB_UserDAO userdao = new OB_UserDAO();
        	Collection c=null;
        	c = userdao.findByLoginNo(strLogin);
        	SessionOB sM=new SessionOB();
        	
        	
        	if(c!=null)
        	{
    			Iterator it = c.iterator ( ) ;
    			
    			while (it.hasNext())
    			{
    				OB_UserInfo info = (OB_UserInfo) it.next ( ) ;

    				sM.m_lUserID = info.getId();
    				sM.m_lCurrencyID = info.getNCurrencyId();
    				sM.m_lOfficeID = info.getNOfficeID();
    				sM.m_lClientID = info.getNClientId();
    				sM.m_strUserName = info.getSName();
    				
    			}
        		

        	}
        	else
        	{
        		sM.m_strLogin = strLogin;
        	}
        	  //写登录日志
            LoginLogBean loginLogBean = new LoginLogBean();
            loginLogBean.addLoginLog(sM,Constant.EBANK_USER,clientIp,Constant.FAIL,e.getMessage());
            
        	throw e;
        }
    }
    /**
     * 针对于SDC集成过滤器调用
     * @param strLogin
     * @param strPassword
     * @param clientIp
     * @param integration
     * @throws Exception
     */
    public void init(String strLogin, String strPassword,String clientIp,String integration) throws Exception
    {
        try
        {
        	OB_UserDAO userdao = new OB_UserDAO();
        	LoginUserInfo loginInfo = userdao.getLoginUserInfo(strLogin,strPassword,"SDC");
            if(loginInfo != null){
                m_lUserID = loginInfo.getId();
                m_lClientID = loginInfo.getClientID();
                m_lCurrencyID = loginInfo.getCurrencyId();
                m_strCurrencySymbol = loginInfo.getCurrencyIdSymbol();
                m_strLogin = strLogin;
                m_strClientName = loginInfo.getClientName();
                m_strClientShortName = loginInfo.getSimpleName();
                m_lOfficeID = loginInfo.getOfficeID();
                m_strOfficeNo = loginInfo.getOfficeNo();
                m_strClientCode = loginInfo.getClientCode();
                m_strOfficeName = loginInfo.getOfficeName();
                m_strUserName = loginInfo.getUsername();
                m_lSAID = loginInfo.getSaId();
                if(loginInfo.getCertSerialNumber() != null){
                	m_strCertSerialNumber = loginInfo.getCertSerialNumber();
                }
                if(loginInfo.getSubjectCommonName() != null){
                	m_strCertCN = loginInfo.getSubjectCommonName();
                }
        		dLastLoginTime = loginInfo.getLastLoginTime();
                random = new Random();
            }
            else {
            	log4j.print("用户名或密码不正确, 请重新登录");
            	throw new IException("用户名或密码不正确, 请重新登录");
            }

            //Modify by leiyang 2008-11-19
            //判断该用户是否是网银系统管理员
            if (userdao.isSA(m_lUserID))
            {
                m_lSAID = m_lUserID;
            }

            //Modify by wmzheng 2011-01-07 sdc集成登录，注释掉老用户组菜单
//            OB_PrivilegeDAO privilegeDao = new OB_PrivilegeDAO();
//            m_userPrivilege = privilegeDao.getPrivilegeOfUser(m_lUserID, m_lModuleID);
//            m_allPrivilege = privilegeDao.getAllPrivilege();
//            if(m_userPrivilege == null){
//            	log4j.print("用户没有可用权限");
//            	throw new IException("用户没有可用权限");
//            }
//            else {
//            	StringBuffer strPrivName = new StringBuffer();
//                StringBuffer strPrivNo = new StringBuffer();
//                StringBuffer strPrivLevel = new StringBuffer();
//                StringBuffer strPrivUrl = new StringBuffer();
//                OB_PrivilegeInfo privilegeInfo = null;
//                Iterator iterator = m_userPrivilege.iterator();
//                while (iterator.hasNext())
//                {
//                	privilegeInfo = (OB_PrivilegeInfo)iterator.next();
//                	strPrivName.append(privilegeInfo.getName() + ";;");
//                	strPrivNo.append(privilegeInfo.getPrivilegeNo() + ";;");
//                	strPrivLevel.append(privilegeInfo.getPlevel() + ";;");
//                	strPrivUrl.append(privilegeInfo.getMenuUrl() + ";;");
//                	m_privilegeNameRef.put(privilegeInfo.getPrivilegeNo(), privilegeInfo.getName() );
//                }
//                m_strMenu = strPrivName.substring(0,strPrivName.length()-2) + "::" + strPrivNo.substring(0, strPrivNo.length()-2) + "::" + strPrivLevel.substring(0,strPrivLevel.length()-2) + "::" + strPrivUrl.substring(0,strPrivUrl.length()-2);
//            }

        }catch (IException e) 
        {
        	OB_UserDAO userdao = new OB_UserDAO();
        	Collection c=null;
        	c = userdao.findByLoginNo(strLogin);
        	SessionOB sM=new SessionOB();
        	
        	
        	if(c!=null)
        	{
    			Iterator it = c.iterator ( ) ;
    			
    			while (it.hasNext())
    			{
    				OB_UserInfo info = (OB_UserInfo) it.next ( ) ;

    				sM.m_lUserID = info.getId();
    				sM.m_lCurrencyID = info.getNCurrencyId();
    				sM.m_lOfficeID = info.getNOfficeID();
    				sM.m_lClientID = info.getNClientId();
    				sM.m_strUserName = info.getSName();
    				
    			}
        		

        	}
        	else
        	{
        		sM.m_strLogin = strLogin;
        	}
        	  //写登录日志
            LoginLogBean loginLogBean = new LoginLogBean();
            loginLogBean.addLoginLog(sM,Constant.EBANK_USER,clientIp,Constant.FAIL,e.getMessage());
            
        	throw e;
        }
    }

    public boolean isLogin()
    {
        if (this.m_lUserID > 0){
            return true;
        } 
        else {
            return false;
        }
    }

    public void logout()
    {
        m_lOfficeID = -1;
        m_strLogin = "";
        m_strOfficeName = "";
        m_strOfficeNo="";
        m_lClientID = -1;
        m_strClientCode = "";
        m_strClientName = "";
        m_strClientShortName = "";
        m_lCurrencyID = -1;
        m_bIsStockCompany = false;
        this.m_lUserID = -1;
        //m_vPrivilegeNo = null;
        //m_userPrivilege = null;
        //m_vGroupID = null;
        m_strMenu = "";

    }

    public void setCurrencyID(long lCurrencyID)
    {
        m_lCurrencyID = lCurrencyID;
    }

    /**
     * 此处插入方法说明。 创建日期：(2002-6-14 10:22:25)
     * 
     * @return boolean
     * @param strLogin
     *            java.lang.String
     * @param strPassword
     *            java.lang.String
     */
    /*
     * public boolean login(String strLogin, String strPassword, long
     * lCurrencyID) throws Exception { Connection conn = null; PreparedStatement
     * ps = null; ResultSet rs = null; boolean b = false; try { Log.print("came
     * in SessionOB.Login()"); Ldap ldap = new Ldap(); Log.print("new a ldap
     * object..."); if (ldap.authenticate(strLogin, strPassword)) {
     * Log.print("authenticate success....."); PeopleInfo pi =
     * ldap.getUserByDN(strLogin); cPrivilegeOfUser =
     * ldap.getPrivilegeByUser(pi.lUid); StringBuffer sb = new StringBuffer();
     * sb.append(" select a.id as id ,a.sName as sUserName,a.ncurrencyID as
     * lCurrencyID ,a.nsaid, "); sb.append(" b.id as lClientID,b.nOfficeID as
     * lOfficeID,b.sName as sClientName,"); sb.append(" b.sCode as
     * sClientCode,b.sSimpleName, c.sName as sOfficeName"); sb.append(" from
     * ob_user a,client b,office c "); sb.append(" where a.nclientid=b.id(+) and
     * b.nofficeID=c.id(+) and a.sloginno=? and a.spassword=? "); sb.append("
     * and a.nStatus = " + Constant.RecordStatus.VALID);
     * 
     * Log.print(sb.toString()); conn = Database.getConnection(); ps =
     * conn.prepareStatement(sb.toString()); ps.setString(1, strLogin);
     * ps.setString(2, strPassword); rs = ps.executeQuery(); if (rs.next()) { if
     * (pi.lCurrencyID == lCurrencyID || pi.lCurrencyID == 0) { m_lUserID =
     * rs.getLong("id"); m_lClientID = rs.getLong("lClientID"); m_lCurrencyID =
     * lCurrencyID; switch ((int) lCurrencyID) { case (int)
     * Constant.CurrencyType.RMB : m_strCurrencySymbol = "￥"; break; case (int)
     * Constant.CurrencyType.USD : m_strCurrencySymbol = "＄"; break; case (int)
     * Constant.CurrencyType.ED : m_strCurrencySymbol = "E"; break; case (int)
     * Constant.CurrencyType.UKP : m_strCurrencySymbol = "￡"; break; case (int)
     * Constant.CurrencyType.JP : m_strCurrencySymbol = "￥"; break; case (int)
     * Constant.CurrencyType.SP : m_strCurrencySymbol = "＄"; break; default :
     * break; } m_strLogin = strLogin; m_strClientName =
     * rs.getString("sClientName"); m_strClientShortName =
     * rs.getString("sSimpleName"); if (m_strClientShortName == null) {
     * m_strClientShortName = ""; } m_lOfficeID = rs.getLong("lOfficeID");
     * m_strClientCode = rs.getString("sClientCode"); m_strOfficeName =
     * rs.getString("sOfficeName"); m_strUserName = rs.getString("sUserName");
     * m_lSAID = rs.getLong("nsaid"); b = true; random=new Random(); } else { b =
     * false; } } Log.print("lClientID in login(): " + m_lClientID); rs.close();
     * rs = null; ps.close(); ps = null; conn.close(); conn = null; UserAdmin ua =
     * new UserAdmin(); if (ua.isSA(m_lUserID)) { m_lSAID = m_lUserID; }
     * Log.print("after ua.isSA() "); } else { b = false; } } catch (Exception
     * e) { e.printStackTrace(); throw new Exception(e.getMessage()); } finally {
     * try { if (rs != null) { rs.close(); } if (ps != null) { ps.close(); } if
     * (conn != null) { conn.close(); } } catch (Exception ex) { throw new
     * Exception(ex.getMessage()); } } return b; }
     */
    /*
     * public boolean hasRight(HttpServletRequest request) { boolean b = false;
     * String strURL = request.getRequestURI(); try { int nTemp =
     * strURL.indexOf(Env.EBANK_URL); String strPageURL = strURL.substring(nTemp +
     * 23, strURL.length()); Log.print(" Request jsp : " + strPageURL); String
     * strSuffix = ""; Ldap ldap = new Ldap(); Collection cAll =
     * ldap.getAllPrivileges(); boolean bMenuJsp = false; if (cAll != null) {
     * Iterator it = cAll.iterator(); while (it.hasNext()) { PrivilegeInfo pvg =
     * (PrivilegeInfo) it.next(); if (pvg.strJSP.indexOf(strPageURL) >= 0) {
     * bMenuJsp = true; break; } } } Log.print("Is Menu Jsp : " + bMenuJsp); if
     * (bMenuJsp == true) { boolean bHasMenuRight = false; Collection cUser =
     * ldap.getPrivilegeByUser(m_lUserID); if (cUser != null) { Iterator it =
     * cUser.iterator(); while (it.hasNext()) { PrivilegeInfo pvg =
     * (PrivilegeInfo) it.next(); Log.print("Pvg no : " + pvg.strJSP); if
     * (pvg.strJSP.indexOf(strPageURL) >= 0) { b = true; bHasMenuRight = true;
     * Log.print("Match right jsp: " + pvg.strJSP); break; } } } } else { b =
     * true; }
     * 
     * Enumeration en = request.getParameterNames(); while
     * (en.hasMoreElements()) { String strName = (String) en.nextElement();
     * String strValue = request.getParameter(strName); strValue =
     * DataFormat.toChinese(strValue); if (request.getAttribute(strName) ==
     * null) { request.setAttribute(strName, strValue); Log.print("======
     * request.parameter < " + strName + " : " + request.getAttribute(strName) + "
     * >"); } }
     * 
     * Log.print(" Return : " + b); } catch (Exception e) { b = false; } return
     * b; }
     */

    public boolean hasRight1(HttpServletRequest request)
    {
        boolean b = false;
        String strURL = request.getRequestURI();
        String upurl=request.getHeader("Referer");
        try
        {
            RequestAlteredValidator.validate(request);
            
            //int nTemp = strURL.indexOf(Env.EBANK_URL);
        	int nTemp = Env.EBANK_URL.length();
            String strPageURL = strURL.substring(nTemp, strURL.length());
            log4j.print(" Request jsp : " + strPageURL);
            //tring strSuffix = "";
            //Ldap ldap = new Ldap();
            //Collection cAll = ob_privilegeDao.getAllPrivilege();
            //boolean bMenuJsp = false;
	        HostInfo hostInfo=new HostInfo();
			hostInfo.setRemoteHost(request.getRemoteHost());
			hostInfo.setRemoteIP(request.getRemoteAddr());
            
            /*if (cAll != null)
            {
                Iterator it = cAll.iterator();
                while (it.hasNext())
                {
                    OB_PrivilegeInfo pvg = (OB_PrivilegeInfo) it.next();
                    String strJsp = pvg.getMenuUrl();
                    if (strJsp != null)
                    {

                        if (strJsp.indexOf(strPageURL.substring(1,strPageURL.length())) >= 0)
                        {
                            bMenuJsp = true;
                            addLogger ( pvg , strPageURL ,hostInfo) ;
                            break;
                        }
                    }
                }
            }*/
            //Log.print("Is Menu Jsp :  " + bMenuJsp);
            /*if (bMenuJsp == true)
            {

                boolean bHasMenuRight = false;

                if (this.m_userPrivilege != null)
                {
                    Iterator it = m_userPrivilege.iterator();
                    while (it.hasNext())
                    {
                        OB_PrivilegeInfo pvg = (OB_PrivilegeInfo) it.next();
                        Log.print("Pvg no : " + pvg.getMenuUrl());
                        String strJsp = pvg.getMenuUrl();
                        if (strJsp != null)
                        {

                            if (strJsp.indexOf(strPageURL) >= 0)
                            {
                                b = true;
                                bHasMenuRight = true;
                                Log.print("Match right jsp: "
                                        + pvg.getMenuUrl());
                                break;
                            }
                        }
                    }
                }
            } else
            {
                b = true;
            }*/
			boolean isMenuJsp=false;
			
			if(m_allPrivilege!=null)
			{
				Iterator it =m_allPrivilege.iterator();
				OB_PrivilegeInfo privilegeInfo=null;
				while (it.hasNext())
				{
					privilegeInfo=(OB_PrivilegeInfo)it.next();
					if(strPageURL.equalsIgnoreCase(privilegeInfo.getMenuUrl()))
					{
						isMenuJsp=true;
						break;
					}
					
				}
				
			}
			if(isMenuJsp==false)
			{
				b=true;
			}
			if(isMenuJsp==true&&m_userPrivilege!=null)
			{
				Iterator it=m_userPrivilege.iterator();
				OB_PrivilegeInfo privilegeInfo=null;
				while(it.hasNext())
				{
					privilegeInfo=(OB_PrivilegeInfo)it.next();
					if(strPageURL.equalsIgnoreCase(privilegeInfo.getMenuUrl()))
					{
						b=true;
						//原有日志增加位置
						if(upurl.indexOf("NASApp/iTreasury-ebank/ebankMain.jsp")>=0||upurl==null)
						{
							this.addLogger(privilegeInfo , strPageURL ,hostInfo) ;//增加操作信息
						}
						break;
					}
					
				}
				
			}
			if(m_strMenu.equals("")){
				b = false;
			}
			else {
	            
	            Enumeration en = request.getParameterNames();
	            while (en.hasMoreElements())
	            {
	                /*String strName = (String) en.nextElement();
	                String strValue = request.getParameter(strName);
	                strValue = DataFormat.toChinese(strValue);
	                if (request.getAttribute(strName) == null)
	                {
	                    request.setAttribute(strName, strValue);
	                    log4j.print("====== request.parameter < " + strName + "   : " + request.getAttribute(strName) + " >");
	                }*/
	            	String strName = (String)en.nextElement();
	            	String strValue = request.getParameter(strName);
	                if(request.getAttribute(strName) == null)
	                {
	                    request.setAttribute(strName, strValue);
	                    log4j.print("====== request.parameter < " + strName + "   : " + strValue + " >");
	                }
	            }
			}

			log4j.print(" Return :  " + b);
        } 
        catch (Exception e){
            b = false;
            e.printStackTrace();
        }
        return b;
    }
    public boolean hasRight(HttpServletRequest request)
    {

    	String strURL = request.getRequestURI();
        //String upurl=request.getHeader("Referer");
        
    	int nTemp = Env.EBANK_URL.length();
        String strPageURL = strURL.substring(nTemp, strURL.length());
        log4j.print(" Request jsp : " + strPageURL);
        HostInfo hostInfo=new HostInfo();
		hostInfo.setRemoteHost(request.getRemoteHost());
		hostInfo.setRemoteIP(request.getRemoteAddr());
    	
    	Enumeration en = request.getParameterNames();
        while (en.hasMoreElements())
        {
        	String strName = (String)en.nextElement();
        	String strValue = request.getParameter(strName);
            if(request.getAttribute(strName) == null)
            {
                request.setAttribute(strName, strValue);
                log4j.print("====== request.parameter < " + strName + "   : " + strValue + " >");
            }
        }
        return true;
    }

    /**
     * 显示用户权限
     * <p>
     * <ol>
     * <b>显示用户权限 </b>
     * <ul>
     * <li>操作数据库userinfo
     * <li>返回String
     * </ul>
     * </ol>
     * 
     * @Copyright (c) Jan. 2002, by iSoftStone Inc. All Rights Reserved
     * @author gtjin
     * @date 2003.4.17
     * @param lUserID
     *            long
     */
    public String getUserPriv(long lUserID)
    {
        StringBuffer sb = new StringBuffer();
        String strResult = "";
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try
        {
            con = Database.getConnection();
            // get the whole content
            sb.append(" SELECT sPrivLevel FROM userinfo Where ID = ?");
            ps = con.prepareStatement(sb.toString());
            ps.setLong(1, lUserID);
            rs = ps.executeQuery();
            if (rs.next())
            {
                strResult = rs.getString(1);
            } else
            {
                strResult = "";
            }
            if (rs != null)
            {
                rs.close();
                rs = null;
            }
            if (ps != null)
            {
                ps.close();
                ps = null;
            }
            sb.setLength(0);
        } catch (Exception e)
        {
            strResult = "";
        } finally
        {
            try
            {
                if (rs != null)
                {
                    rs.close();
                    rs = null;
                }
                if (ps != null)
                {
                    ps.close();
                    ps = null;
                }
                if (con != null)
                {
                    con.close();
                    con = null;
                }
            } catch (Exception e)
            {
                strResult = "";
            }
        }
        return strResult;
    }

    /*
     * public void prepareMenu() { try { String strPrivNo = ""; String
     * strPrivName = ""; String strPrivLevel = ""; String strPrivUrl = "";
     * Vector vPi = new Vector(); PrivilegeInfo pi = new PrivilegeInfo(); Ldap
     * ldap = new Ldap(); vPi = (Vector) cPrivilegeOfUser; if (vPi == null) {
     * m_strMenu = ""; System.out.println("m_strMenu:------- is null" +
     * m_strMenu); } else { Enumeration ePi = vPi.elements(); long lLevelTmp =
     * 0; String strNoTmp = ""; String strNameTmp = ""; String strUrlTmp = "";
     * while (ePi.hasMoreElements()) { pi = (PrivilegeInfo) ePi.nextElement();
     * lLevelTmp = pi.lLevel; strNoTmp = pi.strNo; strNameTmp = pi.strName;
     * strUrlTmp = pi.strJSP; strPrivNo = strPrivNo + strNoTmp + ";;";
     * strPrivName = strPrivName + strNameTmp + ";;"; strPrivLevel =
     * strPrivLevel + String.valueOf(lLevelTmp) + ";;"; strPrivUrl = strPrivUrl +
     * strUrlTmp + ";;"; } strPrivName = strPrivName.substring(0,
     * strPrivName.length() - 2); strPrivNo = strPrivNo.substring(0,
     * strPrivNo.length() - 2); strPrivLevel = strPrivLevel.substring(0,
     * strPrivLevel.length() - 2); strPrivUrl = strPrivUrl.substring(0,
     * strPrivUrl.length() - 2); m_strMenu = strPrivName + "::" + strPrivNo +
     * "::" + strPrivLevel + "::" + strPrivUrl;
     * System.out.println("m_strMenu:-------" + m_strMenu); } } catch (Exception
     * e) { System.out.print(e.toString()); } }
     */

    /*public void prepareMenu()
    {
        try
        {
            String strPrivNo = "";
            String strPrivName = "";
            String strPrivLevel = "";
            String strPrivUrl = "";
            Vector vPi = new Vector();
            OB_PrivilegeInfo pi = new OB_PrivilegeInfo();
            UserBean userbean = new UserBean();

            vPi = (Vector) m_userPrivilege;
            if (vPi == null)
            {
                System.out.println("权限为空我晕死");
                m_strMenu = "";
            }
            //vPi = (Vector) cPrivilegeOfUser;

            else
            {
                Enumeration ePi = vPi.elements();
                long lLevelTmp = 0L;
                String strNoTmp = "";
                String strNameTmp = "";
                String strUrlTmp = "";
                while (ePi.hasMoreElements())
                {
                    pi = (OB_PrivilegeInfo) ePi.nextElement();
                    lLevelTmp = pi.getPlevel();
                    strNoTmp = pi.getPrivilegeNo();
                    strNameTmp = pi.getName();
                    //strUrlTmp = (pi.getMenuUrl()!=null)?pi.getMenuUrl():"" ;
                    //处理定级菜单问题
                    strUrlTmp = (pi.getMenuUrl() == null) ? "" : pi
                            .getMenuUrl();
                    strPrivNo = strPrivNo + strNoTmp + ";;";
                    strPrivName = strPrivName + strNameTmp + ";;";
                    strPrivLevel = strPrivLevel + String.valueOf(lLevelTmp)
                            + ";;";
                    strPrivUrl = strPrivUrl + strUrlTmp + ";;";
                }
                strPrivName = strPrivName
                        .substring(0, strPrivName.length() - 2);
                strPrivNo = strPrivNo.substring(0, strPrivNo.length() - 2);
                strPrivLevel = strPrivLevel.substring(0,
                        strPrivLevel.length() - 2);
                strPrivUrl = strPrivUrl.substring(0, strPrivUrl.length() - 2);
                m_strMenu = strPrivName + "::" + strPrivNo + "::"
                        + strPrivLevel + "::" + strPrivUrl;
                System.out.println("m_strMenu:-------" + m_strMenu);
            }
        } catch (Exception e)
        {
            System.out.print(e.toString());
        }
    }*/
    
    /**
     * 一汽网银准备菜单（只有一级菜单）
     *add 2007-04-13
     */
    /*public void prepareMenuForYQ()
    {
        try
        {
            String strPrivNo = "";
            String strPrivName = "";
            String strPrivLevel = "";
            String strPrivUrl = "";
            Vector vPi = new Vector();
            OB_PrivilegeInfo pi = new OB_PrivilegeInfo();
            //UserBean userbean = new UserBean();

            vPi = (Vector) m_userPrivilege;
            if (vPi == null)
            {
                System.out.println("权限为空我晕死");
                m_strMenu = "";
            }
            else
            {
                Enumeration ePi = vPi.elements();
                long lLevelTmp = 0L;
                String strNoTmp = "";
                String strNameTmp = "";
                String strUrlTmp = "";
                while (ePi.hasMoreElements())
                {
                    pi = (OB_PrivilegeInfo) ePi.nextElement();
                    lLevelTmp = pi.getPlevel();
                    strNoTmp = pi.getPrivilegeNo();
                    strNameTmp = pi.getName();
                    //strUrlTmp = (pi.getMenuUrl()!=null)?pi.getMenuUrl():"" ;
                    //处理一级菜单问题

	                strUrlTmp = (pi.getMenuUrl() == null) ? "" : pi.getMenuUrl();
	                strPrivNo = strPrivNo + strNoTmp + ";;";
	                strPrivName = strPrivName + strNameTmp + ";;";
	                strPrivLevel = strPrivLevel + String.valueOf(lLevelTmp)+ ";;";
	                strPrivUrl = strPrivUrl + strUrlTmp + ";;";
                }
                strPrivName = strPrivName.substring(0, strPrivName.length() - 2);
                strPrivNo = strPrivNo.substring(0, strPrivNo.length() - 2);
                strPrivLevel = strPrivLevel.substring(0,strPrivLevel.length() - 2);
                strPrivUrl = strPrivUrl.substring(0, strPrivUrl.length() - 2);
                m_strMenu = strPrivName + "::" + strPrivNo + "::" + strPrivLevel + "::" + strPrivUrl;
                System.out.println("m_strMenu:-------" + m_strMenu);
            }
        } catch (Exception e)
        {
            System.out.print(e.toString());
        }
    }*/
    
    /**
	 * 清除所有PageLoader的对象，包括和PageLoader相关的条件对象，统计结果对象
	 */
	public void clearPageLoader ( )
	{
		if (this.hmPageLoader != null)
		{
			this.hmPageLoader.clear ( ) ;
		}
		if (this.hmQueryCondition != null)
		{
			this.hmQueryCondition.clear ( ) ;
		}
		if (this.sumResult != null)
		{
			this.sumResult.clear();
		}
	}

    /**
     * @return Returns the pageLoader.
     */
    public PageLoader getPageLoader(String key)
    {
        PageLoader result = null;
        if (key != null && key.trim().length() > 0 && this.hmPageLoader != null)
        {
            try
            {
                result = (PageLoader) this.hmPageLoader.get(key);
            } catch (ClassCastException e)
            {
                System.out.println("Session中PageLoader属性存有非PageLoader对象（key:"
                        + key + "）");
                this.hmPageLoader.remove(key);
            }
        }

        return result;
    }

    /**
     * 保存PageLoader对象同时返回key值
     * 注：key值使用传人对象的hashCode，如果Session中已经有同样key的PageLoader对象，则不保存同时返回null
     * 
     * @param pageLoader
     * @return String 返回的key
     */
    public String setPageLoader(PageLoader pageLoader)
    {
        String key = null;
        if (pageLoader != null)
        {
            if (this.hmPageLoader == null)
            {
                this.hmPageLoader = new HashMap(6);
            }

            Random random1 = new Random();
            Random random2 = new Random(random1.nextLong());

            key = String.valueOf(random2.nextLong());

            if (!this.hmPageLoader.containsKey(key))
            {
                this.hmPageLoader.put(key, pageLoader);
            } else
            {
                key = null;
            }

        }

        return key;
    }

    /**
     * @return
     */
    public Object getQueryCondition(String key)
    {
        Object result = null;
        if (key != null && key.trim().length() > 0
                && this.hmQueryCondition != null)
        {
            result = this.hmQueryCondition.get(key);
        }

        return result;
    }

    /**
     * 在集合中保存查询条件对象
     * 
     * @param key
     *            key值，用于索引对象，不能为“”和null
     * @param queryCondition
     */
    public void setQueryCondition(String key, Object queryCondition)
    {
        if (key != null && key.trim().length() > 0 && queryCondition != null)
        {
            if (this.hmQueryCondition == null)
            {
                this.hmQueryCondition = new HashMap(6);
            }

            this.hmQueryCondition.put(key, queryCondition);
        }
    }
    /**
	 * 在集合中获取统计结果对象
	 * @return
	 */
	public Object getSumResult(String key)
	{
		Object result = null;
		if (key != null && key.trim().length() > 0 && this.sumResult != null)
		{
			result = this.sumResult.get(key);
		}
		return result;
	}
	/**
	 * 在集合中保存统计结果对象
	 * @param key 用于索引对象，不能为“”和null
	 * @param sumResult
	 */
	public void setSumResult(String key , Object sumResult)
	{
		if (key != null && key.trim().length() > 0 && sumResult != null)
		{
			if (this.sumResult == null)
			{
				this.sumResult = new HashMap(6);
			}
			this.sumResult.put(key , sumResult);
		}
	}
    public long nextInterval()
    {
        if (random == null)
            random = new Random();
        return random.nextLong();
    }

    public boolean checkInterval(long l)
    {
        if (l == currentKey)
        {
            return true;
        } else
        {
            currentKey = l;
            return false;
        }
    }

    public static void main(String[] args)
    {
        HttpServletRequest request = null;
        try
        {
            SessionOB s = new SessionOB();
            s.init("01-0002", "440495","127.0.0.1");
            s.hasRight(request);
            //Log.print (s.login("shiny","111111") ) ;
        } catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    /**
     * @return Returns the digitalSign.
     */
    public String getDigitalSign()
    {
        return DigitalSign;
    }

    /**
     * @param digitalSign
     *            The digitalSign to set.
     */
    public void setDigitalSign(String digitalSign)
    {
        DigitalSign = digitalSign;
    }

    /**
     * @return Returns the currentKey.
     */
    public long getCurrentKey()
    {
        return currentKey;
    }

    /**
     * @param currentKey
     *            The currentKey to set.
     */
    public void setCurrentKey(long currentKey)
    {
        this.currentKey = currentKey;
    }
    
    /**
	 * Returns the actionMessages.
	 * 
	 * @return ActionMessages
	 */
	public ActionMessages getActionMessages ( )
	{
		return m_actionMessages ;
	}
	
	private void addLogger ( OB_PrivilegeInfo pvg , String strURL ,HostInfo hostInfo) throws Exception
	{
		LoggerInfo li = new LoggerInfo() ;
			//
		li.setCurrencyID(m_lCurrencyID ) ;
		li.setOfficeID(m_lOfficeID ) ;
		li.setModuleID(m_lModuleID ) ;
		li.setUserID(m_lUserID ) ;
		li.setUserName(m_strUserName);
		li.setFunctionPointCode(pvg.getPrivilegeNo()) ;
		//修改日志时间取数方式：为数据库时间20090531 minzhao
		li.setAccessTime (Env.getSystemDateTime()) ;
		//
		log4j.print ( "pvg.jsp - " + pvg.getMenuUrl() + "  ==  url - " + strURL ) ;
		int fromIndex = 2 ;
		int offset = pvg.getPrivilegeNo().indexOf ( "-" , fromIndex ) ;
		int length = pvg.getPrivilegeNo().length ( ) ;
		String pvgNo = "" ;
		String funcationPointDescription = "" ;
		for (int i = 1; i <= pvg.getPlevel(); i++)
		{
			offset = pvg.getPrivilegeNo().indexOf ( "-" , fromIndex + 1 ) ;
			pvgNo = pvg.getPrivilegeNo().substring ( 0 , offset < 0 ? length : offset ) ;
			log4j.print ( "第 " + i + " 级权限编码 : " + pvgNo ) ;
			funcationPointDescription = funcationPointDescription + m_privilegeNameRef.get(pvgNo) + "-" ;
			//funcationPointDescription = funcationPointDescription + pvg.getName() + "-" ;
			//20090602 minzhao 将杨垒优化的代码还原原因菜单显示错误，如果发现问题，请提出。
			fromIndex = pvgNo.length() ;
		}
		m_NowPrivilegeName=funcationPointDescription.substring(0, funcationPointDescription.length()-1);
		//add by xfma(马现福) 2008-7-2-------------------begin
		int iSize=funcationPointDescription.length();
		if(!"".equals(funcationPointDescription)&&funcationPointDescription.lastIndexOf("-")==iSize-1){
			funcationPointDescription=funcationPointDescription.substring(0,iSize-1);
		}
		li.setFunctionPointDescription ( funcationPointDescription) ;
		//----------------------------------------------end
		li.setRemoteHost( hostInfo.getRemoteHost() );
		li.setRemoteIP( hostInfo.getRemoteIP() );

		LoggerDelegation delegation = new LoggerDelegation ( ) ;
		delegation.addLogger ( li ) ;
	}
	
	public boolean checkUserCert(String strCertCN,String strCertSN) throws Exception {
		boolean isPass = false;
		if(strCertCN == null || strCertSN == null){
			isPass = false;
		}else{
			isPass = (strCertCN.equals(this.certcn) && strCertSN.equals(this.certsn));
		}
		return isPass;
	}

}