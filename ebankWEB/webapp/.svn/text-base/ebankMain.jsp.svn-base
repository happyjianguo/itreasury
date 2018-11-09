<%@ page language="java" contentType="text/html; charset=gbk" pageEncoding="gbk"%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ page import="com.iss.itreasury.util.Env"%>
<%@ page import="com.iss.itreasury.ebank.util.*"%>
<%@ page import="com.iss.itreasury.dataentity.HtmlHeaderInfo"%>
<%@ page import="com.iss.itreasury.system.bulletin.dataentity.BulletinInfo"%>
<%@ page import="com.iss.itreasury.system.bulletin.bizlogic.BulletinBiz"%>

<%String strContext = request.getContextPath();%>

<%
try{
	String strTitle = Env.getClientName() + "金融服务系统";
	String strBulletin = "";
	
    //用户登录检测 
    if (sessionMng.isLogin() == false)
    {
    	response.sendRedirect(strContext + "/Index.jsp");
    	out.flush();
    	return;
    }

    // 判断用户是否有权限 
    /*if (sessionMng.hasRight(request) == false)
    {
        out.println(sessionMng.hasRight(request));
    	OBHtml.showCommonMessage(out, sessionMng, strTitle, "", 1, "Gen_E003");
    	out.flush();
    	return;
    }*/
	
    //初始化页面信息
    OBHtml obHtml = new OBHtml();
	HtmlHeaderInfo htmlHeader = obHtml.getHtmlHeaderInfo(out, sessionMng, strTitle);
	
	//初始化公告栏
	BulletinBiz bulletinBiz=new BulletinBiz();
	BulletinInfo bulletinInfo = new BulletinInfo();
	bulletinInfo.setClients(String.valueOf(sessionMng.m_lClientID));
	bulletinInfo.setOfficeId(sessionMng.m_lOfficeID);
	strBulletin = bulletinBiz.findByBulletinString(bulletinInfo);
	sessionMng.setServletInfo(request.getServerPort(),request.getServerName(),request.getContextPath());	
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<title><%=strTitle%></title>
<link rel="stylesheet" href="/webob/css/style.css" type="text/css">
<script language="javascript" src="/itreasury/js/coolmenus4.js"></script>
<script language="javascript" src="/itreasury/js/cm_addins.js"></script>
<script language="javascript" src="/itreasury/js/jquery-1.3.2.js"></script>
<script language="javascript" src="/itreasury/js/util.js"></script>
<script language="javascript">
<!--
//关于菜单的变量
var strMenu = new String("<%=sessionMng.m_strMenu%>");
var menus = strMenu.split("::");
var menuName = menus[0].split(";;");
var menuNo = menus[1].split(";;");
var menuPlevel = menus[2].split(";;");
var menuUrl = menus[3].split(";;");
var menuNum = 0;

function changebgcolor(arg0, arg1)
{
	var oTdmenu = document.getElementById(arg0);
	if(oTdmenu != null){
		oTdmenu.background="/webob/graphics/new_til_4.gif";
	}
	
	var strImage = arg1.substring(0,8)+(parseInt(arg1.substring(8,9))-1);
	var oImgmenu1 = document.getElementById(strImage);
	var oImgmenu2 = document.getElementById(arg1);
	if(oImgmenu1 != null && oImgmenu2 != null){
		oImgmenu1.src="/webob/graphics/new_til_1.gif";
		oImgmenu2.src="/webob/graphics/new_til_6.gif" 
	}
}

function changebgcolor0(arg0, arg1)
{
	var oTdmenu = document.getElementById(arg0);
	if(oTdmenu != null){
		oTdmenu.background="";
	}

	var strImage = arg1.substring(0,8)+(parseInt(arg1.substring(8,9))-1);
	var oImgmenu1 = document.getElementById(strImage);
	var oImgmenu2 = document.getElementById(arg1);
	if(oImgmenu1 != null && oImgmenu2 != null){
		oImgmenu1.src="/webob/graphics/new_til_2.gif";
		oImgmenu2.src="/webob/graphics/new_til_2.gif" 
	}
}

/**
*主菜单的开合
*/
var oldScontent = "start";
function turnit(arg0) {
	var oScontent = document.getElementById(arg0);
	if(oScontent != null){
		if(oScontent.style.display == "none"){
			if(oldScontent != "start") {
				oldScontent.style.display = "none";
			}
		    oScontent.style.display = "block";
		    oldScontent = oScontent;
		 }
		 else {
		    oScontent.style.display = "none";
		 }
	}
}

function changeMenu(moduleid) {
	var oLeftTab;
	var strTab = "leftTab_";
	
	//把每个主菜单的集合置为不可见
	for(var i=1; i<=menuNum; i++) {
		oLeftTab = document.getElementById(strTab + i.toString());
		if(oLeftTab != null){
			oLeftTab.style.display = "none";
		}
	}
	
	//清空Content
	if(oldScontent != "start") {
		oldScontent.style.display = "none";
	}
	
	//清空Img
	var oImgCLs = document.getElementsByName("imgCL");
	for(var i=0; i<oImgCLs.length; i++){
		oImgCLs[i].src = "/webob/graphics/shim.gif";
	}
	
	strTab = strTab + moduleid;
	oLeftTab = document.getElementById(strTab);
	if(oLeftTab != null){
		oLeftTab.style.display = "block";
	}
}

function showfullwin(myurl) {
  var fullwindow = window.open(myurl,'',"scrollbars=no");
  // var fullwindow = window.open(myurl,'');
  fullwindow.moveTo(0,0);
  fullwindow.resizeTo(1024,768);
}

function showiniframe(myurl) {
  document.all.mainIframe.src = myurl;
}

function showarrowhead(arg0,num){
	var oImgCLs = document.getElementsByName("imgCL");
	for(var i=0; i<oImgCLs.length; i++){
		oImgCLs[i].src = "/webob/graphics/shim.gif";
	}
	
	//清空Content
	if(oldScontent != "start" && num == 0) {
		oldScontent.style.display = "none";
	}

	var oImgCL = document.getElementById(arg0);
	if(oImgCL != null){
		oImgCL.src="/webob/graphics/newconer2.gif";
	}
}

function showSelectCurrency(){
	var oWindow = window;
	var oDivSending = document.getElementById("sendingCurrency");
	var oSubmitCurrency = document.getElementById("butSubmitCurrency");
	
	try{
		var oBody = oWindow.document.body;
		if(oBody != null && oDivSending != null){
			//网页正文全文高
			var bodyHeigth = oBody.scrollHeight;
			//网页正文全文宽
			var bodyWidth = oBody.scrollWidth;
			//网页可见区域高
			var displayHeight = oBody.clientHeight;
			//网页可见区域宽
			var displayWidth = oBody.clientWidth;
			//获取oBody的位置
			var bbPosition = $("body").offset();
			//获取DivSending高度
			var divSendingHeight = $("#sendingCurrency").height();
			//获取DivSending宽度
			var divSendingWidth = $("#sendingCurrency").width();
			
			
			//在内存中创建一个oDivBackground对象
			var oDivBackground = oWindow.document.createElement("DIV");
			oDivBackground.id = "sendingBackground";
			oDivBackground.style.position = "absolute";
			oDivBackground.style.left = bbPosition.left;
			oDivBackground.style.top = bbPosition.top;
			oDivBackground.style.zIndex = 500;
			oDivBackground.style.backgroundColor = "white";
			oDivBackground.style.filter = "progid:DXImageTransform.Microsoft.Alpha(Opacity=60, FinishOpacity=30, Style=0, StartX=0,  FinishX=0, StartY=0, FinishY=0)";
			oDivBackground.style.height = bodyHeigth;
			oDivBackground.style.width = bodyWidth;
			oDivBackground.style.display = "block";
			oBody.appendChild(oDivBackground);
			
			//设置DivSending位置
			var divSendingLeft = bbPosition.left;
			//var divSendingTop = displayHeight/2 - divSendingHeight + oBody.scrollTop;
			if(displayHeight <= divSendingHeight){
				divSendingTop = 0;
			}
			else {
				divSendingTop = displayHeight/2 - divSendingHeight/2;
			}
			oDivSending.style.left = divSendingLeft;
			oDivSending.style.top = divSendingTop;
			oDivSending.style.display = "block";
			
			oSubmitCurrency.focus();
		}
	}
	catch(ex){
		/*var debug = "";
		for(var i in ex){
			debug += i + "  = " + ex[i] + "\n";
		}
		alert(debug);*/
		return;
	}
}

function closeSelectCurrency(){
	var oDivBackground = window.document.getElementById("sendingBackground");
	var oDivSending = document.getElementById("sendingCurrency");
	
	try{
		if(oDivSending != null && oDivBackground != null){
			window.document.body.removeChild(oDivBackground);
			oDivSending.style.display = "none";
		}
	}
	catch(ex){
		return;
	}
}

//-->
</script>
</head>
<jsp:include flush="true" page="/ShowMessage.jsp" />
<body bgcolor="#FFFFFF" style="margin:0px;overflow:hidden">
<iframe src="<%=strContext%>/common/jsp/listenUsbkey.jsp" style="display:none"></iframe>
<table id="tabBody" width="1010" height="595" border="0" cellpadding="0" cellspacing="0">
	<tr>
    	<td valign="top" height="60">
      		<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
      			<tr>
					<td width="250"><img src="/webob/graphics/logo.gif"/></td>
        			<td width="250"><img src="/webob/graphics/logo_bank.gif"/></td>
        			<td width="500" nowrap><%=htmlHeader.getStatus()%></td>
        			<td width="*%" nowrap>&nbsp;</td>
 				 </tr>
			</table>
		</td>
	</tr>
	<tr>
		<td id="menutd" valign="top" height="20" width="1010">
			<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
  				<tr height="20">
    				<td width="*%" height="100%" class="menuMainForYQ">
      					<table width="100%" border="0" cellpadding="0" cellspacing="0">
        					<tr>
       							<td width="5"><img id="imgmenu_0" src="/webob/graphics/new_til_2.gif"></td>
								<script language="javascript">
								<!--
									menuNum = 0;
								    for(var i=0; i<menuName.length; i++){
								    	if(menuPlevel[i] == "1"){
								    		menuNum++;
								    		document.write("<td width=\"60\" id=\"tdmenu_"+ menuNo[i] +"\" align=\"center\" style=\"cursor:hand;\" onmouseover=\"changebgcolor('tdmenu_"+ menuNo[i] +"','imgmenu_"+ menuNum +"');\" onmouseout=\"changebgcolor0('tdmenu_"+ menuNo[i] +"','imgmenu_"+ menuNum +"');\" onclick=\"javascript:changeMenu('"+ menuNum +"');\" ><font color=\"white\">"+menuName[i]+"</font></td>\n");
								     		document.write("<td align=\"center\" width=\"5\"><img id=\"imgmenu_"+ menuNum +"\" src=\"/webob/graphics/new_til_2.gif\" ></td>\n");
								     	}
								    }
								//-->
								</script>
        						<td width="*%">&nbsp;</td>
        					</tr>
      					</table>
   					 </td>
    				<td width="5"><img src="/webob/graphics/new_til_5.gif"></td>
    				<td width="295" align="right">  
         				<img src="/webob/graphics/logout.gif" align="absbottom">&nbsp;
         				<a href="<%=strContext%>/Logout.jsp?control=view" class="txt_black" target="mainIframe"><font style="color: #000000;text-decoration: none;">退出登录</font></a>
         				<img src="/webob/graphics/repass.gif" align="absbottom">&nbsp;
         				<a href="#" onClick="window.open('<%=strContext%>/system/V857.jsp?control=view','_blank','height=280,width=400,center=yes,status=no,toolbar=no,menubar=no,location=no,resizable=yes,scrollbars =no,left='+((screen.width)/2-150)+',top='+((screen.height)/2-140)+' ')" class="txt_black"><font style="color: #000000;text-decoration: none;">修改密码</font></a>
   			 		</td>
    				<td width="20" nowrap>&nbsp;</td>
  				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td id="messagetd" valign="top" height="21" width="1010">
      		<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0" bgcolor="#999999">
        		<tr>
          			<td><img src="/itreasury/graphics/space.gif" width="1" height="1"></td>
        		</tr>
  				<tr>
    				<td>
						<iframe border="0" frameborder="0" framespacing="0" marginheight="0" marginwidth="0" name="new_date" noResize scrolling="no" height="20" src="<%=htmlHeader.getRemindURL()%>" width="100%" vspale="0"></iframe>
    				</td>
  				</tr>
      		</table>
    	</td>
  	</tr>
	<tr>
		<td id="maintd"	valign="top" height="*%" width="1010">
			<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
  				<tr>
    				<td width="178" height="100%" valign="top" align="right" background="/webob/graphics/bodyleft_bg2.jpg">
					<script language="javascript">
					<!--
						menuNum = 0;
					    for(var i=0; i<menuName.length; i++){
					    	if(menuPlevel[i] == "1"){
					    		menuNum++;
					    		document.write("<table id=\"leftTab_"+ menuNum +"\" style=\"DISPLAY: none;\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">");
					     		document.write("<tr>");
					     		document.write("<td><img src=\"/webob/graphics/body_left_til2.jpg\" width=\"179\" height=\"27\"></td>");
					     		document.write("</tr>");
					     		
					     		for(var j=i+1; j<menuName.length; j++){
					     			if(menuPlevel[j] == "1"){
					     				break;
					     			}
					     			if(menuPlevel[j] == "2"){
							     		if(menuUrl[j] != "null"){
							     			document.write("<tr>");
							     			document.write("<td height=\"25\" align=\"left\" valign=\"middle\" style=\"cursor:hand;\">");
							     			document.write("&nbsp;&nbsp;<img src=\"/webob/graphics/conner5.gif\" name=\"imgKB1\" id=\"imgKB1\" border=\"0\">");
							     			document.write("&nbsp;&nbsp;<a onClick=\"showarrowhead('imgCL_"+ menuNo[j] +"',0)\" href=\"javascript:showiniframe('"+ menuUrl[j] +"')\" class=\"txt_black\"><font style=\"color: #000000; text-decoration: none;\">"+ menuName[j] +"</font></a>");
							     			document.write("&nbsp;<img id=\"imgCL_"+ menuNo[j] +"\" name=\"imgCL\" src=\"/webob/graphics/shim.gif\">");
							     			document.write("</td>");
							     			document.write("</tr>");
							     			document.write("<tr>");
							     			document.write("<td height=\"1\" colspan=\"2\" align=\"center\" background=\"/webob/graphics/line_bg.gif\" class=\"box01\"></td>");
							     			document.write("</tr>");
							     		}
							     		else {
							     			document.write("<tr>");
							     			document.write("<td height=\"25\" align=\"left\" valign=\"middle\" style=\"cursor:hand;\" class=\"box1\" onclick=\"javascript:turnit('scontent_"+ menuNo[j] +"');\">");
							     			document.write("&nbsp;&nbsp;<img src=\"/webob/graphics/conner5.gif\" name=\"imgKB1\" id=\"imgKB1\" border=\"0\">");
							     			document.write("&nbsp;&nbsp;" + menuName[j]);
							     			document.write("</td>");
							     			document.write("</tr>");
							     			document.write("<tr>");
							     			document.write("<td height=\"1\" colspan=\"2\" align=\"center\" background=\"/webob/graphics/line_bg.gif\" class=\"box01\"></td>");
							     			document.write("</tr>");
							     		}
							     		document.write("<tr>");
							     		document.write("<td valign=\"top\" align=\"center\" style=\"DISPLAY: none;\" id=\"scontent_"+ menuNo[j] +"\">");
							     		document.write("<table width=\"74%\" class=\"shw-table-submenu\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">");
							     		document.write("<tr>");
							     		document.write("<td valign=\"top\">");
							     		document.write("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">");
							     		
							     		for(var k=j+1; k<menuName.length; k++){
							     			if(menuPlevel[k] == "1" || menuPlevel[k] == "2"){
							     				break;
							     			}
								     		document.write("<tr>");
								     		document.write("<td height=\"25\" align=\"left\" valign=\"middle\" style=\"cursor:hand;\">");
								     		document.write("<img src=\"/webob/graphics/conner6.gif\">");
								     		document.write("&nbsp;<a onClick=\"showarrowhead('imgCL_"+ menuNo[k] +"',1)\" href=\"javascript:showiniframe('"+ menuUrl[k] +"')\" class=\"txt_black\"><font style=\"color: #000000; text-decoration: none;\">"+ menuName[k] +"</font></a>");
								     		document.write("&nbsp;<img id=\"imgCL_"+ menuNo[k] +"\" name=\"imgCL\" src=\"/webob/graphics/shim.gif\">");
								     		document.write("</td>");
								     		document.write("</tr>");
							     		}
				     					document.write("</table>");
				     					document.write("</td>");
				     					document.write("</tr>");
				     					document.write("</table>");
				     					document.write("</td>");
				     					document.write("</tr>");
					     			}
					     		}
					     		document.write("</table>");
					     	}
					     }
					//-->
					</script>
	  				</td>
	  				<Td width=5>&nbsp;</Td>
					<td width="*%" height="100%" valign="top">
						<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td width="100%" height="*%">
									<input type="hidden" name="mainIsShowSending" value="true" />
									<iframe id="mainIframe" name="mainIframe" style="border: thin solid #EDF6FB;" frameborder="0" framespacing="0" marginheight="0" marginwidth="0" noResize scrolling="auto" height="100%" width="100%" vspale="0" src="LoginMain.html" onload="iframeOnLoad()"></iframe>
								</td>
							</tr>
							<tr>
								<td width="100%" height="1" align="center">
									<img src="/webob/graphics/bottom_gdf2.jpg" />
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>

<div id="sendingCurrency" style="position:absolute; z-index:502; display:none">
<table width="100%">
<tr><td align="center" >
<table width="490" height="420" align="center" border="0" cellpadding="4" cellspacing="4" style="border-top: 1px dashed blue; border-left: 1px dashed blue; border-right: 1px dashed blue; border-bottom: 1px dashed blue; background-color:#FFFFFF">
	<tr>
		<td height="28" colspan="2" width="100%" align="center" class="ItemTitle">
			<B>公告信息栏</B>
		</td>
	</tr>
    <tr>
		<td height="20" width="30%" valign="bottom" class="ItemTitle">
			<p align="center">公告名称</p>
		</td>
		<td height="20" width="70%" valign="bottom" class="ItemTitle"> 
			 <p align="center">公告内容</p>
		 </td>
     </tr>
	 <tr>
	 	<td height="*%" colspan="2" width="100%">
			<div style="width:100%;height:100%;overflow-x:hidden;overflow-y:scroll">
				<table width="100%">
					<%
					if(!strBulletin.equals("")){
						strBulletin = strBulletin.replaceAll("\n","<br>");
						strBulletin = strBulletin.replaceAll(" ","&nbsp;");
						String 	bulletins[] = strBulletin.split("::");
						for(int i=0; i<bulletins.length; i++){
					    	String bulletin_context[] = bulletins[i].split(";;");
					    	out.println("<tr>");
					    	out.println("<td width=\"30%\"  valign=\"top\" align=\"left\" class=\"ItemBody\" style= 'word-wrap:break-word;word-break:break-all '>"+ bulletin_context[0] +"</td>");
					    	out.println("<td width=\"70%\"  valign=\"top\" align=\"left\" class=\"ItemBody\" style= 'word-wrap:break-word;word-break:break-all '>"+ bulletin_context[1] +"</td>");
					    	out.println("</tr>");
					    	out.println("<tr><td colspan=\"2\" height=\"8\"></td></tr>");
						}
					}
					else{
						out.println("<tr>");
						out.println("<td width=\"30%\"  valign=\"top\" align=\"left\" class=\"ItemBody\">&nbsp;</td>");
						out.println("<td width=\"70%\"  valign=\"top\" align=\"left\" class=\"ItemBody\">&nbsp;</td>");
						out.println("</tr>");
					}
					%>
					<%----
					<script language="javascript">
					<!--
						if(strBulletin != ""){
							var bulletins = strBulletin.split("::");
						    for(var i=0; i<bulletins.length; i++){
						    	var bulletin_context = bulletins[i].split(";;");
						    	document.write("<tr>");
								document.write("<td width=\"30%\"  valign=\"top\" align=\"left\" class=\"ItemBody\">"+ bulletin_context[0] +"</td>");
								document.write("<td width=\"70%\"  valign=\"top\" align=\"left\" class=\"ItemBody\">"+ bulletin_context[1] +"</td>");
								document.write("</tr>");
						    }
						}
						else {
							document.write("<tr>");
							document.write("<td width=\"30%\"  valign=\"top\" align=\"left\" class=\"ItemBody\">&nbsp;</td>");
							document.write("<td width=\"70%\"  valign=\"top\" align=\"left\" class=\"ItemBody\">&nbsp;</td>");
							document.write("</tr>");
						}
					//-->
					</script>
					----%>
				</table>
			</div>
		</td>
	</tr>
	<tr>
		<td height="40" colspan="2" align="right">
			<input type="button" id="butSubmitCurrency" value=" 确 定 " class="button" onclick="closeSelectCurrency()">&nbsp;&nbsp;
		</td>
	</tr>
</table>
</td></tr>
</table>
</div>

<script language="javascript">
<!--
//设置窗口
window.moveTo(0,0);  
if(document.all){
	top.window.resizeTo(screen.availWidth, screen.availHeight);  
}  
else if(document.layers || document.getElementById){  
	if (top.window.outerHeight<screen.availHeight || top.window.outerWidth < screen.availWidth){  
		top.window.outerHeight = screen.availHeight;  
		top.window.outerWidth = screen.availWidth;  
	}  
}

//设置主Table
var defaultWidth = 1024;
var defaultHeight = 768;
var oBody = document.body;
var oTabBody = document.getElementById("tabBody");
var menutdBody = document.getElementById("menutd");
//var mainIframeBody = document.getElementById("mainIframe");
var messagetdBody = document.getElementById("messagetd");
//var botomtdBody = document.getElementById("botomtd");
var maintdBody = document.getElementById("maintd");

if(defaultWidth <= screen.width){
	oBody.style.overflow = "hidden";
	oTabBody.width = screen.width - 8;
	menutdBody.width = screen.width - 8;
	//mainIframeBody.width = screen.width - 8-180;
	messagetdBody.width = screen.width - 8;
	//botomtdBody.width = screen.width - 8-180;
	maintdBody.width = screen.width - 8;
}
if(defaultHeight <= screen.height){
	oBody.style.overflow = "hidden";
	oTabBody.height = oBody.clientHeight;
}

//默认初始菜单
changeMenu('1');
//初始化公告栏
<%
if(!strBulletin.equals("")){
	out.println("showSelectCurrency();");
}
%>
//-->
</script>
</body>
</html>
<%
}
catch(Exception ex){
	ex.printStackTrace();
	sessionMng.getActionMessages().addMessage("登录失败");
	response.sendRedirect(strContext + "/Index.jsp");
}
%>