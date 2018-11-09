<!--
/**
 * 页面名称 ：v004.jsp
 * 页面功能 : 资金计划-申报-查找-超链接后得到的申报详情的页面； v003.jsp ————> 当前页面
 * 作    者 ：ylguo
 * 日    期 ：2008-10-24
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
boolean isAutoCheck = false;
try
{      
		if(sessionMng.isLogin() == false)  //请求检测是否登录
		{
			OBHtml.showCommonMessage(out, sessionMng, strTableTitle, "", 1,"Gen_E002");
			out.flush();
			return;
		}
		
		if(sessionMng.hasRight(request) == false)/* 判断用户是否有权限 */
		{
			out.println(sessionMng.hasRight(request));
			OBHtml.showCommonMessage(out, sessionMng, strTableTitle, "", 1,"Gen_E003");
			out.flush();
			return;
		}
		Boolean isAutoCheckTemp = (Boolean)request.getAttribute("isAutoCheck");
		if(isAutoCheckTemp != null)
		{
		   isAutoCheck = isAutoCheckTemp.booleanValue();
		}
		
		
		CapitalPlanInfo planInfo = (CapitalPlanInfo)request.getAttribute("capitalPlanInfo");
		if(planInfo == null)planInfo = new CapitalPlanInfo();
		
		
		
		boolean isEditEnabled = false; //可编辑
		
		boolean isDeleteEnabled = false; //可删除
		
		if(planInfo.getInputuserId()==-1) //没有录入人,为新增
		{
			isEditEnabled = true;
			isDeleteEnabled = false;
		}
		else if(planInfo.getInputuserId()>0 && planInfo.getInputuserId()==sessionMng.m_lUserID && planInfo.getStatusId()==1)
		{
			
			isEditEnabled = true;//录入人修改或删除
			isDeleteEnabled = true;
			
		}
		else
		{
			isEditEnabled = false;
			isDeleteEnabled = false;
		}

		
        OBHtml.showOBHomeHead(out, sessionMng, strTableTitle, Constant.YesOrNo.YES);//显示文件头
%>

<jsp:include page="/ShowMessage.jsp"/>
<!-- 页面使用js声明 -->
<link rel="stylesheet" href="/webob/css/style.css" type="text/css">
<script language="JavaScript" src="/webob/js/Control.js"></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>
<script language="JavaScript" src="/webob/js/date-picker.js"></script>
<script language="JavaScript" src="/webob/js/MagnifierSQL.js"></script>
<script language="JavaScript" src="/webob/js/fundplan.js"></script>
<%@ taglib uri="/WEB-INF/tlds/iss-fundplan-tags.tld" prefix="fundplan"%>
<safety:resources />

<!-- 页面使用js声明结束 -->
<form method="post" name="frm001">
<input name="strAction" type="hidden" value="save">
<input name="p_SuccessPageURL" type="hidden" value="../view/v001.jsp">
<input name="p_FailPageURL" type="hidden" value="../view/v001.jsp">
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
<input name="_isChinese" type="hidden" value="true"><!-- add by dwj  -->
<table cellpadding="0" cellspacing="0" class="title_top">
			  <tr>
			    <td height="22">
				    <table cellspacing="0" cellpadding="0" class=title_Top1>
						<TR>
					       <td class=title><span class="txt_til2">资金计划申报</span></td>
					       <td class=title_right><a class=img_title></td>
						</TR>
					</TABLE>     
	<br/>

     <table width="100%" border="0" cellspacing="0" cellpadding="0" class=normal id="table1" align="">
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
        <tr><td>&nbsp;</td></tr>
        <tr>
          <td colspan=3></td>
        	<td>
          <div align="right">
          <%
          	if(isEditEnabled){
          	%>
          	  <%if(isAutoCheck){%>
          	    <input class="button1" name="commit" type="button" value=" 保 存 " onkeydown="if(event.keyCode==13) document.frm001.commit.click()" onclick="toCommit();">
          	  <%}else{%>
          	    <input class="button1" name="save" type="button" value=" 保 存 " onkeydown="if(event.keyCode==13) document.frm001.save.click()" onclick="toSave()">&nbsp;&nbsp;          	
          	  <%}%>
          	<% 
          	}
          	if(isDeleteEnabled)
          	{
           %>
			<input class="button1" name="delete" type="button" value=" 删 除 " onclick="toDel()">&nbsp;&nbsp;
		  <%
		  	}
		   %>
			<input class="button1" name="back" type="button" value=" 返 回 " onclick="toBack()">&nbsp;&nbsp;
          </div>
          </td>
        </tr>
        <tr><td>&nbsp;</td></tr>
      </table>
</td>
</tr>
</table>
</form>
<script language="javascript">
function toSave()
{
   var obj = window.document.forms[0];
   for(var i =0; i<obj.length; i++)
   {
	   var sss = obj.elements[i].style.cssText;
	   if(sss=="BACKGROUND-COLOR: #7e7774")
	   {
		   alert("备注字数超额，请将灰色的备注删减后再保存！");
		   return false;
	   }
   }
   if(confirm("确认保存吗？"))
   {
		showSending();
		frm001.strAction.value="save";
		frm001.action ="../control/c004.jsp";
		frm001.p_SuccessPageURL.value = "../view/v001.jsp";
		frm001.p_FailPageURL.value = "../view/v001.jsp";				
		showSending();				
		frm001.submit();
   }
}
function toCommit()
{
    var obj = window.document.forms[0];
	for(var i =0; i<obj.length; i++)
	{
		   var sss = obj.elements[i].style.cssText;
		   if(sss=="BACKGROUND-COLOR: #7e7774")
		   {
			   alert("备注字数超额，请将灰色的备注删减后再保存！");
			   return false;
		   }
	}
    if(confirm("确认保存吗？"))
    {
		showSending();
		frm001.action ="../control/c004.jsp";
		frm001.strAction.value="toAutoCheck";	
		frm001.p_SuccessPageURL.value = "../view/v001.jsp";
		frm001.p_FailPageURL.value = "../view/v001.jsp";				
		showSending();							
		frm001.submit();
	}
}	
function toDel()
{
   if(confirm("确认删除吗？"))
   {
	  showSending();
	  frm001.action ="../control/c004.jsp";
	  frm001.strAction.value="del";	
	  frm001.p_SuccessPageURL.value = "../view/v001.jsp";
	  frm001.p_FailPageURL.value = "../view/v001.jsp";				
	  showSending();							
	  frm001.submit();
   }
}
function toBack()
{
	window.location="../view/v001.jsp"
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