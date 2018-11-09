<%--
/**页面功能说明
 * 页面名称 ：v091.jsp
 * 页面功能 : 对账单打印
 * 作    者 ：gqfang
 * 日    期 ：2005-06-05
 * 简单实现说明：
 *				1、账户信息列表
 * 特殊说明 ：
 * 修改历史 ：
 */
--%>

<%@ page contentType = "text/html;charset=GBK" %>
<jsp:directive.page import="com.iss.itreasury.util.Constant.PageControl"/>
<jsp:directive.page import="com.iss.itreasury.util.Env"/>
<%@ page import="com.iss.itreasury.ebank.util.*"%>
<%@ page import="com.iss.itreasury.ebank.system.util.*"%>
<%@ page import="com.iss.itreasury.ebank.util.NameRef"%>
<%@ page import="com.iss.sysframe.pager.dataentity.FlexiGridInfo"%>
<jsp:directive.page import="com.iss.itreasury.ebank.system.dataentity.QueryBillPrintInfo"/>
<jsp:directive.page import="com.iss.itreasury.ebank.system.dataentity.QueryBillPrintParam"/>
<jsp:directive.page import="com.iss.itreasury.util.DataFormat"/>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

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
		JSPLogger.info("*******进入页面--bankportal\\v091-account_ebank.jsp*******");
		/**页面校验结束*/
		
		/**业务逻辑开始*/
		
		String strContext = request.getContextPath();
		//分页控制参数
		String strTemp            = "";
		long   orderField         = -1;
		long   desc               = PageControl.CODE_ASCORDESC_ASC;
		
		//获得PageLoader
		String strPageLoaderKey = (String)request.getAttribute("_pageLoaderKey");
		JSPLogger.info("Result Page ::　strPageLoaderKey : " + strPageLoaderKey);
		
		
		//强制转换
		Object[] queryResults = null;
		queryResults = (QueryBillPrintInfo[])request.getAttribute(PageControl.SearchResults);
		JSPLogger.info("账户交易明细    queryResults :  " + queryResults + " length:" + ((queryResults != null)?queryResults.length:-1));
		
		long clientId = -1;
		clientId = sessionMng.m_lClientID;
		/**业务逻辑结束*/
		
		//判断正序和反序
		strTemp = (String)request.getParameter("desc");
		if( strTemp != null && strTemp.length() > 0 )
		{
		     desc = Long.parseLong(strTemp.trim());
		}
		
		//得到查询区间
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
			
		//生成paramInfo变量
		QueryBillPrintParam paramInfo  =(QueryBillPrintParam)request.getAttribute("queryInfo");
		request.setAttribute("queryInfo", paramInfo);

		/**页面显示开始*/
       // HTMLHelper.showHomeHead(out,sessionMng,"对账单打印",BooleanValue.TRUE);//显示页面头
        OBHtml.showOBHomeHead(out, sessionMng, strTitle, OBConstant.ShowMenu.YES);
%>


