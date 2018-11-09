<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%String strContext = request.getContextPath();%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title><bean:message key="label.webmanage.formctrl.title" /></title>
		<link href="<%=strContext%>/workflow/css/css.css" rel="stylesheet" type="text/css">
		<script language="javascript">
			
				function deleteElement(selID){		
						
				  if(confirm('<bean:message key="label.webmanage.common.deleteconfirm" />')){				  
				 	document.all["operate"].value="deleteElement";				 	
					document.all["selectedElementID"].value=selID;					
					document.forms[0].submit();					
				    return true;				    
				  }else{				  
				    return false;
				  }
				}
				
				function edit(selID,selName,selElement){		
				
					str="FormCtrl.do?operate=toEditElement&selectedElementID="+selID+"&selectedElementName="+selName+"&selectedElementElement="+selElement;				
					strReturn=openActionWindow(str);					
					if(strReturn!=""){
						if(strReturn!=null){
						   strMethod=parseParamters(strReturn,"operate");
				   			 if(strMethod=="editElement"){		
								document.all["operate"].value="editElement";
								document.all["selectedElementID"].value=parseParamters(strReturn,"selectedElementID");
								document.all["selectedElementName"].value=parseParamters(strReturn,"selectedElementName");
								document.all["selectedElementElement"].value=parseParamters(strReturn,"selectedElementElement");	
								document.forms[0].submit();
							 }
						}
					}
				}
				
				function add(){
				
					str="FormCtrl.do?operate=toAddElement";
					strReturn=openActionWindow(str);					
					if(strReturn!=""){
						if(strReturn!=null){
						   strMethod=parseParamters(strReturn,"operate");						   
				   			 if(strMethod=="addElement"){					   			 		
								document.all["operate"].value="addElement";
								document.all["selectedElementName"].value=parseParamters(strReturn,"selectedElementName");
								document.all["selectedElementElement"].value=parseParamters(strReturn,"selectedElementElement");
								document.forms[0].submit();
							 }
						}
					}
				}
				
				function openActionWindow(operate,paramete,dialogTop,dialogLeft,dialogWidth,dialogHeight){	
								
					if(dialogTop == null && dialogLeft == null && dialogWidth==null && dialogHeight == null){
						dialogTop=270;
						dialogLeft=270;
						dialogWidth=(window.screen.availWidth-450);
						dialogHeight=(window.screen.availHeight-550);
					}
					strReturn=window.showModalDialog(str,window,"dialogTop="+dialogTop
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
				
				function submitForm(){
				
					var obj1=document.all["FormName"];					
					if(checkInput(obj1)){
						var obj2=document.all["FormDesc"];					
						if(checkInput(obj2)){
							document.all["operate"].value="updateForm";
							document.all["selectedName"].value=document.all["FormName"].value;
							document.all["selectedDescription"].value=document.all["FormDesc"].value;
							document.forms[0].submit();
							return true;
						}
					}
					alert('<bean:message key="label.webmanage.common.checkInput" />');					
				}			
						
				function checkInput(obj){					
	              	var reg = /^\w+$/;
					if(reg.test(obj.value)){
				  		return true;
				 	   }				
				 	obj.focus();  	
				 	return false;
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
						<input type="hidden" name="operate" />
						<input type="hidden" name="selectedID" value="<bean:write name='formEntity' property="id"/>" />
						<input type="hidden" name="selectedName" value="<bean:write name='formEntity' property="name"/>" />
						<input type="hidden" name="selectedDescription" value="<bean:write name='formEntity' property="description"/>" />
						<input type="hidden" name="selectedElementID" />
						<input type="hidden" name="selectedElementName" />
						<input type="hidden" name=selectedElementElement />
						<table width="100%" border="0" cellspacing="3" cellpadding="2">
							<tr>
								<td colspan="22" height="22" bgcolor="#C4DAFA" style="font-size:12px; font-weight:bold; border-bottom:1px solid #fff;">
									<bean:message key="label.webmanage.elementList.head" />
								</td>
							</tr>
						</table>
						<table width="100%" border="0" cellspacing="2" cellpadding="3" class="tblist" id="DataTable">
							<tr class="thead">
							<td align="center" bgcolor="#ECE9D8"><bean:message key="label.webmanage.transrightList.id"/></td>
								<td align="center" bgcolor="#ECE9D8">
									<bean:message key='label.webmanage.elementList.opt.elementElement' />
								</td>
								<td align="center" bgcolor="#ECE9D8">
									<bean:message key='label.webmanage.elementList.opt.elementName' />
								</td>
								<td align="center" bgcolor="#ECE9D8">
									<bean:message key='label.webmanage.common.operate' />
								</td>
							</tr>
							
									<logic:iterate id="result" name="formEntity" property="osFormelementses" indexId="ind">
										<tr >
										<td align="center" bgcolor="#F8F8F8" height="25"><script>document.write(<bean:write name="ind"/>+1)</script></td>
											<td width=30% align="center"  bgcolor="#F8F8F8">
												&nbsp;<bean:write name='result' property='element' />
											</td>
											<td width=30% align="center" bgcolor="#F8F8F8">
												&nbsp;<bean:write name='result' property='name' />
											</td>
											<td nowrap align="center" bgcolor="#F8F8F8">
												&nbsp;
												<input type="button" class="button1" name="update" value="<bean:message key="label.webmanage.common.edit" />" onclick=edit("<bean:write name='result' property='id' />","<bean:write name='result' property='name' />","<bean:write name='result' property='element' />")>
												 &nbsp;&nbsp;&nbsp;
												&nbsp;
												<input type="button" class="button1" name="delete" value="<bean:message key="label.webmanage.common.delete" />" onclick=deleteElement(<bean:write name='result' property='id' />)>
												
											</td>
										</tr>
									</logic:iterate>
								
							<tr>
							<td align="center" bgcolor="#F8F8F8">
								</td>
								<td align="center" bgcolor="#F8F8F8">
								</td>
								<td align="center" bgcolor="#F8F8F8">
								</td>
								<td align="center" bgcolor="#F8F8F8">
									<input name="Submit" type="button" class="button1" value="<bean:message key="label.webmanage.common.add" />" onclick=add()>
								</td>
							</tr>
							<tr>
							<td align="center" bgcolor="#F8F8F8">
								</td>
								<td align="center" bgcolor="#F8F8F8">
									<bean:message key='label.webmanage.formList.opt.formName' />
									&nbsp;&nbsp;
									<input type="text" value="<bean:write name='formEntity' property='name'/>" id="FormName" name="FormName">
								</td>
								<td align="center" bgcolor="#F8F8F8">
									<bean:message key='label.webmanage.formList.opt.formDesc' />
									&nbsp;&nbsp;
									<input type="text" value="<bean:write name='formEntity' property='description'/>" id="FormDesc" name="FormDesc">
								</td>
								<td align="center" bgcolor="#F8F8F8">
									
									<input type="reset" class="button1" value="<bean:message key="label.webmanage.common.reset"/>" >
									&nbsp;&nbsp;
									<input type="button" class="button1" onclick="javascript:history.go(-1);"  value="<bean:message key='label.webmanage.common.back' />">
								</td>
							</tr>
						</table>
					</html:form>
				</td>
			</tr>
		</table>
	</body>
</html>
