<%--
/**
 * 程序名称：V855.jsp
 * 功能说明：系统管理-用户管理
 * 作　　者：刘琰
 * 完成日期：2003年9月8日
 */
--%>

<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="com.iss.itreasury.util.*,
				 com.iss.itreasury.ebank.util.*,
                 com.iss.itreasury.system.bizlogic.EBankbean,
   				 java.sql.*,
                 com.iss.sdc.*,
				 com.toft.core2.UserInfo"
				 %>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<jsp:useBean id="getData" class="com.iss.itreasury.system.privilege.util.GetData" scope="page"/>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<%
response.setHeader("Cache-Control","no-stored");
response.setHeader("Pragma","no-cache");
response.setDateHeader("Expires",0);
%>
<% String strContext = request.getContextPath();%>
<%
//固定变量
String strTitle = "[用户组管理]";
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
		OBHtml.showCommonMessage(out, sessionMng, strTitle, "", 1,"Gen_E003");
		out.flush();
		return;
	}
	/**
	* isLogin end
	*/
	OBHtml.showOBHomeHead(out, sessionMng, strTitle, OBConstant.ShowMenu.YES);


	  String strPW = "";
	  String strPW1 = "";
	  String strTmp = "";
      String strLogin = "";
      String strSuccess = "0";
	  long lUserID = -1;
	  
		//处理汉字
		HttpServletRequest req = getData.setValue(request);
	  // extract UserID
	  strTmp = (String)req.getAttribute("UserID");
	  if( strTmp != null && strTmp.length() > 0 )
	  {
	    lUserID = Long.parseLong(strTmp);
	  }
      
	  strTmp = (String)req.getAttribute("SUBMIT");
	  if(strTmp != null && strTmp.length() > 0)
	  {// after submit
		
		// extract strUserName
	  	strTmp = (String)req.getAttribute("txtPW");
		if( strTmp != null && strTmp.length() > 0 )
	  	{
	  	  strPW = strTmp;
	  	}
		// extract strLogin
	  	strTmp = (String)req.getAttribute("txtPW1");
		if( strTmp != null && strTmp.length() > 0 )
	  	{
	  	  strPW1 = strTmp;
	  	}

		System.out.println("=================#################\t"+lUserID );

		EBankbean bean = new EBankbean();
            bean.changePassword(lUserID,strPW);
		strSuccess = "1";
		
%>

<script language="JavaScript">
alert("保存成功");
</script>
<%		          
      }
      
      	Connection conn = null;	
	    String str = null;
	    UserInfo userinfo = null;
		try
        {          
			conn = Database.getConnection();
			userinfo = (com.toft.core2.UserInfo)session.getAttribute("Toft_SessionKey_UserData");
	    	PasswodValidate val = new PasswodValidate();
    		str = val.getValidateJS(conn, "mg_passwordpolicy", userinfo);		
    		out.println(str);
    		out.flush();
        } 
        catch (Exception e) 
        {
        	e.printStackTrace();
        	throw e;
        }
        finally
        {
	        try 
            {
    			if (conn != null) 
    			{
    				conn.close();
    				conn = null;
    			}
    		} catch (Exception e) 
    		{
    			throw new Exception("数据库关闭异常发生",e);			
    	    }
    	}
%>
<html>
<head>
<title>修改密码</title>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<script language="JavaScript">
if ("<%=strSuccess%>" == "1")
{
  window.close();
}
function  ConfirmBack()
{
  var i;
  var sTmp;
  var form1;
  sTmp = "";
  form1 = document.form_1;

	if(!checkPswd("txtPW","txtPW1")) return false;
/*	
	if( form1.txtPW.value == null || form1.txtPW.value.length == "" )
	{
		alert("请输入登录密码");
		return false;
	}
	if( form1.txtPW1.value == null || form1.txtPW1.value.length == "" )
	{
		alert("请再次输入登录密码");
		return false;
	}
	if( form1.txtPW.value !=  form1.txtPW1.value )
	{
		alert("请第一次和第二次输入的密码必须完全一致");
		return false;
	}
    //全哨 2010-5-14
    if(form1.txtPW.value.length < <%=Constant.PASSWORD_MIN_LENGTH%> || form1.txtPW.value.length > <%=Constant.PASSWORD_MAX_LENGTH%>){
        alert("输入的密码长度必须在6到20之间");
        return false;
    
    }  
*/   
 
  if (confirm("是否更改?")) 
  {
    return true;
  } else 
  {
    return false;
  }
}
function  ConfirmCancel()
{
	if (confirm("是否放弃?")) 
	{
      window.close();
	} 
}

</script>
</head>

<body >
<form name="form_1" action="<%=strContext%>/system/V855.jsp?control=view&UserID=<%=lUserID%>&Login=<%=strLogin%>" method="post" onsubmit="Javascript:return ConfirmBack();">
<table width="220" height="80" align="center" class="title_top">
  <tr> 
    <td height="27" colspan="2">
        <table cellspacing="0" cellpadding="0" class=title_Top1>
			<TR>
		       <td class=title><span class="txt_til2">更改密码</span></td>
		       <td class=title_right><a class=img_title></td>
			</TR>
		</table> 
		<br>
	<table>
  <tr> 
    <td height="32" width="152" nowrap> 
      <div align="center"> 新密码: </div>
    </td>
    <td height="32" width="130" align="right"> 
      <div align="left">
        <input id="txtPW" class="box" type="password" name="txtPW" size="20" onpaste="return false" value="<%=strPW%>" maxlength="20">
      </div>
    </td>
  </tr>
  <tr> 
    <td height="35" width="152" nowrap> 
      <div align="center">确认新密码:</div>
    </td>
    <td height="35" width="130" align="right"> 
      <div align="left">
        <input id="txtPW1" class="box" type="password" name="txtPW1" size="20" onpaste="return false" value="<%=strPW1%>" maxlength="20">
      </div>
    </td>
  </tr>

  <tr> 
    <td height="43" align="right" colspan="2" nowrap>
      <div align="center"> 
        <input type="submit" name="SUBMIT" value=" 确认 " class="button1" >
        <input type="button" name="cancel" value=" 放弃 " class="button1" onClick="Javascript:ConfirmCancel();">
        </div>
    </td>
  </tr>
  </table>
  </td>
  </tr>
</table>
  </form>
<%
}
catch (IException ie)
{
	OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
}
	OBHtml.showOBHomeEnd(out);
 %>
<jsp:include flush="true" page="/ShowMessage.jsp" />
 <%@ include file="/common/SignValidate.inc" %>