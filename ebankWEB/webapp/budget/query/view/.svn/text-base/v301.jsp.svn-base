<%--
 页面名称 ：v301.jsp
 页面功能 : 预算版本查询
 作    者 ：leiliu
 日    期 ：2005-08-04
 特殊说明 ：  
 修改历史 ：
--%>
<%@ page contentType="text/html;charset=gbk" %>
<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="fs_c" %>
<%@ page import="java.sql.Timestamp" %>
<%@ page import="com.iss.itreasury.util.Env" %>
<%@ page import="com.iss.itreasury.util.Log" %>
<%@ page import="com.iss.itreasury.util.Constant" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.Collection" %>
<%@ page import="com.iss.itreasury.util.DataFormat" %>
<%@ page import="com.iss.itreasury.ebank.util.OBHtml" %>
<%@ page import="com.iss.itreasury.budget.util.BUDGETMagnifier" %>
<%@ page import="com.iss.itreasury.budget.util.BUDGETConstant" %>
<%@ page import="com.iss.itreasury.budget.util.BUDGETNameRef" %>
<%@ page import="com.iss.itreasury.budget.query.paraminfo.QueryBudgetInfo" %> 
<%@ page import="com.iss.itreasury.ebank.util.OBHtml" %>

<!-- 引入此用户当前回话的Session -->
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

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

	/**
	 * 公共参数
	 */
	long operatorId				= sessionMng.m_lUserID;				//当前操作用户ID
	long currencyId				= sessionMng.m_lCurrencyID;			//当前系统使用币种ID
	long officeId				= sessionMng.m_lOfficeID;			//办事处ID
	long clientId				= sessionMng.m_lClientID;			//单位ID

	
	/**
	* 定义业务变量
	*/
	long budgetSystemID = -1;
	long budgetPeriodID = -1;
	Timestamp startDate = null;
	Timestamp endDate = null;
	String versionNo="";
	
	/** 取得dataentity,给变量赋值 */
	QueryBudgetInfo info = (QueryBudgetInfo)request.getAttribute("QueryBudgetInfo");
	if (info != null)
	{
		budgetSystemID = info.getBudgetSystemID();
		budgetPeriodID = info.getBudgetPeriodID();
		startDate = info.getStartDate();
		endDate = info.getEndDate();
		versionNo = info.getVersionNo();
	}
%>
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

<!--form 开始-->
<form name="form_1" method=post action="../control/c301.jsp"  >
<!-- 定义页面控制参数 -->
<input type="hidden" name="strSourcePage" value="">
<input type="hidden" name="strSuccessPageURL" value="">
<input type="hidden" name="strFailPageURL" value="">
<input type="hidden" name="strAction" value="">
<input type="hidden" name="strCtrlPageURL" value="">
<!-- 定义业务逻辑参数 -->
<input type=hidden name=clientID value=<%=clientId%>>

<TABLE width="100%" height="10" border=0 class=top>
       <TBODY>
         <TR>
           <TD class=FormTitle height=2 width="100%"><B>分析考核 - 预算版本查询</B></TD>
         </TR>
         <TR>
         <TD width="100%" vAlign=bottom>           <TABLE width="100%" border=0 align=center>
<TR>
<%
		String strFormNamePCS = "form_1";
		String strCtrlNamePCS = "budgetSystemID";
		String strTitlePCS = "<font color=red>*</font> 预算体系";
		long systemIDPCS = -1;
		String strSystemNoPCS = "";
		String strFirstTDPCS = "";
		String SecondTDPCS = "";
		String[] sNextControlsPCS = {"budgetPeriodIDCtrl"};
		String strRtnClientNameCtrlPCS = "";
		
	BUDGETMagnifier.createBudgetSystemCtrl(
		out,
		officeId,
		currencyId,
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

<TD width="20%" height=23> 预算单位：</TD>
<TD width="30%" height=23><input type="text" name="lClientIDCtrl2" class="box" maxlength='30' disabled value="<%=BUDGETNameRef.getClientNameByID(clientId)%>"></TD>
</TR>
<TR>
<%
		String strFormNamePCS2 = "form_1";
		String strCtrlNamePCS2 = "budgetPeriodID";
		String strTitlePCS2 = "预算周期";
		long lClientIDPCS2 = -1;
		String strClientNoPCS2 = "";
		String strFirstTDPCS2 = "";
		String SecondTDPCS2 = "";
		String[] sNextControlsPCS2 = {"startDate"};
		String strRtnClientNameCtrlPCS2 = "";
		
	BUDGETMagnifier.createBudgetPeriodCtrl(
		out,
		officeId,
		currencyId,
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
 <td ></td>
<td>
</td>
</TR>
<tr>
<td > 预算区间：从</td>
<td>
 		<fs_c:calendar 
          	    name="startDate"
	          	value="" 
	          	properties="javascript:getEndDate();nextfield='submitfunction';" 
	          	size="20"/>
	        <script type="text/javascript">
	        	$('#startDate').blur(function(){
	        		getEndDate();
	        	});
	        </script>
	          	<!-- 
<input type="text" name="startDate" size="20" maxlength="30" class="box" onFocus="javascript:getEndDate();nextfield='submitfunction';" onblur="javascript:getEndDate();">&nbsp;
<A href="javascript:show_calendar('form_1.startDate');" onMouseOut="window.status='';return true;"onMouseOver="window.status='Date Picker';return true;"><IMG border="0" height="16"src="/websett/image/calendar.gif" width="17"></A>
 --></td>
<td >到：</td>
<td><input type="text" name="endDate" size="20" maxlength="30" class="box" readonly>&nbsp;
</td>
</TR>

             <TBODY>
             </TBODY>
           </TABLE></TD>
         </TR>
         <TR>
           <TD height=10 vAlign=top width="100%">
             <TABLE align=center height="15" width="97%">
               <TBODY>
                 <TR>
                   <TD colSpan='6' height="10"><DIV align=right>
				  <input class="button" name="search" type="button" value=" 查 找 " onClick="javascript:doSearch();">
                     <!--input class="button" name="Submit322" type="button" value=" 导 入 " onClick="location.href='2预算编制1.htm';"-->
                     					 
                   </DIV>
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
//设定页面焦点
firstFocus(document.form_1.budgetSystemIDCtrl);
//设定form名称
setFormName("form_1"); 
//设定回车自动执行动作
//setSubmitFunction("doSearch()");

//业务操作
function doSearch()
{
	 if (!validateFields(form_1)) return;
		beginDT = document.form_1.startDate.value ;
        endDT = document.form_1.endDate.value ;
		if( beginDT.length != 0 && endDT.length != 0 &&  beginDT > endDT){
			alert('预算区间－从不能大于－到!');
			document.form_1.endDate.focus();
			return false;
		} 

	showSending();//显示正在执行
	form_1.strSuccessPageURL.value="../view/v302.jsp";	//定义操作成功后跳往的页面
	form_1.strFailPageURL.value="../view/v301.jsp";		//定义失败后跳往的页面
	form_1.strAction.value="<%=Constant.Actions.MATCHSEARCH%>";	//定义操作代码
	form_1.submit();
}

//Form里需要检查的输入框或放大镜
function allFields()
{			
		this.aa = new Array("budgetSystemID","预算体系","magnifier",1);	
		
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