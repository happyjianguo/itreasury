<%--
/*
 * 程序名称：batsign002-c.jsp
 * 功能说明：批量签认查询页面
 * 作　　者：菅中尉
 * 完成日期：2008年03月31日
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
	String strTitle = "[批量签认]";%>
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
	// 为获取相应的签认金额
	long userID= -1;
	long clientID =-1;
	long currencyID = -1;
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
		String strTemp = "";             
		strTemp = (String)request.getParameter("clientID");//获得客户ID
		if (strTemp != null && !strTemp.equals(""))
		{			
			clientID = Long.parseLong(strTemp); 
			System.out.println("clientID========"+clientID);
		}
		strTemp = (String)request.getParameter("UserID");//获得客户ID
		if (strTemp != null && !strTemp.equals(""))
		{			
			userID = Long.parseLong(strTemp); 	
			System.out.println("userID========"+userID);
		}
		strTemp = (String)request.getParameter("CurrencyID");//获得客户ID
		if (strTemp != null && !strTemp.equals(""))
		{			
			currencyID = Long.parseLong(strTemp); 	
			System.out.println("currencyID========"+currencyID);
		}
		//获得上一页面上的信息，并装入实体中
		strStartDate = (String)request.getParameter("strStartDate");
		strEndDate = (String)request.getParameter("strEndDate");
		// 交易指令状态
		status = (String)request.getParameter("SelectStatus");      
		info.setDtDepositBillCheckdate(DataFormat.getDateTime(strStartDate));//用它储存开始日期
		info.setDtDepositBillInputdate(DataFormat.getDateTime(strEndDate));//用它储存结束日期
		info.setSStatus((String)request.getParameter("SelectStatus"));
		info.setStatus(Long.parseLong(status));
		info.setUserID(userID);
		info.setClientID(clientID);
		info.setCurrencyID(currencyID);
		//查出结果
		infoList = obstr.findBatchInfoByDateforsign(info);
		request.setAttribute("infoList", infoList);
		request.setAttribute("strStartDate", strStartDate);
		request.setAttribute("strEndDate", strEndDate);
		request.setAttribute("SelectStatus", status);
		//结果跳转		
		PageControllerInfo pageControllerInfo = new PageControllerInfo();
		pageControllerInfo.setSessionMng(sessionMng);
		pageControllerInfo.setUrl(strContext + "/capital/sign/batsign001-v.jsp");
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
