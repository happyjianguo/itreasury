<%
/**ҳ�湦��˵��
 * ҳ������ ��c001.jsp
 * ҳ�湦�� : ��������ѯ   ���Ʋ�
 * ��    �� ��barneyliu	
 * ��    �� ��2005-11-15
 * ��ʵ��˵����
 *				1����ʷ���ײ�ѯ
 * ����˵�� ��
 * �޸���ʷ ��
 */
%>

<%@ page contentType = "text/html;charset=gbk"%>
<!--�ർ�벿�ֿ�ʼ-->
 
<%@ page import="com.iss.itreasury.util.Log"%>

<%@ page import="com.iss.itreasury.ebank.util.OBHtml"%>
<%@ page import="com.iss.itreasury.util.PageCtrlInfo"%>
<%@ page import="com.iss.itreasury.ebank.obquery.bizlogic.OBTodayBalanceBiz"%>
<%@ page import="com.iss.itreasury.ebank.obquery.dataentity.OBQueryAccInfo"%>
<%@ page import="com.iss.itreasury.ebank.obquery.dataentity.OBTodayBalanceResultInfo"%>
<%@page import="com.iss.itreasury.util.PageControllerInfo"%>
<%@page import="com.iss.itreasury.util.PageController"%>
 
<!--�ർ�벿�ֽ���-->

<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<%
		

		PageCtrlInfo pageCtrl = new PageCtrlInfo();
 		String strSuccessPageURL =  "";
		String strFailPageURL    =  "";
    try
	{

		 System.out.println("----------------- ����ҳ�� c001.jsp -----------------");
		/* �û���¼��� */
        /*if (sessionMng.isLogin() == false)
        {
            OBHtml.showCommonMessage(out, sessionMng, "", "",1, "Gen_E002");
        	out.flush();
        	return;
        }*/
		

        /* �ж��û��Ƿ���Ȩ�� */
        if (sessionMng.hasRight(request) == false)
        {
        	out.println(sessionMng.hasRight(request));
        	OBHtml.showCommonMessage(out, sessionMng, "", "",1, "Gen_E003");
        	out.flush();
        	return;
        }
		
		
			//��ȡҳ�������Ϣ
			pageCtrl.convertRequestToDataEntity(request);
			strSuccessPageURL = pageCtrl.getStrSuccessPageURL();
   			strFailPageURL = pageCtrl.getStrFailPageURL();
	//		Log.print("pagectrl-----------"+pageCtrl);
		 
	 
		//�������
		OBQueryAccInfo paramInfo        = new OBQueryAccInfo();
	  	OBTodayBalanceBiz biz             = new OBTodayBalanceBiz();
		OBTodayBalanceResultInfo[] OBtodaybalanceresults = null; 
 
		
		/** ��ȡsessionMng���� **/
		paramInfo.setClientid(sessionMng.m_lClientID);           //�ͻ�
		paramInfo.setUserid(sessionMng.m_lUserID);				 //�û�
		  
		
		/** ��ȡ�����ݣ�����AcctTransParam���� **/		
		paramInfo.convertRequestToDataEntity(request);
		Log.print("paramInfo"+paramInfo);
	
	    System.out.println("�������");
		   
		   //�б���Ϣ
		   OBtodaybalanceresults = biz.QueryTodayBalance(paramInfo);
		 		 
	 
	    System.out.println("�Ѳ��꣡������");
		
		request.setAttribute("OBtodaybalanceresults",OBtodaybalanceresults);
		
		request.setAttribute("strSuccessPageURL",pageCtrl.getStrSuccessPageURL());
		request.setAttribute("strFailPageURL",pageCtrl.getStrFailPageURL());
		
		pageCtrl.success();
		
		/**ҵ���߼�����*/
	}
	catch( Exception exp )
	{
		 
		exp.printStackTrace();
		Log.print(exp.getMessage());
		pageCtrl.fail();
		OBHtml.showCommonMessage(out,sessionMng,"","",1,"Gen_E001");
	}
	
	
	String nextURL = pageCtrl.getStrNextPageURL();
	Log.print("������һҳ�棺"+nextURL);	
//����ҳ��ַ�ʱ��Ҫ�õ���ʵ��
	PageControllerInfo pageControllerInfo = new PageControllerInfo();
	pageControllerInfo.setSessionMng(sessionMng);
	pageControllerInfo.setUrl(strNextURL);
	//�ַ�
	RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL

(pageControllerInfo));
	
	rd.forward( request,response );
	
%>