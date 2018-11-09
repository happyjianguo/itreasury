<%--
/**
 * 程序名称：C853.jsp
 * 功能说明：系统管理-用户管理
 * 作　　者：刘琰
 * 完成日期：2003年9月3日
 */
--%>

<!--Header start-->
<%@ page contentType = "text/html;charset=gbk" %>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<jsp:useBean id="getData" class="com.iss.itreasury.system.privilege.util.GetData" scope="page"/>
<%@ page import="java.util.*,
				 com.iss.itreasury.util.*,
				 com.iss.itreasury.system.bizlogic.EBankbean,
				 com.iss.itreasury.ebank.privilege.dataentity.*,
                 com.iss.itreasury.ebank.util.*"
%>
<%@ page import="com.iss.itreasury.util.PageControllerInfo"%>
<%@ page import="com.iss.itreasury.util.PageController"%>
<%@ page import="com.iss.inut.workflow.ws.IWorkflowSpecial"%>
<%@ page import="com.iss.inut.workflow.ws.IWorkflowSpecialImp"%>
<%
    response.setHeader("Cache-Control","no-stored");
	response.setHeader("Pragma","no-cache");
	response.setDateHeader("Expires",0);
%>

<% String strContext = request.getContextPath();%>
<!--Header end-->

<%!
	/* 标题固定变量 */
	String strTitle = "";
%>

