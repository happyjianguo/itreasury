<%@ page contentType="application/msexcel;charset=gbk" %>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<!--类导入部分开始-->
 

<%@ page import="com.iss.itreasury.util.Log"%>
<%@ page import="com.iss.itreasury.util.DataFormat"%>
<%@ page import="com.iss.itreasury.util.Constant.PageControl"%>
<%@ page import="com.iss.itreasury.util.PageCtrlInfo"%>

<%@ page import="com.iss.itreasury.ebank.util.OBHtml"%>
<%@ page import="com.iss.itreasury.ebank.util.NameRef"%>
<%@ page import="com.iss.itreasury.settlement.util.SETTConstant"%>
<%@ page import="com.iss.itreasury.ebank.obquery.bizlogic.OBHisBalanceBiz"%>
<%@ page import="com.iss.itreasury.ebank.obquery.dataentity.OBQueryAccInfo"%>
<%@ page import="com.iss.itreasury.ebank.obquery.dataentity.OBHisBalanceResultInfo"%>
<%@ page import="com.iss.system.dao.PageLoader"%>
<%@ page import="com.iss.system.dao.PageLoaderInfo"%>
<%@ page import="com.iss.itreasury.settlement.print.IPrintTemplate" %>
<!--类导入部分结束-->

<%
	response.setHeader("Content-Disposition","; filename=\treport.xls");
	
%>

<%
	 int i = 0;
    try
	{
		// 用户登录检测 
		//请求检测
		/* if (sessionMng.isLogin() == false)
        {
            OBHtml.showCommonMessage(out, sessionMng, "", "",1, "Gen_E002");
        	out.flush();
        	return;
        }*/
		

        /* 判断用户是否有权限 */
        if (sessionMng.hasRight(request) == false)
        {
        	out.println(sessionMng.hasRight(request));
        	OBHtml.showCommonMessage(out, sessionMng, "", "",1, "Gen_E003");
        	out.flush();
        	return;
        }
		
		//强制转换
		Object[] queryResults = null;
		queryResults = (OBHisBalanceResultInfo[])request.getAttribute(PageControl.SearchResults);
		
		
		/**业务逻辑开始*/
		 
		IPrintTemplate.showPrintHead(out,true,"A4","",1,sessionMng.m_lOfficeID);

%>
	<html>
<head>
<title></title>
<!--
<link rel="stylesheet" href="/webob/template.css" type="text/css">
-->
<style type="text/css">
<object id="factory" style="display:none" viewastext classid="clsid:1663ed61-23eb-11d2-b92f-008048fdd814" codebase="http://www.meadroid.com/scriptx/ScriptX.cab#Version=5,60,0,360"></object>

</style>
</head>
	<body text="#000000" bgcolor="#FFFFFF">
		<TABLE width=900>
  	<TBODY>
	
  		<TR>
    		<TD width="900" class="" align="center" height="20"><B>[账户信息]--汇总账户信息查询--历史余额查询 
    		</B></TD>
		</TR>
  		
		<TR>
			<TD>
				<TABLE width="900" border="0" cellspacing="0" cellpadding="0"  class="table1"  align="center" >
					<TBODY>
						
						<TR >
				           <TD class=td-rightbottom align=center width="8%">国家</TD>
				            <TD class=td-rightbottom align=center width="8%">区域中心</TD>
				            <TD class=td-rightbottom  align=center width="12%">银行</TD>
							 <TD class=td-rightbottom align=center width="10%">账户类型</TD>
							<TD class=td-rightbottom align=center width="15%">账号</TD>
							<TD class=td-rightbottom align=center width="15%">账户名称</TD>
							<TD class=td-rightbottom align=center width="8%">币种</TD>
							<TD class=td-rightbottom  align=center width="12%">日期</TD>
							<TD class=td-rightbottom width="15%">余额</TD>
						</TR>
						
						<%
						if( queryResults != null && queryResults.length >0 )
						{
							for(int j = 0;j<queryResults.length;j++)
							{
								OBHisBalanceResultInfo info = (OBHisBalanceResultInfo)queryResults[j];
								String AccounttypeName = "&nbsp;";
								String strCurrencyName = "&nbsp;";
								strCurrencyName = NameRef.getCurrencyNamebyId(info.getCurrencyID(),"zh");
								AccounttypeName = NameRef.getAcctTypeNameByAcctTypeID(info.getAccounttype());
								 
						%>
						<TR align="center">
				            <TD class=td-topright ><%=info.getCountryName()%>&nbsp;</TD>
				            <TD class=td-topright ><%=info.getAreaName()%>&nbsp;</TD>
							<TD class=td-topright ><%=info.getBankName()%>&nbsp;</TD>
							<TD class=td-topright ><%=AccounttypeName%>&nbsp;</TD>
							<TD class=td-topright ><%=info.getAccountNo()%>&nbsp;</TD>
							<TD class=td-topright ><%=info.getAccountname()%>&nbsp;</TD>
				            <TD class=td-topright ><%=strCurrencyName%>&nbsp;</TD>
							<TD class=td-topright ><%=info.getExecuteDate().toString().substring(0,10)%>&nbsp;</TD>
							<TD class=td-topright  align="right"><%= (DataFormat.formatDisabledAmount(info.getHisBalance(),2) == null)? "" : DataFormat.formatDisabledAmount(info.getHisBalance(), 2) %>&nbsp;</TD>
							 
						</TR>
						<%
								 
								 
							}
						}
						else
						{
						%>
						<TR >
				           <TD class=td-topright>&nbsp;</TD>
				            <TD class=td-topright>&nbsp;</TD>
				            <TD class=td-topright>&nbsp;</TD>
				            <TD class=td-topright>&nbsp;</TD>
				            <TD class=td-topright>&nbsp;</TD>
							<TD class=td-topright>&nbsp;</TD>
							<TD class=td-topright>&nbsp;</TD>
							<TD class=td-topright>&nbsp;</TD>
							<TD class=td-topright>&nbsp;</TD>
							 
						</TR>
						<%
						}

						%>
						</TBODY>
				</TABLE>
			</TD>
		</TR>
		<table  width="900" border="0" align="center">
			<tr>
				
                 <td width="70%" >操作人：<%=sessionMng.m_strUserName%></td>
				<td width="30%" >打印时间：<%=DataFormat.getDateString()%></td>
		
			</tr>		
		
		
	</table>
		<script language= "javascript">
	factory.printing.Print(true);
</script>

		<!--页面脚本元素结束-->
<%
	}
	catch( Exception exp )
	{
		exp.printStackTrace();
		OBHtml.showCommonMessage(out,sessionMng,"","",1,"Gen_E001"); 
	}
	//显示页面尾

	/**页面显示结束*/
%>
		