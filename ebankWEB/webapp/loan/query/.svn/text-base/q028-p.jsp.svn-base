<%@ page contentType="text/html;charset=gbk" %>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"/>
<jsp:useBean id="Magnifier" scope="request" class="com.iss.itreasury.ebank.util.OBMagnifier"/>
<%@ page import="com.iss.itreasury.util.*,
                com.iss.itreasury.loan.util.*,
               com.iss.itreasury.ebank.util.*,
                com.iss.itreasury.ebank.obsystem.bizlogic.*,
                com.iss.itreasury.ebank.obquery.bizlogic.*,                
                com.iss.itreasury.loan.loancommonsetting.bizlogic.*,
    			com.iss.itreasury.loan.discount.bizlogic.*,
                com.iss.itreasury.loan.discount.dataentity.*,
    			java.sql.*,
                java.rmi.RemoteException,
				java.util.*"
%>
<%
	try
	{		
		if( !sessionMng.isLogin() )
		{	
			OBHtml.showMessage(out,sessionMng,null,"登录",null,Constant.RecordStatus.INVALID,"Gen_E002");
			OBHtml.showOBHomeEnd(out);
			out.flush();
			return;
		}
		//判断是否有权限
		if (sessionMng.hasRight(request) == false)
		{
			OBHtml.showMessage(out,sessionMng,null,"登录",null,Constant.RecordStatus.INVALID,"Gen_E003");
			OBHtml.showOBHomeEnd(out);
			out.flush();
			return;
		}
		
		String strTmp = null;
		long lDiscountID = -1;
		
		strTmp = (String)request.getAttribute("lDiscountID");
        if( strTmp != null && strTmp.length() > 0 )
		{
			 lDiscountID = Long.parseLong(strTmp.trim());
		}
		//out.print(request.getParameter("lDiscountID"));
			
/////////////////////////////////////////////////////
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
			window.open("q049-p.jsp?control=view&lDiscountID=<%=lDiscountID%>&isSM=<%=Constant.YesOrNo.NO%>","","width=800,height=600,scrollbars=1,resizable=1,menubar=0,toolbar=0,locations=0,directories=0,screenX=0,screenY=0,left=10,top=10");
		}
		if (document.txselect.tx2.checked == true)
		{
			window.open("q048-p.jsp?control=view&lDiscountID=<%=lDiscountID%>&isSM=<%=Constant.YesOrNo.NO%>","","width=800,height=600,scrollbars=1,resizable=1,menubar=0,toolbar=0,locations=0,directories=0,screenX=0,screenY=0,left=10,top=10");	
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

<%	}
	catch(Exception e)
    {
		e.printStackTrace();
		out.flush();
		return; 
    }
%>