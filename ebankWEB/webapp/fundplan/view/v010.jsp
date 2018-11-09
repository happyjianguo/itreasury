<!--
/**
 * 页面名称 ：v010.jsp
 * 页面功能 : 资金计划-查找-超链接后得到的详情的页面； v009.jsp ————>c009.jsp————> 当前页面
 * 作    者 ：ylguo
 * 日    期 ：2008-12-04
 * 		
 * 修改历史 ：
 */
-->
<%@ page contentType="text/html;charset=gbk" %>
<%@ page import="java.util.Collection,
				 java.util.Date,
				 com.iss.itreasury.util.*,
                 java.util.Iterator,
                 com.iss.itreasury.ebank.util.SessionOB,
                 com.iss.itreasury.ebank.util.OBHtml,
                 com.iss.itreasury.ebank.util.OBConstant,
                 com.iss.itreasury.util.Constant,
                 java.sql.Timestamp           
                 " 
                 %>
<%@ page import="com.iss.itreasury.ebank.util.OBConstant.ListType" %>
<%@ page import="com.iss.itreasury.util.Constant.ModuleType" %>
<%@ page import="com.iss.itreasury.util.SessionMng" %>
<%@ page import="com.iss.itreasury.ebank.fundplan.dataentity.CapitalPlanInfo"%>
		
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<%
String strTableTitle = null;
String strContext = request.getContextPath();
try
{       //请求检测是否登录
		if (sessionMng.isLogin() == false) {
			OBHtml.showCommonMessage(out, sessionMng, strTableTitle, "", 1,
			"Gen_E002");
			out.flush();
			return;
		}
		/* 判断用户是否有权限 */
		if (sessionMng.hasRight(request) == false) {
			out.println(sessionMng.hasRight(request));
			OBHtml.showCommonMessage(out, sessionMng, strTableTitle, "", 1,
			"Gen_E003");
			out.flush();
			return;
		}
		
		CapitalPlanInfo planInfo = (CapitalPlanInfo)request.getAttribute("capitalPlanInfo");
		boolean isEditEnabled = false; //可编辑
		//显示文件头
        OBHtml.showOBHomeHead(out, sessionMng, strTableTitle, Constant.YesOrNo.YES);
%>

<jsp:include page="/ShowMessage.jsp"/>
<!-- 页面使用js声明 -->
<script language="JavaScript" src="/webob/js/Control.js"></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>
<script language="JavaScript" src="/webob/js/date-picker.js"></script>
<script language="JavaScript" src="/webob/js/MagnifierSQL.js"></script>
<script language="JavaScript" src="/webob/js/fundplan.js"></script>
<%@ taglib uri="/WEB-INF/tlds/iss-fundplan-tags.tld" prefix="fundplan"%>
<safety:resources />
<head>
	<link rel="stylesheet" href="/webob/css/style.css" type="text/css">
</head>

<!-- 页面使用js声明结束 -->
<form method="post" name="frm001">
<input name="strAction" type="hidden" value="save">
<input name="p_SuccessPageURL" type="hidden" value="../view/v010.jsp">
<input name="p_FailPageURL" type="hidden" value="../view/v010.jsp">
<input type="hidden" name="officeId" value="<%= sessionMng.m_lOfficeID%>">
<!-- 币种，客户id，客户名称，客户编号 -->
<input type="hidden" name="currencyId" value="<%= sessionMng.m_lCurrencyID %>">
<input type="hidden" name="clientId" value="<%= sessionMng.m_lClientID%>">
<input type="hidden" name="clientName" value="<%= sessionMng.m_strClientName %>">
<!-- 开始日期和结束日期 -->
<input type="hidden" name="startDate" value="<%=DataFormat.formatDate(planInfo.getStartdate(), DataFormat.FMT_DATE_YYYYMMDD)%>">
<input type="hidden" name="endDate" value="<%=DataFormat.formatDate(planInfo.getEnddate(), DataFormat.FMT_DATE_YYYYMMDD) %>">
<input type="hidden" name="cpCode" value="<%=planInfo.getCpCode() %>">
<input type="hidden" name="capitalplanId" value="<%=planInfo.getId() %>">
<input type="hidden" name="modelId" value="<%=planInfo.getModelId() %>">
<table cellpadding="0" cellspacing="0" class="title_top">
	  <tr>
	    <td height="24">
		    <table cellspacing="0" cellpadding="0" class=title_Top1>
				<TR>
			       <td class=title><span class="txt_til2">资金计划详情</span></td>
			       <td class=title_right><a class=img_title></td>
				</TR>
			</TABLE>
		<br/>
     <table width=100% border="0" cellspacing="0" cellpadding="0" class=normal id="table1" >
        <tr>
          <td colspan="4" height="1"></td>
        </tr>
        <tr>
		  <td width="4" height="25" ></td>
          <td width="100" align="left">申报单位</td>
          <td width="586">
		  	<input type="text" class="box" name="clientName" size="32" value="<%=sessionMng.m_strClientName%>" disabled="disabled">
          </td>
          <td width="100" class="MsoNormal"></td>
        </tr>
        <tr>
		  <td width="4" height="25" ></td>
          <td width="100" align="left">申报范围</td>
          <td width="586">
		  		<input type="text" class="box" name="dateFrom" size="16" value="<%=DataFormat.formatDate(planInfo.getStartdate(), DataFormat.FMT_DATE_YYYYMMDD) %>" disabled="disabled">
				&nbsp;至&nbsp;
				<input type="text" class="box" name="dateTo" size="16" value="<%=DataFormat.formatDate(planInfo.getEnddate(), DataFormat.FMT_DATE_YYYYMMDD) %>" disabled="disabled">
          </td>
          <td width="100" class="MsoNormal"></td>
        </tr>
        <tr>  
		  		<td width="4" height="25" ></td>
          <td width="100" align="left">申报编号</td>
          <td width="586">
		  		<input type="text" class="box" name="cpCode" size="20" value="<%=planInfo.getCpCode()%>" disabled="disabled">
          </td>
          <td width="100" class="MsoNormal"></td>
        </tr>        
        
        <tr>
		  <td width="4" height="25" ></td>
          <td width="100" align="left">&nbsp;</td>
          <td width="686" colspan="2" align="right">
		  		单位：万元
          </td>
        </tr>
		
        <tr>
		  <td width="4" height="25"></td>
          <td width="786" colspan="3">
          	<!-- 项目显示组件 -->
          	<fundplan:FundPlanWidget capitalplanId="<%=planInfo.getId() %>" 
          							 disabled="<%=!isEditEnabled%>"
          							 modelId="<%=planInfo.getModelId()%>"
          							 type="submit"
          							 office="<%=sessionMng.m_lOfficeID%>"
          							 currency="<%=sessionMng.m_lCurrencyID%>"
          							 clientId="<%=sessionMng.m_lClientID%>"
          							 dateFrom="<%=DataFormat.formatDate(planInfo.getStartdate(), DataFormat.FMT_DATE_YYYYMMDD)%>"
          							 dateTo="<%=DataFormat.formatDate(planInfo.getEnddate(), DataFormat.FMT_DATE_YYYYMMDD)%>" />
		  </td>
        </tr>
</table> 


          
      <br>
      <table width=100% border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td align="right">
            	<input class="button1" name="back" type="button" value=" 返 回 " onclick="toBack()">&nbsp;&nbsp;
          </td>
        </tr>
      </table>
</td>
</tr>
</table>
</form>
<script language="javascript">
	firstFocus(frm001.back);
    setFormName("frm001");
    //setSubmitFunction("toBack()");
	function toBack(){
		window.history.back();
	}
	
</script>
<%
	/**
	* 显示文件尾
	*/
	OBHtml.showOBHomeEnd(out);
%>

<%
}
catch(IException ie)
{
	
	ie.printStackTrace();
	out.flush();
	return; 
}
%>	

<%@ include file="/common/SignValidate.inc" %>