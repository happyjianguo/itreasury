<%@ page contentType="text/html;charset=gbk" %>
<%@ page import="com.iss.itreasury.safety.fingercert.FingerPrintCret"%>
<%@page import="com.iss.itreasury.ebank.util.OBHtml"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>指纹认证处理页面</title>
	<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
  </head>
  <body>
<%
	String strAction = "";
	String returnValue = "";
	try{
			//请求检测
			/* 判断用户是否有权限 */
			if (sessionMng.hasRight(request) == false) {
				out.println(sessionMng.hasRight(request));
				OBHtml.showCommonMessage(out, sessionMng, "", "",1, "Gen_E003");
				out.flush();
				return;
			}
			System.out.println("---------------进入/NASApp/iTreasury-ebank/fingerprintControl.jsp---------------------------");

	        //读取数据		
	        strAction = (String)request.getAttribute("strAction");
			//根据请求操作，完成业务处理的调用
			if("fingerprint".equals(strAction)){
		        String fingerPrintValue = (String)request.getAttribute("Ver");
		        returnValue = FingerPrintCret.getFingerPrintCret().checkFingerPrint(request,fingerPrintValue,sessionMng.m_strLogin);
			}
	}
	catch(Exception e)
	{
		e.printStackTrace();
		returnValue = e.getMessage();
	}
	out.print("<div id='returnValue'>"+returnValue+"</div>");
	%>
		
  </body>
</html>