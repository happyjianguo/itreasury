<%@ page contentType="text/html;charset=GBK"%>
<%
	String url = request.getRequestURL().toString();
	url = url.substring(0, url.lastIndexOf(request.getServletPath()));
%>

<HTML>
	<HEAD>
		<TITLE>流程设计</TITLE>
	<BODY>

		<div style="top:0;left:0;">
			
			<applet WIDTH="100%" HEIGHT="100%" archive="iss-workflow-applet-2.0.jar" code="com.iss.inut.workflow.designer.client.MainApplet">
				<PARAM NAME="<%=com.iss.inut.workflow.designer.util.Constants.BizInfo.PARAM_HOST%>" VALUE="<%=url%>" />
				<PARAM NAME="<%=com.iss.inut.workflow.designer.util.Constants.BizInfo.PARAM_WFSERVLET%>" VALUE="/wfDesignerServlet?" />
				<PARAM NAME="<%=com.iss.inut.workflow.designer.util.Constants.BizInfo.PARAM_USER%>" VALUE="<%=com.iss.inut.workflow.constants.ParamUtils.getLoginName(request)%>" />
				<PARAM NAME="<%=com.iss.inut.workflow.designer.util.Constants.BizInfo.PARAM_ACTION%>" VALUE="<%=request.getParameter(com.iss.inut.workflow.designer.util.Constants.BizInfo.PARAM_ACTION)%>" />
				<PARAM NAME="<%=com.iss.inut.workflow.designer.util.Constants.BizInfo.PARAM_WFID%>" VALUE="<%=request.getParameter(com.iss.inut.workflow.designer.util.Constants.BizInfo.PARAM_WFID)%>" />
				<PARAM NAME="<%=com.iss.inut.workflow.designer.util.Constants.BizInfo.PARAM_OFFICEID%>" VALUE="<%=com.iss.inut.workflow.constants.ParamUtils.getOfficeId(request)%>" />
				<PARAM NAME="<%=com.iss.inut.workflow.designer.util.Constants.BizInfo.PARAM_CLIENTID%>" VALUE="<%=com.iss.inut.workflow.constants.ParamUtils.getClientId(request)%>" />
			</applet>
		</div>
		<script type="text/javascript">
			//关闭打开窗口以后刷新父页面
			window.onunload=function(){
				if(window.opener && !window.opener.closed){
				//modified by kevin(刘连凯)2011-05-30，针对由于原方法中在父窗口为单击下一页所得到的页面时，关闭当前窗口找不到selectWfDefineList（）方法的问题的修改
					//window.opener.location.reload();//该种方式不如下面的方式好
					//window.opener.location.href=window.opener.location.href;
					window.opener.location.href="<%=url%>/wfDefine.do?operate=selectWfDefineList";
				}
			}
		</script>
	</body>
</html>
