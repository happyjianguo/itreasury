<%--
 页面名称 ：v014.jsp
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

	%>
	<form name="frmv014" method="post" action="../control/c011.jsp">
		<input type="hidden" name="strSourcePage" value="">
		<input type="hidden" name="strSuccessPageURL" value="">
		<input type="hidden" name="strFailPageURL" value="">
		<input type="hidden" name="strAction" value="">
		<input type="hidden" name="strCtrlPageURL" value="">
		<input type="hidden" name="budgetSystemID" value="<%=info.getBudgetSystemID()%>">
		<input type="hidden" name="budgetPeriodID" value="<%=info.getBudgetPeriodID()%>">
		<input type="hidden" name="startDate" value="<%=info.getStartDate()%>">
		<input type="hidden" name="endDate" value="<%=info.getEndDate()%>">
		<input type="hidden" name="remark" value="<%=info.getRemark()%>">
		<input type="hidden" name="versionNo" value="">
		<input type="hidden" name="id" value="">
		<input type="hidden" name="clientID" value="<%=clientId%>">
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
                     <div align="center">预算体系</div>
                   </td>
                   <td height="20" nowrap class="ItemTitle">
                     <div align="center">预算周期</div>
                   </td>
                   <td height="20" nowrap class="ItemTitle">
                     <div align="center">预算区间开始日期</div>
                   </td>
                   <td height="20" nowrap class="ItemTitle">
                     <div align="center">预算区间截至日期</div>
                   </td>
				    <td height="20" nowrap class="ItemTitle">
                     <div align="center">版本号</div>
                   </td>
				   <td height="20" nowrap class="ItemTitle">
                     <div align="center">状态</div>
                   </td>
				   <td height="20" nowrap class="ItemTitle">
                     <div align="center">编制人</div>
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
							 <div align="center"><a href="#" onclick="find(<%=planInfo.getBudgetSystemID()%>,<%=planInfo.getBudgetPeriodID()%>,'<%=planInfo.getStartDate()%>','<%=planInfo.getVersionNo()%>',<%=planInfo.getId()%>,'<%=planInfo.getEndDate()%>');"><%=planInfo.getBudgetSystemName()%></a></div>
						   </td>
						   <td height="20">
							 <div align="center"><%=planInfo.getBudgetPeriodName()%></div>
						   </td>
						   <td height="20">
							 <div align="center"><%=DataFormat.getDateString(planInfo.getStartDate())%></div>
						   </td>
						   <td height="20">
							 <div align="center"><%=DataFormat.getDateString(planInfo.getEndDate())%></div>
						   </td>
						   <td height="20">
							 <div align="center"><%=planInfo.getVersionNo()%></div>
						   </td>
						   <td height="20">
							 <div align="center"><%=BUDGETConstant.ConstituteStatus.getName(planInfo.getStatusID())%></div>
						   </td>
						   <td height="20">
							 <div align="center"><%=NameRef.getUserNameByID(planInfo.getInputUserID())%></div>
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
			   <td height="20">
				 <div align="center"></div>
			   </td>
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
             </TABLE></TD></TR></TBODY></TABLE></form>
			 <TABLE align=center height="15" width="97%">
               <TBODY>
                 <TR>
                   <TD colSpan='6' height="10"><DIV align=right>
					<input class="button" name="next" type="button" value=" 返 回 " onClick="javascript:location.href='../view/v011.jsp'">
                     </DIV>
                   </TD>
                 </TR>
               </TBODY>
             </TABLE>
        

<!-- Javascript代码 -->
<script language="JavaScript">
function find(systemID,periodID,startDate,versionNo,planId,endDate){
	showSending();//显示正在执行
	 frmv014.budgetSystemID.value = systemID;
	 frmv014.budgetPeriodID.value = periodID;
	 frmv014.startDate.value = startDate;
	  frmv014.endDate.value = endDate;
	 frmv014.versionNo.value = versionNo;
	  frmv014.id.value = planId;
	frmv014.strSuccessPageURL.value="../view/v013.jsp";	//定义操作成功后跳往的页面
	frmv014.strFailPageURL.value="../view/v011.jsp";		//定义失败后跳往的页面
	frmv014.strAction.value="<%=Constant.Actions.LINKSEARCH%>";	//定义操作代码
	frmv014.submit();
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