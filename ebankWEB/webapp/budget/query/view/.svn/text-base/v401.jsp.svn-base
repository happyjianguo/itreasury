<%--
 页面名称 ：v401.jsp
 页面功能 : 分析考核 - 预算调整查询页面
 作    者 ：xrli
 日    期 ：
 特殊说明 ：
 修改历史 ：
--%>
<%@ page contentType="text/html;charset=gbk" %>
<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="fs_c" %>
<!-- 引入需要的类,尽量不用.* -->
<%@ page import="java.sql.Timestamp" %>
<%@ page import="com.iss.itreasury.util.Env" %>
<%@ page import="com.iss.itreasury.util.Log" %>
<%@ page import="com.iss.itreasury.util.Constant" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.Collection" %>
<%@ page import="com.iss.itreasury.budget.setting.dataentity.ParameterInfo" %>
<%@ page import="com.iss.itreasury.budget.util.BUDGETHTML" %>
<%@ page import="com.iss.itreasury.budget.util.BUDGETConstant" %>
<%@ page import="com.iss.itreasury.budget.util.BUDGETMagnifier" %>

<%@ page import="com.iss.itreasury.budget.util.BUDGETNameRef" %>
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
<script language="javascript" src="/itreasury/js/jquery-1.3.2.js"></script>

<safety:resources />

<form name="form_1" method="post" action="../control/c401.jsp">
<!-- 定义页面控制参数 -->
<input type="hidden" name="strSourcePage" value="">
<input type="hidden" name="strSuccessPageURL" value="">
<input type="hidden" name="strFailPageURL" value="">
<input type="hidden" name="strAction" value="">
<input type="hidden" name="strCtrlPageURL" value="">
<input type="hidden" name="clientID" value="<%=sessionMng.m_lClientID%>">

<!-- 定义业务逻辑参数 -->

