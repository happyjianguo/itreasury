<%--
/**
 * 程序名称：C854.jsp
 * 功能说明：系统管理-帐户管理
 * 作　　者：倪卫楠
 * 完成日期：2011年10月15日
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
	
		EBankbean bean = new EBankbean();
		ArrayList list = new ArrayList();
		list = bean.findAuthorizedUser(sessionMng.m_lClientID);
		request.setAttribute("userList",list);

		//构建页面分发时需要用到的实体
		PageControllerInfo pageControllerInfo = new PageControllerInfo();		
		pageControllerInfo.setSessionMng(sessionMng);
		pageControllerInfo.setUrl(strContext + "/system/sdc/V855.jsp");
		RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
		rd.forward(request, response);
		
		
		
	}
	catch(IException ie)
	{
		OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
		return;
	}
%>
<!--Forward end-->