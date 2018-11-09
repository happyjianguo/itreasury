<%--
 ҳ������ ��c005.jsp
 ҳ�湦�� : �ʽ�ƻ��걨���޸ģ�ֻ������״̬Ϊ1�Ĳſ��Խ��в��� ����ҳ��  v007.jsp  ��������> c006.jsp ��������> v001.jsp
 ��    �� ��ylguo
 ��    �� ��2008-10-24
 ����˵�� ��
 �޸���ʷ ��
--%>
<%@page contentType="text/html;charset=gbk"%>
<%@page import="com.iss.itreasury.ebank.obaheadrepaynotice.bizlogic.*,
com.iss.itreasury.ebank.obaheadrepaynotice.dataentity.*,
com.iss.itreasury.util.*,
com.iss.itreasury.ebank.util.*,
java.util.Enumeration,
com.iss.itreasury.ebank.fundplan.dataentity.SubCapitalPlanInfo,
java.util.Vector,
com.iss.itreasury.ebank.fundplan.dataentity.CapitalPlanInfo,
java.sql.Timestamp,
com.iss.itreasury.ebank.fundplan.bizlogic.AllCapitalPlanBiz"%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%
	/* ����̶����� */
	String strTitle = "[�ʽ�ƻ�]";
%>
<% String strContext = request.getContextPath(); %>
<%
	String sCode = "";
	String temp = "";
	Timestamp startDate = null;
	Timestamp endDate = null;
	PageControllerInfo pageCtrlInfo = new PageControllerInfo();
	
	try
	{
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
        //����ID
        long capitalplanId = -1;
        temp = request.getParameter("capitalplanId");
        if(temp != null && temp.length() > 0)
        {
        	capitalplanId = Long.parseLong(temp);
        }
		// ��ò�������
		String formAction = "";
		formAction = request.getParameter("method");
		
		//������ID
		temp = request.getParameter("checker");
		long modifyuserid = -1;
		if(temp != null && temp.length() > 0)
        {
        	modifyuserid = Long.parseLong(temp);
        }
		//����ʱ��
		temp = request.getParameter("checkDate");
		String checkdate ="";
		if(temp != null && temp.length() > 0)
        {
        	checkdate = temp;
        }
		
		if(formAction != "" && formAction.length()>0){
		     if("toCheck".equals(formAction)){
		     	 AllCapitalPlanBiz allCapitalPlanBiz = new AllCapitalPlanBiz();
             	 long isSucc = 0;
		     	 isSucc = allCapitalPlanBiz.checkAllCapitalPlan(capitalplanId,modifyuserid,Env.getSystemDateString());
			    if(isSucc == 1){
			    	sessionMng.getActionMessages().addMessage("���˳ɹ�");
			    }
			    else{
			    	sessionMng.getActionMessages().addMessage("����ʧ��");
			    }
 				
		     }
		     
		     if("toDisCheck".equals(formAction)){
		     	 AllCapitalPlanBiz allCapitalPlanBiz = new AllCapitalPlanBiz();
		     	 long isSucc = 0;
		     	 isSucc = allCapitalPlanBiz.disCheckAllCapitalPlan(capitalplanId);
		     	 if(isSucc == 1){
			    	sessionMng.getActionMessages().addMessage("ȡ�����˳ɹ�");
			    }
			    else{
			    	sessionMng.getActionMessages().addMessage("ȡ������ʧ��");
			    }
		     }
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
	pageCtrlInfo.setSessionMng(sessionMng);
	RequestDispatcher rd = request.getRequestDispatcher(nextURL);
	/* forward�����ҳ�� */
	rd.forward(request, response);
%>
