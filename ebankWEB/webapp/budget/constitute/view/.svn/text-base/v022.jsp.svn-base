<%--
 页面名称 ：v022.jsp
 页面功能 : 模板测试页面
 作    者 ：weilu
 日    期 ：2005-8-1
 特殊说明 ：
 实现操作说明：
 修改历史 ：

--%>
<%@ page contentType="text/html;charset=gbk" %>
<!-- 引入需要的类,尽量不用.* -->
<%@ page import="java.sql.Timestamp" %>
<%@ page import="com.iss.itreasury.util.Env" %>
<%@ page import="com.iss.itreasury.util.Log" %>
<%@ page import="com.iss.itreasury.util.Constant" %>
<%@ page import="com.iss.itreasury.util.DataFormat" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.Collection" %>
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.iss.itreasury.budget.templet.dataentity.*" %>
<%@ page import="com.iss.itreasury.budget.util.*" %>
<%@ page import="com.iss.itreasury.ebank.util.*" %>
<%@ page import="com.iss.itreasury.budget.util.BUDGETConstant" %>
<%@ page import="com.iss.itreasury.budget.setting.bizlogic.BudgetParameterOperation" %>
<%@ page import="com.iss.itreasury.budget.constitute.dataentity.BudgetPlanInfo" %>
<%@ page import="com.iss.itreasury.ebank.approval.dataentity.ApprovalSettingInfo" %>
<%@ page import="com.iss.itreasury.ebank.approval.dataentity.ApprovalTracingInfo" %>
<%@ page import="com.iss.itreasury.ebank.bizdelegation.ApprovalDelegation" %>

<%@ page import="com.iss.itreasury.ebank.util.*" %>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<jsp:include page="/ShowMessage.jsp"/>

<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>

<script language="JavaScript" src="/webbudget/js/Control.js" ></script>
<script language="JavaScript" src="/webbudget/js/budgetCheck.js" ></script>
<script language="JavaScript" src="/webbudget/js/Check.js"></script>
<script language="JavaScript" src="/webbudget/js/MagnifierSQL.js"></script>

