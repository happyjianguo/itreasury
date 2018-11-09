<%--
 ҳ������ ��d002-c.jsp
 ҳ�湦�� : ������������-������������ ��ѯ��λ��ϸ��Ϣ����ҳ��
 ��    �� ��gqzhang
 ��    �� ��2004��1��
 ����˵�� ��ʵ�ֲ���˵����
 �޸���ʷ ��
--%>
<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="java.sql.*,
                 javax.servlet.*,
				 com.iss.itreasury.util.*,
				 com.iss.itreasury.loan.util.*,
                 com.iss.itreasury.ebank.obdiscountapply.bizlogic.*,
                 com.iss.itreasury.ebank.obdiscountapply.dataentity.*,
				 com.iss.itreasury.ebank.obsystem.bizlogic.*,
				 com.iss.itreasury.ebank.obsystem.dataentity.*,
                 com.iss.itreasury.ebank.util.*,
				 com.iss.itreasury.ebank.obsystem.dataentity.OBClientInfo,
				 com.iss.itreasury.loan.loancommonsetting.dataentity.ClientInfo"
%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%
	/* ����̶����� */
	String strTitle = "[��������]";
%>					
<%
  try
  {
	Log.print("*******����ҳ��--ebank/loan/discountapply/d002-c.jsp*******");
	
	   /* �û���¼��� */
        if (sessionMng.isLogin() == false)
        {
            OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E002");
        	out.flush();
        	return;
        }

        /* �ж��û��Ƿ���Ȩ�� */
        if (sessionMng.hasRight(request) == false)
        {
        	out.println(sessionMng.hasRight(request));
        	OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E003");
        	out.flush();
        	return;
        }
		//�������
		String strTemp = "";
		long lClientID = sessionMng.m_lClientID;
		//Log.print("=============�ͻ�ID��"+lClientID);
		String strBackPage = "";
		
		strTemp = (String)request.getAttribute("strBackPage");
		if( strTemp != null && strTemp.length() > 0 )
		{
			strBackPage = DataFormat.toChinese(strTemp.trim());
		}
		
		OBSystemHome  obSystemHome = null;
        OBSystem      obSystem = null;
        obSystemHome = (OBSystemHome)EJBObject.getEJBHome("OBSystemHome");
        obSystem = obSystemHome.create();
        ClientInfo clientInfo = null;
		
		clientInfo = obSystem.findClientByID(lClientID);
		
		if(clientInfo != null)
		{
		  request.setAttribute("resultInfo",clientInfo);
		  
		  
		  /* ��ȡ�����Ļ��� */
	      ServletContext sc = getServletContext();
		  /* ���÷��ص�ַ */
		  RequestDispatcher rd = sc.getRequestDispatcher(PageController.getDispatcherURL(new PageControllerInfo(sessionMng,"/loan/discountapply/d003-v.jsp")));
	      /* forward�����ҳ�� */
	      rd.forward(request, response); 
		  
		}
		else 
	    {
		  OBHtml.showCommonMessage(out, sessionMng, strTitle, "", 1, "Gen_E001");
		  return;
	    }
    }
	catch(IException ie) 
	{
		OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
		return;
    }
%>


