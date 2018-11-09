<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title><bean:message key="label.webmanage.wfctrl.title" /></title>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/workflow/css/css.css">
		<script language="javascript">
		
				function toTransactionForm(entryId,actionCode,wfid,stepCode){

					document.forms[0].selectedEntryID.value=entryId;					
					document.forms[0].selectedActionCode.value=actionCode;					
					document.forms[0].selectedDefineID.value=wfid;					
					document.forms[0].selectedStepCode.value=stepCode;					
					document.forms[0].operate.value="toTransactionForm";					
					document.forms[0].submit();					
				}
		
				function startupEntry(selID){	
									
					document.all["operate"].value="startUpEntry";					
					document.all["selectedEntryID"].value=selID;					
					document.forms[0].submit();
				}
				
				function suspendEntry(selID){		
						
				  if(confirm('<bean:message key="label.webmanage.ctlEntryList.suspendconfirm" />')){				  
				 	document.all["operate"].value="suspendEntry";				 	
					document.all["selectedEntryID"].value=selID;					
					document.forms[0].submit();					
				    return true;				    
				  }else{				  
				    return false;
				  }
				}
				
				function stopEntry(selID){			
						
				  if(confirm('<bean:message key="label.webmanage.ctlEntryList.stopconfirm" />')){				  
				 	document.all["operate"].value="stopEntry";				 	
					document.all["selectedEntryID"].value=selID;					
					document.forms[0].submit();					
				    return true;				    
				  }else{				  
				    return false;
				  }
				}
				
				function traceEntry(selID){			
										  
				 	document.all["operate"].value="traceEntry";				 	
					document.all["selectedEntryID"].value=selID;					
					document.forms[0].submit();		
				}						
				
				//"调控":更改步骤负责人（这个功能现在给去掉了）！
				function changeStepOwner(selID){
				
					str="WFCtrl.do?operate=selectAllUserLNList&selectedStepID="+selID;				
					strReturn=openActionWindow(str);					
					if(strReturn!=null){
						document.all["operate"].value="changeStepOwner";	
						document.all["selectedCurStepID"].value=selID;					
						document.all["selectedOwner"].value=strReturn;						
						document.forms[0].submit();
					}
				}			
				
				function openActionWindow(operate,paramete,dialogTop,dialogLeft,dialogWidth,dialogHeight){
									
					if(dialogTop == null && dialogLeft == null && dialogWidth==null && dialogHeight == null){
						dialogTop=270;
						dialogLeft=270;
						dialogWidth=(window.screen.availWidth-250);
						dialogHeight=(window.screen.availHeight-250);
					}
					strReturn=window.showModalDialog(str,window,"dialogTop="+dialogTop
						+"px;dialogLeft="+dialogLeft+"px;dialogWidth="+dialogWidth+"px;dialogHeight="
						+dialogHeight+"px;help=no;scrollbars=yes;border=thin;");
				   	return strReturn;
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
						<input name="operate" type="hidden" />
						<input name="selectedDefineID" type="hidden" value="<bean:write name="selectedDefineID" />" />
						<input name="selectedDefineName" type="hidden" value="<bean:write name="selectedDefineName" />" />
						<input name="selectedEntryID" type="hidden" />
						<input name="selectedStepCode" type="hidden" />
						<input name="selectedActionCode" type="hidden" />
						<input name="selectedOwner" type="hidden" />
						<input name="selectedCurStepID" type="hidden" />
						<table width="100%" border="0" cellspacing="3" cellpadding="2">
							<tr>
								<td colspan="22" height="22" bgcolor="#B1D0EC" style="font-size:12px; font-weight:bold; border-bottom:1px solid #fff;">
									<bean:message key="label.webmanage.ctlEntryList.head" />
								</td>
							</tr>
						</table>
						<table align="center" width="100%" border="0" cellpadding="3" cellspacing="2" class="tblist">
							<tr class="thead">
								<td width="15%" align="center" bgcolor="#B1D0EC">
									<bean:message key="label.webmanage.ctlEntryList.opt.id" />
								</td>
								<td width="15%" align="center" bgcolor="#B1D0EC">
									<bean:message key="label.webmanage.ctlEntryList.opt.name" />
								</td>
								
								<td width="15%" align="center" bgcolor="#B1D0EC">
									<bean:message key="label.webmanage.ctlEntryList.opt.state" />
								</td>
								<td width="15%" align="center" bgcolor="#B1D0EC">
									<table align="center" width="100%" border="0">
										<tr>
											<td colspan=3 align="center">
												<bean:message key="label.webmanage.traceStepList.opt.currentStep" />
											</td>
										</tr>
										<tr>
											<td align="center" width=30%>
												<bean:message key="label.webmanage.traceStepList.opt.name" />
											</td>
										</tr>
									</table>
								</td>
								<td width="40%" align="center" bgcolor="#B1D0EC">
									<bean:message key="label.webmanage.common.operate" />
								</td>
							</tr>
							
									<logic:iterate id="result" name="pageResult" property="list">
										<tr>
											<td align="center" bgcolor="#F8F8F8">	&nbsp;											
												<bean:write name="result" property="id" />
											</td>
											<td align="center" bgcolor="#F8F8F8">&nbsp;
												<bean:write name="result" property="osWfdefine.name"/>
											</td>
											
											<td align="center" bgcolor="#F8F8F8">&nbsp;
												<logic:equal value="created" name="result" property="myState.meaning">
													<bean:message key="label.webmanage.common.state.wfentry.created" />
												</logic:equal>

												<logic:equal value="activated" name="result" property="myState.meaning">
													<bean:message key="label.webmanage.common.state.wfentry.activated" />
												</logic:equal>

												<logic:equal value="suspended" name="result" property="myState.meaning">
													<bean:message key="label.webmanage.common.state.wfentry.suspended" />
												</logic:equal>
												
												<logic:equal value="killed" name="result" property="myState.meaning">
													<bean:message key="label.webmanage.common.state.wfentry.killed" />
												</logic:equal>

												<logic:equal value="completed" name="result" property="myState.meaning">
													<bean:message key="label.webmanage.common.state.wfentry.completed" />
												</logic:equal>
											</td>
											<td align="center" bgcolor="#F8F8F8">&nbsp;
												<table align="center" width="100%" border="0">
													<logic:iterate id="objStep" name="result" property="osCurrentsteps">
														<tr>
															<td align="center">
																<bean:write name="objStep" property="stepName" />
															</td>
														</tr>
													</logic:iterate>
												</table>
											</td>
											<td nowrap align="center" bgcolor="#F8F8F8">&nbsp;
												<logic:equal value="created" name="result" property="myState.meaning">
													<input disabled name="Submit" type="button" class="button1" value="<bean:message key="label.webmanage.common.startup" />" onclick=startupEntry("<bean:write name="result" property="id" />")>&nbsp;
													<input name="Submit" type="button" class="button1" value="<bean:message key="label.webmanage.common.suspend" />" onclick=suspendEntry("<bean:write name="result" property="id" />")>&nbsp;
													<input name="Submit" type="button" class="button1" value="<bean:message key="label.webmanage.common.stop" />" onclick=stopEntry("<bean:write name="result" property="id" />")>&nbsp;													
												</logic:equal>
												<logic:equal value="activated" name="result" property="myState.meaning">
													<input disabled name="Submit" type="button" class="button1" value="<bean:message key="label.webmanage.common.startup" />" onclick=startupEntry("<bean:write name="result" property="id" />")>&nbsp;
													<input name="Submit" type="button" class="button1" value="<bean:message key="label.webmanage.common.suspend" />" onclick=suspendEntry("<bean:write name="result" property="id" />")>&nbsp;
													<input name="Submit" type="button" class="button1" value="<bean:message key="label.webmanage.common.stop" />" onclick=stopEntry("<bean:write name="result" property="id" />")>&nbsp;													
												</logic:equal>
												<logic:equal value="suspended" name="result" property="myState.meaning">
													<input name="Submit" type="button" class="button1" value="<bean:message key="label.webmanage.common.startup" />" onclick=startupEntry("<bean:write name="result" property="id" />")>&nbsp;
													<input disabled name="Submit" type="button" class="button1" value="<bean:message key="label.webmanage.common.suspend" />" onclick=suspendEntry("<bean:write name="result" property="id" />")>&nbsp;
													<input name="Submit" type="button" class="button1" value="<bean:message key="label.webmanage.common.stop" />" onclick=stopEntry("<bean:write name="result" property="id" />")>&nbsp;																										
												</logic:equal>
												<logic:equal value="killed" name="result" property="myState.meaning">
													<input disabled name="Submit" type="button" class="button1" value="<bean:message key="label.webmanage.common.startup" />" onclick=startupEntry("<bean:write name="result" property="id" />")>&nbsp;
													<input disabled name="Submit" type="button" class="button1" value="<bean:message key="label.webmanage.common.suspend" />" onclick=suspendEntry("<bean:write name="result" property="id" />")>&nbsp;
													<input disabled name="Submit" type="button" class="button1" value="<bean:message key="label.webmanage.common.stop" />" onclick=stopEntry("<bean:write name="result" property="id" />")>&nbsp;													
												</logic:equal>
												<logic:equal value="completed" name="result" property="myState.meaning">
													<input disabled name="Submit" type="button" class="button1" value="<bean:message key="label.webmanage.common.startup" />" onclick=startupEntry("<bean:write name="result" property="id" />")>&nbsp;
													<input disabled name="Submit" type="button" class="button1" value="<bean:message key="label.webmanage.common.suspend" />" onclick=suspendEntry("<bean:write name="result" property="id" />")>&nbsp;
													<input disabled name="Submit" type="button" class="button1" value="<bean:message key="label.webmanage.common.stop" />" onclick=stopEntry("<bean:write name="result" property="id" />")>&nbsp;													
												</logic:equal>
												<input name="Submit" type="button" class="button1" value="<bean:message key="label.webmanage.common.trace" />" onclick=traceEntry("<bean:write name="result" property="id" />")>&nbsp;
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
