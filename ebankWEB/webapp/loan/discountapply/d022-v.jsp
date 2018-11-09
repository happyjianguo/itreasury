<%--
 页面名称 ：d022-v.jsp
 页面功能 : 新增贴现申请-打印主页面
 作    者 ：gqzhang
 日    期 ：2004年1月
 特殊说明 ：实现操作说明：
 修改历史 ：
--%>
<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import = "java.sql.*,
                   com.iss.itreasury.ebank.util.*,
				   com.iss.itreasury.util.*,
				   com.iss.itreasury.loan.util.*,
				   com.iss.itreasury.ebank.obdiscountapply.bizlogic.*,
				   com.iss.itreasury.ebank.obdiscountapply.dataentity.*,
				   com.iss.itreasury.ebank.obsystem.dataentity.*,
				   com.iss.itreasury.loan.loancommonsetting.dataentity.ClientInfo"
%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<jsp:useBean id="Magnifier" scope="request" class="com.iss.itreasury.loan.util.LOANMagnifier"/>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>

<%
	/* 标题固定变量 */
	String strTitle = "[贴现申请]";
%>	
<%
  try
  {
	Log.print("*******进入页面--ebank/loan/discountapply/d022-v.jsp*******");
	
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
		
		//定义变量
		String strTemp = "";
		long lID = -1;//贴现申请Id
		
		strTemp = (String)request.getAttribute("lID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lID = Long.valueOf(strTemp).longValue();
		}
		
	    //显示文件头
      //  OBHtml.showOBHomeHead(out, sessionMng, strTitle, OBConstant.ShowMenu.NO);
%>		
<html>
<head>
<Script Language="JavaScript">
self.resizeTo(300,380);
self.moveTo(300,180);
</SCRIPT>
<title>打印</title>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<style type="text/css">

<!--
Table {  font-family: 宋体,Arial,Helvetica,Helv; font-size: 12px;}
BODY {
	BORDER-RIGHT: 0px; PADDING-RIGHT: 0px; BORDER-TOP: 0px; PADDING-LEFT: 0px; PADDING-BOTTOM: 0px; MARGIN: -8px 0px 0px 4px; BORDER-LEFT: 0px; WIDTH: auto; PADDING-TOP: 0px; BORDER-BOTTOM: 0px; FONT-FAMILY: Arial, Helvetica, sans-serif
}
.button {  border: thin outset; background-color: #d6d3ce; font-family: "Arial", "Helvetica", "sans-serif"; font-size: 10px; font-style: normal; color: #000000}
-->
</style>

</head>	
<Script Language="JavaScript">
var num = 0;

nextfield = "submit"; // name of first box on page
netscape = "";
ver = navigator.appVersion; len = ver.length;
for(iln = 0; iln < len; iln++) if (ver.charAt(iln) == "(") break;
netscape = (ver.charAt(iln+1).toUpperCase() != "C");

function keyDown(DnEvents) 
{ // handles keypress
	// determines whether Netscape or Internet Explorer
	k = (netscape) ? DnEvents.which : window.event.keyCode;
	if (k == 13)
	{ // enter key pressed
		if (num == 0)
		{ 
			alert("请选择需要打印的资料！");
		}
		else
		{
			sendback1();
		}
	}
}
document.onkeydown = keyDown; // work together to analyze keystrokes
if (netscape) document.captureEvents(Event.KEYDOWN|Event.KEYUP);
//  End -->

function sendback1()
{
 	var strfrmList = "";
	if (num == 0)
	{ 
		alert("请选择需要打印的资料！");
		return false;
	}
	else 
	{
		//贴现申请
		if (document.txselect.tx1.checked == true)
		{
			window.open("d023-c.jsp?lID=<%=lID%>","","width=800,height=600,scrollbars=1,resizable=1,menubar=0,toolbar=0,locations=0,directories=0,screenX=0,screenY=0,left=10,top=10");
		}
		if (document.txselect.tx2.checked == true)
		{
			window.open("d025-c.jsp?lID=<%=lID%>","","width=800,height=600,scrollbars=1,resizable=1,menubar=0,toolbar=0,locations=0,directories=0,screenX=0,screenY=0,left=10,top=10");	
		}		
		
		window.close();
	}
}

function check(box)
{
	if (box.checked == false) 
	{
  		num = num - 1;
	}
	else 
	{
  		num = num + 1;
	}
}

</Script>
<body bgcolor="#d6d3ce" text="#000000">
<script language="JavaScript" src="/webob/js/Control.js"></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>
<script language="JavaScript" src="/webob/js/date-picker.js"></script>
<script language="JavaScript" src="/webob/js/MagnifierSQL.js"></script>
<safety:resources />
<form name="txselect">
  <table width="98%" class="table" align="center">
    <tr bordercolor="#d6d3ce" align="center">      
      <td height="20">&nbsp;</td>
	</tr>
	<tr bordercolor="#d6d3ce" align="center">
      <td height="30">  
	  <strong>贴现申请</strong>
	  <hr width="200">
	  </td>
	</tr>
    <tr bordercolor="#d6d3ce" align="center">
      <td height="30"> 
		<input type="checkbox" name="tx1" value="checkbox" onClick="check(this)">贴现申请表</td>
    </tr>
    <tr bordercolor="#d6d3ce" align="center"> 
      <td height="30" >   
        <input type="checkbox" name="tx2" value="checkbox" onClick="check(this)">贴现票据明细</td>
    </tr>
	<tr bordercolor="#d6d3ce" align="center">      
      <td>	  
	  <hr width="200">
	  </td>
	</tr>
    <tr bordercolor="#d6d3ce">
	  <td height="50">
        <div align="center"> 
          <input type="button" name="Submit" value=" 确 定 " onClick="sendback1()" class="button">
        </div>
      </td>
	</tr>
  </table>

</form>
</body>
</html>

<%	
   //显示文件尾
	//	OBHtml.showOBHomeEnd(out);	
    }
	catch(IException ie) 
	{
		OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
		return;
    }
%>
<%@ include file="/common/SignValidate.inc" %>

