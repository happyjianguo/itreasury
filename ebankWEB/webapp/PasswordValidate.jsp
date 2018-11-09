<%@page language="java" pageEncoding="GBK"%>
<%@ page import="com.iss.itreasury.util.PageControllerInfo"%>
<%@ page import="com.iss.itreasury.util.PageController"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtml"%>
<%@ page import="com.iss.itreasury.ebank.pwconfig.bizlogic.PWConfigBean"%>
<%@ page import="com.iss.itreasury.util.Constant"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.Map"%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%String strContext = request.getContextPath();%>
<HTML>
<body>
<%
try {
	String strTitle = "密码校验";
	Map map = new HashMap();
	// 用户登录检测
	if (sessionMng.isLogin() == false) {
		OBHtml.showCommonMessage(out, sessionMng, strTitle, "", 1,"Gen_E002");
		out.flush();
		return;
	}
	
	// 判断用户是否有权限 
	if (sessionMng.hasRight(request) == false) {
		out.println(sessionMng.hasRight(request));
		OBHtml.showCommonMessage(out, sessionMng, strTitle, "", 1,"Gen_E003");
		out.flush();
		return;
	}

	PWConfigBean configBean = new PWConfigBean();
	String signatureValue = request.getParameter("certDN");	
		if (signatureValue != null && signatureValue.length() > 0 )
		{
			String enCode = new String(signatureValue.getBytes("GBK"),"UTF-8");
			String[] nameValueArray = enCode.split(",");
			for (int i = 0; i < nameValueArray.length; i++) {
				if (nameValueArray[i].length()>0) {
					String[] nameValue = nameValueArray[i].split("=");
					if (nameValue.length > 1) {
						map.put(nameValue[0], nameValue[1]);
					} else {
						map.put(nameValue[0], "");
					}
				}
				
			}
					
		    signatureValue = map.get("CN").toString();
		}
	configBean.setSignatureValue(signatureValue);
	String strPwdConfig[] = configBean.passwordValidate(sessionMng.m_lOfficeID, sessionMng.m_lUserID);
	if(strPwdConfig[0].equals(PWConfigBean.PasswordAwokeMode.ALERT)){
		sessionMng.getActionMessages().addMessage(strPwdConfig[1]);
		response.sendRedirect(strContext + "/ChangePassword.jsp?strSuccessPageURL=ebankMain.jsp");
		return;
	}
	else if(strPwdConfig[0].equals(PWConfigBean.PasswordAwokeMode.CONFIRM)){
%>
	<script language="JavaScript">
		if(confirm("<%=strPwdConfig[1]%>")){
			window.location.href='ChangePassword.jsp?strSuccessPageURL=ebankMain.jsp';
		}else{
			window.location.href='ebankMain.jsp';
		}
	</script>
<%
	}
	else if(strPwdConfig[0].equals(PWConfigBean.PasswordAwokeMode.CNERROR)){
		sessionMng.getActionMessages().addMessage(strPwdConfig[1]);
		response.sendRedirect(strContext + "/Index.jsp");
		return;
	}else{
		response.sendRedirect(strContext + "/ebankMain.jsp");
		return;
	}
} catch (Exception e) {
	e.printStackTrace();
	sessionMng.getActionMessages().addMessage("验证密码失败");
	response.sendRedirect(strContext + "/Index.jsp");
	return;
}
%>
</BODY>
</HTML>