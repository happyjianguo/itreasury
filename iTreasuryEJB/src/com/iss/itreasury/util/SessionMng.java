/*
 * SessionMng.java
 
 * Created on 2001年12月6日, 下午2:51
 */
package com.iss.itreasury.util ;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSessionBindingListener;
import javax.servlet.jsp.JspWriter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


import com.iss.itreasury.custommenu.bizlogic.CustomMenubiz;
import com.iss.itreasury.dataentity.HtmlHeaderInfo;
import com.iss.itreasury.dataentity.OfficeInfo;
import com.iss.itreasury.encrypt.EncryptManager;
import com.iss.itreasury.settlement.enddayprocess.process.EndDayProcess;
import com.iss.itreasury.settlement.util.NameRef;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.system.bizdelegation.GroupDelegation;
import com.iss.itreasury.system.bizdelegation.LoggerDelegation;
import com.iss.itreasury.system.bizdelegation.PrivilegeDelegation;
import com.iss.itreasury.system.logger.bizlogic.LoggerBean;
import com.iss.itreasury.system.logger.dataentity.HostInfo;
import com.iss.itreasury.system.logger.dataentity.LoggerBtnLevelInfo;
import com.iss.itreasury.system.logger.dataentity.LoggerInfo;
import com.iss.itreasury.system.logger.dataentity.LoggerResults;
import com.iss.itreasury.system.loginlog.bizlogic.LoginLogBean;
import com.iss.itreasury.system.privilege.dataentity.Sys_PrivilegeInfo;
import com.iss.itreasury.system.privilege.dataentity.Sys_UserAuthorityInfo;
import com.iss.itreasury.system.privilege.dataentity.Sys_UserInfo;
import com.iss.system.dao.PageLoader;
/**
 * 
 * @author yiwang
 * @version
 */
public class SessionMng extends java.lang.Object implements HttpSessionBindingListener ,Externalizable
{
	public static HashMap sessionCache = new HashMap();
	public int isValidate = 0 ;		//是否进行权限以及登录验证 0 = 验证 ，1=不验证
	public long				m_lUserID			= -1;
	public long				m_lOfficeID			= -1;
	public Vector			m_vGroupID			= null;
	public long				m_lCurrencyID		= 1;						//币种信息
	public String			m_strPrivLevel		= "";						//added
	public long 			m_lClientID 		= -1;						//从网银登录预算会记录用户所属单位，为测试默认值设为1
																				 // hally
																				 // 03/14/2002
	public String   		m_isStop 			= "false";					//币种非必选模块是否需要重新加载 jzw 2010-04-21
	public String			m_isSelectCurrency	= "false";					//币种必选模块是否需要重新加载   jzw 2010-04-21
	public long             m_oldCurrentID      = -1;						//针对证劵等需要有默认币种的模块，并且和选择的币种区分开，而添加的中间币种类型 jzw 2010-04-29
	
	public String			m_strUserName		= "";
	public String			m_strLogin			= "";
	public long				m_lModuleID			= -1;
	public String			m_strOfficeName		= "";
	private int				nSize				= 0;
	public String           m_strMainMenu       = ""; //模式菜单
	public String			m_strMenu			= ""; //Tree菜单
	public String			m_strCurrencySymbol	= "￥";
	public String			m_strOfficeNo		= "";
	public String			m_strCertIssuer		= "";
	public String			m_strSignature		= "";
	public String			m_strSessionID		= "";						//Session
																				 // 的标示
																				 // added
																				 // by
																				 // Hally
																				 // 01/14/2003
	public long				m_lCloseSystem		= 0 ;
	public long				m_lIsAdminUser		= 2 ;						//默认为否
	public long				m_lIsVirtualUser	= 2 ;						//默认为否
	public boolean			m_bIsOpenCloseMsg	= true ;					/////莫认为true;当关机时值为false;如果打开过开机提醒页面，则值为true;
	//正在进行的交易业务类型
	private long			lSessionTransType	= -1 ;
	private String			m_strMenuURL		= null ;
	//
	private static Log4j	log4j				= null ;
	//标识提交的请求
	private long			m_lActionID			= -1 ;
	private ActionMessages	m_actionMessages	= new ActionMessages ( ) ;
	// 分页控制类
	private HashMap			hmPageLoader		= null ;
	//
	private HashMap			hmQueryCondition	= null ;
	private HashMap			hmSumResult			= null ;
	//权限集合
	private Collection		m_userPrivilege		= null ;
	private static Collection m_allPrivilege    = null; 
	private Vector			m_vPrivilegeNo		= null ;
	private HashMap			m_privilegeNameRef	= new HashMap();
	private ArrayList		availableModules  	=null;						//返回用户所能操作的模块集合
	
	/** ****声音提醒控制参数，2004-04-15，Forest加入****开始* */
	private long			lRemindNo			= 0 ;						//第几次提醒
	private long			lRemindCount		= 3 ;						//每次需要提醒次数
	private long			lLastOBCount		= 0 ;						//网上银行业务的数量
	public boolean			bIsNeedSoundRemind	= false ;					//是否需要声音提醒
	//2005-01-25 kewen hu(声音提醒控制参数)
	private long			lRemindIntervalTime = 120000;					//网上银行业务提醒的时间间隔(在需要提醒的前提下)
	private long			lLastSystemTime		= 0;						//上一次刷新时的系统时间
	//
	private long			m_lDeportmentID		= -1 ;
	
	public Timestamp      dLastLoginTime = null;					//上次登录时间
	public String sLastLoginTime = null;							//上次登录时间，中文年月日时分秒格式
	public Timestamp settlemetSystemDate= null;								//结算开机时间
	public String sChineseSystemDate = null;						//结算开机时间，中文年月日格式
	//Modify by leiyang date 2007/07/03
	public String m_logCode = "";           //日志批号 
	
	public boolean isCustomModule =false;  //自定义模块标识 add by zcwang 2008-1-10
	
	public String m_strCertSerialNumber = ""; //added by mzh_fu 2007/09/03 用户证书序列号
	
	public String m_strCertCN=""; //added by mzh_fu 2008/01/30 证书CN
	
	public StringBuffer m_CurrentAllURL = null; //保存当前URL add by leiyang 2008-10-25
	
	//去掉分办事处的机制、机核用户，机制、机核用户没有必要分办事处，直接从Constant.MachineUser里取 modify by bingliu 20110730
//	public long AUTOINPUTUSERID = -100;
//	public long AUTOCHECKUSERID = -101;

