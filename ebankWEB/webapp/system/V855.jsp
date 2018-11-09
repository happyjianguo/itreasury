<%--
/**
 * �������ƣ�V855.jsp
 * ����˵����ϵͳ����-�û�����
 * �������ߣ�����
 * ������ڣ�2003��9��8��
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
//�̶�����
String strTitle = "[�û������]";
try
{
	/**
	* isLogin start
	*/
	//��¼���
	if( sessionMng.isLogin() == false)
	{
		OBHtml.showCommonMessage(out, sessionMng, strTitle, "", 1, "Gen_E002");
		out.flush();
		return;
	}
	//�ж��Ƿ���Ȩ��
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
	  
		//������
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
alert("����ɹ�");
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
    			throw new Exception("���ݿ�ر��쳣����",e);			
    	    }
    	}
%>
<html>
<head>
<title>�޸�����</title>
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
		alert("�������¼����");
		return false;
	}
	if( form1.txtPW1.value == null || form1.txtPW1.value.length == "" )
	{
		alert("���ٴ������¼����");
		return false;
	}
	if( form1.txtPW.value !=  form1.txtPW1.value )
	{
		alert("���һ�κ͵ڶ�����������������ȫһ��");
		return false;
	}
    //ȫ�� 2010-5-14
    if(form1.txtPW.value.length < <%=Constant.PASSWORD_MIN_LENGTH%> || form1.txtPW.value.length > <%=Constant.PASSWORD_MAX_LENGTH%>){
        alert("��������볤�ȱ�����6��20֮��");
        return false;
    
    }  
*/   
 
  if (confirm("�Ƿ����?")) 
  {
    return true;
  } else 
  {
    return false;
  }
}
function  ConfirmCancel()
{
	if (confirm("�Ƿ����?")) 
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
		       <td class=title><span class="txt_til2">��������</span></td>
		       <td class=title_right><a class=img_title></td>
			</TR>
		</table> 
		<br>
	<table>
  <tr> 
    <td height="32" width="152" nowrap> 
      <div align="center"> ������: </div>
    </td>
    <td height="32" width="130" align="right"> 
      <div align="left">
        <input id="txtPW" class="box" type="password" name="txtPW" size="20" onpaste="return false" value="<%=strPW%>" maxlength="20">
      </div>
    </td>
  </tr>
  <tr> 
    <td height="35" width="152" nowrap> 
      <div align="center">ȷ��������:</div>
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
        <input type="submit" name="SUBMIT" value=" ȷ�� " class="button1" >
        <input type="button" name="cancel" value=" ���� " class="button1" onClick="Javascript:ConfirmCancel();">
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