
<jsp:directive.page import="com.iss.itreasury.ebank.system.dataentity.AcctTransInfo"/>
<jsp:directive.page import="com.iss.itreasury.ebank.system.dataentity.AccountInfo"/>
<jsp:directive.page import="com.iss.itreasury.util.Env"/><%
/**页面功能说明
 * 页面名称 ：v094.jsp
 * 页面功能 : 对账单导出-网银使用
 * 作    者 ：xintan
 * 日    期 ：2007-06-19
 * 简单实现说明：
 *				1、对账单信息导出
  *				2、去掉了操作人的显示
 * 特殊说明 ：
 * 修改历史 ：
 */
%>

<%@ page contentType = "text/html;charset=GBK" %>

<!--类导入部分开始-->

<jsp:directive.page import="com.iss.itreasury.util.Constant.PageControl"/>
<%@ page import="com.iss.itreasury.ebank.util.*"%>
<%@ page import="com.iss.itreasury.ebank.system.util.*"%>
<%@ page import="com.iss.itreasury.ebank.util.NameRef"%>
<jsp:directive.page import="com.iss.itreasury.ebank.system.dataentity.QueryBillPrintInfo"/>
<jsp:directive.page import="com.iss.itreasury.ebank.system.dataentity.QueryBillPrintParam"/>
<jsp:directive.page import="com.iss.itreasury.util.DataFormat"/>
<%@ page import="java.util.*"%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<!--类导入部分结束-->

