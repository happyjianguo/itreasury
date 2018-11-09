<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/issworkflow-operate.tld" prefix="isswf" %>

<html:html>
<head>
<title>业务测试-销户申请</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/workflow/css/css.css">
<script language="JavaScript" src="<%=request.getContextPath()%>/workflow/js/date/common.js"></script>
<script language="JavaScript">
function addTestData()
{
		document.forms[0].operate.value="addTestData";
		document.forms[0].submit();
}
function doAction()
{
	document.forms[0].operate.value="doAction";
	document.forms[0].submit();
}
</script>
</head>
<body bgcolor="#e9f4fc" leftmargin="0" topmargin="0" style=" border-width:0px;">	
		<table width="100%" border="0" cellspacing="0" cellpadding="0" style="border-bottom:1px solid rgb(173,195,239);">
			<tr>
				<td>
					<jsp:include page="/workflow/common/wf_header.jsp"/>
				</td>
			</tr>
		</table>		
		<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td>
					<jsp:include page="/workflow/common/wf_menu.jsp"/>
				</td>
				<td valign="top">
					<html:form action="/testCtrl" method="post">
					<input name="operate" type="hidden"/>					
					<table width="100%" border="0" cellspacing="3" cellpadding="2">
						<tr>
							<td colspan="22" height="22" bgcolor="#C4DAFA" style="font-size:12px; font-weight:bold; border-bottom:1px solid #fff;">　
								业务测试-销户申请
							</td>
						</tr>
					</table>
					<table width="100%" border="0" cellspacing="3" cellpadding="2" class="tblist" id="DataTable">
							<tr class="thead">
							    <td align="right" bgcolor="#ECE9D8">申请单编号</td>
							    <td align="left" bgcolor="#ECE9D8">  
		                  <html:text property="testEntity.sqNo" maxlength="15"/>		                  
		              </td>
		          </tr>
		          <tr class="thead">
		          		<td align="right" bgcolor="#ECE9D8">申请机构编号</td>
			            <td align="left" bgcolor="#ECE9D8">
					          	<html:text property="testEntity.sqInstno" maxlength="15"/>					          	
			            </td>
		          </tr>
		          <tr class="thead">
		          		<td align="right" bgcolor="#ECE9D8">申请机构名称</td>
			            <td align="left" bgcolor="#ECE9D8">
					          	<html:text property="testEntity.sqInstname" maxlength="15"/>
			            </td>
		          </tr>
		          <tr class="thead">
		          		<td align="right" bgcolor="#ECE9D8">申请人</td>
			            <td align="left" bgcolor="#ECE9D8">
					          	<html:text property="testEntity.sqUser" maxlength="15"/>					          
			            </td>
		          </tr>
		          <tr class="thead">
		          		<td align="right" bgcolor="#ECE9D8">账户所属机构编号</td>
			            <td align="left" bgcolor="#ECE9D8">
					          	<html:text property="testEntity.sqZhinstno" maxlength="15"/>					          
			            </td>
		          </tr>
		          <tr class="thead">
		          		<td align="right" bgcolor="#ECE9D8">账户所属机构名称</td>
			            <td align="left" bgcolor="#ECE9D8">
					          	<html:text property="testEntity.sqZhinstname" maxlength="15"/>
			            </td>
		          </tr>
		          <tr class="thead">
		          		<td align="right" bgcolor="#ECE9D8">开户银行</td>
			            <td align="left" bgcolor="#ECE9D8">
					          	<html:text property="testEntity.sqBank" maxlength="15"/>
			            </td>
		          </tr>
		          
		          <tr class="thead">
		          		<td align="right" bgcolor="#ECE9D8">开户时间</td>
			            <td align="left" bgcolor="#ECE9D8">
					          	<html:text property="testEntity.khDate" />		
					          	<a href="#"><img src="<%=request.getContextPath()%>/workflow/images/calendar.gif" border="0" align="absmiddle" onclick="Container_onclick(document.forms[0].elements['testEntity.khDate'],false)"></a>
			            </td>
		          </tr>
		          <tr class="thead">
		          		<td align="right" bgcolor="#ECE9D8">启动流程编号</td>
			            <td align="left" bgcolor="#ECE9D8"><input type="text" name="osentryId" maxlength="20"></td>
		          </tr>
		          
 							<tr class="thead">
		          		<td align="center" bgcolor="#ECE9D8" colspan="2">		          				
											<input type="submit" value="提交审批" onclick="addTestData()"/>
			            </td>
		          </tr>
					</table>			
					</html:form>
				</td>
			</tr>
		</table>
	</body>
</html:html>