	/**
	 * 声音提醒前处理 long lOBCount 网上业务总数
	 */
	public void BeforeSound ( long lOBCount )
	{
		//如果新增了网上业务（或登录后有网上业务），则进行声音提醒
		if (lOBCount > lLastOBCount)
		{
			this.bIsNeedSoundRemind = true ;
			this.lLastOBCount = lOBCount ;
		}
		//如果到了设定的提醒次数，就置为为false。
		if (this.bIsNeedSoundRemind == true)
		{
			this.lRemindNo += 1 ;
			if (this.lRemindNo % (this.lRemindCount + 1) == 0)
			{
				this.bIsNeedSoundRemind = false ;
				this.lRemindNo = 0 ;
			}
		}
	}
	/**
	 * 声音提醒前处理
	 * @author zqhu
	 * @param nothing
	 * @see 为上海电气专做的方法，前提是结算中至少有1笔的网银指令
	 */
	public void BeforeSound () {
		//如果当前时间与前一次提醒的时间间距达到120秒时就提醒
		long sysdate=Env.getSystemDateTime().getTime();
		System.out.println("时间间距达到="+(sysdate-lLastSystemTime));
		if ((sysdate-lLastSystemTime) >= lRemindIntervalTime) {
			this.bIsNeedSoundRemind = true ;
			this.lLastSystemTime = sysdate;
		}
		//如果到了设定的提醒次数，就置为为false。
		if (this.bIsNeedSoundRemind == true) {
			this.lRemindNo += 1 ;
			if (this.lRemindNo % (this.lRemindCount + 1) == 0) {
				this.bIsNeedSoundRemind = false ;
				this.lRemindNo = 0 ;
			}
		}
	}
	/** ****声音提醒控制参数，2004-04-15，Forest加入****结束* */
	/** Creates new SessionMng */
	public SessionMng ( )
	{
		log4j = new Log4j ( Constant.ModuleType.SETTLEMENT , this ) ;
	}
	public void valueUnbound ( javax.servlet.http.HttpSessionBindingEvent httpSessionBindingEvent )
	{
		logout ( ) ;
	}
	public void valueBound ( javax.servlet.http.HttpSessionBindingEvent httpSessionBindingEvent )
	{
	}
	public boolean isLogin ( )
	{
		if (m_lUserID > 0  || m_lUserID==Constant.MachineUser.InputUser || m_lUserID==Constant.MachineUser.CheckUser)
			return true ;
		else
			return false ;
	}
	public void logout ( )
	{
		m_lUserID = -1 ;
		m_lOfficeID = -1 ;
		m_lCurrencyID = 1 ;
		m_strPrivLevel = "" ; //added by hally 03/14/2002
		m_strUserName = "" ;
		m_strLogin = "" ;
		m_lModuleID = -1 ;
		m_strOfficeName = "" ;
		nSize = 0 ;
		m_strMenu = "" ;
		m_strCurrencySymbol = "￥" ;
		m_strOfficeNo = "" ;
		m_strCertIssuer = "" ;
		m_strSignature = "" ;
		//
		m_vGroupID = null ;
		m_userPrivilege = null ;
		m_privilegeNameRef = null ;
		m_vPrivilegeNo = null ;
	}
	/**
	 * 结算主线登录
	 * @param login
	 * @param password
	 * @param clientIp
	 * @param isSelectOffice
	 * @param lOfficeID
	 * @throws Exception
	 */
	public void login(String login, String password,String clientIp,boolean isSelectOffice,long lOfficeID) throws Exception
	{
		Sys_UserInfo info = new Sys_UserInfo() ;
		PrivilegeDelegation delegation = new PrivilegeDelegation ( ) ;
		Sys_UserAuthorityInfo authorityInfo = new Sys_UserAuthorityInfo();
		//
		
		Collection c = null;
		try
		{
		//加密校验
		if(!isSelectOffice)
		{
			if(Config.getBoolean(ConfigConstant.SETT_CAN_ENCRYPT,false))
			{
				if (EncryptManager.getBeanFactory().checkPassword(login,password))
				{
					info.setLoginNo(login);
					//info.setStatusID(Constant.RecordStatus.VALID);
					c = delegation.findUserByCondition(info);
				}
			}
			else
			{
				info.setLoginNo(login);
				info.setPassword (password) ;
				//info.setStatusID(Constant.RecordStatus.VALID);
				c =delegation.findUserByCondition(info);
				
			}
		}else{
			info.setLoginNo(login);
			info.setPassword (password) ;
			//info.setStatusID(Constant.RecordStatus.VALID);
			c =delegation.findUserByCondition(info);
		}
		if (c == null || c.size() == 0 )
		{
			throw new IException ( "登录失败,请检查用户名和密码" ) ;
		} 
		else
		{
			Iterator it = c.iterator ( ) ;
			while (it.hasNext ( ))
			{
				info = (Sys_UserInfo) it.next ( ) ;
				//add by zcwang 2008-1-28
				if(info.getStatusID()!=Constant.RecordStatus.VALID)
				{
					if(info.getStatusID()==Constant.RecordStatus.STASIS)
					{
						throw new IException ( "此用户未复核,请复核后登录!" ) ;
					}
				}
				//
				m_lUserID = info.getId() ;
				LoginLogBean loginBean = new LoginLogBean();	
				dLastLoginTime = loginBean.getLastLoginTime(info.getId ( ),Constant.SETT_USER);
				sLastLoginTime = DataFormat.getChineseTimeString(dLastLoginTime);
				if(isSelectOffice)
				{
					m_lOfficeID = lOfficeID;
				}
				else
				{
					authorityInfo = delegation.findAuthorizeByUserCondition(info);
					m_lOfficeID = authorityInfo.getAuthorizedOfficeId();
				}

				m_lIsAdminUser = info.getIsAdminUser ( ) ;
				m_lIsVirtualUser = info.getIsVirtualUser ( ) ;
				m_lDeportmentID = info.getDepartmentID ( ) ;
				m_strUserName = info.getName ( ) ;
				m_strLogin = info.getLoginNo ( ) ;
				
				//added by mzh_fu 用户证书序列号
				m_strCertSerialNumber = info.getCertNo();
				m_strCertCN = info.getCertCn();
				
				if(null == m_strCertSerialNumber) m_strCertSerialNumber = "";
				if(null == m_strCertCN) m_strCertCN = "";
				break;
			}
			
			availableModules = (ArrayList)delegation.findModulesByUser(m_lUserID,m_lOfficeID);
			if (availableModules==null) availableModules=new ArrayList();
		}
		OfficeInfo officeInfo = findOfficeByID ( m_lOfficeID ) ;
		if( officeInfo != null )
		{
			m_strOfficeName = officeInfo.m_strName ;
			m_strOfficeNo = officeInfo.m_strCode ;
		}
		}
		catch( IException e)
		{
			LoginLogBean loginBean=new LoginLogBean();
			SessionMng sM= new SessionMng();
			Sys_UserInfo userinfo = new Sys_UserInfo() ;
			userinfo.setLoginNo(info.getLoginNo());
			c =delegation.findUserByCondition(userinfo);
			
			if(c!=null)
			{
			Iterator it = c.iterator ( ) ;
		
			while (it.hasNext())
			{
				info = (Sys_UserInfo) it.next ( ) ;

				sM.m_lUserID = info.getId ( ) ;
				sM.m_lOfficeID = info.getOfficeID ( ) ;
				sM.m_lIsAdminUser = info.getIsAdminUser ( ) ;
				sM.m_lIsVirtualUser = info.getIsVirtualUser ( ) ;
				sM.m_lDeportmentID = info.getDepartmentID ( ) ;
				sM.m_strUserName = info.getName ( ) ;
				sM.m_strLogin = info.getLoginNo ( ) ;
				
			}
			}
			else
			{
				sM.m_lUserID = info.getId ( ) ;
				sM.m_lOfficeID = info.getOfficeID ( ) ;
				sM.m_lIsAdminUser = info.getIsAdminUser ( ) ;
				sM.m_lIsVirtualUser = info.getIsVirtualUser ( ) ;
				sM.m_lDeportmentID = info.getDepartmentID ( ) ;
				sM.m_strUserName = info.getName ( ) ;
				sM.m_strLogin = info.getLoginNo ( ) ;
				
			}
		
			loginBean.addLoginLog(sM,Constant.SETT_USER,clientIp,Constant.FAIL,e.getMessage());
			throw e ;
		}
	}
	/**
	 * 针对于SDC集成过滤器调用
	 * @param login
	 * @param password
	 * @param clientIp
	 * @param integration
	 * @throws Exception
	 */
	public void login(String login,long lOfficeId, String password,String clientIp,String integration) throws Exception
	{
		
		Sys_UserInfo info = new Sys_UserInfo() ;
		PrivilegeDelegation delegation = new PrivilegeDelegation () ;

		Collection c = null;
		try
		{
			info.setLoginNo(login);
			//如果配置了启用ldap查询，则登陆时就不需要验证业务登陆密码，否则，就需要验证密码是否和数据库中一致才可以登陆
			if (!Config.getBoolean(ConfigConstant.GLOBAL_PRIVILEGE_LDAP,false)){
				info.setPassword (password) ;
			}
			c =delegation.findUserByCondition(info);
		
		if (c == null || c.size() == 0 )
		{
			throw new IException ( "登录失败,请检查用户名和密码" ) ;
		} 
		else
		{
			Iterator it = c.iterator ( ) ;
			while (it.hasNext ( ))
			{
				info = (Sys_UserInfo) it.next ( ) ;
				//add by zcwang 2008-1-28
				if(info.getStatusID()!=Constant.RecordStatus.VALID)
				{
					if(info.getStatusID()==Constant.RecordStatus.STASIS)
					{
						throw new IException ( "此用户未复核,请复核后登录!" ) ;
					}
				}
				//
				m_lUserID = info.getId ( ) ;
				
				LoginLogBean loginBean = new LoginLogBean();	
				dLastLoginTime = loginBean.getLastLoginTime(info.getId ( ),Constant.SETT_USER);
				sLastLoginTime = DataFormat.getChineseTimeString(dLastLoginTime);
				
				
				//m_lOfficeID = info.getOfficeID ( ) ;
				m_lIsAdminUser = info.getIsAdminUser ( ) ;
				m_lIsVirtualUser = info.getIsVirtualUser ( ) ;
				m_lDeportmentID = info.getDepartmentID ( ) ;
				m_strUserName = info.getName ( ) ;
				m_strLogin = info.getLoginNo ( ) ;
				
				//added by mzh_fu 用户证书序列号
				m_strCertSerialNumber = info.getCertNo();
				m_strCertCN = info.getCertCn();
				
				if(null == m_strCertSerialNumber) m_strCertSerialNumber = "";
				if(null == m_strCertCN) m_strCertCN = "";
				break;
			}
			
			//availableModules = (ArrayList)delegation.findModulesByUser(m_lUserID);
			//if (availableModules==null) availableModules=new ArrayList();
		}
		m_lOfficeID = lOfficeId;
		OfficeInfo officeInfo = findOfficeByID ( m_lOfficeID ) ;
		if( officeInfo != null )
		{
			m_strOfficeName = officeInfo.m_strName ;
			m_strOfficeNo = officeInfo.m_strCode ;
		}
		}
		catch( IException e)
		{
			LoginLogBean loginBean=new LoginLogBean();
			SessionMng sM= new SessionMng();
			Sys_UserInfo userinfo = new Sys_UserInfo() ;
			userinfo.setLoginNo(info.getLoginNo());
			c =delegation.findUserByCondition(userinfo);
			
			if(c!=null)
			{
			Iterator it = c.iterator ( ) ;
		
			while (it.hasNext())
			{
				info = (Sys_UserInfo) it.next ( ) ;

				sM.m_lUserID = info.getId ( ) ;
				sM.m_lOfficeID = lOfficeId ;
				sM.m_lIsAdminUser = info.getIsAdminUser ( ) ;
				sM.m_lIsVirtualUser = info.getIsVirtualUser ( ) ;
				sM.m_lDeportmentID = info.getDepartmentID ( ) ;
				sM.m_strUserName = info.getName ( ) ;
				sM.m_strLogin = info.getLoginNo ( ) ;
				
			}
			}
			else
			{
				sM.m_lUserID = info.getId ( ) ;
				sM.m_lOfficeID = lOfficeId ;
				sM.m_lIsAdminUser = info.getIsAdminUser ( ) ;
				sM.m_lIsVirtualUser = info.getIsVirtualUser ( ) ;
				sM.m_lDeportmentID = info.getDepartmentID ( ) ;
				sM.m_strUserName = info.getName ( ) ;
				sM.m_strLogin = info.getLoginNo ( ) ;
				
			}
		
			loginBean.addLoginLog(sM,Constant.SETT_USER,clientIp,Constant.FAIL,e.getMessage());
			throw e ;
		}
	}
	/**
	 * 拆分模块登录时使用
	 * @param login
	 * @param password
	 * @param clientIp
	 * @throws Exception
	 */
	public void login(String login, String password,String clientIp) throws Exception
	{
		
		Sys_UserInfo info = new Sys_UserInfo() ;
		PrivilegeDelegation delegation = new PrivilegeDelegation ( ) ;
		//
		
		Collection c = null;
		try
		{
		//加密校验
		if(Config.getBoolean(ConfigConstant.SETT_CAN_ENCRYPT,false))
		{
			if (EncryptManager.getBeanFactory().checkPassword(login,password))
			{
				info.setLoginNo(login);
				//info.setStatusID(Constant.RecordStatus.VALID);
				c = delegation.findUserByCondition(info);
			}
		}else{
			info.setLoginNo(login);
			info.setPassword (password) ;
			//info.setStatusID(Constant.RecordStatus.VALID);
			c =delegation.findUserByCondition(info);
		}
		if (c == null || c.size() == 0 )
		{
			throw new IException ( "登录失败,请检查用户名和密码" ) ;
		} 
		else
		{
			Iterator it = c.iterator ( ) ;
			while (it.hasNext ( ))
			{
				info = (Sys_UserInfo) it.next ( ) ;
				//add by zcwang 2008-1-28
				if(info.getStatusID()!=Constant.RecordStatus.VALID)
				{
					if(info.getStatusID()==Constant.RecordStatus.STASIS)
					{
						throw new IException ( "此用户未复核,请复核后登录!" ) ;
					}
				}
				//
				m_lUserID = info.getId ( ) ;
				
				LoginLogBean loginBean = new LoginLogBean();	
				dLastLoginTime = loginBean.getLastLoginTime(info.getId ( ),Constant.SETT_USER);
				sLastLoginTime = DataFormat.getChineseTimeString(dLastLoginTime);
				
				
				m_lOfficeID = info.getOfficeID ( ) ;
				m_lIsAdminUser = info.getIsAdminUser ( ) ;
				m_lIsVirtualUser = info.getIsVirtualUser ( ) ;
				m_lDeportmentID = info.getDepartmentID ( ) ;
				m_strUserName = info.getName ( ) ;
				m_strLogin = info.getLoginNo ( ) ;
				
				//added by mzh_fu 用户证书序列号
				m_strCertSerialNumber = info.getCertNo();
				m_strCertCN = info.getCertCn();
				
				if(null == m_strCertSerialNumber) m_strCertSerialNumber = "";
				if(null == m_strCertCN) m_strCertCN = "";

				break;
			}
			
			availableModules = (ArrayList)delegation.findModulesByUser(m_lUserID);
			if (availableModules==null) availableModules=new ArrayList();
		}
		OfficeInfo officeInfo = findOfficeByID ( m_lOfficeID ) ;
		if( officeInfo != null )
		{
			m_strOfficeName = officeInfo.m_strName ;
			m_strOfficeNo = officeInfo.m_strCode ;
		}
		}
		catch( IException e)
		{
			LoginLogBean loginBean=new LoginLogBean();
			SessionMng sM= new SessionMng();
			Sys_UserInfo userinfo = new Sys_UserInfo() ;
			userinfo.setLoginNo(info.getLoginNo());
			c =delegation.findUserByCondition(userinfo);
			
			if(c!=null)
			{
			Iterator it = c.iterator ( ) ;
		
			while (it.hasNext())
			{
				info = (Sys_UserInfo) it.next ( ) ;

				sM.m_lUserID = info.getId ( ) ;
				sM.m_lOfficeID = info.getOfficeID ( ) ;
				sM.m_lIsAdminUser = info.getIsAdminUser ( ) ;
				sM.m_lIsVirtualUser = info.getIsVirtualUser ( ) ;
				sM.m_lDeportmentID = info.getDepartmentID ( ) ;
				sM.m_strUserName = info.getName ( ) ;
				sM.m_strLogin = info.getLoginNo ( ) ;
				
			}
			}
			else
			{
				sM.m_lUserID = info.getId ( ) ;
				sM.m_lOfficeID = info.getOfficeID ( ) ;
				sM.m_lIsAdminUser = info.getIsAdminUser ( ) ;
				sM.m_lIsVirtualUser = info.getIsVirtualUser ( ) ;
				sM.m_lDeportmentID = info.getDepartmentID ( ) ;
				sM.m_strUserName = info.getName ( ) ;
				sM.m_strLogin = info.getLoginNo ( ) ;
				
			}
		
			loginBean.addLoginLog(sM,Constant.SETT_USER,clientIp,Constant.FAIL,e.getMessage());
			throw e ;
		}
	}
	
