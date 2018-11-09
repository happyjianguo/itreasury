<%--
 页面名称 ：v303.jsp
 页面功能 : 显示详细模版查询结果页面
 作    者 ：leiliu
 日    期 ：2004-08-08
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
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.Collection" %>
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.iss.itreasury.budget.templet.dataentity.*" %>
<%@ page import="com.iss.itreasury.budget.util.*" %>
<%@ page import="com.iss.itreasury.budget.util.BUDGETConstant" %>
<%@ page import="com.iss.itreasury.budget.setting.bizlogic.BudgetParameterOperation" %>
<%@ page import="com.iss.itreasury.budget.constitute.dataentity.BudgetPlanInfo" %>
<%@ page import="com.iss.itreasury.budget.query.paraminfo.QueryBudgetInfo" %>
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

	
	String[] aryHeadDateString = null;
	Vector vTemplate = null;
	long lLevelCount = -1;
	String versionNo = "";
	Vector v = new Vector();
	String strTmp = null;
	
	QueryBudgetInfo info = (QueryBudgetInfo)request.getAttribute("QueryBudgetInfo");
	vTemplate = (Vector)request.getAttribute("srcheaResult");
	strTmp = (String)request.getAttribute("versionNo");
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
	
	long[] aryLevelStatus = {1};//第一级默认显示

%>
	<jsp:include page="/ShowMessage.jsp"/>
	
	<form name="frmV002" method="post" action="../control/c301.jsp">
		<input type="hidden" name="strSourcePage" value="">
		<input type="hidden" name="strSuccessPageURL" value="">
		<input type="hidden" name="strFailPageURL" value="">
		<input type="hidden" name="strAction" value="">
		<input type="hidden" name="strCtrlPageURL" value="">
		<input type="hidden" name="hdnLevelCount" value="<%=lLevelCount%>">
		<input type="hidden" name="budgetSystemID" value="<%=info.getBudgetSystemID()%>">
		<input type="hidden" name="budgetPeriodID" value="<%=info.getBudgetPeriodID()%>">
		<input type="hidden" name="startDate" value="<%=info.getStartDate()%>">
		<input type="hidden" name="endDate" value="<%=info.getEndDate()%>">
		<input type="hidden" name="clientID" value="<%=info.getClientID()%>">
		<input type="hidden" name="showColumns" value="<%=tmp.length%>">
			
		
		<table width="100%" border="0" class="top" height="100">
			<tr>
				<td height="2" class="FormTitle" width="100%" colspan="2"><b>分析考核 - 预算版本查询</b>
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
					<table width="97%" border="0" cellspacing="2" cellpadding="2" height="15" align="center">
						<tr>
							<td height="19" colspan="3">
								<div align="right">
									<input type="button" name="back" value=" 返 回 " class="button" onClick="javascript:doSearch();">
								</div>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</form>


<script language="javascript">

function compute(form)
{
	if (<%=tmp.length%>==1)
	{
		alert("请先选择系数所基于的列");
		return false;
	}
	if (form.quotiety.value == "")
	{
		alert("请填写计算系数");
		form.quotiety.focus();
		return false;
	}
	else
	{
		if (isNaN(form.quotiety.value))
		{
			alert("计算系数必须为数字");
			form.quotiety.focus();
			return false;
		}
		else
		{
			if (form.quotiety.value <= 0)
			{
				alert("计算系数必须大于0");
				form.quotiety.focus();
				return false;
			}
		}
	}
	for (var i=0;i<form.hdnItemNo.length;i++)
	{
		eval("form.txtAmount1_"+form.hdnItemNo[i].value+".value = (form.txtAmount0_"+form.hdnItemNo[i].value+".value * form.quotiety.value)");
	}

}
function doSearch()
{	
	showSending();//显示正在执行
	frmV002.strSuccessPageURL.value="../view/v302.jsp";	//定义操作成功后跳往的页面
	frmV002.strFailPageURL.value="../view/v301.jsp";		//定义失败后跳往的页面
	frmV002.strAction.value="<%=Constant.Actions.MATCHSEARCH%>";	//定义操作代码
	frmV002.submit();
}




function showColumn()
{
	if (frmV002.show.checked==true)
	{
		for (var i=0;i<frmV002.showcolumn.length;i++)
		{
			if (frmV002.showcolumn[i].checked==true)
			{
				dosubmit(frmV002);
				break;
			}
		}
	}
}
function dosubmit(form_1)
{
	showSending();
	form_1.action="../control/c001.jsp";
	form_1.strSuccessPageURL.value="../view/v002.jsp";	//定义操作成功后跳往的页面
	form_1.strFailPageURL.value="../view/v001.jsp";		//定义失败后跳往的页面
	form_1.strAction.value="<%=Constant.Actions.NEXTSTEP%>";	//定义操作代码
	form_1.submit();
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