<%@ page contentType="text/html;charset=gbk"%>
<%@ page import="com.iss.itreasury.util.*"%>
<jsp:useBean id="sessionMng" scope="session"
	class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<%
	String strContext = request.getContextPath();
	String strIssuerDnArray = Config.getProperty(
			ConfigConstant.GLOBAL_TROY_ISSUERDNARRAY, "");
%>
<HTML>
<head>
<safety:resources />
<link href="/itreasury/css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
<safety:loginCertList 
	issuerDnArray='<%=strIssuerDnArray %>'	
	subjectCommonName='<%=sessionMng.m_strCertCN %>'
	serialNumber='<%=sessionMng.m_strCertSerialNumber %>'
	action='<%=strContext + "/Init_iTrus.jsp"%>' />
</BODY>
</HTML>
