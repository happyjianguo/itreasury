<%
/**
 * 页面名称 ：v012_P.jsp
 * 页面功能 : 交易记录查询——结果显示的页面
 * 作    者 ：Boxu
 * 日    期 ：2006-12-14
 * 特殊说明 ：
 *			
 * 修改历史 ：
 */
%>
<%@ page contentType="text/html;charset=gbk" %>
<%@ page import="java.sql.Timestamp"%>
<%@ page import="java.util.Collection"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="com.iss.itreasury.util.Env"%>
<%@ page import="com.iss.itreasury.util.Log"%>
<%@ page import="com.iss.itreasury.util.Constant"%>
<%@ page import="com.iss.itreasury.util.DataFormat"%>
<%@ page import="com.iss.itreasury.settlement.util.SETTMagnifier"%>
<%@ page import="com.iss.itreasury.settlement.util.SETTHTML"%>
<%@ page import="com.iss.itreasury.settlement.query.resultinfo.QueryTransactionInfo"%>
<%@ page import="com.iss.itreasury.settlement.query.resultinfo.QueryTransactionSumInfo"%>
<%@ page import="com.iss.itreasury.settlement.bankbill.dataentity.QueryCondition_Sett_BankBill"%>
<%@ page import="com.iss.itreasury.settlement.util.SETTConstant"%>
<%@ page import="com.iss.itreasury.settlement.util.NameRef"%>
<%@ page import="com.iss.itreasury.ebank.util.*"%>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>

<% String strContext = request.getContextPath();%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<jsp:include flush="true" page="/ShowMessage.jsp" />
<script language="JavaScript" src="/webob/js/Control.js"></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>
<script language="JavaScript" src="/webob/js/date-picker.js"></script>
<script language="JavaScript" src="/webob/js/MagnifierSQL.js"></script>
<safety:resources />

<head>
	<link rel="stylesheet" href="/webob/css/style.css" type="text/css">
</head>

 
<%
	/* 标题固定变量 */
	String strTitle = "";
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
%>
	<jsp:include page="/ShowMessage.jsp"/>
<%	
	Log.print("1");
	String strPageLoaderKey = (String)request.getAttribute("_pageLoaderKey");
	Log.print("Result Page ::　strPageLoaderKey : " + strPageLoaderKey);
	String strTemp = null;
	QueryTransactionInfo[] resultInfos = (QueryTransactionInfo[])request.getAttribute(Constant.PageControl.SearchResults);
	Log.print("2");
	Log.print("resultInfos :  " + resultInfos);
	//
	String strSuccessPageURL = (String)request.getAttribute("strSuccessPageURL");
	String strFailPageURL = (String)request.getAttribute("strFailPageURL");

	long lStatusID = -1;

	String StatusID = ""; // 交易记录状态: save 未复核，check 已复核

	strTemp = (String)request.getAttribute("lTransactionStatusID");
	if (strTemp != null && strTemp.trim().length() > 0)
	{	
		session.setAttribute("lTransactionStatusID",strTemp);
		lStatusID = Long.valueOf(strTemp).longValue();
	}
	
	//if(lStatusID == 2)
	//{
	//	strTitle = "业务复核——查询结果";
	//	StatusID = "save";
	//}
	//if(lStatusID == 3)
	//{
	//	strTitle = "业务复核——查询结果";
	//	StatusID = "check";
	//}

	QueryTransactionSumInfo sumInfo = (QueryTransactionSumInfo)sessionMng.getSumResult(strPageLoaderKey);
	Log.print("sumInfo :  " + sumInfo);
	Log.print("3");
	
	//显示文件头
    OBHtml.showOBHomeHead(out, sessionMng, strTitle, OBConstant.ShowMenu.YES);
%>

<form name="frmV012" method="post" >

 <input type="hidden" name="StatusID" value="<%=StatusID%>">

