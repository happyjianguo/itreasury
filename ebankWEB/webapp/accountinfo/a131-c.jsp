<%
/**ҳ�湦��˵��
 * ҳ������ ��c002.jsp
 * ҳ�湦�� : ��ʷ����ѯ   ���Ʋ�
 * ��    �� ��barneyliu	
 * ��    �� ��2005-11-8
 * ��ʵ��˵����
 *				1����ʷ���ײ�ѯ
 * ����˵�� ��
 * �޸���ʷ ��
 */
%>

<%@ page contentType = "text/html;charset=gbk"%>
<!--�ർ�벿�ֿ�ʼ-->

<%@ page import="com.iss.itreasury.util.Log"%>
<%@ page import="com.iss.itreasury.util.Constant.PageControl"%>
<%@ page import="com.iss.itreasury.util.PageCtrlInfo"%>

<%@ page import="com.iss.itreasury.ebank.util.OBHtml"%>
<%@ page import="com.iss.itreasury.ebank.obquery.bizlogic.OBHisBalanceBiz"%>
<%@ page import="com.iss.itreasury.ebank.obquery.dataentity.OBQueryAccInfo"%>
<%@ page import="com.iss.system.dao.PageLoader"%>
<%@ page import="com.iss.system.dao.PageLoaderInfo"%>
<%@page import="com.iss.itreasury.util.PageControllerInfo"%>
<%@page import="com.iss.itreasury.util.PageController"%>
<!--�ർ�벿�ֽ���-->

<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<%
		

		PageCtrlInfo pageCtrl = new PageCtrlInfo();
		PageLoaderInfo pageLoaderInfo = null;
		//PageControl pageInfo = new PageControl();
     
		String strSuccessPageURL =  "";
		String strFailPageURL    =  "";
    try
	{

		 System.out.println("----------------- ����ҳ�� a131-c.jsp -----------------");
		
        //��¼���
		//������
		/* if (sessionMng.isLogin() == false)
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
		OBQueryAccInfo paramInfo        = new OBQueryAccInfo();
		OBQueryAccInfo paramInfo_2      = new OBQueryAccInfo();
	 	OBHisBalanceBiz biz             = new OBHisBalanceBiz();
 
		
		/** ��ȡsessionMng���� **/
		paramInfo.setClientid(sessionMng.m_lClientID);           //�ͻ�
		paramInfo.setUserid(sessionMng.m_lUserID);				 //�û�
		  
		
		/** ��ȡ�����ݣ�����AcctTransParam���� **/		
		paramInfo.convertRequestToDataEntity(request);
		System.out.println("aaaaaaaaaaaaaaaaaaaaaa"+paramInfo.getCountryId());
		Log.print("paramInfo"+paramInfo);

 
        if(pageCtrl.getStrAction().equals("findFirst"))
		{
            System.out.println("PageLoader ��һ�β���");
		   
		   //�б���Ϣ
		   pageLoader = biz.QueryHistoryBalance(paramInfo);
		 		 
		}
		else //if( pageCtrl.getStrAction().equals("find") )
		{
			 System.out.println("PageLoader ���ǵ�һ�β���");
			 
			paramInfo_2 = (OBQueryAccInfo)sessionMng.getQueryCondition("queryConditions");
			
			paramInfo_2.setDesc( paramInfo.getDesc() );
			paramInfo_2.setOrderField( paramInfo.getOrderField() );
			
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
		
		/**ҵ���߼�����*/
	}
	catch( Exception exp )
	{
		 
		exp.printStackTrace();
		Log.print(exp.getMessage());
		pageCtrl.fail();
		OBHtml.showCommonMessage(out,sessionMng,"","",1,"Gen_E001");
	}
	
	
	String nextURL = "/pagecontrol.jsp";
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