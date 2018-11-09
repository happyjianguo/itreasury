<%--
/*
 * 程序名称：S841-Ctr.jsp
 * 功能说明：交易类型设置查找控制页面
 * 作　　者：刘琰
 * 完成日期：2003年8月27日
 */
--%>

<!--Header start-->
<%@ page contentType = "text/html;charset=gbk" %>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ page import="java.util.*,
				 javax.servlet.*,
				 com.iss.itreasury.util.*,
                 com.iss.itreasury.ebank.obsystem.dao.*,
                 com.iss.itreasury.ebank.util.*"
%>
<%@ page import="com.iss.itreasury.util.PageControllerInfo"%>
<%@ page import="com.iss.itreasury.util.PageController"%>
<%
    response.setHeader("Cache-Control","no-stored");
	response.setHeader("Pragma","no-cache");
	response.setDateHeader("Expires",0);
%>
<!--Header end-->
<% String strContext = request.getContextPath();%>

<%!
	/* 标题固定变量 */
	String strTitle = "账户交易类型设置";
%>

<%
	/* 用户登录检测与权限校验 */
	try 
	{
        /* 用户登录检测 */
        if (sessionMng.isLogin() == false)
        {
        	OBHtml.showCommonMessage(out, sessionMng, strTitle, "", 1,"Gen_E002");
        	out.flush();
        	return;
        }

        // 判断用户是否有权限 
        if (sessionMng.hasRight(request) == false)
        {
            out.println(sessionMng.hasRight(request));
        	OBHtml.showCommonMessage(out, sessionMng, strTitle, "", 1,"Gen_E003");
        	out.flush();
        	return;
        }	
%>

<!--Get Data start-->
<%
   		/* 定义相应变量 */
		long lAccountID = -1; // 银行账户ID
	
		/* 从session中获取相应数据 */
		long lClientID = sessionMng.m_lClientID;
		long lCurrencyID = sessionMng.m_lCurrencyID;

	
		/* 从获取相应数据 */
		/* 银行账户ID */
		/*if(request.getParameter("lAccountID") != null)
		{
			lAccountID = Long.parseLong((String)request.getParameter("lAccountID")); 
			Log.print("银行账户ID=" + lAccountID);
		}*/
		if(request.getAttribute("lAccountID") != null)
		{
			try
			{
				lAccountID =Long.parseLong((String)request.getAttribute("lAccountID")); 
				Log.print("银行账户ID=" + lAccountID);
			}
			catch(Exception e){
				lAccountID = -1;
			}
		}
%>
<!--Get Data end-->

<!--Check start-->
<!--Check end-->

<!--Access DB start-->
<%
		/* 初始化结果信息类 */
		Collection cltApf = null;

		/* 初始化EJB 
		OBSystemHome obSystemHome = null;
		OBSystem obSystem = null;
		obSystemHome = (OBSystemHome)EJBObject.getEJBHome("OBSystemHome");
		obSystem = obSystemHome.create();*/
		/*初始化信息查询类*/
		OBSystemDao obSystemDao = new OBSystemDao();
		/* 调用方法获取查询信息 */
		cltApf = (Collection) obSystemDao.queryAccountPrvg(lAccountID);
	
%>
<!--Access DB end>

<!--Forward start-->
<%
		/* 在请求中保存结果对象 */
	    request.setAttribute("cltApf", cltApf);
		request.setAttribute("lAccountID",Long.toString(lAccountID));
	    /* 获取上下文环境 */
	    //ServletContext sc = getServletContext();
	    /* 设置返回地址 */
		//构建页面分发时需要用到的实体
		PageControllerInfo pageControllerInfo = new PageControllerInfo();
		pageControllerInfo.setSessionMng(sessionMng);
		pageControllerInfo.setUrl(strContext + "/system/S840-Ipt.jsp");
		//分发
		RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
	    /* forward到结果页面 */
	    rd.forward(request, response);
	} 
	catch(IException ie) 
	{
		OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
		return;
	}
%>
<!--Forward end-->