<%--
 页面名称 ：c003.jsp
 页面功能 : 资金计划申报查询 控制页面   v001.jsp ————>c003.jsp ————>v003.jsp
 作    者 ：ylguo
 日    期 ：2008-10-23
 特殊说明 ：
 修改历史 ：
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
	 String strTitle = "[资金计划申报查找]";
	 
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
        
	    pageCtrlInfo.convertRequestToDataEntity(request);
         		 
		CapitalPlanInfo conditionInfo = new CapitalPlanInfo();
		conditionInfo.convertRequestToDataEntity(request);//将查询条件封装到一个对象中去
		//调用BIZ层获得查询的结果
		AllCapitalPlanBiz planBiz = new AllCapitalPlanBiz();
		Collection colResult = planBiz.findCapitalPlan(conditionInfo);
		request.setAttribute("capitalResults", colResult);
		
	    //构建页面分发时需要用到的实体
	    pageCtrlInfo.success();
	}catch(IException exp){
    	sessionMng.getActionMessages().addMessage(exp.getMessage());
    	exp.printStackTrace();
 		pageCtrlInfo.fail();
	}

   /* 设置返回地址 */
	String nextURL = pageCtrlInfo.getP_NextPageURL();
	JSPLogger.info("进入下一页面："+nextURL);		
	pageCtrlInfo.setSessionMng(sessionMng);
	RequestDispatcher rd = request.getRequestDispatcher(nextURL);
	/* forward到结果页面 */
	rd.forward(request, response);		
%>


