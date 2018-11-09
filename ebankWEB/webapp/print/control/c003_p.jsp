<%--
 ҳ������ ��c003.jsp
 ҳ�湦�� : ���ݴ�ӡ--����ҳ��
 ��    �� ��Boxu
 ��    �� ��2007-7-17
 ����˵�� ��ʵ�ֲ���˵����
				1��
 �޸���ʷ ��
--%>

<%@ page contentType="text/html;charset=gbk" %>

<%@ page import="java.util.Collection" %>
<%@ page import="com.iss.itreasury.util.Log" %>
<%@ page import="com.iss.itreasury.util.Constant" %>
<%@ page import="com.iss.itreasury.ebank.util.*"%>
<%@ page import="com.iss.itreasury.evoucher.util.VOUConstant"%>
<%@ page import="com.iss.itreasury.evoucher.print.bizlogic.QueryPrintBiz"%>
<%@ page import="com.iss.itreasury.evoucher.print.dataentity.PrintXMLTimeInfo"%>
<%@ page import="com.iss.itreasury.util.PageControllerInfo"%>
<%@ page import="com.iss.itreasury.util.PageController"%>
<%@ page import="com.iss.itreasury.evoucher.setting.dataentity.TransformOperationDataEntity"%>
<%@page import="java.util.HashMap"%>

