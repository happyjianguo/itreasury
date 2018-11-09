<%--
/*
 * 程序名称：S842-Ctr.jsp
 * 功能说明：交易类型设置提交控制页面
 * 作　　者：刘琰
 * 完成日期：2003年8月28日
 */
--%>

<!--Header start-->
<%@ page contentType = "text/html;charset=gbk" %>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ page import="java.util.*,
				 javax.servlet.*,
				 com.iss.itreasury.util.*,
                 com.iss.itreasury.ebank.obsystem.dao.*,
                 com.iss.itreasury.ebank.util.*,
				 com.iss.itreasury.ebank.obsystem.dataentity.*,
				 com.iss.itreasury.ebank.obsystem.bizlogic.*"
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
		/*初始化结果集*/
		String[] chktype = null;
		Vector vResult = new Vector();
		/*定义相应变量*/
		AccountPrvgInfo  info = null;
		long lAccountID = -1;
	
		/* 从FORM表单中获取相应数据设置结果集 */
		/* 银行账号 */
		if(request.getParameter("lAccountID") != null)
		{
			lAccountID = Long.parseLong((String)request.getParameter("lAccountID")); // 银行账户ID
			Log.print("银行账户ID=" + lAccountID);
		}
		
		/*checkbox数组*/
		if(request.getParameter("chktype") != null )
		{
			
			chktype = request.getParameterValues("chktype"); 
			Log.print("交易类型数=" + chktype.length);
			
			/* 初始化交易类型结果集 */
			for(int i=0;i<chktype.length;i++)
			{
				long ltypeid = Long.parseLong(chktype[i]);
				info = new AccountPrvgInfo();
				info.setInputUserID(sessionMng.m_lUserID);
				info.setTypeID(ltypeid);
				info.setAccountID(lAccountID);	
				info.setValue(true);
				vResult.add(info);
			}
		}
		else if (request.getParameter("chktype") == null)
		{			
			info = new AccountPrvgInfo();		
			info.setInputUserID(sessionMng.m_lUserID);		
			info.setAccountID(lAccountID);				
			vResult.add(info);						
		}
	
%>
<!--Get Data end-->

<!--Check start-->
<!--Check end-->

<!--Access DB start-->
<%
		/* 提交返回结果 */
		long lAddResult = -1;

		/* 初始化EJB */
		OBSystemHome obSystemHome = null;
		OBSystem obSystem = null;
		obSystemHome = (OBSystemHome)EJBObject.getEJBHome("OBSystemHome");
		obSystem = obSystemHome.create();
		
		/* 调用EJB方法提交信息 */
		lAddResult = obSystem.addAccountPrvg((Collection)vResult);

%>
<!--Access DB end>

<!--Forward start-->
<%
		System.out.println("************ lAddResult的值"+lAddResult+"**************");
		if (lAddResult >= 0)
		{
			/* 在请求中保存结果对象 */
	    	request.setAttribute("lAccountID",Long.toString(lAccountID));
		    /* 获取上下文环境 */
		    //ServletContext sc = getServletContext();
		    /* 设置返回地址 */
			//构建页面分发时需要用到的实体
			PageControllerInfo pageControllerInfo = new PageControllerInfo();
			pageControllerInfo.setSessionMng(sessionMng);
			pageControllerInfo.setUrl(strContext + "/system/S857-Opt.jsp");
			//分发
			RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
		    /* forward到结果页面 */
		    rd.forward(request, response);
		} 	
		else 
		{
			OBHtml.showCommonMessage(out, sessionMng, strTitle, "", 1,"Gen_E001");
			return;
		}
	}
	catch(IException ie) 
	{
		OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
		return;
	}
%>
<!--Forward end-->