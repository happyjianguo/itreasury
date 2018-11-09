<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@page import="com.iss.itreasury.ebank.util.NameRef"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title><bean:message key="label.webmanage.wfctrl.title" /></title>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/workflow/css/css.css">
		<script language="javascript">		
				
				function startupWFDefine(wfid){	
				
					document.all["operate"].value="startUpDefine";					
					document.all["selectedDefineID"].value=wfid;					
					document.forms[0].submit();
				}
				
				function suspendWFDefine(selID){
								
				  if(confirm('<bean:message key="label.webmanage.ctlDefineList.suspendconfirm" />')){				  
				 	document.all["operate"].value="suspendDefine";				 	
					document.all["selectedDefineID"].value=selID;					
					document.forms[0].submit();					
				    return true;				    
				  }else{				  
				    return false;
				  }
				}
				
				function stopWFDefine(selID){	
							
				  if(confirm('<bean:message key="label.webmanage.ctlDefineList.stopconfirm" />')){				  
				 	document.all["operate"].value="stopDefine";				 	
					document.all["selectedDefineID"].value=selID;					
					document.forms[0].submit();					
				    return true;				    
				  }else{				  
				    return false;
				  }
				}
				
				function showEntryList(wfid){
				
					document.all["operate"].value="showEntryList";				
					document.all["selectedDefineID"].value=wfid;					
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
					<html:form action="WFCtrl.do" method="post">
						<input name="operate" type="hidden" value="showDefineList"/>
						<input name="selectedDefineID" type="hidden" value="<bean:write name="selectedDefineID"/>" />
						<input name="selectedDefineName" type="hidden" value="<bean:write name="selectedDefineName"/>" />
						<table width="100%" border="0" cellspacing="3" cellpadding="2">
							<tr>
								<td colspan="22" height="22" bgcolor="#B1D0EC" style="font-size:12px; font-weight:bold; border-bottom:1px solid #fff;">
									<bean:message key="label.webmanage.ctlDefineList.head" />
								</td>
							</tr>
						</table>
						<table align="center" width="100%" border="0" cellpadding="3" cellspacing="2" class="tblist">
							<tr class="thead">
								<td width="10%" align="center" bgcolor="#B1D0EC">
									<bean:message key="label.webmanage.ctlDefineList.opt.id" />
								</td>
								<td width="15%" align="center" bgcolor="#B1D0EC">
									<bean:message key="label.webmanage.ctlDefineList.opt.name" />
								</td>
								<td width="15%" align="center" bgcolor="#B1D0EC">
									<bean:message key="label.webmanage.ctlDefineList.opt.author" />
								</td>
								<td width="20%" align="center" bgcolor="#B1D0EC">
									<bean:message key="label.webmanage.ctlDefineList.opt.createDate" />
								</td>
								<td width="10%" align="center" bgcolor="#B1D0EC">
									<bean:message key="label.webmanage.ctlDefineList.opt.state" />
								</td>
								<td width="30%" align="center" bgcolor="#B1D0EC">
									<bean:message key="label.webmanage.common.operate" />
								</td>
							</tr>
							
									<logic:iterate id="result" name="pageResult" property="list">
										<tr>
											<td align="center" bgcolor="#F8F8F8">&nbsp;
												<a style="CURSOR: hand;" onclick=showEntryList("<bean:write name="result" property="id" />")><bean:write name="result" property="id" /></a>
											</td>
											<td align="center" bgcolor="#F8F8F8">&nbsp;
												<bean:write name="result" property="name" />
											</td>
											<td align="center" bgcolor="#F8F8F8">&nbsp;
												<!--bean:write name="result" property="author" /-->
												<bean:define id="author" type="java.lang.String" name="result" property="author"></bean:define>
												<%=NameRef.getUserNameByID(Long.parseLong(author))%>
											</td>
											<td align="center" bgcolor="#F8F8F8">&nbsp;
												<bean:write name="result" property="createdate" format="yyyy-MM-dd HH:mm:ss"/>
											</td>
											<td align="center" bgcolor="#F8F8F8">&nbsp;
												<logic:match value="created" name="result" property="state.meaning">
													<bean:message key="label.webmanage.common.state.wfdefine.startup" />
												</logic:match>
												<logic:match value="activated" name="result" property="state.meaning">
													<bean:message key="label.webmanage.common.state.wfdefine.startup" />
												</logic:match>
												<logic:match value="suspended" name="result" property="state.meaning">
													<bean:message key="label.webmanage.common.state.wfdefine.suspend" />
												</logic:match>
												<logic:match value="completed" name="result" property="state.meaning">
													<bean:message key="label.webmanage.common.state.wfdefine.suspend" />
												</logic:match>
											</td>
											<td nowrap align="center" bgcolor="#F8F8F8">&nbsp;
												<logic:equal value="created" name="result" property="state.meaning">
													<input disabled name="Submit" type="button" class="button1"  value="<bean:message key="label.webmanage.common.suspend" />" onclick=suspendWFDefine("<bean:write name="result" property="id" />")>
													<input disabled name="Submit" type="button" class="button1" value="<bean:message key="label.webmanage.common.stop" />" onclick=stopWFDefine("<bean:write name="result" property="id" />")>
												</logic:equal>	
												<logic:equal value="activated" name="result" property="state.meaning">
													<input name="Submit" type="button" class="button1"  value="<bean:message key="label.webmanage.common.suspend" />" onclick=suspendWFDefine("<bean:write name="result" property="id" />")>
													<input name="Submit" type="button" class="button1"  value="<bean:message key="label.webmanage.common.stop" />" onclick=stopWFDefine("<bean:write name="result" property="id" />")>
												</logic:equal>													
												<logic:equal value="suspended" name="result" property="state.meaning">													
													<input disabled name="Submit" type="button" class="button1"  value="<bean:message key="label.webmanage.common.suspend" />" onclick=suspendWFDefine("<bean:write name="result" property="id" />")>
													<input name="Submit" type="button" class="button1" value="<bean:message key="label.webmanage.common.stop" />" onclick=stopWFDefine("<bean:write name="result" property="id" />")>
												</logic:equal>
												<logic:equal value="completed" name="result" property="state.meaning">
													<input disabled name="Submit" type="button" class="button1" value="<bean:message key="label.webmanage.common.suspend" />" onclick=suspendWFDefine("<bean:write name="result" property="id" />")>
													<input disabled name="Submit" type="button" class="button1" value="<bean:message key="label.webmanage.common.stop" />" onclick=stopWFDefine("<bean:write name="result" property="id" />")>													
												</logic:equal>
											</td>
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
					</html:form>
				</td>
			</tr>
		</table>
	</body>
</html>
