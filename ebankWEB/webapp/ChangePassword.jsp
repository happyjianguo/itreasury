<%@ page contentType="text/html;charset=gbk"%> 
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@page import="com.iss.itreasury.util.*,
				com.iss.itreasury.ebank.util.OBHtml,
				java.util.*" 
%>
<%@ page import="com.iss.itreasury.ebank.pwconfig.bizlogic.*"%>
<%@ page import="com.iss.itreasury.ebank.pwconfig.dataentity.*"%>
<%@ page import="java.sql.*"%>
<%@ page import="java.util.regex.*"%>
<jsp:include page="/ShowMessage.jsp"/>
<%String strContext = request.getContextPath();%>
<%
	String strSuccessPageURL = "";
	String strTmp = request.getParameter("strSuccessPageURL");
	if( strTmp != null && strTmp.length() > 0 ){
	    strSuccessPageURL = strTmp;
	}
	PWConfigBean bizPW = new PWConfigBean();					//密码校验，written by liangpan 2007-6-27
	PasswordInfo passwordInfo = new PasswordInfo();
	String password = "";
	try{
		passwordInfo = bizPW.getPasswordConfigInfo(sessionMng.m_lOfficeID); 
		password = (String)(bizPW.getOBUserPwdInfo(sessionMng.m_lUserID).get(0));
		password = password.replaceAll("'","\\\\'");			//转义，将'变为\'
	}catch(Exception e){
		out.println(e.toString());
	}	
%>
<link rel="stylesheet" href="/itreasury/css/style.css" type="text/css">
<html>
<head>
<title>修改密码</title>
 
<script lang="JavaScript" src="/websys/js/Check.js" type="text/javascript"></script>
<script language="JavaScript">
function  validate()
{
	var oldPwd = form1.txtOldPW.value.Trim();
	form1.txtOldPW.value = oldPwd;
	var newPwd = form1.txtNewPW.value.Trim();
	form1.txtNewPW.value = newPwd;
	var confirmPwd = form1.txtConfirmPW.value.Trim();
	form1.txtConfirmPW.value = confirmPwd;
	if(oldPwd==""){
		alert("请输入原密码");
		form1.txtOldPW.focus();
		return;
	}
	if(newPwd==""){
		alert("请输入新密码");
		form1.txtNewPW.focus();
		return;
	}
	if(confirmPwd==""){
		alert("请输入确认密码");
		form1.txtConfirmPW.focus();
		return;
	}
	
	//if(oldPwd != '<%=password%>'){
	//	alert("您输入的原密码不正确，请重新输入");
	//	form1.txtOldPW.focus();
	//	return;
	//}
	
    if (newPwd != confirmPwd){
    	alert("确认密码与新密码不一致，请重新输入");
    	form1.txtNewPW.focus();
		return;
 	}
 	if(newPwd == oldPwd){
 		alert("新密码不应与原密码相同，请重新输入");
 		return;
 	}
 	var hasNumber = 0;
	var hasLowercase = 0;
	var hasCapital = 0;
	var hasSpecial = 0;
	var hasLength = 0;
	var infoMsg = "";
	var pattern = /[0-9]/;
	var pattern2 = /[a-z]/;
	var pattern3 = /[A-Z]/;
	var pattern4 = /[^0-9a-zA-Z]/;
	if(<%=passwordInfo.getCompriseNumber()%>==1){
		if(newPwd.match(pattern)==null){
			hasNumber = 1;			
		}
		infoMsg += "[数字]";
	}
	if(<%=passwordInfo.getCompriseLowercase()%>==1){
		if(newPwd.match(pattern2)==null){
			hasLowercase = 1;			
		}
		infoMsg += "[小写字母]";
	}
	if(<%=passwordInfo.getCompriseCapital()%>==1){
		if(newPwd.match(pattern3)==null){
			hasCapital = 1;			
		}
		infoMsg += "[大写字母]";
	}
	if(<%=passwordInfo.getCompriseSpecial()%>==1){
		if(newPwd.match(pattern4)==null){
			hasSpecial = 1;			
		}
		infoMsg += "[特殊字符]";
	}
	if(newPwd.length< <%=passwordInfo.getMinPassword()%>||newPwd.length><%=Constant.PASSWORD_MAX_LENGTH%>){
		hasLength = 1;
	}
	if((hasNumber+hasLowercase+hasCapital+hasSpecial)>0){
		if( <%=passwordInfo.getMinPassword()%> != <%=Constant.PASSWORD_MAX_LENGTH%> ){
			alert("请重新输入密码，必须包含"+infoMsg+"，且长度为<%=passwordInfo.getMinPassword()%>到<%=Constant.PASSWORD_MAX_LENGTH%>");
		}else if ( <%=passwordInfo.getMinPassword()%> == <%=Constant.PASSWORD_MAX_LENGTH%> ){
			alert("请重新输入密码，必须包含"+infoMsg+"，且长度为<%=passwordInfo.getMinPassword()%>");
		}
		return;
	}else if((hasNumber+hasLowercase+hasCapital+hasSpecial)==0 && hasLength > 0){
		if( <%=passwordInfo.getMinPassword()%> != <%=Constant.PASSWORD_MAX_LENGTH%> ){
			alert("请重新输入密码，长度为<%=passwordInfo.getMinPassword()%>到<%=Constant.PASSWORD_MAX_LENGTH%>");
		}else if( <%=passwordInfo.getMinPassword()%> == <%=Constant.PASSWORD_MAX_LENGTH%> ){
			alert("请重新输入密码，长度为<%=passwordInfo.getMinPassword()%>");
		}
		return;
	}
  	if (confirm("是否更改?")){
		form1.strSuccessPageURL.value="<%=strContext%>/ebankMain.jsp";
		form1.strFailPageURL.value="<%=strContext%>/ChangePassword.jsp";
	    form1.action="<%=strContext%>/S521.jsp";
		form1.submit();
  	}
}
function Cancel()
{
	if (confirm("是否放弃?")) 
	{
      window.location.href="<%=strContext%>/Index.jsp?istroy=isoftstone";
	} 
}

