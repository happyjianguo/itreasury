<%@ page contentType="text/html;charset=gbk"%>
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
<%@ page import="com.iss.itreasury.settlement.print.IPrintTemplate" %>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
 
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<!--类导入部分结束-->
<!--类导入部分结束-->

<%
	 
	response.setHeader("Pragma","no-cache");
	 
%>

<%
  int nPageLine=25;//定义每页行数
  int i = 0;
 
    try
	{
		// 用户登录检测 
		//请求检测
		 /*if (sessionMng.isLogin() == false)
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
 
		/**打印方法*/
		IPrintTemplate.showPrintHead(out,true,"A4","",1,sessionMng.m_lOfficeID);

%>
<jsp:include flush="true" page="/ShowMessage.jsp" />
<safety:resources />
		<body text="#000000" bgcolor="#FFFFFF">
		<table width="960" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td width="15%">&nbsp;</td>
				<td width="70%" align="center"><b><font style="font-size:22px">[账户信息]--汇总账户信息查询--历史余额查询 
				</font></b></td>
				<td width="15%">&nbsp;</td>
			</tr>
		</table>
			<br>
			
				<TABLE width="900" border="2" cellspacing="0" cellpadding="0"  class="table1"  align="center" >
					 
						<TR>
				            <TD class=td-rightbottom align=center width="8%">国家</TD>
				            <TD class=td-rightbottom align=center width="8%">区域中心</TD>
				            <TD class=td-rightbottom  align=center width="12%">银行</TD>
							 <TD class=td-rightbottom align=center width="10%">账户类型</TD>
							<TD class=td-rightbottom align=center width="15%">账号</TD>
							<TD class=td-rightbottom align=center width="15%">账户名称</TD>
							<TD class=td-rightbottom align=center width="8%">币种</TD>
							<TD class=td-rightbottom  align=center width="12%">日期</TD>
							<TD class=td-rightbottom align=center width="15%">余额</TD>
						</TR>
						
						
						<%
							int nLine=0;
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
								 
								nLine++;
								if(nLine%nPageLine==0 && nLine<queryResults.length )//如果行数大于每页行数，则退出，即分页
								{
									 int i1 = 0;
						 %>
							 	</TABLE>
							 <br>
							<table  width="900" border="0" align="center">
								<tr>
								
									 <td width="70%" >操作人：<%=sessionMng.m_strUserName%></td>
									<td width="30%" >打印时间：<%=DataFormat.getDateString()%></td>
						
								</tr>		
		 		
							</table>


							<br clear=all style='page-break-before:always'>
								<body text="#000000" bgcolor="#FFFFFF">
					<table width="960" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td width="15%">&nbsp;</td>
				<td width="70%" align="center"><b><font style="font-size:22px"></font></b></td>
				<td width="15%">&nbsp;</td>
			</tr>
		</table>


				
				<br>
				<TABLE width="900" border="2" cellspacing="0" cellpadding="0"  class="table1"  align="center" >
				 
						
						<TR >
				             <TD class=td-rightbottom align=center width="8%"></TD>
				            <TD class=td-rightbottom align=center width="8%"></TD>
				            <TD class=td-rightbottom align=center width="12%"></TD>
							 <TD class=td-rightbottom align=center width="10%"></TD>
							<TD class=td-rightbottom align=center width="15%"></TD>
							<TD class=td-rightbottom align=center width="15%"></TD>
							<TD class=td-rightbottom align=center width="8%"></TD>
							<TD class=td-rightbottom  align=center width="12%"></TD>
							
							<TD class=td-rightbottom align=center width="15%"></TD>
						</TR>
						
				

						<%		 }

							}
							%>
								</table>	
						<%}
						 
						%>
						 
		 <br>
	 
		<table  width="900" border="0" align="center">
			<tr>
				
                 <td width="70%" >操作人：<%=sessionMng.m_strUserName%></td>
				<td width="30%">打印时间：<%=DataFormat.getDateString()%></td>
		
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


<%@ include file="/common/SignValidate.inc" %>
		