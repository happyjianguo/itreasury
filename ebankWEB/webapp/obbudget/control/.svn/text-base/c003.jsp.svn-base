<%--
/*
 * 程序名称：v001.jsp
 * 功能说明：网银预算新增页
 * 作　　者：banreyliu
 * 完成日期：2006-09-04
 */
--%>
<%@ page contentType="text/html;charset=gbk"%>
<!-- 引入需要的类,尽量不用.* -->
<%@ page import="com.iss.itreasury.util.Log"%>
<%@ page import="com.iss.itreasury.util.IException"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtml"%>
<%@ page import="com.iss.itreasury.util.PageCtrlInfo"%>
<%@ page import="com.iss.itreasury.ebank.obbudget.bizlogic.OBBudgetBiz"%>
<%@ page import="com.iss.itreasury.ebank.obbudget.dataentity.OBBudgetInfo"%>
<%@ page import="java.util.*"%>
<%@ page import="com.iss.itreasury.util.DataFormat"%>
<%@ page import="java.sql.Timestamp"%>
<%@ page import="com.iss.itreasury.ebank.bizdelegation.OBBudgetDelegation"%>
<%@ page import="com.iss.itreasury.util.Constant"%>
<%@ page import="com.iss.itreasury.util.PageControllerInfo"%>
<%@ page import="com.iss.itreasury.util.PageController"%>
<!-- 引入此用户当前回话的Session -->
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<%
	 /**
	 * 页面控制类
	 */
	String strTitle = "预算新增";
	PageCtrlInfo pageInfo = new PageCtrlInfo();

	/** 定义业务实体类 */
	OBBudgetInfo info = new OBBudgetInfo();

	try {
		/** 权限检查 **/
		System.out
		.println("=================进入页面../control/c001.jsp=========");

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

		long period = -1;
		String strTemp = "";
		String startDate = "";
		strTemp = (String) request.getAttribute("period");
		if (strTemp != null && !strTemp.equals("")) {
			period = Long.parseLong(strTemp);
		}

		strTemp = (String) request.getParameter("startDate");
		if (strTemp != null && !strTemp.equals("")) {
			startDate = strTemp;
		}

		info.convertRequestToDataEntity(request);

		request.setAttribute("period", String.valueOf(period));
		request.setAttribute("startdate", startDate);

		OBBudgetBiz biz = new OBBudgetBiz();
		long retlong = -1;
		retlong = biz.check(info);
		if (retlong == Constant.FALSE) {
			//sessionMng.getActionMessages().addMessage("保存成功!");
			//pageInfo.setStrNextPageURL("../view/v001.jsp");
			pageInfo.success();
			pageInfo
			.setStrNextPageURL("../view/v001.jsp?ID=" + retlong);
		} else {
			pageInfo.setStrNextPageURL(pageInfo.getStrSuccessPageURL());
		}

		request.setAttribute("strActionResult", pageInfo
		.getStrActionResult());

		System.out.println("Next Page URL:"
		+ pageInfo.getStrNextPageURL());
		//构建页面分发时需要用到的实体
		PageControllerInfo pageControllerInfo = new PageControllerInfo();
		pageControllerInfo.setSessionMng(sessionMng);
		pageControllerInfo.setUrl(pageInfo.getStrNextPageURL());
		//分发
		RequestDispatcher rd = request
		.getRequestDispatcher(PageController
				.getDispatcherURL(pageControllerInfo));
		rd.forward(request, response);

	} catch (IException ie) {
		OBHtml.showExceptionMessage(out, sessionMng, ie, strTitle, "",
		1);
		return;
	}
%>
<input type="hidden" name="clientID" value=<%=sessionMng.m_lClientID%>>
<input type="hidden" name="ModuleID" value=<%=sessionMng.m_lModuleID%>>
<input type="hidden" name="OfficeID" value=<%=sessionMng.m_lOfficeID%>>
<input type="hidden" name="CurrencyID" value=<%=sessionMng.m_lCurrencyID%>>
<input type="hidden" name="transCode" value="">
<input type="hidden" name="sname" value="<%=info.getSname()%>">
<input type="hidden" name="accountID" value="<%=info.getAccountID()%>">
<input type="hidden" name="note" value="<%=info.getNote()%>">