	/**
	 * 对结算系统的一级菜单（模块）的显示顺序排序
	 * 
	 * @param colModules 待排序的一级菜单Collection
	 * @return
	 */
	private ArrayList sortModules(Collection colModules) {
		if(colModules==null) return null;
		ArrayList colResult = new ArrayList();;
		
		try{
			String[] strOrderedModules = getOrderedModules();
			
			if(strOrderedModules == null || strOrderedModules.length<=1)
				return (ArrayList) colModules;
			
			Sys_PrivilegeInfo[] orgModules = (Sys_PrivilegeInfo[])colModules.toArray(new Sys_PrivilegeInfo[0]);
			long curModuleId = -1;
			
			for(int i=0; i<strOrderedModules.length; i++){
				try{
					curModuleId = Long.parseLong(strOrderedModules[i]);
				}catch(Exception exp){
					curModuleId = -1;
				}
				if(curModuleId == -1) continue;
				for(int j=0; j<orgModules.length; j++){
					if(orgModules[j]==null) continue;
					
					if(curModuleId == orgModules[j].getModuleID())
					{
						colResult.add(orgModules[j]);
						orgModules[j] = null;
						break;
					}
				}
			}
			
			for(int i=0; i<orgModules.length; i++){
				if(orgModules[i]!=null){
					colResult.add(orgModules[i]);
				}
			}
		}catch(Exception exp){
			exp.printStackTrace();
		}
		
		return colResult;
	}
	
	
	/**
	 * 返回系统当前配置的模块排序方式
	 * 
	 * @return
	 */
	private String[] getOrderedModules() {
		String[] orderedModules = null;
		try{
			ArrayList alOrderedResult = Config.getArray(ConfigConstant.GLOBAL_MODULES_ORDER, new ArrayList());
		
			alOrderedResult.add(0, Constant.CustomModule.CUSTOMMODULE + "");
			orderedModules = (String[]) alOrderedResult.toArray(new String[0]);
		}catch(Exception exp){
			System.out.println("获取当前配置的模块排序方式时出错，" + exp.getMessage());
			exp.printStackTrace();
		}
		
		return orderedModules;
	}
	/**
	 * 登录后，选择了模块、币种后，初始化session，不再使用ldap作权限管理。
	 * @param strLogin
	 * @param lModuleID
	 * @param lCurrencyID
	 * @throws Exception
	 */
	public void init(long lModuleID,long lCurrencyID) throws Exception
	{
	    // 初始化用户信息
 		m_lCurrencyID = lCurrencyID;
		m_lModuleID = lModuleID ;
		//自定义模块标识 add by zcwang 2008-1-10
		if(lModuleID==Constant.CustomModule.CUSTOMMODULE)
		{
			isCustomModule = true;
		}
		else
		{
			isCustomModule = false;
		}
		//
		System.out.println("===========================m_lUserID="+m_lUserID);
 		PrivilegeDelegation delegation = new PrivilegeDelegation ( ) ;
 		Sys_UserInfo info = delegation.findUserInfoByID(m_lUserID);
		m_lUserID = info.getId ( ) ;
		m_lIsAdminUser = info.getIsAdminUser ( ) ;
		m_lIsVirtualUser = info.getIsVirtualUser ( ) ;
		m_lDeportmentID = info.getDepartmentID ( ) ;
		m_strUserName = info.getName ( ) ;
		m_strLogin = info.getLoginNo ( ) ;
		//added by ryping on 07-07-23
		//LoginLogBean loginBean = new LoginLogBean();
		//dLastLoginTime = loginBean.getLastLoginTime(info.getId ( ),Constant.SETT_USER);
		//
		settlemetSystemDate = Env.getSystemDate(m_lOfficeID, m_lCurrencyID); //得到结算开机日期
		sChineseSystemDate = DataFormat.getChineseDateString(Env.getSystemDate(m_lOfficeID, m_lCurrencyID));		
		
		//
		OfficeInfo officeInfo = findOfficeByID ( m_lOfficeID ) ;
		if( officeInfo != null )
		{
			m_strOfficeName = officeInfo.m_strName ;
			m_strOfficeNo = officeInfo.m_strCode ;
		}	   		
		// 初始化用户权限信息
		PrivilegeDelegation privilegeDelegation = new PrivilegeDelegation();
		GroupDelegation groupDelegation = new GroupDelegation();
		
		if ( m_allPrivilege == null )
		{
		   // 取得全部权限,或者是模块的权限

		    m_allPrivilege = groupDelegation.findPrivilegesbyModuleId(lModuleID,m_lOfficeID);
 		}
		// 获得用户的权限集合
		if(lModuleID==Constant.CustomModule.CUSTOMMODULE)
		{
			CustomMenubiz customMenubiz = new CustomMenubiz();
			m_userPrivilege = customMenubiz.getPrivilegeOfUser(m_lUserID,m_lModuleID);
		}
		else
		{
			m_userPrivilege = privilegeDelegation.getPrivilegeOfUser(m_lUserID,m_lModuleID,m_lOfficeID);
		}
	 	
	 	//m_userPrivilege = ldap.getPrivilegeOfUser ( m_lUserID , lModuleID ) ;
			m_vPrivilegeNo = new Vector ( ) ;
			if (m_userPrivilege != null)
			{
				java.util.Iterator it = m_userPrivilege.iterator ( ) ;
				while (it.hasNext ( ))
				{
					Sys_PrivilegeInfo o = (Sys_PrivilegeInfo) it.next ( ) ;
					m_vPrivilegeNo.addElement ( o.getPrivilegeNo() ) ;
					//临时增加此初始化，找到null point原因后删除
					if(m_privilegeNameRef == null)
						m_privilegeNameRef = new HashMap();
					if (m_privilegeNameRef.get ( o.getPrivilegeNo() ) == null)
						m_privilegeNameRef.put ( o.getPrivilegeNo() , o.getName() ) ;
				}
			}
			
 	 	prepareMenu();
 	 	parseMenuToHTML(lModuleID);
	}

