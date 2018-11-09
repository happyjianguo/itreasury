<%--
 页面名称 ：v003.jsp
 页面功能 : 预算编制查找返回结果集
 作    者 ：weilu
 日    期 ：2005-7-14
 特殊说明 ：
 修改历史 ：
--%>
<%@ page contentType="text/html;charset=gbk" %>

<%@ page import="java.sql.Timestamp" %>
<%@ page import="com.iss.itreasury.util.Env" %>
<%@ page import="com.iss.itreasury.util.Log" %>
<%@ page import="com.iss.itreasury.util.Constant" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.Collection" %>
<%@ page import="com.iss.itreasury.util.DataFormat" %>
<%@ page import="com.iss.itreasury.budget.util.BUDGETHTML" %>
<%@ page import="com.iss.itreasury.budget.util.BUDGETConstant" %>
<%@ page import="com.iss.itreasury.budget.util.BUDGETMagnifier" %>
<%@ page import="com.iss.itreasury.budget.util.BUDGETNameRef" %>
<%@ page import="com.iss.itreasury.budget.constitute.dataentity.BudgetPlanInfo" %>
<%@ page import="com.iss.itreasury.ebank.util.*" %>

<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<jsp:include page="/ShowMessage.jsp"/>

<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<!--引入js文件-->
<script language="JavaScript" src="/webbudget/js/Control.js" ></script>
<script language="JavaScript" src="/webbudget/js/budgetCheck.js" ></script>
<script language="JavaScript" src="/webbudget/js/Check.js"></script>
<script language="JavaScript" src="/webbudget/js/MagnifierSQL.js"></script>

