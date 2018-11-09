<%--
/**
 页面名称 ：a404-v.jsp
 页面功能 : 定期续存业务打印选择界面
 作    者 ：kewen hu
 日    期 ：2004-03-01
 特殊说明 ：简单实现说明：
 定期续存
           定期存款开户证实书
           存款利息计付通知单（利息总额凭证）
           特种转账（借/贷方）凭证
 修改历史 ：
 */
--%>

<%@ page contentType="text/html;charset=gbk" %>
<%@ page import="java.sql.Timestamp"%>
<%@ page import="com.iss.itreasury.util.Env"%>
<%@ page import="com.iss.itreasury.util.Log"%>
<%@ page import="com.iss.itreasury.util.Constant"%>
<%@ page import="com.iss.itreasury.ebank.util.OBConstant"%>
<%@ page import="com.iss.itreasury.util.IException"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtml"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtmlCom"%>
<%@ page import="com.iss.itreasury.ebank.util.OBOperation"%>
<%@ page import="com.iss.itreasury.util.DataFormat"%>
<%@ page import="com.iss.itreasury.settlement.util.SETTMagnifier"%>
<%@ page import="com.iss.itreasury.settlement.util.SETTConstant"%>	
<%@ page import="com.iss.itreasury.settlement.util.NameRef"%>	
<%@ page import="com.iss.itreasury.settlement.print.PrintInfo"%>
<%@ page import="com.iss.itreasury.settlement.print.IPrintTemplate"%>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>

<% String strContext = request.getContextPath();%>
<% Log.print("==============strContext="+strContext);%>

<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<jsp:include flush="true" page="/ShowMessage.jsp" />
<script language="JavaScript" src="/websett/js/SettCheck.js"></script>
<script language="javascript" src="/webob/js/Check.js"></script>
<script language="javascript" src="/webob/js/Control.js"></script>
<script language="javascript" src="/webob/js/date-picker.js"></script>
<script language="javascript" src="/webob/js/DropDownData.js"></script>
<script language="javascript" src="/webob/js/MagnifierSQL.js"></script>
<safety:resources />

