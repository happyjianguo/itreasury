/*
 * SessionMng.java
 
 * Created on 2001年12月6日, 下午2:51
 */
package com.iss.itreasury.fcinterface.bankportal.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import com.iss.itreasury.fcinterface.bankportal.accesslog.bizlogic.LoggerBiz;
import com.iss.itreasury.fcinterface.bankportal.accesslog.dataentity.HostInfo;
import com.iss.itreasury.fcinterface.bankportal.accesslog.dataentity.LoggerInfo;
import com.iss.itreasury.fcinterface.bankportal.privilegemgt.OperationConfig;
import com.iss.itreasury.fcinterface.bankportal.privilegemgt.bizlogic.DataPrivilegeBiz;
import com.iss.itreasury.fcinterface.bankportal.sysframe.exp.business.BusinessException;
import com.iss.itreasury.fcinterface.bankportal.usermgt.bizlogic.UserBiz;
import com.iss.itreasury.fcinterface.bankportal.usermgt.dataentity.UserPrivilegeInfo;
import com.iss.system.dao.PageLoader;
import com.iss.itreasury.fcinterface.bankportal.system.AccessLog;
import com.iss.itreasury.fcinterface.bankportal.system.LoginInfo;

/**
 * 会话对象<br>
 * 维护客户端的整个会话
 * @author mxzhou
 */
public class SessionMng implements java.io.Serializable
{
	/** 日志对象 */
	//private Logger log = null;
	//private static Logger log = new Logger(SessionMng.class);
	
	/**用户与权限信息*/
	public long					userID				= -1;				//用户ID
	public String				userNo				= "";
	public String				userName			= "";
	public long                 officeID            = -1;               //办事处ID
	public String               officeName          = null;               //办事处名称
	public long                 groupID       = -1;               //资金监控用户组信息
	public long                 operationType       = -1;               //请求操作类型
	public String				menu				= "";
	public String				certIssuer			= "";
	public String				signature			= "";
	private UserPrivilegeInfo[]			userPrivilege		= null;
	private HashMap	userPvgMap = new HashMap();
	
	/**会话信息*/
	public String				sessionID			= "";
	private long				sessionTransType	= -1;
	private String				menuURL				= null;
	private long				actionID			= -1;				//标识提交的请求
	private ActionMessages		actionMessages		= new ActionMessages();

	/**需要由会话保存的对象*/
	private HashMap				pageLoader			= null;				//分页控制信息
	private HashMap				queryCondition		= null;				//查询条件信息
	private HashMap				sumResult			= null;				//统计结果信息
	
	/** Creates new SessionMng */
	public SessionMng()
	{
		//this.log = new Logger(SessionMng.class);
		
		//以下代码为未使用权限访问控制时，进行的测试控制，正常情况下应注释掉！
//		this.userID = 1;
//		this.userNo = "test1";
//		this.userName = "测试用户";
	}

	public boolean isLogin()
	{
//		System.out.println("####login check, user id is :["+userID+"]");
		if (userID > 0)
			return true;
		else
			return false;
	}
	
	public void logout()
	{
		//注销用户信息
		this.userID = -1;
		this.userNo = "";
		this.userName = "";
		this.menu = "";
		this.certIssuer = "";
		this.signature = "";
		//清空用户权限
		this.userPrivilege = null;
		this.userPvgMap.clear();
	}
	
