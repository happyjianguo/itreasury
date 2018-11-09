<%--
 页面名称 ：v003-3.jsp
 页面功能 : 保证金支取业务打印选择界面
 作    者 ：mingfang
 日    期 ：2006-04-12
 特殊说明 ：简单实现说明：
 保证金支取:		
			进账单
			特种转账（借/贷方）凭证
			存款利息计付通知单
			存款支取凭证
 修改历史 ：
--%>
<%@ page contentType="text/html;charset=gbk" %>
<%@ page import="com.iss.itreasury.util.Log"%>
<%@ page import="com.iss.itreasury.util.Constant"%>
<%@ page import="com.iss.itreasury.util.Config"%>
<%@ page import="com.iss.itreasury.util.ConfigConstant"%>
<%@ page import="com.iss.itreasury.settlement.util.SETTHTML"%>
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
			//请求检测
//			if(!SETTHTML.validateRequest(out, request,response)) return;
%>
	<jsp:include page="/ShowMessage.jsp"/>
<%
			
			//页面辅助变量
			String strAction = null;
			String strSuccessPageURL = "";
			String strFailPageURL = "";
			String strActionResult = Constant.ActionResult.FAIL;
			
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
			
			strTemp = (String)request.getParameter("lTransTypeID");
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				lTransactionTypeID = Long.valueOf(strTemp).longValue();
			}

%>	
<html>
<head>
<Script Language="JavaScript">
self.resizeTo(160,400);
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
		if (document.frmv003.jzd.checked == true)//进账单
		{
			strfrmList = strfrmList + "1";
		}
		else
		{
			strfrmList = strfrmList + "a";
		}
		if (document.frmv003.tzzzjfpz.checked == true)//特种转账借方凭证
		{
			strfrmList = strfrmList + "2";
		}
		else
		{
			strfrmList = strfrmList + "a";
		}
		if (document.frmv003.tzzzdfpz.checked == true)//特种转账贷方凭证
		{
			strfrmList = strfrmList + "3";
		}
		else
		{
			strfrmList = strfrmList + "a";
		}
		
		if (document.frmv003.cklxjftzd.checked == true)//存款利息计付通知单
		{
			strfrmList = strfrmList + "4";
		}
		else
		{
			strfrmList = strfrmList + "a";
		}
	 	
		if (document.frmv003.ckzqpz.checked == true)//存款支取凭证
		{
			strfrmList = strfrmList + "5";
		}
		else
		{
			strfrmList = strfrmList + "a";
		}
	
		if (document.frmv003.PrintTemplate1.checked == true)
		{
			strfrmList = strfrmList + "b";
		}
		else
		{
			strfrmList = strfrmList + "a";
		}
		if (document.frmv003.PrintTemplate2.checked == true)
		{
			strfrmList = strfrmList + "c";
		}
		else
		{
			strfrmList = strfrmList + "a";
		}
		if (document.frmv003.PrintTemplate3.checked == true)
		{
			strfrmList = strfrmList + "d";
		}
		else
		{
			strfrmList = strfrmList + "a";
		}				
		if (document.frmv003.PrintTemplate4.checked == true)
		{
			strfrmList = strfrmList + "e";
		}
		else
		{
			strfrmList = strfrmList + "a";
		}	
		if (document.frmv003.PrintTemplate5.checked == true)
		{
			strfrmList = strfrmList + "f";
		}
		else
		{
			strfrmList = strfrmList + "a";
		}	
		if (document.frmv003.PrintTemplate6.checked == true)
		{
			strfrmList = strfrmList + "g";
		}
		else
		{
			strfrmList = strfrmList + "a";
		}	
		if (document.frmv003.PrintTemplate7.checked == true)
		{
			strfrmList = strfrmList + "h";
		}
		else
		{
			strfrmList = strfrmList + "a";
		}	
		if (document.frmv003.PrintTemplate8.checked == true)
		{
			strfrmList = strfrmList + "i";
		}
		else
		{
			strfrmList = strfrmList + "a";
		}	
		if (document.frmv003.PrintTemplate9.checked == true)
		{
			strfrmList = strfrmList + "j";
		}
		else
		{
			strfrmList = strfrmList + "a";
		}	
		if (document.frmv003.PrintTemplate10.checked == true)
		{
			strfrmList = strfrmList + "k";
		}
		else
		{
			strfrmList = strfrmList + "a";
		}	
		if (document.frmv003.PrintTemplate11.checked == true)
		{
			strfrmList = strfrmList + "l";
		}
		else
		{
			strfrmList = strfrmList + "a";
		}	
		if (document.frmv003.PrintTemplate12.checked == true)
		{
			strfrmList = strfrmList + "m";
		}
		else
		{
			strfrmList = strfrmList + "a";
		}	
		
		if (document.frmv003.PrintTemplate13.checked == true)
		{
			strfrmList = strfrmList + "n";
		}
		else
		{
			strfrmList = strfrmList + "a";
		}	
	

		window.open('a1014p-c.jsp?lID=<%=lID%>&lTransactionTypeID=<%=lTransactionTypeID%>&strPrintPage='+strfrmList);
		window.close();
	}
}