<%
	/* 用户登录检测与权限校验 */
	try
	{
		/* 用户登录检测 */
		if (sessionMng.isLogin() == false)
		{
			OBHtml.showCommonMessage(out,sessionMng, strTitle, "", 1, "Gen_E002");
			out.flush();
			return;
		}
		// 判断用户是否有权限 
		if (sessionMng.hasRight(request) == false)
		{
			out.println(sessionMng.hasRight(request));
			OBHtml.showCommonMessage(out, sessionMng, strTitle,"NO WAY", 1, "Gen_E003");
			out.flush();
			return;
		}
	
		String strTemp = null;
		String strMethod = "";
		//
		strTemp = request.getParameter("method");
		Log.print("Method is : " + strTemp);
		if( strTemp != null && strTemp.length() > 0 )
		{
			strMethod = strTemp;
		}
	
		//处理汉字
		HttpServletRequest req = getData.setValue(request);

		if( strMethod.equals("Add") ) //新增用户
		{
			String strUserName = "";
			String strLogin = "";
			String strPassword = "";
			long lCurrencyID = -1;
			
			
			strTemp = (String)req.getAttribute("UserName");
			Log.print("UserName " + strTemp);
			if( strTemp != null && strTemp.length() > 0 )
			{
				strUserName = strTemp;
			}
			strTemp = (String)req.getAttribute("LoginNo");
			Log.print("strLogin " + strTemp);
			if( strTemp != null && strTemp.length() > 0 )
			{
				strLogin = strTemp;
			}
			strTemp = (String)req.getAttribute("Password");
			Log.print("strPassword " + strTemp);
			if( strTemp != null && strTemp.length() > 0 )
			{
				strPassword = strTemp;
			}
			strTemp = (String)req.getAttribute("Currency");
			Log.print("strLogin " + strTemp);
			if( strTemp != null && strTemp.length() > 0 )
			{
				lCurrencyID = Long.parseLong(strTemp);
			}

			OB_UserInfo pi = new OB_UserInfo();
			pi.setSName(strUserName);
			pi.setSLoginNo(strLogin);
			pi.setSPassword(strPassword);
			pi.setNClientId(sessionMng.m_lClientID);
			pi.setNCurrencyId(lCurrencyID);
			pi.setNInputUserID(sessionMng.m_lUserID);
			pi.setInputUserName(sessionMng.m_strUserName);
			pi.setNOfficeID(sessionMng.m_lOfficeID);
			pi.setNSaid(sessionMng.m_lUserID);
			pi.setDtChangePassword(Env.getSystemDate());
			pi.setNClientId(sessionMng.m_lClientID);

			int len = 0;

			String strNextUrl = null;
			EBankbean userAdmin = new EBankbean();	
				
			/***********新增用户时查询Ldap，看该用户是否存在，若存在添加新用户，不存在不允许添加*************/
			if (Config.getBoolean(ConfigConstant.GLOBAL_PRIVILEGE_LDAP,false))
           	{
           		if(SDCUtil.isNeedLDAP(request)){//密码策略中是否有Ldap验证 true 需要，false 不需要
	           		if(!LdapUtil.isUserExist(request,pi.getSLoginNo()))
	           		{
	           			throw new IException("Ldap中不存在该用户"+pi.getSLoginNo()+"，请重新输入!");
	           		}
           		}
           	}
			/***********新增用户时查询Ldap，看该用户是否存在，若存在添加新用户，不存在不允许添加*************/
					
			try{		
				long lUserID = userAdmin.addUser(pi);
				if(lUserID==-1)
				{
					throw new IException("新增用户无效");
				}
				
				strNextUrl = strContext + "/system/sdc/C853.jsp?method=view";	
				
			}catch(IException exp){
			%>
				<script language="JavaScript">
					alert("<%=exp.getMessage()%>");
				</script>
			<%		  
				request.setAttribute("isRepeat",Constant.YesOrNo.YES+"");
				Collection c  = userAdmin.findUserByClientID(sessionMng.m_lClientID);
				/* 在请求中保存结果对象 */
			    request.setAttribute("GroupsOfClient", c);
			    strNextUrl = strContext + "/system/sdc/C853.jsp?method=toAdd";				
			}
			
			PageControllerInfo pageControllerInfo = new PageControllerInfo();
			pageControllerInfo.setSessionMng(sessionMng);
			pageControllerInfo.setUrl(strNextUrl);
			RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
	    	rd.forward(request, response);			
		}
		else if( strMethod.equals("Modify") ) //帐户权限修改
		{
			long lCurrencyID = -1;
			long lUserID = -1;
			String strUserName = "";
			String strLogin = "";
			//
			strTemp = (String)req.getAttribute("UserID");
			if( strTemp != null && strTemp.length() > 0 )
			{
				lUserID = Long.parseLong(strTemp);
			}
			//
			strTemp = (String)req.getAttribute("Currency");
			if( strTemp != null && strTemp.length() > 0 )
			{
				lCurrencyID = Long.parseLong(strTemp);
			}
			//
			strTemp = (String)req.getAttribute("UserName");
			if( strTemp != null && strTemp.length() > 0 )
			{
				strUserName = strTemp;
			}

			strTemp = (String)req.getAttribute("hnstrLogin");
			Log.print("strLogin " + strTemp);
			if( strTemp != null && strTemp.length() > 0 )
			{
				strLogin = strTemp;
			}

			Log.print("UserID is " + lUserID);
			
			OB_UserInfo pi = new OB_UserInfo();
			pi.setSLoginNo(strLogin);
			pi.setId(lUserID);
			pi.setSName(strUserName);
			pi.setNCurrencyId(lCurrencyID);
			pi.setNClientId(sessionMng.m_lClientID);
			
			String [] AccountNo = request.getParameterValues("CheckboxAccount");
			String [] AccountNoQuery = request.getParameterValues("CheckboxAccountQuery");
			String [] EbankAccountNoOperate = request.getParameterValues("checkEbankAccountOperate");
			String [] EbankAccountNoQuery = request.getParameterValues("checkEbankAccountQuery");

			EBankbean userAdmin = new EBankbean();
			lUserID = userAdmin.updateUserAccount(pi,AccountNo);
			userAdmin.updateUserInfoPublic(pi,AccountNoQuery,"OB_AccountOwnedByUserQuery");
			userAdmin.updateUserEbankAccountPublic(pi,EbankAccountNoOperate,"OB_EbankAccountByUserOperation");
			userAdmin.updateUserEbankAccountPublic(pi,EbankAccountNoQuery,"OB_EbankAccountByUserQuery");
		    
		    // 设置返回地址 
			//构建页面分发时需要用到的实体
			PageControllerInfo pageControllerInfo = new PageControllerInfo();
			pageControllerInfo.setSessionMng(sessionMng);
			pageControllerInfo.setUrl(strContext + "/system/sdc/C854.jsp");
			//分发
			RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
		    // forward到结果页面 
		    rd.forward(request, response);
		}
		else if( strMethod.equals("Delete") )//删除用户
		{
			long lUserID = -1;
			strTemp = (String)req.getAttribute("UserID");
			if( strTemp != null && strTemp.length() > 0 )
			{
				lUserID = Long.parseLong(strTemp);
			}
			//add by kevin(刘连凯) 添加是否在审批流中使用的校验 
			 try{
			 	IWorkflowSpecial IWorkflowSpecialBiz=new IWorkflowSpecialImp();
			    boolean isBeUsedInApproval = IWorkflowSpecialBiz.checkUserBeUsedInApproval(String.valueOf(lUserID));	
				if(isBeUsedInApproval){
				   throw new IException("用户具有审批权限，请先取消");
				}
				EBankbean userAdmin = new EBankbean();
			    userAdmin.delUser(lUserID);	
			 }catch(Exception e){			 
				sessionMng.getActionMessages().addMessage(e.getMessage()); 		 	
			 }			
			
		    // 设置返回地址 
			//构建页面分发时需要用到的实体
			PageControllerInfo pageControllerInfo = new PageControllerInfo();
			pageControllerInfo.setSessionMng(sessionMng);
			pageControllerInfo.setUrl(strContext + "/system/sdc/C853.jsp?method=view");
			//分发
			RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
		    // forward到结果页面 
		    rd.forward(request, response);
		}
		else if( strMethod.equals("view") )//进入用户管理页面
		{
			EBankbean userAdmin = new EBankbean();
			Collection c = userAdmin.findUserByClientID(sessionMng.m_lClientID);
			Log.print(" UsersOfClient " + c);
			/* 在请求中保存结果对象 */
		    request.setAttribute("UsersOfClient", c);
		    // 设置返回地址 
			//构建页面分发时需要用到的实体
			PageControllerInfo pageControllerInfo = new PageControllerInfo();
			pageControllerInfo.setSessionMng(sessionMng);
			pageControllerInfo.setUrl(strContext + "/system/sdc/V853.jsp");
			//分发
			RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
		    // forward到结果页面 
		    rd.forward(request, response);
		}
		else if( strMethod.equals("toModify") )//进入修改用户页面
		{
			long lUserID = -1;
			strTemp = request.getParameter("UserID");
			if( strTemp != null && strTemp.length() > 0 )
			{
				lUserID = Long.parseLong(strTemp);
			}
			EBankbean userAdmin = new EBankbean();
			OB_UserInfo pi = userAdmin.findUserInfoByID(lUserID);

			/* 在请求中保存结果对象 */
		    request.setAttribute("UserInfo", pi);

		    // 设置返回地址 
			//构建页面分发时需要用到的实体
			PageControllerInfo pageControllerInfo = new PageControllerInfo();
			pageControllerInfo.setSessionMng(sessionMng);
			pageControllerInfo.setUrl(strContext + "/system/sdc/V856.jsp");
			//分发
			RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
		    // forward到结果页面 
		    rd.forward(request, response);
		}else if(strMethod.equals("ModifyUser"))  //修改用户管理信息
		{
			long lCurrencyID = -1;
			long lUserID = -1;
			String strUserName = "";
			String strLogin = "";
			//
			strTemp = (String)req.getAttribute("UserID");
			if( strTemp != null && strTemp.length() > 0 )
			{
				lUserID = Long.parseLong(strTemp);
			}
			//
			strTemp = (String)req.getAttribute("Currency");
			if( strTemp != null && strTemp.length() > 0 )
			{
				lCurrencyID = Long.parseLong(strTemp);
			}
			//
			strTemp = (String)req.getAttribute("UserName");
			if( strTemp != null && strTemp.length() > 0 )
			{
				strUserName = strTemp;
			}

			strTemp = (String)req.getAttribute("hnstrLogin");
			Log.print("strLogin " + strTemp);
			if( strTemp != null && strTemp.length() > 0 )
			{
				strLogin = strTemp;
			}
			
			OB_UserInfo pi = new OB_UserInfo();
			pi.setSLoginNo(strLogin);
			pi.setId(lUserID);
			pi.setSName(strUserName);
			pi.setNCurrencyId(lCurrencyID);
			pi.setNClientId(sessionMng.m_lClientID);
			
			EBankbean userAdmin = new EBankbean();
			lUserID = userAdmin.updateUserInfo(pi);		
			
			RequestDispatcher rd = request.getRequestDispatcher("C853.jsp?method=view");	
			rd.forward(request,response);
		}else if( strMethod.equals("AccountAuthorize") )//进入帐户授权明细页面
		{
			long lUserID = -1;
			strTemp = request.getParameter("UserID");
			if( strTemp != null && strTemp.length() > 0 )
			{
				lUserID = Long.parseLong(strTemp);
			}
			EBankbean userAdmin = new EBankbean();
			OB_UserInfo pi = userAdmin.findUserInfoByID(lUserID);

			/* 在请求中保存结果对象 */
		    request.setAttribute("UserInfo", pi);

		    // 设置返回地址 
			//构建页面分发时需要用到的实体
			PageControllerInfo pageControllerInfo = new PageControllerInfo();
			pageControllerInfo.setSessionMng(sessionMng);
			pageControllerInfo.setUrl(strContext + "/system/sdc/V854.jsp");
			//分发
			RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
		    // forward到结果页面 
		    rd.forward(request, response);
		}
	}
	catch(IException ie)
	{
		OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
		return;
	}
%>
<!--Forward end-->