	/**
	 * 集成平台用 机构id和币种id从页面传入
	 * @param lModuleID
	 * @param lCurrencyID
	 * @param lOfficeId
	 * @throws Exception
	 */
	public void initForSDC(long lModuleID,long lCurrencyID,long lOfficeId) throws Exception
	{
	    // 初始化用户信息
 		m_lCurrencyID = lCurrencyID;
		m_lModuleID = lModuleID ;
		m_lOfficeID = lOfficeId;
		//自定义模块标识 add by zcwang 2008-1-10
		if(lModuleID==Constant.CustomModule.CUSTOMMODULE)
		{
			isCustomModule = true;
		}
		else
		{
			isCustomModule = false;
		}
		//
		System.out.println("===========================m_lUserID="+m_lUserID);
 		PrivilegeDelegation delegation = new PrivilegeDelegation ( ) ;
 		Sys_UserInfo info = delegation.findUserInfoByID(m_lUserID);
		m_lUserID = info.getId ( ) ;
		m_lIsAdminUser = info.getIsAdminUser ( ) ;
		m_lIsVirtualUser = info.getIsVirtualUser ( ) ;
		m_lDeportmentID = info.getDepartmentID ( ) ;
		m_strUserName = info.getName ( ) ;
		m_strLogin = info.getLoginNo ( ) ;
		//added by ryping on 07-07-23
		//LoginLogBean loginBean = new LoginLogBean();
		//dLastLoginTime = loginBean.getLastLoginTime(info.getId ( ),Constant.SETT_USER);
		//
		settlemetSystemDate = Env.getSystemDate(m_lOfficeID, m_lCurrencyID); //得到结算开机日期
		sChineseSystemDate = DataFormat.getChineseDateString(Env.getSystemDate(m_lOfficeID, m_lCurrencyID));		
		
		//
		OfficeInfo officeInfo = findOfficeByID ( m_lOfficeID ) ;
		if( officeInfo != null )
		{
			m_strOfficeName = officeInfo.m_strName ;
			m_strOfficeNo = officeInfo.m_strCode ;
		}	   		
	}
	/**
	 * 登录后，选择了模块、币种后，初始化session，分离模块时使用。
	 * @param strLogin
	 * @param lModuleID
	 * @param lCurrencyID
	 * @throws Exception
	 */
	public void initApart(long lModuleID,long lCurrencyID) throws Exception
	{
	    // 初始化用户信息
 		m_lCurrencyID = lCurrencyID;
		m_lModuleID = lModuleID ;
		//自定义模块标识 add by zcwang 2008-1-10
		if(lModuleID==Constant.CustomModule.CUSTOMMODULE)
		{
			isCustomModule = true;
		}
		else
		{
			isCustomModule = false;
		}
		//
		System.out.println("===========================m_lUserID="+m_lUserID);
 		PrivilegeDelegation delegation = new PrivilegeDelegation ( ) ;
 		Sys_UserInfo info = delegation.findUserInfoByID(m_lUserID);
		m_lUserID = info.getId ( ) ;
		m_lIsAdminUser = info.getIsAdminUser ( ) ;
		m_lIsVirtualUser = info.getIsVirtualUser ( ) ;
		m_lDeportmentID = info.getDepartmentID ( ) ;
		m_strUserName = info.getName ( ) ;
		m_strLogin = info.getLoginNo ( ) ;
		//added by ryping on 07-07-23
		//LoginLogBean loginBean = new LoginLogBean();
		//dLastLoginTime = loginBean.getLastLoginTime(info.getId ( ),Constant.SETT_USER);
		//
		settlemetSystemDate = Env.getSystemDate(m_lOfficeID, m_lCurrencyID); //得到结算开机日期
		sChineseSystemDate = DataFormat.getChineseDateString(Env.getSystemDate(m_lOfficeID, m_lCurrencyID));		
		
		//
		OfficeInfo officeInfo = findOfficeByID ( m_lOfficeID ) ;
		if( officeInfo != null )
		{
			m_strOfficeName = officeInfo.m_strName ;
			m_strOfficeNo = officeInfo.m_strCode ;
		}	   		
		// 初始化用户权限信息
		PrivilegeDelegation privilegeDelegation = new PrivilegeDelegation();
		GroupDelegation groupDelegation = new GroupDelegation();
		
		if ( m_allPrivilege == null )
		{
		   // 取得全部权限,或者是模块的权限

		    m_allPrivilege = groupDelegation.findPrivilegesbyModuleId(lModuleID,m_lOfficeID);
 		}
		// 获得用户的权限集合
		if(lModuleID==Constant.CustomModule.CUSTOMMODULE)
		{
			CustomMenubiz customMenubiz = new CustomMenubiz();
			m_userPrivilege = customMenubiz.getPrivilegeOfUser(m_lUserID,m_lModuleID);
		}
		else
		{
			m_userPrivilege = privilegeDelegation.getPrivilegeOfUser(m_lUserID,m_lModuleID,m_lOfficeID);
		}
	 	
	 	//m_userPrivilege = ldap.getPrivilegeOfUser ( m_lUserID , lModuleID ) ;
			m_vPrivilegeNo = new Vector ( ) ;
			if (m_userPrivilege != null)
			{
				java.util.Iterator it = m_userPrivilege.iterator ( ) ;
				while (it.hasNext ( ))
				{
					Sys_PrivilegeInfo o = (Sys_PrivilegeInfo) it.next ( ) ;
					m_vPrivilegeNo.addElement ( o.getPrivilegeNo() ) ;
					//临时增加此初始化，找到null point原因后删除
					if(m_privilegeNameRef == null)
						m_privilegeNameRef = new HashMap();
					if (m_privilegeNameRef.get ( o.getPrivilegeNo() ) == null)
						m_privilegeNameRef.put ( o.getPrivilegeNo() , o.getName() ) ;
				}
			}
			
 	 	prepareMenuApart();
 	 
	}
	/**
	 * Add by leiyang date 2007/08/09
	 *
	 */
	/*public void initMainModel(){
		try
		{
			String strPrivName = "" ;
			String strPrivLevel = "" ;
			String strPrivUrl = "" ;
			Collection mainModelColl = null;
			Sys_PrivilegeInfo pi = new Sys_PrivilegeInfo();
			
			String strNameTmp = "";
			String strUrlTmp = "";
			long lLevelTmp = 0;

			GroupDelegation groupDelegation = new GroupDelegation();
			if(availableModules == null){
				m_strMainMenu = "";
			}
			else {
				mainModelColl =  groupDelegation.findPrivilegesByMain(availableModules,m_lUserID);
				if(mainModelColl != null){
					Iterator iMain = mainModelColl.iterator();
					while (iMain.hasNext()) {
						pi = (Sys_PrivilegeInfo)iMain.next();
						strNameTmp = pi.getName();
						strUrlTmp = pi.getMenuUrl();
						lLevelTmp = pi.getPlevel();
						
						strPrivName = strPrivName + strNameTmp + ";;" ;
						strPrivUrl = strPrivUrl + strUrlTmp + ";;" ;
						strPrivLevel = strPrivLevel + String.valueOf(lLevelTmp) + ";;" ;
					}
					strPrivName = strPrivName.substring(0, strPrivName.length()-2);
					strPrivUrl = strPrivUrl.substring(0, strPrivUrl.length()-2);
					strPrivLevel = strPrivLevel.substring(0, strPrivLevel.length()-2);
					m_strMainMenu = strPrivName + "::" + strPrivUrl + "::" + strPrivLevel;
				}
				else{
					m_strMainMenu = "";
				}
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}*/
	public void initMainModel(){
		try
		{
			StringBuffer strPrivName = new StringBuffer();
			StringBuffer strPrivModel = new StringBuffer();
			StringBuffer strPrivUrl = new StringBuffer();
			Collection mainModelColl = null;
			Sys_PrivilegeInfo pi = new Sys_PrivilegeInfo();

			GroupDelegation groupDelegation = new GroupDelegation();
			if(availableModules == null){
				m_strMainMenu = "";
			}
			else {
				mainModelColl =  groupDelegation.findPrivilegesByMain(availableModules,m_lUserID,m_lOfficeID);
				
				if(mainModelColl != null){
					mainModelColl = sortModules(mainModelColl);
					
					Iterator iMain = mainModelColl.iterator();
					while (iMain.hasNext()) {
						pi = (Sys_PrivilegeInfo)iMain.next();
						
						strPrivName.append(pi.getName() + ";;");
						strPrivModel.append(String.valueOf(pi.getModuleID()) + ";;");
						strPrivUrl.append(pi.getMenuUrl() + ";;");
					}
					strPrivName = new StringBuffer(strPrivName.substring(0, strPrivName.length()-2));
					strPrivModel = new StringBuffer(strPrivModel.substring(0, strPrivModel.length()-2));
					strPrivUrl = new StringBuffer(strPrivUrl.substring(0, strPrivUrl.length()-2));
					
					m_strMainMenu = strPrivName.toString() + "::" + strPrivModel.toString() + "::" + strPrivUrl.toString();
				}
				else{
					m_strMainMenu = "";
				}
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 初始化session, 使用ldap管理权限
	 * @param strLogin
	 * @param strPassword
	 * @param lModuleID
	 * @throws java.lang.Exception
	 */
	/*
	public void init ( String strLogin , String strPassword , long lModuleID ) throws java.lang.Exception
	{
  		Connection conn = null ;
		PreparedStatement ps = null ;
		ResultSet rs = null ;
		try
		{
 			UserInfo info = new UserInfo() ;
			UserDelegation delegation = new UserDelegation ( ) ;
			//
			info.setLoginno ( strLogin ) ;
			info.setPassword ( strPassword ) ;
			info.setStatusID(Constant.RecordStatus.VALID);
			Collection c = delegation.findUserByConditioni ( info ) ;
			if (c == null || c.size() == 0 )
			{
				throw new IException ( "用户和密码不匹配，请重新登录" ) ;
			} 
			else
			{
				Iterator it = c.iterator ( ) ;
				while (it.hasNext ( ))
				{
					info = (UserInfo) it.next ( ) ;
					//
					m_lUserID = info.getId ( ) ;
					m_lOfficeID = info.getOfficeID ( ) ;
					m_lIsAdminUser = info.getIsAdminUser ( ) ;
					m_lIsVirtualUser = info.getIsVirtualUser ( ) ;
					m_lDeportmentID = info.getDepartmentID ( ) ;
					m_strUserName = info./getName ( ) ;
					m_strLogin = info.getLoginno ( ) ;
					m_lCurrencyID = info.getCurrencyID ( ) ;
					m_lModuleID = lModuleID ;
					if (m_lCurrencyID <= 0)
					{
						if (lModuleID == Constant.ModuleType.LOAN)
							m_lCurrencyID = Constant.CurrencyType.RMB ;
						else if (lModuleID == Constant.ModuleType.FOREIGN)
							m_lCurrencyID = Constant.CurrencyType.USD ;
					}
					break;
				}
			}
			OfficeInfo officeInfo = findOfficeByID ( m_lOfficeID ) ;
			if( officeInfo != null )
			{
				m_strOfficeName = officeInfo.m_strName ;
				m_strOfficeNo = officeInfo.m_strCode ;
			}
			// receive user info from db.			
			log4j.info ( "m_lOfficeID=" + info.getOfficeID ( ) ) ;
			log4j.info ( "m_lUserID=" + info.getId ( ) ) ;
			log4j.info ( "Session OfficeName : " + m_strOfficeName ) ;
			/// receive user info from ldap
			PeopleInfo pi = null ;
			Ldap ldap = new Ldap ( ) ;
			pi = ldap.getUserByDN ( strLogin ) ;
			m_vGroupID = pi.vGroupID ;
			m_strPrivLevel = getUserPriv ( m_lUserID ) ;
			//
			m_userPrivilege = ldap.getPrivilegeOfUser ( m_lUserID , lModuleID ) ;
			m_vPrivilegeNo = new Vector ( ) ;
			if (m_userPrivilege != null)
			{
				java.util.Iterator it = m_userPrivilege.iterator ( ) ;
				while (it.hasNext ( ))
				{
					PrivilegeInfo o = (PrivilegeInfo) it.next ( ) ;
					m_vPrivilegeNo.addElement ( o.strNo ) ;
					//临时增加此初始化，找到null point原因后删除
					if(m_privilegeNameRef == null)
						m_privilegeNameRef = new HashMap();
					if (m_privilegeNameRef.get ( o.strNo ) == null)
						m_privilegeNameRef.put ( o.strNo , o.strName ) ;
				}
			}
		} catch (Exception e)
		{
			e.printStackTrace ( ) ;
			log4j.error ( e.toString ( ) ) ;
		}
  	}
  	*/
	
	/**
	 * 贷款模块用的，贷款模块保存操作日志的做法是：先在ejb中将loginfo保存到内存中，再在c页面调用此方法，将内存中的日志保存到数据库中
	 */
	synchronized public void addOperationLogger( HttpServletRequest request ) throws IException
	{
        //是否需要加业务操作日志
		boolean bool = Config.getBoolean(Config.GLOBAL_BTNLEVERLOG_ISVALID, false);		
		if(bool)
		{			
			LoggerResults vResult = LoggerResults.getInstance(); 
			// 得到当前所有未保存的日志记录
			Vector v = vResult.getResult();
			if(v != null && v.size() > 0)
			{
				LoggerBtnLevelInfo logInfo = null;
				while(v.size() > 0)
				{
					logInfo = (LoggerBtnLevelInfo)v.get(0);
//					判断操作类型是否需要记录日志
					if(Constant.LoggerOfOperationType.isNeedLog(logInfo.getActionTypeID()))
					{
						logInfo.setOfficeID(m_lOfficeID);
						logInfo.setCurrencyID(m_lCurrencyID);		
					    logInfo.setModuleID(m_lModuleID);
					    logInfo.setUserID(m_lUserID);
					    logInfo.setUserName(NameRef.getUserNameByID(logInfo.getUserID()));
					    logInfo.setActionType(Constant.LoggerOfOperationType.getName(logInfo.getActionTypeID()));
					    logInfo.setRemoteHost(request.getRemoteHost());
					    logInfo.setRemoteIP(request.getRemoteAddr());			    
					    LoggerBean logBean = new LoggerBean();
					    logBean.add(logInfo);					   
					}
					//保存日志以后从集合中移除 不管有没有保存日志，都从内存中移除，不然会出现死循环！！
				    v.remove(0);
				}
			}
			
			
		}
	}
	
	public void addOperationLogger( HttpServletRequest request , LoggerBtnLevelInfo logInfo) throws IException
	{
        //是否需要加业务操作日志
		boolean bool = Config.getBoolean(Config.GLOBAL_BTNLEVERLOG_ISVALID, false);
		if(bool)
		{			
			//判断操作类型是否需要记录日志
			if(Constant.LoggerOfOperationType.isNeedLog(logInfo.getActionTypeID()))
			{
				logInfo.setOfficeID(m_lOfficeID);
				logInfo.setCurrencyID(m_lCurrencyID);		
			    logInfo.setModuleID(m_lModuleID);
			    logInfo.setUserID(m_lUserID);
			    logInfo.setUserName(NameRef.getUserNameByID(logInfo.getUserID()));
			    logInfo.setActionType(Constant.LoggerOfOperationType.getName(logInfo.getActionTypeID()));
			    logInfo.setRemoteHost(request.getRemoteHost());
			    logInfo.setRemoteIP(request.getRemoteAddr());			    
			    LoggerBean logBean = new LoggerBean();
			    logBean.add(logInfo);
			}
			
		}
	}
	
	/**
	 * 此处插入方法说明。 创建日期：(2002-2-7 19:10:57)
	 * 
	 * @return boolean
	 * @param strCode
	 *            java.lang.String
	 */
	public boolean hasRight ( HttpServletRequest request ) throws IException
	{
		
		addOperationLogger(request);  //在每一次进页面之前将session中的日志信息保存
		boolean bReturn = false ;
		String strURL = request.getRequestURI();
		String strFromQuery = (String)request.getParameter("FromQuery");

		try
		{
			//校验请求是否由系统直接提交，而不是被人为修改后的请求
			RequestAlteredValidator.validate(request);
			
			String strIndex = "" ;
			int nOffset = 0 ;
			//区别自定义菜单
			String strTemp = "";
			if(isCustomModule)
			{
				strTemp = (String)request.getParameter("UmoduleId");
				if(strTemp!=null && strTemp.length()>0)
				{
					m_lModuleID = Long.valueOf(strTemp).longValue();
				}
			}
			//
			if (m_lModuleID == Constant.ModuleType.SETTLEMENT)
			{
				strIndex = "iTreasury-settlement/";
				nOffset = strIndex.length();
			} else if (m_lModuleID == Constant.ModuleType.LOAN)
			{
				strIndex = "iTreasury-loan/" ;
				nOffset = strIndex.length ( ) ;
			} else if (m_lModuleID == Constant.ModuleType.FOREIGN)
			{
				strIndex = "iTreasury-loan/" ;
				nOffset = strIndex.length ( ) ;
			} else if (m_lModuleID == Constant.ModuleType.SYSTEM)
			{
				strIndex = "iTreasury-system/" ;
				nOffset = strIndex.length ( ) ;
			} else if (m_lModuleID == Constant.ModuleType.SECURITIES)
			{
				strIndex = "iTreasury-securities/" ;
				nOffset = strIndex.length ( ) ;
			} else if (m_lModuleID == Constant.ModuleType.CLIENTCENTER)
			{
				strIndex = "iTreasury-clientcenter/" ;
				nOffset = strIndex.length ( ) ;
			}
			else if (m_lModuleID == Constant.ModuleType.PLAN)
			{
				strIndex = "iTreasury-treasuryplan/" ;
				nOffset = strIndex.length ( ) ;
			}
			else if (m_lModuleID == Constant.ModuleType.BILL)
			{
				strIndex = "iTreasury-bill/" ;
				nOffset = strIndex.length ( ) ;
			}
			else if (m_lModuleID == Constant.ModuleType.BUDGET)
			{
				strIndex = "iTreasury-budget/" ;
				nOffset = strIndex.length ( ) ;
			}
			else if (m_lModuleID == Constant.ModuleType.MANAGER)
			{
				strIndex = "iTreasury-settlement/" ;
				nOffset = strIndex.length ( ) ;
			}
			else if(m_lModuleID == Constant.ModuleType.CLIENTMANAGE)
			{
				strIndex = "iTreasury-clientmanage/" ;
				nOffset = strIndex.length ( ) ;
				
			}
			//added by xwhe 2009-05-31 
			else if(m_lModuleID == Constant.ModuleType.REPORT)
			{
				strIndex = "iTreasury-report/" ;
				nOffset = strIndex.length ( ) ;
				
			}
			else if(m_lModuleID == Constant.ModuleType.MANAGERQUERY)
			{
				strIndex = "iTreasury-managerQuery/" ;
				nOffset = strIndex.length ( ) ;
				
			}
			//增加过滤防止菜单插入无关业务日志
			String strPageURL="";
			if(strURL.indexOf(strIndex)>0)
			{
				strPageURL = strURL.substring (strURL.indexOf(strIndex) + nOffset , strURL.length());
			}
			else
			{
				strPageURL=strURL;
			}
			log4j.info ( " Request jsp : " + strPageURL ) ;
			//检查是否正在关机
			//Log.print("strFromQuery="+strFromQuery);
			//是否是交易，如果是交易，则关机后不能再处理
			boolean blnIsTransaction = strURL.indexOf("settlement/tran")>0 
										|| strURL.indexOf("settlement/obinstruction")>0 //网银指令接受
										|| strURL.indexOf("settlement/other/view/v101.jsp")>0 //手动结息 
										|| strURL.indexOf("settlement/mergevoucher/view/v001.jsp")>0 //合并凭证
										|| strURL.indexOf("settlement/accountsystem")>0; //企业资金上拨、下划
			if (blnIsTransaction && !(strFromQuery != null && strFromQuery.equals ("yes")))
			{
				//EndDayProcess prcess = new EndDayProcess();
				if (EndDayProcess.getSystemStatusID ( m_lOfficeID , m_lCurrencyID ) != Constant.SystemStatus.OPEN)
				{
					throw new IException ( "系统已经关机，不能继续业务处理。" ) ;
				}
				if (EndDayProcess.getDealStatusID ( m_lOfficeID , m_lCurrencyID ) == Constant.ShutDownStatus.DOING)
				{
					throw new IException ( "系统正在关机，不能继续业务处理。" ) ;
				}
				if (!DataFormat.getDateString(Env.getSystemDate(m_lOfficeID, m_lCurrencyID)).equals(DataFormat.getDateString(settlemetSystemDate)))
				{
					throw new IException ( "系统开机日期已经更新，请重新登录。" ) ;
				}
			}
			// 判断是否有权限
			boolean bMenuJsp = false;
			HostInfo hostInfo=new HostInfo();
			hostInfo.setRemoteHost(request.getRemoteHost());
			hostInfo.setRemoteIP(request.getRemoteAddr());
			
			if(m_userPrivilege != null)
			{
				Sys_PrivilegeInfo addlogpvg=null;
				Iterator it = m_userPrivilege.iterator();
				while (it.hasNext( ))
				{
				    Sys_PrivilegeInfo pvg = (Sys_PrivilegeInfo) it.next ( ) ;
					if(pvg.getMenuUrl() == null || pvg.getMenuUrl().length ( ) == 0)
						continue ;
					if(pvg.getMenuUrl().indexOf ( strPageURL ) >= 0)
					{
						bReturn = true ;
						bMenuJsp = true ;
						addlogpvg = pvg;
						break;
					}
				}
				
				if(bMenuJsp)
				{
					log4j.info ( " 写入系统菜单日志: " + strPageURL ) ;
					addLogger ( addlogpvg , strPageURL ,hostInfo) ;
				}
				else
				{
					log4j.info ( " 该URL不是系统菜单URL: " + strPageURL ) ;
				}
				if (bMenuJsp == false)
				{
					bReturn = true ;
				}
			}
			// 提取 request parameter， 中文转码。
			//加入判断如果是否需要中文转码，add by Forest,20040720
			String strToChinese = (String)request.getParameter("IsNeedConvertToChinese");
			int iToChinese = 1;//默认需要中文转码
			
			//保存当前的完整url 和 request.parameter()， 在整个框架的*c.jsp 可以做提示信息框
			//add by leiyang 2008-10-25
			m_CurrentAllURL = new StringBuffer();
			m_CurrentAllURL.append(strURL + "?1=1");
			
			if (strToChinese != null && strToChinese.trim().length()>0 )
			{
				iToChinese = Integer.parseInt(strToChinese);
			}
			Enumeration en = request.getParameterNames ( ) ;
			while (en.hasMoreElements ( ))
			{
				String strName = (String) en.nextElement ( ) ;
				String strValue = request.getParameter ( strName ) ;
				String lowerCaseName = strName.toLowerCase();
				//Forest注释，因为太慢。
				//if( lowerCaseName.indexOf("amount") == -1  &&  lowerCaseName.indexOf("balance") == -1 && lowerCaseName.indexOf("interest") == -1)
				if (iToChinese == 1)
				{
					strValue = DataFormat.toChinese ( strValue ) ;
				}
				
				if (request.getAttribute ( strName ) == null)
				{
					request.setAttribute ( strName , strValue ) ;
					if (iToChinese == 1)
					{
						log4j.info ( "====== request.parameter < " + strName + "   : " + request.getAttribute ( strName ) + " >" ) ;
						//add by leiyang 2008-10-25
						m_CurrentAllURL.append("&" + strName + "=" + request.getAttribute ( strName ));
					}
				}
			}
		} catch (IException e)
		{
			throw e ;
		} catch (Exception e)
		{
			e.printStackTrace();
			log4j.error ( e.toString ( ) ) ;
			return false ;
		}
		if(isValidate==1){
			bReturn = true ;
		}
		return bReturn ;
	}
	/**
	 * 此处插入方法说明。 创建日期：(2002-7-10 11:26:14)
	 * 
	 * @return com.iss.cpf.remoteresult.OfficeInfo
	 * @param lOfficeID
	 *            long
	 * @exception java.lang.Exception
	 *                异常说明。
	 */
	public OfficeInfo findOfficeByID ( long lID ) throws java.lang.Exception
	{
		PreparedStatement ps = null ;
		ResultSet rs = null ;
		Connection conn = null ;
		String FindString = null ;
		OfficeInfo OfficeInfo = null ;
		try
		{
			conn = Database.getConnection ( ) ;
			//Find in the Branch by lID
			FindString = "select id,scode,sname from office where id=?  " ;
			ps = conn.prepareStatement ( FindString ) ;
			ps.setLong ( 1 , lID ) ;
			rs = ps.executeQuery ( ) ;
			if (rs.next ( ))
			{
				OfficeInfo = new OfficeInfo ( ) ;
				OfficeInfo.m_lID = rs.getLong ( "ID" ) ;
				OfficeInfo.m_strCode = rs.getString ( "sCode" ) ;
				OfficeInfo.m_strName = rs.getString ( "sName" ) ;
			}
			rs.close ( ) ;
			rs = null ;
			ps.close ( ) ;
			ps = null ;
			conn.close ( ) ;
			conn = null ;
		} catch (Exception e)
		{
			throw new Exception ( e.getMessage ( ) ) ;
		} finally
		{
			try
			{
				if (rs != null)
				{
					rs.close ( ) ;
					rs = null ;
				}
				if (ps != null)
				{
					ps.close ( ) ;
					ps = null ;
				}
				if (conn != null)
				{
					conn.close ( ) ;
					conn = null ;
				}
			} catch (Exception ex)
			{
				throw new Exception (ex.getMessage ());
			}
		}
		return (OfficeInfo) ;
	}
	
	/**
	 * Modify by leiyang date 2007/08/09
	 *
	 */
	private void prepareMenu(){
		try
		{
			String strPrivNo = "" ;
			String strPrivName = "" ;
			String strPrivLevel = "" ;
			String strPrivUrl = "" ;
			String strModuleId = "";
			Vector vPi = null;
			Sys_PrivilegeInfo pi = new Sys_PrivilegeInfo();
			
			long lLevelTmp = 0 ;
			String strNoTmp = "" ;
			String strNameTmp = "" ;
			String strUrlTmp = "" ;
			long lModuleIdTmp = 0;
 			vPi = (Vector)m_userPrivilege;
			if (vPi == null)
			{
				m_strMenu = "" ;
			} 
			else{
				Enumeration ePi = vPi.elements();
				while(ePi.hasMoreElements()){
					pi = (Sys_PrivilegeInfo) ePi.nextElement();
					lLevelTmp = pi.getPlevel();
					strNoTmp = pi.getPrivilegeNo();
					strNameTmp = pi.getName();
					strUrlTmp = pi.getMenuUrl();
					lModuleIdTmp = pi.getModuleID();
					strPrivNo = strPrivNo + strNoTmp + ";;";
					strPrivName = strPrivName + strNameTmp + ";;";
					strPrivLevel = strPrivLevel + String.valueOf ( lLevelTmp ) + ";;";
					strPrivUrl = strPrivUrl + strUrlTmp + ";;";
					strModuleId = strModuleId + lModuleIdTmp + ";;";
				}
				strPrivName = strPrivName.substring (0 , strPrivName.length() -2);
				strPrivNo = strPrivNo.substring (0 , strPrivNo.length() -2);
				strPrivLevel = strPrivLevel.substring (0 , strPrivLevel.length() -2) ;
				strPrivUrl = strPrivUrl.substring (0 , strPrivUrl.length() -2);
				strModuleId = strModuleId.substring (0 , strModuleId.length() -2);
				m_strMenu = strPrivName + "::" + strPrivNo + "::" + strPrivLevel + "::" + strPrivUrl + "::" + strModuleId;
			}
		} 
		catch (Exception e) {
			System.out.print (e.toString());
		}
	}
	
	/**
	 * Modify by leiyang date 2007/08/09
	 *
	 */
	private void prepareMenuApart(){
		try
		{
			String strPrivNo = "" ;
			String strPrivName = "" ;
			String strPrivLevel = "" ;
			String strPrivUrl = "" ;
			String strModuleId = "";
			Vector vPi = null;
			Sys_PrivilegeInfo pi = new Sys_PrivilegeInfo();
			
			long lLevelTmp = 0 ;
			String strNoTmp = "" ;
			String strNameTmp = "" ;
			String strUrlTmp = "" ;
			long lModuleIdTmp = 0;
 			vPi = (Vector)m_userPrivilege;
			if (vPi == null)
			{
				m_strMenu = "" ;
			} 
			else{
				Enumeration ePi = vPi.elements();
				while(ePi.hasMoreElements()){
					pi = (Sys_PrivilegeInfo) ePi.nextElement();
					lLevelTmp = pi.getPlevel();
					strNoTmp = pi.getPrivilegeNo();
					strNameTmp = pi.getName();
					strUrlTmp = pi.getMenuUrl();
					lModuleIdTmp = pi.getModuleID();
					strPrivNo = strPrivNo + strNoTmp + ";;";
					strPrivName = strPrivName + strNameTmp + ";;";
					strPrivLevel = strPrivLevel + String.valueOf ( lLevelTmp ) + ";;";
					strPrivUrl = strPrivUrl + strUrlTmp + ";;";
					strModuleId = strModuleId + lModuleIdTmp + ";;";
				}
				strPrivName = strPrivName.substring (0 , strPrivName.length() -2);
				strPrivNo = strPrivNo.substring (0 , strPrivNo.length() -2);
				strPrivLevel = strPrivLevel.substring (0 , strPrivLevel.length() -2) ;
				strPrivUrl = strPrivUrl.substring (0 , strPrivUrl.length() -2);
				
				m_strMenu = strPrivName + "::" + strPrivNo + "::" + strPrivLevel + "::" + strPrivUrl;
			}
		} 
		catch (Exception e) {
			System.out.print (e.toString());
		}
	}	
	
	/**
	 * Create by leiyang date 2007/08/09
	 * @throws IOException 
	 * @throws IOException 
	 * @throws ParserConfigurationException 
	 * @throws JDOMException 
	 * @throws ParserConfigurationException 
	 *
	 */
	private void parseMenuToHTML(long lModuleID) throws IOException, ParserConfigurationException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document htmlDoc = builder.newDocument();
		Element mainDiv = htmlDoc.createElement("div");
		mainDiv.setAttribute("id","qm0");
		mainDiv.setAttribute("class","qmmc");
		
		
		//m_strMenu 的解析
		String[] arrDepart = null; //取得用户权限;
		
		String[] arrName = null;
		String[] arrNo = null;
		String[] arrLevel = null;
		String[] arrUrl = null;
		String[] arrModule = null;
		ArrayList sParent = null;
		String[] tmpArrNo = null; //替换后的编号
		String[] newArrNo = null; //处理完成后的编号

		if(!this.m_strMenu.equals("")){
			arrDepart = this.m_strMenu.split("::");
			if(arrDepart.length == 5 && arrDepart != null){
				arrName = arrDepart[0].split(";;");
				arrNo = arrDepart[1].split(";;");
				arrLevel = arrDepart[2].split(";;");
				arrUrl = arrDepart[3].split(";;");
				arrModule = arrDepart[4].split(";;");
				int iLength = arrName.length;
				int i = 0;
				String tmpStr = "";
				sParent = new ArrayList();
				tmpArrNo = new String[iLength];
				newArrNo = new String[iLength];
				
				
				//替换编号的"-"号为下划线
				for(i=0; i<iLength; i++){
					tmpArrNo[i] = arrNo[i].replace('-','_');
					while(tmpArrNo[i].indexOf("-")>0){
					 	tmpArrNo[i] = tmpArrNo[i].replace('-','_');
					}
				}
				//自定义模块 add by zcwang 2008-1-11
				if(lModuleID==Constant.CustomModule.CUSTOMMODULE)
				{
					for(i=0; i<iLength; i++){
						//根据级别取出上级权限编号
						if("1".equals(arrLevel[i]))
						{
							sParent.add("");
							newArrNo[i] = "Mod" + arrModule[i];
						}
						else
						{
							if(tmpArrNo[i].indexOf("_") == tmpArrNo[i].lastIndexOf("_")){
								//sParent[i] = "";
								sParent.add("Mod" + arrModule[i]);
								newArrNo[i] = "Top" + tmpArrNo[i];
							}
							else{
								tmpStr = tmpArrNo[i].substring(0,tmpArrNo[i].lastIndexOf("_"));
								if (tmpStr.lastIndexOf("_")==1 || tmpStr.lastIndexOf("_")==2){
									//sParent[i] = "Top" + tmpStr;
									sParent.add("Top" + tmpStr);
								}
								else{
									//sParent[i] = "Sub" + tmpStr;
									sParent.add("Sub" + tmpStr);
								}
								newArrNo[i] = "Sub" + tmpArrNo[i];
							}
						}
							//处理链接地址
							/*if (!arrUrl[i].equals("") && !arrUrl[i].equals("null")){
								arrUrl[i] = Env.URL_PREFIX + arrUrl[i];
							}
							else{
								arrUrl[i] = "";
							}*/
							if(arrUrl[i].equals("") || arrUrl[i].equals("null")){
								arrUrl[i] = "";
							}
							else{
								String Umodule="UmoduleId=";
								if(lModuleID == Constant.ModuleType.SYSTEM){
									if(arrUrl[i].indexOf(".do") == -1){
										//add by zcwang 2008-1-11
										lModuleID=Long.valueOf(arrUrl[i].substring(arrUrl[i].indexOf(Umodule)+Umodule.length())).longValue();
										arrUrl[i] = Env.getInstance().getURL(lModuleID) + arrUrl[i];
									}
								}
								else{
//										//add by zcwang 2008-1-11
										lModuleID=Long.valueOf(arrUrl[i].substring(arrUrl[i].indexOf(Umodule)+Umodule.length())).longValue();
									arrUrl[i] = Env.getInstance().getURL(lModuleID) + arrUrl[i];
								}
							}
						}
				}
				else
				{
					for(i=0; i<iLength; i++){
					//根据级别取出上级权限编号
						if(tmpArrNo[i].indexOf("_") == tmpArrNo[i].lastIndexOf("_")){
							//sParent[i] = "";
							sParent.add("");
							newArrNo[i] = "Top" + tmpArrNo[i];
						}
						else{
							tmpStr = tmpArrNo[i].substring(0,tmpArrNo[i].lastIndexOf("_"));
							if (tmpStr.lastIndexOf("_")==1 || tmpStr.lastIndexOf("_")==2){
								//sParent[i] = "Top" + tmpStr;
								sParent.add("Top" + tmpStr);
							}
							else{
								//sParent[i] = "Sub" + tmpStr;
								sParent.add("Sub" + tmpStr);
							}
							newArrNo[i] = "Sub" + tmpArrNo[i];
						}
						//处理链接地址
						/*if (!arrUrl[i].equals("") && !arrUrl[i].equals("null")){
							arrUrl[i] = Env.URL_PREFIX + arrUrl[i];
						}
						else{
							arrUrl[i] = "";
						}*/
						if(arrUrl[i].equals("") || arrUrl[i].equals("null")){
							arrUrl[i] = "";
						}
						else{
							if(lModuleID == Constant.ModuleType.SYSTEM){
								if(arrUrl[i].indexOf(".do") == -1){
									arrUrl[i] = Env.getInstance().getURL(lModuleID) + arrUrl[i];
								}
							}
							else{
								arrUrl[i] = Env.getInstance().getURL(lModuleID) + arrUrl[i];
							}
						}
					}
				}
				//m_strMenu 的解析结束
					
				for(i=0; i<iLength; i++){
					Element elementA = null;
					Element elementDIV = null;
					Element subElementDIV = null;
					String strParent = sParent.get(i).toString();
					
					if(strParent.equals("")){						
						elementA = htmlDoc.createElement("a");
						elementA.appendChild(htmlDoc.createTextNode(arrName[i]));
						elementA.setAttribute("href", "javascript:void(0)");
						elementA.setAttribute("id",newArrNo[i]);
						mainDiv.appendChild(elementA);
						
						if(sParent.contains(newArrNo[i])){
							elementDIV = htmlDoc.createElement("div");
							elementDIV.setAttribute("id", newArrNo[i] + "_div");
							mainDiv.appendChild(elementDIV);
						}
					}
					else{
						NodeList nodeList = mainDiv.getElementsByTagName("div");
						for(int j=0; j<nodeList.getLength(); j++){
							elementDIV = (Element)nodeList.item(j);
							if(elementDIV.getNodeName().equals("div") && elementDIV.getAttribute("id").equals(strParent + "_div")){
								
								elementA = htmlDoc.createElement("a");
								elementA.appendChild(htmlDoc.createTextNode(arrName[i]));
								if(arrUrl[i].equals("")){
									elementA.setAttribute("href", "javascript:void(0)");
								}
								else{
									elementA.setAttribute("href", "javascript:navigation('" + arrUrl[i] +"','"+newArrNo[i]+ "')");//add by xfma 2008-12-22
								}
								elementA.setAttribute("id",newArrNo[i]);
								elementDIV.appendChild(elementA);
								
								if(sParent.contains(newArrNo[i])){
									subElementDIV = htmlDoc.createElement("div");
									subElementDIV.setAttribute("id", newArrNo[i] + "_div");
									elementDIV.appendChild(subElementDIV);
								}
							}
						}
					}
				}
			}
		}
		//htmlDoc.appendChild(mainDiv);
		
		//输出
		StringBuffer stringOut = new StringBuffer();
		stringOut.append("<div id=\"qm0\" class=\"qmmc\">");
		this.parseMenuDOM(stringOut, mainDiv);
		stringOut.append("<span class=\"qmclear\">&nbsp;</span></div>");
		
		//System.out.println(stringOut.toString());
		
		
		System.out.println("准备菜单 ------------------------");
		this.m_strMenu = stringOut.toString();
	}
	
	private void parseMenuDOM(StringBuffer stringOut, Node node){
		NodeList parentList = node.getChildNodes();
		for(int i=0; i<parentList.getLength(); i++){
			Node parentNode = parentList.item(i);
			stringOut.append("<");
			stringOut.append(parentNode.getNodeName());
			
			NamedNodeMap parentAttrMap = parentNode.getAttributes();
			if(parentAttrMap != null){
				for(int j=0; j<parentAttrMap.getLength(); j++){
					Node parentNodeAttr = parentAttrMap.item(j);
					stringOut.append(" ");
					stringOut.append(parentNodeAttr.getNodeName());
					stringOut.append("=\"");
					stringOut.append(parentNodeAttr.getNodeValue());
					stringOut.append("\"");
				}
			}
			stringOut.append(">");
			
			if(parentNode.getChildNodes().getLength() > 1){
				parseMenuDOM(stringOut,parentNode);
			}
			else {
				Node childNode = parentNode.getFirstChild();
				if(childNode.getNodeType() == Node.ELEMENT_NODE){
					parseMenuDOM(stringOut,parentNode);
				}
				else{
					stringOut.append(parentNode.getFirstChild().getNodeValue());
				}
			}

			stringOut.append("</");
			stringOut.append(parentNode.getNodeName());
			stringOut.append(">");
		}
	}
	
	/*
	 */
	public long getTransactionType ( )
	{
		return lSessionTransType ;
	}
	/**
	 * 检查权限，用于优化后的系统
	 * 
	 * @param request
	 * @return boolean
	 */
	public boolean checkRight ( HttpServletRequest request )
	{
		boolean b = true ;
		return b ;
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
	public String getUserPriv ( long lUserID )
	{
		StringBuffer sb = new StringBuffer ( ) ;
		String strResult = "" ;
		Connection con = null ;
		PreparedStatement ps = null ;
		ResultSet rs = null ;
		try
		{
			con = Database.getConnection ( ) ;
			// get the whole content
			sb.append ( " SELECT sPrivLevel FROM userinfo Where ID = ?" ) ;
			ps = con.prepareStatement ( sb.toString ( ) ) ;
			ps.setLong ( 1 , lUserID ) ;
			rs = ps.executeQuery ( ) ;
			if (rs.next ( ))
			{
				strResult = rs.getString ( 1 ) ;
			} else
			{
				strResult = "" ;
			}
			if (rs != null)
			{
				rs.close ( ) ;
				rs = null ;
			}
			if (ps != null)
			{
				ps.close ( ) ;
				ps = null ;
			}
			sb.setLength ( 0 ) ;
		} catch (Exception e)
		{
			strResult = "" ;
		} finally
		{
			try
			{
				if (rs != null)
				{
					rs.close ( ) ;
					rs = null ;
				}
				if (ps != null)
				{
					ps.close ( ) ;
					ps = null ;
				}
				if (con != null)
				{
					con.close ( ) ;
					con = null ;
				}
			} catch (Exception e)
			{
				strResult = "" ;
			}
		}
		return strResult ;
	}
	/**
	 * Returns the actionID.
	 * 
	 * @return long
	 */
	public long getActionID ( )
	{
		return m_lActionID ;
	}
	/**
	 * Sets the actionID.
	 * 
	 * @param actionID
	 *            The actionID to set
	 */
	public void setActionID ( long actionID )
	{
		m_lActionID = actionID ;
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
	/**
	 * Returns the login.
	 * 
	 * @return String
	 */
	public String getLogin ( )
	{
		return m_strLogin ;
	}
	/**
	 * Sets the login.
	 * 
	 * @param login
	 *            The login to set
	 */
	public void setLogin ( String login )
	{
		m_strLogin = login ;
	}
	/**
	 * @return Returns the pageLoader.
	 */
	public PageLoader getPageLoader ( String key )
	{
		PageLoader result = null ;
		if (key != null && key.trim ( ).length ( ) > 0 && this.hmPageLoader != null)
		{
			try
			{
				result = (PageLoader) this.hmPageLoader.get ( key ) ;
			} catch (ClassCastException e)
			{
				System.out.println ( "Session中PageLoader属性存有非PageLoader对象（key:" + key + "）" ) ;
				this.hmPageLoader.remove ( key ) ;
			}
		}
		return result ;
	}
	/**
	 * 保存PageLoader对象同时返回key值
	 * 注：key值使用传人对象的hashCode，如果Session中已经有同样key的PageLoader对象，则不保存同时返回null
	 * 
	 * @param pageLoader
	 * @return String 返回的key
	 */
	public String setPageLoader ( PageLoader pageLoader )
	{
		String key = null ;
		if (pageLoader != null)
		{
			if (this.hmPageLoader == null)
			{
				this.hmPageLoader = new HashMap ( 6 ) ;
			}
			Random random1 = new Random ( ) ;
			Random random2 = new Random ( random1.nextLong ( ) ) ;
			key = String.valueOf ( random2.nextLong ( ) ) ;
			if (!this.hmPageLoader.containsKey ( key ))
			{
				this.hmPageLoader.put ( key , pageLoader ) ;
			} else
			{
				key = null ;
			}
		}
		return key ;
	}
	/**
	 * @return
	 */
	public Object getQueryCondition ( String key )
	{
		Object result = null ;
		if (key != null && key.trim ( ).length ( ) > 0 && this.hmQueryCondition != null)
		{
			result = this.hmQueryCondition.get ( key ) ;
		}
		return result ;
	}
	/**
	 * 在集合中保存查询条件对象
	 * 
	 * @param key
	 *            key值，用于索引对象，不能为“”和null
	 * @param queryCondition
	 */
	public void setQueryCondition ( String key , Object queryCondition )
	{
		if (key != null && key.trim ( ).length ( ) > 0 && queryCondition != null)
		{
			if (this.hmQueryCondition == null)
			{
				this.hmQueryCondition = new HashMap ( 6 ) ;
			}
			this.hmQueryCondition.put ( key , queryCondition ) ;
		}
	}
	/**
	 * @return
	 */
	public Object getSumResult ( String key )
	{
		Object result = null ;
		if (key != null && key.trim ( ).length ( ) > 0 && this.hmSumResult != null)
		{
			result = this.hmSumResult.get ( key ) ;
		}
		return result ;
	}
	/**
	 * 在集合中保存统计结果对象
	 * 
	 * @param key
	 *            key值，用于索引对象，不能为“”和null
	 * @param sumResult
	 */
	public void setSumResult ( String key , Object sumResult )
	{
		if (key != null && key.trim ( ).length ( ) > 0 && sumResult != null)
		{
			if (this.hmSumResult == null)
			{
				this.hmSumResult = new HashMap ( 6 ) ;
			}
			this.hmSumResult.put ( key , sumResult ) ;
		}
	}
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
		if (this.hmSumResult != null)
		{
			this.hmSumResult.clear ( ) ;
		}
	}
	public void addLogger ( Sys_PrivilegeInfo pvg , String strURL ,HostInfo hostInfo) throws Exception
	{
		LoggerInfo li = new LoggerInfo ( ) ;
			//
		li.setCurrencyID ( m_lCurrencyID ) ;
		li.setOfficeID ( m_lOfficeID ) ;
		li.setModuleID ( m_lModuleID ) ;
		li.setUserID ( m_lUserID ) ;
		li.setUserName ( m_strUserName ) ;
		li.setFunctionPointCode ( pvg.getPrivilegeNo() ) ;
		li.setAccessTime ( Env.getSystemDateTime() ) ;
		//
		Log.print ( "pvg.jsp - " + pvg.getMenuUrl() + "  ==  url - " + strURL ) ;
		int fromIndex = 3 ;
		int offset = pvg.getPrivilegeNo().indexOf ( "-" , fromIndex ) ;
		int length = pvg.getPrivilegeNo().length ( ) ;
		String pvgNo = "" ;
		String funcationPointDescription = "" ;
		for (int i = 2; i <= pvg.getPlevel(); i++)
		{
			offset = pvg.getPrivilegeNo().indexOf ( "-" , fromIndex + 1 ) ;
			pvgNo = pvg.getPrivilegeNo().substring ( 0 , offset < 0 ? length : offset ) ;
			Log.print ( "第 " + i + " 级权限编码 : " + pvgNo ) ;
			funcationPointDescription = funcationPointDescription + m_privilegeNameRef.get ( pvgNo ) + "-" ;
			fromIndex = pvgNo.length ( ) ;
		}
		li.setFunctionPointDescription ( funcationPointDescription.substring ( 0 , funcationPointDescription.length ( ) - 1 ) ) ;

		li.setRemoteHost( hostInfo.getRemoteHost() );
		li.setRemoteIP( hostInfo.getRemoteIP() );

		LoggerDelegation delegation = new LoggerDelegation ( ) ;
		delegation.addLogger ( li ) ;
	}
	/**
	 * @return Returns the m_lDeportmentID.
	 */
	public long getDeportmentID ( )
	{
		return m_lDeportmentID ;
	}
	/**
	 * @param deportmentID
	 *            The m_lDeportmentID to set.
	 */
	public void setDeportmentID ( long deportmentID )
	{
		m_lDeportmentID = deportmentID ;
	}
	/**
	 * @return Returns the availableModules.
	 */
	public ArrayList getAvailableModules() {
		return availableModules;
	}
	
	/**
	 * Create my leiyang date 2007/08/16
	 * @param out
	 * @param strTitle
	 * @return
	 */
	public HtmlHeaderInfo getHtmlHeaderInfo(JspWriter out, String strTitle){
		HtmlHeaderInfo htmlHeader = null;
        try{
			String strRemindURL = "";
			
			if(m_lModuleID == Constant.ModuleType.SETTLEMENT){
				strRemindURL = Env.getInstance().getURL(Constant.ModuleType.SETTLEMENT) + "settlement/msg/RemindMsg.jsp";
			}
			else if(m_lModuleID == Constant.ModuleType.LOAN){
				strRemindURL = Env.getInstance().getURL(Constant.ModuleType.LOAN) + "msg/RemindMsg.jsp";
			}
			/*else if(m_lModuleID == Constant.ModuleType.SYSTEM){
				strRemindURL = Env.getInstance().getURL(Constant.ModuleType.SYSTEM) + "msg/RemindMsg.jsp";
			}*/
			else if(m_lModuleID == Constant.ModuleType.CLIENTMANAGE){
				strRemindURL = Env.getInstance().getURL(Constant.ModuleType.CLIENTMANAGE) + "msg/RemindMsg.jsp";
			}
			/*else if(m_lModuleID == Constant.ModuleType.EVOUCHER){
				strRemindURL = Env.getInstance().getURL(Constant.ModuleType.EVOUCHER) + "msg/RemindMsg.jsp";
			}*/
			else if(m_lModuleID == Constant.ModuleType.BILL){
				strRemindURL = Env.getInstance().getURL(Constant.ModuleType.BILL) + "RemindMsg.jsp";
			}
			/*
			else if(m_lModuleID == Constant.ModuleType.SECURITIES){
				strRemindURL = Env.getInstance().getURL(Constant.ModuleType.SECURITIES) + "msg/RemindMsg.jsp";
			}
			*/
			else if(m_lModuleID == Constant.ModuleType.CRAFTBROTHER){
				strRemindURL = Env.getInstance().getURL(Constant.ModuleType.CRAFTBROTHER) + "msg/RemindMsg.jsp";
			}
			else{
				strRemindURL = "/NASApp/msg/RemindMsg.htm";
			}
			
			
	        StringBuffer strStatus = new StringBuffer();
	        EndDayProcess process = new EndDayProcess();
	        
	    	if(m_lCurrencyID > 0){
	    		    strStatus.append("当前机构："+Env.getOfficeName(m_lOfficeID)+ "  ");
	    		    if(m_lModuleID==Constant.ModuleType.SETTLEMENT
	    					||m_lModuleID==Constant.ModuleType.LOAN||m_lModuleID==Constant.ModuleType.EVOUCHER
	    					||m_lModuleID==Constant.ModuleType.AUDIT||m_lModuleID==Constant.ModuleType.REPORT||m_lModuleID==Constant.ModuleType.BANKPORTAL) //jzw 2010-04-15 必选币种功能有货币币种显示
	        		strStatus.append(Constant.CurrencyType.getName(m_lCurrencyID) + "  ");
	        		strStatus.append(SETTConstant.SystemStatus.getName(process.getSystemStatusID(m_lOfficeID, m_lCurrencyID)) + "<br>");
	        		strStatus.append("用&nbsp;&nbsp;&nbsp;&nbsp;户：" + m_strUserName + "<br/>开机时间：" + DataFormat.getChineseDateString(Env.getSystemDate(m_lOfficeID, m_lCurrencyID)));	        		
	        		if(dLastLoginTime != null && !dLastLoginTime.equals("")){
	             		strStatus.append("<br/>您上次登录时间："+ DataFormat.getChineseTimeString(dLastLoginTime));
	            	}
	        	}
	    	
	    	String[] date = Env.getSystemDateString().split("-");
	
	        String strProject = "iTreasuryPro";
	        htmlHeader = new HtmlHeaderInfo();
	        htmlHeader.setJspWriter(out);
	        htmlHeader.setTitle(strTitle);
	        htmlHeader.setShowMenu(1);
	        htmlHeader.setRemindURL(strRemindURL);
	        htmlHeader.setStatus(strStatus.toString());
	        htmlHeader.setProjectName(strProject);
	        htmlHeader.setTitle(strProject);
	        htmlHeader.setYear(date[0]);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return htmlHeader;
	}
	
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		m_lUserID = in.readLong();
		m_lOfficeID = in.readLong();
		m_vGroupID = (Vector) in.readObject();
		m_lCurrencyID = in.readLong();
		m_strPrivLevel = (String) in.readObject();
		m_lClientID = in.readLong();
		m_strUserName = (String) in.readObject();
		m_strLogin = (String) in.readObject();
		m_lModuleID = in.readLong();
		m_strOfficeName = (String) in.readObject();
		nSize = in.readInt();
		m_strMainMenu =(String) in.readObject();
		m_strMenu =(String) in.readObject();
		m_strCurrencySymbol =(String) in.readObject();
		m_strOfficeNo =(String) in.readObject();
		m_strCertIssuer =(String) in.readObject();
		m_strSignature =(String) in.readObject();
		m_strSessionID =(String) in.readObject();
		
		m_lCloseSystem = in.readLong();
		m_lIsAdminUser = in.readLong();
		m_lIsVirtualUser = in.readLong();
		m_bIsOpenCloseMsg = in.readBoolean();
		lSessionTransType = in.readLong();
		m_strMenuURL = (String) in.readObject();
		//log4j = (Log4j) in.readObject();
		m_lActionID = in.readLong();
		m_actionMessages = (ActionMessages) in.readObject();
		hmPageLoader = (HashMap) in.readObject();
		hmQueryCondition = (HashMap) in.readObject();
		hmSumResult = (HashMap) in.readObject();
		m_userPrivilege = (Collection) in.readObject();
		m_allPrivilege = (Collection) in.readObject();
		m_vPrivilegeNo = (Vector) in.readObject();
		m_privilegeNameRef = (HashMap) in.readObject();
		availableModules =(ArrayList) in.readObject();
		
		lRemindNo = in.readLong();
		lRemindCount = in.readLong();
		lLastOBCount = in.readLong();
		bIsNeedSoundRemind = in.readBoolean();
		lRemindIntervalTime = in.readLong();
		lLastSystemTime = in.readLong();
		m_lDeportmentID = in.readLong();
		
		dLastLoginTime =(Timestamp) in.readObject();
		sLastLoginTime =(String) in.readObject();
		sChineseSystemDate =(String) in.readObject();
		m_logCode =(String) in.readObject();
		
	}
	public void setServletInfo(int port,String name){
		if(com.iss.itreasury.evoucher.setting.dao.PrintSettingDao.servletPort==0){
			com.iss.itreasury.evoucher.setting.dao.PrintSettingDao.servletPort = port;
			com.iss.itreasury.evoucher.setting.dao.PrintSettingDao.servletName = name;
		}
	}
	public void setServletInfo(int port,String name,String contextPath){
		if(com.iss.itreasury.evoucher.setting.dao.PrintSettingDao.servletPort==0){
			com.iss.itreasury.evoucher.setting.dao.PrintSettingDao.servletPort = port;
			com.iss.itreasury.evoucher.setting.dao.PrintSettingDao.servletName = name;
			com.iss.itreasury.evoucher.setting.dao.PrintSettingDao.servletContextPath = contextPath;
		}
	}
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeLong(m_lUserID);
		out.writeLong(m_lOfficeID);
		out.writeObject(m_vGroupID);
		out.writeLong(m_lCurrencyID);
		out.writeObject(m_strPrivLevel);
		out.writeLong(m_lClientID);
		out.writeObject(m_strUserName);
		out.writeObject(m_strLogin);
		out.writeLong(m_lModuleID);
		out.writeObject(m_strOfficeName);
		out.writeInt(nSize);
		out.writeObject(m_strMainMenu);
		out.writeObject(m_strMenu);
		out.writeObject(m_strCurrencySymbol);
		out.writeObject(m_strOfficeNo);
		out.writeObject(m_strCertIssuer);
		out.writeObject(m_strSignature);
		out.writeObject(m_strSessionID);
		
		out.writeLong(m_lCloseSystem);
		out.writeLong(m_lIsAdminUser);
		out.writeLong(m_lIsVirtualUser);
		out.writeBoolean(m_bIsOpenCloseMsg);
		out.writeLong(lSessionTransType);
		out.writeObject(m_strMenuURL);
		//out.writeObject(log4j);
		out.writeLong(m_lActionID);
		out.writeObject(m_actionMessages);
		out.writeObject(hmPageLoader);
		out.writeObject(hmQueryCondition);
		out.writeObject(hmSumResult);
		out.writeObject(m_userPrivilege);
		out.writeObject(m_allPrivilege);
		out.writeObject(m_vPrivilegeNo);
		out.writeObject(m_privilegeNameRef);
		out.writeObject(availableModules);
		
		out.writeLong(lRemindNo);
		out.writeLong(lRemindCount);
		out.writeLong(lLastOBCount);
		out.writeBoolean(bIsNeedSoundRemind);
		out.writeLong(lRemindIntervalTime);
		out.writeLong(lLastSystemTime);
		out.writeLong(m_lDeportmentID);
		
		out.writeObject(dLastLoginTime);
		out.writeObject(sLastLoginTime);
		out.writeObject(sChineseSystemDate);
		out.writeObject(m_logCode);
	}
	
	public boolean checkUserCert(String strCertCN,String strCertSN) throws Exception {
		boolean isPass = false;
		if(strCertCN == null || strCertSN == null){
			isPass = false;
		}else{
			if(Config.getBoolean(ConfigConstant.SETTLEMENT_CERT_SUBJECTCOMMONNAME_ISNEEDED, true))//CN校验默认是必须的
	    	{
				isPass = strCertCN.equals(this.m_strCertCN);
	    	}
			if(isPass && Config.getBoolean(ConfigConstant.SETTLEMENT_CERT_SERIALNUMBER_ISNEEDED, false)){
				isPass = strCertSN.equals(this.m_strCertSerialNumber);
			}
		}
		return isPass;
	}
}
