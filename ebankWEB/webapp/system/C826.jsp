<%--
/*
 * 程序名称：C826.jsp
 * 功能说明：系统管理-收款方资料修改控制页面
 * 作　　者：刘琰
 * 完成日期：2003年10月20日
 */
--%>

<!--Header start-->
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ include file="/common/common.jsp" %>
<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="com.iss.itreasury.ebank.obsystem.dataentity.*,
				 com.iss.itreasury.ebank.obsystem.dao.*,
				 com.iss.itreasury.ebank.util.*,
                 java.rmi.*,
                 java.util.*,
                 com.iss.itreasury.util.*,
                 java.sql.*"

%>
<%@ page import="com.iss.itreasury.util.PageControllerInfo"%>
<%@ page import="com.iss.itreasury.util.PageController"%>
<% String strContext = request.getContextPath();%>
<!--Header end-->

<%
	//固定变量
	String strTitle = "[收款方资料维护--新增收款方资料]";
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

        //判断用户是否有权限 
        if (sessionMng.hasRight(request) == false)
        {
            out.println(sessionMng.hasRight(request));
        	OBHtml.showCommonMessage(out, sessionMng,strTitle, "", 1, "Gen_E003");
        	out.flush();
        	return;
		}
	 } 
	 catch (Exception e) 
	{
        OBHtml.showExceptionMessage(out,sessionMng, (IException)e, strTitle,"",1);
		return;
    }
%>


<%
	try
	{
		long lID = Long.parseLong(request.getParameter("txtID").trim());

      	//初始化查询类及查询结果集
		OBSystemDao obSystemDao = new OBSystemDao(); 	
		ClientCapInfo info = new ClientCapInfo();
		
		//调用类中方法查询结果
		info = 	obSystemDao.findPayeeInfoByID(lID);
%>

<%
		//在请求中保存结果对象
		if (info != null)
		{
			request.setAttribute("ClientCapInfo",info);
		}
		//获取上下文环境
      	//ServletContext sc = getServletContext();
      	//设置返回地址
	  	RequestDispatcher rd = null;
	  	String strTemp = request.getParameter("txtIsCPF").trim();
	  	if (strTemp.equalsIgnoreCase("true"))
	  	{
			Log.print("是中油用户，走S827-Opt.jsp");
			//构建页面分发时需要用到的实体
			PageControllerInfo pageControllerInfo = new PageControllerInfo();
			pageControllerInfo.setSessionMng(sessionMng);
			pageControllerInfo.setUrl(strContext + "/system/V827.jsp");
			//分发
			rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
	  	}
	  	else
	  	{
			Log.print("不是中油用户，走S837-Opt.jsp");
			//构建页面分发时需要用到的实体
			PageControllerInfo pageControllerInfo = new PageControllerInfo();
			pageControllerInfo.setSessionMng(sessionMng);
			pageControllerInfo.setUrl(strContext + "/system/V837.jsp");
			//分发
			rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
	  	}
      	//forward到结果页面
      	rd.forward(request, response);
   	}
   	catch(IException ie)
   	{
        OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
		return;
   	}

%>

