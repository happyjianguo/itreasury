<%@ page contentType="text/html; charset=GBK" import="com.iss.inut.workflow.entity.po.*,com.iss.inut.base.*,java.util.*"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title><bean:message key="label.webmanage.traceStepList.title" /></title>
		<SCRIPT type="text/javascript" src="<%=request.getContextPath()%>/workflow/js/wz_jsgraphics.js"></SCRIPT>
		<link rel="stylesheet" type="text/css" href="../framework/css/css.css">
		<script language="javascript">
				function showEntryList(){
				
					document.all["operate"].value="showEntryList";						
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
					<html:form action="WFCtrl.do" method="post">
						<input name="operate" type="hidden" value="" />
						<input name="selectedDefineID" type="hidden" value="<bean:write name="selectedDefineID" />" />
						<table width="100%" border="0" cellspacing="3" cellpadding="2">
							<tr>
								<td colspan="22" height="22" bgcolor="#C4DAFA" style="font-size:12px; font-weight:bold; border-bottom:1px solid #fff;">
									<bean:message key="label.webmanage.traceStepList.head" />
								</td>
							</tr>
						</table>
						<table align="center" width="100%" border="0" cellpadding="3" cellspacing="2" class="tblist">
							<tr class="thead">
								<td width="15%" align="center" bgcolor="#ECE9D8">
									<bean:message key="label.webmanage.traceStepList.opt.id" />
								</td>
								<td width="15%" align="center" bgcolor="#ECE9D8">
									<bean:message key="label.webmanage.traceStepList.opt.name" />
								</td>
								<td width="15%" align="center" bgcolor="#ECE9D8">
									<bean:message key="label.webmanage.traceStepList.opt.caller" />
								</td>
								<td width="15%" align="center" bgcolor="#ECE9D8">
									<bean:message key="label.webmanage.traceStepList.opt.startDate" />
								</td>
								<td width="15%" align="center" bgcolor="#ECE9D8">
									<bean:message key="label.webmanage.traceStepList.opt.dueDate" />
								</td>
								<td width="15%" align="center" bgcolor="#ECE9D8">
									<bean:message key="label.webmanage.traceStepList.opt.finishDate" />
								</td>
								<td width="15%" align="center" bgcolor="#ECE9D8">
									<bean:message key="label.webmanage.traceStepList.opt.state" />
								</td>
							</tr>
							
									<logic:iterate id="result" name="steplist">
										<tr>
											<td align="center" height="25" bgcolor="#F8F8F8">
												&nbsp;<bean:write name="result" property="stepCode" />
											</td>
											<td align="center" bgcolor="#F8F8F8">
												&nbsp;<bean:write name="result" property="stepName" />
											</td>
											<td align="center" bgcolor="#F8F8F8">
												&nbsp;<bean:write name="result" property="callerName" />
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
											<td align="center" bgcolor="#F8F8F8">&nbsp;
												<logic:equal value="Queued" name="result" property="status">
													<bean:message key="label.webmanage.common.state.wfstep.queued" />
												</logic:equal>
												<logic:equal value="Finished" name="result" property="status">
													<bean:message key="label.webmanage.common.state.wfstep.finished" />
												</logic:equal>
											</td>
										</tr>
									</logic:iterate>
						
													
							
						</table>
						
						<table align="center" width="100%" border="0" cellspacing="1" class="a">
							<tr>
								<td align="center">
									<div id="workflowCanvas" style="position:relative;height:566px;width:508px;">
										<img src="<%=request.getContextPath()%>/wfDefine.do?operate=preview&WFID=<bean:write name="selectedDefineID"/>" border=0 />
									</div>
								</td>
							</tr>
						</table>
						<table width="100%" border="0" cellspacing="0" cellpadding="3">
						<tr>
								<td colspan="22" align="center">
									<input name="back" type="button" value="<bean:message key="label.webmanage.common.back" />" onclick=showEntryList()>&nbsp;
								</td>
						</tr>
						</table>	
					</html:form>
				</td>
			</tr>
		</table>
		<script type="text/javascript">
			var currentStepIds = [];
			<%
				List list=(ArrayList)request.getAttribute("steplist");
				for(int i = 0; i < list.size(); i++){
					AbstractStep model=(AbstractStep)list.get(i);
					if(model.getStatus().equals("Queued") && model.getClass().toString().equals("class com.iss.inut.workflow.entity.po.OsCurrentstep")){
			%>
			currentStepIds[<%=i%>] = <%=model.getStepCode()%>;
			<%
					}
				}
			%>
			
			var xmlHttp =new ActiveXObject("MSXML2.XMLHTTP") ;
			var async = true;
			xmlHttp.open("GET", "<%=request.getContextPath()%>/wfDefine.do?operate=layout&WFID=<bean:write name="selectedDefineID"/>", async);
			xmlHttp.onreadystatechange = function () {
			    if (xmlHttp.readyState == 4){
			        //set up graphics
			        var jg = new jsGraphics("workflowCanvas");
			        jg.setColor("#ff0000");
			        jg.setStroke(3);
			        var xAdjust = -0;
			        var yAdjust = -0;
			        var widthAdjust = 0;
			        var heightAdjust = 0;
			        
			        //parsing xml and paint;
			        var cells = xmlHttp.responseXML.getElementsByTagName("cell");
			        for(var i = 0; i < currentStepIds.length; i++){
			            for(var n = 0; n < cells.length; n++){
			                var cell = cells[n];
			                if(cell.getAttribute("type") == "StepCell" && currentStepIds[i] == parseInt(cell.getAttribute("id"))){
			                    jg.drawRect(parseInt(cell.getAttribute("x")) + xAdjust, parseInt(cell.getAttribute("y")) + yAdjust, parseInt(cell.getAttribute("width")) + widthAdjust, parseInt(cell.getAttribute("height")) + heightAdjust);
			                }
			            }
			        }
			        jg.paint();
			    }
			};
			xmlHttp.send(null);    
		</script>
	</body>
</html>
