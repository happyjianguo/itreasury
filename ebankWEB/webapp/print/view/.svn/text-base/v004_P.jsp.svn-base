<%--
 页面名称 ：v004.jsp
 页面功能 : 单据打印
 作    者 ：Mliu
 日    期 ：2006-12-11
 特殊说明 ：
 实现操作说明：
 修改历史 ：
--%>
<%@ page contentType="text/html;charset=gbk" %>

<%@ page import="java.util.Collection" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="com.iss.itreasury.util.Log"%>
<%@ page import="com.iss.itreasury.util.Constant" %>
<%@ page import="com.iss.itreasury.ebank.util.OBHtml"%>
<%@ page import="com.iss.itreasury.settlement.print.IPrintTemplate" %>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>

<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<jsp:include flush="true" page="/ShowMessage.jsp" />
<%
String strTitle = "";
String strContext = request.getContextPath();
try
{
	/* 用户登录检测 */
    if (sessionMng.isLogin() == false)
    {
        OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E002");
    	out.flush();
    	return;
    }

    /* 判断用户是否有权限 */
    if (sessionMng.hasRight(request) == false)
    {
    	out.println(sessionMng.hasRight(request));
    	OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E003");
    	out.flush();
    	return;
    }

	Collection printOptions = null;
	Iterator iter = null;
	String printContent = "";

	if(request.getAttribute("printOptions")!=null)
	{
		printOptions = (Collection)request.getAttribute("printOptions");
	}

	IPrintTemplate.showPrintHead(out,true,"A4","",1,sessionMng.m_lOfficeID);
	System.out.println("printOptions is null?"+(printOptions==null));
%>
<safety:resources />
<html>
	<title>单据打印</title>
	<body>
<%
	if(printOptions!=null && printOptions.size()>0)
	{
		iter = printOptions.iterator();
		while(iter.hasNext())
		{
			printContent = (String)iter.next();
%>
	<table width="100%">
	<%
		out.print(printContent);
	%>
	</table>

	<br>
	<br>
	<br clear=all style='page-break-before:always'>
<%
		}
	}
%>
	</body>

<script language= "javascript">
	factory.printing.Print(true);
</script>

</html>
<%
	}
	catch(Exception ie)
	{
		ie.printStackTrace();
		Log.print(ie.getMessage());
	}
%>

<%@ include file="/common/SignValidate.inc" %>