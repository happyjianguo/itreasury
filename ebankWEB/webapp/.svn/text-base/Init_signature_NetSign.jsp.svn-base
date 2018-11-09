<%@page language="java" pageEncoding="GBK"%>
<%@page import="com.iss.itreasury.safety.signature.*"%>
<%@ page import="com.iss.itreasury.util.PageControllerInfo"%>
<%@ page import="com.iss.itreasury.util.PageController"%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%String strContext = request.getContextPath();%>
<%
try {		
	String strNextPageURL = "/PasswordValidate.jsp";
	boolean blnResult = LoginAuthentication.validateByNetSign(request);
	if (blnResult) {
		//构建页面分发时需要用到的实体
		PageControllerInfo pageControllerInfo = new PageControllerInfo();
		pageControllerInfo.setSessionMng(sessionMng);
		pageControllerInfo.setUrl(strNextPageURL);
		RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
		rd.forward(request,response);
	}
} catch (Exception e) {
	e.printStackTrace();
	sessionMng.getActionMessages().addMessage("初始化数字证书失败");
	response.sendRedirect(strContext + "/Index.jsp");
	return;
}
%>