<safety:resources />
<%
try
{
	String strTitle = "预算审核";
	 //用户登录检测 
	if (sessionMng.isLogin() == false)
	{
		OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E002");
		out.flush();
		return;
	}

	// 判断用户是否有权限 
	if (sessionMng.hasRight(request) == false)
	{
		out.println(sessionMng.hasRight(request));
		OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E003");
		out.flush();
		return;
	}
	/* 显示文件头 */
	OBHtml.showOBHomeHead(out, sessionMng, strTitle, Constant.YesOrNo.YES);

	/**
	 * 公共参数
	 */
	long operatorId				= sessionMng.m_lUserID;				//当前操作用户ID
	long currencyId				= sessionMng.m_lCurrencyID;			//当前系统使用币种ID
	long officeId				= sessionMng.m_lOfficeID;			//办事处ID	
	long clientId				= sessionMng.m_lClientID;			//单位ID

	
	String[] aryHeadDateString = null;
	Vector vTemplate = null;
	long lLevelCount = -1;
	String versionNo = "";
	Vector v = new Vector();
	String strTmp = null;
	
	BudgetPlanInfo info = (BudgetPlanInfo)request.getAttribute("BudgetPlanInfo");
	vTemplate = (Vector)request.getAttribute("searchResult");
	Vector approvalVector = (Vector)request.getAttribute("approvalVector");
	
	long lActionID = -1;	//审批流操作类型
	if (info.getBudgetFlag() == BUDGETConstant.BudgetFlag.CONSTITUTE 
		|| info.getBudgetFlag() == BUDGETConstant.BudgetFlag.ADJUST)
		lActionID = Constant.ApprovalAction.BUDGET_CURRENT;
	else
		lActionID = Constant.ApprovalAction.BUDGET_TOTAL;

	if (strTmp != null && strTmp.length() > 0)
		versionNo = strTmp;

	strTmp = (String)request.getAttribute("maxLevel");
	lLevelCount = Long.parseLong(strTmp);

	long[] tmp = info.getShowColumn();
	aryHeadDateString = new String[tmp.length];
	for (int i=0;i<tmp.length;i++)
	{
		aryHeadDateString[i] = BUDGETConstant.BudgetColumnList.getName(tmp[i]);
	}
	
	long[] aryLevelStatus = {1,1};//第一级默认显示

	ApprovalDelegation approvalBiz=new ApprovalDelegation();
%>
	<jsp:include page="/ShowMessage.jsp"/>
	
	<form name="frmV022" method="post" action="../control/c022.jsp">
		<input type="hidden" name="strSourcePage" value="">
		<input type="hidden" name="strSuccessPageURL" value="../control/c021.jsp?strSuccessPageURL=../view/v021.jsp&strFailPageURL=../view/v021.jsp&strAction=7&statusID=3">
		<input type="hidden" name="strFailPageURL" value="../control/c021.jsp?strSuccessPageURL=../view/v021.jsp&strFailPageURL=../view/v021.jsp&strAction=7&statusID=2">
		<input type="hidden" name="strAction" value="">
		<input type="hidden" name="strCtrlPageURL" value="">
		<input type="hidden" name="actionID" value="<%=lActionID%>">
		<input type="hidden" name="loanTypeID" value="<%=clientId%>">
		<input type="hidden" name="approvalContentID" value="<%=info.getId()%>">
		<input type="hidden" name="userID" value="<%=operatorId%>">
		<input type="hidden" name="hdnLevelCount" value="<%=lLevelCount%>">
			
		
		<table width="100%" border="0" class="top" height="100">
			<tr>
				<td height="2" class="FormTitle" width="100%" colspan="2"><b>预算编制及审批 - 预算审核</b>
				</td>
			</tr>
			<tr>
				<td height="5" width="100%" valign="bottom" colspan="2">
				<hr>
<%
	//显示模版树
	BUDGETHTML.showBudgetTemplate(out,aryHeadDateString,vTemplate,lLevelCount,aryLevelStatus);
%>
				</td>
			</tr>
			<tr>
				<td height="5" width="100%" valign="bottom" colspan="2">
				
					<table width="100%" border="0">
					<tr>
					<td >预算说明：</td>
					<td colspan="3"><input name="lDiscountContractIDCtrl" type="text" class="box" value='<%=info.getRemark()%>' size="50"  maxlength='30' readonly></td>
					</tr>
					</table>
					<table width=100% border="1" bordercolor="#999999" cellpadding="0" cellspacing="0">
					<tr>
					<td width="100%"><table width="100%" border="0">
					<tr>
				
					<td>历史审核意见:</td>
					<td colspan=2>
					
					<table width="550" border="0" align="left" height="50%" class="ItemList">
                        <tr> 
                          <td class="ItemTitle" width="12%" height="20"> <div align="center">序列号</div> </td>
                          <td class="ItemTitle" width="21%" height="20"> <div align="center">意见内容</div> </td>
                          <td class="ItemTitle" width="21%" height="20"> <div align="center">审核人</div> </td>
                          <td class="ItemTitle" width="20%" height="20"> <div align="center">审核决定</div> </td>
                          <td class="ItemTitle" width="26%" height="20"> <div align="center">日期和时间</div> </td>
                        </tr>
                        <% if ( (approvalVector!=null)&&(approvalVector.size()>0) ){ %>
                        <%  	ApprovalTracingInfo approvalInfo=null;
                        		long serialID=-1;
                        		String opinion="";
                        		String checkUserName="";
                        		long resultID=-1;
                        		String strResult="";
                        		Timestamp checkDate=null;
                        		String strCheckDate="";
                        		int approvalCount=approvalVector.size();
                        		for ( int i=0;i<approvalCount;i++ )
                        		{
                        			approvalInfo=(ApprovalTracingInfo)approvalVector.get(i);
                        			serialID=approvalInfo.getSerialID();
                        			opinion=approvalInfo.getOpinion();
                        			checkUserName=approvalInfo.getUserName();
                        			resultID=approvalInfo.getResultID();
                        			checkDate=approvalInfo.getApprovalDate();
                        			strResult=Constant.ApprovalDecision.getName(resultID);
                        			if ( opinion==null ) opinion="";
                        			if ( checkDate==null) 
                        				strCheckDate="";
                        			else
                        				strCheckDate=DataFormat.getDateTimeString(checkDate);
                        %>					
                        <tr> 
                          <td class="ItemBody" width="12%" height="24"><%=serialID%></td>
                          <td class="ItemBody" width="21%" height="24"><%=opinion%></td>
                          <td class="ItemBody" width="21%" height="24"><%=checkUserName%></td>
                          <td class="ItemBody" width="20%" height="24"><%=strResult%></td>
                          <td class="ItemBody" width="26%" height="24"><%=strCheckDate%></td>
                        </tr>
                        <%		} 	%>
                        <% }else{ 	%>
                        <tr> 
                          <td class="ItemBody" width="12%" height="24">&nbsp;</td>
                          <td class="ItemBody" width="21%" height="24">&nbsp;</td>
                          <td class="ItemBody" width="21%" height="24">&nbsp;</td>
                          <td class="ItemBody" width="20%" height="24">&nbsp;</td>
                          <td class="ItemBody" width="26%" height="24">&nbsp;</td>
                        </tr>
                        <% } %>
                      </table>
					
					</td>
					</tr>
					<tr>
				
					<td>审批意见:</td>
					<td colspan=2>
					<textArea cols='70' name="opinion"  onFocus="nextfield='NextUserID';"></textArea>
					</td>
					</tr>
						
					<tr>
					<td>送交审核人:</td>

					<td>
						<%
							approvalBiz.showApprovalUserList(out,"nextUserID","submitfunction",Constant.ModuleType.BUDGET,sessionMng.m_lClientID,lActionID,sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID,sessionMng.m_lUserID,info.getNextCheckLevel(),(info.getIsLowLevel() == Constant.YesOrNo.YES) ? true : false);
						%>
					</td>
					</tr>
					</table></td>
					</tr>
					</table>	
					
					
					<table width="97%" border="0" cellspacing="2" cellpadding="2" height="15" align="center">
						<tr>
							<td height="19" colspan="3">
								<div align="right">
								<input type="button" name="back" value=" 返回 " class="button" onClick="doBack(frmV022);">
									
									<input class="button" name="Submit32" type="button" value=" 审核 " onClick="doCheck(frmV022);">
									<input class="button" name="Submit32" type="button" value=" 拒绝 " onClick="doRefuse(frmV022);">
									<%if (info.getBudgetFlag() != BUDGETConstant.BudgetFlag.TOTAL){%>
										<input class="button" name="Submit32" type="button" value=" 返回修改 " onClick="doReturn(frmV022);">
									<%}%>
								</div>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</form>


<script language="javascript">

function doBack(form)
{	
	showSending();
	document.location.href="../control/c021.jsp?strSuccessPageURL=../view/v021.jsp&strFailPageURL=../view/v021.jsp&strAction=7&statusID=2";		
}

function doCheck(form)
{	
	if (!validateOpinion(form))return false;
	if (!validateNextUser(form))return false;
	if (confirm("确认审核?"))
	{
		showSending();
		form.strAction.value="<%=Constant.Actions.CHECK%>";	//定义操作代码
		form.submit();
	}
}
function doRefuse(form)
{	
	if (!validateOpinion(form))return false;
	if (confirm("确认拒绝?"))
	{
		showSending();
		form.strAction.value="<%=Constant.Actions.REJECT%>";	//定义操作代码
		form.submit();
	}
}
function doReturn(form)
{	
	if (!validateOpinion(form))return false;
	if (confirm("确认返回修改?"))
	{
		showSending();
		form.strAction.value="<%=Constant.Actions.RETURN%>";	//定义操作代码
		form.submit();
	}
}
function validateOpinion(form)
{
	if (form.opinion.value == null || form.opinion.value == "")
	{
		alert("审批意见不能为空！");
		return false;
	}
	else
	{
		if(form.opinion.value.length > 250)
		{
			alert("审批意见不能长于250个字符！");
			return false;
		}
	}
	return true;
}
function validateNextUser(form)
{
	if (form.nextUserID.value == null || form.nextUserID.value == "-1")
	{
		alert("必须选择下一级审核人！");
		return false;
	}
	return true;
}

</script>
<% 
	OBHtml.showOBHomeEnd(out);	
}
catch (Exception ex)
{
	System.out.println(ex.toString());
	ex.printStackTrace();
}
%>
<%@ include file="/common/SignValidate.inc" %>