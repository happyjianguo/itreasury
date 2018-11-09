<%--
/*
 * 程序名称：batchck002-c.jsp
 * 功能说明：批量复核查询控制
 * 作　　者：菅中尉
 * 完成日期：2007年04月21日
 */
--%>
<%@ page contentType="text/html;charset=gbk"%>
<%@ page
	import="com.iss.itreasury.ebank.util.*,com.iss.itreasury.settlement.util.*,com.iss.itreasury.util.*"%>
<%@ page
	import="com.iss.itreasury.ebank.obfinanceinstr.dataentity.FinanceInfo"%>
<%@ page
	import="com.iss.itreasury.ebank.obfinanceinstr.dao.OBFinanceInstrDao"%>
<%@ page
	import="java.util.*"%>

<jsp:useBean id="sessionMng" scope="session"
	class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ include file="/common/common.jsp"%>
<%String strContext = request.getContextPath();%>
<%!/* 标题固定变量 */
	String strTitle = "[批量复核]";%>
<%
	/* 实现菜单控制 */
	long lShowMenu = OBConstant.ShowMenu.YES;
	String strMenu = (String) request.getAttribute("menu");
	if ((strMenu != null) && (strMenu.equals("hidden"))) {
		lShowMenu = OBConstant.ShowMenu.NO;
	}
%>

<%
	/* 实例化信息类 */
	//实体
	FinanceInfo info = new FinanceInfo();
	OBFinanceInstrDao obstr = new OBFinanceInstrDao();//查询方法
	List infoList = null;
	String strStartDate = null;//上一个页面传来的开始日期
	String strEndDate = null;//上个页面传来的结束日期
	String status = null;//存储状态
	//查询类

	/* 用户登录检测与权限校验及文件头显示 */
	try {
		//用户登录检测 
		if (sessionMng.isLogin() == false) {
			OBHtml.showCommonMessage(out, sessionMng, strTitle, "", 1,
			"Gen_E002");
			out.flush();
			return;
		}

		// 判断用户是否有权限 
		if (sessionMng.hasRight(request) == false) {
			out.println(sessionMng.hasRight(request));
			OBHtml.showCommonMessage(out, sessionMng, strTitle, "", 1,
			"Gen_E003");
			out.flush();
			return;
		}
		
		OBHtml.showOBHomeHead(out, sessionMng, strTitle, lShowMenu);
%>
<%
		//获得上一页面上的信息，并装入实体中
		strStartDate = (String)request.getParameter("strStartDate");
		strEndDate = (String)request.getParameter("strEndDate");
		status = (String)request.getParameter("sStatus");
		info.setDtDepositBillCheckdate(DataFormat.getDateTime(strStartDate));//用它储存开始日期
		info.setDtDepositBillInputdate(DataFormat.getDateTime(strEndDate));//用它储存结束日期
		info.setNUserID(sessionMng.m_lUserID);
		info.setSStatus((String)request.getParameter("sStatus"));
		
		info.setClientID( sessionMng.m_lClientID );//网银指令所属客户ID
		//查出结果
		infoList = obstr.findBatchInfoByDatehl(info);
		request.setAttribute("infoList", infoList);
		request.setAttribute("strStartDate", strStartDate);
		request.setAttribute("strEndDate", strEndDate);
		
		//结果跳转		
		PageControllerInfo pageControllerInfo = new PageControllerInfo();
		pageControllerInfo.setSessionMng(sessionMng);
		pageControllerInfo.setUrl(strContext + "/capital/check/batchck001-v.jsp");
		//分发
		RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
		rd.forward(request,response);
	}
	catch(IException ie)
	{
		OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
		Log.print("---------"+ie.toString());
		return;
	}	
		
 %>
