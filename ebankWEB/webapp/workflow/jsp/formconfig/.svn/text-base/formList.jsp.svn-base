<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%String strContext = request.getContextPath();%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title><bean:message key="label.webmanage.formctrl.title" /></title>
		<link rel="stylesheet" type="text/css" href="<%=strContext%>/workflow/css/css.css">
		<script language="javascript">	
			
				function deleteForm(selID){	
							
				  if(confirm('<bean:message key="label.webmanage.common.deleteconfirm" />')){				  
				 	document.all["operate"].value="deleteForm";				 	
					document.all["selectedID"].value=selID;					
					document.forms[0].submit();					
				    return true;				    
				  }else{				  
				    return false;
				  }
				}
				
				function editForm(selID,selName,selDesc){	
								
					document.all["operate"].value="toEditForm";					
					document.all["selectedID"].value=selID;					
					document.all["selectedName"].value=selName;					
					document.all["selectedDescription"].value=selDesc;					
					document.forms[0].submit();
				}
				
				function addForm(){
				
					strReturn=openActionWindow("toAddForm");					
					if(strReturn!=""){
						if(strReturn!=null){
						   strMethod=parseParamters(strReturn,"operate");
				   			 if(strMethod=="addForm"){		
								document.all["operate"].value="addForm";
								document.all["selectedName"].value=parseParamters(strReturn,"selectedName");
								document.all["selectedDescription"].value=parseParamters(strReturn,"selectedDescription");	
								document.forms[0].submit();
							 }
						}
					}
				}
				
				function openActionWindow(operate,paramete,dialogTop,dialogLeft,dialogWidth,dialogHeight){	
							
	    			str="FormCtrl.do?operate="+operate;	    			
					if(paramete!=null){
						str=str+paramete;
					}					
					if(dialogTop == null && dialogLeft == null && dialogWidth==null && dialogHeight == null){
						dialogTop=270;
						dialogLeft=270;
						dialogWidth=(window.screen.availWidth-450);
						dialogHeight=(window.screen.availHeight-550);
					}					
					strReturn=window.showModalDialog(str,"","dialogTop="+dialogTop
						+"px;dialogLeft="+dialogLeft+"px;dialogWidth="+dialogWidth+"px;dialogHeight="
						+dialogHeight+"px;help=no;scrollbars=yes;border=thin;");					
				   	return strReturn;
			   }
			   
			   function parseParamters(strParamters,Key){	
			   	
				if(strReturn!=null){	
					var strs=new Array();
					strs=strParamters.split("&");
					for(i=0;i<strs.length;i++){
						temps=strs[i].split("=");					
						if(temps[0]== Key){
							return temps[1];
						}
					}
				}
				return "";
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
					<html:form action="FormCtrl.do" method="post">
						<input type="hidden" name="operate" value="showFormsList"/>
						<input type="hidden" name="selectedID" />
						<input type="hidden" name="selectedName" />
						<input type="hidden" name="selectedDescription" />
						<table width="100%" border="0" cellspacing="3" cellpadding="2">
							<tr>
								<td colspan="22" height="22" bgcolor="#C4DAFA" style="font-size:12px; font-weight:bold; border-bottom:1px solid #fff;">
									<bean:message key="label.webmanage.formList.head" />
								</td>
							</tr>
						</table>
						<table align="center" width="100%" border="0" cellpadding="3" cellspacing="2" class="tblist">
							<tr class="thead">
							<td align="center" bgcolor="#ECE9D8"><bean:message key="label.webmanage.transrightList.id"/></td>
								<td align="center" bgcolor="#ECE9D8">
									<bean:message key="label.webmanage.formList.opt.formName" />
								</td>
								<td align="center" bgcolor="#ECE9D8">
									<bean:message key="label.webmanage.formList.opt.formDesc" />
								</td>
								<td align="center" bgcolor="#ECE9D8">
									<bean:message key="label.webmanage.common.operate" />
								</td>
							</tr>
							
									<logic:iterate id="result" name="pageResult" property="list" indexId="ind">
										<tr>
										<td align="center" bgcolor="#F8F8F8" height="25"><script>document.write(<bean:write name="ind"/>+1)</script></td>
											<td align="center" bgcolor="#F8F8F8">
												&nbsp;<bean:write name="result" property="name" />
											</td>
											<td align="center" bgcolor="#F8F8F8">
												&nbsp;<bean:write name="result" property="description" />
											</td>
											<td nowrap align="center" bgcolor="#F8F8F8">
												<input name="Submit" type="button" class="button1" value="<bean:message key="label.webmanage.common.edit" />" onclick=editForm(<bean:write name="result" property="id" />,"<bean:write name="result" property="name" />","<bean:write name="result" property="description" />")>
												&nbsp;&nbsp;&nbsp;
												<input name="Submit" type="button" class="button1" value="<bean:message key="label.webmanage.common.delete" />" onclick=deleteForm(<bean:write name="result" property="id" />)>
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
						<table width="100%" border="0" cellspacing="3" cellpadding="3">
							<tr>
								
								<td valign="top" align="center">
									<input name="Submit" type="button" class="button1" value="<bean:message key="label.webmanage.common.add" />" onclick=addForm()>
								</td>
								
							</tr>
						</table>
					</html:form>
				</td>
			</tr>
		</table>
	</body>
</html>
