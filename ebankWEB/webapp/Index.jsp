<%@ page contentType="text/html;charset=gbk"%>
<%@ page import="com.iss.itreasury.util.*"%>
<%@ page import="com.iss.itreasury.safety.signature.*"%>
<%String strContext = request.getContextPath();%>

<%
	String strTmp = "";
	String strTroy = "";
	strTmp = request.getParameter("istroy");
	if (strTmp != null && strTmp.length() > 0) {
		strTroy = strTmp;
	}
	
	String strTroyName=Config.getProperty(ConfigConstant.GLOBAL_TROY_NAME,Constant.GlobalTroyName.NotUseCertificate);
	if(strTroyName.equals(Constant.GlobalTroyName.ITrus)){
		//使用天威诚信证书系统登录
		LoginAuthentication.setSignatureName(Constant.GlobalTroyName.ITrus);
		LoginAuthentication.validateByHttps(request);
	}else if(strTroyName.equals(Constant.GlobalTroyName.NetSign)){
		//使用信安证书系统登录
		LoginAuthentication.setSignatureName(Constant.GlobalTroyName.NetSign);
		LoginAuthentication.validateByNetSign(request);
	}else if (strTroyName.equalsIgnoreCase(Constant.GlobalTroyName.CFCA))
	{
	    //使用CFCA证书登陆系统
	    LoginAuthentication.setSignatureName(Constant.GlobalTroyName.CFCA);
		LoginAuthentication.validateByNetSign(request);
	}
	boolean isRequireCheckcode = Config.getBoolean(ConfigConstant.EBANK_ISREQUIRECHECKCODE, true);
%>
<jsp:include flush="true" page="/ShowMessage.jsp" />
<html>
	<head>
		<title><%=Env.getClientName()%>
		</title>
		<meta http-equiv="Content-Type" content="text/html; charset=gbk">
		<link rel="stylesheet" href="/itreasury/css/style.css" type="text/css">
		<script src="/itreasury/js/softkeyboard.js" type="text/javascript"></script>
		<SCRIPT language=JavaScript>
	function window.onload()
	{
		form1.UserName.focus();
	}
	var a = 0;
	function checkForm()
	{
		var frm=document.form1;
		if(frm.UserName.value=="" && frm.Password.value==""){
			alert("请输入用户名和密码");
			frm.UserName.focus();
			return false;
		}
		if (frm.UserName.value==""){
			alert("请输入用户名");
			frm.UserName.focus();
			return false;
		}
		if (frm.Password.value==""){
			alert("请输入密码");
			frm.Password.focus();
			return false;
		}
		<%if(isRequireCheckcode){%>
		if (frm.checkcode.value==""){
			alert("请输入验证码");
			frm.checkcode.focus();
			reloadcode()
			return false;
		}
		<%}%>
		frm.submit();
	}
	function reloadcode()
	{
		var verify=document.getElementById('checkcodeimage');
		verify.setAttribute('src','CheckCodeImage.jsp?'+Math.random());
	}
	function onChange(){
		if(event.keyCode == 13){
			document.form1.Password.focus();
		}
		
	}
	function onPassChange(){
		if(event.keyCode == 13){
			document.form1.Submit1.focus();
		}
	}
	function onPassChangeToCode(){
		if(event.keyCode == 13){
			document.form1.checkcode.focus();
		}
	}
	function onPass(){
		if(event.keyCode == 13 && a == 0){
			document.form1.UserName.focus();
			a = 1;
		}
	}
	
	function onReset(){
		document.form1.UserName.value="";
		document.form1.Password.value="";
		document.form1.checkcode.value="";
		document.form1.UserName.focus();
	}
</script>
	</head>
	<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0"
		marginwidth="0" marginheight="0" align="center" onkeydown="onPass();">
		<form name="form1" method="post" action="<%=strContext%>/LoginControl.jsp">
			<table width="100%" height="100%" border="0" cellpadding="0"
				cellspacing="0">
				<tr>
					<td valign="top" background="/webob/graphics/bg.jpg">
						<table width="100%" border="0" align="center" cellpadding="0"
							cellspacing="0">
							<tr>
								<!--
								<td align="right">								 
									<img src="/webob/graphics/logo.gif" width="251" height="76">
								 -->	
								<td align="right"  width="251" height="76"> 
								</td>
							</tr>
							<tr>
								<td height="60">
									&nbsp;
								</td>
							</tr>
							<tr>
								<td align="center">
									<table width="685" height="320" border="0" cellpadding="0"
										cellspacing="1" bgcolor="#00458A">
										<tr>
											<td align="left" valign="bottom"
												background="/webob/graphics/login_bg.jpg"
												bgcolor="#FFFFFF">
												<table width="300" border="0" cellpadding="0"
													cellspacing="4" class="txt_blue">
													<tr>
														<td width="55" rowspan="5">
															&nbsp;
														</td>
														<input type="Hidden" name="istroy" value="<%=strTroy%>">
														<td width="55">
															用户名
														</td>
														<td width="163">
															<input name="UserName" type="text" size="23"
																class="login_input" maxlength="20"
																onkeydown="onChange();" onfocus="javascript:a=1;">
														</td>
														<td>
															
														</td>
													</tr>
													<tr>
														<td width="55">
															密&nbsp;&nbsp;码
														</td>
														<td width="163">
															<% if(isRequireCheckcode){%>
															<input name="Password" type="password" size="23"
																class="login_input" maxlength="20" onkeydown="onPassChangeToCode();" 
																onfocus="javascript:a=1;" onpaste="return false" oncontextmenu="return false">
															<%}else{ %>
															<input name="Password" type="password" size="23"
																class="login_input" maxlength="20" onkeydown="onPassChange();" 
																onfocus="javascript:a=1;" onpaste="return false" oncontextmenu="return false">
															<%} %>
														</td>
														<td>
															<%
															if(Config.getBoolean(ConfigConstant.GLOBAL_EBANK_SOFTKEYBOARD,false))
															{
															 %>
															<input type="button" name="softkey" value="软键盘"
																class="button1" onclick="javascript:see(form1.Password,checkForm)">
															<%
															}
															 %>
														</td>
													</tr>
													<% if(isRequireCheckcode){%>
													<tr>
														<td width="55">
															验证码
														</td>
														<td width="163">
															<input name="checkcode" type="text" size="23"
																class="login_input" maxlength="10"
																 onkeydown="onPassChange();" onfocus="javascript:a=1;" >
														</td>
														<td width="80">
															<img name="checkcodeimage" border=0 src="CheckCodeImage.jsp" onclick="reloadcode();" style="cursor:hand;padding:2px 8px 0pt 3px;" >
														</td>
													</tr>
													<% }%>
													<tr>
														<td>
															&nbsp;
														</td>
														
														<td colspan=2>
															<input type="hidden" name="currency" value="1">
															<input type="button" name="Submit1" value=" 提交 "
																class="button1" onclick="javascript:checkForm()">
															<input type="button" name="Submit2" value=" 重置 "
																class="button1" onclick="javascript:onReset()">
														</td>
													</tr>
													<tr>
														<td height="30" colspan="4">
															&nbsp;
														</td>
													</tr>
												</table>
											</td>
										</tr>

									</table>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>



