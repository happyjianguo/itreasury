/*
 * SessionMng.java
 
 * Created on 2001��12��6��, ����2:51
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
	public int isValidate = 0 ;		//�Ƿ����Ȩ���Լ���¼��֤ 0 = ��֤ ��1=����֤
	public long				m_lUserID			= -1;
	public long				m_lOfficeID			= -1;
	public Vector			m_vGroupID			= null;
	public long				m_lCurrencyID		= 1;						//������Ϣ
	public String			m_strPrivLevel		= "";						//added
	public long 			m_lClientID 		= -1;						//��������¼Ԥ����¼�û�������λ��Ϊ����Ĭ��ֵ��Ϊ1
																				 // hally
																				 // 03/14/2002
	public String   		m_isStop 			= "false";					//���ַǱ�ѡģ���Ƿ���Ҫ���¼��� jzw 2010-04-21
	public String			m_isSelectCurrency	= "false";					//���ֱ�ѡģ���Ƿ���Ҫ���¼���   jzw 2010-04-21
	public long             m_oldCurrentID      = -1;						//���֤������Ҫ��Ĭ�ϱ��ֵ�ģ�飬���Һ�ѡ��ı������ֿ�������ӵ��м�������� jzw 2010-04-29
	
	public String			m_strUserName		= "";
	public String			m_strLogin			= "";
	public long				m_lModuleID			= -1;
	public String			m_strOfficeName		= "";
	private int				nSize				= 0;
	public String           m_strMainMenu       = ""; //ģʽ�˵�
	public String			m_strMenu			= ""; //Tree�˵�
	public String			m_strCurrencySymbol	= "��";
	public String			m_strOfficeNo		= "";
	public String			m_strCertIssuer		= "";
	public String			m_strSignature		= "";
	public String			m_strSessionID		= "";						//Session
																				 // �ı�ʾ
																				 // added
																				 // by
																				 // Hally
																				 // 01/14/2003
	public long				m_lCloseSystem		= 0 ;
	public long				m_lIsAdminUser		= 2 ;						//Ĭ��Ϊ��
	public long				m_lIsVirtualUser	= 2 ;						//Ĭ��Ϊ��
	public boolean			m_bIsOpenCloseMsg	= true ;					/////Ī��Ϊtrue;���ػ�ʱֵΪfalse;����򿪹���������ҳ�棬��ֵΪtrue;
	//���ڽ��еĽ���ҵ������
	private long			lSessionTransType	= -1 ;
	private String			m_strMenuURL		= null ;
	//
	private static Log4j	log4j				= null ;
	//��ʶ�ύ������
	private long			m_lActionID			= -1 ;
	private ActionMessages	m_actionMessages	= new ActionMessages ( ) ;
	// ��ҳ������
	private HashMap			hmPageLoader		= null ;
	//
	private HashMap			hmQueryCondition	= null ;
	private HashMap			hmSumResult			= null ;
	//Ȩ�޼���
	private Collection		m_userPrivilege		= null ;
	private static Collection m_allPrivilege    = null; 
	private Vector			m_vPrivilegeNo		= null ;
	private HashMap			m_privilegeNameRef	= new HashMap();
	private ArrayList		availableModules  	=null;						//�����û����ܲ�����ģ�鼯��
	
	/** ****�������ѿ��Ʋ�����2004-04-15��Forest����****��ʼ* */
	private long			lRemindNo			= 0 ;						//�ڼ�������
	private long			lRemindCount		= 3 ;						//ÿ����Ҫ���Ѵ���
	private long			lLastOBCount		= 0 ;						//��������ҵ�������
	public boolean			bIsNeedSoundRemind	= false ;					//�Ƿ���Ҫ��������
	//2005-01-25 kewen hu(�������ѿ��Ʋ���)
	private long			lRemindIntervalTime = 120000;					//��������ҵ�����ѵ�ʱ����(����Ҫ���ѵ�ǰ����)
	private long			lLastSystemTime		= 0;						//��һ��ˢ��ʱ��ϵͳʱ��
	//
	private long			m_lDeportmentID		= -1 ;
	
	public Timestamp      dLastLoginTime = null;					//�ϴε�¼ʱ��
	public String sLastLoginTime = null;							//�ϴε�¼ʱ�䣬����������ʱ�����ʽ
	public Timestamp settlemetSystemDate= null;								//���㿪��ʱ��
	public String sChineseSystemDate = null;						//���㿪��ʱ�䣬���������ո�ʽ
	//Modify by leiyang date 2007/07/03
	public String m_logCode = "";           //��־���� 
	
	public boolean isCustomModule =false;  //�Զ���ģ���ʶ add by zcwang 2008-1-10
	
	public String m_strCertSerialNumber = ""; //added by mzh_fu 2007/09/03 �û�֤�����к�
	
	public String m_strCertCN=""; //added by mzh_fu 2008/01/30 ֤��CN
	
	public StringBuffer m_CurrentAllURL = null; //���浱ǰURL add by leiyang 2008-10-25
	
	//ȥ���ְ��´��Ļ��ơ������û������ơ������û�û�б�Ҫ�ְ��´���ֱ�Ӵ�Constant.MachineUser��ȡ modify by bingliu 20110730
