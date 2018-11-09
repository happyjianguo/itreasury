<%--
/*
 * 程序名称	c002.jsp
 * 功能说明：网银预算新增页
 * 作　　者：banreyliu
 * 完成日期：2006-09-04
 */
--%>
<%@ page contentType="text/html;charset=gbk" %>
<!-- 引入需要的类,尽量不用.* -->
<%@ page import="com.iss.itreasury.util.Log"%>
<%@ page import="com.iss.itreasury.util.IException"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtml"%>
<%@ page import="com.iss.itreasury.util.PageCtrlInfo"%>
<%@ page import="com.iss.itreasury.ebank.obbudget.bizlogic.OBBudgetBiz"%>
<%@ page import="com.iss.itreasury.ebank.obbudget.dataentity.OBBudgetInfo"%>
<%@ page import="com.iss.itreasury.util.PageControllerInfo"%>
<%@ page import="com.iss.itreasury.util.PageController"%>
<!-- 引入此用户当前回话的Session -->
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<%
 
     String strTitle = "预算复核"; 
     String action = "";
PageCtrlInfo pageInfo = new PageCtrlInfo();

/** 定义业务实体类 */
OBBudgetInfo info = null;

try {
	 /** 权限检查 **/
    System.out.println("=================进入页面../control/c002.jsp=========");


     // 用户登录检测 
		if (sessionMng.isLogin() == false) {
			OBHtml.showCommonMessage(out, sessionMng, "", "", 1,
			"Gen_E002");
			out.flush();
			return;
		}

		// 判断用户是否有权限 
		if (sessionMng.hasRight(request) == false) {
			out.println(sessionMng.hasRight(request));
			OBHtml.showCommonMessage(out, sessionMng, "", "", 1,
			"Gen_E003");
			out.flush();
			return;
		}

	pageInfo.convertRequestToDataEntity(request);
	//System.out.println("barney@@@@@@"+pageInfo);
	if(request.getParameter("action")!=null)
	{
		 action = (String)request.getParameter("action");
		 System.out.println("barney@@@@@@@@@@@"+action);
	}
	
	info = new OBBudgetInfo();
 
	info.convertRequestToDataEntity(request);
	System.out.println(info);
 

	OBBudgetBiz biz = new OBBudgetBiz();
	 if(action.equalsIgnoreCase("match"))
	 {
	 	long retlong = biz.matching(info,sessionMng.m_lUserID);
		if(retlong > 0)
		{
			System.out.println("==========match=========match=========match");		
			OBBudgetInfo rInfo = new OBBudgetInfo();
			rInfo = biz.findByID(retlong);
			System.out.println("barney"+rInfo);
			request.setAttribute("rInfo",rInfo);
			pageInfo.success();
	    	//pageInfo.setStrNextPageURL("../view/v003.jsp");
		}
		else
		{System.out.println("没有相匹配的预算，请重新录入");
		request.setAttribute("CInfo",info);
		 pageInfo.setStrNextPageURL("../view/v002.jsp?RID=-1");
		}
	 }
	 else if(action.equalsIgnoreCase("check"))
	 {System.out.println("==========check=========check=========check"+info.getId());	
	 	long retlong = biz.check(info.getId(),sessionMng.m_lUserID);
		if(retlong > 0)
		{		 
			pageInfo.success();
	    	pageInfo.setStrNextPageURL("../view/v002.jsp?RID="+retlong);
		}
		else
		{System.out.println("复核失败");
		 pageInfo.setStrNextPageURL("../view/v002.jsp?RID=-2");
		}
	 }
	System.out.println("Next Page URL:"+pageInfo.getStrNextPageURL());	
	//构建页面分发时需要用到的实体
	PageControllerInfo pageControllerInfo = new PageControllerInfo();
	pageControllerInfo.setSessionMng(sessionMng);
	pageControllerInfo.setUrl(pageInfo.getStrNextPageURL());
	//分发
	RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));

	rd.forward( request,response );
	
	 }
	catch (IException ie)
	{	
        OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);		
		return;
    }
%>
