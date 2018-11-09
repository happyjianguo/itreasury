<%--
/**页面功能说明
 * 页面名称 ：v092.jsp
 * 页面功能 : 对账单打印-网银使用
 * 作    者 ：xintan
 * 日    期 ：2005-06-05
 * 简单实现说明：
 *				1、对账单信息
 * 特殊说明 ：
 * 修改历史 ：
 */
--%>
<%@ page contentType = "text/html;charset=GBK" %>

<%@ page import="java.util.*"%>
<%@ page import="com.iss.itreasury.ebank.util.*"%>
<%@ page import="com.iss.itreasury.ebank.system.util.*"%>
<%@ page import="com.iss.itreasury.ebank.util.NameRef"%>
<%@ page import="com.iss.sysframe.pager.dataentity.FlexiGridInfo"%>
<jsp:directive.page import="com.iss.itreasury.ebank.system.dataentity.QueryBillPrintParam"/>
<jsp:directive.page import="com.iss.itreasury.util.DataFormat"/>
<jsp:directive.page import="com.iss.itreasury.ebank.system.dataentity.AcctTransInfo"/>
<jsp:directive.page import="com.iss.itreasury.ebank.system.dataentity.AccountInfo"/>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<%
    try
	{
		//emoduleid等于6代表网银模块
		//String emoduleid = (String)session.getAttribute("emoduleid");
		//if ( session.getAttribute("eofficeID")==null || !emoduleid.equals("6") || session.getAttribute("eclientid")==null) {
		//		OBHtml.showCommonMessage(out, sessionMng, "", "",1, "Gen_E002");
        //		out.flush();
		//		return;
		//}
		//分页信息
		FlexiGridInfo flexiGridInfo = new FlexiGridInfo();
		
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
		JSPLogger.info("*******进入页面--bankportal\\v092-account_ebank.jsp*******");
		/**页面校验结束*/
		//获得PageLoaderKey
		//String strPageLoaderKey = (String)request.getAttribute("_pageLoaderKey");	
		
		//得到查询区间
		String strTemp  = "";
		String bankType = "";
		String queryStartDate = "";
		String queryEndDate   = "";
		strTemp = (String)request.getParameter("transactionStartDateString");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			queryStartDate = strTemp;
		}
		strTemp = (String)request.getParameter("transactionEndDateString");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			queryEndDate = strTemp;
		}
		strTemp = (String)request.getAttribute("bankType");
		if( strTemp != null && strTemp.length() > 0 )
		{
		     bankType = strTemp;
		}	
		/**业务逻辑开始*/		
		String strContext = request.getContextPath();		
		/**返回结果对象之一：账户期初余额**/	
		double beginBalance  = 0.00;
		beginBalance = ((Double)request.getAttribute("balanceCol")).doubleValue();		
		String p_action=(String)request.getAttribute("p_action");	
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
		
		//查询条件，用于打印
		long acctId = -1;
		String strTransStartDate = "";
		String strTransEndDate = "";
		if(param!=null)
		{
			acctId = param.getAccountId();
			String strAccountNo = NameRef.getAccountNOByAccountID(acctId);
			acctInfo.setAccountNo(strAccountNo);
			String strAccountName = NameRef.getAccountNameByAccountID(acctId);
			acctInfo.setAccountName(strAccountName);
			strTransStartDate = DataFormat.formatDate(param.getTransactionStartDate(),1);
			strTransEndDate = DataFormat.formatDate(param.getTransactionEndDate(),1);
		}
		/**页面显示开始*/
      //  HTMLHelper.showHomeHead(out,sessionMng,"对账单信息",BooleanValue.TRUE);//显示页面头
       OBHtml.showOBHomeHead(out, sessionMng, strTitle, OBConstant.ShowMenu.YES);
%>

