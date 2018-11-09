<!--Header start-->

<%@ page contentType="text/html;charset=gbk" %>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ page import="com.iss.itreasury.ebank.util.*,
				 com.iss.itreasury.util.*,				
                 java.util.*,
				 java.sql.*"
%>
<%@ page import="com.iss.itreasury.util.PageControllerInfo"%>
<%@ page import="com.iss.itreasury.util.PageController"%>
<%String strContext = request.getContextPath();%>

<%
    try{
        //登录检测
       if( sessionMng.isLogin() == false)
	{
		OBHtml.showCommonMessage(out, sessionMng, "", "", 1, "Gen_E002");
		out.flush();
		return;
	}
	//判断是否有权限
	if(sessionMng.hasRight(request)==false)
	{
		out.println(sessionMng.hasRight(request));
		OBHtml.showCommonMessage(out, sessionMng, "", "", 1, "Gen_E003");
		out.flush();
		return;
	}
        /**
         * isLogin end
         */


    	String strTmp = "";
    	String strReq1 = "";
    	long lReq1 = -1;
    	String strReq2 = "";
    	Timestamp tsReq3 = null;
    	// 获取req1的值
    	strTmp = request.getParameter("req1");
    	if( strTmp != null && strTmp.length() > 0 )
    	{
        	try
        	{
         		lReq1 = Long.parseLong(strTmp);
        	}
        	catch(Exception e)
        	{
            	lReq1 = -1;
        	}
    	}
    	// 获取req2的值
    	strTmp = request.getParameter("req2");
    	if( strTmp != null && strTmp.length() > 0 )
    	{
        	strReq2 = strTmp;
    	}
    	// 获取req3的值
    	strTmp = request.getParameter("req3");
    	if( strTmp != null && strTmp.length() > 0 )
    	{
        	try
        	{
         		tsReq3 = DataFormat.getDateTime(strTmp);
        	}
        	catch(Exception e)
        	{
            	tsReq3 = null;
        	}
    	}
%>
<!--Get Data end-->
<!--如果需要校验，写在此处-->
<!--Access DB-->

<%
    	//在请求中保存结果对象
    	//request.setAttribute("return",rf);
    	//获取上下文环境
    	//ServletContext sc = getServletContext();
    	//设置返回地址
		//构建页面分发时需要用到的实体
		PageControllerInfo pageControllerInfo = new PageControllerInfo();
		pageControllerInfo.setSessionMng(sessionMng);
		pageControllerInfo.setUrl(strContext + "/templet/Output.jsp");
		//分发
		RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
    	//forward到结果页面
    	rd.forward(request, response);
	}
    catch (Exception e)
    {
      
    }
%>
