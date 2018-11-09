<%@ page contentType="text/html; charset=gbk" language="java" errorPage="" %>
<%@ page import="java.util.*" %>
<%@ page import="com.iss.itreasury.util.ActionMessages"%>
<%@ page import="com.iss.itreasury.util.Constant"%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<%
String strTemp = "";
String strValidateComfirmMessage1 = "";
String strValidateComfirmMessage2 = "";
String strValidateComfirmMessage3 = "";
StringBuffer strAllMessage = new StringBuffer();

strTemp = (String)request.getAttribute(Constant.ComfirmMessageValidateName.Message1);
if (strTemp != null && strTemp.length() > 0)
{
	strValidateComfirmMessage1 = strTemp;
}
else {
	strValidateComfirmMessage1 = "0";
}

strTemp = (String)request.getAttribute(Constant.ComfirmMessageValidateName.Message2);
if (strTemp != null && strTemp.length() > 0)
{
	strValidateComfirmMessage2 = strTemp;
}
else {
	strValidateComfirmMessage2 = "0";
}

strTemp = (String)request.getAttribute(Constant.ComfirmMessageValidateName.Message3);
if (strTemp != null && strTemp.length() > 0)
{
	strValidateComfirmMessage3 = strTemp;
}
else {
	strValidateComfirmMessage3 = "0";
}
strAllMessage.append("&" + Constant.ComfirmMessageValidateName.Message1 + "=" + strValidateComfirmMessage1);
strAllMessage.append("&" + Constant.ComfirmMessageValidateName.Message2 + "=" + strValidateComfirmMessage2);
strAllMessage.append("&" + Constant.ComfirmMessageValidateName.Message3 + "=" + strValidateComfirmMessage3);


ActionMessages messages = null;
messages = sessionMng.getActionMessages();
if(messages != null && !messages.isEmpty())
{
    Iterator itTemp = messages.getMessages().iterator();

	StringBuffer sbTemp = new StringBuffer(128);

    while(itTemp.hasNext())
    {
        strTemp = (String)itTemp.next();

		//去除回车换行符
		StringBuffer sbTempForConvert = new StringBuffer(strTemp);

		boolean bFlag = true;
		while (bFlag)
		{
			bFlag = false;

			int nPoint = sbTempForConvert.toString().indexOf("\r\n");
			if (nPoint > -1)
			{
				sbTempForConvert.replace(nPoint, nPoint + 2, " ");

				bFlag = true;
				
				continue;
			}

			nPoint = sbTempForConvert.toString().indexOf("\r");
			if (nPoint > -1)
			{
				sbTempForConvert.replace(nPoint, nPoint + 1, " ");

				bFlag = true;
				
				continue;
			}

			nPoint = sbTempForConvert.toString().indexOf("\n");
			if (nPoint > -1)
			{
				sbTempForConvert.replace(nPoint, nPoint + 1, " ");

				bFlag = true;
				
				continue;
			}
		}

		int nPoint = 0;

		while (nPoint >= 0)
		{
			nPoint = sbTempForConvert.toString().indexOf("\"", nPoint+1);

			if (nPoint > -1)
			{
				if (!sbTempForConvert.substring(nPoint - 1, nPoint).equalsIgnoreCase("\\"))
				{
					sbTempForConvert.replace(nPoint, nPoint + 1, "\\\"");
				}
			}
		}

		sbTemp.append(sbTempForConvert);
        sbTemp.append("\\n");
    }
%>
	<script language="JavaScript">
	
		if (confirm("<%=sbTemp.toString()%>"))
		{
			var strURL = "<%=sessionMng.m_CurrentAllURL.toString().replaceAll("\"","\\\\\"").toString()%><%=strAllMessage.toString()%>";
			var urls = strURL.split("&");

			var form1 = document.createElement("form");
			form1.action = urls[0];
			for(var i=1; i<urls.length; i++){
				form1.innerHTML = "<input type='hidden' name='"+ urls[i] +"' value='"+ urls[i++] +"'>";
			}
			form1.submit();
			
			//window.location.href = "<%=sessionMng.m_CurrentAllURL.toString().replaceAll("\"","\\\\\"").toString()%><%=strAllMessage.toString()%>";
		}
		else {
			window.history.back(-1);
		}
	</script>
<%
    //使用后立即清除
    messages.clear();
}
%>

