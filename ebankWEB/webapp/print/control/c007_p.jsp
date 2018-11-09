<%--
 ҳ������ ��c007_p.jsp
 ҳ�湦�� : ����������ӡ--����ҳ��
 ��    �� ��Boxu
 ��    �� ��2007-8-9
 ����˵�� ��ʵ�ֲ���˵����
				1��
 �޸���ʷ ��
--%>

<%@ page contentType="text/html;charset=gbk" %>

<%@ page import="java.util.Collection"%>
<%@ page import="com.iss.itreasury.util.Log" %>
<%@ page import="com.iss.itreasury.util.Constant" %>
<%@ page import="com.iss.itreasury.ebank.util.*"%>
<%@ page import="com.iss.itreasury.evoucher.util.VOUConstant" %>
<%@ page import="com.iss.itreasury.evoucher.print.bizlogic.QueryPrintBiz"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="com.iss.itreasury.util.PageControllerInfo"%>
<%@ page import="com.iss.itreasury.util.PageController"%>

<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<%	
/* ����̶����� */
	String strTitle = "";
	String strSuccessPageURL = "";
	String strFailPageURL = "";
	String strNextPageURL = "";
	String strActionResult = Constant.ActionResult.FAIL;
	try
	{
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
		
		// data define start
		long lCurrencyID = sessionMng.m_lCurrencyID;//����
		long lOfficeID = sessionMng.m_lOfficeID;//���´�

		strSuccessPageURL = (String)request.getAttribute("strSuccessPageURL");
		strFailPageURL = (String)request.getAttribute("strFailPageURL");

		String[] checkPrint = null;
		String transIDs = "";
		String strTransNos = "";
		String strPrintAction = "";
		String transTypeID = "";
		String strPageLoaderKey = "";
		
		if(request.getAttribute("_pageLoaderKey") != null)
		{
			strPageLoaderKey = (String)request.getAttribute("_pageLoaderKey");
			request.setAttribute("_pageLoaderKey",strPageLoaderKey);
		}

		if(request.getAttribute("strPrintAction") != null)
		{
			strPrintAction = (String)request.getAttribute("strPrintAction");
		}

		if(request.getAttribute("lTransTypeID") != null)
		{
			transTypeID = (String)request.getAttribute("lTransTypeID");
		}
		
		if(request.getParameter("printCheck") != null)
		{
			checkPrint = request.getParameterValues("printCheck");
			for(int i=0;i<checkPrint.length;i++)
			{
				if(i == 0)
				{
					transIDs += checkPrint[i].split("####")[0];
				}
				else
				{
					transIDs += ","+checkPrint[i].split("####")[0];
				}
				Log.print("checkPrint"+i+":"+checkPrint[i].split("####")[0]);
			}
		}
		else
		{
			transIDs = (String)request.getAttribute("printCheck");
		}

		if(request.getAttribute("strTransNos") != null)
		{
			strTransNos = (String)request.getAttribute("strTransNos");
			if(!strPrintAction.equals("singalPrint"))
			{
				strTransNos = strTransNos.substring(1);
			}			
		}
		
		System.out.println("***************-----------strTransNos:"+strTransNos);
		System.out.println("***************-----------transIDs:"+transIDs);

		long lDeptID = -1;
		lDeptID = VOUConstant.PrintSection.EBANKCUSTOMER;
		
		strSuccessPageURL = "../control/c006_p.jsp";
		strFailPageURL = "../control/c001_P.jsp";
		
		Collection printOptions = null;
	    QueryPrintBiz biz = new QueryPrintBiz();
	    
		printOptions = biz.getPrintTemplateContentmany(strTransNos, transIDs, VOUConstant.PrintSection.EBANKCUSTOMER, lCurrencyID, lOfficeID, Constant.ModuleType.SETTLEMENT);		
		
		request.setAttribute("printOptions",printOptions);
		strActionResult = Constant.ActionResult.SUCCESS;
	}
	catch( Exception exp )
	{
		exp.printStackTrace();
		sessionMng.getActionMessages().addMessage(exp);
		//�����쳣,���������Ϊʧ��	
		strActionResult = Constant.ActionResult.FAIL;
		
		request.setAttribute("strSuccessPageURL", "/print/view/v005_P.jsp");
		request.setAttribute("strFailPageURL", "/print/view/v005_P.jsp");
	}

	//ҳ����ת
	
	//���ݴ�����������һ����ת��Ŀ��ҳ��
	strNextPageURL = (Constant.ActionResult.SUCCESS.equals(strActionResult))?strSuccessPageURL:strFailPageURL;
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