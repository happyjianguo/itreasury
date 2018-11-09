<%--
 ҳ������ ��c003.jsp
 ҳ�湦�� : �ʽ�ƻ��걨��ѯ ����ҳ��   v001.jsp ��������>c003.jsp ��������>v003.jsp
 ��    �� ��ylguo
 ��    �� ��2008-10-23
 ����˵�� ��
 �޸���ʷ ��
--%>
<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="com.iss.itreasury.ebank.fundplan.bizlogic.AllCapitalPlanBiz,
				 com.iss.itreasury.ebank.fundplan.dataentity.CapitalPlanInfo,
				 java.util.Collection,
				 com.iss.itreasury.util.IException,
				 com.iss.itreasury.ebank.util.OBHtml,
				 com.iss.itreasury.util.PageControllerInfo"
%>
<%@ page import="com.iss.itreasury.ebank.system.util.JSPLogger"%>

<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%
	 String strTitle = "[�ʽ�ƻ��걨����]";
	 
     PageControllerInfo pageCtrlInfo = new PageControllerInfo();	 
     
  try{
		JSPLogger.info("*******����ҳ��--\\iTreasury-ebank\\fundplan\\control\\c003.jsp*******");	
	
	   /* �û���¼��� */
        if (sessionMng.isLogin() == false)
        {
            OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1,"Gen_E002");
        	out.flush();
        	return;
        }

        /* �ж��û��Ƿ���Ȩ�� */
        if (sessionMng.hasRight(request) == false)
        {
        	out.println(sessionMng.hasRight(request));
        	OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1,"Gen_E003");
        	out.flush();
        	return;
        }
        
	    pageCtrlInfo.convertRequestToDataEntity(request);
         		 
		CapitalPlanInfo conditionInfo = new CapitalPlanInfo();
		conditionInfo.convertRequestToDataEntity(request);//����ѯ������װ��һ��������ȥ
		//����BIZ���ò�ѯ�Ľ��
		AllCapitalPlanBiz planBiz = new AllCapitalPlanBiz();
		Collection colResult = planBiz.findCapitalPlan(conditionInfo);
		request.setAttribute("capitalResults", colResult);
		
	    //����ҳ��ַ�ʱ��Ҫ�õ���ʵ��
	    pageCtrlInfo.success();
	}catch(IException exp){
    	sessionMng.getActionMessages().addMessage(exp.getMessage());
    	exp.printStackTrace();
 		pageCtrlInfo.fail();
	}

   /* ���÷��ص�ַ */
	String nextURL = pageCtrlInfo.getP_NextPageURL();
	JSPLogger.info("������һҳ�棺"+nextURL);		
	pageCtrlInfo.setSessionMng(sessionMng);
	RequestDispatcher rd = request.getRequestDispatcher(nextURL);
	/* forward�����ҳ�� */
	rd.forward(request, response);		
%>


