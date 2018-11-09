<%--
 页面名称 ：c005.jsp
 页面功能 : 资金计划申报的修改，只有主表状态为1的才可以进行操作 控制页面  v007.jsp  ————> c006.jsp ————> v001.jsp
 作    者 ：ylguo
 日    期 ：2008-10-24
 特殊说明 ：
 修改历史 ：
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
	/* 标题固定变量 */
	String strTitle = "[资金计划]";
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
	    /* 用户登录检测 */
        if (sessionMng.isLogin() == false)
        {
            OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E002");
        	out.flush();
        	return;
        }
		
        /* 判断用户是否有权限 */
        if (sessionMng.hasRight(request) == false)
        {
        	out.println(sessionMng.hasRight(request));
        	OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E003");
        	out.flush();
        	return;
        }
         pageCtrlInfo.convertRequestToDataEntity(request);
        //主表ID
        long capitalplanId = -1;
        temp = request.getParameter("capitalplanId");
        if(temp != null && temp.length() > 0)
        {
        	capitalplanId = Long.parseLong(temp);
        }
		// 获得操作方法
		String formAction = "";
		formAction = request.getParameter("method");
		
		//复核人ID
		temp = request.getParameter("checker");
		long modifyuserid = -1;
		if(temp != null && temp.length() > 0)
        {
        	modifyuserid = Long.parseLong(temp);
        }
		//复核时间
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
			    	sessionMng.getActionMessages().addMessage("复核成功");
			    }
			    else{
			    	sessionMng.getActionMessages().addMessage("复核失败");
			    }
 				
		     }
		     
		     if("toDisCheck".equals(formAction)){
		     	 AllCapitalPlanBiz allCapitalPlanBiz = new AllCapitalPlanBiz();
		     	 long isSucc = 0;
		     	 isSucc = allCapitalPlanBiz.disCheckAllCapitalPlan(capitalplanId);
		     	 if(isSucc == 1){
			    	sessionMng.getActionMessages().addMessage("取消复核成功");
			    }
			    else{
			    	sessionMng.getActionMessages().addMessage("取消复核失败");
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
     /* 设置返回地址 */
	String nextURL = pageCtrlInfo.getP_NextPageURL();
	pageCtrlInfo.setSessionMng(sessionMng);
	RequestDispatcher rd = request.getRequestDispatcher(nextURL);
	/* forward到结果页面 */
	rd.forward(request, response);
%>
