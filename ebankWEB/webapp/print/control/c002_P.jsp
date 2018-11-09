<%--
 页面名称 ：c002_P.jsp
 页面功能 : 单据打印--控制页面
 作    者 ：Mliu
 日    期 ：2006-12-7
 特殊说明 ：实现操作说明：
				1、
 修改历史 ：
--%>

<%@ page contentType="text/html;charset=gbk" %>

<%@ page import="java.util.Collection" %>
<%@ page import="com.iss.itreasury.util.Log" %>
<%@ page import="com.iss.itreasury.util.Constant" %>
<%@ page import="com.iss.itreasury.ebank.util.OBHtml"%>
<%@ page import="com.iss.itreasury.evoucher.util.VOUConstant"%>
<%@ page import="com.iss.itreasury.evoucher.print.bizlogic.QueryPrintBiz"%>
<%@ page import="com.iss.itreasury.util.PageControllerInfo"%>
<%@ page import="com.iss.itreasury.util.PageController"%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%
	/* 标题固定变量 */
	String strTitle = "[电子回单打印]";
%>
<%
	//页面控制变量
    String strNextPageURL = "";
	String strSuccessPageURL = "";
	String strFailPageURL = "";
	String strAction = "";
	String strActionResult = Constant.ActionResult.FAIL;
	String strContinueSave = "";

    try
	{
		/* 用户登录检测 */
        if (sessionMng.isLogin() == false)
        {
            OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E002");
        	out.flush();
        	return;
        }

        /* 判断用户是否有权限 */
        if (sessionMng.hasRight(request) == false)
        {
        	out.println(sessionMng.hasRight(request));
        	OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E003");
        	out.flush();
        	return;
        }
		
        //定义变量
		long lOfficeID = sessionMng.m_lOfficeID;//办事处
		long lCurrencyID = sessionMng.m_lCurrencyID;//币种

		String transIDs = "";
		String strTransNo = "";
		String[] templateIDs = null;

		strSuccessPageURL = (String)request.getAttribute("strSuccessPageURL");
		strFailPageURL = (String)request.getAttribute("strFailPageURL");

		String strTemp = "";
		strTemp = (String)request.getAttribute("strTransID");
		if(strTemp!=null && strTemp.length()>0)
		{
			transIDs = strTemp;
		}

		strTemp = (String)request.getAttribute("strTransNo");
		if(strTemp!=null && strTemp.length()>0)
		{
			strTransNo = strTemp;
		}

		if(request.getParameter("templateID")!=null)
		{
			templateIDs = request.getParameterValues("templateID");
		}

		Collection printOptions = null;
		QueryPrintBiz biz = new QueryPrintBiz();

		printOptions = biz.getPrintTemplateContentmany(strTransNo,transIDs,VOUConstant.PrintSection.EBANKCUSTOMER,lCurrencyID,lOfficeID,sessionMng.m_lUserID);		

		request.setAttribute("printOptions",printOptions);

		strActionResult = Constant.ActionResult.SUCCESS;


	}
	catch( Exception exp )
	{
		exp.printStackTrace();
		sessionMng.getActionMessages().addMessage(exp);
		%>
		<SCRIPT LANGUAGE="JavaScript">
		<!--
		window.close();
		//-->
		</SCRIPT>
	<%	 
	}
	//根据处理结果设置下一步跳转的目标页面
	strNextPageURL = (Constant.ActionResult.SUCCESS.equals(strActionResult))?
        strSuccessPageURL:strFailPageURL;
	Log.print("strNextPageURL :  " + strNextPageURL);

	//转向下一页面
	//构建页面分发时需要用到的实体
	PageControllerInfo pageControllerInfo = new PageControllerInfo();
	pageControllerInfo.setSessionMng(sessionMng);
	pageControllerInfo.setUrl(strNextPageURL);
	//分发
	RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
	
	rd.forward( request,response );
%>