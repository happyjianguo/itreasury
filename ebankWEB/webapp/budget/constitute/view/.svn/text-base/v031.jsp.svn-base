<%--
 页面名称 ：v031.jsp
 页面功能 : 下级单位预算接受界面
 作    者 ：weilu
 日    期 ：2005-8-1
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
	String strTitle = "下级单位预算接收";
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
	<form name="frmV021" method="post" action="../control/c031.jsp">
		<input type="hidden" name="strSourcePage" value="">
		<input type="hidden" name="strSuccessPageURL" value="">
		<input type="hidden" name="strFailPageURL" value="">
		<input type="hidden" name="strAction" value="">
		<input type="hidden" name="strCtrlPageURL" value="">
		<input type="hidden" name="officeID" value="<%=officeId%>">
		<input type="hidden" name="currencyID" value="<%=currencyId%>">
		<input type="hidden" name="nextCheckUserID" value="<%=operatorId%>">
		<input type="hidden" name="budgetSystemID" value="">
		<input type="hidden" name="budgetPeriodID" value="">
		<input type="hidden" name="startDate" value="">
		<input type="hidden" name="endDate" value="">
		<input type="hidden" name="remark" value="">
		<input type="hidden" name="budgetFlag" value="">
		<input type="hidden" name="id" value="">
		<input type="hidden" name="versionNo" value="">
		<input type="hidden" name="clientID" value="<%=clientId%>">

	<TABLE width="100%" height="10" border=0 class=top>
       <TBODY>
         <TR>
           <TD class=FormTitle height=2 width="100%"><B>预算编制及审批 - 下级单位预算接收</B></TD>
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
                     <div align="center">编制日期</div>
                   </td>
				   <td height="20" nowrap class="ItemTitle">
                     <div align="center">编制人</div>
                   </td>
					<td height="20" nowrap class="ItemTitle">
                     <div align="center"></div>
                   </td>
				</tr>
				<%
		Collection coll =(Collection)request.getAttribute("searchResult");
			if(coll!=null && coll.size() > 0){
				for(Iterator iter =coll.iterator();iter.hasNext();)
				{
					BudgetPlanInfo planInfo=(BudgetPlanInfo)iter.next();
						%>
						<TR class=ItemBody>
						   <td height="20">
							 <div align="center"><%=planInfo.getBudgetSystemName()%></div>
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
							 <div align="center"><%=DataFormat.getDateString(planInfo.getConstituteDate())%></div>
						   </td>
						   <td height="20">
							 <div align="center"><%=NameRef.getUserNameByID(planInfo.getInputUserID())%></div>
						   </td>
						    <td height="20">
							 <div align="center"><a href="#" onclick="doReceive(<%=planInfo.getBudgetSystemID()%>,<%=planInfo.getBudgetPeriodID()%>,'<%=planInfo.getStartDate()%>','<%=planInfo.getVersionNo()%>','<%=planInfo.getEndDate()%>','<%=planInfo.getBudgetFlag()%>','<%=planInfo.getId()%>');">接收</a></div>
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
			   <td height="20">
				 <div align="center"></div>
			   </td>
			    <td height="20">
				 <div align="center"></div>
			   </td>
			</tr>
<%}%>
  </TBODY></TABLE></TD></TR></TBODY></TABLE></form>
<!-- Javascript代码 -->
<script language="JavaScript">
function doReceive(systemID,periodID,startDate,versionNo,endDate,flag,id){
	if (confirm("确认接收?"))
	{
		 showSending();//显示正在执行
		 frmV021.budgetSystemID.value = systemID;
		 frmV021.budgetPeriodID.value = periodID;
		 frmV021.startDate.value = startDate;
		 frmV021.endDate.value = endDate;
		 frmV021.versionNo.value = versionNo;
		 frmV021.budgetFlag.value = flag;
		 frmV021.id.value = id;
		
		frmV021.strSuccessPageURL.value="../control/c021.jsp?strSuccessPageURL=../view/v021.jsp&strFailPageURL=../view/v021.jsp&strAction=7&statusID=2";	//定义操作成功后跳往的页面
		frmV021.strFailPageURL.value="../view/v031.jsp";		//定义失败后跳往的页面
		frmV021.strAction.value="<%=Constant.Actions.NEXTSTEP%>";	//定义操作代码
		frmV021.submit();
	}
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