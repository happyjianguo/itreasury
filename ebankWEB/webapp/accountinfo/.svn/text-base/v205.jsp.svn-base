<%--
 页面名称 ：v017.jsp
 页面功能 : 资金上收凭证打印
 作    者 ：xgzhang
 日    期 ：2005-09-09
 特殊说明 ：简单实现说明：
 	1、收方凭证:进账单、特种转账借方凭证、特种转账贷发凭证
 	2、付发凭证:进账单、特种转账借方凭证、特种转账贷发凭证
 修改历史 ：
--%>
<%@ page contentType="text/html;charset=gbk" %>
<%@ page import="java.sql.Timestamp"%>
<%@ page import="com.iss.itreasury.util.Env"%>
<%@ page import="com.iss.itreasury.util.Log"%>
<%@ page import="com.iss.itreasury.util.Constant"%>
<%@ page import="com.iss.itreasury.util.DataFormat"%>
<%@ page import="com.iss.itreasury.util.Config"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtml"%>
<%@ page import="com.iss.itreasury.util.ConfigConstant"%>
<%@ page import="com.iss.itreasury.settlement.util.SETTMagnifier"%>
<%@ page import="com.iss.itreasury.settlement.util.SETTHTML"%>
<%@ page import="com.iss.itreasury.settlement.util.SETTConstant"%>	
<%@ page import="com.iss.itreasury.settlement.util.NameRef"%>	
<%@ page import="com.iss.itreasury.settlement.print.PrintInfo"%>
<%@ page import="com.iss.itreasury.settlement.print.IPrintTemplate"%>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>

<% String strContext = request.getContextPath();%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<jsp:include flush="true" page="/ShowMessage.jsp" />
<script language="JavaScript" src="/websett/js/Control.js" ></script>
<script language="JavaScript" src="/websett/js/SettCheck.js"></script>
<safety:resources />
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

		 
%>
	<jsp:include page="/ShowMessage.jsp"/>
<%		
			long lDirectionID = -1; //收付方向
			String strTemp = null;
			strTemp = (String) request.getAttribute("DirectionID");
 			if (strTemp != null && strTemp.trim().length() > 0) {
				lDirectionID = Long.parseLong(strTemp.trim());
			}
			//页面辅助变量
			String strPageLoaderKey = (String)request.getAttribute("_pageLoaderKey");
%>	
<html>
<head>
<Script Language="JavaScript">
self.resizeTo(175,300);
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

function sendback1()
{
 	var strfrmList = "";
 	var isPay = ('<%=lDirectionID%>' == '<%=SETTConstant.ReceiveOrPay.PAY%>');
	if (num == 0)
	{ 
		alert("请选择需要打印的资料！");
	}
	else 
	{
		
	   if (document.frm.ckRA.checked == true)//收方进账单
		{
			if(isPay)
				strfrmList = strfrmList + "B";
			else
				strfrmList = strfrmList + "A";
		}
		else
		{
			strfrmList = strfrmList + "a";
		}
		 
		
		 
		if (document.frm.ckRB.checked == true)//收方特种转账借方凭证
		{
			if(isPay)
				strfrmList = strfrmList + "C";
			else
				strfrmList = strfrmList + "D";
		}
		else
		{
			strfrmList = strfrmList + "a";
		}
		 
		if (document.frm.ckRC.checked == true)//收方特种转账贷方凭证
		{
			if(isPay)
				strfrmList = strfrmList + "E";
			else
				strfrmList = strfrmList + "F";
		}
		else
		{
			strfrmList = strfrmList + "a";
		} 
		frm.action = '<%=strContext%>'+"/pagecontrol.jsp";
		
		frm.strPrintPage.value = strfrmList;
		frm.strAction.value ='<%=Constant.PageControl.LISTALL%>';
		frm.strSuccessPageURL.value = '/accountinfo/c204.jsp';
		frm.strFailPageURL.value = '/accountinfo/v205.jsp';
		frm.submit();
	}
}	

function check(box)
{
  if (box.checked == false) 
  {
  	num = num + 1;
  }
  else
  {
  	num = num - 1;
  }
}
</Script>
<body bgcolor="#d6d3ce"  text="#000000">
<form name="frm" method="post" action="../control/c005.jsp">
<input type="hidden" name="strAction">
<input type="hidden" name="strSuccessPageURL">
<input type="hidden" name="strFailPageURL">
<input type="Hidden" name="strPrintPage">
<input type="Hidden" name="_pageLoaderKey" value="<%=strPageLoaderKey%>">
  <table width="150" name="table" align="center">
  	
    <tr bordercolor="#d6d3ce" > 
      <td  height="30"> 
      <input type="checkbox" name="ckRA" value="checkbox" onClick="check(this)">
        进账单 </td>   
    </tr>
   <tr bordercolor="#d6d3ce"> 
      <td height="30"><input type="checkbox" name="ckRB" value="checkbox" onClick="check(this)">
        特种转账借方传票 </td>
    </tr>
    <tr bordercolor="#d6d3ce">
      <td height="30"><input type="checkbox" name="ckRC" value="checkbox" onClick="check(this)">
        特种转账贷方传票</td>
    </tr>
     <tr bordercolor="#d6d3ce"> 
      <TD> 
        <hr>
      </TD>
      
    </tr>
	<tr bordercolor="#d6d3ce"> 
      <td height="30" align="left"> 
          <input type="button" name="Submit" value=" 确 定 " onClick="sendback1()" class="button">
          <input type="button" name="Submit" value="关闭窗口" onClick="window.close()" class="button">
      </td>
    </tr>
  </table>
</form>
</body>
</html>
<%
    }
	catch( Exception exp )
	{
		exp.printStackTrace();
		Log.print(exp.getMessage());
	}
	SETTHTML.showHomeEnd(out);
%>

<%@ include file="/common/SignValidate.inc" %>