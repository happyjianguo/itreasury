<%--
/**
 页面名称 ：a408-v.jsp
 页面功能 : 总账类业务打印选择界面
 作    者 ：kewen hu
 日    期 ：2004-02-24
 特殊说明 ：简单实现说明：
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
<script language="JavaScript" src="/websett/js/Control.js" ></script>
<script language="JavaScript" src="/websett/js/SettCheck.js"></script>
<script language="javascript" src="/webob/js/Check.js"></script>
<script language="javascript" src="/webob/js/Control.js"></script>
<script language="javascript" src="/webob/js/date-picker.js"></script>
<script language="javascript" src="/webob/js/DropDownData.js"></script>
<script language="javascript" src="/webob/js/MagnifierSQL.js"></script>
<safety:resources />

<%
    Log.print("\n*******进入页面--ebank/capital/accountinfo/a408-v.jsp******\n");
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

			long lID = -1;
			
			//页面辅助变量
			String strAction = null;
			String strSuccessPageURL = "";
			String strFailPageURL = "";
			String strActionResult = Constant.ActionResult.FAIL;
			
			String strTemp = "";
			strTemp = (String)request.getParameter("lID");
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				lID = Long.valueOf(strTemp).longValue();
			}
			strTemp = (String)request.getParameter("lReturn");
			long lReturn = -1;
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				lReturn = Long.valueOf(strTemp).longValue();
			}
			Log.print("=============lReturn="+lReturn);
			strSuccessPageURL = (String)request.getAttribute("strSuccessPageURL");
			strFailPageURL = (String)request.getAttribute("strFailPageURL");

%>	
<html>
<head>
<Script Language="JavaScript">
self.resizeTo(640,450);
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
<%
switch ((int) lReturn) {
	case (int) OBConstant.CheckSettType.RecAndPayID:
%>
		if (document.frmv008.jzd1.checked == true)//进账单1
		{
			strfrmList = strfrmList + "b";
		}
		else
		{
			strfrmList = strfrmList + "a";
		}
		if (document.frmv008.tzzzjfpz1.checked == true)//特种转账借方凭证1
		{
			strfrmList = strfrmList + "e";
		}
		else
		{
			strfrmList = strfrmList + "a";
		}
		if (document.frmv008.tzzzdfpz1.checked == true)//特种转账贷方凭证1
		{
			strfrmList = strfrmList + "c";
		}
		else
		{
			strfrmList = strfrmList + "a";
		}
		if (document.frmv008.ckzqpz1.checked == true)//存款支取凭证1
		{
			strfrmList = strfrmList + "d";
		}
		else
		{
			strfrmList = strfrmList + "a";
		}
		
		if (document.frmv008.jzd2.checked == true)//进账单2
		{
			strfrmList = strfrmList + "f";
		}
		else
		{
			strfrmList = strfrmList + "a";
		}

		if (document.frmv008.tzzzjfpz2.checked == true)//特种转账借方凭证2
		{
			strfrmList = strfrmList + "i";
		}
		else
		{
			strfrmList = strfrmList + "a";
		}
		if (document.frmv008.tzzzdfpz2.checked == true)//特种转账贷方凭证2
		{
			strfrmList = strfrmList + "g";
		}
		else
		{
			strfrmList = strfrmList + "a";
		}
		if (document.frmv008.ckzqpz2.checked == true)//存款支取凭证2
		{
			strfrmList = strfrmList + "h";
		}
		else
		{
			strfrmList = strfrmList + "a";
		}
		
		if (document.frmv008.jzd3.checked == true)//进账单3
		{
			strfrmList = strfrmList + "j";
		}
		else
		{
			strfrmList = strfrmList + "a";
		}
		if (document.frmv008.tzzzjfpz3.checked == true)//特种转账借方凭证3
		{
			strfrmList = strfrmList + "m";
		}
		else
		{
			strfrmList = strfrmList + "a";
		}
		if (document.frmv008.tzzzdfpz3.checked == true)//特种转账贷方凭证3
		{
			strfrmList = strfrmList + "k";
		}
		else
		{
			strfrmList = strfrmList + "a";
		}
		if (document.frmv008.ckzqpz3.checked == true)//存款支取凭证3
		{
			strfrmList = strfrmList + "l";
		}
		else
		{
			strfrmList = strfrmList + "a";
		}
		if (document.frmv008.jzd4.checked == true)//进账单4
		{
			strfrmList = strfrmList + "n";
		}
		else
		{
			strfrmList = strfrmList + "a";
		}
		if (document.frmv008.tzzzjfpz4.checked == true)//特种转账借方凭证4
		{
			strfrmList = strfrmList + "y";
		}
		else
		{
			strfrmList = strfrmList + "a";
		}
		if (document.frmv008.tzzzdfpz4.checked == true)//特种转账贷方凭证4
		{
			strfrmList = strfrmList + "o";
		}
		else
		{
			strfrmList = strfrmList + "a";
		}
		if (document.frmv008.ckzqpz4.checked == true)//存款支取凭证4
		{
			strfrmList = strfrmList + "x";
		}
		else
		{
			strfrmList = strfrmList + "a";
		}
<%
	break;
	case (int) OBConstant.CheckSettType.PayID:
%>
		if (document.frmv008.tzzzjfpz1.checked == true)//特种转账借方凭证1
		{
			strfrmList = strfrmList + "e";
		}
		else
		{
			strfrmList = strfrmList + "a";
		}
		if (document.frmv008.ckzqpz1.checked == true)//存款支取凭证1
		{
			strfrmList = strfrmList + "d";
		}
		else
		{
			strfrmList = strfrmList + "a";
		}
		if (document.frmv008.tzzzjfpz2.checked == true)//特种转账借方凭证2
		{
			strfrmList = strfrmList + "i";
		}
		else
		{
			strfrmList = strfrmList + "a";
		}
		if (document.frmv008.ckzqpz2.checked == true)//存款支取凭证2
		{
			strfrmList = strfrmList + "h";
		}
		else
		{
			strfrmList = strfrmList + "a";
		}
		if (document.frmv008.tzzzjfpz3.checked == true)//特种转账借方凭证3
		{
			strfrmList = strfrmList + "m";
		}
		else
		{
			strfrmList = strfrmList + "a";
		}
		if (document.frmv008.ckzqpz3.checked == true)//存款支取凭证3
		{
			strfrmList = strfrmList + "l";
		}
		else
		{
			strfrmList = strfrmList + "a";
		}
		if (document.frmv008.tzzzjfpz4.checked == true)//特种转账借方凭证4
		{
			strfrmList = strfrmList + "y";
		}
		else
		{
			strfrmList = strfrmList + "a";
		}
		if (document.frmv008.ckzqpz4.checked == true)//存款支取凭证4
		{
			strfrmList = strfrmList + "x";
		}
		else
		{
			strfrmList = strfrmList + "a";
		}
<%
	break;
	case (int) OBConstant.CheckSettType.RecID:
%>
		if (document.frmv008.jzd1.checked == true)//进账单1
		{
			strfrmList = strfrmList + "b";
		}
		else
		{
			strfrmList = strfrmList + "a";
		}
		if (document.frmv008.tzzzdfpz1.checked == true)//特种转账贷方凭证1
		{
			strfrmList = strfrmList + "c";
		}
		else
		{
			strfrmList = strfrmList + "a";
		}
		if (document.frmv008.jzd2.checked == true)//进账单2
		{
			strfrmList = strfrmList + "f";
		}
		else
		{
			strfrmList = strfrmList + "a";
		}
		if (document.frmv008.tzzzdfpz2.checked == true)//特种转账贷方凭证2
		{
			strfrmList = strfrmList + "g";
		}
		else
		{
			strfrmList = strfrmList + "a";
		}
		if (document.frmv008.jzd3.checked == true)//进账单3
		{
			strfrmList = strfrmList + "j";
		}
		else
		{
			strfrmList = strfrmList + "a";
		}
		if (document.frmv008.tzzzdfpz3.checked == true)//特种转账贷方凭证3
		{
			strfrmList = strfrmList + "k";
		}
		else
		{
			strfrmList = strfrmList + "a";
		}
		if (document.frmv008.jzd4.checked == true)//进账单4
		{
			strfrmList = strfrmList + "n";
		}
		else
		{
			strfrmList = strfrmList + "a";
		}
		if (document.frmv008.tzzzdfpz4.checked == true)//特种转账贷方凭证4
		{
			strfrmList = strfrmList + "o";
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
		window.open("<%=strContext%>/common/p003-c.jsp?lID=<%=lID%>&printPage=/accountinfo/a408-c.jsp&strPrintPage="+strfrmList);
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
	<form name="frmv008" method="post" action="../control/c008.jsp">
        <input name="strAction"  type="hidden">
	    <input name="strSuccessPageURL"  type="hidden" value="<%=strSuccessPageURL%>">
		<input name="strFailPageURL"  type="hidden" value="<%=strFailPageURL%>">
		<input name="strContinueSave" type="hidden" value="false">
		<input name="lID"  type="hidden" value="<%=lID%>">
  <table width="700 class="table" align="center">
<% lReturn = -1;
switch ((int) lReturn) {
	case (int) OBConstant.CheckSettType.RecAndPayID:
%>
    <tr bordercolor="#d6d3ce"> 
      <td>金额一</td>
      <td>金额二</td>
      <td>金额三</td>
      <td height="30">金额四</td>
    </tr>
    <tr bordercolor="#d6d3ce"> 
      <td><input type="checkbox" name="jzd1" value="checkbox" onClick="check(this)">
        进账单 </td>
      <td><input type="checkbox" name="jzd2" value="checkbox" onClick="check(this)">
        进账单 </td>
      <td><input type="checkbox" name="jzd3" value="checkbox" onClick="check(this)">
        进账单 </td>
      <td height="30"> <input type="checkbox" name="jzd4" value="checkbox" onClick="check(this)">
        进账单 </td>
    </tr>
    <tr bordercolor="#d6d3ce"> 
      <td><input type="checkbox" name="tzzzjfpz1" value="checkbox" onClick="check(this)">
        特种转账借方传票</td>
      <td><input type="checkbox" name="tzzzjfpz2" value="checkbox" onClick="check(this)">
        特种转账借方传票</td>
      <td><input type="checkbox" name="tzzzjfpz3" value="checkbox" onClick="check(this)">
        特种转账借方传票</td>
      <td height="30"> <input type="checkbox" name="tzzzjfpz4" value="checkbox" onClick="check(this)">
        特种转账借方传票</td>
    </tr>
    <tr bordercolor="#d6d3ce"> 
      <td> <input type="checkbox" name="ckzqpz1" value="checkbox" onClick="check(this)">
        存款支取凭证</td>
      <td> <input type="checkbox" name="ckzqpz2" value="checkbox" onClick="check(this)">
        存款支取凭证</td>
      <td> <input type="checkbox" name="ckzqpz3" value="checkbox" onClick="check(this)">
        存款支取凭证</td>
      <td height="30"> <input type="checkbox" name="ckzqpz4" value="checkbox" onClick="check(this)">
        存款支取凭证</td>
    </tr>
    <tr bordercolor="#d6d3ce"> 
      <td><input type="checkbox" name="tzzzdfpz1" value="checkbox" onClick="check(this)">
        特种转账贷方传票</td>
      <td><input type="checkbox" name="tzzzdfpz2" value="checkbox" onClick="check(this)">
        特种转账贷方传票</td>
      <td><input type="checkbox" name="tzzzdfpz3" value="checkbox" onClick="check(this)">
        特种转账贷方传票</td>
      <td height="30"> <input type="checkbox" name="tzzzdfpz4" value="checkbox" onClick="check(this)">
        特种转账贷方传票</td>
    </tr>
<%
	break;
	case (int) OBConstant.CheckSettType.PayID:
%>
    <tr bordercolor="#d6d3ce"> 
      <td>金额一</td>
      <td>金额二</td>
      <td>金额三</td>
      <td height="30">金额四</td>
    </tr>
    <tr bordercolor="#d6d3ce"> 
      <td> <input type="checkbox" name="ckzqpz1" value="checkbox" onClick="check(this)">
        存款支取凭证</td>
      <td> <input type="checkbox" name="ckzqpz2" value="checkbox" onClick="check(this)">
        存款支取凭证</td>
      <td> <input type="checkbox" name="ckzqpz3" value="checkbox" onClick="check(this)">
        存款支取凭证</td>
      <td height="30"> <input type="checkbox" name="ckzqpz4" value="checkbox" onClick="check(this)">
        存款支取凭证</td>
    </tr>
    <tr bordercolor="#d6d3ce"> 
      <td><input type="checkbox" name="tzzzjfpz1" value="checkbox" onClick="check(this)">
        特种转账借方传票</td>
      <td><input type="checkbox" name="tzzzjfpz2" value="checkbox" onClick="check(this)">
        特种转账借方传票</td>
      <td><input type="checkbox" name="tzzzjfpz3" value="checkbox" onClick="check(this)">
        特种转账借方传票</td>
      <td height="30"> <input type="checkbox" name="tzzzjfpz4" value="checkbox" onClick="check(this)">
        特种转账借方传票</td>
    </tr>
<%
	break;
	case (int) OBConstant.CheckSettType.RecID:
%>
    <tr bordercolor="#d6d3ce"> 
      <td>金额一</td>
      <td>金额二</td>
      <td>金额三</td>
      <td height="30">金额四</td>
    </tr>
    <tr bordercolor="#d6d3ce"> 
      <td><input type="checkbox" name="jzd1" value="checkbox" onClick="check(this)">
        进账单 </td>
      <td><input type="checkbox" name="jzd2" value="checkbox" onClick="check(this)">
        进账单 </td>
      <td><input type="checkbox" name="jzd3" value="checkbox" onClick="check(this)">
        进账单 </td>
      <td height="30"> <input type="checkbox" name="jzd4" value="checkbox" onClick="check(this)">
        进账单 </td>
    </tr>
    <tr bordercolor="#d6d3ce"> 
      <td><input type="checkbox" name="tzzzdfpz1" value="checkbox" onClick="check(this)">
        特种转账贷方传票</td>
      <td><input type="checkbox" name="tzzzdfpz2" value="checkbox" onClick="check(this)">
        特种转账贷方传票</td>
      <td><input type="checkbox" name="tzzzdfpz3" value="checkbox" onClick="check(this)">
        特种转账贷方传票</td>
      <td height="30"> <input type="checkbox" name="tzzzdfpz4" value="checkbox" onClick="check(this)">
        特种转账贷方传票</td>
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
      <td height="30" colspan="4"> <div align="center"> 
          <input type="button" name="Submit" value=" 确 定 " onClick="window.close();" class="button">
        </div></td>
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
