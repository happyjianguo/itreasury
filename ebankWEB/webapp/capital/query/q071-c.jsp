<%--
 �������� ��queryLiShi.jsp
 ����˵�� : ��ʷ��ϸ��ѯ����ҳ��
 ��    �� ������
 ��    �� ��2003��11��25��
--%>

<!--Header start-->
<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="java.util.Collection,com.iss.itreasury.util.*,
				 com.iss.itreasury.ebank.util.*,
				 com.iss.itreasury.ebank.obquery.dataentity.OBInterestWhereInfo,
				 com.iss.itreasury.ebank.obquery.dao.OBInterestQueryDao"
%>

<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ include file="/common/common.jsp" %>
<!--Header end-->

<%!
	/* ����̶����� */
	String strTitle = "[��ʷ��ϸ]";
%>

<%
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
		
		//�������
		String strClientCode = "";//�ͻ���
		String strAccountNo = "";//�˺�
		long lAccountID = -1;//�˻�ID
		long lAccountTypeID = -1;//�˻�����ID
		long lAccountGroupID = -1;//�˻���ID

		String strEndDate = "";
		
		//��ȡ����
		if(request.getParameter("lAccountID")!=null)
		{
			lAccountID = Long.parseLong(request.getParameter("lAccountID")); //�˻�ID
		}
		if(request.getParameter("lAccountTypeID")!=null)
		{
			lAccountTypeID = Long.parseLong(request.getParameter("lAccountTypeID")); //�˻�����ID
		}
		if(request.getParameter("lAccountGroupID")!=null)
		{
			lAccountGroupID = Long.parseLong(request.getParameter("lAccountGroupID")); //�˻���ID
		}
		if(request.getParameter("tsEnd")!=null)
		{
			strEndDate = (String)request.getParameter("tsEnd");
		}
		
		//��ʼ�������ò�ѯ������
        OBInterestWhereInfo qtai = new OBInterestWhereInfo();
		OBInterestQueryDao qobj = new OBInterestQueryDao();
				
        
		qtai.setAccctNo(strAccountNo);//�˺�
		qtai.setAcctId(lAccountID);//�˻�ID
		qtai.setEndDate(strEndDate);
		
        //����������������ҵ����ĵ���
		Collection coll = null;
		
        coll = qobj.queryInterest(qtai);
			
		/* �������б��������� */
		request.setAttribute("whereInfo",qtai);
		request.setAttribute("coll_resultInfo",coll);
	
		/* ��ȡ�����Ļ��� */
	    //ServletContext sc = getServletContext();
	    /* ���÷��ص�ַ */
	  
	   
	   //����ҳ��ַ�ʱ��Ҫ�õ���ʵ��
	PageControllerInfo pageControllerInfo = new PageControllerInfo();
	pageControllerInfo.setSessionMng(sessionMng);
	pageControllerInfo.setUrl("/capital/query/q070-v.jsp");
	//�ַ�
	RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
	   
	    /* forward�����ҳ�� */
	    rd.forward(request, response);
	}
	catch( Exception e )
	{
		OBHtml.showExceptionMessage(out,sessionMng,(IException)e, strTitle,"",1);
		return;
	}
%>