	public void login(String userNo, String userPassword) throws BusinessException
	{
		long id = -1;
		UserBiz userBiz = new UserBiz();
		id = userBiz.login(userNo, userPassword);
		
		this.userID = id;
		this.userNo = userNo;
		this.userName = userBiz.getUserName(userNo);
		this.officeID = userBiz.getUserOfficeID(userNo);
		this.officeName = userBiz.getUserOfficeName(officeID);
		if(this.officeName==null)
		{
			this.officeName = Env.getEnvConfigItem(Env.G_CLIENT_NAME);
		}

		//数据权限授权
		if(Env.getEnvConfigItem(Env.isNeedAutoAuthorize).equals("true"))
		{
			DataPrivilegeBiz dataPrivilegeBiz = new DataPrivilegeBiz();
			groupID = dataPrivilegeBiz.autoAuthorize(userID,officeID);
			dataPrivilegeBiz = null;
		}
		//记录登录日志	
		Boolean isNeedUserLog = new Boolean(Env.getEnvConfigItem(Env.isNeedUserLog));
		if(isNeedUserLog.booleanValue())
		{
			Connection con = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			try
			{
				con = Database.getConnection();				
				String strSQL = " insert into BS_LOGININFO(n_id,n_userid,s_userno,dt_logintime) values ((select nvl(max(n_id)+1,1) n_id from bs_logininfo),?,?,sysdate)";
				int nIndex = 1;
				ps = con.prepareStatement(strSQL);
				ps.setLong(nIndex++, this.userID);
				ps.setString(nIndex++, this.userNo);
				ps.executeUpdate();
				ps.close();
				ps = null;
				con.close();
				con = null;
			}
			catch (Exception e)  
			{
				e.printStackTrace();
				throw new BusinessException("新增登录日志失败："+e.getMessage());
			}
			finally
			{
				try
				{
					if (rs != null)
						rs.close();
					if (ps != null)
						ps.close();
					if (con != null)
						con.close();
				}
				catch (Exception ex)
				{
					ex.printStackTrace();
				}
			}
		}
//		中油记录日志	
		Boolean isCpfLog = new Boolean(Env.getEnvConfigItem(Env.ISCPFLOG));
		if(isCpfLog.booleanValue())
		{
			try
			{
				AccessLog al = new AccessLog();
				LoginInfo li = new LoginInfo();
				li.setOffice(0);
				li.setUserID(userID);
				li.setUserName(userName);
				li.setAccessType(1);
				li.setModule("资金监控");
				al.addLoginInfo(li);
			}
			catch(Exception e)
			{
				e.printStackTrace(); 
				throw new BusinessException("新增中油日志失败："+e.getMessage());
			}
		}
		
//		System.out.println("####login success, user id is :["+userID+"]");
	}
	
	public void login(String userNo) throws BusinessException
	{
		long id = -1;
		UserBiz userBiz = new UserBiz();
		id = userBiz.login(userNo);
		
		this.userID = id;
		this.userNo = userNo;
		this.userName = userBiz.getUserName(userNo);
		this.officeID = userBiz.getUserOfficeID(userNo);
		this.officeName = userBiz.getUserOfficeName(officeID);
		if(this.officeName==null)
		{
			this.officeName = Env.getEnvConfigItem(Env.G_CLIENT_NAME);
		}
		//数据权限授权
		if(Env.getEnvConfigItem(Env.isNeedAutoAuthorize).equals("true"))
		{
			DataPrivilegeBiz dataPrivilegeBiz = new DataPrivilegeBiz();
			groupID = dataPrivilegeBiz.autoAuthorize(userID,officeID);
			dataPrivilegeBiz = null;
		}
		//记录登录日志	
		Boolean isNeedUserLog = new Boolean(Env.getEnvConfigItem(Env.isNeedUserLog));
		if(isNeedUserLog.booleanValue())
		{
			Connection con = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			try
			{
				con = Database.getConnection();				
				String strSQL = " insert into BS_LOGININFO(n_id,n_userid,s_userno,dt_logintime) values ((select nvl(max(n_id)+1,1) n_id from bs_logininfo),?,?,sysdate)";
				int nIndex = 1;
				ps = con.prepareStatement(strSQL);
				ps.setLong(nIndex++, this.userID);
				ps.setString(nIndex++, this.userNo);
				ps.executeUpdate();
				ps.close();
				ps = null;
				con.close();
				con = null;
			}
			catch (Exception e)  
			{
				e.printStackTrace();
				throw new BusinessException("新增登录日志失败："+e.getMessage());
			}
			finally
			{
				try
				{
					if (rs != null)
						rs.close();
					if (ps != null)
						ps.close();
					if (con != null)
						con.close();
				}
				catch (Exception ex)
				{
					ex.printStackTrace();
				}
			}
		}
//		中油记录日志	
		Boolean isCpfLog = new Boolean(Env.getEnvConfigItem(Env.ISCPFLOG));
		if(isCpfLog.booleanValue())
		{
			try
			{
				AccessLog al = new AccessLog();
				LoginInfo li = new LoginInfo();
				li.setOffice(0);
				li.setUserID(userID);
				li.setUserName(userName);
				li.setAccessType(1);
				li.setModule("资金监控");
				al.addLoginInfo(li);
			}
			catch(Exception e)
			{
				e.printStackTrace(); 
				throw new BusinessException("新增中油日志失败："+e.getMessage());
			}
		}
	}
	/**
	 * 登录后，选择了模块、币种后，初始化session，不再使用ldap作权限管理。
	 * @param strLogin
	 * @param lModuleID
	 * @param lCurrencyID
	 * @throws Exception
	 */
	public void init() throws BusinessException
	{
		// 初始化用户权限信息
		UserBiz userBiz = new UserBiz();
		this.userPrivilege = userBiz.getUserPrivilege(this.userNo);
		if(userPrivilege!=null)
		{
		    for(int i=0;i<userPrivilege.length;i++)
		    {
		        this.userPvgMap.put(userPrivilege[i].getCode(), userPrivilege[i].getName());
		    }
		}

 	 	prepareMenu();
	}

