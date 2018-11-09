<%--
/*
 * ��������	c002.jsp
 * ����˵��������Ԥ������ҳ
 * �������ߣ�banreyliu
 * ������ڣ�2006-09-04
 */
--%>
<%@ page contentType="text/html;charset=gbk" %>
<!-- ������Ҫ����,��������.* -->
<%@ page import="com.iss.itreasury.util.Log"%>
<%@ page import="com.iss.itreasury.util.IException"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtml"%>
<%@ page import="com.iss.itreasury.util.PageCtrlInfo"%>
<%@ page import="com.iss.itreasury.ebank.obbudget.bizlogic.OBBudgetBiz"%>
<%@ page import="com.iss.itreasury.ebank.obbudget.dataentity.OBBudgetInfo"%>
<%@ page import="com.iss.itreasury.util.PageControllerInfo"%>
<%@ page import="com.iss.itreasury.util.PageController"%>
<!-- ������û���ǰ�ػ���Session -->
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<%
 
     String strTitle = "Ԥ�㸴��"; 
     String action = "";
PageCtrlInfo pageInfo = new PageCtrlInfo();

/** ����ҵ��ʵ���� */
OBBudgetInfo info = null;

try {
	 /** Ȩ�޼�� **/
    System.out.println("=================����ҳ��../control/c002.jsp=========");


     // �û���¼��� 
		if (sessionMng.isLogin() == false) {
			OBHtml.showCommonMessage(out, sessionMng, "", "", 1,
			"Gen_E002");
			out.flush();
			return;
		}

		// �ж��û��Ƿ���Ȩ�� 
		if (sessionMng.hasRight(request) == false) {
			out.println(sessionMng.hasRight(request));
			OBHtml.showCommonMessage(out, sessionMng, "", "", 1,
			"Gen_E003");
			out.flush();
			return;
		}

	pageInfo.convertRequestToDataEntity(request);
	//System.out.println("barney@@@@@@"+pageInfo);
	if(request.getParameter("action")!=null)
	{
		 action = (String)request.getParameter("action");
		 System.out.println("barney@@@@@@@@@@@"+action);
	}
	
	info = new OBBudgetInfo();
 
	info.convertRequestToDataEntity(request);
	System.out.println(info);
 

	OBBudgetBiz biz = new OBBudgetBiz();
	 if(action.equalsIgnoreCase("match"))
	 {
	 	long retlong = biz.matching(info,sessionMng.m_lUserID);
		if(retlong > 0)
		{
			System.out.println("==========match=========match=========match");		
			OBBudgetInfo rInfo = new OBBudgetInfo();
			rInfo = biz.findByID(retlong);
			System.out.println("barney"+rInfo);
			request.setAttribute("rInfo",rInfo);
			pageInfo.success();
	    	//pageInfo.setStrNextPageURL("../view/v003.jsp");
		}
		else
		{System.out.println("û����ƥ���Ԥ�㣬������¼��");
		request.setAttribute("CInfo",info);
		 pageInfo.setStrNextPageURL("../view/v002.jsp?RID=-1");
		}
	 }
	 else if(action.equalsIgnoreCase("check"))
	 {System.out.println("==========check=========check=========check"+info.getId());	
	 	long retlong = biz.check(info.getId(),sessionMng.m_lUserID);
		if(retlong > 0)
		{		 
			pageInfo.success();
	    	pageInfo.setStrNextPageURL("../view/v002.jsp?RID="+retlong);
		}
		else
		{System.out.println("����ʧ��");
		 pageInfo.setStrNextPageURL("../view/v002.jsp?RID=-2");
		}
	 }
	System.out.println("Next Page URL:"+pageInfo.getStrNextPageURL());	
	//����ҳ��ַ�ʱ��Ҫ�õ���ʵ��
	PageControllerInfo pageControllerInfo = new PageControllerInfo();
	pageControllerInfo.setSessionMng(sessionMng);
	pageControllerInfo.setUrl(pageInfo.getStrNextPageURL());
	//�ַ�
	RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));

	rd.forward( request,response );
	
	 }
	catch (IException ie)
	{	
        OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);		
		return;
    }
%>
