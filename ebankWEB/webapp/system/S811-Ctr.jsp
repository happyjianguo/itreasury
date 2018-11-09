<%--
/**
 * �������ƣ�S811-Ctr.jsp
 * ����˵�����ύƱ�ݲ�ѯ����ҳ��
 * �������ߣ�
 * ������ڣ�
 */
--%>

<!--Header start-->
<%@ page contentType = "text/html;charset=gbk" %>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ page import="java.util.*,
                 javax.servlet.*,
				 com.iss.itreasury.util.*,
                 com.iss.itreasury.ebank.obsystem.dataentity.*,
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
	String strTitle = "[Ʊ�ݲ�ѯ]";
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

<!--Get Data start-->
<%
   	/* ����Ա�����Ӧ���� */
	String strPayerBankNo = ""; // �����˺�
	long lType = -1; // Ʊ������
	String strStartSubmit = null; // ��������-��
	String strEndSubmit = null; // ��������-��
	String lStartVoucherNo = ""; // Ʊ�ݺ�-��
	String lEndVoucherNo = ""; // Ʊ�ݺ�-��

	try 
	{
		/* ��FORM���л�ȡ��Ӧ���� */
		/* �ͻ��������� */
		if(request.getParameter("lType") != null)
		{
			lType= Long.parseLong((String)request.getParameter("lType"));// Ʊ������
			Log.print("�˺�=" + lType);	
		}
			Log.print("begin");
		if(!((String)request.getParameter("sStartSubmit")).equals(""))
		{
			strStartSubmit = (String)request.getParameter("sStartSubmit");// ��������-��
			Log.print("��������-��=" + strStartSubmit);
		}
		if(!((String)request.getParameter("sEndSubmit")).equals(""))
		{
			strEndSubmit = (String)request.getParameter("sEndSubmit");// ��������-��
			Log.print("��������-��=" + strEndSubmit);
		}
		if(!((String)request.getParameter("sStartVoucherNo")).equals(""))
		{
			lStartVoucherNo = (String)request.getParameter("sStartVoucherNo");// Ʊ�ݺ�-��
			Log.print("Ʊ�ݺ�-��=" + lStartVoucherNo);
		}
		if(!((String)request.getParameter("sEndVoucherNo")).equals(""))
		{
			lEndVoucherNo = (String)request.getParameter("sEndVoucherNo");// Ʊ�ݺ�-��
			Log.print("Ʊ�ݺ�-��=" + lEndVoucherNo);
		}
	} 
	catch(Exception e) 
	{
		OBHtml.showCommonMessage(out,   sessionMng, strTitle, "", 1, "Gen_E001");
		return;
	}
%>
<!--Get Data end-->

<!--Check start-->
<!--Check end-->

<!--Set QueryVoucherForm Attribute start-->
<%
	/* ��ʼ����Ϣ�� */
	QueryVoucherInfo queryVoucherInfo = new QueryVoucherInfo();

	/* ��FORM���л�ȡ����Ӧ���� */
	/* �ͻ��������� */
	queryVoucherInfo.setTypeID( lType ); // Ʊ������
	queryVoucherInfo.setStartDate( strStartSubmit ); // ��������-��
	queryVoucherInfo.setEndDate( strEndSubmit ); // ��������-��
	queryVoucherInfo.setStartVoucherNo(lStartVoucherNo ); // Ʊ�ݺ�-��
	queryVoucherInfo.setEndVoucherNo( lEndVoucherNo ); // Ʊ�ݺ�-��
	
	//��session�л�ȡ��Ӧ���� 
	queryVoucherInfo.setClientID( sessionMng.m_lClientID );
	queryVoucherInfo.setCurrencyID( sessionMng.m_lCurrencyID );
%>
<!--Set QueryVoucherForm Attribute end-->

<!--Access DB start-->
<%
	/* ��ʼ����ѯ����� */
	Collection cltQvf = null;
	/*��ʼ����ѯ��*/
	OBSystemDao obSystemDao = new OBSystemDao();
	/* ���÷�����ò�ѯ��� */
	cltQvf = (Collection)obSystemDao.queryVoucher(queryVoucherInfo);
%>
<!--Access DB end>

<!--Forward start-->
<%
	try
	{
		/* �������б��������� */
	    request.setAttribute("cltQvf", cltQvf);
		//request.setAttribute("sPayerBankNo", strPayerBankNo);
		request.setAttribute("queryVoucherInfo", queryVoucherInfo);
	    /* ��ȡ�����Ļ��� */
	    //ServletContext sc = getServletContext();
	    /* ���÷��ص�ַ */
		//����ҳ��ַ�ʱ��Ҫ�õ���ʵ��
		PageControllerInfo pageControllerInfo = new PageControllerInfo();
		pageControllerInfo.setSessionMng(sessionMng);
		pageControllerInfo.setUrl(strContext + "/system/S810-Ipt.jsp");
		//�ַ�
		RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
	    /* forward�����ҳ�� */
	    rd.forward(request, response);
	} 
	catch(Exception e) 
	{
		OBHtml.showExceptionMessage(out,sessionMng, (IException)e, strTitle,"",1);
		return;
	}
%>
<!--Forward end-->