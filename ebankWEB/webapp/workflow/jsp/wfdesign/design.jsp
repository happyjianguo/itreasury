<%@ page contentType="text/html;charset=GBK"%>
<%
	String url = request.getRequestURL().toString();
	url = url.substring(0, url.lastIndexOf(request.getServletPath()));
%>

<HTML>
	<HEAD>
		<TITLE>�������</TITLE>
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
			//�رմ򿪴����Ժ�ˢ�¸�ҳ��
			window.onunload=function(){
				if(window.opener && !window.opener.closed){
				//modified by kevin(������)2011-05-30���������ԭ�������ڸ�����Ϊ������һҳ���õ���ҳ��ʱ���رյ�ǰ�����Ҳ���selectWfDefineList����������������޸�
					//window.opener.location.reload();//���ַ�ʽ��������ķ�ʽ��
					//window.opener.location.href=window.opener.location.href;
					window.opener.location.href="<%=url%>/wfDefine.do?operate=selectWfDefineList";
				}
			}
		</script>
	</body>
</html>
