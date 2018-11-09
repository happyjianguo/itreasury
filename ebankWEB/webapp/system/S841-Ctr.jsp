<%--
/*
 * �������ƣ�S841-Ctr.jsp
 * ����˵���������������ò��ҿ���ҳ��
 * �������ߣ�����
 * ������ڣ�2003��8��27��
 */
--%>

<!--Header start-->
<%@ page contentType = "text/html;charset=gbk" %>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ page import="java.util.*,
				 javax.servlet.*,
				 com.iss.itreasury.util.*,
                 com.iss.itreasury.ebank.obsystem.dao.*,
                 com.iss.itreasury.ebank.util.*"
%>
<%@ page import="com.iss.itreasury.util.PageControllerInfo"%>
<%@ page import="com.iss.itreasury.util.PageController"%>
<%
    response.setHeader("Cache-Control","no-stored");
	response.setHeader("Pragma","no-cache");
	response.setDateHeader("Expires",0);
%>
<!--Header end-->
<% String strContext = request.getContextPath();%>

<%!
	/* ����̶����� */
	String strTitle = "�˻�������������";
%>

<%
	/* �û���¼�����Ȩ��У�� */
	try 
	{
        /* �û���¼��� */
        if (sessionMng.isLogin() == false)
        {
        	OBHtml.showCommonMessage(out, sessionMng, strTitle, "", 1,"Gen_E002");
        	out.flush();
        	return;
        }

        // �ж��û��Ƿ���Ȩ�� 
        if (sessionMng.hasRight(request) == false)
        {
            out.println(sessionMng.hasRight(request));
        	OBHtml.showCommonMessage(out, sessionMng, strTitle, "", 1,"Gen_E003");
        	out.flush();
        	return;
        }	
%>

<!--Get Data start-->
<%
   		/* ������Ӧ���� */
		long lAccountID = -1; // �����˻�ID
	
		/* ��session�л�ȡ��Ӧ���� */
		long lClientID = sessionMng.m_lClientID;
		long lCurrencyID = sessionMng.m_lCurrencyID;

	
		/* �ӻ�ȡ��Ӧ���� */
		/* �����˻�ID */
		/*if(request.getParameter("lAccountID") != null)
		{
			lAccountID = Long.parseLong((String)request.getParameter("lAccountID")); 
			Log.print("�����˻�ID=" + lAccountID);
		}*/
		if(request.getAttribute("lAccountID") != null)
		{
			try
			{
				lAccountID =Long.parseLong((String)request.getAttribute("lAccountID")); 
				Log.print("�����˻�ID=" + lAccountID);
			}
			catch(Exception e){
				lAccountID = -1;
			}
		}
%>
<!--Get Data end-->

<!--Check start-->
<!--Check end-->

<!--Access DB start-->
<%
		/* ��ʼ�������Ϣ�� */
		Collection cltApf = null;

		/* ��ʼ��EJB 
		OBSystemHome obSystemHome = null;
		OBSystem obSystem = null;
		obSystemHome = (OBSystemHome)EJBObject.getEJBHome("OBSystemHome");
		obSystem = obSystemHome.create();*/
		/*��ʼ����Ϣ��ѯ��*/
		OBSystemDao obSystemDao = new OBSystemDao();
		/* ���÷�����ȡ��ѯ��Ϣ */
		cltApf = (Collection) obSystemDao.queryAccountPrvg(lAccountID);
	
%>
<!--Access DB end>

<!--Forward start-->
<%
		/* �������б��������� */
	    request.setAttribute("cltApf", cltApf);
		request.setAttribute("lAccountID",Long.toString(lAccountID));
	    /* ��ȡ�����Ļ��� */
	    //ServletContext sc = getServletContext();
	    /* ���÷��ص�ַ */
		//����ҳ��ַ�ʱ��Ҫ�õ���ʵ��
		PageControllerInfo pageControllerInfo = new PageControllerInfo();
		pageControllerInfo.setSessionMng(sessionMng);
		pageControllerInfo.setUrl(strContext + "/system/S840-Ipt.jsp");
		//�ַ�
		RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
	    /* forward�����ҳ�� */
	    rd.forward(request, response);
	} 
	catch(IException ie) 
	{
		OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
		return;
	}
%>
<!--Forward end-->