//去掉左右空格
String.prototype.Trim = function()
{
	return this.replace(/(^\s*)|(\s*$)/g, "");
}

//-->
</script>
</head>
 
<body>

  <form name="form1" action="<%=strContext%>/S521.jsp?control=view" method="post" >
  <input type="hidden" name="strSuccessPageURL" value="">
  <input type="hidden" name="strFailPageURL" value="">
<table height="120"><tr><td></td></tr></table>
<TABLE align=center border=0 class=top height=217 width="27%">
  <TBODY>
  <TR>
    <TD class=FormTitle height=2 width="100%"><B>更改密码</B></TD></TR>
  <TR>
    <TD height=40 vAlign=bottom class=FormTitle width="100%">
      <TABLE align=center height=100 width="97%">
        <TBODY>
        <TR>
          <TD height=40 vAlign=top  width="96%">
            <TABLE align=center border=1  width="99%">
              <TBODY>
              <TR borderColor=#E8E8E8 vAlign=center>
			    <td height="32" width="152"> 
			      <div align="center"> 原密码: </div>
			    </td>
			    <td height="32" width="130" align="right"> 
			      <div align="left">
			        <input type="password" name="txtOldPW" size="10" value="" onFocus="nextfield ='txtNewPW';" onpaste="return false" oncontextmenu="return false">
			      </div>
			    </td>
		  </TR>
              <TR borderColor=#E8E8E8 vAlign=center>
			    <td height="32" width="152"> 
			      <div align="center"> 新密码: </div>
			    </td>
			    <td height="32" width="130" align="right"> 
			      <div align="left">
			        <input type="password" name="txtNewPW" size="10" value="" onFocus="nextfield ='txtConfirmPW';" onpaste="return false" oncontextmenu="return false">
			      </div>
			    </td>
		  </TR>
              <TR borderColor=#E8E8E8 vAlign=center>
			    <td height="32" width="152"> 
			      <div align="center"> 确认新密码: </div>
			    </td>
			    <td height="32" width="130" align="right"> 
			      <div align="left">
			        <input type="password" name="txtConfirmPW" size="10" value="" onFocus="nextfield ='submitfunction';" onpaste="return false" oncontextmenu="return false">
			      </div>
			    </td>
		  </TR>
		  
		</TBODY></TABLE></TD></TR></TBODY></TABLE></TD></TR>
  <TR>
    <TD height=11 vAlign=top class=FormTitle width="100%">
      <TABLE align=center height=17  width="97%">
        <TBODY>
        <TR vAlign=center>
          <TD borderColor=#E8E8E8 colSpan=6 height=40>
            <DIV align=center>
			        <input type="button" name="SUBMIT" value=" 确 认 "  onclick="validate();" >
		       <input type="button" name="cancel" value=" 取 消 " onClick="Javascript:Cancel();">
            </DIV></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE>
</form>
</table>
