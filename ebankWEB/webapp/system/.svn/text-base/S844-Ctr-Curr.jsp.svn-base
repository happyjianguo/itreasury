<%--
/*
 * 程序名称：S844-Ctr-Curr.jsp
 * 功能说明：签认金额设置查找控制页面（新奥--活期）
 * 作　　者：周翔
 * 完成日期：2011年4月15日
 */
--%>

<!--Header start-->
<%@ page contentType = "text/html;charset=gbk" %>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ page import="com.iss.itreasury.util.*,
				 com.iss.itreasury.ebank.obsystem.dataentity.*,
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
	String strTitle = "活期签认金额设置";
%>

<%
	/* 用户登录检测与权限校验 */
	try 
	{
        /* 用户登录检测 */
        if (sessionMng.isLogin() == false)
        {
        	OBHtml.showCommonMessage(out, sessionMng, strTitle, "", 1, "Gen_E002");
        	out.flush();
        	return;
        }

        // 判断用户是否有权限
        if (sessionMng.hasRight(request) == false)
        {
            out.println(sessionMng.hasRight(request));
        	OBHtml.showCommonMessage(out, sessionMng, strTitle, "", 1, "Gen_E003");
        	out.flush();
        	return;
        }
	 
%>

<!--Get Data start-->
<%
		/* 从session中获取相应数据 */
		long lClientID = sessionMng.m_lClientID;
		long lCurrencyID = sessionMng.m_lCurrencyID;
%>
<!--Get Data end-->

<!--Check start-->
<!--Check end-->

<!--Access DB start-->
<%
		/* 初始化结果信息类 */
		SignAmountInfo  signAmountInfo  = new SignAmountInfo ();

		/* 初始化EJB 
		OBSystemHome obSystemHome = null;
		OBSystem obSystem = null;
		obSystemHome = (OBSystemHome)EJBObject.getEJBHome("OBSystemHome");
		obSystem = obSystemHome.create();*/
		/*初始化信息查询类*/
		OBSystemDao obSystemDao = new OBSystemDao();
		/* 调用方法获取查询信息 */
		signAmountInfo = obSystemDao.querySignAmountForCurr(lClientID, lCurrencyID);
		Log.print("sAmount1====" + signAmountInfo.getAmount1());
%>
<!--Access DB end>

<!--Forward start-->
<%
		/* 在请求中保存结果对象 */
	    request.setAttribute("signAmountInfo", signAmountInfo);
	    /* 获取上下文环境 */
	    //ServletContext sc = getServletContext();
	    /* 设置返回地址 */
		//构建页面分发时需要用到的实体
		PageControllerInfo pageControllerInfo = new PageControllerInfo();
		pageControllerInfo.setSessionMng(sessionMng);
		pageControllerInfo.setUrl(strContext + "/system/S843-Ipt-Curr.jsp");
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