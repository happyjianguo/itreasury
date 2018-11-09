<%--
 ҳ������ ��c031.jsp
 ҳ�湦�� : ��������������
 ��    �� ��ypxu
 ��    �� ��2007-4-16
 ����˵�� ��
 ʵ�ֲ���˵����
 �޸���ʷ ��
--%>
<%@ page contentType="text/html;charset=gbk" %>	
<%@ page import="java.util.Collection" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.iss.itreasury.util.PageCtrlInfo" %>
<%@ page import="com.iss.itreasury.util.Log" %>	
<%@ page import="com.iss.itreasury.util.Constant" %>		
<%@ page import="com.iss.itreasury.ebank.approval.bizlogic.InutApprovalRelationBiz" %>	
<%@ page import="com.iss.itreasury.ebank.approval.dataentity.InutApprovalRelationInfo" %>	
<%@ page import="com.iss.itreasury.system.util.SYSHTML" %>
<%@ page import="com.iss.itreasury.ebank.util.OBHtml" %>
<%@page import="com.iss.itreasury.util.PageControllerInfo"%>
<%@page import="com.iss.itreasury.util.PageController"%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%	
	String strTitle = "��������������";
	//ҳ�������
	PageCtrlInfo pageInfo = new PageCtrlInfo();	
	try
	{
		//�û���¼��� 
        if (sessionMng.isLogin() == false)
        {
        	OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E002");
        	out.flush();
        	return;
        }

        // �ж��û��Ƿ���Ȩ�� 
        if (sessionMng.hasRight(request) == false)
        {
            out.println(sessionMng.hasRight(request));
        	OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E003");
        	out.flush();
        	return;
        }
		
		//��request�л��ҳ�������Ϣ
		pageInfo.convertRequestToDataEntity(request);
		
		//��ʼ����ѯ��\������\�����
		InutApprovalRelationBiz appRelationBiz = new InutApprovalRelationBiz();
		InutApprovalRelationInfo qInfo = new InutApprovalRelationInfo();
		Collection c_Result = null;
		//��request�л�ò�ѯ������Ϣ
		qInfo.convertRequestToDataEntity(request);	



		long islowerunit = Long.parseLong(request.getParameter("isLowerUnit")) ;  
		qInfo.setIslowerunit(islowerunit);
		



		
		//��ѯ
		c_Result = appRelationBiz.queryByConditions(qInfo);
		//����ѯ�������request��
		request.setAttribute("c_Result",c_Result);
		sessionMng.setQueryCondition("qInfo",qInfo);

	    pageInfo.success();
	}
	catch( Exception exp )
	{
		exp.printStackTrace();
		sessionMng.getActionMessages().addMessage(exp);	
		//�����쳣,���������Ϊʧ��	
		pageInfo.fail();
	}	
	//�������������request�� 
	request.setAttribute("strActionResult",pageInfo.getStrActionResult());

	//ת����һҳ�� 
	Log.print("Next Page URL:"+pageInfo.getStrNextPageURL());
		//����ҳ��ַ�ʱ��Ҫ�õ���ʵ��
	PageControllerInfo pageControllerInfo = new PageControllerInfo();
	pageControllerInfo.setSessionMng(sessionMng);
	pageControllerInfo.setUrl(pageInfo.getStrNextPageURL());
	//�ַ�
	RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
	rd.forward( request,response );
%>