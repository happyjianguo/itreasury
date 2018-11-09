<%@ page contentType="text/html;charset=gbk"%> 
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ page import="com.iss.itreasury.system.loginlog.bizlogic.*"%>
<%@ page import="com.iss.itreasury.util.*"%>
<%@ page import="com.iss.itreasury.safety.signature.*"%>
<%@ page import="com.iss.itreasury.util.PageControllerInfo"%>
<%@ page import="com.iss.itreasury.util.PageController"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.Map"%>
<%String strContext = request.getContextPath();%>

<%
try
{
	sessionMng.logout();
	String strTmp = null;
	String strUserName = null;
	String strPassword = null;
    long lCurrencyID = -1;
    Map map = new HashMap();
	String strNextPageURL = "";
	//验证码
	String strCheckcode = "";
	String realCheckcode = (String)session.getAttribute("rand");
	//配置文件获取是否需要验证码
	boolean isRequireCheckcode = Config.getBoolean(ConfigConstant.EBANK_ISREQUIRECHECKCODE, true);
	
	// 用户登录检测
	if(sessionMng.isLogin() == false)
	{
		strTmp = request.getParameter("UserName");		
		if( strTmp != null && strTmp.length() > 0 )
		{
		   	strUserName = strTmp;
		}
		strTmp = request.getParameter("Password");		
		if( strTmp != null && strTmp.length() > 0 )
		{
		   	strPassword = strTmp;
		}
		strTmp = request.getParameter("currency");		
		if (strTmp != null && strTmp.length() > 0 )
		{
		    lCurrencyID = Long.parseLong(strTmp);
		}
		Log.print("Username :" + strUserName);
		Log.print("Password :" + strPassword);
		Log.print("Currency :" + lCurrencyID);
		
		strTmp = request.getParameter("checkcode");
		if( strTmp != null && strTmp.length() > 0 )
		{
			strCheckcode = strTmp;
		}
		
		//验证码校验
		if(isRequireCheckcode && (realCheckcode==null || !realCheckcode.equals(strCheckcode)))
		{
			throw new IException("您输入的验证码有误") ;
		}
		
		sessionMng.init(strUserName, strPassword,request.getRemoteHost());
		if(sessionMng.isLogin() == false)
		{
			throw new IException("登录失败，请检查用户名和密码");
		}
	}
		
    //写登录日志
    LoginLogBean loginLogBean = new LoginLogBean();
    loginLogBean.addLoginLog(sessionMng,Constant.EBANK_USER,request.getRemoteHost(),Constant.SUCCESSFUL,"用户登录成功");
    
	//使用数字签名
	String strConfigTroy = Config.getProperty(ConfigConstant.GLOBAL_TROY_NAME);
	
	Object objCertInfo = session.getAttribute(SignatureConstant.Session.CERTIFICATEINFO);
	boolean blnSubjectCommonNameIsneeded = Config.getBoolean(ConfigConstant.EBANK_CERT_SUBJECTCOMMONNAME_ISNEEDED,true);
	boolean blnCertSerialNumberIsneeded = Config.getBoolean(ConfigConstant.EBANK_CERT_SERIALNUMBER_ISNEEDED,false);
	
	if(strConfigTroy.equals(Constant.GlobalTroyName.ITrus)){
		//对HTTPS方式登录的用户进行用户证书绑定校验（天威诚信）
		if(objCertInfo != null){
			Object objCertSubjectCommonName = session.getAttribute(SignatureConstant.Session.CERTIFICATEINFO_COMMONNAME);
			Object objCertSerialNumber = session.getAttribute(SignatureConstant.Session.CERTIFICATEINFO_SERIALNUMBER);
			boolean blnCertBindRight = true;
			
			if(blnSubjectCommonNameIsneeded 
					&& (objCertSubjectCommonName == null 
							|| !objCertSubjectCommonName.toString().equals(sessionMng.m_strCertCN)))
			{
				blnCertBindRight = false;
				
			}else if(!blnSubjectCommonNameIsneeded 
					&& (objCertSubjectCommonName != null 
							&& !objCertSubjectCommonName.toString().equals(sessionMng.m_strCertCN)) ){
				blnCertBindRight = false;
			}
			
			if(blnCertSerialNumberIsneeded 
					&& (objCertSerialNumber == null 
							|| !objCertSerialNumber.toString().equals(sessionMng.m_strCertSerialNumber)))
			{
				blnCertBindRight = false;
				
			}
			/*
			else if(!blnCertSerialNumberIsneeded 
					&& (objCertSerialNumber != null 
							&& !objCertSerialNumber.toString().equals(sessionMng.m_strCertSerialNumber))){
				blnCertBindRight = false;
			}
			*/
			if(!blnCertBindRight)
			{
				/**从安全性考虑，注掉这些日志，如果需要，可以再放开
				Log.print("是否需要校验证书名：" + blnSubjectCommonNameIsneeded);
				Log.print("是否需要校验证书的序列号：" + blnCertSerialNumberIsneeded);
				Log.print("当前Ukey的证书名为：" + objCertSubjectCommonName);
				Log.print("当前Ukey的序列号为：" + objCertSerialNumber);
				Log.print("当前用户的证书名为：" + sessionMng.m_strCertCN);
				Log.print("当前用户的证书序列号为：" + sessionMng.m_strCertSerialNumber);
				*/	
				
				throw new IException("用户无此证书的使用权限，请确认该用户是否正确绑定了此证书！");
			}
		}
	}else if(strConfigTroy.equals(Constant.GlobalTroyName.NetSign)){
		//用户证书绑定校验（信安世纪） jzw 暂时没有做，但是信息已经通过LoginAuthentication保存到session中。
		
	}	
	
	if(strConfigTroy.equals(Constant.GlobalTroyName.NetSign)){
		//信安世纪 jzw 2010-07-26
		strNextPageURL = "/Init_signature_NetSign.jsp?loginno=" + sessionMng.m_strUserName;									
	}else if(null==objCertInfo && strConfigTroy.equals(Constant.GlobalTroyName.ITrus)){
		//天威诚信
		strNextPageURL = "/Init_signature_iTrus.jsp";			 
	}else if (null==objCertInfo && strConfigTroy.equalsIgnoreCase(Constant.GlobalTroyName.CFCA))
	{
	    //CFCA证书认证
		strNextPageURL = "Init_CFCASignature.jsp?loginno="+strUserName+"&Password="+strPassword;
	}
	else {
		strNextPageURL = "/PasswordValidate.jsp";
	}

	//构建页面分发时需要用到的实体
	PageControllerInfo pageControllerInfo = new PageControllerInfo();
	pageControllerInfo.setSessionMng(sessionMng);
	pageControllerInfo.setUrl(strNextPageURL);
	RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
	rd.forward(request,response);
}
catch(IException exp){
	exp.printStackTrace();
	sessionMng.getActionMessages().addMessage(exp.getMessage());
	response.sendRedirect(strContext + "/Index.jsp");
}catch(Exception exp1){
	exp1.printStackTrace();
	sessionMng.getActionMessages().addMessage("登录失败");
	response.sendRedirect(strContext + "/Index.jsp");
}
%>