	/**
	 *  
	 * @return boolean
	 * @param strCode
	 *            java.lang.String
	 */
	public boolean hasRight(HttpServletRequest request) throws BusinessException
	{
//	    return true;	//由于当前权限管理没涉及到每个页面，当每个功能的页面跳转时，会出现限制，所以当前对合法用户的页面跳转暂时不作限制
		boolean bReturn = false;
		String strURL = request.getRequestURI();
		String strFromQuery = (String) request.getParameter("FromQuery");
		String strModule = "";
		//
		try
		{
			String strIndex = "";
			int nOffset = 0;

			strIndex = Env.getEnvConfigItem(Env.G_BANKPORTAL_URL);
			nOffset = strIndex.length();
			
			String strPageURL = strURL.substring(strURL.indexOf(strIndex) + nOffset , strURL.length());
			HostInfo hostInfo = new HostInfo();
			hostInfo.setRemoteHost(request.getRemoteHost());
			hostInfo.setRemoteIP(request.getRemoteAddr());
			// 判断是否有权限
			if (this.userPrivilege != null && this.userPrivilege.length > 0)
			{
				for(int i = 0; i < this.userPrivilege.length; i++)
				{
				    UserPrivilegeInfo userPvg = this.userPrivilege[i];
					if (userPvg.getPrivilegeUrl() == null || userPvg.getPrivilegeUrl().trim().length() <= 0)
					{
						continue;
					}
					if (strPageURL.indexOf(userPvg.getPrivilegeUrl()) >= 0)
					{
						//操作类型更新
						OperationConfig.Operation operation = OperationConfig.getOperationByPageURL(strPageURL);
						if(operation != null)
						{
							operationType = operation.getType();
						}
						else
						{
                            operationType = -1;
						}
						Boolean isNeedAccessLog = new Boolean(Env.getEnvConfigItem(Env.ISNEEDACCESSLOG));
						if(isNeedAccessLog.booleanValue())
						{
							addLogger(userPvg,strPageURL,hostInfo);
						}
						bReturn = true;
						break;
					}
				}
			}
            //由于当前权限管理没涉及到每个页面，当每个功能的页面跳转时，会出现限制，所以当前对合法用户的页面跳转暂时不作限制
            bReturn = true;
		}
		catch (Exception e)
		{
			System.out.println("校验页面访问权限时出现异常，出错原因："+e.toString());
			e.printStackTrace();
			bReturn = false;
		}
		return bReturn;
	}

	/**
	 * @param userPvg
	 * @param strPageURL
	 * @param hostInfo
	 */
	private void addLogger(UserPrivilegeInfo userPvg, String strPageURL, HostInfo hostInfo) {

		LoggerInfo loggerInfo = new LoggerInfo();

		try{
			loggerInfo.setCurrencyID(1);
			loggerInfo.setOfficeID(officeID);
			loggerInfo.setModuleID(userPvg.getSystemModuleID()); 
			Boolean isAccessLoggerShare = new Boolean(Env.getEnvConfigItem(Env.ISACCESSLOGSHARE));
			if(isAccessLoggerShare.booleanValue())
			{ 
			    loggerInfo.setUserID(new UserBiz().getImplIDByUserNo(this.userNo));
			}else
			{
			    loggerInfo.setUserID(this.userID);
			}
			loggerInfo.setUserName(this.userName);
			loggerInfo.setFunctionPointCode(userPvg.getCode());
			loggerInfo.setAccessTime(Env.getSystemDateTime());
			
			loggerInfo.setFunctionPointCode ( userPvg.getCode()) ;
			System.out.println("jsp is: " + userPvg.getPrivilegeUrl() + " url is:" + strPageURL);
	
			int fromIndex = 3 ;
			int offset = 0;
			int length = userPvg.getCode().length ( ) ;
			String pvgNo = "" ;
			String funcationPointDescription = "" ;
			for (int i = 2; ; i++)
			{
			    offset = userPvg.getCode().indexOf ( "-" , fromIndex) ;
			    if(offset<0) offset=length;
				pvgNo = userPvg.getCode().substring ( 0 , offset ) ;
				System.out.println ( "第 " + i + " 级权限编码 : " + pvgNo ) ;
				funcationPointDescription = funcationPointDescription + userPvgMap.get ( pvgNo ) + "-" ;
				fromIndex = offset + 1;
				if(fromIndex>length) break;
				i++;
			}
			
			loggerInfo.setFunctionPointDescription ( 
			        funcationPointDescription.substring ( 0 , funcationPointDescription.length () - 1 ) );
		
			loggerInfo.setRemoteHost( hostInfo.getRemoteHost() );
			loggerInfo.setRemoteIP( hostInfo.getRemoteIP() );
		
			LoggerBiz loggerBiz = new LoggerBiz ( ) ;
			loggerBiz.add( loggerInfo );
		}catch(Exception ex){
		    ex.printStackTrace();
		    System.out.print("添加访问日志信息失败："+ex.getMessage());
		}
	}

