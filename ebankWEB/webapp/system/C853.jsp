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
				com.iss.itreasury.ebank.privilege.bizlogic.*,
                 com.iss.itreasury.ebank.util.*"
%>
<%@ page import="com.iss.itreasury.util.PageControllerInfo"%>
<%@ page import="com.iss.itreasury.util.PageController"%>
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

		if( strMethod.equals("toAdd") )
		{
			EBankbean userAdmin = new EBankbean();
			Collection c  = userAdmin.findGroupsByClient(sessionMng.m_lClientID);
			/* 在请求中保存结果对象 */
		    request.setAttribute("GroupsOfClient", c);
		    /* 获取上下文环境 */
		    //ServletContext sc = getServletContext();
		    /* 设置返回地址 */
			System.out.println("***************begin");
			//构建页面分发时需要用到的实体
			PageControllerInfo pageControllerInfo = new PageControllerInfo();
			pageControllerInfo.setSessionMng(sessionMng);
			pageControllerInfo.setUrl(strContext + "/system/V854.jsp");
			//分发
			RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
			
		    /* forward到结果页面 */
		    rd.forward(request, response);
			System.out.println("**************end");
		}
		else if( strMethod.equals("Add") )
		{
			String strUserName = "";
			String strLogin = "";
			String strPassword = "";
			long lCurrencyID = -1;
			long nIsVirtualUser=0;
			String []strGroups = null;
			
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
			if(request.getAttribute("nIsVirtualUser")!=null)
			{
				nIsVirtualUser = DataFormat.parseLong(request.getAttribute("nIsVirtualUser").toString());
			}
			strGroups = request.getParameterValues("Checkbox");
			///////////////////////////////////////////////////////
			OB_UserInfo pi = new OB_UserInfo();
			//pi.strUserName = strUserName;
			pi.setSName(strUserName);
			//pi.strLogin = strLogin;
			pi.setSLoginNo(strLogin);
			//pi.strPassword = strPassword;
			pi.setSPassword(strPassword);
			//pi.lClientID = sessionMng.m_lClientID;
			pi.setNClientId(sessionMng.m_lClientID);
			//pi.lCurrencyID = lCurrencyID;
			pi.setNCurrencyId(lCurrencyID);
			//pi.lInputUserID = sessionMng.m_lUserID;
			pi.setNInputUserID(sessionMng.m_lUserID);
			//pi.strInputUserName = sessionMng.m_strUserName;
			pi.setInputUserName(sessionMng.m_strUserName);
			//pi.lOfficeID = sessionMng.m_lOfficeID;
			pi.setNOfficeID(sessionMng.m_lOfficeID);
			//pi.lSAID = sessionMng.m_lSAID;
			pi.setNSaid(sessionMng.m_lUserID);
			pi.setNIsVirtualUser(nIsVirtualUser);
			pi.setDtChangePassword(Env.getSystemDate());

			String [] AccountNo = request.getParameterValues("CheckboxAccount");
			String [] AccountNoQuery = request.getParameterValues("CheckboxAccountQuery");
			String [] EbankAccountNoOperate = request.getParameterValues("checkEbankAccountOperate");
			String [] EbankAccountNoQuery = request.getParameterValues("checkEbankAccountQuery");
			
			
			//pi.lClientID = sessionMng.m_lClientID;
			pi.setNClientId(sessionMng.m_lClientID);

			int len = 0;
			if (strGroups!=null)
			{
				len = strGroups.length;
			}
			
			OB_Group_Of_UserInfo[] group_user = new OB_Group_Of_UserInfo[len];
			
			for (int i=0;i<len;i++)
			{
				OB_Group_Of_UserInfo ob_Group_Of_UserInfo = new OB_Group_Of_UserInfo();
				ob_Group_Of_UserInfo.setGroupID(DataFormat.parseLong(strGroups[i]));
				group_user[i] = ob_Group_Of_UserInfo;
			}
			
			String strNextUrl = null;
			EBankbean userAdmin = new EBankbean();		
			
			try{		
				long lUserID = userAdmin.addUserAll(pi,group_user,AccountNo,AccountNoQuery,EbankAccountNoOperate,EbankAccountNoQuery);
				if(lUserID==-1)
				{
					throw new IException("新增用户无效");
				}
				
				strNextUrl = strContext + "/system/C853.jsp?method=view";	
				
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
			    strNextUrl = strContext + "/system/C853.jsp?method=toAdd";				
			}
			
			PageControllerInfo pageControllerInfo = new PageControllerInfo();
			pageControllerInfo.setSessionMng(sessionMng);
			pageControllerInfo.setUrl(strNextUrl);
			RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
	    	rd.forward(request, response);			
		}
		else if( strMethod.equals("Modify") )
		{
			long lCurrencyID = -1;
			long lUserID = -1;
			String strUserName = "";
			String strLogin = "";
			String []strGroups = null;
			long nIsVirtualUser=0;

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
			if(request.getAttribute("nIsVirtualUser")!=null)
			{
				nIsVirtualUser = DataFormat.parseLong(request.getAttribute("nIsVirtualUser").toString());
			}
			
			
			Log.print("UserID is " + lUserID);
			
			OB_UserInfo pi = new OB_UserInfo();
			pi.setSLoginNo(strLogin);
			//pi.lUid = lUserID;
			pi.setId(lUserID);
			//pi.strUserName = strUserName;
			pi.setSName(strUserName);
			//pi.lCurrencyID = lCurrencyID;
			pi.setNCurrencyId(lCurrencyID);
			//pi.lClientID = sessionMng.m_lClientID;
			pi.setNClientId(sessionMng.m_lClientID);
			pi.setNIsVirtualUser(nIsVirtualUser);

			strGroups = request.getParameterValues("Checkbox");
			String [] AccountNo = request.getParameterValues("CheckboxAccount");
			String [] AccountNoQuery = request.getParameterValues("CheckboxAccountQuery");
			String [] EbankAccountNoOperate = request.getParameterValues("checkEbankAccountOperate");
			String [] EbankAccountNoQuery = request.getParameterValues("checkEbankAccountQuery");
			
			int len = 0;
			if (strGroups!=null)
			{
				len = strGroups.length;
			}
			
			OB_Group_Of_UserInfo[] group_user = new OB_Group_Of_UserInfo[len];
			
			for (int i=0;i<len;i++)
			{
				OB_Group_Of_UserInfo ob_Group_Of_UserInfo = new OB_Group_Of_UserInfo();
				ob_Group_Of_UserInfo.setGroupID(DataFormat.parseLong(strGroups[i]));
				group_user[i] = ob_Group_Of_UserInfo;
			}



			EBankbean userAdmin = new EBankbean();
			System.out.println("################ \t"+pi.getId()+"\t"+pi.getNClientId()+"\t"+pi.getSLoginNo());
			lUserID = userAdmin.updateUserInfo(pi,group_user,AccountNo);
			userAdmin.updateUserInfoPublic(pi,AccountNoQuery,"OB_AccountOwnedByUserQuery");
			userAdmin.updateUserEbankAccountPublic(pi,EbankAccountNoOperate,"OB_EbankAccountByUserOperation");
			userAdmin.updateUserEbankAccountPublic(pi,EbankAccountNoQuery,"OB_EbankAccountByUserQuery");
		    // 获取上下文环境 
		    //ServletContext sc = getServletContext();
		    // 设置返回地址 
			//构建页面分发时需要用到的实体
			PageControllerInfo pageControllerInfo = new PageControllerInfo();
			pageControllerInfo.setSessionMng(sessionMng);
			pageControllerInfo.setUrl(strContext + "/system/C853.jsp?method=view");
			//分发
			RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
		    // forward到结果页面 
		    rd.forward(request, response);
		}
		else if( strMethod.equals("Delete") )
		{
			long lUserID = -1;
			//
			strTemp = (String)req.getAttribute("UserID");
			if( strTemp != null && strTemp.length() > 0 )
			{
				lUserID = Long.parseLong(strTemp);
			}
			EBankbean userAdmin = new EBankbean();
			userAdmin.delUser(lUserID);
	
		    // 获取上下文环境 
		    //ServletContext sc = getServletContext();
		    // 设置返回地址 
			//构建页面分发时需要用到的实体
			PageControllerInfo pageControllerInfo = new PageControllerInfo();
			pageControllerInfo.setSessionMng(sessionMng);
			pageControllerInfo.setUrl(strContext + "/system/C853.jsp?method=view");
			//分发
			RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
		    // forward到结果页面 
		    rd.forward(request, response);
		}
		else if( strMethod.equals("view") )
		{
			EBankbean userAdmin = new EBankbean();
			Collection c = userAdmin.findUserByClientID(sessionMng.m_lClientID);
			Log.print(" UsersOfClient " + c);
			/* 在请求中保存结果对象 */
		    request.setAttribute("UsersOfClient", c);
		    // 获取上下文环境 
		    //ServletContext sc = getServletContext();
		    // 设置返回地址 
			//构建页面分发时需要用到的实体
			PageControllerInfo pageControllerInfo = new PageControllerInfo();
			pageControllerInfo.setSessionMng(sessionMng);
			pageControllerInfo.setUrl(strContext + "/system/V853.jsp");
			//分发
			RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
		    // forward到结果页面 
		    rd.forward(request, response);
		}
		else if( strMethod.equals("toModify") )
		{
			long lUserID = -1;
			strTemp = request.getParameter("UserID");
			Log.print("To update User ID +++++++++++++++++++++++++++++++++++++++++++++++++++" + strTemp);
			if( strTemp != null && strTemp.length() > 0 )
			{
				lUserID = Long.parseLong(strTemp);
			}
			Vector vGroupID = new Vector();
			EBankbean userAdmin = new EBankbean();
			OB_UserInfo pi = userAdmin.findUserInfoByID(lUserID);
			//ebank.privilege.bizlogic.UserBean
			UserBean bean = new UserBean();

			vGroupID = (Vector)bean.findGroupByUserId(lUserID);
	//
			/* 在请求中保存结果对象 */
		    request.setAttribute("UserInfo", pi);
			//
			Collection c  = userAdmin.findGroupsByClient(sessionMng.m_lClientID);
			/* 在请求中保存结果对象 */
		    request.setAttribute("GroupsOfClient", c);
			request.setAttribute("vGroupID",vGroupID);
		    // 获取上下文环境 
		    //ServletContext sc = getServletContext();
		    // 设置返回地址 
			//构建页面分发时需要用到的实体
			PageControllerInfo pageControllerInfo = new PageControllerInfo();
			pageControllerInfo.setSessionMng(sessionMng);
			pageControllerInfo.setUrl(strContext + "/system/V854.jsp");
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