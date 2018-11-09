<%
/**页面功能说明
 * 页面名称 ：v012.jsp
 * 页面功能 : 账户历史余额查询
 * 作    者 ：barneyliu
 * 日    期 ：2005-10-30
 * 简单实现说明：
 *				1、查询结果显示列表
 * 特殊说明 ：
 * 修改历史 ：
 */
%>

<%@ page contentType = "text/html;charset=gbk" %>

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
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<!--类导入部分结束-->

<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%
	int i = 0;
	String strRootPath = request.getContextPath();//http://xxxx/../cpob
    
    try
	{
         
        //登录检测
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
		 
 
		//分页控制参数
		String strTemp            = "";
		long   orderField         = -1;
		long   desc               = PageControl.CODE_ASCORDESC_ASC;
		
		//获得PageLoader
		String strPageLoaderKey = (String)request.getAttribute("_pageLoaderKey");
		Log.print("Result Page ::　strPageLoaderKey : " + strPageLoaderKey);
		
		//强制转换
		Object[] queryResults = null;
		queryResults = (OBHisBalanceResultInfo[])request.getAttribute(PageControl.SearchResults);
		
	 
		/**业务逻辑结束*/
		
		//判断正序和反序
		strTemp = (String)request.getParameter("desc");
		if( strTemp != null && strTemp.length() > 0 )
		{
		     desc = Long.parseLong(strTemp.trim());
		}
		
		/**页面显示开始*/
        OBHtml.showOBHomeHead(out, sessionMng, "汇总账户历史余额", 1);

%>

<!--引入信息处理页面,此页面会以弹出窗口的形式弹出已经捕捉到的异常-->
<jsp:include page="/ShowMessage.jsp"/>
<!--引入js文件-->
<script language="JavaScript" src="/webob/js/Control.js" ></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>
<script language="JavaScript" src="/webob/js/MagnifierSQL.js"></script>
<script language="JavaScript" src="/webscript/taglib.js"></script>
<safety:resources />
<!--引入js文件-->

<!--页面表单开始-->
<form name="frmV012" method="post" action="">
<!--页面控制元素开始-->
	<input name="p_Action" type="hidden" value="find">
	<input name="strAction" type="hidden" value="find">
	<input name="strSuccessPageURL" type="hidden" value="">
	<input name="strFailPageURL" type="hidden" value="">
	<input type="hidden" name="pageLoaderKey" value="<%=strPageLoaderKey%>">
	<input type="hidden" name="orderField" value="<%=orderField%>">
	
	<input type="hidden" name="pageLoaderInfo_rowPerPage" value="">	
	<input type="hidden" name="pageLoaderInfo_pageNo" value="">
	<input type="hidden" name="strOrderBy" value="">
	
	<input type="hidden" name="desc" value="<%=desc%>">
 
<!--页面控制元素结束-->
	
	<table   align=center border="0" cellpadding="2" cellspacing="0" width=98% class="tableform">
		 <TR>
			<TD>
				<TABLE border=0 borderColor=#999999 class=ItemList cellspacing="1" width="100%">
					<TBODY>
						
						<TR align=center bgColor=#cccccc borderColor=#999999>
				            <TD class=ItemTitle width="8%">国家</TD>
				            <TD class=ItemTitle width="8%">区域中心</TD>
				            <TD class=ItemTitle width="12%">银行</TD>
							 <TD class=ItemTitle width="15%">账户类型</TD>
							<TD class=ItemTitle width="15%">账号</TD>
							<TD class=ItemTitle width="10%">账户名称</TD>
							<TD class=ItemTitle width="8%">币种</TD>
							<TD class=ItemTitle width="12%">日期</TD>
							<TD class=ItemTitle width="15%">余额</TD>
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
						<TR align=center bgColor=#cccccc borderColor=#999999>
				            <TD class=ItemBody ><%=info.getCountryName()%>&nbsp;</TD>
				            <TD class=ItemBody ><%=info.getAreaName()%>&nbsp;</TD>
							<TD class=ItemBody ><%=info.getBankName()%>&nbsp;</TD>
							<TD class=ItemBody ><%=AccounttypeName%>&nbsp;</TD>
							<TD class=ItemBody ><%=info.getAccountNo()%>&nbsp;</TD>
							<TD class=ItemBody ><%=info.getAccountname()%>&nbsp;</TD>
				            <TD class=ItemBody ><%=strCurrencyName%>&nbsp;</TD>
							<TD class=ItemBody ><%=info.getExecuteDate().toString().substring(0,10)%>&nbsp;</TD>
							<TD class=ItemBody  align="right"><%= (DataFormat.formatDisabledAmount(info.getHisBalance(),2) == null)? "" : DataFormat.formatDisabledAmount(info.getHisBalance(), 2) %>&nbsp;</TD>
							 
							
							
						</TR>
						<%
							}
						}
						else
						{
						%>
						<TR align=center bgColor=#cccccc borderColor=#999999>
				            <TD class=ItemBody>&nbsp;</TD>
				            <TD class=ItemBody>&nbsp;</TD>
				            <TD class=ItemBody>&nbsp;</TD>
				            <TD class=ItemBody>&nbsp;</TD>
				            <TD class=ItemBody>&nbsp;</TD>
							<TD class=ItemBody>&nbsp;</TD>
							<TD class=ItemBody>&nbsp;</TD>
							<TD class=ItemBody>&nbsp;</TD>
							<TD class=ItemBody>&nbsp;</TD>
						</TR>
						<%
						}
						%>
						</TBODY>
				</TABLE>
			</TD>
		</TR>
		
				 
	<!-- 分页控件 -->
	    <TR> 
	  		<TD>
	        	<TABLE border="0" cellPadding=1 height=20 width="100%" class="PageItemList">
	         		<TBODY>
	             		<TR>
			                <TD class="PageItemTitle" colspan="10" align="right">
			                    <DIV style="overflow:hidden;height:24;"> 
			                       <jsp:include page="/pagenavigator.jsp" />  
			                    </DIV>
							</TD>
						</TR>
			    	</TBODY>
				</TABLE>
			</TD>
		</TR> 
		