<%
    try
	{
		//emoduleid等于6代表网银模块
		//String emoduleid = (String)session.getAttribute("emoduleid");
		//if ( session.getAttribute("eofficeID")==null || !emoduleid.equals("6") || session.getAttribute("eclientid")==null) {
		//		OBHtml.showCommonMessage(out, sessionMng, "", "",1, "Gen_E002");
       // 		out.flush();
		//		return;
		//}
		String strTitle = null;
		//用户登录检测 
        if (sessionMng.isLogin() == false)
        {
        	OBHtml.showCommonMessage(out, sessionMng,  strTitle, "",1, "Gen_E002");
        	out.flush();
        	return;
        }

        // 判断用户是否有权限 
        if (sessionMng.hasRight(request) == false)
        {
            out.println(sessionMng.hasRight(request));
        	OBHtml.showCommonMessage(out, sessionMng, strTitle, "", 1, "Gen_E003");
        	out.flush();
        	return;
        }
		/**页面校验开始（用户登录校验、用户权限校验、重复请求校验）*/
		JSPLogger.info("*******进入页面--account\\view\\v094-account_ebank.jsp*******");
		/**页面校验结束*/
		/**业务逻辑开始*/		
		String strContext = request.getContextPath();		
		/**返回结果对象之一：账户期初余额**/
		double beginBalance  = Double.NaN;
		beginBalance = ((Double)request.getAttribute("balanceCol")).doubleValue();		
		/**返回结果对象之二：账户历史交易信息**/
		Object[] queryResults = null;
		queryResults = (AcctTransInfo[])request.getAttribute("transInfos");
		JSPLogger.info("账户历史交易    queryResults :  " + queryResults + " length:" + ((queryResults != null)?queryResults.length:-1));		
		/**返回结果对象之三：获得银行账户基本信息**/
		AccountInfo acctInfo = new AccountInfo();
		//acctInfo = (AccountInfo)request.getAttribute("acctInfo");	
		/**查询条件**/
		QueryBillPrintParam param = new QueryBillPrintParam();
		param = (QueryBillPrintParam)request.getAttribute("param");	
		if(param!=null)
		{
			long acctId = param.getAccountId();
		    String strAccountNo = NameRef.getAccountNOByAccountID(acctId);
			acctInfo.setAccountNo(strAccountNo);
			String strAccountName = NameRef.getAccountNameByAccountID(acctId);
			acctInfo.setAccountName(strAccountName);
			acctInfo.setClientId(param.getClientIdFrom());
			acctInfo.setCurrencyType(param.getCurrencyType());	
		}	
		/**业务逻辑结束*/		
		//定义统计变量
		double startBalance          = 0.00;  //期初余额
		double sumDebitAmount  		 = 0.00;  //借方金额合计
		double sumCreditAmount 		 = 0.00;  //贷方金额合计
		double sumDebitAmountPerDay  = 0.00;  //每日借方金额合计
		double sumCreditAmountPerDay = 0.00;  //每日贷方金额合计
		double beginBalancePerDay	=0.00;	//每日的期初余额		
		String strDebitAmount = null;
		String strCreditAmount = null;
		Date   statDate       		 = null;  //日期
        String fileName = "Stat-"+acctInfo.getAccountNo()
        		+"-"+DataFormat.formatDate(param.getTransactionEndDate(),DataFormat.FMT_DATE_YYYYMMDD)
        		+".xls";
        boolean flag=true; 	    
		response.setContentType("application/msexcel;charset=GBK");
        response.setHeader("Content-Disposition",";filename="+fileName);
%>
<META http-equiv=Content-Type content="application/msexcel; charset=GBK"> 
		
<TABLE class="" width="100%">	
	  <TR>
    		<TD class=FormTitle align="center"><B>账户交易明细</B></TD>
		</TR>
	  <TR>		
			<TD>
				<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"    align="center">
					<TBODY>
						<TR>
						<BR>
							<TD valign="top" width="100%">
							<TABLE  width="100%" border="0" cellspacing="0" cellpadding="0"    align="center">
							<TBODY>
						<TR>							
							<TD nowrap> <font style="font-size: 16px">&nbsp;客户编号：</font></TD>
							<TD nowrap> <font style="font-size: 16px">&nbsp;<%=NameRef.getClientCodeByID( acctInfo.getClientId() )%></font></TD>
							<TD nowrap><font style="font-size: 16px">&nbsp;客户名称：</font></TD>
							<TD nowrap><font style="font-size: 16px">&nbsp;<%=NameRef.getClientNameByID( acctInfo.getClientId() )%></font></TD>							
						</TR>						
						<TR>							
							<TD nowrap><font style="font-size: 16px">&nbsp;账号：</font></TD>
							<TD nowrap><font style="font-size: 16px">&nbsp;<%=acctInfo.getAccountNo()%></font></TD>
							<TD nowrap><font style="font-size: 16px">&nbsp;账户名称：</font></TD>
							<TD nowrap><font style="font-size: 16px">&nbsp;<%=acctInfo.getAccountName()%></font></TD>
						</TR>						
						<TR>							
							<TD nowrap><font style="font-size: 16px">&nbsp;币种：</font></TD>
							<TD nowrap><font style="font-size: 16px">&nbsp;<%=NameRef.getCurrencyNameByID( acctInfo.getCurrencyType() )%></font></TD>
							<TD nowrap>&nbsp;</td>
							<TD nowrap>&nbsp;</td>
  						</TR>
						<TR>						
							<TD nowrap><font style="font-size: 16px">&nbsp;日期 从：</font></TD>
							<TD nowrap><font style="font-size: 16px">&nbsp;<%=  DataFormat.formatDate(param.getTransactionStartDate(),1)%></font></TD>
							<TD nowrap><font style="font-size: 16px">&nbsp;到：</font></TD>
							<TD nowrap><font style="font-size: 16px">&nbsp;<%=  DataFormat.formatDate(param.getTransactionEndDate(),1)%></font></TD>
					</TABLE>
			</TD>
		</TR>			
	</TBODY>
</TABLE>
<BR>
<TABLE width="100%" border="<%=flag ? 1:0%>" cellspacing="0" cellpadding="0"  class="table1"  align="center">
<TBODY> 		
		<TR>
			<TD>
				<TABLE width="100%" border="<%=flag ? 1:0%>" cellspacing="0" cellpadding="0"  class="table1" style="font-size: 10pt">
					<TBODY >						
						<TR >
				            <TD class=td-topright  width="8%" align="center">&nbsp;日期</TD>
				            <TD class=td-topright  width="11%" align="center">&nbsp;摘要</TD>
				            <TD class=td-topright  width="10%" align="center">&nbsp;票据号</TD>
						    <TD class=td-topright  width="16%" align="center">&nbsp;对方账号</TD>
							<TD class=td-topright  width="16%" align="center">&nbsp;对方账户名称</TD>
							<TD class=td-topright  width="13%" align="center" nowrap>&nbsp;借方金额</TD>
							<TD class=td-topright  width="13%" align="center" nowrap>&nbsp;贷方金额</TD>
							<TD class=td-topright  width="13%" align="center" nowrap>&nbsp;余额</TD>
						</TR>						
						<TR >
				            <TD class=td-topright >&nbsp;</TD>
				            <TD class=td-topright align="center">&nbsp;<b>期初余额</b></TD>
				            <TD class=td-topright >&nbsp;</TD>
						   	<TD class=td-topright >&nbsp;</TD>
							<TD class=td-topright >&nbsp;</TD>
							<TD class=td-topright >&nbsp;</TD>
							<TD class=td-topright >&nbsp;</TD>
							<TD class=td-topright  align="right"><b>
							<%
										startBalance = beginBalance;
										beginBalancePerDay = beginBalance;										
										%>
										<b>
										<%
										out.println( DataFormat.formatNumber(beginBalance,2) );
										%>
										</b>
&nbsp;</TD>
						</TR>
						
						<%
						if( queryResults != null && queryResults.length >0 )
						{
							
							//临时日期
							statDate = ( (AcctTransInfo)queryResults[0]).getTransactionTime();
							
							for(int i = 0;i<queryResults.length;i++)
							{
								AcctTransInfo info = (AcctTransInfo)queryResults[i];
								
								//借方
								if( info.getDirection() == AccountInfo.DEBIT )
								{
									sumDebitAmount   += info.getDebitAmount();
									strDebitAmount = DataFormat.formatNumber(info.getDebitAmount(),2);
									strCreditAmount = "";
								}
								else if( info.getDirection() == AccountInfo.CREDIT )
								{
									sumCreditAmount  += info.getCreditAmount();
									strCreditAmount = DataFormat.formatNumber(info.getCreditAmount(),2);
									strDebitAmount = "";								
								}
								
								if( queryResults.length == 1 || info.getTransactionTime().equals(statDate) )
								{
									//汇总账户同一天的借贷方金额
									sumDebitAmountPerDay   += info.getDebitAmount();
									sumCreditAmountPerDay  += info.getCreditAmount();
								}
								
								if(  !info.getTransactionTime().equals(statDate) )
								{
									beginBalancePerDay = Arithmetic.add( beginBalancePerDay,Arithmetic.sub(sumCreditAmountPerDay,sumDebitAmountPerDay) );								
									%>
									<TR >
							            <TD class=td-topright >&nbsp;<%=DataFormat.formatDate(statDate,1)%></TD>
							            <TD class=td-topright align="center"><b>&nbsp;本日合计</b></TD>
							            <TD class=td-topright >&nbsp;</TD>
									   	<TD class=td-topright >&nbsp;</TD>
										<TD class=td-topright >&nbsp;</TD>
										<TD class=td-topright  align="right" nowrap><b><%=DataFormat.formatNumber(sumDebitAmountPerDay,2)%></b>&nbsp;</TD>
										<TD class=td-topright  align="right" nowrap><b><%=DataFormat.formatNumber(sumCreditAmountPerDay,2)%></b>&nbsp;</TD>
										<TD class=td-topright  align="right" nowrap><b><%=DataFormat.formatNumber(beginBalancePerDay ,2  )%></b></TD>
									</TR>
									<%
									//如果不是同一天的数据,则再重新初始化统计数据
									statDate = info.getTransactionTime();
									sumDebitAmountPerDay   = info.getDebitAmount();
									sumCreditAmountPerDay  = info.getCreditAmount();
								}	
								String strBalance ="";
								double balance = beginBalance+info.getCreditAmount()-info.getDebitAmount();
								beginBalance = balance;
								strBalance = DataFormat.formatNumber(balance , 2);						
						%>
						<TR >
				            <TD class=td-topright  style="word-break :break-all;">&nbsp;<%=DataFormat.formatDate(info.getTransactionTime(),1)%></TD>
				            <TD class=td-topright  style="word-break :break-all;">&nbsp;<%=DataFormat.formatString(info.getAbstractInfo())%></TD>
				            <TD class=td-topright  style="word-break :break-all;">&nbsp;<%=DataFormat.formatString(info.getCheckNo())%></TD>
						   	<TD class=td-topright  style="word-break :break-all;">&nbsp;<%=DataFormat.formatString(info.getOppAccountNo())%></TD>
							<TD class=td-topright  style="word-break :break-all;">&nbsp;<%=DataFormat.formatString(info.getOppAccountName())%></TD>
							<TD class=td-topright  style="word-break :break-all;" align="right" nowrap><%=strDebitAmount%></TD>
							<TD class=td-topright  style="word-break :break-all;" align="right" nowrap><%=strCreditAmount%></TD>
							<TD class=td-topright  style="word-break :break-all;" align="right" nowrap><%=strBalance%></TD>
						</TR>
						<%
								/** 如果只有一条记录，则在这条记录后面输出一行本日合计
								 * &如果已经是最后一条记录了，则在这条记录后面输出一行本日合计
								 **/
								if( queryResults.length == 1 || i == queryResults.length -1 )
								{
									beginBalancePerDay = Arithmetic.add( beginBalancePerDay,Arithmetic.sub(sumCreditAmountPerDay,sumDebitAmountPerDay) );																
									%>
									<TR >
							            <TD class=td-topright >&nbsp;<%=DataFormat.formatDate(statDate,1)%></TD>
							            <TD class=td-topright align="center"> &nbsp;<b>本日合计</b></TD>
							            <TD class=td-topright >&nbsp;</TD>
									   	<TD class=td-topright >&nbsp;</TD>
										<TD class=td-topright >&nbsp;</TD>
										<TD class=td-topright  align="right" nowrap><b><%=DataFormat.formatNumber(sumDebitAmountPerDay,2)%></b></TD>
										<TD class=td-topright  align="right" nowrap><b><%=DataFormat.formatNumber(sumCreditAmountPerDay,2)%></b></TD>
										<TD class=td-topright  align="right" nowrap><b><%=DataFormat.formatNumber(	beginBalancePerDay ,2  )%></b></TD>
									</TR>
									<%
									//初始化统计数据清零
									statDate = info.getTransactionTime();
									sumDebitAmountPerDay  = 0.00;
									sumCreditAmountPerDay = 0.00;
								}								
							}
						}
						else
						{
						%>
						<TR >
				            <TD class=td-topright >&nbsp;</TD>
				            <TD class=td-topright >&nbsp;</TD>
				            <TD class=td-topright >&nbsp;</TD>
				            <TD class=td-topright >&nbsp;</TD>
							<TD class=td-topright >&nbsp;</TD>
							<TD class=td-topright >&nbsp;</TD>
							<TD class=td-topright >&nbsp;</TD>
							<TD class=td-topright >&nbsp;</TD>
						</TR>
						<%
						}
						%>
						
						<TR >
						    <TD class=td-topright >&nbsp;</TD>
				            <TD class=td-topright align="center">&nbsp;<b>期末余额</b></TD>
				            <TD class=td-topright >&nbsp;</TD>
						   	<TD class=td-topright >&nbsp;</TD>
							<TD class=td-topright >&nbsp;</TD>
							<TD class=td-topright  align="right" nowrap><b><%=DataFormat.formatNumber(sumDebitAmount,2)%></b></TD>
							<TD class=td-topright  align="right" nowrap><b><%=DataFormat.formatNumber(sumCreditAmount,2)%></b></TD>
							<TD class=td-topright  align="right" nowrap><b><%=DataFormat.formatNumber(Arithmetic.add( startBalance,Arithmetic.sub(sumCreditAmount,sumDebitAmount) ),2)%></b></TD>
						</TR>						
						</TBODY>
				</TABLE>
			</TD>
		</TR>		
</TBODY>
</TABLE>
</TD>
</TABLE>
<BR>
<table  width="100%" border="0" align="center">
	<tr>				
        <td width="70%" >&nbsp;</td>
		<td width="30%" >打印时间：<%=DataFormat.formatDate(Env.getSystemDateTime(),DataFormat.FMT_DATE_YYYYMMDD)%></td>
	</tr>		
</table>
<!--页面表单结束-->
<!--页面脚本开始-->
<!--页面脚本元素结束-->
<%
	}
	catch( Exception exp )
	{
		exp.printStackTrace();
		JSPLogger.error(exp.getMessage());
	}
	//显示页面尾	
	/**页面显示结束*/
%>