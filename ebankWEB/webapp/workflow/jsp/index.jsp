<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title><bean:message key="label.webmanage.wfctrl.title" /></title>
		<link rel="stylesheet" type="text/css" href="../framework/css/css.css">
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
				<td valign="top" align="center">
					»¶Ó­µÇÂ¼ :<%=com.iss.inut.workflow.constants.ParamUtils.getLoginName(request)%>
				</td>
			</tr>
		</table>
	</body>
</html>
