<%--
 ҳ������ ��showbankaccount.jsp
 ҳ�湦�� : ��ѯ�����˻����
 ��    �� ��zcwang
 ��    �� ��2008-4-24
 ����˵�� ��ʵ�ֲ���˵����
				1�����Ӳ���
 �޸���ʷ ��
--%>
<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="com.iss.itreasury.util.Log"%>
<%@ page import="com.iss.itreasury.ebank.util.*"%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<%
	
    try
	{
			/* �û���¼��� */
        if (sessionMng.isLogin() == false)
        {
            OBHtml.showCommonMessage(out, sessionMng, "", "",1, "Gen_E002");
        	out.flush();
        	return;
        }
		

        /* �ж��û��Ƿ���Ȩ�� */
        if (sessionMng.hasRight(request) == false)
        {
        	out.println(sessionMng.hasRight(request));
        	OBHtml.showCommonMessage(out, sessionMng, "", "",1, "Gen_E003");
        	out.flush();
        	return;
        }
	String strNextPageURL = "";
	String strTemp = null;	
	String strAction = "";											//��ò�����ת����
	strTemp = (String)request.getAttribute("action");						//��¼��ID
	if (strTemp != null && strTemp.trim().length() > 0)
	{
		strAction = strTemp;
	}
	String indexUrl = request.getContextPath();
	if(strAction.equals("bankacount"))
	{
		//strNextPageURL =indexUrl+ "/bankportal/v090-account_ebank.jsp?moduleid="+sessionMng.m_lModuleID+"&clientid="+sessionMng.m_lClientID+"&officeid="+sessionMng.m_lOfficeID;
		strNextPageURL =indexUrl+ "/bankportal/v090-account_ebank.jsp";
	}
	else if(strAction.equals("subbankacount"))
	{
		//strNextPageURL =indexUrl+ "/bankportal/v090-subaccount_ebank.jsp?moduleid="+sessionMng.m_lModuleID+"&clientid="+sessionMng.m_lClientID+"&officeid="+sessionMng.m_lOfficeID;
		strNextPageURL =indexUrl+ "/bankportal/v090-subaccount_ebank.jsp";
	}
	Log.print("Next Page URL:"+strNextPageURL);	
	response.sendRedirect(strNextPageURL);
		
	}
	catch( Exception exp )
	{
		exp.printStackTrace();
	}	
%>