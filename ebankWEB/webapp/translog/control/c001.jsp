<%--
 页面名称 ：c001.jsp
 页面功能 : 业务日志查询 - 查询c页面
 作    者 ：Li Liang
 日    期 ：2009-05-26
 特殊说明 ：
 实现操作说明：
 修改历史 ：
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
    
    //动作
    String strAction			="";
	//排序参数
	String orderfield			="";
	String ordertype			="";
	
    String strContext = request.getContextPath() ;
    
	try {
		
		String strTitle = null;
	  	
	  	//用户登录检测 
        if (sessionMng.isLogin() == false)
        {
        	OBHtml.showCommonMessage(out, sessionMng,  strTitle, "",1, "Gen_E002");
        	out.flush();
        	return;
        }

        // 判断用户是否有权限 
        if (sessionMng.hasRight(request) == false)
        {
            out.println(sessionMng.hasRight(request));
        	OBHtml.showCommonMessage(out, sessionMng, strTitle, "", 1, "Gen_E003");
        	out.flush();
        	return;
        }	
		String strTemp = "";
		
		//页面控制参数
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
		
		//内容
		strTemp = (String)request.getAttribute("strSuccessPageURL");
		if (strTemp != null && strTemp.trim().length() > 0)					//成功页面
		{				
			strSuccessPageURL = strTemp.trim();
		}
		strTemp = (String)request.getAttribute("strFailPageURL");
		if (strTemp != null && strTemp.trim().length() > 0)					 //失败页面
		{				
			strFailPageURL = strTemp.trim();
		}
		
		pageInfo.convertRequestToDataEntity(request);
		
		TranslogBiz translogBiz = new TranslogBiz();
    	QueryTransLogInfo queryConditionTransLogInfo = new QueryTransLogInfo();
	  	
	  	String startTime = "";
	  	String endTime = "";
	  	
	  	String strPageLoaderKey = (String)request.getAttribute("_pageLoaderKey");	
	  
		strTemp = (String)request.getAttribute("hiduserid");	      //操作人
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			queryConditionTransLogInfo.setQueryuserid(strTemp) ;
		}
		strTemp = (String)request.getAttribute("username");	      //操作人
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			queryConditionTransLogInfo.setUsername(strTemp);
		}
		
		queryConditionTransLogInfo.setUsertype(Constant.EBANK_USER);  //用户类型
		
		queryConditionTransLogInfo.setClientid(sessionMng.m_lClientID);//客户编号
		
		strTemp = (String)request.getAttribute("startDate");			//开始日期
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			queryConditionTransLogInfo.setStartdate(strTemp);
		}
		
		//组装时间参数															//开始时间
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
			//将个位数字前面加0，用于比较
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
		
		strTemp = (String)request.getAttribute("endDate");             //结束日期
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			queryConditionTransLogInfo.setEnddate(strTemp);
		}
		//组装时间参数															//结束时间
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
			//将个位数字前面加0，用于比较
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
		strTemp = (String)request.getAttribute("trasLogType");       //日志类型
		if(strTemp != null && strTemp.trim().length() > 0)
		{
			queryConditionTransLogInfo.setQuerylogtype(strTemp);
		}
		strTemp = (String)request.getAttribute("actionResult");      //操作结果
		if(strTemp != null && strTemp.trim().length() > 0)
		{
			queryConditionTransLogInfo.setQuerystatus(strTemp);
		}
		//货币
		long longTemp = sessionMng.m_lCurrencyID;
		if(longTemp > 0)
		{
			queryConditionTransLogInfo.setCurrencyid(longTemp);
		}	
		//办事处
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
			//成功完成设置
		}
		else
		{
			pageLoader = sessionMng.getPageLoader(strPageLoaderKey);
			pageLoader.setOrderBy(" order by "+orderfield+" "+ordertype);
			ordertype=ordertype.equals("asc")?"desc":"asc";
			request.setAttribute("ordertype",ordertype);
			sessionMng.setQueryCondition(strPageLoaderKey,queryConditionTransLogInfo);
			request.setAttribute("_pageLoaderKey",strPageLoaderKey);
			//成功完成设置
		}
		
		strNextPageURL = strContext+"/pagecontrol.jsp?strSuccessPageURL="+strSuccessPageURL+"&strFailPageURL="+strFailPageURL;
		pageInfo.setStrNextPageURL(strNextPageURL);
	}
	catch( Exception exp ) 
	{
		exp.printStackTrace();
		strActionResult = Constant.ActionResult.FAIL;
		sessionMng.getActionMessages().addMessage("操作失败！"+exp.getMessage());
	}
	
	PageControllerInfo pageControllerInfo = new PageControllerInfo();
	pageControllerInfo.setSessionMng(sessionMng);
	pageControllerInfo.setUrl(pageInfo.getStrNextPageURL());
  	RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
	rd.forward(request, response);
%>