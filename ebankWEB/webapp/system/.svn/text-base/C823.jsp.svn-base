<%--
/*
 * 程序名称：C823.jsp
 * 功能说明：系统管理-收款方资料查找控制页面
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
<!--Header end-->
<% String strContext = request.getContextPath();%>

<%
	String strTitle = "[收款方资料维护]";
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
	//固定变量
	
	long lISCPFAccount = 2;//Notes中定义的
	//从S825－Ctr.jsp过来时，判断是不是中油用户
	String strIsCPF = (String)request.getAttribute("IsCPF");
	if ((strIsCPF != null && strIsCPF.equalsIgnoreCase("true")) ||  (request.getParameter("txtIsCPF") != null && request.getParameter("txtIsCPF").trim().equalsIgnoreCase("true")))
	{
		lISCPFAccount = 1;
	}
%>
<%
	try
	{
		//初始化信息查询类和查询结果集
		OBSystemDao obSystemDao = new OBSystemDao();
		Collection collection = null;
		Log.print("是否是中油账户:  "+lISCPFAccount);
		
		//调用类中方法查询
		collection = obSystemDao.queryPayee(sessionMng.m_lClientID,sessionMng.m_lCurrencyID,lISCPFAccount);
		
		//在请求中保存结果对象
		if (collection != null)
		{
			request.setAttribute("collection",collection);	//获取上下文环境
		}
		//ServletContext sc = getServletContext();
		//设置返回地址
		RequestDispatcher rd = null;
		if ((strIsCPF != null && strIsCPF.equalsIgnoreCase("true")) ||  (request.getParameter("txtIsCPF") != null && request.getParameter("txtIsCPF").trim().equalsIgnoreCase("true")))
		{
			Log.print("是内部用户，走V824.jsp");
			String stradd = (String)request.getAttribute("success");
			if(stradd!=null&&stradd.equals("success")){
				request.setAttribute("TJCG","success");
			}
			String strdel = (String)request.getAttribute("delete");
			if(strdel!=null&&strdel.equals("delete")){
				request.setAttribute("SCCG","success");
			}

			//构建页面分发时需要用到的实体
			PageControllerInfo pageControllerInfo = new PageControllerInfo();
			pageControllerInfo.setSessionMng(sessionMng);
			pageControllerInfo.setUrl(strContext + "/system/V824.jsp");
			//分发
			rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
		}
		else
		{
			Log.print("不是内部用户，走S834-Opt.jsp");
			String stradd = (String)request.getAttribute("success");
			if(stradd!=null&&stradd.equals("success")){
				request.setAttribute("TJCG","success");
			}
			String strdel = (String)request.getAttribute("delete");
			if(strdel!=null&&strdel.equals("delete")){
				request.setAttribute("SCCG","success");
			}
			
			//构建页面分发时需要用到的实体
			PageControllerInfo pageControllerInfo = new PageControllerInfo();
			pageControllerInfo.setSessionMng(sessionMng);
			pageControllerInfo.setUrl(strContext + "/system/V834.jsp");
			//分发
			rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
		}
		//forward到结果页面
		rd.forward(request, response);
	}
	catch(Exception e)
	{
		OBHtml.showExceptionMessage(out,sessionMng, (IException)e, strTitle,"",1);
	}
%>