	public void prepareMenu()
	{
		//this.menu = "数据维护;;数据导入;;数据导出;;数据补录;;账户管理;;账户设置;;账户审核;;账户销户;;账户查询;;账户基础信息查询;;账户归集关系查询;;余额查询;;当前余额查询;;历史余额查询;;交易明细查询;;当日交易查询;;历史交易查询;;对账单打印;;统计报表;;账户每日余额表;;账户日均余额表;;账户日均余额变动表;;账户收支明细表;;账户收支统计表;;监控;;提醒方式设置;;余额监控;;交易量监控;;可疑交易监控;;设置;;客户设置;;科目设置;;有效币种设置;;银行设置;;国家设置;;账户属性一设置;;账户属性二设置;;账户属性三设置::1-100;;1-100-100;;1-100-200;;1-100-300;;1-200;;1-200-100;;1-200-200;;1-200-300;;1-300;;1-300-100;;1-300-200;;1-300-300;;1-300-300-100;;1-300-300-200;;1-300-400;;1-300-400-100;;1-300-400-200;;1-300-500;;1-400;;1-400-100;;1-400-200;;1-400-300;;1-400-400;;1-400-500;;1-500;;1-500-100;;1-500-200;;1-500-300;;1-500-400;;1-600;;1-600-100;;1-600-200;;1-600-300;;1-600-400;;1-600-500;;1-600-600;;1-600-700;;1-600-800::1;;2;;2;;2;;1;;2;;2;;2;;1;;2;;2;;3;;4;;4;;3;;4;;4;;3;;1;;2;;2;;2;;2;;2;;1;;2;;2;;2;;2;;1;;2;;2;;2;;2;;2;;2;;2;;2::;;;;;;;;;;/account/view/v001.jsp;;/account/view/v010.jsp;;/account/view/v020.jsp;;;;/account/view/v070.jsp;;/account/view/v080.jsp;;;;/account/view/v030.jsp;;/account/view/v040.jsp;;;;/account/view/v050.jsp;;/account/view/v060.jsp;;/account/view/v090.jsp;;;;/query/view/v001.jsp;;/query/view/v010.jsp;;/query/view/v020.jsp;;/query/view/v030.jsp;;/query/view/v040.jsp;;;;;;;;;;;;;;/setting/view/v002.jsp;;/setting/view/v005.jsp;;/setting/view/v001.jsp;;/setting/view/v008.jsp;;/setting/view/v011.jsp;;/setting/view/v014.jsp;;/setting/view/v017.jsp;;/setting/view/v020.jsp;;";
		try
		{
			String strPrivCode = "";
			String strPrivName = "";
			String strPrivLevel = "";
			String strPrivUrl = "";
			if (this.userPrivilege == null || this.userPrivilege.length <= 0)
			{
				this.menu = "";
			} 
			else
			{
				UserPrivilegeInfo pi = null;
				long lLevelTmp = 0;
				String strCodeTmp = "";
				String strNameTmp = "";
				String strUrlTmp = "";
				for(int i = 0; i < this.userPrivilege.length; i++)
				{
					pi = this.userPrivilege[i];
					strCodeTmp = pi.getCode();
					strNameTmp = pi.getName();
					strUrlTmp = pi.getPrivilegeUrl();
					//从strCodeTmp中获取lLevelTmp
					int pos = strCodeTmp.indexOf("-");
					while(pos >= 0)
					{
						lLevelTmp++;
						pos = strCodeTmp.indexOf("-", pos+1);
					}
					strPrivCode = strPrivCode + strCodeTmp + ";;";
					strPrivName = strPrivName + strNameTmp + ";;";
					strPrivLevel = strPrivLevel + String.valueOf(lLevelTmp) + ";;";
					strPrivUrl = strPrivUrl + strUrlTmp + ";;";
				}
				strPrivName = strPrivName.substring(0 , strPrivName.length() - 2);
				strPrivCode = strPrivCode.substring(0 , strPrivCode.length() - 2);
				strPrivLevel = strPrivLevel.substring(0 , strPrivLevel.length() - 2);
				strPrivUrl = strPrivUrl.substring(0 , strPrivUrl.length() - 2);
				this.menu = strPrivName + "::" + strPrivCode + "::" + strPrivLevel + "::" + strPrivUrl;
//				System.out.println("menu:[" + this.menu + "]");
			}
		}
		catch (Exception e)
		{
			System.out.println("构造页面菜单信息时出现异常，出错原因："+e.toString());
		}
	}
	/*
	 */
	public long getTransactionType()
	{
		return sessionTransType;
	}
	/**
	 * 检查权限，用于优化后的系统
	 * 
	 * @param request
	 * @return boolean
	 */
	public boolean checkRight(HttpServletRequest request)
	{
		boolean b = true;
		return b;
	}

