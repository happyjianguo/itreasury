<%--
 页面名称 ：c008.jsp
 页面功能 : 资金计划-查询 控制页面   v008.jsp ————>c008.jsp ————>v009.jsp
 作    者 ：ylguo
 日    期 ：2008-12-04
 特殊说明 ：
 修改历史 ：
--%>
<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="com.iss.itreasury.ebank.fundplan.bizlogic.AllCapitalPlanBiz,
				 com.iss.itreasury.ebank.fundplan.dataentity.CapitalPlanCondition,
				 java.util.Collection,
				 java.sql.Timestamp,
				 com.iss.itreasury.util.IException,
				 com.iss.itreasury.ebank.util.OBHtml,
				 com.iss.itreasury.util.PageCtrlInfo,
				 com.iss.itreasury.util.PageControllerInfo,
				 com.iss.itreasury.util.PageController,
				 com.iss.system.dao.PageLoader,
				 com.iss.itreasury.util.Constant"
%>
<%@ page import="com.iss.itreasury.ebank.system.util.JSPLogger"%>
<jsp:directive.page import="com.iss.itreasury.util.DataFormat"/>

<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%
	 String strTitle = "[资金计划查找]";
	 String strActionResult = Constant.ActionResult.FAIL;
     PageControllerInfo pageCtrlInfo = new PageControllerInfo();	
     
     
  try{
		JSPLogger.info("*******进入页面--\\iTreasury-ebank\\fundplan\\control\\c003.jsp*******");	
	
	   /* 用户登录检测 */
        if (sessionMng.isLogin() == false)
        {
            OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1,"Gen_E002");
        	out.flush();
        	return;
        }

        /* 判断用户是否有权限 */
        if (sessionMng.hasRight(request) == false)
        {
        	out.println(sessionMng.hasRight(request));
        	OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1,"Gen_E003");
        	out.flush();
        	return;
        }
        
	    String strTemp =""; 
	    Timestamp startdate = null;
	    Timestamp enddate = null;
	    String cPIDStartCtrl = "";
	    String cPIDEndCtrl = "";
	    long statusId = -2;
	    
	    
		CapitalPlanCondition conditionInfo = new CapitalPlanCondition();
		strTemp = (String)request.getAttribute("startdate");
		if(strTemp!=null && strTemp !="" && strTemp.length()>0){
		
			startdate = DataFormat.getDateTime(strTemp);
		}
		strTemp = (String)request.getAttribute("enddate");
		if(strTemp!=null && strTemp !="" && strTemp.length()>0){
			enddate = DataFormat.getDateTime(strTemp);
		}
		strTemp = (String)request.getAttribute("cPIDStartCtrl");
		if(strTemp!=null && strTemp !="" && strTemp.length()>0){
			cPIDStartCtrl = strTemp;
		}
		strTemp = (String)request.getAttribute("cPIDEndCtrl");
		if(strTemp!=null && strTemp !="" && strTemp.length()>0){
			cPIDEndCtrl = strTemp;
		}
		strTemp = (String)request.getAttribute("statusId");
		if(strTemp!=null && strTemp !="" && strTemp.length()>0){
			statusId = Long.parseLong(strTemp);
		}
		
		
		conditionInfo.setStartdate(startdate);
		conditionInfo.setEnddate(enddate);
		conditionInfo.setStatusId(statusId);
		conditionInfo.setCPIDStartCtrl(cPIDStartCtrl);
		conditionInfo.setCPIDEndCtrl(cPIDEndCtrl);
		conditionInfo.setClientIDStart(sessionMng.m_lClientID);
		conditionInfo.setClientIDEnd(sessionMng.m_lClientID);
		//conditionInfo.convertRequestToDataEntity(request);//将查询条件封装到一个对象中去
		//调用BIZ层获得查询的结果
		AllCapitalPlanBiz planBiz = new AllCapitalPlanBiz();
		PageLoader pageLoader = null;
		String strPageLoaderKey = (String)request.getAttribute("_pageLoaderKey");
		pageLoader = planBiz.queryCapitalPlans(conditionInfo);
		strPageLoaderKey=sessionMng.setPageLoader(pageLoader);
		sessionMng.setQueryCondition(strPageLoaderKey,conditionInfo);
		request.setAttribute("_pageLoaderKey",strPageLoaderKey);
		 //request.getSession().setAttribute("_pageLoaderKey2",strPageLoaderKey);//供v102.jsp使用，解决v102.jsp导出或打印后返回
		
		strActionResult = Constant.ActionResult.SUCCESS;
		
	}catch(IException exp){
    	sessionMng.getActionMessages().addMessage(exp.getMessage());
    	exp.printStackTrace();
 		pageCtrlInfo.fail();
	}
	PageControllerInfo pageControllerInfo = new PageControllerInfo();
	pageControllerInfo.setSessionMng(sessionMng);
	/* 设置返回地址 */
	String strSuccessPageURL = "";
	String strFailPageURL = "";
	strSuccessPageURL = (String)request.getAttribute("strSuccessPageURL");
	strFailPageURL = (String)request.getAttribute("strFailPageURL");
	String strNextPageURL = "/pagecontrol.jsp?strSuccessPageURL="+strSuccessPageURL+"&strFailPageURL="+strFailPageURL;
	pageControllerInfo.setUrl(strNextPageURL);


   	
	
	RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
	rd.forward(request, response);		
%>


