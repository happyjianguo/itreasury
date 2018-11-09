<%--
 页面名称 ：c101.jsp
 页面功能 : 打印控件安装页面
 作    者 ：Boxu
 日    期 ：2007-9-6
 特殊说明 ：实现操作说明：
				1、
 修改历史 ：
--%>

<%@ page contentType="text/html;charset=gbk" %>

<%@ page import="com.iss.itreasury.util.Constant" %>
<%@ page import="com.iss.itreasury.ebank.util.*"%>
<%@ page import="com.iss.itreasury.evoucher.setting.dao.PrintSettingDao"%>
<%@ page import="com.iss.itreasury.settlement.bizdelegation.*"%>
<%@ page import="com.iss.itreasury.settlement.transcurrentdeposit.dataentity.TransCurrentDepositInfo"%>
<%@ page import="com.iss.itreasury.settlement.transfixeddeposit.dataentity.TransFixedOpenInfo"%>
<%@ page import="com.iss.itreasury.evoucher.setting.dataentity.TransformOperationDataEntity"%>
<%@ page import="com.iss.itreasury.settlement.bizdelegation.TransDiscountDelegation"%>
<%@ page import="com.iss.itreasury.settlement.transdiscount.dataentity.TransGrantDiscountInfo"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="com.iss.itreasury.settlement.util.SETTConstant"%>
<%@ page import="com.iss.itreasury.util.IException"%>

<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<script src="../../fceform/eprint/eprint.js"></script>
<script src="../../fceform/eprint/printer.js"></script>
<script src="../../fceform/js/fcpub.js"></script>
<script src="../../fceform/js/fcopendj.js"></script>

<body>
	<table align="center">
		<tr align="center">
			<td align="center">&nbsp;</td>
		</tr>
		<tr align="center">
			<td align="center">&nbsp;</td>
		</tr>
		<tr align="center">
			<td align="center">&nbsp;</td>
		</tr>
		<tr align="center">
			<td align="center">&nbsp;</td>
		</tr>
		<tr align="center">
			<td align="center"><font size="3"><b>页面出现"打印控件安装成功"，表示控件已安装完毕</b></font></td>
		</tr>
	</table>
</body>

<%	
	/* 标题固定变量 */
	String strTitle = "";
	String strSuccessPageURL = "";
	String strFailPageURL = "";
	String strNextPageURL = "";
	String strActionResult = Constant.ActionResult.FAIL;
	try
	{
    if (sessionMng.isLogin() == false)
    {
        OBHtml.showCommonMessage(out, sessionMng, strTitle, "",Long.parseLong("1"), "Gen_E002");
    	out.flush();
    	return;
    }

    /* 判断用户是否有权限 */
    if (sessionMng.hasRight(request) == false)
    {
    	out.println(sessionMng.hasRight(request));
    	OBHtml.showCommonMessage(out, sessionMng, strTitle, "",Long.parseLong("1"), "Gen_E003");
    	out.flush();
    	return;
    }

	//String strpath = getServletContext().getRealPath("/");
	String strTransNos = "";
	long lID = -1;
	String printXMLName = "";
	long transTypeID = -1;
	String printResult = "";
	String path = "";
	String strAction = "";

	if(request.getAttribute("path") != null)
	{
		path = (String)request.getAttribute("path");
	}
	
	if(request.getAttribute("strAction") != null)
	{
		strAction = (String)request.getAttribute("strAction");
	}
	try
	{
		if(strAction.equals("setup"))
		{
			printXMLName = "Example.xml";
			printResult = "";
			HashMap hashMap = new HashMap();

			PrintSettingDao PrintSettingdao = new PrintSettingDao();
			printResult = PrintSettingdao.getTemplateContent(path, printXMLName, hashMap);
			
			System.out.println("printResult===="+printResult);
			
			printResult = printResult.replaceAll("\"","\'");
			%>
				
		<script>
			var oTemps="<%=printResult%>";
			e_Preview(oTemps);
			
			function e_Preview(oTemp) 
			{
				var oTempXml = new ActiveXObject("Microsoft.XMLDOM");
				oTempXml.loadXML(oTemp);
				Printer.oXmlTemp = oTempXml;
				
				if (document.all("div_printPage")==null)
				{
					document.body.insertAdjacentHTML("BeforeEnd", "<div id='div_printPage' style='position:absolute; left:100;  top:202; z-index:2;'></div>");
				}
				
				document.all("div_printPage").innerHTML="<iframe name='_print_iframe' src='"+location.protocol+"//"+location.host+fcpubdata.Path+"/fceform/eprint/printdata.htm' width='0' height='0' border=1 NORESIZE=NORESIZE SCROLLING=no MARGINWIDTH=0 MARGINHEIGHT=0 FRAMESPACING=0 FRAMEBORDER=0></iframe>";		
			}
		</script>

		<%
		}
		else
		{
			throw new IException("Gen_E001");
		}
	}
	catch( Exception exp )
	{
		exp.printStackTrace();
		sessionMng.getActionMessages().addMessage(exp);
	}
	}
	catch( Exception exp )
	{
		exp.printStackTrace();
		sessionMng.getActionMessages().addMessage(exp);
	}
%>