</table></form>
	
		 

			<table width="98%">
			<tr>
			<td width="450"><div align="left"><span class="graytext">查询日期：<%=DataFormat.getDateString()%></span> 
			</div></td>
			</tr>
			</table>
		<hr>
		  	<table width="98%">
					<TR align="right">
						<TD align="right" colspan=>
							 <input type="button" class="button" name="button1" value="返 回" onclick="javascript:doBack();" onfocus="javascript:nextField='submitfunction'"/>
								&nbsp;	&nbsp;	&nbsp;
					 
							 <input type="button" class="button" name="button2" value="导 出" 	onclick="javascript:doExport();" onfocus="javascript:nextField='submitfunction'"/>
								&nbsp;	&nbsp;	&nbsp;
					 
							 <input type="button" class="button" name="button3"  value="打 印" onclick="javascript:doPrint();" onfocus="javascript:nextField='submitfunction'"/>					
						</TD>
						
					</TR>
				</TABLE>
        


<!--页面表单结束-->
<!--页面脚本开始-->
<script language="JavaScript">
	////setSubmitFunction("doPrint()");
	setFormName("frmV012");
</script>

<script language="javascript">


 
function doSort(frm,orderBy)
{
    frm.action = "accountinfo/a131-c.jsp"
	frm.strSuccessPageURL.value = "accountinfo/a132-v.jsp";
	frm.strFailPageURL.value    = "accountinfo/a132-v.jsp";
	
	frm.pageLoaderInfo_pageNo.value = listForm.elements["pageLoaderInfo.pageNo"].value;
	frm.pageLoaderInfo_rowPerPage.value    = listForm.elements["pageLoaderInfo.rowPerPage"].value;
	
	 
	
	if (frm.desc.value == "<%=PageControl.CODE_ASCORDESC_ASC%>")
	{
		frm.desc.value = "<%=PageControl.CODE_ASCORDESC_DESC%>";
		
		
		frm.strOrderBy.value = " Order by "+orderBy + " DESC " ;
	}
	else
	{
		frm.desc.value = "<%=PageControl.CODE_ASCORDESC_ASC%>"; 
		
		frm.strOrderBy.value = " Order by "+orderBy +" ASC ";
	}
	
	frm.strAction.value="<%= PageControl.FIRSTPAGE %>";
	
	showSending();
	frm.submit();
}

 
	 function doPrint()
{
   if (confirm("是否打印？"))
		{
			window.open('<%=strRootPath%>/pagecontrol.jsp?strAction=<%=PageControl.LISTALL%>&_pageLoaderKey=<%=strPageLoaderKey %>&strSuccessPageURL=accountinfo/a133-p.jsp&strFailPageURL=accountinfo/a133-p.jsp');
         }
			
}
	
 
	
 
function doExport()
{
		if (confirm("是否导出？"))
		{
			window.open('<%=strRootPath%>/pagecontrol.jsp?strAction=<%=PageControl.LISTALL%>&_pageLoaderKey=<%=strPageLoaderKey %>&strSuccessPageURL=accountinfo/a134-e.jsp&strFailPageURL=accountinfo/a134-e.jsp');
         }
			
}



function doBack()
{
	var strHref = "<%=strRootPath%>/accountinfo/a130-v.jsp";
	document.location.href= strHref;
}
</script>
<!--页面脚本元素结束-->


<%	
	/**
	* 显示文件尾
	*/
	OBHtml.showOBHomeEnd(out);
}
//异常处理
catch(Exception exp)
{
	Log.print(exp.getMessage());
	OBHtml.showCommonMessage(out,sessionMng,"","",1,"Gen_E001");
}
%>

<%@ include file="/common/SignValidate.inc" %>