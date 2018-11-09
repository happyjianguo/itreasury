<%--
 页面名称 ：v403.jsp
 页面功能 : 预算调整查询
 作    者 ：xrli
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
<%@ page import="java.util.Vector" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="com.iss.itreasury.budget.setting.dataentity.BudgetItemPrivilegeInfo"%>
<%@ page import="com.iss.itreasury.budget.util.BUDGETHTML" %>
<%@ page import="com.iss.itreasury.budget.query.resultinfo.QBudgetResultInfo" %>
<%@ page import="com.iss.itreasury.budget.util.BUDGETConstant" %>
<%@ page import="com.iss.itreasury.budget.util.BUDGETNameRef" %>
<%@ page import="com.iss.itreasury.budget.constitute.dataentity.BudgetPlanInfo" %>
<%@ page import="com.iss.itreasury.ebank.util.OBHtml" %>

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
	String strTableTitle = "系统查询 - 预算调整查询";
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
	OBHtml.showOBHomeHead(out,sessionMng,"[预算调整查询]",Constant.YesOrNo.YES);


	/**
	 * 公共参数
	 */
	long operatorId				= sessionMng.m_lUserID;				//当前操作用户ID
	long currencyId				= sessionMng.m_lCurrencyID;			//当前系统使用币种ID
	long officeId				= sessionMng.m_lOfficeID;			//办事处ID	
	/**
	* 定义业务变量
	*/
	String[] aryHeadDateString = null;
	Vector vTemplate = null;
	long lLevelCount = -1;
	long adjustNum = 0;
	String versionNo = "";
	Vector v = new Vector();
	String strTmp = null;

	vTemplate = (Vector)request.getAttribute("searchResult");
	
	int adNum = 0;
	strTmp = (String)request.getAttribute("adjustNum");

	if(strTmp!=null)
	{	
	 adNum = Integer.valueOf(strTmp).intValue();
	adjustNum = Long.parseLong(strTmp);	
	}
	strTmp = (String)request.getAttribute("maxLevel");
	lLevelCount = Long.parseLong(strTmp);

	
	


 	aryHeadDateString = new String[adNum+5];

	
	aryHeadDateString[0]=new String("原始金额");

	for (int i=1;i<=adNum;i++)
	{	
		aryHeadDateString[i] = new String("第"+ i + "次调整金额") ;
	}
	
	
	aryHeadDateString[adNum+1]=new String("实际预算金额");
	
	aryHeadDateString[adNum+2]=new String("实际执行金额");
	aryHeadDateString[adNum+3]=new String("执行比例(%)");
	aryHeadDateString[adNum+4]=new String("调整比例(%)");	

	
	
	long[] aryLevelStatus = {1};//第一级默认显示
	%>

	<TABLE width="95%" height="10" border=0 class=top>
       <TBODY>
         <TR>
           <TD class=FormTitle height=2 width="100%"><B>		分析考核 - 预算调整查询</B></TD>
         </TR>
         <TR>
           <TR>
           <TD height=10 vAlign=top width="100%"><input type="hidden" name="hdnLevelCount" value="<%=lLevelCount%>">
             <table width="100%" border="0" cellspacing="1" class="ItemList"><tr>
			 <td height="5" width="100%" valign="bottom" colspan="2">
				<hr>
<%
	//显示模版树
	BUDGETHTML.showBudgetTemplate(out,aryHeadDateString,vTemplate,lLevelCount,aryLevelStatus);
%>
</td>
</tr>
             </TABLE>
			 <TR>
         <TD width="100%" vAlign=bottom>           <TABLE align=center border=0 width="100%">
             <TBODY>
               <TR>
                   <td><DIV align=right>
				   <!--
				   <input name="Submit32" type="button" class="button" onClick="location.href='../view/v401.jsp';" value=" 返 回 ">&nbsp;&nbsp;</td>
				   -->
				   <input name="Submit32" type="button" class="button" onClick="doBack();" value=" 返 回 ">&nbsp;&nbsp;</DIV></td>
               </TR>
             </TBODY>
           </TABLE></TD>
         </TR>
       </TBODY>
    </TABLE>
	<script language="JavaScript">
function doBack(){
	
	//document.form_1.strSuccessPageURL.value="../view/v102.jsp";	//定义操作成功后跳往的页面
	//document.form_1.strFailPageURL.value="../view/v102.jsp";		//定义失败后跳往的页面
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