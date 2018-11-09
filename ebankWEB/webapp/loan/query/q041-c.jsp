<%@page contentType="text/html;charset=gbk"%>
<%@page import="java.util.*,
	com.iss.itreasury.loan.util.*,
	com.iss.itreasury.loan.loancommonsetting.dataentity.*,
	com.iss.itreasury.loan.loancommonsetting.bizlogic.*,
	com.iss.itreasury.util.*
" %>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.util.SessionMng"></jsp:useBean>
<%

	try
	{
		/**
		* isLogin start
		*/
		//��¼���
		
    	if (!sessionMng.isLogin())
    	{
			LOANHTML.showMessage(out,sessionMng,request,response,"�ͻ�����",Constant.RecordStatus.INVALID,"Gen_E002");
			LOANHTML.showHomeEnd(out);
        	out.flush();
        	return;
    	}

    	//���Ȩ��
    	if (sessionMng.hasRight(request) == false)
    	{
			LOANHTML.showMessage(out,sessionMng,request,response,"�ͻ�����",Constant.RecordStatus.INVALID,"Gen_E003");
        	LOANHTML.showHomeEnd(out);
        	out.flush();
        	return;
    	}
		
		long lClientID = -1;
		long lLoanType = -1;
		
		//ȡ��������
		String strTemp = "";
		strTemp = request.getParameter("lClientID");
		if (strTemp != null && !strTemp.equals(""))
		{
			try
			{
				lClientID = Long.parseLong(strTemp);
			}
			catch (Exception e)
			{
				lClientID = -1;
			}
		}
		
		strTemp = request.getParameter("lLoanType");
		if (strTemp != null && !strTemp.equals(""))
		{
			try
			{
				lLoanType = Long.parseLong(strTemp);
			}
			catch (Exception e)
			{
				lLoanType = -1;
			}
		}
		
		LoanCommonSettingHome home = (LoanCommonSettingHome)EJBObject.getEJBHome("LoanCommonSettingHome");
		LoanCommonSetting lcs = home.create();
		
		ClientInfo cInfo=null;
		cInfo=lcs.findClientByID(lClientID);
		request.setAttribute("ClientInfo",cInfo);	
		
		String strURL = "/loan/l004-v.jsp?txtAction=check&lLoanType="+lLoanType;

		/* ��ȡ�����Ļ��� */
		ServletContext sc = getServletContext();
	
		/* ���÷��ص�ַ */
	//����ҳ��ַ�ʱ��Ҫ�õ���ʵ��
	PageControllerInfo pageControllerInfo = new PageControllerInfo();
	pageControllerInfo.setSessionMng(sessionMng);
	pageControllerInfo.setUrl(strURL);
	//�ַ�
	RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
	
		/* forward�����ҳ�� */
		rd.forward(request, response);
		//return;
	}
	catch (IException ie)
	{
		LOANHTML.showExceptionMessage(out, sessionMng, ie, request, response, "�ͻ�����", Constant.RecordStatus.VALID);
		ie.printStackTrace();
		out.flush();
		return;
	}
%>

			