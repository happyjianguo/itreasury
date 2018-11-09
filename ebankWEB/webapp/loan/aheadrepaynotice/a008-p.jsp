<%@ page contentType="text/html;charset=gbk" %> 

<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<%@ page import="java.util.*,
com.iss.itreasury.loan.util.*,
com.iss.itreasury.util.*,
com.iss.itreasury.ebank.util.*,
com.iss.itreasury.ebank.obaheadrepaynotice.dataentity.*"
%>
<%
 //response.setHeader("Cache-Control","no-stored");
// response.setHeader("Pragma","no-cache");
 //response.setDateHeader("Expires",0);
%>
<%
	/* 标题固定变量 */
	String strTitle = "[贷款还款通知单]";
%>	
<%
	  try
	  {
	   /* 用户登录检测 */
        if (sessionMng.isLogin() == false)
        {
            OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E002");
        	out.flush();
        	return;
        }

        /* 判断用户是否有权限 */
        if (sessionMng.hasRight(request) == false)
        {
        	out.println(sessionMng.hasRight(request));
        	OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E003");
        	out.flush();
        	return;
        }
		
		AheadRepayNoticeInfo info  = (AheadRepayNoticeInfo) request.getAttribute("info");
		

%>
<HTML><HEAD><TITLE>贷款还款通知单</TITLE>

<link rel="stylesheet" href="/itreasury/css/style.css" type="text/css">
<style type="text/css">
<!--
.table1 {  border: 2px #000000 solid}
.td-right {  border-color: black black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 1px; border-bottom-width: 0px; border-left-width: 0px}
.td-topright {  border-color: black black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 1px; border-bottom-width: 0px; border-left-width: 0px}
.td-top {  border-color: black black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 0px; border-bottom-width: 0px; border-left-width: 0px}
.td-right2 {  border-color: black black black #000000; border-style: double; border-top-width: 0px; border-right-width: 3px; border-bottom-width: 0px; border-left-width: 0px}
.td-leftbottom {  border-color: black black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 1px; border-left-width: 1px}
.td-topbottom2right {  border-color: #000000 black black; border-style: solid; border-top-width: 1px; border-right-width: 1px; border-bottom-width: 2px; border-left-width: 0px}
.td-topbottom2 {  border-color: black black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 0px; border-bottom-width: 2px; border-left-width: 0px}
.td-bottom {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 1px; border-left-width: 0px}
.td-rightbottom {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 1px; border-bottom-width: 1px; border-left-width: 0px}
.td-rightbottomleft {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 1px; border-bottom-width: 1px; border-left-width: 1px}
.td-bottom2 {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 2px; border-left-width: 0px}
-->
</style>
<SCRIPT language="javascript" src="/webob/js/Control.js"></SCRIPT>
<SCRIPT language=JavaScript src="/webob/js/Check.js"></SCRIPT>

</HEAD>

<object id="factory" style="display:none"  classid="clsid:1663ed61-23eb-11d2-b92f-008048fdd814" codebase="http://www.meadroid.com/scriptx/ScriptX.cab#Version=5,60,0,360"></object>
<BODY>
	
<table width="600" border="0" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF" bordercolor="#000000" align="center">  
	<tr>    
      <td height="100" align="center"><b><FONT SIZE="5" COLOR="">贷款还款通知单</FONT></b></td>
	</tr>
	<tr>    
      <td height="50" align="center">编号：<%=info.getCode()%></td>
	</tr>
	<tr>     
      <td valign="bottom"> 
        <table width="100%" border="1" cellpadding="0" cellspacing="0" bordercolor="#000000">          
		  <tr> 
            <td width="22%" height="30">借款单位：</td>
            <td colspan=3>&nbsp;<%=info.getClientName()%></td>
		  </tr>
          <tr> 
            <td width="22%" height="30">合同编号：</td>
            <td width="30%" height="30">&nbsp;<%=info.getContractCode()%></td>
            <td width="22%" height="30">贷款期限：</td>
            <td width="26%" height="30">&nbsp;<%=info.getIntervalNum()%> 月</td>
          </tr>		  
          <tr> 
            <td height="30">放款通知单编号：</td>
            
          <td>&nbsp;<%=info.getLetoutNoticeCode()%></td>
            <td>放款通知单利率：</td>
            
          <td>&nbsp;<%= DataFormat.formatRate(info.getLetoutNoticeRate())%>%</td>
          </tr>
		  <tr> 
            <td height="30">放款通知单金额：</td>
            
          <td>&nbsp;<%=sessionMng.m_strCurrencySymbol%> <%=DataFormat.formatDisabledAmount(info.getLetoutNoticeAmount())%></td>
		    <td>放款通知单余额：</td>
            
          <td>&nbsp;<%=sessionMng.m_strCurrencySymbol%> <%=DataFormat.formatDisabledAmount(info.getLetoutNoticeBalance())%></td>
          </tr>
		  <tr> 
            <td height="30">还款金额：</td>
            
          <td>&nbsp;<%=sessionMng.m_strCurrencySymbol%> <%=DataFormat.formatDisabledAmount(info.getAmount())%></td>
		   <td>还款利率：</td>
            
          <td>&nbsp; <%=DataFormat.formatRate(info.getLetoutNoticeRate())%>%</td>
          </tr>
		   <tr> 
            <td width="17%" height="30">是否提前还款：</td>
            <td colspan="3"> 
<%=LOANConstant.YesOrNo.getName(info.getIsAhead())%>      
            </td>
           
          </tr>
        </table>
		</td>
	  </tr>
	  <tr>    
        <td height="30">&nbsp;</td>
	  </tr>  
</table>

<P><BR></P>

<SCRIPT language="JavaScript">
	window.print();
</SCRIPT>

</BODY></HTML>

<%
		OBHtml.showOBHomeEnd(out);	
	}
	catch(IException ie) 
	{
		OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
		return;
    }
%>