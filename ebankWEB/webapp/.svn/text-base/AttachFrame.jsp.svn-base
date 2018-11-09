<%@ page contentType="text/html; charset=GBK"%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%/*
			 response.setHeader("Cache-Control","no-stored");
			 response.setHeader("Pragma","no-cache");
			 response.setDateHeader("Expires",0);*/

			%>

<%@page import="java.util.*,
                com.iss.itreasury.util.*,
                com.iss.itreasury.common.attach.bizlogic.*,
                com.iss.itreasury.common.attach.dataentity.*"%>
<%String strContext = request.getContextPath();%>
<link rel="stylesheet" type="text/css" href="<%=strContext%>/workflow/css/css.css">
<%long ID = -1; //合同或者贷款的申请ID
			long typeID = -1; //附件类型
			String buttonCaption = "";
			boolean showOnly = false;
			String control = null;
			long ModuleID = -1;
			long TransTypeID = -1;
			long TransSubTypeID = -1;
			long CurrencyID = -1;
			long OfficeID = -1;
			long clientID = -1;  //客户ID
			long ParentID = -1;
			String transCode = "";
			String firstIn = ""; //第一次载入页面
			//取参数变量
			request.setCharacterEncoding("gb2312");
			String strTmp = "";
			strTmp = (String) request.getParameter("lID");
			if (strTmp != null && strTmp.length() > 0) {
				ID = Long.parseLong(strTmp.trim());
			}
			strTmp = (String) request.getParameter("lTypeID");
			if (strTmp != null && strTmp.length() > 0) {
				typeID = Long.parseLong(strTmp.trim());
			}
			strTmp = (String) request.getParameter("sCaption");
			if (strTmp != null && strTmp.length() > 0) {
				buttonCaption = strTmp.trim();
			}
			strTmp = (String) request.getParameter("showOnly");
			if (strTmp != null && strTmp.length() > 0) {
				if (strTmp.trim().equals("true"))
					showOnly = true;
			}
			strTmp = (String) request.getParameter("control");
			if (strTmp != null && strTmp.length() > 0) {
				control = strTmp.trim();
			}
			strTmp = (String) request.getParameter("ModuleID");
			if (strTmp != null && strTmp.length() > 0) {
				ModuleID = Long.parseLong(strTmp.trim());
			}
			strTmp = (String) request.getParameter("TransTypeID");
			if (strTmp != null && strTmp.length() > 0) {
				TransTypeID = Long.parseLong(strTmp.trim());
			}
			strTmp = (String) request.getParameter("TransSubTypeID");
			if (strTmp != null && strTmp.length() > 0) {
				TransSubTypeID = Long.parseLong(strTmp.trim());
			}
			strTmp = (String) request.getParameter("CurrencyID");
			if (strTmp != null && strTmp.length() > 0) {
				CurrencyID = Long.parseLong(strTmp.trim());
			}
			strTmp = (String) request.getParameter("OfficeID");
			if (strTmp != null && strTmp.length() > 0) {
				OfficeID = Long.parseLong(strTmp.trim());
			}
			strTmp = (String) request.getParameter("ClientID");
			if (strTmp != null && strTmp.length() > 0) {
				clientID = Long.parseLong(strTmp.trim());
			}
			strTmp = (String) request.getParameter("ParentID");
			if (strTmp != null && strTmp.length() > 0) {
				ParentID = Long.parseLong(strTmp.trim());
			}
			strTmp = (String) request.getParameter("transCode");
			if (strTmp != null && strTmp.length() > 0) {
				transCode = (strTmp.trim());
			}
			firstIn = (String) request.getParameter("firstIn");

			if (firstIn == null || !firstIn.equals("false")
					|| !firstIn.equals("null")) {

				firstIn = "true";
			} else {
				firstIn = "false";

			}
			ParentID = ID;
			AttachInfo aInfo = new AttachInfo();
			aInfo.setModuleID(ModuleID);
			aInfo.setTransTypeID(TransTypeID);
			aInfo.setTransSubTypeID(TransSubTypeID);
			aInfo.setParentID(ParentID);
			aInfo.setParentType(typeID);
			aInfo.setCurrencyID(CurrencyID);
			aInfo.setOfficeID(OfficeID);
			aInfo.setTransCode(transCode);
			aInfo.setNClientID(clientID);
			java.util.Date dtCurrent = Env.getSystemDateTime();
