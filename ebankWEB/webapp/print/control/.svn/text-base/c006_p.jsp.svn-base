<%--
 页面名称 ：c006_p.jsp
 页面功能 : 批量打印页面
 作    者 ：Boxu
 日    期 ：2007-7-12
 特殊说明 ：实现操作说明：
				1、
 修改历史 ：
--%>

<%@ page contentType="text/html;charset=gbk" %>

<%@ page import="java.util.Collection"%>
<%@ page import="com.iss.itreasury.util.Constant" %>
<%@ page import="com.iss.itreasury.ebank.util.*"%>
<%@ page import="com.iss.itreasury.evoucher.setting.dao.PrintSettingDao"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="com.iss.itreasury.evoucher.setting.dataentity.TransformOperationDataEntity"%>
<%@ page import="com.iss.itreasury.evoucher.setting.dataentity.BillrelationSetInfo"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="com.iss.itreasury.util.IException"%>
<%@ page import="com.iss.itreasury.util.Config"%>
<%@ page import="com.iss.itreasury.util.ConfigConstant"%>
<%@ page import="com.iss.itreasury.settlement.util.SETTConstant"%>

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
	Collection printOptions = null;
	
	try
	{
		if (sessionMng.isLogin() == false)
    {
        OBHtml.showCommonMessage(out, sessionMng, strTitle, "",Long.parseLong("1"), "Gen_E002");
    	out.flush();
    	return;
    }

	OBHtml.ShowSendingHtml(out);

    /* 判断用户是否有权限 */
    if (sessionMng.hasRight(request) == false)
    {
    	out.println(sessionMng.hasRight(request));
    	OBHtml.showCommonMessage(out, sessionMng, strTitle, "",Long.parseLong("1"), "Gen_E003");
    	out.flush();
    	return;
    }

		String strpath = getServletContext().getRealPath("/");
		String strTransNos = "";
		long lID = -1;
		String[] printXMLName = null;
		long transTypeID = -1;
		HashMap hashMap = null;
		String printResult = "";
		String path = "";
        
        String sMessage = "";
        
		if(request.getAttribute("path") != null)
		{
			path = (String)request.getAttribute("path");
		}
		if(request.getAttribute("printOptions") != null)
		{
			printOptions = (Collection)request.getAttribute("printOptions");
		}
		if(request.getAttribute("strSuccessPageURL") != null)
		{
			strSuccessPageURL = (String)request.getAttribute("strSuccessPageURL");
		}
		
		if(request.getAttribute("strFailPageURL") != null)
		{
			strFailPageURL = (String)request.getAttribute("strFailPageURL");
		}

		//if(path==null || path.equals("") || path.length() < 0)
		//{
			//网银需要注意的地方，这个路径根据部署的方式路径不同
		path = Config.getProperty(ConfigConstant.GLOBAL_EVOUCHER_FILEPATH);
		//}
		System.out.println("***************-----------path:"+path);

        if(printOptions != null && printOptions.size() > 0)
        {
            Iterator iter  = printOptions.iterator();
        	
		    for(int k = 0; k < printOptions.size(); k++)
			{
				BillrelationSetInfo info = (BillrelationSetInfo)iter.next();
				
				//调用取数类
				//现在只限于结算业务
				TransformOperationDataEntity transEntity = new TransformOperationDataEntity();
				hashMap = transEntity.getOperationHashMap(info.getRelationtypeid(), info.getTransID(), info.getTransNo());
				String isSignature = transEntity.isSignature(sessionMng.m_lClientID,info.getRelationtypeid(),sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID,Constant.NETBANK,info.getTemplatename());
				
				hashMap.put("thiscanbesigness",info.getPrintCount()+"");	//添加打印次数	
				
				if( info.getTemplatename() != null && info.getTemplatename().length() >0 )
				{
					PrintSettingDao PrintSettingdao = new PrintSettingDao();
					PrintSettingdao.setIsSignature(isSignature);
					printResult = PrintSettingdao.getTemplateContent(path,  info.getTemplatename(), hashMap);
					
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
	}
	catch( Exception exp )
	{
		exp.printStackTrace();
		sessionMng.getActionMessages().addMessage(exp);
	}
%>
<script>
	var allsize = "<%=printOptions.size()%>";
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
			window.close();
		}
	}
</script>