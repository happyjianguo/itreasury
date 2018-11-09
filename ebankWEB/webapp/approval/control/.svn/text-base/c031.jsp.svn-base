<%--
 页面名称 ：c031.jsp
 页面功能 : 审批流关联设置
 作    者 ：ypxu
 日    期 ：2007-4-16
 特殊说明 ：
 实现操作说明：
 修改历史 ：
--%>
<%@ page contentType="text/html;charset=gbk" %>	
<%@ page import="java.util.Collection" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.iss.itreasury.util.PageCtrlInfo" %>
<%@ page import="com.iss.itreasury.util.Log" %>	
<%@ page import="com.iss.itreasury.util.Constant" %>		
<%@ page import="com.iss.itreasury.ebank.approval.bizlogic.InutApprovalRelationBiz" %>	
<%@ page import="com.iss.itreasury.ebank.approval.dataentity.InutApprovalRelationInfo" %>	
<%@ page import="com.iss.itreasury.system.util.SYSHTML" %>
<%@ page import="com.iss.itreasury.ebank.util.OBHtml" %>
<%@page import="com.iss.itreasury.util.PageControllerInfo"%>
<%@page import="com.iss.itreasury.util.PageController"%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%	
	String strTitle = "审批流关联设置";
	//页面控制类
	PageCtrlInfo pageInfo = new PageCtrlInfo();	
	try
	{
		//用户登录检测 
        if (sessionMng.isLogin() == false)
        {
        	OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E002");
        	out.flush();
        	return;
        }

        // 判断用户是否有权限 
        if (sessionMng.hasRight(request) == false)
        {
            out.println(sessionMng.hasRight(request));
        	OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E003");
        	out.flush();
        	return;
        }
		
		//从request中获得页面控制信息
		pageInfo.convertRequestToDataEntity(request);
		
		//初始化查询类\参数类\结果类
		InutApprovalRelationBiz appRelationBiz = new InutApprovalRelationBiz();
		InutApprovalRelationInfo qInfo = new InutApprovalRelationInfo();
		Collection c_Result = null;
		//从request中获得查询条件信息
		qInfo.convertRequestToDataEntity(request);	



		long islowerunit = Long.parseLong(request.getParameter("isLowerUnit")) ;  
		qInfo.setIslowerunit(islowerunit);
		



		
		//查询
		c_Result = appRelationBiz.queryByConditions(qInfo);
		//将查询结果置入request中
		request.setAttribute("c_Result",c_Result);
		sessionMng.setQueryCondition("qInfo",qInfo);

	    pageInfo.success();
	}
	catch( Exception exp )
	{
		exp.printStackTrace();
		sessionMng.getActionMessages().addMessage(exp);	
		//出现异常,操作结果置为失败	
		pageInfo.fail();
	}	
	//将操作结果置入request中 
	request.setAttribute("strActionResult",pageInfo.getStrActionResult());

	//转向下一页面 
	Log.print("Next Page URL:"+pageInfo.getStrNextPageURL());
		//构建页面分发时需要用到的实体
	PageControllerInfo pageControllerInfo = new PageControllerInfo();
	pageControllerInfo.setSessionMng(sessionMng);
	pageControllerInfo.setUrl(pageInfo.getStrNextPageURL());
	//分发
	RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
	rd.forward( request,response );
%>