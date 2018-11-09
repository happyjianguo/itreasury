<%--
 页面名称 ：c004.jsp
 页面功能 : 单据打印页面
 作    者 ：Boxu
 日    期 ：2007-7-12
 特殊说明 ：实现操作说明：
				1、
 修改历史 ：
--%>

<%@ page contentType="text/html;charset=gbk" %>

<%@ page import="com.iss.itreasury.util.Constant" %>
<%@ page import="com.iss.itreasury.ebank.util.*"%>
<%@ page import="com.iss.itreasury.evoucher.setting.dao.PrintSettingDao"%>
<%@ page import="com.iss.itreasury.evoucher.setting.dataentity.TransformOperationDataEntity"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="com.iss.itreasury.util.IException"%>
<%@ page import="com.iss.itreasury.util.Config"%>
<%@ page import="com.iss.itreasury.util.ConfigConstant"%>

<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<script src="../../fceform/eprint/eprint.js"></script>
<script src="../../fceform/eprint/printer.js"></script>
<script src="../../fceform/js/fcpub.js"></script>
<script src="../../fceform/js/fcopendj.js"></script>
<body></body>

<script language="javascript">
	var ar = new Array();
</script>
<%	
		/* 标题固定变量 */
		String strTitle = "";
		String strSuccessPageURL = "";
		String strFailPageURL = "";
		String strNextPageURL = "";
		String strActionResult = Constant.ActionResult.FAIL;
		
		String strTransNos = "";
		long lID = -1;
		long transTypeID = -1;
		long operationTypeID = -1;
		String[] printXMLName = null;
		
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
	    
	    OBHtml.ShowSendingHtml(out);

		String strpath = getServletContext().getRealPath("/");
		HashMap hashMap = null;
		String printResult = "";
		String path = "";

		if(request.getAttribute("path") != null)
		{
			path = (String)request.getAttribute("path");
		}

		if(request.getAttribute("lTransTypeID") != null)
		{
			transTypeID = Long.parseLong((String)request.getAttribute("lTransTypeID"));
		}
		
		if(request.getAttribute("operationTypeID") != null)
		{
			operationTypeID = Long.parseLong((String)request.getAttribute("operationTypeID"));
		}

		if(request.getAttribute("transIDs") != null)
		{
			lID = Long.parseLong((String)request.getAttribute("transIDs"));
		}

		if(request.getAttribute("strTransNos") != null)
		{
			strTransNos = (String)request.getAttribute("strTransNos");
		}

		if(request.getParameter("billName") != null)
		{
			printXMLName = request.getParameterValues("billName");
		}

		if(request.getAttribute("strSuccessPageURL") != null)
		{
			strSuccessPageURL = (String)request.getAttribute("strSuccessPageURL");
		}
		
		if(request.getAttribute("strFailPageURL") != null)
		{
			strFailPageURL = (String)request.getAttribute("strFailPageURL");
		}

		for(int i=0; i<printXMLName.length; i++)
		{
//			System.out.println("printXMLName=="+printXMLName[i]);
//			System.out.println("clientID=="+sessionMng.m_lClientID);
//			System.out.println("transTypeID=="+transTypeID);
//			System.out.println("officeID=="+sessionMng.m_lOfficeID);
//			System.out.println("currencyID=="+sessionMng.m_lCurrencyID);
//			System.out.println("Constant.NETBANK=="+Constant.NETBANK);

			//调用取数类
			//现在只限于结算业务
			TransformOperationDataEntity transEntity = new TransformOperationDataEntity();
			hashMap = transEntity.getOperationHashMap(transTypeID, lID, strTransNos);
			if(printXMLName[i]==null){
				printXMLName[i]="";
			}
			String isSignature = transEntity.isSignature(sessionMng.m_lClientID,transTypeID,sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID,Constant.NETBANK,printXMLName[i].toString());
			
			if( printXMLName[i] != null && printXMLName[i].length() >0 )
			{
				PrintSettingDao PrintSettingdao = new PrintSettingDao();
				PrintSettingdao.setIsSignature(isSignature);
				printResult = PrintSettingdao.getTemplateContent(path, printXMLName[i], hashMap);
				
				printResult = printResult.replaceAll("\"","\u0027");
			}
			else
			{
				throw new IException("Gen_E001");
			}
			
			%>
				<script language="javascript">
					var opop = "<%= printResult %>";
					ar.push(opop);
				</script>
			<%
		}
	}
	catch( Exception exp )
	{
		exp.printStackTrace();
		sessionMng.getActionMessages().addMessage(exp);
	}
%>
<script>
	var allsize = "<%=printXMLName.length%>";
	var printTime = "<%=Config.getInteger(ConfigConstant.GLOBAL_EVOUCHER_PRINTTIME, Constant.EvoucherPrint.ITIME)%>";
	var ss;
	var arrr = 0;
	
	//打印函数
	e_Preview();
	
	function e_Preview() 
	{
		var oTemp = ar[arrr];

		var oTempXml = new ActiveXObject("Microsoft.XMLDOM");
		oTempXml.loadXML(oTemp);
		Printer.oXmlTemp = oTempXml;

		//直接打印
		Printer.operate = "printtrue";
		
		if (document.all("div_printPage")==null)
		{
			document.body.insertAdjacentHTML("BeforeEnd", "<div id='div_printPage' style='position:absolute; left:100;  top:202; z-index:2;'></div>");
		}
		
		document.all("div_printPage").innerHTML="<iframe name='_print_iframe' src='"+location.protocol+"//"+location.host+fcpubdata.Path+"/fceform/eprint/printdata.htm' width='0' height='0' border=1 NORESIZE=NORESIZE SCROLLING=no MARGINWIDTH=0 MARGINHEIGHT=0 FRAMESPACING=0 FRAMEBORDER=0></iframe>";		

		arrr = arrr + 1;
		
		//打印提示
		showSending(arrr);
		
		ss = window.setTimeout("e_Preview()", printTime);
		
		if ( arrr > allsize-1 )
		{
			window.clearTimeout(ss);
			
			alert("打印完毕！");
			//window.close();
			window.location.href="../view/v003_P.jsp?lID=<%= String.valueOf(lID) %>&TransactionTypeID=<%= String.valueOf(transTypeID) %>&operationTypeID=<%=operationTypeID%>&TransNo=<%= strTransNos %>&strSuccessPageURL=../view/v003_P.jsp&strFailPageURL=../view/v003_P.jsp";
		}
	}
</script>