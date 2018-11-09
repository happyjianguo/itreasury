<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/workflow/css/css.css">

<html>
	<head>
	<title><bean:message key="label.webmanage.transrightList.title"/></title>
	
	<script language="JavaScript">
		function update(id)
		{
			document.forms[0].selectedID.value = id;
			document.forms[0].operate.value = "toUpdateTransForm";
			document.forms[0].submit();
		}
		
		function add()
		{
			document.forms[0].operate.value = "toaddTransForm";
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
				<%-- 去掉左边链接菜单，用网自定义菜单
			<td>					
					<jsp:include page="/workflow/common/wf_menu.jsp"/>					
				</td>			
			--%>
				<td valign="top">

					<html:form action="/transRight">
					<input name="operate" type="hidden" value="selectTransRightList"/>
					<input name="selectedID" type="hidden"/>
					<table width="100%" border="0" cellspacing="3" cellpadding="2">
						<tr>
							<td colspan="22" height="22" bgcolor="#C4DAFA" style="font-size:12px; font-weight:bold; border-bottom:1px solid #fff;">　
								<bean:message key="label.webmanage.transrightList.head"/>
							</td>
						</tr>
					</table>
					<table width="100%" border="0" cellpadding="3" cellspacing="2" class="tblist" id="DataTable">
					<tr class="thead">
					  <td align="center" bgcolor="#ECE9D8"><bean:message key="label.webmanage.transrightList.id"/></td>
					  <td align="center" bgcolor="#ECE9D8"><bean:message key="label.webmanage.transrightList.sid"/></td>
					  <td align="center" bgcolor="#ECE9D8"><bean:message key="label.webmanage.transrightList.did"/></td>
					  <td align="center" bgcolor="#ECE9D8"><bean:message key="label.webmanage.transrightList.startdate"/></td>
					  <td align="center" bgcolor="#ECE9D8"><bean:message key="label.webmanage.transrightList.enddate"/></td>
					  <td align="center" bgcolor="#ECE9D8"><bean:message key="label.webmanage.transrightList.operate"/></td>
					  <td align="center" bgcolor="#ECE9D8"><bean:message key="label.webmanage.transrightList.operatedate"/></td>
					  <td align="center" bgcolor="#ECE9D8"><bean:message key="label.webmanage.transrightList.status"/></td>
					  <td align="center" bgcolor="#ECE9D8"><bean:message key="label.webmanage.transrightList.update"/></td>
					</tr>
					
					<logic:iterate id = "result" name = "pageResult" property="list" indexId="ind">
					<tr>
					<td align="center" bgcolor="#F8F8F8" height="25"><script>document.write(<bean:write name="ind"/>+1)</script></td>
					<td align="center" bgcolor="#F8F8F8"><bean:write name="result" property="srcname"/></td>
					<td align="center" bgcolor="#F8F8F8"><bean:write name="result" property="destname" /></td>
					<td align="center" bgcolor="#F8F8F8"><bean:write name="result" property="strbegindate"/></td>
					<td align="center" bgcolor="#F8F8F8"><bean:write name="result" property="strenddate"/></td>
					<td align="center" bgcolor="#F8F8F8"><bean:write name="result" property="opername" /></td>
					<td align="center" bgcolor="#F8F8F8"><bean:write name="result" property="inputdate" format="yyyy-MM-dd HH:mm:ss"/></td>
					<td align="center" bgcolor="#F8F8F8">
					<logic:equal name="result" property="valid" value="1">
						<bean:message key="label.webmanage.transrightList.isvalid" />
					</logic:equal>
					<logic:equal name="result" property="valid" value="0">
						<bean:message key="label.webmanage.transrightList.isnotvalid" />
					</logic:equal>
					</td>
					<td align="center" bgcolor="#F8F8F8"><input type="button" class="button1" name="updateTransfer" value="<bean:message key="label.webmanage.common.edit"/>" onclick="update('<bean:write name="result" property="id"/>')">
					</tr>
					</logic:iterate>
					</table>
					
					<table width="100%" border="0" cellspacing="0" cellpadding="3">
					  <tr>
					  	<td>
					  	  <jsp:include page="/workflow/pageloader/pageHibernate.jsp"/>
					  	</td>
					  </tr>
					</table>
					<table width="100%" border="0" cellspacing="0" cellpadding="3">
					<tr>
					<td align="center"><input type="button" class="button1" name="addTransfer" value="<bean:message key="label.webmanage.common.add"/>" onclick="add()"></td>
					</tr>
					</table>
					</html:form>
				</td>
			</tr>
		</table>
	</body>
</html>