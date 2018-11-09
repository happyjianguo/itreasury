<%--
 页面名称 ：a915-v.jsp
 页面功能 : 贷款发放业务打印选择界面
 作    者 ：qqgd
 日    期 ：2003-11-17
 特殊说明 ：简单实现说明：
 修改历史 ：
--%>
<%@ page contentType="text/html;charset=gbk" %>
<%@ page import="java.sql.Timestamp"%>
<%@ page import="com.iss.itreasury.util.Env"%>
<%@ page import="com.iss.itreasury.ebank.util.*"%>
<%@ page import="com.iss.itreasury.util.Log"%>
<%@ page import="com.iss.itreasury.util.Constant"%>
<%@ page import="com.iss.itreasury.util.DataFormat"%>
<%@ page import="com.iss.itreasury.settlement.util.SETTMagnifier"%>
<%@ page import="com.iss.itreasury.settlement.util.SETTConstant"%>	
<%@ page import="com.iss.itreasury.settlement.util.NameRef"%>	
<%@ page import="com.iss.itreasury.settlement.print.PrintInfo"%>
<%@ page import="com.iss.itreasury.settlement.print.IPrintTemplate"%>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>

<% String strContext = request.getContextPath();%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<jsp:include flush="true" page="/ShowMessage.jsp" />
<script language="JavaScript" src="/webob/js/Control.js" ></script>
<script language="JavaScript" src="/webob/js/SettCheck.js"></script>
<%
	try
	{
		//判断是否登录
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

			//页面辅助变量
			String strAction = null;
			String strSuccessPageURL = "";
			String strFailPageURL = "";
			String strActionResult = Constant.ActionResult.FAIL;
			
			//
			//业务变量
			long lID = -1;
			long lTransactionTypeID = -1;
			
			
			String strTemp = "";
			
			strTemp = (String)request.getParameter("strSuccessPageURL");
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				strSuccessPageURL = strTemp;
			}
			
			strTemp = (String)request.getParameter("strFailPageURL");
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				strFailPageURL = strTemp;
			}
			
			strTemp = (String)request.getParameter("lID");
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				lID = Long.valueOf(strTemp).longValue();
			}
			
			strTemp = (String)request.getParameter("lTransactionTypeID");
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				lTransactionTypeID = Long.valueOf(strTemp).longValue();
			}
			
			long lOBReturn=-1;
			strTemp=(String)request.getAttribute("lReturn");
			if ( (strTemp!=null)&&(strTemp.length()>0) )
			{
				lOBReturn=Long.valueOf(strTemp).longValue();
			}
%>	
<safety:resources />
<html>
<head>
<Script Language="JavaScript">
self.resizeTo(200,250);
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
	if (num == 0)
	{ 
		alert("请选择需要打印的资料！");
	}
	else 
	{
		if (document.frmv004.jzd.checked == true)// 进账单
		{
			strfrmList = strfrmList + "1";
		}
		else
		{
			strfrmList = strfrmList + "a";
		}
		<%if(lTransactionTypeID == SETTConstant.TransactionType.TRUSTLOANGRANT)
		{%>
		if (document.frmv004.zydkffpz.checked == true)// 自营贷款发放凭证
		{
			strfrmList = strfrmList + "8";
		}
		else
		{
			strfrmList = strfrmList + "a";
		}
		<%
		}
		if(lTransactionTypeID == SETTConstant.TransactionType.CONSIGNLOANGRANT)
		  {%>
			if (document.frmv004.wtdkffpz.checked == true)// 委托贷款发放凭证
			{
				strfrmList = strfrmList + "9";
			}
			else
			{
				strfrmList = strfrmList + "a";
			}
			
			if (document.frmv004.tzzzpz.checked == true)//特种转账凭证
			{
				strfrmList = strfrmList + "2";
			}
			else
			{
				strfrmList = strfrmList + "a";
			}
		<%}%>
		
		window.open('../common/p001-c.jsp?lID=<%=lID%>&printPage=../accountinfo/a916-c.jsp&lTransactionTypeID=<%=lTransactionTypeID%>&strPrintPage='+strfrmList);
		window.close();
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
function closeThis()
{
	window.close();
}
</Script>
<body bgcolor="#d6d3ce"  text="#000000">
	<form name="frmv004" method="post" action="../accountinfo/a916-c.jsp">
        <input name="strAction"  type="hidden">
	    <input name="strSuccessPageURL"  type="hidden" value="<%=strSuccessPageURL%>">
		<input name="strFailPageURL"  type="hidden" value="<%=strFailPageURL%>">
		<input name="strContinueSave" type="hidden" value="false">
		<input name="lID"  type="hidden" value="<%=lID%>">
		
  <table width="150" class="table" align="center">
  <% if ( (lOBReturn==OBConstant.CheckSettType.RecAndPayID)||(lOBReturn==OBConstant.CheckSettType.RecID) ){  %> 
    <tr bordercolor="#d6d3ce"> 
      <td colspan="2" height="30"> 
          <input type="checkbox" name="jzd" value="checkbox" onClick="check(this)">
          进账单
      </td>
    </tr>
	<%
	if (lTransactionTypeID == SETTConstant.TransactionType.TRUSTLOANGRANT)
	{
	%>
    <tr bordercolor="#d6d3ce"> 
      <td colspan="2" height="30"> 
          <input type="checkbox" name="zydkffpz" value="checkbox" onClick="check(this)">
          自营贷款发放凭证
      </td>
    </tr>
	<%
	}
	if (lTransactionTypeID == SETTConstant.TransactionType.CONSIGNLOANGRANT)
	{
	%>
	 <tr bordercolor="#d6d3ce"> 
      <td colspan="2" height="30"> 
          <input type="checkbox" name="wtdkffpz" value="checkbox" onClick="check(this)">
          委托贷款发放凭证
      </td>
    </tr>
	<tr bordercolor="#d6d3ce"> 
      <td colspan="2" height="30">  
        <input type="checkbox" name="tzzzpz" value="checkbox" onClick="check(this)">
        特种转账凭证</td>
    </tr>
	<%
	}
	%>
  	<tr bordercolor="#d6d3ce">
      <td width="124" height="30">
        <div align="center"> 
          <input type="button" name="Submit" value=" 确 定 " onClick="sendback1()" class="button">
        </div>
      </td>
      <td height="30" width="14">&nbsp;</td>
    </tr>
   <% } else { %>
	<tr bordercolor="#d6d3ce"> 
     <td colspan="2" height="30">  无可打印票据 </td>
    </tr>
  	<tr bordercolor="#d6d3ce">
      <td width="124" height="30">
        <div align="center"> 
          <input type="button" name="Submit" value=" 确 定 " onClick="closeThis()" class="button">
        </div>
      </td>
      <td height="30" width="14">&nbsp;</td>
    </tr>
<% } %>          
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
	//.showHomeEnd(out);
%>

<%@ include file="/common/SignValidate.inc" %>
