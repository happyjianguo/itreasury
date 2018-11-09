<%--
/*
 * 程序名称：batchck003-c.jsp
 * 功能说明：批量复核查询控制
 * 作　　者：菅中尉
 * 完成日期：2007年04月23日
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
	String sbatchno = null;//批次号
	String strSuccessURL = null;//成功页面
	String strFaileURL = null;//失败页面
	//查询类
	
		sbatchno = (String)request.getParameter("sbatchno");
		System.out.println("传入的批次号："+sbatchno);
		strSuccessURL = (String)request.getParameter("strSuccessURL");
		System.out.println("传入的成功地址："+strSuccessURL);
		strFaileURL = (String)request.getParameter("strFaileURL");

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
		info.setSBatchNo(sbatchno.trim());//存储批
		//查出结果
		System.out.println("aaa传入的成功地址：" );
		infoList = obstr.findFinanceInfoByBatchno(info);
		System.out.println("bbbs传入的成功地址：" );
		System.out.println("上个页面查询的结果："+infoList);
		request.setAttribute("infoList", infoList);
		
		//结果跳转
	
		PageControllerInfo pageControllerInfo = new PageControllerInfo();
		pageControllerInfo.setSessionMng(sessionMng);
		pageControllerInfo.setUrl(strContext + "/capital/check/batchck004-v.jsp?sbatchno=" + sbatchno);
		//分发
		RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
		rd.forward(request,response);
		
	}
	catch(IException ie)
	{
		OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
		Log.print("batchck003-c异常消息："+ie.toString());
		return;
	}	
		
 %>