<!--页面表单开始-->
<form name="frmV092" method="post" action="">
<!--页面控制元素开始-->
	<input name="p_Action" type="hidden" value="find">
	<input type="hidden" name="bankType" value="<%=param.getBankType() %>">
	<input type="hidden" name="transactionStartDateString" value="<%=strTransStartDate%>">
	<input type="hidden" name="transactionEndDateString"  value="<%=strTransEndDate%>">	
	<input type="hidden" name="accountid"  value="<%=param.getAccountId()%>">
	<input type="hidden" name="lOfficeID"  value="<%=param.getOfficeID()%>">
	<input type="hidden" name="lCurrencyID"  value="<%=param.getCurrencyType()%>">
	<input type="hidden" name="lClientID"  value="<%=param.getClientId()%>">

	<!--页面控制元素结束-->
	<table cellpadding="0" cellspacing="0" class="title_top" width="98%">
		  <tr>
		    <td height="24">
			    <table cellspacing="0" cellpadding="0" class=title_Top1>
					<TR>
				       <td class=title><span class="txt_til2">账户交易明细</span></td>
				       <td class=title_right><a class=img_title></td>
					</TR>
				</TABLE>
				<br/>
				<TABLE width="100%" align="left" border="0">
						<TR>
							<TD width="10%">&nbsp;&nbsp;客户编号：</TD>
							<TD width="30%"><input name="clientNo" class="box" value="<%=NameRef.getClientCodeByID( param.getClientIdFrom() )%>" disabled></TD>
							<TD width="10%">客户名称：</TD>
							<TD colspan="2" width="40%"><input name="clientName" class="box" value="<%=NameRef.getClientNameByID( param.getClientIdFrom() )%>" size="40"  disabled></TD>
						</TR>						
						<TR>
							<TD width="10%">&nbsp;&nbsp;账号：</TD>
							<TD width="30%"><input name="accountNo" class="box" value="<%=acctInfo.getAccountNo()%>" disabled></TD>
							<TD width="10%">账户名称：</TD>
							<TD colspan="2" width="40%"><input name="accountName" class="box" value="<%=acctInfo.getAccountName()%>" size="40"  disabled></TD>
						</TR>						
						<TR>
							<TD width="10%">&nbsp;&nbsp;币种：</TD>
							<TD width="30%"><input name="currencyType" class="box" value="<%=NameRef.getCurrencyNameByID( param.getCurrencyType() )%>" disabled></TD>
							<TD colspan="3" width="50%">&nbsp;</TD>
						</TR>
						<TR>
							<TD width="10%">&nbsp;&nbsp;日期 从：</TD>
							<TD width="30%"><input name="startDate" class="box" value="<%=strTransStartDate%>" disabled></TD>
							<TD width="10%">到： </TD>
							<TD colspan="2" width="40%"> <input name="endDate" class="box" value="<%=strTransEndDate%>" disabled></TD>
						</TR>
				</TABLE>
				<br>
				<TABLE border="0" width="100%" class="top">
			       	<tr>
			       	    <td width="1%">&nbsp;</td>
						<TD width="*%">
							<br><TABLE width="100%" id="flexlist"></TABLE><br>
						</TD>
			       	    <td width="1%">&nbsp;</td>
					</tr>
				</TABLE>
				<BR>
				<TABLE width="100%" align="center">
				        <TR borderColor=#ffffff >
				            <TD colspan="8" align=right> 
				            <%	if(param==null)
				            	{ %>
				            	<input class=button1 type=button name="butPrint" value=" 打 印 "  disabled>
				            	<input class=button1 type=button name="butExport" value=" 导 出 "  disabled>				            					            	
				            <%	}else{ %>
				            	<input class=button1 type=button name="butPrint" value=" 打 印 " onClick="doPrint()" >
				            	<input class=button1 type=button name="butExport" value=" 导 出 " onClick="doExport()" >						            	
				            	<%}%>				              
							  <input class=button1 type=button name="butPrint" value=" 关 闭 " onClick="doClose()" >
				            </TD>
				        </TR>
		        </TABLE>
			</td>
		</tr>
	</table>
</form>
<!--页面表单结束-->
<!--页面脚本开始-->
<script language="JavaScript">
	
	<% if(param==null)
	{  %>
		//setSubmitFunction("doClose()");
	<%}else{ %>
		//setSubmitFunction("doPrint()");
		<%}%>
	setFormName("frmV092");
</script>
<script language="javascript"> 
$(document).ready(function() {

    $(".FormTitle").click(function(){
      	$("#iTable").toggle();
    });

	$("#flexlist").flexigridenc({
		colModel : [
			{display: '日期',  name : 'transactionTime', width : 150, sortable : true, align: 'center'},
			{display: '摘要',  name : 'abstractInfo', width : 150, sortable : true, align: 'center'},
			{display: '单据号',  name : 'checkNo', width : 150, sortable : true, align: 'center'},
			{display: '对方账号',  name : 'oppAccountNo', width : 150, sortable : true, align: 'center'},
			{display: '对方账户名称',  name : 'oppAccountName', width : 150, sortable : true, align: 'center'},
			{display: '借方金额',  name : 'debitAmount', width : 150, sortable : true, align: 'center'},
			{display: '贷方金额',  name : 'creditAmount', width : 150, sortable : true, align: 'center'},
			{display: '余额',  name : 'dtinput', width : 150, sortable : false, align: 'center'}
		],//列参数
		title:'账户交易明细',
		classMethod : 'com.iss.itreasury.ebank.system.action.BillPrintAction.queryBillPrintDetailInfo',//要调用的方法
		page : <%=flexiGridInfo.getFlexigrid_page()%>,
		rp : <%=flexiGridInfo.getFlexigrid_rp()%>,
		//sortname : '<%=flexiGridInfo.getFlexigrid_sortname().equals("") == true ? "sname" : flexiGridInfo.getFlexigrid_sortname()%>',// 预设指定哪列进行排序
		//sortorder : '<%=flexiGridInfo.getFlexigrid_sortorder().equals("") == true ? "asc" : flexiGridInfo.getFlexigrid_sortorder()%>',// 预设排序的方式
		userFunc : getFormData,
		usepager : false,
		printbutton : false,
		exportbutton : false
	});
	
});

function getFormData() 
{
	return $.addFormData("frmV092","flexlist");
}
function doPrint()
{
   if (confirm("确定打印?"))
	{
	  window.open('<%=strContext%>/bankportal/c091-account_ebank.jsp?p_SuccessPageURL=/bankportal/v093-account_ebank.jsp&p_FailPageURL=/bankportal/v093-account_ebank.jsp&p_action=findById&accountId=<%=acctId%>&transactionStartDateString=<%=strTransStartDate%>&transactionEndDateString=<%=strTransEndDate%>');
    }			
}

function doExport()
{
   if(confirm("确定导出?"))
	{
		var strUrl = "<%=strContext%>/bankportal/c091-account_ebank.jsp";
		strUrl += "?p_SuccessPageURL=/bankportal/v094-account_ebank.jsp";
		strUrl += "&p_FailPageURL=/bankportal/v094-account_ebank.jsp";
		strUrl += "&p_action=findById&accountId=<%=acctId%>";
		strUrl += "&transactionStartDateString=<%=strTransStartDate%>&transactionEndDateString=<%=strTransEndDate%>";
		window.open(strUrl);
    }			
}

function doClose()
{
 window.close();
}
</script>
<!--页面脚本元素结束-->
<%
	}
	catch( Exception exp )
	{
		exp.printStackTrace();
		JSPLogger.error(exp.getMessage());
	}
	//显示页面尾
	OBHtml.showOBHomeEnd(out);
	/**页面显示结束*/
%>
<jsp:include page="/ShowMessage.jsp"/>