	/**
	 * Returns the actionID.
	 * 
	 * @return long
	 */
	public long getActionID()
	{
		return actionID;
	}
	/**
	 * Sets the actionID.
	 * 
	 * @param actionID
	 *            The actionID to set
	 */
	public void setActionID(long actionID)
	{
		this.actionID = actionID;
	}
	/**
	 * Returns the actionMessages.
	 * 
	 * @return ActionMessages
	 */
	public ActionMessages getActionMessages()
	{
		return actionMessages;
	}
	/**
	 * Returns the login.
	 * 
	 * @return String
	 */
	public String getUserNo()
	{
		return userNo;
	}
	/**
	 * Sets the login.
	 * 
	 * @param login
	 *            The login to set
	 */
	public void setUserNo(String loginNo)
	{
		this.userNo = loginNo;
	}
	
	/**
	 * @return Returns the pageLoader.
	 */
	public PageLoader getPageLoader(String key)
	{
		PageLoader result = null;
		if (key != null && key.trim().length() > 0 && this.pageLoader != null)
		{
			try
			{
				result = (PageLoader) this.pageLoader.get(key);
			} catch (ClassCastException e)
			{
				System.out.println("Session中PageLoader属性存有非PageLoader对象（key:" + key + "）");
				this.pageLoader.remove(key);
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
			if (this.pageLoader == null)
			{
				this.pageLoader = new HashMap(6);
			}
			Random random1 = new Random();
			Random random2 = new Random(random1.nextLong());
			key = String.valueOf(random2.nextLong());
			if (!this.pageLoader.containsKey(key))
			{
				this.pageLoader.put(key , pageLoader);
			} else
			{
				key = null;
			}
		}
		return key;
	}
	
	/**
	 * 从集合中获取查询条件对象
	 * @return
	 */
	public Object getQueryCondition(String key)
	{
		Object result = null;
		if (key != null && key.trim().length() > 0 && this.queryCondition != null)
		{
			result = this.queryCondition.get(key);
		}
		return result;
	}
	
	/**
	 * 在集合中保存查询条件对象
	 * @param key 用于索引对象，不能为“”和null
	 * @param queryCondition
	 */
	public void setQueryCondition(String key , Object queryCondition)
	{
		if (key != null && key.trim().length() > 0 && queryCondition != null)
		{
			if (this.queryCondition == null)
			{
				this.queryCondition = new HashMap(6);
			}
			this.queryCondition.put(key , queryCondition);
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
		if (key != null && key.trim().length() > 0 )
		{
			if (this.sumResult == null)
			{
				this.sumResult = new HashMap(6);
			}
			this.sumResult.put(key , sumResult);
		}
	}
	/**
	 * 清除所有PageLoader的对象，包括和PageLoader相关的条件对象，统计结果对象
	 */
	public void clearPageLoader()
	{
		if (this.pageLoader != null)
		{
			this.pageLoader.clear();
		}
		if (this.queryCondition != null)
		{
			this.queryCondition.clear();
		}
		if (this.sumResult != null)
		{
			this.sumResult.clear();
		}
	}
    
    public void clearPageLoader(String key)
    {
        if(key==null) return;
        if ( this.pageLoader != null)
        {
            this.pageLoader.remove(key);
        }
        if (this.queryCondition != null) {
            this.queryCondition.remove(key);
        }
        if (this.sumResult != null) {
            this.sumResult.remove(key);
        }
    }
	/**
	 * 清除queryCondition中对应的对象
	 */
	public boolean clearQueryCondition(String key)
	{				
		boolean flag=false;
		if (this.queryCondition != null && this.queryCondition.containsKey(key))
		{			
			this.queryCondition.remove(key);
			flag=true;
		}
		return flag;
	}
}