<%
try
{
	//请求检测
	/** 权限检查 **/
	String strTableTitle = "分析考核 - 预算调整查询";
	
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
	 long lOfficeID = sessionMng.m_lOfficeID;
	long lCurrencyID = sessionMng.m_lCurrencyID;
	long operatorId				= sessionMng.m_lUserID;				//当前操作用户ID
		long clientId				= sessionMng.m_lClientID;			//单位ID
	/**
	* 定义业务变量
	*/
	%>
	
	<TABLE width="80%" height="10" border=0 class=top><TBODY><TR><TD class=FormTitle height=2 width="100%"><B>分析考核 - 预算调整查询</B></TD></TR><TR>
	<TD width="100%"  vAlign=bottom><table width="100%" border=0>
<TR><td>
<%
		String strFormNamePCS = "form_1";
		String strCtrlNamePCS = "budgetSystemID";
		String strTitlePCS = "<font color=#CC0000>*</font> 预算体系";
		long systemIDPCS = -1;
		String strSystemNoPCS = "";
		String strFirstTDPCS = "";
		String SecondTDPCS = "";
		String[] sNextControlsPCS = {"budgetPeriodIDCtrl"};
		String strRtnClientNameCtrlPCS = "";
		
	BUDGETMagnifier.createBudgetSystemCtrl(
		out,
		lOfficeID,
		lCurrencyID,
		strFormNamePCS,
		strCtrlNamePCS,
		strTitlePCS,
		systemIDPCS,
		strSystemNoPCS,
		strFirstTDPCS,
		SecondTDPCS,
		sNextControlsPCS,
		strRtnClientNameCtrlPCS);
 %>
</TD>
<TD width="20%" height=23> 预算单位：</TD>
<TD width="30%" height=23><input type="text" name="clientIDCtrl" class="box" maxlength='30' disabled value="<%=BUDGETNameRef.getClientNameByID(clientId)%>"></TD>


</tr><tr><td>
<%
		String strFormNamePCS2 = "form_1";
		String strCtrlNamePCS2 = "budgetPeriodID";
		String strTitlePCS2 = "<font color=#CC0000>*</font> 预算周期";
		long lClientIDPCS2 = -1;
		String strClientNoPCS2 = "";
		String strFirstTDPCS2 = "";
		String SecondTDPCS2 = "";
		String[] sNextControlsPCS2 = {"startDate"};
		String strRtnClientNameCtrlPCS2 = "";
		
	BUDGETMagnifier.createBudgetPeriodCtrl(
		out,
		lOfficeID,
		lCurrencyID,
		strFormNamePCS2,
		strCtrlNamePCS2,
		strTitlePCS2,
		lClientIDPCS2,
		strClientNoPCS2,
		strFirstTDPCS2,
		SecondTDPCS2,
		sNextControlsPCS2,
		strRtnClientNameCtrlPCS2);
 %>
</TD></tr>


<tr>
<td ><font color=red>*</font> 预算区间：从</td>
<td>
	<fs_c:calendar 
          	    name="startDate"
	          	value="" 
	          	properties="javascript:getEndDate();nextfield='endDate';" 
	          	size="20"/>
	          	<script>
	          		$('#startDate').blur(
	          			function (){
	          				getEndDate();
	          			}
	          		);
	          	</script>
	          	<!-- 
<input type="text" name="startDate" size="20" maxlength="30" class="box" onFocus="javascript:getEndDate();nextfield='endDate';" onblur="javascript:getEndDate();">&nbsp;
<A href="javascript:show_calendar('form_1.startDate');" onMouseOut="window.status='';return true;"onMouseOver="window.status='Date Picker';return true;"><IMG border="0" height="16"src="/websett/image/calendar.gif" width="17"></A>
 --></td>
<td >到：</td>
<td><input type="text" name="endDate" size="20" maxlength="30" class="box" onFocus="nextfield='submitfunction';" readonly>&nbsp;
</td>
</TR>
</TABLE></TD>
         </TR>
         <TR>
           <TD height=10 vAlign=top width="100%">
             <TABLE align=center height="15" width="97%">
               <TBODY>
                 <TR>
                   <TD colSpan='6' height="10"><DIV align=right>
				   <input class="button" name="search" type="button" value=" 查 找 " onClick="javascript:doSearch();">

                   </DIV>
                   </TD>
                 </TR>
               </TBODY>
             </TABLE>
		</td>
         </TR>
       </TBODY>
    </TABLE>
     </form>

<!-- Javascript代码 -->
<script language="JavaScript">
//设定页面焦点

//设定form名称
setFormName("form_1"); 
firstFocus(document.form_1.budgetSystemIDCtrl);
//设定回车自动执行动作
//setSubmitFunction("doSearch()");

//业务操作
function doSearch()
{
	
		if (!validateFields(form_1)) return;	
		showSending();//显示正在执行
		document.form_1.strSuccessPageURL.value="../view/v402.jsp";	//定义操作成功后跳往的页面
		document.form_1.strFailPageURL.value="../view/v402.jsp";		//定义失败后跳往的页面
		document.form_1.strAction.value="<%=Constant.Actions.MATCHSEARCH%>";	//定义操作代码

		form_1.submit();
}
function allFields()
{
		this.a0 = new Array("budgetSystemID"," 预算体系","magnifier",1);
		this.a1 = new Array("clientID"," 预算单位","magnifier",1);
		this.a2 = new Array("budgetPeriodID"," 预算周期","magnifier",1);
		this.a3 = new Array("startDate"," 预算区间开始日期","date",1);		
		
}
function getEndDate()
{
var strStartDate = form_1.startDate.value;
if (strStartDate != "" && form_1.BudgetPeriodType.value != "-1")
	{
		var startDate = new Date(strStartDate.substring(0,4),new Number(strStartDate.substring(5,7)),new Number(strStartDate.substring(8,10)));
		var newDate = startDate;
		var periodType = form_1.BudgetPeriodType.value;
		if (periodType == <%=BUDGETConstant.BudgetPeriod.Y%>)
		{
			newDate.setYear(startDate.getYear()+1); 
		}
		else if (periodType == <%=BUDGETConstant.BudgetPeriod.H%>)
		{
			newDate.setMonth(startDate.getMonth()+6); 
		}
		else if (periodType == <%=BUDGETConstant.BudgetPeriod.Q%>)
		{
			newDate.setMonth(startDate.getMonth()+3); 
		}
		else if (periodType == <%=BUDGETConstant.BudgetPeriod.M%>)
		{
			newDate.setMonth(startDate.getMonth()+1); 
		}
		else if (periodType == <%=BUDGETConstant.BudgetPeriod.T%>)
		{
			newDate.setDate(startDate.getDate()+10); 
		}
		else if (periodType == <%=BUDGETConstant.BudgetPeriod.W%>)
		{
			newDate.setDate(startDate.getDate()+7); 
		}
		else if (periodType == <%=BUDGETConstant.BudgetPeriod.D%>)
		{
			newDate.setDate(startDate.getDate()+1); 
		}
		else if (periodType == <%=BUDGETConstant.BudgetPeriod.X1%>)
		{
			newDate.setDate(startDate.getDate()+14); 
		}
		else if (periodType == <%=BUDGETConstant.BudgetPeriod.X2%>)
		{
			newDate.setDate(startDate.getDate()+ new Number(form_1.periodDays.value)); 
		}
		var year = newDate.getYear();
		var month = newDate.getMonth();
		var day = newDate.getDate();
		if (month==0){month=12;year--;}
		if (month<10)month="0"+month;
		if (day<10)day="0"+day;
		form_1.endDate.value = year + "-" + month + "-" + day;
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
	Log.print(exp.getMessage());
}
%>
<%@ include file="/common/SignValidate.inc" %>