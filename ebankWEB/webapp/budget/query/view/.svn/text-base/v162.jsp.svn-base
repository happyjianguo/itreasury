<%--
 页面名称 ：v162.jsp
 页面功能 : 银行滞留比例分析查询结果集页面
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
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="com.iss.itreasury.budget.setting.dataentity.BudgetItemPrivilegeInfo"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtml" %>
<%@ page import="com.iss.itreasury.budget.query.resultinfo.QBudgetResultInfo" %>
<%@ page import="com.iss.itreasury.budget.util.BUDGETConstant" %>
<%@ page import="com.iss.itreasury.budget.util.BUDGETNameRef" %>


<!-- 引入此用户当前回话的Session -->
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<!--引入信息处理页面,此页面会以弹出窗口的形式弹出已经捕捉到的异常-->
<jsp:include page="/ShowMessage.jsp"/>

<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>

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
	String strTableTitle = "系统设置 - 银行滞留比例分析";
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
	OBHtml.showOBHomeHead(out,sessionMng,"[银行滞留比例分析]",Constant.YesOrNo.YES);
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
	<br>
	<TABLE width="100%" height="10" border=0 class=top>
       <TBODY>
         <TR>
           <TD class=FormTitle height=2 width="100%"><B>		分析考核 - 银行滞留比例分析</B></TD>
         </TR>
         <TR>
           <TR>
           <TD height=10 vAlign=top width="100%">
             <table width="100%" border="0" cellspacing="1" class="ItemList">
<tr class="ItemTitle">
<td height="20"> <div align="center">单位编号 </div></td>
<td> <div align="center">单位名称</div></td>
<td> <div align="center">预算金额</div></td>
<td> <div align="center">内转外金额</div></td>
<td> <div align="center">对外支付金额</div></td>
<td><div align="center">资金集中度比例(%)</div></td>
<td><div align="center">银行滞留比例(%)</div></td>
<td> <div align="center">预算滞留比例(%)</div></td>
</tr>
<%
	Collection tableColl=null;
	tableColl=(Collection)request.getAttribute("tableColl");
	for(Iterator iter1=tableColl.iterator();iter1.hasNext();){
		QBudgetResultInfo iteInfo=(QBudgetResultInfo)iter1.next();
		
	
		out.println("<tr class=ItemBody><td align=center>"+BUDGETNameRef.getClientCodeByID(iteInfo.getClientID())+"</td>");
		out.println("<td>"+BUDGETNameRef.getClientNameByID(iteInfo.getClientID())+"</td>");
		out.println("<td align=right>"+iteInfo.getBBudgetAmount()+"</td><td align=right>"+iteInfo.getInAmount()+"</td>");	
		out.println("<td align=right>"+iteInfo.getOutAmount()+"</td><td align=right>"+iteInfo.getCapitalPercent()+"</td><td align=right>"+iteInfo.getBankResortPercent()+"</td>");
		out.println("<td align=right>"+iteInfo.getBudgetResortPercent()+"</td></tr>");
	}
	request.removeAttribute("tableColl");
%>
             </TABLE>
			 <TR>
         <TD width="100%" vAlign=bottom>           <TABLE align=center border=0 width="100%">
             <TBODY>
               <TR>
                   <td><DIV align=right><input name="Submit32" type="button" class="button" onClick="location.href='../view/v161.jsp';" value=" 返 回 ">&nbsp;&nbsp;</DIV></td>
               </TR>
             </TBODY>
           </TABLE>
           </TD>
         </TR>
       </TBODY>
    </TABLE>
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