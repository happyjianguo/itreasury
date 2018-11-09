<%--
/*
 * �������ƣ�c003.jsp
 * ����˵���������������ÿ���ҳ��
 */
--%>

<!--Header start-->
<%
/* ����ҳ�����ԡ�session������ĳ���� */
%>
<%@ page contentType="text/html;charset=gbk"%> 
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@page import="com.iss.itreasury.util.*,
				com.iss.itreasury.ebank.util.*,
                com.iss.itreasury.ebank.approval.bizlogic.*,
				com.iss.itreasury.ebank.approval.dataentity.*,
                java.util.Collection,
				java.sql.Timestamp"
%>
<%@page import="com.iss.itreasury.util.PageControllerInfo"%>
<%@page import="com.iss.itreasury.util.PageController"%>
<%   
    response.setHeader("Cache-Control","no-stored");
	response.setHeader("Pragma","no-cache");
	response.setDateHeader("Expires",0);
%>
<!--Header end-->

<%
	Log.print("---------���� c003.jsp �����û�Ȩ�����ÿ���ҳ��---------");
	
	
	/* ����̶����� */
	String strTitle = "��������"; 
%>

<%	
    //��Session��ȡ�ñ���Ĳ���
    long lCurrencyID = sessionMng.m_lCurrencyID;
    
	//���ò���
	ApprovalSettingBiz AppBiz = new ApprovalSettingBiz();
	String strTmp = "";
    long lModuleID = -1;	//ģ���ʾ
	long lLoanTypeID = -1;	//���ͱ�ʾ
	long lActionID = -1;	//������ʾ
	long lApprovalID = -1;	//�������ñ�ʾ
    long lTotalLevel = 0;	//�����ܼ���
    long lIsPass = 0;		//�Ƿ�����Խ��
	long lIsReduplicateAssign = 0;	//�Ƿ�����������ظ�������Ա
	long lIsReduplicateCheck = 0;	//�Ƿ�����ͬһ��Ա�ظ�����
	long lStatusID = 1;		//��ǰ����״̬��1�������У�2���������
	long lLevel = -1;		//��ǰ��������
	long lResult = -1;
	long lIsOver = 1;       //�ж��Ƿ�ȫ����������1���ǡ�2����
	String strErrorMessage ="";
	/* �û���¼�����Ȩ��У�鼰�ļ�ͷ��ʾ */
    try {
		//�û���¼��� 
        if (sessionMng.isLogin() == false)
        {
        	OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E002");
        	out.flush();
        	return;
        }

        // �ж��û��Ƿ���Ȩ�� 
        if (sessionMng.hasRight(request) == false)
        {
            out.println(sessionMng.hasRight(request));
        	OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E003");
        	out.flush();
        	return;
        }
        
		//��ȡ�����ݣ�����currentTransInfo����
		Log.print("��ȡ��ѯ����");

		// ��ȡlApprovalID
		strTmp = (String)request.getAttribute("lApprovalID");
		if( strTmp != null && strTmp.length() > 0 )
		{
			try
			{
				lApprovalID = Long.parseLong(strTmp);
			}
			catch(Exception e)
			{
				lApprovalID = -1;
			}
		}

		// ��ȡlType
		strTmp = (String)request.getAttribute("isOver");
		if( strTmp != null && strTmp.length() > 0 )
		{
			try
			{
				lIsOver = Long.parseLong(strTmp);
			}
			catch(Exception e)
			{
				lIsOver = -1;
			}
		}
        
        //���ݻ�ȡ�Ĳ�������֯����
        String strURL = "";
		ApprovalSettingInfo ASInfo = null;
		lResult = AppBiz.checkApprovalTracing(lApprovalID);
		if (lResult == 1)
		{
			//��ô�����Ϣ
			strErrorMessage = "����������δ���������ҵ�񣬲������";
			request.setAttribute("Error", strErrorMessage);
			
		}
		else
		{
			lResult = AppBiz.checkApprovalValidity(lApprovalID);
			if (lResult == 2)
			{
				//��ô�����Ϣ
				strErrorMessage = "��������û��ȫ��������ɣ��������";
				request.setAttribute("Error", strErrorMessage);
				
			}
			else if (lResult == 3)
			{
				//��ô�����Ϣ
				strErrorMessage = "������������ظ�������Ա�����飡";
				request.setAttribute("Error", strErrorMessage);
				
			}
			else
			{
				lResult = AppBiz.activeApprovalSetting(lApprovalID);
				if (lResult < 0)
				{
					//��ô�����Ϣ
					strErrorMessage = "���ü���ɹ���";
					request.setAttribute("Error", strErrorMessage);
				}
			}
		}
		//�������ñ�ʾ
		request.setAttribute("lApprovalID",Long.toString(lApprovalID));
		//�����Ƿ���δ��ɵ�����
		request.setAttribute("isOver",Long.toString(lIsOver));
		
		if(request.getAttribute("fromPage") != null)
		{
			request.setAttribute("fromPage",(String)request.getAttribute("fromPage"));
		}
		
        strURL = "c002.jsp?control=view";
        
		/* ��ȡ�����Ļ��� */
		Log.print("��ȡ�����Ļ���");
	    //ServletContext sc = getServletContext();
	    
		/* ���÷��ص�ַ */
		Log.print("���÷��ص�ַ");
		
		//����ҳ��ַ�ʱ��Ҫ�õ���ʵ��
	PageControllerInfo pageControllerInfo = new PageControllerInfo();
	pageControllerInfo.setSessionMng(sessionMng);
	pageControllerInfo.setUrl(strURL);
	//�ַ�
	RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
		
		/* forward�����ҳ�� */
		Log.print("forward�����ҳ��");
	    rd.forward(request, response);
		return;
		
    } catch (Exception e) {
		Log.print(e.toString());
    }
%>