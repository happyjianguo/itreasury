<%--
 ҳ������ ��c002.jsp
 ҳ�湦�� : ���ڿ����������Ӳ���ҳ��Ŀ�����ҳ��
 ��    �� ��xrli
 ��    �� ��2003-09-27
 ����˵�� ��ʵ�ֲ���˵����
				1�����Ӳ���
 �޸���ʷ ��
--%>

<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="java.util.Collection"%>
<%@ page import="com.iss.itreasury.util.Log"%>
<%@ page import="com.iss.itreasury.util.Env"%>
<%@ page import="com.iss.itreasury.util.Constant"%>
<%@ page import="com.iss.itreasury.settlement.util.SETTConstant"%>
<%@ page import="com.iss.itreasury.ebank.obaccountinfo.dataentity.*"%>
<%@ page import="com.iss.itreasury.ebank.obaccountinfo.dao.*"%>
<%@ page import="com.iss.itreasury.ebank.util.*"%>
<%@page import="com.iss.itreasury.util.PageControllerInfo"%>
<%@page import="com.iss.itreasury.util.PageController"%>

<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<%
	//ҳ����Ʊ���
	String strNextPageURL = "";
	String strSuccessPageURL = "";
	String strFailPageURL = "";
	String strAction = null;
	String strActionResult = Constant.ActionResult.FAIL;
	if(request.getParameter("next")!=null){
	request.setAttribute("next",request.getParameter("next"));
	}
	if(request.getParameter("accountType")!=null){
	request.setAttribute("accountType",request.getParameter("accountType"));
	}
    try
	{
			/* �û���¼��� */
        if (sessionMng.isLogin() == false)
        {
            OBHtml.showCommonMessage(out, sessionMng, "", "",1, "Gen_E002");
        	out.flush();
        	return;
        }
		

        /* �ж��û��Ƿ���Ȩ�� */
        if (sessionMng.hasRight(request) == false)
        {
        	out.println(sessionMng.hasRight(request));
        	OBHtml.showCommonMessage(out, sessionMng, "", "",1, "Gen_E003");
        	out.flush();
        	return;
        }

        //�������		


		long lAccountID =-1;
		long lSubAccountID =-1;
		long lContractID =-1;
		strAction = (String)request.getAttribute("strAction");
		strSuccessPageURL = (String)request.getAttribute("strSuccessPageURL");
		strFailPageURL = (String)request.getAttribute("strFailPageURL");
		//��ȡ����
		String strTemp = null;		
		strTemp = (String)request.getAttribute("lAccountID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    lAccountID = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("lSubAccountID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    lSubAccountID = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("lContractID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    lContractID = Long.valueOf(strTemp).longValue();
		}
		
        OBAccountBalanceQueryDao dao = new OBAccountBalanceQueryDao(); 
		OBAccountQueryInfo info = new OBAccountQueryInfo();
		OBAccountQueryInfo tempinfo = new OBAccountQueryInfo();
		info.setAccountID(lAccountID);
		info.setSubAccountID(lSubAccountID);
		info.setContractID(lContractID);
		info.setOfficeID(sessionMng.m_lOfficeID);
		info.setCurrencyID(sessionMng.m_lCurrencyID);
		//��ȡ�˻���Ӧ�Ŀ�������
		tempinfo = dao.getOfficeAndCurrencyByAccountID(lAccountID);
		info.setExecuteDate(Env.getSystemDate(tempinfo.getOfficeID(), tempinfo.getCurrencyID()));
        //����������������ҵ����ĵ���
        Collection resultColl = null;
        double [] MorY = null;
        
		if(strAction.equals("current"))
		{
		MorY = dao.findByCurrentAccountInfo(info);
        resultColl = dao.findByCurrentAccountID(info);		
		strActionResult = Constant.ActionResult.SUCCESS;
	
		}
		else if(strAction.equals("fixed"))
		{
								
	        resultColl = dao.findByFixedAccountID(info);			
			strActionResult = Constant.ActionResult.SUCCESS;
			
		}
		else if(strAction.equals("fixedDzd"))
		{
								
	        resultColl = dao.findByFixedAccountIDDzd(info);			
			strActionResult = Constant.ActionResult.SUCCESS;
			
		}
		else if(strAction.equals("notice"))
		{
									
	        //resultColl = dao.findByNoticeAccountID(info);
			resultColl = dao.findByFixedAccountID(info);
			strActionResult = Constant.ActionResult.SUCCESS;
		   
			
		}
		else if(strAction.equals("loan"))
		{
								
	        resultColl = dao.findByLoanAccountID(info);
			strActionResult = Constant.ActionResult.SUCCESS;
			
		}
		request.setAttribute("MorY",MorY);
		request.setAttribute("searchResults",resultColl);
		request.setAttribute("tempinfo",tempinfo);
	}
	
	catch( Exception exp )
	{
		exp.printStackTrace();
		//sessionMng.getActionMessages().addMessage(exp);
		strActionResult = Constant.ActionResult.FAIL;		
	}	
	request.setAttribute("strActionResult",strActionResult);
	//���ݴ�����������һ����ת��Ŀ��ҳ��
	strNextPageURL = (Constant.ActionResult.SUCCESS.equals(strActionResult))?strSuccessPageURL:strFailPageURL;	
	//ת����һҳ��
	//����ҳ��ַ�ʱ��Ҫ�õ���ʵ��
	String strNextURL = strNextPageURL;
	PageControllerInfo pageControllerInfo = new PageControllerInfo();
	pageControllerInfo.setSessionMng(sessionMng);
	pageControllerInfo.setUrl(strNextURL);
	//�ַ�
	RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL

(pageControllerInfo));
	rd.forward( request,response);
%>