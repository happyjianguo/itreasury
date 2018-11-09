<%--
/**
 * 程序名称：V857.jsp
 * 功能说明：系统管理-用户管理
 * 作　　者：刘琰
 * 完成日期：2003年9月10日
 */
--%>
<!--Header start-->

<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="com.iss.itreasury.util.*,
				 com.iss.itreasury.ebank.util.*,
				 com.iss.itreasury.system.bizlogic.EBankbean"%>
<%@ page import="com.iss.itreasury.ebank.pwconfig.bizlogic.*"%>
<%@ page import="com.iss.itreasury.ebank.pwconfig.dataentity.*"%>
<%@ page import="com.iss.itreasury.encrypt.EncryptManager"%>
<%@ page import="java.sql.*"%>
<%@ page import="java.util.regex.*"%>
<%@ page import="java.util.*"%>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>

<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>


<!--Header end-->
<%String strContext = request.getContextPath();%>
<%
//固定变量
String strTitle = "";
try
{
	/**
	* isLogin start
	*/
	//登录检测
	if( sessionMng.isLogin() == false)
	{
		OBHtml.showCommonMessage(out, sessionMng, strTitle, "", 1, "Gen_E002");
		out.flush();
		return;
	}
	//判断是否有权限
	if(sessionMng.hasRight(request)==false)
	{
		out.println(sessionMng.hasRight(request));
		OBHtml.showCommonMessage(out, sessionMng, strTitle, "", 1, "Gen_E003");
		out.flush();
		return;
	}
	OBHtml.showOBHomeHead(out, sessionMng, strTitle, OBConstant.ShowMenu.NO);
	
	/**
	* isLogin end
	*/



	  String strPW = "";
	  String strPW1 = "";
	  String strTmp = "";
      String strLogin = "";
      String strSuccess = "0";
	  long lUserID = -1;
	  String password = "";
	  String oldPassword = "";
	  PasswordInfo passwordInfo = new PasswordInfo();

	  // extract UserID
      lUserID = sessionMng.m_lUserID;
      PWConfigBean pwBiz = new PWConfigBean();
	  strTmp = request.getParameter("SUBMIT");
	  if(strTmp != null && strTmp.length() > 0)
	  {// after submit
		
		// extract strUserName
		strTmp = request.getParameter("txtOldPW");
		if( strTmp != null && strTmp.length() > 0 )
	  	{
	  	  oldPassword = strTmp;
	  	}
		
	  	strTmp = request.getParameter("txtPW");
		if( strTmp != null && strTmp.length() > 0 )
	  	{
	  	  strPW = strTmp;
	  	}
		// extract strLogin
	  	strTmp = request.getParameter("txtPW1");
		if( strTmp != null && strTmp.length() > 0 )
	  	{
	  	  strPW1 = strTmp;
	  	}
		
        try
        {
            //加密校验
		    if (Config.getBoolean(ConfigConstant.EBANK_CAN_ENCRYPT,false))
		    {
		    	if(EncryptManager.getOBBeanFactory().checkOBPassword(sessionMng.m_strLogin, oldPassword))
		    	{
		    		EncryptManager.getOBBeanFactory().changeOBUserPassword(lUserID, strPW);
		    		strSuccess = "1";
		    	}
		    	else
		    	{
		    		%>
		    			<script language="JavaScript">
							if (confirm("原密码错误,是否关闭")) 
							{					
								window.close();
							}
							//location.href = "<%=strContext%>/system/V857.jsp";
						</script>
		    		<%
		    	}
		    }
		    else
		    {
		    	List list = pwBiz.getOBUserPwdInfo(sessionMng.m_lUserID);
            	password = (String)list.get(0);
            	if(password.equals(oldPassword))
            	{
					pwBiz.changeUserPassword(lUserID,strPW);
					  strSuccess = "1";
				}
				else
				{
					%>
		    			<script language="JavaScript">
							if (confirm("原密码错误,是否关闭")) 
							{					
								window.close();
							}
							//location.href = "<%=strContext%>/system/V857.jsp";
						</script>
		    		<%
				}
			}
 
          
if(  strSuccess.equals("1"))
{
%>
<script language="JavaScript">
	if (confirm("密码修改成功,是否关闭")) 
	{					
		window.close();
	}
	//window.location.href="../NextMenu.jsp?userPrivilegeNo=6-2";
</script>
<%	
}	  
        }catch(Exception e)
        {
          out.println(e.toString());
        }
        
      }
      try{     		
            List list = pwBiz.getOBUserPwdInfo(sessionMng.m_lUserID);
            password = (String)list.get(0);
            if(password!=null)
            {
       	     password = password.replaceAll("'","\\\\'");			//转义，将'变为\'
            }
            passwordInfo = pwBiz.getPasswordConfigInfo(sessionMng.m_lOfficeID);
      }catch(Exception e){
      		out.println(e.toString());
      }

%>
<jsp:include flush="true" page="/ShowMessage.jsp" />
<safety:resources />
<html>
<head>
<title>修改密码</title>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<link rel="stylesheet" href="/Graphics/template.css" type="text/css">
<script language="JavaScript">
setSubmitFunction("doSubmit()");
<!--
function  ConfirmBack()
{
	var i;
	var sTmp;
	var form1;
	sTmp = "";
	form1 = document.form_1;
	var newPwd = form1.txtPW.value;
	if( form1.txtOldPW.value == null || form1.txtOldPW.value.length == "" )
	{
		alert("请输入原密码");
		return false;
	}
	if( form1.txtPW.value == null || form1.txtPW.value.length == "" )
	{
		alert("请输入新密码");
		return false;
	}
	if( form1.txtPW1.value == null || form1.txtPW1.value.length == "" )
	{
		alert("请输入确认密码");
		return false;
	}
	
	//if(form1.txtOldPW.value != '<%=password%>'){
	//	alert("您输入的原密码不正确，请重新输入");
	//	return false;
	//}
	
	if( form1.txtPW.value !=  form1.txtPW1.value )
	{
		alert("确认密码与新密码不一致，请重新输入");
		return false;
	}
	if(newPwd == form1.txtOldPW.value){
 		alert("新密码不应与原密码相同，请重新输入");
 		return false;
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
		return false;
	}else if((hasNumber+hasLowercase+hasCapital+hasSpecial)==0 && hasLength > 0){
		if( <%=passwordInfo.getMinPassword()%> != <%=Constant.PASSWORD_MAX_LENGTH%> ){
			alert("请重新输入密码，长度为<%=passwordInfo.getMinPassword()%>到<%=Constant.PASSWORD_MAX_LENGTH%>");
		}else if( <%=passwordInfo.getMinPassword()%> == <%=Constant.PASSWORD_MAX_LENGTH%> ){
			alert("请重新输入密码，长度为<%=passwordInfo.getMinPassword()%>");
		}
		return false;
	}
  
  if (confirm("是否更改?")) 
  {
    return true;
  } else 
  {
    return false;
  }
}
function doSubmit()
{
    if(!ConfirmBack()) return;
    submit();
}
function Cancel()
{
	if (confirm("是否放弃?")) 
	{
		window.close();
    //  window.location.href="../NextMenu.jsp?userPrivilegeNo=6-2";
	} 
}
//-->
</script>
</head>
<style type="text/css">

<!--
.top {  border: thin outset; background-color: #D7DFE5}
.box {  border: 1px #333333 inset; background-color: #FFFFCC}
.button {  border: thin outset; background-color: #D7DFE5; font-family: "Arial", "Helvetica", "sans-serif"; font-size: 12px; font-style: normal; color: #333333}
-->
</style>
<body>
<form name="form_1" action="<%=strContext%>/system/V857.jsp?control=view" method="post">
<table align="center" width="360" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td>
			<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
			  <tr>
			    <td height="4"></td>
			  </tr>
			  <tr>
			    <td height="1" bgcolor="#47BBD2"></td>
			  </tr>
			  <tr>
			    <td height="24" valign="top">
					<table width="150" border="0" cellspacing="0" cellpadding="0">
				      <tr>
				        <td width="1" bgcolor="#47BBD2"></td>
				        <td width="124" background="/webob/graphics/new_til_bg.gif"><span class="txt_til2">更改密码</span></td>
				        <td width="25" align="right"><img src="/webob/graphics/newconner.gif" width="30" height="22"></td>
				      </tr>
				    </table>
			    </td>
			  </tr>
			</table>
		</td>
	</tr>
	<tr>
		<td height="10"></td>
	</tr>
	<tr>
		<td>
	    	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="normal" align="center">
		        <tr>
		          <td width="5%" height="40"></td>
		          <td width="30%">原密码：</td>
		          <td width="60%" align="left">
		          	<input type="password" name="txtOldPW" size="25" maxlength="20" value="" onpaste="return false" oncontextmenu="return false">
		          </td>
		          <td width="5%"></td>
				</tr>
		        <tr>
		          <td width="5%" height="40"></td>
		          <td width="30%">新密码：</td>
		          <td width="60%" align="left">
		          	<input type="password" name="txtPW" size="25" maxlength="20" value="" onpaste="return false" oncontextmenu="return false">
		          </td>
		          <td width="5%"></td>
				</tr>
		        <tr>
		          <td width="5%" height="40"></td>
		          <td width="30%">确认新密码：</td>
		          <td width="60%" align="left">
		          	<input type="password" name="txtPW1" size="25" maxlength="20" value="" onpaste="return false" oncontextmenu="return false">
		          </td>
		          <td width="5%"></td>
				</tr>
		        <tr>
		          <td width="5%" height="40"></td>
		          <td width="90%" colspan="2">
			        <hr />
		          </td>
		          <td width="5%"></td>
				</tr>
		        <tr>
		          <td width="5%" height="40"></td>
		          <td width="90%" colspan="2" align="center">
			        <input type="submit" name="SUBMIT" value=" 确 认 " >&nbsp;&nbsp;
					<input type="button" name="cancel" value=" 取 消 " onClick="Javascript:Cancel();">
		          </td>
		          <td width="5%"></td>
				</tr>
			</table>
		</td>
	</tr>
</table>
</form>
</body>
</html>

<%
}
catch (IException ie)
{
	OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
}
	OBHtml.showOBHomeEnd(out);
 %>
 
 <%@ include file="/common/SignValidate.inc" %>
 