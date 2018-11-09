<%--
 页面名称 ：v103.jsp
 页面功能 : 分析考核 - 预算项目单位情况查询结果集页面
 作    者 ：liuyang
 日    期 ：
 特殊说明 ：
 修改历史 ：
--%>
<%@ page contentType="text/html;charset=gbk" %>

<!-- 引入需要的类,尽量不用.* -->
<%@ page import="java.sql.Timestamp" %>
<%@ page import="com.iss.itreasury.util.Env" %>
<%@ page import="com.iss.itreasury.util.Log" %>
<%@ page import="com.iss.itreasury.util.Constant" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.Collection" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="com.iss.itreasury.budget.query.paraminfo.QueryBudgetInfo" %>
<%@ page import="com.iss.itreasury.budget.query.resultinfo.QBudgetResultInfo"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtml" %>
<%@ page import="com.iss.itreasury.budget.util.BUDGETConstant" %>
<%@ page import="com.iss.itreasury.budget.util.BUDGETNameRef" %>



<!-- 引入此用户当前回话的Session -->
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<!--引入信息处理页面,此页面会以弹出窗口的形式弹出已经捕捉到的异常-->
<jsp:include page="/ShowMessage.jsp"/>

<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>

<form name=form_1 action="../control/c002.jsp">
<!--引入js文件-->
<script language="JavaScript" src="/webbudget/js/Control.js" ></script>
<script language="JavaScript" src="/webbudget/js/budgetCheck.js" ></script>
<script language="JavaScript" src="/webbudget/js/Check.js"></script>
<script language="JavaScript" src="/webbudget/js/MagnifierSQL.js"></script>

<safety:resources />

<!-- 定义页面控制参数 -->
<input type="hidden" name="strSourcePage" value="">
<input type="hidden" name="strSuccessPageURL" value="">
<input type="hidden" name="strFailPageURL" value="">
<input type="hidden" name="strAction" value="">
<input type="hidden" name="strCtrlPageURL" value="">
<!-- 定义业务逻辑参数 -->

<%
try
{
	//请求检测
	/** 权限检查 **/
	String strTableTitle = "预算体系设置 - 预算项目单位情况查询";
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
	OBHtml.showOBHomeHead(out,sessionMng,"[预算项目单位情况查询]",Constant.YesOrNo.YES);

	/**
	 * 公共参数
	 */
	long operatorId				= sessionMng.m_lUserID;				//当前操作用户ID
	long currencyId				= sessionMng.m_lCurrencyID;			//当前系统使用币种ID
	long officeId				= sessionMng.m_lOfficeID;			//办事处ID	
	/**
	* 定义业务变量
	*/
	%>
<%QueryBudgetInfo objInfo=(QueryBudgetInfo)request.getAttribute("QueryBudgetInfo");%>
<input type=hidden name=budgetSystemID value="<%=objInfo.getBudgetSystemID()%>">
<input type=hidden name=clientID value="<%=objInfo.getClientID()%>">
<input type=hidden name=budgetPeriodID value="<%=objInfo.getBudgetPeriodID()%>">
<input type=hidden name=startDate value="<%=objInfo.getStartDate()%>">
<input type=hidden name=endDate value="<%=objInfo.getEndDate()%>">
<input type=hidden name=strItemId value="<%=objInfo.getStrItemId()%>">
	<br>
	<TABLE width="980" height="10" border=0 class=top>
       <TBODY>
         <TR>
           <TD class=FormTitle height=2 width="100%"><B>		分析考核 - 预算项目单位情况查询</B></TD>
         </TR>
         <TR>
           <TD height=10 vAlign=top width="100%">
             <TABLE width="100%" border=0 align=center cellspacing="1" class=ItemList>
               <TBODY>
                
<tr align="center" class="ItemTitle">
  <td width="6%" height="20" rowspan="2" nowrap>
    <div align="center">单位编号 </div></td>
  <td width="6%" rowspan="2" nowrap>
    <div align="center">单位名称 </div></td>
	<%String strItem=(String)request.getAttribute("strItem");
		String[] itemIds=strItem.split(",");
		for(int i=0;i<itemIds.length;i++){//getItemNameByID
			out.println("<td colspan=4>"+BUDGETNameRef.getItemNameByID(Long.parseLong(itemIds[i]))+"</td>");
		}
	%>
</tr>
<tr align="center" class="ItemTitle">
<%
for(int i=0;i<itemIds.length;i++){%>
  <td nowrap>预算金额</td>
  <td nowrap>执行金额</td>
  <td nowrap>差额</td>
  <td nowrap>执行比例(%)</td>
<%}%>
</tr>
<%
	Collection coll=null;
	coll=(Collection)request.getAttribute("ClientColl");
	Collection iteCol=null;
	iteCol=(Collection)request.getAttribute("itemColl");
	if(coll!=null){
		for(Iterator iter =coll.iterator();iter.hasNext();){
			QBudgetResultInfo info=(QBudgetResultInfo)iter.next();
			out.println("<tr class=ItemBody><td nowrap align=center><a href='../control/c002.jsp?clientID="+info.getClientID()+"&budgetSystemID="+objInfo.getBudgetSystemID()+"&budgetPeriodID="+objInfo.getBudgetPeriodID()+"&startDate="+objInfo.getStartDate()+"&endDate="+objInfo.getEndDate()+"&strAction="+Constant.Actions.MATCHSEARCH+"&strItemId="+objInfo.getStrItemId()+"&SelPrivilege="+objInfo.getStrItemId()+","+"'>"+info.getClientNo()+"</a></td><td nowrap>"+info.getClientName()+"</td>");
			
			for(int i=0;i<itemIds.length;i++){
				String panduan="1";
				int j=0;
				if(iteCol!=null){
					for(Iterator iter1=iteCol.iterator();iter1.hasNext();){
						QBudgetResultInfo iteInfo=(QBudgetResultInfo)iter1.next();
						if(Long.parseLong(itemIds[i])==iteInfo.getItemID()&& info.getClientID()==iteInfo.getClientID()){
							out.println("<td nowrap align=right>"+iteInfo.getBBudgetAmount()+"</td><td nowrap align=right>"+iteInfo.getBExecuteAmout()+"</td>");	out.println("<td nowrap align=right>"+iteInfo.getDiffExecuteAmount()+"</td><td nowrap align=right>"+iteInfo.getExecutePercent()+"</td>");
								panduan="0";
								j++;
						}
					}
				}
				if(panduan.equals("1")){
					out.println("<td align=right>0</td><td align=right>0</td><td align=right>0</td><td align=right>0</td>");
				}
			}
		}
	}
	request.removeAttribute("ClientColl");
	


%>
             </TABLE>
			 <TR>
         <TD width="100%" vAlign=bottom>           <TABLE align=center border=0 width="100%">
             <TBODY>
               <TR>
                   <td><DIV align=right><input name="Submit32" type="button" class="button" onClick="doBack();" value=" 返 回 ">&nbsp;&nbsp;</DIV></td>
               </TR>
             </TBODY>
          </TABLE></TD></TR></TBODY></TABLE></form>
<!-- Javascript代码 -->
<script language="JavaScript">
function doBack(){
	//document.form_1.strSuccessPageURL.value="../view/v102.jsp";	//定义操作成功后跳往的页面
	///document.form_1.strFailPageURL.value="../view/v102.jsp";		//定义失败后跳往的页面
	//document.form_1.strAction.value="doback";
	//form_1.submit();
	history.back();
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
	Log.print(exp.getMessage());
}
%>
<%@ include file="/common/SignValidate.inc" %>