<!--查询条件表单开始-->
<!--页面表单开始-->
<form name="frmV091" method="post" action="">
<!--页面控制元素开始-->
	<input name="p_Action" type="hidden" value="find">
	<input name="strAction" type="hidden" value="">
	<input type="hidden" name="pageLoaderKey" value="<%=strPageLoaderKey%>">
	<input type="hidden" name="orderField" value="<%=orderField%>">
	<input type="hidden" name="pageLoaderInfo_rowPerPage" value="">	
	<input type="hidden" name="pageLoaderInfo_pageNo" value="">
	<input type="hidden" name="strOrderBy" value="">
	<input type="hidden" name="transactionStartDateString" value="<%=queryStartDate%>">
	<input type="hidden" name="transactionEndDateString"  value="<%=queryEndDate%>">	
	<input type="hidden" name="desc" value="<%=desc%>">
	<input name="systemDate" type="hidden" value="<%=Env.getSystemDateString()%>">
	<input name="isCheck" type="hidden" value="1"/>
	<input name="isDirectLink" type="hidden" value="1"/>		
	<input name="accountStatus" type="hidden" value="1"/>	
	<input name="clientId" type="hidden" value="<%=clientId%>"/>	
	<input name="lUserID" type="hidden" value="<%=sessionMng.m_lUserID %>"/>
	<input name="lCurrencyID" type="hidden" value="<%=sessionMng.m_lCurrencyID %>"/>
	<input name="lOfficeID" type="hidden" value="<%=sessionMng.m_lOfficeID %>"/>
	<input name="bankType" type="hidden" value="<%=paramInfo.getBankType() %>"/>
	<input name="accountId" type="hidden" value="<%=paramInfo.getAccountId() %>"/>
	
	<!--页面控制元素结束-->
	<table width="98%" cellpadding="0" cellspacing="0" class="title_top">
		<tr>
			<td height="24">
				<table cellspacing="0" cellpadding="0" >
					<TR>
				       <td class=title><span class="txt_til2">账户信息</span></td>
				       <td class=title_right><a class=img_title></td>
					</TR>
				</TABLE>
				<br/>
				<table width=100% border="0"  cellpadding="0" cellspacing="0" class="top">
					<tbody>
					<TR>
					    <td width="2%">&nbsp;</td>
						<TD>
							<br><TABLE width="100%" id="flexlist"></TABLE><br>
						</TD>
					    <td width="2%">&nbsp;</td>
					</TR>
					</tbody>
					</table>
					<TR>
						<TD>
							<TABLE width="97%" align="center">
								<TBODY>
							        <TR borderColor=#ffffff>
							            <TD colspan="9" align=right> 
										  <input class=button1 type=button name="butBack" value=" 返 回 " onClick="doBack()" >
							            </TD>
							        </TR>
								</TBODY>
					        </TABLE>
						</TD>
					</TR>
				</TABLE>
			</td>
		</tr>
	</table>
</form>

<script language="JavaScript">
	setFormName("frmV091");
</script>

<script language="javascript">
$(document).ready(function() {
	$("#flexlist").flexigridenc({
		colModel : [
			{display: '客户编号',  name : 'clientId', width : 120, sortable : true, align: 'center'},
			{display: '客户名称',  name : 'clientId', width : 120, sortable : true, align: 'center'},
			{display: '银行',  name : 'bankId', width : 100, sortable : true, align: 'center'},
			{display: '国家',  name : 'countryId', width : 100, sortable : true, align: 'center'},
			{display: '币种',  name : 'currencyType', width : 100, sortable : true, align: 'center'},
			{display: '账号', name: 'accountNo', elType : 'link', elName : 'sTransNoLink', methodName : 'toDetail("?","?","?","?")', width: 135, sortable: true, align: 'center'},
			{display: '账户名称',  name : 'accountName', width : 100, sortable : true, align: 'center'},
			{display: '日期范围',  name : 'balanceStartDate', width : 120, sortable : true, align: 'center'},
			{display: '余额',  name : 'balance', width : 120, sortable : true, align: 'center'}
		],//列参数
		title:'账户对账单',
		classMethod : 'com.iss.itreasury.ebank.system.action.BillPrintAction.queryBillPrintInfo',//要调用的方法
		page : <%=flexiGridInfo.getFlexigrid_page()%>,
		rp : <%=flexiGridInfo.getFlexigrid_rp()%>,
		//sortname : '<%=flexiGridInfo.getFlexigrid_sortname().equals("") == true ? "sname" : flexiGridInfo.getFlexigrid_sortname()%>',// 预设指定哪列进行排序
		//sortorder : '<%=flexiGridInfo.getFlexigrid_sortorder().equals("") == true ? "asc" : flexiGridInfo.getFlexigrid_sortorder()%>',// 预设排序的方式
		userFunc : getFormData
	});
});
function getFormData() 
{
	return $.addFormData("frmV091","flexlist");
}
function toDetail(acctId,queryStartDate,queryEndDate,bankType)
{	
	window.open('<%=strContext%>/bankportal/c091-account_ebank.jsp?p_SuccessPageURL=/bankportal/v092-account_ebank.jsp&p_FailPageURL=/bankportal/v091-account_ebank.jsp&p_Action=findById&accountId='+acctId+'&transactionStartDateString='+queryStartDate+'&transactionEndDateString='+queryEndDate+'&bankType='+bankType);
}

function doBack()
{
	history.back();
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