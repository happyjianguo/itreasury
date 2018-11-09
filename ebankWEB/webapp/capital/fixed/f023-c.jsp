<%--
 ҳ������ ��c050.jsp
 ҳ�湦�� : �ҳ����ڿ��������˲��һ������ڴ浥��״̬Ϊ�ջ���Ϊ�����˵Ķ��ڿ�������
 ��    �� ��feiye
 ��    �� ��2006-03-23
 ����˵�� ����ʵ��˵����
				1��
 �޸���ʷ ��
--%>

<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="com.iss.itreasury.ebank.util.*"%>
<%@ page import="com.iss.itreasury.util.*"%>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.bizlogic.*"%>
<%@ page import="com.iss.itreasury.util.Constant"%>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.dataentity.*"%>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.dao.*"%>

<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<%
	/* ����̶����� */
	String strTitle = "[�������ڴ浥]";

	/* ʵ�ֲ˵����� */
	long lShowMenu = OBConstant.ShowMenu.YES;
	String strMenu = (String)request.getParameter("menu");
	Log.print("--------strMenu="+strMenu);
	if ((strMenu != null) && (strMenu.equals("hidden")))
	{
	    lShowMenu = OBConstant.ShowMenu.NO;
	}
	request.setAttribute("menu", strMenu);
	//ҳ����Ʊ���
	String strNextPageURL = "";
	String strSuccessPageURL = "";
	String strFailPageURL = "";
	String strAction = null;
	String strActionResult = Constant.ActionResult.FAIL;

    try
	{
		OBHtml.validateRequest(out, request, response);

		//�������
		long lID=-1;		//���ڿ������ݵ�ID��			

		//��ȡ����		
        strAction = (String)request.getAttribute("strAction");
		strSuccessPageURL = (String)request.getAttribute("strSuccessPageURL");						
		strFailPageURL = (String)request.getAttribute("strFailPageURL");
				
		String strTemp = null;
		
		strTemp = (String)request.getAttribute("lID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    lID = Long.valueOf(strTemp).longValue();
		}

		/* ʵ������Ϣ�� */
		FinanceInfo financeInfo = new FinanceInfo();
		/* ��ʼ��EJB */
		OBFinanceInstrHome financeInstrHome = null;
		OBFinanceInstr financeInstr = null;
		financeInstrHome = (OBFinanceInstrHome)EJBObject.getEJBHome("OBFinanceInstrHome");
		financeInstr = financeInstrHome.create();		

		/* ���÷�����ȡ��Ϣ���� */	
	    if(strAction.equals("toCreate")){
			//����
			if(lID>999999999999L)
			{
				//OBFinanceInstrDao dao = new OBFinanceInstrDao();
				
				financeInfo = financeInstr.findByIDForSett(lID,sessionMng.m_lClientID,sessionMng.m_lCurrencyID);
			}
			else
			{
	    		financeInfo = financeInstr.findByID(lID,sessionMng.m_lClientID,sessionMng.m_lCurrencyID);
			}
	    	strNextPageURL = "f024-v.jsp?strAction=toCreate&lID=" + lID;
			sessionMng.setCurrentKey(new Long(lID).longValue());
		}else if(strAction.equals("toModify")){
			//�޸�
	    	financeInfo = financeInstr.findByID(lID,sessionMng.m_lClientID,sessionMng.m_lCurrencyID);
	    	strNextPageURL = "f024-v.jsp?strAction=toModify&lID=" + lID;
		}else{
			Log.print("û�д˲���");
		}
		request.setAttribute("financeInfo",financeInfo);
		//ת����һҳ��
		Log.print("Next Page URL:"+strNextPageURL);	
		
		
		//����ҳ��ַ�ʱ��Ҫ�õ���ʵ��
	PageControllerInfo pageControllerInfo = new PageControllerInfo();
	pageControllerInfo.setSessionMng(sessionMng);
	pageControllerInfo.setUrl(strNextPageURL);
	//�ַ�
	RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
		
		rd.forward( request,response );
	}
	catch( Exception exp )
	{
		exp.printStackTrace();
		sessionMng.getActionMessages().addMessage(exp);
		strActionResult = Constant.ActionResult.FAIL;		
	}
	
%>