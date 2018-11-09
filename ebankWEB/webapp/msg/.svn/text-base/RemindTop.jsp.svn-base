<%@ page contentType="text/html;charset=gbk" %>

<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"/>

<%@ page import="com.iss.itreasury.ebank.obawake.dataentity.OBAwakeCondition,
                 com.iss.itreasury.util.*"%>
		
<%
	long lClientID = sessionMng.m_lClientID;
	long lCurrencyID = sessionMng.m_lCurrencyID;
	long lUserID = sessionMng.m_lUserID;
	long lOfficeID = sessionMng.m_lOfficeID;
	StringBuffer sAwake = new StringBuffer();
	
	System.out.println("lClientID="+lClientID);
	System.out.println("lCurrencyID="+lCurrencyID);
	
	if (lClientID>0)
	{
		OBAwakeCondition.AwakeMSG.put("lCurrencyID",String.valueOf(lCurrencyID));
		OBAwakeCondition.AwakeMSG.put("lClientID",String.valueOf(lClientID));
		OBAwakeCondition.AwakeMSG.put("lUserID",String.valueOf(lUserID));
		OBAwakeCondition.AwakeMSG.put("lOfficeID",String.valueOf(lOfficeID));
		
		String strKey = String.valueOf(lClientID)+String.valueOf(lCurrencyID)+String.valueOf(lUserID)+String.valueOf(lOfficeID);
		if (OBAwakeCondition.AwakeMSG.get(strKey) != null)
		{
			sAwake.append(OBAwakeCondition.AwakeMSG.get(strKey));
		}
	}
	else
	{
		sAwake.append("没有登录！");
	}
	if(sAwake == null || sAwake.length() <= 0)
	{
		sAwake.append("没有提醒！");
	}
%>
<html>
<head>
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<meta http-equiv="refresh" content="40">
<link rel="stylesheet" href="/itreasury/css/style.css" type="text/css">
</head>

<body bgcolor="#FFFFFF">
<form method="post" action="RemindTop.jsp?lClientID=<%=lClientID%>" name="form1">

<SCRIPT LANGUAGE=javascript>
 	window.onload=setValue; 
	function setValue()
	{ 
	/*
			if(parent.m_topNum>0)
			{
				parent.bottomFrame.marQ.innerHTML="<%=sAwake.toString()%>";
			}
			parent.m_topNum++;
	*/
		if(parent.m_topNum>0)
		{
			parent.bottomFrame.marQ.innerHTML="<%=sAwake.toString()%>";
		}
		parent.m_topNum++;
	}
		 
</SCRIPT>    
</form>
</body>
</html>
 