<safety:resources />
<!-- 定义页面控制参数 -->
<%
try
{
	String strTitle = "预算汇总";
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

	BudgetPlanInfo info = (BudgetPlanInfo)request.getAttribute("BudgetPlanInfo");

	/**
	 * 公共参数
	 */
	long operatorId				= sessionMng.m_lUserID;				//当前操作用户ID
	long currencyId				= sessionMng.m_lCurrencyID;			//当前系统使用币种ID
	long officeId				= sessionMng.m_lOfficeID;			//办事处ID
	long clientId				= sessionMng.m_lClientID;			//单位ID
	String versionNo = (String)request.getAttribute("versionNo");

	%>
	<form name="frmV012" method="post" action="../control/c011.jsp">
		<input type="hidden" name="strSourcePage" value="">
		<input type="hidden" name="strSuccessPageURL" value="">
		<input type="hidden" name="strFailPageURL" value="">
		<input type="hidden" name="strAction" value="">
		<input type="hidden" name="strCtrlPageURL" value="">
		<input type="hidden" name="remark" value="<%=info.getRemark()%>">
		<input type="hidden" name="inputUserID" value="<%=operatorId%>">
		<input type="hidden" name="officeID" value="<%=officeId%>">
		<input type="hidden" name="currencyID" value="<%=currencyId%>">
		<input type="hidden" name="budgetSystemID" value="<%=info.getBudgetSystemID()%>">
		<input type="hidden" name="budgetPeriodID" value="<%=info.getBudgetPeriodID()%>">
		<input type="hidden" name="startDate" value="<%=info.getStartDate()%>">
		<input type="hidden" name="endDate" value="<%=info.getEndDate()%>">
		<input type="hidden" name="clientID" value="<%=clientId%>">
		<input type="hidden" name="budgetFlag" value="<%=BUDGETConstant.BudgetFlag.TOTAL%>">
	<TABLE width="80%" height="10" border=0 class=top>
       <TBODY>
         <TR>
           <TD class=FormTitle height=2 width="100%"><B>预算编制及审批 - 预算汇总</B></TD>
         </TR>
         <TR>
           <TD height=10 vAlign=top width="100%">
             <TABLE width="100%" border=0 align=center cellspacing="1" class=ItemList>
               <TBODY>
                 <TR>
                   <td height="20" nowrap class="ItemTitle">
                     <div align="center">单位编号</div>
                   </td>
                   <td height="20" nowrap class="ItemTitle">
                     <div align="center">单位名称</div>
                   </td>
                   <td height="20" nowrap class="ItemTitle">
                     <div align="center">状态</div>
                   </td>
				</tr>
				<%
		Collection coll =(Collection)request.getAttribute("searchResult");
			if(coll!=null){
				for(Iterator iter =coll.iterator();iter.hasNext();)
				{
					BudgetPlanInfo planInfo=(BudgetPlanInfo)iter.next();
						%>
						<TR class=ItemBody>
						   <td height="20">
							 <div align="center"><%=BUDGETNameRef.getClientCodeByID(planInfo.getClientID())%></div>
						   </td>
						   <td height="20">
							 <div align="center"><%=BUDGETNameRef.getClientNameByID(planInfo.getClientID())%></div>
						   </td>
						   <td height="20">
							 <input type="hidden" name="tmpStatusID" value="<%=planInfo.getStatusID()%>">
							 <div align="center"><%=BUDGETConstant.ConstituteStatus.getName(planInfo.getStatusID())%></div>
						   </td>
						</tr>
<%
				}
			}else{
%>
			<TR class=ItemBody>
			   <td height="20">
				 <div align="center"></div>
			   </td>
			   <td height="20">
				 <div align="center"></div>
			   </td>
			   <td height="20">
				 <div align="center"></div>
			   </td>
			</tr>
<%}%>
               </TBODY>
             </TABLE>
           </TD>
         </TR>
<TR>
<TD height=10 vAlign=top>
	<TABLE align=center height="15" width="97%">
	<TBODY>
		<TR>
		<TD height="10">&nbsp;</TD>
		<TD height="10" align="right">
		<input type="hidden" name="tmpStatusID" value="">
		<input type="hidden" name="tmpStatusID" value="">
			<input name="Submit32" type="button" class="button" onClick="javascript:doBack();" value=" 返回 ">
			<input class="button" name="Submit322" type="button" value=" 汇 总 " onClick="javascript:doSum(frmV012);">
		</TD>
		</TR>
	</TBODY>
	</TABLE>
</TD>
</TR>
       </TBODY>
    </TABLE>
</form>
<!-- Javascript代码 -->
<script language="JavaScript">
function doSum(form){
	 if (!validate(form)) 
	 {
		 alert("还有没通过审批的编制!");
		 return;
	 }
	 if (confirm("确认汇总?"))
	{
		 showSending();//显示正在执行
		 var strUrl = "../control/c011.jsp?strSuccessPageURL=../view/v013.jsp&strFailPageURL=../view/v011.jsp&strAction=<%=Constant.Actions.MATCHSEARCH%>";
		 strUrl += "&budgetSystemID=<%=info.getBudgetSystemID()%>";
		 strUrl += "&budgetPeriodID=<%=info.getBudgetPeriodID()%>";
		 strUrl += "&startDate=<%=info.getStartDate()%>";
		 strUrl += "&versionNo=<%=versionNo%>";
		 strUrl += "&clientID=<%=clientId%>";

		 form.strSuccessPageURL.value = "../view/v013.jsp";	;	//定义操作成功后跳往的页面
		 form.strFailPageURL.value="../view/v011.jsp";		//定义失败后跳往的页面
		 form.strAction.value="<%=Constant.Actions.SUM%>";	//定义操作代码
		 form.submit();
	}
}
function doBack()
{
	location.href="../view/v011.jsp";
}
function validate(form)
{
	var allStatus = true;
	var len = form.tmpStatusID.length;
	for (var i=0;i<len;i++)
	{
		if (form.tmpStatusID[i].value != ""
			&& form.tmpStatusID[i].value != <%=BUDGETConstant.ConstituteStatus.UPPERCHECK%> 
			&& form.tmpStatusID[i].value != <%=BUDGETConstant.ConstituteStatus.CURRENTCHECK%>
			&& form.tmpStatusID[i].value != <%=BUDGETConstant.ConstituteStatus.LASTCHECK%>)
		{
			allStatus = false;
			break;
		}
	}
	return allStatus;
}
</script>
<%	
	/**
	* 现实文件尾
	*/
	OBHtml.showOBHomeEnd(out);	
}
//异常处理
catch(Exception exp)
{
	exp.printStackTrace();
	Log.print(exp.getMessage());
}
%>
<%@ include file="/common/SignValidate.inc" %>