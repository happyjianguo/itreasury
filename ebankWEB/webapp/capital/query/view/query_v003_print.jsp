<%--
 页面名称 ：query_v003_print.jsp
 页面功能 : 网上银行 - 交易总结查询打印页面
 作    者 ：leiyang
 日    期 ：2008-12-01
 特殊说明 ：
 实现操作说明：
 修改历史 ：
--%>
<%@ page language="java"  contentType="text/html;charset=gbk" %>
<%@ page import="com.iss.itreasury.util.Log"%>
<%@ page import="com.iss.itreasury.util.IException"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtml"%>
<%@ page import="com.iss.itreasury.ebank.util.OBConstant"%>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.dataentity.FinanceInfo"%>
<%@ page import="com.iss.itreasury.ebank.util.*"%>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.dataentity.QueryCapForm"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="com.iss.itreasury.settlement.print.IPrintTemplate"%>

<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<!-- 引入此用户当前回话的Session -->
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%String strContext = request.getContextPath();%>
<%
String strTitle = "交易总结查询";

try{
	IPrintTemplate.showPrintHead(out,false,"A4","",1,sessionMng.m_lOfficeID);
	
	//登录检测
   	if (!sessionMng.isLogin()){
       	OBHtml.showCommonMessage(out, sessionMng,  strTitle, "",1, "Gen_E002");
       	OBHtml.showOBHomeEnd(out);
       	out.flush();
       	return;
   	}
   	//检测权限
   	if (!sessionMng.hasRight(request)){
		OBHtml.showCommonMessage(out, sessionMng, strTitle, "", 1, "Gen_E003");
		OBHtml.showOBHomeEnd(out);
       	out.flush();
       	return;
   	}
   	
   	Map map = (Map)request.getAttribute("financeMap");
 	QueryCapForm queryCapForm = (QueryCapForm)request.getAttribute("queryCapForm");
	if(queryCapForm == null){
		queryCapForm = new QueryCapForm();
		queryCapForm.setStartSubmit(DataFormat.getDateString(Env.getSystemDateTime()));
		queryCapForm.setEndSubmit(DataFormat.getDateString(Env.getSystemDateTime()));
	}
	
	long statusCount = 0;
	long statusSave = 0;
	long statusApprovaling = 0;
	long statusApprovaled = 0;
	long statusCheck = 0;
	long statusDeal = 0;
	long statusFinish = 0;
	long statusRefuse = 0;
	double amounts = 0.0;
	
	Iterator iterator = map.keySet().iterator();
	while(iterator.hasNext()) {
		String strKey = iterator.next().toString();
		String strValue = map.get(strKey).toString();
		
		if(strKey.equals(String.valueOf(OBConstant.SettInstrStatus.SAVE))){
			statusSave = Long.parseLong(strValue);
		}
		if(strKey.equals(String.valueOf(OBConstant.SettInstrStatus.APPROVALING))){
			statusApprovaling = Long.parseLong(strValue);
		}
		if(strKey.equals(String.valueOf(OBConstant.SettInstrStatus.APPROVALED))){
			statusApprovaled = Long.parseLong(strValue);
		}
		if(strKey.equals(String.valueOf(OBConstant.SettInstrStatus.CHECK))){
			statusCheck = Long.parseLong(strValue);
		}
		if(strKey.equals(String.valueOf(OBConstant.SettInstrStatus.DEAL))){
			statusDeal = Long.parseLong(strValue);
		}
		if(strKey.equals(String.valueOf(OBConstant.SettInstrStatus.FINISH))){
			statusFinish = Long.parseLong(strValue);
		}
		if(strKey.equals(String.valueOf(OBConstant.SettInstrStatus.REFUSE))){
			statusRefuse = Long.parseLong(strValue);
		}
		if(strKey.equals(String.valueOf("amounts"))){
			amounts = Double.parseDouble(strValue);
		}
	}
	statusCount = statusSave + statusApprovaling + statusApprovaled + statusCheck + statusDeal + statusFinish + statusRefuse;
%>
<safety:resources />
<script language="JavaScript" src="/webob/js/Control.js"></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>
<script language="JavaScript" src="/webob/js/date-picker.js"></script>
<script language="JavaScript" src="/webob/js/MagnifierSQL.js"></script>
<style type="text/css">
<!--
.table1 {  border: 2px solid #000000}
.table2 {  border: 1px solid #000000}
.td-right {  border-color: black black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 1px; border-bottom-width: 0px; border-left-width: 0px}
.td-leftbottom {  border-color: black black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 1px; border-left-width: 1px}
.td-leftright {  border-color: black black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 1px; border-bottom-width: 0px; border-left-width: 1px}
.td-leftbottomright {  border-color: black black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 1px; border-bottom-width: 1px; border-left-width: 1px}
.td-topbottom2right {  border-color: #000000 black black; border-style: solid; border-top-width: 1px; border-right-width: 1px; border-bottom-width: 2px; border-left-width: 0px}
.td-topbottom2 {  border-color: black black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 0px; border-bottom-width: 2px; border-left-width: 0px}
.td-bottom {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 1px; border-left-width: 0px}
.td-rightbottom {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 1px; border-bottom-width: 1px; border-left-width: 0px}
.td-bottom2 {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 2px; border-left-width: 0px}
.td-right2bottom {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 2px; border-bottom-width: 1px; border-left-width: 0px}
.td-toprightbottom {  border-color: black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 1px; border-bottom-width: 1px; border-left-width: 0px}
.td-topleftbottom2 {  border-color: black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 0px; border-bottom-width: 2px; border-left-width: 1px}
.td-topright2bottom {  border-color: black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 2px; border-bottom-width: 1px; border-left-width: 0px}
.td-topleftright2bottom2 {  border-color: black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 2px; border-bottom-width: 2px; border-left-width: 1px}
.td-topleftrightbottom2 {  border-color: black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 1px; border-bottom-width: 2px; border-left-width: 1px}
.td-topleftrightbottom {  border-color: black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 1px; border-bottom-width: 1px; border-left-width: 1px}
.td-topbottom {  border-color: black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 0px; border-bottom-width: 1px; border-left-width: 0px}
.td-topright {  border-color: black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 1px; border-bottom-width: 0px; border-left-width: 0px}
body {
	font-family: "宋体";
	font-size: 14px;
}
td {
	font-family: "宋体";
	font-size: 12px;
}
.f16 {
	font-family: "楷体_GB2312";
	font-size: 16px;
}
.f14 {
	font-family: "宋体";
	font-size: 14px;
}
.f10 {
	font-family: "宋体";
	font-size: 10px;
	line-height:10px;
}
.f12 {
	font-family: "宋体";
	font-size: 12px;
}

.f22
{
  font-family:"黑体";
  font-size:22px;
}
.f15 {
	font-family: "楷体_GB2312";
	font-size: 16px;
}
.tnt {Writing-mode:tb-rl;Text-align:center;font-size:12px}
-->
</style>
<br>
<br>
<form name="form1" method="post">
<table width="800" align="center" border="0" cellspacing="0" cellpadding="0">
<tbody>
	<tr>
		<td align="center">
			<b><font style="font-size:22px"><%=sessionMng.m_strClientName%></font></b>
       	</td>
	</tr>
	<tr>
		<td height="10">&nbsp;</td>
	</tr>
	<tr>
		<td valign="top">
			<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table1">
		        <tr class="ItemBody">
					<td width="*%" height="25" align="left" colspan="5" class="td-rightbottom">提交日期：<%=queryCapForm.getStartSubmit()%>&nbsp;&nbsp;至&nbsp;&nbsp;<%=queryCapForm.getEndSubmit()%></td>
		        </tr>
		        <tr class="ItemBody">
					<td width="*%" height="25" align="left" colspan="5" class="td-rightbottom">共有笔数：<%=statusCount%></td>
		        </tr>
		        <tr class="ItemBody">
		        	<td width="40" height="25" align="left" class="td-rightbottom">&nbsp;</td>
					<td width="150" height="25" align="left" class="td-rightbottom">未复核笔数：<%=statusSave%></td>
		          	<td width="150" height="25" align="left" class="td-rightbottom">审批中笔数：<%=statusApprovaling%></td>
		          	<td width="150" height="25" align="left" class="td-rightbottom">已审批笔数：<%=statusApprovaled%></td>
		          	<td width="*%" height="25" align="left" class="td-rightbottom">已复核笔数：<%=statusCheck%></td>
		        </tr>
		        <tr class="ItemBody">
		        	<td width="40" height="25" align="left" class="td-rightbottom">&nbsp;</td>
					<td width="150" height="25" align="left" class="td-rightbottom">处理中笔数：<%=statusDeal%></td>
		          	<td width="150" height="25" align="left" class="td-rightbottom">已完成笔数：<%=statusFinish%></td>
		          	<td width="150" height="25" align="left" class="td-rightbottom">已拒绝笔数：<%=statusRefuse%></td>
		          	<td width="*%" height="25" align="left" class="td-rightbottom">&nbsp;</td>
		        </tr>
		        <tr class="ItemBody">
					<td width="*%" height="25" align="left" colspan="5" class="td-rightbottom">交易总金额：<%=sessionMng.m_strCurrencySymbol%><%=DataFormat.formatDisabledAmount(amounts)%></td>
		        </tr>
		        <tr class="ItemBody">
		        	<td width="40" height="25" align="left" class="td-rightbottom">&nbsp;</td>
					<td width="150" height="25" align="left" class="td-rightbottom">（贷）<%=sessionMng.m_strCurrencySymbol%>0.00</td>
		          	<td width="*%" height="25" align="left" colspan="3" class="td-rightbottom">（借）<%=sessionMng.m_strCurrencySymbol%><%=DataFormat.formatDisabledAmount(amounts)%></td>
		        </tr>
			</table>
		</td>
	</tr>
	<tr>
		<td height="10">&nbsp;</td>
	</tr>
	<tr>
		<td>
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td height="25" align="left">查询时间：<%=DataFormat.getDateString(sessionMng.dLastLoginTime)%></td>
					<td height="25" align="right">操作员：<%=sessionMng.m_strUserName%></td>
				</tr>
			</table>
		</td>
		
	</tr>
	<tr>
		<td height="10">&nbsp;</td>
	</tr>
</tbody>
</table>
</form>
<script language="javascript">
<!--
//设置主Table
var defaultWidth = 1024;
var defaultHeight = 768;
var oBody = document.body;

if(defaultWidth <= screen.width){
	oBody.style.overflow = "hidden";
}

if(defaultHeight <= screen.height){
	oBody.style.overflow = "hidden";
}

//设置窗口
window.moveTo(0,0);  
if(document.all){
	top.window.resizeTo(screen.availWidth, screen.availHeight);  
}  
else if(document.layers || document.getElementById){  
	if (top.window.outerHeight<screen.availHeight || top.window.outerWidth < screen.availWidth){  
		top.window.outerHeight = screen.availHeight;  
		top.window.outerWidth = screen.availWidth;  
	}  
}
//-->
</script>
<%
}
catch (IException ie){
    OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
    Log.print(ie.getMessage());
}
%>
<%@ include file="/common/SignValidate.inc" %>