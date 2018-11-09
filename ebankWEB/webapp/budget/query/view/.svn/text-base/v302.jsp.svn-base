<%--
 页面名称 ：v302.jsp
 页面功能 : 预算版本查找返回结果集
 作    者 ：leiliu
 日    期 ：2005-08-05
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
<%@ page import="com.iss.itreasury.budget.query.resultinfo.QBudgetVersionResultInfo" %>
<%@ page import="com.iss.itreasury.budget.query.paraminfo.QueryBudgetInfo" %>
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
	//请求检测
	/** 权限检查 **/
	String strTableTitle = "预算版本查询";
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
	/** 显示文件头 **/
	OBHtml.showOBHomeHead(out,sessionMng,"预算版本查询",Constant.YesOrNo.YES);
                                                                                 
    QueryBudgetInfo info = (QueryBudgetInfo)request.getAttribute("QueryBudgetInfo");
	System.out.println("*******************info.getVersionNo()********************");
	/**
	 * 公共参数
	 */
	long operatorId				= sessionMng.m_lUserID;				//当前操作用户ID
	long currencyId				= sessionMng.m_lCurrencyID;			//当前系统使用币种ID
	long officeId				= sessionMng.m_lOfficeID;			//办事处ID
	long clientId				= sessionMng.m_lClientID;			//单位ID

	%>
	<form name="frmV003" method="post" action="../control/c301.jsp">
		<input type="hidden" name="strSourcePage" value="">
		<input type="hidden" name="strSuccessPageURL" value="">
		<input type="hidden" name="strFailPageURL" value="">
		<input type="hidden" name="strAction" value="">
		<input type="hidden" name="strCtrlPageURL" value="">

		<input type="hidden" name="budgetSystemID" value="<%=info.getBudgetSystemID()%>">
		<input type="hidden" name="budgetPeriodID" value="<%=info.getBudgetPeriodID()%>">
		<input type="hidden" name="startDate" value="<%=info.getStartDate()%>">
		<input type="hidden" name="endDate" value="<%=info.getEndDate()%>">
		
		<input type="hidden" name="versionNo" value="<%=info.getVersionNo()%>">
		
		<input type="hidden" name="planID" value="">
		<input type="hidden" name="clientID" value="<%=info.getClientID()%>">
		
		
	<TABLE width="100%" height="10" border=0 class=top>
       <TBODY>
         <TR>
           <TD class=FormTitle height=2 width="100%"><B>分析考核 - 预算版本查询</B></TD>
         </TR>
         <TR>
           <TD height=10 vAlign=top width="100%">
             <TABLE width="100%" border=0 align=center cellspacing="1" class=ItemList>
               <TBODY>
                 <TR>
                   <td height="20" nowrap class="ItemTitle">
                     <div align="center">预算版本号</div>
                   </td>
				   <td height="20" nowrap class="ItemTitle">
                     <div align="center">预算体系</div>
                   </td>
				   <td height="20" nowrap class="ItemTitle">
                     <div align="center">预算单位</div>
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
                     <div align="center">编制时间</div>
                   </td>
				   <td height="20" nowrap class="ItemTitle">
                     <div align="center">编制人</div>
				   <td height="20" nowrap class="ItemTitle">
                     <div align="center">版本状态</div>
                   </td>

				</tr>
				<%
					
		 Collection coll =(Collection)request.getAttribute("srcheaResult");	
         if(coll!=null){
				for(Iterator iter =coll.iterator();iter.hasNext();)
				{
					QBudgetVersionResultInfo planInfo=(QBudgetVersionResultInfo)iter.next();
					System.out.println(planInfo.getPlanID());
						%>
						<TR class=ItemBody>
						   <td height="20">
							 <div align="center"><a href="#" onclick="find(<%=planInfo.getPlanID()%>,<%=planInfo.getBudgetSystemID()%>);"><%=planInfo.getVersionNo()%></a></div>
						   </td>
						    <td height="20">
							 <div align="center"><%=planInfo.getBudgetSystemName()%></div>
						   </td>
						   <td height="20">
						   
							 <div align="center"><%=NameRef.getClientNameByID(planInfo.getClientID())%></div>
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
							 <div align="center"><%=DataFormat.getDateString(planInfo.getPlanDate())%></div>
						   </td>

						   <td height="20">
							 <div align="center"><%=NameRef.getUserNameByID(planInfo.getInputUserID())%></div>
						   </td>
						   <td height="20">
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
					<input class="button" name="next" type="button" value=" 返 回 " onClick="javascript:location.href='../view/v301.jsp'">
                     </DIV>
                   </TD>
                 </TR>
               </TBODY>
             </TABLE>
<!-- Javascript代码 -->
<script language="JavaScript">
function find(planId,budgetSystemID){
	
	showSending();//显示正在执行

	 frmV003.planID.value = planId;
	frmV003.budgetSystemID.value = budgetSystemID;
	frmV003.strSuccessPageURL.value="../view/v303.jsp";	//定义操作成功后跳往的页面
	frmV003.strFailPageURL.value="../view/v301.jsp";		//定义失败后跳往的页面
	frmV003.strAction.value="<%=Constant.Actions.NEXTSTEP%>";	//定义操作代码
	frmV003.submit();
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