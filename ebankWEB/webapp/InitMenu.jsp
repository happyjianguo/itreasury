
<%@ page contentType="text/html;charset=gbk" %>
<!-- 引入需要的类,尽量不用.* -->
<%@page import="java.util.Collection,
				com.iss.itreasury.util.Log,
				com.iss.itreasury.util.Constant,
				com.iss.itreasury.util.DataFormat,
				com.iss.itreasury.util.PageCtrlInfo,
				com.iss.itreasury.budget.constitute.dataentity.BudgetPlanInfo,
				com.iss.itreasury.budget.util.BUDGETNameRef,
				com.iss.itreasury.budget.util.BUDGETConstant,
				com.iss.itreasury.budget.util.BUDGETHTML,
				com.iss.itreasury.ebank.util.*,
				com.iss.itreasury.budget.bizdelegation.ConstituteDelegation,
				com.iss.itreasury.system.loginlog.bizlogic.*,
				com.iss.itreasury.ebank.privilege.dao.OB_PrivilegeDAO,
				java.util.Vector,
				com.iss.itreasury.util.*,
				com.iss.itreasury.safety.signature.*,
				java.security.cert.X509Certificate"
%>
<%@ page import="com.iss.itreasury.util.PageControllerInfo"%>
<%@ page import="com.iss.itreasury.util.PageController"%>
<!-- 引入此用户当前回话的Session -->
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%String strContext = request.getContextPath();%>
<%
/** 定义业务实体类 */
BudgetPlanInfo info = null;
long m_lUserID = sessionMng.m_lUserID;
long lModuleID = sessionMng.m_lModuleID;
Collection m_userPrivilege = new Vector();
OB_PrivilegeDAO privilegedao = new OB_PrivilegeDAO();
String tempStr = "";
String userPrivilegeNo = "";
tempStr = (String)request.getParameter("userPrivilegeNo");
if(tempStr!=null && !"".equals(tempStr)){
   userPrivilegeNo = request.getParameter("userPrivilegeNo");
}else{
   userPrivilegeNo = ""; 
}

String strTitle = "初始化菜单";
try {
	 /** 权限检查 **/
    Log.print("=================进入页面InitMenu.jsp=========");
    //请求检测
     //用户登录检测 
        if (sessionMng.isLogin() == false)
        {
        	OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E002");
        	out.flush();
        	return;
        }

        // 判断用户是否有权限 
        if (sessionMng.hasRight(request) == false)
        {
            out.println(sessionMng.hasRight(request));
        	OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E003");
        	out.flush();
        	return;
        }
       // m_userPrivilege = privilegedao.getPrivilegeOfUserForYQ(m_lUserID,lModuleID,userPrivilegeNo);       
        session.setAttribute("userprivilege",m_userPrivilege);
        
        //写登录日志
        LoginLogBean loginLogBean = new LoginLogBean();
        loginLogBean.addLoginLog(sessionMng,Constant.EBANK_USER,request.getRemoteHost(),Constant.SUCCESSFUL,"用户登录成功");
        
		//added by mzh_fu 2007/09/03
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
				
				if(blnSubjectCommonNameIsneeded && (objCertSubjectCommonName == null || !objCertSubjectCommonName.toString().equals(sessionMng.m_strCertCN))){
					blnCertBindRight = false;
				}else if(!blnSubjectCommonNameIsneeded && (objCertSubjectCommonName != null && !objCertSubjectCommonName.toString().equals(sessionMng.m_strCertCN)) ){
					blnCertBindRight = false;
				}
				
				if(blnCertSerialNumberIsneeded && (objCertSerialNumber == null || !objCertSerialNumber.toString().equals(sessionMng.m_strCertSerialNumber))){
					blnCertBindRight = false;
				}else if(!blnCertSerialNumberIsneeded && (objCertSerialNumber != null && !objCertSerialNumber.toString().equals(sessionMng.m_strCertSerialNumber))){
					blnCertBindRight = false;
				}
				
				if(!blnCertBindRight)
					throw new Exception("用户无此证书的使用权限，请确认该用户是否正确绑定了此证书！");
			}
		}
				
		String strNextPageURL = "/Validate.jsp";		
		
		if(strConfigTroy.equals(Constant.GlobalTroyName.NetSign)){
			//信安世纪
			strNextPageURL = "/Init_signature.jsp?loginno="+sessionMng.m_strUserName;									
		}else if(null==objCertInfo && strConfigTroy.equals(Constant.GlobalTroyName.ITrus)){
			//天威诚信
			strNextPageURL = "/Init_signature_iTrus.jsp";			 
		}
	
		//构建页面分发时需要用到的实体
		PageControllerInfo pageControllerInfo = new PageControllerInfo();
		pageControllerInfo.setSessionMng(sessionMng);
		pageControllerInfo.setUrl(strContext + strNextPageURL);		
		//分发
		RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
	    rd.forward( request,response );
/**
* 异常处理
*/
}catch ( Exception exp ) {
	/**
	* 用户提交信息置入request中
	*/
	
	exp.printStackTrace();
	/**
	 * 出现异常,添加报错信息
	 */
	sessionMng.getActionMessages().addMessage(exp.getMessage());

	/** 
	 * 转向下一页面 
	 **/
	//added by mzh_fu 2007/09/03 
	RequestDispatcher rd = request.getRequestDispatcher("./Index.jsp");
	rd.forward( request,response );
}
%>