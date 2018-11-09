<%@ page contentType="text/html; charset=GBK"%>
<%@page import="com.iss.itreasury.ebank.util.NameRef"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%String strContext = request.getContextPath();%>
<!-- <link rel="stylesheet" type="text/css" href="../css/css.css">  -->
<link rel="stylesheet" type="text/css" href="<%=strContext%>/workflow/css/css.css">
<html>
	<head>
		<title><bean:message key="label.webmanage.wfModelList.title" /></title>
		<script language="JavaScript">
		function formedit(entryId,actionId,wfid,stepid)
		{
			document.forms[0].entryid.value=entryId;
			document.forms[0].actionid.value=actionId;
			document.forms[0].wfid.value=wfid;
			document.forms[0].stepid.value=stepid;
			document.forms[0].operate.value="toTransactionForm";
			document.forms[0].submit();
		}
		function preview(wfid){
			window.open('wfPreview.do?operate=preview&<%=com.iss.inut.workflow.designer.util.Constants.BizInfo.PARAM_WFID%>='+wfid,'preview','height=200,width=400,top=0,left=0,toolbar=no,menubar=no,scrollbars=no, resizable=yes,location=no, status=no')
		}
		function design(wfid){
			window.open(wfid,'design','width='+(screen.width-10)+',height='+(screen.height-80)+',top=0,left=0,toolbar=no,menubar=no,scrollbars=no, resizable=yes,location=no, status=no')
		}
		function add(wfid){
			window.open(wfid,'add','width='+(screen.width-10)+',height='+(screen.height-80)+',top=0,left=0,toolbar=no,menubar=no,scrollbars=no, resizable=yes,location=no, status=no')
		}
		function deleteModel(wfid,isExsit){
			if(isExsit==true){
				alert("请先取消审批关联，才能删除！");
				return false;				
			}
			if(confirm("删除流程将删除流程下所有实例、代办任务、已办任务，您确定删除吗？")){
			document.forms[0].action='wfDefine.do?operate=delete&<%=com.iss.inut.workflow.designer.util.Constants.BizInfo.PARAM_WFID%>='+wfid;
			document.forms[0].submit();
			}
		}
		</script>
	</head>
	<body bgcolor="#e9f4fc" leftmargin="0" topmargin="0" style=" border-width:0px;">

		<table width="100%" border="0" cellspacing="0" cellpadding="0" style="border-bottom:1px solid rgb(173,195,239);">
			<tr>
				<td>
					<jsp:include page="/workflow/common/wf_header.jsp" />
				</td>
			</tr>
		</table>

		<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td valign="top">

					<html:form action="/wfDefine">
						<input name="operate" type="hidden" value="selectWfDefineList" />
						<table width="100%" border="0" cellspacing="3" cellpadding="2">
							<tr>
								<td colspan="22" height="22" bgcolor="#C4DAFA" style="font-size:12px; font-weight:bold; border-bottom:1px solid #fff;">
									<bean:message key="label.webmanage.wfModelList.head" />
								</td>
							</tr>
						</table>
						<table width="100%" border="0" cellpadding="3" cellspacing="2" class="tblist" id="DataTable">
							<tr class="thead">
								<td align="center" bgcolor="#ECE9D8">
									<bean:message key="label.webmanage.wfModelList.wfno" />
								</td>
								<td align="center" bgcolor="#ECE9D8">
									<bean:message key="label.webmanage.wfModelList.wfname" />
								</td>
								<td align="center" bgcolor="#ECE9D8">
									<bean:message key="label.webmanage.wfModelList.creator" />
								</td>
								<td align="center" bgcolor="#ECE9D8">
									<bean:message key="label.webmanage.wfModelList.createtime" />
								</td>
								<td align="center" bgcolor="#ECE9D8">
									<bean:message key="label.webmanage.wfModelList.template" />
								</td>
								<td align="center" bgcolor="#ECE9D8">
									<bean:message key="label.webmanage.wfModelList.preview" />
								</td>
								<td align="center" bgcolor="#ECE9D8">
									<bean:message key="label.webmanage.wfModelList.open" />
								</td>
								<td align="center" bgcolor="#ECE9D8">
									<bean:message key="label.webmanage.common.delete" />
								</td>
							</tr>
							<logic:iterate id="result" name="pageResult" property="list" indexId="ind">
								<tr>
									<td align="center" bgcolor="#F8F8F8" height="25">
										<bean:write name="result" property="id" />
									</td>
									<td align="center" bgcolor="#F8F8F8" height="25">
										<bean:write name="result" property="name" />
									</td>
									<td align="center" bgcolor="#F8F8F8">
									 <bean:define id="author" type="java.lang.String" name="result" property="author"></bean:define>
												<%=NameRef.getUserNameByID(Long.parseLong(author))%>
									</td>
									<td align="center" bgcolor="#F8F8F8">
										<bean:write name="result" property="createdate" format="yyyy-MM-dd HH:mm:ss" />
									</td>
									<td align="center" bgcolor="#F8F8F8">
										<input type="button" value="<bean:message key="label.webmanage.wfModelList.newtemp" />"
											onclick="design('<%=strContext%>/workflow/jsp/wfdesign/design.jsp?<%=com.iss.inut.workflow.designer.util.Constants.BizInfo.PARAM_ACTION%>=<%=com.iss.inut.workflow.designer.util.Constants.BizInfo.ACTION_TEMPLATE%>&<%=com.iss.inut.workflow.designer.util.Constants.BizInfo.PARAM_WFID%>=<bean:write name="result" property="id" />')">
									</td>
									<td align="center" bgcolor="#F8F8F8">
										<input type="button" onClick="javascript:preview(<bean:write name="result" property="id" />);" value='<bean:message key="label.webmanage.wfModelList.preview" />'>
									</td>
									<td align="center" bgcolor="#F8F8F8">
										<input type="button"
											onClick="design('wfPreview.do?operate=design&<%=com.iss.inut.workflow.designer.util.Constants.BizInfo.PARAM_ACTION%>=<%=com.iss.inut.workflow.designer.util.Constants.BizInfo.ACTION_EDIT%>&<%=com.iss.inut.workflow.designer.util.Constants.BizInfo.PARAM_WFID%>=<bean:write name="result" property="id" />')"
											value='<bean:message key="label.webmanage.wfModelList.open" />'>
									</td>
									<td align="center" bgcolor="#F8F8F8">
									 <bean:define id="wid" type="java.lang.Long" name="result" property="id"></bean:define>												
										<input type="button" onClick="deleteModel('<bean:write name="result" property="id" />',<%=NameRef.verifyRelationByWorkflowID(wid.longValue())%>)" value='<bean:message key="label.webmanage.common.delete" />'>
									</td>
								</tr>
							</logic:iterate>

						</table>

						<table width="100%" border="0" cellspacing="0" cellpadding="3">
							<tr>
								<td>
									<jsp:include page="/workflow/pageloader/pageHibernate.jsp" />
								</td>
							</tr>
						</table>
						<table width="100%" border="0" cellspacing="0" cellpadding="3">
							<tr>
								<td align="center">
									<input type="button" onClick="add('<%=strContext%>/workflow/jsp/wfdesign/design.jsp?<%=com.iss.inut.workflow.designer.util.Constants.BizInfo.PARAM_ACTION%>=<%=com.iss.inut.workflow.designer.util.Constants.BizInfo.ACTION_NEW%>')"
										value='<bean:message key="label.webmanage.common.add" />'>
								</td>
							</tr>
						</table>
					</html:form>
				</td>
			</tr>
		</table>
	</body>
</html>
