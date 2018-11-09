<%--
 ҳ������ ��c001.jsp
 ҳ�湦�� : ҵ����־��ѯ - ��ѯcҳ��
 ��    �� ��Li Liang
 ��    �� ��2009-05-26
 ����˵�� ��
 ʵ�ֲ���˵����
 �޸���ʷ ��
--%>

<%@ page contentType="text/html;charset=gbk"%>
<%@ page import="com.iss.itreasury.util.Constant"%>
<%@ page import="com.iss.itreasury.ebank.util.*"%>
<%@ page import="com.iss.system.dao.PageLoader"%>
<%@ page import="com.iss.itreasury.util.PageControllerInfo"%>
<%@ page import="com.iss.itreasury.util.PageController"%>
<%@ page import="com.iss.itreasury.system.translog.bizlogic.TranslogBiz"%>
<%@ page import="com.iss.itreasury.system.translog.dataentity.QueryTransLogInfo"%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<jsp:directive.page import="com.iss.itreasury.util.PageCtrlInfo"/>
<%
    PageCtrlInfo pageInfo = new PageCtrlInfo();
    String strNextPageURL = "";
	String strSuccessPageURL = "";
	String strFailPageURL = "";
	String strActionResult = Constant.ActionResult.FAIL;  	
    
    //����
    String strAction			="";
	//�������
	String orderfield			="";
	String ordertype			="";
	
    String strContext = request.getContextPath() ;
    
	try {
		
		String strTitle = null;
	  	
	  	//�û���¼��� 
        if (sessionMng.isLogin() == false)
        {
        	OBHtml.showCommonMessage(out, sessionMng,  strTitle, "",1, "Gen_E002");
        	out.flush();
        	return;
        }

        // �ж��û��Ƿ���Ȩ�� 
        if (sessionMng.hasRight(request) == false)
        {
            out.println(sessionMng.hasRight(request));
        	OBHtml.showCommonMessage(out, sessionMng, strTitle, "", 1, "Gen_E003");
        	out.flush();
        	return;
        }	
		String strTemp = "";
		
		//ҳ����Ʋ���
		strTemp = (String)request.getAttribute("strAction");
		if (strTemp != null && strTemp.trim().length() > 0)
		{				
			strAction = strTemp.trim();
		}
		strTemp = (String)request.getAttribute("orderfield");
		if (strTemp != null && strTemp.trim().length() > 0)
		{				
			orderfield = strTemp.trim();
		}
		strTemp = (String)request.getAttribute("ordertype");
		if (strTemp != null && strTemp.trim().length() > 0)
		{				
			ordertype = strTemp.trim();
		}
		
		//����
		strTemp = (String)request.getAttribute("strSuccessPageURL");
		if (strTemp != null && strTemp.trim().length() > 0)					//�ɹ�ҳ��
		{				
			strSuccessPageURL = strTemp.trim();
		}
		strTemp = (String)request.getAttribute("strFailPageURL");
		if (strTemp != null && strTemp.trim().length() > 0)					 //ʧ��ҳ��
		{				
			strFailPageURL = strTemp.trim();
		}
		
		pageInfo.convertRequestToDataEntity(request);
		
		TranslogBiz translogBiz = new TranslogBiz();
    	QueryTransLogInfo queryConditionTransLogInfo = new QueryTransLogInfo();
	  	
	  	String startTime = "";
	  	String endTime = "";
	  	
	  	String strPageLoaderKey = (String)request.getAttribute("_pageLoaderKey");	
	  
		strTemp = (String)request.getAttribute("hiduserid");	      //������
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			queryConditionTransLogInfo.setQueryuserid(strTemp) ;
		}
		strTemp = (String)request.getAttribute("username");	      //������
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			queryConditionTransLogInfo.setUsername(strTemp);
		}
		
		queryConditionTransLogInfo.setUsertype(Constant.EBANK_USER);  //�û�����
		
		queryConditionTransLogInfo.setClientid(sessionMng.m_lClientID);//�ͻ����
		
		strTemp = (String)request.getAttribute("startDate");			//��ʼ����
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			queryConditionTransLogInfo.setStartdate(strTemp);
		}
		
		//��װʱ�����															//��ʼʱ��
		String timeTemp1 = (String)request.getAttribute("startTimeHour");
		String timeTemp2 = (String)request.getAttribute("startTimeMinute");
		String timeTemp3 = (String)request.getAttribute("startTimeSecond");
		if((timeTemp1 != null && timeTemp1.trim().length() > 0)||(timeTemp2 != null && timeTemp2.trim().length() > 0)||(timeTemp3 != null && timeTemp3.trim().length() > 0))
		{
			if (timeTemp1 == null || !(timeTemp1.trim().length() > 0))
			{
				timeTemp1="00";
			}
			if (timeTemp2 == null || !(timeTemp1.trim().length() > 0))
			{
				timeTemp2="00";
			}
			if (timeTemp3 == null || !(timeTemp1.trim().length() > 0))
			{
				timeTemp3="00";
			}
			//����λ����ǰ���0�����ڱȽ�
			if(timeTemp1.length()==1)
			{
				timeTemp1="0"+timeTemp1;
			}
			if(timeTemp2.length()==1)
			{
				timeTemp2="0"+timeTemp2;
			}
			if(timeTemp3.length()==1)
			{
				timeTemp3="0"+timeTemp3;
			}
			startTime = timeTemp1+":"+timeTemp2+":"+timeTemp3;
			queryConditionTransLogInfo.setStarttime(startTime);
			queryConditionTransLogInfo.setStartTimeHour(timeTemp1);
			queryConditionTransLogInfo.setStartTimeMinute(timeTemp2);
			queryConditionTransLogInfo.setStartTimeSecond(timeTemp3);
		}
		
		strTemp = (String)request.getAttribute("endDate");             //��������
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			queryConditionTransLogInfo.setEnddate(strTemp);
		}
		//��װʱ�����															//����ʱ��
		timeTemp1 = (String)request.getAttribute("endTimeHour");         
		timeTemp2 = (String)request.getAttribute("endTimeMinute");
		timeTemp3 = (String)request.getAttribute("endTimeSecond");
		if((timeTemp1 != null && timeTemp1.trim().length() > 0)||(timeTemp2 != null && timeTemp2.trim().length() > 0)||(timeTemp3 != null && timeTemp3.trim().length() > 0))
		{
			if (timeTemp1 == null || !(timeTemp1.trim().length() > 0))
			{
				timeTemp1="00";
			}
			if (timeTemp2 == null || !(timeTemp1.trim().length() > 0))
			{
				timeTemp2="00";
			}
			if (timeTemp3 == null || !(timeTemp1.trim().length() > 0))
			{
				timeTemp3="00";
			}
			//����λ����ǰ���0�����ڱȽ�
			if(timeTemp1.length()==1)
			{
				timeTemp1="0"+timeTemp1;
			}
			if(timeTemp2.length()==1)
			{
				timeTemp2="0"+timeTemp2;
			}
			if(timeTemp3.length()==1)
			{
				timeTemp3="0"+timeTemp3;
			}
			endTime = timeTemp1+":"+timeTemp2+":"+timeTemp3;
			queryConditionTransLogInfo.setEndtime(endTime);
			queryConditionTransLogInfo.setEndTimeHour(timeTemp1);
			queryConditionTransLogInfo.setEndTimeMinute(timeTemp2);
			queryConditionTransLogInfo.setEndTimeSecond(timeTemp3);
		}
		strTemp = (String)request.getAttribute("trasLogType");       //��־����
		if(strTemp != null && strTemp.trim().length() > 0)
		{
			queryConditionTransLogInfo.setQuerylogtype(strTemp);
		}
		strTemp = (String)request.getAttribute("actionResult");      //�������
		if(strTemp != null && strTemp.trim().length() > 0)
		{
			queryConditionTransLogInfo.setQuerystatus(strTemp);
		}
		//����
		long longTemp = sessionMng.m_lCurrencyID;
		if(longTemp > 0)
		{
			queryConditionTransLogInfo.setCurrencyid(longTemp);
		}	
		//���´�
		longTemp = Long.parseLong(request.getParameter("officeId"));
		if(longTemp > 0)
		{
			queryConditionTransLogInfo.setOfficeid(longTemp);
		}
		
	  	PageLoader pageLoader = null;
	  	
	  	
	  	if(strAction.equals("search")||strPageLoaderKey == null||strPageLoaderKey.equals("null"))
		{
			pageLoader =translogBiz.queryTransLogInfo(queryConditionTransLogInfo);
			strPageLoaderKey = sessionMng.setPageLoader(pageLoader);
			sessionMng.setQueryCondition(strPageLoaderKey,queryConditionTransLogInfo);		
			request.setAttribute("_pageLoaderKey",strPageLoaderKey);
			//�ɹ��������
		}
		else
		{
			pageLoader = sessionMng.getPageLoader(strPageLoaderKey);
			pageLoader.setOrderBy(" order by "+orderfield+" "+ordertype);
			ordertype=ordertype.equals("asc")?"desc":"asc";
			request.setAttribute("ordertype",ordertype);
			sessionMng.setQueryCondition(strPageLoaderKey,queryConditionTransLogInfo);
			request.setAttribute("_pageLoaderKey",strPageLoaderKey);
			//�ɹ��������
		}
		
		strNextPageURL = strContext+"/pagecontrol.jsp?strSuccessPageURL="+strSuccessPageURL+"&strFailPageURL="+strFailPageURL;
		pageInfo.setStrNextPageURL(strNextPageURL);
	}
	catch( Exception exp ) 
	{
		exp.printStackTrace();
		strActionResult = Constant.ActionResult.FAIL;
		sessionMng.getActionMessages().addMessage("����ʧ�ܣ�"+exp.getMessage());
	}
	
	PageControllerInfo pageControllerInfo = new PageControllerInfo();
	pageControllerInfo.setSessionMng(sessionMng);
	pageControllerInfo.setUrl(pageInfo.getStrNextPageURL());
  	RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
	rd.forward(request, response);
%>