%>
<BODY class="">
	<link rel="stylesheet" href="/webob/css/style.css" type="text/css">
	<form name="attchForm">		
			<input type="hidden" name="transCode" value='<%=transCode%>'>
			<input type="hidden" name="firstIn" value='<%=firstIn%>'>
			<input type="hidden" name="ParentID" value="<%=ParentID%>">
			<input type="hidden" name="ModuleID" value="<%=ModuleID%>">
			<input type="hidden" name="TransTypeID" value="<%=TransTypeID%>">
			<input type="hidden" name="TransSubTypeID" value="<%=TransSubTypeID%>">
			<input type="hidden" name="CurrencyID" value="<%=CurrencyID%>">
			<input type="hidden" name="OfficeID" value="<%=OfficeID%>">
			<input type="hidden" name="sCaption" value="<%=buttonCaption%>">
			<input type="hidden" name="ClientID" value="<%=clientID%>">
		<table width=100%>			
			<TR>
				<TD colspan="2">
					<%AttachOperation attachOperation = new AttachOperation();
					DownLoadFileNameEncryptionAndDecrypt en = new DownLoadFileNameEncryptionAndDecrypt();
			Collection c = null;
			AttachInfo info = new AttachInfo();
			c = attachOperation.findByOBCondition(aInfo);

			if (c == null || c.size() < 1) {
				c = (Collection) request.getAttribute("AttachInfo");
			}

			if (c != null) {
				Iterator it = c.iterator();
				while (it.hasNext()) {
					info = (AttachInfo) it.next();

					%>
					<A href="<%=strContext%>/DownLoadServlet?FileID=<%=en.getEncString(String.valueOf(info.getFileID())) %>" target="_blank"><%=info.getShowName()%></A>
					<font color="#999999">-</font>
					<font color="#999999"><%=info.getRealName()%></font>&nbsp;&nbsp;
					<%}
			}

			%>
				</TD>
				<TD align="right" width="20%">
					<INPUT name=btnLink class="button1" <% if (showOnly){%> disabled <%}%> onclick="Javascript:upload()" type="button" value="<%=buttonCaption%>">
				</TD>
			</TR>
		</table>
	</form>
</BODY>

	<script language="javascript">
    //把交易信息赋给页面
    init();
	function init()
	{
		document.attchForm.transCode.value = '<%=transCode%>';
		window.parent.document.getElementById("strTransTypeNo").value = '<%=transCode%>';
		if(false == <%=firstIn%>)
		{
			document.attchForm.action = "<%=strContext%>/common/c107-c.jsp?transCode=" + '<%=transCode%>';
			document.attchForm.submit();
		}
	}
      function upload()
      {
      	var transCode;
      	transCode = '<%=transCode%>';
      	if('<%=transCode%>' == "" || '<%=transCode%>' == "null")
      	{
      		transCode = "<%=dtCurrent.getTime()%>";
      	}
      	window.open('<%=strContext%>/common/c101-c.jsp?ModuleID=<%=ModuleID%>&TransTypeID=<%=TransTypeID%>&TransSubTypeID=<%=TransSubTypeID%>&CurrencyID=<%=CurrencyID%>&OfficeID=<%=OfficeID%>&ClientID=<%=clientID%>&ParentID=<%=ParentID%>&lID=<%=ParentID%>&transCode='+ transCode + '&ParentIDType=<%=typeID%>','','width=800,height=600,status=yes,toolbar=no,menubar=no,location=no,resizable=yes,scrollbars =yes');
      }
</script>