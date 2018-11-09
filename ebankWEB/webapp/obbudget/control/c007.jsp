<%
/**ҳ�湦��˵��
 * ҳ������ ��c007.jsp
 * ҳ�湦�� : 
 * ��    �� ��barneyliu	
 * ��    �� ��2005-10-30
 */
%>

<%@ page contentType = "text/html;charset=gbk"%>
<!--�ർ�벿�ֿ�ʼ-->
 
<%@ page import="com.iss.itreasury.util.Constant.PageControl"%>
<%@ page import="com.iss.itreasury.util.PageCtrlInfo"%>
<%@ page import="com.iss.itreasury.ebank.util.*"%>
<%@ page import="com.iss.itreasury.ebank.obbudget.bizlogic.OBBudgetBiz"%>
<%@ page import="com.iss.itreasury.ebank.obbudget.dataentity.OBBudgetInfo"%>
<%@ page import="com.iss.system.dao.PageLoader"%>
<%@ page import="com.iss.system.dao.PageLoaderInfo"%>
<%@ page import="com.iss.itreasury.util.PageControllerInfo"%>
<%@ page import="com.iss.itreasury.util.PageController"%>
<!--�ർ�벿�ֽ���-->
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%String strContext = request.getContextPath();%>
<%@ include file="/common/common.jsp" %>

<%
		

		PageCtrlInfo pageCtrl = new PageCtrlInfo();
		PageLoaderInfo pageLoaderInfo = null;
		//PageControl pageInfo = new PageControl();
     
		String strSuccessPageURL =  "";
		String strFailPageURL    =  "";
    try
	{

		 System.out.println("----------------- ����ҳ�� c007.jsp -----------------");
		
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
		
			//��ȡҳ�������Ϣ
			pageCtrl.convertRequestToDataEntity(request);
			strSuccessPageURL = pageCtrl.getStrSuccessPageURL();
   			strFailPageURL = pageCtrl.getStrFailPageURL();
	//		Log.print("pagectrl-----------"+pageCtrl);
		 
		//��ҳ����pageLoader
		PageLoader pageLoader = null;
		
		//��request �л������ҵ������
		String strPageLoaderKey = (String)request.getAttribute("pageLoaderKey");
		if( request.getAttribute("pageLoaderKey") == null )
		{
			strPageLoaderKey = (String)request.getParameter("pageLoaderKey");
		}
		
		//ͳ�ƶ���
		String strStatKey = "";
		
		//�������
		OBBudgetInfo paramInfo        = new OBBudgetInfo();
		OBBudgetInfo paramInfo_2      = new OBBudgetInfo();
	 	OBBudgetBiz biz               = new OBBudgetBiz();
 

 		/** ��ȡsessionMng���� **/
		paramInfo.setClientID(sessionMng.m_lClientID);           //�ͻ�
		 

		/** ��ȡ�����ݣ�����AcctTransParam���� **/		
		paramInfo.convertRequestToDataEntity(request);  
		 
		Log.print("paramInfo"+paramInfo);

 
		  
        if(pageCtrl.getStrAction().equals("findFirst"))
		{
            System.out.println("PageLoader ��һ�β���");
		   
		   //�б���Ϣ
		   pageLoader = biz.query(paramInfo);
		 		 
		}
		else //if( pageCtrl.getStrAction().equals("find") )
		{
			 System.out.println("PageLoader ���ǵ�һ�β���");
			 
			paramInfo_2 = (OBBudgetInfo)sessionMng.getQueryCondition("queryConditions");
			
			//paramInfo_2.setDesc( paramInfo.getDesc() );
			//paramInfo_2.setOrderField( paramInfo.getOrderField() );
			
			//�б���Ϣ
			pageLoader = sessionMng.getPageLoader(strPageLoaderKey);
			
			pageLoaderInfo = (pageLoader == null)?null:pageLoader.getPageLoaderInfo();
			
			String strTemp = (String)request.getParameter("pageLoaderInfo_rowPerPage");
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				pageLoaderInfo.setRowPerPage(Integer.valueOf(strTemp).intValue());
			}
			strTemp = (String)request.getParameter("pageLoaderInfo_pageNo");
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				pageLoaderInfo.setPageNo(Integer.valueOf(strTemp).intValue());
			}
			
			strTemp = (String)request.getParameter("strOrderBy");
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				pageLoader.setOrderBy( strTemp.trim() );
			}
			
			 
			
		}
		
		
		//����pageLoader Keyֵ
		strPageLoaderKey = sessionMng.setPageLoader(pageLoader);
		
		
	    System.out.println("pageLoader �Ѿ����ɣ�������");
		
		//����strPageLoaderKey������˲�ѯ����param
		if( pageCtrl.getStrAction().equals("findFirst") )
		{
			sessionMng.setQueryCondition("queryConditions",paramInfo);
		}
		else if( pageCtrl.getStrAction().equals("find") )
		{
			sessionMng.setQueryCondition("queryConditions",paramInfo_2);
		}
		
		request.setAttribute("_pageLoaderKey",strPageLoaderKey);
		request.setAttribute("strSuccessPageURL",pageCtrl.getStrSuccessPageURL());
		request.setAttribute("strFailPageURL",pageCtrl.getStrFailPageURL());
		
		pageCtrl.success();
		System.out.println("@@@@@@@@@@@@@@@@@comein cccccccccccccccc1111111111111");
		/**ҵ���߼�����*/
	}
	catch( Exception exp )
	{
		 
		exp.printStackTrace();
		Log.print(exp.getMessage());
		pageCtrl.fail();
		OBHtml.showCommonMessage(out,sessionMng,"","",OBConstant.ShowMenu.NO,"Gen_E001");
	}
	
	
	String nextURL = strContext + "/pagecontrol.jsp";
	Log.print("������һҳ�棺"+nextURL);	
	
	//����ҳ��ַ�ʱ��Ҫ�õ���ʵ��
	PageControllerInfo pageControllerInfo = new PageControllerInfo();
	pageControllerInfo.setSessionMng(sessionMng);
	pageControllerInfo.setUrl(nextURL);
	//�ַ�
	RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
	
	System.out.println("@@@@@@@@@@@@@@@@@comein cccccccccccccccc 222222222222222222");
	rd.forward( request,response );
	
%>
