<%--
 页面名称 ：query_v003.jsp
 页面功能 : 网上银行 - 交易总结查询
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

<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<!-- 引入此用户当前回话的Session -->
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%String strContext = request.getContextPath();%>
<%
String strTitle = "交易总结查询";

try{
	OBHtml.showOBHomeHead(out, sessionMng, strTitle, OBConstant.ShowMenu.YES);
	
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
<jsp:include flush="true" page="/ShowMessage.jsp" />
<safety:resources />
<script language="JavaScript" src="/webob/js/Control.js"></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>
<script language="JavaScript" src="/webob/js/date-picker.js"></script>
<script language="JavaScript" src="/webob/js/MagnifierSQL.js"></script>

<form name="form1" method="post" action="../control/fi_c001.jsp">
<input type="hidden" name="strSuccessPageURL" value="../view/fi_v001.jsp">		<!--操作成功转向页面-->
<input type="hidden" name="strFailPageURL" value="../view/fi_v001.jsp">		<!--操作失败转向页面-->
<input type="hidden" name="strAction" value="<%=OBConstant.QueryOperationType.QUERY%>">  <!--操作代码-->
<!-- 查询申请指令的隐藏域 -->
<input type="hidden" name="lTransType" value="<%=queryCapForm.getTransType()%>">
<input type="hidden" name="lDepositID" value="<%=queryCapForm.getDepositID()%>">
<input type="hidden" name="strDepositNo" value="<%=queryCapForm.getDepositNo()%>">
<input type="hidden" name="lStatus" value="<%=queryCapForm.getStatus()%>">
<input type="hidden" name="sStartExe" value="<%=queryCapForm.getStartExe()%>">
<input type="hidden" name="sEndExe" value="<%=queryCapForm.getEndExe()%>">
<input type="hidden" name="dMinAmount" value="<%=DataFormat.formatListAmount(queryCapForm.getMinAmount())%>">
<input type="hidden" name="dMaxAmount" value="<%=DataFormat.formatListAmount(queryCapForm.getMaxAmount())%>">
<input type="hidden" name="sStartSubmit" value="<%=queryCapForm.getStartSubmit()%>">
<input type="hidden" name="sEndSubmit" value="<%=queryCapForm.getEndSubmit()%>">
<table width="800" align="center"  border="0" cellspacing="0" cellpadding="0">
<tbody>
	<tr>
		<td valign="top">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="1"><img src="/webob/graphics/lab_conner1.gif" width="3" height="23"></td>
					<td width="90" background="/webob/graphics/lab_conner2.gif" class="txt_til2">交易总结查询</td>
					<td width="16"><img src="/webob/graphics/lab_conner3.gif" width="16" height="23"></td>
					<td width="*%" class="MsoNormal">&nbsp;</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td valign="top">
			<table width="100%" border="0" cellspacing="0" cellpadding="0" class="normal">
		        <tr>
		        	<td width="20" height="25">&nbsp;</td>
					<td width="*%" height="25" align="left" colspan="5">提交日期：<%=queryCapForm.getStartSubmit()%>&nbsp;&nbsp;至&nbsp;&nbsp;<%=queryCapForm.getEndSubmit()%></td>
		          	<td width="20" height="25" class="MsoNormal">&nbsp;</td>
		        </tr>
		        <tr>
		        	<td width="20" height="25">&nbsp;</td>
					<td width="*%" height="25" align="left" colspan="5">共有笔数：<%=statusCount%></td>
		          	<td width="20" height="25" class="MsoNormal">&nbsp;</td>
		        </tr>
		        <tr>
		        	<td width="20" height="25">&nbsp;</td>
		        	<td width="40" height="25" align="left">&nbsp;</td>
					<td width="150" height="25" align="left">未复核笔数：<%=statusSave%></td>
		          	<td width="150" height="25" align="left">审批中笔数：<%=statusApprovaling%></td>
		          	<td width="150" height="25" align="left">已审批笔数：<%=statusApprovaled%></td>
		          	<td width="*%" height="25" align="left">已复核笔数：<%=statusCheck%></td>
		          	<td width="20" height="25" class="MsoNormal">&nbsp;</td>
		        </tr>
		        <tr>
		        	<td width="20" height="25">&nbsp;</td>
		        	<td width="40" height="25" align="left">&nbsp;</td>
					<td width="150" height="25" align="left">处理中笔数：<%=statusDeal%></td>
		          	<td width="150" height="25" align="left">已完成笔数：<%=statusFinish%></td>
		          	<td width="150" height="25" align="left">已拒绝笔数：<%=statusRefuse%></td>
		          	<td width="*%" height="25" align="left">&nbsp;</td>
		          	<td width="20" height="25" class="MsoNormal">&nbsp;</td>
		        </tr>
		        <tr>
		        	<td width="20" height="25">&nbsp;</td>
					<td width="*%" height="25" align="left" colspan="5">交易总金额：<%=sessionMng.m_strCurrencySymbol%><%=DataFormat.formatDisabledAmount(amounts)%></td>
		          	<td width="20" height="25" class="MsoNormal">&nbsp;</td>
		        </tr>
		        <tr>
		        	<td width="20" height="25">&nbsp;</td>
		        	<td width="40" height="25" align="left">&nbsp;</td>
					<td width="150" height="25" align="left">（贷）<%=sessionMng.m_strCurrencySymbol%>0.00</td>
		          	<td width="*%" height="25" align="left" colspan="3">（借）<%=sessionMng.m_strCurrencySymbol%><%=DataFormat.formatDisabledAmount(amounts)%></td>
		          	<td width="20" height="25" class="MsoNormal">&nbsp;</td>
		        </tr>
				<tr>
					<td colspan="7" height="1" class="MsoNormal">&nbsp;</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td height="25">查询时间：<%=DataFormat.getDateString(sessionMng.dLastLoginTime)%></td>
	</tr>
	<tr>
		<td height="10">&nbsp;</td>
	</tr>
	<tr>
		<td valign="top">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="*%" align="right">
						<input type="button" value=" 打 印 " class="button1" name="butPrint" onclick="toPrint(form1)"/>&nbsp;
						<input type="button" value=" 返 回 " class="button1" name="butReturn" onclick="toReturn(form1)"/>
					</td>
					<td width="20" class="MsoNormal">&nbsp;</td>
				</tr>
				<tr>
					<td colspan="2" height="1" class="MsoNormal">&nbsp;</td>
				</tr>
			</table>
		</td>
	</tr>
</tbody>
</table>
</form>
	
<script language="javascript">
/* 页面焦点及回车控制 */
setFormName("form1");

function toPrint(form)
{
	if(confirm("是否打印？"))
	{
		form.target = "_blank";
		form.action = "../control/query_c003.jsp";
		form.strSuccessPageURL.value = "../view/query_v003_print.jsp";
		form.strFailPageURL.value = "../view/query_v001.jsp";
		form.strAction.value = "<%=OBConstant.QueryOperationType.QUERY%>";
	    form.submit();
    }
}

function toReturn(form){
	form.target = "";
	form.action = "<%=strContext%>/capital/query/control/query_c001.jsp";
	form.strSuccessPageURL.value = "/capital/query/view/query_v002.jsp";
	form.strFailPageURL.value = "/capital/query/view/query_v001.jsp";
	form.strAction.value = "<%=OBConstant.QueryOperationType.QUERY%>";
	showSending();
    form.submit();
}

</script>
<%
}
catch (IException ie){
    OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
    Log.print(ie.getMessage());
}
OBHtml.showOBHomeEnd(out,sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID);
%>
<%@ include file="/common/SignValidate.inc"%>