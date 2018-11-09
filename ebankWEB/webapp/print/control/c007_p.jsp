<%--
 页面名称 ：c007_p.jsp
 页面功能 : 单据批量打印--控制页面
 作    者 ：Boxu
 日    期 ：2007-8-9
 特殊说明 ：实现操作说明：
				1、
 修改历史 ：
--%>

<%@ page contentType="text/html;charset=gbk" %>

<%@ page import="java.util.Collection"%>
<%@ page import="com.iss.itreasury.util.Log" %>
<%@ page import="com.iss.itreasury.util.Constant" %>
<%@ page import="com.iss.itreasury.ebank.util.*"%>
<%@ page import="com.iss.itreasury.evoucher.util.VOUConstant" %>
<%@ page import="com.iss.itreasury.evoucher.print.bizlogic.QueryPrintBiz"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="com.iss.itreasury.util.PageControllerInfo"%>
<%@ page import="com.iss.itreasury.util.PageController"%>

<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<%	
/* 标题固定变量 */
	String strTitle = "";
	String strSuccessPageURL = "";
	String strFailPageURL = "";
	String strNextPageURL = "";
	String strActionResult = Constant.ActionResult.FAIL;
	try
	{
		 if (sessionMng.isLogin() == false)
    {
        OBHtml.showCommonMessage(out, sessionMng, strTitle, "",Long.parseLong("1"), "Gen_E002");
    	out.flush();
    	return;
    }

    /* 判断用户是否有权限 */
    if (sessionMng.hasRight(request) == false)
    {
    	out.println(sessionMng.hasRight(request));
    	OBHtml.showCommonMessage(out, sessionMng, strTitle, "",Long.parseLong("1"), "Gen_E003");
    	out.flush();
    	return;
    }
		
		// data define start
		long lCurrencyID = sessionMng.m_lCurrencyID;//币种
		long lOfficeID = sessionMng.m_lOfficeID;//办事处

		strSuccessPageURL = (String)request.getAttribute("strSuccessPageURL");
		strFailPageURL = (String)request.getAttribute("strFailPageURL");

		String[] checkPrint = null;
		String transIDs = "";
		String strTransNos = "";
		String strPrintAction = "";
		String transTypeID = "";
		String strPageLoaderKey = "";
		
		if(request.getAttribute("_pageLoaderKey") != null)
		{
			strPageLoaderKey = (String)request.getAttribute("_pageLoaderKey");
			request.setAttribute("_pageLoaderKey",strPageLoaderKey);
		}

		if(request.getAttribute("strPrintAction") != null)
		{
			strPrintAction = (String)request.getAttribute("strPrintAction");
		}

		if(request.getAttribute("lTransTypeID") != null)
		{
			transTypeID = (String)request.getAttribute("lTransTypeID");
		}
		
		if(request.getParameter("printCheck") != null)
		{
			checkPrint = request.getParameterValues("printCheck");
			for(int i=0;i<checkPrint.length;i++)
			{
				if(i == 0)
				{
					transIDs += checkPrint[i].split("####")[0];
				}
				else
				{
					transIDs += ","+checkPrint[i].split("####")[0];
				}
				Log.print("checkPrint"+i+":"+checkPrint[i].split("####")[0]);
			}
		}
		else
		{
			transIDs = (String)request.getAttribute("printCheck");
		}

		if(request.getAttribute("strTransNos") != null)
		{
			strTransNos = (String)request.getAttribute("strTransNos");
			if(!strPrintAction.equals("singalPrint"))
			{
				strTransNos = strTransNos.substring(1);
			}			
		}
		
		System.out.println("***************-----------strTransNos:"+strTransNos);
		System.out.println("***************-----------transIDs:"+transIDs);

		long lDeptID = -1;
		lDeptID = VOUConstant.PrintSection.EBANKCUSTOMER;
		
		strSuccessPageURL = "../control/c006_p.jsp";
		strFailPageURL = "../control/c001_P.jsp";
		
		Collection printOptions = null;
	    QueryPrintBiz biz = new QueryPrintBiz();
	    
		printOptions = biz.getPrintTemplateContentmany(strTransNos, transIDs, VOUConstant.PrintSection.EBANKCUSTOMER, lCurrencyID, lOfficeID, Constant.ModuleType.SETTLEMENT);		
		
		request.setAttribute("printOptions",printOptions);
		strActionResult = Constant.ActionResult.SUCCESS;
	}
	catch( Exception exp )
	{
		exp.printStackTrace();
		sessionMng.getActionMessages().addMessage(exp);
		//出现异常,操作结果置为失败	
		strActionResult = Constant.ActionResult.FAIL;
		
		request.setAttribute("strSuccessPageURL", "/print/view/v005_P.jsp");
		request.setAttribute("strFailPageURL", "/print/view/v005_P.jsp");
	}

	//页面跳转
	
	//根据处理结果设置下一步跳转的目标页面
	strNextPageURL = (Constant.ActionResult.SUCCESS.equals(strActionResult))?strSuccessPageURL:strFailPageURL;
	//request.setAttribute("strSuccessPageURL", strSuccessPageURL);
	
	//转向下一页面
	//构建页面分发时需要用到的实体
	PageControllerInfo pageControllerInfo = new PageControllerInfo();
	pageControllerInfo.setSessionMng(sessionMng);
	pageControllerInfo.setUrl(strNextPageURL);
	//分发
	RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
	
	rd.forward( request,response );
%>