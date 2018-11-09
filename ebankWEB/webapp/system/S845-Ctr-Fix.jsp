<%--
/*
 * �������ƣ�S845-Ctr-Fix.jsp
 * ����˵����ǩ�Ͻ�������ύ����ҳ�棨�°�--���ڣ�
 * �������ߣ�����
 * ������ڣ�2011��4��15��
 */
--%>

<!--Header start-->
<%@ page contentType = "text/html;charset=gbk" %>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ page import="com.iss.itreasury.util.*,
                 com.iss.itreasury.ebank.util.*,
				 com.iss.itreasury.ebank.obsystem.dataentity.*,
				 com.iss.itreasury.ebank.obsystem.bizlogic.*"
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
	String strTitle = "����ǩ�Ͻ������";
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

        // �ж��û��Ƿ���Ȩ�� 
        if (sessionMng.hasRight(request) == false)
        {
            out.println(sessionMng.hasRight(request));
        	OBHtml.showCommonMessage(out, sessionMng, strTitle, "", 1, "Gen_E003");
        	out.flush();
        	return;
        }
	
%>

<!--Get Data start-->
<%
   		/* ����Ա�����Ӧ���� */
		double dAmount1 = 0.00; // ���һ
    	double dAmount2 = 0.00; // ����
    	double dAmount3 = 0.00; // �����
    	long lSignUserID1 = -1; // ָ��ǩ����һ
    	long lSignUserID2 = -1; // ָ��ǩ���˶�
    	long lSignUserID3 = -1; // ָ��ǩ������

		/* ��FORM���л�ȡ��Ӧ���� */
		/* ���һ */
		if((request.getParameter("sAmount1") != null) && !((String)request.getParameter("sAmount1")).equals(""))
		{
			dAmount1 = Double.valueOf(DataFormat.reverseFormatAmount((String)request.getParameter("sAmount1"))).doubleValue(); // ���һ
			Log.print("���һ=" + dAmount1);
		}
		/* ���� */
		if((request.getParameter("sAmount2") != null) && !((String)request.getParameter("sAmount2")).equals(""))
		{
			dAmount2 = Double.valueOf(DataFormat.reverseFormatAmount((String)request.getParameter("sAmount2"))).doubleValue(); // ����
			Log.print("����=" + dAmount2);
		}
		/* ����� */
		if((request.getParameter("sAmount3") != null) && !((String)request.getParameter("sAmount3")).equals(""))
		{
			dAmount3 = Double.valueOf(DataFormat.reverseFormatAmount((String)request.getParameter("sAmount3"))).doubleValue(); // �����
			Log.print("�����=" + dAmount3);
		}
		/* ָ��ǩ����һ */
		if((request.getParameter("nSignUserID1") != null) && !((String)request.getParameter("nSignUserID1")).equals(""))
		{
			lSignUserID1 = Long.parseLong((String)request.getParameter("nSignUserID1")); // ָ��ǩ����һ
			Log.print("ָ��ǩ����һ=" + lSignUserID1);
		}
		/* ָ��ǩ���˶� */
		if((request.getParameter("nSignUserID2") != null) && !((String)request.getParameter("nSignUserID2")).equals(""))
		{
			lSignUserID2 = Long.parseLong((String)request.getParameter("nSignUserID2")); // ָ��ǩ���˶�
			Log.print("ָ��ǩ���˶�=" + lSignUserID2);
		}
		/* ָ��ǩ������ */
		if((request.getParameter("nSignUserID3") != null) && !((String)request.getParameter("nSignUserID3")).equals(""))
		{
			lSignUserID3 = Long.parseLong((String)request.getParameter("nSignUserID3")); // ָ��ǩ������
			Log.print("ָ��ǩ������=" + lSignUserID3);
		}
%>
<!--Get Data end-->

<!--Check start-->
<!--Check end-->

<!--Set SignAmountInfo Attribute start-->
<%
		/* ��ʼ����Ϣ�� */
		SignAmountInfo signAmountInfo = new SignAmountInfo();

		/* ��FORM���л�ȡ����Ӧ���� */
		signAmountInfo.setAmount1( dAmount1 ); // ���һ
		signAmountInfo.setAmount2( dAmount2 ); // ����
		signAmountInfo.setAmount3( dAmount3 ); // �����
   		signAmountInfo.setSignUserID1( lSignUserID1 ); // ָ��ǩ����һ
		signAmountInfo.setSignUserID2( lSignUserID2 ); // ָ��ǩ���˶�
		signAmountInfo.setSignUserID3( lSignUserID3 ); // ָ��ǩ������

		/* ��session�л�ȡ��Ӧ���� */
		signAmountInfo.setClientID( sessionMng.m_lClientID );
		signAmountInfo.setCurrencyID( sessionMng.m_lCurrencyID ); 
		signAmountInfo.setInputUserID( sessionMng.m_lUserID );
%>
<!--Set SignAmountInfo Attribute end-->

<!--Access DB start-->
<%
		/* �ύ���ؽ�� */
		long lAddResult = -1;
		
		/* ��ʼ��EJB */
		OBSystemHome obSystemHome = null;
		OBSystem obSystem = null;
		obSystemHome = (OBSystemHome)EJBObject.getEJBHome("OBSystemHome");
		obSystem = obSystemHome.create();
		
		/* ����EJB�����ύ��Ϣ */
		lAddResult = obSystem.addSignAmountForFix(signAmountInfo);
	
%>
<!--Access DB end>

<!--Forward start-->
<%
		if (lAddResult >=  0)
		{
		    /* ��ȡ�����Ļ��� */
		    //ServletContext sc = getServletContext();
		    /* ���÷��ص�ַ */
			//����ҳ��ַ�ʱ��Ҫ�õ���ʵ��
			PageControllerInfo pageControllerInfo = new PageControllerInfo();
			pageControllerInfo.setSessionMng(sessionMng);
			pageControllerInfo.setUrl(strContext + "/system/S858-Opt-Fix.jsp");
			//�ַ�
			RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
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
<!--Forward end-->