<%
    Log.print("\n*******进入页面--ebank/capital/accountinfo/a404-v.jsp******\n");
    //标题变量
    String strTitle = "[账户历史明细]";
    try {
         /* 用户登录检测 */
        if (sessionMng.isLogin() == false) {
            OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E002");
            out.flush();
            return;
        }

        /* 判断用户是否有权限 */
        if (sessionMng.hasRight(request) == false) {
            out.println(sessionMng.hasRight(request));
            OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E003");
            out.flush();
            return;
        }
        /* 显示文件头 */
        //OBHtml.showOBHomeHead(out, sessionMng, strTitle, OBConstant.ShowMenu.YES);

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
			
			strTemp = (String)request.getParameter("lTransactionTypeID");
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				lTransactionTypeID = Long.valueOf(strTemp).longValue();
			}
			strTemp = (String)request.getParameter("lReturn");
			long lReturn = -1;
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				lReturn = Long.valueOf(strTemp).longValue();
			}
			Log.print("=============lReturn="+lReturn);

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
/*
		if (document.frmv003.dqckkhzss.checked == true)//定期存款开户证实书
		{
			strfrmList = strfrmList + "1";
		}
		else
		{
			strfrmList = strfrmList + "a";
		}
		if (document.frmv003.cklxjftzd.checked == true)//存款利息计付通知单
		{
			strfrmList = strfrmList + "2";
		}
		else
		{
			strfrmList = strfrmList + "a";
		}
*/
<%
switch ((int) lReturn) {
	case (int) OBConstant.CheckSettType.RecAndPayID:
%>
		if (document.frmv003.tzzzjfpz.checked == true)//特种转账借方凭证
		{
			strfrmList = strfrmList + "4";
		}
		else
		{
			strfrmList = strfrmList + "a";
		}
		
		if (document.frmv003.tzzzdfpz.checked == true)//特种转账贷方凭证
		{
			strfrmList = strfrmList + "2";
		}
		else
		{
			strfrmList = strfrmList + "a";
		}
		
		if (document.frmv003.dqkhzs.checked == true)//定期存款开户证实书
		{
			strfrmList = strfrmList + "5";
		}
		else
		{
			strfrmList = strfrmList + "a";
		}
		
		if (document.frmv003.cklxjftz.checked == true)//存款利息计付通知单
		{
			strfrmList = strfrmList + "7";
		}
		else
		{
			strfrmList = strfrmList + "a";
		}
<%
	break;
	case (int) OBConstant.CheckSettType.PayID:
%>
		if (document.frmv003.tzzzjfpz.checked == true)//特种转账借方凭证
		{
			strfrmList = strfrmList + "4";
		}
		else
		{
			strfrmList = strfrmList + "a";
		}
<%
	break;
	case (int) OBConstant.CheckSettType.RecID:
%>
		if (document.frmv003.tzzzdfpz.checked == true)//特种转账贷方凭证
		{
			strfrmList = strfrmList + "2";
		}
		else
		{
			strfrmList = strfrmList + "a";
		}
<%
	break;
	case (int) OBConstant.CheckSettType.NoRecAndPayID:
	default :
%>
	num = -1;
<%
	break;
}
%>
/*		
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
		*/
	if (num == 0)
	{ 
		alert("请选择需要打印的资料！");
	}
	else if (num == -1)
	{
		window.close();
	}
	else 
	{
		window.open("<%=strContext%>/common/p001-c.jsp?lID=<%=lID%>&printPage=/accountinfo/a404-c.jsp&lTransTypeID=<%=lTransactionTypeID%>&strPrintPage="+strfrmList);
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
<body bgcolor="#d6d3ce"  text="#000000">
	<form name="frmv003" method="post" action="../control/c003.jsp">
        <input name="strAction"  type="hidden">
	    <input name="strSuccessPageURL"  type="hidden" value="<%=strSuccessPageURL%>">
		<input name="strFailPageURL"  type="hidden" value="<%=strFailPageURL%>">
		<input name="strContinueSave" type="hidden" value="false">
		<input name="lID"  type="hidden" value="<%=lID%>">
  <table width="150" class="table" align="center">
    <!--tr bordercolor="#d6d3ce">
      <td colspan="2" height="30"> 
          <input type="checkbox" name="dqckkhzss" value="checkbox" onClick="check(this)">
        定期存款开户证实书
      </td>
    </tr>
    <tr bordercolor="#d6d3ce"> 
     <td colspan="2" height="30">  
        <input type="checkbox" name="cklxjftzd" value="checkbox" onClick="check(this)">
        存款利息计付通知单</td>
    </tr-->
<%
switch ((int) lReturn) {
	case (int) OBConstant.CheckSettType.RecAndPayID:
%>
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
        <input type="checkbox" name="dqkhzs" value="checkbox" onClick="check(this)">
        定期存款开户证实书</td>
    </tr>
	<tr bordercolor="#d6d3ce"> 
      <td colspan="2" height="30">  
        <input type="checkbox" name="cklxjftz" value="checkbox" onClick="check(this)">
        存款利息计付通知单</td>
    </tr>
<%
	break;
	case (int) OBConstant.CheckSettType.PayID:
%>
	<tr bordercolor="#d6d3ce"> 
      <td colspan="2" height="30">  
        <input type="checkbox" name="tzzzjfpz" value="checkbox" onClick="check(this)">
        特种转账借方凭证</td>
    </tr>
<%
	break;
	case (int) OBConstant.CheckSettType.RecID:
%>
	<tr bordercolor="#d6d3ce"> 
      <td colspan="2" height="30">  
        <input type="checkbox" name="tzzzdfpz" value="checkbox" onClick="check(this)">
        特种转账贷方凭证</td>
    </tr>
<%
	break;
	case (int) OBConstant.CheckSettType.NoRecAndPayID:
	default :
%>
    <tr bordercolor="#d6d3ce"> 
     <td colspan="2" height="30">  
        没有可打的单据</td>
    </tr>
<%
	break;
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
  </table>
</form>
</body>
</html>
<%
    } catch(IException ie) {
        OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
    }
%>

<%@ include file="/common/SignValidate.inc" %>