<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<%	
/* ����̶����� */
	String strTitle = "";
	String strSuccessPageURL = "";
	String strFailPageURL = "";
	String strNextPageURL = "";
	String strActionResult = Constant.ActionResult.FAIL;
	
	long transID = -1;
	String strTransNo = "";
	long transTypeID = -1;
	//���⽻������
	long operationTypeID = -1;
	String strAction = "";
	sessionMng.setServletInfo(request.getServerPort(),request.getServerName(),request.getContextPath());	
	try
	{
		/* �û���¼��� */
	    if (sessionMng.isLogin() == false)
	    {
	        OBHtml.showCommonMessage(out, sessionMng, strTitle, "",Long.parseLong("1"), "Gen_E002");
	    	out.flush();
	    	return;
	    }
	
	    /* �ж��û��Ƿ���Ȩ�� */
	    if (sessionMng.hasRight(request) == false)
	    {
	    	out.println(sessionMng.hasRight(request));
	    	OBHtml.showCommonMessage(out, sessionMng, strTitle, "",Long.parseLong("1"), "Gen_E003");
	    	out.flush();
	    	return;
	    }
    
		boolean blPrint = false;
		
		//��ӡXML������
		String[] billName = null;
		HashMap printCountMap = new HashMap();//��ӡ����

		strSuccessPageURL = (String)request.getAttribute("strSuccessPageURL");
		strFailPageURL = (String)request.getAttribute("strFailPageURL");

		String strTemp = "";
		strTemp = (String)request.getAttribute("transIDs");
		if(strTemp!=null && strTemp.length()>0)
		{
			transID = Long.parseLong(strTemp);
		}

		strTemp = (String)request.getAttribute("strTransNos");
		if(strTemp!=null && strTemp.length()>0)
		{
			strTransNo = strTemp;
		}

		if(request.getParameter("billName")!=null)
		{
			billName = request.getParameterValues("billName");
		}
		else
		{
			billName = new String[1];
			strTemp = (String)request.getAttribute("printXMLName");
			if(strTemp!=null && strTemp.length()>0)
			{
				billName[0] = strTemp;
			}
		}

		strTemp = (String)request.getAttribute("lTransTypeID");
		if(strTemp!=null && strTemp.length()>0)
		{
			transTypeID = Long.valueOf(strTemp).longValue();
		}
		
		//���⽻������
		strTemp = (String)request.getAttribute("operationTypeID");
		if(strTemp!=null && strTemp.length()>0)
		{
			operationTypeID = Long.valueOf(strTemp).longValue();
		}
		
		strTemp = (String)request.getAttribute("strAction");
		if(strTemp!=null && strTemp.length()>0)
		{
			strAction = strTemp;
		}

		if(strAction.equals("preview"))
		{
			QueryPrintBiz biz = new QueryPrintBiz();
			strActionResult = Constant.ActionResult.SUCCESS;
			PrintXMLTimeInfo p = new PrintXMLTimeInfo();
			p.setId(transID);
			p.setTransNo(strTransNo);
			p.setBillName(billName);
			p.setDeptID(VOUConstant.PrintSection.EBANKCUSTOMER);
			p.setOfficeID(sessionMng.m_lOfficeID);
			p.setCurrencyID(sessionMng.m_lCurrencyID);
			p.setModule(Constant.ModuleType.SETTLEMENT);
			
			//��������⽻���޸�
			p.setTransTypeID(transTypeID);
			p.setOpretionType(operationTypeID);
			
			p.setPrintUser(sessionMng.m_lUserID);//jzw 2010-05-25 ��Ե���ǩ����Ӵ�ӡ�û���Ϣ
			p.setClientID(sessionMng.m_lClientID);
			p.setIsPreview(1);
			String printCheck = biz.valadatePrintTHREE(p);
			String printCount = printCheck.split("_")[0];
			strActionResult = Constant.ActionResult.SUCCESS;
			request.setAttribute("printCount",printCount+"");//���� ���  ��ӡ����
		}
		else
		{
			QueryPrintBiz biz = new QueryPrintBiz();
			PrintXMLTimeInfo printXMLInfo = new PrintXMLTimeInfo();
			printXMLInfo.setId(transID);
			printXMLInfo.setTransNo(strTransNo);
			printXMLInfo.setBillName(billName);
			printXMLInfo.setDeptID(VOUConstant.PrintSection.EBANKCUSTOMER);
			printXMLInfo.setOfficeID(sessionMng.m_lOfficeID);
			printXMLInfo.setCurrencyID(sessionMng.m_lCurrencyID);
			printXMLInfo.setModule(Constant.ModuleType.SETTLEMENT);
			
			//��������⽻���޸�
			printXMLInfo.setTransTypeID(transTypeID);
			printXMLInfo.setOpretionType(operationTypeID);
			
			printXMLInfo.setPrintUser(sessionMng.m_lUserID);//jzw 2010-05-25 ��Ե���ǩ����Ӵ�ӡ�û���Ϣ
			printXMLInfo.setClientID(sessionMng.m_lClientID);
			
			//blPrint = biz.valadatePrintTWO(printXMLInfo);
			String printCheck = biz.valadatePrintTHREE(printXMLInfo);
			String canPrint = printCheck.split("_")[1];
			String printCount = printCheck.split("_")[0];
			if("can".equals(canPrint))
			{
				strActionResult = Constant.ActionResult.SUCCESS;
				printCountMap = biz.getPrintCountMap(printXMLInfo);
				request.setAttribute("printCountMap",printCountMap);//xiang ���  ��ӡ��������
			}
			else
			{
				strActionResult = Constant.ActionResult.FAIL;
				sessionMng.getActionMessages().addMessage("�ý��׶�Ӧ����ģ���Ѵﵽ����ӡ����");
				request.setAttribute("printCount",printCount+"");//���� ���  ��ӡ����
				request.setAttribute("lID", String.valueOf(transID));
				request.setAttribute("TransactionTypeID", String.valueOf(transTypeID));
				request.setAttribute("TransNo", strTransNo);
				request.setAttribute("strSuccessPageURL", "../view/v003_p.jsp");
				request.setAttribute("strFailPageURL", "../view/v003_p.jsp");
			}
		}
	}
	catch( Exception exp )
	{
		exp.printStackTrace();
		sessionMng.getActionMessages().addMessage(exp);
		//�����쳣,���������Ϊʧ��
		strActionResult = Constant.ActionResult.FAIL;
		
		request.setAttribute("lID", String.valueOf(transID));
		request.setAttribute("TransactionTypeID", String.valueOf(transTypeID));
		request.setAttribute("TransNo", strTransNo);
		request.setAttribute("strSuccessPageURL", "../view/v003_p.jsp");
		request.setAttribute("strFailPageURL", "../view/v003_p.jsp");
	}

	// ҳ����ת

	//���ݴ�����������һ����ת��Ŀ��ҳ��
	strNextPageURL = (Constant.ActionResult.SUCCESS.equals(strActionResult)) ? strSuccessPageURL:strFailPageURL;
	//request.setAttribute("strSuccessPageURL", strSuccessPageURL);

	//ת����һҳ��
	//����ҳ��ַ�ʱ��Ҫ�õ���ʵ��
	PageControllerInfo pageControllerInfo = new PageControllerInfo();
	pageControllerInfo.setSessionMng(sessionMng);
	pageControllerInfo.setUrl(strNextPageURL);
	//�ַ�
	RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
	rd.forward( request,response );
%>