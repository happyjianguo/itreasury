<%@ page contentType="text/html;charset=gbk" %>
<%@ page import="com.iss.itreasury.safety.fingercert.FingerPrintCret"%>
<%@page import="com.iss.itreasury.ebank.util.OBHtml"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>ָ����֤����ҳ��</title>
	<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
  </head>
  <body>
<%
	String strAction = "";
	String returnValue = "";
	try{
			//������
			/* �ж��û��Ƿ���Ȩ�� */
			if (sessionMng.hasRight(request) == false) {
				out.println(sessionMng.hasRight(request));
				OBHtml.showCommonMessage(out, sessionMng, "", "",1, "Gen_E003");
				out.flush();
				return;
			}
			System.out.println("---------------����/NASApp/iTreasury-ebank/fingerprintControl.jsp---------------------------");

	        //��ȡ����		
	        strAction = (String)request.getAttribute("strAction");
			//����������������ҵ����ĵ���
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