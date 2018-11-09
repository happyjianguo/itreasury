<%--
/**
 * 程序名称：S860-Ctr.jsp
 * 功能说明：客户信息查看控制页面
 * 作　　者：刘琰
 * 完成日期：2003年11月27日
 */
--%>
<!--Header start-->
<%@ page contentType = "text/html;charset=gbk" %>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ page import="java.util.*,
				 javax.servlet.*,
				 com.iss.itreasury.util.*,
				 com.iss.itreasury.ebank.obsystem.dao.*,
				 com.iss.itreasury.ebank.obsystem.dataentity.*,
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

<%
    //固定变量
    String strTitle = "[客户资料]";

%>
<%
	/* 用户登录检测与权限校验 */
	try
	{
		/* 用户登录检测 */
		if (sessionMng.isLogin() == false)
		{
			OBHtml.showCommonMessage(out, sessionMng, strTitle,"",1,"Gen_E002");
			out.flush();
			return;
		}
		// 判断用户是否有权限 
		if (sessionMng.hasRight(request) == false)
		{
			out.println(sessionMng.hasRight(request));
			OBHtml.showCommonMessage(out,sessionMng, strTitle,"",1,"Gen_E003");
			out.flush();
			return;
		}
%>
<!--Access DB--start-->
<%
		// 获取OBSystemEJB远程接口
		OBSystemDao obSystemDao = new OBSystemDao();

		// 定义查询返回的结果集变量
		OBClientInfo obClientInfo = null;

		long lngClientID = sessionMng.m_lClientID;
		obClientInfo = obSystemDao.findClientByID(lngClientID);
%>
<!--Access DB--end-->
<%
		//在请求中保存结果对象
		request.setAttribute("OBClientInfo", obClientInfo);
		//获取上下文环境
		//ServletContext sc = getServletContext();
		//设置返回地址
		//构建页面分发时需要用到的实体
		PageControllerInfo pageControllerInfo = new PageControllerInfo();
		pageControllerInfo.setSessionMng(sessionMng);
		pageControllerInfo.setUrl(strContext + "/system/S861-Ipt.jsp?operation=" );
		//分发
		RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
		//forward到结果页面
		rd.forward(request, response);
		}
    catch (IException ie)
    {
        OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
		return;
    }
%>


