<%--
/*
 * �������ƣ�C826.jsp
 * ����˵����ϵͳ����-�տ�����޸Ŀ���ҳ��
 * �������ߣ�����
 * ������ڣ�2003��10��20��
 */
--%>

<!--Header start-->
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ include file="/common/common.jsp" %>
<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="com.iss.itreasury.ebank.obsystem.dataentity.*,
				 com.iss.itreasury.ebank.obsystem.dao.*,
				 com.iss.itreasury.ebank.util.*,
                 java.rmi.*,
                 java.util.*,
                 com.iss.itreasury.util.*,
                 java.sql.*"

%>
<%@ page import="com.iss.itreasury.util.PageControllerInfo"%>
<%@ page import="com.iss.itreasury.util.PageController"%>
<% String strContext = request.getContextPath();%>
<!--Header end-->

<%
	//�̶�����
	String strTitle = "[�տ����ά��--�����տ����]";
%>

<%
	/* �û���¼�����Ȩ��У�� */
	try 
	{
        /* �û���¼��� */
        if (sessionMng.isLogin() == false)
        {
        	OBHtml.showCommonMessage(out, sessionMng, strTitle, "", 1, "Gen_E002");
        	out.flush();
        	return;
        }

        //�ж��û��Ƿ���Ȩ�� 
        if (sessionMng.hasRight(request) == false)
        {
            out.println(sessionMng.hasRight(request));
        	OBHtml.showCommonMessage(out, sessionMng,strTitle, "", 1, "Gen_E003");
        	out.flush();
        	return;
		}
	 } 
	 catch (Exception e) 
	{
        OBHtml.showExceptionMessage(out,sessionMng, (IException)e, strTitle,"",1);
		return;
    }
%>


<%
	try
	{
		long lID = Long.parseLong(request.getParameter("txtID").trim());

      	//��ʼ����ѯ�༰��ѯ�����
		OBSystemDao obSystemDao = new OBSystemDao(); 	
		ClientCapInfo info = new ClientCapInfo();
		
		//�������з�����ѯ���
		info = 	obSystemDao.findPayeeInfoByID(lID);
%>

<%
		//�������б���������
		if (info != null)
		{
			request.setAttribute("ClientCapInfo",info);
		}
		//��ȡ�����Ļ���
      	//ServletContext sc = getServletContext();
      	//���÷��ص�ַ
	  	RequestDispatcher rd = null;
	  	String strTemp = request.getParameter("txtIsCPF").trim();
	  	if (strTemp.equalsIgnoreCase("true"))
	  	{
			Log.print("�������û�����S827-Opt.jsp");
			//����ҳ��ַ�ʱ��Ҫ�õ���ʵ��
			PageControllerInfo pageControllerInfo = new PageControllerInfo();
			pageControllerInfo.setSessionMng(sessionMng);
			pageControllerInfo.setUrl(strContext + "/system/V827.jsp");
			//�ַ�
			rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
	  	}
	  	else
	  	{
			Log.print("���������û�����S837-Opt.jsp");
			//����ҳ��ַ�ʱ��Ҫ�õ���ʵ��
			PageControllerInfo pageControllerInfo = new PageControllerInfo();
			pageControllerInfo.setSessionMng(sessionMng);
			pageControllerInfo.setUrl(strContext + "/system/V837.jsp");
			//�ַ�
			rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
	  	}
      	//forward�����ҳ��
      	rd.forward(request, response);
   	}
   	catch(IException ie)
   	{
        OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
		return;
   	}

%>