<table cellpadding="0" cellspacing="0" class="title_top">
	  <tr>
	    <td height="24">
		    <table cellspacing="0" cellpadding="0" class=title_Top1>
				<TR>
			       <td class=title><span class="txt_til2">补打申请</span></td>
			       <td class=title_right><a class=img_title></td>
				</TR>
			</TABLE>
			<br/>
	      <table  width=100% border="0" cellspacing="0" cellpadding="0" class=normal >
	        <tr align="center"> 
	          <td width="14%" height="20" class="ItemTitle">交易日</td>
	          <td width="14%" height="20" class="ItemTitle">交易号</td>
	          <td width="14%" height="20" class="ItemTitle">摘要</td>
	          <td width="14%" height="20" class="ItemTitle">对方账号</td>
	          <td width="16%" height="20" class="ItemTitle">对方账户名称</td>
	          <td width="14%" height="20" class="ItemTitle">付款金额</td>
	          <td width="14%" height="20" class="ItemTitle" nowrap>收款金额</td>
	        </tr>
	<%
			int resultCount = 0;
			for( int i=0; resultInfos != null && i<resultInfos.length; i++ )
			{
				resultCount++;
				
				QueryTransactionInfo resultInfo = (QueryTransactionInfo)resultInfos[i];
				String strDetailPageURL = "";
			
			String payaccountno = "";
			//付款方客户
			if ( resultInfo.getPayClientID() == sessionMng.m_lClientID ) 
			{
				payaccountno = resultInfo.getPayAccountNo();
			}
			//收款方客户
			else
			{
				payaccountno = resultInfo.getReceiveAccountNo();
			}
			
			strDetailPageURL = strContext+"/print/control/c012_P.jsp?strSuccessPageURL=../view/v013_P.jsp&nextAction=view&strFailPageURL=../view/v012_P.jsp&strAction=toModify&payaccountno="+payaccountno+"&TransactionTypeID="+resultInfo.getTransactionTypeID()+"&TransNo="+resultInfo.getTransNo()+"&lID="+resultInfo.getID()+"&_pageLoaderKey="+strPageLoaderKey+"";
			
	%>	
	        <tr bordercolor="#999999" align="center" class="ItemBody">
	          <td nowrap class="ItemBody" width="14%" height="20"><%=DataFormat.getDateString(resultInfo.getExecute())%></td>
	          <td nowrap class="ItemBody" width="14%" height="20">
			    <A href="<%=strDetailPageURL%>"><%=((resultInfo.getTransNo() == null || resultInfo.getTransNo().equals("")) ? "&nbsp;" : resultInfo.getTransNo())%></a>
			  </td>
			  <td width="14%" height="20"><%=resultInfo.getAbstract() == null ? "&nbsp;" : resultInfo.getAbstract()%></td>
	          <%
	          	if ( resultInfo.getPayClientID() == sessionMng.m_lClientID ) {
	          %>
	          <td nowrap class="ItemBody" width="14%" height="20"><%=resultInfo.getReceiveAccountNo() == null ? "&nbsp;" : resultInfo.getReceiveAccountNo()%></td>
	          <td nowrap class="ItemBody" width="16%" height="20"><%= resultInfo.getReceiveAccountName() %></td>
	          <td nowrap class="ItemBody" width="14%" height="20" align="right"><%=DataFormat.formatDisabledAmount(resultInfo.getPayAmount())%></td>
	          <td nowrap class="ItemBody" width="14%" height="20" align="right">&nbsp;</td>
	          <% } else
	          	//if ( resultInfo.getReceiveClientID() == sessionMng.m_lClientID ) 
	          	 { %>
	          <td nowrap class="ItemBody" width="14%" height="20"><%=resultInfo.getPayAccountNo() == null ? "&nbsp;" : resultInfo.getPayAccountNo()%></td>
	          <td nowrap class="ItemBody" width="16%" height="20"><%= resultInfo.getPayAccountName() %></td>
	          <td nowrap class="ItemBody" width="14%" height="20" align="right">&nbsp;</td>
	          <td nowrap class="ItemBody" width="14%" height="20" align="right"><%=DataFormat.formatDisabledAmount(resultInfo.getReceiveAmount())%></td>
	          <% } %>
	        </tr>
	        
	        <tr width="100%" height="1"><td colspan="7" bgcolor="#C5E7F8" height="1"></td></tr>
	        
	<%  
			}
			//没有记录显示空白行
			if (resultCount == 0)
			{
	%>		
			<tr bordercolor="#999999" align="center" class="ItemBody"> 
	          <td width="14%" height="20">&nbsp;</td>
	          <td width="14%" height="20">&nbsp;</td>
	          <td width="14%" height="20">&nbsp;</td>
	          <td width="14%" height="20">&nbsp;</td>
	          <td width="16%" height="20">&nbsp;</td>
	          <td width="14%" height="20" align="right">&nbsp;</td>
	          <td width="14%" height="20" align="right">&nbsp;</td>
	        </tr>
	<%
			}
	%>	
	
	</table>
	</td>
	</tr>
	</table>
	</form>
	<br/>
	<table width="80%">
	<!-- 分页控件 -->
		<TR>
			<td width="100%" align="right">
				<table width=100% cellspacing="3" cellpadding="0" class="SearchBar" height="19" >
					  <TR>
				           <TD height=20 width=100% align="right">
				             <DIV align=right>
							    <jsp:include page="/pagenavigator.jsp"  />
							 </DIV>
						   </TD>
					  </TR>
			     </table> 
		     </td>
	     </TR> 
	</table>

	  <br/>
      <table width=80% border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td nowrap>
            <div align="right">
			<input class="button1" name="Submit23" value=" 返 回 "  type=button onClick="doBack();">
			</div>
          </td>
        </tr>
      </table> 

<form name="listReport" method="post" >
	<input name="strAction"  type="hidden">
	<input name="strSuccessPageURL"  type="hidden" >
	<input name="strFailPageURL"  type="hidden" >
	<input name="_pageLoaderKey"  type="hidden" value="<%=strPageLoaderKey%>">
	<input name="lTransactionStatusID"  type="hidden" value="<%= lStatusID %>">
</form>

<script language='javascript'>
function doBack()
{
	window.location.href = '<%=strContext%>'+"/print/view/v011_P.jsp";
}

</script>



<%
OBHtml.showOBHomeEnd(out);
}
catch( Exception exp )
{
	exp.printStackTrace();
	Log.print(exp.getMessage());
}
%>

<%@ include file="/common/SignValidate.inc" %>