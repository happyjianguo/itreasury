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
	//��֤��
	String strCheckcode = "";
	String realCheckcode = (String)session.getAttribute("rand");
	//�����ļ���ȡ�Ƿ���Ҫ��֤��
	boolean isRequireCheckcode = Config.getBoolean(ConfigConstant.EBANK_ISREQUIRECHECKCODE, true);
	
	// �û���¼���
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
		
		//��֤��У��
		if(isRequireCheckcode && (realCheckcode==null || !realCheckcode.equals(strCheckcode)))
		{
			throw new IException("���������֤������") ;
		}
		
		sessionMng.init(strUserName, strPassword,request.getRemoteHost());
		if(sessionMng.isLogin() == false)
		{
			throw new IException("��¼ʧ�ܣ������û���������");
		}
	}
		
    //д��¼��־
    LoginLogBean loginLogBean = new LoginLogBean();
    loginLogBean.addLoginLog(sessionMng,Constant.EBANK_USER,request.getRemoteHost(),Constant.SUCCESSFUL,"�û���¼�ɹ�");
    
	//ʹ������ǩ��
	String strConfigTroy = Config.getProperty(ConfigConstant.GLOBAL_TROY_NAME);
	
	Object objCertInfo = session.getAttribute(SignatureConstant.Session.CERTIFICATEINFO);
	boolean blnSubjectCommonNameIsneeded = Config.getBoolean(ConfigConstant.EBANK_CERT_SUBJECTCOMMONNAME_ISNEEDED,true);
	boolean blnCertSerialNumberIsneeded = Config.getBoolean(ConfigConstant.EBANK_CERT_SERIALNUMBER_ISNEEDED,false);
	
	if(strConfigTroy.equals(Constant.GlobalTroyName.ITrus)){
		//��HTTPS��ʽ��¼���û������û�֤���У�飨�������ţ�
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
				/**�Ӱ�ȫ�Կ��ǣ�ע����Щ��־�������Ҫ�������ٷſ�
				Log.print("�Ƿ���ҪУ��֤������" + blnSubjectCommonNameIsneeded);
				Log.print("�Ƿ���ҪУ��֤������кţ�" + blnCertSerialNumberIsneeded);
				Log.print("��ǰUkey��֤����Ϊ��" + objCertSubjectCommonName);
				Log.print("��ǰUkey�����к�Ϊ��" + objCertSerialNumber);
				Log.print("��ǰ�û���֤����Ϊ��" + sessionMng.m_strCertCN);
				Log.print("��ǰ�û���֤�����к�Ϊ��" + sessionMng.m_strCertSerialNumber);
				*/	
				
				throw new IException("�û��޴�֤���ʹ��Ȩ�ޣ���ȷ�ϸ��û��Ƿ���ȷ���˴�֤�飡");
			}
		}
	}else if(strConfigTroy.equals(Constant.GlobalTroyName.NetSign)){
		//�û�֤���У�飨�Ű����ͣ� jzw ��ʱû������������Ϣ�Ѿ�ͨ��LoginAuthentication���浽session�С�
		
	}	
	
	if(strConfigTroy.equals(Constant.GlobalTroyName.NetSign)){
		//�Ű����� jzw 2010-07-26
		strNextPageURL = "/Init_signature_NetSign.jsp?loginno=" + sessionMng.m_strUserName;									
	}else if(null==objCertInfo && strConfigTroy.equals(Constant.GlobalTroyName.ITrus)){
		//��������
		strNextPageURL = "/Init_signature_iTrus.jsp";			 
	}else if (null==objCertInfo && strConfigTroy.equalsIgnoreCase(Constant.GlobalTroyName.CFCA))
	{
	    //CFCA֤����֤
		strNextPageURL = "Init_CFCASignature.jsp?loginno="+strUserName+"&Password="+strPassword;
	}
	else {
		strNextPageURL = "/PasswordValidate.jsp";
	}

	//����ҳ��ַ�ʱ��Ҫ�õ���ʵ��
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
	sessionMng.getActionMessages().addMessage("��¼ʧ��");
	response.sendRedirect(strContext + "/Index.jsp");
}
%>