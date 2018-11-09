<%@page contentType="text/html;charset=gbk"%>
<%
    //response.setHeader("Cache-Control","no-stored");
	//response.setHeader("Pragma","no-cache");
	//response.setDateHeader("Expires",0);
%>
<%@page import="
com.iss.itreasury.ebank.obaheadrepaynotice.bizlogic.*,
com.iss.itreasury.ebank.obaheadrepaynotice.dataentity.AheadRepayNoticeInfo,
com.iss.itreasury.loan.util.*,
com.iss.itreasury.ebank.util.*,
com.iss.itreasury.util.*
" %>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%
	/* ����̶����� */
	String strTitle = "[��ǰ����֪ͨ��]";
%>
<%String strContext = request.getContextPath();%>
<%
	long ContractID = -1; //��ͬ��ID
	long PayID = -1;//�ſ�֪ͨ��ID
	double dAmount = 0;//��ǰ������
	long lID = -1;
	long lIsAhead=-1;

	try
	{
		/**
		* isLogin start
		*/
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

		//ȡ��������
		String strTemp = "";
		strTemp = (String)request.getAttribute("lID");
		if (strTemp != null && !strTemp.equals(""))
		{
			try
			{
				lID = Long.parseLong(strTemp);
			}
			catch (Exception e)
			{
				lID = -1;
			}
		}

		strTemp = (String)request.getAttribute("ContractID");
		if (strTemp != null && !strTemp.equals(""))
		{
			try
			{
				ContractID = Long.parseLong(strTemp);
			}
			catch (Exception e)
			{
				ContractID = -1;
			}
		}
		
		strTemp = (String)request.getAttribute("PayID");
		if (strTemp != null && !strTemp.equals(""))
		{
			try
			{
				PayID = Long.parseLong(strTemp);
			}
			catch (Exception e)
			{
				PayID = -1;
			}
		}
		
		strTemp = (String)request.getAttribute("dAheadRepayAmount");
		if (strTemp != null && !strTemp.equals(""))
		{
			try
			{
				dAmount = Double.parseDouble(DataFormat.reverseFormatAmount(strTemp));
			}
			catch (Exception e)
			{
				dAmount = 0;
			}
		}
		strTemp = (String)request.getAttribute("nIsAhead");
		if (strTemp != null && !strTemp.equals(""))
		{
			try
			{
				lIsAhead = Long.parseLong(strTemp);
			}
			catch (Exception e)
			{
				lIsAhead = -1;
			}
		}
		
		AheadRepayNoticeInfo info = new AheadRepayNoticeInfo();
		
		//info.setOfficeID(sessionMng.m_lOfficeID);
		//info.setCurrencyID(sessionMng.m_lCurrencyID);
		info.setContractID(ContractID);//��ͬID
		info.setLetoutNoticeID(PayID);//�ſ�֪ͨ��ID
		info.setAmount(dAmount);
		info.setInputUserID(sessionMng.m_lUserID);
		info.setIsAhead(lIsAhead);
		//������Ҫ���������
		info.setID(lID);

		OBAheadRepayNoticeHome aheadRepayNoticeHome = (OBAheadRepayNoticeHome)EJBObject.getEJBHome("OBAheadRepayNoticeHome");
		OBAheadRepayNotice aheadRepayNotice = aheadRepayNoticeHome.create();
		
		lID = aheadRepayNotice.saveAheadRepayNotice(info);

		//info = aheadRepayNotice.findAheadRepayNoticeByID(lID);

		request.setAttribute("lID", String.valueOf(lID));  
		request.setAttribute("action", "view");  		
		long lAction = 1;
		request.setAttribute("actionControl", String.valueOf(lAction));  

		/* ��ȡ�����Ļ��� */
		ServletContext sc = getServletContext();
	
		/* ���÷��ص�ַ */

		
	//����ҳ��ַ�ʱ��Ҫ�õ���ʵ��
	PageControllerInfo pageControllerInfo = new PageControllerInfo();
	pageControllerInfo.setSessionMng(sessionMng);
	pageControllerInfo.setUrl(strContext + "/loan/aheadrepaynotice/a002-c.jsp");
	//�ַ�
	RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
	
		/* forward�����ҳ�� */ 
		rd.forward(request, response);
		//return;
	}
	catch(IException ie) 
	{
		OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
		return;
    }
%>

			