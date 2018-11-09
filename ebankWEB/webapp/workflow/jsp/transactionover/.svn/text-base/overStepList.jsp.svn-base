<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title><bean:message key="label.webmanage.overEntryList.title" /></title>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/workflow/css/css.css">
		<script language="JavaScript" src="<%=request.getContextPath()%>/workflow/js/date/common.js"></script>
		<script language="javascript">
		
			function search(){
				if(check()){
					document.all["operate"].value="search";
					document.forms[0].submit();
					return true;	
				}
				alert("<bean:message key="label.webmanage.common.checkInput" />");
			}
			
			function check(){
			
				var startDate;
				var endDate;
				var f = document.forms[0].elements;
				for(var i=0; i<f.length; i++){
				 	if(f[i].type == 'text') {
				 		if(f[i].name=="pdcStepFinishDate.startDate"){
				 			startDate=f[i].value;	
				 		}
				 		if(f[i].name=="pdcStepFinishDate.endDate"){
				 			endDate=f[i].value;
				 		}
					}
				}
				if(checkEmpty(startDate) && checkEmpty(endDate)){
					if(!checkDate(startDate,endDate))
						return false;
				}			
				return true;
			}
			
			function checkDate(startDate,endDate){
			
				if(checkEmpty(startDate) && checkEmpty(endDate)){
					if(getNum(startDate)<=getNum(endDate))
						return true;
				}
				return false;
			}
			
			function checkEmpty(values){
			
				if(values=="")				
					return false
				return true;			
			}
			
			function getNum(str){			
			
				var temps=new Array();
				temps=str.split("-");
				var returnVal="";
				for(i=0;i<temps.length;i++){
					returnVal=returnVal+temps[i];
				}
				return returnVal;
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
					<html:form action="TranOverCtrl.do" method="post">
						<input name="operate" type="hidden" value="showStepList"/>
						<table width="100%" border="0" cellspacing="3" cellpadding="2">
							<tr>
								<td colspan="22" height="22" bgcolor="#C4DAFA" style="font-size:12px; font-weight:bold; border-bottom:1px solid #fff;">
									<bean:message key="label.webmanage.overStepList.head" />
								</td>
							</tr>
						</table> 
						<table align="center" width="100%" border="0" cellpadding="3" cellspacing="2" class="tblist">
							<tr class="thead">
								<td align="center" bgcolor="#ECE9D8" colspan="6">
									<bean:message key="label.webmanage.common.condition.stepFinishDate" />
									<bean:message key="label.webmanage.common.condition.startDate" />
									<html:text property="pdcStepFinishDate.startDate" readonly="true" />
									<a href="#"><img src="<%=request.getContextPath()%>/workflow/images/calendar.gif" border="0" align="absmiddle" onclick="Container_onclick(document.forms[0].elements['pdcStepFinishDate.startDate'],false)"></a>								
									<bean:message key="label.webmanage.common.condition.endDate" />
									<html:text property="pdcStepFinishDate.endDate" readonly="true" />
									<a href="#"><img src="<%=request.getContextPath()%>/workflow/images/calendar.gif" border="0" align="absmiddle" onclick="Container_onclick(document.forms[0].elements['pdcStepFinishDate.endDate'],false)"></a>
								</td>
								<td align="center" bgcolor="#ECE9D8" colspan="6">									
									<input type="button" class="button1" name="btnSel" value="<bean:message key="label.webmanage.common.search" />" onclick="search();">
								</td>
							</tr>
						</table>
						<table width="100%" border="0" cellspacing="2" cellpadding="2" class="tblist" id="DataTable">
							<tr class="thead">
							    <td align="center" bgcolor="#ECE9D8"><bean:message key="label.webmanage.transrightList.id"/></td>
								<td  align="center" bgcolor="#ECE9D8">
									<bean:message key="label.webmanage.overEntryList.opt.name" />
								</td>
								<td  align="center" bgcolor="#ECE9D8">
									<bean:message key="label.webmanage.ctlEntryList.opt.id" />
								</td>
								<td align="center" bgcolor="#ECE9D8">
									<bean:message key="label.webmanage.overStepList.opt.name" />
								</td>
								<td  align="center" bgcolor="#ECE9D8">
									<bean:message key="label.webmanage.overStepList.opt.owner" />
								</td>
								<td  align="center" bgcolor="#ECE9D8">
									<bean:message key="label.webmanage.overStepList.opt.startDate" />
								</td>
								<td align="center" bgcolor="#ECE9D8">
									<bean:message key="label.webmanage.overStepList.opt.dueDate" />
								</td>
								<td  align="center" bgcolor="#ECE9D8">
									<bean:message key="label.webmanage.overStepList.opt.finishDate" />
								</td>
							</tr>
							
									<logic:iterate id="result" name="pageResult" property="list" indexId="ind">
										<tr>
										<td align="center" bgcolor="#F8F8F8" height="25"><script>document.write(<bean:write name="ind"/>+1)</script></td>
											<td align="center" height="25" bgcolor="#F8F8F8">
												&nbsp;<bean:write name="result" property="osWfentry.osWfdefine.name" />
											</td>
											<td align="center" bgcolor="#F8F8F8">
												&nbsp;<bean:write name="result" property="osWfentry.id" />
											</td>
											<td align="center" bgcolor="#F8F8F8">
												&nbsp;<bean:write name="result" property="stepName" />
											</td>
											<td align="center" bgcolor="#F8F8F8">
												&nbsp;<bean:write name="result" property="caller" />
											</td>
											<td align="center" bgcolor="#F8F8F8">
												&nbsp;<bean:write name="result" property="startDate" format="yyyy-MM-dd HH:mm:ss"/>
											</td>
											<td align="center" bgcolor="#F8F8F8">
												&nbsp;<bean:write name="result" property="dueDate" format="yyyy-MM-dd HH:mm:ss"/>
											</td>
											<td align="center" bgcolor="#F8F8F8">
												&nbsp;<bean:write name="result" property="finishDate" format="yyyy-MM-dd HH:mm:ss"/>
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
