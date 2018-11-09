<%@ page contentType="text/html;charset=gbk" %>
<%@ page import="com.iss.itreasury.util.Env"%>
<%String strContext = request.getContextPath();%>
<jsp:include page="/ShowMessage.jsp"/>
<html>  
<head>
	<title><%=Env.getClientName()%>金融服务系统</title>
	<meta http-equiv="Content-Type" content="text/html; charset=gbk">
	<script type="text/javascript" src="/webob/js/util.js"></script>
</head>

<frameset rows="105,*" resize="no"   border="0">
	<frame src="<%=strContext%>/Authenticate.jsp" name="header" noresize="noresize" scrolling="no"/> 
	<frame name="menu" src="<%=strContext%>/NextMenu.jsp" noresize="noresize" scrolling="auto" frameborder=0  />
</frameset><noframes></noframes>
</html>