<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<jsp:include page="/ShowMessage.jsp" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/workflow/css/css.css">
<html>
	<head>
	<title><bean:message key="label.webmanage.historystepList.title"/></title>
	<script language="JavaScript" src="<%=request.getContextPath()%>/workflow/js/date/common.js"></script>
	<script language="javascript">
		function formedit(id,entryId,actioncode,stepcode)
		{
			document.forms[0].osTaskId.value = id ;
			document.forms[0].osentryId.value=entryId;
			document.forms[0].osActionId.value=actioncode;
			//document.forms[0].osWfId.value=wfid;
			document.forms[0].osStepId.value=stepcode;
			document.forms[0].operate.value="toDisplayForm";
			document.forms[0].submit();
		}
		function find()
		{
			if(!check())
			{
				alert('<bean:message key="label.webmanage.currentstepList.alerttime"/>');
				return false;
			}
			document.forms[0].operate.value="selListByCondition";
			document.forms[0].submit();
		}
		function check()
		{
			var startdate=document.forms[0].elements['strbegindate'].value;
			var enddate=document.forms[0].elements['strenddate'].value;
			if(checkEmpty(startdate) && checkEmpty(enddate)){
				if(!checkDate(startdate,enddate))
					return false;
			}			
			return true;
		}
		function checkEmpty(values){
			if(values=="")				
				return false
			return true;			
		}
		function checkDate(startDate,endDate){			
			if(getNum(startDate)<=getNum(endDate))
				return true;
			return false;
		}	
		function getNum(str)
		{			
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
					<html:form action="historyStep.do">
					<input name="osentryId" type="hidden">
					<input name="osActionId" type="hidden">
					<input name="osTaskId" type="hidden">					
					<!-- input name="osWfId" type="hidden"-->
					<input name="osStepId" type="hidden">
					<input name="operate" type="hidden" value="selectHistoryStepList"/>
						<table width="100%" border="0" cellspacing="3" cellpadding="2">
						<tr>
							<td colspan="22" height="22" bgcolor="#C4DAFA" style="font-size:12px; font-weight:bold; border-bottom:1px solid #fff;">　
								<bean:message key="label.webmanage.historystepList.head"/>
							</td>
						</tr>
						</table>
						<table width="100%" border="0" cellspacing="3" cellpadding="2" class="tblist" id="DataTable">
					<tr class="thead">
					  <td align="center" bgcolor="#ECE9D8"><bean:message key="label.webmanage.common.condition.stepFinishDate"/><bean:message key="label.webmanage.common.condition.startDate"/>				  
                    	<html:text property="strbegindate" readonly="true"/>
                    	<a href="#"><img src="<%=request.getContextPath()%>/workflow/images/calendar.gif" border="0" align="absmiddle" onclick="Container_onclick(document.forms[0].elements['strbegindate'],false)"></a>
                     	<bean:message key="label.webmanage.common.condition.endDate"/>
                    	<html:text property="strenddate" readonly="true"/>
                    	<a href="#"><img src="<%=request.getContextPath()%>/workflow/images/calendar.gif" border="0" align="absmiddle" onclick="Container_onclick(document.forms[0].elements['strenddate'],false)"></a>
                      </td>
                    
						<td align="center"><input type="button" class="button1" name="addTransfer" value="<bean:message key="label.webmanage.common.search"/>" onclick="find()"></td>
					</tr>
					</table>
						<table width="100%" border="0" cellpadding="3" cellspacing="2" class="tblist" id="DataTable">
						<tr class="thead">
						  <td align="center" bgcolor="#ECE9D8"><bean:message key="label.webmanage.transrightList.id"/></td>
						  <td align="center" bgcolor="#ECE9D8"><bean:message key="label.webmanage.historystepList.elementname"/></td>
						  <td align="center" bgcolor="#ECE9D8"><bean:message key="label.webmanage.historystepList.workflowid"/></td>
						  <td align="center" bgcolor="#ECE9D8"><bean:message key="label.webmanage.historystepList.workflowmodename"/></td>
						  <td align="center" bgcolor="#ECE9D8"><bean:message key="label.webmanage.historystepList.caller"/></td>
						  <td align="center" bgcolor="#ECE9D8"><bean:message key="label.webmanage.historystepList.createtime"/></td>
						  <td align="center" bgcolor="#ECE9D8"><bean:message key="label.webmanage.historystepList.finishtime"/></td>
						  <td align="center" bgcolor="#ECE9D8"><bean:message key="label.webmanage.historystepList.status"/></td>
						</tr>
						
						<logic:present name = "pageResult" property="list" scope="request">
							<logic:iterate id = "result" name = "pageResult" property="list" indexId="ind">
							<tr>
							<td align="center" bgcolor="#F8F8F8" height="25"><script>document.write(<bean:write name="ind"/>+1)</script></td>
							<td align="center" bgcolor="#F8F8F8" height="25"><a href="#" onclick="formedit('<bean:write name="result" property="id"/>','<bean:write name="result" property="osWfentry.id"/>','<bean:write name="result" property="actionCode"/>','<bean:write name="result" property="stepCode" />')"><bean:write name="result" property="stepName" /></a></td>
							<td align="center" bgcolor="#F8F8F8"><bean:write name="result" property="osWfentry.id" /></td>
							<td align="center" bgcolor="#F8F8F8"><bean:write name="result" property="osWfentry.osWfdefine.name" /></td>
							<td align="center" bgcolor="#F8F8F8"><bean:write name="result" property="caller" /></td>
							<td align="center" bgcolor="#F8F8F8"><bean:write name="result" property="startDate" format="yyyy-MM-dd HH:mm:ss"/></td>
							<td align="center" bgcolor="#F8F8F8"><bean:write name="result" property="finishDate"format="yyyy-MM-dd HH:mm:ss"/></td>
							<td align="center" bgcolor="#F8F8F8">
						<logic:equal name="result" property="status" value="Queued">
						<bean:message key="label.webmanage.common.state.wfstep.queued" />
						</logic:equal>
						<logic:equal name="result" property="status" value="Finished">
						<bean:message key="label.webmanage.common.state.wfstep.finished" />
						</logic:equal>
							</td>
							
							</tr>
							</logic:iterate>
						</logic:present>
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