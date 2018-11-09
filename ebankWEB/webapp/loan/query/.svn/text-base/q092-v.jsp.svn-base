<%
/**
 * 页面名称 ：q092-v.jsp
 * 页面功能 : 查询——合同计划查询——结果显示的页面
 * 作    者 ：ninghao
 * 日    期 ：2003-11-18
 * 特殊说明 ：
 *			
 * 修改历史 ：
 */
%>
<%@page contentType="text/html;charset=gbk"%>
<%@page	import="java.util.*,
				java.sql.*,
				java.net.URLEncoder,
				java.rmi.RemoteException,
				com.iss.itreasury.util.*,
				com.iss.itreasury.loan.util.*,
				com.iss.itreasury.loan.query.dataentity.*,
				com.iss.itreasury.ebank.util.*,			
				com.iss.itreasury.ebank.obdataentity.*,	
				com.iss.itreasury.ebank.obquery.bizlogic.*,
				com.iss.itreasury.ebank.obquery.dataentity.*,
				com.iss.itreasury.loan.query.bizlogic.*"
%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"/>
<%@ include file="/common/common.jsp" %>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<script language="JavaScript" src="/webob/js/Control.js"></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>
<safety:resources />
<%
	String strTableTitle = "贷款合同执行计划修改记录查询";
	try
	{
 		//判断是否登录
		if( !sessionMng.isLogin() )
		{	
			OBHtml.showMessage(out,sessionMng,null,"登录",null,Constant.RecordStatus.INVALID,"Gen_E002");
			OBHtml.showOBHomeEnd(out);
			out.flush();
			return;
		}
		//判断是否有权限
		if (sessionMng.hasRight(request) == false)
		{
			OBHtml.showMessage(out,sessionMng,null,"登录",null,Constant.RecordStatus.INVALID,"Gen_E003");
			OBHtml.showOBHomeEnd(out);
			out.flush();
			return;
		}
		
		String strContractCode="";
		String tmp=(String)request.getAttribute("ContractCode");
		if ( tmp!=null)
			strContractCode=tmp;
		
		long lContractID=GetNumParam(request,"lContractID",-1);
		String actType=GetParam(request,"actType","");
		
		Vector queryResults=null;
		queryResults = (Vector)request.getAttribute("queryResult");
					
		OBHtml.showOBHomeHead(out,sessionMng,strTableTitle,Constant.YesOrNo.YES);
%>

<form name="frmV001">
<input type='hidden' name="actType" value="1">
<table width="99%" border="0" class="top" height="100">

	<TR class="tableHeader">
		<TD class=FormTitle height=20><B><%=strTableTitle%></B></TD>
	</TR>
	<TR>
		<TD height=20>  合同号：<%=strContractCode%></TD>
	</TR>
	<tr>
		<td height="50" valign="top" width="100%">
		<table width="99%" height="50" border="0" align="center" class="ItemList">
			<tr class="tableHeader">
				<td width="25%" align="center" class="ItemTitle">版本号</td>
				<td width="25%" align="center" class="ItemTitle">版本日期及时间</td>
				<td width="25%" align="center" class="ItemTitle">修改人</td>
				<td width="25%" align="center" class="ItemTitle">下一级审核人</td>
			</tr>
<%
if( (queryResults != null)&&(queryResults.size()>0))
{
	for( int i=0; i<queryResults.size(); i++ )
	{ 
		QuestContractPlanInfo info=new QuestContractPlanInfo();
		info = (QuestContractPlanInfo )queryResults.get(i);
	%>
			<tr>
				<td  align="center" class="ItemBody">
					<a href="javascript:frmGoto(<%=info.getPlanID()%>)">
					<%=info.getPlanVersion()%>
					</a>
				</td>
				<td  align="center" class="ItemBody"><%=DataFormat.getDateString(info.getInputDate())%></td>
				<td  align="center" class="ItemBody"><%=DataFormat.formatString(info.getModifier())%></td>
				<td  align="center" class="ItemBody"><%=DataFormat.formatString(info.getNextCheckUserName())%></td>
			</tr>
<%	}
}
else
{
%>
			<tr>
				<td  align="center" class="ItemBody">&nbsp;</td>
				<td  align="center" class="ItemBody">&nbsp;</td>
				<td  align="center" class="ItemBody">&nbsp;</td>
				<td  align="center" class="ItemBody">&nbsp;</td>
			</tr>
<%
}
%>
	</table></td></tr></table></form>
<form name=frm>
<table width="100%" border="0" cellpadding="5">
			<tr>
				<td align="right">
					<input type="button" name="print" onclick="javasctript:window.print();" value=" 打印 " class="button" onClick="dailyReport()">
<% if (actType.equals("query")){ %>
					<input type="button" name="butReturn" value=" 返回 " class="button" onClick="returnPage()">
<% }else{ %>
					<input type="button" name="butReturn" value=" 关闭 " class="button" onClick="closePage()">
<% } %>					
				</td>
			</tr>
		</table>
	<input type=hidden name="control" value="view">
	<input type=hidden name="actType" value="<%=actType%>">
</form>
<br>

<script language='javascript'>
function frmGoto(planID)
{
	window.open('q094-v.jsp?control=view&isSM=<%=Constant.YesOrNo.NO%>&isYU=<%=Constant.YesOrNo.NO%>&nTmpID='+planID+'&nContractID=<%=lContractID%>','','width=800,height=600,status=yes,toolbar=no,menubar=no,location=no,resizable=yes,scrollbars =yes');
}
function returnPage()
{
	frm.action="../query/q090-v.jsp";
	showSending();			//正在执行
	frm.submit();
}
function closePage()
{
	window.close();
}

</script>
<%
		OBHtml.showOBHomeEnd(out);
	}
	catch(IException ie)
	{
		OBHtml.showExceptionMessage(out,sessionMng,ie,strTableTitle,"", Constant.RecordStatus.VALID); 
		out.flush();
		return;
	}
%>
<%@ include file="/common/SignValidate.inc" %>