//	public long AUTOINPUTUSERID = -100;
//	public long AUTOCHECKUSERID = -101;

	/**
	 * ��������ǰ���� long lOBCount ����ҵ������
	 */
	public void BeforeSound ( long lOBCount )
	{
		//�������������ҵ�񣨻��¼��������ҵ�񣩣��������������
		if (lOBCount > lLastOBCount)
		{
			this.bIsNeedSoundRemind = true ;
			this.lLastOBCount = lOBCount ;
		}
		//��������趨�����Ѵ���������ΪΪfalse��
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
	 * ��������ǰ����
	 * @author zqhu
	 * @param nothing
	 * @see Ϊ�Ϻ�����ר���ķ�����ǰ���ǽ�����������1�ʵ�����ָ��
	 */
	public void BeforeSound () {
		//�����ǰʱ����ǰһ�����ѵ�ʱ����ﵽ120��ʱ������
		long sysdate=Env.getSystemDateTime().getTime();
		System.out.println("ʱ����ﵽ="+(sysdate-lLastSystemTime));
		if ((sysdate-lLastSystemTime) >= lRemindIntervalTime) {
			this.bIsNeedSoundRemind = true ;
			this.lLastSystemTime = sysdate;
		}
		//��������趨�����Ѵ���������ΪΪfalse��
		if (this.bIsNeedSoundRemind == true) {
			this.lRemindNo += 1 ;
			if (this.lRemindNo % (this.lRemindCount + 1) == 0) {
				this.bIsNeedSoundRemind = false ;
				this.lRemindNo = 0 ;
			}
		}
	}
	/** ****�������ѿ��Ʋ�����2004-04-15��Forest����****����* */
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
		m_strCurrencySymbol = "��" ;
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
	 * �������ߵ�¼
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
		//����У��
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
			throw new IException ( "��¼ʧ��,�����û���������" ) ;
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
						throw new IException ( "���û�δ����,�븴�˺��¼!" ) ;
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
				
				//added by mzh_fu �û�֤�����к�
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
	 * �����SDC���ɹ���������
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
			//�������������ldap��ѯ�����½ʱ�Ͳ���Ҫ��֤ҵ���½���룬���򣬾���Ҫ��֤�����Ƿ�����ݿ���һ�²ſ��Ե�½
			if (!Config.getBoolean(ConfigConstant.GLOBAL_PRIVILEGE_LDAP,false)){
				info.setPassword (password) ;
			}
			c =delegation.findUserByCondition(info);
		
		if (c == null || c.size() == 0 )
		{
			throw new IException ( "��¼ʧ��,�����û���������" ) ;
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
						throw new IException ( "���û�δ����,�븴�˺��¼!" ) ;
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
				
				//added by mzh_fu �û�֤�����к�
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
	 * ���ģ���¼ʱʹ��
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
		//����У��
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
			throw new IException ( "��¼ʧ��,�����û���������" ) ;
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
						throw new IException ( "���û�δ����,�븴�˺��¼!" ) ;
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
				
				//added by mzh_fu �û�֤�����к�
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
	 * �Խ���ϵͳ��һ���˵���ģ�飩����ʾ˳������
	 * 
	 * @param colModules �������һ���˵�Collection
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
	 * ����ϵͳ��ǰ���õ�ģ������ʽ
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
			System.out.println("��ȡ��ǰ���õ�ģ������ʽʱ����" + exp.getMessage());
			exp.printStackTrace();
		}
		
		return orderedModules;
	}
	/**
	 * ��¼��ѡ����ģ�顢���ֺ󣬳�ʼ��session������ʹ��ldap��Ȩ�޹���
	 * @param strLogin
	 * @param lModuleID
	 * @param lCurrencyID
	 * @throws Exception
	 */
	public void init(long lModuleID,long lCurrencyID) throws Exception
	{
	    // ��ʼ���û���Ϣ
 		m_lCurrencyID = lCurrencyID;
		m_lModuleID = lModuleID ;
		//�Զ���ģ���ʶ add by zcwang 2008-1-10
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
		settlemetSystemDate = Env.getSystemDate(m_lOfficeID, m_lCurrencyID); //�õ����㿪������
		sChineseSystemDate = DataFormat.getChineseDateString(Env.getSystemDate(m_lOfficeID, m_lCurrencyID));		
		
		//
		OfficeInfo officeInfo = findOfficeByID ( m_lOfficeID ) ;
		if( officeInfo != null )
		{
			m_strOfficeName = officeInfo.m_strName ;
			m_strOfficeNo = officeInfo.m_strCode ;
		}	   		
		// ��ʼ���û�Ȩ����Ϣ
		PrivilegeDelegation privilegeDelegation = new PrivilegeDelegation();
		GroupDelegation groupDelegation = new GroupDelegation();
		
		if ( m_allPrivilege == null )
		{
		   // ȡ��ȫ��Ȩ��,������ģ���Ȩ��

		    m_allPrivilege = groupDelegation.findPrivilegesbyModuleId(lModuleID,m_lOfficeID);
 		}
		// ����û���Ȩ�޼���
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
					//��ʱ���Ӵ˳�ʼ�����ҵ�null pointԭ���ɾ��
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
	 * ����ƽ̨�� ����id�ͱ���id��ҳ�洫��
	 * @param lModuleID
	 * @param lCurrencyID
	 * @param lOfficeId
	 * @throws Exception
	 */
	public void initForSDC(long lModuleID,long lCurrencyID,long lOfficeId) throws Exception
	{
	    // ��ʼ���û���Ϣ
 		m_lCurrencyID = lCurrencyID;
		m_lModuleID = lModuleID ;
		m_lOfficeID = lOfficeId;
		//�Զ���ģ���ʶ add by zcwang 2008-1-10
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
		settlemetSystemDate = Env.getSystemDate(m_lOfficeID, m_lCurrencyID); //�õ����㿪������
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
	 * ��¼��ѡ����ģ�顢���ֺ󣬳�ʼ��session������ģ��ʱʹ�á�
	 * @param strLogin
	 * @param lModuleID
	 * @param lCurrencyID
	 * @throws Exception
	 */
	public void initApart(long lModuleID,long lCurrencyID) throws Exception
	{
	    // ��ʼ���û���Ϣ
 		m_lCurrencyID = lCurrencyID;
		m_lModuleID = lModuleID ;
		//�Զ���ģ���ʶ add by zcwang 2008-1-10
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
		settlemetSystemDate = Env.getSystemDate(m_lOfficeID, m_lCurrencyID); //�õ����㿪������
		sChineseSystemDate = DataFormat.getChineseDateString(Env.getSystemDate(m_lOfficeID, m_lCurrencyID));		
		
		//
		OfficeInfo officeInfo = findOfficeByID ( m_lOfficeID ) ;
		if( officeInfo != null )
		{
			m_strOfficeName = officeInfo.m_strName ;
			m_strOfficeNo = officeInfo.m_strCode ;
		}	   		
		// ��ʼ���û�Ȩ����Ϣ
		PrivilegeDelegation privilegeDelegation = new PrivilegeDelegation();
		GroupDelegation groupDelegation = new GroupDelegation();
		
		if ( m_allPrivilege == null )
		{
		   // ȡ��ȫ��Ȩ��,������ģ���Ȩ��

		    m_allPrivilege = groupDelegation.findPrivilegesbyModuleId(lModuleID,m_lOfficeID);
 		}
		// ����û���Ȩ�޼���
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
					//��ʱ���Ӵ˳�ʼ�����ҵ�null pointԭ���ɾ��
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
	 * ��ʼ��session, ʹ��ldap����Ȩ��
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
				throw new IException ( "�û������벻ƥ�䣬�����µ�¼" ) ;
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
					//��ʱ���Ӵ˳�ʼ�����ҵ�null pointԭ���ɾ��
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
	 * ����ģ���õģ�����ģ�鱣�������־�������ǣ�����ejb�н�loginfo���浽�ڴ��У�����cҳ����ô˷��������ڴ��е���־���浽���ݿ���
	 */
	synchronized public void addOperationLogger( HttpServletRequest request ) throws IException
	{
        //�Ƿ���Ҫ��ҵ�������־
		boolean bool = Config.getBoolean(Config.GLOBAL_BTNLEVERLOG_ISVALID, false);		
		if(bool)
		{			
			LoggerResults vResult = LoggerResults.getInstance(); 
			// �õ���ǰ����δ�������־��¼
			Vector v = vResult.getResult();
			if(v != null && v.size() > 0)
			{
				LoggerBtnLevelInfo logInfo = null;
				while(v.size() > 0)
				{
					logInfo = (LoggerBtnLevelInfo)v.get(0);
//					�жϲ��������Ƿ���Ҫ��¼��־
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
					//������־�Ժ�Ӽ������Ƴ� ������û�б�����־�������ڴ����Ƴ�����Ȼ�������ѭ������
				    v.remove(0);
				}
			}
			
			
		}
	}
	
	public void addOperationLogger( HttpServletRequest request , LoggerBtnLevelInfo logInfo) throws IException
	{
        //�Ƿ���Ҫ��ҵ�������־
		boolean bool = Config.getBoolean(Config.GLOBAL_BTNLEVERLOG_ISVALID, false);
		if(bool)
		{			
			//�жϲ��������Ƿ���Ҫ��¼��־
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
	 * �˴����뷽��˵���� �������ڣ�(2002-2-7 19:10:57)
	 * 
	 * @return boolean
	 * @param strCode
	 *            java.lang.String
	 */
	public boolean hasRight ( HttpServletRequest request ) throws IException
	{
		
		addOperationLogger(request);  //��ÿһ�ν�ҳ��֮ǰ��session�е���־��Ϣ����
		boolean bReturn = false ;
		String strURL = request.getRequestURI();
		String strFromQuery = (String)request.getParameter("FromQuery");

		try
		{
			//У�������Ƿ���ϵͳֱ���ύ�������Ǳ���Ϊ�޸ĺ������
			RequestAlteredValidator.validate(request);
			
			String strIndex = "" ;
			int nOffset = 0 ;
			//�����Զ���˵�
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
			//���ӹ��˷�ֹ�˵������޹�ҵ����־
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
			//����Ƿ����ڹػ�
			//Log.print("strFromQuery="+strFromQuery);
			//�Ƿ��ǽ��ף�����ǽ��ף���ػ������ٴ���
			boolean blnIsTransaction = strURL.indexOf("settlement/tran")>0 
										|| strURL.indexOf("settlement/obinstruction")>0 //����ָ�����
										|| strURL.indexOf("settlement/other/view/v101.jsp")>0 //�ֶ���Ϣ 
										|| strURL.indexOf("settlement/mergevoucher/view/v001.jsp")>0 //�ϲ�ƾ֤
										|| strURL.indexOf("settlement/accountsystem")>0; //��ҵ�ʽ��ϲ����»�
			if (blnIsTransaction && !(strFromQuery != null && strFromQuery.equals ("yes")))
			{
				//EndDayProcess prcess = new EndDayProcess();
				if (EndDayProcess.getSystemStatusID ( m_lOfficeID , m_lCurrencyID ) != Constant.SystemStatus.OPEN)
				{
					throw new IException ( "ϵͳ�Ѿ��ػ������ܼ���ҵ����" ) ;
				}
				if (EndDayProcess.getDealStatusID ( m_lOfficeID , m_lCurrencyID ) == Constant.ShutDownStatus.DOING)
				{
					throw new IException ( "ϵͳ���ڹػ������ܼ���ҵ����" ) ;
				}
				if (!DataFormat.getDateString(Env.getSystemDate(m_lOfficeID, m_lCurrencyID)).equals(DataFormat.getDateString(settlemetSystemDate)))
				{
					throw new IException ( "ϵͳ���������Ѿ����£������µ�¼��" ) ;
				}
			}
			// �ж��Ƿ���Ȩ��
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
					log4j.info ( " д��ϵͳ�˵���־: " + strPageURL ) ;
					addLogger ( addlogpvg , strPageURL ,hostInfo) ;
				}
				else
				{
					log4j.info ( " ��URL����ϵͳ�˵�URL: " + strPageURL ) ;
				}
				if (bMenuJsp == false)
				{
					bReturn = true ;
				}
			}
			// ��ȡ request parameter�� ����ת�롣
			//�����ж�����Ƿ���Ҫ����ת�룬add by Forest,20040720
			String strToChinese = (String)request.getParameter("IsNeedConvertToChinese");
			int iToChinese = 1;//Ĭ����Ҫ����ת��
			
			//���浱ǰ������url �� request.parameter()�� ��������ܵ�*c.jsp ��������ʾ��Ϣ��
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
				//Forestע�ͣ���Ϊ̫����
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
	 * �˴����뷽��˵���� �������ڣ�(2002-7-10 11:26:14)
	 * 
	 * @return com.iss.cpf.remoteresult.OfficeInfo
	 * @param lOfficeID
	 *            long
	 * @exception java.lang.Exception
	 *                �쳣˵����
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
		
		
		//m_strMenu �Ľ���
		String[] arrDepart = null; //ȡ���û�Ȩ��;
		
		String[] arrName = null;
		String[] arrNo = null;
		String[] arrLevel = null;
		String[] arrUrl = null;
		String[] arrModule = null;
		ArrayList sParent = null;
		String[] tmpArrNo = null; //�滻��ı��
		String[] newArrNo = null; //������ɺ�ı��

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
				
				
				//�滻��ŵ�"-"��Ϊ�»���
				for(i=0; i<iLength; i++){
					tmpArrNo[i] = arrNo[i].replace('-','_');
					while(tmpArrNo[i].indexOf("-")>0){
					 	tmpArrNo[i] = tmpArrNo[i].replace('-','_');
					}
				}
				//�Զ���ģ�� add by zcwang 2008-1-11
				if(lModuleID==Constant.CustomModule.CUSTOMMODULE)
				{
					for(i=0; i<iLength; i++){
						//���ݼ���ȡ���ϼ�Ȩ�ޱ��
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
							//�������ӵ�ַ
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
					//���ݼ���ȡ���ϼ�Ȩ�ޱ��
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
						//�������ӵ�ַ
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
				//m_strMenu �Ľ�������
					
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
		
		//���
		StringBuffer stringOut = new StringBuffer();
		stringOut.append("<div id=\"qm0\" class=\"qmmc\">");
		this.parseMenuDOM(stringOut, mainDiv);
		stringOut.append("<span class=\"qmclear\">&nbsp;</span></div>");
		
		//System.out.println(stringOut.toString());
		
		
		System.out.println("׼���˵� ------------------------");
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
	 * ���Ȩ�ޣ������Ż����ϵͳ
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
	 * ��ʾ�û�Ȩ��
	 * <p>
	 * <ol>
	 * <b>��ʾ�û�Ȩ�� </b>
	 * <ul>
	 * <li>�������ݿ�userinfo
	 * <li>����String
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
				System.out.println ( "Session��PageLoader���Դ��з�PageLoader����key:" + key + "��" ) ;
				this.hmPageLoader.remove ( key ) ;
			}
		}
		return result ;
	}
	/**
	 * ����PageLoader����ͬʱ����keyֵ
	 * ע��keyֵʹ�ô��˶����hashCode�����Session���Ѿ���ͬ��key��PageLoader�����򲻱���ͬʱ����null
	 * 
	 * @param pageLoader
	 * @return String ���ص�key
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
	 * �ڼ����б����ѯ��������
	 * 
	 * @param key
	 *            keyֵ�������������󣬲���Ϊ������null
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
	 * �ڼ����б���ͳ�ƽ������
	 * 
	 * @param key
	 *            keyֵ�������������󣬲���Ϊ������null
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
	 * �������PageLoader�Ķ��󣬰�����PageLoader��ص���������ͳ�ƽ������
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
			Log.print ( "�� " + i + " ��Ȩ�ޱ��� : " + pvgNo ) ;
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
	    		    strStatus.append("��ǰ������"+Env.getOfficeName(m_lOfficeID)+ "  ");
	    		    if(m_lModuleID==Constant.ModuleType.SETTLEMENT
	    					||m_lModuleID==Constant.ModuleType.LOAN||m_lModuleID==Constant.ModuleType.EVOUCHER
	    					||m_lModuleID==Constant.ModuleType.AUDIT||m_lModuleID==Constant.ModuleType.REPORT||m_lModuleID==Constant.ModuleType.BANKPORTAL) //jzw 2010-04-15 ��ѡ���ֹ����л��ұ�����ʾ
	        		strStatus.append(Constant.CurrencyType.getName(m_lCurrencyID) + "  ");
	        		strStatus.append(SETTConstant.SystemStatus.getName(process.getSystemStatusID(m_lOfficeID, m_lCurrencyID)) + "<br>");
	        		strStatus.append("��&nbsp;&nbsp;&nbsp;&nbsp;����" + m_strUserName + "<br/>����ʱ�䣺" + DataFormat.getChineseDateString(Env.getSystemDate(m_lOfficeID, m_lCurrencyID)));	        		
	        		if(dLastLoginTime != null && !dLastLoginTime.equals("")){
	             		strStatus.append("<br/>���ϴε�¼ʱ�䣺"+ DataFormat.getChineseTimeString(dLastLoginTime));
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
			if(Config.getBoolean(ConfigConstant.SETTLEMENT_CERT_SUBJECTCOMMONNAME_ISNEEDED, true))//CNУ��Ĭ���Ǳ����
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
