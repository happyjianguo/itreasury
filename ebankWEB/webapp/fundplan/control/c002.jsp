<%--
 ҳ������ ��c002.jsp
 ҳ�湦�� : �ʽ�ƻ��걨��ѯ ��ѯһ���걨ҳ��   v001.jsp ��������>c002.jsp ��������>v004.jsp
 ��    �� ��ylguo
 ��    �� ��2008-10-23
 ����˵�� ��
 �޸���ʷ ��
--%>
<%@page contentType="text/html;charset=gbk"%>
<%@page import="com.iss.itreasury.util.*,
com.iss.itreasury.ebank.util.*,
com.iss.itreasury.ebank.fundplan.dataentity.CapitalPlanInfo,
com.iss.itreasury.ebank.fundplan.dao.*,
com.iss.itreasury.ebank.fundplan.bizlogic.Sett_CapitalPlanSettingBiz"%>

<%@ page import="com.iss.itreasury.ebank.system.util.JSPLogger"%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%
	/* ����̶����� */
	String strTitle = "[�ʽ�ƻ�]";
%>
<% String strContext = request.getContextPath(); %>
<%
	PageControllerInfo pageCtrlInfo = new PageControllerInfo();
	
	try
	{
		JSPLogger.info("*******����ҳ��--\\iTreasury-ebank\\fundplan\\control\\c002.jsp*******");	
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
        
        pageCtrlInfo.convertRequestToDataEntity(request);
        
        String strTemp = null;
	    //�ӱ��е��ֶ�CapitalplanId
	    long capitalplanId = -1;
	    strTemp = request.getParameter("capitalPlanId");
	    if(strTemp != null && strTemp.length()>0)
	    {
			capitalplanId = Long.parseLong(strTemp);
	    }
	     		
 		if(capitalplanId>0)
 		{
	 		CapitalPlanInfo capitalplanInfo = null; 		
	        //������Ϣ
			CapitalPlanDao planDAO = new CapitalPlanDao();
			capitalplanInfo 
				= (CapitalPlanInfo)planDAO.findByID(capitalplanId, CapitalPlanInfo.class);
				
		request.setAttribute("capitalPlanInfo", capitalplanInfo);	
		request.setAttribute("isAutoCheck",new Boolean(new Sett_CapitalPlanSettingBiz().isAutoCheck(sessionMng.m_lClientID)));	
				
		}else{
			throw new IException("�ʽ�ƻ��걨��Ч");
		}
				
		pageCtrlInfo.success();
	}
	catch(IException exp)
	{
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