function check(box)
{
	if (box.checked == true) 
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
	<form name="frmv003" method="post" action="../a1014p-c1.jsp">
        <input name="strAction"  type="hidden">
	    <input name="strSuccessPageURL"  type="hidden" value="<%=strSuccessPageURL%>">
		<input name="strFailPageURL"  type="hidden" value="<%=strFailPageURL%>">
		<input name="strContinueSave" type="hidden" value="false">
		<input name="lID"  type="hidden" value="<%=lID%>">
		<input name="lTransactionTypeID"  type="hidden" value="<%=lTransactionTypeID%>">

  <table width="150" align="center">
    <tr bordercolor="#d6d3ce">
      <td colspan="2" height="30"> 
          <input type="checkbox" name="jzd" value="checkbox" onClick="check(this)">
          进账单
      </td>
    </tr>

    <tr bordercolor="#d6d3ce"> 
     <td colspan="2" height="30">  
        <input type="checkbox" name="tzzzjfpz" value="checkbox" onClick="check(this)">
        特种转账借方凭证</td>
    </tr>
	<tr bordercolor="#d6d3ce"> 
      <td colspan="2" height="30">  
        <input type="checkbox" name="tzzzdfpz" value="checkbox" onClick="check(this)">
        特种转账贷方凭证</td>
    </tr>
	<tr bordercolor="#d6d3ce"> 
      <td colspan="2" height="30">  
        <input type="checkbox" name="cklxjftzd" value="checkbox" onClick="check(this)">
        存款利息计付通知单</td>
    </tr>
	<tr bordercolor="#d6d3ce"> 
      <td colspan="2" height="30">  
        <input type="checkbox" name="ckzqpz" value="checkbox" onClick="check(this)">
        存款支取凭证</td>
    </tr>
	  <tr bordercolor="#d6d3ce"> 
      <td colspan="2" height="30">  
        <input type="checkbox" name="PrintTemplate1" value="checkbox" onClick="check(this)">
        <%=IPrintTemplate.getPrintTemplateNameByID(1)%></td>
    </tr>

    <tr bordercolor="#d6d3ce"> 		
      <td colspan="2" height="30">   
        <input type="checkbox" name="PrintTemplate2" value="checkbox" onClick="check(this)">
        <%=IPrintTemplate.getPrintTemplateNameByID(2)%></td>
    </tr>

    <tr bordercolor="#d6d3ce"> 
      <td colspan="2" height="30">  
        <input type="checkbox" name="PrintTemplate3" value="checkbox" onClick="check(this)">
        <%=IPrintTemplate.getPrintTemplateNameByID(3)%></td>
    </tr>

    <tr bordercolor="#d6d3ce"> 
      <td colspan="2" height="30">  
        <input type="checkbox" name="PrintTemplate4" value="checkbox" onClick="check(this)">
        <%=IPrintTemplate.getPrintTemplateNameByID(4)%></td>
    </tr>
    <tr bordercolor="#d6d3ce"> 
      <td colspan="2" height="30">  
        <input type="checkbox" name="PrintTemplate5" value="checkbox" onClick="check(this)">
        <%=IPrintTemplate.getPrintTemplateNameByID(5)%></td>
    </tr>

    <tr bordercolor="#d6d3ce"> 
      <td colspan="2" height="30">   
        <input type="checkbox" name="PrintTemplate6" value="checkbox" onClick="check(this)">
        <%=IPrintTemplate.getPrintTemplateNameByID(6)%></td>
    </tr>
    <tr bordercolor="#d6d3ce"> 
      <td colspan="2" height="30">  
        <input type="checkbox" name="PrintTemplate7" value="checkbox" onClick="check(this)">
        <%=IPrintTemplate.getPrintTemplateNameByID(7)%></td>
    </tr>

    <tr bordercolor="#d6d3ce"> 
      <td colspan="2" height="30">   
        <input type="checkbox" name="PrintTemplate8" value="checkbox" onClick="check(this)">
        <%=IPrintTemplate.getPrintTemplateNameByID(8)%></td>
    </tr>
    <tr bordercolor="#d6d3ce"> 
      <td colspan="2" height="30">  
        <input type="checkbox" name="PrintTemplate9" value="checkbox" onClick="check(this)">
        <%=IPrintTemplate.getPrintTemplateNameByID(9)%></td>
    </tr>

    <tr bordercolor="#d6d3ce"> 
      <td colspan="2" height="30">   
        <input type="checkbox" name="PrintTemplate10" value="checkbox" onClick="check(this)">
        <%=IPrintTemplate.getPrintTemplateNameByID(10)%></td>
    </tr>
    <tr bordercolor="#d6d3ce"> 
      <td colspan="2" height="30">  
        <input type="checkbox" name="PrintTemplate11" value="checkbox" onClick="check(this)">
        <%=IPrintTemplate.getPrintTemplateNameByID(11)%></td>
    </tr>
    <tr bordercolor="#d6d3ce"> 
      <td colspan="2" height="30">   
        <input type="checkbox" name="PrintTemplate12" value="checkbox" onClick="check(this)">
        <%=IPrintTemplate.getPrintTemplateNameByID(12)%></td>
    </tr>
    <tr bordercolor="#d6d3ce"> 
      <td colspan="2" height="30">   
        <input type="checkbox" name="PrintTemplate13" value="checkbox" onClick="check(this)">
        <%=IPrintTemplate.getPrintTemplateNameByID(13)%></td>
    </tr>


	<tr bordercolor="#d6d3ce">
      <td width="124" height="30">
        <div align="center"> 
          <input type="button" name="Submit" value=" 确 定 " onClick="sendback1()" class="button">
        </div>
      </td>
      <td height="30" width="14">&nbsp;</td>
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